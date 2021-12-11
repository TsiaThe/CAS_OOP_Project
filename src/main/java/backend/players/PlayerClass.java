package backend.players;

import backend.cards.Card;
import java.util.ArrayList;
import java.util.List;

/**
 * Models a player class (human/elf/halbling/dwarf)
 * Each class enables the use of a number of cards
 * and/or a  special property (classPower).
 * @author Theofanis Tsiantas
 * @version  2021.12.04 - version 1
 */
public abstract class PlayerClass {

    // Number of cards a player can carry
    private List<Card> handCards;


    /**
     * Constructor.
     * The number of cards depends on the
     * specific race.
     */
    public PlayerClass(int numberOfCards) {
        this.handCards = new ArrayList<>(numberOfCards);
    }

    /**
     * Defines the power effect of a specific player class.
     * @param c The player which the player must
     * discard to use his/her power.
     */
    public void classPower(Card c){

    }
}
