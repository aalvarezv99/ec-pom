package Acciones.ConfigGlobalAccion;

import static org.junit.Assert.assertTrue;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import Acciones.ComunesAccion.PanelPrincipalAccion;
import CommonFuntions.BaseTest;
import Pages.ConfigGlobalPage.ConfigGlobalPrepagoPage;


public class ConfigPrepagoAccion extends BaseTest {
private WebDriver driver;
	
	ConfigGlobalPrepagoPage configGlobalPrepagoPage = new ConfigGlobalPrepagoPage(driver);
	PanelPrincipalAccion panelprincipalAccion; 
	Logger log = Logger.getLogger(ConfigPrepagoAccion.class);
	
	public ConfigPrepagoAccion(WebDriver driver) {
		super(driver);
		panelprincipalAccion = new PanelPrincipalAccion(driver);
	}
	
	public void ingresarConfigGlobalPrepago() {
		panelprincipalAccion.navegarConfigPrepago();
	}
	
	/*
	 * CONFIGURACION GLOBAL - MODULO PREPAGO
	 * pestana prepago
	 * Se realiza la configuracion para la certificacion de saldo, dia certificacion, vencimiento y valor
	 * */
	public void configurarPrepagoGlobal(String diaCert, String venCert, String vlrCert) {
		log.info("*********ConfigPrepagoAccion - configurarPrepagoGlobal()************");
		try {
			escribirListaInput(configGlobalPrepagoPage.listParametrosCertificacion,1,diaCert);
			escribirListaInput(configGlobalPrepagoPage.listParametrosCertificacion,2,venCert);
			escribirListaInput(configGlobalPrepagoPage.listParametrosCertificacion,3,diaCert);
			escribirListaInput(configGlobalPrepagoPage.listParametrosCertificacion,4,venCert);
			escribirListaInput(configGlobalPrepagoPage.listParametrosCertificacion,5,vlrCert); 
			adjuntarCaptura("ConfiguracionPrepago");
			hacerClick(configGlobalPrepagoPage.btnGuardar);			
			ElementVisible();
			assertTextonotificacion(configGlobalPrepagoPage.notificacion, "Parametros guardados correctamente");
		} catch (Exception e) {
			log.error("######### ConfigPrepagoAccion - configurarPrepagoGlobal() ########" + e);
			assertTrue("######### ConfigPrepagoAccion - configurarPrepagoGlobal() ########"+ e,false);
		}
		
	}
	
	/*
	 * CONFIGURACION GLOBAL - MODULO PREPAGO
	 * pestana prepago
	 * Se realiza la extraccion del valor de certificacion mostrado en pantalla
	 * */
	public String extraerValorCertificacion() {	
		log.info("*********** ConfigPrepagoAccion - extraerValorCertificacion() ************ ");
		String result = null; 
		try {
			 ingresarConfigGlobalPrepago();
			 result = TextoElemento(configGlobalPrepagoPage.vlrCertificacion);
			 adjuntarCaptura("extraerValorCertificacion");
		} catch (Exception e) {
			log.error("######### ConfigPrepagoAccion - extraerValorCertificacion() ########" + e);
			assertTrue("######### ConfigPrepagoAccion - extraerValorCertificacion() ########"+ e,false);
		}
		return result;
		
	}
}
