package Consultas;

import static org.junit.Assert.assertTrue;

import java.sql.ResultSet;

import org.apache.log4j.Logger;

public class MovimientoContableQuery {
	
	private static Logger log = Logger.getLogger(MovimientoContableQuery.class);
	ConexionBase dbconector = new ConexionBase();
	
	/*CONSULTAS APLICACION DE PAGOS APLPAG*/
	/**/
	public ResultSet consultarCreditosMasivos(String accoutingSource, String idPagaduria, String fecharegistro) {
		ResultSet r= null;
		try {
			r = dbconector.conexion("select c.numero_radicacion\r\n"
					+ "					from movimiento_contable mc \r\n"
					+ "					join credito c on c.id = mc.id_credito \r\n"
					+ "					join pagaduria p on c.id_pagaduria = p.id \r\n"
					+ "					where 1=1\r\n"
					+ "					and to_char(fecha_registro::date,'DD/MM/YYYY') in ('"+fecharegistro+"')\r\n"
					+ "					and p.id = "+idPagaduria+" \r\n"
					+ "					and (mc.transaccion_contable::json->>'accountingSource'::text) in ("+accoutingSource+")\r\n"
					+ "				order by c.numero_radicacion asc	");
		} catch (Exception e) {
			log.error("********ERROR EJECUTANDO LA CONSULTA EL METODO - consultarCreditosConMovimientos() ********");
			assertTrue("########## MovimientoContableAccion - consultarCreditosConMovimientos()########"+ e.getMessage(),false);
		}
		return r;
	}
	
	public ResultSet validarProcesamientoBridge(String idPagaduria, String fechaRegistro, String accoutingSource) {
		ResultSet r = null;
		try {
			r = dbconector.conexion("with bridgeLibranza as (	\r\n"
					+ "select count (distinct movCont.detalles), movCont.detalles\r\n"
					+ "from detalle_pago_nomina dp \r\n"
					+ "left join causal ca on dp.id_causal = ca.id\r\n"
					+ "join credito c on c.id = dp.id_credito \r\n"
					+ "join  (select c.id, c.numero_radicacion, c.estado, mc.detalles \r\n"
					+ "					from movimiento_contable mc \r\n"
					+ "					join credito c on c.id = mc.id_credito \r\n"
					+ "					join pagaduria p on c.id_pagaduria = p.id \r\n"
					+ "					where 1=1\r\n"
					+ "					and to_char(fecha_registro::date,'DD/MM/YYYY') = '"+fechaRegistro+"'\r\n"
					+ "					and p.id = "+idPagaduria+"\r\n"
					+ "					and (mc.transaccion_contable::json->>'accountingSource'::text) in ("+accoutingSource+")\r\n"
					+ "				order by c.numero_radicacion asc) movCont on movCont.id = dp.id_credito \r\n"
					+ "join pagaduria p on c.id_pagaduria = p.id\r\n"
					+ "where 1=1\r\n"
					+ "and p.id = "+idPagaduria+"\r\n"
					+ "and to_char(dp.fecha_recepcion_pago ::date,'DD/MM/YYYY') in ('"+fechaRegistro+"')\r\n"
					+ "and dp.estado_incorporacion not in ('NUEVO_FUTURO')\r\n"
					+ "group  by movCont.detalles)\r\n"
					+ "select * from bridgeLibranza;");
		} catch (Exception e) {
			log.error("********ERROR EJECUTANDO LA CONSULTA EL METODO - buscarCreditosPlanilla() ********");
			assertTrue("########## MovimientoContableAccion - buscarCreditosPlanilla()########"+ e.getMessage(),false);
		}
		return r;
	}
	
