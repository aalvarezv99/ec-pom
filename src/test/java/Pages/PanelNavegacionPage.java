package Pages;

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
	
	
	
	//Select Secundarios
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
	
	
	public PanelNavegacionPage(WebDriver driver) {
		this.driver = driver;		
		//Componentes Pagaduria
		selectPagaduria = By.xpath("//*[@id=\"formMenu:menu\"]/div[2]/h3");//
		selectSucursalActiva = By.xpath("//*[@id=\"formMenu:menu_1\"]/ul/li[3]/a");	//
		
		//Componentes Configuracion
		selectConfigBlobal = By.xpath("//*[@id=\"formMenu:menu\"]/div[4]/h3");
		selecPrepagoConfig = By.xpath("//*[@id=\"formMenu:menu_3\"]/ul/li[6]/a");
		
		//Componentes Prepago
		selectPrepago = By.xpath("//*[@id=\"formMenu:menu\"]/div[17]/h3");
		selectCertificacionSaldos = By.xpath("//*[@id=\"formMenu:menu_16\"]/ul/li[1]/a");
		selectGestionCertificado = By.xpath("//*[@id=\"formMenu:menu_16\"]/ul/li[2]/a");
		
		//Componentes Recaudo
		selectRecaudo = By.xpath("//*[@id=\"formMenu:menu\"]/div[15]/h3");
		selectPagosRecaudos = By.xpath("//*[@id=\"formMenu:menu_14\"]/ul/li/a");
		
		//Componentes Simulador
		selectSimulador = By.xpath("//a[text()='Simulador']");
		selectIrSimulador = By.xpath("//span[text()='Ir']");
		
		//Componentes Creditos 
		selectCreditos = By.xpath("//a[text()='Creditos']");
		selectSolicitudCredito = By.xpath("//span[text()='Solicitud de crédito']");
		
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
