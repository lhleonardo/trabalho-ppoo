package br.ufla.simulator.actors;

import java.util.List;
import java.util.Random;

import br.ufla.simulator.simulation.Field;
import br.ufla.simulator.simulation.Location;

public abstract class Animal implements Actor {

	// idade do animal
	private int age;
	// localização que o animal se encontra
	private Location location;
	// campo de simulação atual
	private Field field;

	private static final Random rand = new Random();

	public Animal(Field f, Location l) {
		this.location = l;
		this.field = f;
		this.age = 0;
	}

	public int getAge() {
		return age;
	}

	protected void incrementAge() {
		this.age++;
	}

	protected void setAge(int age) {
		this.age = age;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public void setLocation(int row, int col) {
		this.location = new Location(row, col);
	}

	public Field getField() {
		return field;
	}

	/**
	 * Generate a number representing the number of births, if it can breed.
	 * 
	 * @return The number of births (may be zero).
	 */
	public int breed() {
		int births = 0;
		if (canBreed() && rand.nextDouble() <= getBreedingProbability()) {
			births = rand.nextInt(getMaxLitterSize()) + 1;
		}
		return births;
	}

	public abstract boolean canBreed();

	@Override
	public abstract void act(List<Animal> newAnimals);

	public abstract int getBreedingAge();

	public abstract int getMaxAge();

	public abstract double getBreedingProbability();

	public abstract int getMaxLitterSize();

}