package Acciones;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import CommonFuntions.BaseTest;
import Pages.PanelNavegacionPage;

public class PanelPrincipalAccion extends BaseTest {
	WebDriver driver;
	PanelNavegacionPage panelnavegacionpage = new PanelNavegacionPage(driver);
	Logger log = Logger.getLogger(PanelPrincipalAccion.class); 

	public PanelPrincipalAccion(WebDriver driver) {
		super(driver);
	}

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
		driver.findElement(panelnavegacionpage.selectCreditos).click();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.findElement(panelnavegacionpage.selectSolicitudCredito).click();
	}

	public void instancia5() {

		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.findElement(By.xpath("//*[@id=\"details-button\"]")).click();
		driver.findElement(By.xpath("//*[@id=\"proceed-link\"]")).click();
	}

}
