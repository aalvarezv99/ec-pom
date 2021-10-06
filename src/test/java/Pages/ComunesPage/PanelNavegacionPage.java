package Pages.ComunesPage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class PanelNavegacionPage {
	WebDriver driver;
	
	//Select Principales
	public By selectPrepago;
	public By selectPagaduria;
	public By selectRecaudo;
	public By selectConfigBlobal;
	public By selectSimulador;
	public By selectCreditos;
	public By botonSalir;
	public By selectDesembolso;
	public By selectPagos;
	
	
	
	//Select Secundarios
	// sub menu del modulo de pagos
	public By selectListaPagosCargar;
	public By selectPreaplicacionPago;
	public By selectaplicacionFinal;
	public By selectCierrePagos;
	
	
	//select sunMenu prepago
	public By selectCertificacionSaldos;
	public By selectGestionCertificado;
	
	
	public By selectSucursalActiva;
	public By selectPagosRecaudos;	
	public By selectIrSimulador;
	public By selecPrepagoConfig;
	public By selectSolicitudCredito;
	public By selectAnalisisDeCredito;
	public By selectTareas;	
	public By selectTareasVer;
	public By selectClientesBienvenida;
	public By selectClientesVisacion;
	public By selectListadepagos;
	public By selectLMediosDispersion;
	public By selectRetanqueo;
	public By selectListaCreditoRetanqueo;
	public By selectPreaplicacionPagos;
	public By selectCartaCondiciones;
	
	
	public PanelNavegacionPage(WebDriver driver) {
		this.driver = driver;
		//Componentes Pagaduria		
		selectPagaduria = By.xpath("//*[@id=\"formMenu:menu\"]/div[2]/h3");
		selectSucursalActiva = By.xpath("//*[@id=\"formMenu:menu_1\"]/ul/li[3]/a");	
		
		//Componentes Configuracion
		selectConfigBlobal = By.xpath("//a[text()='Configuración global']");
		selecPrepagoConfig = By.xpath("//span[text()='Prepago']");
		
		//componentesPagos
		selectPagos = By.xpath("//a[text()='Pagos']");
		selectListaPagosCargar = By.xpath("//span[text()='Lista pagos a cargar']");
		selectPreaplicacionPago = By.xpath("//span[text()='Preaplicacion pagos']");
		selectaplicacionFinal = By.xpath("//span[text()='Aplicacion Final']");
		selectCierrePagos = By.xpath("//span[text()='Cierre Pagos']");
		
		//Componentes Prepago
		selectPrepago = By.xpath("//a[text()='Prepago']");
		selectCertificacionSaldos = By.xpath("//span[text()='Certificacion de saldos']");
		selectGestionCertificado = By.xpath("//span[text()='Gestión de certificacion saldos']");
		
		//Componentes Recaudo
		selectRecaudo = By.xpath("//a[text()='Recaudo']");
		selectPagosRecaudos = By.xpath("//span[text()='Pagos de recaudo']");
		
		//Componentes Pagos
		selectPagos = By.xpath("//a[text()='Pagos']");
		selectPreaplicacionPagos = By.xpath("//span[text()='Preaplicacion pagos']");
		
		
		//Componentes Simulador
		selectSimulador = By.xpath("//a[text()='Simulador']");
		selectIrSimulador = By.xpath("//span[text()='Ir']");
		
		//Componentes Creditos 
		selectCreditos = By.xpath("//a[text()='Creditos']");
		selectSolicitudCredito = By.xpath("//span[text()='Solicitud de crédito']");
		selectCartaCondiciones = By.xpath("//span[text()='Carta de notificación de condiciones']");

		//Componentes Analisis
		selectAnalisisDeCredito = By.xpath("//span[text()='Análisis de Crédito']");
		
		//Componente Tareas
	    selectTareas= By.xpath("//a[text()='Tareas']");
		selectTareasVer = By.xpath("//span[text()='Ver']");
		
		//Componente clientes bienvenida
		selectClientesBienvenida = By.xpath("//span[text()='Clientes para bienvenida']");
		
		//Componente clientes visacion
		selectClientesVisacion = By.xpath("//span[text()='Creditos para visación']");
		
		//Componente Desembolso
		
		selectDesembolso = By.xpath("//a[text()='Desembolso']");
		selectListadepagos = By.xpath("//span[text()='Lista de pagos']");
		selectLMediosDispersion = By.xpath("//span[text()='Descarga medios de dispersión']");
		
		//Componente retanqueo
		selectRetanqueo = By.xpath("//a[text()='Retanqueo']");
		selectListaCreditoRetanqueo = By.xpath("//span[text()='Lista créditos a retanquear']");
		
		//logout
		botonSalir = By.id("formMenu:usuario:salir");				
	}							
	
	
	

	

}
