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

    @Autowired
    public GameState(Starter gameStarter) {
        this.gameStarter = gameStarter;
        this.gamePlayers = gameStarter.getPlayers();
        mainPlayer = gamePlayers.get(0);
        for (Player p:gamePlayers){
            if (!p.equals(mainPlayer)) secondaryPlayers.add(p);
        }
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




    // -----------------------------------------------------------------
    // -----------------------------------------------------------------
    // OLD methods and staff.....
    /*

    @Bean(name="secondaryPlayers")
    public List<Player> getSecondaryPlayers() {
        return secondaryPlayers;
    }

    @Bean(name="mainPlayer")
    public Player getMainPlayer() {
        return p4; //mainPlayer;
    }

    // Cards in the GUI (hiden and shown)
    private List<Card> activeGameCards = new ArrayList<>();

    static int roundNumber = 0;
    Player mainPlayer;
    List<Player> secondaryPlayers = new ArrayList<>(3);

    DoorCard activeDoorCard;
    List<TreasureCard> treasureCards = new ArrayList<>();

    public GameState(Player mainPlayer, List<Player> secondaryPlayers, DoorCard activeDoorCard,
                     List<TreasureCard> treasureCards){
        roundNumber++;
        this.mainPlayer = mainPlayer;
        this.secondaryPlayers = secondaryPlayers;
        this.secondaryPlayers.remove(mainPlayer);
        this.activeDoorCard = activeDoorCard;
        this.treasureCards = treasureCards;
    }
     */
}