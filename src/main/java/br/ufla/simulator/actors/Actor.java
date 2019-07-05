package br.ufla.simulator.actors;

import java.awt.Color;
import java.util.List;

/**
 * Ator presente na simulação. Esse ator é representativo de um elemento
 * presente na simulação e desenhável no campo
 * 
 * @author Guilherme Barbosa Ochikubo, Guilherme Henrique de Melo e Leonardo Henrique de Braz
 *
 */
public interface Actor {
	/**
	 * Todo ator executa uma ação na simulação. Método responsável por definir qual
	 * será a atuação dentro da simulação
	 * 
	 * @param newAnimals - Novos animais que são criados a partir de uma ação de um
	 *                   ator
	 */
	void act(List<Actor> newAnimals);

	/**
	 * Método que define se um ator ainda está ativo na simulação. Atores que não
	 * estão ativos são retirados do mapa .
	 * 
	 * @return situação de atividade de um ator na simulação
	 */
	boolean isActive();

	/**
	 * Define a cor que o ator possuirá na visualização da simulação
	 * 
	 * @return cor que o ator possuirá na interface
	 */
	Color getColorRepresentation();
}
