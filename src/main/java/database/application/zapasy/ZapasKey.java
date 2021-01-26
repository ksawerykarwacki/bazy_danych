package database.application.zapasy;

import database.application.dostawcy.DostawcaKey;
import database.application.lokale.Lokal;
import database.application.skladniki.Skladnik;
import lombok.Data;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Objects;

@Data
@Embeddable
public class ZapasKey implements Serializable {
    @ManyToOne(fetch = FetchType.EAGER)
    Lokal lokal;
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
        ZapasKey z = (ZapasKey) o;
        return lokal.equals(z.lokal) &&
                skladnik == z.skladnik;
    }

    @Override
    public int hashCode() {
        return Objects.hash(lokal, skladnik);
    }
}
