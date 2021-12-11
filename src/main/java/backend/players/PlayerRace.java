package backend.players;

import backend.cards.Card;

/**
 * This interface defines the various races.
 * @author Theofanis Tsiantas
 * @version  2021.12.04 - version 1
 */
public interface PlayerRace {

    /**
     * Defines the power effect of a specific player race.
     * @param c The player which the player must
     * discard to use his/her power.
     */
    void racePower(Card c);
}
