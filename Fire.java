import java.util.ArrayList;
import java.util.Random;

public class Fire {
	private Graphic graphic;
	private int fireballCountdown;
	private Random randGen;
	private Fireball fireball;
	private int heat;
	private int NUM_TIMES;

	public Fire(float x, float y, Random randGen) {
		this.graphic = new Graphic();

		this.graphic.setType("FIRE");

		this.graphic.setX(x);
		this.graphic.setY(y);
		this.randGen = randGen;
		this.heat = 40;
		this.graphic.setDirection((float)(Math.PI * 2));
		this.fireballCountdown =  (int)((float) Math.random() * 3000) + 3000;
		this.NUM_TIMES = 4;
	}
	public Fireball update(int time) {
		this.graphic.draw();
		this.fireballCountdown = this.fireballCountdown - time;

		if (this.fireballCountdown <= 0 && this.heat >= 1) {

			fireball = new Fireball(this.graphic.getX(), 
					this.graphic.getY(), 
					((float) Math.PI) * randGen.nextInt());

			this.fireballCountdown =  (int)((float)Math.random() * 3000) + 3000;
			return this.fireball;
		}
		return null;

	}	

	public Graphic getGraphic() {
		return this.graphic;
	}

	public void handleWaterCollisions(Water[] water) {

		for (int i = 0; i < water.length; i++) {
			if (water[i] != null) {
				if (this.graphic.isCollidingWith(water[i].getGraphic())) {

					this.heat--;
					water[i].getGraphic().destroy();
					water[i] = null;

					if (heat < 1) {
						this.graphic.destroy();
					}
				}
			}
		}
	}

	public boolean shouldRemove() {

		if (this.heat < 1) {
			return true;
		}
		else {
			return false;
		}
	}
}
