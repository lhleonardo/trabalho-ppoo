package br.ufla.simulator.influencers.seasons;

import br.ufla.simulator.actors.Actor;
import br.ufla.simulator.actors.Fox;
import br.ufla.simulator.actors.Rabbit;
import br.ufla.simulator.simulation.Simulator;

public class Winter extends Season{

	public Winter(Simulator simulation) {
		super(simulation);
	}

	
	public void configurar(double x, double y) {
		Simulator winterSimulation = this.getSimulation();
		for (Actor animal : winterSimulation.getAnimals()) {
			if (animal instanceof Fox) {
				((Fox) animal).setPercentual(x);
			} else {
				((Rabbit) animal).setPercentual(y);
			}
		}
	}
	
	@Override
	public void onEnter() {
		configurar(2,0.85);
	}
	@Override
	public void onLeave() {
		configurar(1,1);
	}
}