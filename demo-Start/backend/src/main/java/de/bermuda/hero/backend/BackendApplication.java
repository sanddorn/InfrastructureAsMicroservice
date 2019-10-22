package de.bermuda.hero.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@SpringBootApplication(scanBasePackages = {"de.bermuda.hero", "de.bermuda.hero.backend"})
public class BackendApplication {
    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }

    // This is part of the backend, must move when seperate applications were generated.
    @Bean
    Map<String, HeroRepository> heroRepositoryFactory(Set<HeroRepository> heroRepositories) {
        Map<String, HeroRepository> heroRepositoryFactory = new HashMap<>();
        heroRepositories.forEach(heroRepository -> heroRepositoryFactory.put(heroRepository.getName(), heroRepository));
        return heroRepositoryFactory;
    }

}
