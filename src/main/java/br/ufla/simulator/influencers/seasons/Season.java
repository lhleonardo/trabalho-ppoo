package br.ufla.simulator.influencers.seasons;

import br.ufla.simulator.influencers.Influencer;
import br.ufla.simulator.simulation.Simulator;

public abstract class Season extends Influencer{

    public Season(Simulator simulation) {
        super(simulation);
    }
    public abstract void onEnter();
    public abstract void onLeave();
}
