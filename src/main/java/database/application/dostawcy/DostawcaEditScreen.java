package database.application.dostawcy;

import database.application.DecimalTextFormatter;
import database.application.base.BaseEditScreen;
import database.application.skladniki.Skladnik;
import database.application.skladniki.SkladnikiRepository;
import io.micronaut.context.annotation.Prototype;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import javax.inject.Inject;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Prototype
public class DostawcaEditScreen extends BaseEditScreen<Dostawca, DostawcyRepository> {

    @Inject
    DostawcyRepository repository;

    SkladnikiRepository skladnikiRepository;

    TextField nazwa, cena;
    ComboBox<Skladnik> skladnik;

    Dostawca dostawca;

    public DostawcaEditScreen(SkladnikiRepository skladnikiRepository) {
        super("Nowy dostawca");
        this.skladnikiRepository = skladnikiRepository;

        dostawca = new Dostawca();
        nazwa = new TextField();
        nazwa.setId("nazwa");
        cena = new TextField();
        cena.setId("cena");
        cena.setTextFormatter(new DecimalTextFormatter(0, 2, false));
        skladnik = new ComboBox<>();
        skladnik.setId("skladnik");

        skladnik.getItems().addAll(StreamSupport.stream(skladnikiRepository.findAll().spliterator(), false).collect(Collectors.toList()));

        grid.add(new Label("Dostawca"), 0, 0);
        grid.add(nazwa, 1, 0);

        grid.add(new Label("Skladnik"), 0, 1);
        grid.add(skladnik, 1, 1);

        grid.add(new Label("Cena"), 0, 2);
        grid.add(cena, 1, 2);

        grid.add(getSaveButton(), 4, 3);
    }

    @Override
    public void loadData(Dostawca object) {
        this.dostawca = object;
        nazwa.setText(object.getDostawca());
        skladnik.getSelectionModel().select(skladnik.getItems().filtered(s -> s.getId() == object.getSkladnik().getId()).get(0));
        cena.setText(object.getCena().toString());

        this.setText(object.getDostawca());
    }

    @Override
    protected EventHandler<ActionEvent> save() {
        return event -> {
          this.dostawca.getDostawcaKey().setDostawca(nazwa.getText());
          this.dostawca.getDostawcaKey().setSkladnik(skladnik.getValue());
          this.dostawca.setCena(Double.parseDouble(cena.getText()));
            if (repository.findById(dostawca.getDostawcaKey()).isEmpty()) {
                repository.save(dostawca);
            } else {
                repository.update(dostawca);
            }
            tabs.open(DostawcyScreen.class);
            tabs.getTabs().remove(this);
        };
    }
}
