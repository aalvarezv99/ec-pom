package Pages.SolicitudCreditoPage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class PestanaDigitalizacionPage {
	WebDriver driver;
	
	public By Titulo;
	public By Notificacion;
	public By Guardar;	
	public static By EnVerificacion;
	public By SegundaPestanaDigitalizacion;
	public By CodigoProforenses;
	public By IdentidadConfirmada;
	public By Radicar;
	
	public PestanaDigitalizacionPage(WebDriver driver) {
		
		Titulo=By.xpath("//div[text()='VERIFICACIÓN DE CALIDAD']");
		Notificacion=By.xpath("//*[@class='ui-growl-title']");
		Guardar = By.xpath("//a[text()='Guardar']");
		EnVerificacion = By.id("form:verificacionCredito");
		SegundaPestanaDigitalizacion= By.xpath("//a[@class='link-circle']");
		CodigoProforenses= By.id("formRadicacion:proforenses");
		IdentidadConfirmada= By.id("formRadicacion:resultadoProforenses:0_clone");
		Radicar= By.id("formRadicacion:radicar");
		//input[starts-with(@id,'form:cargarDocumentos')]
	}
}
