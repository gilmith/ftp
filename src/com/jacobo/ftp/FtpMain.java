/**
 * 
 */
package com.jacobo.ftp;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 * Clase principal para realizar un envio via ftp desde java sin tirar de comandos
 * ftp ni shell script
 * @author jake
 * Codigos de Salida:
 * 1 : Error de conexion con el servidor, comprobar manualmente la conexion con el usuario y al servidor 
 * 2 : Error no existe el archivo a enviar, comprobar si tiene permisos de acceso o si se ha movido antes de enviar
 * 3 : Error de envio se ha cortado la conexion antes de enviar, volver a enviar el fichero.
 */
public class FtpMain {
	
	private final static Logger logger = Logger.getLogger(FtpMain.class);

	/**
	 * @param args args[0] ruta del fichero de origen 
	 * args[1] ruta de destino para el fichero
	 */
	public static void main(String[] args) {

		PropertyConfigurator.configure("/home/jake/workspace/ftp/properties/log4j.properties");
		if(args.length < 2 ){
			logger.error("error en el paso de parametros de lanzamiento de la clase");
			System.exit(1);
		} else {
			try {
				logger.info("comienza el ftp");
				FtpClient clienteFTP = new FtpClient();
				FileClient archivo = new FileClient(args[0]);
				logger.info("envia el fichero");
				clienteFTP.envia(archivo, args[1]);
				logger.info("fichero enviado");
			} catch (IOException e) {
				logger.error("IOException capturada");
				e.printStackTrace();
			}
		}
	}

}
