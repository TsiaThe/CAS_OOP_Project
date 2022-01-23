package com.backend.players;

/**
 * Dwarf is one of the player classes.
 * A Dwarf can carry maximum of:
 * - Boots
 * - Armour
 * - Headgear
 * - 2 Small items
 * - 2 Large items (special class power)
 * In addition, when the FightSpell and MonsterBooster
 * cards are incorporated, the dwarf will be able to
 * carry six cards on himself (thus the constructor below).
 * @author Theofanis Tsiantas
 * @version  2021.01.20 - version 1
 */
public class Dwarf extends PlayerClass{

    // Number of cards a dwarf can carry
    public Dwarf() {
        super(6);
    }
}
