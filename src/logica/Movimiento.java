/**
	 * Juego de Ajedrez.
	 * @author  Oscar Rojas-Juan Aguilar-Sergio Sierra
	 * @version 1.0
	 * @since   2021-02-07
*/
package logica;

public class Movimiento {// clase que guarda de manera publica la posicion inicial y la posicion final
							// de un movimiento
	public int sourceRow;
	public int sourceColumn;
	public int targetRow;
	public int targetColumn;

	/**
	 * 
	 * @param sourceRow
	 * @param sourceColumn
	 * @param targetRow
	 * @param targetColumn
	 */
	public Movimiento(int sourceRow, int sourceColumn, int targetRow, int targetColumn) {
		this.sourceRow = sourceRow;
		this.sourceColumn = sourceColumn;
		this.targetRow = targetRow;
		this.targetColumn = targetColumn;
	}
}
