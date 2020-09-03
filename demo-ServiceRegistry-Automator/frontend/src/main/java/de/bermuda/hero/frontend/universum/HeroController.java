package de.bermuda.hero.frontend.universum;

import de.bermuda.hero.client.model.Hero;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.List;

@Controller
public class HeroController {

    private final HeroService heroService;

    public HeroController(HeroService heroService) {
        this.heroService = heroService;
    }

    @GetMapping("/hero")
    public String viewAllHeros(Model model) {
        List<Hero> allHeros = heroService.collectAllHeros();
        model.addAttribute("heros", allHeros);
        model.addAttribute("ipAddress", inspectLocalHost());

        return "hero/hero.list.html";
    }

    @GetMapping("/hero/new")
    public String newHero(Model model) {
        model.addAttribute("newHero", new NewHeroModel());
        model.addAttribute("repos", heroService.getHeroRepositoryNames());
        return "hero/hero.new.html";
    }

    @PostMapping("/hero/new")
    public String addNewHero(@ModelAttribute("newHero") NewHeroModel newHeroModel) {
        Hero hero = newHeroModel.getHero();
        String repositoryName = newHeroModel.getRepository();
        heroService.addHeroToRepository(hero, repositoryName);
        return "redirect:/hero";
    }


    private String inspectLocalHost() {
        try {
            return Inet4Address.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }

}
