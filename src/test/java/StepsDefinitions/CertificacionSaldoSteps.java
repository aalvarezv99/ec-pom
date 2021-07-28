package StepsDefinitions;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import Acciones.PrepagoAccion.*;
import cucumber.api.java.es.Cuando;
import cucumber.api.java.es.Y;

public class CertificacionSaldoSteps {
	
	WebDriver driver;
	Logger log = Logger.getLogger(CertificacionSaldoSteps.class);
	CertificacionSaldosAccion certificacionAccion;
	
	public CertificacionSaldoSteps() {		
		driver = Driver.driver;		
		certificacionAccion = new CertificacionSaldosAccion(driver);
	}
	
	@Cuando("Navegue a la configuracion global del pregago")
    public void navegueALaConfiguracionGlobalDelPregago() {
        certificacionAccion.ingresarConfigGlobalPrepago();
    }
	
	@Y("^Configure los valores (.+) (.+) (.+) para la certificacion de saldo$")
    public void configureLosValoresParaLaCertificacionDeSaldo(String diacertificacion,String venCert,String vlrCert) throws Throwable {
		certificacionAccion.configurarPregagoCertificacion(diacertificacion,venCert,vlrCert);
    }
	
	
	@Y("ingrese a la opcion de certificacion de saldos")
	public void ingreseALaOpcionDeCertificacionDeSaldos() throws Exception {
		try {
			certificacionAccion.ingresarSolicitudCertificado();	
		} catch (Exception e) {
			log.fatal("#ERROR###"+e);
			throw new Exception();
		}
			
	}

	@Y("este ingrese el numero de radicado {int} con cedula {int} para un cliente con credito {string} se aplicara el filtro")
	public void esteIngreseElNumeroDeRadicadoConCedulaParaUnClienteConCreditoSeAplicaraElFiltro(int numRaducadi, int numCedula, String estado) {	
		try {
			certificacionAccion.buscarCliente(numRaducadi, numCedula, estado);			
		} catch (Exception e) {
			log.error("###########ERROR##########"+e);
		}
		
	}
	

	@Y("seleccionando el boton solicitar")
	public void seleccionandoElBotonSolicitar() throws Throwable{
		certificacionAccion.presionarBotonSolicitar();
	}

	@Y("este ingrese la informacion en la ventana solicitar presionando el boton guardar")
	public void esteIngreseLaInformacionEnLaVentanaSolicitarPresionandoElBotonGuardar() {
		certificacionAccion.presionarBotonGuardar();
	}
	
	
	@Y("posteriormente descargando la certificacion en el modulo gestion certificados con los datos del cliente {int} y {int}")
	public void posteriormenteDescargandoLaCertificacionEnElModuloGestionCertificadosConLosDatosDelCliente(int numRadicado, int numCedula) {
		certificacionAccion.descargarPdfGestionCertificado(numRadicado, numCedula);
	}
	
	@Y("^se realiza la validacion del PDF descargado con el (.+) en la ruta \"([^\"]*)\"$")
    public void seRealizaLaValidacionDelPDFDescargadoConElEnLaRuta(String numradicado, String rutadocumento) throws Throwable {
		try {
			certificacionAccion.validarValoresPDF(numradicado, rutadocumento);	
		} catch (Exception e) {
			log.fatal("#ERROR###"+e);
			throw new Exception("ERROR");
		}
    }
	
}
