package br.ufla.simulator.influencers.seasons;

import java.util.Iterator;

import java.util.List;

import br.ufla.simulator.actors.Actor;
import br.ufla.simulator.simulation.Field;

/**
 * Representação da estação outono na simulação, nessa estação nada acontece. É
 * uma estação utilizada como o equilibrio das transições
 * 
 * @author Guilherme Barbosa Ochikubo, Guilherme Henrique de Melo e Leonardo
 *         Henrique de Braz
 */
public class Autumn extends Season {

	public Autumn(List<Actor> actors, Field field) {
		super(actors, field);
	}

	@Override
	protected void execute(List<Actor> newActors) {
		// Faz com que todos os atores atuem
		for (Iterator<Actor> iter = this.getActors().iterator(); iter.hasNext();) {
			Actor animal = (Actor) iter.next();
			animal.act(newActors);
			if (!animal.isActive()) {
				iter.remove();
			}
		}
		// Adiciona novos animais recem gerados a lista de animais
		this.getActors().addAll(newActors);
	}

	@Override
	protected int getMaxDuration() {
		return 10;
	}

	@Override
	public Season prepareToNextSeason() {
		return new Summer(getActors(), getField());
	}

}
