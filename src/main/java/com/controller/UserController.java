package com.controller;

import com.dto.GameState;
import com.repository.UserRepository;
import com.web.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

/**
 * This controller is responsible for:
 * 1. Signup of a new player.
 * 2. Redirecting a player to the waiting lobby.
 * 3. Informing any player which arrives after the
 *    start of a game, that the game has started.
 * 4. Redirecting a player to the ActionPage.
 * @author Theofanis Tsiantas
 * @version  2021.12.27 - version 1
 */
@Controller
@RequestMapping
public class UserController {

    // Repo with all signed up users.
    private final UserRepository userRepository;
    // DTO which holds the actual game state.
    private final GameState gameState;

    @Autowired
    public UserController(UserRepository userRepository, GameState gameState) {
        this.userRepository = userRepository;
        this.gameState= gameState;
    }

    // Mapping to initial page, which contains information of waiting players (if any),
    // and allows for signing up.
    // In case of started game, it redirects to the SupportPage (info that game is ongoing).
    @GetMapping("/")
    public String loginPage(Model model) {
        if (userRepository.count()==4) return "SupportPage";
        if (userRepository.count()>0) model.addAttribute("existingUsers",true);
        model.addAttribute("users", userRepository.findAll());
        return "LoginPage";
    }

    // Mapping to the signp capability which is provided by the AddUser page.
    @GetMapping("/signup")
    public String showSignUpForm() {
        return "AddUser";
    }

    // Post method of the AddUser page.
    // It redirects to the same page (AddUser) if:
    // 1. The user tries to login with the same name as another user.
    // 2. The user tries to login with an empty name.
    // 3. The user tries to login with a name starting with a space.
    // ATTENTION:The BindingResult parameter is not used but it is necessary
    // otherwise the blank input check does not work!!!
    @PostMapping("/adduser")
    public String addUser(@Valid User user, BindingResult result, Model model) {
        for (User u:userRepository.findAll()){
         if (u.getName().equals(user.getName())) return "AddUser";
        }
        if (user.getName()=="" || user.getName().charAt(0)==' ') return  "AddUser";
        if (userRepository.count()==gameState.getNumberOfPlayers()){
            return "SupportPage";
        }
        User newUser = userRepository.save(user);
        gameState.assignPlayerId(user.getId());
        model.addAttribute("users", userRepository.findAll());
        return "redirect:/action/"+newUser.getId();

    }

}
