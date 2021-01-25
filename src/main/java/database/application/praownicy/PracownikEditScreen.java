package database.application.praownicy;

import database.application.base.BaseEditScreen;
import database.application.lokale.Lokal;
import database.application.lokale.LokaleRepository;
import database.application.lokale.LokaleScreen;
import database.application.stanowiska.StanowiskaRepository;
import database.application.stanowiska.Stanowisko;
import io.micronaut.context.annotation.Prototype;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
@Prototype
public class PracownikEditScreen extends BaseEditScreen<Pracownik, PracownicyRepository> {

    Pracownik pracownik;
    TextField imie, nazwisko;
    DatePicker dataZatrudnienia;
    ComboBox<Lokal> lokal;
    ComboBox<Stanowisko> stanowisko;

    @Inject
    LokaleRepository lokaleRepository;

    @Inject
    StanowiskaRepository stanowiskaRepository;

    @Inject
    PracownicyRepository repository;

    @Inject
    public PracownikEditScreen(LokaleRepository lokaleRepository, StanowiskaRepository stanowiskaRepository, PracownicyRepository repository) {
        super("Nowy pracownik");
        this.lokaleRepository = lokaleRepository;
        this.stanowiskaRepository = stanowiskaRepository;
        this.repository = repository;

        pracownik = new Pracownik();
        imie = new TextField();
        nazwisko = new TextField();
        dataZatrudnienia = new DatePicker();
        lokal = new ComboBox<>();
        lokal.getItems().addAll(StreamSupport.stream(lokaleRepository.findAll().spliterator(), false)
                .collect(Collectors.toList()));

        stanowisko = new ComboBox<>();
        stanowisko.getItems().addAll( StreamSupport.stream(stanowiskaRepository.findAll().spliterator(), false)
                .collect(Collectors.toList()));

        grid.add(new Label("Imie"), 0, 0);
        grid.add(imie, 1, 0);

        grid.add(new Label("Nazwisko"), 0, 1);
        grid.add(nazwisko, 1, 1);

        grid.add(new Label("Lokal"), 0, 2);
        grid.add(lokal, 1, 2);

        grid.add(new Label("Stanowisko"), 0, 3);
        grid.add(stanowisko, 1, 3);

        grid.add(new Label("Data zatrudnienia"), 0, 4);
        grid.add(dataZatrudnienia, 1, 4);

        grid.add(getSaveButton(), 4, 5);
    }

    @Override
    public void loadData(Pracownik pracownik) {
        this.pracownik = pracownik;
        imie.setText(pracownik.getImie());
        nazwisko.setText(pracownik.getNazwisko());
        lokal.getSelectionModel().select(lokal.getItems().filtered(l -> l.getId() == pracownik.getLokal().getId()).get(0));
        stanowisko.getSelectionModel().select(stanowisko.getItems().filtered(s-> s.getId() == pracownik.getStanowisko().getId()).get(0));
        dataZatrudnienia.setValue(pracownik.getDataZatrudnienia());
        this.setText(pracownik.getImie() + " " + pracownik.getNazwisko());
    }

    @Override
    protected EventHandler<ActionEvent> save() {
        return event -> {
          pracownik.setImie(imie.getText());
          pracownik.setNazwisko(nazwisko.getText());
          pracownik.setStanowisko(stanowisko.getSelectionModel().getSelectedItem());
          pracownik.setLokal(lokal.getSelectionModel().getSelectedItem());
          pracownik.setDataZatrudnienia(dataZatrudnienia.getValue());
            if (pracownik.getId() == null) {
                repository.save(pracownik);
            } else {
                repository.update(pracownik);
            }
            tabs.open(PracownicyScreen.class);
            tabs.getTabs().remove(this);
        };
    }
}
