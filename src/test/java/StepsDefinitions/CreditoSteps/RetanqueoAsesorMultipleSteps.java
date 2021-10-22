package StepsDefinitions.CreditoSteps;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import Acciones.CreditoAccion.RetanqueoMultipleAccion;
import CommonFuntions.BaseTest;
import StepsDefinitions.CommunSteps.Driver;
import cucumber.api.java.es.Y;

public class RetanqueoAsesorMultipleSteps {

    WebDriver driver;
    Logger log = Logger.getLogger(SimuladorAsesorSteps.class);
    RetanqueoMultipleAccion retanqueomultipleaccion;
    BaseTest baseTest;

    public RetanqueoAsesorMultipleSteps() throws InterruptedException {
        this.driver = Driver.driver;
        retanqueomultipleaccion = new RetanqueoMultipleAccion(driver);

    }

    @Y("se filtra por {string} {string} para retanqueo multiple")
    public void sefiltraporCedulaPagaduriapararetanqueomultiple(String Cedula, String Pagaduria)
            throws InterruptedException {
        retanqueomultipleaccion.FiltrarCreditoMultiple(Cedula, Pagaduria);
    }

    @Y("se da clic a retanquear a todos los creditos")
    public void sedaclicaretanquearatodosloscreditos() throws InterruptedException {
        retanqueomultipleaccion.RetanquearMultiples();
    }

    @Y("^se ingresa el monto a solicitar (.+)$")
    public void seIngresaElMontoASolicitar(String Retanqueo) throws Throwable {
        retanqueomultipleaccion.LlenarMontoSolicitar(Retanqueo);
    }
}
