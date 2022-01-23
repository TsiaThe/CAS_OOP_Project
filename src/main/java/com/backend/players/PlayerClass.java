package com.backend.players;

import com.backend.cards.Card;
import java.util.ArrayList;
import java.util.List;

/**
 * Models a player class (human/elf/halbling/dwarf)
 * Each class enables the use of a number of cards
 * and/or a  special property (classPower).
 * @author Theofanis Tsiantas
 * @version  2021.12.04 - version 1
 */
public abstract class PlayerClass {

    // Number of cards a player can carry
    // for future implementations
    // (incl. FightSpell, MonsterBooster)
    private final int maxHandCards;
    // Actual handcards
    private List<Card> handCards;

    /**
     * Constructor.
     * The number of cards depends on the
     * specific race.
     */
    public PlayerClass(int maxHandCards) {
        this.handCards = new ArrayList<>();
        this.maxHandCards = maxHandCards;
    }

    // Getters
    public int getMaxHandCards() {
        return maxHandCards;
    }

    public List<Card> getHandCards() {
        return handCards;
    }

    // Setters
    public void setHandCards(List<Card> handCards) {
        this.handCards = handCards;
    }
}
