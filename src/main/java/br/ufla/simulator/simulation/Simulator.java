package br.ufla.simulator.simulation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import br.ufla.simulator.actors.Actor;
import br.ufla.simulator.actors.principal.Fox;
import br.ufla.simulator.actors.principal.Hunter;
import br.ufla.simulator.actors.principal.Rabbit;
import br.ufla.simulator.influencers.seasons.Autumn;
import br.ufla.simulator.influencers.seasons.Season;
import br.ufla.simulator.simulation.view.SimulatorView;

public class Simulator {
	private static Map<Occurrence, Creator> probabilities;

	static {
		probabilities = new HashMap<>();
		probabilities.put(new Occurrence(Hunter.class, 0.001), (field, location) -> new Hunter(field, location));
		probabilities.put(new Occurrence(Fox.class, 0.02), (field, location) -> new Fox(field, location, true));
		probabilities.put(new Occurrence(Rabbit.class, 0.08), (field, location) -> new Rabbit(field, location, true));
	}
	// The private static final variables represent
	// configuration information for the simulation.
	// The default width for the grid.
	private static final int DEFAULT_WIDTH = 50;
	// The default depth of the grid.
	private static final int DEFAULT_DEPTH = 50;

	private int step;
	private Season currentSeason;

	private Field field;
	private List<Actor> actors;

	private SimulatorView view;

	public Simulator() {
		this(DEFAULT_DEPTH, DEFAULT_WIDTH);
	}

	public Simulator(int depth, int width) {
		if (width <= 0 || depth <= 0) {
			System.out.println("The dimensions must be greater than zero.");
			System.out.println("Using default values.");
			depth = DEFAULT_DEPTH;
			width = DEFAULT_WIDTH;
		}

		this.field = new Field(depth, width);
		this.actors = new ArrayList<>();

		this.step = 0;
		this.currentSeason = new Autumn(this.actors, this.field);

		this.view = new SimulatorView(depth, width);
		this.populate();
	}

	public final void simulate() {
		this.simulate(1);
	}

	public final void simulate(int steps) {
		for (int i = 0; i < steps; i++) {
			this.executeStep();
		}

		this.view.showStatus(this.step, field);
	}

	public void reset() {
		this.field.clear();
		this.actors.clear();

		this.view.showStatus(step, field);
	}

	private void executeStep() {
		this.step++;
		this.currentSeason.simulateOneStep();

		if (this.currentSeason.isEnd()) {
			this.currentSeason.reset();

			this.currentSeason = this.currentSeason.prepareToNextSeason();
		}
	}

	private static interface Creator {
		Actor create(Field f, Location l);
	}

	private static class Occurrence implements Comparable<Occurrence> {
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

		@Override
		public int compareTo(Occurrence o) {
			if (this.probability > o.probability) {
				return 1;
			}

			if (this.probability < o.probability) {
				return -1;
			}
			return 0;
		}

	}

	/**
	 * Populate the field with foxes and rabbits.
	 */
	private void populate() {
		Random rand = new Random();
		field.clear();

		List<Occurrence> occurrences = new LinkedList<>(Simulator.probabilities.keySet());
		Collections.sort(occurrences);

		for (int row = 0; row < field.getDepth(); row++) {
			for (int col = 0; col < field.getWidth(); col++) {
				boolean isCreated = false;
				double chance = rand.nextDouble();

				Iterator<Occurrence> iterador = occurrences.iterator();

				while (iterador.hasNext() && isCreated == false) {
					Occurrence current = iterador.next();

					if (chance <= current.probability) {
//						System.out.println("Um novo ator foi criado. Tipo: " + current.from.getName());

						Actor animal = Simulator.probabilities.get(current).create(field, new Location(row, col));
						actors.add(animal);
						field.place(animal, row, col);
						isCreated = true;
					}
				}
				// else leave the location empty.
			}
		}
		Collections.shuffle(actors);
	}
}