package database.application;

import database.application.dania.DaniaTest;
import database.application.lokale.LokaleTest;
import database.application.pracownicy.PracownicyTest;
import database.application.skladniki.SkladnikiTest;
import database.application.stanowiska.StanowiskaTest;
import database.application.dostawcy.DostawcyTest;
import database.application.zamowienia.ZamowieniaTest;
import database.application.zapasy.ZapasyTest;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.runner.RunWith;

@RunWith(JUnitPlatform.class)
@SelectClasses({StanowiskaTest.class, LokaleTest.class, PracownicyTest.class, SkladnikiTest.class,
        DostawcyTest.class, ZapasyTest.class, DaniaTest.class, ZamowieniaTest.class})
public interface TestSuit {
}
