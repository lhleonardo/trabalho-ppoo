package br.ufla.simulator.influencers.seasons;

import java.util.List;

import br.ufla.simulator.actors.Actor;
import br.ufla.simulator.simulation.Field;

public class Autumn extends Season{

	public Autumn(List<Actor> actors, Field field) {
		super(actors, field);
	}

	@Override
	public void prepare() {
		
	}

	@Override
	protected void execute(List<Actor> newActors) {
		
	}

	@Override
	protected int getMaxDuration() {
		return 0;
	}

	@Override
	public Season getNextSeason() {
		return null;
	}

}
