package database.application.dostawcy;

import database.application.skladniki.Skladnik;
import lombok.Data;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Objects;

@Data
@Embeddable
public class DostawcaKey implements Serializable {
    String dostawca;
    @ManyToOne(fetch = FetchType.EAGER)
    Skladnik skladnik;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DostawcaKey d = (DostawcaKey) o;
        return dostawca.equals(d.dostawca) &&
                skladnik == d.skladnik;
    }

    @Override
    public int hashCode() {
        return Objects.hash(dostawca, skladnik);
    }
}
