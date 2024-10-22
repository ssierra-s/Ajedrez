/**
	 * Juego de Ajedrez.
	 * @author  Oscar Rojas-Juan Aguilar-Sergio Sierra
	 * @version 1.0
	 * @since   2021-02-07
*/
package gui;

// Se importan las librerias
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class Panel_Menu extends JPanel {

	// Se crea un panel
	JPanel panel = new JPanel();

	/**
	 * Crea el panel menu
	 */
	public Panel_Menu() {
		// Se crean las dimensiones del frame
		int ancho = 1280;
		int alto = 720;
		// Se establecen las dimensiones del panel
		setBounds(0, 0, ancho, alto);
		setBorder(new EmptyBorder(5, 5, 5, 5));
		setLayout(null);
		// Se inicializa el otro panel
		panel.setBounds(0, 0, ancho, alto);
		panel.setLayout(null);
		add(panel);
		// Se crea una variable que contiene el fondo del panel
		Image img1 = new ImageIcon("src\\recursos\\fondo.png").getImage();
		// Se redimensiona
		ImageIcon imagen_1 = new ImageIcon(img1.getScaledInstance(1280, 720, Image.SCALE_SMOOTH));

		// Boton para salir de la aplicacion
		JButton botonSalir = new JButton("");
		// Se modifica el cursor del boton
		botonSalir.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		botonSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// Se envia al metodo que reproduce el sonido al oprimir el boton
				sonidoBoton();
				// Se cierra la aplicacion
				System.exit(0);
			}
		});
		botonSalir.setBounds(550, 640, 180, 76);
		// Redimensiona el boton y le añade la imagen
		botonSalir.setIcon(setIcono("src\\recursos\\salir.png", botonSalir));
		// Envia el boton al metodo donde se ponen los parametros para que sea
		// transparente
		setTransparencia(botonSalir);
		panel.add(botonSalir);

		// Boton para ir al panel de instrucciones
		JButton botonInst = new JButton();
		botonInst.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		botonInst.setBounds(1220, 11, 60, 60);
		botonInst.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				// Se envia al metodo que reproduce el sonido al oprimir el boton
				sonidoBoton();
				// Se crea el panel de instrucciones y se añade, par aluego eliminar el panel
				// actual
				Panel_Instr ints = new Panel_Instr();
				remove(panel);
				add(ints);
				// Repinta
				repaint();
			}
		});
		botonInst.setIcon(setIcono("src\\recursos\\inst.png", botonInst));
		setTransparencia(botonInst);
		panel.add(botonInst);

		// Boton para ir al panel de tabla de jugadores
		JButton botonTabla = new JButton("");
		botonTabla.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		botonTabla.setBounds(550, 540, 180, 76);
		botonTabla.setIcon(setIcono("src\\recursos\\tabla.png", botonTabla));
		botonTabla.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				// Se envia al metodo que reproduce el sonido al oprimir el boton
				sonidoBoton();
				// Se crea el panel de tabla y se añade, par aluego eliminar el panel
				// actual
				Panel_Tabla tabla = new Panel_Tabla();
				remove(panel);
				add(tabla);
				// Repinta
				repaint();
			}
		});
		setTransparencia(botonTabla);
		panel.add(botonTabla);

		// Boton para ir al panel de reanudar partida
		JButton botonReanudar = new JButton("");
		botonReanudar.setBounds(550, 340, 180, 76);

		// Se crea una variable para mirar si hay partidas sin terminar
		File carpeta = new File("src\\data\\partidas");
		File[] archivos = carpeta.listFiles();

		// Si no hay partidas el boton se crea con una imagen mas opaca
		if (archivos == null || archivos.length == 0) {
			botonReanudar.setIcon(setIcono("src\\recursos\\reanudarOp.png", botonReanudar));

		} else {
			// De lo contrario se deja una imagen mas nitida y se crea una funcion al ser
			// presionado
			botonReanudar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
			botonReanudar.setIcon(setIcono("src\\recursos\\reanudar.png", botonReanudar));
			botonReanudar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					// Se envia al metodo que reproduce el sonido al oprimir el boton
					sonidoBoton();
					// Se crea el panel de reanudar y se añade, par aluego eliminar el panel
					// actual
					Reanudar rea = new Reanudar();
					remove(panel);
					add(rea);
					repaint();
				}
			});
		}
		setTransparencia(botonReanudar);
		panel.add(botonReanudar);

		// Boton para ir al panel de nueva partida
		JButton botonNueva = new JButton("");
		botonNueva.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Se envia al metodo que reproduce el sonido al oprimir el boton
				sonidoBoton();
				// Se crea el panel de reanudar y se añade, par aluego eliminar el panel
				// actual
				Panel_Nueva nue = new Panel_Nueva();
				remove(panel);
				add(nue);
				repaint();
			}
		});
		botonNueva.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		botonNueva.setBounds(550, 440, 180, 76);
		botonNueva.setIcon(setIcono("src\\recursos\\nueva.png", botonNueva));
		setTransparencia(botonNueva);
		panel.add(botonNueva);

		// Se añade el fondo mediante un label
		JLabel imagen1 = new JLabel(imagen_1);
		imagen1.setBounds(0, 0, ancho, alto);
		panel.add(imagen1);

	}

	/**
	 * Pone el icono a un boton
	 *
	 * @param boton boton
	 * @param url   url
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
	 * Modifica un boton haciendolo transparente
	 *
	 * @param boton boton
	 */
	private void setTransparencia(JButton boton) {
		boton.setOpaque(false);
		boton.setContentAreaFilled(false);
		boton.setBorderPainted(false);
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