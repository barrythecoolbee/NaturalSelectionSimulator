package project;

import java.util.ArrayList;

import graph.SubPlot;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

public class PopulationMonster {
	private ArrayList<Monster> monsters;
	private static float radius = PopulationModel.radius;
	private double[] window;
	private PApplet p;
	private PImage img;

	public PopulationMonster(PApplet p, SubPlot plt) {
		window = plt.getWindow();
		this.p = p;
		img = p.loadImage("pictures/Monsters/scary_terry.png");
		monsters = new ArrayList<Monster>();

	}

	public ArrayList<Monster> getMonsters() {
		return monsters;
	}

	public void live(StockOfFlowers flowers, PopulationMorty PopulationMorty, float dt) {
		PVector f;
		PVector w;

		for (int i = 0; i < monsters.size(); i++) {
			Monster a = monsters.get(i);
			Monster n = a.reproduceMonster();
			if (n != null) {
				monsters.add(n);
			}

			f = a.discover(flowers, PopulationMorty);
			a.applyForce(f);
			a.move(dt);

			if (a.eat(flowers, PopulationMorty)) {
				w = a.wander(p);
				a.applyForce(w);
				a.move(dt);
			}

		}

	}

	public PVector rndTarget() {
		float margin = (float) (0.1f * (window[1] - window[0]));
		float xmin = (float) (window[0] + margin);
		float xmax = (float) ((window[1] * 0.75f) - margin);
		float ymin = (float) (window[2] + margin);
		float ymax = (float) (window[3] - margin);

		float x = (float) (xmin + (xmax - xmin) * Math.random());
		float y = (float) (ymin + (ymax - ymin) * Math.random());

		return new PVector(x, y);
	}

	public void addMonster() {
		monsters.add(new Monster(rndTarget(), radius, window, img, p));

	}

	public void display(PApplet p, SubPlot plt) {
		for (Monster a : monsters) {
			a.display(p, plt);
		}
	}

}
