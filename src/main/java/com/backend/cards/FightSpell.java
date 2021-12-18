package com.backend.cards;

public class FightSpell extends Spell{

    private int additionalLevel;

    public FightSpell(String name, String description, int additionalLevel) {
        super(name, description);
        this.additionalLevel = additionalLevel;
    }

    public int getAdditionalLevel() {
        return additionalLevel;
    }

}