	public ResultSet buscarCreditosPlanilla(String idPagaduria, String fechaRegistro, String accoutingSource) {
		ResultSet r = null;
		try {
			r= dbconector.conexion("select c.numero_radicacion, dp.bloqueo_causal, c.estado, dp.estado_incorporacion, coalesce(ca.nombre,'Sin Causal') causal\r\n"
					+ "from detalle_pago_nomina dp \r\n"
					+ "left join causal ca on dp.id_causal = ca.id\r\n"
					+ "join credito c on c.id = dp.id_credito \r\n"
					+ "left join (select c.id, c.numero_radicacion, c.estado, mc.detalles \r\n"
					+ "					from movimiento_contable mc \r\n"
					+ "					join credito c on c.id = mc.id_credito \r\n"
					+ "					join pagaduria p on c.id_pagaduria = p.id \r\n"
					+ "					where 1=1\r\n"
					+ "					and to_char(fecha_registro::date,'DD/MM/YYYY') in ('"+fechaRegistro+"')\r\n"
					+ "					and p.id = "+idPagaduria+" \r\n"
					+ "					and (mc.transaccion_contable::json->>'accountingSource'::text) in ("+accoutingSource+")\r\n"
					+ "				order by c.numero_radicacion asc) movCont on movCont.id = dp.id_credito \r\n"
					+ "join pagaduria p on c.id_pagaduria = p.id\r\n"
					+ "where 1=1\r\n"
					+ "and movCont.id is null\r\n"
					+ "and p.id = "+idPagaduria+" \r\n"
					+ "and to_char(dp.fecha_recepcion_pago ::date,'DD/MM/YYYY') in ('"+fechaRegistro+"')\r\n"
					+ "and dp.estado_incorporacion not in ('NUEVO_FUTURO')\r\n"
					+ "order by c.numero_radicacion asc;");
			
		} catch (Exception e) {
			log.error("********ERROR EJECUTANDO LA CONSULTA EL METODO - buscarCreditosPlanilla() ********");
			assertTrue("########## MovimientoContableAccion - buscarCreditosPlanilla()########"+ e.getMessage(),false);
		}
		return r;
	}
	
	
	/*CONSULTAS DINAMICAS CSAD, CCRED Y ACRED*/
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
			assertTrue("########## MovimientoContableAccion - validarProcesoBridge()########"+ e.getMessage(),false);
		}
		
		return r;
	}
	
	/*ThainerPerez 11/Nov/2021 - Se validan la causacion de los movimientos contables*/
	public ResultSet validarCausacionMovimientos(String accountingSource, String numRadicado, String fecha) {
		ResultSet r = null;
		try {
			r= dbconector.conexion("with consulta as (select c.numero_radicacion ,mcc.tipo_movimiento , mcc.valor valor\r\n"
					+ "from movimiento_contable mc\r\n"
					+ "inner join movimiento_contable_cuenta mcc on mc.id = mcc.id_movimiento_contable\r\n"
					+ "inner join credito c on mc.id_credito = c.id\r\n"
					+ "inner join pagaduria p on c.id_pagaduria = p.id\r\n"
					+ "where mc.id_credito in (select id from credito where numero_radicacion in ("+numRadicado+"))\r\n"
					+ "and (mc.transaccion_contable::json->>'accountingSource'::text) in ("+accountingSource+")\r\n"
					+ "					and to_char(fecha_registro::date,'DD/MM/YYYY') = '"+fecha+"'\r\n"
					+ "group by c.numero_radicacion,  mcc.tipo_movimiento,  mcc.valor),\r\n"
					+ "movTipoDos as (select round(sum(mu.valor)) sumaDos from consulta mu where mu.tipo_movimiento = 2),\r\n"
					+ "movTipoUno as (select round(sum(mu.valor)) sumaUno from consulta mu where mu.tipo_movimiento = 1)\r\n"
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
	
	/*ThainerPerez 12/Nov/2021 Se crea el query para sacer el numero de radicado por numero de cedula, se usa mas en originacion*/
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
	
	/*ThainerPerez 12/Nov/2021 Se consultan los movimientos de libranzas basado en los parametros 
	 * accountingSource seran las fuentes = 'CESALD', 'CCRED', 'ACRED', etc*/
	public ResultSet consultarMovimientos(String numeroRadicado, String accountinSource, String fecha) {
		ResultSet r=null;
		try {
			r = dbconector.conexion("select distinct  mcc.tipo_movimiento ,mcc.cuenta, mc.tipo_transaccion, c.numero_radicacion\r\n"
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
	
	/*ThainerPerez 12/11/2021 Se consultan las cuentas en la base de datos del bridge correspondiente a cada instancia
	 * 
	 * */
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
	
	/*ThainerPerez 12/Nov/2021 Consultar movimientos en PSL en la tabla co_detalcompr de la DB PSL-PRUEBAS*/
	public ResultSet consultarCuentasPSL(String AcoountingSource, String fecha, String numradicado) {
		ResultSet r = null;
		try {
			r = dbconector.conexionPSL("select \r\n"
					+ "	dcocodielem4,\r\n"
					+ "	replace(dcofuente,'SETT','CSALD') origin,\r\n"
					+ "	dcooperacion,\r\n"
					+ "	dcocuenta,\r\n"
					+ "	dcovalomoneloca\r\n"
					+ "from\r\n"
					+ "	co_detalcompr\r\n"
					+ "where 1=1\r\n"
					+ "	and dcocodielem4 in ("+numradicado+")	\r\n"
					+ "	and replace(dcofuente,'SETT','CSALD') in  ("+AcoountingSource+")\r\n"
					+ "	and dcofechtran in ('"+fecha+"')\r\n"
					+ " order by dcocomprobante desc;");
		} catch (Exception e) {
			log.error("##ERROR EJECUTANDO LA CONSULTA EL METODO - consultarCuentasPSL()##");
			log.error(e.getMessage());	
		}
		return r;
	}
	
	/*ThainerPerez 12/Nov/2021 Consultar movimientos en PSL en la tabla co_movimentra de la DB PSL-PRUEBAS, donde se buscan los movimientos no procesados*/
	public ResultSet consultarCuentaPslCo_movimentra(String AcoountingSource, String fecha, String numradicado) {
		ResultSet r=null;
		try {
			r = dbconector.conexionPSL("select menmensaje, mencodielem4, replace(menfuente,'SETT','CSALD'), menoperacion, mencuenta, menvalomoneloca \r\n"
					+ "from co_movimentra \r\n"
					+ "WHERE 1=1\r\n"
					+ "and replace(menfuente,'SETT','CSALD') in  ("+AcoountingSource+")\r\n"
					+ "and mencodielem4 in ("+numradicado+")\r\n"
					+ "and menfechtran = '"+fecha+"'\r\n"
					+ "order by mencodielem4 asc;");
		} catch (Exception e) {
			log.error("********ERROR EJECUTANDO LA CONSULTA EL METODO - consultarCuentaPslCo_movimentra() ********");
			log.error(e.getMessage());			
		}

		return r;
	}
}
