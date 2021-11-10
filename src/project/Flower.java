package project;

import processing.core.PVector;

public class Flower {
	private PVector pos;
	private static float marginPercentage = 0.08f;

	public Flower(double[] window) {
		float margin = (float) (marginPercentage * (window[1] - window[0]));
		pos = rndPos(window, margin);
	}

	public PVector getPos() {
		return pos;
	}

	private PVector rndPos(double[] window, float margin) {
		float xmin = (float) (window[0] + margin);
		float xmax = (float) (window[1] - margin);
		float ymin = (float) (window[2] + margin);
		float ymax = (float) (window[3] - margin);
		float x = (float) (xmin + (xmax - xmin) * Math.random());
		float y = (float) (ymin + (ymax - ymin) * Math.random());
		return new PVector(x, y);
	}
}
