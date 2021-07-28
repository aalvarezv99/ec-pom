package Acciones;

import static org.junit.Assert.assertTrue;

import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import CommonFuntions.BaseTest;
import Pages.PanelNavegacionPage;
import StepsDefinitions.CertificacionSaldoSteps;

public class PanelPrincipalAccion extends BaseTest {
	
	WebDriver driver;
	PanelNavegacionPage panelnavegacionpage = new PanelNavegacionPage(driver);
	Logger log = Logger.getLogger(PanelPrincipalAccion.class);
	// BaseTest baseTest;

	public PanelPrincipalAccion(WebDriver driver) {
		super(driver);
	}
	
	/*
	 *Accion para ingresar a la opcion de certificacion de saldo
	 * */	
	public void navegarCertificacionSaldo() {
		try {
			log.info("********navegarCertificacionSaldo()********");
			esperaExplicita(panelnavegacionpage.selectPrepago);			
			hacerClick(panelnavegacionpage.selectPrepago);
			esperaExplicita(panelnavegacionpage.selectCertificacionSaldos);
			hacerClick(panelnavegacionpage.selectCertificacionSaldos);						
		} catch (Exception e) {			
			log.error("##### ERROR navegarCertificacionSaldo()######"+e);
			assertTrue("##### ERROR navegarCertificacionSaldo()######"+ e,false);
		}

	}
	
	/*
	 *Accion para ingresar a la opcion de recaudo
	 * */
	public void navegarRecaudo() {
		log.info("************PanelPrincipalAccion - navegarRecaudo()****************");
		try {			
			esperaExplicita(panelnavegacionpage.selectRecaudo);
			hacerClick(panelnavegacionpage.selectRecaudo);		
			esperaExplicita(panelnavegacionpage.selectPagosRecaudos);
			hacerClick(panelnavegacionpage.selectPagosRecaudos);
		} catch (Exception e) {			
			assertTrue("#####  ERROR PANELPRINCIPALACCION - NAVEGARRECAUDO() ######"+ e,false);
		}
				
	}
	
	public void navegarPagos() {
		log.info("************PanelPrincipalAccion - navegarRecaudo()****************");
		try {			
			esperaExplicita(panelnavegacionpage.selectPagos);
			hacerClick(panelnavegacionpage.selectPagos);		
			esperaExplicita(panelnavegacionpage.selectPreaplicacionPagos);
			hacerClick(panelnavegacionpage.selectPreaplicacionPagos);
		} catch (Exception e) {			
			assertTrue("#####  ERROR PANELPRINCIPALACCION - NAVEGARRECAUDO() ######"+ e,false);
		}
				
	}

	/*
	 *Accion para ingresar a la opcion de descargar el certificado por el modulo prepago
	 * */
	public void navegarGestionCertificado() {
		log.info("**********PanelPrincipalAccion - navegarGestionCertificado()**********");
		try {
			esperaExplicita(panelnavegacionpage.selectPrepago);
			hacerClick(panelnavegacionpage.selectPrepago);			
			esperaExplicita(panelnavegacionpage.selectGestionCertificado);
			hacerClick(panelnavegacionpage.selectGestionCertificado);			
		} catch (Exception e) {
			log.error("####### ERROR PANELPRINCIPALACCION - NAVEGARGESTIONCERTIFICADO() ######"+ e);
			assertTrue("####### ERROR PANELPRINCIPALACCION - NAVEGARGESTIONCERTIFICADO() ######"+ e,false);
		}
		
	}
	
	/*
	 *ingresar al modulo de pagos y seleccionar la opcion necesaria
	 * */
	public void navegarPagoconOpcion(String opcion) {
		log.info("************ PanelPrincipalAccion -  navegarPagoconOpcion()**************");
		try {
			esperaExplicita(panelnavegacionpage.selectPagos);
			hacerClick(panelnavegacionpage.selectPagos);
			switch (opcion) {
			case "Lista pagos a cargar":
				esperaExplicita(panelnavegacionpage.selectListaPagosCargar);
				hacerClick(panelnavegacionpage.selectListaPagosCargar);
				break;
			case"Preaplicacion pagos":
				esperaExplicita(panelnavegacionpage.selectPreaplicacionPago);
				hacerClick(panelnavegacionpage.selectPreaplicacionPago);
				break;			
			case"Aplicacion Final":
				esperaExplicita(panelnavegacionpage.selectaplicacionFinal);
				hacerClick(panelnavegacionpage.selectaplicacionFinal);
				break;				
			case "Cierre Pagos":
				esperaExplicita(panelnavegacionpage.selectCierrePagos);
				hacerClick(panelnavegacionpage.selectCierrePagos);
				break;
			}
		} catch (Exception e) {
			log.error("########## ERROR PANELPRINCIPALACCION - NAVEGARPAGOCONOPCION() ########" + e);
			assertTrue("########## ERROR PANELPRINCIPALACCION - NAVEGARPAGOCONOPCION() ########"+ e,false);
		}
	}
	
