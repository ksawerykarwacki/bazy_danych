package database.application.skladniki;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "skladniki")
public class Skladnik {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "skladnik_id")
    @SequenceGenerator(name = "skladnik_id", sequenceName = "ID_SKLADNIKI_SEQ", allocationSize = 1)
    @Column(name = "id", updatable = false, nullable = false)
    private Integer id;
    String skladnik;
    String jednostka;
    Boolean wegetarianski;
    Boolean bezglutenowy;

    @Override
    public String toString() {
        return this.skladnik;
    }
}
