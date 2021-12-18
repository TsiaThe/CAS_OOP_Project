package com.backend.cards;

import com.backend.players.Player;

public interface MonsterEffects {

    void monsterExtraStrength(Monster m,Player p);

    String monsterWinsFight(Monster m, Player p);
}
