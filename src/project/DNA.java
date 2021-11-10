package project;

public class DNA {
	protected float maxSpeed;
	protected float maxDistance;
	protected float deltaTPursuit;
	protected float maxAngle = (float) Math.PI;
	public float deltaTWander;
	public float deltaPhiWander;
	public float radiusWander;

	public DNA() {
		// physics
		maxSpeed = random(1f, 2f);
		maxDistance = random(0.7f, 1.2f);
		// pursuit behavior
		deltaTPursuit = 1f;
		// wander behavior
		deltaTWander = 0.5f;
		deltaPhiWander = (float) (Math.PI / 5);
		radiusWander = 5;
	}

	public DNA(DNA dna, boolean mutate) {
		this.maxSpeed = dna.maxSpeed;
		this.maxDistance = dna.maxDistance;
	}


	public static float random(float min, float max) {
		return (float) (min + (max - min) * Math.random());
	}

}
