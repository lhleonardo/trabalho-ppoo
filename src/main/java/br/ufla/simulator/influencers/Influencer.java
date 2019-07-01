package br.ufla.simulator.influencers;

import br.ufla.simulator.simulation.Simulator;

public abstract class Influencer {
    private Simulator simulation;

    public Influencer (Simulator s) {
        this.simulation = s;
    }

    public Simulator getSimulation() {
        return this.simulation;
    }
}
