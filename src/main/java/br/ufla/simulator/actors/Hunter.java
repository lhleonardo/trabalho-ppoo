package br.ufla.simulator.actors;

import java.util.List;

import br.ufla.simulator.simulation.Field;
import br.ufla.simulator.simulation.Location;

public class Hunter implements Actor {
	private Field field;
	private Location location;
	
	public Hunter(Field field, Location location) {
		this.location = location;
		this.field = field;
	}
	
	@Override
	public void act(List<Actor> newAnimals) {
		if (isActive()) {
			Location newLocation = this.findFox(field,location);
			if (newLocation == null) {
				needMove();
			}
		}
	}
	/*
	 * Caso o cacador necessite andar para encontrar sua caca,ele anda 1 posicao se
	 * a posicao desejada estiver livre
	 */
	
	public void needMove() {
		Location newLocation = field.moveToNearestFox(location);
		if (newLocation != null) {
			field.place(null, this.location);
			field.place(this, newLocation);
			location = newLocation;
		}else {
			field.place(this, this.location);
		}
	}
	
	public Location findFox(Field field,Location location) {
		Location newLocation = field.findActor(location, Fox.class,2);
		if (newLocation != null) {
			((Animal)field.getActorAt(newLocation)).setWasHunted();
			field.place(null,this.location);
			field.place(this,newLocation);
			location = newLocation;
			return newLocation;
		}
		return null;
	}
	
	@Override
	public boolean isActive() {
		return true;
	}
}
