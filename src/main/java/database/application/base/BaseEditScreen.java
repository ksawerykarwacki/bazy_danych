package database.application.base;

import database.application.stanowiska.Stanowisko;
import database.application.tabs.Tabs;
import io.micronaut.context.annotation.Prototype;
import io.micronaut.data.repository.CrudRepository;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.layout.GridPane;

import javax.inject.Inject;

@Prototype
public abstract class BaseEditScreen<T, R extends CrudRepository<T, ?>> extends Tab {

    @Inject
    protected Tabs tabs;
    protected  GridPane grid;
    protected R repository;
    public BaseEditScreen(String text) {
        super(text);
        grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        this.setContent(grid);
    }

    public abstract void loadData(T object);

    protected abstract EventHandler<ActionEvent> save();

    protected Button getSaveButton() {
        Button save = new Button("Zapisz");
        save.setOnAction(save());
        return  save;
    }
}
