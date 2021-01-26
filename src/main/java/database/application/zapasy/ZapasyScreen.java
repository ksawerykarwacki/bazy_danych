package database.application.zapasy;

import database.application.base.BaseScreen;
import database.application.tabs.Tabs;
import io.micronaut.context.ApplicationContext;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

import javax.inject.Singleton;
import java.util.Arrays;
import java.util.Locale;

@Singleton
public class ZapasyScreen extends BaseScreen<Zapas, ZapasRepository, ZapasEditScreen> {
    public ZapasyScreen(Tabs tabs, ZapasRepository repository, ApplicationContext applicationContext) {
        super(tabs, repository, applicationContext, ZapasEditScreen.class);

        for(String columnName : Arrays.asList("Lokal", "Skladnik", "Ilosc")) {
            TableColumn t = new TableColumn<>(columnName);
            t.setCellValueFactory(new PropertyValueFactory<>(columnName.toLowerCase(Locale.ROOT)));
            table.getColumns().add(t);
        }
    }
}
