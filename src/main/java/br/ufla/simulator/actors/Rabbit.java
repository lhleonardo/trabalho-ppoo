package br.ufla.simulator.actors;

import java.util.List;
import java.util.Random;

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
	private static double percentual;

	/**
	 * Create a new rabbit. A rabbit may be created with age zero (a new born) or
	 * with a random age.
	 * 
	 * @param randomAge If true, the rabbit will have a random age.
	 */
	public Rabbit(Field field, Location location, boolean randomAge) {
		super(field, location);
		this.percentual=1;
		if (randomAge) {
			this.setAge(rand.nextInt(getMaxAge()));
		}
	}

	/**
	 * This is what the rabbit does most of the time - it runs around. Sometimes it
	 * will breed or die of old age.
	 */
	@Override
	public void act(List<Actor> newRabbits) {
		incrementAge();
		Field f = this.getField();
		if (this.isActive()) {
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

	public void setWasHunted() {
		this.setWasHunted();
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
		return 0.15*this.getPercentual();
	}

	@Override
	public int getMaxLitterSize() {
		return 5;
	}
	public void setPercentual(double p) {
        this.percentual = p;
    }
	
	public double getPercentual() {
		return this.percentual;
	}
	public int breed() {
	    if (this.getBreedingProbability() == 0.1275) {
	        return getMaxLitterSize();
	    }
	    return super.breed();
	}


}
