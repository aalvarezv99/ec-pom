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
		solicitudcompracarterasaneamientoaccion.GuardarDatosSaneamiento();
	}
	
	@Y("se pasa a la primera pestana de referenciacion para confirmar la entidad")
	public void SePasaALaPrimeraPestanaDeReferenciacionParaConfirmarLaEntidad(DataTable table) throws InterruptedException {
		solicitudcompracarterasaneamientoaccion.ConfirmarEntidad(table);
	}
		
	@Y("se pasa a la segunda pestana de digitalizacion se agrega el codigo proforences {string}")
	public void SePasaALaSegundaPestanaDeDigitalizacionParaAgregarElCodigoProforencesApruebaReferencias(String Codigo) throws InterruptedException {
		solicitudcompracarterasaneamientoaccion.Referenciaspositivas(Codigo);
	}
	
	//####################### FinalizaSolicitudCreditoParaCompraDeCartera ########################################################################

	//####################### IniciaAnalisisCreditoParaCompraDeCartera ###########################################################################
	
	@Y("Valida los valores del simulador para compra de cartera con saneamiento {string}{string}{string}{string}{string}{string}{string}{string}{string}{string}{string}{string}")
	   public void ValidaLosValoresDelSimuladorParaCompraDeCarteraConSaneamiento (String Mes, String Monto,String Tasa,String Plazo, String Ingresos, String descLey, String descNomina, String Pagaduria, String Cartera1, String Saneamiento2,String anoAnalisis, String fechaDesembolso) throws NumberFormatException, SQLException {
		solicitudcompracarterasaneamientoaccion.ValidarSimuladorAnalistaCompraCartera(Mes,Monto,Tasa,Plazo,Ingresos,descLey,descNomina,Pagaduria,Cartera1,Saneamiento2,anoAnalisis, fechaDesembolso);
	}
	//####################### FinalizaAnalisisCreditoParaCompraDeCartera ########################################################################

	//####################### IniciaClientesBienvenidaCreditoParaCompraDeCartera ################################################################
//	@Entonces("se pasa a la pestana condiciones de credito se marcan los check condiciones y de carteras y se acepta{string}")
//	public void SePasaALaPestanaCondicionesDeCreditoSeMarcanLosCheckYSeAcepta (String TipoDesen) throws InterruptedException {
//		solicitudcompracarterasaneamientoaccion.Aceptarcondiconesdelcredito(TipoDesen);
//	}	
	//####################### FinalizaLlamadaDeBienvenidaParaCompraDeCartera #####################################################################

	 //####################### IniciaDesembolsoCartera ##################################################################### 
	
	@Cuando ("el agente ingresa a la lista de pagos para procesar la cartera uno {string}")
	public void ElAgenteIngresaALaListaDePagosParaProcesarLaPrimerCartera (String Cedula) throws InterruptedException {
		solicitudcompracarterasaneamientoaccion.ProcesarCartera(Cedula);
	   }
	   
	//####################### FinalizaDesembolsoCartera #####################################################################     
	 
 
	//####################### IniciaDesembolsoSaneamiento #####################################################################  
	   	   
	   @Y("se descargan medios de dispersion para el saneamiento dos {string}{string}{string}")
		public void sedescargadescarganmediosdedispersionparaelsaneamiento (String Saneamiento2, String Banco,String Pdf) throws InterruptedException {
		   solicitudcompracarterasaneamientoaccion.DescargarMediosDispercionSaneamiento(Saneamiento2, Banco,Pdf);
		}
	   
//####################### FinalizaDesembolsoSaneamiento #####################################################################
	   
//####################### IniciaDesembolsoRemanente #####################################################################  
	
	   @Y ("se descarga medios de dispersion para el remanente {string}{string}{string}{string}{string}") 
	   public void sedescargamediosdedispersionparaelremanente (String Monto, String Cartera1, String Saneamiento2, String Banco, String Pdf) throws InterruptedException {
		   solicitudcompracarterasaneamientoaccion.DescargarMediosDispercionRemanente (Monto,Cartera1, Saneamiento2,Banco,Pdf);
	  }  
//####################### FinalizaDesembolsoRemanente #####################################################################
}