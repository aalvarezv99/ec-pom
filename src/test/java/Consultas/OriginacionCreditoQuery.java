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
	
	/*Thainer perez - 17/sep/2021 Consultar el estudio credito del credito padre*/
	public ResultSet consultaEstudioCreditoPadre(String numeroradicado) {
		ResultSet r=null;
		int idCredito = 0;
		int idPagaduria = 0;
		try {
			ResultSet result = this.consultarIdCreditoIdPagaduria(numeroradicado);
			while (result.next()) {
				 idCredito = result.getInt(1);
				 idPagaduria = result.getInt(2);
			}
			r = dbconector.conexion("select coalesce(round(obtener_valor_estudio_credito(current_date,"+ idCredito +","+ idPagaduria+", false)),0) estudiCreditoPadre;");
						
		} catch (Exception e) {
			log.error("********ERROR EJECUTANDO LA CONSULTA  - consultaEstudioCredito() ********");
			log.error(e.getMessage());			
		}

		return r;
	}
	
	/*ThainerPerez - 17/Sep/2021, Se consulta la fianza del credito padre para capitalizacion de retanqueos*/
	public ResultSet consultaFianzaCreditoPadre(String numeroradicado) {
		ResultSet r=null;
		int idCredito = 0;
		int idPagaduria = 0;
		try {
			r = dbconector.conexion("select coalesce(round(d.valor_fianza),0) fianzaPadre from desglose d \r\n"
					+ "where id_credito in (select  id from credito where numero_radicacion ="+ numeroradicado+")\r\n"
					+ "and desglose_seleccionado is true\r\n"
					+ "limit 1;");
						
		} catch (Exception e) {
			log.error("********ERROR EJECUTANDO LA CONSULTA  - consultaFianzaCreditoPadre() ********");
			log.error(e.getMessage());			
		}

		return r;
	}
	
	/*Thainer Perez - 17/09/2021 se crea la consulta para extraer el Id credito y pagaduria*/
	public ResultSet consultarIdCreditoIdPagaduria(String numeroRadicado) {
		ResultSet r=null;
		try {
			r = dbconector.conexion("select c.id, c.id_pagaduria from credito c where numero_radicacion ="+ numeroRadicado+";");
						
		} catch (Exception e) {
			log.error("********ERROR EJECUTANDO LA CONSULTA  - consultarIdCreditoIdPagaduria() ********");
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
	
	public ResultSet consultarPagaduriaRetanq(String numRadicado) {
		ResultSet r = null;
		try {
			r = dbconector.conexion("select p.nombre from credito c \r\n"
					+ "join pagaduria p on c.id_pagaduria = p.id \r\n"
					+ "where c.numero_radicacion ="+numRadicado+";");
						
		} catch (Exception e) {
			log.error("********ERROR EJECUTANDO LA CONSULTA EL METODO - consultarPagaduriaRetanq() ********");
			log.error(e.getMessage());			
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
	
	
	public ResultSet ConsultaProspeccion(String Cedula) {
		ResultSet r=null;
		try {
			r = dbconector.conexion("select c.concepto from central_consulta c\r\n"
					+ "join cliente cl on  cl.id = c.id_cliente \r\n"
					+ "where cl.identificacion = '"+Cedula+"'\r\n"
					+ "and fecha::date=current_date\r\n"
					+ "order by fecha desc limit 1;");
			
			
		} catch (Exception e) {
			log.error("********ERROR EJECUTANDO LA CONSULTA EL METODO - ConsultarRegistroCertificacion() ********");
			log.error(e.getMessage());			
		}

		return r;
	}
	
	/*
	 *ThainerPerez 29-Sep-2021, Se crea un unico metodo para consultar los valores de capitalizados segun la tasa  */
	public ResultSet consultarValoresCapitalizador(String tasa) {
		log.info("******* consultar valores capitalizados, OriginacionCreditoQuery - consultarValoresCapitalizador()***********");
		ResultSet r = null;
		try {
			r = dbconector.conexion("select tasa_inicial, segunda_tasa, estudio_credito, fianza, mes_cambio_tasa \r\n"
					+ "from configuracion_capitalizacion_cxc ccc where tasa_inicial ="+tasa+";");
		} catch (Exception e) {
			log.error("********ERROR EJECUTANDO LA CONSULTA EL METODO - consultarValoresCapitalizador() ********");
			log.error(e.getMessage());
		}
		return r;
	}
	
	public ResultSet ConsultaEstado(String Cedula) {
		ResultSet r=null;
		try {
			r = dbconector.conexion("select c.estado from credito c \r\n"
					+ "join cliente cl on c.id_cliente = cl.id \r\n"
					+ "where 1=1\r\n"
					+ "and cl.identificacion = '"+Cedula+"'\r\n"
					+ "order by c.id desc;");
						
		} catch (Exception e) {
			log.error("********ERROR EJECUTANDO LA CONSULTA EL METODO - Consulta Estado credito********");
			log.error(e.getMessage());			
		}

		return r;
	}
	
	public ResultSet consultarCreditosMultiples(String cedula) {
		ResultSet r=null;
		try {
			r = dbconector.conexion("select coalesce(count(cr.id),0) from cliente c\r\n"
					+ "join credito cr on c.id = cr.id_cliente \r\n"
					+ "join credito_retanqueo crr on crr.id_credito_original = cr.id\r\n"
					+ "where identificacion = '"+cedula+"';");
						
		} catch (Exception e) {
			log.error("********ERROR EJECUTANDO LA CONSULTA EL METODO - Consulta consultarCreditosMultiples()********");
			log.error(e.getMessage());			
		}

		return r;
	}
	
	
	public ResultSet ConsultaCreditoSimuladorAsesor(String Cedula) {//Jonathan Varon
		ResultSet r = null;
		try {
			r = dbconector.conexion("select * from simulador_asesor where id_credito in(select cre.id from credito cre where cre.id_cliente in (select c.id from cliente c where c.identificacion in('"+ Cedula +"')));");

		} catch (Exception e) {
			log.error("********ConsultaCreditoSimuladorAsesor() ********");
			log.error(e.getMessage());
		}

		return r;
	}
	
	/*
	 * ThainerPerez V1.0 - 03/Dic/2021, 1. Se implementa la funcion de calculos directamente desde la base de datos
	 * 									2. Parametros utilizados: (p_monto integer, p_xperiodoprima integer, p_tasainicial numeric,
	 *  															p_plazo numeric, p_diasiniciales numeric, p_vlrcompassaneamientos numeric
 																	p_ingresos numeric, p_descley numeric, p_descnomina numeric, p_pagaduria text)*/
	public ResultSet consultarCalculosSimuladorOriginacion(String Monto,int DesPrimaAntic,String Tasa,String Plazo,
    		String DiasHabilesIntereses,String vlrCompasSaneamientos, String Ingresos, String descLey, 
    		String descNomina,String pagaduria) {
		String Pagaduria = "'"+pagaduria+"'";
		ResultSet r = null;
		try {
			r = dbconector.conexion("select * from public.calculos_automatizacion_pruebas ("+Monto +","+DesPrimaAntic+","+Tasa+
					","+Plazo+","+DiasHabilesIntereses+","+vlrCompasSaneamientos+","+Ingresos+","+descLey+
					","+descNomina+","+Pagaduria+");");
		} catch (Exception e) {
			log.error("********consultarCalculosSimuladorOriginacion() ********");
			log.error(e.getMessage());
		}
		return r;
	}
	
	public ResultSet idClienteCedula(String Cedula) {
		ResultSet r=null;
		try {
			r = dbconector.conexion("select id from cliente where identificacion ='"+Cedula+"';");
			
			
		} catch (Exception e) {
			log.error("********ERROR EJECUTANDO LA CONSULTA EL METODO - ConsultarRegistroCertificacion() ********");
			log.error(e.getMessage());			
		}

		return r;
	}
	
	
	public ResultSet ConsultaToken(String idCliente) {
		ResultSet r=null;
		try {
			r = dbconector.BDtoken("SELECT token from otp o where id_cliente ='"+idCliente+"';");
			
			
		} catch (Exception e) {
			log.error("********ERROR EJECUTANDO LA CONSULTA EL METODO - ConsultarRegistroCertificacion() ********");
			log.error(e.getMessage());			
		}

		return r;
	}
}


	

