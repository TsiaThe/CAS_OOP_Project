package com.controller;

import com.backend.cards.*;
import com.backend.players.*;
import com.dto.GameState;
import com.repository.GameHistoryRepository;
import com.repository.MessageRepository;
import com.repository.UserRepository;
import com.web.GamePhase;
import com.web.Message;
import com.web.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

/**
 * This controller is responsible for:
 * 1.
 * @author Theofanis Tsiantas
 * @version  2021.12.27 - version 1
 */
@Controller
@RequestMapping // (path="/users")
public class ActionController {

    // Repo with all signed up users.
    private final UserRepository userRepository;
    // Repo with all communicated messages.
    private final MessageRepository messageRepository;
    // Repo with all game history.
    private final GameHistoryRepository gameHistoryRepository;
    // DTO which holds the actual game state.
    private final GameState gameState;

    @Autowired
    public ActionController(UserRepository userRepository,
                            MessageRepository messageRepository,
                            GameHistoryRepository gameHistoryRepository,
                            GameState gameState) {
        this.userRepository = userRepository;
        this.messageRepository = messageRepository;
        this.gameHistoryRepository = gameHistoryRepository;
        this.gameState= gameState;
    }

    // ................
    @GetMapping("/action/{currentUserId}")
    public String ActionPage(@PathVariable("currentUserId") long cuID,
                              Map<String, Object> model) {

        // Testing section
        String gen="Runde: "+ gameState.getRound()+", Hauptspieler: " + getMainPlayerName();
        model.put("GeneralInfo", gen);



        model.put("gameHistory", gameHistoryRepository.findAll());
        // Testing section

        // User-specific information for URL
        model.put("currentUser", userRepository.findById(cuID));
        model.put("users", userRepository.findAll());

        // Show monster/curse icon and activate corresponding
        // buttons by door opening.
        if (gameState.getNewRound()){
            doorModel((DoorCard)gameState.doorOpen(),cuID, model);
            gameState.setNewRound(false);
        }
        else{
            doorModel((DoorCard)gameState.getCurrentDoorCard(), cuID, model);
        }

        // Add player information bar
        playerModel(cuID, model);
        // Add boots information, if any...
        bootsModel(cuID, model);
        // Add armour information, if any...
        armourModel(cuID, model);
        // Add headgear information, if any...
        headgearModel(cuID, model);
        // Add items information, if any...
        itemsModel(cuID, model);

        // Communication handling (show messaging history)
        if (messageRepository.count()==0){
           messageRepository.save(new Message("Chat history"));
        }
        model.put("messages", messageRepository.findAll());
        if (userRepository.count()==4){
            return "ActionPage";
        }
        return "WaitPage";
    }

    // Post method which posts a new message to the server.
    @PostMapping("/action/{currentUserId}")
    public String ChatMessage(@Valid Message message,
                              @PathVariable("currentUserId") long cuID,
                              Map<String, Object> model) {
        // Add new message to repository and redirect to ActionPage.
            String sender = "";
        if (userRepository.findById(cuID).isPresent()){
            Optional<User> pageUser = userRepository.findById(cuID);
            model.put("currentUser", pageUser);
            sender = pageUser.get().getName();
        }
        if(message.getMessageText() != null){
            message.setMessageText(sender+": "+message.getMessageText());
            messageRepository.save(message);
        }
        return "redirect:/action/"+ cuID;
    }

    // Post method which starts a new round.
    @PostMapping("/action/{currentUserId}/newRound")
    public String fight(@PathVariable("currentUserId") long cuID){
        gameState.nextRound();
        return "redirect:/action/"+ cuID;
    }

    // .
    @PostMapping("/action/{currentUserId}/fight")
    public String fight(@PathVariable("currentUserId") long cuID,
                            Map<String, Object> model){

        // Message that fight has begun
        String descrpition = "R: "+gameState.getRound()+", HS: "+ getMainPlayerName() + ", Kampf angefangen!";
        gameHistoryRepository.save(new GamePhase(descrpition));
        // Calculation of fighting players (w/t main player)
        gameHistoryRepository.save(new GamePhase("Kaempfende Spieler: "));
        descrpition=getMainPlayerName();
        for (Player p:gameState.getSecondaryPlayers()){
            if(p.getFights()){
                String secPlayerName = userRepository.findById(p.getId()).get().getName();
                descrpition += ", "+secPlayerName;
            }
        }
        gameHistoryRepository.save(new GamePhase(descrpition));

        int limit = 5;
        long iterations = gameHistoryRepository.count()-limit;
        if (iterations>0){
            Iterable<GamePhase> it = gameHistoryRepository.findAll();
            for (int i=1;i<=iterations;i++)
                gameHistoryRepository.deleteById(it.iterator().next().getId());
        }






        return "redirect:/action/"+ cuID;
    }



