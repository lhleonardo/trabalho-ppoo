package br.ufla.simulator.actors;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import br.ufla.simulator.simulation.Field;
import br.ufla.simulator.simulation.Location;

public class Flood extends NaturalEvent {

	private static final int MAX_SIZE = 20;
	private static final int MAX_DURATION = 3;
	private static final Random random = new Random();

	private List<Location> locations;
	private Field field;

	private boolean executed;
	private int duration;

	public Flood(Field field) {
		this.field = field;
		this.executed = false;
		this.locations = new ArrayList<Location>();

		this.duration = 1;
	}

	@Override
	public void act(List<Actor> newAnimals) {
		if (!executed) {
			// precisa colocar em vários locais a inundacao

			int maxWidth = this.field.getWidth();
			int maxDepth = this.field.getDepth();

			int x = random.nextInt(maxWidth);
			int y = random.nextInt(maxDepth);

			this.criarQuadradoDeAgua(new Location(x, y), random.nextInt(MAX_SIZE));

		} else {
			if (this.duration == MAX_DURATION) {
				// remover a inundacao do mapa
				for (Location l : this.locations) {
					this.field.place(null, l);
				}
			}

			// deixa a inundacao no mapa um pouquinho
			this.duration++;
		}
	}

	private void criarQuadradoDeAgua(Location baseLocation, int tamanho) {
		int startRow = baseLocation.getRow() - tamanho >= 0 ? baseLocation.getRow() - tamanho : 0;
		int endRow = baseLocation.getRow() + tamanho <= this.field.getWidth() ? baseLocation.getRow() + tamanho
				: this.field.getWidth();

		int startColumn = baseLocation.getCol() - tamanho >= 0 ? baseLocation.getCol() - tamanho : 0;
		int endColumn = baseLocation.getCol() + tamanho <= this.field.getDepth() ? baseLocation.getCol() + tamanho
				: this.field.getDepth();

		for (int i = startRow; i <= endRow; i++) {
			for (int j = startColumn; j <= endColumn; j++) {
				Location location = new Location(i, j);
				Animal actorAt = (Animal) this.field.getActorAt(location);
				actorAt.setLocation(null);
				this.field.place(this, location);
				this.locations.add(location);
			}
		}
	}

	@Override
	public boolean isActive() {
		return this.duration <= MAX_DURATION;
	}

}
