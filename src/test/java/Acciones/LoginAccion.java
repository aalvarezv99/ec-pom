package Acciones;


import org.apache.log4j.Logger;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import CommonFuntions.BaseTest;
import CommonFuntions.Navegador;
import Pages.InicioSesionPage;


public class LoginAccion extends BaseTest {
	
	//private WebDriver driver;	
	InicioSesionPage iniciosesionpage = new InicioSesionPage(driver);
	Navegador navegador;
	LoginAccion loginaccion;
	
	private static Logger log = Logger.getLogger(LoginAccion.class);
	
	BaseTest baseTest;
	
	public LoginAccion(WebDriver driver) {
		super(driver);
		navegador=new Navegador();
	}	
	
	public WebDriver iniciarSesion(String Url,String Navegador,String Usario,String Password) {	
		
		try {
			
			driver  = navegador.SelecionarNavegadorUrl(Navegador,Url);
			baseTest = new BaseTest(driver);//**********************************************************
		    esperaExplicita(iniciosesionpage.inputUsuario);				
			EscribirElemento(iniciosesionpage.inputUsuario,Usario);
			EscribirElemento(iniciosesionpage.inputContrasena,Password);		
			hacerClick(iniciosesionpage.botonAutenticar);			
			adjuntarCaptura("InisioSesionExitoso");		
			System.out.println("Analista");
			log.info("*****Adjunta Captura InisioSesionExitoso******");
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}
		
		return driver;
	}
	
	
	
		
	
}
