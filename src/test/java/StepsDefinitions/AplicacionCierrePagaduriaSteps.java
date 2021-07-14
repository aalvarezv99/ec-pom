package StepsDefinitions;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import Acciones.AplicacionCierreAccion.AplicacionCierreAccion;
import cucumber.api.PendingException;
import cucumber.api.java.es.Cuando;
import cucumber.api.java.es.Entonces;
import cucumber.api.java.es.Y;

public class AplicacionCierrePagaduriaSteps {
	
	WebDriver driver;
	Logger log = Logger.getLogger(AplicacionCierrePagaduriaSteps.class);
	AplicacionCierreAccion aplicacioncierre;
	
	public AplicacionCierrePagaduriaSteps() {
		driver = Driver.driver;	
		aplicacioncierre = new AplicacionCierreAccion(driver);
	}
	
	@Cuando("^Navegue al modulo de pagos y seleccione \"([^\"]*)\"$")
    public void navegueAlModuloDePagosYSeleccioneSomething(String opcion) throws Throwable {
        aplicacioncierre.NavegarPagoConOpcion(opcion);
    }  

    @Y("^en la pantalla cargue de lista de pagos seleccione el (.+) para el ano actual$")
    public void enLaPantallaCargueDeListaDePagosSeleccioneElParaElAnoActual(String periodo) throws Throwable {
       log.info(periodo);
    }

    @Y("^Ingrese el (.+) en el campo pagaduria verificando que no se ha cargado anteriormente$")
    public void ingreseElEnElCampoPagaduriaVerificandoQueNoSeHaCargadoAnteriormente(String nombrepagaduria) throws Throwable {
        log.info(nombrepagaduria);
    }

    @Y("seleccione el boton cargar")
    public void seleccioneElBotonCargar() throws Throwable {
        throw new PendingException();
    }

    @Y("^cargue la pagaduria (.+) que se encuentra en la ruta (.+)$")
    public void cargueLaPagaduriaQueSeEncuentraEnLaRuta(String nombrepagaduria, String rutapagaduria) throws Throwable {
        log.info(rutapagaduria);
    }
    
    @Entonces("^cargara la pagaduria de manera exitosa mostando el mensaje \"([^\"]*)\"$")
    public void cargaraLaPagaduriaDeManeraExitosaMostandoElMensajeSomething(String Mensaje) throws Throwable {
       log.info(Mensaje);
    }

    @Y("se validara el valor listado con el valor del sistema terminando con el proceso")
    public void seValidaraElValorListadoConElValorDelSistemaTerminandoConElProceso() throws Throwable {
        throw new PendingException();
    }

	
}
