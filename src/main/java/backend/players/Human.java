package backend.players;

import backend.cards.Card;

/**
 * Human is one of the player classes.
 * @author Theofanis Tsiantas
 * @version  2021.12.04 - version 1
 */
public class Human extends PlayerClass{

    // Number of cards a human can carry
    public Human() {
        super(5);
    }

    /**
     * Defines the power effect of the human class.
     * @param c The player which the player must
     * discard to use his/her power.
     */
    @Override
    public void classPower(Card c){

    }
}
