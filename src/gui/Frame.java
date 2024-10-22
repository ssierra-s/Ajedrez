/**
	 * Juego de Ajedrez.
	 * @author  Oscar Rojas-Juan Aguilar-Sergio Sierra
	 * @version 1.0
	 * @since   2021-02-07
*/
package gui;

// Se importan las librerias 
import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

// Frame inicial de la aplicaccion
public class Frame extends JFrame {

	private JPanel contentPane;
	public JPanel panel = new JPanel();

	/**
	 * Lanza la aplicacion
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Frame frame = new Frame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Crea el frame inicial
	 */

	// Constructor
	public Frame() {
		// Se inhabilita el redimensionamiento del frame
		setResizable(false);
		// Se crean las dimensiones del frame
		int ancho = 1280;
		int alto = 720;
		// Se crea una instancia del panel menu
		Panel_Menu men = new Panel_Menu();

		// Titulo de la aplicacion
		setTitle("Ajedrez");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// Se establecen las dimensiones del frame
		setBounds(100, 100, ancho, alto);
		// Se crea un panel
		contentPane = new JPanel();

		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		// Se establece la ventana
		setUndecorated(true);

		// Se define el layout nulo
		contentPane.setLayout(null);

		// Se crea el panel donde se anadira los otros paneles
		panel.setBounds(0, 0, ancho, alto);
		panel.setLayout(null);
		contentPane.add(panel);

		// Se anade el panel de Panel_Menu
		panel.add(men);

		// Icono de la aplicacion
		Image ic = new ImageIcon(("src\\recursos\\icono.png")).getImage();
		setIconImage(ic);
		setLocationRelativeTo(null);
	}

}
