import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Game 
{	
	private Hero hero;
	private Water[] water;
	private ArrayList<Pant> pants;
	private ArrayList<Fire> fires;
	private ArrayList<Fireball> fireballs;
	private Fireball fireball;
	private Fire fire;
	private Random randGen;
	private int controlType;

	final int NUM_PANTS;
	final int NUM_FIRES;

	public Game(String level, Random randGen) {

		this.water = new Water[8];
		this.pants = new ArrayList<Pant>();
		this.fires = new ArrayList<Fire>();
		this.fireballs = new ArrayList<Fireball>();
		this.randGen = randGen;

		this.NUM_PANTS = 20;
		this.NUM_FIRES = 6;

		this.controlType = (randGen.nextInt(3) + 1);


		if (level.equalsIgnoreCase("RANDOM")) {
			createRandomLevel();
		} else {
			loadLevel(level);
		}
	}

	public String update(int time) {

		//updates pants
		for (int i = 0; i < pants.size(); i++) {
			pants.get(i).update(time);
		}

		//updates fires
		for (int i = 0; i < fires.size(); i++) {
			fireball = fires.get(i).update(time);
			if (fireball != null) {
				fireballs.add(fireball);
			}
		}

		//handles pant to fire interactions
		for (int i = 0; i < pants.size(); i++) {
			fire = pants.get(i).handleFireballCollisions(fireballs);
			if (fire != null) {
				fires.add(fire);
			}
		}

		//updates water
		for (int i = 0; i < water.length; i++) {
			if (water[i] != null) {
				water[i] = water[i].update(time);
			}
		}

		//update fire-balls
		for (int i = 0; i < fireballs.size(); i++) {
			fireballs.get(i).update(time);
		}

		//handles hero to fire-ball interactions
		if (this.hero.handleFireballCollisions(fireballs)) {
			return "QUIT";
		}

		//handles fire-ball to water interactions
		for (int i = 0; i < fireballs.size(); i++) {
			fireballs.get(i).handleWaterCollisions(water);
		}

		//handles fire to water interactions
		for (int i = 0; i < fires.size(); i++) {
			fires.get(i).handleWaterCollisions(water);
		}

		//check if fire is dead
		for (int i = 0; i < fires.size(); i++) {
			if (fires.get(i).shouldRemove()) {
				fires.remove(i);
			}
		}

		//check if pant is dead
		for (int i = 0; i < pants.size(); i++) {
			if (pants.get(i).shouldRemove()) {
				pants.remove(i);
			}
		}

		//check if fire-ball is dead
		for (int i = 0; i < fireballs.size(); i++) {
			if (fireballs.get(i).shouldRemove()) {
				fireballs.remove(i);
			}
		}

		//updates hero
		hero.update(time, water);

		if (fires.size() == 0) {
			return "ADVANCE";
		}

		if (pants.size() == 0) {
			return "QUIT";
		}

		return "CONTINUE";
	}	

	public String getHUDMessage() { return "Pants Left: " + pants.size() + "\n" +
			"\nFires Left: "  + fires.size(); 
	}

	public void createRandomLevel() {

		//create hero at specified x and y coordinates
		this.hero = new Hero(((float)Engine.getWidth()/2), 
				((float)Engine.getHeight()/2), controlType);

		//create 20 random pants
		for (int i = 0; i < NUM_PANTS; i++) {
			float x = (randGen.nextFloat() * Engine.getWidth());
			float y = (randGen.nextFloat() * Engine.getHeight());
			pants.add(new Pant(x, y, randGen));
		}

		//creates 6 random fires
		for (int i = 0; i < NUM_FIRES; i++) {
			float x = (randGen.nextFloat() * Engine.getWidth());
			float y = (randGen.nextFloat() * Engine.getHeight());
			fires.add(new Fire(x,y, randGen));
		}
	}

	public void loadLevel(String level) { 

		//create Scanner object
		Scanner input = new Scanner(level);

		//initialize arrayLists
		this.pants = new ArrayList<Pant>();
		this.fires = new ArrayList<Fire>();

		//create local variables
		String line = null;
		String line2 = null;
		String[] tokens = null;

		//loop through each line in the level description
		while (input.hasNextLine()) {

			//get the next line
			line = input.nextLine();

			//filters the line
			String clean = line.replace("@", ",");
			String clean2 = clean.replace(":", ",");

			//breaks line into tokens
			tokens = clean2.split(",");

			//determine the types of objects to add
			for (int i = 0; i < tokens.length; i++) {

				//System.out.println(i + ": " + tokens[i]);
				
				tokens[i] = tokens[i].trim();
				
				if (tokens[i].equalsIgnoreCase("controlType")) {
					controlType = new Integer(tokens[i + 1].trim());
				}
				else if (tokens[i].equalsIgnoreCase("FIRE")) {
					float x = Float.parseFloat(tokens[i + 1].trim());
					float y = Float.parseFloat(tokens[i + 2].trim());
					fires.add(new Fire(x,y, randGen));
				}
				else if (tokens[i].equalsIgnoreCase("pant")) {
					float x = Float.parseFloat(tokens[i + 1].trim());
					float y = Float.parseFloat(tokens[i + 2].trim());
					pants.add(new Pant(x,y, randGen));
				}
				else if (tokens[i].equalsIgnoreCase("hero")) {
					float x = Float.parseFloat(tokens[i + 1].trim());
					float y = Float.parseFloat(tokens[i + 2].trim());
					hero = new Hero(x,y, controlType);
				}
			}
		}
		input.close();
	}

	public static void main(String[] args) {
		Application.startEngineAndGame(args);		
	}
}
