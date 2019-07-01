package br.ufla.simulator;

import br.ufla.simulator.simulation.Simulator;

public class Principal {
	public static void main(String[] args) {
		Simulator simulator = new Simulator();
		simulator.simulate(100);

	}
}
