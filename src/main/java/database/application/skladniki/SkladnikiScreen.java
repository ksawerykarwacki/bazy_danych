package database.application.skladniki;

import database.application.base.BaseScreen;
import database.application.tabs.Tabs;
import io.micronaut.context.ApplicationContext;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

import javax.inject.Singleton;
import java.util.Arrays;
import java.util.Locale;

@Singleton
public class SkladnikiScreen extends BaseScreen<Skladnik, SkladnikiRepository, SkladnikEditScreen> {

    public SkladnikiScreen(Tabs tabs, SkladnikiRepository repository, ApplicationContext applicationContext) {
        super(tabs, repository, applicationContext, SkladnikEditScreen.class);

        for(String columnName : Arrays.asList("Skladnik", "Jednostka", "Wegetarianski", "Bezglutenowy")) {
            TableColumn t = new TableColumn<>(columnName);
            t.setCellValueFactory(new PropertyValueFactory<>(columnName.toLowerCase(Locale.ROOT)));
            table.getColumns().add(t);
        }
    }
}
