package de.bermuda.hero.backend;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Repository
public class DuplicateHeroRepository implements HeroRepository {

    private List<Hero> heroes = new ArrayList<>();
    private Counter addCounter;

    public DuplicateHeroRepository(MeterRegistry meterRegistry) {
        addCounter = meterRegistry.counter("hero.repository.duplicate");
    }

    @Override
    public void addHero(Hero hero) {
        addCounter.increment();
        heroes.add(hero);
    }

    @Override
    public Collection<Hero> allHeros() {
        return heroes;
    }

    @Override
    public String getName() {
        return "Duplicate";
    }
}
