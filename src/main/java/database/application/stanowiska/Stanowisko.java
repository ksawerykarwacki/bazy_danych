package database.application.stanowiska;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "stanowiska")
public class Stanowisko {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "stanowisko_id")
    @SequenceGenerator(name = "stanowisko_id", sequenceName = "ID_STANOWISKA_SEQ", allocationSize = 1)
    @Column(name = "id", updatable = false, nullable = false)
    private Integer id;
    String stanowisko;
    Double wynagrodzenie;
}
