package project;

import java.util.ArrayList;

import graph.SubPlot;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

public class StockOfCrystals {
	private PImage img;
	private ArrayList<Crystal> piecesOfFood;

	public StockOfCrystals(int num, double[] window, PApplet p) {
		img = p.loadImage("pictures/Crystals/Crystal2.png");
		piecesOfFood = new ArrayList<Crystal>();
		for (int i = 0; i < num; i++)
			piecesOfFood.add(new Crystal(window));
	}

	public void remove(Crystal piece) {
		piecesOfFood.remove(piece);
	}

	public void display(PApplet p, SubPlot plt) {
		p.pushStyle();
		for (Crystal piece : piecesOfFood) {
			PVector pf = piece.getPos();
			float[] pp = plt.getPixelCoord(pf.x, pf.y);
			img.resize(30, 30);
			p.image(img, pp[0], pp[1]);
		}
		p.popStyle();
	}

	public ArrayList<Crystal> getCrystals() {
		return piecesOfFood;
	}

}
