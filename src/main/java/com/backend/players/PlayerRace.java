package com.backend.players;

import com.backend.cards.Card;

/**
 * This interface defines the various races.
 * Each race can also have a special property
 * which can be used by sacrificing a game card.
 * This has been programmed but is currently not
 * implemented in the game.
 * @author Theofanis Tsiantas
 * @version  2021.01.02 - version 2
 */
public interface PlayerRace {

    /**
     * Defines the power effect of a specific player race.
     * @param c The player which the player must
     * discard to use his/her power.
     */
    void racePower(Card c);
}
