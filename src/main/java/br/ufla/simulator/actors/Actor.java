package br.ufla.simulator.actors;

import java.util.List;

public interface Actor {
	void act(List<Animal> newAnimals);
	boolean isActive();
}
