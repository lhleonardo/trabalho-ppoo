package br.ufla.simulator.influencers.seasons;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import br.ufla.simulator.actors.Actor;
import br.ufla.simulator.actors.events.Flood;
import br.ufla.simulator.actors.principal.Hunter;
import br.ufla.simulator.actors.principal.Rabbit;
import br.ufla.simulator.simulation.Field;

public class Spring extends Season {

	private Flood oneFlood;

	private List<Hunter> hunters;

	public Spring(List<Actor> actors, Field field) {
		super(actors, field);
		this.hunters = new ArrayList<>();
		Rabbit.setBreedingBuffer(1.25);
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
		if (oneFlood == null) {
			oneFlood = new Flood(this.getField());
		}

		if (!oneFlood.isActive()) {
			oneFlood = null;
		} else {
			oneFlood.act(newActors);
		}
		this.getActors().addAll(newActors);
	}

	@Override
	protected int getMaxDuration() {
		return 10;
	}

	@Override
	public Season prepareToNextSeason() {
		if (oneFlood != null)
			oneFlood.clear();

		Rabbit.setBreedingBuffer(1);
		return new Winter(getActors(), getField());
	}

}
