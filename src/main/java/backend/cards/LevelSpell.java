package backend.cards;

import backend.players.Player;

public class LevelSpell extends Spell{

    public LevelSpell(String name, String description) {
        super(name, description);
    }

    public void levelUp(Player p){
        p.setLevel(p.getLevel()+1);
    }
}
