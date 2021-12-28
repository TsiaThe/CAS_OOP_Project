package com.backend;

import com.backend.cards.*;
import com.backend.players.Player;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class Starter {

    private static int numPlayers = 4;
    private static List<Player> players = new ArrayList<>(numPlayers);
    private static List<Card> gameDoorCards = new ArrayList<>();
    private static List<Card> gameTreasureCards = new ArrayList<>();
    private static GameSetup gameSetup = new GameSetup();

    public Starter(){
        // Generate players
        for (int nPlayers = 1; nPlayers < numPlayers+1; nPlayers++) {
            players.add(gameSetup.generatePlayer("Dummy player" + nPlayers));
        }
        // Generate armour cards
        gameTreasureCards = gameSetup.generateArmourCards(gameTreasureCards);
        // Generate boots cards
        gameTreasureCards = gameSetup.generateBootsCards(gameTreasureCards);
        // Generate headgear cards
        gameTreasureCards = gameSetup.generateHeadgearCards(gameTreasureCards);
        // Generate item cards
        gameTreasureCards = gameSetup.generateItemCards(gameTreasureCards);
        // Generate monster booster cards
        gameTreasureCards = gameSetup.generateMonsterBoosterCards(gameTreasureCards);
        // Generate fight spell cards
        gameTreasureCards = gameSetup.generateFightSpellCards(gameTreasureCards);
        // Generate level spell cards
        gameTreasureCards = gameSetup.generateLevelSpellCards(gameTreasureCards);
        // Generate monster cards
        gameDoorCards = gameSetup.generateMonsterCards(gameDoorCards);
        // Generate curse cards
        gameDoorCards = gameSetup.generateCurseCards(gameDoorCards);
    }

    public void testing(){
        // Test players
        for (int nPlayers = 0; nPlayers < numPlayers; nPlayers++) {
            System.out.println("Number of players: " + nPlayers);
            System.out.println("Player "+nPlayers+" name: " + players.get(nPlayers).getName());
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

    public List<Player> getPlayers() {
        return players;
    }

    public List<Card> getGameDoorCards() {
        return gameDoorCards;
    }

    public List<Card> getGameTreasureCards() {
        return gameTreasureCards;
    }

}
