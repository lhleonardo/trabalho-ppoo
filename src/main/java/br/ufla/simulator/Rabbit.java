package br.ufla.simulator;

import java.util.List;
import java.util.Random;

/**
 * A simple model of a rabbit. Rabbits age, move, breed, and die.
 * 
 * @author David J. Barnes and Michael Kolling
 * @version 2002-04-11
 */
public class Rabbit extends Animal {
	private static final Random rand = new Random();
	private boolean wasEaten;

	/**
	 * Create a new rabbit. A rabbit may be created with age zero (a new born) or
	 * with a random age.
	 * 
	 * @param randomAge If true, the rabbit will have a random age.
	 */
	public Rabbit(Field field, Location location, boolean randomAge) {
		super(field, location);
		if (randomAge) {
			this.setAge(rand.nextInt(getMaxAge()));
		}
	}

	/**
	 * This is what the rabbit does most of the time - it runs around. Sometimes it
	 * will breed or die of old age.
	 */
	@Override
	public void act(List<Animal> newRabbits) {
		incrementAge();
		Field f = this.getField();
		if (this.isAlive()) {
			int births = breed();
			for (int b = 0; b < births; b++) {
				Rabbit newRabbit = new Rabbit(f, this.getLocation(), false);
				newRabbits.add(newRabbit);
				Location loc = f.randomAdjacentLocation(this.getLocation());
				newRabbit.setLocation(loc);
				f.place(newRabbit, loc);
			}
			Location newLocation = f.freeAdjacentLocation(this.getLocation());
			// Only transfer to the updated field if there was a free location
			setLocation(newLocation);
			if (newLocation != null) {
				f.place(this, newLocation);
			}
		}
	}

	public void setEaten() {
		this.wasEaten = true;
	}

	@Override
	public boolean canBreed() {
		return this.getAge() >= getBreedingAge();
	}

	@Override
	public boolean isAlive() {
		if (this.wasEaten) {
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
		return 0.15;
	}

	@Override
	public int getMaxLitterSize() {
		return 5;
	}

}
