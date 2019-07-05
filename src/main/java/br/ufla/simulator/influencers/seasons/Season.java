package br.ufla.simulator.influencers.seasons;

import java.util.ArrayList;
import java.util.List;

import br.ufla.simulator.actors.Actor;
import br.ufla.simulator.simulation.Field;
import br.ufla.simulator.simulation.Simulator;

/**
 * Representação de uma estação do ano dentro da simulação. As estações são
 * definidas como interferências que ocorrem em curtos períodos de tempo dentro
 * da simulação.
 * 
 * Sua interferência é variada e seu acesso é total dentro da simulação.
 * 
 * @author Guilherme Barbosa Ochikubo, Guilherme Henrique de Melo e Leonardo Henrique de Braz
 *
 */
public abstract class Season {

	// dia atual da estação
	private int currentDay;
	// atores existentes na simulação
	private List<Actor> actors;
	// campo atual da simulação
	private Field field;

	/**
	 * Cria uma nova estação, recebendo os atores já existentes e o campo de
	 * simulação
	 * 
	 * @see Simulator
	 * @param actors - atores existentes na simulação
	 * @param field  - campo de representação da simulação
	 */
	public Season(List<Actor> actors, Field field) {
		this.currentDay = 1;
		this.actors = actors;
		this.field = field;
	}

	/**
	 * Verifica se a estação chegou ao final de seu período máximo de duração.
	 * 
	 * @return true se a estação estiver finalizada e false caso contrário
	 */
	public boolean isEnd() {
		return this.currentDay > this.getMaxDuration();
	}

	/**
	 * Simulação de um único dia dentro da estação. É definida pela sua execução
	 * ({@link #execute(List)} que manipulará as influências e ações dos atores
	 * presentes na simulação.
	 */
	public void simulateOneStep() {
		List<Actor> newActors = new ArrayList<>();
		this.execute(newActors);
		this.actors.addAll(newActors);

		this.currentDay++;
	}

	/**
	 * Obtem todos os atores presentes na simulação
	 * 
	 * @return lista de atores da simulação
	 */
	protected List<Actor> getActors() {
		return this.actors;
	}

	/**
	 * Obtem o campo de simulação em sua representação atual
	 * 
	 * @return campo de simulação
	 */
	protected Field getField() {
		return this.field;
	}

	/**
	 * Executa um passo (dia) de uma estação. Cada estação define como será a
	 * execução de seus passos diários
	 * 
	 * @param newActors - Novos atores que podem ser criados ao passar dos dias da
	 *                  estação
	 */
	protected abstract void execute(List<Actor> newActors);

	/**
	 * Define qual é a duração máxima que uma estação poderá durar. Essa duração é
	 * dada em dias corridos da própria estação
	 * 
	 * @return
	 */
	protected abstract int getMaxDuration();

	/**
	 * Preparação para trocar de estação. Realiza as restaurações de atributos
	 * definidos na estação e define qual é a próxima a ser processada. Cria uma
	 * lista circulada.
	 * 
	 * @return nova estação a ser executada
	 */
	public abstract Season prepareToNextSeason();

}
