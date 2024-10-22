/**
	 * Juego de Ajedrez.
	 * @author  Oscar Rojas-Juan Aguilar-Sergio Sierra
	 * @version 1.0
	 * @since   2021-02-07
*/
package aplicacion;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

// Importa las clases del paquete gui
import gui.Panel_Nueva;
import gui.Reanudar;
// Importa las clase del paquete logica
import logica.Archivo;
import logica.Fichas;
import logica.Movimiento;
import logica.Tablero;
import logica.Validar;

public class Juego extends JPanel {

	// Version
	private static final long serialVersionUID = 1;

	// Dimensiones donde empieza el tablero en X y en Y
	private static final int BOARD_START_X = 420;
	private static final int BOARD_START_Y = 98;

	// El tamaño de cada recuadro
	private static final int SQUARE_WIDTH = 60;
	private static final int SQUARE_HEIGHT = 60;

	// Tamaño de las piezas
	private static final int PIECE_WIDTH = 48;
	private static final int PIECE_HEIGHT = 48;

	// Donde se ponen las piezas inicialmente
	private static final int PIECES_START_X = BOARD_START_X + (int) (SQUARE_WIDTH / 2.0 - PIECE_WIDTH / 2.0);
	private static final int PIECES_START_Y = BOARD_START_Y + (int) (SQUARE_HEIGHT / 2.0 - PIECE_HEIGHT / 2.0);

	// Donde comienza el arrastrado en X y en Y
	private static final int DRAG_TARGET_SQUARE_START_X = BOARD_START_X - (int) (PIECE_WIDTH / 2.0);
	private static final int DRAG_TARGET_SQUARE_START_Y = BOARD_START_Y - (int) (PIECE_HEIGHT / 2.0);

	// Variable que contendra el fondo del juego
	private Image imgBackground;
	// Boton para salir del juego
	private JButton btnSalir;

	// panel de fichas de cambio Blancas
	private JPanel panelB;
	private JButton btnTorreB;
	private JButton btnCaballoB;
	private JButton btnAlfilB;
	private JButton btnReinaB;

	// panel de fichas de cambio Negras
	private JPanel panelN;
	private JButton btnTorreN;
	private JButton btnCaballoN;
	private JButton btnAlfilN;
	private JButton btnReinaN;

	// Instancia de la clase tablero
	private Tablero chessGame;
	// Etiqueta que mostrara los turnos
	JLabel JTurno;

	// Arreglo que contiene las SFichas del juego
	private List<SFicha> guiPieces = new ArrayList<SFicha>();

	// Variable para saber que pieza se esta arrastrando
	private SFicha dragPiece;
	// Etiqueta que muestra el jugador que gano
	private JLabel JGanador;

