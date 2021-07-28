package Acciones.ComunesAccion;


import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import CommonFuntions.BaseTest;
import Pages.ComunesPage.InicioSesionPage;


public class LoginAccion extends BaseTest {
	
	
    InicioSesionPage iniciosesionpage = new InicioSesionPage(driver);
	
	private static Logger log = Logger.getLogger(LoginAccion.class);
	
	BaseTest baseTest;
	
	public LoginAccion(WebDriver driver) { 
		super(driver);		
	}	
	
	public void iniciarSesion() {	
		esperaExplicita(iniciosesionpage.inputUsuario);
		EscribirElemento(iniciosesionpage.inputUsuario, "jtellez@excelcredit.co");
		EscribirElemento(iniciosesionpage.inputContrasena, "Suaita.01");
		hacerClick(iniciosesionpage.botonAutenticar);			
		adjuntarCaptura("InisioSesionExitoso");		
	}	
	
		
	
}
