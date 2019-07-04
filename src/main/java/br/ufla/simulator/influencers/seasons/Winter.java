package br.ufla.simulator.influencers.seasons;

import java.util.Iterator;
import java.util.List;

import br.ufla.simulator.actors.Actor;
import br.ufla.simulator.actors.Fox;
import br.ufla.simulator.simulation.Field;
import br.ufla.simulator.actors.Rabbit;


public class Winter extends Season {
	

	public Winter(List<Actor> actors, Field field) {
		super(actors, field);
		Rabbit.setPercentual(0.85);
		Fox.setPercentual(2);
	}

	@Override
	protected void execute(List<Actor> newActors) {
		// let all animals act
		for (Iterator<Actor> iter = this.getActors().iterator(); iter.hasNext();) {
			Actor animal = (Actor) iter.next();
			animal.act(newActors);
			if (!animal.isActive()) {
				iter.remove();
			}
		}
		// add new born animals to the list of animals
		
		this.getActors().addAll(newActors);
	}

	@Override
	protected int getMaxDuration() {
		return 10;
	}

	@Override
	public Season prepareToNextSeason() {
		return new Winter(getActors(), getField());
	}


}
