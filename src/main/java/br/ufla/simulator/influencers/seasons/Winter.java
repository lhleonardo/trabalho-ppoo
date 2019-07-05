package br.ufla.simulator.influencers.seasons;

import java.util.Iterator;
import java.util.List;

import br.ufla.simulator.actors.Actor;
import br.ufla.simulator.actors.principal.Fox;
import br.ufla.simulator.actors.principal.Rabbit;
import br.ufla.simulator.simulation.Field;

/**
 * No Inverno as raposas precisam comer o dobro de coelhos para conseguir
 * sobreviver e os coelhos reproduzem 15% menos que o normal. Entretanto, a
 * quantidade de coelhos que irão nascer em uma reprodução no inverno é a
 * quantidade máxima de filhos que um coelho pode ter;
 * 
 * @author Guilherme Barbosa Ochikubo, Guilherme Henrique de Melo e Leonardo
 *         Henrique de Braz
 *
 */
public class Winter extends Season {

	public Winter(List<Actor> actors, Field field) {
		super(actors, field);
		Rabbit.setBreedingBuffer(0.85);
		Fox.setFoodBuffer(2);
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
		Rabbit.setBreedingBuffer(1);
		Fox.setFoodBuffer(1);
		return new Autumn(getActors(), getField());
	}

}
