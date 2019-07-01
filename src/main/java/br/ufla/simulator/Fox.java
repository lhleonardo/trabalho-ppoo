package br.ufla.simulator;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * A simple model of a fox. Foxes age, move, eat rabbits, and die.
 * 
 * @author David J. Barnes and Michael Kolling
 * @version 2002-04-11
 */
public class Fox extends Animal {

	// The food value of a single rabbit. In effect, this is the
	// number of steps a fox can go before it has to eat again.
	private static final int RABBIT_FOOD_VALUE = 4;
	// A shared random number generator to control breeding.
	private static final Random rand = new Random();

	// The fox's food level, which is increased by eating rabbits.
	private int foodLevel;

	/**
	 * Create a fox. A fox can be created as a new born (age zero and not hungry) or
	 * with random age.
	 * 
	 * @param randomAge If true, the fox will have random age and hunger level.
	 */
	public Fox(Field field, Location location, boolean randomAge) {
		super(field, location);
		if (randomAge) {
			this.setAge(rand.nextInt(getMaxAge()));
			foodLevel = rand.nextInt(RABBIT_FOOD_VALUE);
		} else {
			// leave age at 0
			foodLevel = RABBIT_FOOD_VALUE;
		}
	}

	/**
	 * This is what the fox does most of the time: it hunts for rabbits. In the
	 * process, it might breed, die of hunger, or die of old age.
	 */
	@Override
	public void act(List<Animal> newFoxes) {
		incrementAge();
		incrementHunger();
		if (isAlive()) {
			// New foxes are born into adjacent locations.
			int births = breed();
			Field f = this.getField();
			for (int b = 0; b < births; b++) {
				Fox newFox = new Fox(f, this.getLocation(), false);
				newFoxes.add(newFox);
				Location loc = f.randomAdjacentLocation(this.getLocation());
				newFox.setLocation(loc);
				f.place(newFox, loc);
			}
			// Move towards the source of food if found.
			Location newLocation = findFood(f, this.getLocation());
			if (newLocation == null) { // no food found - move randomly
				newLocation = f.freeAdjacentLocation(this.getLocation());
			}
			setLocation(newLocation);
			if (newLocation != null) {
				f.place(this, newLocation);
			}
		}
	}

	/**
	 * Make this fox more hungry. This could result in the fox's death.
	 */
	private void incrementHunger() {
		foodLevel--;
	}

	/**
	 * Tell the fox to look for rabbits adjacent to its current location.
	 * 
	 * @param field    The field in which it must look.
	 * @param location Where in the field it is located.
	 * @return Where food was found, or null if it wasn't.
	 */
	private Location findFood(Field field, Location location) {
		Iterator<?> adjacentLocations = field.adjacentLocations(location);
		while (adjacentLocations.hasNext()) {
			Location where = (Location) adjacentLocations.next();
			Object animal = field.getActorAt(where);
			if (animal instanceof Rabbit) {
				Rabbit rabbit = (Rabbit) animal;
				if (rabbit.isAlive()) {
					rabbit.setEaten();
					foodLevel = RABBIT_FOOD_VALUE;
					return where;
				}
			}
		}
		return null;
	}

	@Override
	public boolean isAlive() {
		if (foodLevel <= 0) {
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
	public boolean canBreed() {
		return this.getAge() >= getBreedingAge();
	}

	@Override
	public int getBreedingAge() {
		return 10;
	}

	@Override
	public int getMaxAge() {
		return 150;
	}

	@Override
	public double getBreedingProbability() {
		return 0.09;
	}

	@Override
	public int getMaxLitterSize() {
		return 3;
	}


}
