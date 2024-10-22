/**
	 * Juego de Ajedrez.
	 * @author  Oscar Rojas-Juan Aguilar-Sergio Sierra
	 * @version 1.0
	 * @since   2021-02-07
*/
package gui;

import java.awt.Color;
import java.awt.Font;
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
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class Reanudar extends JPanel {

	// Declaracion de parametros del panel reanudar
	JPanel panel = new JPanel();
	private JList list;
	public static boolean valor = false;

	public static String value;

	/**
	 * Crea el panel Reanudar
	 */
	public Reanudar() {

		// Define los parametros del panel
		int ancho = 1280;
		int alto = 720;
		Panel_Menu ini = new Panel_Menu();// crea un panel menu
		setBounds(0, 0, ancho, alto);
		setBorder(new EmptyBorder(5, 5, 5, 5));
		setLayout(null);
		panel.setBounds(0, 0, ancho, alto);
		panel.setLayout(null);
		add(panel);

		// agrega el fondo al panel
		Image img1 = new ImageIcon("src\\recursos\\fondo_nueva.png").getImage();
		ImageIcon imagen_1 = new ImageIcon(img1.getScaledInstance(1280, 720, Image.SCALE_SMOOTH));

		// crea y agrega un boton a el panel reanudar para retroceder al panel menu
		JButton botonRetro = new JButton("");
		botonRetro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				sonidoBoton();
				add(ini);
				remove(panel);
				repaint();
			}
		});
		botonRetro.setBounds(10, 0, 200, 142);
		botonRetro.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		botonRetro.setIcon(setIcono("src\\recursos\\botonFlecha.png", botonRetro));
		botonRetro.setOpaque(false);
		botonRetro.setContentAreaFilled(false);
		botonRetro.setBorderPainted(false);
		panel.add(botonRetro);

		// crea un label y lo agrega al panel
		JLabel jLabelLetrero = new JLabel("REANUDAR PARTIDA");
		jLabelLetrero.setForeground(Color.BLACK);
		jLabelLetrero.setHorizontalAlignment(SwingConstants.CENTER);
		jLabelLetrero.setFont(new Font("Times New Roman", jLabelLetrero.getFont().getStyle(),
				jLabelLetrero.getFont().getSize() + 60));
		jLabelLetrero.setBounds(0, 0, 1360, 121);
		panel.add(jLabelLetrero);

		// crea una lista que guardara las partidas anteriores sin terminar y las
		// mostrara en el panel
		list = new JList();
		list.setOpaque(false);
		list.setBounds(64, 196, 670, 324);
		panel.add(list);
		cargarF();
		list.setOpaque(false);
		DefaultListCellRenderer renderer = new DefaultListCellRenderer();
		renderer.setOpaque(false);
		list.setCellRenderer(renderer);

		// crea y agrega un boton jugar para reanudar la partida seleccionada
		JButton jBotonJugar = new JButton("");
		jBotonJugar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (list.getSelectedValue() == null) {
					JOptionPane.showMessageDialog(null, "No eligió ninguna partida", "ERROR",
							JOptionPane.PLAIN_MESSAGE);
				} else {

					valor = true;
					value = list.getSelectedValue().toString();

					sonidoBoton();
					Panel_Juego pa = new Panel_Juego();
					add(pa);
					remove(panel);
					repaint();
				}
			}
		});
		jBotonJugar.setBounds(465, 568, 350, 100);
		jBotonJugar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		jBotonJugar.setIcon(setIcono("src\\recursos\\jugar.png", jBotonJugar));
		jBotonJugar.setOpaque(false);
		jBotonJugar.setContentAreaFilled(false);
		jBotonJugar.setBorderPainted(false);
		panel.add(jBotonJugar);

		// fondo
		JLabel imagen1 = new JLabel(imagen_1);
		imagen1.setBounds(0, 0, ancho, alto);
		panel.add(imagen1);

	}

	/**
	 * lee los archivos ubicado en la carpeta partidas y las agrega a la lista de
	 * partidas
	 * 
	 */
	public void cargarF() {
		File carpeta = new File("src\\data\\partidas");
		File[] archivos = carpeta.listFiles();
		if (archivos == null || archivos.length == 0) {
			DefaultListModel modelo = new DefaultListModel();
			for (int i = 1; i <= 1; i++) {
				modelo.addElement("No hay partidas");
			}
			list.setModel(modelo);
			return;
		} else {
			DefaultListModel modelo = new DefaultListModel();
			for (int i = 0; i < archivos.length; i++) {
				File archivo = archivos[i];
				modelo.addElement(archivo.getName());
			}
			list.setModel(modelo);
		}
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

	// setters y getters de la variable Valor
	public static boolean isValor() {
		return valor;
	}

	public static void setValor(boolean valor) {
		Reanudar.valor = valor;
	}
}
