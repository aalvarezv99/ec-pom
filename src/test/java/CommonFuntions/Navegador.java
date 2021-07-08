package CommonFuntions;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import cucumber.api.Scenario;
import cucumber.api.java.Before;

public class Navegador {
	
	public static WebDriver driver;
	
	BaseTest baseTest ;
   
	public Navegador() {
		//baseTest = new BaseTest(driver);
	}
	
	
	public WebDriver SelecionarNavegadorUrl(String browserType, String url ) {
		
		if (browserType.equalsIgnoreCase("Chrome")) {			
			driver = chromeDriverConnection();
		}else if(browserType.equalsIgnoreCase("Firefox")){
			driver = FirefoxDriverConnection();
		}else if(browserType.equalsIgnoreCase("Internet Explorer")) {
			driver = IEDriverConnection();
		}
		driver.manage().window().maximize();
		driver.get(url);		
		
		System.out.println("Navegador: "+browserType);
		return driver;
	}
	
	public static WebDriver chromeDriverConnection() {
		System.setProperty("webdriver.chrome.driver","src/test/resources/Drivers/Windows/Chrome/chromedriver.exe");
		driver= new ChromeDriver();
		return driver;
	}
	
	public static WebDriver FirefoxDriverConnection() {
		System.setProperty("webdriver.gecko.driver","src/test/resources/Drivers/Windows/Firefox/geckodriver.exe");
		driver= new FirefoxDriver();
		return driver;
	}
	
	public static WebDriver IEDriverConnection() {
		System.setProperty("webdriver.ie.driver","src/test/resources/chromedriver/IEDriverServer.exe");
		driver= new InternetExplorerDriver();
		return driver;
	}
	
	
}