    // Post method which sells all equipment that a user has selected to sell.
    @PostMapping("/action/{currentUserId}/sell")
    public String sellItems(@PathVariable("currentUserId") long cuID){

        Player currentPlayer = findPlayerbyID(cuID, gameState.getAllPlayers());
        currentPlayer.sell();
        return "redirect:/action/"+ cuID;
    }

    // Post method which sets the fighting state of a player appropriately.
    @PostMapping("/action/{currentUserId}/fightState")
    public String fightHelp(@PathVariable("currentUserId") long cuID,
                            Map<String, Object> model){
        fightState(cuID);
        return "redirect:/action/"+ cuID;
    }

    // Post method which sets the sell attribute of Headgear.
    @PostMapping("/action/{currentUserId}/sellHeadgear")
    public String sellHeadgear(@PathVariable("currentUserId") long cuID){

        Player currentPlayer = findPlayerbyID(cuID, gameState.getAllPlayers());
        sellState(currentPlayer.getHeadgear());
        return "redirect:/action/"+ cuID;
    }

    // Post method which sets the sell attribute of an Armour.
    @PostMapping("/action/{currentUserId}/sellArmour")
    public String sellArmour(@PathVariable("currentUserId") long cuID){

        Player currentPlayer = findPlayerbyID(cuID, gameState.getAllPlayers());
        sellState(currentPlayer.getArmour());
        return "redirect:/action/"+ cuID;
    }

    // Post method which sets the sell attribute of Boots.
    @PostMapping("/action/{currentUserId}/sellBoots")
    public String sellBoots(@PathVariable("currentUserId") long cuID){

        Player currentPlayer = findPlayerbyID(cuID, gameState.getAllPlayers());
        sellState(currentPlayer.getBoots());
        return "redirect:/action/"+ cuID;
    }

    // Post method which sets the sell attribute of an item.
    @PostMapping("/action/{currentUserId}/{itemName}")
    public String sellItem(@PathVariable("currentUserId") long cuID,
                            @PathVariable("itemName") String itemName){

        Player currentPlayer = findPlayerbyID(cuID, gameState.getAllPlayers());
        for (Item i:currentPlayer.getItems()){
            if (i.getName().equals(itemName)){
                sellState(i);
                break;
            }
        }
        return "redirect:/action/"+ cuID;
    }


    // ----------------------------------------------------------------------------------------
    //      -------------------------- SUPPORTING METHODS -------------------------------
    // ----------------------------------------------------------------------------------------

    // Method which populates the current model view with the open door information
    // (and the corresponding available buttons).
    private Map<String, Object> doorModel(DoorCard dc, long cuID, Map<String, Object> currentModel){
        // Check if the door is monster or curse
        // Monster door
        if (dc instanceof Monster) {
            currentModel.put("door","monster");
            // If the door just opened, add to the game history.
            if (gameState.getNewRound()){
                String descrpition = "R: "+gameState.getRound()+", HS: "+ getMainPlayerName() + ", Monster hinten der Tuer!";
                gameHistoryRepository.save(new GamePhase(descrpition));
            }
            // For the main player the gamePhase is returned. This shows 4-buttons!
            if (cuID==gameState.getMainPlayer().getId()) {
                currentModel.put("gamePhase","fight");
            }
            // Else only the fight participation factor is shown.
            else{
                currentModel.put("fightButton",true);
            }
        }
        // Curse door
        else{
            currentModel.put("door","curse");
            // If the door just opened, add to the game history.
            if (gameState.getNewRound()){
                String descrpition = "R: "+gameState.getRound()+", HS: "+ getMainPlayerName() + ", Fluch hinten der Tuer!";
                gameHistoryRepository.save(new GamePhase(descrpition));
            }
            // For the main player the gamePhase is returned. This shows 4-buttons!
            if (cuID==gameState.getMainPlayer().getId()) currentModel.put("gamePhase","nofight");
        }

        currentModel.put("doorName","Name: "+dc.getName());
        currentModel.put("doorDescription","Beschreibung: "+dc.getDescription());

        return currentModel;
    }

    // Method which sets the "sell" attribute of an equipment to true/false based on
    // whether the player wants to sell it or not.
    private void sellState(Equipment equipment){
        equipment.setSell(!equipment.getSell());
    }

    // Method which populates the current model view with the boots information.
    private Map<String, Object> itemsModel(long cuID, Map<String, Object> currentModel){
        Player currentPlayer = findPlayerbyID(cuID, gameState.getAllPlayers());
        if (currentPlayer.getItems().size()>0){
            currentModel.put("items", currentPlayer.getItems());
        }
        else{
            currentModel.put("Noitem", "Kein Gegenstand");
        }
        return currentModel;
    }

