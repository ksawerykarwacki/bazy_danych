package database.application.zamowienia;

import database.application.dania.Danie;
import database.application.lokale.Lokal;
import database.application.praownicy.Pracownik;
import lombok.Data;

import javax.persistence.*;
import java.util.HashMap;
import java.util.List;

@Data
@Entity
@Table(name = "zamowienia_na_dowoz")
public class Zamowienie {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "zamowienie_id")
    @SequenceGenerator(name = "zamowienie_id", sequenceName = "ID_ZAMOWIENIA_NA_DOWOZ_SEQ", allocationSize = 1)
    @Column(name = "id", updatable = false, nullable = false)
    private Integer id;
    private String adres;
    private String miasto;
    @ManyToOne(fetch = FetchType.EAGER)
    private Lokal najblizszyLokal;
    @ManyToOne(fetch = FetchType.EAGER)
    private Pracownik dostawca;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name="zamowienie_id")
    private List<ZamowioneJedzenie> zamowioneJedzenie;
}
