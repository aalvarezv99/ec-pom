package CommonFuntions;


import java.io.InputStream;
import java.util.HashMap;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

public class CrearDriver {
	
	private static String navegador;
	private static String sistemaOperativo;	
	private static String origenFuente;
	
	
	
	private static Properties pro = new Properties();
	private static InputStream in = CrearDriver.class.getResourceAsStream("../test.properties");
	private static Logger log = Logger.getLogger(CrearDriver.class);
	//BaseTest baseTest = new BaseTest(driver);
	
	//Se crea el contructor de la clase donde se oinicia la condiguracion
	private CrearDriver() {
		CrearDriver.inicialConfig();
	}
	
	//Esta confiuracion toma el Siste operativo y el navegador del archivo .propertiesy crea el driver
	public static WebDriver inicialConfig() {
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
	
	//Esta lee el sistema operativo y el navegador basado en esto ejecuta el driver correspondiente
	public static WebDriver crearNuevoWebDriver(String navegador, String so) {
		WebDriver driver = null;
		DesiredCapabilities ruta = null;
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
					System.setProperty("webdriver.chrome.driver", origenFuente + so +"/" + navegador +"/chromedriver");
					break;
				case "Mac":
					System.setProperty("webdriver.chrome.driver", origenFuente + so + "/linux");
					break;	
				}
				ruta = setDownloadsPath();
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
				ruta = setDownloadsPath();
				driver = new FirefoxDriver(ruta);
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
	
	public static  DesiredCapabilities setDownloadsPath() {
		DesiredCapabilities caps = null;
		try {
			pro.load(in);
			String RutaDescargas = pro.getProperty("RutaArchivosDescargados");			
			HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
			chromePrefs.put("download.default_directory", RutaDescargas);
			
			ChromeOptions options = new ChromeOptions();			
			options.setExperimentalOption("prefs", chromePrefs);
			/*options.setHeadless(true);
			options.setBinary("/usr/bin/google-chrome"); 
			options.addArguments("headless");
			
			options.addArguments("--disable-gpu");
			options.addArguments("disable-infobars");
			options.addArguments("--disable-extensions");
			options.addArguments("--disable-dev-shm-usage");
			options.addArguments("start-maximized");
			options.addArguments("--no-sandbox");*/
			caps = new DesiredCapabilities();
			caps.setCapability(ChromeOptions.CAPABILITY, options);
		} catch (Exception e) {
			log.error("####### ERROR - DesiredCapabilities setDownloadsPath()  ########" + e);
		}
		
		return caps;
	}
	
}



