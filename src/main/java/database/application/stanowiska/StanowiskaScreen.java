package database.application.stanowiska;

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
public class StanowiskaScreen extends GridPane {

    Tabs tabs;
    StanowiskaRepository repository;
    ApplicationContext applicationContext;
    private final ScrollPane scrollPane;
    private final TableView<Stanowisko> table;
    private final FlowPane flowPane;

    public StanowiskaScreen(Tabs tabs, StanowiskaRepository repository, ApplicationContext applicationContext) {
        super();
        this.applicationContext = applicationContext;
        this.tabs = tabs;
        this.repository = repository;
        flowPane = new FlowPane();
        scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(true);
        this.setPadding(new Insets(15));
        table = new TableView<>();
        flowPane.setPadding(new Insets(15));
        flowPane.setHgap(10);
        for(String name : Arrays.asList("add", "edit", "delete")) {
            Button b = new Button(name);
            flowPane.getChildren().add(b);

            if(name.equals("add")) {
                b.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        StanowiskoEditScreen t = applicationContext.createBean(StanowiskoEditScreen.class);
                        tabs.getTabs().add(t);
                        tabs.getSelectionModel().select(t);
                    }
                });
            }
            else if(name.equals("edit")) {
                b.disableProperty().bind(Bindings.isEmpty(table.getSelectionModel().getSelectedItems()));
                b.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        ObservableList<Stanowisko> list = table.getSelectionModel().getSelectedItems();
                        for(Stanowisko stanowisko : list) {
                            StanowiskoEditScreen t = applicationContext.createBean(StanowiskoEditScreen.class);
                            t.loadStanowisko(stanowisko);
                            tabs.getTabs().add(t);
                            tabs.getSelectionModel().select(t);
                        }
                    }
                });
            }
            else if(name.equals("delete")) {
                b.disableProperty().bind(Bindings.isEmpty(table.getSelectionModel().getSelectedItems()));
                b.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

                    StanowiskaScreen screen;
                    @Override
                    public void handle(MouseEvent event) {
                        ObservableList<Stanowisko> list = table.getSelectionModel().getSelectedItems();
                        for(Stanowisko stanowisko : list) {
                            repository.delete(stanowisko);
                        }
                        screen.refreshData();
                    }

                    private EventHandler<MouseEvent> init(StanowiskaScreen screen) {
                        this.screen = screen;
                        return this;
                    }
                }.init(this));
            }
        }

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

    public void refreshData() {
        table.getItems().clear();
        for(Stanowisko s : repository.findAll()) {
            table.getItems().add(s);
        }
    }
}
