package br.ufla.simulator.influencers.seasons;

import java.util.Iterator;
import java.util.List;

import br.ufla.simulator.actors.Actor;
import br.ufla.simulator.actors.principal.Fox;
import br.ufla.simulator.actors.principal.Rabbit;
import br.ufla.simulator.simulation.Field;

public class Winter extends Season {

	public Winter(List<Actor> actors, Field field) {
		super(actors, field);
		Rabbit.setBreedingBuffer(0.85);
		Fox.setFoodBuffer(2);
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

		this.getActors().addAll(newActors);
	}

	@Override
	protected int getMaxDuration() {
		return 10;
	}

	@Override
	public Season prepareToNextSeason() {
		Rabbit.setBreedingBuffer(1);
		Fox.setFoodBuffer(1);
		return new Autumn(getActors(), getField());
	}

}
