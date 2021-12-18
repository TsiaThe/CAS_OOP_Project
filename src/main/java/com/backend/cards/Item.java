package com.backend.cards;

/**
 * Models an  item of the  game.
 * - Items are weapons which need a  number of hands to carry them.
 * - They can be used only from particular classes.
 * - They can be small or large.
 * - They may have a special  power.
 * @author Theofanis Tsiantas
 * @version  2021.12.04 - version 1
 */
public class Item extends Equipment{

    // Number of hands needed to carry the item.
    private int handNumber;
    // Class which can use the item.
    private Class<?> applicableClass;
    // Size of the item (!small==large).
    private boolean smallItem;

    private ItemPower itemPower;

    public Item(String name, String description, int value, int bonus,
                int handNumber, Class<?> applicableClass, boolean smallItem, ItemPower itemPower) {
        super(name, description, value, bonus);
        this.handNumber = handNumber;
        this.applicableClass = applicableClass;
        this.smallItem = smallItem;
        this.itemPower = itemPower;
    }

    public boolean isSmallItem() {
        return smallItem;
    }

}
