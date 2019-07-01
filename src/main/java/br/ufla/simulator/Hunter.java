package br.ufla.simulator;

import java.util.Iterator;
import java.util.List;

public class Hunter implements Actor {
	
	private Location location;
	
	@Override
	public void act(List<Actor> newAnimals) {
		
	}
	
	public Location hunt() {
		//caso exista alvos perto, caca o alvo.
	}
	
	/*
	 * Caso o cacador necessite andar para encontrar sua caca
	 */
	public boolean needMove() {
		//implementar procura de alvos
		return false;
	}
	
	public Location findFox(Field field,Location location) {
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
	
	public Location moveTo() {
		
	}
}
