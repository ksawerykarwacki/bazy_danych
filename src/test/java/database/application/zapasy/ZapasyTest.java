package database.application.zapasy;

import database.application.BaseScreenTest;
import database.application.lokale.Lokal;
import database.application.lokale.LokaleRepository;
import database.application.skladniki.Skladnik;
import database.application.skladniki.SkladnikiRepository;
import database.application.stanowiska.StanowiskaRepository;
import database.application.stanowiska.StanowiskaScreen;
import database.application.stanowiska.Stanowisko;
import database.application.tabs.Tabs;
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
public class ZapasyTest extends BaseScreenTest<ZapasyScreen, Zapas, ZapasRepository> {

    Lokal lokal = null;
    Skladnik skladnik = null;
    Double amount = getRandomNumber(1, 100, 2);
    Double editedAmount = getRandomNumber(1, 100, 2);
    ZapasKey zapasKey;

    @Start
    protected void start(Stage stage) {
        lokal = getObject(LokaleRepository.class);
        skladnik = getObject(SkladnikiRepository.class);
        zapasKey = new ZapasKey();
        zapasKey.setSkladnik(skladnik);
        zapasKey.setLokal(lokal);
        repository = applicationContext.getBean(ZapasRepository.class);
        baseClass = ZapasyScreen.class;
        Tabs tabs = applicationContext.getBean(Tabs.class);
        tabs.open(baseClass);
        tableScreen = applicationContext.getBean(baseClass);
        tableView = (TableView<Zapas>) tableScreen.getChildren().get(1);
        stage.setScene(new Scene(new StackPane(tabs), 500, 500));
        stage.show();
    }


    public void add(FxRobot robot) {
        robot.clickOn("Dodaj");
        robot.clickOn("#lokal");
        robot.sleep(1, TimeUnit.SECONDS);
        robot.clickOn(lokal.toString());
        robot.clickOn("#skladnik");
        robot.sleep(1, TimeUnit.SECONDS);
        robot.clickOn(skladnik.toString());
        robot.doubleClickOn("#ilosc").write(amount.toString());
        robot.clickOn("Zapisz");
    }

    @Test
    void addItem(FxRobot robot) {
        int starting = tableView.getItems().size();

        add(robot);

        Assertions.assertThat(tableView).hasExactlyNumRows(starting+1);
        Assertions.assertThat(tableView).hasChild(lokal.toString());
        for(Zapas zapas : repository.findAll()) {
            if(zapas.getZapasKey().equals(zapasKey)) {
                org.junit.jupiter.api.Assertions.assertEquals(amount, zapas.getIlosc());
            }
        }
    }

    @Test
    void editItem(FxRobot robot) {
        add(robot);

        int starting = tableView.getItems().size();

        robot.clickOn(lokal.toString());

        robot.clickOn("Edytuj");
        robot.doubleClickOn("#ilosc").write(editedAmount.toString());
        robot.clickOn("Zapisz");

        Assertions.assertThat(tableView).hasExactlyNumRows(starting);
        Assertions.assertThat(tableView).hasChild(lokal.toString());
        for(Zapas zapas : repository.findAll()) {
            if(zapas.getZapasKey().equals(zapasKey)) {
                org.junit.jupiter.api.Assertions.assertEquals(editedAmount, zapas.getIlosc());
            }
        }
    }

    @Test
    void deleteItem(FxRobot robot) {

        add(robot);

        int starting = tableView.getItems().size();

        robot.clickOn(lokal.toString());

        robot.clickOn("Usun");

        Assertions.assertThat(tableView).hasExactlyNumRows(starting-1);

        Assertions.assertThat(tableView).doesNotHaveChild(lokal.toString());
    }
}
