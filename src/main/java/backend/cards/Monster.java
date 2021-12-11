package backend.cards;

import backend.players.Player;

public class Monster extends DoorCard {

    private int levelValue;
    private int treasureValue;
    private MonsterEffects monsterEffects;

    public Monster(String name, String description, int levelValue, int treasureValue, MonsterEffects monsterEffects) {
        super(name, description);
        this.levelValue = levelValue;
        this.treasureValue = treasureValue;
        this.monsterEffects = monsterEffects;
    }

    public int getLevelValue() {
        return levelValue;
    }

    public void setLevelValue(int levelValue) {
        this.levelValue = levelValue;
    }

    public int getTreasureValue() {
        return treasureValue;
    }

    public void setTreasureValue(int treasureValue) {
        this.treasureValue = treasureValue;
    }

    public void useSpecialPower(Player p) {
        monsterEffects.monsterSpecialPower(this, p);
    }

    public void monsterWin(Player p) {
        monsterEffects.monsterWinsFight(this, p);
    }

}
