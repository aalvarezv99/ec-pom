package Consultas;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.apache.log4j.Logger;


public class ConexionBase {
	
	private static Logger log = Logger.getLogger(ConexionBase.class);
	
	public ResultSet conexion(String query) throws SQLException, ClassNotFoundException {
		ResultSet rs = null;
		try {
			String dbUrl = "jdbc:postgresql://libranzas-preproduccion.chimul6agbmw.us-east-1.rds.amazonaws.com:5432/libranzas_instancia4";

			// Database Username
			String username = "abacus";

			String password = "L1br4nz4s$2018";

			Class.forName("org.postgresql.Driver");

			Connection con = DriverManager.getConnection(dbUrl, username, password);

			// Create Statement Object
			Statement stmt = con.createStatement();

			// Execute the SQL Query. Store results in ResultSet
			rs = stmt.executeQuery(query);
			
			
			// While Loop to iterate through all data and print results
//			while (rs.next()) {
//				String myName = rs.getString(1);
//				String myAge = rs.getString(2);
//				System.out.println(myName + "  " + myAge);
//			}
			// closing DB Connection
//			con.close();
		} catch (Exception e) {
			log.error("********ERROR CONEXION BASE DATOS*******");
			log.error(e.getMessage());
		}
		return rs;
		
	}
}
