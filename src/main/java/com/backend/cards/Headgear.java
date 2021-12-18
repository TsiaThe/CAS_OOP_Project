package com.backend.cards;

/**
 * Models a headgear.
 * Headgear gives a special power  and
 * some points.
 * @author Theofanis Tsiantas
 * @version  2021.12.04 - version 1
 */
public class Headgear extends Equipment{

    public Headgear(String name, String description, int value, int bonus) {
        super(name, description, value, bonus);
    }
}
