package Pages.AplicacionCierrePage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class PagoAplicacionFinalPage {
	WebDriver driver;
	public By inputPeriodo;
	public By inputPagaduria;
	public By listDiasCalendario;
	public By contTablaColumnas;
	public By btnConfirmarPago;
	public By btnCerrarPagaduria;

	public PagoAplicacionFinalPage(WebDriver driver) {
		this.driver = driver;
		
		inputPeriodo = By.id("form:aplicacionPagoPagaduria:j_idt71_input");
		//inputPagaduria = By.id("form:aplicacionPagoPagaduria:nombrePagaduria:filter"); -- se comentarea para ejecutar en instancia 2
		inputPagaduria = By.id("form:aplicacionPagoPagaduria:j_idt55:filter");
		listDiasCalendario = By.xpath("//td[contains(@class,' ')]");
		contTablaColumnas = By.xpath("//*[@id=\"form:aplicacionPagoPagaduria_data\"]/tr/td");
		btnConfirmarPago = By.id("form:aplicacionPagoPagaduria:0:botonConfirmarPagos");
		btnCerrarPagaduria = By.id("form:aplicacionPagoPagaduria:0:botonCerrarPagaduria");
	}
}
