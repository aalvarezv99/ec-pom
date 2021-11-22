package Acciones.ComunesAccion;

import static org.junit.Assert.assertTrue;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import CommonFuntions.BaseTest;
import Consultas.MovimientoContableQuery;
import dto.MovimientoContableDto;


/* Autor:ThainerPerez ----- Fecha:12/11/2021 -----  Version:V1.0 --- 
 * Descripcion: Se crea la clase y sus metodos inciales y  la validacion contable para CCRED ACRED y CSALD
 * */
public class MovimientoContableAccion extends BaseTest{
	
	MovimientoContableQuery queryDinamica;
	private static Logger log = Logger.getLogger(MovimientoContableAccion.class);
	List<MovimientoContableDto> listMovContable = new ArrayList<MovimientoContableDto>();
	String numCedula = "";
	public MovimientoContableAccion(WebDriver driver) {
		super(driver);
		queryDinamica = new MovimientoContableQuery();
	}
	
	/*ThainerPerez 12/Nov/2021, Se valida que se proceso por el bridge*/
	public void validarProcesoBridge(String numRadicado, String numCedula, String accountingSource, String fechaRegistro) {
		log.info("******* Se valida que se registro en el bridge, MovimientoContableAccion -validarProcesoBridge()*****");
		ResultSet result = null;
		this.numCedula = numCedula;
		try {
			
			if(numRadicado.equals("null")) {
				numRadicado = consultarNumradicadoPorCedula(numCedula);
			}
			
			long start_time = System.currentTimeMillis();
	        long wait_time = 30000;
	        long end_time = start_time + wait_time;
	        String detalle="";
	        String origin = "";
			while (System.currentTimeMillis() < end_time && (detalle.equals("") ||detalle.equals("Pendiente de ser procesado por el Bridge Contable"))) {
				result = queryDinamica.validarDetalleBridge(numRadicado, accountingSource, fechaRegistro);				
				while(result.next()) {
					origin = result.getString(1);
					detalle = result.getString(2);
				}				
			}
			if (detalle.equals("Registered in the accounting bridge"))
				log.info("******* 'Exitoso' - Se Proceso el movimiento por el Bridge contable ******");
			else {
				assertBooleanImprimeMensaje("#### "+ origin +"  ERROR - No se ha procesado por el bridge revisar la tabla  'movimiento_contable' ###", true);
			}
				
		} catch (Exception e) {
			log.error("############## 'ERROR' MovimientoContableAccion - validarProcesoBridge() ################"+e);
			assertTrue("########## MovimientoContableAccion - validarProcesoBridge()########"+ e,false);
		}
		
	}
	
	/*ThainerPerez 12/Nov/2021, Se valida la causacion de intereses con los movimientos de tipo 1 y 2, los debitos y creditos*/
	public void validarCausacionMovimientos(String accountingSource, String numRadicado, String fecha) {
		log.info("******* Validar que la suma de los movimientos credito y debito coincidan, MovimientoContableAccion - validarCausacionMovimientos() ******");
		ResultSet result = null;
		Boolean caousacion = false;
		
		if(numRadicado.equals("null")) {
			numRadicado = consultarNumradicadoPorCedula(this.numCedula);
		}
		
		try {
			result = queryDinamica.validarCausacionMovimientos(accountingSource, numRadicado, fecha);
			while(result.next()) {
				caousacion = result.getBoolean(1);
			}
			if(caousacion==false) {
				assertBooleanImprimeMensaje("#### ERROR - validar la causacion de movimientos contables, no hizo match en la suma de los tipos debito y credito ###", true);
			}
			log.info("******** 'Exitoso' - se validaron los debitos y creditos de los movimientos contables *******");
		} catch (Exception e) {
			log.error("############## 'ERROR' MovimientoContableAccion - validarCausacionMovimientos() ################"+e);
			assertTrue("########## MovimientoContableAccion - validarCausacionMovimientos()########"+ e,false);
		}
	}
	
