package database.application.dostawcy;

import database.application.BaseScreenTest;
import database.application.skladniki.Skladnik;
import database.application.skladniki.SkladnikiRepository;
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
public class DostawcyTest extends BaseScreenTest<DostawcyScreen, Dostawca, DostawcyRepository> {

    String name = generateString(10);
    Skladnik skladnik = null;
    Double amount = getRandomNumber(2, 120, 2);
    Double editedAmount = getRandomNumber(2, 120, 2);


    @Start
    protected void start(Stage stage) {
        skladnik = applicationContext.getBean(SkladnikiRepository.class).findAll().iterator().next();
        repository = applicationContext.getBean(DostawcyRepository.class);
        baseClass = DostawcyScreen.class;
        Tabs tabs = applicationContext.getBean(Tabs.class);
        tabs.open(baseClass);
        tableScreen = applicationContext.getBean(baseClass);
        tableView = (TableView<Dostawca>) tableScreen.getChildren().get(1);
        stage.setScene(new Scene(new StackPane(tabs), 500, 500));
        stage.show();
    }


    public void add(FxRobot robot) {
        robot.clickOn("Dodaj");
        robot.clickOn("#nazwa").write(name);
        robot.clickOn("#skladnik");
        robot.sleep(1, TimeUnit.SECONDS);
        robot.clickOn(skladnik.toString());
        robot.clickOn("#cena").write(String.valueOf(amount));
        robot.clickOn("Zapisz");
    }

    @Test
    void addItem(FxRobot robot) {
        int starting = tableView.getItems().size();

        add(robot);

        Assertions.assertThat(tableView).hasExactlyNumRows(starting+1);
        Assertions.assertThat(tableView).hasChild(name);
        for(Dostawca dostawca : repository.findAll()) {
            if(dostawca.getDostawca().equals(name)) {
                org.junit.jupiter.api.Assertions.assertEquals(skladnik.toString(), dostawca.getSkladnik().toString());
                org.junit.jupiter.api.Assertions.assertEquals(amount, dostawca.getCena());
            }
        }
    }

    @Test
    void editItem(FxRobot robot) {
        add(robot);

        int starting = tableView.getItems().size();

        robot.clickOn(name);

        robot.clickOn("Edytuj");
        robot.doubleClickOn("#cena").write(editedAmount.toString());
        robot.clickOn("Zapisz");

        Assertions.assertThat(tableView).hasExactlyNumRows(starting);
        Assertions.assertThat(tableView).hasChild(name);
        for(Dostawca dostawca : repository.findAll()) {
            if(dostawca.getDostawca().equals(name)) {
                org.junit.jupiter.api.Assertions.assertEquals(editedAmount, dostawca.getCena());
            }
        }
    }

    @Test
    void deleteItem(FxRobot robot) {

        add(robot);

        int starting = tableView.getItems().size();

        robot.clickOn(name);

        robot.clickOn("Usun");

        Assertions.assertThat(tableView).hasExactlyNumRows(starting-1);

        Assertions.assertThat(tableView).doesNotHaveChild(name);
    }
}
