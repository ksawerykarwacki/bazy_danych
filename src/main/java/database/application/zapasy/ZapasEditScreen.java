package database.application.zapasy;

import database.application.DecimalTextFormatter;
import database.application.base.BaseEditScreen;
import database.application.dostawcy.DostawcyScreen;
import database.application.lokale.Lokal;
import database.application.lokale.LokaleRepository;
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
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Prototype
public class ZapasEditScreen extends BaseEditScreen<Zapas, ZapasRepository> {
    @Inject
    ZapasRepository repository;

    LokaleRepository lokaleRepository;
    SkladnikiRepository skladnikiRepository;

    ComboBox<Lokal> lokal;
    ComboBox<Skladnik> skladnik;
    TextField ilosc;

    Zapas zapas;

    public ZapasEditScreen(LokaleRepository lokaleRepository, SkladnikiRepository skladnikiRepository) {
        super("Nowy zapas");
        this.lokaleRepository = lokaleRepository;
        this.skladnikiRepository = skladnikiRepository;

        zapas = new Zapas();
        lokal = new ComboBox<>();
        lokal.setId("lokal");
        lokal.getItems().addAll(StreamSupport.stream(lokaleRepository.findAll().spliterator(), false).collect(Collectors.toList()));
        skladnik = new ComboBox<>();
        skladnik.setId("skladnik");
        skladnik.getItems().addAll(StreamSupport.stream(skladnikiRepository.findAll().spliterator(), false).collect(Collectors.toList()));

        ilosc = new TextField();
        ilosc.setId("ilosc");
        ilosc.setTextFormatter(new DecimalTextFormatter(0, 2, false));

        grid.add(new Label("Lokal"), 0, 0);
        grid.add(lokal, 1, 0);

        grid.add(new Label("Skladnik"), 0, 1);
        grid.add(skladnik, 1, 1);

        grid.add(new Label("Ilosc"), 0, 2);
        grid.add(ilosc, 1, 2);

        grid.add(getSaveButton(), 4, 3);
    }

    @Override
    public void loadData(Zapas object) {

    }

    @Override
    protected EventHandler<ActionEvent> save() {
        return event -> {
          zapas.getZapasKey().setLokal(lokal.getValue());
          zapas.getZapasKey().setSkladnik(skladnik.getValue());
          zapas.setIlosc(Double.parseDouble(ilosc.getText()));
            if (repository.findById(zapas.getZapasKey()).isEmpty()) {
                repository.save(zapas);
            } else {
                repository.update(zapas);
            }
            tabs.open(ZapasyScreen.class);
            tabs.getTabs().remove(this);
        };
    }
}
