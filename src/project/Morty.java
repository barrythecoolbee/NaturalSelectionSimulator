package project;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import graph.SubPlot;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

public class Morty extends Boid {
	private Crystal crystal;
	private PVector target;
	private int crystalsCount;
	private PImage img, imgMeeseeks, mortyNormal, mortyEat;
	private PApplet p;

	protected Morty(PVector pos, float radius, double[] window, PImage img, PApplet p) {
		super(pos, 1, radius, window);
		this.p = p;
		this.img = img;
		imgMeeseeks = p.loadImage("pictures/MRMeeseeks/MrM.png");
		mortyNormal = p.loadImage("pictures/Morty/morty_normal.png");
		mortyEat = p.loadImage("pictures/Morty/morty_eat.png");
		reset();
	}

	public void reset() {
		crystal = null;
		crystalsCount = 0;
		target = new PVector();
	}

	public boolean eat(StockOfCrystals food) {
		boolean ate = false;
		if (crystal != null && target != null) {
			if (PVector.dist(pos, target) < 0.17f) {
				crystalsCount++;
				ate = true;
				food.remove(crystal);
				target = null;
				crystal = null;
			}
		}
		return ate;
	}

	public PVector discover(StockOfCrystals food) {
		boolean seekFood = false;
		PVector f;
		float dmax = Float.MAX_VALUE;
		for (Crystal piece : food.getCrystals()) {
			if (isInSight(piece.getPos())) {
				float d = PVector.dist(pos, piece.getPos());
				if (d < dmax) {
					dmax = d;
					crystal = piece;
					seekFood = true;
				}
			}
		}
		if (seekFood) {
			target = crystal.getPos();
			f = seek(target);
			changeImage(1);

		} else {
			f = wander(p);
			changeImage(0);
		}

		return f;
	}

	public Meeseeks createMeeseeks() {
		Meeseeks newMeeseeks = null;
		if (crystalsCount == 2) {
			crystalsCount = 0;
			newMeeseeks = new Meeseeks(pos, radius, window, imgMeeseeks, p);
			try {
				AudioInputStream audioInputStream = AudioSystem
						.getAudioInputStream(this.getClass().getResource("im_mr_meeseeks.wav"));
				Clip clip = AudioSystem.getClip();
				clip.open(audioInputStream);
				clip.start();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return newMeeseeks;
	}

	public void display(PApplet p, SubPlot plt) {

		img.resize(45, 45);

		p.pushMatrix();
		float[] pp = plt.getPixelCoord(pos.x, pos.y);
		p.translate(pp[0], pp[1]);
		p.rotate(-vel.heading());
		p.rotate((float) -Math.PI / 2);
		p.image(img, pos.x - img.width / 2, pos.y - img.height / 2);

		p.popMatrix();

	}

	public PVector getPos() {
		return this.pos;
	}

	public void changeImage(int nImagem) {
		if (nImagem == 0) {
			img = mortyNormal;
		} else if (nImagem == 1) {
			img = mortyEat;
		}
	}

}