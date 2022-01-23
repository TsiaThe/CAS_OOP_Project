package com.backend.cards;

import com.backend.players.Player;

/**
 * Monster effects Interface.
 * It contains:
 * - A method which increases the strength of
 *   a monster based on the player race.
 * - A method which applies the negative cons-
 *   equences of a lost fight to the main player.
 * @author Theofanis Tsiantas
 * @version  2021.12.04 - version 1
 */
public interface MonsterEffects {

    void monsterExtraStrength(Monster m,Player p);

    String monsterWinsFight(Monster m, Player p);
}
