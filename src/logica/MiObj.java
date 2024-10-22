/**
	 * Juego de Ajedrez.
	 * @author  Oscar Rojas-Juan Aguilar-Sergio Sierra
	 * @version 1.0
	 * @since   2021-02-07
*/
package logica;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

public class MiObj extends ObjectOutputStream {
	/** Constructor que recibe OutputStream */
	/**
	 * 
	 * @param out
	 * @throws IOException
	 */
	public MiObj(OutputStream out) throws IOException {
		super(out);
	}

	/** Constructor sin paremetros */
	/**
	 * 
	 * @throws IOException
	 * @throws SecurityException
	 */
	protected MiObj() throws IOException, SecurityException {
		super();
	}

	/** Redefinicion del metodo de escribir la cabecera para que no haga nada. */
	protected void writeStreamHeader() throws IOException {
	}
}
