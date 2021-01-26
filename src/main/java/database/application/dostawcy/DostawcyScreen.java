package database.application.dostawcy;

import database.application.base.BaseScreen;
import database.application.tabs.Tabs;
import io.micronaut.context.ApplicationContext;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

import javax.inject.Singleton;
import java.util.Arrays;
import java.util.Locale;

@Singleton
public class DostawcyScreen extends BaseScreen<Dostawca, DostawcyRepository, DostawcaEditScreen> {
    public DostawcyScreen(Tabs tabs, DostawcyRepository repository, ApplicationContext applicationContext) {
        super(tabs, repository, applicationContext, DostawcaEditScreen.class);

        for(String columnName : Arrays.asList("Dostawca", "Skladnik", "Cena")) {
            TableColumn t = new TableColumn<>(columnName);
            t.setCellValueFactory(new PropertyValueFactory<>(columnName.toLowerCase(Locale.ROOT)));
            table.getColumns().add(t);
        }
    }
}
