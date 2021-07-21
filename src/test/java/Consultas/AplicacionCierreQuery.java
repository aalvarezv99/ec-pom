package Consultas;

import static org.junit.Assert.assertTrue;

import java.sql.ResultSet;

import org.apache.log4j.Logger;

public class AplicacionCierreQuery {
	
	private static Logger log = Logger.getLogger(AplicacionCierreQuery.class);
	
	ConexionBase dbconector = new ConexionBase();
	
	/*
	 * Se consulta el valor que se cargo en el sistema de la planilla*/
	public ResultSet consultarVlrCargadoPlanilla(String nombrePagaduria, String periodo) {
		ResultSet r = null;
		log.info("**************** AplicacionCierreQuery - consultarVlrCargadoPlanilla()");
		try {
			 r = dbconector.conexion("select  sum(valor_reportado)VlrReportado,  sum(valor_recibido)vlrRecibido,  sum(valor_listado)vlrListado , dp.fecha_periodo \r\n"
			 		+ "from detalle_pago_nomina dp\r\n"
			 		+ "join credito c on c.id = dp.id_credito \r\n"
			 		+ "join pagaduria p on c.id_pagaduria = p.id\r\n"
			 		+ "where 1=1\r\n"
			 		+ "and upper(nombre) like upper('%"+nombrePagaduria+"%')\r\n"
			 		+ "group by dp.fecha_periodo \r\n"
			 		+ "order by dp.fecha_periodo desc limit 1;");
	
		} catch (Exception e) {
			log.error("********ERROR EJECUTANDO LA CONSULTA AplicacionCierreQuery- consultarVlrCargadoPlanilla() ********" + e);
			assertTrue("########## ERROR EJECUTANDO LA CONSULTA AplicacionCierreQuery- consultarVlrCargadoPlanilla() ########"+ e,false);		
		}
		return r;
	}
	
	/*
	 * Se consulta que el check de la preaplicacion se encuentre en "false"*/
	public ResultSet validarPreaplicacionRealizada(String idPagaduria) {
		ResultSet r = null;
		log.info("************* AplicacionCierreQuery - validarPreaplicacionRealizada()********************");
		try {
			r = dbconector.conexion("select pago_preaplicado_si_no, periodo from aplicacion_pago_pagaduria app \r\n"
					+ "where app.id_pagaduria ="+idPagaduria+"\r\n"
					+ "and listado_procesado_si_no = true\r\n"
					+ "order by periodo desc\r\n"
					+ "limit 1;");
		} catch (Exception e) {
			log.error("********ERROR EJECUTANDO LA CONSULTA AplicacionCierreQuery- validarPreaplicacionRealizada() ********" + e);
			assertTrue("########## ERROR EJECUTANDO LA CONSULTA AplicacionCierreQuery- validarPreaplicacionRealizada() ########"+ e,false);
		}
		return r;
	}
}
