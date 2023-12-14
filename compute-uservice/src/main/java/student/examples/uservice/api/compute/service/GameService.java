package student.examples.uservice.api.compute.service;

import java.util.HashSet;
import java.util.Set;import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lombok.Getter;
import student.examples.uservice.api.compute.game.Game;

@Getter
@Service
public class GameService {

	private Set<Game> games;
	
	public GameService() {
		super();
		this.games = new HashSet<Game>();
	}
	
	//this should be invoked by timer(1 per sek)
	public void update() {
		games = games.stream()
				.map(game -> {
					game.getSpace().setItems(
						game.getSpace()
						.getItems()
						.stream()
						.map( item -> {
							item.update();
							return item;
						})
						.collect(Collectors.toSet())
					);
					return game;})
				.collect(Collectors.toSet());
	}
}
