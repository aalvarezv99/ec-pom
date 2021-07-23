package Consultas;

import static org.junit.Assert.assertTrue;

import java.sql.ResultSet;

import org.apache.log4j.Logger;

public class RecaudoQuery {
	
	private static Logger log = Logger.getLogger(RecaudoQuery.class);
	ConexionBase dbconector = new ConexionBase();
	
	public ResultSet validarRecaudo(String numRadicado, String tipoRecaudo) {
		ResultSet r = null;
		log.info("**************** RecaudoQuery - validarRecaudo()");
		try {
			 r = dbconector.conexion("select round(r.valor)::varchar,c.estado, c.numero_radicacion,r.origen,r.tipo, r.fecha\r\n"
			 		+ "from recaudo_cliente rc\r\n"
			 		+ "inner join credito c on rc.id_credito = c.id\r\n"
			 		+ "inner join recaudo r on rc.id_recaudo = r.id\r\n"
			 		+ "where  c.id in (select id from credito where numero_radicacion in ("+ numRadicado +"))\r\n"
			 		+ "and r.tipo = '"+tipoRecaudo.toUpperCase()+"' ;");	
		} catch (Exception e) {
			log.error("********ERROR EJECUTANDO LA CONSULTA EL METODO - validarRecaudo() ********");
			assertTrue("########## ERROR EJECUTANDO LA CONSULTA EL METODO - validarRecaudo() ########"+ e,false);	
		}
		return r;
	}
	
	public ResultSet validarRecaudoPagaduria(String nombrePagaduria) {
		ResultSet r = null;
		log.info("**************** RecaudoQuery - validarRecaudoPagaduria()");
		try {
			 r = dbconector.conexion("select re.origen, re.valor, re.fecha, rp.estado , rp.id_pagaduria, re.tipo, rp.periodo \r\n"
			 		+ "from recaudo re \r\n"
			 		+ "join recaudo_pagaduria rp on rp.id_recaudo = re.id\r\n"
			 		+ "join pagaduria p on rp.id_pagaduria = p.id\r\n"
			 		+ "where 1=1\r\n"
			 		+ "and  upper(nombre) like upper('%"+nombrePagaduria+"%')\r\n"
			 		+ "and rp.estado = 'A'\r\n"
			 		+ "order by rp.periodo desc\r\n"
			 		+ "limit 1;");	
		} catch (Exception e) {
			log.error("********ERROR EJECUTANDO LA CONSULTA EL METODO RecaudoQuery - validarRecaudoPagaduria() ********" + e);
			assertTrue("########## ERROR EJECUTANDO LA CONSULTA EL METODO RecaudoQuery - validarRecaudoPagaduria() ########"+ e,false);	
		}
		return r;
	}

}
