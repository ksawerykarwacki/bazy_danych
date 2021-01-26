package database.application.zamowienia;

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
public class ZamowieniaScreen extends BaseScreen<Zamowienie, ZamowieniaRepository, ZamowienieEditScreen> {


    public ZamowieniaScreen(Tabs tabs, ZamowieniaRepository repository, ApplicationContext applicationContext) {
        super(tabs, repository, applicationContext, ZamowienieEditScreen.class);

        for(String columnName : Arrays.asList("Id", "Najblizszy Lokal", "Miasto", "Adres")) {
            TableColumn t = new TableColumn<>(columnName);
            t.setCellValueFactory(new PropertyValueFactory<>(CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, columnName.toLowerCase(Locale.ROOT).replace(" ", "_"))));
            table.getColumns().add(t);
        }
    }
}
