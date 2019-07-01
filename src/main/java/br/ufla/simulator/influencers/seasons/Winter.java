package br.ufla.simulator.influencers.seasons;

import br.ufla.simulator.actors.Animal;
import br.ufla.simulator.actors.Fox;
import br.ufla.simulator.actors.Rabbit;
import br.ufla.simulator.influencers.Influencer;
import br.ufla.simulator.simulation.Field;
import br.ufla.simulator.simulation.Simulator;

public class Winter implements Influencer {

	@Override
	public void influence(Simulator simulation) {
		for(Animal animal: simulation.getAnimals()) {
			if(animal instanceof Fox) {
				((Fox)animal).setPercentual(2);
			}
			else{
				((Rabbit)animal).setPercentual(0.85);
			}
		}
	}
}