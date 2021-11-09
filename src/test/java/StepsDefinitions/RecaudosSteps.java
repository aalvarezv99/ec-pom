package StepsDefinitions;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import Acciones.RecaudosAccion;
import StepsDefinitions.CommunSteps.Driver;
import cucumber.api.java.es.Cuando;
import cucumber.api.java.es.Entonces;
import cucumber.api.java.es.Y;

public class RecaudosSteps {

	WebDriver driver;
	Logger log = Logger.getLogger(RecaudosSteps.class);
	RecaudosAccion recaudoAccion;
	String vlrTotalPagar = "";

	public RecaudosSteps() {
		driver = Driver.driver;
		recaudoAccion = new RecaudosAccion(driver);
	}

	@Cuando("^El agente visualice el documento del certificado \"([^\"]*)\" del credito (.+)$")
	public void elAgenteVisualiceElDocumentoDelCertificadoDelCredito(String rutadocumento, String numradicado) {
		recaudoAccion.abrirCertificacionNavegador(rutadocumento, numradicado);
	}

	@Y("Navegue a la pestana pagos de recaudos")
	public void navegueALaPestanaPagosDeRecaudos() throws InterruptedException {
		recaudoAccion.ingresarVentanaRecaudo();
	}

	@Y("^Realice el recaudo con el valor (.+) y origen \"([^\"]*)\"$")
	public void realiceElRecaudoConElValorYOrigenSomething(String vlrpago, String origen) throws Throwable {
		recaudoAccion.seleccionarPagaduria(vlrpago, origen);
	}

	@Entonces("^permite generar el recaudo con el (.+) para \"([^\"]*)\" con los datos del cliente (.+)$")
	public void permiteGenerarElRecaudoConElParaConLosDatosDelCliente(String vlrRecaudo, String Descripcion,
			String numCedula) {
		recaudoAccion.recaudoCliente(vlrRecaudo, Descripcion, numCedula, null);
	}
	
	@Y("^inserte el (.+) para el origen \"([^\"]*)\"$")
    public void inserteElParaElOrigen(String valorcertificacion, String origen) throws Throwable {
		recaudoAccion.insertCcredDinamica(valorcertificacion, origen);
	}
	
	@Y("^Realice el recaudo del credito (.+) con el valor total a pagar \"([^\"]*)\" para (.+) con los datos del cliente (.+)$")
	public void realiceElRecaudoConElValorTotalAPagarPrepagoConLosDatosDelCliente(String numRadicado, String rutaCert,
			String Descripcion, String numCedula) {
		recaudoAccion.recaudoCliente(recaudoAccion.consultarVlrTotal(numRadicado, rutaCert), Descripcion, numCedula,
				numRadicado);
	}

	@Entonces("^se finaliza verificando el estado del credito (.+) que cambio a \"([^\"]*)\"$")
	public void seFinalizaVerificandoElEstadoDelCreditoQueCambioASomething(String numradicado, String estadoCredito) {
		recaudoAccion.validarEstadoCredito(numradicado, estadoCredito);
	}

	@Entonces("la amortizacion del prepago y lo movimientos contables en bases de datos")
	public void laAmortizacionDelPrepagoYLoMovimientosContablesEnBasesDeDatos() {
	}

	@Cuando("El agente navegue a la pestana pagos hasta la pestana preaplicacion de pagos")
	public void Elagentenaveguealapestanapagoshastalapestanapreaplicaciondepagos() {
		recaudoAccion.IngresaVentanaPagos();
	}

	@Y("Se filtra por {string}{int}{string}")
	public void sefiltrapor(String Pagaduria, int Ano, String Periodo) throws InterruptedException {
		recaudoAccion.filtrosPreAplicacionPagos(Pagaduria,String.valueOf(Ano), Periodo);
	}

	@Y("Se captura el valor del recaudo con la suma de valores recibidos")
	public void secapturaelvalordelrecaudoconlasumadevaloresrecibidos() {
		recaudoAccion.capturarValoresPreaplicacionPagos();
	}

	@Entonces("se pasa a la pestana de recaudo")
	public void sepasaalapestanaderecaudo() {
		recaudoAccion.pestanarecaudo();
	}

	@Y("se agrega el pago de recaudo {string}{int}{string}")
	public void seagregaelpagoderecaudo(String Pagaduria, int ano, String periodo) {
		recaudoAccion.Agregarpago(Pagaduria, String.valueOf(ano), periodo);
	}

	@Y("^finaliza con la validacion del recaudo (.+) realizado con el registrado en el sistema$")
	public void finalizaConLaValidacionDelRecaudoRealizadoConElRegistradoEnElSistema(String pagaduria)
			throws Throwable {
		recaudoAccion.validarRecaudoPagaduriaContraDB(pagaduria);
	}	

}
