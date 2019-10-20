package de.bermuda.hero.frontend.universum;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import de.bermuda.hero.client.ApiClient;
import de.bermuda.hero.client.api.HeroApi;
import de.bermuda.hero.client.api.RepositoryApi;
import de.bermuda.hero.client.model.Hero;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;

@Component
public class HeroServiceRestClient implements HeroService {

    private final HeroApi heroApi;
    private final Logger LOGGER = LoggerFactory.getLogger(HeroServiceRestClient.class);
    private final RepositoryApi repositoryApi;


    public HeroServiceRestClient(ConfigurableEnvironment configurableEnvironment) {
        String backendUrl =  configurableEnvironment.getRequiredProperty("backend.url");
        String username = configurableEnvironment.getProperty("backend.username");
        String password = configurableEnvironment.getProperty("backend.password");
        heroApi = new HeroApi();
        final ApiClient apiClient = heroApi.getApiClient();
        apiClient.setBasePath(backendUrl);
        if (username != null ) {
            apiClient.setPassword(password);
            apiClient.setUsername(username);
        }
        repositoryApi = new RepositoryApi(apiClient);
    }

    @Override
    public List<Hero> collectAllHeros() {
        List<Hero> heroList = new ArrayList<>();
        try {
            heroList = heroApi.collectAllHeros();
        } catch (RestClientException e) {
            LOGGER.debug("Could not collect all Heros", e);
        }
        return heroList;
    }

    @Override
    public void addHeroToRepository(Hero hero, String repositoryName) {
        try {
            heroApi.addHeroToRepository(repositoryName, hero);
        } catch (RestClientException e) {
            LOGGER.debug("Could not add Hero", e);
        }

    }

    @Override
    public Set<String> getHeroRepositoryNames() {
        try {
            return new HashSet<>(repositoryApi.getHeroRepositoryNames());
        } catch (RestClientException e) {
            LOGGER.debug("Could not get repositories", e);
            return Collections.emptySet();
        }
    }
}
