package StepsDefinitions;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import Acciones.OriginacionCreditosAccion;
import Acciones.RetanqueoCreditos;
import CommonFuntions.BaseTest;
import cucumber.api.java.es.Cuando;
import cucumber.api.java.es.Entonces;
import cucumber.api.java.es.Y;

public class RetanqueoAsesorSteps {
	
	WebDriver driver;
	Logger log = Logger.getLogger(SimuladorAsesorSteps.class);	
	RetanqueoCreditos retanqueocreditos;	
	BaseTest baseTest;
	
	public RetanqueoAsesorSteps() {		
		//super(driver);
		this.driver = Driver.driver;				
		retanqueocreditos = new RetanqueoCreditos(driver);		
	}
	
	@Cuando("El agente ingrese a la pestana retanqueo")
	public void  Elagenteingresealapestanaretanqueo() {
		retanqueocreditos.ingresarRetanqueoAsesor();
	}
	
	@Y("se filtra por {string}{string}")
	public void sefiltrapor (String Cedula, String Credito) throws InterruptedException {
		retanqueocreditos.FiltrarCredito(Cedula, Credito);
	}

	@Y("se da clic a retanquear")
	public void sedaclicaretanquear () throws InterruptedException {
		retanqueocreditos.Retanquear();
	}
	
	@Y("se busca el credito por {string}")
	public void sebuscaelcreditopor (String Cedula) throws InterruptedException {
		retanqueocreditos.Credito(Cedula);
	}
	
	@Y("se selecciona el retanqueo")
	public void seseleccionaelretanqueo(){
		retanqueocreditos.seleccionarRetanqueo();
	}
	
	@Y("borrar archivos")
	public void borrararchivos() throws InterruptedException {
		retanqueocreditos.borrarArchivos();
	}
	
	@Y("cargar archivos nuevos {string}")
	public void cargararchivosnuevos (String pdf) throws InterruptedException {
		retanqueocreditos.CargarArchivos(pdf);
	}
	
	@Y("se solicita la consulta a centrales de riesgo")
	public void sesolicitalaconsultaacentralesderiesgo () {
		retanqueocreditos.ConsultaCentrales();
	}
	
	@Y("marcar el credito viable")
	public void marcarelcreditoviable(){
		retanqueocreditos.Seguridad();
	}
	
	@Y("ingresar al simulador interno y llenar los campos {string}{string}{string}{string}{string}{string}{string}")
	public void ingresaralsimuladorinternoyllenarloscampos(String Tasa, String Plazo, String Monto,String DiasHabilesIntereses ,String Ingresos ,String descLey,String descNomina) {
		retanqueocreditos.Simulador(Tasa, Plazo, Monto, DiasHabilesIntereses , Ingresos , descLey, descNomina);
	}
	
	
}
