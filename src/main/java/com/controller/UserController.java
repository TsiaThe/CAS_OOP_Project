package com.controller;

import com.backend.Starter;
import com.backend.players.*;
import com.repository.MessageRepository;
import com.repository.UserRepository;
import com.web.Message;
import com.web.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Controller
@RequestMapping //(path="/users")
public class UserController {

    private final UserRepository userRepository;
    private final MessageRepository messageRepository;
    private final Starter gameStarter;
    private boolean gameStarted = false;

    @Autowired
    public UserController(UserRepository userRepository, MessageRepository messageRepository, Starter gameStarter) {
        this.userRepository = userRepository;
        this.messageRepository = messageRepository;
        this.gameStarter = gameStarter;
    }

    @GetMapping("/")
    public String loginPage(Model model) {
        if (userRepository.count()==4) return "SupportPage";
        model.addAttribute("users", userRepository.findAll());
        return "LoginPage";
    }

    @GetMapping("/signup")
    public String showSignUpForm() {
        return "AddUser";
    }

    @PostMapping("/adduser")
    public String addUser(@Valid User user, BindingResult result, Model model) {
        for (User u:userRepository.findAll()){
         if (u.getName().equals(user.getName())) return "AddUser";
        }
        if (userRepository.count()==4){
            return "SupportPage";
        }
        User newUser = userRepository.save(user);
        model.addAttribute("users", userRepository.findAll());
        model.addAttribute("userID", newUser.getId());
        return "redirect:/action/"+newUser.getId();
    }

    @GetMapping("/action/{id}")
    public String ActionPage(@PathVariable("id") long id, Map<String, Object> model) {
        model.put("currentUser", userRepository.findById(id));
        model.put("users", userRepository.findAll());

        // Add player information bar (name, class, race, level, fighting strength)
        playerModel(id, model);

        if (messageRepository.count()==0){
           messageRepository.save(new Message("Chat history"));
       }
        model.put("messages", messageRepository.findAll());
        if (userRepository.count()==4){
            return "GamePage";
       }
        return "WaitPage";
    }

    @PostMapping("/action/{id}")
    public String ChatMessage(@Valid Message message, @PathVariable("id") long id, Map<String, Object> model) {
            String sender = "";
        if (userRepository.findById(id).isPresent()){
            Optional<User> pageUser = userRepository.findById(id);
            model.put("currentUser", pageUser);
            sender = pageUser.get().getName();
        }
        if(message.getMessageText() != null){
            message.setMessageText(sender+": "+message.getMessageText());
            messageRepository.save(message);
        }

        // Add player information bar (name, class, race, level, fighting strength)
        playerModel(id, model);

        model.put("messages", messageRepository.findAll());
        return "GamePage";
    }


    private Map<String, Object> playerModel(long id, Map<String, Object> currentModel){

        Player currentPlayer = findPlayerbyID(id, gameStarter.getPlayers());
        currentModel.put("playerClass", getPlayerClass(currentPlayer.getPlayerClass()));
        currentModel.put("playerRace", getPlayerRace(currentPlayer.getPlayerRace()));
        currentModel.put("playerLevel", currentPlayer.getLevel()+" /");
        currentModel.put("playerStrength", String.valueOf(currentPlayer.getFightStrength()));

        return currentModel;
    }


    private Player findPlayerbyID(long playerID, List<Player> players){
        for (Player p:players) {
            if (p.getId() == playerID) {
                return p;
            }
        }
        return null;
    }

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
