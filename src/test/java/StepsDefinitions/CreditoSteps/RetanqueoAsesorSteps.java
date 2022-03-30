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
	
	public RetanqueoAsesorSteps() throws InterruptedException {		
		//super(driver);
		this.driver = Driver.driver;				
		retanqueocreditos = new RetanqueoCreditos(driver);		
	}
	
	@Cuando("El agente ingrese a la pestana retanqueo")
	public void  Elagenteingresealapestanaretanqueo() {
		log.info("@STEP - El agente ingrese a la pestana retanqueo - @STEP");
		retanqueocreditos.ingresarRetanqueoAsesor();
	}
	
	@Y("se filtra por {string}{string}")
	public void sefiltrapor (String Cedula, String Credito) throws InterruptedException {
		log.info("@STEP - se filtra por cedula: "+ Cedula +" y Credito: " +Credito +" - @STEP");
		retanqueocreditos.FiltrarCredito(Cedula, Credito);
	}
	
	@Y("se da clic a retanquear")
	public void sedaclicaretanquear () throws InterruptedException {
		log.info("@STEP - se da clic a retanquear - @STEP");
		retanqueocreditos.Retanquear();
	}
	
	
	@Y("se busca el credito por {string}")
	public void sebuscaelcreditopor (String Cedula) throws InterruptedException {
		log.info("@STEP - Se busca el credito por cedula "+ Cedula +" - @STEP");
		retanqueocreditos.Credito(Cedula);
	}
	
	@Y("se selecciona el retanqueo")
	public void seseleccionaelretanqueo(){
		log.info("@STEP - se selecciona el retanqueo - @STEP");
		retanqueocreditos.seleccionarRetanqueo();
	}
	
	@Y("borrar archivos")
	public void borrararchivos() throws InterruptedException {
		log.info("@STEP - borrar archivos - @STEP");
		retanqueocreditos.borrarArchivos();
	}
	
	@Y("cargar archivos nuevos {string}")
	public void cargararchivosnuevos (String pdf) throws InterruptedException {
		log.info("@STEP - cargar archivos nuevos - @STEP");
		retanqueocreditos.CargarArchivos(pdf);
	}
	
	@Y("se solicita la consulta a centrales de riesgo")
	public void sesolicitalaconsultaacentralesderiesgo () throws InterruptedException {
		log.info("@STEP - se solicita la consulta a centrales de riesgo - @STEP");
		retanqueocreditos.ConsultaCentrales();
	}
	
	@Y("marcar el credito viable")
	public void marcarelcreditoviable() throws InterruptedException, NumberFormatException, SQLException{
		log.info("@STEP - marcar el credito viable - @STEP");
		retanqueocreditos.Seguridad();
	}
	
	@Y("ingresar al simulador interno y llenar los campos {string}{string}{string}{string}{string}{string}{string}{string}")
	public void ingresaralsimuladorinternoyllenarloscampos(String retanqueo,String Tasa, String Plazo,String DiasHabilesIntereses ,String Ingresos ,String descLey,String descNomina,String VlrCompraSaneamiento) throws NumberFormatException, SQLException {
		log.info("@STEP - ingresar al simulador interno y llenar los campos - @STEP");
		retanqueocreditos.Simulador(retanqueo,Tasa, Plazo, DiasHabilesIntereses, Ingresos, descLey, descNomina,VlrCompraSaneamiento);
	}
	
	@Y("se validan los datos del simulador {string}{string}{string}{string}{string}{string}{string}{string}")
	public void sevalidanlosdatosdelsimulador(String Ingresos, String descLey, String descNomina,String Tasa,String Plazo,String Credito,String DiasHabilesIntereses,String VlrCompraSaneamiento) throws NumberFormatException, SQLException {
		log.info("@STEP - se validan los datos del simulador - @STEP");
		retanqueocreditos.ValidarSimulador(Ingresos, descLey, descNomina,Tasa,Plazo,Credito,DiasHabilesIntereses,VlrCompraSaneamiento);
	}
	
	@Y("se da clic en solicitar")
	public void sedaclicensolicitar() throws InterruptedException {
		log.info("@STEP - se da clic en solicitar - @STEP");
		retanqueocreditos.SolicitarCredito();
	}
	
	@Y("se confirma identidad en digitalizacion {string}")
	public void seconfirmaidentidadendigitalizacion (String codigo) {
		log.info("@STEP - se confirma identidad en digitalizacion - @STEP");
		retanqueocreditos.Confirmaidentidad(codigo);
	}
	
	@Y("se aprueban las referencias de la pagaduria")
	public void seapruebanlasreferenciasdelapagaduria () {
		log.info("@STEP - se aprueban las referencias de la pagaduria - @STEP");
		retanqueocreditos.AprobarReferenciasPagaduria();
	}
	
	@Entonces("Valida los valores del simulador retanqueos {string}{string}{string}{string}{string}{string}{string}{string}{string}{string}{string}{string}")
	public void Validalosvaloresdelsimuladorretanqueos(String Anno,String Credito,String retanqueo,String fecha,String Mes, String Plazo,String Ingresos, String descLey, String descNomina,String DiasHabilesIntereses, String Tasa, String VlrCompraSaneamiento) throws InterruptedException, SQLException {
		log.info("@STEP - Valida los valores del simulador retanqueos - @STEP");
		retanqueocreditos.ValidarSimuladorAnalistaRetanqueos(Anno,Credito,retanqueo,fecha,Mes,Plazo,Ingresos,descLey,descNomina,DiasHabilesIntereses, Tasa, VlrCompraSaneamiento);
	}
	
	@Y("valide la informacion cabecera con sus conceptos para Retanqueo{string}{string}")
	public void validelainformacioncabeceraconsusconceptosparaRetanqueo (String Tasa, String Plazo) {
		log.info("@STEP - valide la informacion cabecera con sus conceptos para Retanqueo - @STEP");
		retanqueocreditos.validelainformacioncabeceraconsusconceptosparaRetanqueo(Tasa,Plazo);
	}
	
    @Entonces("Valida los valores del simulador retanqueos con compra de cartera y saneamiento {string}{string}{string}{string}{string}{string}{string}{string}{string}{string}{string}{string}{string}")
    public void validalosvaloresdelsimuladorretanqueosconcompradecarteraysaneamiento(String Anno,String Credito,String Retanqueo,String fecha,String Mes,String Plazo,String Ingresos,String descLey,String descNomina,String Cartera1,String Saneamiento2,String DiasHabilesIntereses, String Tasa) throws InterruptedException, SQLException {
    	retanqueocreditos.ValidarSimuladorAnalistaRetanqueosCarteraSaneamiento(Anno,Credito,Retanqueo, fecha, Mes, Plazo, Ingresos, descLey, descNomina, Cartera1, Saneamiento2,DiasHabilesIntereses, Tasa);
	}
    
	
	@Y("se filtra por monto para retanqueo y se edita {string}{string}{string}{string}{string}{string}{string}{string}")
	public void sefiltrapormontopararetanqueoyseedita(String Banco, String rutaPDF,String cedula,String tasa, String Credito, String Plazo, String DiasHabilesIntereses, String VlrCompraSaneamiento) throws InterruptedException, SQLException {
		log.info("@STEP - se filtra por monto para retanqueo y se edita - @STEP");
		retanqueocreditos.DescargarMediosdedispercionRetanqueo(Banco,rutaPDF,cedula,tasa,Credito,Plazo,DiasHabilesIntereses,VlrCompraSaneamiento); 
	}
	
	@Y("se valida el estado del credito padre {string}{string}")
	public void sevalidaelestadodelcreditopadre(String Credito,String FechaRegistro) throws InterruptedException, SQLException {
		log.info("@STEP - se valida el estado del credito padre - @STEP");
		retanqueocreditos.validarEstadoCreditoPadre(Credito,FechaRegistro);
	}
	
	@Y("se valida el estado del credito padre {string}")
	public void sevalidaelestadodelcreditopadre(String FechaRegistro) throws InterruptedException, SQLException {
		log.info("@STEP - se valida el estado del credito padre - @STEP");
		retanqueocreditos.validarEstadoCreditoPadreMultiple(FechaRegistro);
	}
	
	@Y("se validan los valores de las condiciones del credito {string}{string}{string}")
	public void sevalidanlosvaloresdelascondicionesdelcredito(String Credito, String Plazo, String DiasHabilesIntereses ) throws NumberFormatException, SQLException, InterruptedException {
		log.info("@STEP - se validan los valores de las condiciones del credito - @STEP");
		retanqueocreditos.ValidarValoresLlamadoBienvenidaRetanqueo(Credito, Plazo, DiasHabilesIntereses);
	}
	
	@Y("se validan los valores de las condiciones del credito Multiple {string}{string}{string}{string}{string}{string}")
	public void sevalidanlosvaloresdelascondicionesdelcreditoMultiple(String cedula, String pagaduria,String Tasa,String Plazo,String DiasHabilesIntereses,String VlrCompraSaneamiento) throws NumberFormatException, SQLException, InterruptedException {
		log.info("@STEP - se validan los valores de las condiciones del credito Multiple - @STEP");
		retanqueocreditos.ValidarValoresLlamadoBienvenidaRetanqueoMultiple(cedula, pagaduria,Tasa,Plazo,DiasHabilesIntereses,VlrCompraSaneamiento);
	}
	
	@Y("validar las condiciones de la carta de notificacion de creditos {string}")
	public void validarLasCondicionesDeLaCartaDeNotificacionDeCreditos(String cedula) throws NumberFormatException, SQLException {
		log.info("@STEP - validar las condiciones de la carta de notificacion de creditos - @STEP");
		retanqueocreditos.validarLasCondicionesDeLaCartaDeNotificacionDeCreditos(cedula);
	}

	@Y("se validan los datos del simulador retanqueo multiple {string}{string}{string}{string}{string}{string}{string}{string}{string}")
	public void sevalidanlosdatosdelsimuladorRetanqueoMultiple(String cedula, String pagaduria,String Ingresos, String descLey, String descNomina,String Tasa,String Plazo,String DiasHabilesIntereses,String VlrCompraSaneamiento) throws NumberFormatException, SQLException {
		log.info("@STEP - se validan los datos del simulador retanqueo multiple - @STEP");
		retanqueocreditos.ValidarSimuladorRetanqueoMultiple(cedula,pagaduria,Ingresos, descLey, descNomina,Tasa,Plazo,DiasHabilesIntereses,VlrCompraSaneamiento);
	}

	@Entonces("Valida los valores del simulador retanqueo multiple {string}{string}{string}{string}{string}{string}{string}{string}{string}{string}")
	public void validarSimuladorAnalistaRetanqueosMultiple(String cedula, String pagaduria,String DiasHabilesIntereses,String Anno,String retanqueo,String fecha,String Mes, String Plazo, String Tasa, String VlrCompraSaneamiento) throws InterruptedException, SQLException {
		log.info("@STEP - Valida los valores del simulador retanqueo multiple - @STEP");
		retanqueocreditos.validarSimuladorAnalistaRetanqueosMultiple(cedula, pagaduria, DiasHabilesIntereses ,  Anno , retanqueo , fecha , Mes , Plazo , Tasa, VlrCompraSaneamiento);
	}

	/*ThainePerez V1.23/Marzo/2022 : 1. Se agregan los valores (cedula, pagaduria y diashabiles intereses)*/
	@Entonces("Valida los valores del simulador retanqueos con compra de cartera y saneamiento retanqueo multiple {string}{string}{string}{string}{string}{string}{string}{string}{string}{string}{string}")
	public void validalosvaloresdelsimuladorretanqueosCSS(String cedula,String pagaduria,String DiasHabilesIntereses,String Anno,String Retanqueo,String fecha,String Mes,String Plazo,String Cartera1,String Saneamiento2, String Tasa) throws InterruptedException, SQLException {
		log.info("@STEP - Valida los valores del simulador retanqueos con compra de cartera y saneamiento retanqueo multiple - @STEP");
		retanqueocreditos.validarSimuladorAnalistaRetanqueosCCS( cedula, pagaduria, DiasHabilesIntereses,Anno, Retanqueo, fecha, Mes, Plazo, Cartera1, Saneamiento2, Tasa);
	}
}
