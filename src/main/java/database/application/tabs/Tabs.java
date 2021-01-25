package database.application.tabs;

import database.application.Application;
import database.application.base.BaseScreen;
import database.application.stanowiska.StanowiskaScreen;
import io.micronaut.context.ApplicationContext;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import javax.inject.Singleton;

@Slf4j
@Singleton
public class Tabs extends TabPane {

    @Inject
    ApplicationContext applicationContext;

     public <S extends BaseScreen> void open(Class<S> sClass) {
        Tab tab = this.getTabs().stream().filter(t->t.getContent().getClass() == sClass).findFirst().orElse(null);
        if(tab != null) {
            log.info("Found existing");
            this.getSelectionModel().select(tab);
            ((S) tab.getContent()).refreshData();
        }
        else {
            S screen = applicationContext.getBean(sClass);
            screen.refreshData();
            tab = new Tab(sClass.getSimpleName().replace("Screen", ""), screen);
            this.getTabs().add(tab);
            this.getSelectionModel().select(tab);
        }
    }
}
