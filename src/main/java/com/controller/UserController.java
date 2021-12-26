package com.controller;

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
import java.util.Map;
import java.util.Optional;


@Controller
@RequestMapping //(path="/users")
public class UserController {

    private final UserRepository userRepository;
    private final MessageRepository messageRepository;
    private boolean gameStarted = false;

    @Autowired
    public UserController(UserRepository userRepository, MessageRepository messageRepository) {
        this.userRepository = userRepository;
        this.messageRepository = messageRepository;
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
       model.put("messages", messageRepository.findAll());
        return "GamePage";
    }






}
