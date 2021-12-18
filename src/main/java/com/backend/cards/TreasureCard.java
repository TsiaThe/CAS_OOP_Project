package com.backend.cards;

/**
 * Generic class which models a treasure card.
 * Treasure cards can be Equipment/Spells.
 * @author Theofanis Tsiantas
 * @version  2021.12.04 - version 1
 */
public abstract class TreasureCard extends Card{

    public TreasureCard(String name, String description) {
        super(name, description);
    }
}
