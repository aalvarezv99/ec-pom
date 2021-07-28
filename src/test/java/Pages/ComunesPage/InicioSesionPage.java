package Pages.ComunesPage;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class InicioSesionPage {
	
	WebDriver driver;
	
	public By inputUsuario;
	public By inputContrasena;
	public By botonAutenticar;
		
	

	public InicioSesionPage(WebDriver driver){
		this.driver = driver;		
		inputUsuario = By.id("autenticacion-5:nombre");
		inputContrasena = By.id("autenticacion-5:contrasena");
		botonAutenticar = By.id("autenticacion-5:autenticacion-13");
		
	}	
	
	
	
}
