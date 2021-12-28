package com.backend.players;

import com.backend.cards.*;
import com.backend.cards.Armour;
import com.backend.cards.Boots;
import com.backend.cards.Headgear;
import com.backend.cards.Item;

import java.util.List;

/**
 * Models a player of the game.
 * Each player has some fixed attributes.
 * In addition, he/she has a class (human/elf/halbling/dwarf)
 * and a race (thief/warrior/priest/wizard). The class is
 * defined as an extension and the race as an interface.
 * BOth are assigned randomly at the beginning of the game.
 * @author Theofanis Tsiantas
 * @version  2021.12.04 - version 1
 */
public class Player {

    private String name;
    private int level;
    private List<Item> items;
    private Boots boots;
    private Headgear headgear;
    private Armour armour;
    private PlayerClass playerClass;
    private PlayerRace playerRace;
    private int fightStrength;
    private long id;
    private static long cumulativeId = 0;

    public Player(String name,PlayerClass  playerClass,PlayerRace  playerRace) {
        this.name = name;
        this.level = 1;
        this.fightStrength = 1;
        this.items = null;
        this.boots = null;
        this.headgear = null;
        this.armour = null;
        this.playerClass = playerClass;
        this.playerRace = playerRace;
        this.id = cumulativeId+1;
        cumulativeId++;
    }

    public int getFightStrength() {
        return fightStrength;
    }

    public void setFightStrength(int fightStrength) {
        this.fightStrength = fightStrength;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = Math.max(level, 1);
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public Boots getBoots() {
        return boots;
    }

    public void setBoots(Boots boots) {
        this.boots = boots;
    }

    public Headgear getHeadgear() {
        return headgear;
    }

    public void setHeadgear(Headgear headgear) {
        this.headgear = headgear;
    }

    public Armour getArmour() {
        return armour;
    }

    public void setArmour(Armour armour) {
        this.armour = armour;
    }

    public PlayerClass getPlayerClass() {
        return playerClass;
    }

    public PlayerRace getPlayerRace() {
        return playerRace;
    }

    public long getId(){return id;}
}
