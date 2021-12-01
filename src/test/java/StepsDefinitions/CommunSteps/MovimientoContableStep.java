package StepsDefinitions.CommunSteps;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import Acciones.AplicacionCierreAccion.AplicacionCierreAccion;
import Acciones.ComunesAccion.MovimientoContableAccion;
import StepsDefinitions.AplicacionPagosSteps.AplicacionCierrePagaduriaSteps;
import cucumber.api.java.es.Cuando;
import cucumber.api.java.es.Y;

public class MovimientoContableStep {
	
	WebDriver driver;
	Logger log = Logger.getLogger(MovimientoContableStep.class);
	MovimientoContableAccion movimientoContableAccion;
	
	public MovimientoContableStep() {
		driver = Driver.driver;	
		movimientoContableAccion = new MovimientoContableAccion(driver);
	}
	
    @Y("^el sistema valida por (.+) y (.+) en la tabla movimiento contable las (.*) que se proceso por el bridge en la (.+)$")
    public void elSistemaValidaPorEnLaTablaMovimientoContableQueSeProcesoPorElBridge(String numeroradicadocredito, String numCedula, String accountingSource, String fecha) throws Throwable {
    	movimientoContableAccion.validarProcesoBridge(numeroradicadocredito, numCedula, accountingSource.replace("\"", ""), fecha);
    }
    
    @Y("^valide la causacion de movimientos (.+) con sus tipos y valores usando el (.+) en la (.+)$")
    public void valideLaCausacionDeMovimientosConSusTiposYValoresUsandoEl(String accountingsource, String numRadicado, String fechaRegistro) throws Throwable {
    	movimientoContableAccion.validarCausacionMovimientos(accountingsource.replaceAll("\"", ""), numRadicado, fechaRegistro);
    }

    @Y("^valida que las cuentas de libranzas (.*) sean las del bridge (.*) con el (.+) y (.+) en la (.+)$")
    public void validaQueLasCuentasDeLibranzasSeanLasDelBridgeConElY(String accountingsource, String accountingname, String numradicado, String numcedula, String fechaRegistro) throws Throwable {
    	movimientoContableAccion.compararCuentasLibranzasVsBridge(numradicado, accountingsource.replaceAll("\"", ""),accountingname.replaceAll("\"", ""), numcedula, fechaRegistro);
    }
   

    @Y("^finalmente se valida la transaccion (.*) con (.+) en la base de datos de PSL con el (.+)$")
    public void finalmenteSeValidaLaTransaccionConEnLaBaseDeDatosDePSLConEl(String accountingsource, String fecharegistro, String numradicado) throws Throwable {
    	movimientoContableAccion.validacionPSL(accountingsource.replaceAll("\"", ""), fecharegistro, numradicado);
    }
    
    /*STEPS Aplicacion de pagos Inicio*/
    @Cuando("^Se consulten los creditos cargados para (.+) y se comparen con los que generaron movimientos contables (.+) en la (.+)$")
    public void seConsultenLosCreditosCargadosParaYSeComparenConLosQueGeneraronMovimientosContablesEnLa(String idpagaduria, String accountingsource, String fecharegistro) throws Throwable {
    	movimientoContableAccion.validarCargueContraLibranzas(idpagaduria, accountingsource.replaceAll("\"", ""), fecharegistro);
    }
    
    @Cuando("^el sistema valida por (.+) en la tabla movimiento contable las (.+) que se proceso por el bridge en la (.+)$")
    public void elSistemaValidaPorEnLaTablaMovimientoContableLasQueSeProcesoPorElBridgeEnLa(String idPagaduria, String accountingsource, String fecharegistro) throws Throwable {
    	movimientoContableAccion.validarBridgeMasivo(idPagaduria, accountingsource.replaceAll("\"", ""), fecharegistro);
    }
    
    @Y("^valide la causacion de movimientos (.+) con sus tipos y valores usando la (.+) en la (.+)$")
    public void valideLaCausacionDeMovimientosConSusTiposYValoresUsandoLa(String accountingsource, String idPagaduria, String fechaRegistro) throws Throwable {
    	movimientoContableAccion.validarCausacionMovimientosMasivo(accountingsource.replaceAll("\"", ""), idPagaduria, fechaRegistro);
    }
    
    @Y("^valida que las cuentas de libranzas (.*) sean las del bridge (.*) con el (.+) en la (.+)$")
    public void validaQueLasCuentasDeLibranzasSeanLasDelBridgeConElY(String accountingsource, String accountingname, String numradicado, String fechaRegistro) throws Throwable {
    	movimientoContableAccion.compararCuentasLibranzasVsBridge(numradicado, accountingsource.replaceAll("\"", ""),accountingname.replaceAll("\"", ""), fechaRegistro);
    }
    
    /*Step Aplicacion de pagos final*/

}
