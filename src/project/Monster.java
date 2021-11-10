package project;

import graph.SubPlot;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

public class Monster extends Boid {

	private Flower flower;
	private Morty Morty;
	private PVector targetFlower, targetMorty;
	private int flowersCount, mortysCount;
	private PImage img, scaryTerryNormal, scaryTerryEat;
	private PApplet p;
	private double[] window;

	protected Monster(PVector pos, float radius, double[] window, PImage img, PApplet p) {
		super(pos, 1, radius, window);
		this.window = window;
		this.p = p;
		this.img = img;
		scaryTerryNormal = p.loadImage("pictures/Monsters/scary_terry.png");
		scaryTerryEat = p.loadImage("pictures/Monsters/scary_terry_eat.png");
		reset();
	}

	public void reset() {
		flower = null;
		Morty = null;
		flowersCount = 0;
		mortysCount = 0;
		targetFlower = new PVector();
		targetMorty = new PVector();
	}

	public boolean eat(StockOfFlowers flowers, PopulationMorty population) {
		boolean ate = false;

		if (Morty != null && targetMorty != null) {
			if (PVector.dist(pos, targetMorty) < 0.17f) {
				mortysCount++;
				ate = true;
				population.getMortys().remove(Morty);
				Morty = null;
				targetMorty = null;
			}
		}
		if (flower != null && targetFlower != null) {
			if (PVector.dist(pos, targetFlower) < 0.15f) {
				flowersCount++;
				ate = true;
				flowers.remove(flower);
				flower = null;
				targetFlower = null;
			}

		}

		return ate;
	}

	public PVector discover(StockOfFlowers flowers, PopulationMorty population/* , float dt */) {

		boolean seekFlower = false;
		boolean seekMorty = false;
		PVector f = null;
		PVector fMorty = null;

		float dmaxFlower = Float.MAX_VALUE;
		float dmaxMorty = Float.MAX_VALUE;

		for (Morty morty : population.getMortys()) {
			if (isInSight(morty.getPos())) {
				float dm = PVector.dist(pos, morty.getPos());

				if (dm < dmaxMorty) {
					dmaxMorty = dm;
					Morty = morty;
					seekMorty = true;
				}
			}
		}

		if (seekMorty) {
			targetMorty = Morty.getPos();
			f = pursuit(Morty);
			changeImage(1);
			fMorty = Morty.evade(this);
			Morty.applyForce(fMorty);
			fMorty = Morty.brake();
			Morty.applyForce(fMorty);

		} else {
			for (Flower Flower : flowers.getFlowers()) {
				if (isInSight(Flower.getPos())) {
					float d = PVector.dist(pos, Flower.getPos());

					if (d < dmaxFlower) {
						dmaxFlower = d;
						flower = Flower;
						seekFlower = true;
					}
				}
			}
			if (seekFlower) {
				targetFlower = flower.getPos();
				f = seek(targetFlower);
				changeImage(0);
			}
		}

		if (!seekFlower && !seekMorty) {
			f = wander(p);
			changeImage(0);
		}

		return f;
	}

	public Monster reproduceMonster() {
		Monster newMonster = null;
		if (flowersCount == 3) {
			flowersCount = 0;
			newMonster = new Monster(pos, radius, window, img, p);
			newMonster.dna = this.dna;
			newMonster.changeImage(0);
		}
		return newMonster;
	}

	public void display(PApplet p, SubPlot plt) {

		img.resize(60, 60);

		p.pushMatrix();
		float[] pp = plt.getPixelCoord(pos.x, pos.y);
		p.translate(pp[0], pp[1]);
		p.rotate(-vel.heading());
		p.rotate((float) -Math.PI / 2);
		p.image(img, pos.x - img.width / 2, pos.y - img.height / 2);

		p.popMatrix();

	}

	public int getMortysCount() {
		return mortysCount;
	}

	public PVector getPos() {
		return this.pos;
	}

	public void changeImage(int nImagem) {
		if (nImagem == 0) {
			img = scaryTerryNormal;
		} else if (nImagem == 1) {
			img = scaryTerryEat;
		}
	}

}
