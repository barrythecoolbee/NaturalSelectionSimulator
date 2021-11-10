package project;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import graph.SubPlot;
import processing.IProcessingApp;
import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;

public class EnvironmentApp implements IProcessingApp {
	private double[] window = { -5, 5, -5, 5 };
	private float[] viewport = { 0f, 0f, 0.75f, 1f };
	private PImage background, arvore, rick, title, morty, meeseeks, monster, flower, crystal, inicialMenu, gameOver,
			plus, minus;

	private SubPlot plt;
	private StockOfCrystals crystals;
	private StockOfFlowers flowers;
	private PopulationMorty populationMortys;
	private PopulationMonster populationMonsters;
	private PopulationMeeseeks populationMeeseeks;
	private float timerCrystal, crystalTimer, nNewCrystals, timerFlower, flowerTimer, nNewFlowers;
	private final int maxMortys = 10;
	private int painelWidth = 400;
	private PFont Fonte;
	private int ecra;
	private int populationMortySize;
	private int populationMeeseeksSize;
	private int populationMonsterSize;
	private int stockOfCrystalSize;
	private int stockOfFlowerSize;
	private AudioInputStream audioEcra0, audioEcra1, audioEcra2;
	private Clip clipEcra0, clipEcra1, clipEcra2;
	private final int countLoop = 999999999;

	private int mortysCount, monstersCount, crystalsCount, flowersCount;
	private boolean playEcra0, playEcra1, playEcra2;

	@Override
	public void setup(PApplet p) {
		plt = new SubPlot(window, viewport, p.width, p.height);
		ecra = 0;
		background = p.loadImage("pictures/background.jpg");
		arvore = p.loadImage("pictures/arvore.png");
		rick = p.loadImage("pictures/Rick/Rickhappy.png");
		title = p.loadImage("pictures/rick_morty.png");
		morty = p.loadImage("pictures/Morty/morty_normal.png");
		meeseeks = p.loadImage("pictures/MRMeeseeks/MrM.png");
		monster = p.loadImage("pictures/Monsters/scary_terry.png");
		flower = p.loadImage("pictures/Flowers/Flower4.png");
		crystal = p.loadImage("pictures/Crystals/Crystal2.png");
		inicialMenu = p.loadImage("pictures/inicial_image.jpg");
		gameOver = p.loadImage("pictures/game_over.jpg");
		plus = p.loadImage("pictures/symbols/plusCartoon.png");
		minus = p.loadImage("pictures/symbols/minusCartoon.png");

		populationMortys = new PopulationMorty(p, plt);

		timerCrystal = 0;
		crystalTimer = PopulationModel.secondsToNewCrystals;
		nNewCrystals = PopulationModel.crystalsEachSecs;

		populationMonsters = new PopulationMonster(p, plt);

		timerFlower = 0;
		flowerTimer = PopulationModel.secondsToNewFlowers;
		nNewFlowers = PopulationModel.flowersEachSecs;

		populationMeeseeks = new PopulationMeeseeks(p, plt);

		String FonteName = "Yu Gothic Bold";
		Fonte = p.createFont(FonteName, 32, true);

		mortysCount = 5;
		monstersCount = 5;
		crystalsCount = 5;
		flowersCount = 5;
		playEcra0 = true;
		playEcra1 = true;
		playEcra2 = true;

	}

