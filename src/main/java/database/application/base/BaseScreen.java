package database.application.base;

import database.application.stanowiska.StanowiskaRepository;
import database.application.stanowiska.StanowiskaScreen;
import database.application.stanowiska.Stanowisko;
import database.application.stanowiska.StanowiskoEditScreen;
import database.application.tabs.Tabs;
import io.micronaut.context.ApplicationContext;
import io.micronaut.data.repository.CrudRepository;
import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;

import java.util.Arrays;

public abstract class BaseScreen<T, R extends CrudRepository<T, Integer>, E extends BaseEditScreen<T, R>> extends GridPane {
    Class<E> eClass;
    protected Tabs tabs;
    protected R repository;
    protected ApplicationContext applicationContext;
    protected final ScrollPane scrollPane;
    protected final TableView<T> table;
    protected final FlowPane flowPane;

    public BaseScreen(Tabs tabs, R repository, ApplicationContext applicationContext, Class<E> editSreen) {
        eClass = editSreen;
        this.tabs = tabs;
        this.repository = repository;
        this.applicationContext = applicationContext;
        flowPane = new FlowPane();
        scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(true);
        this.setPadding(new Insets(15));
        table = new TableView<>();
        flowPane.setPadding(new Insets(15));
        flowPane.setHgap(10);
        addButtons();
    }

    public void refreshData() {
        table.getItems().clear();
        for(T t : repository.findAll()) {
            table.getItems().add(t);
        }
    }

    protected void addButtons() {
        for(String name : Arrays.asList("add", "edit", "delete")) {
            Button b = new Button(name);
            flowPane.getChildren().add(b);

            if(name.equals("add")) {
                b.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        E t = applicationContext.createBean(eClass);
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
                        ObservableList<T> list = table.getSelectionModel().getSelectedItems();
                        for(T t : list) {
                            E e = applicationContext.createBean(eClass);
                            e.loadData(t);
                            tabs.getTabs().add(e);
                            tabs.getSelectionModel().select(e);
                        }
                    }
                });
            }
            else if(name.equals("delete")) {
                b.disableProperty().bind(Bindings.isEmpty(table.getSelectionModel().getSelectedItems()));
                b.setOnAction(event -> {
                    ObservableList<T> list = table.getSelectionModel().getSelectedItems();
                    for(T t : list) {
                        repository.delete(t);
                    }
                    this.refreshData();
                });
            }
        }
    }
}
