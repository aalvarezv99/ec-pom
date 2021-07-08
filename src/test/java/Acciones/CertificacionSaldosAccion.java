package Acciones;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import CommonFuntions.BaseTest;
import Pages.CertificacionSaldoPage;

public class CertificacionSaldosAccion extends BaseTest {

	WebDriver driver;
	CertificacionSaldoPage certificacionsaldopage = new CertificacionSaldoPage(driver);
	PanelPrincipalAccion panelprincipal;
	private static Logger log = Logger.getLogger(CertificacionSaldosAccion.class);

	public CertificacionSaldosAccion(WebDriver driver) {
		super(driver);
		// this.driver = driver;
		panelprincipal = new PanelPrincipalAccion(driver);
	}
	
	public void ingresarSolicitudCertificado() {
		panelprincipal.navegarCertificacionSaldo();
	}

	public void buscarCliente(String cliente, String idCredito) throws InterruptedException {
		Clear(certificacionsaldopage.inputCedula);
		// driver.findElement(certificacionsaldopage.inputCedula).clear();
		// espera();
		hacerClick(certificacionsaldopage.inputCedula);
		// driver.findElement(certificacionsaldopage.inputCedula).click();
		// espera();
		EscribirElemento(certificacionsaldopage.inputCedula, cliente);
		// driver.findElement(certificacionsaldopage.inputCedula).sendKeys(cliente);
		// driver.findElement(certificacionsaldopage.inputNumRadicado).clear();
		hacerClick(certificacionsaldopage.inputNumRadicado);
		// driver.findElement(certificacionsaldopage.inputNumRadicado).click();
		EscribirElemento(certificacionsaldopage.inputNumRadicado, idCredito);
		// driver.findElement(certificacionsaldopage.inputNumRadicado).sendKeys(idCredito);
		int countFilas;
		countFilas = driver.findElements(certificacionsaldopage.countFilas).size();
		while (countFilas != 1) {
			countFilas = driver.findElements(certificacionsaldopage.countFilas).size();
		}
		// driver.findElement(certificacionsaldopage.selectEstadoCredito).click();

//		WebElement tabla = driver.findElement(certificacionsaldopage.tablaDatos);
//		List<WebElement> columnas = tabla.findElements(certificacionsaldopage.conteColumnas);
//
//		for (WebElement contenido : columnas) {
//			count = count + 1;
//			//Numero de radicado
//			if (count == 5) {
//				this.numRadicado = Integer.parseInt(contenido.getText()); 				
//			}
//			//Estado 
//			if (count == 7) {
//				estado = contenido.getText();
//			}
//			//Vlr Mora
//			if (count == 8) {
//				vlrMora = Integer.parseInt(contenido.getText());
//			}
//			//Columna presolicitar
//			if (count == 12) {
//				if (contenido.getText().contains("Pre solicitar")) {
//					pre = true;
//				}
//			}
//			//Columna solicitar
//			if (count == 14) {
//				if (contenido.getText().contains("Solicitar")) {
//					soli = true;
//				}
//			}
//		}
//		//buscarEstado(estado);
//
//		if (vlrMora == 0) {
//			System.out.println("Credito sin mora");
//			if (pre == true) {
//				driver.findElement(certificacionsaldopage.botonPresolicitar).click();
//				Thread.sleep(1000);
//				driver.findElement(certificacionsaldopage.botonSolicitar).click();
//				Thread.sleep(2500);
//				System.out.println(driver.findElement(certificacionsaldopage.titulo).getText());				
//				driver.findElement(certificacionsaldopage.botonGuardarCertificado).click();
//				//driver.findElement(certificacionsaldopage.botonCerrarModal).click();
//				Thread.sleep(2500);	
//				
//			} else {
//				driver.findElement(certificacionsaldopage.botonSolicitar).click();
//				Thread.sleep(2500);
//				System.out.println(driver.findElement(certificacionsaldopage.titulo).getText());	
//				driver.findElement(certificacionsaldopage.botonGuardarCertificado).click();
//				//driver.findElement(certificacionsaldopage.botonCerrarModal).click();
//				Thread.sleep(2500);		
//				//assertEquals(driver.findElement(certificacionsaldopage.titulo).getText(), "nada");
//			}
//
//		} else {
//			System.out.println("Credito con mora");
//			driver.findElement(certificacionsaldopage.botonSolicitar).click();
//			Thread.sleep(2000);
//			System.out.println(driver.findElement(certificacionsaldopage.titulo).getText());
//			//driver.findElement(certificacionsaldopage.botonCerrarModal).click();
//			driver.findElement(certificacionsaldopage.botonGuardarCertificado).click();
//			Thread.sleep(2500);	
//		}
//		System.out.println("################################################");
//	}
//
//	public void buscarEstado(String estado) {
//
//		WebElement contEstado = driver.findElement(certificacionsaldopage.contEstadoCre);
//		List<WebElement> listaEstado = contEstado.findElements(certificacionsaldopage.listEstado);
//		for (WebElement contenido : listaEstado) {
//			if (contenido.getText().contains(estado)) {
//				contenido.click();
//				driver.findElement(certificacionsaldopage.botonCerrarEstado).click();
//				break;
//			}
//		}
//
//	}
//	
//	public void espera() {
//		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
//	
	}
}
