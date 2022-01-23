package com.backend.cards;

/**
 * Models an  item of the  game.
 * - Items are weapons which need a  number of hands to carry them.
 * - Sometimes they can be used only from particular classes.
 * - They can have a special power (programmed but not implemented currently)
 * - They can be small or large.
 * @author Theofanis Tsiantas
 * @version  2021.01.23 - version 2
 */
public class Item extends Equipment{

    // Number of hands needed to carry the item.
    private int handNumber;
    // Class which can use the item.
    private Class<?> applicableClass;
    // Size of the item (!small==large).
    private boolean smallItem;
    // Interface to assign a special power to an item
    private ItemPower itemPower;

    // Constructor
    public Item(String name, String description, int value, int bonus,
                Class<?> applicableClass, boolean smallItem, ItemPower itemPower) {
        super(name, description, value, bonus);
        this.applicableClass = applicableClass;
        this.smallItem = smallItem;
        this.itemPower = itemPower;
    }

    // Return the size of the item
    public boolean isSmallItem() {
        return smallItem;
    }

    // Get the special power of the item
    public ItemPower getItemPower() {
        return itemPower;
    }
}
