package database.application.zamowienia;

import database.application.EditCell;
import database.application.base.BaseEditScreen;
import database.application.dania.DaniaRepository;
import database.application.dania.Danie;
import database.application.lokale.Lokal;
import database.application.lokale.LokaleRepository;
import database.application.pracownicy.PracownicyRepository;
import database.application.pracownicy.Pracownik;
import io.micronaut.context.annotation.Prototype;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.util.converter.IntegerStringConverter;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
@Prototype
public class ZamowienieEditScreen extends BaseEditScreen<Zamowienie, ZamowieniaRepository> {

    @Inject
    ZamowieniaRepository repository;

    LokaleRepository lokaleRepository;
    PracownicyRepository pracownicyRepository;
    DaniaRepository daniaRepository;

    Zamowienie zamowienie;

    TextField adres, miasto;
    ComboBox<Lokal> lokal;
    ComboBox<Pracownik> dostawca;
    ComboBox<Danie> danie;
    TableView<ZamowioneJedzenie> zamowioneJedzenie;

    public ZamowienieEditScreen(LokaleRepository lokaleRepository, PracownicyRepository pracownicyRepository, DaniaRepository daniaRepository) {
        super("Nowe zamowienie");
        this.lokaleRepository = lokaleRepository;
        this.pracownicyRepository = pracownicyRepository;
        this.daniaRepository = daniaRepository;

        zamowienie = new Zamowienie();
        adres = new TextField();
        adres.setId("adres");
        miasto = new TextField();
        miasto.setId("miasto");
        lokal = new ComboBox<>();
        lokal.setId("lokal");
        lokal.getItems().addAll(StreamSupport.stream(lokaleRepository.findAll().spliterator(), false).collect(Collectors.toList()));
        lokal.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> filterDostawcy(newValue));
        dostawca = new ComboBox<>();
        dostawca.setId("dostawca");
        dostawca.getItems().addAll(StreamSupport.stream(pracownicyRepository.findAll().spliterator(), false).collect(Collectors.toList()));

        zamowioneJedzenie = new TableView<>();
        zamowioneJedzenie.setId("zamowioneJedzenie");
        zamowioneJedzenie.setPrefHeight(100);
        zamowioneJedzenie.setEditable(true);
        for(String columnName : Arrays.asList("Danie", "Ilosc")) {
            TableColumn t = new TableColumn<>(columnName);
            t.setCellValueFactory(new PropertyValueFactory<>(columnName.toLowerCase(Locale.ROOT)));
            if(columnName == "Ilosc") {
                t.setCellFactory(param -> new EditCell<>(new IntegerStringConverter()));
            }
            zamowioneJedzenie.getColumns().add(t);

        }

        ((TableColumn<ZamowioneJedzenie, Integer>) zamowioneJedzenie.getColumns().get(1)).setOnEditCommit(event -> event.getRowValue().setIlosc(event.getNewValue()));

        danie = new ComboBox<>();
        danie.setId("danie");
        danie.getItems().addAll(StreamSupport.stream(daniaRepository.findAll().spliterator(), false).collect(Collectors.toList()));

        Button addDanie = new Button("Dodaj");
        addDanie.setOnAction(event -> {
            ZamowioneJedzenie z = new ZamowioneJedzenie();
            z.setDanie(danie.getValue());
            z.ilosc = 1;
            zamowioneJedzenie.getItems().add(z);
        });

        grid.add(new Label("Adres"), 0, 0);
        grid.add(adres, 1, 0);

        grid.add(new Label("Miasto"), 0, 1);
        grid.add(miasto, 1, 1);

        grid.add(new Label("Najblizszy lokal"), 0, 2);
        grid.add(lokal, 1, 2);

        grid.add(new Label("Dostawca"), 0, 3);
        grid.add(dostawca, 1, 3);

        grid.add(new Label("Zamowienie"), 0, 4);
        grid.add(zamowioneJedzenie, 1, 4);

        HBox hBox = new HBox();
        hBox.setSpacing(10);
        hBox.getChildren().addAll(danie, addDanie);

        grid.add(hBox, 1, 5);

        grid.add(getSaveButton(),4, 6);
    }

    @Override
    public void loadData(Zamowienie object) {
        this.zamowienie = object;
        adres.setText(object.getAdres());
        miasto.setText(object.getMiasto());
        lokal.getSelectionModel().select(lokal.getItems().filtered(l->l.getId()==object.getNajblizszyLokal().getId()).get(0));
        dostawca.getSelectionModel().select(dostawca.getItems().filtered(p->p.getId()==object.getDostawca().getId()).get(0));
        zamowioneJedzenie.getItems().setAll(object.getZamowioneJedzenie());
    }

    @Override
    protected EventHandler<ActionEvent> save() {
        return event -> {
          zamowienie.setAdres(adres.getText());
          zamowienie.setMiasto(miasto.getText());
          zamowienie.setNajblizszyLokal(lokal.getValue());
          zamowienie.setDostawca(dostawca.getValue());
          zamowienie.setZamowioneJedzenie(zamowioneJedzenie.getItems());
            if(zamowienie.getId() == null) {
                repository.save(zamowienie);
            } else {
                repository.update(zamowienie);
            }

            tabs.open(ZamowieniaScreen.class);
            tabs.getTabs().remove(this);
        };
    }

    private void filterDostawcy(Lokal newValue) {
        List<Pracownik> pracownicyList = StreamSupport.stream(pracownicyRepository.findAll().spliterator(), false).collect(Collectors.toList());
        if(lokal.getValue() != null) {
            pracownicyList = pracownicyList.stream().filter(pracownik -> pracownik.getLokal().getId()==lokal.getValue().getId()).collect(Collectors.toList());
            this.dostawca.getItems().setAll(pracownicyList);
        }

        if(!pracownicyList.contains(this.dostawca.getValue())) {
            this.dostawca.getSelectionModel().clearSelection();
        }
    }
}