	/*ThainerPerez 12/Nov/2021, Se realiza la comparacion Libranzas Vs Bridge con las cuentas que correspondan*/
	public void compararCuentasLibranzasVsBridge(String numRadicado, String accountingSource, String accountingName, String cedula, String fecha) {
		log.info("*********** Se compara libranzas con el bridge, MovimientoContableAccion - compararCuentasLibranzasVsBridge()********************");
		ResultSet result = null;
		
		if(numRadicado.equals("null")) {
			numRadicado = consultarNumradicadoPorCedula(cedula);
		}

		result = queryDinamica.consultarMovimientos(numRadicado, accountingSource,fecha);
		
		try {
			
			while (result.next()) {
				MovimientoContableDto movimientoLibranza = new MovimientoContableDto();
				movimientoLibranza.setTipoMovimiento(result.getString(1));
				movimientoLibranza.setCuenta(result.getString(2));
				movimientoLibranza.setTipoTransaccion(result.getString(3));
				listMovContable.add(movimientoLibranza);				
			}
			result.close();
			int count = 0;
			String cuentas = "";
			for (MovimientoContableDto movimientoContableDto : listMovContable) {
				cuentas = cuentas +"'" + movimientoContableDto.getCuenta() + "'";
				count ++;
				log.info("Cuentas Libranzas ("+(count)+") - " + movimientoContableDto.getCuenta() +" | " + movimientoContableDto.getTipoMovimiento() + " | " + movimientoContableDto.getTipoTransaccion());
			}
			cuentas = cuentas.replace("''", "','") ;

			List<MovimientoContableDto> listMovCuentasBridge = new ArrayList<MovimientoContableDto>();
			result = queryDinamica.consultarCuentasBridge(accountingName, cuentas);
			while (result.next()) {
				MovimientoContableDto movimiento = new MovimientoContableDto();
				movimiento.setNombreProcessoAcounting(result.getString(1));
				movimiento.setNombreCuentaAcouting(result.getString(2));
				movimiento.setCuenta(result.getString(3));
				movimiento.setTipoTransaccion(result.getString(4));
				listMovCuentasBridge.add(movimiento);
			}
			result.close();
			
			count=0;
			for (MovimientoContableDto movimientoContableDto : listMovCuentasBridge) {
				count ++;
				log.info("Cuentas Bridge("+(count)+") - " + movimientoContableDto.getCuenta() +" | " + movimientoContableDto.getNombreProcessoAcounting() + " | " + movimientoContableDto.getTipoTransaccion());
			}

			for (MovimientoContableDto movimientoContableDto : listMovContable) {
				MovimientoContableDto prueba = listMovCuentasBridge.stream()
						  .filter(filtro -> movimientoContableDto.getCuenta().equals(filtro.getCuenta()))
						  .findAny()
						  .orElse(null);
				if(prueba==null) {
					log.error("No se encontro la cuenta de libranzas "+ movimientoContableDto.getCuenta()  +" En acoounting bridge");
				}
				
			}
			
			if (listMovContable.size() != listMovCuentasBridge.size()) {
				assertBooleanImprimeMensaje(
						"###### ERROR - la cantidad de cuentas de libranzas y accounting bridge no coinciden ####",
						true);
			}
			
			log.info(" 'Exitoso' - Las cuentas de libranzas se encuentran en el bridge");

		} catch (Exception e) {
			log.error("############## 'ERROR' MovimientoContableAccion - compararCuentasLibranzasVsBridge() ################"+e);
			assertTrue("########## MovimientoContableAccion - compararCuentasLibranzasVsBridge()########"+ e,false);
		}

	}
	
