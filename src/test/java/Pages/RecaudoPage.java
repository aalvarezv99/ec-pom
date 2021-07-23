package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class RecaudoPage {
	WebDriver driver;
	
	public By botonAddPagoRecaudo;
	public By inputFecha;
	public By inputValor;
	public By archivoSoporte;
	public By checkCliente;
	public By checkPagaduria;
	public By listaPagaduria;
	public By FiltroPagaduria;
	public By Ano;
	public By RecaudoPeriodo;
	public By ListPeriodo;
	public By botonGuardarInfPago;
	
	//Elementos fecha
	public By calendario;
	public By encabezadoCalendario;
	public By tituloEncabezado;
	public By selectCalendario;
	public By selectSiguiente;
	public By contDiasCalendario;	
	public By selectDia;
	
	//Elementos Cliente	
	public By tipoPago;
	public By contTipoPago;
	public By listTipoPago;
	public By inputCedula;
	public By selectCertCancelar;
	public By selectClienteCert;
	
	//Elementos Pagaduria
	public By radioPagaduria;
	
	//Desplegables
	public By contEstadoCredito;
	public By lisEstadoCredito;
	public By selectCredito;
	public By desCedula;
	public By listCredito;
	public By botonGuardar;
	
	public By notificacion;
	
	public RecaudoPage(WebDriver driver) {
		this.driver = driver;		
		botonAddPagoRecaudo = By.xpath("//span[contains(text(),'Agregar pago de recaudo')]");
		inputFecha = By.id("formulario-pagos-recaudo:fechaPago_input");		
		contDiasCalendario = By.xpath("//*[@id=\"ui-datepicker-div\"]/table/tbody/tr");		
		selectDia = By.xpath("//td[contains(@class,' ')]");
		
		inputValor = By.xpath("//*[starts-with(@id,'formulario-pagos-recaudo:j_idt') and starts-with(@class,'ui-inputfield')]");
		archivoSoporte = By.id("formulario-pagos-recaudo:fileUploadBtn_input");
		checkCliente = By.xpath("//*[starts-with(@id,'formulario-pagos-recaudo:origenPago:') and @value='CLIENTE']");
		checkPagaduria = By.xpath("//*[starts-with(@id,'formulario-pagos-recaudo:origenPago:') and @value='PAGADURIA']");
		listaPagaduria = By.id("formulario-pagos-recaudo:DivPagaduria_label");
		FiltroPagaduria = By.id("formulario-pagos-recaudo:DivPagaduria_filter");
		Ano = By.id("formulario-pagos-recaudo:j_idt93_input");
		RecaudoPeriodo = By.id("formulario-pagos-recaudo:j_idt96_label");
		ListPeriodo= By.xpath("//*[starts-with(@id,'formulario-pagos-recaudo:j_idt96')  and @role='option']");		
		botonGuardarInfPago = By.xpath("//span[contains(text(),'Guardar Información del Pago')]");
		
		
		botonGuardar = By.id("//span[contains(text(),'Guardar Información')]");
		//Cliente
		tipoPago = By.xpath("//label[contains(text(),'Seleccione un tipo de')]");
		
		//contTipoPago = By.xpath("/html/body/div[29]/div/ul");
		listTipoPago = By.xpath("//li[contains(@class,'ui-selectonemenu-item')]");
		inputCedula = By.id("formulario-pagos-recaudo:autocomplete-cliente_input");
		selectCredito = By.xpath("//label[contains(text(),'Seleccione un')]");
		botonGuardar = By.id("formulario-pagos-recaudo:j_idt134");
		listCredito = By.xpath("//*[@id=\"formulario-pagos-recaudo:j_idt115_1\"]");
		selectCertCancelar = By.xpath("//*[@id=\"formulario-pagos-recaudo:j_idt103_label\"]");
		
		//pagaduria
		radioPagaduria = By.id("formulario-pagos-recaudo:origenPago:0");				
		
		notificacion = By.xpath("//*[@class='ui-growl-title']");
				
	}
}
