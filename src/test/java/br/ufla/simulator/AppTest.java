package br.ufla.simulator;

import br.ufla.simulator.actors.Fox;

/**
 * Unit test for simple App.
 */
public class AppTest {
    
	public static void main(String[] args) {
		Class<?> type = Fox.class;
		
		System.out.println(new Fox(null, null, false).getClass().equals(type));
	}
}
