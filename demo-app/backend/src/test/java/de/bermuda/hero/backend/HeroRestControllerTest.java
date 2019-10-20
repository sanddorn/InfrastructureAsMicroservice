package de.bermuda.hero.backend;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@EnableAutoConfiguration
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {HeroRestController.class, HeroMapper.class, HeroMapperImpl.class})
@AutoConfigureMockMvc
public class HeroRestControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private HeroService heroService;

    @Test
    public void addHeroToRepository() throws Exception {
        mockMvc.perform(post("/api/hero/hero/repository")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                objectMapper.writeValueAsString(new de.bermuda.hero.model.Hero()
                        .name("Batman")
                        .city("Gotham City")
                        .universum(de.bermuda.hero.model.Hero.UniversumEnum.DC_COMICS))))
                .andExpect(status().isCreated());
        verify(heroService).addHeroToRepository(any(Hero.class), eq("repository"));

    }

    @Test
    public void collectAllHeros() throws Exception{
        when(heroService.collectAllHeros()).thenReturn(createHeroList());
        mockMvc.perform(get("/api/hero/heros"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(2)))
                .andExpect(jsonPath("$[0].name", Matchers.is("Batman")));
    }

    @Test
    public void getHeroRepositoryNames() throws Exception {
        when(heroService.getHeroRepositoryNames()).thenReturn(new HashSet<>(Arrays.asList("repo1", "repo2")));
        mockMvc.perform(get("/api/hero/repositories"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(2)));
    }

    private List<Hero> createHeroList() {
        Hero hero1 = new Hero("Batman", "Gotham City", ComicUniversum.DC_COMICS);
        Hero hero2 = new Hero("Ironman", "Los Angeles", ComicUniversum.MARVEL);
        return Arrays.asList(hero1, hero2);
    }
}
