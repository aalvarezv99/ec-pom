package Consultas;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;

import dto.SaldoInsolutoDto;

public class CertificacionSaldoQuery {

	private static Logger log = Logger.getLogger(CertificacionSaldoQuery.class);

	ConexionBase dbconector = new ConexionBase();

	/**
	 * Obtiene los valores del saldo insoluto
	 * 
	 * @param idCredito
	 * @param fechaVencimiento        formato yyyy-MM-dd
	 * @param periodoFechaVencimiento formato yyyy-MM-dd
	 * @param fechaEjecucionCierre    formato yyyy-MM-dd
	 * @throws SQLException
	 */
	public SaldoInsolutoDto obtenerSaldoInsoluto(String numRadicado, Date fechaVencimiento) throws SQLException {
		SaldoInsolutoDto saldoInsolutoDto = new SaldoInsolutoDto();
		try {
			ResultSet fechaEjecucionCierreRs;
			String fechaEjecucionCierre;
			ResultSet capitalRs;
			BigDecimal capital;
			ResultSet interesesMoraRs;
			BigDecimal interesesMora;
			ResultSet gastosCobranzaRs;
			BigDecimal gastosCobranza;
			ResultSet interesesCorrientesRs;
			BigDecimal interesesCorrientes;
			ResultSet segurosRs;
			BigDecimal seguros;
			ResultSet cxcEstudioCreditoRs;
			BigDecimal cxcEstudioCredito;
			ResultSet cxcInteresesInicialesRs;
			BigDecimal cxcInteresesIniciales;
			ResultSet cxcSegurosRs;
			BigDecimal cxcSeguros;
			ResultSet cxcInteresesFianzaRs;
			BigDecimal cxcInteresesFianza;
			ResultSet fianzaRs;
			BigDecimal fianza;
			
			String periodoFechaVencimiento = "";
			
			/**/
			//Consultar IdCredito
			StringBuilder query = new StringBuilder();
			query.append("select id from credito c where numero_radicacion ="+numRadicado+";");
			ResultSet idCreditoRs = dbconector.conexion(query.toString());
			BigDecimal idCredito = obtenerValorResultSet(idCreditoRs, "idCredito");
			
			periodoFechaVencimiento = calcularPeriodoFechaVencimiento(fechaVencimiento.toString());
			
			
			/*consultar fecha ejecucucion cierre*/
			//
			query = new StringBuilder();
			query.append("select fecha_ejecucion_cierre \r\n"
					+ "			from historico_cierres hc\r\n"
					+ "			where 1 = 1	\r\n"
					+ "			and fecha_ejecucion_cierre is not null\r\n"
					+ "			order by periodo_cierre	desc\r\n"
					+ "			limit 1");
			fechaEjecucionCierreRs = dbconector.conexion(query.toString());
			fechaEjecucionCierre = obtenerValorResultSetString(fechaEjecucionCierreRs, "Fecha Cierre Ejecucion");

			// Capital
			query = new StringBuilder();
			query.append("select sum(distinct(pdp.capital)) - sum(distinct(dcp.capital)) as saldo_capital\n");
			query.append("from plan_de_pagos pdp\n");
			query.append("left join desglose_contable_pago dcp on dcp.id_plan_de_pago = pdp.id\n");
			query.append("where id_credito = " + idCredito + ";");
			capitalRs = dbconector.conexion(query.toString());
			capital = obtenerValorResultSet(capitalRs, "Capital");

			// Intereses mora
			query = new StringBuilder();
			query.append("select calcular_interes_mora_cobranza(" + idCredito + ", 0, '" + fechaEjecucionCierre
					+ "'::date)");
			interesesMoraRs = dbconector.conexion(query.toString());
			interesesMora = obtenerValorResultSet(interesesMoraRs, "Intereses mora");

			// Gastos de cobranza
			gastosCobranzaRs = dbconector.conexion("select calcular_interes_mora_cobranza(" + idCredito + ", 1, '"
					+ fechaEjecucionCierre + "'::date)");
			gastosCobranza = obtenerValorResultSet(gastosCobranzaRs, "Gastos de cobranza");

			// Intereses corrientes
			query = new StringBuilder();
			query.append("select sum(distinct(pdp.interes)) - sum(distinct(dcp.intereses_corrientes)) + \n");
			query.append("(select (round(interes / 30) * DATE_PART('day', '" + fechaVencimiento
					+ "'::date)) from plan_de_pagos where id_credito = " + idCredito + " and  fecha = '"
					+ periodoFechaVencimiento + "') as intereses_corrientes_total\n");
			query.append("from plan_de_pagos pdp\n");
			query.append("left join desglose_contable_pago dcp on dcp.id_plan_de_pago = pdp.id\n");
			query.append("where id_credito = " + idCredito + "\n");
			query.append("and pdp.fecha <= ('" + periodoFechaVencimiento + "'::date - INTERVAL '1 month');");
			interesesCorrientesRs = dbconector.conexion(query.toString());
			interesesCorrientes = obtenerValorResultSet(interesesCorrientesRs, "Intereses corrientes");

			// Seguros
			query = new StringBuilder();
			query.append("select sum(pdp.seguros) - \n");
			query.append("(select sum(dcp.seguro)\n");
			query.append("from desglose_contable_pago dcp\n");
			query.append("where id_plan_de_pago in (\n");
			query.append("select id\n");
			query.append("from plan_de_pagos pdp\n");
			query.append("where id_credito = " + idCredito + "\n");
			query.append("and  pdp.fecha <= ('" + periodoFechaVencimiento + "'::date - INTERVAL '1 month') \n");
			query.append(")) + \n");
			query.append("(select seguros \n");
			query.append("from plan_de_pagos\n");
			query.append("where id_credito = " + idCredito + "\n");
			query.append("and  fecha = '" + periodoFechaVencimiento + "') as seguros_pendientes\n");
			query.append("from plan_de_pagos pdp\n");
			query.append("where id_credito = " + idCredito + "\n");
			query.append("and  pdp.fecha <= ('" + periodoFechaVencimiento + "'::date - INTERVAL '1 month')");
			segurosRs = dbconector.conexion(query.toString());
			seguros = obtenerValorResultSet(segurosRs, "Seguros");

			// CXC Estudio de crédito
			query = new StringBuilder();
			query.append("select round(obtener_valor_estudio_credito('" + fechaVencimiento + "'::date, " + idCredito
					+ ", \n");
			query.append("(select pag.id\n");
			query.append("from credito cre\n");
			query.append("inner join pagaduria pag on pag.id = cre.id_pagaduria \n");
			query.append("where cre.id = " + idCredito + ")::integer, \n");
			query.append("false));");
			cxcEstudioCreditoRs = dbconector.conexion(query.toString());
			cxcEstudioCredito = obtenerValorResultSet(cxcEstudioCreditoRs, "CXC Estudio de crédito");

			// CXC Intereses iniciales
			query = new StringBuilder();
			query.append(
					"select coalesce(dccpp.intereses_dias_iniciales_restante, 0) as intereses_dias_iniciales_restante\n");
			query.append("from desglose_contable_cuenta_por_pagar dccpp \n");
			query.append("where dccpp.id_credito = " + idCredito + "\n");
			query.append("order by dccpp.mes_contabilizacion desc, dccpp.fecha_abono desc, dccpp.id desc\n");
			query.append("limit 1;");
			cxcInteresesInicialesRs = dbconector.conexion(query.toString());
			cxcInteresesIniciales = obtenerValorResultSet(cxcInteresesInicialesRs, "CXC Intereses iniciales");

			// CXC Seguros
			query = new StringBuilder();
			query.append("select coalesce(dccpp.seguro_restante, 0) as seguro_restante\n");
			query.append("from desglose_contable_cuenta_por_pagar dccpp\n");
			query.append("where dccpp.id_credito = " + idCredito + "\n");
			query.append("order by dccpp.mes_contabilizacion desc, dccpp.fecha_abono desc, dccpp.id desc \n");
			query.append("limit 1;");
			cxcSegurosRs = dbconector.conexion(query.toString());
			cxcSeguros = obtenerValorResultSet(cxcSegurosRs, "CXC Seguros");

			// CXC Intereses de fianza
			query = new StringBuilder();
			query.append("select obtener_cxc_intereses_fianza(" + idCredito + ", '" + fechaVencimiento + "');\n");
			cxcInteresesFianzaRs = dbconector.conexion(query.toString());
			cxcInteresesFianza = obtenerValorResultSet(cxcInteresesFianzaRs, "CXC Intereses de fianza");

			// Fianza
			query = new StringBuilder();
			query.append("select des.valor_fianza \n");
			query.append("from credito cre \n");
			query.append("inner join simulador_analista san on san.id_credito = cre.id\n");
			query.append("inner join desglose des on des.id = san.id_desglose\n");
			query.append("and cre.id = " + idCredito + ";");
			fianzaRs = dbconector.conexion(query.toString());
			fianza = obtenerValorResultSet(fianzaRs, "Fianza");

			saldoInsolutoDto.setCapital(capital);
			saldoInsolutoDto.setInteresMora(interesesMora);
			saldoInsolutoDto.setGastosCobranza(gastosCobranza);
			saldoInsolutoDto.setInteresesCorrientes(interesesCorrientes);
			saldoInsolutoDto.setSeguro(seguros);
			saldoInsolutoDto.setCxcEstudioCredito(cxcEstudioCredito);
			saldoInsolutoDto.setCxcIntesesInciales(cxcInteresesIniciales);
			saldoInsolutoDto.setCxcSeguroIncial(cxcSeguros);
			saldoInsolutoDto.setCxcInteresesFianza(cxcInteresesFianza);
			saldoInsolutoDto.setCxcFianza(fianza);
			saldoInsolutoDto.setSaldoInsoluto(capital.add(interesesMora).add(gastosCobranza).add(interesesCorrientes)
					.add(seguros).add(cxcEstudioCredito).add(cxcInteresesIniciales).add(cxcSeguros)
					.add(cxcInteresesFianza).add(fianza));
			System.out.println("Saldo insoluto: "+saldoInsolutoDto.getSaldoInsoluto());
		} catch (Exception e) {
			log.error(e);
		}
		return saldoInsolutoDto;
	}
	
