package ui;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * @author Agustín Espinosa Minguet (aespinos@upvnet.upv.es)
 * 
 *         Clase con un único objeto que representa el log que se muestra en la
 *         parte inferior de la UI
 * 
 *         Permite añadir strings que se visualizan si se supera un cierto
 *         umbral de importancia
 *
 */
public class Log {

	private static Log log = new Log();

	private Log() {
		logContents.set("LOG:");
	};

	static Log getLog() {
		return log;
	}

	StringProperty logContents = new SimpleStringProperty();
	private int logLevel = 0;

	/**
	 * Añade una línea al log
	 * 
	 * @param level
	 *            Nivel de importancia. Sólo se muestra si es igual o inferior
	 *            al umbral actual
	 * @param text
	 *            Texto a mostrar
	 */
	public void add(int level, String text) {
		if (level <= logLevel) {
			Platform.runLater(new Runnable() {
				public void run() {
					logContents.set(logContents.get() + "\n" + text);
				}
			});
		}
	}

	/**
	 * @return Umbral actual del log
	 */
	public int getLogLevel() {
		return logLevel;
	}

	/**
	 * @param logLevel
	 *            Establece el umbral actual del log
	 */
	public void setLogLevel(int logLevel) {
		this.logLevel = logLevel;
	}

}
