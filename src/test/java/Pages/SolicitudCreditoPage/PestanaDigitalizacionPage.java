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
	public By AgregarCartera;
	public By RadioCompra;
	public By RadioSaneamiento;
	public By EntidadCompetidor;
	public By FiltroLista;
	public By MontoSaneamiento;
	public By ValorCuotaSaneamiento;
	public By FechaVencimientoSaneamiento;
	public By NumeroObligacionSaneamiento;
	public By EntidadCompetidorCartera;
	public By FiltroListaCartera;
	public By MontoCartera;
	public By ValorCuotaCartera;
	public By FechaVencimientoCartera;
	public By NumeroObligacionCartera;
	public By BotonGuardarCartera;
	public By MarcarCartera1;
	public By MarcarCartera2;
	public By Entidad;
	public By FiltroEntidad;
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
		AgregarCartera=By.id("formRadicacion:j_idt110");
		
		//Compra cartera
		Entidad = By.id("formRadicacion:j_idt93:0:competidorSO_label");	
		FiltroEntidad =  By.id("formRadicacion:j_idt93:0:competidorSO_filter");
		ValorCuota = By.xpath("//input[starts-with(@id,'formRadicacion:j_idt') and contains(@id,'valorCuotaSO_input')]");
		FechaVencimiento = By.xpath("//input[starts-with(@id,'formRadicacion:j_idt') and contains(@id,'fechaVencimientoSO_input')]");
		NumObligacion = By.xpath("//input[starts-with(@id,'formRadicacion:j_idt') and contains(@id,'numeroObligacionSO')]");
		
		//Datos saneamineto
		EntidadCompetidor = By.id("formRadicacion:j_idt93:0:competidorSO_label");
		FiltroLista = By.id("formRadicacion:j_idt93:0:competidorSO_filter");
		RadioSaneamiento = By.id("formRadicacion:j_idt93:0:tipoCarteraSO:0");
		MontoSaneamiento = By.id("formRadicacion:j_idt93:0:montoSO_input");
		ValorCuotaSaneamiento = By.id("formRadicacion:j_idt93:0:valorCuotaSO_input");
		FechaVencimientoSaneamiento = By.id("formRadicacion:j_idt93:0:fechaVencimientoSO_input");
		NumeroObligacionSaneamiento	= By.id("formRadicacion:j_idt93:0:numeroObligacionSO");
		SeleccionSaneamiento = By.id("formRadicacion:j_idt93:0:tipoCarteraSO:0");
		
		
		//datos cartera
		EntidadCompetidorCartera = By.id("formRadicacion:j_idt93:1:competidorSO_label");
		FiltroListaCartera = By.id("formRadicacion:j_idt93:1:competidorSO_filter");
		RadioCompra = By.id("formRadicacion:j_idt93:1:tipoCarteraSO:1");
		MontoCartera = By.xpath("//input[starts-with(@id,'formRadicacion:j_idt') and contains(@id,'montoSO_input')]");
		ValorCuotaCartera = By.xpath("//input[starts-with(@id,'formRadicacion:j_idt') and contains(@id,'valorCuotaSO_input')]");
		FechaVencimientoCartera = By.id("formRadicacion:j_idt93:1:fechaVencimientoSO_input");
		NumeroObligacionCartera	= By.id("formRadicacion:j_idt93:1:numeroObligacionSO");
		BotonGuardarCartera = By.id("formRadicacion:j_idt166");
		MarcarCartera1 = By.id("formRadicacion:certificacionesDeuda:0:estadoCertificacion:0_clone");
		MarcarCartera2 = By.id("formRadicacion:certificacionesDeuda:1:estadoCertificacion:2_clone");
		MarcarCartera = By.id("formRadicacion:certificacionesDeuda:0:estadoCertificacion:0_clone");
		
	}
}
