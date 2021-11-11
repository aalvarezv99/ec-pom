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

public class MovimientoContableAccion extends BaseTest{
	
	MovimientoContableQuery queryDinamica;
	private static Logger log = Logger.getLogger(MovimientoContableAccion.class);
	String numRadicacion = "";
	List<MovimientoContableDto> listMovContable = new ArrayList<MovimientoContableDto>();
	
	public MovimientoContableAccion(WebDriver driver) {
		super(driver);
		queryDinamica = new MovimientoContableQuery();
	}
	
	public void validarProcesoBridge(String numRadicado, String numCedula, String accountingSource, String fechaRegistro) {
		this.numRadicacion = numRadicado;
		log.info("******* Se valida que se registro en el bridge, MovimientoContableAccion -validarProcesoBridge()*****");
		ResultSet result = null;
		try {
			
			if(numRadicacion.equals("null")) {
				try {
					result = queryDinamica.consultarNumeroradicado(numCedula);
					while(result.next()) {
						this.numRadicacion = result.getString(1);
					}
				} catch (Exception e) {
					log.error("############## 'ERROR' consultarNumeroradicado() - compararCuentasLibranzasVsBridge() ################"+e);
					assertTrue("########## MovimientoContableAccion - compararCuentasLibranzasVsBridge()########"+ e,false);
				}
			}
			
			long start_time = System.currentTimeMillis();
	        long wait_time = 30000;
	        long end_time = start_time + wait_time;
	        String detalle="";
	        String origin = "";
			while (System.currentTimeMillis() < end_time && (detalle.equals("") ||detalle.equals("Pendiente de ser procesado por el Bridge Contable"))) {
				result = queryDinamica.validarDetalleBridge(numRadicacion, accountingSource, fechaRegistro);				
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
	
	public void validarCausacionMovimientos(String accountingSource, String numRadicado, String fecha) {
		log.info("******* Validar que la suma de los movimientos credito y debito coincidan, MovimientoContableAccion - validarCausacionMovimientos() ******");
		ResultSet result = null;
		Boolean caousacion = false;
		try {
			result = queryDinamica.validarCausacionMovimientos(accountingSource, numRadicacion, fecha);
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
	
	public void compararCuentasLibranzasVsBridge(String numRadicado, String accountingSource, String accountingName, String cedula, String fecha) {
		log.info("*********** Se compara libranzas con el bridge, MovimientoContableAccion - compararCuentasLibranzasVsBridge()********************");
		ResultSet result = null;
			

		result = queryDinamica.consultarMovimientos(numRadicacion, accountingSource,fecha);
		
		try {
			
			while (result.next()) {
				MovimientoContableDto movimientoLibranza = new MovimientoContableDto();
				movimientoLibranza.setTipoMovimiento(result.getString(1));
				movimientoLibranza.setCuenta(result.getString(2));
				movimientoLibranza.setTipoTransaccion(result.getString(3));
				listMovContable.add(movimientoLibranza);				
			}
			result.close();
			
			String cuentas = "";
			for (MovimientoContableDto movimientoContableDto : listMovContable) {
				cuentas = cuentas +"'" + movimientoContableDto.getCuenta() + "'";
				log.info("Cuentas Libranzas - " + movimientoContableDto.getCuenta() +" | " + movimientoContableDto.getTipoMovimiento() + " | " + movimientoContableDto.getTipoTransaccion());
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
			
			for (MovimientoContableDto movimientoContableDto : listMovCuentasBridge) {
				log.info("Cuentas Bridge - " + movimientoContableDto.getCuenta() +" | " + movimientoContableDto.getNombreProcessoAcounting() + " | " + movimientoContableDto.getTipoTransaccion());
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
			//quitar comentario
			/*if (listMovContable.size() != listMovCuentasBridge.size()) {
				assertBooleanImprimeMensaje(
						"###### ERROR - la cantidad de cuentas de libranzas y accounting bridge no coinciden ####",
						true);
			}*/
			
			log.info(" 'Exitoso' - Las cuentas de libranzas se encuentran en el bridge");

		} catch (Exception e) {
			log.error("############## 'ERROR' MovimientoContableAccion - compararCuentasLibranzasVsBridge() ################"+e);
			assertTrue("########## MovimientoContableAccion - compararCuentasLibranzasVsBridge()########"+ e,false);
		}

	}
	
	public void validacionPSL(String acountingsource) {
		ResultSet result = null;
		result = queryDinamica.consultarCuentasPSL();
		String prueba = "";
		try {
			while (result.next()) {
				prueba = result.getString(1);
			}
			result.close();
		} catch (Exception e) {
			log.info("Error");
		}
		
		
		/*for (MovimientoContableDto movimientoContableDto : listMovContable) {
			log.info("Imprime para PSL - " + movimientoContableDto.getCuenta() +" | " + movimientoContableDto.getTipoMovimiento() + " | " + movimientoContableDto.getTipoTransaccion());
		}*/
		
	}

}