	private String calcularPeriodoFechaVencimiento(String fecha) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/mm/dd");
		Calendar calendar = Calendar.getInstance();  
		try {
			 Date date = sdf.parse(fecha);
		     calendar.setTime(date);  
		} catch (Exception e) {
			// TODO: handle exception
		}
        
        return String.valueOf(calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
	}

	private BigDecimal obtenerValorResultSet(ResultSet resultSet, String concepto) {
		BigDecimal valor = BigDecimal.ZERO;
		try {
			while (resultSet.next()) {
				valor = resultSet.getBigDecimal(1);
				log.info(concepto + ": " + valor);
				break;
			}
		} catch (Exception e) {
			log.error(e);
		}
		return valor;
	}
	
	private String obtenerValorResultSetString(ResultSet resultSet, String concepto) {
		String valor = "";
		try {
			while (resultSet.next()) {
				valor = resultSet.getString(1);
				log.info(concepto + ": " + valor);
				break;
			}
		} catch (Exception e) {
			log.error(e);
		}
		return valor;
	}

	public ResultSet ConsultarRegistroCertificacion(String numRadicado) {
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
					+ "		cr.fecha_aprobacion,\r\n" + "		c.fecha_solicitud fechaSolicitudCert,\r\n"
					+ "		to_char(c.fecha_vencimiento, 'DD/MM/YYYY') fechaVenciCert,\r\n"
					+ "		coalesce(c.valor_sancion_prepago,0) clausulaIndemnizacion\r\n" + "from certificacion c \r\n"
					+ "inner join credito cr on c.id_credito = cr.id\r\n"
					+ "inner join desglose d on cr.id = d.id_credito \r\n" + "where 1=1\r\n"
					+ "and cr.numero_radicacion =" + numRadicado + "and d.desglose_seleccionado is true\r\n"
					+ "group by c.id, cr.fecha_aprobacion, d.valor_fianza\r\n" + "order by c.id desc\r\n" + "limit 1;");

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
			r = dbconector.conexion("select\r\n" + "	d.valor_fianza,\r\n" + "	cr.estado,\r\n"
					+ "	(case when cr.fecha_aprobacion < '2019-02-05'\r\n" + "	then 'SINCXC'\r\n" + "	else \r\n"
					+ "	'CONCXC' END) Cuentas\r\n" + "from\r\n" + "	desglose d\r\n" + "join credito cr on\r\n"
					+ "	cr.id = d.id_credito\r\n" + "where\r\n" + "	1 = 1\r\n" + "	and cr.credito_activo is true\r\n"
					+ "	and d.desglose_seleccionado is true\r\n" + "and cr.numero_radicacion =" + numRadicado);

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
			r = dbconector.conexion(" SELECT * from funcautocalculocertificacion(" + 38570 + ");");
		} catch (Exception e) {
			log.error("********ERROR EJECUTANDO LA CONSULTA EL METODO - TipoCertificacion() ********");
			log.error(e.getMessage());
		}
		return r;
	}

}
