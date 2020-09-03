package de.bermuda.hero.frontend.universum;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import de.bermuda.hero.client.ApiClient;
import de.bermuda.hero.client.api.HeroApi;
import de.bermuda.hero.client.api.RepositoryApi;
import de.bermuda.hero.client.model.Hero;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;

@Component
public class HeroServiceRestClient implements HeroService {

    private static final Logger LOGGER = LoggerFactory.getLogger(HeroServiceRestClient.class);
    private final HeroApi heroApi;
    private final RepositoryApi repositoryApi;
    private final DiscoveryClient discoveryClient;
    private final String backendUrl;
    private String backendInstanceName;

    public HeroServiceRestClient(DiscoveryClient discoveryClient,
                                 ConfigurableEnvironment configurableEnvironment,
                                 @Value("${backend.instanceName}") String backendInstanceName) {
        this.backendInstanceName = backendInstanceName;
        this.discoveryClient = discoveryClient;
        backendUrl =  configurableEnvironment.getProperty("backend.url");
        String username = configurableEnvironment.getProperty("backend.username");
        String password = configurableEnvironment.getProperty("backend.password");
        heroApi = new HeroApi();
        final ApiClient apiClient = heroApi.getApiClient();
        apiClient.setBasePath(retrieveURL());


        if (username != null ) {
            apiClient.setPassword(password);
            apiClient.setUsername(username);
        } else {
            LOGGER.info("No username, password: '{}'", password);
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

    private String retrieveURL() {
        // Maybe the serviceId should be configurable.
        Optional<ServiceInstance> serviceOptional = discoveryClient.getInstances(backendInstanceName).stream().findAny();
        if (serviceOptional.isPresent()) {
            ServiceInstance serviceInstance = serviceOptional.get();
            LOGGER.info("Found Service URL: '{}''", serviceInstance.getUri().toString());
            URI uri = serviceInstance.getUri();
            String baseUrl = "/api";
            String fullURL = uri.toString() + baseUrl;
            LOGGER.info("ServicePath: '{}'", fullURL);
            return fullURL;
        }
        return backendUrl;
    }
}
