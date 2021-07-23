package StepsDefinitions.AplicacionPagosSteps;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import Acciones.AplicacionCierreAccion.AplicacionCierreAccion;
import StepsDefinitions.Driver;
import cucumber.api.java.es.Entonces;
import cucumber.api.java.es.Y;

public class PagosPreaplicacionSteps {
	
	WebDriver driver;
	Logger log = Logger.getLogger(PagosPreaplicacionSteps.class);
	AplicacionCierreAccion aplicacioncierreaccion;
	
	public PagosPreaplicacionSteps() {
		driver = Driver.driver;	
		aplicacioncierreaccion = new AplicacionCierreAccion(driver);
	}
	
/*PREAPLICACION STEPS*/
    
    @Y("^valide que no se ha realizado una preaplicacion anteriormente con el (.+)$")
    public void valideQueNoSeHaRealizadoUnaPreaplicacionAnteriormenteConEl(String idpagaduria) throws Throwable {
    	aplicacioncierreaccion.validarCheckPreaplicacion(idpagaduria);
    }

	@Y("valide que el valor del recaudo sea igual al de recibido")
	public void valideQueElValorDelRecaudoSeaIgualAlDeRecibido() throws Throwable {
		aplicacioncierreaccion.capturarValidarValoresPreaplicacion();
	}

	@Entonces("^permite realizar la preaplicacion mostrando el mensaje \"([^\"]*)\"$")
    public void permiteRealizarLaPreaplicacionMostrandoElMensaje(String mensaje) throws Throwable {
		aplicacioncierreaccion.realizarPreaplicacion(mensaje);
    }

	@Y("^se finaliza con el mensaje \"([^\"]*)\"$")
	public void seFinalizaConElMensaje(String mensaje) throws Throwable {
		aplicacioncierreaccion.mensajeFinalizacion(mensaje);
	}

    
    /*PREAPLICACION STEPS*/

}
