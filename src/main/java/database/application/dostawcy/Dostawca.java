package database.application.dostawcy;

import database.application.skladniki.Skladnik;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "dostawcy_skladnikow")
public class Dostawca {
    @EmbeddedId
    private DostawcaKey dostawcaKey;
    private Double cena;

    public Dostawca() {
        dostawcaKey = new DostawcaKey();
    }

    public String getDostawca() {
        return this.dostawcaKey.getDostawca();
    }

    public Skladnik getSkladnik() {
        return this.dostawcaKey.getSkladnik();
    }
}
