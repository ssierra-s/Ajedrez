/**
	 * Juego de Ajedrez.
	 * @author  Oscar Rojas-Juan Aguilar-Sergio Sierra
	 * @version 1.0
	 * @since   2021-02-07
*/
package logica;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

//Se importa la clase que se encuentra en el paquete gui
import gui.Panel_Nueva;

// Clase que hace el manejo de archivos de la tabla de jugadores
public class ArchivoTabla {
	// Arreglo que contiene los onjetos de Jugador
	public static List<Jugador> carga = new ArrayList<Jugador>();
	// Variable que contiene la direccion del archivo donde se guardaran los datos
	// de la tabla
	static final String direccion = "src\\data\\jugadores\\info.txt";

	// Metodo que maneja el archivo, donde se añaden jugadores nuevos y/o se
	// modifica la informacion de los ya existentes
	public static void manejo() throws ClassNotFoundException, IOException {
		// Se limpia el arreglo
		carga.clear();
		// Se llama al metodo leer, para guardar los objetos de jugadores ya existentes
		leerF();
		// Auxiliares para saber si un jugador ya se encuentra en la tabla o no
		int aux1 = 0;
		int aux2 = 0;
		// Se itera el arreglo que contiene los objetos
		for (int i = 0; i < carga.size(); i++) {
			// Si el nombre que se ingreso como jugador 1 se encuentra en el arreglo
			if (Panel_Nueva.player1.equals(carga.get(i).getNombre())) {
				// Verifica si ese jugador gano, y de ser asi, le anade una victoria y una
				// partida jugada
				if (Tablero.aux == 0) {
					Jugador j = new Jugador(Panel_Nueva.player1, carga.get(i).getGanadas() + 1,
							carga.get(i).getPerdidas(), carga.get(i).getJugadas() + 1);
					carga.set(i, j);

				} else {// De lo contrario , le anade una derrota y una partida jugada
					Jugador j = new Jugador(Panel_Nueva.player1, carga.get(i).getGanadas(),
							carga.get(i).getPerdidas() + 1, carga.get(i).getJugadas() + 1);
					carga.set(i, j);
				}
				// Se aumenta en 1 el auxiliar, que significa que ese jugador ya se encontraba
				// en el arreglo
				aux1 += 1;
			}
			// Y del mismo modo con el jugador 2
			if (Panel_Nueva.player2.equals(carga.get(i).getNombre())) {
				if (Tablero.aux == 1) {
					Jugador j = new Jugador(Panel_Nueva.player2, carga.get(i).getGanadas() + 1,
							carga.get(i).getPerdidas(), carga.get(i).getJugadas() + 1);
					carga.set(i, j);
				} else {
					Jugador j = new Jugador(Panel_Nueva.player2, carga.get(i).getGanadas(),
							carga.get(i).getPerdidas() + 1, carga.get(i).getJugadas() + 1);
					carga.set(i, j);
				}
				aux2 += 1;
			}
		}

		// Con los auxiliares se sabe si los jugadores actuales se encuentran ya en el
		// arreglo, si son iguales a 0 es porque no se encuentran en estos
		if (aux1 < 1) {
			if (aux2 < 1) {
				// Y de la misma forma iterativa, se verifica si el jugador gano o perdio
				if (Tablero.aux == 0) {
					carga.add(new Jugador(Panel_Nueva.player2, 0, 1, 1));
					carga.add(new Jugador(Panel_Nueva.player1, 1, 0, 1));
				} else {
					carga.add(new Jugador(Panel_Nueva.player2, 1, 0, 1));
					carga.add(new Jugador(Panel_Nueva.player1, 0, 1, 1));
				}
			} else {
				if (Tablero.aux == 0) {
					carga.add(new Jugador(Panel_Nueva.player1, 1, 0, 1));
				} else {
					carga.add(new Jugador(Panel_Nueva.player1, 0, 1, 1));
				}
			}
		} else {
			if (aux2 < 1) {
				if (Tablero.aux == 0) {
					carga.add(new Jugador(Panel_Nueva.player2, 0, 1, 1));
				} else {
					carga.add(new Jugador(Panel_Nueva.player2, 1, 0, 1));
				}
			}
		}

		// Se busca el archivo y se borra
		File f = new File(direccion);
		f.delete();
		// Se vuelve a crear
		crearF();
		// Se itera el arreglo
		for (int i = 0; i < carga.size(); i++) {
			// Se añaden los objetos del arreglo al archivo
			anadirF(carga.get(i));
		}

	}

	/**
	 * Metodo que anade los objetos al archivo
	 * 
	 * @param juga juga
	 */
	public static void anadirF(Jugador juga) {
		// Se crea el objeto que permite el flujo de salida
		FileOutputStream fos = null;
		// Objeto que se crea a partir de un objeto FileOutputStream asociado al fichero
		// que se quiere acceder
		MiObj salida = null;
		// Try catch
		try {
			// Se accede al fichero
			fos = new FileOutputStream(direccion, true);
			// Se usa el constructor de la clase, pero sin escribir la cabecera
			salida = new MiObj(fos);
			// Se crea el objeto Jugador
			Jugador f = juga;
			// Se escribe el objeto en el fichero
			salida.writeObject(f);
			// Se declaran las excepciones
		} catch (FileNotFoundException e) {
			// System.out.print("ERROR");
		} catch (IOException e) {
			// System.out.print("ERROR");
		} finally {
			try {
				// Se cierra
				if (fos != null) {
					fos.close();
				}
				if (salida != null) {
					salida.close();
				}
			} catch (IOException e) {
				// System.out.print("ERROR");
			}
		}
	}

	/** Metodo para crear el archivo de la partida */
	public static void crearF() {
		// Se crea el objeto que permite el flujo de salida
		FileOutputStream fos = null;
		// Objeto que se crea a partir de un objeto FileOutputStream asociado al fichero
		// que se quiere acceder
		ObjectOutputStream salida = null;
		try {
			// Se crea el fichero
			fos = new FileOutputStream(direccion, true);
			salida = new ObjectOutputStream(fos);

		} catch (FileNotFoundException e) {
			// System.out.print("ERROR");
		} catch (IOException e) {
		} finally {
			try {
				// Se cierra
				if (fos != null) {
					fos.close();
				}
				if (salida != null) {
					salida.close();
				}
			} catch (IOException e) {
				// System.out.print("ERROR");
			}
		}
	}

	/** Metodo para leer el archivoa de la tabla */
	public static void leerF() throws ClassNotFoundException, IOException {
		// Objecto que permite la entrada
		ObjectInputStream ois = null;
		// Try catch
		try {
			// Se abre el fichero mediante la clase File
			File f = new File(direccion);
			// Se crea el objeto que permite el flujo de entrada
			FileInputStream fis = new FileInputStream(f);
			ois = new ObjectInputStream(fis);
			// Bucle que leï¿½ todos los objetos del fichero
			while (true) {
				Jugador ju = (Jugador) ois.readObject();
				carga.add(ju);
			}
		} catch (IOException io) {

		} finally {
			// Try catch
			try {
				// Se cierra
				if (ois != null) {
					ois.close();
				} else {
					// System.out.print("ERROR");
				}
			} catch (IOException e) {
			}
		}
	}
}
