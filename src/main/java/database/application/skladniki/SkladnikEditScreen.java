package database.application.skladniki;

import database.application.base.BaseEditScreen;
import database.application.stanowiska.StanowiskaScreen;
import io.micronaut.context.annotation.Prototype;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import javax.inject.Inject;

@Prototype
public class SkladnikEditScreen extends BaseEditScreen<Skladnik, SkladnikiRepository> {

    @Inject
    SkladnikiRepository repository;

    Skladnik skladnik;
    TextField nazwa, jednostka;
    CheckBox wegetarianski, bezglutenowy;

    public SkladnikEditScreen() {
        super("Nowy skladnik");
        skladnik = new Skladnik();
        nazwa = new TextField();
        jednostka = new TextField();
        wegetarianski = new CheckBox();
        bezglutenowy = new CheckBox();

        grid.add(new Label("Nazwa"), 0, 0);
        grid.add(nazwa, 1, 0);

        grid.add(new Label("Jednostka"), 0, 1);
        grid.add(jednostka, 1, 1);

        grid.add(new Label("Wegetarianski"), 0, 2);
        grid.add(wegetarianski, 1, 2);

        grid.add(new Label("Bezglutenowy"), 0, 3);
        grid.add(bezglutenowy, 1, 3);

        grid.add(getSaveButton(), 4, 4);
    }

    @Override
    public void loadData(Skladnik object) {

    }

    @Override
    protected EventHandler<ActionEvent> save() {
        return event -> {
            skladnik.setSkladnik(nazwa.getText());
            skladnik.setJednostka(jednostka.getText());
            skladnik.setWegetarianski(wegetarianski.isSelected());
            skladnik.setBezglutenowy(bezglutenowy.isSelected());
            if (skladnik.getId() == null) {
                repository.save(skladnik);
            } else {
                repository.update(skladnik);
            }
            tabs.open(StanowiskaScreen.class);
            tabs.getTabs().remove(this);
        };
    }
}
