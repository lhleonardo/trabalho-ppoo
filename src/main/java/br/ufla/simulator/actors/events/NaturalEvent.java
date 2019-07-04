package br.ufla.simulator.actors.events;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import br.ufla.simulator.actors.Actor;
import br.ufla.simulator.actors.principal.Animal;
import br.ufla.simulator.simulation.Field;
import br.ufla.simulator.simulation.Location;

public abstract class NaturalEvent implements Actor {

	private Field field;
	private boolean executed;
	private ArrayList<Location> locations;
	private int duration;

	private static Random random = new Random();

	public NaturalEvent(Field field) {
		this.field = field;
		this.executed = false;
		this.locations = new ArrayList<Location>();

		this.duration = 1;
	}

	@Override
	public void act(List<Actor> newAnimals) {
		if (!executed) {
			// precisa colocar em vários locais o fogo

			int maxWidth = this.field.getWidth();
			int maxDepth = this.field.getDepth();

			int x = random.nextInt(maxWidth);
			int y = random.nextInt(maxDepth);

			this.createBlock(new Location(x, y), random.nextInt(this.getMaxSize()));
			this.executed = true;
		} else {
			if (this.duration == this.getMaxDuration()) {
				this.clear();
			}
			// deixa o fogo no mapa um pouquinho

		}
		this.duration++;
	}

	protected void createBlock(Location baseLocation, int tamanho) {
		int startRow = baseLocation.getRow() - tamanho >= 0 ? baseLocation.getRow() - tamanho : 0;
		int endRow = baseLocation.getRow() + tamanho <= this.field.getWidth() - 1 ? baseLocation.getRow() + tamanho
				: this.field.getWidth() - 1;

		int startColumn = baseLocation.getCol() - tamanho >= 0 ? baseLocation.getCol() - tamanho : 0;
		int endColumn = baseLocation.getCol() + tamanho <= this.field.getDepth() - 1 ? baseLocation.getCol() + tamanho
				: this.field.getDepth() - 1;

		for (int i = startRow; i <= endRow; i++) {
			for (int j = startColumn; j <= endColumn; j++) {
				Location location = new Location(i, j);
				Actor actorAt = this.field.getActorAt(location);
				if (actorAt instanceof Animal) {
					if (actorAt != null) {
						((Animal) actorAt).setLocation(null);
					}
				}
				this.field.place(this, location);
				this.locations.add(location);
			}
		}
	}

	public void clear() {
		// remover o fogo do mapa
		for (Location l : this.locations) {
			this.field.place(null, l);
		}
		this.locations.clear();
	}

	@Override
	public boolean isActive() {
		return this.duration <= getMaxDuration();
	}

	public abstract int getMaxSize();

	public abstract int getMaxDuration();
}