package database.application.lokale;

import database.application.BaseScreenTest;
import database.application.tabs.Tabs;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxRobot;
import org.testfx.assertions.api.Assertions;
import org.testfx.framework.junit5.Start;

@Slf4j
public class LokaleTest extends BaseScreenTest<LokaleScreen, Lokal, LokaleRepository> {

    String adres = generateString(10);
    String miasto = generateString(10);
    String kodPocztowy = generateString(6);
    String editedKodPocztowy = generateString(6);
    float powierzchnia = (float) getRandomNumber(20, 140, 2);
    int miejsca = (int) getRandomNumber(5, 50, 0);



    /**
     * Will be called with {@code @Before} semantics, i. e. before each test method.
     *
     * @param stage - Will be injected by the test runner.
     */
    @Start
    private void start(Stage stage) {
        repository = applicationContext.getBean(LokaleRepository.class);
        baseClass = LokaleScreen.class;
        Tabs tabs = applicationContext.getBean(Tabs.class);
        tabs.open(baseClass);
        tableScreen = applicationContext.getBean(baseClass);
        tableView = (TableView<Lokal>) tableScreen.getChildren().get(1);
        stage.setScene(new Scene(new StackPane(tabs), 500, 500));
        stage.show();
    }

    public void add(FxRobot robot) {
        robot.clickOn("Dodaj");
        robot.clickOn("#adres").write(adres);
        robot.clickOn("#miasto").write(miasto);
        robot.clickOn("#kodPocztowy").write(kodPocztowy);
        robot.clickOn("#powierzchnia").write(String.valueOf(powierzchnia));
        robot.clickOn("#miejsca").write(String.valueOf(miejsca));
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
        Assertions.assertThat(tableView).hasChild(adres);
        for(Lokal lokal : repository.findAll()) {
            if(lokal.getAdres().equals(adres)) {
                org.junit.jupiter.api.Assertions.assertEquals(miasto, lokal.getMiasto());
                org.junit.jupiter.api.Assertions.assertEquals(kodPocztowy, lokal.getKodPocztowy());
                org.junit.jupiter.api.Assertions.assertEquals(powierzchnia, lokal.getPowierzchnia());
                org.junit.jupiter.api.Assertions.assertEquals(miejsca, lokal.getMiejscaDlaKlientow());

            }
        }
    }

    @Test
    void editItem(FxRobot robot) {
        add(robot);
        int starting = tableView.getItems().size();

        robot.clickOn(adres);

        robot.clickOn("Edytuj");
        robot.doubleClickOn("#kodPocztowy").write(editedKodPocztowy);
        robot.clickOn("Zapisz");

        Assertions.assertThat(tableView).hasExactlyNumRows(starting);
        Assertions.assertThat(tableView).hasChild(adres);
        for(Lokal lokal : repository.findAll()) {
            if(lokal.getAdres().equals(adres)) {
                org.junit.jupiter.api.Assertions.assertEquals(editedKodPocztowy, lokal.getKodPocztowy());
            }
        }
    }

    @Test
    void deleteItem(FxRobot robot) {
        add(robot);

        int starting = tableView.getItems().size();

        robot.clickOn(adres);

        robot.clickOn("Usun");

        Assertions.assertThat(tableView).hasExactlyNumRows(starting-1);

        Assertions.assertThat(tableView).doesNotHaveChild(adres);
    }


}
