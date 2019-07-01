package br.ufla.simulator;

import java.util.Random;

public abstract class Animal extends Actor {

    // The Animal age.
    private int age;
    // Whether the Animal is alive or not.
    private boolean alive;
    // The Animal position
    private Location location;
    
    public Animal() {	
    	this.age = 0;
    	this.alive = true;
    }

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}
	
	/**
    * Check whether the Animal is alive or not.
    * @return True if the Animal is still alive.
    */
	public boolean isAlive() {
		return alive;
	}

	public void setAlive(boolean alive) {
		this.alive = alive;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}
	
	public void setLocation(int row, int col){
	    this.location = new Location(row, col);
	}
    
}