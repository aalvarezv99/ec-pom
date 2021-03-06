package StepsDefinitions.CreditoSteps;

import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import Acciones.CreditoAccion.RetanqueoCreditoSaneamientoAccion;
import Acciones.CreditoAccion.RetanqueoCreditos;
import CommonFuntions.BaseTest;
import StepsDefinitions.CommunSteps.Driver;
import cucumber.api.java.es.Entonces;
import cucumber.api.java.es.Y;

public class RetanqueoCreditoSaneamientoSteps {

	WebDriver driver;
	Logger log = Logger.getLogger(RetanqueoCreditoSaneamientoSteps.class);
	RetanqueoCreditoSaneamientoAccion retanqueocreditosaneamientoaccion;
	RetanqueoCreditos retanqueocreditos;	
	BaseTest baseTest;
	
	public RetanqueoCreditoSaneamientoSteps() throws InterruptedException {		
		driver = Driver.driver;		
		retanqueocreditosaneamientoaccion = new RetanqueoCreditoSaneamientoAccion(driver);
		retanqueocreditos = new RetanqueoCreditos(driver);	
		baseTest = new BaseTest(driver);
	}
	//####################### SeContinuaSolicitudCreditoParaCompraDeCartera #####################################################################
	
	@Y("se pasa a la segunda pestana de digitalizacion se compra cartera y saneamiento {string}{string}{string}{string}{string}")
	public void SePasaAlaSegundaPestanaDeDigitalizacionSeCompraCarteraSaneamiento(String Competidor, String Saneamiento, String VlrCuota, String FechaVencimiento, String NumObligacion) throws InterruptedException {
		retanqueocreditosaneamientoaccion.DatosCarteraSaneamiento(Competidor, Saneamiento, VlrCuota, FechaVencimiento, NumObligacion);
	}
	
	@Y("se pasa a la primera pestana de referenciacion para confirmar la entidad {string}{string}{string}{string}{string}")
	public void SePasaALaPrimeraPestanaDeReferenciacionParaConfirmarLaEntidad(String Competidor, String Saneamiento, String VlrCuota, String FechaVencimiento, String NumObligacion) throws InterruptedException {
		retanqueocreditosaneamientoaccion.ConfirmarEntidad(Competidor, Saneamiento, VlrCuota, FechaVencimiento, NumObligacion);
	}
	@Y("se pasa a la segunda pestana de digitalizacion se agrega el codigo proforences del retanqueo saneamiento {string}")
	public void SePasaALaSegundaPestanaDeDigitalizacionParaAgregarElCodigoProforencesApruebaReferencias(String Codigo) throws InterruptedException {
		retanqueocreditosaneamientoaccion.ReferenciasPositivasRetanqueoSaneamiento(Codigo);
	}
	
	 //####################### AnalisisCredito #####################################################################
	
	@Entonces("Valida los valores del simulador retanqueos con saneamiento {string}{string}{string}{string}{string}{string}{string}{string}{string}{string}{string}")
	    public void validalosvaloresdelsimuladorretanqueosconcompradecarteraysaneamiento(String Anno,String Retanqueo,String fecha,String Mes,String Plazo,String Ingresos,String descLey,String descNomina,String Saneamiento,String Credito,String DiasHabilesIntereses) throws InterruptedException, SQLException {
		   retanqueocreditos.ValidarSimuladorAnalistaRetanqueos(Anno,Retanqueo,fecha,Mes,Plazo,Ingresos,descLey,descNomina,Saneamiento,Credito,DiasHabilesIntereses);
	}
	//####################### IniciaClientesBienvenidaCreditoParaCompraDeCartera ################################################################
	@Entonces("se pasa a la pestana condiciones de credito retanqueo se marcan los check condiciones y de saneamiento y se acepta {string}")
	public void SePasaALaPestanaCondicionesDeCreditoSeMarcanLosCheckYSeAcepta (String TipoDesen) throws InterruptedException {
		retanqueocreditosaneamientoaccion.AceptarcondiconesdelcreditoRetanqueoSaneamiento(TipoDesen);
	}	
	//####################### FinalizaLlamadaDeBienvenidaParaCompraDeCartera #####################################################################

}
