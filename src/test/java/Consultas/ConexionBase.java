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
	
	private static String instanciaAcounting;	
	private static String dbUrlAcounting;
	private static String usernameAcounting;	
	private static String passwordAcounting;
	
	private static String dbPsl;	
	private static String dbUrlPsl;
	private static String userPsl;	
	private static String passwordPsl;
	
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
			log.error("********ERROR CONEXION BASE DATOS LIBRANZAS*******");
			log.error(e.getMessage());
		}
		
		return rs;
	}
	
	public ResultSet conexionAcountingBridge(String query) throws SQLException, ClassNotFoundException {
		ResultSet rs = null;
		
		try {
			if(instanciaAcounting == null) {
				instanciaAcounting = leerPropiedades("instanciaAcounting");
				log.info("====================");
				log.info("[ Base de Datos - AccountinBridge ] - " + instanciaAcounting.toUpperCase());
				log.info("====================");
				dbUrlAcounting = leerPropiedades("jdbc-urlAcouting")+instanciaAcounting;

				usernameAcounting = leerPropiedades("usernameAcouting");

				passwordAcounting = leerPropiedades("passwordAcountig");					

				Class.forName(leerPropiedades("driverClassName"));
		
			}
			Connection con = DriverManager.getConnection(dbUrlAcounting, usernameAcounting, passwordAcounting);
			
			Statement stmt = con.createStatement();
			stmt.setFetchSize(50);
			rs = stmt.executeQuery(query);
		
		} catch (Exception e) {
			log.error("********ERROR CONEXION BASE DATOS ACCOUNTING BRIDGE*******");
			log.error(e.getMessage());
		}
		return rs;
	}
	
	public ResultSet conexionPSL(String query) throws SQLException, ClassNotFoundException {
		ResultSet rs = null;
		
		try {
			if(dbPsl == null) {
				dbPsl = leerPropiedades("DBPsl");
				log.info("====================");
				log.info("[ Base de Datos - PSL PRUEBAS ] - " + dbPsl.toUpperCase());
				log.info("====================");
				dbUrlPsl = leerPropiedades("jdbc-urlPSL")+dbPsl;

				userPsl = leerPropiedades("userPSL");

				passwordPsl = leerPropiedades("PasswordPSL");					

				Class.forName(leerPropiedades("driverClassNameSqlServer"));
		
			}
			Connection con = DriverManager.getConnection(dbUrlPsl, userPsl, passwordPsl);
			
			Statement stmt = con.createStatement();
			stmt.setFetchSize(50);
			rs = stmt.executeQuery(query);
		
		} catch (Exception e) {
			log.error("********ERROR CONEXION BASE DATOS PSL PRUEBAS *******");
			log.error(e.getMessage());
		}
		return rs;
	}
	
}
