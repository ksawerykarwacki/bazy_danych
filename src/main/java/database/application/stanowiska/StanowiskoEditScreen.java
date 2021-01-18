package database.application.stanowiska;

import database.application.DecimalTextFormatter;
import database.application.tabs.Tabs;
import io.micronaut.context.annotation.Bean;
import io.micronaut.context.annotation.Prototype;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import javax.inject.Inject;

@Prototype
public class StanowiskoEditScreen extends Tab {

    @Inject
    StanowiskaRepository repository;
    @Inject
    Tabs tabs;
    Stanowisko stanowisko;
    GridPane grid;
    private final TextField stanowiskoField;
    private final TextField wynagrodzenieField;

    public StanowiskoEditScreen() {
        super("Nowe Stanowisko");
        grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);

        this.stanowisko = new Stanowisko();

        Label stanowiskoLabel = new Label("Stanowisko");
        grid.add(stanowiskoLabel, 0, 0);

        stanowiskoField = new TextField();
        grid.add(stanowiskoField, 1, 0);

        Label wynagrodzenieLabel = new Label("Wynagrodzenie");
        grid.add(wynagrodzenieLabel, 0, 1);

        wynagrodzenieField = new TextField();
        wynagrodzenieField.setTextFormatter(new DecimalTextFormatter(0, 2, false));
        grid.add(wynagrodzenieField, 1, 1);

        Button save = new Button("Save");
        save.setOnAction(event -> {
            stanowisko.setStanowisko(stanowiskoField.getText());
            stanowisko.setWynagrodzenie(Double.parseDouble(wynagrodzenieField.getText()));
            if(stanowisko.getId() == null) {
                repository.save(stanowisko);
            }
            else {
                repository.update(stanowisko);
            }
            tabs.openStanowiska();
            tabs.getTabs().remove(this);
        });

        grid.add(save, 4, 4);
        this.setContent(grid);
    }

    public void loadStanowisko(Stanowisko stanowisko) {
        this.stanowisko = stanowisko;
        this.stanowiskoField.setText(stanowisko.getStanowisko());
        this.wynagrodzenieField.setText(stanowisko.getWynagrodzenie().toString());
        this.setText(stanowisko.getStanowisko());
    }

}
