package Pages.SolicitudCreditoPage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Clase que contiene la definici&oacute;n de los elementos que se encuentran en
 * la pestaña de digitalizaci&oacute;n.
 *
 * @author Equipo de desarrollo de pruebas automatizadas.
 * @version 1.0
 */
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
	public By AgregarCartera;
	public By Entidad;
	public By FiltroEntidad;
	public By MontoCartera;
	public By ValorCuota;
	public By FechaVencimiento;
	public By NumObligacion;
	public By MarcarCartera;
	public By SeleccionSaneamiento;
	
	public PestanaDigitalizacionPage(WebDriver driver) {
		
		Titulo=By.xpath("//div[text()='VERIFICACIÓN DE CALIDAD']");
		Notificacion=By.xpath("//*[@class='ui-growl-title']");
		Guardar = By.xpath("//a[text()='Guardar']");
		EnVerificacion = By.id("form:verificacionCredito");
		SegundaPestanaDigitalizacion= By.xpath("//a[@class='link-circle']");
		CodigoProforenses= By.id("formRadicacion:proforenses");
		IdentidadConfirmada= By.id("formRadicacion:resultadoProforenses:0_clone");
		Radicar= By.id("formRadicacion:radicar");
		AgregarCartera = By.id("formRadicacion:j_idt110");
		Entidad = By.id("formRadicacion:j_idt93:0:competidorSO_label");	
		FiltroEntidad =  By.id("formRadicacion:j_idt93:0:competidorSO_filter");
		MontoCartera = By.id("formRadicacion:j_idt93:0:montoSO_input");
		ValorCuota = By.id("formRadicacion:j_idt93:0:valorCuotaSO_input");
		FechaVencimiento = By.id("formRadicacion:j_idt93:0:fechaVencimientoSO_input");
		NumObligacion = By.id("formRadicacion:j_idt93:0:numeroObligacionSO");
		MarcarCartera = By.id("formRadicacion:certificacionesDeuda:0:estadoCertificacion:0_clone");
		SeleccionSaneamiento = By.id("formRadicacion:j_idt93:0:tipoCarteraSO:0");
		
	}
}
