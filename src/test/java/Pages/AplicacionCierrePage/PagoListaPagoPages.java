package Pages.AplicacionCierrePage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class PagoListaPagoPages {
	WebDriver driver;
	
	public By labelPeriodo;
	public By labelAno;
	public By inputPagaduria;
	public By listPeriodo;
	public By listAno;
	
	//
	public By contTablafiltro;
	public By btnCargaArchivo;
	
	public By notificacion;
	
	public PagoListaPagoPages(WebDriver driver) {
		this.driver = driver;
		
		labelPeriodo = By.id("form:usuarioFirma_label");
		labelAno = By.xpath("//*[starts-with(@id,'form:j_idt') and starts-with(@class,'ui-selectonemenu-label ui-inputfield ui-corner-all')]");
		listPeriodo = By.xpath("//*[starts-with(@id,'form:usuarioFirma_') and starts-with(@class,'ui-selectonemenu-item ui-selectonemenu-list-item ui-corner-all')]");
		listAno = By.xpath("//*[starts-with(@id,'form:j_idt5') and starts-with(@class,'ui-selectonemenu-item ui-selectonemenu-list-item ui-corner-all')]");
		inputPagaduria = By.id("form:listaPagadurias:j_idt66:filter");
		contTablafiltro = By.xpath("//*[@id=\"form:listaPagadurias_data\"]/tr/td");
		btnCargaArchivo = By.id("form:listaPagadurias:0:fileUpload2_input");
									
		notificacion = By.xpath("//*[@class='ui-growl-title']");
	}
}
