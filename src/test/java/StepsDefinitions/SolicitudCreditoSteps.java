package StepsDefinitions;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import Acciones.OriginacionCreditosAccion;
import CommonFuntions.BaseTest;
import cucumber.api.java.es.Cuando;

public class SolicitudCreditoSteps {
	
	WebDriver driver;
	Logger log = Logger.getLogger(SolicitudCreditoSteps.class);
	OriginacionCreditosAccion originacionaccion;
	BaseTest baseTest;
	
	public SolicitudCreditoSteps() {		
		driver = Driver.driver;		
		originacionaccion = new OriginacionCreditosAccion(driver);
		baseTest = new BaseTest(driver);
	}
	
	@Cuando("el agente ingrese a la pestana solicitud credito")
	public void elAgenteIngreseALaPestanaSolicitudCredito() {
		originacionaccion.ingresarSolicitudCredito();
	}

}
