package Pages.CreditosPage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class PagesCartaNotificaciones {
	WebDriver driver;
	public By filtroCedula;
	public By generarCarta;
	public By spinnerCarta;
	public By listaValoresKey;
	public By listaValoresValue;
	public By tablaValores;
	public By vlrCredito;

	public PagesCartaNotificaciones(WebDriver driver) {
		filtroCedula = By.id("form:listaCartaAceptacion:j_idt56:filter");
		generarCarta = By.id("form:listaCartaAceptacion:0:enlaceGenerarCarta");
		spinnerCarta = By.className("ng-tns-c44-0 ng-star-inserted");
		tablaValores = By.xpath("//table[@class='t-values m-t-20']");
		listaValoresKey = By.xpath("//table[@class='t-values m-t-20']/child::tr//child::td[1]");
		listaValoresValue = By.xpath("//table[@class='t-values m-t-20']/child::tr//child::td[2]");
		vlrCredito = By.xpath("//table[@class='t-values m-t-20']/child::tr[3]//child::td[2]");
	}
}
