package boardgame.database;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;


/**
 * This class holds the details of the players that needs to be saved on the database.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder

public class Game {

        private String firstPlayerName;
        private String secondPlayerName;
        private LocalTime startTime;
        private LocalDate date;
        private int steps;
        private String winner;
        private int winnerScore;
}
