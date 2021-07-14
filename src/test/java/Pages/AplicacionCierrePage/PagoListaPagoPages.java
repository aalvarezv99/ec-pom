package Pages.AplicacionCierrePage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class PagoListaPagoPages {
	WebDriver driver;
	
	By labelPeriodo;
	By labelAno;
	By inputPagaduria;
	
	
	public PagoListaPagoPages(WebDriver driver) {
		this.driver = driver;
		
		labelPeriodo = By.id("form:usuarioFirma_label");
		labelAno = By.xpath("//*[starts-with(@id,'form:j_idt') and starts-with(@class,'ui-selectonemenu-label ui-inputfield ui-corner-all')]");
		inputPagaduria = By.id("form:listaPagadurias:j_idt66:filter");
		
	}
}
