package graph;

import processing.IProcessingApp;
import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;

public class TestSubPlot implements IProcessingApp {
	private SubPlot graphPlot, rectPlot, buttonPlot, imagePlot;

	private double[] graphWindow = { -3, 3, 0, 9 };
	private float[] graphViewport = { 0.2f, 0.7f, 0.6f, 0.25f };

	private double[] drawWindow = { 0, 1, 0, 1 };
	private float[] drawViewport = { 0.2f, 0.3f, 0.3f, 0.3f };
	private double[] myBox = { 0.1, 0.1, 0.8, 0.8 };

	private double[] buttonWindow = { 0, 1, 0, 1 };
	private float[] buttonViewport = { 0.52f, 0.55f, 0.05f, 0.05f };

	private double[] imageWindow = { 0, 5, 0, 5 };
	private float[] imageViewport = { 0.55f, 0.05f, 0.4f, 0.45f };

	private int hue1, hue2, hue;

	private PFont font;
	private PImage backgroundImg, img;

	@Override
	public void setup(PApplet p) {
		font = p.createFont("Arial Black", 12);
		p.textFont(font);

		hue1 = p.color(255, 0, 0);
		hue2 = p.color(0, 0, 255);
		hue = hue1;

		backgroundImg = p.loadImage("C:\\Users\\afafc\\eclipse-workspace\\MSSN2\\src\\Aulas\\graph\\imgs\\beach.jpg");
		img = p.loadImage("C:\\Users\\afafc\\eclipse-workspace\\MSSN2\\src\\Aulas\\graph\\imgs\\crab.png");
		img.resize(img.width / 8, img.height / 8);

		graphPlot = new SubPlot(graphWindow, graphViewport, p.width, p.height);
		rectPlot = new SubPlot(drawWindow, drawViewport, p.width, p.height);
		buttonPlot = new SubPlot(buttonWindow, buttonViewport, p.width, p.height);
		imagePlot = new SubPlot(imageWindow, imageViewport, p.width, p.height);

		float[] bb = graphPlot.getBoundingBox();
		p.rect(bb[0], bb[1], bb[2], bb[3]);
		drawGraph(p, graphPlot);

		bb = buttonPlot.getBoundingBox();
		p.rect(bb[0], bb[1], bb[2], bb[3]);

		bb = imagePlot.getBoundingBox();
		p.rect(bb[0], bb[1], bb[2], bb[3]);
		p.image(backgroundImg, bb[0], bb[1], bb[2], bb[3]);

		float c[] = buttonPlot.getPixelCoord(0.1, 0.4);
		p.fill(255, 0, 0);
		p.text("Button", c[0], c[1]);
	}

	@Override
	public void draw(PApplet p, float dt) {
		p.fill(205);
		float[] bb = rectPlot.getBoundingBox();
		p.rect(bb[0], bb[1], bb[2], bb[3]);

		float[] r = rectPlot.getBox(myBox);
		p.fill(hue);
		p.rect(r[0], r[1], r[2], r[3]);
	}

	@Override
	public void mousePressed(PApplet p) {
		if (imagePlot.isInside(p.mouseX, p.mouseY)) {
			float[] bb = imagePlot.getBoundingBox();
			p.image(backgroundImg, bb[0], bb[1], bb[2], bb[3]);
			p.image(img, p.mouseX, p.mouseY);
		}
		if (buttonPlot.isInside(p.mouseX, p.mouseY)) {
			hue = hue == hue1 ? hue2 : hue1;
		}
		if (graphPlot.isInside(p.mouseX, p.mouseY)) {
			double[] c = graphPlot.getWorldCoord(p.mouseX, p.mouseY);
			p.text(String.format("(%.2f : ", c[0]), p.mouseX, p.mouseY);
			p.text(String.format("%.2f)", c[1]), p.mouseX + 50, p.mouseY);
		}
	}

	@Override
	public void keyPressed(PApplet p) {
		myBox[0] += 0.01;
		myBox[1] += 0.01;
		myBox[2] -= 0.02;
		myBox[3] -= 0.02;
	}
	
	@Override
	public void mouseReleased(PApplet p) {
		// TODO Auto-generated method stub

	}

	private void drawGraph(PApplet p, SubPlot plot) {
		double[] w = plot.getWindow();
		int np = 100;
		double step = (w[1] - w[0]) / np;
		float[] oldc = plot.getPixelCoord(w[0], w[0] * w[0]);
		for (double x = w[0] + step; x <= w[1]; x += step) {
			double y = x * x;
			float[] c = plot.getPixelCoord(x, y);
			p.line(oldc[0], oldc[1], c[0], c[1]);
			oldc = c;
		}
	}

}
