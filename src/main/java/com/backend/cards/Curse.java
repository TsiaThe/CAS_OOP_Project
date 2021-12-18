package com.backend.cards;

import com.backend.players.Player;

/**
 * Curse card (door card).
 * Curses have only one method.
 * @author Theofanis Tsiantas
 * @version  2021.12.04 - version 1
 */
public class Curse extends DoorCard{

    // Interface which implements the curse
    private CurseEffect curseEffect;

    // Constructor
    public Curse(String name, String description, CurseEffect curseEffect) {
        super(name, description);
        this.curseEffect = curseEffect;
    }

    // method
    public void curse(Player p) {
        curseEffect.curseEffect(p);
    }

}
