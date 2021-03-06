package Pages.CreditosPage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class RetanqueoPages {
	WebDriver driver;
	
	//Componentes iniciales
		public By cedula;
		public By credito;
		public By BtnRentaqueo;
		public By notificacion;
		public By inputCedula;
		public By continuar;
		public By borrararchivos;
		public By ImputAutorizacion;
		public By ImputCedula;
		public By ImputDesprendibleNomina;
		public By ImputOtros;
		public By BarraCarga;
		public By BtnConsultaCentrales;
		public By Viable;
		public By Guardar;
		public By Concepto;
		public By inputTasa;
		public By inputPlazo;		
		public By inputMonto;	
		public By diasIntInicial;
		public By inputIngresos;
		public By inputDescLey;
		public By inputdDescNomina;
		public By vlrCompra;
		public By MontoSolicitar;
		public By inputMontoValor;
		
		public RetanqueoPages(WebDriver driver) {
			this.driver = driver;		
			cedula = By.id("form:listaCreditos:j_idt66:filter");
			credito = By.id("form:listaCreditos:j_idt55:filter");		
			BtnRentaqueo= By.xpath("//a[@title='Retanquear']");
			notificacion = By.xpath("//*[@class='ui-growl-title']");
			inputCedula = By.xpath("//input[@id='form:listaCreditos:identificacion_cred_filtro:filter']");
			continuar = By.xpath("//a[starts-with(@id,'form:listaCreditos:') and contains(@id,'continuar_proceso')]");
			borrararchivos = By.xpath("//a[starts-with(@id,'formHistorial:otrosDocumentosList') and @class='ui-commandlink ui-widget iconoMatematicos iconoResta iconoBtn']");
			ImputAutorizacion = By.id("formHistorial:j_idt198_input");
			ImputCedula = By.id("formHistorial:j_idt205_input");
			ImputDesprendibleNomina = By.id("formHistorial:j_idt212_input");
			ImputOtros = By.id("formHistorial:j_idt219_input");
			BarraCarga= By.xpath("//div[@class='ui-progressbar ui-widget ui-widget-content ui-corner-all']");
			BtnConsultaCentrales = By.id("formHistorial:solicitudConsultaCentrales");
			Viable=By.xpath("//input[starts-with(@id,'formConsultas:estados') and contains(@id,'clone')and @value='VIABLE']");
			Guardar=By.xpath("//*[text()='Guardar']");
			Concepto=By.xpath("//div[text()='CONCEPTO FINAL ']");
			
			//Componentes valores
			MontoSolicitar = By.id("formSimuladorCredito:montoTotalCr_hinput");
			inputTasa = By.id("formSimuladorCredito:tasaCr_input");
			inputPlazo = By.id("formSimuladorCredito:plazoCr_input");		
			inputMonto = By.id("formSimuladorCredito:montoCr_input");	
			inputMontoValor = By.id("formSimuladorCredito:montoCr_hinput");	
			diasIntInicial = By.id("formSimuladorCredito:iInicialesCr_input");
			inputIngresos = By.id("formSimuladorCredito:ingresosCr");
			inputDescLey = By.id("formSimuladorCredito:leyCr");
			inputdDescNomina = By.id("formSimuladorCredito:nominaCr");
			vlrCompra = By.id("formSimuladorCredito:comprasSaneamientoCr");
		}	
}