	/*ThainerPerez 12/Nov/2021, se valida Libranzas vs PSL, buscando la coincidencia de las cuentas*/
	public void validacionPSL(String acountingsource,  String fecharegistro, String numradicado) {
		ResultSet result = null;
		log.info("***** validando movimientos libranzas contra PSL , MovimientoContableAccion - validacionPSL()******");
		try {
			
		if(numradicado.equals("null")) {
			numradicado = consultarNumradicadoPorCedula(this.numCedula);
		}
		
		int count=0;
		for (MovimientoContableDto movimientoContableDto : listMovContable) {
			count++;
			log.info("Cuentas Libranzas ("+(count)+") - "+ movimientoContableDto.getCuenta() +" | " + movimientoContableDto.getTipoMovimiento() + " | " + movimientoContableDto.getTipoTransaccion());
		}
		
		count = 0;
		List<MovimientoContableDto> listMovPslCo_detalcompr = new ArrayList<MovimientoContableDto>();
		result = queryDinamica.consultarCuentasPSL(acountingsource,fecharegistro, numradicado );
		while (result.next()) {
			MovimientoContableDto movimiento = new MovimientoContableDto();
			movimiento.setNumeroRadicado(result.getString(1));
			movimiento.setNombreCuentaAcouting(result.getString(2));
			movimiento.setTipoTransaccion(result.getString(3));
			movimiento.setCuenta(result.getString(4));
			movimiento.setValor(result.getString(5));
			listMovPslCo_detalcompr.add(movimiento);
		}
		result.close();
		
		for (MovimientoContableDto movimientoContableDto : listMovPslCo_detalcompr) {
			count ++;
			log.info("Cuentas PSL - co_detalcompr ("+(count)+") - "+  movimientoContableDto.getCuenta() +" | " + movimientoContableDto.getNombreCuentaAcouting() + " | " + movimientoContableDto.getTipoTransaccion());
		}
		
		for (MovimientoContableDto movimientoContableDto : listMovContable) {
			MovimientoContableDto prueba = listMovPslCo_detalcompr.stream()
					  .filter(filtro -> movimientoContableDto.getCuenta().equals(filtro.getCuenta()))
					  .findAny()
					  .orElse(null);
			if(prueba==null) {
				log.error("No se encontro la cuenta de libranzas "+ movimientoContableDto.getCuenta()  +" En PSL co_detalcompr");
			}			
		}
		
		if (listMovContable.size() != listMovPslCo_detalcompr.size()) {
			
			List<MovimientoContableDto> listMovPslCo_movimentra = new ArrayList<MovimientoContableDto>();
			result = queryDinamica.consultarCuentaPslCo_movimentra(acountingsource,fecharegistro, numradicado );
			while (result.next()) {
				MovimientoContableDto movimiento = new MovimientoContableDto();
				movimiento.setMmensaje(result.getString(1));
				movimiento.setNumeroRadicado(result.getString(2));
				movimiento.setNombreCuentaAcouting(result.getString(3));
				movimiento.setTipoMovimiento(result.getString(4));
				movimiento.setCuenta(result.getString(5));
				movimiento.setValor(result.getString(6));
				listMovPslCo_movimentra.add(movimiento);
			}
			result.close();
			count=0;
			for (MovimientoContableDto movimientoContableDto : listMovPslCo_movimentra) {
				count++;
				log.error("Cuentas PSL - co_movimientra  ("+(count)+") |"+  movimientoContableDto.getMmensaje() +"|" + movimientoContableDto.getCuenta() +" | " + movimientoContableDto.getNombreCuentaAcouting() + " | " + movimientoContableDto.getTipoMovimiento());
			}
			
			assertBooleanImprimeMensaje(
					"###### ERROR - la cantidad de cuentas de libranzas y PSL no coinciden - 'Revisar las tablas co_movimientra, co_detalcompr' ####",
					true);
			}
			
		} catch (Exception e) {
			log.error("############## 'ERROR' MovimientoContableAccion() - validacionPSL() ################"+e);
			assertTrue("########## MovimientoContableAccion - validacionPSL()########"+ e,false);
		}
		
	}
	
	/*ThainerPerez 12/11/2021, Se consulta el numero de radicado para los procesos que no lo tienen como prerequisito*/
	public String consultarNumradicadoPorCedula(String numCedula) {
		String numRadicado = "";
		ResultSet result = null;
		try {
			result = queryDinamica.consultarNumeroradicado(numCedula);
			while(result.next()) {
				numRadicado = result.getString(1);
			}
		} catch (Exception e) {
			log.error("############## 'ERROR' consultarNumeroradicado() - consultarNumradicadoPorCedula() ################"+e);
			assertTrue("########## MovimientoContableAccion - consultarNumradicadoPorCedula()########"+ e,false);
		}
		return numRadicado;
	}

}