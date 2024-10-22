/**
	 * Juego de Ajedrez.
	 * @author  Oscar Rojas-Juan Aguilar-Sergio Sierra
	 * @version 1.0
	 * @since   2021-02-07
*/
package aplicacion;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.List;

import logica.Archivo;
import logica.Fichas;
import logica.Tablero;

public class Presionar implements MouseListener, MouseMotionListener {

	// Declaracion de los parametros de la clase presionar
	public static List<SFicha> guiPieces;
	private Juego chessGui;
	private int dragOffsetX;
	private int dragOffsetY;

	/**
	 * tiene solo un constructor que pide una lista de Sficha y un objeto de tipo
	 * Juego
	 * 
	 * @param guiPieces
	 * @param chessGui
	 */
	public Presionar(List<SFicha> guiPieces, Juego chessGui) {
		Presionar.guiPieces = guiPieces;
		this.chessGui = chessGui;
	}

	// Eventos
	@Override
	public void mouseClicked(MouseEvent evt) {// cuando se hace click
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
	}// cuando se presiona el click

	@Override
	public void mouseExited(MouseEvent arg0) {
	}// cuando se deja de presionar el click

	@Override
	public void mousePressed(MouseEvent evt) {// mientras se sostiene el click
		int x = evt.getPoint().x;
		int y = evt.getPoint().y;

		/**
		 * Busca que pieza mover. Se itera el arreglo de arriba a bajo
		 *
		 */
		for (int i = this.guiPieces.size() - 1; i >= 0; i--) {
			SFicha guiPiece = this.guiPieces.get(i);
			if (guiPiece.isCaptured())
				continue;
			if (mouseOverPiece(guiPiece, x, y)) {
				if ((this.chessGui.getGameState() == Tablero.GAME_STATE_WHITE
						&& guiPiece.getColor() == Fichas.COLOR_WHITE)
						|| (this.chessGui.getGameState() == Tablero.GAME_STATE_BLACK
								&& guiPiece.getColor() == Fichas.COLOR_BLACK)) {
					// calcular el desplazamiento, porque no queremos que la pieza de arrastre
					// para saltar con su esquina superior izquierda de la posicion actual del mouse
					this.dragOffsetX = x - guiPiece.getX();
					this.dragOffsetY = y - guiPiece.getY();
					this.chessGui.setDragPiece(guiPiece);
					this.chessGui.repaint();
					break;
				}
			}
		}

		// mover la pieza que arrastre a la parte superior de la lista
		if (this.chessGui.getDragPiece() != null) {
			this.guiPieces.remove(this.chessGui.getDragPiece());
			this.guiPieces.add(this.chessGui.getDragPiece());
		}
	}

	@Override
	public void mouseReleased(MouseEvent evt) {
		if (this.chessGui.getDragPiece() != null) {
			int x = evt.getPoint().x - this.dragOffsetX;
			int y = evt.getPoint().y - this.dragOffsetY;

			// Establece la pieza en la nueva ubicacion
			// si es posible
			chessGui.setNewPieceLocation(this.chessGui.getDragPiece(), x, y);
			Archivo.anadirFichas();
			if (Tablero.getGameState() != 2) {
				this.chessGui.coronar();
			}
			this.chessGui.repaint();
			this.chessGui.setDragPiece(null);
		}

	}

	@Override
	public void mouseDragged(MouseEvent evt) {// mientras se mueve el mouse
		if (this.chessGui.getDragPiece() != null) {

			int x = evt.getPoint().x - this.dragOffsetX;
			int y = evt.getPoint().y - this.dragOffsetY;

			SFicha dragPiece = this.chessGui.getDragPiece();
			dragPiece.setX(x);
			dragPiece.setY(y);

			this.chessGui.repaint();
		}

	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
	}// cuando se mueve el mouse

	// verifica si el mouse esta actualmente sobre esta pieza
	private boolean mouseOverPiece(SFicha guiPiece, int x, int y) {

		return guiPiece.getX() <= x && guiPiece.getX() + guiPiece.getWidth() >= x && guiPiece.getY() <= y
				&& guiPiece.getY() + guiPiece.getHeight() >= y;
	}

	// setters y getters
	/**
	 * 
	 * @return el arreglo de SFichas
	 */
	public static List<SFicha> getGuiPieces() {
		return guiPieces;
	}

	/**
	 * 
	 * @param guiPieces
	 */
	public void setGuiPieces(List<SFicha> guiPieces) {
		this.guiPieces = guiPieces;
	}
}