	/*
	 *Accion para ingresar a la configuracion del prepago
	 * */
	public void navegarConfigPrepago() {
		log.info("**********PanelPrincipalAccion - navegarConfigPrepago()**********");
		try {
			esperaExplicita(panelnavegacionpage.selectConfigBlobal);
			hacerClick(panelnavegacionpage.selectConfigBlobal);
			esperaExplicita(panelnavegacionpage.selecPrepagoConfig);
			hacerClick(panelnavegacionpage.selecPrepagoConfig);	
		} catch (Exception e) {
			log.error("########## ERROR PANELPRINCIPALACCION - NAVEGARCONFIGPREPAGO() ########" + e);
			assertTrue("########## ERROR PANELPRINCIPALACCION - NAVEGARCONFIGPREPAGO() ########"+ e,false);
			
		}
			
	}

	public void navegarSimulador() {
		// driver.findElement(panelnavegacionpage.selectSimulador).click();
		hacerClick(panelnavegacionpage.selectSimulador);
		esperaImplicita();
		hacerClick(panelnavegacionpage.selectIrSimulador);
		// driver.findElement(panelnavegacionpage.selectIrSimulador).click();
		adjuntarCaptura("IngresoSimulador");
	}

	public void navegarCreditoSolicitud() {
		
		esperaExplicita(panelnavegacionpage.selectCreditos);
		hacerClick(panelnavegacionpage.selectCreditos);
		esperaExplicita(panelnavegacionpage.selectSolicitudCredito);
		hacerClick(panelnavegacionpage.selectSolicitudCredito);
	}
	
    public void navegarCreditoAnalisis() {
		
		esperaExplicita(panelnavegacionpage.selectCreditos);
		hacerClick(panelnavegacionpage.selectCreditos);
		esperaExplicita(panelnavegacionpage.selectAnalisisDeCredito);
		hacerClick(panelnavegacionpage.selectAnalisisDeCredito);
	}

	public void instancia5() {

		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.findElement(By.xpath("//*[@id=\"details-button\"]")).click();
		driver.findElement(By.xpath("//*[@id=\"proceed-link\"]")).click();
	}
	
	public void navegarTareas() {
		esperaExplicita(panelnavegacionpage.selectTareas);
		hacerClick(panelnavegacionpage.selectTareas);
		esperaExplicita(panelnavegacionpage.selectTareasVer);
		hacerClick(panelnavegacionpage.selectTareasVer);
	}
	
	public void CreditoClientesBienvenida() {
		esperaExplicita(panelnavegacionpage.selectCreditos);
		hacerClick(panelnavegacionpage.selectCreditos);
		esperaExplicita(panelnavegacionpage.selectClientesBienvenida);
		hacerClick(panelnavegacionpage.selectClientesBienvenida);
	}

	public void CreditoClientesVisacion() {
		esperaExplicita(panelnavegacionpage.selectCreditos);
		hacerClick(panelnavegacionpage.selectCreditos);
		esperaExplicita(panelnavegacionpage.selectClientesVisacion);
		hacerClick(panelnavegacionpage.selectClientesVisacion);
	}
	
	public void CreditoParaDesembolso() {
		esperaExplicita(panelnavegacionpage.selectDesembolso);
		hacerClick(panelnavegacionpage.selectDesembolso);
		esperaExplicita(panelnavegacionpage.selectListadepagos );
		hacerClick(panelnavegacionpage.selectListadepagos );
	}
	
	public void CreditoParaDesembolsoDescargar() {
		esperaExplicita(panelnavegacionpage.selectDesembolso);
		hacerClick(panelnavegacionpage.selectDesembolso);
		esperaExplicita(panelnavegacionpage.selectLMediosDispersion );
		hacerClick(panelnavegacionpage.selectLMediosDispersion );
	}
	
	public void Retanqueo() {
		esperaExplicita(panelnavegacionpage.selectRetanqueo);
		hacerClick(panelnavegacionpage.selectRetanqueo);
		esperaExplicita(panelnavegacionpage.selectListaCreditoRetanqueo);
		hacerClick(panelnavegacionpage.selectListaCreditoRetanqueo);
	}
	
}
