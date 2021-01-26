package database.application.dania;

import database.application.DecimalTextFormatter;
import database.application.base.BaseEditScreen;
import database.application.skladniki.Skladnik;
import database.application.skladniki.SkladnikiRepository;
import io.micronaut.context.annotation.Prototype;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Prototype
public class DanieEditScreen extends BaseEditScreen<Danie, DaniaRepository> {

    @Inject
    DaniaRepository repository;
    SkladnikiRepository skladnikiRepository;

    Danie danie;

    TextField nazwa, cena;
    ListView<Skladnik> skladniki;
    ComboBox<Skladnik> skladnik;

    public DanieEditScreen(SkladnikiRepository skladnikiRepository) {
        super("Nowe danie");
        this.skladnikiRepository = skladnikiRepository;
        danie = new Danie();
        nazwa = new TextField();
        cena = new TextField();
        cena.setTextFormatter(new DecimalTextFormatter(0, 2, false));
        skladniki = new ListView<>();
        skladniki.setPrefHeight(100);
        skladnik = new ComboBox<>();

        skladnik.getItems().addAll(StreamSupport.stream(skladnikiRepository.findAll().spliterator(), false).collect(Collectors.toList()));

        grid.add(new Label("Nazwa"), 0, 0);
        grid.add(nazwa, 1, 0);

        grid.add(new Label("Cena"), 0, 1);
        grid.add(cena, 1, 1);

        grid.add(new Label("Skladniki"), 0, 2);
        grid.add(skladniki, 1, 2);

        Button remove = new Button("Usun");
        remove.setOnAction(event -> skladniki.getItems().remove(skladniki.getSelectionModel().getSelectedItem()));

        grid.add(remove, 2, 2);

        Button add = new Button("Dodaj");
        add.setOnAction(event -> addToList());

        HBox hBox = new HBox();
        hBox.setSpacing(10);
        hBox.getChildren().addAll(skladnik, add);

        grid.add(hBox, 1, 3);

        grid.add(getSaveButton(), 4, 4);
    }

    private void addToList() {
        Skladnik s = skladnik.getValue();
        List<Skladnik> sList = skladniki.getItems();

        if(!sList.contains(s)) {
            sList.add(s);
        }
    }

    @Override
    public void loadData(Danie object) {
        danie = object;
        nazwa.setText(object.getDanie());
        cena.setText(object.getCena().toString());
        skladniki.getItems().addAll(object.getSkladniki());

        this.setText(object.getDanie());
    }

    @Override
    protected EventHandler<ActionEvent> save() {
        return event -> {
            danie.setDanie(nazwa.getText());
            danie.setCena(Double.parseDouble(cena.getText()));
            danie.skladniki = skladniki.getItems();
            if(danie.getId() == null) {
                repository.save(danie);
            } else {
                repository.update(danie);
            }

            tabs.open(DaniaScreen.class);
            tabs.getTabs().remove(this);
        };
    }
}
