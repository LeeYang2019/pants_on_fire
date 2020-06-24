import java.util.ArrayList;
import java.util.Random;

public class Pant {
	
	private Graphic graphic;
	private Random randGen;
	private Fire fire;
	private boolean isDestroyed;
	 
	public Pant(float x, float y, Random randGen) {
	
		this.graphic = new Graphic();
		this.randGen = randGen;
		
		//set type
		this.graphic.setType("PANT");
		
		//set x and y coordinate
		this.graphic.setX(x);
		this.graphic.setY(y);
		
		//set direction
		this.graphic.setDirection((float)Math.PI * 2);
		
		this.isDestroyed = false;
	}
	
	public void update(int time) {
		this.graphic.draw();
	}
	
	public Fire handleFireballCollisions(ArrayList<Fireball> fireballs)  {
		
		for (int i = 0; i < fireballs.size(); i++) {
			if (this.graphic.isCollidingWith(fireballs.get(i).getGraphic())) {
				this.graphic.destroy();
				fireballs.get(i).destroy();
				fire = new Fire(this.graphic.getX(), 
						this.graphic.getY(), 
						randGen);
				this.isDestroyed = true;
				return this.fire;
			}
		}
		return null;
	}
	
	public boolean shouldRemove() {
		
		if (this.isDestroyed == true) {
			return true;
		} else {
			return false;
		}
	}
	
	public Graphic getGraphic() {
		return this.graphic;
	}
}
