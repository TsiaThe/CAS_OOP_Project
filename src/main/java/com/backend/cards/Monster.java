package com.backend.cards;

import com.backend.players.Player;

/**
 * Moodels a moster of the game. A monster has:
 * - A level value
 * - A number of tresures which you win if it is killed
 * - Maybe a racist behavior
 * @author Theofanis Tsiantas
 * @version  2021.12.25 - version 1
 */
public class Monster extends DoorCard {

    // Monster level
    private int levelValue;
    // Number of treasures the monster offers
    private int treasureValue;
    // Interface which controls monster
    // specific actions (see corresponding interface)
    private MonsterEffects monsterEffects;

    // Constructor
    public Monster(String name, String description, int levelValue, int treasureValue, MonsterEffects monsterEffects) {
        super(name, description);
        this.levelValue = levelValue;
        this.treasureValue = treasureValue;
        this.monsterEffects = monsterEffects;
    }

    // Getters
    public int getLevelValue() {
        return levelValue;
    }

    public int getTreasureValue() {
        return treasureValue;
    }

    // Setters
    public void setLevelValue(int levelValue) {
        this.levelValue = levelValue;
    }

    // Extra strength of the monster if it is racist
    public void monsterExtraStrength(Player p) {
        monsterEffects.monsterExtraStrength(this, p);
    }

    // Negative consquencies on the player of the monster wins
    public String monsterWinsFight(Player p) {
        return monsterEffects.monsterWinsFight(this, p);
    }

}
