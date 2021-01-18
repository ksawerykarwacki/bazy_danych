package database.application;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
public class Clicks {
    @GeneratedValue
    @Id
    UUID id;
    LocalDateTime clickDate;
    String commentText;
    Integer clicks = 0;

    public void increment(String comment) {
        clickDate = LocalDateTime.now();
        clicks++;
        this.commentText = comment;
    }
}
