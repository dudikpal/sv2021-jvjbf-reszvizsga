package cinema;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class MovieDTO {

    private String title;

    private LocalDateTime date;

    private int freeSpaces;
}
