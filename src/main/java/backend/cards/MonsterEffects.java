package backend.cards;

import backend.players.Player;

public interface MonsterEffects {

    void monsterSpecialPower(Monster m,Player p);

    void monsterWinsFight(Monster m, Player p);
}
