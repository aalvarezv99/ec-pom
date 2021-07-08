package Acciones;


import org.apache.log4j.Logger;

import org.openqa.selenium.WebDriver;

import CommonFuntions.BaseTest;
import Pages.InicioSesionPage;


public class LoginAccion extends BaseTest {
	
	InicioSesionPage iniciosesionpage = new InicioSesionPage(driver);
	
	private static Logger log = Logger.getLogger(LoginAccion.class);
	
	BaseTest baseTest;
	
	public LoginAccion(WebDriver driver) { 
		super(driver);		
	}	
	
	public void iniciarSesion() {	
		esperaExplicita(iniciosesionpage.inputUsuario);
		EscribirElemento(iniciosesionpage.inputUsuario, "taperez@excelcredit.co");
		EscribirElemento(iniciosesionpage.inputContrasena, "Colombia.2020");
		hacerClick(iniciosesionpage.botonAutenticar);			
		adjuntarCaptura("InisioSesionExitoso");		
	}	
		
	
}
