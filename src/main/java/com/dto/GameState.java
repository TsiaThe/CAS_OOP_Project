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

@Component
public class GameState {

    private final Starter gameStarter;
    private List<Player> gamePlayers;
    Player mainPlayer;
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

    public void assignPlayerId(long id){
        for (Player p:gamePlayers){
            if (p.getId()==0){
                p.setId(id);
                break;
            }
        }
    }

    public List<Player> getAllPlayers(){
        return gamePlayers;
    }

    public Player getMainPlayer(){
        return mainPlayer;
    }



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