package br.ufla.simulator.actors;

import java.util.List;

public interface Actor {
	void act(List<Actor> newAnimals);
	boolean isActive();
}
