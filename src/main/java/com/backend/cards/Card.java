package com.backend.cards;

/**
 * Generic class which models a card.
 * - Cards can be Doors/Treasures.
 * - Cards are further divided based
 *   on their functionality.
 * @author Theofanis Tsiantas
 * @version  2021.12.04 - version 1
 */
public abstract class Card {

    // Name and description of the card
    // (attributes of all cards)
    private String name;
    private String description;
    private static long id = 0;

    // Constructor
    public Card(String name, String description) {
        id++;
        this.name = name;
        this.description = description;
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public static long getId() {
        return id;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
