package StepsDefinitions.CreditoSteps;

import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import Acciones.CreditoAccion.RetanqueoCreditos;
import CommonFuntions.BaseTest;
import StepsDefinitions.CommunSteps.Driver;
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
	public void sesolicitalaconsultaacentralesderiesgo () throws InterruptedException {
		retanqueocreditos.ConsultaCentrales();
	}
	
	@Y("marcar el credito viable")
	public void marcarelcreditoviable() throws InterruptedException{
		retanqueocreditos.Seguridad();
	}
	
	@Y("ingresar al simulador interno y llenar los campos {string}{string}{string}{string}{string}{string}{string}{string}")
	public void ingresaralsimuladorinternoyllenarloscampos(String retanqueo,String Tasa, String Plazo,String DiasHabilesIntereses ,String Ingresos ,String descLey,String descNomina,String VlrCompraSaneamiento) throws NumberFormatException, SQLException {
		retanqueocreditos.Simulador(retanqueo,Tasa, Plazo, DiasHabilesIntereses, Ingresos, descLey, descNomina,VlrCompraSaneamiento);
	}
	
	@Y("se validan los datos del simulador {string}{string}{string}{string}{string}{string}{string}{string}")
	public void sevalidanlosdatosdelsimulador(String Ingresos, String descLey, String descNomina,String Tasa,String Plazo,String Credito,String DiasHabilesIntereses,String VlrCompraSaneamiento) throws NumberFormatException, SQLException {
		retanqueocreditos.ValidarSimulador(Ingresos, descLey, descNomina,Tasa,Plazo,Credito,DiasHabilesIntereses,VlrCompraSaneamiento);
	}
	
	@Y("se da clic en solicitar")
	public void sedaclicensolicitar() throws InterruptedException {
		retanqueocreditos.SolicitarCredito();
	}
	
	@Y("se confirma identidad en digitalizacion {string}")
	public void seconfirmaidentidadendigitalizacion (String codigo) {
		retanqueocreditos.Confirmaidentidad(codigo);
	}
	
	@Y("se aprueban las referencias de la pagaduria")
	public void seapruebanlasreferenciasdelapagaduria () {
		retanqueocreditos.AprobarReferenciasPagaduria();
	}
	
	@Entonces("Valida los valores del simulador retanqueos {string}{string}{string}{string}{string}{string}{string}{string}{string}{string}")
	public void Validalosvaloresdelsimuladorretanqueos(String Anno,String Credito,String retanqueo,String fecha,String Mes, String Plazo,String Ingresos, String descLey, String descNomina,String DiasHabilesIntereses) throws InterruptedException, SQLException {
		retanqueocreditos.ValidarSimuladorAnalistaRetanqueos(Anno,Credito,retanqueo,fecha,Mes,Plazo,Ingresos,descLey,descNomina,DiasHabilesIntereses);
	}
	
	@Entonces("Valida los valores del simulador retanqueos {string}{string}{string}{string}{string}{string}{string}{string}{string}{string}{string}")
	public void Validalosvaloresdelsimuladorretanqueos(String Anno,String Credito,String retanqueo,String fecha,String Mes, String Plazo,String Ingresos, String descLey, String descNomina, String cartera,String DiasHabilesIntereses) throws InterruptedException, SQLException {
		retanqueocreditos.ValidarSimuladorAnalistaRetanqueos(Anno,Credito,retanqueo,fecha,Mes,Plazo,Ingresos,descLey,descNomina,cartera,DiasHabilesIntereses);
	}
	
    @Entonces("Valida los valores del simulador retanqueos con compra de cartera y saneamiento {string}{string}{string}{string}{string}{string}{string}{string}{string}{string}{string}{string}")
    public void validalosvaloresdelsimuladorretanqueosconcompradecarteraysaneamiento(String Anno,String Credito,String Retanqueo,String fecha,String Mes,String Plazo,String Ingresos,String descLey,String descNomina,String Cartera1,String Saneamiento2,String DiasHabilesIntereses) throws InterruptedException, SQLException {
    	retanqueocreditos.ValidarSimuladorAnalistaRetanqueosCarteraSaneamiento(Anno,Credito,Retanqueo, fecha, Mes, Plazo, Ingresos, descLey, descNomina, Cartera1, Saneamiento2,DiasHabilesIntereses);
	}
    
	
	@Y("se filtra por monto para retanqueo y se edita {string}{string}{string}")
	public void sefiltrapormontopararetanqueoyseedita(String Retanqueo,String Banco, String rutaPDF) {
		retanqueocreditos.DescargarMediosdedispercionRetanqueo(Retanqueo,Banco,rutaPDF);
	}
	
	@Y("se validan los valores de las condiciones del credito {string}")
	public void sevalidanlosvaloresdelascondicionesdelcredito(String Credito) throws NumberFormatException, SQLException {
		retanqueocreditos.ValidarValoresLlamadoBienvenidaRetanqueo(Credito);
	}
	
}
