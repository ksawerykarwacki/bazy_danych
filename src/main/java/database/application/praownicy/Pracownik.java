package database.application.praownicy;

import database.application.lokale.Lokal;
import database.application.stanowiska.Stanowisko;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Data
@Entity
@Table(name = "pracownicy")
public class Pracownik {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pracownik_id")
    @SequenceGenerator(name = "pracownik_id", sequenceName = "ID_PRACOWNICY_SEQ", allocationSize = 1)
    @Column(name = "id", updatable = false, nullable = false)
    private Integer id;
    String imie;
    String nazwisko;
    @ManyToOne(fetch = FetchType.EAGER)
    Lokal lokal;
    @ManyToOne(fetch = FetchType.EAGER)
    Stanowisko stanowisko;
    LocalDate dataZatrudnienia;
}
