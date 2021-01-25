package database.application.lokale;

import com.google.common.base.CaseFormat;
import database.application.base.BaseScreen;
import database.application.stanowiska.StanowiskaRepository;
import database.application.stanowiska.StanowiskoEditScreen;
import database.application.tabs.Tabs;
import io.micronaut.context.ApplicationContext;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Singleton;
import java.util.Arrays;
import java.util.Locale;

@Slf4j
@Singleton
public class LokaleScreen extends BaseScreen<Lokal, LokaleRepository, LokalEditScreen> {

    public LokaleScreen(Tabs tabs, LokaleRepository repository, ApplicationContext applicationContext) {
        super(tabs, repository, applicationContext, LokalEditScreen.class);


        this.addRow(0, flowPane);
        table.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);

        for(String columnName : Arrays.asList("Adres", "Miasto", "Kod pocztowy", "Powierzchnia", "Miejsca dla klientow")) {
            TableColumn t = new TableColumn<>(columnName);
            t.setCellValueFactory(new PropertyValueFactory<>(CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, columnName.toLowerCase(Locale.ROOT).replace(" ", "_"))));
            table.getColumns().add(t);
        }



        scrollPane.setContent(table);

        this.addRow(1, table);

    }
}
