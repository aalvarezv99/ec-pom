package Pages.Prepago;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class GestionCertificacionPage {

	WebDriver driver;

	public By inputCedula;
	public By inputNumCredito;
	public By inputFechaSolicitud;
	public By botonDescargar;
	public By countFilas;
	
	//Elementos Solicituf fecha
	public By contFecha;
	public By listDias;

	public GestionCertificacionPage(WebDriver driver) {
		this.driver = driver;
		
		inputCedula = By.xpath("//*[@id=\"formulario-certificaciones:listaRecaudos:j_idt61:filter\"]");
		inputNumCredito = By.xpath("//*[@id=\"formulario-certificaciones:listaRecaudos:j_idt69:filter\"]");
		inputFechaSolicitud = By.id("formulario-certificaciones:listaRecaudos:j_idt90_input");
		botonDescargar = By.id("formulario-certificaciones:listaRecaudos:0:j_idt114");
		countFilas = By.xpath("//*[@id=\"formulario-certificaciones:listaRecaudos_data\"]/tr");
		//soliElementos
		contFecha = By.xpath("//*[@id=\"ui-datepicker-div\"]/table");
		listDias = By.xpath("//*[@id=\"ui-datepicker-div\"]/table/tbody/tr/td/a");
		
	}
}
