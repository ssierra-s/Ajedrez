/**
	 * Juego de Ajedrez.
	 * @author  Oscar Rojas-Juan Aguilar-Sergio Sierra
	 * @version 1.0
	 * @since   2021-02-07
*/
package logica;

import java.io.Serializable;

public class Jugador implements Serializable {

	// declaracion de parametros de la clase jugador
	private String nombre;
	private int ganadas;
	private int perdidas;
	private int jugadas;

	// Constructor vacio
	public Jugador() {

	}

	/** constructor con los parametros de nombre, ganadas, perdida y jugadas */
	/**
	 * 
	 * @param nombre
	 * @param ganadas
	 * @param perdidas
	 * @param jugadas
	 */
	public Jugador(String nombre, int ganadas, int perdidas, int jugadas) {
		this.nombre = nombre;
		this.ganadas = ganadas;
		this.perdidas = perdidas;
		this.jugadas = jugadas;
	}

	// Setters y getters de los parametros
	/**
	 * 
	 * @return el nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * 
	 * @param nombre
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * 
	 * @return las partidas ganadas
	 */
	public int getGanadas() {
		return ganadas;
	}

	/**
	 * 
	 * @param ganadas
	 */
	public void setGanadas(int ganadas) {
		this.ganadas = ganadas;
	}

	/**
	 * 
	 * @return las partidas perdidas
	 */
	public int getPerdidas() {
		return perdidas;
	}

	/**
	 * 
	 * @param perdidas
	 */
	public void setPerdidas(int perdidas) {
		this.perdidas = perdidas;
	}

	/**
	 * 
	 * @return las partidas jugadas
	 */
	public int getJugadas() {
		return jugadas;
	}

	/**
	 * 
	 * @param jugadas
	 */
	public void setJugadas(int jugadas) {
		this.jugadas = jugadas;
	}
}
