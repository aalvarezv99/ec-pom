package StepsDefinitions;

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

import CommonFuntions.BaseTest;
import CommonFuntions.CrearDriver;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;

public class Driver {

	public static WebDriver driver;
	Logger log = Logger.getLogger(Driver.class);
	Scenario scenario = null;
	BaseTest baseTest;
	
	private static String navegador;
	private static String sistemaOperativo;	
	private static String origenFuente;
	
	private static Properties pro = new Properties();
	private static InputStream in = CrearDriver.class.getResourceAsStream("../test.properties");

	public Driver() {
		baseTest = new BaseTest(driver);
	}

	@Before
	public void before(Scenario scenario) {
		this.scenario = scenario;
	}

	@Before
	public void iniciarDriver() throws MalformedURLException {
		log.info(
				"***********************************************************************************************************");
		log.info("[ Configuracion ] - Inicializando la configuracion del Driver");
		log.info(
				"***********************************************************************************************************");
		driver = inicialConfig();
		levantarURL();
		log.info(
				"***********************************************************************************************************");
		log.info("[ Scenario Ejecuadando ] - " + scenario.getName().toUpperCase());
		log.info(
				"***********************************************************************************************************");
	}

	public void levantarURL() {
		try {
			String url = baseTest.leerPropiedades("UrlAmbiente");
			log.info("===============================");
			log.info("[ Ambiente ] - NAVEGANDO EN LA URL " + url);
			log.info("===============================");
			driver.get(url);
		} catch (Exception e) {
			log.error("================== ERROR EN LA URL ================" + e);
		}
	}

	@After
	public void salirDriver() {
		log.info("******************");
		log.info("****SALIR NAVEGADOR******");
		after(scenario);
		// driver.quit();
		log.info("******************");
		log.info("******************");
	}

	// @After
	public void after(Scenario scenario) {
		if (scenario.isFailed()) {
			log.error("## FALLO EL ESCENARIO ##" + scenario.toString().toUpperCase());
		}
		log.info(
				"***********************************************************************************************************");
		log.info("[ Scenario Ejecucion Terminada ] - " + scenario.getStatus());
		log.info(
				"***********************************************************************************************************");

	}
	
	/*Esta confiuracion toma el Siste operativo y el navegador del archivo .propertiesy crea el driver*/
	public  WebDriver inicialConfig() {
		WebDriver driver;
		
		try {
			
			pro.load(in);
			navegador = pro.getProperty("navegador");
			sistemaOperativo = pro.getProperty("SO");			
			log.info("Levanta el navegador: " + navegador + "\n  en el Sistema operativo: " + sistemaOperativo);
			
		} catch (Exception e) {
			log.error("Error en la configuracion inicial", e);
		}
		
		driver = crearNuevoWebDriver(navegador, sistemaOperativo);
		//BaseTest baseTest = new BaseTest(driver);
		return driver;
		
	}
	
	/*Esta lee el sistema operativo y el navegador basado en esto ejecuta el driver correspondiente*/
	public  WebDriver crearNuevoWebDriver(String navegador, String so) {
		WebDriver driver = null;
		try {
			pro.load(in);
			origenFuente = pro.getProperty("OrigenDriver");

			switch (navegador) {
			case "Chrome":
				
				switch (so) {
				case "Windows":
					System.setProperty("webdriver.chrome.driver", origenFuente + so +"/" + navegador +"/chromedriver.exe");
					break;
				case "Linux":
					System.setProperty("webdriver.chrome.driver", origenFuente + so + "/linux");
					break;
				case "Mac":
					System.setProperty("webdriver.chrome.driver", origenFuente + so + "/linux");
					break;	
				}
				DesiredCapabilities ruta = setDownloadsPath();
				driver = new ChromeDriver(ruta);
				break;

			case "Firefox":
				switch (so) {
				case "Windows":
					System.setProperty("webdriver.gecko.driver", origenFuente + so +"/" + navegador +"/geckodriver.exe");
					break;
				case "Linux":
					System.setProperty("webdriver.gecko.driver", origenFuente + so + "/linux");
					break;
				case "Mac":
					System.setProperty("webdriver.gecko.driver", origenFuente + so + "/linux");
					break;	
				}
				driver = new FirefoxDriver();
				break;
				
			case "InternetExplored":
				switch (so) {
				case "Windows":
					System.setProperty("webdriver.InternetExplorerDriver.driver",origenFuente + so + "/InternetExplored");
					break;
				case "Linux":
					System.setProperty("webdriver.InternetExplorerDriver.driver", origenFuente + so + "/linux");
					break;
				case "Mac":
					System.setProperty("webdriver.InternetExplorerDriver.driver", origenFuente + so + "/linux");
					break;	
				}
				driver = new InternetExplorerDriver();
				break;
				
			}
			
			driver.manage().window().maximize();
		} catch (Exception e) {
			
			log.error("Error en el crear driver, validar el driver y las rutas", e);
		}

		return driver;
	}
	
	public  DesiredCapabilities setDownloadsPath() {
		DesiredCapabilities caps = null;
		try {
			pro.load(in);
			String RutaDescargas = pro.getProperty("RutaArchivosDescargados");			
			HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
			chromePrefs.put("download.default_directory", RutaDescargas);
			ChromeOptions options = new ChromeOptions();
			options.setExperimentalOption("prefs", chromePrefs);
			caps = new DesiredCapabilities();
			caps.setCapability(ChromeOptions.CAPABILITY, options);
		} catch (Exception e) {
			log.error("####### ERROR - DesiredCapabilities setDownloadsPath()  ########" + e);
		}
		
		return caps;
	}
}
