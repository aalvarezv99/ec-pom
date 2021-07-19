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
	}


	

