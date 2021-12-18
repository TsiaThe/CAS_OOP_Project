package com.controller;

import com.backend.players.Player;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/fight")
public class FightController {

    public void activateFightActions(Player mainPlayer, List<Player> secondaryPlayers){
        // GUI buttons activated
    }

    public void askHelp(Player p){
        // Deactivate a button in main player
        // Activate two buttons in non-fighting players
        // Maybe in jason???
    }

    public void giveHelp(Player p){
        // Deactivate a button two buttons in non-fighting players

    }



}
