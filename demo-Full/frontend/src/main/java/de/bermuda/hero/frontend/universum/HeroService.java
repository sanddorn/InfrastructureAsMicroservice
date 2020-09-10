package de.bermuda.hero.frontend.universum;

import de.bermuda.hero.client.model.Hero;

import java.util.List;
import java.util.Set;

public interface HeroService {
    List<Hero> collectAllHeros();

    void addHeroToRepository(Hero hero, String repositoryName);

    Set<String> getHeroRepositoryNames();
}
