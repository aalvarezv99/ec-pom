package Pages.ConfigGlobalPage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ConfigGlobalPrepagoPage {
WebDriver driver;
	
	public By vlrCertificacion;
	public By listParametrosCertificacion;
	public By btnGuardar;
	public By notificacion;
	
	public ConfigGlobalPrepagoPage(WebDriver driver) {
		this.driver = driver;		
		vlrCertificacion = By.name("formulario-firma-certificaciones:j_idt189");
		listParametrosCertificacion = By.xpath("//input[contains(@name,'formulario-firma-certificaciones:') and contains(@class,'form-control')]");
		btnGuardar = By.xpath("//span[contains(text(),'Guardar Configuracion')]");
		notificacion = By.xpath("//*[@class='ui-growl-title']");
	}
}
