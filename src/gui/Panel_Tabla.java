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
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import logica.ArchivoTabla;

public class Panel_Tabla extends JPanel {

	// Declaracion de parametros del panel tabla
	JPanel panel = new JPanel();
	private JTable table;

	/**
	 * Crea el panel tabla
	 */
	public Panel_Tabla() {
		// Define los parametros del panel
		int ancho = 1280;
		int alto = 720;
		Panel_Menu ini = new Panel_Menu();// crea un panel de inicio
		setBounds(0, 0, ancho, alto);
		setBorder(new EmptyBorder(5, 5, 5, 5));
		setLayout(null);
		panel.setBounds(0, 0, ancho, alto);
		panel.setLayout(null);
		add(panel);

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
		JLabel jLabelLetrero = new JLabel("TABLA DE JUGADORES");
		jLabelLetrero.setForeground(Color.BLACK);
		jLabelLetrero.setHorizontalAlignment(SwingConstants.CENTER);
		jLabelLetrero.setFont(new Font("Times New Roman", jLabelLetrero.getFont().getStyle(),
				jLabelLetrero.getFont().getSize() + 60));
		jLabelLetrero.setBounds(0, 0, 1360, 121);
		panel.add(jLabelLetrero);

		// crea un Jtable y lo agrega al panel
		table = new JTable();
		table.setRowSelectionAllowed(false);
		table.setEnabled(false);
		table.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "New column", "New column", "New column", "New column" }));

		table.setBounds(30, 153, 1220, 417);
		TableColumn columna;// modifica todas las columnas para que sean igual
		columna = table.getColumnModel().getColumn(0);
		columna.setPreferredWidth(305);
		columna.setMaxWidth(305);
		columna.setMinWidth(305);
		columna = table.getColumnModel().getColumn(1);
		columna.setPreferredWidth(305);
		columna.setMaxWidth(305);
		columna.setMinWidth(305);
		columna = table.getColumnModel().getColumn(2);
		columna.setPreferredWidth(305);
		columna.setMaxWidth(305);
		columna.setMinWidth(305);
		columna = table.getColumnModel().getColumn(3);
		columna.setPreferredWidth(305);
		columna.setMaxWidth(305);
		columna.setMinWidth(305);
		table.setRowHeight(25);
		table.setOpaque(false);
		table.setBackground(new Color(0f, 0f, 0f, 0.5f));
		table.setForeground(Color.WHITE);
		panel.add(table);

		// modifica la apariencia de la lista
		DefaultTableModel modelo = (DefaultTableModel) table.getModel();
		Object[] fila = new Object[4];
		fila[0] = "JUGADOR";
		fila[1] = "PARTIDAS GANADAS";
		fila[2] = "PARTIDAS PERDIDAS";
		fila[3] = "PARTIDAS JUGADAS";
		modelo.addRow(fila);
		table.setModel(modelo);

		// llama al metodo anadirTabla
		anadirTabla();

		// fondo
		JLabel imagen1 = new JLabel(imagen_1);
		imagen1.setBounds(0, 0, ancho, alto);
		panel.add(imagen1);

	}

	/**
	 * Agrega toda la informacion de los jugadores a la tabla y la agrega al panel
	 *
	 */

	public void anadirTabla() {
		try {
			ArchivoTabla.leerF();
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		DefaultTableModel modelo = (DefaultTableModel) table.getModel();
		Object[] fila = new Object[5];

		for (int i = 0; i < ArchivoTabla.carga.size(); i++) {

			fila[0] = ArchivoTabla.carga.get(i).getNombre();
			fila[1] = ArchivoTabla.carga.get(i).getGanadas();
			fila[2] = ArchivoTabla.carga.get(i).getPerdidas();
			fila[3] = ArchivoTabla.carga.get(i).getJugadas();
			modelo.addRow(fila);

			table.setModel(modelo);
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
}
