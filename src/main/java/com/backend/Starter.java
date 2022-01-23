package com.backend;

import com.backend.cards.*;
import com.backend.players.Player;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

/**
 * This class creates an instance of the class "GameSetup"
 * which contains all cards and can randomly generate players
 * (of random class and race).  It contains lists with all
 * door and treasure cards as well as with the players.
 * @author Theofanis Tsiantas
 * @version  2022.01.13 - version 3
 */
@Component
public class Starter {

    // Number of game players.
    private static final int numPlayers = 3;
    // Maximum level till game ends.
    private static final int maxLevel = 3;
    // List of randomly generated players.
    private static List<Player> players = new ArrayList<>(numPlayers);
    // List of all door cards.
    private static List<Card> gameDoorCards = new ArrayList<>();
    // List of all treasure cards.
    private static List<Card> gameTreasureCards = new ArrayList<>();
    // gameSetup object at which all card types and players are generated.
    private static GameSetup gameSetup = new GameSetup();

    // Constructor
    public Starter(){
        // Generate players
        for (int nPlayers = 1; nPlayers < numPlayers+1; nPlayers++) {
            players.add(gameSetup.generatePlayer());
        }
        // Generate armour cards
        gameTreasureCards = gameSetup.generateArmourCards(gameTreasureCards);
        // Generate boots cards
        gameTreasureCards = gameSetup.generateBootsCards(gameTreasureCards);
        // Generate headgear cards
        gameTreasureCards = gameSetup.generateHeadgearCards(gameTreasureCards);
        // Generate item cards
        gameTreasureCards = gameSetup.generateItemCards(gameTreasureCards);
        // Generate monster booster cards (not supported in current version)
        // gameTreasureCards = gameSetup.generateMonsterBoosterCards(gameTreasureCards);
        // Generate fight spell cards (not supported in current version)
        // gameTreasureCards = gameSetup.generateFightSpellCards(gameTreasureCards);
        // Generate level spell cards
        gameTreasureCards = gameSetup.generateLevelSpellCards(gameTreasureCards);
        // Generate monster cards
        gameDoorCards = gameSetup.generateMonsterCards(gameDoorCards);
        // Generate curse cards
        gameDoorCards = gameSetup.generateCurseCards(gameDoorCards);
    }

    // Method for testing all generated cards and players.
    public void testing(){
        // Test players
        for (int nPlayers = 0; nPlayers < numPlayers; nPlayers++) {
            System.out.println("Number of players: " + nPlayers);
            System.out.println("Player "+nPlayers+" class: " + players.get(nPlayers).getPlayerClass());
            System.out.println("Player "+nPlayers+" race: " + players.get(nPlayers).getPlayerRace());
        }
        // Test armour cards
        for (Card c:gameTreasureCards){
            if (c.getClass()== Armour.class){
                System.out.println(c.getName());
            }
        }
        // Test boots cards
        for (Card c:gameTreasureCards){
            if (c.getClass()== Boots.class){
                System.out.println(c.getName());
            }
        }
        // Test headgear cards
        for (Card c:gameTreasureCards){
            if (c.getClass()== Headgear.class){
                System.out.println(c.getName());
            }
        }
        // Test item cards
        for (Card c:gameTreasureCards){
            if (c.getClass()== Item.class){
                System.out.println(c.getName());
            }
        }
        // Test monster booster cards
        for (Card c:gameTreasureCards){
            if (c.getClass()== MonsterBooster.class){
                System.out.println(c.getName());
            }
        }
        // Test fight spell cards
        for (Card c:gameTreasureCards){
            if (c.getClass()== FightSpell.class){
                System.out.println(c.getName());
            }
        }
        // Test level spell cards
        for (Card c:gameTreasureCards){
            if (c.getClass()== LevelSpell.class){
                System.out.println(c.getName());
            }
        }
        // Test monster cards
        for (Card c:gameDoorCards){
            if (c.getClass()== Monster.class){
                System.out.println(c.getName());
                // ((Monster) c).useSpecialPower(null);
                // ((Monster) c).monsterWin(null);
            }
        }
        // Test curse cards
        for (Card c:gameDoorCards){
            if (c.getClass()== Curse.class){
                System.out.println(c.getName());
                //    ((Curse) c).curse(null);
            }
        }
    }

    // Method which returns the four randomly generated players.
    public List<Player> getPlayers() {
        return players;
    }

    // Method which returns all door cards.
    public List<Card> getGameDoorCards() {
        return gameDoorCards;
    }

    // Method which returns all treasure cards.
    public List<Card> getGameTreasureCards() {
        return gameTreasureCards;
    }

    // Getter for the total number of players of the game.
    public static int getNumPlayers() {
        return numPlayers;
    }

    // Getter for the maximum player level of the game.
    public static int getMaxLevel(){return maxLevel;}
}
