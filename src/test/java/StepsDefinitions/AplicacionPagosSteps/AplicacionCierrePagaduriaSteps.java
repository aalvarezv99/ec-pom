package StepsDefinitions.AplicacionPagosSteps;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import Acciones.AplicacionCierreAccion.AplicacionCierreAccion;
import StepsDefinitions.Driver;
import cucumber.api.PendingException;
import cucumber.api.java.es.Cuando;
import cucumber.api.java.es.Entonces;
import cucumber.api.java.es.Y;

public class AplicacionCierrePagaduriaSteps {
	
	WebDriver driver;
	Logger log = Logger.getLogger(AplicacionCierrePagaduriaSteps.class);
	AplicacionCierreAccion aplicacioncierreaccion;
	
	public AplicacionCierrePagaduriaSteps() {
		driver = Driver.driver;	
		aplicacioncierreaccion = new AplicacionCierreAccion(driver);
	}
	
	/*CARGUE ARCGIVO STEPS*/
	@Cuando("^Navegue al modulo de pagos y seleccione \"([^\"]*)\"$")
    public void navegueAlModuloDePagosYSeleccioneSomething(String opcion) throws Throwable {
		aplicacioncierreaccion.NavegarPagoConOpcion(opcion);
    }  

    @Y("^en la pantalla cargue de lista de pagos seleccione el (.+) para el ano actual$")
    public void enLaPantallaCargueDeListaDePagosSeleccioneElParaElAnoActual(String periodo) throws Throwable {
    	aplicacioncierreaccion.SeleccionarPeriodoAno(periodo);
    }

    @Y("^Ingrese el (.+) en el campo pagaduria verificando que no se ha cargado anteriormente$")
    public void ingreseElEnElCampoPagaduriaVerificandoQueNoSeHaCargadoAnteriormente(String nombrepagaduria) throws Throwable {
        aplicacioncierreaccion.escribirPagaduriaValidarCargue(nombrepagaduria);
    }    

    @Y("^cargue la pagaduria (.+) que se encuentra en la ruta \"([^\"]*)\"$")
    public void cargueLaPagaduriaQueSeEncuentraEnLaRuta(String nombrepagaduria, String rutapagaduria) throws Throwable {
        aplicacioncierreaccion.cargarArchivoPagaduria(nombrepagaduria, rutapagaduria);
    }
    
    @Entonces("^cargara la pagaduria de manera exitosa mostando el mensaje \"([^\"]*)\"$")
    public void cargaraLaPagaduriaDeManeraExitosaMostandoElMensaje(String Mensaje) throws Throwable {
       aplicacioncierreaccion.validarMensajeCargueTerminado(Mensaje);
    }

    @Y("^se valida el valor listado de la (.+) para el (.+) con el valor del sistema terminando con el proceso$")
    public void seValidaElValorListadoDeLaParaElConElValorDelSistemaTerminandoConElProceso(String nombrepagaduria, String periodo) throws Throwable {
       aplicacioncierreaccion.validarVlrPlanillaContraSistema(nombrepagaduria, periodo);
    }
    /*CARGUE ARCHIVO STEPS*/
    
    
    

	
}
