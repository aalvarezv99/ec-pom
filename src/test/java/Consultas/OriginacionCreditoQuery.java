package Consultas;

import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

public class OriginacionCreditoQuery {
	
	private static Logger log = Logger.getLogger(CertificacionSaldoQuery.class);
	ConexionBase dbconector = new ConexionBase();
	WebDriver driver;

	public ResultSet ConsultaDescuentoPrimaAntic() {
		ResultSet r=null;
		try {
			r = dbconector.conexion("select valor\r\n"
					+ "from parametro_configuracion\r\n"
					+ "where nombre = 'PRIMA_SEGURO_PERIODOS_DESCONTAR'\r\n"
					+ "order by id desc;");
			
			
		} catch (Exception e) {
			log.error("********ERROR EJECUTANDO LA CONSULTA EL METODO - ConsultarRegistroCertificacion() ********");
			log.error(e.getMessage());			
		}

		return r;
	}

	public ResultSet EstudioCredito() {
		ResultSet r=null;
		try {
			r = dbconector.conexion("select valor from configuracion_credito cc where tipo = 'ESTUDIO_CREDITO'");
			
			
		} catch (Exception e) {
			log.error("********ERROR EJECUTANDO LA CONSULTA EL METODO - ConsultarRegistroCertificacion() ********");
			log.error(e.getMessage());			
		}

		return r;
	}
    
	public ResultSet porcentajefianza() {
		ResultSet r=null;
		try {
			r = dbconector.conexion("select max(distinct valor_calculo) from configuracion_fianza");
			
			
		} catch (Exception e) {
			log.error("********ERROR EJECUTANDO LA CONSULTA EL METODO - ConsultarRegistroCertificacion() ********");
			log.error(e.getMessage());			
		}

		return r;
	}
	
	public ResultSet colchonpagaduria(String pagaduria) {
		ResultSet r=null;
		try {
			r = dbconector.conexion("select pp.valor from pagaduria p inner join pagaduria_parametro pp on pp.id_pagaduria = p.id inner join parametro pa on pa.id = pp.id_parametro where p.nombre = '"+ pagaduria +"' and pa.nombre = 'colchon';");
						
		} catch (Exception e) {
			log.error("********ERROR EJECUTANDO LA CONSULTA EL METODO - ConsultarRegistroCertificacion() ********");
			log.error(e.getMessage());			
		}

		return r;
	}
	
	public ResultSet CalculoPrima(String Credito) {
		ResultSet r=null;
		try {
			r = dbconector.conexion("select \r\n"
					+ "case when c.numero_radicacion = null then 'true'\r\n"
					+ "else 'false' end as Prima\r\n"
					+ "from credito c \r\n"
					+ "inner join desglose d on d.id_credito = c.id \r\n"
					+ "inner join prima_seguro_anticipada ps on ps.id_desglose = d.id \r\n"
					+ "where 1=1\r\n"
					+ "and d.desglose_seleccionado is true \r\n"
					+ "and c.credito_activo is true\r\n"
					+ "and c.numero_radicacion ='"+Credito+"'");
						
		} catch (Exception e) {
			log.error("********ERROR EJECUTANDO LA CONSULTA EL METODO - ConsultarRegistroCertificacion() ********");
			log.error(e.getMessage());			
		}

		return r;
	}
	
	/*
	* TP - 02/08/2021
	* Se crea la consulta que retorna los valores de mes dos y tasaDos
	* Para realizar los calculos en los simuladores de Cuentas por cobrar capitalizadas*/
	public ResultSet consultarValoresMesCapitalizadas() {
	log.info("*********************** OriginacionCreditoQuery - consultarValoresMesTasaCapitalizadas()");
	ResultSet r= null;
	try {
	r = dbconector.conexion("select valor::integer from configuracion_credito c\r\n"
	+ " where 1=1 \r\n"
	+ " and tipo = 'CAPITALIZACION' and tipo_valor = 'NUMERO_CUOTA';");
	} catch (Exception e) {
	log.error("#################### ERROR - OriginacionCreditoQuery - consultarValoresMesTasaCapitalizadas()#############");
	}
	return r;
	}

	public ResultSet consultarValoresTasaDosCapitalizadas() {
	log.info("*********************** OriginacionCreditoQuery - consultarValoresMesTasaCapitalizadas()");
	ResultSet r= null;
	try {
	r = dbconector.conexion("select valor from configuracion_credito cc\r\n"
	+ "where 1=1 \r\n"
	+ "and tipo = 'CAPITALIZACION' and tipo_valor = 'SEGUNDA_TASA';");
	} catch (Exception e) {
	log.error("#################### ERROR - OriginacionCreditoQuery - consultarValoresMesTasaCapitalizadas()#############");
	}
	return r;
	}
	
	public ResultSet ValorPrimaCreditoPadre(String Credito) {
		ResultSet r=null;
		try {
			r = dbconector.conexion("select round(valor) from prima_seguro_anticipada psa\r\n"
					+ "join desglose d2 \r\n"
					+ "on d2.id=psa.id_desglose \r\n"
					+ "join credito c2\r\n"
					+ "on c2.id = d2.id_credito \r\n"
					+ "where d2.desglose_seleccionado is true \r\n"
					+ "and c2.numero_radicacion = '"+Credito+"';");
						
		} catch (Exception e) {
			log.error("********ERROR EJECUTANDO LA CONSULTA EL METODO - ConsultarRegistroCertificacion() ********");
			log.error(e.getMessage());			
		}

		return r;
	}
	
	
	public ResultSet ValorMontoCreditoPadre(String Credito) {
		ResultSet r=null;
		try {
			r = dbconector.conexion("select round(c2.monto_aprobado) from prima_seguro_anticipada psa\r\n"
					+ "join desglose d2 \r\n"
					+ "on d2.id=psa.id_desglose \r\n"
					+ "join credito c2\r\n"
					+ "on c2.id = d2.id_credito \r\n"
					+ "where d2.desglose_seleccionado is true \r\n"
					+ "and c2.numero_radicacion = '"+Credito+"';");
						
		} catch (Exception e) {
			log.error("********ERROR EJECUTANDO LA CONSULTA EL METODO - ConsultarRegistroCertificacion() ********");
			log.error(e.getMessage());			
		}

		return r;
	}
	
	public ResultSet MesesActivoPadre(String Credito) {
		ResultSet r=null;
		try {
			r = dbconector.conexion("with ConteoPeriodos as (\r\n"
					+ "	select (count(fechas)+complemento-1) Periodos_activos\r\n"
					+ "	from (\r\n"
					+ "	select id_credito, generate_series(mes_contable, now(), cast('1 month' as interval) )fechas,\r\n"
					+ "	case when date_part('day', now()) < date_part('day', mes_contable) then 1 else 0 end complemento\r\n"
					+ "	from movimiento_contable as mc\r\n"
					+ "	where id_credito in(select c2.id from credito c2 where c2.numero_radicacion = '"+Credito+"')\r\n"
					+ "	and tipo_transaccion = 'ACTIVACION_CREDITO'\r\n"
					+ "	) x group by complemento, id_credito\r\n"
					+ "	)\r\n"
					+ "	select * from ConteoPeriodos;");
						
		} catch (Exception e) {
			log.error("********ERROR EJECUTANDO LA CONSULTA EL METODO - ConsultarRegistroCertificacion() ********");
			log.error(e.getMessage());			
		}

		return r;
	}
	
	
	}


	

