package br.ufla.simulator.simulation;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import br.ufla.simulator.actors.Actor;
import br.ufla.simulator.actors.Fox;
import br.ufla.simulator.actors.Rabbit;
import br.ufla.simulator.simulation.view.SimulatorView;

public class Simulator {
	// The private static final variables represent
	// configuration information for the simulation.
	// The default width for the grid.
	private static final int DEFAULT_WIDTH = 50;
	// The default depth of the grid.
	private static final int DEFAULT_DEPTH = 50;

	private static Map<Occurrence, Creator> probabilities;

	static {
		probabilities = new HashMap<>();
		probabilities.put(new Occurrence(Fox.class, 0.02), (field, location) -> new Fox(field, location, true));
		probabilities.put(new Occurrence(Rabbit.class, 0.08), (field, location) -> new Rabbit(field, location, true));
	}

	private static interface Creator {
		Actor create(Field f, Location l);
	}

	private static class Occurrence {
		private final Class<? extends Actor> from;
		private final double probability;

		public Occurrence(Class<? extends Actor> from, double probability) {
			this.from = from;
			this.probability = probability;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((from == null) ? 0 : from.hashCode());
			long temp;
			temp = Double.doubleToLongBits(probability);
			result = prime * result + (int) (temp ^ (temp >>> 32));
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Occurrence other = (Occurrence) obj;
			if (from == null) {
				if (other.from != null)
					return false;
			} else if (!from.equals(other.from))
				return false;
			if (Double.doubleToLongBits(probability) != Double.doubleToLongBits(other.probability))
				return false;
			return true;
		}

	}

	// The list of animals in the field and new actors just will be created
	private List<Actor> actors;
	// The current state of the field.
	private Field field;
	// The current step of the simulation.
	private int step;
	// A graphical view of the simulation.
	private SimulatorView view;

	/**
	 * Construct a simulation field with default size.
	 */
	public Simulator() {
		this(DEFAULT_DEPTH, DEFAULT_WIDTH);
	}

	/**
	 * Create a simulation field with the given size.
	 * 
	 * @param depth Depth of the field. Must be greater than zero.
	 * @param width Width of the field. Must be greater than zero.
	 */
	public Simulator(int depth, int width) {
		if (width <= 0 || depth <= 0) {
			System.out.println("The dimensions must be greater than zero.");
			System.out.println("Using default values.");
			depth = DEFAULT_DEPTH;
			width = DEFAULT_WIDTH;
		}
		actors = new ArrayList<>();
		field = new Field(depth, width);

		// Create a view of the state of each location in the field.
		view = new SimulatorView(depth, width);
		view.setColor(Fox.class, Color.blue);
		view.setColor(Rabbit.class, Color.orange);

		// Setup a valid starting point.
		reset();
	}

	/**
	 * Run the simulation from its current state for a reasonably long period, e.g.
	 * 500 steps.
	 */
	public void runLongSimulation() {
		simulate(500);
	}

	/**
	 * Run the simulation from its current state for the given number of steps. Stop
	 * before the given number of steps if it ceases to be viable.
	 */
	public void simulate(int numSteps) {
		for (int step = 1; step <= numSteps && view.isViable(field); step++) {
			simulateOneStep();
		}
	}

	/**
	 * Run the simulation from its current state for a single step. Iterate over the
	 * whole field updating the state of each fox and rabbit.
	 */
	public void simulateOneStep() {
		step++;

		// let all animals act
		List<Actor> newActors = new LinkedList<>();
		for (Iterator<Actor> iter = actors.iterator(); iter.hasNext();) {
			Actor animal = (Actor) iter.next();
			animal.act(newActors);
			if (!animal.isActive()) {
				iter.remove();
			}
		}
		// add new born animals to the list of animals
		actors.addAll(newActors);

		// display the new field on screen
		view.showStatus(step, field);
	}

	/**
	 * Reset the simulation to a starting position.
	 */
	public void reset() {
		step = 0;
		actors.clear();
		field.clear();
		populate(field);

		// Show the starting state in the view.
		view.showStatus(step, field);
	}

	/**
	 * Populate the field with foxes and rabbits.
	 */
	private void populate(Field field) {
		Random rand = new Random();
		field.clear();

		Set<Occurrence> occurrences = Simulator.probabilities.keySet();
		for (int row = 0; row < field.getDepth(); row++) {
			for (int col = 0; col < field.getWidth(); col++) {
				boolean isCreated = false;
				double chance = rand.nextDouble();

				Iterator<Occurrence> iterador = occurrences.iterator();

				while (iterador.hasNext() && isCreated == false) {
					Occurrence current = iterador.next();

					if (chance <= current.probability) {
						actors.add(Simulator.probabilities.get(current).create(field, new Location(row, col)));
						isCreated = true;
					}
				}
				// else leave the location empty.
			}
		}
		Collections.shuffle(actors);
	}
}