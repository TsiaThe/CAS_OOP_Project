package backend.cards;

import backend.players.Player;

/**
 * Curse card (door card).
 * Curses have only one method.
 * @author Theofanis Tsiantas
 * @version  2021.12.04 - version 1
 */
public class Curse extends DoorCard{

    private CurseEffect curseEffect;

    public Curse(String name, String description, CurseEffect curseEffect) {
        super(name, description);
        this.curseEffect = curseEffect;
    }

    public void curse(Player p) {
        curseEffect.curseEffect(p);
    }

}
