package StepsDefinitions;

import Acciones.OriginacionCreditoSaneamientoAccion;
import Acciones.OriginacionCreditosAccion;
import CommonFuntions.BaseTest;
import cucumber.api.java.es.Cuando;
import cucumber.api.java.es.Entonces;
import cucumber.api.java.es.Y;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import java.sql.SQLException;

/**
 * @author Adri&aacute;n Francisco Becerra Arias - abecerra@excelcredit.co
 * @version 1.0
 */
public class OriginacionCreditoSaneamientoSteps {

    WebDriver driver;
    Logger log = Logger.getLogger(SolicitudCreditoSteps.class);
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

	//@Y("se pasa a la primera pestana de referenciacion para confirmar la entidad {string}")
	//public void sepasaalaprimerapestanadereferenciacionparaconfirmarlaentidad(String entidad_san) throws InterruptedException {
	//    originacionCreditoSaneamientoAccion.ConfirmarEntidadSaneamiento(entidad_san);
	//}
	
	/*@Y("se confirma el numero de obligacion {string}")
	public void seconfirmaelnumerodeobligacion(String num_obligacion_san) throws InterruptedException {
	    originacionCreditoSaneamientoAccion.ConfirmarObligacionSaneamiento(num_obligacion_san);
	}*/

	@Y("se aprueba el saneamiento")
	public void seapruebaelsaneamiento() {
	    originacionCreditoSaneamientoAccion.AprobarSaneamiento();
	}

	/*@Y("se guarda en la primera pestana de referenciacion")
	public void seguardaenlaprimerapestanadereferenciacion() {
	    originacionCreditoSaneamientoAccion.Guardar();
	}*/

	/*@Y("se pasa a la segunda pestana de digitalizacion para agregar el codigo proforences aprueba referencias{string}")
	public void sepasaalasegundapestanadedigitalizacionparaagregarelcodigoproforencesapruebareferencias(String codigo) throws InterruptedException {
	     originacionCreditoSaneamientoAccion.Referenciaspositivas(codigo);
	}*/
	
	@Y("se pasa a la segunda pestana de digitalizacion y seleciona el saneamiento")
	public void sepasaalasegundapestanadedigitalizacionyselecionaelsaneamiento() throws InterruptedException {
	    originacionCreditoSaneamientoAccion.MarcarSaneamiento();
	}
	
	/*@Y("se marca identidida confirmada para radicar la solicitud")
	public void semarcaidentididaconfirmadapararadicarlasolicitud() throws InterruptedException {
	     originacionCreditoSaneamientoAccion.Radicar();
	}/*
	
	/*@Entonces("se realiza la solicitud del analisis")
	public void serealizalasolicituddelanalisis() throws InterruptedException {
		 originacionCreditoSaneamientoAccion.ReferenciacionSolicitarAnalisis();
	}*/
	
//####################### FinalizaSolicitudCreditoParaSaneamiento #####################################################################

	
//####################### IniciaAnalisisCreditoParaSaneamiento #####################################################################

	   @Y("Valida los valores del simulador para compra saneamiento {string}{string}{string}{string}{string}{string}{string}{string}{string}")
	   public void Validalosvaloresdelsimuladorparacomprasaneamiento(String Mes, String Monto,String Tasa,String Plazo, String Ingresos, String descLey, String descNomina, String Pagaduria, String saneamiento) throws NumberFormatException, SQLException {
		   originacionCreditoSaneamientoAccion.ValidarSimuladorAnalistaSaneamiento(Mes,Monto,Tasa,Plazo,Ingresos,descLey,descNomina,Pagaduria,saneamiento);
	   }

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
	   
	 /*  @Y ("se descarga medios de dispersion para el remanente {string}{string}{string}{string}") 
	   public void sedescargamediosdedispersionparaelremanente (String Monto, String saneamiento, String Banco, String Pdf) throws InterruptedException {
		   originacionCreditoSaneamientoAccion.DescargarMediosDispercionRemanente (Monto,saneamiento,Banco,Pdf);
	  }*/
	   
//####################### FinalizaDesembolsoRemanente #####################################################################
}
