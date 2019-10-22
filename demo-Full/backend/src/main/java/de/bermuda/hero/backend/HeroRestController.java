package de.bermuda.hero.backend;

import de.bermuda.hero.api.HeroApi;
import de.bermuda.hero.model.Hero;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class HeroRestController implements HeroApi {
    private final HeroService heroService;
    private final HeroMapper mapper;

    public HeroRestController(HeroService heroService, HeroMapper mapper) {
        this.heroService = heroService;
        this.mapper = mapper;
    }

    @Override
    public ResponseEntity<Void> addHeroToRepository(String repository, Hero body) {
        heroService.addHeroToRepository(mapper.map(body), repository);
        return ResponseEntity.created(null).build();
    }

    @Override
    public ResponseEntity<List<Hero>> collectAllHeros() {
        return ResponseEntity.ok(
                heroService.collectAllHeros()
                        .stream()
                        .map(mapper::reverseMap)
                        .collect(Collectors.toList()));
    }

    @Override
    public ResponseEntity<List<String>> getHeroRepositoryNames() {
        return ResponseEntity.ok(heroService.getHeroRepositoryNames().stream().collect(Collectors.toList()));
    }
}
