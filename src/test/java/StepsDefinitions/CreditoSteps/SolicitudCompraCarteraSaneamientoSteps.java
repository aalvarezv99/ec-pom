package StepsDefinitions.CreditoSteps;

import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import Acciones.CreditoAccion.SolicitudCompraCarteraSaneamientoAccion;
import CommonFuntions.BaseTest;
import StepsDefinitions.CommunSteps.Driver;
import cucumber.api.java.es.Cuando;
import cucumber.api.java.es.Entonces;
import cucumber.api.java.es.Y;
import io.cucumber.datatable.DataTable;

public class SolicitudCompraCarteraSaneamientoSteps {

	WebDriver driver;
	Logger log = Logger.getLogger(SolicitudCompraCarteraSaneamientoSteps.class);
	SolicitudCompraCarteraSaneamientoAccion solicitudcompracarterasaneamientoaccion;
	BaseTest baseTest;
	
	public SolicitudCompraCarteraSaneamientoSteps() throws InterruptedException {		
		driver = Driver.driver;		
		solicitudcompracarterasaneamientoaccion = new SolicitudCompraCarteraSaneamientoAccion(driver);
		baseTest = new BaseTest(driver);
	}
	//####################### SeContinuaSolicitudCreditoParaCompraDeCartera #####################################################################	
	
	@Y("se guarda cartera")
	public void SeGuardaCartera() throws InterruptedException {
		log.info("@STEP - se guarda cartera - @STEP");
		solicitudcompracarterasaneamientoaccion.GuardarDatosSaneamiento();
	}
	
	@Y("se pasa a la primera pestana de referenciacion para confirmar la entidad")
	public void SePasaALaPrimeraPestanaDeReferenciacionParaConfirmarLaEntidad(DataTable table) throws InterruptedException {
		log.info("@STEP - se pasa a la primera pestana de referenciacion para confirmar la entidad - @STEP");
		solicitudcompracarterasaneamientoaccion.ConfirmarEntidad(table);
	}
		
	@Y("se pasa a la segunda pestana de digitalizacion se agrega el codigo proforences {string}")
	public void SePasaALaSegundaPestanaDeDigitalizacionParaAgregarElCodigoProforencesApruebaReferencias(String Codigo) throws InterruptedException {
		log.info("@STEP - se pasa a la segunda pestana de digitalizacion se agrega el codigo proforences - @STEP");
		solicitudcompracarterasaneamientoaccion.Referenciaspositivas(Codigo);
	}
	
	//####################### FinalizaSolicitudCreditoParaCompraDeCartera ########################################################################

	//####################### IniciaAnalisisCreditoParaCompraDeCartera ###########################################################################
	
	@Y("Valida los valores del simulador para compra de cartera con saneamiento {string}{string}{string}{string}{string}{string}{string}{string}{string}{string}{string}{string}")
	   public void ValidaLosValoresDelSimuladorParaCompraDeCarteraConSaneamiento (String Mes, String Monto,String Tasa,String Plazo, String Ingresos, String descLey, String descNomina, String Pagaduria, String Cartera1, String Saneamiento2,String anoAnalisis, String fechaDesembolso) throws NumberFormatException, SQLException {
		log.info("@STEP - Valida los valores del simulador para compra de cartera con saneamiento - @STEP");
		solicitudcompracarterasaneamientoaccion.ValidarSimuladorAnalistaCompraCartera(Mes,Monto,Tasa,Plazo,Ingresos,descLey,descNomina,Pagaduria,Cartera1,Saneamiento2,anoAnalisis, fechaDesembolso);
	}
	
	@Y("valide la informacion cabecera con sus conceptos para OriginacionCCS{string}{string}")
	public void validelainformacioncabeceraconsusconceptosparaOriginacionCCS (String Tasa, String Plazo) throws InterruptedException {
		log.info("@STEP - valide la informacion cabecera con sus conceptos para OriginacionCCS - @STEP");
		solicitudcompracarterasaneamientoaccion.validelainformacioncabeceraconsusconceptosparaOriginacionCCS(Tasa,Plazo);
	}
	//####################### FinalizaAnalisisCreditoParaCompraDeCartera ########################################################################

	//####################### IniciaClientesBienvenidaCreditoParaCompraDeCartera ################################################################
//	@Entonces("se pasa a la pestana condiciones de credito se marcan los check condiciones y de carteras y se acepta{string}")
//	public void SePasaALaPestanaCondicionesDeCreditoSeMarcanLosCheckYSeAcepta (String TipoDesen) throws InterruptedException {
//		solicitudcompracarterasaneamientoaccion.Aceptarcondiconesdelcredito(TipoDesen);
//	}	
	//####################### FinalizaLlamadaDeBienvenidaParaCompraDeCartera #####################################################################

	//####################### IniciaDesembolsoCartera ##################################################################### 
	
	@Cuando("el agente ingresa a la lista de pagos para procesar {string} con {string}")
	public void ElAgenteIngresaALaListaDePagosParaProcesarLaPrimerCartera(String tipo, String Cedula)
			throws InterruptedException {
		log.info("@STEP - el agente ingresa a la lista de pagos para procesar - @STEP");
		solicitudcompracarterasaneamientoaccion.ProcesarCartera(tipo, Cedula);
	}
	   

	//####################### IniciaDesembolsoRemanente #####################################################################  
	
	@Y("se descarga medios de dispersion para el remanente")
	public void sedescargamediosdedispersionparaelremanente(DataTable tabla) throws InterruptedException {
		log.info("@STEP - se descarga medios de dispersion para el remanente - @STEP");
		solicitudcompracarterasaneamientoaccion.DescargarMediosDispercionRemanente(tabla);
	}
	//####################### FinalizaDesembolsoRemanente #####################################################################
}