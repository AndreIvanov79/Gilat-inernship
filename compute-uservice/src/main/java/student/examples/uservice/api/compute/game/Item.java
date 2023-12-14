package student.examples.uservice.api.compute.game;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public abstract class Item {
	private int id;
	private int x;
	private int y;
	private int size;
	private int ang;
	private int mass;
	private int sx;
	private int sy;
	private int sang;
	
	public void update() {
		x += sx;
		y += sy;
		ang += sang;
	}
}
