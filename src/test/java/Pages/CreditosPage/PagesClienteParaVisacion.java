package Pages.CreditosPage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class PagesClienteParaVisacion {
	WebDriver driver;
	
	public static By filtrocedula;
	public static By Continuar;
	public static By AprobadoCheck;
	public static By FechaResultado;
	public static By DocumentoLibranza;
	public static By Aprobar;
	public static By cargapdf;
	public static By PazYSalvoCartera;
	public static By PazYSalvoSaneamiento;
	
	public PagesClienteParaVisacion(WebDriver driver) {
		filtrocedula = By.id("form:listaCreditosVisacion:j_idt69:filter");
		Continuar = By.xpath("//a[@title='Continuar proceso']");
	    AprobadoCheck = By.id("uniform-formulario-visacion:seleccion:0");
	    FechaResultado = By.id("formulario-visacion:j_idt91_input");
	    DocumentoLibranza = By.id("formulario-visacion:j_idt96_input");
	    cargapdf =By.xpath("//*[text()='Ver PDF']");
	    Aprobar= By.xpath("//*[text()='Aprobar']");
	    PazYSalvoCartera = By.id("formCarteras:carteras:0:j_idt88_input");
	    PazYSalvoSaneamiento = By.id("formCarteras:carteras:1:j_idt88_input");
	}
}
