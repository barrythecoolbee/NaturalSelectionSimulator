package project;

import java.util.ArrayList;

import graph.SubPlot;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

public class StockOfFlowers {
	private PImage img;
	private ArrayList<Flower> flowers;

	public StockOfFlowers(int num, double[] window, PApplet p) {
		img = p.loadImage("pictures/Flowers/Flower4.png");
		flowers = new ArrayList<Flower>();
		for (int i = 0; i < num; i++)
			flowers.add(new Flower(window));
	}

	public void remove(Flower piece) {
		flowers.remove(piece);
	}

	public void display(PApplet p, SubPlot plt) {
		p.pushStyle();
		for (Flower flower : flowers) {
			PVector pf = flower.getPos();
			float[] pp = plt.getPixelCoord(pf.x, pf.y);
			img.resize(35, 30);
			p.image(img, pp[0], pp[1]);
		}
		p.popStyle();
	}

	public ArrayList<Flower> getFlowers() {
		return flowers;
	}

}
