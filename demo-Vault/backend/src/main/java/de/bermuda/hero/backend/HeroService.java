package de.bermuda.hero.backend;

import java.util.List;
import java.util.Set;

public interface HeroService {
    List<Hero> collectAllHeros();

    void addHeroToRepository(Hero hero, String repositoryName);

    Set<String> getHeroRepositoryNames();
}
