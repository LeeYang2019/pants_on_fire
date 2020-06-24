
public class Fireball {

	private Graphic graphic;
	private float speed;
	private boolean isAlive;

	public Fireball(float x, float y, float directionAngle) {
		
		this.graphic = new Graphic();
		this.graphic.setType("Fireball");
		this.speed = 0.2f; //initial speed is 0.2f

		this.graphic.setX(x);
		this.graphic.setY(y);

		this.graphic.setDirection(directionAngle);
	}

	public void update(int time) {
		
		this.isAlive = true;

		//set x-coordinates
		float xDirection = this.graphic.getX() + this.graphic.getDirectionX() * 
				(speed * time);
		this.graphic.setX(xDirection);

		//set y-coordinates
		float yDirection = this.graphic.getY() + this.graphic.getDirectionY() * 
				(speed * time);
		this.graphic.setY(yDirection);

		//boundaries of the grid 
		if (this.graphic.getX() >= Engine.getWidth() + 100 || 
				this.graphic.getX() <= (Engine.getWidth()-Engine.getWidth()) - 
				100 || this.graphic.getY() >= Engine.getHeight() + 100 ||
				this.graphic.getY() <= (Engine.getHeight() - 
						Engine.getHeight() - 100)) {

			this.graphic.destroy();
			this.isAlive = false;
		}

		if (this.isAlive == true) {
			this.graphic.draw();
		}
	}

	public Graphic getGraphic() {
		return this.graphic;
	}

	public void handleWaterCollisions(Water[] water) {

		for (int i = 0; i < water.length; i++) {
			if (water[i] != null) {
				if (this.graphic.isCollidingWith(water[i].getGraphic())) {

					//destroy fire and change isAlive
					this.isAlive = false;
					this.graphic.destroy();

					//destroy water and change to null
					water[i].getGraphic().destroy();
					water[i] = null;
				}
			}
		}
	}

	public void destroy() {
		this.graphic.destroy();
		this.isAlive = false;
	}

	public boolean shouldRemove() {

		if (this.isAlive == false) {
			return true;
		} else {
			return false;
		}

	}
}
