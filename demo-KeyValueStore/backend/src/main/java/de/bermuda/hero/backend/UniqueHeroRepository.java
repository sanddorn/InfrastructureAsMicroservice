package de.bermuda.hero.backend;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import javax.annotation.PostConstruct;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Repository;

@Repository
public class UniqueHeroRepository implements HeroRepository {

    private Set<Hero> heroes = new HashSet<>();
    private Counter addCounter;

    public UniqueHeroRepository(MeterRegistry meterRegistry) {
        addCounter = meterRegistry.counter("hero.repository.unique");
    }

    @PostConstruct
    public void init() {
        heroes.add(new Hero("Batman", "Gotham City", ComicUniversum.DC_COMICS));
        heroes.add(new Hero("Superman", "Metropolis", ComicUniversum.DC_COMICS));
    }

    @Override
    public String getName() {
        return "Unique";
    }

    @Override
    public void addHero(Hero hero) {
        addCounter.increment();
        heroes.add(hero);
    }

    @Override
    public Collection<Hero> allHeros() {
        return new HashSet<>(heroes);
    }

}
