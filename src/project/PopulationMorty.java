package project;

import java.util.ArrayList;

import graph.SubPlot;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

public class PopulationMorty {
	private ArrayList<Morty> mortys;
	private static float radius = PopulationModel.radius;

	private double[] window;
	private PApplet p;
	private PImage img;

	public PopulationMorty(PApplet p, SubPlot plt) {
		window = plt.getWindow();
		this.p = p;
		img = p.loadImage("pictures/Morty/morty_normal.png");
		mortys = new ArrayList<Morty>();

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

	public void live(StockOfCrystals food, PopulationMeeseeks population, float dt) {
		PVector f;
		for (Morty a : mortys) {

			f = a.discover(food);
			a.applyForce(f);
			a.move(dt);

			a.eat(food);

			Meeseeks m = a.createMeeseeks();
			if (m != null) {
				population.addMeeseeks(m);
			}
		}
	}

	public void display(PApplet p, SubPlot plt) {
		for (Morty a : mortys)
			a.display(p, plt);
	}

	public void addMorty() {
		mortys.add(new Morty(rndTarget(), radius, window, img, p));

	}
	
	public ArrayList<Morty> getMortys() {
		return mortys;
	}

}