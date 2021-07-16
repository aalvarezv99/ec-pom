package Consultas;

import static org.junit.Assert.assertTrue;

import java.sql.ResultSet;

import org.apache.log4j.Logger;

public class AplicacionCierreQuery {
	
	private static Logger log = Logger.getLogger(AplicacionCierreQuery.class);
	
	ConexionBase dbconector = new ConexionBase();
	
	public ResultSet consultarVlrCargadoPlanilla(String nombrePagaduria, String periodo) {
		ResultSet r = null;
		log.info("**************** AplicacionCierreQuery - consultarVlrCargadoPlanilla()");
		try {
			 r = dbconector.conexion("select  sum(valor_reportado)VlrReportado,  sum(valor_recibido)vlrRecibido,  sum(valor_listado)vlrListado from detalle_pago_nomina dp \r\n"
			 		+ "join credito c on c.id = dp.id_credito \r\n"
			 		+ "join pagaduria p on c.id_pagaduria = p.id\r\n"
			 		+ "and upper(nombre) like upper('%"+nombrePagaduria+"%')\r\n"
			 		+ "and dp.fecha_periodo = '2021-06-30';");
	
		} catch (Exception e) {
			log.error("********ERROR EJECUTANDO LA CONSULTA AplicacionCierreQuery- consultarVlrCargadoPlanilla() ********" + e);
			assertTrue("########## ERROR EJECUTANDO LA CONSULTA AplicacionCierreQuery- consultarVlrCargadoPlanilla() ########"+ e,false);		
		}
		return r;
	}
}
