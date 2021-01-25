package database.application.lokale;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "lokale")
public class Lokal {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "lokal_id")
    @SequenceGenerator(name = "lokal_id", sequenceName = "ID_LOKALE_SEQ", allocationSize = 1)
    @Column(name = "id", updatable = false, nullable = false)
    private Integer id;
    private String adres;
    private String miasto;
    private String kodPocztowy;
    @Column(name = "powierzchnia_w_m2")
    private Float powierzchnia;
    private Integer miejscaDlaKlientow;
}
