package com.backend.players;

/**
 * Elf is one of the player classes.
 * An Elf can carry a maximum of:
 * - Boots
 * - Armour
 * - Headgear
 * - 2 Small items
 * - 1 Large item
 * When trying to escape, an elf gets +1 (special class power)
 * In addition, when the FightSpell and MonsterBooster
 * cards are incorporated, the dwarf will be able to
 * carry five cards on himself (thus the constructor below).
 * @author Theofanis Tsiantas
 * @version  2021.01.20 - version 1
 */
public class Elf extends PlayerClass{

    // Number of cards an elft can carry
    public Elf()
    {
        super(5);
    }

}
