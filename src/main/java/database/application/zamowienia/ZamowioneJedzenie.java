package database.application.zamowienia;

import database.application.dania.Danie;
import database.application.dostawcy.DostawcaKey;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Data
@Entity
@Table(name = "zamowione_jedzenie")
public class ZamowioneJedzenie {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "zamowione_jedzenie_id")
    @SequenceGenerator(name = "zamowione_jedzenie_id", sequenceName = "ID_ZAMOWIONE_JEDZENIE_SEQ", allocationSize = 1)
    @Column(name = "id", updatable = false, nullable = false)
    private Integer id;
    @ManyToOne(fetch = FetchType.EAGER)
    Danie danie;
    Integer ilosc;
}
