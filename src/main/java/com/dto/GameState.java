package com.dto;

import com.backend.GameSetup;
import com.backend.Starter;
import com.backend.cards.*;
import com.backend.players.Player;
import com.repository.MessageRepository;
import com.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
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
 * @version  2021.12.27 - version 1
 */
@Component
public class GameState {

    // "gameStarter" is an object that contains an instance of the GameSetup class
    // (which generates all cards and players with random classes/races).
    // It also contains all lists of cards (door/treasure cards).
    private final Starter gameStarter;
    // "gamePlayers" is a list of all the players in the actual game.
    private List<Player> gamePlayers;
    // "mainPlayer" is the currently active player of a round.
    Player mainPlayer;
    // "secondaryPlayers" is a list of all the players in the actual game,
    // except for the "mainPlayer".
    List<Player> secondaryPlayers = new ArrayList<>(3);
    // List of all door cards (randomly mixed from Starter).
    private static List<Card> gameDoorCards = new ArrayList<>();
    // Current element of game door card.
    private static int currentGameDoorCardElement=0;
    // Current game door card.
    private static Card currentGameDoorCard;
    // List of all treasure cards (randomly mixed from Starter).
    private static List<Card> gameTreasureCards = new ArrayList<>();

    private static int round = 0;
    private static boolean newRound=true;

    @Autowired
    public GameState(Starter gameStarter) {
        this.gameStarter = gameStarter;
        this.gamePlayers = gameStarter.getPlayers();
        mainPlayer = gamePlayers.get(0);
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






        // Testing equipment
        Boots testBoots = null;
        Armour testArmour = null;
        Headgear testHeadgear = null;
        List<Item> testItems =  new ArrayList<>();
        for (Card c:gameTreasureCards){
            if (c instanceof Boots) testBoots = (Boots)c;
            if (c instanceof Armour) testArmour = (Armour)c;
            if (c instanceof Headgear) testHeadgear = (Headgear)c;
            if (c instanceof Item) testItems.add((Item)c);
            if (testBoots!=null && testArmour!=null && testHeadgear!=null && testItems!=null) break;
        }
        for (Player p:gamePlayers){
            p.setHeadgear(testHeadgear);
            p.setArmour(testArmour);
            p.setBoots(testBoots);
            p.setItems(testItems);
        }
        // Testing equipment



    }


    public void nextRound(){
        // Change the main player
        secondaryPlayers.add(mainPlayer);
        mainPlayer = secondaryPlayers.remove(0);
        round++;
        //phase = "Door open";
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

    // Returns a list of randomly shorted door cards.
    public List<Card> getGameDoorCards() {
        return gameDoorCards;
    }

    // Returns a list of randomly shorted treasure cards.
    public List<Card> getGameTreasureCards() {
        return gameTreasureCards;
    }

    public void setNewRound(boolean newRound){
        this.newRound = newRound;
    }

    public boolean getNewRound(){
        return newRound;
    }

    public Card getCurrentDoorCard(){
        return currentGameDoorCard;
    }
}