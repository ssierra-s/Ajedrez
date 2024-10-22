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

/** Se hace herencia de la clase tablero */
public class Archivo extends Tablero {
	// Se crean dos listas del objeto fichas
	private static List<Fichas> aux = new ArrayList<Fichas>();
	private static List<Fichas> carga = new ArrayList<Fichas>();

	/** Metodo para añadir fichas al archivo */
	public static void anadirFichas() {
		// Si es el turno de las blancas se borra el anterior archivo que contiene el
		// turno de las negras
		if (getGameState() == 0) {
			// Direccion del archivo
			String direccion = "src\\data\\partidas\\" + Panel_Nueva.nameAr + "_" + 1 + ".txt";
			// Se llama al metodo que heredo de tablero
			mane();
			// Iguala el arreglo con el que se encuentra en tablero
			aux = fichas;
			// Crea una variable que contiene el achivo
			File f = new File(direccion);
			// Borra el archivo anterior
			f.delete();
		} else {
			// Lo mismo, pero cuando el turno es de la negras
			String direccion = "src\\data\\partidas\\" + Panel_Nueva.nameAr + "_" + 0 + ".txt";
			mane();
			aux = fichas;
			File f = new File(direccion);
			f.delete();
		}
		// Llama al metodo que crea nuevamente el archivo
		crearF();
		// Se itera el arreglo que contiene las fichas
		for (int i = 0; i < aux.size(); i++) {
			// Se envia al metodo donde se añaden los objetos al archivo
			anadirF(aux.get(i));
		}
		// Si el juego se termino, borra el archivo
		if (getGameState() == 2) {
			String direccion = "src\\data\\partidas\\" + Panel_Nueva.nameAr + "_" + 2 + ".txt";
			File f = new File(direccion);
			f.delete();
		}
	}

	/**
	 * Metodo para leer el archivo/partida que se desea reanudar
	 * 
	 * @param url
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public static void leerF(String url) throws ClassNotFoundException, IOException {
		// Objecto que permite la entrada
		ObjectInputStream ois = null;

		// Direccion donde se encuentra el archivo de la partida
		String dire = "src\\data\\partidas\\" + url;

		// Try catch
		try {
			// Se abre el fichero mediante la clase File
			File f = new File(dire);
			// Se crea el objeto que permite el flujo de entrada
			FileInputStream fis = new FileInputStream(f);
			ois = new ObjectInputStream(fis);
			// Bucle que leer todos los objetos del fichero
			while (true) {
				Fichas est = (Fichas) ois.readObject();
				// Se añaden los objetos al arreglo carga
				carga.add(est);
			}
		} catch (IOException io) {
			// System.out.print("ERROR");

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

	/**
	 * 
	 * Metodo que añade los objetos al archivo
	 * 
	 * @param Ficha
	 */
	public static void anadirF(Fichas Ficha) {
		// Se crea el objeto que permite el flujo de salida
		FileOutputStream fos = null;
		// Objeto que se crea a partir de un objeto FileOutputStream asociado al fichero
		// que se quiere acceder
		MiObj salida = null;
		// Direccion del archivo al que se le quieren añadir objetos
		String direccion = "src\\data\\partidas\\" + Panel_Nueva.nameAr + "_" + getGameState() + ".txt";
		// Try catch
		try {
			// Se accede al fichero
			fos = new FileOutputStream(direccion, true);
			// Se usa el constructor de la clase, pero sin escribir la cabecera
			salida = new MiObj(fos);
			// Se crea el objeto Fichas
			Fichas f = Ficha;
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
		// Direccion del archivo al que se le quieren añadir objetos
		String direccion = "src\\data\\partidas\\" + Panel_Nueva.nameAr + "_" + getGameState() + ".txt";
		try {

			// Se crea el fichero
			fos = new FileOutputStream(direccion, true);
			salida = new ObjectOutputStream(fos);

		} catch (FileNotFoundException e) {
			// System.out.print("ERROR");
		} catch (IOException e) {
			System.out.print("ERROR");
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

	/**
	 * 
	 * Metodo que lee los objetos/fichas de un archivo/partida
	 * 
	 * @param url
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public static void manejo(String url) throws ClassNotFoundException, IOException {
		leerF(url);
	}

	// Getter y setter del arreglo carga
	/**
	 * 
	 * @return la lista carga
	 */
	public static List<Fichas> getCarga() {
		return carga;
	}

	/**
	 * 
	 * @param carga
	 */
	public static void setCarga(List<Fichas> carga) {
		Archivo.carga = carga;
	}

}
