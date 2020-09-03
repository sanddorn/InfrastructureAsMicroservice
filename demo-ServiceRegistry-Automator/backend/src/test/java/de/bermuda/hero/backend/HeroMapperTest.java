package de.bermuda.hero.backend;

import com.javacook.proxifier.Proxifier;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

public class HeroMapperTest {

    private HeroMapper subjectUnderTest = new HeroMapperImpl();

    @Test
    public void map() {
        de.bermuda.hero.model.Hero modelHero = Proxifier.proxyOf(createModelHero());
        Hero hero = subjectUnderTest.map(modelHero);
        Proxifier.assertAllGettersInvoked(modelHero);
    }

    @Test
    public void reverseMap() {
        Hero hero = Proxifier.proxyOf(createHero());
        de.bermuda.hero.model.Hero modelHero = subjectUnderTest.reverseMap(hero);
        Proxifier.assertAllGettersInvoked(hero);
    }

    private Hero createHero() {
        Hero hero = new Hero();
        hero.setName("Batman");
        hero.setCity("Gotham City");
        hero.setUniversum(ComicUniversum.MARVEL);
        return hero;
    }

    private de.bermuda.hero.model.Hero createModelHero() {
        de.bermuda.hero.model.Hero hero = new de.bermuda.hero.model.Hero();
        hero.setName("Batman");
        hero.setCity("Gotham City");
        hero.setUniversum(de.bermuda.hero.model.Hero.UniversumEnum.MARVEL);
        return hero;
    }

}