    // Method which populates the current model view with the boots information.
    private Map<String, Object> bootsModel(long cuID, Map<String, Object> currentModel){
        Player currentPlayer = findPlayerbyID(cuID, gameState.getAllPlayers());
        if (currentPlayer.getBoots()!=null){
            currentModel.put("bootsName", currentPlayer.getBoots().getName());
            currentModel.put("bootsBonus", currentPlayer.getBoots().getBonus());
            currentModel.put("bootsValue", currentPlayer.getBoots().getValue()+" Goldstuecke");
            currentModel.put("bootsSell", currentPlayer.getBoots().getSell());
        }
        else{
            currentModel.put("bootsName", "Keine Schuhe");
        }
        return currentModel;
    }

    // Method which populates the current model view with the armour information.
    private Map<String, Object> armourModel(long cuID, Map<String, Object> currentModel){
        Player currentPlayer = findPlayerbyID(cuID, gameState.getAllPlayers());
        if (currentPlayer.getArmour()!=null){
            currentModel.put("armourName", currentPlayer.getArmour().getName());
            currentModel.put("armourBonus", currentPlayer.getArmour().getBonus());
            currentModel.put("armourValue", currentPlayer.getArmour().getValue()+" Goldstuecke");
            currentModel.put("armourSell", currentPlayer.getArmour().getSell());
        }
        else{
            currentModel.put("armourName", "Keine Ruestung");
        }
        return currentModel;
    }

    // Method which populates the current model view with the headgear information.
    private Map<String, Object> headgearModel(long cuID, Map<String, Object> currentModel){
        Player currentPlayer = findPlayerbyID(cuID, gameState.getAllPlayers());
        if (currentPlayer.getHeadgear()!=null){
            currentModel.put("headgearName", currentPlayer.getHeadgear().getName());
            currentModel.put("headgearBonus", currentPlayer.getHeadgear().getBonus());
            currentModel.put("headgearValue", currentPlayer.getHeadgear().getValue()+" Goldstuecke");
            currentModel.put("headgearSell", currentPlayer.getHeadgear().getSell());
        }
        else{
            currentModel.put("headgearName", "Keine Kopfbedeckung");
        }
        return currentModel;
    }

    // Method which sets the "fights" attribute of a player to true/false based on
    // whether the player wants to participate in the fight or not.
    private void fightState(long cuID){
        Player currentPlayer = findPlayerbyID(cuID, gameState.getAllPlayers());
        // Change the button name and the player attribute accordingly
        if (currentPlayer.getFights()==true){
            currentPlayer.setFights(false);
        }
        else{
            currentPlayer.setFights(true);
        }
    }

    // Method which populates the current model view with player information
    // (class, race, level, fighting strength, fight participation, etc...).
    private Map<String, Object> playerModel(long cuID, Map<String, Object> currentModel){

        Player currentPlayer = findPlayerbyID(cuID, gameState.getAllPlayers());
        currentModel.put("playerClass", getPlayerClass(currentPlayer.getPlayerClass()));
        currentModel.put("playerRace", getPlayerRace(currentPlayer.getPlayerRace()));
        currentModel.put("playerLevel", currentPlayer.getLevel()+" /");
        currentModel.put("playerStrength", String.valueOf(currentPlayer.getFightStrength()));

        // Changes the name of the fight button to be the opposite of the
        // player fighting state. The button will be shown only for the non-main players
        // in case of a monster door (as per doorModel controller!).
        if (currentPlayer.getFights()==true){
            currentModel.put("fightingState","Nicht mitkaempfen!");
        }
        else{
            currentModel.put("fightingState","Mitkaempfen!");
        }

        return currentModel;
    }

    // Method which returns a player object from the player list
    // based on the player id.
    private Player findPlayerbyID(long playerID, List<Player> players){
        for (Player p:players) {
            if (p.getId() == playerID) {
                return p;
            }
        }
        return null;
    }

    // Method which returns a player class name (in german)
    // based on the player class type.
    private String getPlayerClass(PlayerClass pC){
        String out = "Klasse: N/A";
        if (pC instanceof Dwarf){
            out = "Zwerg /";
        }
        else if (pC instanceof Elf){
            out = "Elf /";
        }
        else if (pC instanceof Halbling){
            out = "Halbling /";
        }
        else if (pC instanceof Human){
            out = "Mensch /";
        }
        return out;
    }

    // Method which returns a player race name (in german)
    // based on the player race type.
    private String getPlayerRace(PlayerRace pR){
        String out = "Rasse: N/A";
        if (pR instanceof Priest){
            out = "Priester /";
        }
        else if (pR instanceof Thief){
            out = "Dieb /";
        }
        else if (pR instanceof Warrior){
            out = "Krieger /";
        }
        else if (pR instanceof Wizard){
            out = "Zauberer /";
        }
        return out;
    }

    private String getMainPlayerName(){
        return userRepository.findById(gameState.getMainPlayer().getId()).get().getName();
    }

}
