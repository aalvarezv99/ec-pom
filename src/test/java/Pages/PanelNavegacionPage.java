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
	
	
	
	//Select Secundarios
	public By selectCertificacionSaldos;
	public By selectGestionCertificado;
	public By selectSucursalActiva;
	public By selectPagosRecaudos;	
	public By selectIrSimulador;
	public By selecPrepagoConfig;
	public By selectSolicitudCredito;
		
	
	public PanelNavegacionPage(WebDriver driver) {
		this.driver = driver;		
		//Componentes Pagaduria
		selectPagaduria = By.xpath("//*[@id=\"formMenu:menu\"]/div[2]/h3");
		selectSucursalActiva = By.xpath("//*[@id=\"formMenu:menu_1\"]/ul/li[3]/a");	
		
		//Componentes Configuracion
		selectConfigBlobal = By.xpath("//a[text()='Configuración global']");
		selecPrepagoConfig = By.xpath("//span[text()='Prepago']");
		
		//Componentes Prepago
		selectPrepago = By.xpath("//a[text()='Prepago']");
		selectCertificacionSaldos = By.xpath("//span[text()='Certificacion de saldos']");
		selectGestionCertificado = By.xpath("//span[text()='Gestión de certificacion saldos']");
		
		//Componentes Recaudo
		selectRecaudo = By.xpath("//a[text()='Recaudo']");
		selectPagosRecaudos = By.xpath("//span[text()='Pagos de recaudo']");
		
		//Componentes Simulador
		selectIrSimulador = By.xpath("//*[@id=\"formMenu:menu_2\"]/ul/li/a");
		selectSimulador = By.xpath("//*[@id=\"formMenu:menu\"]/div[3]/h3");
		
		//Componentes Creditos 
		selectCreditos = By.xpath("//*[@id=\"formMenu:menu\"]/div[9]/h3");
		selectSolicitudCredito = By.xpath("//*[@id=\"formMenu:menu_8\"]/ul/li[1]/a");
		
		botonSalir = By.id("formMenu:usuario:salir");				
		
	}							
	
	
	

	

}
