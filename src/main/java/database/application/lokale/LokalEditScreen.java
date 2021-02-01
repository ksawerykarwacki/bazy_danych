package database.application.lokale;

import database.application.DecimalTextFormatter;
import database.application.base.BaseEditScreen;
import database.application.stanowiska.StanowiskaScreen;
import io.micronaut.context.annotation.Prototype;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.util.converter.IntegerStringConverter;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;

@Slf4j
@Prototype
public class LokalEditScreen extends BaseEditScreen<Lokal, LokaleRepository> {

    @Inject
    LokaleRepository repository;
    Lokal lokal;
    TextField adres, miasto, kodPocztowy, powierzchnia, miejsca;

    public LokalEditScreen() {
        super("Nowy lokal");
        adres = new TextField();
        adres.setId("adres");
        miasto = new TextField();
        miasto.setId("miasto");
        kodPocztowy = new TextField();
        kodPocztowy.setId("kodPocztowy");
        powierzchnia = new TextField();
        powierzchnia.setId("powierzchnia");
        miejsca = new TextField();
        miejsca.setId("miejsca");

        lokal = new Lokal();

        grid.add(new Label("Adres"), 0, 0);
        grid.add(adres, 1, 0);

        grid.add(new Label("Miasto"), 0, 1);
        grid.add(miasto, 1, 1);

        grid.add(new Label("Kod pocztowy"), 0, 2);
        grid.add(kodPocztowy, 1, 2);

        grid.add(new Label("Powierzchnia [m2]"), 0, 3);
        powierzchnia.setTextFormatter(new DecimalTextFormatter(0, 2, false));
        grid.add(powierzchnia, 1, 3);

        grid.add(new Label("Liczba miejsc"), 0, 4);
        miejsca.setTextFormatter(new TextFormatter<>(new IntegerStringConverter()));
        grid.add(miejsca, 1, 4);

        grid.add(getSaveButton(), 4, 5);
    }

    @Override
    public void loadData(Lokal lokal) {
        this.lokal = lokal;
        this.adres.setText(lokal.getAdres());
        this.miasto.setText(lokal.getMiasto());
        this.kodPocztowy.setText(lokal.getKodPocztowy());
        this.powierzchnia.setText(lokal.getPowierzchnia().toString());
        this.miejsca.setText(lokal.getMiejscaDlaKlientow().toString());
        this.setText(lokal.getMiasto() + " " + lokal.getAdres());
    }

    @Override
    protected EventHandler<ActionEvent> save() {
        return event -> {
            lokal.setAdres(adres.getText());
            lokal.setMiasto(miasto.getText());
            lokal.setKodPocztowy(kodPocztowy.getText());
            lokal.setPowierzchnia(Float.parseFloat(powierzchnia.getText()));
            lokal.setMiejscaDlaKlientow(Integer.parseInt(miejsca.getText()));
            if (lokal.getId() == null) {
                repository.save(lokal);
            } else {
                repository.update(lokal);
            }
            tabs.open(LokaleScreen.class);
            tabs.getTabs().remove(this);
        };
    }
}
