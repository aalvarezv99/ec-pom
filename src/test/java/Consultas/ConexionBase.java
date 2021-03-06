package Consultas;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.apache.log4j.Logger;

import CommonFuntions.CrearDriver;


public class ConexionBase {
	
	private  Properties pro = new Properties();
	private  InputStream in = CrearDriver.class.getResourceAsStream("../Dbconfig.properties");	
	private  Logger log = Logger.getLogger(ConexionBase.class);
	
	private static String instancia;	
	private static String dbUrl;
	private static String username;	
	private static String password;
	private static String driver;
	
	public String leerPropiedades(String valor) {
		try {
			pro.load(in);
		} catch (Exception e) {
			log.error("====== ERROR LEYENDO EL ARCHIVO DE PROPIEDADES========= " + e);
		}
		return pro.getProperty(valor);
	}
	
	public ResultSet conexion(String query) throws SQLException, ClassNotFoundException {
		ResultSet rs = null;
		

		try {
			if(instancia == null) {
				instancia = leerPropiedades("instancia");
				log.info("====================");
				log.info("[ Base de Datos ] - " + instancia.toUpperCase());
				log.info("====================");
				dbUrl = leerPropiedades("jdbc-url")+instancia;

				username = leerPropiedades("username");

				password = leerPropiedades("password");					

				Class.forName(leerPropiedades("driverClassName"));
		
			}
			Connection con = DriverManager.getConnection(dbUrl, username, password);
			
			Statement stmt = con.createStatement();
			stmt.setFetchSize(50);
			rs = stmt.executeQuery(query);
		
		} catch (Exception e) {
			log.error("********ERROR CONEXION BASE DATOS*******");
			log.error(e.getMessage());
		}
		
		return rs;
		
	}
}
