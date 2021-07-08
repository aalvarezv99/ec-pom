package StepsDefinitions;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import Acciones.RecaudosAccion;
import cucumber.api.java.es.Cuando;
import cucumber.api.java.es.Entonces;
import cucumber.api.java.es.Y;

public class RecaudosSteps {
	
	WebDriver driver;
	Logger log = Logger.getLogger(RecaudosSteps.class);	
	RecaudosAccion recaudoAccion;
	String vlrTotalPagar = "";
	
	public RecaudosSteps() {		
		driver = Driver.driver;		
		recaudoAccion = new RecaudosAccion(driver);
	}
	
	@Cuando("^El agente visualice el documento del certificado \"([^\"]*)\" del credito (.+)$")
    public void elAgenteVisualiceElDocumentoDelCertificadoDelCredito(String rutadocumento, String numradicado) {    
		recaudoAccion.abrirCertificacionNavegador(rutadocumento, numradicado);	
    } 
	
	@Y("Navegue a la pestana pagos de recaudos")
	public void navegueALaPestanaPagosDeRecaudos() throws InterruptedException {
		recaudoAccion.ingresarVentanaRecaudo(); 
	}
	
	@Y("^Realice el recaudo con el valor (.+) y origen \"([^\"]*)\"$")
    public void realiceElRecaudoConElValorYOrigenSomething(String vlrpago, String origen) throws Throwable {
		recaudoAccion.seleccionarPagaduria(vlrpago,origen);
    }

	@Entonces("^permite generar el recaudo con el (.+) para \"([^\"]*)\" con los datos del cliente (.+)$")
	public void permiteGenerarElRecaudoConElParaConLosDatosDelCliente(String vlrRecaudo, String Descripcion, String numCedula) {			
		recaudoAccion.recaudoCliente(vlrRecaudo,Descripcion, numCedula, null);
	}
	
	@Y("^Realice el recaudo del credito (.+) con el valor total a pagar \"([^\"]*)\" para \"([^\"]*)\" con los datos del cliente (.+)$")
	public void realiceElRecaudoConElValorTotalAPagarPrepagoConLosDatosDelCliente(String numRadicado, String rutaCert, String Descripcion, String numCedula) {	    	    
	    recaudoAccion.recaudoCliente(recaudoAccion.consultarVlrTotal(numRadicado, rutaCert), Descripcion, numCedula,numRadicado);		
	}
	
	@Entonces("^se finaliza verificando el estado del credito (.+) que cambio a \"([^\"]*)\"$")
    public void seFinalizaVerificandoElEstadoDelCreditoQueCambioASomething(String numradicado, String estadoCredito) {
		recaudoAccion.validarEstadoCredito(numradicado, estadoCredito);
	}

	@Entonces("la amortizacion del prepago y lo movimientos contables en bases de datos")
	public void laAmortizacionDelPrepagoYLoMovimientosContablesEnBasesDeDatos() {
	}

}
