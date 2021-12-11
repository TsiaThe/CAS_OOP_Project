package backend.cards;

import backend.players.Player;

public class FightSpell extends Spell{

    private int additionalLevel;

    public FightSpell(String name, String description, int additionalLevel) {
        super(name, description);
        this.additionalLevel = additionalLevel;
    }

    public void spellEffect(Monster m, Player p){
        if (m==null)
        {
         p.setFightStrength(p.getFightStrength()+additionalLevel);
        }
        else
        {
            m.setLevelValue(m.getLevelValue()+additionalLevel);
        }
    }

}
