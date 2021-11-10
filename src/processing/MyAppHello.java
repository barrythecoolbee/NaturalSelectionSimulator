package processing;

import processing.core.PApplet;

public class MyAppHello implements IProcessingApp {

	private int sw = 1;

	public void setup(PApplet p) {

	}

	public void draw(PApplet p, float dt) {

		p.background(255); // Aplica um background a cada frame, portanto "apaga" as formas que são criadas
							// pelo movimento do mouse, o que faz com que a forma pareça estar agarrada ao
							// mouse

		p.fill(255, 203, 219); // Preenche de uam determinada cor a forma
		p.stroke(255, 0, 0); // Aplica uma espessura de uma determinada cor na forma
		p.strokeWeight(sw); // Muda o diâmetro da espessura aplicada pelo stroke()

		p.circle(400, 300, 50); // Desenha um círculo e toma como argumentos as coordenadas e o diâmetro

		p.fill(0, 0, 255, 64); // Preenche de uma cor a forma (o útlima argumento é a transparência)
		p.rect(400, 300, 100, 100); // Desenha um retângulo e toma como argumentos as coordenadas e as medidas

		p.circle(p.mouseX, p.mouseY, 100); // Desenha um círculo e toma como argumentos as coordenadas do mouse e o
											// diâmetro

		System.out.println(1 / dt); // Frame Rate = 1/Tempo

	}

	@Override
	public void keyPressed(PApplet p) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(PApplet p) {
		// TODO Auto-generated method stub

		sw++;

	}

	@Override
	public void mouseReleased(PApplet p) {
		// TODO Auto-generated method stub

	}

}
