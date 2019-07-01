package br.ufla.simulator.actors;

import java.util.List;

import br.ufla.simulator.simulation.Field;
import br.ufla.simulator.simulation.Location;

public class Hunter implements Actor {
	private Field field;
	private Location location;
	
	@Override
	public void act(List<Actor> newAnimals) {

		if (isActive()) {
			this.hunt();
		}
	}
	
	public void hunt() {
		Location newLocation = this.findFox(field,location);
		if (this.findFox(field,location) == null) {
			needMove();
		}else {
			location = newLocation;
		}
	}
	
	/*
	 * Caso o cacador necessite andar para encontrar sua caca,ele anda 1 posicao se
	 * a posicao desejada estiver livre
	 */
	public void needMove() {
		Location newLocation = field.moveToNearestFox(location);
		if (newLocation != null) {
			location = newLocation;
		}
	}
	
	public Location findFox(Field field,Location location) {
		Location newLocation = field.findActor(location, Fox.class,2);
		if (newLocation != null) {
			((Animal)field.getActorAt(newLocation)).setWasHunted();
			location = newLocation;
			return location;
		}
		return null;
	}

	@Override
	public boolean isActive() {
		return true;
	}
}
