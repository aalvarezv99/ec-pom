package Consultas;

import java.sql.ResultSet;

import org.apache.log4j.Logger;

public class PrepagoQuery {
	
	private static Logger log = Logger.getLogger(CertificacionSaldoQuery.class);
	ConexionBase dbconector = new ConexionBase();
	
	public ResultSet validarRecaudo(String numRadicado, String tipoRecaudo) {
		ResultSet r = null;
		log.info("**************** CertificacionSaldoQuery - TipoCertificacion()");
		try {
			 r = dbconector.conexion("select round(r.valor)::varchar,c.estado, c.numero_radicacion,r.origen,r.tipo, r.fecha\r\n"
			 		+ "from recaudo_cliente rc\r\n"
			 		+ "inner join credito c on rc.id_credito = c.id\r\n"
			 		+ "inner join recaudo r on rc.id_recaudo = r.id\r\n"
			 		+ "where  c.id in (select id from credito where numero_radicacion in ("+ numRadicado +"))\r\n"
			 		+ "and r.tipo = '"+tipoRecaudo.toUpperCase()+"' ;");	
		} catch (Exception e) {
			log.error("********ERROR EJECUTANDO LA CONSULTA EL METODO - TipoCertificacion() ********");
			log.error(e.getMessage());			
		}
		return r;
	}

}
