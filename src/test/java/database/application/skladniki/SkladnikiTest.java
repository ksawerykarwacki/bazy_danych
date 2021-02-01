package database.application.skladniki;

import database.application.BaseScreenTest;
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

import java.util.Random;

@Slf4j
public class SkladnikiTest extends BaseScreenTest<SkladnikiScreen, Skladnik, SkladnikiRepository> {

    String nazwa = generateString(10);
    String jednostka = generateString(10);
    boolean wegetarianski = new Random().nextBoolean();
    boolean bezglutenowy = new Random().nextBoolean();


    @Start
    protected void start(Stage stage) {
        repository = applicationContext.getBean(SkladnikiRepository.class);
        baseClass = SkladnikiScreen.class;
        Tabs tabs = applicationContext.getBean(Tabs.class);
        tabs.open(baseClass);
        tableScreen = applicationContext.getBean(baseClass);
        tableView = (TableView<Skladnik>) tableScreen.getChildren().get(1);
        stage.setScene(new Scene(new StackPane(tabs), 500, 500));
        stage.show();
    }


    public void add(FxRobot robot) {
        robot.clickOn("Dodaj");
        robot.clickOn("#nazwa").write(nazwa);
        robot.clickOn("#jednostka").write(jednostka);
        if(wegetarianski) {
            robot.clickOn("#wegetarianski");
        }
        if(bezglutenowy) {
            robot.clickOn("#bezglutenowy");
        }
        robot.clickOn("Zapisz");
    }

    @Test
    void addItem(FxRobot robot) {
        int starting = tableView.getItems().size();

        add(robot);

        Assertions.assertThat(tableView).hasExactlyNumRows(starting+1);
        Assertions.assertThat(tableView).hasChild(nazwa);
        for(Skladnik skladnik : repository.findAll()) {
            if(skladnik.getSkladnik().equals(nazwa)) {
                org.junit.jupiter.api.Assertions.assertEquals(jednostka, skladnik.getJednostka());
                org.junit.jupiter.api.Assertions.assertEquals(wegetarianski, skladnik.getWegetarianski());
                org.junit.jupiter.api.Assertions.assertEquals(bezglutenowy, skladnik.getBezglutenowy());
            }
        }
    }

    @Test
    void editItem(FxRobot robot) {
        add(robot);

        int starting = tableView.getItems().size();

        robot.clickOn(nazwa);

        robot.clickOn("Edytuj");
        robot.clickOn("#wegetarianski");
        robot.clickOn("Zapisz");

        Assertions.assertThat(tableView).hasExactlyNumRows(starting);
        Assertions.assertThat(tableView).hasChild(nazwa);
        for(Skladnik skladnik : repository.findAll()) {
            if(skladnik.getSkladnik().equals(nazwa)) {
                org.junit.jupiter.api.Assertions.assertEquals(!wegetarianski, skladnik.getWegetarianski());
            }
        }
    }

    @Test
    void deleteItem(FxRobot robot) {

        add(robot);

        int starting = tableView.getItems().size();

        robot.clickOn(nazwa);

        robot.clickOn("Usun");

        Assertions.assertThat(tableView).hasExactlyNumRows(starting-1);

        Assertions.assertThat(tableView).doesNotHaveChild(nazwa);
    }
}
