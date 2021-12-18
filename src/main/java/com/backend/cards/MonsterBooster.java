package com.backend.cards;

public class MonsterBooster extends Spell{

    private int additionalLevel;
    private int additionalTreasures;

    public MonsterBooster(String name, String description, int additionalLevel, int additionalTreasures) {
        super(name, description);
        this.additionalLevel = additionalLevel;
        this.additionalTreasures = additionalTreasures;
    }

    public int getAdditionalLevel() {
        return additionalLevel;
    }

    public int getAdditionalTreasures() {
        return additionalTreasures;
    }
}
