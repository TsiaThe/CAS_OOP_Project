package com.backend.players;

/**
 * Human is one of the player classes.
 * A Human can carry a maximum of:
 * - Boots
 * - Armour
 * - Headgear
 * - 2 Small items
 * - 1 Large item
 * A human has always +1 at his fighting strength (special class power)
 * In addition, when the FightSpell and MonsterBooster
 * cards are incorporated, the dwarf will be able to
 * carry five cards on himself (thus the constructor below).
 * @author Theofanis Tsiantas
 * @version  2021.01.20 - version 1
 */
public class Human extends PlayerClass{

    // Number of cards a human can carry
    public Human() {
        super(5);
    }
}
