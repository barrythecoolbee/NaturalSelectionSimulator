package project;

import graph.SubPlot;
import processing.core.PApplet;
import processing.core.PVector;

public class Boid extends Mover {
	protected double[] window;
	protected DNA dna;
	private float phiWander;

	private final float margin = 0.5f;

	protected Boid(PVector pos, float mass, float radius, double[] window) {

		super(pos, new PVector(), mass, radius);
		this.window = window;
		dna = new DNA();

	}

	@Override
	public void move(float dt) {
		super.move(dt);
		if (pos.x < window[0] + margin)
			pos.x = (float) (window[0] + margin);
		if (pos.y < window[2] + margin)
			pos.y = (float) (window[2] + margin);
		if (pos.x > window[1] - margin)
			pos.x = (float) (window[1] - margin);
		if (pos.y > window[3] - margin)
			pos.y = (float) (window[3] - margin);
	}

	public boolean isInSight(PVector t, float maxDistance, float maxAngle) {
		PVector r = PVector.sub(t, pos);
		float d = r.mag();
		float angle = PVector.angleBetween(r, vel);
		return ((d > 0) && (d < maxDistance) && (angle < maxAngle));
	}

	public boolean isInSight(PVector t) {
		return isInSight(t, dna.maxDistance, dna.maxAngle);
	}

	public PVector pursuit(Boid b) {
		PVector dd = PVector.mult(b.vel, dna.deltaTPursuit);
		PVector target = PVector.add(b.pos, dd);
		return seek(target);
	}

	public PVector brake() {
		PVector vd = new PVector();
		return PVector.sub(vd, vel);
	}

	public PVector seek(PVector target) {
		PVector vd = PVector.sub(target, pos);
		vd.normalize().mult(dna.maxSpeed);
		return PVector.sub(vd, vel);
	}

	public PVector wander(PApplet p) {
		PVector center = pos.copy();
		center.add(PVector.mult(vel, dna.deltaTWander));
		phiWander += p.random(-dna.deltaPhiWander, dna.deltaPhiWander);
		PVector target = new PVector(dna.radiusWander * PApplet.cos(phiWander),
				dna.radiusWander * PApplet.sin(phiWander));
		target.add(center);
		return seek(target);
	}

	public PVector evade(Boid b) {
		return pursuit(b).mult(-1);
	}

	public void display(PApplet p, SubPlot plt) {
		float[] pp = plt.getPixelCoord(pos.x, pos.y);
		p.pushMatrix();
		p.translate(pp[0], pp[1]);
		p.rotate(-vel.heading());
		p.popMatrix();
	}
}
