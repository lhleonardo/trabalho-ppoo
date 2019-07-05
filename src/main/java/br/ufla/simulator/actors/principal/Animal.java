package br.ufla.simulator.actors.principal;

import java.util.List;
import java.util.Random;

import br.ufla.simulator.actors.Actor;
import br.ufla.simulator.simulation.Field;
import br.ufla.simulator.simulation.Location;

/**
 * Representação de um animal na simulação. Todo animal possui uma localização
 * dentro do campo e poderá ser caçado por algum outro ator (podendo ser até um
 * animal)
 * 
 * @author Guilherme Barbosa Ochikubo, Guilherme Henrique de Melo e Leonardo
 *         Henrique de Braz
 *
 */
public abstract class Animal implements Actor {

	// idade do animal
	private int age;
	// localização que o animal se encontra
	private Location location;
	// campo de simulação atual
	private Field field;
	// Se o animal foi caçado
	private boolean wasHunted;

	private static final Random rand = new Random();

	/**
	 * Cria um animal e recebe para manipulação a situação atual do campo, junto com
	 * a sua localização inicial
	 * 
	 * @param field    - campo de simulação
	 * @param location - localização do animal dentro do campo de simulação
	 */
	public Animal(Field field, Location location) {
		this.location = location;
		this.field = field;
		this.age = 0;
	}

	/**
	 * Retorna a idade atual do animal
	 * 
	 * @return idade do animal
	 */
	public int getAge() {
		return age;
	}

	/**
	 * Faz com que o animal fique mais velho, com um dia de vida a mais.
	 */
	protected void incrementAge() {
		this.age++;
	}

	/**
	 * Define a idade inicial do animal para processamento de simulação com data
	 * pre-fixada
	 * 
	 * @param age - Idade que o animal receberá
	 */
	protected void setAge(int age) {
		this.age = age;
	}

	/**
	 * Obtém qual o local que o animal está localizado no momento.
	 * 
	 * @return localização dentro do campo da simulação
	 */
	public Location getLocation() {
		return location;
	}

	/**
	 * Define o local que o animal está posicionado no momento. Esta operação é dada
	 * refletida a partir do campo de simulação
	 * 
	 * @param location - Localização do animal dentro do campo de simulação
	 */
	public void setLocation(Location location) {
		this.location = location;
	}

	/**
	 * Define o local que o animal está posicionado no momento. Esta operação é dada
	 * refletida a partir do campo de simulação
	 * 
	 * @param row - Coordenada que representa a linha dentro do campo
	 * @param col - Coordenada que representa a coluna dentro do campo
	 */
	public void setLocation(int row, int col) {
		this.location = new Location(row, col);
	}

	/**
	 * Obtém o campo de representação com as configurações atuais, que foram criados
	 * pela simulação.
	 * 
	 * @see br.ufla.simulator.simulation.Simulator
	 * @return campo de representação da simulação atual
	 */
	public Field getField() {
		return field;
	}

	/**
	 * Verifica se o animal foi caçado por algum outro ator.
	 * 
	 * @return true se o animal foi caçado e false caso contrário
	 */
	public boolean getWasHunted() {
		return wasHunted;
	}

	/**
	 * Define que um animal foi caçado por algum outro ator na simulação
	 */
	public void setWasHunted() {
		wasHunted = true;
	}

	/**
	 * Gera uma representação de um número que define o número de reproduções que um
	 * animal vai realizar. Esse número representa o número de novos animais do
	 * mesmo tipo que irão nascer.
	 * 
	 * @return número de animais que vão nascer.
	 */
	public int breed() {
		int births = 0;
		if (canBreed() && rand.nextDouble() <= getBreedingProbability()) {
			births = rand.nextInt(getMaxLitterSize()) + 1;
		}
		return births;
	}

	@Override
	public abstract void act(List<Actor> newAnimals);

	/**
	 * Método que verifica se um determinado animal poderá reproduzir
	 * 
	 * @return flag que indica se o animal está apto para reprodução
	 */
	public abstract boolean canBreed();

	/**
	 * Define com qual idade um animal poderá começar a se reproduzir
	 * 
	 * @return idade para reprodução
	 */
	public abstract int getBreedingAge();

	/**
	 * Define a idade máxima que um animal poderá viver. Essa idade é relacionada ao
	 * número de dias (passos) que o animal existe.
	 * 
	 * @return idade máxima de existencia do animal
	 */
	public abstract int getMaxAge();

	/**
	 * Define a probabilidade de um animal reproduzir. Esse valor é independente de
	 * {@link #canBreed()}, isso significa que é a probabilidade do animal
	 * reproduzir independe se ele já está apto a reproduzir.
	 * 
	 * Esse valor é dado por passo.
	 * 
	 * @return probabilidade de um animal reproduzir
	 */
	public abstract double getBreedingProbability();

	/**
	 * Define a quantidade máxima de filhos resultante em uma reprodução do animal.
	 * 
	 * @return quantidade máxima de filhos
	 */
	public abstract int getMaxLitterSize();

}