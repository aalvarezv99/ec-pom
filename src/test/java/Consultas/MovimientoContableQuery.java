package Consultas;

import java.sql.ResultSet;

import org.apache.log4j.Logger;

public class MovimientoContableQuery {
	
	private static Logger log = Logger.getLogger(CertificacionSaldoQuery.class);
	ConexionBase dbconector = new ConexionBase();
	
	/*ThainerPerez 11/nov/2021 - Se valida que se halla registrado en el bridge contable*/
	public ResultSet validarDetalleBridge(String numRadicado, String accountingSource, String fecha) {
		ResultSet r = null;
		try {
			r= dbconector.conexion("select (mc.transaccion_contable::json->>'origin'::text) origin,  mc.detalles, mc.transaccion_contable from movimiento_contable mc \r\n"
					+ "					where id_credito in (select id from credito where numero_radicacion in ("+numRadicado+"))\r\n"
					+ "					and to_char(fecha_registro::date,'DD/MM/YYYY') = '"+fecha+"'\r\n"
					+ "					and (mc.transaccion_contable::json->>'accountingSource'::text) in ("+accountingSource+");"); 
		} catch (Exception e) {
			log.error("********ERROR EJECUTANDO LA CONSULTA EL METODO - validarDetalleBridge() ********");
			log.error(e.getMessage());	
		}
		
		return r;
	}
	
	/*ThainerPerez 11/Nov/2021 - Se validan la causacion de los movimientos contables*/
	public ResultSet validarCausacionMovimientos(String accountingSource, String numRadicado, String fecha) {
		ResultSet r = null;
		try {
			r= dbconector.conexion("with consulta as (select c.numero_radicacion ,mcc.tipo_movimiento , sum(round(mcc.valor)) valor\r\n"
					+ "from movimiento_contable mc\r\n"
					+ "inner join movimiento_contable_cuenta mcc on mc.id = mcc.id_movimiento_contable\r\n"
					+ "inner join credito c on mc.id_credito = c.id\r\n"
					+ "inner join pagaduria p on c.id_pagaduria = p.id\r\n"
					+ "where mc.id_credito in (select id from credito where numero_radicacion in ("+numRadicado+"))\r\n"
					+ "and (mc.transaccion_contable::json->>'accountingSource'::text) in ("+accountingSource+")\r\n"
					+ "					and to_char(fecha_registro::date,'DD/MM/YYYY') = '"+fecha+"'\r\n"
					+ "group by c.numero_radicacion,  mcc.tipo_movimiento,  mcc.valor),\r\n"
					+ "movTipoDos as (select sum(mu.valor) sumaDos from consulta mu where mu.tipo_movimiento = 2),\r\n"
					+ "movTipoUno as (select sum(mu.valor) sumaUno from consulta mu where mu.tipo_movimiento = 1)\r\n"
					+ "select case when  mu.sumaUno=md.sumaDos then\r\n"
					+ "true\r\n"
					+ "else false\r\n"
					+ "end as causacion\r\n"
					+ "from movTipoDos md, movTipoUno mu;"); 
		} catch (Exception e) {
			log.error("********ERROR EJECUTANDO LA CONSULTA EL METODO - validarDetalleBridge() ********");
			log.error(e.getMessage());	
		}
		
		return r;
	}
	
	public ResultSet consultarNumeroradicado(String cedula) {
		ResultSet r = null;
		try {
			r = dbconector.conexion("select c.numero_radicacion, c.estado from cliente cl\r\n"
					+ "join credito c on cl.id = c.id_cliente\r\n"
					+ "where cl.identificacion = '"+cedula+"'\r\n"
					+ "and c.numero_radicacion is not null \r\n"
					+ "order by c.id desc\r\n"
					+ "limit 1;");
	
		} catch (Exception e) {
			log.error("********ERROR EJECUTANDO LA CONSULTA EL METODO - consultarNumeroradicado() ********");
			log.error(e.getMessage());	
		}
	    return r;
		
	}
	
	public ResultSet consultarMovimientos(String numeroRadicado, String accountinSource, String fecha) {
		ResultSet r=null;
		try {
			r = dbconector.conexion("select distinct  mcc.tipo_movimiento ,mcc.cuenta, mc.tipo_transaccion\r\n"
					+ "from movimiento_contable mc\r\n"
					+ "inner join movimiento_contable_cuenta mcc on mc.id = mcc.id_movimiento_contable\r\n"
					+ "inner join credito c on mc.id_credito = c.id\r\n"
					+ "inner join pagaduria p on c.id_pagaduria = p.id\r\n"
					+ "where 1=1\r\n"
					+ "and mc.id_credito in (select id from credito c where c.numero_radicacion ="+ numeroRadicado+")\r\n"
					+ "and (mc.transaccion_contable::json->>'accountingSource'::text) in ("+accountinSource+")\r\n"
					+ "	and to_char(mc.fecha_registro::date,'DD/MM/YYYY') = '"+fecha+"'\r\n"
					+ "and mcc.valor <> 0\r\n"
					+ "order by mcc.cuenta asc;	");
			
			
		} catch (Exception e) {
			log.error("********ERROR EJECUTANDO LA CONSULTA EL METODO - consultarMovimientos() ********");
			log.error(e.getMessage());			
		}

		return r;
	}	
	
	public ResultSet consultarCuentasBridge(String acoountinName , String cuentas) {
		ResultSet r=null;
		try {
			r = dbconector.conexionAcountingBridge("select ad.name, ac.\"name\", ac.account, cc.accounting_nature_id\r\n"
					+ "from accounting_dynamics ad \r\n"
					+ "inner join concept_configuration cc on cc.accounting_dynamics_id = ad.id \r\n"
					+ "inner join accounting_concept ac on ac.id = cc.accounting_concept_id \r\n"
					+ "where 1=1\r\n"
					+ "and ac.account in ("+cuentas+")\r\n"
					+ "and upper(ad.\"name\") in ("+acoountinName+")\r\n"
					+ "and ac.enabled is true\r\n"
					+ "order by ac.account asc ;");
			
			
		} catch (Exception e) {
			log.error("********ERROR EJECUTANDO LA CONSULTA EL METODO - consultarCuentasBridge() ********");
			log.error(e.getMessage());			
		}

		return r;
	}
	
	public ResultSet consultarCuentasPSL() {
		ResultSet r = null;
		try {
			r = dbconector.conexionPSL("select mencuenta \r\n"
					+ "from co_movimentra \r\n"
					+ "WHERE 1=1\r\n"
					+ "and mencodielem4 in (87387);");
		} catch (Exception e) {
			log.error("##ERROR -  cconultando en PSL ##");
		}
		return r;
	}
}
