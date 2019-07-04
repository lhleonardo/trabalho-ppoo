package br.ufla.simulator.actors.events;

import java.awt.Color;

import br.ufla.simulator.simulation.Field;

/**
 * Evento natural que representa uma inundação em um determinado local do campo
 * de representação da simulação.
 * 
 * @author lhleo
 *
 */
public class Fire extends NaturalEvent {
	// tamanho máximo, em blocos, para cálculo da área do bloco de representação
	private static final int MAX_SIZE = 20;
	// duração máxima, em passos, que a inundação vai existir na simulação
	private static final int MAX_DURATION = 3;

	public Fire(Field field) {
		super(field);
	}

	@Override
	public Color getColorRepresentation() {
		return Color.red;
	}

	@Override
	public int getMaxSize() {
		return MAX_SIZE;
	}

	@Override
	public int getMaxDuration() {
		return MAX_DURATION;
	}

}
