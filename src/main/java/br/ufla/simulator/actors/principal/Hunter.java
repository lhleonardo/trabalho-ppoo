package br.ufla.simulator.actors.principal;

import java.awt.Color;
import java.util.List;

import br.ufla.simulator.actors.Actor;
import br.ufla.simulator.simulation.Field;
import br.ufla.simulator.simulation.Location;


/**
 * 
 * @author Guilherme Barbosa Ochikubo, Guilherme Henrique de Melo e Leonardo Henrique de Braz
 *
 */
public class Hunter implements Actor {
	private Field field;
	private Location location;
	
	/**
	 * 
	 * @param field matriz de posições do campo da simulação.
	 * @param location local aonde o caçador se encontra na matriz.
	 */

	public Hunter(Field field, Location location) {
		this.location = location;
		this.field = field;
	}
	
	/**
	 * metodo relaciona as ações do caçador, iniciando com a caça por raposas. No caso de não encontrar um
	 * raposa em seu alcance o caçador anda em direção a raposa mais proxima.
	 */
	@Override
	public void act(List<Actor> newAnimals) {
		if (isActive()) {
			Location newLocation = this.findFox();
			if (newLocation == null) {
				needMove();
			}
		}
	}
	/**
	 * Faz com que o caçador ande em direção a raposa mais proxima.
	 */
	public void needMove() {
		Location newLocation = field.moveToNearestFox(location);
		if (newLocation != null) {
			field.place(null, this.location);
			field.place(this, newLocation);
			location = newLocation;
		} else {
			field.place(this, this.location);
		}
	}
	
	/**
	 * Busca pela raposa a aleatoria mais proxima da localizacao do objeto. Caça seu alvo raposa, tornando sua
	 * wasHunted true e move-se até o local do alvo.
	 * @return retorna a localização da raposa caçada, caso não tenho encontrado uma raposa proxima é retornado null
	 */
	public Location findFox() {
		Location newLocation = field.findActor(location, Fox.class,2);
		if (newLocation != null) {
			((Animal)field.getActorAt(newLocation)).setLocation(null);
			field.place(null,location);
			field.place(this, newLocation);
			location = newLocation;
			field.place(this,location);
			return newLocation;
		}
		return null;
	}
	@Override
	public boolean isActive() {
		return true;
	}

	@Override
	public Color getColorRepresentation() {
		return Color.green;
	}
}
