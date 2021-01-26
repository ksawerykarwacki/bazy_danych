package database.application.dania;

import database.application.skladniki.Skladnik;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "dania")
public class Danie {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "danie_id")
    @SequenceGenerator(name = "danie_id", sequenceName = "ID_DANIA_SEQ", allocationSize = 1)
    @Column(name = "id", updatable = false, nullable = false)
    private Integer id;
    private String danie;
    private Double cena;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "sklad_dan")
    List<Skladnik> skladniki;
}
