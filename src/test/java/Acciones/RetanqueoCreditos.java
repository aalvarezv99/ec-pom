package Acciones;

import static org.junit.Assert.assertTrue;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

import Archivo.LeerArchivo;
import CommonFuntions.BaseTest;
import Consultas.CertificacionSaldoQuery;
import Consultas.OriginacionCreditoQuery;
import JSch.JSchSSHConnection;
import Pages.CreditoSolicitudPage;
import Pages.PagesClienteParaBienvenida;
import Pages.PagesClienteParaVisacion;
import Pages.PagesCreditosDesembolso;
import Pages.PagesTareas;
import Pages.PanelNavegacionPage;
import Pages.RetanqueoPages;
import Pages.SimuladorAsesorPages;
import Pages.SolicitudCreditoPage.PestanaDigitalizacionPage;
import Pages.SolicitudCreditoPage.PestanaFormularioPage;
import Pages.SolicitudCreditoPage.PestanaReferenciacionPage;
import Pages.SolicitudCreditoPage.PestanaSimuladorInternoPage;

public class RetanqueoCreditos extends BaseTest {
	
	WebDriver driver;

	PanelPrincipalAccion panelnavegacionaccion;
	PanelNavegacionPage PanelNavegacionPage;
	RetanqueoPages  retanqueopages;
	
	LoginAccion loginaccion;
	LeerArchivo archivo;
	// BaseTest baseTest;
	private static Logger log = Logger.getLogger(OriginacionCreditosAccion.class);

	public RetanqueoCreditos(WebDriver driver) {
		// this.driver = driver;
		super(driver);
		// baseTest = new BaseTest(driver);
		
		panelnavegacionaccion = new PanelPrincipalAccion(driver);
		PanelNavegacionPage = new PanelNavegacionPage(driver);	
		retanqueopages = new RetanqueoPages(driver);
		loginaccion = new LoginAccion(driver);
		
		archivo = new LeerArchivo();
	}

	/************ INICIO ACCIONES RETANQUEO CREDITOS ***************/
	public void iniciarSesion() {
	//	loginaccion.iniciarSesion();
	}

	public void ingresarRetanqueoAsesor() {		
		panelnavegacionaccion.Retanqueo();
	}
 
	public void FiltrarCredito(String Cedula,String Credito) throws InterruptedException {
        BuscarenGrilla(retanqueopages.cedula,Cedula);
        Thread.sleep(5000);
        ElementVisible();
        BuscarenGrilla(retanqueopages.credito,Credito);
        Thread.sleep(5000);
        ElementVisible();
        esperaExplicitaTexto(Credito);
         
	}
	
    public void Retanquear() throws InterruptedException {
    	ElementVisible();
    	hacerClick(retanqueopages.BtnRentaqueo);
    	Thread.sleep(5000);
        ElementVisible();
        assertTextonotificacion(retanqueopages.notificacion,"Se ha generado un retanqueo para el");
    }
    
    public void Credito(String Cedula) throws InterruptedException {
    	 panelnavegacionaccion.navegarCreditoSolicitud();
         BuscarenGrilla(retanqueopages.inputCedula,Cedula);
         ElementVisible();
         esperaExplicitaTexto(Cedula); 
    }
    
    public void seleccionarRetanqueo() {
    	 ClicUltimoElemento(retanqueopages.continuar);
         ElementVisible();
    }
    
    public void borrarArchivos() throws InterruptedException {
    	 clickvariosespera(retanqueopages.borrararchivos);
    }
    
    public void CargarArchivos(String pdf) throws InterruptedException {
    	cargarpdf(retanqueopages.ImputAutorizacion,pdf);
        esperaExplicitaNopresente(retanqueopages.BarraCarga);
        hacerClickVariasNotificaciones();
    	cargarpdf(retanqueopages.ImputCedula,pdf);
    	esperaExplicitaNopresente(retanqueopages.BarraCarga);
        hacerClickVariasNotificaciones();
    	cargarpdf(retanqueopages.ImputDesprendibleNomina,pdf);
    	esperaExplicitaNopresente(retanqueopages.BarraCarga);
        hacerClickVariasNotificaciones();
    	cargarpdf(retanqueopages.ImputOtros,pdf);
    	esperaExplicitaNopresente(retanqueopages.BarraCarga);
        hacerClickVariasNotificaciones();
    	
    }
    
    public void ConsultaCentrales(){
    	/*ElementVisible();
    	hacerClick(retanqueopages.BtnConsultaCentrales);
    	ElementVisible();*/
    	//assertTextonotificacion(retanqueopages.notificacion,"Se ha solicitado la consulta en listas");
    }
    
    public void Seguridad() {
    /*	recorerpestanas("SEGURIDAD");
    	hacerClick(retanqueopages.Viable);
    	hacerClick(retanqueopages.Guardar);
    	ElementVisible();
    	esperaExplicita(retanqueopages.Concepto);*/
    	recorerpestanas("SIMULADOR");
    }
    
    public void Simulador(String Tasa, String Plazo, String Monto,String DiasHabilesIntereses ,String Ingresos ,String descLey,String descNomina) {
    	LimpiarConTeclado(retanqueopages.inputTasa);
		EscribirElemento(retanqueopages.inputTasa, Tasa);
		ElementVisible();
		hacerClick(retanqueopages.inputPlazo);
		ElementVisible();
		LimpiarConTeclado(retanqueopages.inputPlazo);
		EscribirElemento(retanqueopages.inputPlazo, Plazo);
		ElementVisible();
		hacerClick(retanqueopages.inputMonto);
		ElementVisible();
		LimpiarConTeclado(retanqueopages.inputMonto);
		EscribirElemento(retanqueopages.inputMonto, Monto);
		ElementVisible();
		hacerClick(retanqueopages.diasIntInicial);
		ElementVisible();
		LimpiarConTeclado(retanqueopages.diasIntInicial);
		EscribirElemento(retanqueopages.diasIntInicial, DiasHabilesIntereses);
		ElementVisible();
		hacerClick(retanqueopages.inputIngresos);
		ElementVisible();
		LimpiarConTeclado(retanqueopages.inputIngresos);
		EscribirElemento(retanqueopages.inputIngresos, Ingresos);
		ElementVisible();
		hacerClick(retanqueopages.inputDescLey);
		ElementVisible();
		LimpiarConTeclado(retanqueopages.inputDescLey);
		EscribirElemento(retanqueopages.inputDescLey, descLey);
		ElementVisible();
		hacerClick(retanqueopages.inputdDescNomina);
		ElementVisible();
		LimpiarConTeclado(retanqueopages.inputdDescNomina);
		EscribirElemento(retanqueopages.inputdDescNomina, descNomina);
		ElementVisible();
		hacerClick(retanqueopages.vlrCompra);
		ElementVisible();
		LimpiarConTeclado(retanqueopages.vlrCompra);
		EscribirElemento(retanqueopages.vlrCompra,descNomina);
		
    }
    /************ FIN RETANQUEO CREDITOS  **********/
}
