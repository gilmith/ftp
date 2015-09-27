package com.jacobo.ftp;



import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.apache.log4j.Logger;

/**
 * Clase para setear el fichero a enviar
 * @author jake
 * @version 1.0
 */

public class FileClient {

	private File archivo;
	private String ruta;
	private final static Logger logger = Logger.getLogger(FileClient.class);
	
	
	public FileClient(String ruta){
		this.setRuta(ruta);
		archivo = new File(ruta);
	}
	
	/**
	 * metodo para obtener un inputStream para el envio de fichero
	 * @return inputStream del fichero, si no esta el fichero sale con salida 2
	 */
	
	public InputStream getInputStream(){
		try {
			return (InputStream) new FileInputStream(archivo);
		} catch (FileNotFoundException e) {
			logger.error("Archivo no encontrado", e);
			System.exit(2);
		}
		return null;
	}
	
	/**
	 * obtiene la ruta indicada del fichero que se va a enviar
	 * @return ruta absoluta del fichero
	 */

	public String getRuta() {
		return ruta;
	}
	
	/**
	 * setea la ruta del fichero a enviar
	 * @param ruta absoluta del archivo
	 */

	public void setRuta(String ruta) {
		this.ruta = ruta;
	}
	
}
