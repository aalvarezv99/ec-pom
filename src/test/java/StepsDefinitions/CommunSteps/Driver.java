package StepsDefinitions.CommunSteps;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.HashMap;

import java.util.Properties;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;


import CommonFuntions.BaseTest;
import CommonFuntions.CrearDriver;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;

public class Driver {
	
	public static WebDriver driver;
	static Logger log = Logger.getLogger(Driver.class);
	Scenario scenario ;
	BaseTest baseTest ;
	
	private static String navegador;
	private static String sistemaOperativo;	
	private static String origenFuente;
	
	private static Properties pro = new Properties();
	private static InputStream in = CrearDriver.class.getResourceAsStream("../test.properties");
	
	public Driver() {
		baseTest = new BaseTest(driver);
		
	}
	
	/*
	@Before
	public void before(Scenario scenario) {
		   this.scenario = scenario;
		   System.out.println("como seria prueba de escenario "+scenario);
	}*/
	
	
	
	@Before
	public void iniciarDriver(Scenario scenario) throws MalformedURLException {			
		log.info("***********************************************************************************************************");
		log.info("[ Configuracion ] - Inicializando la configuracion del Driver");
		log.info("***********************************************************************************************************");
	    driver = CrearDriver.inicialConfig();
		levantarURL();
    	log.info("***********************************************************************************************************");
    	
    	System.out.println("Antes prueba de escenario "+scenario);	
    	this.scenario = scenario;		
		System.out.println("prueba de escenario       "+scenario);	
		
    	log.info("[ Scenario Ejecutando ] - "+ scenario.getName().toUpperCase());
		log.info("***********************************************************************************************************");
	}
	
	public void levantarURL() {
		try {			
			String url = baseTest.leerPropiedades("UrlAmbiente");
			log.info("******************** NAVEGANDO EN LA URL " + url + " **********************");
			driver.get(url);			
		} catch (Exception e) {
			log.error("================== ERROR EN LA URL ================" + e);
		}
	}
	
	@After
	public void salirDriver()  {
		//log.info("******************");
		log.info("****SALIR NAVEGADOR******");	
		after(scenario);
		//driver.quit();
		log.info("******************");
		log.info("******************");		
	}
	
	//@After
	public void after(Scenario scenario) {
		  if (scenario.isFailed()) {
		    log.error("## FALLO EL ESCENARIO ##" + scenario.toString().toUpperCase());
		  }
		  log.info("***********************************************************************************************************");
			log.info("[ Scenario Ejecucion Terminada ] - "+ scenario.getStatus());
			log.info("***********************************************************************************************************");
		  
		}
	
	
}
