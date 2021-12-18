package com.dto;

import com.backend.GameSetup;
import com.backend.cards.*;
import com.backend.players.Player;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class GameState {


    Player mainPlayer;
    List<Player> secondaryPlayers = new ArrayList<>(3);

    Player p1 = (new GameSetup().generatePlayer("Player 1"));
    Player p2 = (new GameSetup().generatePlayer("Player 2"));
    Player p3 = (new GameSetup().generatePlayer("Player 3"));
    Player p4 = (new GameSetup().generatePlayer("Player 4"));


    @Bean(name="secondaryPlayers")
    public List<Player> getSecondaryPlayers() {
        return secondaryPlayers;
    }

    @Bean(name="mainPlayer")
    public Player getMainPlayer() {
        return p4; //mainPlayer;
    }



    /*
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