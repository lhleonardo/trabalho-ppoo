package br.ufla.simulator.simulation;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

import br.ufla.simulator.actors.Actor;
import br.ufla.simulator.actors.principal.Fox;

public class Field {
	private static final Random rand = new Random();

	// The depth and width of the field.
	private int depth, width;
	// Storage for the animals.
	private Actor[][] field;

	/**
	 * Represent a field of the given dimensions.
	 * 
	 * @param depth The depth of the field.
	 * @param width The width of the field.
	 */
	public Field(int depth, int width) {
		this.depth = depth;
		this.width = width;
		this.field = new Actor[depth][width];
	}

	/**
	 * Empty the field.
	 */
	public void clear() {
		for (int row = 0; row < depth; row++) {
			for (int col = 0; col < width; col++) {
				field[row][col] = null;
			}
		}
	}

	/**
	 * Place an animal at the given location. If there is already an animal at the
	 * location it will be lost.
	 * 
	 * @param actor The animal to be placed.
	 * @param row   Row coordinate of the location.
	 * @param col   Column coordinate of the location.
	 */
	public void place(Actor actor, int row, int col) {
		place(actor, new Location(row, col));
	}

	/**
	 * Place an animal at the given location. If there is already an animal at the
	 * location it will be lost.
	 * 
	 * @param actor    The animal to be placed.
	 * @param location Where to place the animal.
	 */
	public void place(Actor actor, Location location) {
		field[location.getRow()][location.getCol()] = actor;
	}

	/**
	 * Return the animal at the given location, if any.
	 * 
	 * @param location Where in the field.
	 * @return The animal at the given location, or null if there is none.
	 */
	public Actor getActorAt(Location location) {
		return getActorAt(location.getRow(), location.getCol());
	}

	/**
	 * Return the animal at the given location, if any.
	 * 
	 * @param row The desired row.
	 * @param col The desired column.
	 * @return The animal at the given location, or null if there is none.
	 */
	public Actor getActorAt(int row, int col) {
		return field[row][col];
	}

	/**
	 * Generate a random location that is adjacent to the given location, or is the
	 * same location. The returned location will be within the valid bounds of the
	 * field.
	 * 
	 * @param location The location from which to generate an adjacency.
	 * @return A valid location within the grid area. This may be the same Actor as
	 *         the location parameter.
	 */
	public Location randomAdjacentLocation(Location location) {
		int row = location.getRow();
		int col = location.getCol();
		// Generate an offset of -1, 0, or +1 for both the current row and col.
		int nextRow = row + rand.nextInt(3) - 1;
		int nextCol = col + rand.nextInt(3) - 1;
		// Check in case the new location is outside the bounds.
		if (nextRow < 0 || nextRow >= depth || nextCol < 0 || nextCol >= width) {
			return location;
		} else if (nextRow != row || nextCol != col) {
			return new Location(nextRow, nextCol);
		} else {
			return location;
		}
	}

	/**
	 * Try to find a free location that is adjacent to the given location. If there
	 * is none, then return the current location if it is free. If not, return null.
	 * The returned location will be within the valid bounds of the field.
	 * 
	 * @param location The location from which to generate an adjacency.
	 * @return A valid location within the grid area. This may be the same Actor as
	 *         the location parameter, or null if all locations around are full.
	 */
	public Location freeAdjacentLocation(Location location) {
		Iterator<Location> adjacent = adjacentLocations(location);
		while (adjacent.hasNext()) {
			Location next = (Location) adjacent.next();
			if (field[next.getRow()][next.getCol()] == null) {
				return next;
			}
		}
		// check whether current location is free
		if (field[location.getRow()][location.getCol()] == null) {
			return location;
		} else {
			return null;
		}
	}

	public Location findActor(Location location, Class<?> actorType, int radius) {
		Iterator<?> adjacentLocations = this.adjacentLocations(location, radius);
		while (adjacentLocations.hasNext()) {
			Location where = (Location) adjacentLocations.next();
			Actor animal = this.getActorAt(where);
			if (animal != null && animal.getClass().equals(actorType)) {
//				System.out.println("Animal founded. Type: " + animal.getClass().getCanonicalName());
				return where;
			}
		}
		return null;
	}

	public Location moveToNearestFox(Location location) {

		Location actorLocation = null;
		int y = 3;
		while (actorLocation == null && y != 100) {
			actorLocation = this.findActor(location, Fox.class, y);
			y += 1;
		}

		if (actorLocation != null) {
			Location newLocation = new Location(location.getRow(), location.getCol());
			if (actorLocation.getRow() > location.getRow()) {
				newLocation.setRowPlus(1);
			} else if (actorLocation.getRow() < location.getRow()) {
				newLocation.setRowPlus(-1);
			}
			if (actorLocation.getCol() > location.getCol()) {
				newLocation.setColPlus(1);
			} else if (actorLocation.getCol() < location.getCol()) {
				newLocation.setColPlus(-1);
			}

			if (this.getActorAt(newLocation) == null) {
				return newLocation;
			}
		}
		return null;
	}

	public Iterator<Location> adjacentLocations(Location location, int x) {
		int row = location.getRow();
		int col = location.getCol();
		LinkedList<Location> locations = new LinkedList<>();
		for (int roffset = (-1) * x; roffset <= x; roffset++) {
			int nextRow = row + roffset;
			if (nextRow >= 0 && nextRow < depth) {
				for (int coffset = (-1) * x; coffset <= x; coffset++) {
					int nextCol = col + coffset;
					// Exclude invalid locations and the original location.
					if (nextCol >= 0 && nextCol < width && (roffset != 0 || coffset != 0)) {
						locations.add(new Location(nextRow, nextCol));
					}
				}
			}
		}
		Collections.shuffle(locations, rand);
		return locations.iterator();
	}

	/**
	 * Generate an iterator over a shuffled list of locations adjacent to the given
	 * one. The list will not include the location itself. All locations will lie
	 * within the grid.
	 * 
	 * @param location The location from which to generate adjacencies.
	 * @return An iterator over locations adjacent to that given.
	 */
	public Iterator<Location> adjacentLocations(Location location) {
		int row = location.getRow();
		int col = location.getCol();
		LinkedList<Location> locations = new LinkedList<>();
		for (int roffset = -1; roffset <= 1; roffset++) {
			int nextRow = row + roffset;
			if (nextRow >= 0 && nextRow < depth) {
				for (int coffset = -1; coffset <= 1; coffset++) {
					int nextCol = col + coffset;
					// Exclude invalid locations and the original location.
					if (nextCol >= 0 && nextCol < width && (roffset != 0 || coffset != 0)) {
						locations.add(new Location(nextRow, nextCol));
					}
				}
			}
		}
		Collections.shuffle(locations, rand);
		return locations.iterator();
	}

	public int getDepth() {
		return depth;
	}

	/**
	 * @return The width of the field.
	 */
	public int getWidth() {
		return width;
	}
}