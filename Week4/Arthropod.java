package organism;
import java.util.Random;

public class Anthropod extends Organism {
	
	private int legs;
	private Random rand;
	
	public Anthropod(String name, int xCoord, int yCoord, int speed, String direction, int legs) {
		super(name, xCoord, yCoord, direction, speed);
		this.legs = legs;
	}

	private int getLegs() {
		return legs;
	}

	private void setLegs(int legs) {
		this.legs = legs;
	}
	
	@Override
	public String toString() {
		return super.toString() + String.format(", %d", legs);
	}
	
	public move() {
		rand = new Random();
		int randomint = rand.nextInt(4);
		super.move();
	}

}
