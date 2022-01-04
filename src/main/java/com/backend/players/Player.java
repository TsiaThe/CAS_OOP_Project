package com.backend.players;

import com.backend.cards.*;

import java.util.ArrayList;
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

    private int level;
    private List<Item> items;
    private Boots boots;
    private Headgear headgear;
    private Armour armour;
    private PlayerClass playerClass;
    private PlayerRace playerRace;
    private int fightStrength;
    private long id = 0;
    private boolean fights = false;

    public Player(PlayerClass  playerClass,PlayerRace  playerRace) {
        this.level = 1;
        this.items = new ArrayList<>();
        this.boots = null;
        this.headgear = null;
        this.armour = null;
        this.playerClass = playerClass;
        this.playerRace = playerRace;
    }

    public int getFightStrength() {
        return fightStrength;
    }

    public void setFightStrength(int fightStrength) {
        this.fightStrength = fightStrength;
    }

    public void calculateFightStrength(){
        // Start from player level
        fightStrength = level;
        // If the player is human, it has always +1.
        if (this.getPlayerClass() instanceof Human) fightStrength += 1;
        // Bonus from boots, if any...
        if (boots!=null) fightStrength+=boots.getBonus();
        // Bonus from armour, if any...
        if (armour!=null) fightStrength+=armour.getBonus();
        // Bonus from headgear, if any...
        if (headgear!=null) fightStrength+=headgear.getBonus();
        // Bonus from items, if any... and if the class is correct...
        for (Item i:items){
            i.getItemPower().itemPower(this);
        }
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

    public void setId(long id){this.id = id;}

    public boolean getFights() {
        return fights;
    }

    public void setFights(boolean fights) {
        this.fights = fights;
    }

    public void sell(){
        // Total sell value
        int totalSellValue = 0;
        // Sell value of boots to be sold.
        if (boots!=null && boots.getSell()){
            totalSellValue+=boots.getValue();
            boots=null;
        }
        // Sell value of armour to be sold.
        if (armour!=null && armour.getSell()){
            totalSellValue+=armour.getValue();
            armour=null;
        }

        // Sell value of headgear to be sold.
        if (headgear!=null && headgear.getSell()){
            totalSellValue+=headgear.getValue();
            headgear=null;
        }

        // Sell value of items to be sold.
        List<Item> removeItems = new ArrayList<>();
        for (Item i:items){
            if (i.getSell()){
                totalSellValue+=i.getValue();
                removeItems.add(i);
            }
        }
        for (Item i:removeItems) items.remove(i);

        level += totalSellValue/1000;
    }

    // Method which applies a treasure card (tc) to the current player.
    // It re-calcualtes its fighting strength afterwards.
    public void applyTreasureCard(TreasureCard tc){
        // if treasure = boots and no current boots -> wear them.
        if (tc instanceof Boots && this.boots!=null) this.boots = (Boots)tc;
        // if treasure = armour and no current armour -> wear it.
        else if (tc instanceof Armour && this.armour!=null) this.armour = (Armour)tc;
        // if treasure = headgear and no current headgear -> wear it.
        else if (tc instanceof Headgear && this.headgear!=null) this.headgear = (Headgear)tc;
        // if treasure = item, it depends on the player class and item list.
        else if (tc instanceof Item) {
            Item treasureItem = (Item) tc;
            // count current large and small items
            int currentSmallItems = 0;
            int currentLargeItems = 0;
            for (Item it:items) {
                if (it.isSmallItem()) currentSmallItems++;
                else currentLargeItems++;
            }
            // maximum allowed small items = 2
            if (treasureItem.isSmallItem() && currentSmallItems<2) {
                items.add(treasureItem);
            }
            // maximum allowed large items = 1 (dwarfs = 2)
            else if (!treasureItem.isSmallItem()) {
                if ((playerClass instanceof Dwarf) && currentLargeItems<2){
                    items.add(treasureItem);
                }
                else if (!(playerClass instanceof Dwarf) && currentLargeItems<1) {
                    items.add(treasureItem);
                }
            }
        }
        else if (tc instanceof LevelSpell) ((LevelSpell) tc).levelUp(this);
        calculateFightStrength();
    }
}
