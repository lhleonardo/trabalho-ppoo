package br.ufla.simulator.simulation;

import java.util.ArrayList;
import java.util.List;

import br.ufla.simulator.actors.Actor;
import br.ufla.simulator.influencers.seasons.Autumn;
import br.ufla.simulator.influencers.seasons.Season;
import br.ufla.simulator.simulation.population.Population;
import br.ufla.simulator.simulation.view.SimulatorView;

/**
 * Representação de uma simulação. Esta classe manipula todo o ciclo de vida da
 * simulação. Nela estão contidos os atores, manipulação inicial do campo e
 * controle da interface gráfica.
 * 
 * @author lhleo
 *
 */
public class Simulator {

	// valor padrão de largura da tela e, consequentemente, do tamanho do campo
	private static final int DEFAULT_WIDTH = 50;
	// valor padrão de profundidade da tela e, consequentemente, do tamanho do campo
	private static final int DEFAULT_DEPTH = 50;

	private int step;
	private Season currentSeason;
	private Population population;

	private Field field;
	private List<Actor> actors;

	private SimulatorView view;

	/**
	 * Construtor padrão que inicializa uma simulação com o tamanho de janela
	 * presente em {@value #DEFAULT_WIDTH} e {@value #DEFAULT_DEPTH}.
	 * 
	 * Inicialização conta com população aleatória dos atores presentes.
	 */
	public Simulator() {
		this(DEFAULT_DEPTH, DEFAULT_WIDTH);
	}

	/**
	 * Construtor de uma simulação, com dimensões de tamanho variável. Caso valores
	 * sejam inválidos, é utilizado os valores padrões das dimensões presentes em
	 * {@value #DEFAULT_WIDTH} e {@value #DEFAULT_DEPTH}.
	 * 
	 * @param depth - Profundidade
	 * @param width - Largura
	 */
	public Simulator(int depth, int width) {
		if (width <= 0 || depth <= 0) {
			System.out.println("The dimensions must be greater than zero.");
			System.out.println("Using default values.");
			depth = DEFAULT_DEPTH;
			width = DEFAULT_WIDTH;
		}

		this.field = new Field(depth, width);
		this.actors = new ArrayList<>();

		this.step = 0;
		this.currentSeason = new Autumn(this.actors, this.field);

		this.view = new SimulatorView(depth, width);
		this.population = new Population(field, actors);
		this.population.populate();
	}

	/**
	 * Realiza um único passo da simulação a partir do seu estado atual.
	 */
	public final void simulate() {
		this.simulate(1);
	}

	/**
	 * Simulação de um número finito de passos e exibe após sua finalização.
	 * Sequência de simulações influenciam na existência dos atores e na estação
	 * climática atual, juntamente com os eventos climáticos.
	 * 
	 * @param steps - quantidade de passos que deverá ser executados
	 */
	public final void simulate(int steps) {
		for (int i = 0; i < steps; i++) {
			this.executeStep();
		}

		this.view.showStatus(this.step, field);
	}

	/**
	 * Restaura os valores para simulação. Utilizado para reinicialização de
	 * simulação.
	 */
	public void reset() {
		this.field.clear();
		this.actors.clear();
		this.step = 1;

		this.view.showStatus(step, field);
	}

	/**
	 * Método que realiza o passo da simulação. A variável de etapas da simulação é
	 * manipulada e a estação do ano é prosseguida caso necessário.
	 * 
	 * @see Season
	 */
	private void executeStep() {
		this.step++;
		this.currentSeason.simulateOneStep();

		if (this.currentSeason.isEnd()) {
			this.currentSeason.reset();

			this.currentSeason = this.currentSeason.prepareToNextSeason();
		}
	}
}