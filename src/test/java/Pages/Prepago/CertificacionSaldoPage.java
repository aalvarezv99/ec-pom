package Pages.Prepago;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CertificacionSaldoPage {
	WebDriver driver;

	public By inputCedula;
	public By inputNumRadicado;
	public By tablaDatos;
	public By selectEstadoCredito;
	public By contEstadoCre;
	public By listEstado;
	public By botonCerrarEstado;
	public By conteColumnas;
	public By countFilas;
	public By botonSolicitar;
	public By botonPresolicitar;
	public By botonGuardarCertificado;
	public By botonCerrarModal;
	public int incremental = 1;
	
	//Modal Solicitud
	public By modal;
	public By titulo;
	public By contModal;
	public By telefono;

	public CertificacionSaldoPage(WebDriver driver) {
		this.driver = driver;
		
		inputCedula = By.xpath("//*[@id=\"formulario-certificaciones:listaRecaudos:j_idt62:filter\"]");
		inputNumRadicado = By.id("formulario-certificaciones:listaRecaudos:j_idt70:filter");
		tablaDatos = By.xpath("//*[@id=\"formulario-certificaciones:listaRecaudos_data\"]");
		selectEstadoCredito = By.id("formulario-certificaciones:listaRecaudos:j_idt77");
		contEstadoCre = By.xpath("//*[@id=\"formulario-certificaciones:listaRecaudos:j_idt77_panel\"]/div[2]/ul");
		listEstado = By.xpath("//*[@id=\"formulario-certificaciones:listaRecaudos:j_idt77_panel\"]/div[2]/ul/li");
		botonCerrarEstado = By.xpath("//*[@id=\"formulario-certificaciones:listaRecaudos:j_idt77_panel\"]/div[1]/a/span");
		conteColumnas = By.xpath("//*[@id=\"formulario-certificaciones:listaRecaudos_data\"]/tr/td");
		countFilas = By.xpath("//*[@id=\"formulario-certificaciones:listaRecaudos_data\"]/tr");
		botonSolicitar = By.id("formulario-certificaciones:listaRecaudos:0:j_idt103");
		botonPresolicitar = By.id("formulario-certificaciones:listaRecaudos:0:presolicitar");
		botonGuardarCertificado = By.id("formulario-certificaciones:j_idt153");		
		botonCerrarModal = By.xpath("//*[@id=\"formulario-certificaciones:formularioPopUp\"]/div[1]/a");
		
		//Modal
		titulo = By.xpath("/html/body/div[2]/div[2]/div[2]/div/form[1]/div[4]/div[1]/span");
		telefono = By.id("formulario-certificaciones:j_idt113");
		modal = By.xpath("/html/body/div[2]/div[2]/div[2]/div/form[1]/div[4]");
		contModal = By.xpath("//*[@id=\"formulario-certificaciones:formularioPopUp\"]/div[2]");
	}

}
