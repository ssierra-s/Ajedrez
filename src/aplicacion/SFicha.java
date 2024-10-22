/**
	 * Juego de Ajedrez.
	 * @author  Oscar Rojas-Juan Aguilar-Sergio Sierra
	 * @version 1.0
	 * @since   2021-02-07
*/
package aplicacion;

import java.awt.Image;
import java.io.Serializable;

import logica.Fichas;

public class SFicha implements Serializable {
	// declara los parametros de la clase SFicha
	transient private Image img;
	private int x;
	private int y;
	private Fichas piece;

	/**
	 * 
	 * tiene un unico constructor que pide una imagen y un objeto de tipo Fichas
	 * 
	 * @param img
	 * @param piece
	 */
	public SFicha(Image img, Fichas piece) {
		this.img = img;
		this.piece = piece;
		this.resetToUnderlyingPiecePosition();
	}

	/**
	 * Mueve la pieza de la interfaz grafica de usuario de nuevo a las coordenadas
	 * que corresponden con la fila y columna de la pieza
	 */

	public void resetToUnderlyingPiecePosition() {
		this.x = Juego.convertColumnToX(piece.getColumn());
		this.y = Juego.convertRowToY(piece.getRow());
	}

	/** Setters y Getters de los parametros */
	/**
	 * 
	 * @param img
	 */
	public void setImg(Image img) {
		this.img = img;
	}

	/**
	 * 
	 * @return el tipo de pieza
	 */
	public int tipo() {
		return piece.getType();
	}

	/**
	 * 
	 * @return la imagen
	 */
	public Image getImage() {
		return img;
	}

	/**
	 * 
	 * @return la x
	 */
	public int getX() {
		return x;
	}

	/**
	 * 
	 * @return la y
	 */
	public int getY() {
		return y;
	}

	/**
	 * 
	 * @param x
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * 
	 * @param y
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * 
	 * @return el ancho
	 */
	public int getWidth() {
		return img.getHeight(null);
	}

	/**
	 * 
	 * @return el alto
	 */
	public int getHeight() {
		return img.getHeight(null);
	}

	/**
	 * 
	 * @return el color de la ficha
	 */
	public int getColor() {
		return this.piece.getColor();
	}

	@Override

	public String toString() {
		return this.piece + " " + x + "/" + y;
	}

	/**
	 * 
	 * @return la pieza
	 */
	public Fichas getPiece() {
		return piece;
	}

	public boolean isCaptured() {
		return this.piece.isCaptured();
	}
}
