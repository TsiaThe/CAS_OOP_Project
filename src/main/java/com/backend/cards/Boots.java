package com.backend.cards;

/**
 * Models a pair of boots.
 * Boots give some fighting points.
 * @author Theofanis Tsiantas
 * @version  2021.12.14 - version 1
 */
public class Boots extends Equipment{

    public Boots(String name, String description, int value, int bonus) {
        super(name, description, value, bonus);
    }
}
