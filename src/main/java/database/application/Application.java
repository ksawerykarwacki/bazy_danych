package database.application;

import database.application.lokale.LokaleScreen;
import database.application.stanowiska.StanowiskaScreen;
import database.application.tabs.Tabs;
import io.micronaut.context.ApplicationContext;
import io.micronaut.runtime.Micronaut;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;

@Slf4j
public class Application extends javafx.application.Application {

    ApplicationContext applicationContext;

    VBox wrapper;
    VBox menu;
    Tabs tabs;
    private MenuBar menuBar;

    @Override
    public void start(Stage stage) {
        stage.setTitle("Hello World!");
        this.applicationContext = Micronaut.run(Application.class);
        GridPane root = applicationContext.getBean(StanowiskaScreen.class);
        generateScene();
        Scene scene = new Scene(wrapper, 500, 450);
        scene.getStylesheets().add(this.getClass().getClassLoader().getResource("style.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    private void generateScene() {

        tabs = applicationContext.getBean(Tabs.class);
        /*Label label = new Label("Menu");
        Button stanowiska = new Button("Stanowiska");

        stanowiska.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                tabs.openStanowiska();
            }
        });*/
        menuBar = new MenuBar();

        Menu hr = new Menu("HR");
        MenuItem stanowiska = new MenuItem("Stanowiska");
        stanowiska.setOnAction(event -> tabs.open(StanowiskaScreen.class));
        hr.getItems().add(stanowiska);

        Menu zarzadzanieLokalami = new Menu("Zarzadzanie lokalami");
        MenuItem lokale = new MenuItem("Lokale");
        lokale.setOnAction(event -> tabs.open(LokaleScreen.class));
        zarzadzanieLokalami.getItems().add(lokale);

        menuBar.getMenus().addAll(hr, zarzadzanieLokalami);
        //menu = new VBox(label, stanowiska);

        wrapper = new VBox(menuBar, tabs);
    }

    @Override
    public void stop() throws Exception {
        applicationContext.stop();
        super.stop();
    }

    public static void main(String[] args) {
        launch();
    }
}
