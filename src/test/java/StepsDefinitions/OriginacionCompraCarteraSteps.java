package StepsDefinitions;

import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import Acciones.OriginacionCompraCarteraAccion;
import Acciones.OriginacionCreditosAccion;
import CommonFuntions.BaseTest;
import cucumber.api.java.es.Cuando;
import cucumber.api.java.es.Entonces;
import cucumber.api.java.es.Y;

public class OriginacionCompraCarteraSteps {
	
	WebDriver driver;
	Logger log = Logger.getLogger(SolicitudCreditoSteps.class);
	BaseTest baseTest;
	OriginacionCompraCarteraAccion originacioncompracarteraaccion;
	
	public OriginacionCompraCarteraSteps () {
		driver = Driver.driver;		
		baseTest = new BaseTest(driver);
		originacioncompracarteraaccion = new OriginacionCompraCarteraAccion (driver);
			
	} 
	
	@Y("se pasa a la segunda pestana de digitalizacion se agrega la compra de cartera {string}{string}{string}{string}{string}") 
	public void se_pasa_a_la_segunda_pestana_de_digitalizacion_se_agrega_la_compra_de_cartera(String entidad, String cartera, String vlr_cuota, String fecha_vencimiento, String num_obligacion) throws InterruptedException {
		originacioncompracarteraaccion.DatosCartera(entidad, cartera, vlr_cuota, fecha_vencimiento, num_obligacion); 
	}

	@Y("guarda la cartera agregada")
	public void guarda_la_cartera_agregada() throws InterruptedException {
	    originacioncompracarteraaccion.GuardarCartera();
	}

	@Y("se pasa a la primera pestana de referenciacion para confirmar la entidad saneamiento {string}")
	public void se_pasa_a_la_primera_pestana_de_referenciacion_para_confirmar_la_entidad_entidad_saneamiento(String entidad) throws InterruptedException {
	    originacioncompracarteraaccion.ConfirmarEntidad(entidad);
	}

	@Y("se confirma el numero de obligacion {string}")
	public void se_confirma_el_numero_de_obligacion_num_obligacion(String num_obligacion) {
	    originacioncompracarteraaccion.ConfirmarObligacion(num_obligacion);
	}

	@Y("se aprueba la cartera")
	public void se_aprueba_la_cartera() {
	    originacioncompracarteraaccion.AprobarCartera();
	}

	@Y("se guarda en la primera pestana de referenciacion")
	public void se_guarda_en_la_primera_pestana_de_referenciacion() {
	    originacioncompracarteraaccion.Guardar();
	}

	@Y("se pasa a la segunda pestana de digitalizacion para agregar el codigo proforences aprueba referencias{string}")
	public void se_pasa_a_la_segunta_pestana_de_digitalizacion_para_agregar_el_codigo_proforences_aprueba_referencias(String codigo) throws InterruptedException {
	     originacioncompracarteraaccion.Referenciaspositivas(codigo);
	}
	
	@Y("se pasa a la segunda pestana de digitalizacion y seleciona la cartera")
	public void se_pasa_a_la_segunda_pestana_de_digitalizacion_y_seleciona_la_cartera() throws InterruptedException {
	    originacioncompracarteraaccion.MarcarCartera();
	}
	
	@Y("se marca identidida confirmada para radicar la solicitud")
	public void SeMarcaIdentididaConfirmadaParaRadicarLaSolicitud() throws InterruptedException {
	     originacioncompracarteraaccion.Radicar();
	}
	
	@Entonces("se realiza la solicitud del analisis")
	public void serealizalasolicituddelanalisis() throws InterruptedException {
		 originacioncompracarteraaccion.ReferenciacionSolicitarAnalisis();
	}
	
//####################### FinalizaSolicitudCreditoParaCompraDeCartera #####################################################################


//####################### IniciaAnalisisCreditoParaCompraDeCartera #####################################################################

   @Y("Valida los valores del simulador para compra de cartera {string}{string}{string}{string}{string}{string}{string}{string}{string}")
   public void validalosvaloresdelsimuladorparacompradecartera(String Mes, String Monto,String Tasa,String Plazo, String Ingresos, String descLey, String descNomina, String Pagaduria, String cartera) throws NumberFormatException, SQLException {
	   originacioncompracarteraaccion.ValidarSimuladorAnalistaCompraCartera(Mes,Monto,Tasa,Plazo,Ingresos,descLey,descNomina,Pagaduria,cartera);
   }



//####################### FinalizaAnalisisCreditoParaCompraDeCartera #####################################################################

//####################### IniciaLlamadaDeBienvenidaParaCompraDeCartera #####################################################################

   @Entonces("se pasa a la pestana condiciones de credito se marcan los check acepta cartera y se acepta condiciones{string}")
   public void sepasaalapestanacondicionesdecreditosemarcanloscheckaceptacarterayseaceptacondiciones (String TipoDesen) throws InterruptedException {
	  originacioncompracarteraaccion.AceptaCondiconesCreditoCompraCartera(TipoDesen);
   }
   
//####################### FinalizaLlamadaDeBienvenidaParaCompraDeCartera #####################################################################

   
 //####################### IniciaDesembolsoCartera #####################################################################  
   
   @Cuando ("el agente ingresa a la lista de pagos para procesar la cartera {string}{string}")
   public void elagenteingresaalalistadepagosparaprocesarlacartera (String Cedula, String estadopago) throws InterruptedException {
	   originacioncompracarteraaccion.ProcesarCartera(Cedula,estadopago);
   }
   
   @Y("se descargadescargan medios de dispersion para la cartera {string}{string}{string}")
	public void sedescargadescarganmediosdedispersionparalacartera(String cartera, String Banco,String Pdf) throws InterruptedException {
		originacioncompracarteraaccion.DescargarMediosDispercionCartera(cartera, Banco,Pdf);
	}
   
 //####################### FinalizaDesembolsoCartera #####################################################################     
 
 //####################### IniciaVisacionCartera #####################################################################  
   
   @Y("se navega hasta carteras {string}")
	public void senavegahastacarteras(String Pdf) throws InterruptedException {
		originacioncompracarteraaccion.VisacionCartera(Pdf);
	}
 
 //####################### FinalizaVisacionCartera ##################################################################### 
   
   
  @Y ("se descarga medios de dispersion para el remanente {string}{string}{string}{string}") 
   public void sedescargamediosdedispersionparaelremanente (String Monto, String cartera, String Banco, String Pdf) throws InterruptedException {
	  originacioncompracarteraaccion.DescargarMediosDispercionRemanente (Monto,cartera,Banco,Pdf);
  }
}
