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
 * @version  2021.12.04 - version 1
 */
public abstract class Equipment extends TreasureCard{

    private int value;
    private int bonus;
    private boolean sell = false;

    public Equipment(String name, String description, int value, int bonus) {
        super(name, description);
        this.value = value;
        this.bonus = bonus;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getBonus() {
        return bonus;
    }

    public void setBonus(int bonus) {
        this.bonus = bonus;
    }

    public void setSell(boolean sell) {
        this.sell = sell;
    }

    public boolean getSell(){
        return this.sell;
    }
}
