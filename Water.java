import java.util.ArrayList;

public class Water {
	private Graphic graphic;
	private float speed;
	private float distanceTraveled;

	public Water(float x, float y, float direction) {
		this.graphic = new Graphic();

		//set speed
		this.speed = 0.70f; //initial value set at 0.70f

		//set type
		this.graphic.setType("WATER");

		//set X and Y 
		this.graphic.setX(x);
		this.graphic.setY(y);

		//set direction
		this.graphic.setDirection(direction);
	}
	public Water update(int time) {
		
		//set x-coordinate
		this.graphic.setX(this.graphic.getX() + 
				this.graphic.getDirectionX() * (speed * time));
		
		//set y-coordinate
		this.graphic.setY(this.graphic.getY() + 
				this.graphic.getDirectionY() * (speed * time));

		this.graphic.draw();

		float initialX = this.graphic.getX();
		float initialY = this.graphic.getY();

		if (distanceTraveled < 200) {
			float newX = this.graphic.getX() + this.graphic.getDirectionX() * 
					(speed * time);
			float newY = this.graphic.getY() + this.graphic.getDirectionY() * 
					(speed * time);
			float changeX = (float) Math.pow(newX - initialX, 2);
			float changeY = (float) Math.pow(newY - initialY, 2);
			float distance = (float) (Math.sqrt(changeX + changeY));

			distanceTraveled = distanceTraveled + distance;
			return this;
		}
		else {
			return null;
		}
	}

	public Graphic getGraphic() {
		return this.graphic;
	}
}
