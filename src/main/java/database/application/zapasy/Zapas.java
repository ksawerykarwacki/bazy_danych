package database.application.zapasy;

import database.application.lokale.Lokal;
import database.application.skladniki.Skladnik;
import lombok.Data;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "zapasy")
public class Zapas {
    @EmbeddedId
    private ZapasKey zapasKey;
    private Double ilosc;

    public Zapas() {
        zapasKey = new ZapasKey();
    }

    public Lokal getLokal() {
        return zapasKey.getLokal();
    }

    public Skladnik getSkladnik() {
        return zapasKey.getSkladnik();
    }
}
