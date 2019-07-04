package br.ufla.simulator.influencers.seasons;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import br.ufla.simulator.actors.Actor;
import br.ufla.simulator.actors.events.Flood;
import br.ufla.simulator.actors.principal.Hunter;
import br.ufla.simulator.actors.principal.Rabbit;
import br.ufla.simulator.simulation.Field;
import br.ufla.simulator.simulation.Location;

public class Spring extends Season {

	private static final int MAX_HUNTERS = 50;
	private Flood oneFlood;

	private List<Hunter> hunters;

	public Spring(List<Actor> actors, Field field) {
		super(actors, field);
		this.hunters = new ArrayList<>();
		Rabbit.setBreedingBuffer(1.25);
		populate();
	}

	private void populate() {
		for (int i = 0; i < MAX_HUNTERS; i++) {
			Random random = new Random();
			int maxWidth = this.getField().getWidth();
			int maxDepth = this.getField().getDepth();

			int x = random.nextInt(maxWidth);
			int y = random.nextInt(maxDepth);
			Location location = new Location(x, y);
			if (this.getField().getActorAt(location) == null) {
				Hunter hunter = new Hunter(this.getField(), location);
				hunters.add(hunter);
				this.getField().place(hunter, location);

			} else {
				location = this.getField().freeAdjacentLocation(location);
				if (location != null) {
					Hunter hunter = new Hunter(this.getField(), location);
					hunters.add(hunter);
					this.getField().place(hunter, location);
				}
			}
		}
		
		this.getActors().addAll(hunters);
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
	
	public void removeHunters() {
		for(Hunter hunter: hunters) {
			this.getField().place(null, hunter.getLocation());
		}
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
		this.getActors().removeAll(hunters);
		removeHunters();
		return new Winter(getActors(), getField());
	}

}
