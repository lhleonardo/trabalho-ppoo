package br.ufla.simulator;

import br.ufla.simulator.simulation.Simulator;

public class Principal {
	public static void main(String[] args) throws InterruptedException {
		Simulator simulator = new Simulator(50, 50);
		while (true) {
			Thread.sleep(0);
			simulator.simulateOneStep();
		}
	}
}
