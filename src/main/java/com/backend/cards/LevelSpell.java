package com.backend.cards;

import com.backend.players.Player;

/**
 * Class which models a treasure card which
 * increases the level of a player by one.
 * This is done in the "LevelUp" method.
 * @author Theofanis Tsiantas
 * @version  2021.12.20 - version 1
 */
public class LevelSpell extends Spell{

    // Constructor
    public LevelSpell(String name, String description) {
        super(name, description);
    }

    // Level increase of a player
    public void levelUp(Player p){
        p.setLevel(p.getLevel()+1);
    }
}
