package database.application.stanowiska;

import database.application.BaseScreenTest;
import database.application.tabs.Tabs;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import org.testfx.api.FxRobot;
import org.testfx.assertions.api.Assertions;
import org.testfx.framework.junit5.Start;

import java.util.Random;

@Slf4j
public class StanowiskaTest extends BaseScreenTest<StanowiskaScreen, Stanowisko, StanowiskaRepository> {

    String name = generateString(10);
    Double amount = getRandomNumber(1000, 1300, 2);
    Double editedAmount = getRandomNumber(1000, 1300, 2);


    @Start
    protected void start(Stage stage) {
        repository = applicationContext.getBean(StanowiskaRepository.class);
        baseClass = StanowiskaScreen.class;
        Tabs tabs = applicationContext.getBean(Tabs.class);
        tabs.open(baseClass);
        tableScreen = applicationContext.getBean(baseClass);
        tableView = (TableView<Stanowisko>) tableScreen.getChildren().get(1);
        stage.setScene(new Scene(new StackPane(tabs), 500, 500));
        stage.show();
    }


    public void add(FxRobot robot) {
        robot.clickOn("Dodaj");
        robot.clickOn("#stanowisko").write(name);
        robot.doubleClickOn("#wynagrodzenie").write(amount.toString());
        robot.clickOn("Zapisz");
    }

    @Test
    void addItem(FxRobot robot) {
        int starting = tableView.getItems().size();

        add(robot);

        Assertions.assertThat(tableView).hasExactlyNumRows(starting+1);
        Assertions.assertThat(tableView).hasChild(name);
        for(Stanowisko stanowisko : repository.findAll()) {
            if(stanowisko.getStanowisko().equals(name)) {
                org.junit.jupiter.api.Assertions.assertEquals(amount, stanowisko.getWynagrodzenie());
            }
        }
    }

    @Test
    void editItem(FxRobot robot) {
        add(robot);

        int starting = tableView.getItems().size();

        robot.clickOn(name);

        robot.clickOn("Edytuj");
        robot.doubleClickOn("#wynagrodzenie").write(editedAmount.toString());
        robot.clickOn("Zapisz");

        Assertions.assertThat(tableView).hasExactlyNumRows(starting);
        Assertions.assertThat(tableView).hasChild(name);
        for(Stanowisko stanowisko : repository.findAll()) {
            if(stanowisko.getStanowisko().equals(name)) {
                org.junit.jupiter.api.Assertions.assertEquals(editedAmount, stanowisko.getWynagrodzenie());
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
