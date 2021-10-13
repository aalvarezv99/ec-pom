package Pages.CreditosPage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class RetanqueoPages {
	WebDriver driver;
	
	//Componentes iniciales
		public By cedula;
		public By credito;
		public By Pagaduria;
		public By BtnRentaqueo;
		public By ListCreditos;
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
		public By labelTasa;
		public By inputTasaFiltro;
		public By inputPlazo;		
		public By inputMonto;	
		public By diasIntInicial;
		public By inputIngresos;
		public By inputDescLey;
		public By inputdDescNomina;
		public By vlrCompra;
		public By MontoSolicitar;
		public By inputMontoValor;
		public By SaldoTotalRecoger;
		public By montoTotalSolicitado;
		public By BtnRetanqueoMultiple;
		public By SaldoAldiaCreditosMultiples;
		
		public RetanqueoPages(WebDriver driver) {
			this.driver = driver;		
			cedula = By.id("form:listaCreditos:j_idt66:filter");
			credito = By.id("form:listaCreditos:j_idt55:filter");
			Pagaduria = By.id("form:listaCreditos:j_idt72:filter");
			BtnRentaqueo= By.xpath("//a[@title='Retanquear']");
			ListCreditos= By.xpath("//div[starts-with(@id,'form:listaCreditos') and contains(@id,'check')]");
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
			SaldoTotalRecoger=By.id("form:saldo_total_a_recoger_input_hinput");
			montoTotalSolicitado=By.id("form:montoTotalSolicitado");
			BtnRetanqueoMultiple= By.id("form:btn_retanqueo_multiple");
			SaldoAldiaCreditosMultiples= By.xpath("//table[@class='table']/child::tbody/child::tr//child::td[14]");
			
			//Componentes valores
			MontoSolicitar = By.id("formSimuladorCredito:montoTotalCr_hinput");
			inputTasa = By.id("formSimuladorCredito:tasaCr_input");
			labelTasa = By.id("formSimuladorCredito:tasa_label");
			inputTasaFiltro = By.id("formSimuladorCredito:tasa_filter");
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
