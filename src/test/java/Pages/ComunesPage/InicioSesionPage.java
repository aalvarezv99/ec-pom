package Pages.ComunesPage;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class InicioSesionPage {
	
	WebDriver driver;
	
	public By inputUsuario;
	public By inputContrasena;
	public By botonAutenticar;
	public By inputUser;
	public By inputPassword;
	public By btnLogin;

	public InicioSesionPage(WebDriver driver){
		this.driver = driver;
		// login anterior
		inputUsuario = By.id("autenticacion-5:nombre");
		inputContrasena = By.id("autenticacion-5:contrasena");
		botonAutenticar = By.id("autenticacion-5:autenticacion-13");
		// nuevo login
		inputUser = By.id("username");
		inputPassword = By.id("password");
		btnLogin = By.id("kc-login");
	}	
	
	
	
}
