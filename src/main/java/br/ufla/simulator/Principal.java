package br.ufla.simulator;

import br.ufla.simulator.simulation.Simulator;

public class Principal {
	public static void main(String[] args) throws InterruptedException {
		Simulator simulator = new Simulator(100, 100);
		while (true) {
			Thread.sleep(100);
			simulator.simulateOneStep();
		}
	}
}
