package br.ufla.simulator.influencers.seasons;

import br.ufla.simulator.actors.Actor;
import br.ufla.simulator.actors.Fox;
import br.ufla.simulator.actors.Rabbit;
import br.ufla.simulator.simulation.Simulator;

public class Winter extends Season{

	public Winter(Simulator simulation) {
		super(simulation);
	}

	@Override
	public void onEnter() {
		Simulator winterSimulation = this.getSimulation();
		for (Actor animal : winterSimulation.getAnimals()) {
			if (animal instanceof Fox) {
				((Fox) animal).setPercentual(2);
			} else {
				((Rabbit) animal).setPercentual(0.85);
			}
		}
	}
	
	
	@Override
	public void onLeave() {
		Simulator winterSimulation = this.getSimulation();
		for (Actor animal : winterSimulation.getAnimals()) {
			if (animal instanceof Fox) {
				((Fox) animal).setPercentual(1);
			} else {
				((Rabbit) animal).setPercentual(1);
			}
		}
	}
}