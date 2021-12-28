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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping // (path="/users")
public class ActionController {

    private final UserRepository userRepository;
    private final MessageRepository messageRepository;
    private final GameState gameState;

    @Autowired
    public ActionController(UserRepository userRepository, MessageRepository messageRepository, Starter gameStarter, GameState gameState) {
        this.userRepository = userRepository;
        this.messageRepository = messageRepository;
        this.gameState= gameState;
    }

    @GetMapping("/action/{currentUserId}/{mainPlayerId}")
    public String ActionPage(@PathVariable("currentUserId") long cuID,
                             @PathVariable("mainPlayerId") long mpID,
                              Map<String, Object> model) {
        model.put("currentUser", userRepository.findById(cuID));
        model.put("users", userRepository.findAll());

        // Add player information bar (name, class, race, level, fighting strength)
        playerModel(cuID, model);

        if (messageRepository.count()==0){
           messageRepository.save(new Message("Chat history"));
       }
        model.put("messages", messageRepository.findAll());
        if (userRepository.count()==4){
            return "ActionPage";
       }
        return "WaitPage";
    }

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

        model.put("messages", messageRepository.findAll());
        return "ActionPage";
    }


    // ----------------------------------------------------------------------------------------
    //      -------------------------- SUPPORTING METHODS -------------------------------
    // ----------------------------------------------------------------------------------------

    // Method which populates the current model view with player information
    // (class, race, level, fighting strength).
    private Map<String, Object> playerModel(long cuID, Map<String, Object> currentModel){

        Player currentPlayer = findPlayerbyID(cuID, gameState.getAllPlayers());
        currentModel.put("playerClass", getPlayerClass(currentPlayer.getPlayerClass()));
        currentModel.put("playerRace", getPlayerRace(currentPlayer.getPlayerRace()));
        currentModel.put("playerLevel", currentPlayer.getLevel()+" /");
        currentModel.put("playerStrength", String.valueOf(currentPlayer.getFightStrength()));

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
