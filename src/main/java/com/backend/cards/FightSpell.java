package com.backend.cards;

/**
 * The FightSpell is a class simulating the cards
 * which can give extra strength to a monster or
 * a player during a fight.
 * The class has been programmed, but is currently
 * not implemented in the game.
 * @author Theofanis Tsiantas
 * @version  2022.01.14 - version 1
 */
public class FightSpell extends Spell{

    // If the monster is killed, it offers
    // some additional trasures
    private int additionalLevel;

    // Constructor
    public FightSpell(String name, String description, int additionalLevel) {
        super(name, description);
        this.additionalLevel = additionalLevel;
    }

    // Return the number of extra levels for the main player
    public int getAdditionalLevel() {
        return additionalLevel;
    }

}
