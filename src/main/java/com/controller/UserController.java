package com.controller;

import com.repository.UserRepository;
import com.web.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;


@Controller
@RequestMapping //(path="/users")
public class UserController {

    private final UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
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
        User newUser = userRepository.save(user);
        model.addAttribute("users", userRepository.findAll());
        model.addAttribute("userID", newUser.getId());
        return "redirect:/action/"+newUser.getId();
    }

    @GetMapping("/action/{id}")
    public String Wait(@PathVariable("id") long id, Model model) {
        model.addAttribute("currentUser", userRepository.findById(id));
        model.addAttribute("userID", id);
        if (userRepository.count()==4){
            return "GamePage";
        }
        model.addAttribute("users", userRepository.findAll());
        return "WaitPage";
    }









    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        model.addAttribute("user", user);

        return "update-user";
    }

    @PostMapping("/update/{id}")
    public String updateUser(@PathVariable("id") long id, @Valid User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            user.setId(id);
            return "update-user";
        }

        userRepository.save(user);

        return "redirect:/users/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") long id, Model model) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        userRepository.delete(user);

        return "redirect:/users/list";
    }
}
