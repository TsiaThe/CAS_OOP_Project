package com.backend.cards;

/**
 * Generic class which models a door card.
 * Door cards can be Monsters/Curses.
 * @author Theofanis Tsiantas
 * @version  2021.12.04 - version 1
 */
public abstract class DoorCard extends Card{

    public DoorCard(String name, String description) {
        super(name, description);
    }
}
