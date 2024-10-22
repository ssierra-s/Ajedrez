/**
	 * Juego de Ajedrez.
	 * @author  Oscar Rojas-Juan Aguilar-Sergio Sierra
	 * @version 1.0
	 * @since   2021-02-07
*/
package logica;

public class Validar {

	// Declaracion de parametros de la clase Validar
	private Tablero chessGame;
	private Fichas sourcePiece;
	private Fichas targetPiece;
	/**
	 * 
	 */
	public static boolean coronarN = false, coronarB = false;

	// el constructor pide como parametro un Tablero
	
	/**
	 * 
	 * @param chessGame
	 */
	public Validar(Tablero chessGame) {
		this.chessGame = chessGame;
	}

	/**
	 * 
	 * @param move
	 * @return si el movimiento es valido
	 */
	public boolean isMoveValid(Movimiento move) {// pide un movimiento y retorna un true si el movimiento es valido
		int sourceRow = move.sourceRow;
		int sourceColumn = move.sourceColumn;
		int targetRow = move.targetRow;
		int targetColumn = move.targetColumn;

		sourcePiece = chessGame.getNonCapturedPieceAtLocation(sourceRow, sourceColumn);
		targetPiece = this.chessGame.getNonCapturedPieceAtLocation(targetRow, targetColumn);

		// verifica si la pieza existe
		if (sourcePiece == null) {
			return false;
		}

		// verifica el color de la pieza
		if (sourcePiece.getColor() == Fichas.COLOR_WHITE && this.chessGame.getGameState() == Tablero.GAME_STATE_WHITE) {
		} else if (sourcePiece.getColor() == Fichas.COLOR_BLACK
				&& this.chessGame.getGameState() == Tablero.GAME_STATE_BLACK) {
		} else {
			return false;
		}

		// verifica que con el movimiento la ficha no salga del tablero
		if (targetRow < Fichas.ROW_1 || targetRow > Fichas.ROW_8 || targetColumn < Fichas.COLUMN_A
				|| targetColumn > Fichas.COLUMN_H) {

			return false;
		}

		// validar reglas de movimiento de piezas
		boolean validPieceMove = false;
		switch (sourcePiece.getType()) {
		case Fichas.TYPE_BISHOP:
			validPieceMove = isValidBishopMove(sourceRow, sourceColumn, targetRow, targetColumn);
			break;
		case Fichas.TYPE_KING:
			validPieceMove = isValidKingMove(sourceRow, sourceColumn, targetRow, targetColumn);
			break;
		case Fichas.TYPE_KNIGHT:
			validPieceMove = isValidKnightMove(sourceRow, sourceColumn, targetRow, targetColumn);
			break;
		case Fichas.TYPE_PAWN:
			validPieceMove = isValidPawnMove(sourceRow, sourceColumn, targetRow, targetColumn);
			break;
		case Fichas.TYPE_QUEEN:
			validPieceMove = isValidQueenMove(sourceRow, sourceColumn, targetRow, targetColumn);
			break;
		case Fichas.TYPE_ROOK:
			validPieceMove = isValidRookMove(sourceRow, sourceColumn, targetRow, targetColumn);
			break;
		default:
			break;
		}
		if (!validPieceMove) {
			return false;
		}

		return true;
	}

