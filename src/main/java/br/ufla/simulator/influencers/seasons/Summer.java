package br.ufla.simulator.influencers.seasons;

import java.util.Iterator;

import java.util.List;

import br.ufla.simulator.actors.Actor;
import br.ufla.simulator.actors.events.Fire;
import br.ufla.simulator.simulation.Field;

/**
 * Representação da estação verão na simulação,no verão áreas aleatórias no mapa
 * sofrem com incêndios causados pela grande quantidade de calor. Esses
 * incêndios matam todos os animais presentes em seu interior. Por conta da
 * umidade presente no solo, os incêndios acontecem em dias intercalados e
 * possuem o tamanho máximo de 20 unidades de área;
 * 
 * @author Guilherme Barbosa Ochikubo, Guilherme Henrique de Melo e Leonardo
 *         Henrique de Braz
 */

public class Summer extends Season {

	private Fire oneFire;

	public Summer(List<Actor> actors, Field field) {
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
		// Adiciona o fogo no mapa
		if (oneFire == null) {
			oneFire = new Fire(this.getField());
		}

		if (!oneFire.isActive()) {
			oneFire = null;
		} else {
			oneFire.act(newActors);
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
		oneFire.clear();
		return new Spring(getActors(), getField());
	}

}
