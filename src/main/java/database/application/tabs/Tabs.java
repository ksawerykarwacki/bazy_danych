package database.application.tabs;

import database.application.Application;
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

    public void openStanowiska() {
        Tab tab = this.getTabs().stream().filter(t->t.getContent() instanceof StanowiskaScreen).findFirst().orElse(null);
        if(tab != null) {
            log.info("Found existing");
            this.getSelectionModel().select(tab);
            ((StanowiskaScreen) tab.getContent()).refreshData();
        }
        else {
            StanowiskaScreen screen = applicationContext.getBean(StanowiskaScreen.class);
            screen.refreshData();
            tab = new Tab("Stanowiska", screen);
            this.getTabs().add(tab);
            this.getSelectionModel().select(tab);
        }
    }
}
