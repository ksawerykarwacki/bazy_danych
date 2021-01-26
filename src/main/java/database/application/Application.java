package database.application;

import database.application.dania.DaniaScreen;
import database.application.dostawcy.DostawcyScreen;
import database.application.lokale.LokaleScreen;
import database.application.praownicy.PracownicyScreen;
import database.application.skladniki.SkladnikiScreen;
import database.application.stanowiska.StanowiskaScreen;
import database.application.tabs.Tabs;
import database.application.zamowienia.ZamowieniaScreen;
import database.application.zapasy.ZapasyScreen;
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
        stage.setTitle("Zarzadzanie restauracjami");
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

        MenuItem pracownicy = new MenuItem("Pracownicy");
        pracownicy.setOnAction(event -> tabs.open(PracownicyScreen.class));
        hr.getItems().addAll(stanowiska, pracownicy);

        Menu zarzadzanieLokalami = new Menu("Zarzadzanie lokalami");
        MenuItem lokale = new MenuItem("Lokale");
        lokale.setOnAction(event -> tabs.open(LokaleScreen.class));

        MenuItem zapasy = new MenuItem("Zapasy");
        zapasy.setOnAction(event -> tabs.open(ZapasyScreen.class));

        MenuItem zamowienia = new MenuItem("Zamowienia");
        zamowienia.setOnAction(event -> tabs.open(ZamowieniaScreen.class));

        zarzadzanieLokalami.getItems().addAll(lokale, zapasy, zamowienia);

        Menu menu = new Menu("Menu");
        MenuItem skladniki = new MenuItem("Skladniki");
        skladniki.setOnAction(event -> tabs.open(SkladnikiScreen.class));

        MenuItem dostawcy = new MenuItem("Dostawcy");
        dostawcy.setOnAction(event -> tabs.open(DostawcyScreen.class));

        MenuItem dania = new MenuItem("Dania");
        dania.setOnAction(event -> tabs.open(DaniaScreen.class));

        menu.getItems().addAll(skladniki, dostawcy, dania);

        menuBar.getMenus().addAll(hr, zarzadzanieLokalami, menu);
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
