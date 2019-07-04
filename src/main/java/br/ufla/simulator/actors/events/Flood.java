package br.ufla.simulator.actors.events;

import java.awt.Color;

import br.ufla.simulator.simulation.Field;

public class Flood extends NaturalEvent {

	private static final int MAX_SIZE = 20;
	private static final int MAX_DURATION = 3;

	public Flood(Field field) {
		super(field);
	}

	@Override
	public int getMaxSize() {
		return MAX_SIZE;
	}

	@Override
	public int getMaxDuration() {
		return MAX_DURATION;
	}

	@Override
	public Color getColorRepresentation() {
		return Color.blue;
	}

}
