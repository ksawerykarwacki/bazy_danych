package database.application;

import io.micronaut.context.ApplicationContext;
import io.micronaut.runtime.Micronaut;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;

@Slf4j
public class Application extends javafx.application.Application {

    ApplicationContext applicationContext;

    @Override
    public void start(Stage stage) {
        stage.setTitle("Hello World!");
        this.applicationContext = Micronaut.run(Application.class);
        ClicksService service = applicationContext.findBean(ClicksService.class).get();
        String javaVersion = System.getProperty("java.version");
        String javafxVersion = System.getProperty("javafx.version");
        Label l = new Label("Hello, JavaFX " + javafxVersion + ", running on Java " + javaVersion + ".");
        Button b = new Button("My first button");
        Label comment = new Label("Comment:");
        TextField tf1= new TextField();
        final int[] clicks = {0};
        b.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                Clicks click = new Clicks();
                click.setClicks(clicks[0]);
                click.increment(tf1.getText());
                service.save(click);
                log.info("I clicked the button!");
                l.setText(click.getClickDate().toString() + " " + click.getClicks());
                clicks[0] =  click.getClicks();

            }
        });
        GridPane root = new GridPane();
        root.addRow(0, l);
        root.addRow(1, comment, tf1);
        root.addRow(2, b);
        stage.setScene(new Scene(root, 500, 450));
        stage.show();
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
