package br.ufla.simulator.simulation.population;

import br.ufla.simulator.actors.Actor;

public class Occurrence implements Comparable<Occurrence> {
	private final Class<? extends Actor> from;
	private final double probability;

	public Occurrence(Class<? extends Actor> from, double probability) {
		this.from = from;
		this.probability = probability;
	}

	public double getProbability() {
		return probability;
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
