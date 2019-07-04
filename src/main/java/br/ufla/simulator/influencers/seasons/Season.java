package br.ufla.simulator.influencers.seasons;

import java.util.ArrayList;
import java.util.List;

import br.ufla.simulator.actors.Actor;
import br.ufla.simulator.simulation.Field;

public abstract class Season {

	private int currentDay;
	private List<Actor> actors;
	private Field field;

	public Season(List<Actor> actors, Field field) {
		this.currentDay = 1;
		this.actors = actors;
		this.field = field;
	}

	public boolean isEnd() {
		return this.currentDay > this.getMaxDuration();
	}

	public void simulateOneStep() {
		List<Actor> newActors = new ArrayList<>();
		this.execute(newActors);
		this.actors.addAll(newActors);
		
		this.currentDay++;
	}
	
	public void reset() {
		
	}
	
	protected List<Actor> getActors() {
		return this.actors;
	}
	
	protected Field getField() {
		return this.field;
	}
	
	protected abstract void execute(List<Actor> newActors);
	protected abstract int getMaxDuration();
	public abstract Season prepareToNextSeason();
	
	
}
