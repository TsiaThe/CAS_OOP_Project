package com.backend.cards;

/**
 * Equipment card (treasure card).
 * Equipments can be:
 * - Boots.
 * - Armour.
 * - Items
 * - Headgear
 * They may have a special power.
 * @author Theofanis Tsiantas
 * @version  2021.12.24 - version 2
 */
public abstract class Equipment extends TreasureCard{

    // Value of the equipment (gold coins)
    private int value;
    // Bonus strength given by the equipment
    private int bonus;
    // Attribute to control the intention of the user
    // to sell, resp. keep, the item.
    private boolean sell = false;

    // Constructors
    public Equipment(String name, String description, int value, int bonus) {
        super(name, description);
        this.value = value;
        this.bonus = bonus;
    }

    // Getters
    public int getValue() {
        return value;
    }

    public boolean getSell(){
        return this.sell;
    }

    public int getBonus() {
        return bonus;
    }

    // Setters
    public void setValue(int value) {
        this.value = value;
    }

    public void setSell(boolean sell) {
        this.sell = sell;
    }

}
