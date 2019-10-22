package de.bermuda.hero.frontend;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(value = {"spring.cloud.consul.enabled=false"})
public class HeroApplicationTests {

	@Test
	public void contextLoads() {
	}

}
