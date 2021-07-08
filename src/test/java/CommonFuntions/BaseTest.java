package CommonFuntions;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;

import com.google.common.base.Function;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;

import io.qameta.allure.Allure;

public class BaseTest {

	public WebDriver driver;

	private static Properties pro = new Properties(); 
	private static InputStream in = BaseTest.class.getResourceAsStream("../test.properties");
	private static Logger log = Logger.getLogger(BaseTest.class);
	public static Map<String, String> datosEscenario = new HashMap<>();
	
	

	public BaseTest(WebDriver driver) {
		this.driver = driver;
	}

	public String leerPropiedades(String valor) {
		try {
			pro.load(in);
		} catch (Exception e) {
			log.error("====== ERROR LEYENDO EL ARCHIVO DE PROPIEDADES========= " + e);
		}
		return pro.getProperty(valor);
	}

	/*********** INICIO FUNC BASICAS SELENIUM ******************/
	
	public void abrirNuevaVentana() {
		((JavascriptExecutor)driver).executeScript("window.open()");
		ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
		driver.switchTo().window(tabs.get(1));
		driver.get(leerPropiedades("UrlAmbiente"));
	}

	public void Clear(By locator) {
		driver.findElement(locator).clear();
	}

	public void LimpiarConTeclado(By locator) {

		driver.findElement(locator).sendKeys(Keys.HOME, Keys.chord(Keys.SHIFT, Keys.END));

	}

	public void EscribirElemento(By locator, String texto) {
		driver.findElement(locator).sendKeys(texto);
	}

	public void hacerClick(By locator) {
		driver.findElement(locator).click();
	}
		

	public String TextoElemento(By locator) {
		return driver.findElement(locator).getAttribute("value");
	}

	public void selectByVisibleText(By locator, String valor) {

		Select se = new Select(driver.findElement(locator));
		se.selectByVisibleText(valor);

	}

	/************* FIN FUNC Assert Selenium ****************/
	
	public void assertTextonotificacion(By locator,String Texto) {		
		String pageText = driver.findElement(locator).getText();
		assertThat("Texto no encontrado", pageText.toUpperCase(), containsString(Texto.toUpperCase()));
	}
	public void assertTextoelemento(By locator, String Comparar) {

		assertEquals(driver.findElement(locator).getText(), Comparar);

	}
	
	public void assertvalidarEquals(String a, String b) {
		assertEquals(a, b);
	}

	public void BuscarTextoPage(String Texto) {
		String pageText = driver.findElement(By.tagName("body")).getText();
		assertThat("Texto no encontrado", pageText, containsString(Texto));

	}

