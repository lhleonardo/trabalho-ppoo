package br.ufla.simulator;

import br.ufla.simulator.simulation.Simulator;

/**
 * Classe de execução da simulação. Ponto de partida de execução da simulação
 * 
 * @author lhleo
 *
 */
public class Principal {
	public static void main(String[] args) throws InterruptedException {
		Simulator simulator = new Simulator(100, 100);
		while (true) {
			Thread.sleep(300);
			simulator.simulate();
		}
	}
}
