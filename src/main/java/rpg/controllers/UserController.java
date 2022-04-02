package rpg.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.AllArgsConstructor;
import rpg.entity.Race;
import rpg.entity.Specialisation;
import rpg.services.UserService;

import javax.validation.Valid;

@Controller
@AllArgsConstructor
@RequestMapping
public class UserController {
    private final UserService userService;

    @PostMapping
    public String addUser(Model model, @Valid String username) {
        model.addAttribute("user", userService.addUser(username));
        return "profile";
    }

    @GetMapping("profile")
    public String profile(Model model) {
        model.addAttribute("user", userService.getCurrent());
        return "profile";
    }

    @GetMapping
    public String login() {
        return "index";
    }



    @GetMapping("race/{race}")
    public String race(@PathVariable Race race, Model model) {
        model.addAttribute("user", userService.race(race));
        return "profile";
    }

    @GetMapping("specialisation/{specialisation}")
    public String specialisation(@PathVariable Specialisation specialisation, Model model) {
        model.addAttribute("user", userService.specialisation(specialisation));
        return "profile";
    }

}
