package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class PagesCreditosDesembolso {
	WebDriver driver;
	
	public static By filtrocedula;
	public static By CheckProcesarPagos;
	public static By ProcesarPagos;
	public static By FiltroMonto;
	public static By VerEditar;
	public static By Banco;
	public static By ListaBanco;
	public static By CargarEvidencia;
	public static By CrearArchivo;
	public static By Guardar;
	public static By VerEvidencias;
	public static By ArchivoCreado;
	public static By EstadoPago;
	public static By FiltroEstadoPago;
	public static By TipoOperacionSaneamiento;
	public static By TipoOperacionRemanente;
	public static By FiltroTipoOperacion;
	
	
	public PagesCreditosDesembolso(WebDriver driver) {
		filtrocedula = By.id("form:listaPagos:j_idt73:filter");
		CheckProcesarPagos = By.xpath("//span[@class='ui-chkbox-icon ui-icon ui-icon-blank ui-c']");
		ProcesarPagos = By.id("form:j_idt59");
		FiltroMonto = By.id("form:listaLotes:j_idt68:filter");
		VerEditar = By.id("form:listaLotes:0:j_idt76");
		Banco = By.id("formLote:j_idt89_label");
        ListaBanco = By.xpath("//li[starts-with(@id,'formLote:j_idt89') and text()='Seleccione un banco' ]");
        CargarEvidencia = By.id("formLote:j_idt102_input");
        CrearArchivo = By.id("formLote:procesar");
    	Guardar= By.xpath("//*[text()='Guardar']");
    	VerEvidencias= By.id("formLote:j_idt103");
    	ArchivoCreado =By.id("formLote:dispersion");
    	EstadoPago = By.xpath("//*[@id=\"form:listaPagos:j_idt87_panel\"]/div[2]/ul/li[3]/label");
    	TipoOperacionSaneamiento = By.xpath("//*[@id=\"form:listaPagos:j_idt84_panel\"]/div[2]/ul/li[4]/label");
    	TipoOperacionRemanente = By.xpath("//*[@id=\"form:listaPagos:j_idt84_panel\"]/div[2]/ul/li[3]");
    	FiltroEstadoPago = By.xpath("//label[text()='Estados']");
    	FiltroTipoOperacion = By.xpath("//label[text()='Tipo Operación']");
    	FiltroEstadoPago = By.xpath("//label[text()='Estados']");
    	EstadoPago = By.xpath("//*[@id=\"form:listaPagos:j_idt87_panel\"]/div[2]/ul/li[3]/label");
	}
}
