package database.application.stanowiska;

import database.application.base.BaseScreen;
import database.application.tabs.Tabs;
import io.micronaut.context.ApplicationContext;
import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Singleton;
import java.util.Arrays;
import java.util.Locale;

@Slf4j
@Singleton
public class StanowiskaScreen extends BaseScreen<Stanowisko, StanowiskaRepository, StanowiskoEditScreen> {



    public StanowiskaScreen(Tabs tabs, StanowiskaRepository repository, ApplicationContext applicationContext) {
        super(tabs, repository, applicationContext, StanowiskoEditScreen.class);


        this.addRow(0, flowPane);
        table.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);

        for(String columnName : Arrays.asList("Stanowisko", "Wynagrodzenie")) {
            TableColumn t = new TableColumn<>(columnName);
            t.setCellValueFactory(new PropertyValueFactory<>(columnName.toLowerCase(Locale.ROOT)));
            table.getColumns().add(t);
        }



        scrollPane.setContent(table);

        this.addRow(1, scrollPane);

    }
}
