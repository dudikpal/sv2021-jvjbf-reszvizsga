package cinema;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class Movie {

    private long id;
    private String title;
    private LocalDateTime date;
    private int maxSpaces;
    private int freeSpaces;


    public Movie(long id, String title, LocalDateTime date, int maxSpaces) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.maxSpaces = maxSpaces;
        freeSpaces = maxSpaces;
    }


    public void reserve(int spaces) {
        if (freeSpaces < spaces) {
            throw new IllegalStateException("Not enough free spaces left");
        }
        freeSpaces -= spaces;
    }
}
