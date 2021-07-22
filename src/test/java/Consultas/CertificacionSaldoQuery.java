package Consultas;


import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.apache.log4j.Logger;


public class CertificacionSaldoQuery {
	
	private static Logger log = Logger.getLogger(CertificacionSaldoQuery.class);
	
	ConexionBase dbconector = new ConexionBase();
	
	public void obtenerSaldoInsoluto(Integer idCredito, Date fechaVencimiento, Date periodoFechaVencimiento) throws SQLException {
		ResultSet capital = null;
		ResultSet intMora = null;
		try {
			capital = dbconector.conexion("select sum(distinct(pdp.capital)) - sum(distinct(dcp.capital)) as saldo_capital\r\n"
					+ "from plan_de_pagos pdp\r\n"
					+ "left join desglose_contable_pago dcp on dcp.id_plan_de_pago = pdp.id\r\n"
					+ "where id_credito = "+idCredito+";");
			
			/*intMora = dbconector.conexion("select calcular_interes_mora_cobranza("+idCredito+",0, (select max(fecha_ejecucion_cierre)))\r\n"
					+ "from historico_cierres;"); */
			
			intMora = dbconector.conexion("select calcular_interes_mora_cobranza("+idCredito+",0, '2021-07-12')\r\n"
					+ "from historico_cierres;"); 
			
			while (intMora.next()) {
				
				BigDecimal asd = intMora.getBigDecimal(1);
				log.info(asd);
			}
			
		} catch (Exception e) {
			log.error(e);
		}
		
		
	}

	public ResultSet ConsultarRegistroCertificacion( String numRadicado) {
		ResultSet r = null;
		log.info("**************** CertificacionSaldoQuery - ConsultarRegistroCertificacion()");
		try {
			 r = dbconector.conexion("select	'certificacion-saldo-'|| c.id ||'.pdf' nombreDoc,\r\n"
			 		+ "		coalesce(valor_saldo_capital,0) capital,\r\n"
			 		+ "		coalesce(c.valor_intereses_corrientes,0) interesesCorrientes,\r\n"
			 		+ "		coalesce(c.valor_seguros,0) seguro,\r\n"
			 		+ "		coalesce(c.cuenta_por_cobrar_seguro,0) seguroInicialPendientePago,\r\n"
			 		+ "		coalesce(c.cuenta_por_cobrar_intereses_iniciales,0) interesesInicialesPentPago,\r\n"
			 		+ "		coalesce(c.cuenta_por_cobrar_estudio_credito,0) estudiCrediPendPago,\r\n"
			 		+ "		coalesce(round(d.valor_fianza),0) fianza,\r\n"
			 		+ "     sum(coalesce(valor_interes_fianza_corridos,0)+coalesce(valor_interes_fianza_no_corridos,0))InteresesFianza,\r\n"
			 		+ "		coalesce(c.cxc_interes_prima_seguro_anticipada,0) primaSegPendientePago,\r\n"
			 		+ "		coalesce(c.cxc_prima_seguro_anticipada,0) intPrimaSeguro,\r\n"
			 		+ "		round(sum(coalesce(c.valor_saldo_capital,0)+coalesce(c.valor_intereses_corrientes,0) + coalesce(c.valor_seguros,0)\r\n"
			 		+ "			 				+coalesce(c.cuenta_por_cobrar_seguro,0)+ coalesce(c.cuenta_por_cobrar_intereses_iniciales,0)\r\n"
			 		+ "			 				+coalesce(c.cuenta_por_cobrar_estudio_credito,0)+coalesce(d.valor_fianza,0)+coalesce(c.cxc_interes_prima_seguro_anticipada,0)\r\n"
			 		+ "			 				+coalesce(c.cxc_prima_seguro_anticipada,0)+coalesce(valor_interes_fianza_corridos,0)+ coalesce(valor_interes_fianza_no_corridos,0) ))totalPagar,"
			 		+ "		cr.fecha_aprobacion,\r\n"
			 		+ "		c.fecha_solicitud fechaSolicitudCert,\r\n"
			 		+ "		to_char(c.fecha_vencimiento, 'DD/MM/YYYY') fechaVenciCert,\r\n"
			 		+ "		coalesce(c.valor_sancion_prepago,0) clausulaIndemnizacion\r\n"
			 		+ "from certificacion c \r\n"
			 		+ "inner join credito cr on c.id_credito = cr.id\r\n"
			 		+ "inner join desglose d on cr.id = d.id_credito \r\n"
			 		+ "where 1=1\r\n"
			 		+ "and cr.numero_radicacion ="+ numRadicado
			 		+ "and d.desglose_seleccionado is true\r\n"
			 		+ "group by c.id, cr.fecha_aprobacion, d.valor_fianza\r\n"
			 		+ "order by c.id desc\r\n"
			 		+ "limit 1;");
	
		} catch (Exception e) {
			log.error("********ERROR EJECUTANDO LA CONSULTA EL METODO - ConsultarRegistroCertificacion() ********");
			log.error(e.getMessage());			
		}
		return r;
	}
	
	public ResultSet consultarTipoCertificacion(String numRadicado) {
		ResultSet r = null;
		log.info("**************** CertificacionSaldoQuery - TipoCertificacion()");
		try {
			 r = dbconector.conexion("select\r\n"
			 		+ "	d.valor_fianza,\r\n"
			 		+ "	cr.estado,\r\n"
			 		+ "	(case when cr.fecha_aprobacion < '2019-02-05'\r\n"
			 		+ "	then 'SINCXC'\r\n"
			 		+ "	else \r\n"
			 		+ "	'CONCXC' END) Cuentas\r\n"
			 		+ "from\r\n"
			 		+ "	desglose d\r\n"
			 		+ "join credito cr on\r\n"
			 		+ "	cr.id = d.id_credito\r\n"
			 		+ "where\r\n"
			 		+ "	1 = 1\r\n"			 		
			 		+ "	and cr.credito_activo is true\r\n"
			 		+ "	and d.desglose_seleccionado is true\r\n"
			 		+ "and cr.numero_radicacion ="+numRadicado);
	
		} catch (Exception e) {
			log.error("********ERROR EJECUTANDO LA CONSULTA EL METODO - TipoCertificacion() ********");
			log.error(e.getMessage());			
		}
		return r;
	}
	
	public ResultSet ejecutarFuncion() {
		ResultSet r = null;
		log.info("**************** CertificacionSaldoQuery - TipoCertificacion()");
		try {
			 r = dbconector.conexion(" SELECT * from funcautocalculocertificacion("+38570+");");	
		} catch (Exception e) {
			log.error("********ERROR EJECUTANDO LA CONSULTA EL METODO - TipoCertificacion() ********");
			log.error(e.getMessage());			
		}
		return r;
	}
}
