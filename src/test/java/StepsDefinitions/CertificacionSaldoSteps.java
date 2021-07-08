package StepsDefinitions;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import Acciones.CertificacionSaldosAccion;
import CommonFuntions.BaseTest;
import cucumber.api.java.es.Cuando;
import cucumber.api.java.es.Entonces;

public class CertificacionSaldoSteps {
	
	WebDriver driver;
	Logger log = Logger.getLogger(CertificacionSaldoSteps.class);
	CertificacionSaldosAccion certificacionAccion;
	//BaseTest baseTest;
	
	public CertificacionSaldoSteps() {		
		//driver = Driver.driver;		
		certificacionAccion = new CertificacionSaldosAccion(driver);
		//baseTest = new BaseTest(driver);
	}
	
	
	@Cuando("ingrese a la opcion de certificacion de saldos")
	public void ingreseALaOpcionDeCertificacionDeSaldos() throws Exception {
		try {
			certificacionAccion.ingresarSolicitudCertificado();	
		} catch (Exception e) {
			log.fatal("#ERROR###"+e);
			throw new Exception();
		}
			
	}

	@Cuando("este ingrese el numero de radicado {int} con cedula {int} para un cliente con credito {string} se aplicara el filtro")
	public void esteIngreseElNumeroDeRadicadoConCedulaParaUnClienteConCreditoSeAplicaraElFiltro(String Value) {	
		log.info(Value);
	}
	
	@Cuando("este ingrese el numero de radicado con cedula para un cliente con credito en MORA se aplicara el filtro")
	public void esteIngreseElNumeroDeRadicadoConCedulaParaUnClienteConCreditoEnMORASeAplicaraElFiltro() {
		log.info("****CERTIFICACION EN MORA****");
	}

	@Cuando("seleccionando el boton solicitar")
	public void seleccionandoElBotonSolicitar() {
		log.info("Ingresa al boton solicitar");
	}

	@Cuando("este ingrese la informacion en la ventana solicitar presionando el boton guardar")
	public void esteIngreseLaInformacionEnLaVentanaSolicitarPresionandoElBotonGuardar() {
		log.info("Ingresa a la ventana y da guardar");
	}

	@Entonces("permite generar el recaudo para la certificacion con los datos del cliente")
	public void permiteGenerarElRecaudoParaLaCertificacionConLosDatosDelCliente() {
		log.info("Ingresa a y genera el recaudo");
	}

	@Entonces("posteriormente descargando la certificacion en el modulo gestion certificados con los datos del cliente")
	public void posteriormenteDescargandoLaCertificacionEnElModuloGestionCertificadosConLosDatosDelCliente() {
		log.info("Ingresa descarga la certificacion");
	}

	@Entonces("se realiza la validacion del PDF descargado finalizando con el proceso")
	public void seRealizaLaValidacionDelPDFDescargadoFinalizandoConElProceso() {
		log.info("Se valida el PDF");
	}

	
}
