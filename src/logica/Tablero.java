/**
	 * Juego de Ajedrez.
	 * @author  Oscar Rojas-Juan Aguilar-Sergio Sierra
	 * @version 1.0
	 * @since   2021-02-07
*/
package logica;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Tablero {

	// Declara los parametros de la clase tablero
	public static final int GAME_STATE_WHITE = 0;
	private static int gameState = GAME_STATE_WHITE;

	public static final int GAME_STATE_BLACK = 1;
	public static final int GAME_STATE_END = 2;

	public static List<Fichas> pieces = new ArrayList<Fichas>();
	public static List<Fichas> fichas = new ArrayList<Fichas>();
	public static int aux = 0;

	private Validar moveValidator;

	// Constructor de tablero

	public Tablero() {

		this.moveValidator = new Validar(this);

		// crea y coloca las piezas
		createAndAddPiece(Fichas.COLOR_WHITE, Fichas.TYPE_ROOK, Fichas.ROW_1, Fichas.COLUMN_A);
		createAndAddPiece(Fichas.COLOR_WHITE, Fichas.TYPE_KNIGHT, Fichas.ROW_1, Fichas.COLUMN_B);
		createAndAddPiece(Fichas.COLOR_WHITE, Fichas.TYPE_BISHOP, Fichas.ROW_1, Fichas.COLUMN_C);
		createAndAddPiece(Fichas.COLOR_WHITE, Fichas.TYPE_QUEEN, Fichas.ROW_1, Fichas.COLUMN_D);
		createAndAddPiece(Fichas.COLOR_WHITE, Fichas.TYPE_KING, Fichas.ROW_1, Fichas.COLUMN_E);
		createAndAddPiece(Fichas.COLOR_WHITE, Fichas.TYPE_BISHOP, Fichas.ROW_1, Fichas.COLUMN_F);
		createAndAddPiece(Fichas.COLOR_WHITE, Fichas.TYPE_KNIGHT, Fichas.ROW_1, Fichas.COLUMN_G);
		createAndAddPiece(Fichas.COLOR_WHITE, Fichas.TYPE_ROOK, Fichas.ROW_1, Fichas.COLUMN_H);
		int currentColumn = Fichas.COLUMN_A;
		for (int i = 0; i < 8; i++) {
			createAndAddPiece(Fichas.COLOR_WHITE, Fichas.TYPE_PAWN, Fichas.ROW_2, currentColumn);
			currentColumn++;
		}
		createAndAddPiece(Fichas.COLOR_BLACK, Fichas.TYPE_ROOK, Fichas.ROW_8, Fichas.COLUMN_A);
		createAndAddPiece(Fichas.COLOR_BLACK, Fichas.TYPE_KNIGHT, Fichas.ROW_8, Fichas.COLUMN_B);
		createAndAddPiece(Fichas.COLOR_BLACK, Fichas.TYPE_BISHOP, Fichas.ROW_8, Fichas.COLUMN_C);
		createAndAddPiece(Fichas.COLOR_BLACK, Fichas.TYPE_QUEEN, Fichas.ROW_8, Fichas.COLUMN_D);
		createAndAddPiece(Fichas.COLOR_BLACK, Fichas.TYPE_KING, Fichas.ROW_8, Fichas.COLUMN_E);
		createAndAddPiece(Fichas.COLOR_BLACK, Fichas.TYPE_BISHOP, Fichas.ROW_8, Fichas.COLUMN_F);
		createAndAddPiece(Fichas.COLOR_BLACK, Fichas.TYPE_KNIGHT, Fichas.ROW_8, Fichas.COLUMN_G);
		createAndAddPiece(Fichas.COLOR_BLACK, Fichas.TYPE_ROOK, Fichas.ROW_8, Fichas.COLUMN_H);
		currentColumn = Fichas.COLUMN_A;
		for (int i = 0; i < 8; i++) {
			createAndAddPiece(Fichas.COLOR_BLACK, Fichas.TYPE_PAWN, Fichas.ROW_7, currentColumn);
			currentColumn++;
		}
	}

	// Polimorfismo

	/**
	 * constructor de tablero con una lista de fichas como parametro
	 * 
	 * @param pieces
	 */
	public Tablero(List<Fichas> pieces) {
		this.moveValidator = new Validar(this);
		this.pieces = pieces;
	}

	/**
	 * 
	 * crea una instancia de pieza y la agregar a la lista interna de piezas
	 * 
	 * @param color
	 * @param type
	 * @param row
	 * @param column
	 */
	private void createAndAddPiece(int color, int type, int row, int column) {
		Fichas piece = new Fichas(color, type, row, column);
		this.pieces.add(piece);
	}

	/**
	 * Mueve la pieza a la ubicacion especificada. Si la ubicacion de destino esta
	 * ocupada por una pieza del oponente, esa pieza se marca como 'capturada'. Si
	 * el movimiento no se pudo ejecutar con exito, se devuelve 'falso' y el estado
	 * de juego no cambia.
	 */
	/**
	 * 
	 * @param move
	 * @return si es valido o no el movimiento
	 */
	public boolean movePiece(Movimiento move) {
		if (!this.moveValidator.isMoveValid(move)) {
			return false;
		}
		sonidoFicha();
		Fichas piece = getNonCapturedPieceAtLocation(move.sourceRow, move.sourceColumn);

		// verifica si el movimiento esta capturando una pieza oponente
		int opponentColor = (piece.getColor() == Fichas.COLOR_BLACK ? Fichas.COLOR_WHITE : Fichas.COLOR_BLACK);
		if (isNonCapturedPieceAtLocation(opponentColor, move.targetRow, move.targetColumn)) {
			Fichas opponentPiece = getNonCapturedPieceAtLocation(move.targetRow, move.targetColumn);
			opponentPiece.isCaptured(true);
		}
		piece.setRow(move.targetRow);
		piece.setColumn(move.targetColumn);
		if (isGameEndConditionReached()) {
			this.gameState = GAME_STATE_END;
		} else {
			this.changeGameState();
		}
		return true;
	}

	/**
	 * comprueba si se cumple la condicion de finalizacion del juego: rey capturado
	 */
	/**
	 * 
	 * @return si el juego termino
	 */
	private boolean isGameEndConditionReached() {
		for (Fichas piece : this.pieces) {
			if (piece.getType() == Fichas.TYPE_KING && piece.isCaptured()) {
				try {
					ArchivoTabla.manejo();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				return true;
			}
		}
		return false;
	}

	/**
	 * devuelve la primera pieza en la ubicación especificada que no está marcada
	 * como "capturada"
	 */
	/**
	 * 
	 * @param row
	 * @param column
	 * @return la pieza
	 */
	public Fichas getNonCapturedPieceAtLocation(int row, int column) {
		for (Fichas piece : this.pieces) {
			if (piece.getRow() == row && piece.getColumn() == column && piece.isCaptured() == false) {
				return piece;
			}
		}
		return null;
	}

	/**
	 * Comprueba si hay una pieza en la ubicacion especificada que no esto marcada
	 * como 'capturada' y tiene el color especificado.
	 */
	/**
	 * 
	 * @param color
	 * @param row
	 * @param column
	 * @return si se capturo una pieza del color especificado
	 */
	private boolean isNonCapturedPieceAtLocation(int color, int row, int column) {
		for (Fichas piece : this.pieces) {
			if (piece.getRow() == row && piece.getColumn() == column && piece.isCaptured() == false
					&& piece.getColor() == color) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Comprueba si hay una pieza no capturada en la ubicacion especificada
	 * 
	 * @param row
	 * @param column
	 * @return si la posicion esta capturada
	 */
	public boolean isNonCapturedPieceAtLocation(int row, int column) {
		for (Fichas piece : this.pieces) {
			if (piece.getRow() == row && piece.getColumn() == column && piece.isCaptured() == false) {
				return true;
			}
		}
		return false;
	}

	/**
	 * cambia el estado del juego dependiendo de la situacion actual del tablero.
	 */
	public void changeGameState() {

		// comprueba si se ha alcanzado la condición de finalización del juego
		if (this.isGameEndConditionReached()) {
			if (this.gameState == Tablero.GAME_STATE_BLACK) {
				System.out.println("Game over! Black won!");
			} else {
				System.out.println("Game over! White won!");
			}
			this.gameState = Tablero.GAME_STATE_END;
			return;
		}
		switch (this.gameState) {
		case GAME_STATE_BLACK:
			this.gameState = GAME_STATE_WHITE;
			aux = 0;
			break;
		case GAME_STATE_WHITE:
			aux = 1;
			this.gameState = GAME_STATE_BLACK;
			break;
		case GAME_STATE_END:
			break;
		default:
			throw new IllegalStateException("unknown game state:" + this.gameState);
		}
	}

	/**
	 * Reproduce el sonido al ser oprimido un boton
	 *
	 */
	public void sonidoFicha() {
		try {
			AudioInputStream audioInputStream = AudioSystem
					.getAudioInputStream(new File("src/recursos/audioFichas.wav").getAbsoluteFile());
			Clip clip = AudioSystem.getClip();
			clip.open(audioInputStream);
			clip.start();
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
			System.out.println("Error al reproducir el sonido.");
		}
	}

	/**
	 * iguala el arrayList Fichas con el arrayList pieces
	 */
	public static void mane() {
		fichas = pieces;
	}

	// getters y setter de algunos parametros de la clase

	/**
	 * 
	 * @param pieces
	 */
	public static void setPieces(List<Fichas> pieces) {
		Tablero.pieces = pieces;
	}

	/**
	 * 
	 * @return el estado del juego
	 */
	public static int getGameState() {
		return gameState;
	}

	/**
	 * 
	 * @return si el movimiento es valido
	 */
	public Validar getMoveValidator() {
		return this.moveValidator;
	}

	/**
	 * 
	 * @param gameState
	 */
	public static void setGameState(int gameState) {
		Tablero.gameState = gameState;
	}

	/**
	 * 
	 * @return el arreglo de las piezas
	 */
	public List<Fichas> getPieces() {
		return this.pieces;
	}
}
