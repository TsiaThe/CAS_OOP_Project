package com.backend.players;

/**
 * Halbling is one of the player classes.
 * A Halbling can carry a maximum of:
 * - Boots
 * - Armour
 * - Headgear
 * - 2 Small items
 * - 1 Large item
 * A halbling can sell all items at double price (special class power)
 * In addition, when the FightSpell and MonsterBooster
 * cards are incorporated, the dwarf will be able to
 * carry five cards on himself (thus the constructor below).
 * @author Theofanis Tsiantas
 * @version  2021.01.20 - version 1
 */
public class Halbling extends PlayerClass{

    // Number of cards a halbling can carry
    public Halbling() {
        super(5);
    }

}
