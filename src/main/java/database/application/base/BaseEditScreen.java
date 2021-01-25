package database.application.base;

import database.application.stanowiska.Stanowisko;
import database.application.tabs.Tabs;
import io.micronaut.context.annotation.Prototype;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.layout.GridPane;

import javax.inject.Inject;

@Prototype
public abstract class BaseEditScreen<T> extends Tab {

    @Inject
    protected Tabs tabs;
    protected  GridPane grid;
    public BaseEditScreen(String text) {
        super("Nowe Stanowisko");
        grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
    }

    public abstract void loadData(T object);

    protected abstract EventHandler<ActionEvent> save();

    protected Button getSaveButton() {
        Button save = new Button("Save");
        save.setOnAction(save());
        return  save;
    }
}