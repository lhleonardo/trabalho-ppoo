package br.ufla.simulator.actors.principal;

import java.awt.Color;
import java.util.List;
import java.util.Random;

import br.ufla.simulator.actors.Actor;
import br.ufla.simulator.simulation.Field;
import br.ufla.simulator.simulation.Location;

/**
 * A simple model of a rabbit. Rabbits age, move, breed, and die.
 * 
 * @author David J. Barnes and Michael Kolling
 * @version 2002-04-11
 */
public class Rabbit extends Animal {
	private static final Random rand = new Random();
	private static double breedingBuffer = 1;

	/**
	 * Cria um novo coelho. O coelho pode ser criaco com a idade 0 ou com uma idade
	 * aleatorio
	 * 
	 * @param field     - Representação do campo de simulação
	 * @param location  - Localização da raposa dentro do campo de simulação
	 * @param randomAge If true, the rabbit will have a random age.
	 */
	public Rabbit(Field field, Location location, boolean randomAge) {
		super(field, location);
		if (randomAge) {
			this.setAge(rand.nextInt(getMaxAge()));
		}
	}

	/**
	 * Isso é o que o coelho faz quase tempo todo - eles corre . As vezes ele irá
	 * procriar, ou morrer por idade.
	 */
	@Override
	public void act(List<Actor> newRabbits) {
		incrementAge();
		Field f = this.getField();
		Location newLocation = null;
		if (this.isActive()) {
			int births = breed();
			for (int b = 0; b < births; b++) {
				Rabbit newRabbit = new Rabbit(f, this.getLocation(), false);
				newRabbits.add(newRabbit);
				Location loc = f.randomAdjacentLocation(this.getLocation());
				newRabbit.setLocation(loc);
				f.place(newRabbit, loc);
			}
			newLocation = f.freeAdjacentLocation(this.getLocation());
		}
		// Only transfer to the updated field if there was a free location
		if (getLocation() != null) {
			f.place(null, getLocation());
			setLocation(newLocation);
			if (newLocation != null) {
				f.place(this, newLocation);
			}
		}
	}

	@Override
	public int breed() {
		if (breedingBuffer == 0.85) {
			if (canBreed() && rand.nextDouble() <= getBreedingProbability()) {
				return getMaxLitterSize();
			} else {
				return 0;
			}
		}
		return super.breed();
	}

	@Override
	public boolean canBreed() {
		return this.getAge() >= getBreedingAge();
	}

	@Override
	public boolean isActive() {
		if (this.getWasHunted()) {
			return false;
		}

		if (this.getAge() > getMaxAge()) {
			return false;
		}

		if (this.getLocation() == null) {
			return false;
		}

		return true;
	}

	@Override
	public int getBreedingAge() {
		return 5;
	}

	@Override
	public int getMaxAge() {
		return 50;
	}

	@Override
	public double getBreedingProbability() {
		return 0.02 * breedingBuffer;
	}

	@Override
	public int getMaxLitterSize() {
		return 5;
	}

	@Override
	public Color getColorRepresentation() {
		return Color.orange;
	}

	public static void setBreedingBuffer(double p) {
		breedingBuffer = p;
	}

}
