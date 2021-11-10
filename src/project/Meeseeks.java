package project;

import graph.SubPlot;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

public class Meeseeks extends Boid {
	private PVector targetMonster;
	private int monsterCount;
	private PImage img, meeseeksNormal, meeseeksEat;
	private PApplet p;
	private Monster Monster;

	protected Meeseeks(PVector pos, float radius, double[] window, PImage img, PApplet p) {
		super(pos, 1, radius, window);
		this.p = p;
		this.img = img;
		meeseeksNormal = p.loadImage("pictures/MRMeeseeks/MrM.png");
		meeseeksEat = p.loadImage("pictures/MRMeeseeks/MrM2.png");
		resetFood();
	}

	public void resetFood() {
		Monster = null;
		targetMonster = null;
		monsterCount = 0;
	}

	public boolean eat(PopulationMonster population) {
		boolean ate = false;

		if (Monster != null && targetMonster != null) {
			if (PVector.dist(pos, targetMonster) < 0.17f) {
				ate = true;
				population.getMonsters().remove(Monster);
				monsterCount++;
				targetMonster = null;
				Monster = null;
			}
		}
		return ate;
	}

	public int getMonsterCount() {
		return monsterCount;
	}

	public PVector discover(PopulationMonster population) {
		boolean seekMonster = false;
		PVector f;
		float dmax = Float.MAX_VALUE;
		for (Monster monster : population.getMonsters()) {
			if (isInSight(monster.getPos())) {
				float d = PVector.dist(pos, monster.getPos());
				if (d < dmax) {
					dmax = d;
					Monster = monster;
					seekMonster = true;
				}
			}
		}
		if (seekMonster) {
			targetMonster = Monster.getPos();
			f = pursuit(Monster);
			changeImage(1);

		} else {
			f = wander(p);
			changeImage(0);
		}

		return f;
	}

	public void display(PApplet p, SubPlot plt) {

		img.resize(50, 50);

		p.pushMatrix();
		float[] pp = plt.getPixelCoord(pos.x, pos.y);
		p.translate(pp[0], pp[1]);
		p.rotate(-vel.heading());
		p.rotate((float) -Math.PI / 2);
		p.image(img, pos.x - img.width / 2, pos.y - img.height / 2);

		p.popMatrix();

	}

	public void changeImage(int nImagem) {
		if (nImagem == 0) {
			img = meeseeksNormal;
		} else if (nImagem == 1) {
			img = meeseeksEat;
		}
	}

}
