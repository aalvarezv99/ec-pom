package Consultas;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;


public class RunConsultas {
	private static Logger log = Logger.getLogger(RunConsultas.class);
	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub
		CertificacionSaldoQuery query = new CertificacionSaldoQuery();
		query.obtenerSaldoInsoluto(134001, "2021-08-24", "2021-08-30", "2021-07-12");
		/*ResultSet result;
		NumberFormat formatoNumero = NumberFormat.getNumberInstance();
		result = query.ejecutarFuncion(); 
		log.info("CONSULTAS PROCEDURE ***************");
		
		/*result = query.ConsultarRegistroCertificacion(String.valueOf(38570)); 
		log.info("CONSULTAS SELECT ***************");
		while(result.next()) {
			log.info(result.getString(1));
			log.info(formatoNumero.format(Double.parseDouble(result.getString(2))).replace('.', ','));
			log.info(result.getString(3));
			log.info(result.getString(4));
			log.info(result.getString(5));
			log.info(result.getString(6));
			log.info(result.getString(7));
			log.info(result.getString(8));
			log.info(result.getString(9));			
			log.info(result.getString(10));
			log.info(result.getString(11));
			log.info(result.getString(12));
			log.info(result.getString(13));
			log.info(result.getString(14));
			log.info(result.getString(15));
			log.info(result.getString(16));
		}*/
	}

}
