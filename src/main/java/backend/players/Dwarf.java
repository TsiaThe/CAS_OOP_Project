package backend.players;

import backend.cards.Card;

/**
 * Dwarf is one of the player classes.
 * @author Theofanis Tsiantas
 * @version  2021.12.04 - version 1
 */
public class Dwarf extends PlayerClass{

    // Number of cards a dwarf can carry
    public Dwarf() {
        super(6);
    }

    /**
     * Defines the power effect of the dwarf class.
     * @param c The player which the player must
     * discard to use his/her power.
     */
    @Override
    public void classPower(Card c){

    }
}
