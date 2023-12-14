package student.examples.uservice.api.compute.game;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Game {
	private Space space;

	public Game() {
		super();
		this.space = new Space();
	}
}