	// Constructor
	public Juego() {
		// Se crean las dimensiones del frame
		int ancho = 1280;
		int alto = 720;
		// Se establecen las dimensiones del panel
		setBounds(0, 0, ancho, alto);
		setBorder(new EmptyBorder(5, 5, 5, 5));
		setLayout(null);

		// Variable para saber el turno del jugador
		int turno = 0;
		try {
			// Se intenta ver si hay partidas sin terminar
			Archivo.manejo(Reanudar.value);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Fondo
		this.imgBackground = new ImageIcon(("src\\recursos\\fondo_juego.jpg")).getImage();

		// Si se selecciono una para reanudar una partida, se inicia el juego en esa
		// partida
		if (Reanudar.valor == true) {
			// Se guarda el nombre de la partida que se desea reanudar
			String aux = Reanudar.value;
			// el turno se guardo en el nombre de la partida, entoces mediante pa variable
			// turno se establece de quien es
			turno = Character.getNumericValue(aux.charAt(aux.length() - 5));
			// Se modifica la variable del nombre del archivo, para hacer las posteriores
			// actualizaciones del archivo
			Panel_Nueva.nameAr = aux.substring(0, aux.length() - 6);
			// Se crea una variable auxiliar para poder tomar los nombres y añadirlos
			String string = Panel_Nueva.nameAr;
			String[] parts = string.split("_");
			Panel_Nueva.player1 = parts[0];
			Panel_Nueva.player2 = parts[1];
			// Se crea inicializa el tablero con los datos cargados
			this.chessGame = new Tablero(Archivo.getCarga());
			// Se establece el turno segun corresponda
			Tablero.setGameState(turno);
			// Reinicia la variable
			Reanudar.valor = false;
		} else {
			// De no querer reanudar una partida, se inicializa el tablero por defecto
			this.chessGame = new Tablero();
		}

		// Recorre el arreglo de las fichas y las manda al metodo
		for (Fichas piece : this.chessGame.getPieces()) {
			createAndAddGuiPiece(piece);
		}

		// Se añaden las funciones de arrastrar y soltar
		Presionar listener = new Presionar(this.guiPieces, this);
		this.addMouseListener(listener);
		this.addMouseMotionListener(listener);

		// boton salir
		this.btnSalir = new JButton();
		btnSalir.setBounds(10, 30, 90, 80);
		btnSalir.setCursor(new Cursor(java.awt.Cursor.HAND_CURSOR));
		btnSalir.setIcon(setIcono("src\\recursos\\botonX.png", btnSalir));
		setTransparencia(btnSalir);
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		this.add(btnSalir);

		// Etiqueta del nombre del jugador 1
		JLabel jLabelP1 = new JLabel("Jugador 1: " + Panel_Nueva.player1);
		jLabelP1.setFont(new Font("Times New Roman", Font.BOLD, 22));
		jLabelP1.setBounds(31, 548, 286, 37);
		add(jLabelP1);

		// Etiqueta del nombre del jugador 2
		JLabel jLabelP2 = new JLabel("Jugador 2: " + Panel_Nueva.player2);
		jLabelP2.setFont(new Font("Times New Roman", Font.BOLD, 22));
		jLabelP2.setBounds(31, 183, 286, 37);
		add(jLabelP2);

		// Se inicializa la etiqueta que muestra el turno
		JTurno = new JLabel("");
		JTurno.setText(this.getGameStateAsText());
		JTurno.setFont(new Font("Times New Roman", Font.BOLD, 17));
		JTurno.setBounds(577, 681, 200, 28);
		add(JTurno);

		// Se inicializa la etiqueta que muestra el ganador
		JGanador = new JLabel((String) null);
		JGanador.setFont(new Font("Times New Roman", Font.BOLD, 17));
		JGanador.setBounds(975, 270, 281, 28);
		add(JGanador);

		// Panel de fichas Blancas
		// definicion del panel blanco
		panelB = new JPanel();
		panelB.setBackground(Color.WHITE);
		panelB.setBounds(978, 446, 275, 275);
		panelB.setLayout(null);
		this.add(panelB);
		panelB.setVisible(false);
		// Definicion de los botones para las fichas blancas
		btnTorreB = new JButton();
		btnCaballoB = new JButton();
		btnAlfilB = new JButton();
		btnReinaB = new JButton();
		// boton de la torre blanca
		btnTorreB.setBounds(10, 10, 120, 120);
		btnTorreB.setIcon(setIcono("src\\recursos\\fichas\\wr.png", btnTorreB));
		btnTorreB.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		btnTorreB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				cambio(1);
			}
		});
		setTransparencia(btnTorreB);
		panelB.add(btnTorreB);
		// boton del caballo blanco
		btnCaballoB.setBounds(134, 10, 120, 120);
		btnCaballoB.setIcon(setIcono("src\\recursos\\fichas\\wn.png", btnCaballoB));
		btnCaballoB.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		btnCaballoB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				cambio(2);
			}
		});
		setTransparencia(btnCaballoB);
		panelB.add(btnCaballoB);
		// boton del alfil blanco
		btnAlfilB.setBounds(10, 134, 120, 120);
		btnAlfilB.setIcon(setIcono("src\\recursos\\fichas\\wb.png", btnAlfilB));
		btnAlfilB.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		btnAlfilB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				cambio(3);
			}
		});
		setTransparencia(btnAlfilB);
		panelB.add(btnAlfilB);
		// boton de la reina blanca
		btnReinaB.setBounds(134, 134, 120, 120);
		btnReinaB.setIcon(setIcono("src\\recursos\\fichas\\wq.png", btnReinaB));
		btnReinaB.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		btnReinaB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				cambio(4);
			}
		});
		setTransparencia(btnReinaB);
		panelB.add(btnReinaB);

		// Panel de fichas Negras
		panelN = new JPanel();
		panelN.setBackground(Color.WHITE);
		panelN.setBounds(978, 446, 275, 275);
		panelN.setLayout(null);
		this.add(panelN);
		panelN.setVisible(false);

		// Definicion de los botones para las fichas negras
		btnTorreN = new JButton();
		btnCaballoN = new JButton();
		btnAlfilN = new JButton();
		btnReinaN = new JButton();
		// boton de la torre blanca
		btnTorreN.setBounds(10, 10, 120, 120);
		btnTorreN.setIcon(setIcono("src\\recursos\\fichas\\br.png", btnTorreN));
		btnTorreN.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		btnTorreN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				cambio(1);
			}
		});
		setTransparencia(btnTorreN);
		panelN.add(btnTorreN);
		// boton del caballo negro
		btnCaballoN.setBounds(134, 10, 120, 120);
		btnCaballoN.setIcon(setIcono("src\\recursos\\fichas\\bn.png", btnCaballoN));
		btnCaballoN.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		btnCaballoN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				cambio(2);
			}
		});
		setTransparencia(btnCaballoN);
		panelN.add(btnCaballoN);
		// boton del alfil negro
		btnAlfilN.setBounds(10, 134, 120, 120);
		btnAlfilN.setIcon(setIcono("src\\recursos\\fichas\\bb.png", btnAlfilN));
		btnAlfilN.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		btnAlfilN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				cambio(3);
			}
		});
		setTransparencia(btnAlfilN);
		panelN.add(btnAlfilN);
		// boton de la reina negro
		btnReinaN.setBounds(134, 134, 120, 120);
		btnReinaN.setIcon(setIcono("src\\recursos\\fichas\\bq.png", btnReinaN));
		btnReinaN.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		btnReinaN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				cambio(4);
			}
		});
		setTransparencia(btnReinaN);
		panelN.add(btnReinaN);

	}

	// Metodo para cambiar a un peon cuando corona
	private void cambio(int tipo) {
		for (int i = Tablero.pieces.size() - 1; i >= 0; i--) {
			if (Tablero.pieces.get(i).getType() == 6 && Tablero.pieces.get(i).getColor() == 0
					&& Tablero.pieces.get(i).getRow() == 7) {
				for (int j = Presionar.guiPieces.size() - 1; j >= 0; j--) {
					if (Presionar.guiPieces.get(j).getPiece().getRow() == 7
							&& Presionar.guiPieces.get(j).getPiece().getType() == 6
							&& Presionar.guiPieces.get(j).getPiece().getColor() == 0) {
						Tablero.pieces.get(i).setType(tipo);
						Presionar.guiPieces.get(j).setImg(getImageForPiece(0, tipo));
						Presionar.guiPieces.get(j).getPiece().setType(tipo);
						Validar.coronarB = false;
					}
				}
				panelB.setVisible(false);
			} else if (Tablero.pieces.get(i).getType() == 6 && Tablero.pieces.get(i).getColor() == 1
					&& Tablero.pieces.get(i).getRow() == 0) {
				for (int j = Presionar.guiPieces.size() - 1; j >= 0; j--) {
					if (Presionar.guiPieces.get(j).getPiece().getRow() == 0
							&& Presionar.guiPieces.get(j).getPiece().getType() == 6
							&& Presionar.guiPieces.get(j).getPiece().getColor() == 1) {
						Tablero.pieces.get(i).setType(tipo);
						Presionar.guiPieces.get(j).setImg(getImageForPiece(1, tipo));
						Presionar.guiPieces.get(j).getPiece().setType(tipo);
						Validar.coronarN = false;
					}
				}
				panelN.setVisible(false);
			}
		}
		btnSalir.setEnabled(true);
		Tablero.mane();
		Tablero.setGameState(Tablero.aux);
		this.repaint();
	}

	/**
	 * @return Descripcion del estado del juego
	 */
	// Metodo para ponerle el texto al label de turnos
	private String getGameStateAsText() {
		String state = "unknown";
		switch (this.chessGame.getGameState()) {
		case Tablero.GAME_STATE_BLACK:
			state = "Turno de las negras";
			break;
		case Tablero.GAME_STATE_END:

			state = "FIN DEL JUEGO";
			if (Tablero.aux == 0) {
				JGanador.setText("HA GANADO EL JUGADOR 1");
			} else {
				JGanador.setText("HA GANADO EL JUGADOR 2");
			}
			break;
		case Tablero.GAME_STATE_WHITE:
			state = "Turno de las blancas";
			break;
		case 3:
			state = "Pausa";
			break;
		}
		return state;
	}

	/**
	 * Crea las piezas de juego
	 *
	 * @param color color constante
	 * @param type  type constante
	 * @param x     x posicion de la izquina superior izquierda
	 * @param y     y posicion de la izquina superior izquierda
	 */

	// Metodo que añade y crea las fichas de juego
	private void createAndAddGuiPiece(Fichas piece) {
		Image img = this.getImageForPiece(piece.getColor(), piece.getType());
		SFicha guiPiece = new SFicha(img, piece);
		this.guiPieces.add(guiPiece);
	}

	/**
	 * Carga la imagen dependiendo del color y el tipo. Este metodo convierte el
	 * color y el tipo en informacion en un archivo y carga dicho archivo.
	 *
	 * @param color color constante
	 * @param type  type constante
	 * @return imagen
	 */
	// Metodo que crea la direccion de las imagenes dependiendo del color y el tipo
	// de ficha
	private Image getImageForPiece(int color, int type) {

		String filename = "";

		filename += (color == Fichas.COLOR_WHITE ? "w" : "b");
		switch (type) {
		case Fichas.TYPE_BISHOP:
			filename += "b";
			break;
		case Fichas.TYPE_KING:
			filename += "k";
			break;
		case Fichas.TYPE_KNIGHT:
			filename += "n";
			break;
		case Fichas.TYPE_PAWN:
			filename += "p";
			break;
		case Fichas.TYPE_QUEEN:
			filename += "q";
			break;
		case Fichas.TYPE_ROOK:
			filename += "r";
			break;
		}
		filename += ".png";

		String urlPieceImg = ("src\\recursos\\fichas\\" + filename);
		return new ImageIcon(urlPieceImg).getImage();
	}

	@Override
	protected void paintComponent(Graphics g) {

		// Pinta el fondo
		g.drawImage(this.imgBackground, 0, 0, null);

		// Dibuja las fichas del arreglo
		for (SFicha guiPiece : this.guiPieces) {
			if (!guiPiece.isCaptured()) {
				g.drawImage(guiPiece.getImage(), guiPiece.getX(), guiPiece.getY(), null);

			}
		}

		// Dibuja las posiciones que son validas de la ficha que se este arrastrando
		if (isUserDraggingPiece()) {

			Validar moveValidator = this.chessGame.getMoveValidator();

			// iterate the complete board to check if target locations are valid
			for (int column = Fichas.COLUMN_A; column <= Fichas.COLUMN_H; column++) {
				for (int row = Fichas.ROW_1; row <= Fichas.ROW_8; row++) {
					int sourceRow = this.dragPiece.getPiece().getRow();
					int sourceColumn = this.dragPiece.getPiece().getColumn();

					// check if target location is valid
					if (moveValidator.isMoveValid(new Movimiento(sourceRow, sourceColumn, row, column))) {

						int highlightX = convertColumnToX(column);
						int highlightY = convertRowToY(row);

						// draw a black drop shadow by drawing a black rectangle with an offset of 1
						// pixel
						g.setColor(Color.BLACK);
						g.drawRoundRect(highlightX - 2, highlightY - 2, SQUARE_WIDTH - 8, SQUARE_HEIGHT - 8, 10, 10);
						// draw the highlight
						g.setColor(Color.GREEN);
						g.drawRoundRect(highlightX - 3, highlightY - 3, SQUARE_WIDTH - 8, SQUARE_HEIGHT - 8, 10, 10);
					}
				}
			}

		}
		// Se cambia el turno
		JTurno.setText(this.getGameStateAsText());
	}

	// Metodo para validar si un peon corono
	public void coronar() {
		if (this.dragPiece.tipo() == 6 && Validar.coronarB == true) {
			if (this.dragPiece.getPiece().getRow() == 7) {
				btnSalir.setEnabled(false);
				Tablero.setGameState(3);
				panelB.setVisible(true);
			}

		}
		if (this.dragPiece.tipo() == 6 && Validar.coronarN == true) {
			if (this.dragPiece.getPiece().getRow() == 0) {
				btnSalir.setEnabled(false);
				Tablero.setGameState(3);
				panelN.setVisible(true);
			}
		}
		if (Validar.coronarB == false && Validar.coronarN == false) {
			panelB.setVisible(false);
			panelN.setVisible(false);
		}
	}

	/**
	 * @return Da verdadero si se esta arrastrando una ficha
	 */

	// Metodo para saber si se esta arrastrando una ficha
	private boolean isUserDraggingPiece() {
		return this.dragPiece != null;
	}

	/**
	 * @return El estado actual del juego
	 */
	public int getGameState() {
		return this.chessGame.getGameState();
	}

	/**
	 * Convierte una columna a su posicion en x
	 *
	 * @param column
	 * @return x coordenada para la columna
	 */

	// Metodo que convierte una columna a su valor en x
	public static int convertColumnToX(int column) {
		return PIECES_START_X + SQUARE_WIDTH * column;
	}

	/**
	 * Convierte una fila a su posicion en y
	 *
	 * @param row
	 * @return y coordenada para la fila
	 */
	public static int convertRowToY(int row) {
		return PIECES_START_Y + SQUARE_HEIGHT * (Fichas.ROW_8 - row);
	}

	/**
	 * Convierte un valor de X en una columna
	 *
	 * @param x
	 * @return columna para un valor x coordenada
	 */
	public static int convertXToColumn(int x) {
		return (x - DRAG_TARGET_SQUARE_START_X) / SQUARE_WIDTH;
	}

	/**
	 * Convierte un valor de Y en una fila
	 *
	 * @param y
	 * @return fila para un valor Y coordenada
	 */
	public static int convertYToRow(int y) {
		return Fichas.ROW_8 - (y - DRAG_TARGET_SQUARE_START_Y) / SQUARE_HEIGHT;
	}

	/**
	 * Cambia la ubicacion de la pieza seleccionada si es valida. Si la ubicacion no
	 * es valida, mueve la pieza a su ubicacion orginal.
	 *
	 * @param dragPiece
	 * @param x
	 * @param y
	 */
	public void setNewPieceLocation(SFicha dragPiece, int x, int y) {
		int targetRow = Juego.convertYToRow(y);
		int targetColumn = Juego.convertXToColumn(x);

		if (targetRow < Fichas.ROW_1 || targetRow > Fichas.ROW_8 || targetColumn < Fichas.COLUMN_A
				|| targetColumn > Fichas.COLUMN_H) {
			// Restablece la posicion de la ficha si el movimiento no es valido
			dragPiece.resetToUnderlyingPiecePosition();

		} else {
			// Cambia el movimiento y repinta la ficha

			Movimiento move = new Movimiento(dragPiece.getPiece().getRow(), dragPiece.getPiece().getColumn(), targetRow,
					targetColumn);
			boolean wasMoveSuccessfull = this.chessGame.movePiece(move);

			dragPiece.resetToUnderlyingPiecePosition();
		}
	}

	/**
	 * @param guiPiece - Determina que pieza de SFicha se esta arrastrando
	 *                 actualmente
	 */
	public void setDragPiece(SFicha guiPiece) {

		this.dragPiece = guiPiece;
	}

	/**
	 * @return la pieza que se esta arrastrando de SFicha
	 */
	public SFicha getDragPiece() {
		return this.dragPiece;
	}

	private Icon setIcono(String url, JButton boton) {// retorna un icono reescalado al tamaÃ±o del boton
		ImageIcon icon = new ImageIcon(url);
		int anch = boton.getWidth();
		int alt = boton.getHeight();
		ImageIcon icono = new ImageIcon(icon.getImage().getScaledInstance(anch, alt, Image.SCALE_DEFAULT));
		return icono;
	}

	private void setTransparencia(JButton boton) {// modifica un boton haciendolo transparente
		boton.setOpaque(false);
		boton.setContentAreaFilled(false);
		boton.setBorderPainted(false);
	}
}
