package StepsDefinitions.AplicacionPagosSteps;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import Acciones.AplicacionCierreAccion.AplicacionCierreAccion;
import StepsDefinitions.CommunSteps.Driver;
import cucumber.api.java.es.Entonces;
import cucumber.api.java.es.Y;

public class PagosAplicacionFinalSteps {
	
	WebDriver driver;
	Logger log = Logger.getLogger(PagosAplicacionFinalSteps.class);
	AplicacionCierreAccion aplicacioncierreaccion;
	
	public PagosAplicacionFinalSteps() {
		driver = Driver.driver;	
		aplicacioncierreaccion = new AplicacionCierreAccion(driver);
	}
	
	/* APLICACION FINAL STEPS */	
	@Y("^cuando filtre utilizando el (.+) con \"([^\"]*)\" en la pantalla Aplicacion final$")
	public void cuandoFiltreUtilizandoElConEnLaPantallaAplicacionFinal(String periodo, String pagaduria) {
		aplicacioncierreaccion.filtrarTablaAplicacion(pagaduria, periodo, "NO");
	}

	@Y("^Se muestra un unico registro permitiendo confirmar el pago$")
	public void seMuestraUnUnicoRegistroPermitiendoConfirmarElPago() throws Throwable {
		aplicacioncierreaccion.iniciarAplicacionFinal();
	}
	
	@Entonces("^en pantalla se visualiza el siguiente mensaje \"([^\"]*)\"$")
	public void enPantallaSeVisualizaElSiguienteMensaje(String notificacion) throws Throwable {
		aplicacioncierreaccion.mensajeFinalizacion(notificacion);
	}


	 @Y("^Refresque el navegador haste que cambie a \"([^\"]*)\" el \"([^\"]*)\" la \"([^\"]*)\" y (.+)$")
	    public void refresqueElNavegadorHasteQueCambieAElLaY( String vlrfila, String vlrColumna, String pagaduria, String periodo) throws Throwable {
		 aplicacioncierreaccion.validarCambioestado(vlrfila, vlrColumna, pagaduria, periodo);
	    }
		
	
	/*APLICACION FINAL STEPS*/
	 
	 /*STEPS CIERRE*/
	 @Y("^Se muestra un unico registro permitiendo cerrar la pagaduria$")
		public void seMuestraUnUnicoRegistroPermitiendoCerrarLaPagaduria() throws Throwable {
			aplicacioncierreaccion.iniciarCierrePagaduria();
		}
		

}
