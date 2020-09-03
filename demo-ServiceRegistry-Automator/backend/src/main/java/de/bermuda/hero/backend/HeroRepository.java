package de.bermuda.hero.backend;

import java.util.Collection;

public interface HeroRepository {

    String getName();

    void addHero(Hero hero);

    Collection<Hero> allHeros();
}