	public Boolean assertEstaPresenteElemento(By locator) {
		try {
			return driver.findElement(locator).isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}
	
	public void assertVisibleElemento(By locator) {		
		assertTrue(driver.findElement(locator).isDisplayed());
	}
	


	public double MontoaSolicitar(int valorCredito, int primaAnticipada, double tasaPorMillon) {

		double Valor = valorCredito / (1 - (tasaPorMillon / 1000000) * primaAnticipada);

		return redondearDecimales(Valor, 0);
	}

	public double CuotaCorriente() {

		double Valor = 0;

		return redondearDecimales(Valor, 0);
	}

	public double EstudioCreditoIva(int TotalMontoSoli, double porcentajeEstudioCredito) {

		double Valor = (TotalMontoSoli * porcentajeEstudioCredito) / 100;

		System.out.println("valor dentro de la funcion " + redondearDecimales(Valor, 0));
		return (int) redondearDecimales(Valor, 0);

	}
	
	//Metodo utilizado para abir los pdf en el navegador el que recibe la ruta del pdf + el nombre
	public void abriPdfNavegador(String rutaPdf) {		
		try {
			driver.get(rutaPdf);
			Thread.sleep(2000);			
		} catch (Exception e) {
			log.error("########## ERROR BASETEST - ABRIPDFNAVEGADOR() ##########" + e);
		}
		
	}

	/************* FIN FUNC BASICAS SELENIUM ****************/

	/************* INICIO FUNC REPORTES ***********************/
	
	/*
	 * Metodo que selecciona el tipo de captura que se va a realizar (Reporte o Local)
	 * Modificar el archivo test.properties el valor "TipoCaptura"
	 * */
	public void adjuntarCaptura(String descripcion) { 
		String tipoCaptura = leerPropiedades("TipoCaptura");
		if(tipoCaptura.equals("Local")) {
			adjuntarCapturaLocal(descripcion);
		}
		else {
			adjuntarCapturaReporte(descripcion);
		}
	}
	
	/*
	 * Accion que se ejecuta y guarda agrega las imagenes en los reportes de Allure
	 * */
	public byte[] adjuntarCapturaReporte(String descripcion) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMMMM-yyyy hh.mm.ss");		
		byte[] captura = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
		log.info("**************** Evidencia Tomada Reporte:" + descripcion + dateFormat.format(GregorianCalendar.getInstance().getTime()) +"**************");
		Allure.addAttachment(descripcion + dateFormat.format(GregorianCalendar.getInstance().getTime()),
				new ByteArrayInputStream(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES)));
		return captura;
	}
	
	/*
	 * Accion que guarda las capturas de imagenes en la siguiente ruta src/test/resources/Data/Capturas 
	 * */
	public void adjuntarCapturaLocal(String descripcion) {
		try { 
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMMMM-yyyy hh.mm.ss");
		       String imageNombre = leerPropiedades("CapturasPath") +"\\" + descripcion + dateFormat.format(GregorianCalendar.getInstance().getTime());
		       File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		       log.info("**************** Evidencia Tomada Local:" + descripcion + dateFormat.format(GregorianCalendar.getInstance().getTime()) +"**************");
		       FileUtils.copyFile(scrFile, new File(String.format("%s.png", imageNombre)));
		} catch (Exception e) {
			log.error("############## ERROR,  BaseTest - adjuntarCapturaLocal() #########" + e);
		}
		
		
	}

	/************* FIN FUNC REPORTES ***********************/

	/********* INICIO FUNC AVANZADAS SELENIUM **************/

	public void hacerClicknotificacion() {
		WebElement element = driver
				.findElement(By.xpath("//*[@class='ui-growl-icon-close ui-icon ui-icon-closethick']"));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", element);
	}
	
	public String MetodoFecha(String Fecha, By selectFecha, By contAno, By contMes, By conteDia, By ListeDia) {

		int dia = Integer.parseInt(separarFecha(Fecha + "/", "dia"));
		String mes = separarFecha(Fecha + "/", "mes");
		String ano = separarFecha(Fecha + "/", "ano");

		// Seleccionar Fecha
		driver.findElement(selectFecha).click();
		ElementVisible();

		selectByVisibleText(contAno, ano);
		selectByVisibleText(contMes, mes);

		WebElement contDia = driver.findElement(conteDia);
		List<WebElement> listDia = contDia.findElements(ListeDia);
		for (WebElement contenido : listDia) {
			if (contenido.getText().contains(Integer.toString(dia))) {
				contenido.click();

				break;
			}
		}

		ElementVisible();

		return Fecha;

	}
	
	public void selectValorLista(By contlist, By itemsLista, String valor) {

		WebElement contEstado = driver.findElement(contlist);
		List<WebElement> listaEstado = contEstado.findElements(itemsLista);
		for (WebElement contenido : listaEstado) {
			if (limpiarCadena(contenido.getText()).toUpperCase().contains(valor.toUpperCase())) {
				contenido.click();
				break;
			}
		}
	}

	public void contarFilasTablas(By locator) {
		int countFilas;
		countFilas = driver.findElements(locator).size();
		while (countFilas != 1) {
			countFilas = driver.findElements(locator).size();
		}
	}

	public void selectFechActualCalendario(By contlist, By itemsLista) {
		WebElement diasCalen = driver.findElement(contlist);
		List<WebElement> list = diasCalen.findElements(itemsLista);
		String fecha = getFechaActualDia();
		for (WebElement contenido : list) {
			if (contenido.getText().contains(fecha)) {				
				contenido.click();
				break;
			}
		}
	}
	
	public void escribirListaInput(By listElementos,int Posicion,String vlrCampo) {
		List<WebElement> list = driver.findElements(listElementos);
		
		int count = 1;
		for (WebElement element : list) {
			if(Posicion == count) {
				element.clear();
				element.sendKeys(vlrCampo);	
			}
			count = count +1;
		}
	}

	/********* FIN FUNC AVANZADAS SELENIUM **************/

	/************ INICIO FUNC JAVASCRIPT ************/
	public void hacerScrollAbajo() {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,1000)");
	}

	// metodo que usa JavaScrip para hacer Scroll
	public void Hacer_scroll(By locator) throws InterruptedException {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		WebElement Element = driver.findElement(locator);
		js.executeScript("arguments[0].scrollIntoView();", Element);
	}

	/************ FIN FUNC JAVASCRIPT ************/

	/************ INICIO DE ESPERAS ************/
	
	public void esperaExplicitaNopresente() {		
		WebDriverWait wait = new WebDriverWait(driver, 60);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class='ui-growl-title']")));
	}
	
	public void esperaExplicita(By locator) {
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}

	public void esperaImplicita() {
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	public void ElementVisible() {

		By Spinner = By.xpath("//*[starts-with(@id,'formMenu:j_idt') and starts-with(@class,'ui-dialog')]");
		FluentWait<WebDriver> fWait = new FluentWait<WebDriver>(driver);
		fWait.withTimeout(20, TimeUnit.SECONDS);
		fWait.pollingEvery(250, TimeUnit.MILLISECONDS);
		fWait.ignoring(NoSuchElementException.class);

		Function<WebDriver, Boolean> func = new Function<WebDriver, Boolean>() {

			@Override
			public Boolean apply(WebDriver driver) {
				WebElement element = driver.findElement(Spinner);
				if (element.getCssValue("display").equalsIgnoreCase("none")) {
					return true;
				}

				return false;
			}
		};
// System.out.println(fWait.until(func));
		boolean visible = fWait.until(func);
		while (visible = true) {
			break;
		}		
	}

	/************ FIN DE ESPERAS ***********/

	/*************** FUNCIONES COMUNOS JAVA BASICAS ***********************/
	public String getFechaActualDia() {
		Calendar calen = Calendar.getInstance(TimeZone.getDefault());
		int todayInt = calen.get(Calendar.DAY_OF_MONTH);
		return Integer.toString(todayInt);
	}

	// Metodo que limpia los caracteres de las opciones quitando tildes
	public String limpiarCadena(String cadena) {
		cadena = Normalizer.normalize(cadena, Normalizer.Form.NFD);
		cadena = cadena.replaceAll("[^\\p{ASCII}]", "");
		return cadena;
	}
	/*************** FUNCIONES COMUNES JAVA BASICAS ***********************/
	
	/*****************FUNCIONES AVANZADAS JAVA *********************/
	public String extraerValorPDF(String ruta, String nombreDoc, String vlrBuscar) {
		String result = "";
		log.info("************Extrayendo valor Documento************");
		try {
			// abrimos el PDF
			PdfReader reader = new PdfReader(ruta + nombreDoc);
			// empezamos la coversion a pdf
			String page = limpiarCadena(PdfTextExtractor.getTextFromPage(reader, 1));
			String[] cortarString = page.split("\n");

			for (int i = 0; i < cortarString.length; i++) {
				if (cortarString[i].contains(vlrBuscar)) {
					result = cortarString[i].substring(15).replace(".", "").trim();
				}
			}
		log.info("********************************* extraerValorPDF() Valor Extraido "+ vlrBuscar + " - " + result);

		} catch (Exception e) {
			log.error("########## ERROR VALIDACION PDF ########" + vlrBuscar + "|" + e);
		}
		return result;
	}
	
	public boolean buscarVlrArchivoPDF(String nombreArchivo, String vlrBuscar,String ruta) {
		boolean result = false;
		log.info("************Buscando valor "+ nombreArchivo + " : "+ vlrBuscar  +" ************");
		 try {
	        	//abrimos el PDF
	        	PdfReader reader = new PdfReader(ruta+ nombreArchivo);	        		        	       
	        	//empezamos la coversion a pdf
	        	String page = limpiarCadena(PdfTextExtractor.getTextFromPage(reader, 1));		        	
	        	//assertThat(page.toUpperCase(),containsString(vlrBuscar.toUpperCase()));
	        }
	    		 
			 catch (Exception e) {
			 log.error("########## ERROR VALIDACION PDF ########" + vlrBuscar + e);
			}
		 return result;
	}
	
	public String separarFecha(String fecha, String tipo) {
		String result = "";
		String[] cortarString = fecha.split("/");
		switch (tipo) {
		case "dia":
			result = cortarString[0];
			break;
		case "mes":
			result = cortarString[1];
			break;
		case "ano":
			result = cortarString[2];
			break;
		}
		return result;
	}
	
	public static double redondearDecimales(double valorInicial, int numeroDecimales) {
		double parteEntera, resultado;
		resultado = valorInicial;
		parteEntera = Math.floor(resultado);
		resultado = (resultado - parteEntera) * Math.pow(10, numeroDecimales);
		resultado = Math.round(resultado);
		resultado = (resultado / Math.pow(10, numeroDecimales)) + parteEntera;
		return resultado;
	}
	/**************************************/

}
