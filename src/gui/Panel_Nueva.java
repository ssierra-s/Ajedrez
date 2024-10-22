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
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class Panel_Nueva extends JPanel {

	// Declaracion de parametros del panel nueva
	JPanel panel = new JPanel();
	private JTextField jText1;
	private JTextField jText2;
	ArrayList<String> jugadores = new ArrayList<String>();
	JComboBox jCombo1;
	JComboBox jCombo2;
	public static String player1;
	public static String player2;
	public static String nameAr;
	private int aux = 0;
	private int aux2 = 0;

	/**
	 * Crea el Panel Nueva
	 */
	public Panel_Nueva() {

		// define los parametros del panel nueva
		int ancho = 1280;
		int alto = 720;
		Panel_Menu ini = new Panel_Menu();// crea un panel menu
		setBounds(0, 0, ancho, alto);
		setBorder(new EmptyBorder(5, 5, 5, 5));
		setBackground(Color.WHITE);
		setLayout(null);
		Image img1 = new ImageIcon("src\\recursos\\fondo_nueva.png").getImage();
		ImageIcon imagen_1 = new ImageIcon(img1.getScaledInstance(1280, 720, Image.SCALE_SMOOTH));
		panel.setBounds(0, 0, ancho, alto);
		panel.setLayout(null);
		add(panel);

		abrirJugadores();// lamma la funcion abrirJugadores

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

		// crea 3 labels y los agrega al panel
		JLabel jLabelLetrero = new JLabel("NUEVA PARTIDA");
		jLabelLetrero.setForeground(Color.BLACK);
		jLabelLetrero.setHorizontalAlignment(SwingConstants.CENTER);
		jLabelLetrero.setFont(new Font("Times New Roman", jLabelLetrero.getFont().getStyle(),
				jLabelLetrero.getFont().getSize() + 80));
		jLabelLetrero.setBounds(0, 0, 1360, 121);
		panel.add(jLabelLetrero);
		// label del jugador 1
		JLabel jLabelP1 = new JLabel("Jugador 1:");
		jLabelP1.setFont(new Font("MS UI Gothic", jLabelP1.getFont().getStyle(), jLabelP1.getFont().getSize() + 17));
		jLabelP1.setBounds(64, 186, 146, 47);
		panel.add(jLabelP1);
		// label del jugador 2
		JLabel jLabelP2 = new JLabel("Jugador 2:");
		jLabelP2.setFont(new Font("MS UI Gothic", jLabelP2.getFont().getStyle(), jLabelP2.getFont().getSize() + 17));
		jLabelP2.setBounds(64, 253, 146, 47);
		panel.add(jLabelP2);

		// crea un tipo de combo box a base de un JText, un JButton y un comboBox para
		// el jugador 1
		// crea y agrega el JText del jugador 1
		jText1 = new JTextField();
		jText1.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		jText1.setBounds(237, 200, 234, 26);
		panel.add(jText1);
		jText1.setColumns(10);
		// crea y agregar el JButton para el jugador 1
		JButton jBoton1 = new JButton("");
		jBoton1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jCombo1.showPopup();
			}
		});
		jBoton1.setBounds(473, 200, 26, 26);
		jBoton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		jBoton1.setIcon(setIcono("src\\recursos\\desplegar.png", jBoton1));
		jBoton1.setOpaque(false);
		jBoton1.setContentAreaFilled(false);
		jBoton1.setBorderPainted(false);
		panel.add(jBoton1);
		// Crea y agrega el comboBox para el jugador 1
		jCombo1 = new JComboBox();
		jCombo1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jText1.setText((String) jCombo1.getSelectedItem());
			}
		});
		jCombo1.setBounds(237, 203, 233, 22);
		for (String objeto : jugadores) {
			jCombo1.addItem(objeto.toString());
		}
		panel.add(jCombo1);

		// crea un tipo de combo box a base de un JText, un JButton y un comboBox para
		// el jugador 2
		// crea y agrega el JText del jugador 2
		jText2 = new JTextField();
		jText2.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		jText2.setColumns(10);
		jText2.setBounds(237, 261, 234, 26);
		panel.add(jText2);
		// crea y agregar el JButton para el jugador 2
		JButton jBoton2 = new JButton("");
		jBoton2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jCombo2.showPopup();
			}
		});
		jBoton2.setBounds(473, 261, 26, 26);
		jBoton2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		jBoton2.setIcon(setIcono("src\\recursos\\desplegar.png", jBoton2));
		jBoton2.setOpaque(false);
		jBoton2.setContentAreaFilled(false);
		jBoton2.setBorderPainted(false);
		panel.add(jBoton2);
		// Crea y agrega el comboBox para el jugador 2
		jCombo2 = new JComboBox();
		jCombo2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jText2.setText((String) jCombo2.getSelectedItem());
			}
		});
		jCombo2.setBounds(237, 265, 233, 22);
		for (String objeto : jugadores) {
			jCombo2.addItem(objeto.toString());
		}
		panel.add(jCombo2);

		// crea y agrega un boton al panel que iniciara una nueva partida con los
		// jugadores selccionados
		JButton jBotonJugar = new JButton("");
		jBotonJugar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				player1 = jText1.getText();
				player2 = jText2.getText();
				if (!player1.equals("") && !player2.equals("") && !player2.equals(player1)
						&& !player1.equals(player2)) {
					for (String i : jugadores) {
						if (player1.equals(i)) {
							aux += 1;
						}
						if (player2.equals(i)) {
							aux2 += 1;
						}
					}
					if (aux < 1) {
						jugadores.add(player1);
					}
					if (aux2 < 1) {
						jugadores.add(player2);
					}
					guardarJugadores();
					sonidoBoton();
					nameAr = player1 + "_" + player2;
					Panel_Juego pa = new Panel_Juego();
					add(pa);
					remove(panel);
					repaint();
				} else {
					JOptionPane.showMessageDialog(null, "Nombres no validos", "ERROR", JOptionPane.PLAIN_MESSAGE);
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
		imagen1.setBounds(10, 0, ancho, alto);
		panel.add(imagen1);
	}

	/**
	 * Abre el archivo que contiene los jugadores Mira si hay nombres de jugadores
	 * anteriores y los agrega al comboBox
	 *
	 */
	public void abrirJugadores() {
		try {
			FileInputStream fstream = new FileInputStream("src\\data\\jugadores\\data.txt");
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			while ((strLine = br.readLine()) != null) {
				jugadores.add((strLine));
			}
			in.close();
		} catch (Exception o) {
			System.err.println("Error: " + o.getMessage());
		}
	}

	/**
	 * Guarda el nombre del nuevo jugador
	 *
	 */
	public void guardarJugadores() {
		FileWriter fichero = null;
		PrintWriter pw = null;
		try {
			fichero = new FileWriter("src\\data\\jugadores\\data.txt");
			pw = new PrintWriter(fichero);

			for (int i = 0; i < jugadores.size(); i++) {
				pw.println(jugadores.get(i));
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				// Nuevamente aprovechamos el finally para
				// asegurarnos que se cierra el fichero.
				if (null != fichero) {
					fichero.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
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
