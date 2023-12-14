package student.examples.uservice.api.compute.game;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import student.examples.uservice.api.compute.service.GameService;

@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
public class SimpleTranslationTest {
	@Autowired
	private GameService gameService;
	
	@MockBean
	private Rock rock;
	
	private Set<Item> items;
	
	@BeforeAll
	public void setup() {
		rock = new Rock(0,0,0,0,0,0,0,1,0);
		
		items = new HashSet<>();
		items.add(rock);
		Space space = new Space();
		space.setItems(items);
		
		Game game =new Game();
		game.setSpace(space);
		
		gameService.getGames().add(game);
	}
	
	@AfterAll
	public void cleanup() {
		items.clear();
	}
	
	@Test
	public void testRockY_shouldBeIncremented_whenGameServiceUpdate() {
		
		for (int i=0; i<10; i++) {
			gameService.update();
		}
		
		int result = gameService.getGames().stream().findFirst().get()
				.getSpace().getItems().stream().findFirst().get().getY();
		
		Assertions.assertEquals(10, result);
	}

}
