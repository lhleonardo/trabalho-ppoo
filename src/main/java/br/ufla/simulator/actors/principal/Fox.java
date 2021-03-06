package br.ufla.simulator.actors.principal;

import java.awt.Color;
import java.util.List;
import java.util.Random;

import br.ufla.simulator.actors.Actor;
import br.ufla.simulator.simulation.Field;
import br.ufla.simulator.simulation.Location;

/**
 * Representação de uma raposa na simulação. Ela é capaz de andar e procurar
 * comida (coelhos).
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

	private static double foodBuffer;
	// The fox's food level, which is increased by eating rabbits.
	private int foodLevel;

	/**
	 * Cria a raposa. A raposa pode ser criaca com idade zero ou uma idade aleatoria
	 * 
	 * @param field     - Representação do campo de simulação
	 * @param location  - Localização da raposa dentro do campo de simulação
	 * @param randomAge - If true, the fox will have random age and hunger level.
	 */
	public Fox(Field field, Location location, boolean randomAge) {
		super(field, location);
		foodBuffer = 1;
		if (randomAge) {
			this.setAge(rand.nextInt(getMaxAge()));
			foodLevel = rand.nextInt(RABBIT_FOOD_VALUE);
		} else {
			// leave age at 0
			foodLevel = RABBIT_FOOD_VALUE;
		}
	}

	/**
	 * Isto é o que a raposa faz a maior parte do tempo: caça por coelhos. No
	 * processo, pode se reproduzir, morrer de fome ou morrer de velhice.
	 */
	@Override
	public void act(List<Actor> newFoxes) {
		incrementAge();
		incrementHunger();
		Field f = this.getField();
		Location newLocation = null;
		if (isActive()) {
			// New foxes are born into adjacent locations.
			int births = breed();
			for (int b = 0; b < births; b++) {
				Fox newFox = new Fox(f, this.getLocation(), false);
				newFoxes.add(newFox);
				Location loc = f.randomAdjacentLocation(this.getLocation());
				newFox.setLocation(loc);
				f.place(newFox, loc);
			}
			// Move towards the source of food if found.
			newLocation = findFood(f, this.getLocation());
			if (newLocation == null) { // no food found - move randomly
				newLocation = f.freeAdjacentLocation(this.getLocation());
			}

		}
		if (getLocation() != null) {
			f.place(null, getLocation());
			setLocation(newLocation);
			if (newLocation != null) {
				f.place(this, newLocation);
			}
		}

	}

	/**
	 * Faz com que a raposa fique com fome a cada iteração. Para ela não morrer de
	 * fome, é necessário encontrar comida.
	 */
	private void incrementHunger() {
		foodLevel -= foodBuffer;
	}

	/**
	 * Diga à raposa para procurar por coelhos adjacentes à sua localização atual.
	 * 
	 * @param field    The field in which it must look.
	 * @param location Where in the field it is located.
	 * @return Where food was found, or null if it wasn't.
	 */
	private Location findFood(Field field, Location location) {
		Location newLocation = field.findActor(location, Rabbit.class, 1);
		if (newLocation != null) {
			((Animal) field.getActorAt(newLocation)).setWasHunted();

			location = newLocation;
			this.foodLevel += RABBIT_FOOD_VALUE;
			return location;
		}
		return null;

	}

	@Override
	public boolean isActive() {
		if (this.getWasHunted()) {
			return false;
		}
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
		return 50;
	}

	@Override
	public double getBreedingProbability() {
		return 0.15;
	}

	@Override
	public int getMaxLitterSize() {
		return 3;
	}

	public static void setFoodBuffer(double p) {
		foodBuffer = p;
	}

	@Override
	public Color getColorRepresentation() {
		return Color.darkGray;
	}

}
