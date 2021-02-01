package database.application;

import database.application.base.BaseScreen;
import database.application.stanowiska.StanowiskaScreen;
import database.application.stanowiska.StanowiskoEditScreen;
import database.application.tabs.Tabs;
import io.micronaut.context.ApplicationContext;
import io.micronaut.data.repository.CrudRepository;
import io.micronaut.runtime.EmbeddedApplication;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;

import lombok.extern.slf4j.Slf4j;
import org.junit.FixMethodOrder;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.testcontainers.Testcontainers;
import org.testcontainers.containers.OracleContainer;
import org.testfx.api.FxAssert;
import org.testfx.assertions.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;

import org.testfx.framework.junit5.ApplicationExtension;


import javax.inject.Inject;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@MicronautTest
@ExtendWith(ApplicationExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Slf4j
public class BaseScreenTest<T extends BaseScreen, S, R extends CrudRepository> {

    protected T tableScreen;

    protected TableView<S> tableView;

    protected Class<T> baseClass;

    @Inject
    protected ApplicationContext applicationContext;

    protected R repository;


    protected static String generateString(int length) {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        Random random = new Random();

        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .limit(length)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        return generatedString;
    }

    public static double getRandomNumber(double min, double max, int places) {
        return round(min + new Random().nextDouble() * (max - min), places);
    }

    private static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    protected Tabs getTabs() {
        return applicationContext.getBean(Tabs.class);
    }

    protected <E, I, T extends CrudRepository<E, I>> E getObject(Class<T> repository) {
        T r = applicationContext.getBean(repository);
        List<E> objects = StreamSupport.stream(r.findAll().spliterator(), false).collect(Collectors.toList());
        Random rand = new Random();
        E object = objects.get(rand.nextInt(objects.size()));
        return object;
    }

}
