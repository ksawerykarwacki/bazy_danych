package database.application.dania;

import database.application.base.BaseScreen;
import database.application.tabs.Tabs;
import io.micronaut.context.ApplicationContext;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

import javax.inject.Singleton;
import java.util.Arrays;
import java.util.Locale;

@Singleton
public class DaniaScreen extends BaseScreen<Danie, DaniaRepository, DanieEditScreen> {
    public DaniaScreen(Tabs tabs, DaniaRepository repository, ApplicationContext applicationContext) {
        super(tabs, repository, applicationContext, DanieEditScreen.class);

        for(String columnName : Arrays.asList("Danie", "Cena", "Skladniki")) {
            TableColumn t = new TableColumn<>(columnName);
            t.setCellValueFactory(new PropertyValueFactory<>(columnName.toLowerCase(Locale.ROOT)));
            table.getColumns().add(t);
        }
    }
}
