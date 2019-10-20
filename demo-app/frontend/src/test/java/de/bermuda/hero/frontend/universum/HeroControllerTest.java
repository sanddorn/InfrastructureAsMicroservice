package de.bermuda.hero.frontend.universum;

import de.bermuda.hero.client.model.Hero;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.ui.Model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HeroControllerTest {

    private static final String REPOSITORY = "repository";


    @Mock
    HeroService heroService;

    @InjectMocks
    HeroController subjectUnderTest;

    @Test
    public void viewAllHeros() {
        Model model = mock(Model.class);
        String view = subjectUnderTest.viewAllHeros(model);
        assertThat(view).isEqualTo("hero/hero.list.html");
        verify(model).addAttribute(eq("heros"), anyList());
        verify(model).addAttribute(eq("ipAddress"), anyString());
        verify(heroService).collectAllHeros();
    }

    @Test
    public void newHero() {
        Model model = mock(Model.class);
        String view = subjectUnderTest.newHero(model);
        assertThat(view).isEqualTo("hero/hero.new.html");
        verify(model).addAttribute(eq("newHero"), any(NewHeroModel.class));
        verify(model).addAttribute(eq("repos"), anySet());
        verify(heroService).getHeroRepositoryNames();
    }

    @Test
    public void addNewHero() {
        NewHeroModel model = mock(NewHeroModel.class);
        when(model.getRepository()).thenReturn(REPOSITORY);
        when(model.getHero()).thenReturn(new Hero());
        String view = subjectUnderTest.addNewHero(model);
        assertThat(view).isEqualTo("redirect:/hero");
        verify(heroService).addHeroToRepository(any(Hero.class), anyString());
    }
}
