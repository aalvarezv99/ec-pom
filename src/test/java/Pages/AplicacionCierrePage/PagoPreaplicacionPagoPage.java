package Pages.AplicacionCierrePage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class PagoPreaplicacionPagoPage {
	WebDriver driver;
	
	public By ListPagaduria;
	public By FiltroPagaduria;
	public By Ano;
	public By Periodo;
	public By ValorRecaudo;
	public By ValoresRecibidos;
	public By ListAno;
	public By ListaPeriodo;
	public By btnPreaplicar;
	public By notificacion;
	
	
	public PagoPreaplicacionPagoPage(WebDriver driver) {
		this.driver = driver;
		ListPagaduria= By.id("form:DivPagaduria_label");
		FiltroPagaduria = By.id("form:DivPagaduria_filter");
		Ano = By.id("form:DivAnio_label");
		ListAno= By.xpath("//li[starts-with(@id,'form:DivAnio')]");
		Periodo=By.id("form:idPeriodos_label");
		ListaPeriodo=By.id("//*[starts-with(@id,'form:idPeriodos') and starts-with(@class,'ui-selectonemenu-item ui-selectonemenu-list-item ui-corner-all')]");
		ValorRecaudo=By.id("form:vlrRecaudo");
		ValoresRecibidos=By.id("form:vlrRecibido");
		btnPreaplicar = By.id("form:pagosPreaplicacion");
		notificacion = By.xpath("//*[@class='ui-growl-title']");
	}
}
