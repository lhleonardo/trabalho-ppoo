package br.ufla.simulator.simulation;

import java.util.HashMap;
import java.util.Iterator;

import br.ufla.simulator.actors.Actor;
import br.ufla.simulator.influencers.seasons.Season;

/**
 * This class collects and provides some statistical data on the state of a
 * field. It is flexible: it will create and maintain a counter for any class of
 * Actor that is found within the field.
 * 
 * @author David J. Barnes and Michael Kolling
 * @version 2002-04-23
 */
public class FieldStats {
	// Counters for each type of entity (fox, rabbit, etc.) in the simulation.
	private HashMap<Class<? extends Actor>, Counter> counters;
	// Whether the counters are currently up to date.
	private Season currentSeason;
	private boolean countsValid;

	/**
	 * Construct a field-statistics Actor.
	 */
	public FieldStats() {
		// Set up a collection for counters for each type of animal that
		// we might find
		counters = new HashMap<>();
		countsValid = true;
	}

	/**
	 * Obtains details about current population state
	 * 
	 * @param field - Field representation of the simulation
	 * @return A string describing what animals are in the field.
	 */
	public String getPopulationDetails(Field field) {
		StringBuffer buffer = new StringBuffer();
		if (!countsValid) {
			generateCounts(field);
		}
		Iterator<Class<? extends Actor>> keys = counters.keySet().iterator();
		while (keys.hasNext()) {
			Counter info = (Counter) counters.get(keys.next());
			buffer.append(info.getName());
			buffer.append(": ");
			buffer.append(info.getCount());
			buffer.append(' ');
		}
		buffer.append("Season: " + this.currentSeason.getClass().getSimpleName());
		return buffer.toString();
	}

	/**
	 * Invalidate the current set of statistics; reset all counts to zero.
	 */
	public void reset() {
		countsValid = false;
		Iterator<Class<? extends Actor>> keys = counters.keySet().iterator();
		while (keys.hasNext()) {
			Counter cnt = (Counter) counters.get(keys.next());
			cnt.reset();
		}
	}

	/**
	 * Set the current season
	 * 
	 * @param current - the season
	 */
	public void setCurrentSeason(Season current) {
		this.currentSeason = current;
	}

	/**
	 * Increment the count for one class of animal.
	 * 
	 * @param actorClass - ClassType of the current actor
	 */
	public void incrementCount(Class<? extends Actor> actorClass) {
		Counter cnt = (Counter) counters.get(actorClass);
		if (cnt == null) {
			// we do not have a counter for this species yet - create one
			cnt = new Counter(actorClass.getSimpleName());
			counters.put(actorClass, cnt);
		}
		cnt.increment();
	}

	/**
	 * Indicate that an animal count has been completed.
	 */
	public void countFinished() {
		countsValid = true;
	}

	/**
	 * Determine whether the simulation is still viable. I.e., should it continue to
	 * run.
	 * 
	 * @param field - Representation positional of the simulation
	 * @return true If there is more than one species alive.
	 */
	public boolean isViable(Field field) {
		// How many counts are non-zero.
		int nonZero = 0;
		if (!countsValid) {
			generateCounts(field);
		}
		Iterator<Class<? extends Actor>> keys = counters.keySet().iterator();
		while (keys.hasNext()) {
			Counter info = (Counter) counters.get(keys.next());
			if (info.getCount() > 0) {
				nonZero++;
			}
		}
		return nonZero > 1;
	}

	/**
	 * Generate counts of the number of foxes and rabbits. These are not kept up to
	 * date as foxes and rabbits are placed in the field, but only when a request is
	 * made for the information.
	 * 
	 * @param field - Representation positional of the simulation
	 */
	private void generateCounts(Field field) {
		reset();
		for (int row = 0; row < field.getDepth(); row++) {
			for (int col = 0; col < field.getWidth(); col++) {
				Actor animal = field.getActorAt(row, col);
				if (animal != null) {
					incrementCount(animal.getClass());
				}
			}
		}
		countsValid = true;
	}
}
