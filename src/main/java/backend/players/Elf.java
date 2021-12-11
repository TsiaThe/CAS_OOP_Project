package backend.players;

import backend.cards.Card;
import java.util.ArrayList;
import java.util.List;

/**
 * Elf is one of the player classes.
 * @author Theofanis Tsiantas
 * @version  2021.12.04 - version 1
 */
public class Elf extends PlayerClass{

    // Number of cards an elft can carry
    public Elf()
    {
        super(5);
    }

    /**
     * Defines the power effect of the elf class.
     * @param c The player which the player must
     * discard to use his/her power.
     */
    @Override
    public void classPower(Card c){

    }
}
