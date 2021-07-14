package Acciones.AplicacionCierreAccion;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import Acciones.PanelPrincipalAccion;
import CommonFuntions.BaseTest;
import Pages.AplicacionCierrePage.PagoListaPagoPages;


public class AplicacionCierreAccion extends BaseTest {
	
	
	WebDriver driver;
	private static Logger log = Logger.getLogger(AplicacionCierreAccion.class);
	PanelPrincipalAccion panelprincipalaccion;
	PagoListaPagoPages listapagopage;
	
	public AplicacionCierreAccion(WebDriver driver) {
		super(driver);
		panelprincipalaccion = new PanelPrincipalAccion(driver);
		listapagopage = new PagoListaPagoPages(driver);
	}
	
	public void NavegarPagoConOpcion(String opcion) {
		panelprincipalaccion.navegarPagoconOpcion(opcion);
	}
	
	public void SeleccionarPeriodoAno(String Periodo) {
		esperaExplicita(null);
	}
}
