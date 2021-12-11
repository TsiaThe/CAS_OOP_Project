package backend.players;

import backend.cards.Card;

/**
 * Halbling is one of the player classes.
 * @author Theofanis Tsiantas
 * @version  2021.12.04 - version 1
 */
public class Halbling extends PlayerClass{

    // Number of cards a halbling can carry
    public Halbling() {
        super(5);
    }

    /**
     * Defines the power effect of the halbling class.
     * @param c The player which the player must
     * discard to use his/her power.
     */
    @Override
    public void classPower(Card c){

    }
}
