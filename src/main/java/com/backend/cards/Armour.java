package com.backend.cards;

/**
 * Models an armour.
 * Armour gives some fighting points.
 * @author Theofanis Tsiantas
 * @version  2021.12.14 - version 1
 */
public class Armour extends Equipment{

    public Armour(String name, String description, int value, int bonus) {
        super(name, description, value, bonus);
    }
}
