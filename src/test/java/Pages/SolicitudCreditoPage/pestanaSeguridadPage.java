package Pages.SolicitudCreditoPage;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class pestanaSeguridadPage {
	WebDriver driver;
	
	public By Siguiente;
	public By PestanaSeguridad;	
	public By BotonGuardar;
	public By Grilla;
	public By Concepto;
	public By DatosConsulta;
	public By ActivoVigente;
    public By BtnCheck;
    public By Viable;
	
	
	
	public pestanaSeguridadPage(WebDriver driver) {
		this.driver = driver;
		
		Siguiente = By.id("next-menu");
		PestanaSeguridad = By.xpath("//a[text()='SEGURIDAD']");
		BotonGuardar = By.xpath("//a[text()='Guardar']");
		Grilla=By.id("form:listaCreditos_data");
		Concepto=By.xpath("//div[text()='CONCEPTO FINAL ']");
		DatosConsulta= By.xpath("//*[@class='card-title' and text()='DATOS CONSULTA:']");
		ActivoVigente = By.xpath("//div[@class='modal fade']");
		BtnCheck =By.xpath("//div[@class='radio disabled' and @id='uniform-formulario2:estados:1']");
		Viable = By.id("formConsultas:estados0:0_clone");
	}
}
