import java.util.ArrayList;

public class Hero 
{
	private Graphic graphic;
	private float   speed;
	private int     controlType;

	public Hero(float x, float y, int controlType) {
		
		//initialize data fields
		this.graphic = new Graphic();
		this.speed = 0.12f; //initial value set at 0.12f
		this.controlType = controlType;

		//set type to hero
		this.graphic.setType("HERO");

		//set hero position
		this.graphic.setX(x);
		this.graphic.setY(y);
	}

	public void update(int time, Water[] water) {	
		
		//control Movement
		if (controlType == 1) {
			if (Engine.isKeyHeld("LEFT")) { //left movement and direction
				this.graphic.setX(this.graphic.getX()-(speed*time));
				this.graphic.setDirection((float)Math.PI);
			}
			if (Engine.isKeyHeld("RIGHT")) { //right movement and direction
				this.graphic.setX(this.graphic.getX()+(speed*time));
				this.graphic.setDirection(0);
			}
			if (Engine.isKeyHeld("UP")) { //up movement and direction
				this.graphic.setY(this.graphic.getY()-(speed*time));
				this.graphic.setDirection((float)(3*(Math.PI/2)));
			}
			if (Engine.isKeyHeld("DOWN")) { //down movement and direction
				this.graphic.setY(this.graphic.getY()+(speed*time));
				this.graphic.setDirection((float)(Math.PI/2));
			}
		}
		if (controlType == 2) {
			
			if (Engine.isKeyHeld("LEFT")) { //left movement and direction
				this.graphic.setX(this.graphic.getX()-(speed*time));
				this.graphic.setDirection(Engine.getMouseX(), 
						Engine.getMouseY());
			}
			if (Engine.isKeyHeld("RIGHT")) { //right movement and direction
				this.graphic.setX(this.graphic.getX()+(speed*time));
				this.graphic.setDirection(Engine.getMouseX(), 
						Engine.getMouseY());
			}
			if (Engine.isKeyHeld("UP")) { //up movement and direction
				this.graphic.setY(this.graphic.getY()-(speed*time)); 
				this.graphic.setDirection(Engine.getMouseX(), 
						Engine.getMouseY());
			}
			if (Engine.isKeyHeld("DOWN")) { //down movement and direction
				this.graphic.setY(this.graphic.getY()+(speed*time));
				this.graphic.setDirection(Engine.getMouseX(), 
						Engine.getMouseY());
			}
		}

		if (controlType == 3) {
			
			//compute distance
			double distance = computeDistance(graphic);

			//set direction of hero
			this.graphic.setDirection(Engine.getMouseX(), Engine.getMouseY());

			if (distance > 20) {
				//set x-coordinate
				this.graphic.setX(this.graphic.getX() + 
						this.graphic.getDirectionX() * (speed*time));

				//set y-coordinate
				this.graphic.setY(this.graphic.getY() + 
						this.graphic.getDirectionY() * (speed*time));
			}
		}

		//control water
		if (Engine.isKeyHeld("SPACE") || Engine.isKeyHeld("MOUSE")) {
			
			for (int i = 0; i < water.length; i++)  {
				if (water[i] == null) {
					water[i] = new Water(this.graphic.getX(), 
							this.graphic.getY(), 
							this.graphic.getDirection());
					i = water.length;
				} 
			}
		}

		//update
		this.graphic.draw();
	}
	
	public boolean handleFireballCollisions(ArrayList<Fireball> fireballs) {
		
		for (int i = 0; i < fireballs.size(); i++) {
			if (this.graphic.isCollidingWith(fireballs.get(i).getGraphic())) {
				this.graphic.destroy();
				return true;
			}
		}
		return false;
	}

	private double computeDistance(Graphic graphic2) {
		
		double changeX = Math.pow((Engine.getMouseX()-this.graphic.getX()), 2);
		double changeY = Math.pow((Engine.getMouseY()-this.graphic.getY()), 2);
		double addXY = changeX + changeY;
		double distance = Math.sqrt(addXY);

		return distance; 
	}
	public Graphic getGraphic()	{
		return this.graphic;
	}
}
