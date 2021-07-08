package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class PagesClienteParaBienvenida {
	WebDriver driver;
	
	public By filtrocedula;
	public By Continuar;
	public By Check;
	public By Guardar;
	public By Correcta;
	public By CheckCondicionesCredito;
	public By Telefono;
	public By Correo;
	public By CedulaGrilla;
	public By Desembolso;	
	public By ListDesembolso;
	public By detalledelascarteras;
	public By CalificacionProceso;
	public By CalificacionCobro;
	public By Acepta;
	
	public PagesClienteParaBienvenida(WebDriver driver) {
		filtrocedula = By.id("form:listaClientesBienvenida:j_idt77:filter");
		detalledelascarteras= By.xpath("//div[text()='DETALLE DE LAS CARTERAS']");
		Continuar = By.xpath("//a[@title='Continuar proceso']");
		Check =  By.xpath("//*[(starts-with(@id,'form:estado')  or   starts-with(@id,'form:respuesta') ) and @value='true' and @class='ui-radio-clone']");
	    CedulaGrilla = By.xpath("//td[text()='52912399']");
		Guardar= By.xpath("//*[text()='Guardar']");
	    Correcta = By.xpath("//*[text()='Correcta']");
	    CheckCondicionesCredito = By.xpath("//*[@type='radio' and @value='true' and @class='ui-radio-clone' and @id!='formCondicionCredito:cobroAsesor:0_clone' and @id!='formCondicionCredito:informacionSuministrada:0_clone']");
	    Telefono=By.id("form:j_idt109");
	    Correo=By.name("form:j_idt127");
	    Desembolso=By.id("formCondicionCredito:j_idt296_label");
	    ListDesembolso=By.xpath("//li[starts-with(@id,'formCondicionCredito:j_idt296') ]");
	    CalificacionProceso=By.id("formCondicionCredito:informacionSuministrada:0_clone");
	    CalificacionCobro=By.id("formCondicionCredito:cobroAsesor:1_clone");
	    Acepta = By.xpath("//span[text()='Acepta']");
	    
	}
}
