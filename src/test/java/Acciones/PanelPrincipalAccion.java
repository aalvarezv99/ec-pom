package Acciones;

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

	public void navegarCertificacionSaldo() {
		try {
			log.info("********navegarCertificacionSaldo()********");
			
			hacerClick(panelnavegacionpage.selectPrepago);
			esperaImplicita();
			hacerClick(panelnavegacionpage.selectCertificacionSaldos);
			esperaImplicita();
			
		} catch (Exception e) {
			log.error("##### ERROR navegarCertificacionSaldo()######"+e);
		}

	}

	public void navegarRecaudo() {
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.findElement(panelnavegacionpage.selectRecaudo).click();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.findElement(panelnavegacionpage.selectPagosRecaudos).click();
	}

	public void navegarGestionCertificado() {
		driver.findElement(panelnavegacionpage.selectPrepago).click();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.findElement(panelnavegacionpage.selectGestionCertificado).click();
	}

	public void navegarConfigPrepago() {
		driver.findElement(panelnavegacionpage.selectConfigBlobal).click();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.findElement(panelnavegacionpage.selecPrepagoConfig).click();
	}

	public void navegarSimulador() {
		hacerClick(panelnavegacionpage.selectSimulador);
		esperaImplicita();
		hacerClick(panelnavegacionpage.selectIrSimulador);
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
