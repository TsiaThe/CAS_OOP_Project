package com.backend.cards;

/**
 * The MonsterBooster is a class simulating
 * very similar to the FightSpell but it can
 * act only at a monster (making it stronger).
 * If the monster is killed, the players ger
 * extra treasures.
 * The class has been programmed but is currently
 * not implemented in the game.
 * @author Theofanis Tsiantas
 * @version  2022.01.23 - version 1
 */
public class MonsterBooster extends Spell{

    // Additional level the monster earns
    private int additionalLevel;
    // Additional treasures for the players
    // if the monster is killed
    private int additionalTreasures;

    // Constructor
    public MonsterBooster(String name, String description, int additionalLevel, int additionalTreasures) {
        super(name, description);
        this.additionalLevel = additionalLevel;
        this.additionalTreasures = additionalTreasures;
    }

    // Getters
    public int getAdditionalLevel() {
        return additionalLevel;
    }

    public int getAdditionalTreasures() {
        return additionalTreasures;
    }
}
