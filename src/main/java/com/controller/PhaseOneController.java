package com.controller;

import com.backend.cards.*;
import com.backend.players.Player;
import com.backend.cards.Card;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/test")   //(/round{id}/phase1")
public class PhaseOneController {

    // Cards in the GUI (hiden and shown)
    private List<Card> activeGameCards = new ArrayList<>();

    @Autowired
    @Qualifier("mainPlayer")
    Player mainPlayer;

    @Autowired
    @Qualifier("secondaryPlayers")
    List<Player> secondaryPlayers = new ArrayList<>(3);

    /*
    List<Card> monsterLvLincreaseTickedCards = new ArrayList<>();
    List<Card> playerLvLincreaseTickedCards = new ArrayList<>();


    DoorCard activeDoorCard;
    List<Player> fightingPlayers = new ArrayList<>();
    List<Player> nonFightingPlayers = new ArrayList<>();
    List<TreasureCard> treasureCards = new ArrayList<>();
    Random randomGenerator = new Random();
    int fightTreasures;
     */

    @GetMapping("/")
    public String getPlayers() {
        System.out.println("HALLOOOOO");
        String message = "Main player: "+mainPlayer.getName();
    //    for (Player p:secondaryPlayers){
    //        message += ", "+p.getName();
    //    }
        return testMethod();
    }

    public String testMethod(){
     return "This is a test method";
    }








    /*
    public PhaseOneController(Player mainPlayer, List<Player> secondaryPlayers, DoorCard activeDoorCard){
        this.mainPlayer = mainPlayer;
        this.secondaryPlayers = secondaryPlayers;
        this.activeDoorCard = activeDoorCard;
    }

    public void doorOpen(){
        if (activeDoorCard instanceof Monster) {
            fightTreasures = ((Monster) activeDoorCard).getTreasureValue();
            FightController fightActions = new FightController();
            fightActions.activateFightActions(mainPlayer, secondaryPlayers);
        }
        // else curse();
    }


    // --------------------------------------------------------------------------------
    // MOVE TO CONTROLLER!!!



    // --------------------------------------------------------------------------------


    public void giveHelp(Player p){
        // Deactivate a button two buttons in non-fighting players
        if (fightTreasures>nonFightingPlayers.size()){
            fightingPlayers.add(p);
        }
    }

    public String fight(List<Card> _monsterLvLincreaseTickedCards, List<Card> _playerLvLincreaseTickedCards){

        // Result message of the fight
        String fightResult;

        // Assign the checked cards that may influence the fight
        // to the active state of the game
        monsterLvLincreaseTickedCards = _monsterLvLincreaseTickedCards;
        playerLvLincreaseTickedCards = _playerLvLincreaseTickedCards;

        // Calculate the total fighting strength of the players
        // (playerFightStrength) based on their level and their equipment
        int playerFightStrength = mainPlayer.getFightStrength();
        for (Player p:fightingPlayers){
            playerFightStrength += p.getFightStrength();
        }

        // Calculate the total fighting strength of the monster
        // (monsterFightStrength) based on its level and racist effects
        ((Monster) activeDoorCard).monsterExtraStrength(mainPlayer);
        for (Player p:fightingPlayers){
            ((Monster) activeDoorCard).monsterExtraStrength(p);
        }
        int monsterFightStrength = ((Monster) activeDoorCard).getLevelValue();

        // Increase the total fighting strength of the monster
        // in case of monster boosters of fighting spells to the monster
        for (Card c:monsterLvLincreaseTickedCards){
            if (c instanceof MonsterBooster){
                monsterFightStrength += ((MonsterBooster) c).getAdditionalLevel();
                fightTreasures += ((MonsterBooster) c).getAdditionalTreasures();
            }
            else if (c instanceof FightSpell)
            {
                monsterFightStrength += ((FightSpell) c).getAdditionalLevel();
            }
        }

        // Increase the total fighting strength of the player
        // in case of fighting spells to the player
        for (Card c:playerLvLincreaseTickedCards){
            playerFightStrength += ((FightSpell) c).getAdditionalLevel();
        }

        // Fight action
        if (monsterFightStrength<playerFightStrength){
            fightResult = "Du hast den Kampf verloren. Villeicht kannst du weglaufen...";
            flee(mainPlayer, activeDoorCard);
        }
        else
        {
            fightResult = "Du hast den Kampf gewonnen. Die Schätze werden nun verteilt.";
            treasureDistribution();
        }
        return fightResult;
    }

    public String flee(Player mainPlayer, DoorCard activeDoorCard){
        // Result message of the flee action
        // Has a default message if user clicks when not fighting

        // --------------------------------------------------------
        // Deactivate Weglaufen button
        // --------------------------------------------------------

        String fleeResult = "Du bist in keinem Kampf. Also du kannst nicht weglaufen...";
        if (activeDoorCard instanceof Monster){
            int dice = randomGenerator.nextInt(6)+1;
            if (mainPlayer.getPlayerClass() instanceof Elf) dice++;
            if (dice>3){
                fleeResult = "Du hast "+dice+"gewurfellt. Du bist erfolgreich gelaufen!";
            }
            else{
                fleeResult = "Du hast "+dice+"gewurfellt!";
                consequence(mainPlayer, activeDoorCard);
            }
        }
        return fleeResult;
    }

    public String consequence(Player mainPlayer, DoorCard activeDoorCard){
        Monster fightMonster = (Monster) activeDoorCard;
        return fightMonster.monsterWinsFight(mainPlayer);
    }

    public void treasureDistribution(){

        for (Player p:fightingPlayers){
            p.getPlayerClass().getHandCards().add(treasureCards.remove(0));
        }

        int remainingCards = treasureCards.size();
        for (int i=0;i<remainingCards;i++){
            mainPlayer.getPlayerClass().getHandCards().add(treasureCards.remove(0));
        }
    }

     */

}