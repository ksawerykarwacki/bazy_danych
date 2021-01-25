package database.application.praownicy;

import com.google.common.base.CaseFormat;
import database.application.base.BaseScreen;
import database.application.tabs.Tabs;
import io.micronaut.context.ApplicationContext;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

import javax.inject.Singleton;
import java.util.Arrays;
import java.util.Locale;

@Singleton
public class PracownicyScreen extends BaseScreen<Pracownik, PracownicyRepository, PracownikEditScreen> {


    public PracownicyScreen(Tabs tabs, PracownicyRepository repository, ApplicationContext applicationContext) {
        super(tabs, repository, applicationContext, PracownikEditScreen.class);

        for(String columnName : Arrays.asList("Imie", "Nazwisko", "Lokal", "Stanowisko", "Data zatrudnienia")) {
            TableColumn t = new TableColumn<>(columnName);
            t.setCellValueFactory(new PropertyValueFactory<>(CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, columnName.toLowerCase(Locale.ROOT).replace(" ", "_"))));
            table.getColumns().add(t);
        }
    }
}
