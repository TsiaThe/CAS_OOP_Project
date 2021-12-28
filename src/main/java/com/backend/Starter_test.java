package com.backend;

import com.backend.cards.*;
import com.backend.players.Player;
import java.util.ArrayList;
import java.util.List;

public class Starter_test {

    private static int numPlayers = 4;
    private static List<Player> players;
    private static List<Card> gameDoorCards = new ArrayList<>();
    private static List<Card> gameTreasureCards = new ArrayList<>();


    public static void main(String[] args){

        List<Player> players = new ArrayList<>(numPlayers);
        GameSetup gameSetup = new GameSetup();

        // Generate players
        for (int nPlayers = 1; nPlayers < numPlayers+1; nPlayers++) {
            players.add(gameSetup.generatePlayer("Dummy player" + nPlayers));
        }
        // Test players
        for (int nPlayers = 0; nPlayers < numPlayers; nPlayers++) {
            System.out.println("Number of players: " + nPlayers);
            System.out.println("Player "+nPlayers+" class: " + players.get(nPlayers).getPlayerClass());
            System.out.println("Player "+nPlayers+" race: " + players.get(nPlayers).getPlayerRace());
        }

        //region Armour
        // Generate armour cards
        gameTreasureCards = gameSetup.generateArmourCards(gameTreasureCards);
        // Test armour cards
        for (Card c:gameTreasureCards){
            if (c.getClass()== Armour.class){
                System.out.println(c.getName());
            }
        }
        //endregion

        //region Boots
        // Generate boots cards
        gameTreasureCards = gameSetup.generateBootsCards(gameTreasureCards);
        // Test boots cards
        for (Card c:gameTreasureCards){
            if (c.getClass()== Boots.class){
                System.out.println(c.getName());
            }
        }
        //endregion

        //region Headgear
        // Generate headgear cards
        gameTreasureCards = gameSetup.generateHeadgearCards(gameTreasureCards);
        // Test headgear cards
        for (Card c:gameTreasureCards){
            if (c.getClass()== Headgear.class){
                System.out.println(c.getName());
            }
        }
        //endregion

        //region Item
        // Generate item cards
        gameTreasureCards = gameSetup.generateItemCards(gameTreasureCards);
        // Test item cards
        for (Card c:gameTreasureCards){
            if (c.getClass()== Item.class){
                System.out.println(c.getName());
            }
        }
        //endregion

        //region MonsterBoosters
        // Generate monster booster cards
        gameTreasureCards = gameSetup.generateMonsterBoosterCards(gameTreasureCards);
        // Test monster booster cards
        for (Card c:gameTreasureCards){
            if (c.getClass()== MonsterBooster.class){
                System.out.println(c.getName());
            }
        }
        //endregion

        //region FightSpells
        // Generate fight spell cards
        gameTreasureCards = gameSetup.generateFightSpellCards(gameTreasureCards);
        // Test fight spell cards
        for (Card c:gameTreasureCards){
            if (c.getClass()== FightSpell.class){
                System.out.println(c.getName());
            }
        }
        //endregion

        //region LevelSpells
        // Generate level spell cards
        gameTreasureCards = gameSetup.generateLevelSpellCards(gameTreasureCards);
        // Test level spell cards
        for (Card c:gameTreasureCards){
            if (c.getClass()== LevelSpell.class){
                System.out.println(c.getName());
            }
        }
        //endregion

        //region Monsters
        // Generate monster cards
        gameDoorCards = gameSetup.generateMonsterCards(gameDoorCards);
        // Test monster cards
        for (Card c:gameDoorCards){
            if (c.getClass()== Monster.class){
                System.out.println(c.getName());
               // ((Monster) c).useSpecialPower(null);
               // ((Monster) c).monsterWin(null);
            }
        }
        //endregion

        //region Curses
        // Generate curse cards
        gameDoorCards = gameSetup.generateCurseCards(gameDoorCards);
        // Test curse cards
        for (Card c:gameDoorCards){
            if (c.getClass()== Curse.class){
                System.out.println(c.getName());
            //    ((Curse) c).curse(null);
            }
        }
        //endregion
    }
}
