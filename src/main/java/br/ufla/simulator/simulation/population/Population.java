package br.ufla.simulator.simulation.population;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import br.ufla.simulator.actors.Actor;
import br.ufla.simulator.actors.principal.Fox;
import br.ufla.simulator.actors.principal.Rabbit;
import br.ufla.simulator.simulation.Field;
import br.ufla.simulator.simulation.Location;

/**
 * Classe responsável por gerar a população inicial do campo da simulação.
 * 
 * Valores para nascimento dos atores presentes em campo são definidos a partir
 * de sua taxa inicial de nascimento
 * 
 * @author lhleo
 *
 */
public class Population {
	// mapeamento de probabilidades para nascimento de atores.
	private static Map<Occurrence, Creator> probabilities;

	/**
	 * Criador de instancia de atores, presente no mapeamento de probabilidade
	 * 
	 * @author lhleo
	 *
	 */
	private static interface Creator {
		Actor create(Field f, Location l);
	}

	// definição de nascimentos e probabilidade de criação dos atores.
	static {
		probabilities = new HashMap<>();
		probabilities.put(new Occurrence(Fox.class, 0.02), (field, location) -> new Fox(field, location, true));
		probabilities.put(new Occurrence(Rabbit.class, 0.08), (field, location) -> new Rabbit(field, location, true));
	}

	private Field field;
	private List<Actor> actors;

	/**
	 * Construtor principal de inicialização, que recebe os valores já pre-definidos
	 * de campo e atores presentes da simulação. @see Simulator
	 * 
	 * @param field  campo mapeado com os atores, usado para sua localização
	 * @param actors lista de atores presentes na simulação. Lista vazia para
	 *               população
	 */
	public Population(Field field, List<Actor> actors) {
		this.field = field;
		this.actors = actors;
	}

	/**
	 * Método para criar aleatoriamente a partir de probabilidades, presente em
	 * {@link #probabilities}. Valores são definidos para o campo e adicionados na
	 * lista de atores existentes.
	 */
	public void populate() {
		Random rand = new Random();
		field.clear();

		List<Occurrence> occurrences = new LinkedList<>(Population.probabilities.keySet());
		Collections.sort(occurrences);

		for (int row = 0; row < field.getDepth(); row++) {
			for (int col = 0; col < field.getWidth(); col++) {
				boolean isCreated = false;
				double chance = rand.nextDouble();

				Iterator<Occurrence> iterador = occurrences.iterator();

				while (iterador.hasNext() && isCreated == false) {
					Occurrence current = iterador.next();

					if (chance <= current.getProbability()) {
						Actor animal = Population.probabilities.get(current).create(field, new Location(row, col));
						actors.add(animal);
						field.place(animal, row, col);
						isCreated = true;
					}
				}
			}
		}
		Collections.shuffle(actors);
	}

}