	@Override
	public void draw(PApplet p, float dt) {
		int minusXYsize = 60;
		if (ecra == 0) {
			p.fill(50);
			float[] pp = plt.getBoundingBox();
			p.rect(pp[0], pp[1], 1600f, pp[3]);

			if (playEcra0) {
				try {
					audioEcra0 = AudioSystem.getAudioInputStream(this.getClass().getResource("inicial_song.wav"));
					clipEcra0 = AudioSystem.getClip();
					clipEcra0.open(audioEcra0);
					clipEcra0.loop(countLoop);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
			playEcra0 = false;

			p.textFont(Fonte);
			p.textSize(40);

			inicialMenu.resize(1600, 800);
			p.image(inicialMenu, 0, 0);

			// MORTY COUNT
			minus.resize(minusXYsize, minusXYsize);
			p.image(minus, 1145, 495);

			morty.resize(60, 60);
			p.image(morty, 1215, 430);

			if (mortysCount < 10) {
				strokeTextMenu(String.valueOf(mortysCount), 1230, 540, p);
			}
			if (mortysCount == 10) {
				strokeTextMenu(String.valueOf(mortysCount), 1224, 540, p);
			}

			plus.resize(minusXYsize, minusXYsize);
			p.image(plus, 1285, 495);

			// MONSTER COUNT
			minus.resize(minusXYsize, minusXYsize);
			p.image(minus, 1385, 495);

			monster.resize(75, 75);
			p.image(monster, 1450, 422);

			if (monstersCount < 10) {
				strokeTextMenu(String.valueOf(monstersCount), 1474, 540, p);
			}
			if (monstersCount >= 10 && monstersCount <= 99) {
				strokeTextMenu(String.valueOf(monstersCount), 1462, 540, p);
			}
			if (monstersCount >= 100) {
				strokeTextMenu(String.valueOf(monstersCount), 1452, 540, p);
			}

			plus.resize(minusXYsize, minusXYsize);
			p.image(plus, 1525, 495);

			// CRYSTALS COUNT
			minus.resize(minusXYsize, minusXYsize);
			p.image(minus, 1152, 675);

			crystal.resize(60, 60);
			p.image(crystal, 1220, 610);

			if (crystalsCount < 10) {
				strokeTextMenu(String.valueOf(crystalsCount), 1240, 720, p);

			}
			if (crystalsCount >= 10 && crystalsCount <= 99) {
				strokeTextMenu(String.valueOf(crystalsCount), 1230, 720, p);

			}
			if (crystalsCount >= 100) {
				strokeTextMenu(String.valueOf(crystalsCount), 1217, 720, p);

			}

			plus.resize(minusXYsize, minusXYsize);
			p.image(plus, 1288, 675);

			// FLOWERS COUNT
			minus.resize(minusXYsize, minusXYsize);
			p.image(minus, 1385, 675);

			flower.resize(65, 70);
			p.image(flower, 1450, 602);

			if (flowersCount < 10) {
				strokeTextMenu(String.valueOf(flowersCount), 1473, 720, p);
			}
			if (flowersCount >= 10 && flowersCount <= 99) {
				strokeTextMenu(String.valueOf(flowersCount), 1460, 720, p);
			}
			if (flowersCount >= 100) {
				strokeTextMenu(String.valueOf(flowersCount), 1450, 720, p);
			}

			plus.resize(minusXYsize, minusXYsize);
			p.image(plus, 1525, 675);

			// Instructions
			strokeTextMenu("PRESS 'P' TO PLAY", 610, 405, p);

			p.textSize(30);
			strokeTextMenu("CHOOSE", 1300, 405, p);
		}

		if (ecra == 1) {

			p.noStroke();

			float[] pp = plt.getBoundingBox();
			p.rect(pp[0], pp[1], pp[2], pp[3]);
			p.fill(50);

			if (playEcra1) {
				try {

					audioEcra1 = AudioSystem.getAudioInputStream(this.getClass().getResource("forestSound.wav"));
					clipEcra1 = AudioSystem.getClip();
					clipEcra1.open(audioEcra1);
					clipEcra1.loop(countLoop);
				} catch (Exception ex) {
					ex.printStackTrace();
				}

			}
			playEcra1 = false;
			background.resize(1600, 800);
			p.image(background, 0, 0);

			populationMortys.live(crystals, populationMeeseeks, dt);
			populationMortys.display(p, plt);

			populationMonsters.live(flowers, populationMortys, dt);
			populationMonsters.display(p, plt);

			populationMeeseeks.live(populationMonsters, dt);
			populationMeeseeks.display(p, plt);

			timerCrystal += dt;
			timerFlower += dt;

			if (timerCrystal < crystalTimer) {
				crystals.display(p, plt);

			} else {
				timerCrystal = 0;

				for (int x = 0; x < nNewCrystals; x++) {
					crystals.getCrystals().add(new Crystal(window));
				}

				crystals.display(p, plt);

			}

			if (timerFlower < flowerTimer) {
				flowers.display(p, plt);

			} else {
				timerFlower = 0;

				for (int x = 0; x < nNewFlowers; x++) {
					flowers.getFlowers().add(new Flower(window));
				}

				flowers.display(p, plt);

			}

			colocaArvores(p);
			colocaContador(p);
			colocaInstrucoes(p);

			title.resize(420, 136);
			p.image(title, 1180, -2);

			rick.resize(105, 125);
			p.image(rick, 1315, 80);

			if (populationMonsters.getMonsters().size() == 0) {
				clipEcra1.stop();
				clipEcra1.close();
				ecra = 2;
				clearAll();
			}
		}

		if (ecra == 2) {

			p.fill(50);
			float[] pp = plt.getBoundingBox();
			p.rect(pp[0], pp[1], 1600f, pp[3]);

			if (playEcra2) {
				try {

					audioEcra2 = AudioSystem.getAudioInputStream(this.getClass().getResource("gameOver_song.wav"));
					clipEcra2 = AudioSystem.getClip();
					clipEcra2.open(audioEcra2);
					clipEcra2.loop(countLoop);

				} catch (Exception ex) {
					ex.printStackTrace();
				}

			}
			playEcra2 = false;

			gameOver.resize(1600, 800);
			p.image(gameOver, 0, 0);

			p.textFont(Fonte);
			p.textSize(40);
			strokeTextMenu("ALL MONSTERS HAVE DIED", 520, 70, p);
			strokeTextMenu("PRESS 'R' TO RESTART", 580, 760, p);
		}
	}

	public void clearAll() {
		playEcra0 = true;
		playEcra1 = true;
		playEcra2 = true;

		mortysCount = 5;
		monstersCount = 5;
		crystalsCount = 5;
		flowersCount = 5;

		populationMortys.getMortys().clear();
		populationMeeseeks.getMeeseeks().clear();
		populationMonsters.getMonsters().clear();
		crystals.getCrystals().clear();
		flowers.getFlowers().clear();

	}

	public void startAll(int nMortys, int nMonsters, int nCrystals, int nFlowers, PApplet p) {

		for (int a = 0; a < nMortys; a++) {
			populationMortys.addMorty();
		}

		for (int b = 0; b < nMonsters; b++) {
			populationMonsters.addMonster();
		}

		crystals = new StockOfCrystals(nCrystals, window, p);
		flowers = new StockOfFlowers(nFlowers, window, p);
	}

	private void colocaInstrucoes(PApplet p) {
		p.textFont(Fonte);
		int posxText = 1172;

		p.textSize(32);
		strokeText("INSTRUCTIONS:", posxText, 510, p);
		p.textSize(13.2f);

		strokeText("-You can click 'R' to RESTART;", posxText, 540, p);
		strokeText(
				"-You are RICK, a genious cientist who creates universes for fun, \nclick 'M' to generate a maximum of 10 MORTYs;",
				posxText, 570, p);
		strokeText("-MORTYS are fragile and can be eaten by MONSTERS;", posxText, 620, p);
		strokeText("-MORTYS collect two crystals and generate one MR. MEESEEKS;", posxText, 650, p);
		strokeText("-MONSTERS, despite loving eating MORTYS, they have a \ncertain taste for FLOWERS too;", posxText,
				680, p);
		strokeText("-MONSTERS can reproduce each other as they eat three flowers;", posxText, 730, p);
		strokeText("-MR. MEESEEKS live so they can only serve the purpose of their \nmeaning, eating one MONSTER;",
				posxText, 760, p);
	}

	public void colocaContador(PApplet p) {
		int posy1 = 305;
		int posy2 = 430;
		int posyImg = 220;
		int posyImg2 = 350;
		populationMortySize = populationMortys.getMortys().size();
		populationMeeseeksSize = populationMeeseeks.getMeeseeks().size();
		populationMonsterSize = populationMonsters.getMonsters().size();
		stockOfCrystalSize = crystals.getCrystals().size();
		stockOfFlowerSize = flowers.getFlowers().size();

		p.textFont(Fonte);
		p.fill(0);

		morty.resize(50, 50);
		p.image(morty, 1255, posyImg);

		meeseeks.resize(50, 50);
		p.image(meeseeks, 1355, posyImg);

		monster.resize(60, 60);
		p.image(monster, 1450, posyImg - 3);

		crystal.resize(42, 42);
		p.image(crystal, 1305, posyImg2);

		flower.resize(50, 45);
		p.image(flower, 1405, posyImg2 + 2);

		p.textSize(32);
		if (populationMortySize < 10) {
			strokeText(String.valueOf(populationMortySize), 1270, posy1, p);
		} else {
			strokeText(String.valueOf(populationMortySize), 1260, posy1, p);
		}
		if (populationMeeseeksSize < 10) {
			strokeText(String.valueOf(populationMeeseeksSize), 1370, posy1, p);
		} else {
			strokeText(String.valueOf(populationMeeseeksSize), 1360, posy1, p);
		}
		if (populationMonsterSize < 10) {
			strokeText(String.valueOf(populationMonsterSize), 1470, posy1, p);
		} else {
			strokeText(String.valueOf(populationMonsterSize), 1460, posy1, p);
		}
		if (stockOfCrystalSize < 10) {
			strokeText(String.valueOf(stockOfCrystalSize), 1320, posy2, p);
		} else {
			strokeText(String.valueOf(stockOfCrystalSize), 1310, posy2, p);
		}
		if (stockOfFlowerSize < 10) {
			strokeText(String.valueOf(stockOfFlowerSize), 1420, posy2, p);
		} else {
			strokeText(String.valueOf(stockOfFlowerSize), 1410, posy2, p);
		}
	}

	public void colocaArvores(PApplet p) {
		arvore.resize(50, 50);

		for (int x = 0; x < (1600 / 30); x++) {
			p.image(arvore, x * 30, -20);
			p.image(arvore, x * 30, 770);
		}

		for (int y = 0; y < (800 / 25); y++) {
			p.image(arvore, -10, y * 25);
			p.image(arvore, 1465, y * 25);

			for (int j = 0; j < (painelWidth / 30) + 2; j++) {
				p.image(arvore, 1160 + (30 * j), y * 25);
			}

		}
	}

	void strokeText(String message, int x, int y, PApplet p) {
		p.fill(0);
		p.text(message, x - 1, y);
		p.text(message, x, y - 1);
		p.text(message, x + 1, y);
		p.text(message, x, y + 1);

		p.fill(60, 190, 255);
		p.text(message, x, y);
	}

	void strokeTextMenu(String message, int x, int y, PApplet p) {
		p.fill(99, 33, 13);
		p.text(message, x - 2, y);
		p.text(message, x, y - 2);
		p.text(message, x + 2, y);
		p.text(message, x, y + 2);

		p.fill(204, 198, 80);
		p.text(message, x, y);
	}

	@Override
	public void keyPressed(PApplet p) {
		if (p.key == 'm' || p.key == 'M') {
			if (ecra == 1) {
				if (populationMortys.getMortys().size() < maxMortys) {
					populationMortys.addMorty();
					try {
						AudioInputStream audioInputStream = AudioSystem
								.getAudioInputStream(this.getClass().getResource("oh_jeez.wav"));
						Clip clip = AudioSystem.getClip();
						clip.open(audioInputStream);
						clip.start();
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			}
		}

		if (p.key == 'p' || p.key == 'P') {
			if (ecra == 0) {
				startAll(mortysCount, monstersCount, crystalsCount, flowersCount, p);
				ecra = 1;
				clipEcra0.stop();
				clipEcra0.close();
			}

		}

		if (p.key == 'r' || p.key == 'R') {

			if (ecra == 1) {
				clipEcra1.stop();
				clipEcra1.close();
				ecra = 0;
				clearAll();

			} else if (ecra == 2) {
				clipEcra2.stop();
				clipEcra2.close();
				ecra = 0;
				clearAll();
			}
		}

	}

	@Override
	public void mouseReleased(PApplet p) {

	}

	@Override
	public void mousePressed(PApplet p) {
		contadoresMenuInicial(p);

	}

	public void contadoresMenuInicial(PApplet p) {

		// MORTY
		if ((p.mouseX >= 1158) && (p.mouseX <= 1189) && (p.mouseY >= 513) && (p.mouseY <= 530)) {
			mortysCount--;
			if (mortysCount < 0) {
				mortysCount = 0;
			}
		}
		if ((p.mouseX >= 1297) && (p.mouseX <= 1330) && (p.mouseY >= 504) && (p.mouseY <= 539)) {
			mortysCount++;
			if (mortysCount > 10) {
				mortysCount = 10;
			}
		}

		// MONSTER
		if ((p.mouseX >= 1398) && (p.mouseX <= 1430) && (p.mouseY >= 512) && (p.mouseY <= 528)) {
			monstersCount--;
			if (monstersCount < 1) {
				monstersCount = 1;
			}
		}
		if ((p.mouseX >= 1538) && (p.mouseX <= 1569) && (p.mouseY >= 505) && (p.mouseY <= 538)) {
			monstersCount++;
			if (monstersCount > 999) {
				monstersCount = 999;
			}
		}

		// CRYSTAL
		if ((p.mouseX >= 1165) && (p.mouseX <= 1196) && (p.mouseY >= 693) && (p.mouseY <= 707)) {
			crystalsCount--;
			if (crystalsCount < 1) {
				crystalsCount = 1;
			}
		}
		if ((p.mouseX >= 1300) && (p.mouseX <= 1332) && (p.mouseY >= 685) && (p.mouseY <= 718)) {
			crystalsCount++;
			if (crystalsCount > 999) {
				crystalsCount = 999;
			}
		}

		// FLOWER
		if ((p.mouseX >= 1399) && (p.mouseX <= 1430) && (p.mouseY >= 693) && (p.mouseY <= 708)) {
			flowersCount--;
			if (flowersCount < 1) {
				flowersCount = 1;
			}
		}
		if ((p.mouseX >= 1535) && (p.mouseX <= 1570) && (p.mouseY >= 686) && (p.mouseY <= 716)) {
			flowersCount++;
			if (flowersCount > 999) {
				flowersCount = 999;
			}
		}
	}

}
