package br.ufla.simulator.simulation.population;

import br.ufla.simulator.actors.Actor;

/**
 * Ocorrência de ser criado um animal na simulação. Utilizado na construção do
 * Population para iniciar o campo de simulação
 * 
 * @see Population
 * @author Guilherme Barbosa Ochikubo, Guilherme Henrique de Melo e Leonardo Henrique de Braz
 *
 */
class Occurrence implements Comparable<Occurrence> {
	private final Class<? extends Actor> from;
	private final double probability;

	/**
	 * Cria uma nova representação. Esta inclui qual o ator será criado e a
	 * probabilidade de seu nascimento.
	 * 
	 * @param from        - Ator que será criado
	 * @param probability - Probabilidade deste ator ser criado
	 */
	public Occurrence(Class<? extends Actor> from, double probability) {
		this.from = from;
		this.probability = probability;
	}

	/**
	 * Obter a probabilidade do ator ser criado no início da simulação
	 * 
	 * @return probabilidade de criação
	 */
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
