package rpg.controllers.game;

import rpg.dto.GameDto;
import rpg.entity.ActionEnum;
import rpg.entity.Race;
import rpg.entity.Specialisation;
import rpg.services.GameService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
@RequestMapping("games")
public class GameController {
    private final GameService gameService;

    @GetMapping
    public String play(Model model) {
        GameDto game = gameService.play();
        model.addAttribute("game", game);
        return "game";
    }

    @GetMapping("delete")
    public String delete() {
        gameService.delete();
        return "redirect:/profile";
    }

    @GetMapping("select/{action}")
    public String select(@PathVariable ActionEnum action, Model model) {
        GameDto game = gameService.select(action);
        model.addAttribute("game", game);
        return "game";
    }

}
