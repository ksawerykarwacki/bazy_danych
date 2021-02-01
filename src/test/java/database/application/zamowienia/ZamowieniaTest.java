package database.application.zamowienia;

import database.application.BaseScreenTest;
import database.application.dania.DaniaRepository;
import database.application.dania.DaniaScreen;
import database.application.dania.Danie;
import database.application.lokale.Lokal;
import database.application.lokale.LokaleRepository;
import database.application.pracownicy.PracownicyRepository;
import database.application.pracownicy.Pracownik;
import database.application.skladniki.Skladnik;
import database.application.skladniki.SkladnikiRepository;
import database.application.tabs.Tabs;
import database.application.zapasy.ZapasRepository;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxRobot;
import org.testfx.assertions.api.Assertions;
import org.testfx.framework.junit5.Start;

import java.util.concurrent.TimeUnit;

@Slf4j
public class ZamowieniaTest extends BaseScreenTest<ZamowieniaScreen, Zamowienie, ZamowieniaRepository> {

    String adres = generateString(10);
    String miasto = generateString(10);
    String editedMiasto = generateString(10);
    Lokal lokal = null;
    Pracownik dostawca = null;
    Danie danie = null;

    @Start
    protected void start(Stage stage) {
        dostawca = getObject(PracownicyRepository.class);
        lokal = dostawca.getLokal();
        danie = getObject(DaniaRepository.class);
        repository = applicationContext.getBean(ZamowieniaRepository.class);
        baseClass = ZamowieniaScreen.class;
        Tabs tabs = applicationContext.getBean(Tabs.class);
        tabs.open(baseClass);
        tableScreen = applicationContext.getBean(baseClass);
        tableView = (TableView<Zamowienie>) tableScreen.getChildren().get(1);
        stage.setScene(new Scene(new StackPane(tabs), 500, 500));
        stage.show();
    }


    public void add(FxRobot robot) {
        robot.clickOn("Dodaj");
        robot.clickOn("#adres").write(adres);
        robot.doubleClickOn("#miasto").write(miasto);
        robot.clickOn("#lokal");
        robot.sleep(1, TimeUnit.SECONDS);
        robot.clickOn(lokal.toString());
        robot.clickOn("#dostawca");
        robot.sleep(1, TimeUnit.SECONDS);
        robot.clickOn(dostawca.toString());
        robot.clickOn("#danie");
        robot.sleep(1, TimeUnit.SECONDS);
        robot.clickOn(danie.toString());
        robot.clickOn("Dodaj");
        robot.clickOn("Zapisz");
    }

    @Test
    void addItem(FxRobot robot) {
        int starting = tableView.getItems().size();

        add(robot);

        Assertions.assertThat(tableView).hasExactlyNumRows(starting+1);
        Assertions.assertThat(tableView).hasChild(adres);
        for(Zamowienie zamowienie : repository.findAll()) {
            if(zamowienie.getAdres().equals(adres)) {
                org.junit.jupiter.api.Assertions.assertEquals(miasto, zamowienie.getMiasto());
                org.junit.jupiter.api.Assertions.assertEquals(lokal.toString(), zamowienie.getNajblizszyLokal().toString());
                org.junit.jupiter.api.Assertions.assertEquals(dostawca.toString(), zamowienie.getDostawca().toString());
                org.junit.jupiter.api.Assertions.assertEquals(danie.toString(), zamowienie.getZamowioneJedzenie().get(0).getDanie().toString());
            }
        }
    }

    @Test
    void editItem(FxRobot robot) {
        add(robot);

        int starting = tableView.getItems().size();

        robot.clickOn(adres);

        robot.clickOn("Edytuj");
        robot.doubleClickOn("#miasto").write(editedMiasto);
        robot.clickOn("Zapisz");

        Assertions.assertThat(tableView).hasExactlyNumRows(starting);
        Assertions.assertThat(tableView).hasChild(adres);
        for(Zamowienie zamowienie : repository.findAll()) {
            if(zamowienie.getAdres().equals(adres)) {
                org.junit.jupiter.api.Assertions.assertEquals(editedMiasto, zamowienie.getMiasto());
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
