/**
	 * Juego de Ajedrez.
	 * @author  Oscar Rojas-Juan Aguilar-Sergio Sierra
	 * @version 1.0
	 * @since   2021-02-07
*/
package gui;

// Se importan las librerias
import java.awt.Color;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

// Se importa la clase que se encuentra en el paquete aplicacion
import aplicacion.Juego;

// Panel donde se mostrara el juego
public class Panel_Juego extends JPanel {

	// Se crea un panel
	JPanel panel = new JPanel();

	/**
	 * Crea el panel juego
	 */
	public Panel_Juego() {
		// Se crean las dimensiones del frame
		int ancho = 1280;
		int alto = 720;
		// Se establecen las dimensiones del panel
		setBounds(0, 0, ancho, alto);
		setBorder(new EmptyBorder(5, 5, 5, 5));
		setBackground(Color.WHITE);
		setLayout(null);

		// Se inicializa el otro panel
		panel.setBounds(0, 0, ancho, alto);
		panel.setLayout(null);
		add(panel);

		// se crea una instancia de la clase que se importo del paquete juego
		Juego j = new Juego();
		// Se remueve el panel actual
		remove(panel);
		// Se añade el panel del juego
		add(j);
		// Se repinta
		repaint();
	}
}
