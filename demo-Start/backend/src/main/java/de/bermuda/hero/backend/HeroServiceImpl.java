package de.bermuda.hero.backend;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
public class HeroServiceImpl implements HeroService {
    private Map<String, HeroRepository> heroRepositoryFactory;

    HeroServiceImpl(Map<String, HeroRepository> heroRepositoryFactory) {
        this.heroRepositoryFactory = heroRepositoryFactory;
    }

    @Override
    public List<Hero> collectAllHeros() {
        List<Hero> allHeros = new ArrayList<>();
        for (HeroRepository heroRepository : heroRepositoryFactory.values()) {
            allHeros.addAll(heroRepository.allHeros());
        }
        return allHeros;
    }

    @Override
    public void addHeroToRepository(Hero hero, String repositoryName) {
        HeroRepository heroRepository = findHeroRepository(repositoryName);
        heroRepository.addHero(hero);
    }

    @Override
    public Set<String> getHeroRepositoryNames() {
        return heroRepositoryFactory.keySet();
    }

   private HeroRepository findHeroRepository(String repositoryName) {
        return heroRepositoryFactory.get(repositoryName);
    }
}
