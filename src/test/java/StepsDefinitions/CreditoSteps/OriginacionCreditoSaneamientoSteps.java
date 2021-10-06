package StepsDefinitions.CreditoSteps;

import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import Acciones.CreditoAccion.OriginacionCreditoSaneamientoAccion;
import CommonFuntions.BaseTest;
import StepsDefinitions.CommunSteps.Driver;
import cucumber.api.java.es.Cuando;
import cucumber.api.java.es.Entonces;
import cucumber.api.java.es.Y;

/**
 * @author Adri&aacute;n Francisco Becerra Arias - abecerra@excelcredit.co
 * @version 1.0
 */
public class OriginacionCreditoSaneamientoSteps {

    WebDriver driver;
    Logger log = Logger.getLogger(OriginacionCreditoSaneamientoSteps.class);
    OriginacionCreditoSaneamientoAccion originacionCreditoSaneamientoAccion;
    BaseTest baseTest;

    public OriginacionCreditoSaneamientoSteps() throws InterruptedException {
        driver = Driver.driver;
        originacionCreditoSaneamientoAccion = new OriginacionCreditoSaneamientoAccion(driver);
        baseTest = new BaseTest(driver);
    }

    @Y("se pasa a la segunda pestana de digitalizacion se agrega el saneamiento {string}{string}{string}{string}{string}") 
	public void sepasaalasegundapestanadedigitalizacionseagregaelsaneamiento(String entidad_san, String saneamiento, String vlr_cuota_san, String fecha_vencimiento, String num_obligacion_san) throws InterruptedException {
    	originacionCreditoSaneamientoAccion.DatosSaneamiento(entidad_san, saneamiento, vlr_cuota_san, fecha_vencimiento, num_obligacion_san); 
	}
    
    @Y("se selecciona el tipo de cartera saneamiento") 
	public void seseleccionaeltipodecarterasaneamiento() throws InterruptedException {
    	originacionCreditoSaneamientoAccion.SeleccionSaneamiento(); 
	}

	@Y("guarda el saneamiento agregado")
	public void guardaelsaneamientoagregado()  {
	    originacionCreditoSaneamientoAccion.GuardarSaneamiento();
	}
	
	@Y("se pasa a la primera pestana de referenciacion para confirmar la entidad saneamiento {string}")
	public void se_pasa_a_la_primera_pestana_de_referenciacion_para_confirmar_la_entidad_entidad_saneamiento(String entidad_san) throws InterruptedException {
		originacionCreditoSaneamientoAccion.ConfirmarEntidadSaneamiento(entidad_san);
	}

	@Y("se aprueba el saneamiento")
	public void seapruebaelsaneamiento() {
	    originacionCreditoSaneamientoAccion.AprobarSaneamiento();
	}
	
	@Y("se pasa a la segunda pestana de digitalizacion y seleciona el saneamiento")
	public void sepasaalasegundapestanadedigitalizacionyselecionaelsaneamiento() throws InterruptedException {
	    originacionCreditoSaneamientoAccion.MarcarSaneamiento();
	}
		
//####################### FinalizaSolicitudCreditoParaSaneamiento #####################################################################

	
//####################### IniciaAnalisisCreditoParaSaneamiento #####################################################################

	 /*  @Y("Valida los valores del simulador para compra saneamiento {string}{string}{string}{string}{string}{string}{string}{string}{string}")
	   public void Validalosvaloresdelsimuladorparacomprasaneamiento(String Mes, String Monto,String Tasa,String Plazo, String Ingresos, String descLey, String descNomina, String Pagaduria, String saneamiento) throws NumberFormatException, SQLException {
		   originacionCreditoSaneamientoAccion.ValidarSimuladorAnalistaSaneamiento(Mes,Monto,Tasa,Plazo,Ingresos,descLey,descNomina,Pagaduria,saneamiento);
	   }*/

//####################### FinalizaAnalisisCreditoParaSaneamiento #####################################################################

//####################### IniciaLlamadaDeBienvenidaParaSaneamiento #####################################################################

	   @Y("se marcar los check corretos junto con el celular correo para saneamiento {string}{string}")
	   public void semarcarloscheckcorretosjuntoconelcelularcorreoparasaneamiento (String Celular, String Correo ) throws InterruptedException {
		   originacionCreditoSaneamientoAccion.CorrectocondicionesSaneamiento(Celular, Correo);
	   }
	   
	   @Entonces("se pasa a la pestana condiciones de credito se marcan los check acepta saneamiento y se acepta condiciones{string}")
	   public void sepasaalapestanacondicionesdecreditosemarcanloscheckaceptasaneamientoyseaceptacondiciones (String TipoDesen) throws InterruptedException {
		   originacionCreditoSaneamientoAccion.AceptaCondiconesCreditoSaneamiento(TipoDesen);
	   }
	   
//####################### FinalizaLlamadaDeBienvenidaParaCompraDeCartera #####################################################################

//####################### IniciaDesembolsoSaneamiento #####################################################################  
	   
	   @Cuando ("el agente ingresa a la lista de pagos para procesar el saneamiento {string}")
	   public void elagenteingresaalalistadepagosparaprocesarelsaneamiento (String Cedula) throws InterruptedException {
		   originacionCreditoSaneamientoAccion.ProcesarSaneamiento(Cedula);
	   }
	   
	   @Y("se descargadescargan medios de dispersion para el saneamiento {string}{string}{string}")
		public void sedescargadescarganmediosdedispersionparaelsaneamiento (String saneamiento, String Banco,String Pdf) throws InterruptedException {
		   originacionCreditoSaneamientoAccion.DescargarMediosDispercionSaneamiento(saneamiento, Banco,Pdf);
		}
	   
//####################### FinalizaDesembolsoSaneamiento #####################################################################
	   
//####################### IniciaDesembolsoRemanente #####################################################################  
	   
	   @Cuando ("el agente ingresa a la lista de pagos para procesar el remanente {string}")
	   public void elagenteingresaalalistadepagosparaprocesarelremanente (String Cedula) throws InterruptedException {
		   originacionCreditoSaneamientoAccion.ProcesarRemanente(Cedula);
	   }
	   
	   
//####################### FinalizaDesembolsoRemanente #####################################################################
}
