package com.controller;

import com.backend.Starter;
import com.backend.players.*;
import com.dto.GameState;
import com.repository.MessageRepository;
import com.repository.UserRepository;
import com.web.Message;
import com.web.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
    // DTO which holds the actual game state.
    private final GameState gameState;

    @Autowired
    public ActionController(UserRepository userRepository, MessageRepository messageRepository, Starter gameStarter, GameState gameState) {
        this.userRepository = userRepository;
        this.messageRepository = messageRepository;
        this.gameState= gameState;
    }

    //
    @GetMapping("/action/{currentUserId}/{mainPlayerId}")
    public String ActionPage(@PathVariable("currentUserId") long cuID,
                             @PathVariable("mainPlayerId") long mpID,
                              Map<String, Object> model) {
        model.put("currentUser", userRepository.findById(cuID));
        model.put("users", userRepository.findAll());

        // Add player information bar (name, class, race, level, fighting strength)
        playerModel(cuID, model);
        //
        bootsModel(cuID, model);
        armourModel(cuID, model);
        headgearModel(cuID, model);

        // ------------------------------------------------
        // Testing monster doors
        model.put("door","Curse");
        // ------------------------------------------------


        if (messageRepository.count()==0){
           messageRepository.save(new Message("Chat history"));
       }
        model.put("messages", messageRepository.findAll());
        if (userRepository.count()==4){
            return "ActionPage";
       }
        return "WaitPage";
    }

    //
    @PostMapping("/action/{currentUserId}/{mainPlayerId}")
    public String ChatMessage(@Valid Message message, @PathVariable("currentUserId") long cuID,
                              @PathVariable("mainPlayerId") long mpID,
                              Map<String, Object> model) {
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

        // Add player information bar (name, class, race, level, fighting strength)
        playerModel(cuID, model);
        //
        bootsModel(cuID, model);
        armourModel(cuID, model);
        headgearModel(cuID, model);


        // ------------------------------------------------
        // Testing monster doors
        model.put("door","Curse");
        // ------------------------------------------------


        model.put("messages", messageRepository.findAll());
        return "ActionPage";
    }

    // Post method which sets the fifghting state of a player appropriately.
    @PostMapping("/action/{currentUserId}/{mainPlayerId}/fight")
    public String fightHelp(@PathVariable("currentUserId") long cuID,
                            @PathVariable("mainPlayerId") long mpID,
                            Map<String, Object> model){
        fightState(cuID);
        return "redirect:/action/"+ cuID +"/"+mpID;
    }



    // ----------------------------------------------------------------------------------------
    //      -------------------------- SUPPORTING METHODS -------------------------------
    // ----------------------------------------------------------------------------------------

    // Method which populates the current model view with the boots information.
    private Map<String, Object> bootsModel(long cuID, Map<String, Object> currentModel){
        Player currentPlayer = findPlayerbyID(cuID, gameState.getAllPlayers());
        if (currentPlayer.getBoots()!=null){
            currentModel.put("bootsName", currentPlayer.getBoots().getName());
            currentModel.put("bootsBonus", currentPlayer.getBoots().getBonus());
            currentModel.put("bootsValue", currentPlayer.getBoots().getValue()+" Goldstuecke");
        }
        else{
            currentModel.put("bootsName", "Keine Schuhe");
            currentModel.put("bootsBonus", " ");
            currentModel.put("bootsValue", "");
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
        }
        else{
            currentModel.put("armourName", "Keine Ruestung");
            currentModel.put("armourBonus", " ");
            currentModel.put("armourValue", "");
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
        }
        else{
            currentModel.put("headgearName", "Kein Kopfbedeckung");
            currentModel.put("headgearBonus", " ");
            currentModel.put("headgearValue", "");
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
        // player fighting state.
        if (currentPlayer.getFights()==true){
            currentModel.put("fightingButton","Nicht kaempfen!");
        }
        else{
            currentModel.put("fightingButton","Kaempfen!");
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

}
