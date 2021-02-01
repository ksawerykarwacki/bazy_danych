package database.application.pracownicy;

import database.application.BaseScreenTest;
import database.application.lokale.Lokal;
import database.application.lokale.LokaleRepository;
import database.application.lokale.LokaleScreen;
import database.application.lokale.LokaleTest;
import database.application.stanowiska.StanowiskaRepository;
import database.application.stanowiska.StanowiskaScreen;
import database.application.stanowiska.StanowiskaTest;
import database.application.stanowiska.Stanowisko;
import database.application.tabs.Tabs;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxRobot;
import org.testfx.assertions.api.Assertions;
import org.testfx.framework.junit5.Start;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
public class PracownicyTest extends BaseScreenTest<PracownicyScreen, Pracownik, PracownicyRepository> {

    String imie = generateString(10);
    String nazwisko = generateString(10);
    Lokal lokal = null;
    Stanowisko stanowisko = null;
    Stanowisko noweStanowisko = null;
    LocalDate data = LocalDate.now();


    /**
     * Will be called with {@code @Before} semantics, i. e. before each test method.
     *
     * @param stage - Will be injected by the test runner.
     */
    @Start
    private void start(Stage stage) {
        lokal = getObject(LokaleRepository.class);
        stanowisko = getObject(StanowiskaRepository.class);
        repository = applicationContext.getBean(PracownicyRepository.class);
        baseClass = PracownicyScreen.class;
        getTabs().open(baseClass);
        tableScreen = applicationContext.getBean(baseClass);
        tableView = (TableView<Pracownik>) tableScreen.getChildren().get(1);
        stage.setScene(new Scene(new StackPane(getTabs()), 500, 500));
        stage.show();
    }

    void add(FxRobot robot) {
        robot.clickOn("Dodaj");
        robot.clickOn("#imie").write(imie);
        robot.clickOn("#nazwisko").write(nazwisko);
        robot.clickOn("#lokal");
        robot.clickOn(lokal.toString());
        robot.clickOn("#stanowisko");
        robot.clickOn(stanowisko.toString());
        robot.clickOn("#dataZatrudnienia").write(data.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT))).push(KeyCode.ENTER);
        robot.clickOn("Zapisz");
    }

    /**
     * @param robot - Will be injected by the test runner.
     */
    @Test
    void addItem(FxRobot robot) {
        int starting = tableView.getItems().size();

        add(robot);

        Assertions.assertThat(tableView).hasExactlyNumRows(starting+1);
        Assertions.assertThat(tableView).hasChild(imie);
        for(Pracownik pracownik : repository.findAll()) {
            if(pracownik.getImie().equals(imie)) {
                org.junit.jupiter.api.Assertions.assertEquals(nazwisko, pracownik.getNazwisko());
                org.junit.jupiter.api.Assertions.assertEquals(lokal.toString(), pracownik.getLokal().toString());
                org.junit.jupiter.api.Assertions.assertEquals(stanowisko.toString(), pracownik.getStanowisko().toString());
                org.junit.jupiter.api.Assertions.assertEquals(data, pracownik.getDataZatrudnienia());
            }
        }
    }

    @Test
    void editItem(FxRobot robot) {

        add(robot);
        noweStanowisko = StreamSupport.stream(applicationContext.getBean(StanowiskaRepository.class).findAll().spliterator(), false)
                .collect(Collectors.toList()).get(1);
        int starting = tableView.getItems().size();

        robot.clickOn(imie);

        robot.clickOn("Edytuj");
        robot.clickOn("#stanowisko");
        robot.sleep(1, TimeUnit.SECONDS);
        robot.clickOn(noweStanowisko.toString());
        robot.clickOn("Zapisz");

        Assertions.assertThat(tableView).hasExactlyNumRows(starting);
        Assertions.assertThat(tableView).hasChild(imie);
        for(Pracownik pracownik : repository.findAll()) {
            if(pracownik.getImie().equals(imie)) {
                org.junit.jupiter.api.Assertions.assertEquals(noweStanowisko.toString(), pracownik.getStanowisko().toString());
            }
        }
    }
    @Test
    void deleteItem(FxRobot robot) {

        add(robot);
        int starting = tableView.getItems().size();

        robot.clickOn(imie);

        robot.clickOn("Usun");

        Assertions.assertThat(tableView).hasExactlyNumRows(starting-1);

        Assertions.assertThat(tableView).doesNotHaveChild(imie);
    }


}
