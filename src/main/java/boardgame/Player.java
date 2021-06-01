package boardgame;


import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 *
 *    This is the player class.
 *    its gets the players attributes.
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@XmlAccessorType
@XmlRootElement
@XmlType(propOrder = {"name", "figure", "score", "startTime"})


   public class Player {

    private String name;
    private Figure figure;
    private int score;
    private int startTime;


    /**
     * Used to assert unexpected boolean
     * @param name name
     * @param figure figure
     */
    public Player(final String name, final Figure figure) {
        assert name != null;
        assert figure != null;


        this.name = name;
        this.figure = figure;
        this.score = 0;


    }


}





