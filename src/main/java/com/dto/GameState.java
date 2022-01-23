package com.dto;

import com.backend.Starter;
import com.backend.cards.*;
import com.backend.players.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This class serves as a DTO.
 * It holds the state of the game and
 * controls the flow of the game through the
 * various controllers (see "controller" package).
 * @author Theofanis Tsiantas
 * @version  2022.01.13 - version 4
 */
@Component
public class GameState {

    // "gameStarter" is an object that contains an instance of the GameSetup class
    // (which generates all cards and players with random classes/races).
    // It also contains all lists of cards (door/treasure cards).
    private final Starter gameStarter;
    // The number of players as defined in the Starter instance.
    private final int numberOfPlayers;
    // The maximum player level for the game to end.
    private final int maxLevel;
    // "gamePlayers" is a list of all the players in the actual game.
    private List<Player> gamePlayers;
    // "mainPlayer" is the currently active player of a round.
    Player mainPlayer;
    // "secondaryPlayers" is a list of all the players in the actual game,
    // except for the "mainPlayer".
    List<Player> secondaryPlayers;
    // List of all door cards (randomly mixed from Starter).
    private static List<Card> gameDoorCards = new ArrayList<>();
    // Current element of game door card.
    private static int currentGameDoorCardElement=0;
    // Current game door card.
    private static Card currentGameDoorCard;
    // List of all treasure cards (randomly mixed from Starter).
    private static List<Card> gameTreasureCards = new ArrayList<>();
    // Current element of game treasure card.
    private static int currentGameTreasureCardElement=0;
    // Counter of the current round
    private static int round = 0;
    // Attribute which indicates if the current round has just started (true)
    // or if it started before and we are still in it (necessary at ActionController)
    private static boolean newRound=true;

    @Autowired
    public GameState(Starter gameStarter) {
        this.gameStarter = gameStarter;
        this.gamePlayers = gameStarter.getPlayers();
        mainPlayer = gamePlayers.get(0);
        numberOfPlayers = gameStarter.getNumPlayers();
        maxLevel = gameStarter.getMaxLevel();
        this.secondaryPlayers = new ArrayList<>(numberOfPlayers);
        for (Player p:gamePlayers){
            if (!p.equals(mainPlayer)) secondaryPlayers.add(p);
        }

        // Create a list of randomly shorted door cards
        // (from the Starter instance).
        Collections.shuffle(gameStarter.getGameDoorCards());
        gameDoorCards = gameStarter.getGameDoorCards();

        // Create a list of randomly shorted treasure cards
        // (from the Starter instance).
        Collections.shuffle(gameStarter.getGameTreasureCards());
        gameTreasureCards = gameStarter.getGameTreasureCards();

        // Every player starts with three random equipment objects.
        // They must be unique (e.g. they should not  get 2xBoots).
        for (Player p:gamePlayers){
            for (int i=0;i<3;i++){
                Card treasureCard = gameTreasureCards.remove(0);
                if (!(treasureCard instanceof Equipment)) i--; // Repeat loop if no equipment.
                else{
                    if (treasureCard instanceof Boots) {
                        if (p.getBoots()!=null) i--;
                        else p.setBoots((Boots)treasureCard);
                    }
                    if (treasureCard instanceof Armour){
                        if (p.getArmour()!=null) i--;
                        else p.setArmour((Armour)treasureCard);
                    }
                    if (treasureCard instanceof Headgear) {
                        if (p.getHeadgear()!=null) i--;
                        else p.setHeadgear((Headgear) treasureCard);
                    }
                    if (treasureCard instanceof Item) { // Only small items are given for start.
                        if (((Item) treasureCard).isSmallItem()){
                            if (p.getItems().size()==2) i--;
                            else p.getItems().add((Item)treasureCard);
                        }
                        else i--;
                    }
                }
            }
            p.calculateFightStrength();
        }
    }

    // Getters
    public boolean getNewRound(){
        return newRound;
    }

    public int getRound(){
        return round;
    }

    public Card getCurrentDoorCard(){
        return currentGameDoorCard;
    }

    public int getNumberOfPlayers(){
        return numberOfPlayers;
    }

    public int getMaxLevel(){return maxLevel;}

    // Returns a list of randomly shorted door cards.
    public List<Card> getGameDoorCards() {
        return gameDoorCards;
    }

    // Returns a list of randomly shorted treasure cards.
    public List<Card> getGameTreasureCards() {
        return gameTreasureCards;
    }

    // This method returns all the players which participate in a game
    // (each player is controlled by a user).
    public List<Player> getAllPlayers(){
        return gamePlayers;
    }

    // This method returns  the currently active player of a round
    // (denoted as "Main Player").
    public Player getMainPlayer(){
        return mainPlayer;
    }

    // This method returns all the players which participate in a game
    // except for the main player.
    public List<Player> getSecondaryPlayers(){
        return secondaryPlayers;
    }

    // Setters
    public void setNewRound(boolean newRound){
        this.newRound = newRound;
    }

    // Method to start a new round.
    // It also modifies the mainPlayer
    // and the secondary players
    public void nextRound(){
        // Change the main player
        secondaryPlayers.add(mainPlayer);
        mainPlayer = secondaryPlayers.remove(0);
        round++;
        newRound = true;
    }

    // This method opens a new treasure card. If the treasure cards have finished,
    // it opens them from the beginning again after reshuffling.
    public Card treasureOpen(){
        if (currentGameTreasureCardElement<gameTreasureCards.size()){
            currentGameTreasureCardElement++;
        }
        else{
            Collections.shuffle(gameTreasureCards);
            currentGameTreasureCardElement = 1;
        }
        return gameTreasureCards.get(currentGameTreasureCardElement-1);
    }

    // This method opens a new door. If the door cards have finished,
    // it opens them from the beginning again after reshuffling.
    public Card doorOpen(){
        if (currentGameDoorCardElement<gameDoorCards.size()){
            currentGameDoorCardElement++;
        }
        else{
            Collections.shuffle(gameDoorCards);
            currentGameDoorCardElement = 1;
        }
        currentGameDoorCard = gameDoorCards.get(currentGameDoorCardElement-1);
        return currentGameDoorCard;
    }

    // This method assigns the id of an existing user to the
    // id of a randomly generated player (from the "Starter" class).
    public void assignPlayerId(long id){
        for (Player p:gamePlayers){
            if (p.getId()==0){
                p.setId(id);
                break;
            }
        }
    }
}