	/**
	 * 
	 * @return si se puede capturar en esa posicion
	 */
	private boolean isTargetLocationCaptureable() {// verifica si al hacer el movimiento se captura alguna ficha enemiga
		if (targetPiece == null) {
			return false;
		} else if (targetPiece.getColor() != sourcePiece.getColor()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 
	 * @return si la posicion a mover esta libre
	 */
	private boolean isTargetLocationFree() {// verifica si la ubicacion a la que se va a mover la ficha esta libre
		return targetPiece == null;
	}

	// verifica si el movimiento del afil es valido
	/**
	 * 
	 * @param sourceRow
	 * @param sourceColumn
	 * @param targetRow
	 * @param targetColumn
	 * @return si es valido el m,ovimiento del alfil
	 */
	private boolean isValidBishopMove(int sourceRow, int sourceColumn, int targetRow, int targetColumn) {
		// El alfil puede mover cualquier número de casillas en diagonal, pero no puede
		// saltar sobre otras piezas.

		// verifica los posibles movimientos
		if (isTargetLocationFree() || isTargetLocationCaptureable()) {
		} else {
			return false;
		}
		boolean isValid = false;
		int diffRow = targetRow - sourceRow;
		int diffColumn = targetColumn - sourceColumn;
		if (diffRow == diffColumn && diffColumn > 0) {
			// Movimiento diagonal Arriba-derecha
			isValid = !arePiecesBetweenSourceAndTarget(sourceRow, sourceColumn, targetRow, targetColumn, +1, +1);
		} else if (diffRow == -diffColumn && diffColumn > 0) {
			// Movimiento diagonal abajo-derecha
			isValid = !arePiecesBetweenSourceAndTarget(sourceRow, sourceColumn, targetRow, targetColumn, -1, +1);
		} else if (diffRow == diffColumn && diffColumn < 0) {
			// Movimiento diagonal abajo-izquierda
			isValid = !arePiecesBetweenSourceAndTarget(sourceRow, sourceColumn, targetRow, targetColumn, -1, -1);
		} else if (diffRow == -diffColumn && diffColumn < 0) {
			// Movimiento diagonal arriba-izquierda
			isValid = !arePiecesBetweenSourceAndTarget(sourceRow, sourceColumn, targetRow, targetColumn, +1, -1);
		} else {
			isValid = false;
		}
		return isValid;
	}

	// verifica si el movimiento de la reina es valido
	/**
	 * 
	 * @param sourceRow
	 * @param sourceColumn
	 * @param targetRow
	 * @param targetColumn
	 * @return si es valido el movimiento de la reina
	 */
	private boolean isValidQueenMove(int sourceRow, int sourceColumn, int targetRow, int targetColumn) {
		// La dama combina el poder de la torre y el alfil y puede mover cualquier
		// número.
		// de cuadrados a lo largo de la fila, fila o diagonal, pero no puede saltar
		// sobre otras piezas.
		boolean result = isValidBishopMove(sourceRow, sourceColumn, targetRow, targetColumn);
		result |= isValidRookMove(sourceRow, sourceColumn, targetRow, targetColumn);
		return result;
	}

	// verifica si el movimiento del peon es valido
	/**
	 * 
	 * @param sourceRow
	 * @param sourceColumn
	 * @param targetRow
	 * @param targetColumn
	 * @return si es valido el movimiento del peon
	 */
	private boolean isValidPawnMove(int sourceRow, int sourceColumn, int targetRow, int targetColumn) {

		boolean isValid = false;
		// El peón puede avanzar a la casilla desocupada en frente
		// verifica si algun peon cruzo todo el tablero
		if (sourcePiece.getColor() == Fichas.COLOR_WHITE) {
			if (targetRow == 7 && sourceRow == 6) {
				coronarB = true;
			} else {
				coronarB = false;
			}
		}
		if (sourcePiece.getColor() != Fichas.COLOR_WHITE) {
			if (targetRow == 0 && sourceRow == 1) {
				coronarN = true;
			} else {
				coronarN = false;
			}
		}
		// verifica los posibles movimientos del peon
		if (isTargetLocationFree()) {
			if (sourceColumn == targetColumn) {
				// en la misma columna
				if (sourcePiece.getColor() == Fichas.COLOR_WHITE) {
					// si es blanca
					if (sourceRow + 1 == targetRow) {
						// mover arriba
						isValid = true;
					} else {
						isValid = false;
					}
				} else {
					// si es negra
					if (sourceRow - 1 == targetRow) {
						// mover abajo
						isValid = true;
					} else {
						isValid = false;
					}
				}
			} else {
				// si no es en la misma columna
				isValid = false;
			}
			// o puede moverse
			// a una casilla ocupada por la pieza de un oponente, que esta diagonalmente al
			// frente
			// capturando esa pieza.
		} else if (isTargetLocationCaptureable()) {

			if (sourceColumn + 1 == targetColumn || sourceColumn - 1 == targetColumn) {
				// una columna hacia la izquiera o deracha
				if (sourcePiece.getColor() == Fichas.COLOR_WHITE) {
					// si es blanca
					if (sourceRow + 1 == targetRow) {
						// mover arriba
						isValid = true;
					} else {
						isValid = false;
					}
				} else {
					// si es negra
					if (sourceRow - 1 == targetRow) {
						// mover abajo
						isValid = true;
					} else {
						isValid = false;
					}
				}
			} else {
				// a mas de una columna a la izquierda o derecha
				isValid = false;
			}
		}
		return isValid;
	}

	// verifica si el movimiento del caballo es valido
	/**
	 * 
	 * @param sourceRow
	 * @param sourceColumn
	 * @param targetRow
	 * @param targetColumn
	 * @return si es valido el movimiento del caballo
	 */
	private boolean isValidKnightMove(int sourceRow, int sourceColumn, int targetRow, int targetColumn) {
		// El caballo se mueve a cualquiera de las casillas mas cercanas que no estan en
		// el mismo rango,
		// archivo o diagonal, por lo que el movimiento forma una "L" de dos cuadrados
		// de largo y uno
		// cuadrado de ancho. El caballo es la unica pieza que puede saltar sobre otras
		// piezas.

		// verifica los posibles movimientos
		if (isTargetLocationFree() || isTargetLocationCaptureable()) {
		} else {
			return false;
		}

		if (sourceRow + 2 == targetRow && sourceColumn + 1 == targetColumn) {
			// moviendo 2 casillas hacia arriba y 1 a la derecha
			return true;
		} else if (sourceRow + 1 == targetRow && sourceColumn + 2 == targetColumn) {
			// moviendo 1 casilla hacia arriba y 2 a la derecha
			return true;
		} else if (sourceRow - 1 == targetRow && sourceColumn + 2 == targetColumn) {
			// moviendo 1 casilla hacia abajo y 2 a la derecha
			return true;
		} else if (sourceRow - 2 == targetRow && sourceColumn + 1 == targetColumn) {
			// moviendo 2 casillas hacia abajo y 1 a la derecha
			// move down down right
			return true;
		} else if (sourceRow - 2 == targetRow && sourceColumn - 1 == targetColumn) {
			// moviendo 2 casillas hacia abajo y 1 a la izquierda
			return true;
		} else if (sourceRow - 1 == targetRow && sourceColumn - 2 == targetColumn) {
			// moviendo 1 casilla hacia abajo y 2 a la izquierda
			return true;
		} else if (sourceRow + 1 == targetRow && sourceColumn - 2 == targetColumn) {
			// moviendo 1 casilla hacia arriba y 2 a la izquierda
			return true;
		} else if (sourceRow + 2 == targetRow && sourceColumn - 1 == targetColumn) {
			// moviendo 2 casillas hacia arriba y 1 a la izquierda
			return true;
		} else {
			return false;
		}
	}

	// verifica si el movimiento del rey es valido
	/**
	 * 
	 * @param sourceRow
	 * @param sourceColumn
	 * @param targetRow
	 * @param targetColumn
	 * @return si es valido el movimiento del rey
	 */
	private boolean isValidKingMove(int sourceRow, int sourceColumn, int targetRow, int targetColumn) {
		// El rey se mueve una casilla en cualquier dirección.

		// verifica los posibles movimientos del rey
		if (isTargetLocationFree() || isTargetLocationCaptureable()) {
		} else {
			return false;
		}
		boolean isValid = true;
		if (sourceRow + 1 == targetRow && sourceColumn == targetColumn) {
			// arriba
			isValid = true;
		} else if (sourceRow + 1 == targetRow && sourceColumn + 1 == targetColumn) {
			// arriba derecha
			isValid = true;
		} else if (sourceRow == targetRow && sourceColumn + 1 == targetColumn) {
			// derecha
			isValid = true;
		} else if (sourceRow - 1 == targetRow && sourceColumn + 1 == targetColumn) {
			// abajo derecha
			isValid = true;
		} else if (sourceRow - 1 == targetRow && sourceColumn == targetColumn) {
			// abajo
			isValid = true;
		} else if (sourceRow - 1 == targetRow && sourceColumn - 1 == targetColumn) {
			// abajo izquierda
			isValid = true;
		} else if (sourceRow == targetRow && sourceColumn - 1 == targetColumn) {
			// izquierda
			isValid = true;
		} else if (sourceRow + 1 == targetRow && sourceColumn - 1 == targetColumn) {
			// arriba izquierda
			isValid = true;
		} else {
			isValid = false;
		}
		return isValid;
	}

	// verifica si el movimiento de la torre es valido
	/**
	 * 
	 * @param sourceRow
	 * @param sourceColumn
	 * @param targetRow
	 * @param targetColumn
	 * @return si es valido el movimiento de la torre
	 */
	private boolean isValidRookMove(int sourceRow, int sourceColumn, int targetRow, int targetColumn) {
		// La torre puede mover cualquier número de casillas a lo largo de cualquier
		// fila o columna, pero
		// no puede saltar por encima de otras piezas.

		// verifica los posibles movimientos de la torre
		if (isTargetLocationFree() || isTargetLocationCaptureable()) {
		} else {
			return false;
		}
		boolean isValid = false;
		int diffRow = targetRow - sourceRow;
		int diffColumn = targetColumn - sourceColumn;
		if (diffRow == 0 && diffColumn > 0) {
			// derecha
			isValid = !arePiecesBetweenSourceAndTarget(sourceRow, sourceColumn, targetRow, targetColumn, 0, +1);
		} else if (diffRow == 0 && diffColumn < 0) {
			// izquierda
			isValid = !arePiecesBetweenSourceAndTarget(sourceRow, sourceColumn, targetRow, targetColumn, 0, -1);
		} else if (diffRow > 0 && diffColumn == 0) {
			// arriba
			isValid = !arePiecesBetweenSourceAndTarget(sourceRow, sourceColumn, targetRow, targetColumn, +1, 0);
		} else if (diffRow < 0 && diffColumn == 0) {
			// abajo
			isValid = !arePiecesBetweenSourceAndTarget(sourceRow, sourceColumn, targetRow, targetColumn, -1, 0);
		} else {
			isValid = false;
		}
		return isValid;
	}

	// verifica si hay piezas entre una ficha y un enemigo
	/**
	 * 
	 * @param sourceRow
	 * @param sourceColumn
	 * @param targetRow
	 * @param targetColumn
	 * @param rowIncrementPerStep
	 * @param columnIncrementPerStep
	 * @return si hay piezas entre una ficha y un enemigo
	 */
	private boolean arePiecesBetweenSourceAndTarget(int sourceRow, int sourceColumn, int targetRow, int targetColumn,
			int rowIncrementPerStep, int columnIncrementPerStep) {
		int currentRow = sourceRow + rowIncrementPerStep;
		int currentColumn = sourceColumn + columnIncrementPerStep;
		while (true) {
			if (currentRow == targetRow && currentColumn == targetColumn) {
				break;
			}
			if (currentRow < Fichas.ROW_1 || currentRow > Fichas.ROW_8 || currentColumn < Fichas.COLUMN_A
					|| currentColumn > Fichas.COLUMN_H) {
				break;
			}
			if (this.chessGame.isNonCapturedPieceAtLocation(currentRow, currentColumn)) {
				return true;
			}
			currentRow += rowIncrementPerStep;
			currentColumn += columnIncrementPerStep;
		}
		return false;
	}
}
