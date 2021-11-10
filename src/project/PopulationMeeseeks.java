package project;

import java.util.ArrayList;

import graph.SubPlot;
import processing.core.PApplet;
import processing.core.PVector;

public class PopulationMeeseeks {
	private ArrayList<Meeseeks> meeseeks;

	public PopulationMeeseeks(PApplet p, SubPlot plt) {
		meeseeks = new ArrayList<Meeseeks>();
	}

	public void live(PopulationMonster population, float dt) {
		PVector f;
		ArrayList<Meeseeks> characterToRemove = new ArrayList<Meeseeks>();
		for (Meeseeks a : meeseeks) {
			f = a.discover(population);
			a.applyForce(f);
			a.move(dt);

			if (a.eat(population)) {
				characterToRemove.add(a);
			}

		}
		for (int i = 0; i < characterToRemove.size(); i++) {
			meeseeks.remove(characterToRemove.get(i));
		}
	}

	public void addMeeseeks(Meeseeks newMeeseeks) {
		meeseeks.add(newMeeseeks);
	}

	public void display(PApplet p, SubPlot plt) {
		for (Meeseeks a : meeseeks)
			a.display(p, plt);
	}

	public ArrayList<Meeseeks> getMeeseeks() {
		return meeseeks;
	}

}
