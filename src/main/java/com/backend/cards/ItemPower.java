package com.backend.cards;

import com.backend.players.Player;

/**
 * Item Power Interface.
 * It contains only one method,
 * the effect of the item acting on a
 * player
 * @author Theofanis Tsiantas
 * @version  2021.01.23 - version 1
 */
public interface ItemPower {

    void itemPower(Player p);
}
