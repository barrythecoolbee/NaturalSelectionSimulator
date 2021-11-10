package processing;

import processing.core.PApplet;
import project.EnvironmentApp;

/**
 * @author roger
 *
 */
/**
 * @author roger
 *
 */
public class ProcessingSetup extends PApplet {

	private static IProcessingApp app;
	private int lastUpdate;

	@Override
	public void settings() {
		size(1600, 800); // Tamanho da janela
	}

	@Override
	public void setup() {

		app.setup(this);
		lastUpdate = millis();

	}

	@Override
	public void draw() {

		int now = millis();
		float dt = (now - lastUpdate) / 1000f; // Faz a diferença dos tempos e, com a divisão por 1000f, faz a conversão
												// para float

		lastUpdate = now;

		app.draw(this, dt);

	}

	@Override
	public void keyPressed() {
		app.keyPressed(this);
	}

	@Override
	public void mousePressed() {
		app.mousePressed(this);
	}

	@Override
	public void mouseReleased() {
		app.mouseReleased(this);
	}

	public static void main(String[] args) {
		
		app = new EnvironmentApp();
		PApplet.main(ProcessingSetup.class);
	}

}
