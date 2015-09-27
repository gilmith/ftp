package com.jacobo.ftp;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPConnectionClosedException;
import org.apache.log4j.Logger;

/**
 * Clase objeto para el envio del fichero via ftp
 * @author jake
 * @version 1.0
 */

public class FtpClient {
	
	
	private FTPClient ftpClient;
	private final static Logger logger = Logger.getLogger(FtpClient.class);
	private Properties properties;
	
	public FtpClient() throws IOException {
		ftpClient = new FTPClient();
		properties = new Properties();
		FileInputStream input = new FileInputStream("/home/jake/workspace/ftp/properties/FTP.properties");
		properties.load(input);
		setValoresConexion();
	}
	
	/**
	 * metodo de envio del fichero
	 * @param archivo objeto FileClient es un tipo file pero con operaciones para extraer un InputStream
	 * @param rutaDestino String para dar la ruta y nombre del fichero de destino
	 * @throws FTPConnectionClosedException Exception generada si falla el envio
	 * @throws IOException Exception si el fichero es movido antes del envio
	 */
	
	public void envia(FileClient archivo, String rutaDestino) throws FTPConnectionClosedException, IOException{
		logger.info("conectado con el servidor ftp");
		logger.info("Envia el fichero");
		/**
		 * firstRemoteFile es la ruta donde se va a enviar el fichero con el nombre incluido
		 * local es el inputStream del fichero que se va a enviar
		 */
		InputStream streamArchivo =  archivo.getInputStream();
		boolean done = ftpClient.storeFile(rutaDestino, streamArchivo);
		if (done){
			logger.info("fichero enviado con exito");
			logger.info("Codigo del envio del FTP " + ftpClient.getReplyCode());
			streamArchivo.close();
		} else {
			logger.error("error en el envio del fichero");
			logger.info("Codigo del envio del FTP " + ftpClient.getReplyCode());
			streamArchivo.close();
			System.exit(3);
		}
		logger.info("desconexion del servidor");
		ftpClient.logout();
		ftpClient.disconnect();
	}

	private void setValoresConexion(){
		try {
			ftpClient.setConnectTimeout(30000);
			ftpClient.connect(properties.getProperty("SERVIDOR"), Integer.valueOf(properties.getProperty("PUERTO")));
			ftpClient.login(properties.getProperty("USUARIO"), properties.getProperty("PASSWORD"));
			ftpClient.enterLocalPassiveMode();
			ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
		} catch (IOException e) {
			logger.error("error de conexion, comprobar si hay conexion con " + properties.getProperty("SERVIDOR") + " con el usuario " + properties.getProperty("USUARIO"));
			logger.error("exception capturada ", e);
			System.exit(1);
		}
	}

}
