/**
	 * Juego de Ajedrez.
	 * @author  Oscar Rojas-Juan Aguilar-Sergio Sierra
	 * @version 1.0
	 * @since   2021-02-07
*/
package gui;

// Importa las librerias
import javax.swing.JPanel;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;

import java.awt.Color;
import java.awt.Image;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JTextPane;

// Panel que tiene las instrucciones del juego
public class Panel_Instr extends JPanel {

	// Se crea un panel
	JPanel panel = new JPanel();

	/**
	 * Crea el panel de instrucciones.
	 */
	public Panel_Instr() {
		// Se crean las dimensiones del panel
		int ancho = 1280;
		int alto = 720;
		// Se crea una instancia del panel menu
		Panel_Menu ini = new Panel_Menu();

		// Se establecen las dimensiones del panel
		setBounds(0, 0, ancho, alto);
		setBorder(new EmptyBorder(5, 5, 5, 5));
		setBackground(Color.WHITE);
		// Se establece el layout nulo
		setLayout(null);
		// Se crea una variable que contiene el fondo del panel
		Image img1 = new ImageIcon("src\\recursos\\fondoIns.png").getImage();
		// Se redimensiona
		ImageIcon imagen_1 = new ImageIcon(img1.getScaledInstance(1280, 720, Image.SCALE_SMOOTH));

		// Se inicializa el panel
		panel.setBounds(0, 0, ancho, alto);
		panel.setLayout(null);
		add(panel);

		// Se crea el boton para retroceder al panel anterior
		JButton botonRetro = new JButton("");
		// Accion al presionar el boton
		botonRetro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// Se envia al metodo que reproduce el sonido al oprimir el boton
				sonidoBoton();
				// Se añade en el panel, el panel menu y se remueve el panel actual
				add(ini);
				remove(panel);
				repaint();
			}
		});
		botonRetro.setBounds(10, 0, 200, 142);
		// Se establece un cursor al boton
		botonRetro.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		// Se añade la imagen del boton al mismo
		botonRetro.setIcon(setIcono("src\\recursos\\botonFlecha.png", botonRetro));
		// Se modifican parametros para que el boton sea transparente
		botonRetro.setOpaque(false);
		botonRetro.setContentAreaFilled(false);
		botonRetro.setBorderPainted(false);
		// Se añade el boton
		panel.add(botonRetro);

		// Se crea el panel de texto en el que se escribiran las instrucciones del juego
		JTextPane panelTexto = new JTextPane();
		panelTexto.setFont(
				new Font("Segoe UI Emoji", panelTexto.getFont().getStyle(), panelTexto.getFont().getSize() + 10));
		panelTexto.setForeground(Color.BLACK);
		// Se establece el texto con las instrucciones
		panelTexto.setText(
				"El tablero de ajedrez es un cuadrado dividido en 64 casillas denominadas escaques) tambien cuadradas, con distribuci\u00F3n 8 x 8, alternativamente claras y oscuras. Las casillas o escaques de color claro se llaman blancas y las de color oscuro negras. El tablero se coloca entre los jugadores de forma que la casilla de la esquina derecha m\u00E1s cercana a cada jugador sea de color blanco. Las ocho l\u00EDneas verticales de escaques se denominan columnas y las ocho l\u00EDneas horizontales de escaques se denominan filas. Se denomina diagonal a una sucesi\u00F3n de escaques del mismo color en l\u00EDnea recta que va desde un borde del tablero hasta otro adyacente. \r\n1.El rey se puede mover en cualquier direcci\u00F3n (vertical, horizontal y diagonales) avanzando o retrocediendo una sola casilla (excepto en el enroque, en el cual se mueve dos casillas. \r\n2. La reina o dama tambi\u00E9n se puede mover en cualquier direcci\u00F3n (vertical, horizontal y diagonales) avanzando o retrocediendo en el tablero el n\u00FAmero de casillas que se desee, hasta topar con otra pieza o el borde del tablero. \r\n3. El alfil solo se puede mover en direcci\u00F3n diagonal, tantas casillas como se desee. \"\r\n4. La torre solo se puede mover en las direcciones verticales y horizontales, no en diagonal, las casillas que se desee. \"\r\n5.El caballo, seg\u00FAn la definici\u00F3n oficial, se puede mover a la casilla m\u00E1s cercana que no se encuentre en su propia fila, columna o diagonal, aunque para simplificar se dice que se mueve avanzando dos casillas en vertical y una en horizontal, o viceversa, realizando un movimiento de 'L', siendo la \u00FAnica pieza que puede saltar por encima de las dem\u00E1s piezas. \"\r\n6.El pe\u00F3n puede avanzar una o dos casillas en direcci\u00F3n vertical en su primer movimiento, despu\u00E9s de adelantado por primera vez solo puede avanzar una casilla, a diferencia del resto de piezas no puede ir hacia atr\u00E1s y no puede capturar las piezas contrarias que se encuentran en la misma direcci\u00F3n en la que se mueve, solo podr\u00E1 hacerlo si se encuentran a una casilla en diagonal respecto a \u00E9l (excepto en la toma de pe\u00F3n al paso). Un pe\u00F3n tiene la capacidad de transformarse en la pieza que su jugador desee (normalmente en dama) si es capaz de alcanzar la \u00FAltima fila del tablero opuesta a la de su bando, por lo que un pe\u00F3n puede adquirir un enorme poder t\u00E1ctico en determinadas posiciones donde no tiene oposici\u00F3n para \\\"coronar\\\" (pe\u00F3n pasado).\r\n\t\t");
		// No se puede editar el panel de texto
		panelTexto.setEditable(false);
		// Dimensiones
		panelTexto.setBounds(54, 169, 1170, 540);
		// Sea transparente
		panelTexto.setOpaque(false);
		panelTexto.setBackground(Color.BLACK);
		panelTexto.setBorder(BorderFactory.createEmptyBorder());
		panel.add(panelTexto);

		// Etiqueta de encabezado del panel
		JLabel jLabelLetrero = new JLabel("INSTRUCCIONES");
		jLabelLetrero.setForeground(Color.BLACK);
		jLabelLetrero.setHorizontalAlignment(SwingConstants.CENTER);
		jLabelLetrero.setFont(new Font("Times New Roman", jLabelLetrero.getFont().getStyle(),
				jLabelLetrero.getFont().getSize() + 80));
		jLabelLetrero.setBounds(0, 0, 1360, 121);
		panel.add(jLabelLetrero);

		// Se añade el fondo mediante un label
		JLabel imagen1 = new JLabel(imagen_1);
		imagen1.setBounds(0, 0, ancho, alto);
		panel.add(imagen1);
	}

	/**
	 * Pone el icono a un boton
	 *
	 * @param boton
	 * @param url
	 * @return un icono reescalado al tamano del boton
	 */

	private Icon setIcono(String url, JButton boton) {
		ImageIcon icon = new ImageIcon(url);
		int anch = boton.getWidth();
		int alt = boton.getHeight();
		ImageIcon icono = new ImageIcon(icon.getImage().getScaledInstance(anch, alt, Image.SCALE_DEFAULT));
		return icono;
	}

	/**
	 * Reproduce el sonido al ser oprimido un boton
	 *
	 */
	public void sonidoBoton() {
		try {
			AudioInputStream audioInputStream = AudioSystem
					.getAudioInputStream(new File("src/recursos/audioBoton.wav").getAbsoluteFile());
			Clip clip = AudioSystem.getClip();
			clip.open(audioInputStream);
			clip.start();
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
			System.out.println("Error al reproducir el sonido.");
		}
	}

}
