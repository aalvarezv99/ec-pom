package CommonFuntions;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
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
import org.apache.poi.hssf.record.PageBreakRecord.Break;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;

import com.google.common.base.Function;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;

import Consultas.OriginacionCreditoQuery;
import Pages.SolicitudCreditoPage.PestanaDigitalizacionPage;
import Pages.SolicitudCreditoPage.pestanaSeguridadPage;
import io.qameta.allure.Allure;

public class BaseTest {

	public WebDriver driver;

	private static Properties pro = new Properties(); 
	private static InputStream in = BaseTest.class.getResourceAsStream("../test.properties");
	private static Logger log = Logger.getLogger(BaseTest.class);
	public static Map<String, String> datosEscenario = new HashMap<>();
	Pages.SolicitudCreditoPage.pestanaSeguridadPage pestanaSeguridadPage;
	
	

	public BaseTest(WebDriver driver) {
		this.driver = driver;
		pestanaSeguridadPage = new Pages.SolicitudCreditoPage.pestanaSeguridadPage(driver);
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
	
	public String GetText(By locator) {
		return driver.findElement(locator).getText();
	}

	
	public Boolean assertEstaPresenteElemento (By locator) {
		try {
			esperaExplicitaPestana(locator);
			return driver.findElement(locator).isDisplayed();
			}catch (Exception e) {
			return false;
		}
		
	}
	
	public void assertvalidarEquals (String a, String b) {
		assertEquals(a,b);
		
	}
	
	public void assertValidarEqualsImprimeMensaje(String mensaje, String a, String b) {
		assertEquals(mensaje,a,b);
	}
	
	public void assertBooleanImprimeMensaje(String mensaje,boolean variable) {
		assertFalse("####################"+ mensaje +"#############",variable);
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
	 
	 
	public String MetodoFecha(String Fecha,By selectFecha,By contAno,By contMes,By conteDia, By ListeDia) {
		
		int dia = Integer.parseInt(separarFecha(Fecha + "/", "dia"));
		String mes = separarFecha(Fecha + "/", "mes");
        String ano = separarFecha(Fecha + "/", "ano");
		
     // Seleccionar Fecha
		driver.findElement(selectFecha).click();
		ElementVisible();
		
		selectByVisibleText(contAno,ano);
		selectByVisibleText(contMes,mes);
		
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
	
	public void selectByVisibleText(By locator, String valor) {

		Select se = new Select(driver.findElement(locator));
		se.selectByVisibleText(valor);

	}

	public void BuscarenGrilla(By locator, String Dato) {
		driver.findElement(locator).click();
		driver.findElement(locator).sendKeys(Keys.HOME, Keys.chord(Keys.SHIFT, Keys.END),Dato);
	}
    /************* FIN FUNC Assert Selenium ****************/
	
	
	public void Refrescar() {
		driver.navigate().refresh();
	}
    
	public void recorerpestanas(String Dato) {
		By locator = By.xpath("//a[text()='"+Dato+"']");
		while(assertEstaPresenteElemento(locator)==false) {
			hacerClick(pestanaSeguridadPage.Siguiente);
		}
		hacerClick(locator);
	}

	public void EnviarEnter(By locator) {
		driver.findElement(locator).sendKeys(Keys.ENTER);
	}
   
   
   public void assertTextonotificacion(By locator,String Texto) {		
		String pageText = driver.findElement(locator).getText();
		if(assertEstaPresenteElemento(By.xpath("//*[@class='ui-growl-title']"))==true) {
			assertThat("Texto no encontrado", pageText.toUpperCase(), containsString(Texto.toUpperCase()));
		}		
	}
	
	public void assertTextoelemento(By locator, String Comparar) {

		assertEquals(driver.findElement(locator).getText(), Comparar);

	}
	

	public void BuscarTextoPage(String Texto) {
		String pageText = driver.findElement(By.tagName("body")).getText();
		assertThat("Texto no encontrado", pageText, containsString(Texto));

	}

	public static double redondearDecimales(double valorInicial, int numeroDecimales) {
        double parteEntera, resultado;
        resultado = valorInicial;
        parteEntera = Math.floor(resultado);
        resultado=(resultado-parteEntera)*Math.pow(10, numeroDecimales);
        resultado=Math.round(resultado);
        resultado=(resultado/Math.pow(10, numeroDecimales))+parteEntera;
        return resultado;
    }
	
	public static String reemplazar(String cadena, String busqueda, String reemplazo) {
		  return cadena.replaceAll(busqueda, reemplazo);
		}
 
	public int edad(String Fecha){
 
		String modificada="";
		String[] mes = Fecha.split("/");
 
        switch (mes[1]) 
        {
            case "Ene" :  modificada=Fecha.replaceAll("Ene","01");
                          break;
            case "Feb" :  modificada=Fecha.replaceAll("Feb","02");
                          break;
            case "Mar" :  modificada=Fecha.replaceAll("Mar","03");
                          break;
            case "Abr" :  modificada=Fecha.replaceAll("Abr","04");
                          break;
            case "May" :  modificada=Fecha.replaceAll("May","05");
                          break;
            case "Jun" :  modificada=Fecha.replaceAll("Jun","06");
                          break;
            case "Jul" :  modificada=Fecha.replaceAll("Jul","07");
                          break;
            case "Ago" :  modificada=Fecha.replaceAll("Ago","08");
                          break;
            case "Sep" :  modificada=Fecha.replaceAll("Sep","09");
                          break;
            case "Oct" :  modificada=Fecha.replaceAll("Oct","10");
                          break;
            case "Nov" :  modificada=Fecha.replaceAll("Nov","11");
                          break;
            case "Dic" :  modificada=Fecha.replaceAll("Dic","12");;
                          break;                     
           }
                          
		DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");		
		LocalDate fechaNacimiento = LocalDate.parse(modificada, formato);		
		Period edad = Period.between(fechaNacimiento, LocalDate.now());
		int a =  edad.getYears();
		return a;
	}
                          
                          
                          
	public double MontoaSolicitar(int valorCredito,int primaAnticipada,double tasaPorMillon, double estudioCredito, double tasaFianza, double vlrIva) {
                          
		double Valor =valorCredito+(valorCredito*tasaPorMillon/1000000*primaAnticipada)+(valorCredito*(estudioCredito/100)*vlrIva)+(valorCredito*(tasaFianza/100)*vlrIva);
		log.info("MontoaSolicitar "+ redondearDecimales(Valor,0));
		return redondearDecimales(Valor,0);
	}
    
	/*TP 10/08/2021 Se actualiza al nuevo calculo con la tasa dos y el mes dos
	 * */
	public double CuotaCorriente (int valorCredito,double tasaUno,int plazo, double tasaDos, int mesDos) {      
		double Valor = 0;
		//Se valida que el plazo sea manor al mes Dos y toma una u otra formula
		if(plazo < mesDos) {
			Valor = Math.round(valorCredito/((Math.pow((1+tasaUno), (plazo)) -1)/(tasaUno* Math.pow((1+tasaUno), (plazo)))));
		}
		else {
			Valor = Math.round(valorCredito/((Math.pow((1+tasaUno),(mesDos-1)) -1)/(tasaUno*Math.pow((1+tasaUno), (mesDos-1)))
					+((Math.pow((1+tasaDos), (plazo-(mesDos-1)))-1)/(tasaDos*Math.pow((1+tasaDos), (plazo-(mesDos-1) ))))
					/(Math.pow((1+tasaUno), (mesDos-1)))));
		
		}
		log.info("Cuotacorriente "+redondearDecimales(Valor,0));
		return redondearDecimales(Valor,0);
	}
               
	
	/* TP 10/08/2021 Se actualiza el calculo del valor de la fianza por el monto neto, ya no es por monto total de la solicitud
	 * */
	public double EstudioCreditoIva (int MontoSoli, double porcentajeEstudioCredito) {
                          
		double Valor= ((MontoSoli*porcentajeEstudioCredito)/100)+(((MontoSoli*porcentajeEstudioCredito)/100)*0.19);	
		log.info("Estudio Credito Hijo " + redondearDecimales(Valor,0));
		return (int) redondearDecimales(Valor,0);
                          
	}
                          
	public double CapacidadPagaduria(int IngresosCliente,int DescuentosLey,int DescuentosNomina,int colchon) {
		double Valor = ((IngresosCliente-DescuentosLey)/2)-DescuentosNomina-colchon;
		log.info("Capacidad Pagaduria" + redondearDecimales(Valor,0));
		return (int) redondearDecimales(Valor,0);
	}
	
	/* TP 10/08/2021 Se actualiza el calculo del valor de la fianza por el monto neto, ya no es por monto total de la solicitud
	 * */
	public double ValorFianza (int MontoSoli,double TasaFianza, double Variable ){
 
		double Valor=((MontoSoli*TasaFianza)/100)*Variable;
		log.info("VlrFianza Hijo " + redondearDecimales(Valor,0));
		return (int) redondearDecimales(Valor,0);
	}
	
	/** ThainerPerez 28/Sept/2021, Se crea el metodo para calcular la fianza de retanqueo
	 */
	public double vlrFianzaRetanqueoHijo(int montoSoli, double tasaFianza, double iva, double porEstudioCre, int tasaXmillon, int periodoPrima ) {
		double valor = montoSoli/(1+((porEstudioCre/100)*iva)+((tasaFianza/100)*iva)+((double)tasaXmillon/1000000)*periodoPrima)*(tasaFianza/100)*iva;
		log.info("Fianza Hijo " + valor);
		return (int) redondearDecimales(valor,0);
	}
	
	public double PrimaSeguroRetanqueoHijo(int montoSoli, double tasaFianza, double iva, double porEstudioCre, int tasaXmillon, int periodoPrima ) {
		double valor =montoSoli/(1+(porEstudioCre/100)*iva+(tasaFianza/100)*iva+(double)tasaXmillon/1000000*periodoPrima)*tasaXmillon*(double)periodoPrima/1000000;
		log.info("Prima Seguro Hijo " + valor);
		return (int) redondearDecimales(valor,0);
	}
	
	public double EstudioCreditoRetanqueoHijo(int montoSoli, double tasaFianza, double iva, double porEstudioCre, int tasaXmillon, int periodoPrima ) {
		double valor = montoSoli/(1+(porEstudioCre/100)*iva+(tasaFianza/100)*iva+((double)tasaXmillon/1000000*periodoPrima))*(porEstudioCre/100)*iva;
		log.info("Estudio Credito Hijo" + valor);
		return (int) redondearDecimales(valor,0);
	}
	
	public double Gmf4100(int comprascartera, double variable) {
 
		double Valor= comprascartera*variable;
		log.info("Vlr Gmf4100" + redondearDecimales(Valor,0));
		return redondearDecimales(Valor,0);
 
	}
 
	public double ValorInteresesIniciales(int TotalMontoSoli,double Tasa,int DiasIniciales,int diasMes) {
 
		double Valor = ((TotalMontoSoli*Tasa)/100)*((double)DiasIniciales/diasMes);
		log.info("Vlr Intereses Iniciales" + redondearDecimales(Valor,0));
		return redondearDecimales(Valor,0);
 
	}
 
	/* 
	 * TP 10/08/2021 Se actualiza para que calcule con el monto Neto, no con el monto total de la solicitud
	 * */
	public double PrimaAnticipadaSeguro (int MontoSoli,int variable,double TasaxMillon, int ParametroPrimaSeguro) {
		double Valor=((double)MontoSoli/variable)*(TasaxMillon*ParametroPrimaSeguro);
		log.info("Prima Seguro Anticipado " + redondearDecimales(Valor,0));
		return redondearDecimales(Valor,0);
		}
		
	public double PrimaNeta (int PrimaPadre,int MontoPadre,int MesesActivos,int PrimaHijo,int variableMillon,double TasaxMillon, int ParametroPrimaSeguro) {
	  
		double Valor=PrimaPadre-((MontoPadre*TasaxMillon)/variableMillon)*MesesActivos;	
		log.info(" ###### Valor no consumido ##### " + Valor );	
		if(Valor<=0)
	    	Valor=0;
		Valor=PrimaHijo-redondearDecimales(Valor,0);		
	    log.info(" ###### Valor prima ##### " + Valor );
	    if(Valor<=0)
	    	Valor=0;	        
		return redondearDecimales(Valor,0);
	}
	
	public double PrimaNoDevengadaCPadre (int PrimaPadre,int MontoPadre,int MesesActivos,int PrimaHijo,int variableMillon,double TasaxMillon, int ParametroPrimaSeguro) {
		  
		double Valor=PrimaPadre-((MontoPadre*TasaxMillon)/variableMillon)*MesesActivos;	
		log.info(" ###### Valor no consumido ##### " + Valor );	
		if(Valor<=0)
	    	Valor=0;
		        
		return redondearDecimales(Valor,0);
	}
	
	
	
	
   
	
	public double RemanenteEstimado (int TotalMontoSoli,int CompraCartera,int Gravamento4100,int DescuentoPrimaAnticipada, int estudioCredito, int ValorFianza ) {
        double Valor=TotalMontoSoli-(CompraCartera+Gravamento4100+DescuentoPrimaAnticipada+estudioCredito+ValorFianza);
        log.info("Remanente estimado " + redondearDecimales(Valor,0));
		return redondearDecimales(Valor,0);
	}
   
	/*
	 * TP 06/08/2021 Se actualiza el metodo para que trabaje con la capacidad del cliente - tasa uno -plazo -mesdos y tasa dos
	 * */
	public double MontoMaxDesembolsar (int IngresosCliente,int DescuentosLey, int DescuentosNomina, int Colchon,
						double tasaUno,int plazo,double tasaDos,int mesDos) {
		double Capacidad=((double)((IngresosCliente-DescuentosLey)/2))-DescuentosNomina-Colchon;
		double valor = 0;
		if(plazo<mesDos) {
			//plazo menos a mes dos
			valor = Math.round(Capacidad*((Math.pow((1+tasaUno), (plazo)) )-1)/(tasaUno*Math.pow((1+tasaUno),(plazo))));	
		}else {
			//plazo mayor a mes dos
			valor = Math.round(
			Capacidad*((Math.pow((1+tasaUno), (mesDos-1)))-1)/(tasaUno*Math.pow((1+tasaUno), (mesDos-1)) )+
			(Capacidad*(( Math.pow((1+tasaDos), (plazo-(mesDos-1))) )-1)/(tasaDos* Math.pow((1+tasaDos), (plazo-(mesDos-1))) ))/
			Math.pow((1+tasaUno), (mesDos-1)) );
		}
		log.info("Monto Maximo Desembolsar " +redondearDecimales(valor=(valor<0)?0:valor,0));		
		return redondearDecimales(valor,0);
	}
	
	public double EstudioCreditoIvacxc (int TotalMontoSoli, double porcentajeEstudioCredito) {
                          
		double Valor= ((TotalMontoSoli*porcentajeEstudioCredito)/100)+(((TotalMontoSoli*porcentajeEstudioCredito)/100)*0.19);	
		log.info("Estudio Credito" + redondearDecimales(Valor,0));
		return (int) redondearDecimales(Valor,0);
                          
	}
	
	/*ThainerPerez 20-sep-2021, Se crea el metodo que devuelve el remanente estimado para retanqueos*/
	public double remanenteEstimadoRetanqueo(int TotalMontoSoli, int saldoDia, int fianza, int estudioCredito, int comprasCartera, int gmf4x100, int primaSeguro) {
		double valor = 0;
		valor = TotalMontoSoli - (saldoDia+fianza+estudioCredito+comprasCartera+gmf4x100+primaSeguro);
		log.info("Remanente estimado Retanqueo " + redondearDecimales(valor,0));
		return redondearDecimales(valor,0);
		
	}
	
	/*************************Fin Formula de CXC*****************************/
 
	/************* FIN FUNC Assert Selenium ****************/
   
   
   
	/************* INICIO FUNC REPORTES ***********************/
	
   
	/************* FIN FUNC REPORTES ***********************/
   
	/********* INICIO FUNC AVANZADAS SELENIUM **************/
	public void selectValorLista(By contlist, By itemsLista, String valor) {
   
		WebElement contEstado = driver.findElement(contlist);
		List<WebElement> listaEstado = contEstado.findElements(itemsLista);
		for (WebElement contenido : listaEstado) {
			if (contenido.getText().toUpperCase().contains(valor.toUpperCase())) {
				contenido.click();
				break;
			}
		}
	}
   
	public void selectValorLista(By lista, String Texto) {
   
	List<WebElement> ListaElement = driver.findElements(lista);	
	for(int i=0;i<ListaElement.size();i++) {
		String str = limpiarCadena(ListaElement.get(i).getText());		
		  if(str.toUpperCase().contains(limpiarCadena(Texto.toUpperCase()))==true) {			 
			 driver.findElement(By.id(ListaElement.get(i).getAttribute("id"))).click();
		     ElementVisible();
		     break;
		}
	}
	
	}
    
	 public void assertVisibleElemento(By locator) {		
		assertTrue(driver.findElement(locator).isDisplayed());
	}
	 //Metodo que retorna true o false si esta o no presente un elemento
	 public boolean ValidarElementoPresente(By locator) {
		 
		 return driver.findElements(locator).isEmpty();
	 }
	
	public void ClicUltimoElemento(By lista) {
		List<WebElement> ListaElement = driver.findElements(lista);		
		int i=ListaElement.size()-1;
		System.out.println("mensaje  "+i+"  atributo  ");
		if (i>0) { 
		System.out.println("mensaje  "+i+"  atributo  ");
		driver.findElement(By.id(ListaElement.get(i).getAttribute("id"))).click();
		}else {
			System.out.println("mensaje  "+i+"  atributo  ");
			driver.findElement(By.id(ListaElement.get(0).getAttribute("id"))).click();
					}

	}

	/********* FIN FUNC AVANZADAS SELENIUM **************/

	/************ INICIO FUNC JAVASCRIPT ************/
	
	public void hacerScrollAbajo() {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,10)");
	}

	public void hacerScrollSeguridad() {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,50)");
	}


	//metodo que usa JavaScrip para hacer Scroll
	public void Hacer_scroll(By locator) throws InterruptedException {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			WebElement Element = driver.findElement(locator);
			js.executeScript("arguments[0].scrollIntoView();", Element);
	}
	
	//metodo que usa JavaScrip para hacer Scroll Abajo
	public void Hacer_scroll_Abajo(By locator) throws InterruptedException {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		WebElement Element = driver.findElement(locator);
		js.executeScript("arguments[0].scrollIntoView(false);", Element);
}
	//metodo que usa JavaScrip para hacer Scroll Arriba
	public void Hacer_scroll_Arriba(By locator) throws InterruptedException {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		WebElement Element = driver.findElement(locator);
		js.executeScript("arguments[0].scrollIntoView(true);", Element);
}

	public void cargarPdf(By AutorizacionConsulta,By CopiaCedula,By DesprendibleNomina, String Pdf ) throws InterruptedException {
		File fichero = new File(Pdf);
		System.out.println("--------------ruta pdf: " + fichero.getAbsolutePath());
		driver.findElement(AutorizacionConsulta).sendKeys(fichero.getAbsolutePath());
		esperaExplicitaNopresente(AutorizacionConsulta);
		hacerClicknotificacion();
		//esperaExplicitaNopresente(By.xpath("//*[@class='ui-growl-title']"));
		ElementVisible();
		driver.findElement(CopiaCedula).sendKeys(fichero.getAbsolutePath());
		esperaExplicitaNopresente(CopiaCedula);
		hacerClicknotificacion();
		//esperaExplicitaNopresente(By.xpath("//*[@class='ui-growl-title']"));
		ElementVisible();
		driver.findElement(DesprendibleNomina).sendKeys(fichero.getAbsolutePath());
		esperaExplicitaNopresente(DesprendibleNomina);
		hacerClicknotificacion();
		//esperaExplicitaNopresente(By.xpath("//*[@class='ui-growl-title']"));
		ElementVisible();

	}
	
	public void cargarPdfDigitalizacion(String Pdf) throws InterruptedException {
		File fichero = new File(Pdf);
		List<WebElement> BtnCarga = driver.findElements(By.xpath("//input[starts-with(@id,'form:cargarDocumentos')]"));	
		String [] id = new String[BtnCarga.size()];
		for(int i=0;i<BtnCarga.size();i++) {
			id[i]=BtnCarga.get(i).getAttribute("id");
		}
		for(int i=0;i<id.length;i++) {		
			Thread.sleep(450);
		    driver.findElement(By.id(id[i])).sendKeys(fichero.getAbsolutePath());		    
		    esperaExplicitaNopresente(By.xpath("ui-progressbar ui-widget ui-widget-content ui-corner-all"));
		    esperaExplicita(By.xpath("//*[@class='ui-growl-title']"));
		    hacerClicknotificacion();		
		    hacerScrollAbajo();
		    ElementVisible();
		}
		adjuntarCaptura("CargueDocumentos");
		ElementVisible();
		Hacer_scroll(PestanaDigitalizacionPage.EnVerificacion);
    
	}
    
	public void llenarInputMultiples(By locator,String Text) throws InterruptedException {
		List<WebElement> Input = driver.findElements(locator);	
		for(int i=0;i<Input.size();i++) {
			driver.findElement(By.id(Input.get(i).getAttribute("id"))).click();
			ElementVisible();
		    driver.findElement(By.id(Input.get(i).getAttribute("id"))).sendKeys(Text);
		    ElementVisible();
    
		}
		ElementVisible();
		Thread.sleep(1000);
	}
    
    //Metodo utilizado para abir los pdf en el navegador el que recibe la ruta del pdf + el nombre
	public void abriPdfNavegador(String rutaPdf) {		
		try {
			Thread.sleep(2500);
			driver.get(rutaPdf);						
		} catch (Exception e) {
			log.error("########## ERROR BASETEST - ABRIPDFNAVEGADOR() ##########" + e);
		}
		
	}

	public void llenarDepartamentoCiudadReferenciacion(By DepartamentoList, By CiudadList,String Departamento,String Ciudad, int cantidaRef) throws InterruptedException {
		
		List<WebElement> DptList = driver.findElements(DepartamentoList);		
		List<WebElement> CdaList = driver.findElements(CiudadList);	
		List<WebElement> CdaLabel = driver.findElements(By.xpath("//label[starts-with(@id,'form:j_idt156:') and contains(@id,'ciudad_label')]"));
		List<WebElement> DptLabel = driver.findElements(By.xpath("//label[starts-with(@id,'form:j_idt156:') and contains(@id,'departamento_label')]"));
    
    
		ElementVisible();
		for(int i=0;i<cantidaRef;i++) {
			Thread.sleep(1000);
    
			hacerClick(By.id(DptLabel.get(i).getAttribute("id")));
			ElementVisible();			
			selectValorLista(DepartamentoList,Departamento);
			ElementVisible();
			Thread.sleep(1000);		
			hacerClick(By.id("form:j_idt156:"+i+":ciudad_label"));
    
			ElementVisible();
			selectValorLista(CiudadList,Ciudad);
		    ElementVisible();
    
		}
    
		//form:j_idt156:0:relacion_label
		//form:j_idt156:1:relacion_label
		ElementVisible();
    
	}
    
	public void MarcarCheckCorrecto() throws InterruptedException {
		Thread.sleep(1000);
		WebElement Valor = driver.findElement(By.id("form:dUiRepeat"));
		List<WebElement> BtnCheck = Valor.findElements(By.xpath("//*[starts-with(@id,'form:estado') and contains(@id,'0_clone')]"));
    
		WebElement Valor2 = driver.findElement(By.id("form:dUiRepeat"));
		List<WebElement> BtnCheck2 = Valor2.findElements(By.xpath("//*[contains(@id,'form:j_idt1')]"));
    
		int count =0;
        	for (WebElement contenido : BtnCheck) {				
			contenido.click();
			count = count + 1;
			String a = "//div[@id='"+BtnCheck2.get(count).getAttribute("id")+"']";
			hacerScrollAbajo();			
		}
        	adjuntarCaptura("MarcacionCheck");
        }
        
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
		//log.info("**************** Evidencia Tomada Reporte:" + descripcion + dateFormat.format(GregorianCalendar.getInstance().getTime()) +"**************");
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
		       //log.info("**************** Evidencia Tomada Local:" + descripcion + dateFormat.format(GregorianCalendar.getInstance().getTime()) +"**************");
		       FileUtils.copyFile(scrFile, new File(String.format("%s.png", imageNombre)));
		} catch (Exception e) {
			log.error("############## ERROR,  BaseTest - adjuntarCapturaLocal() #########" + e);
		}
		
		
	}

	/************* FIN FUNC REPORTES ***********************/

	/**
	 * M&eacute;todo encargado de marchar una opci&oacute;n proveniente de un
	 * elemento de tipo radio button o checkbox.
	 *
	 * @param locator					El elemento de tipo radio button o
	 *                                  checkbox.
	 * @throws InterruptedException
	 */
	public void MarcarCheck(By locator) throws InterruptedException {
		Thread.sleep(1000);

		List<WebElement> BtnCheck = driver.findElements(locator);

		int count =0;

		for (WebElement contenido : BtnCheck) {

			contenido.click();
			ElementVisible();
			count = count + 1;
			hacerScrollAbajo();
		}
	}
 
 /********* INICIO FUNC AVANZADAS SELENIUM **************/
	

public void clickvarios(By locator) {
		List<WebElement> clickvarios = driver.findElements(locator);
		for(int i=0;i<clickvarios.size();i++) {
			clickvarios.get(i).click();
			//driver.findElement(By.id(Input.get(i).getAttribute("id"))).click();
		}
	}
    
   public void contarFilasTablas(By locator) {
		int countFilas;
		countFilas = driver.findElements(locator).size();
		while (countFilas != 1) {
			countFilas = driver.findElements(locator).size();
		}
	}
   
   /*
    * Recibe una tabla y retorna una pocion con sui valor en String
    * */
   public String buscarElementoFilaTabla(By locator, int pocision) {
	   List<WebElement> ListaElement = driver.findElements(locator);	
	   return ListaElement.get(pocision).getText();	
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
	
	
	/*
	 * TP - 21/07/2021
	 * Se crea el metodo que recibe un dia y lo selecciona en cualquier calendario*/
	public void selecDiaCalendario(By lisDias, String dia) {
		List<WebElement> list = driver.findElements(lisDias);
		for (WebElement contenido : list) {
			if (contenido.getText().contains(dia)) {				
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

	public void clickvariosespera(By locator) throws InterruptedException {
		Thread.sleep(1000);
		List<WebElement> clickvarios = driver.findElements(locator);
		int Totaldoc=clickvarios.size();
		if (Totaldoc != 0) {
		String Borrar=clickvarios.get(0).getAttribute("id");
		for(int i=0;i<Totaldoc;i++) {	
			Thread.sleep(2000);			
			hacerClick(By.id(Borrar));			
			hacerClickVariasNotificaciones();
			}
		hacerClickVariasNotificaciones();}
	}
	
	public void cargarpdf(By locator,String Pdf) {
		File fichero = new File(Pdf);
		driver.findElement(locator).sendKeys(fichero.getAbsolutePath());
	}

	/************ FIN FUNC JAVASCRIPT ************/

	/************ INICIO DE ESPERAS ************/
		
	public void esperaExplicitaTexto(String texto) {
	
		WebDriverWait wait = new WebDriverWait(driver, 60);
		wait.until(ExpectedConditions.textToBePresentInElementLocated(By.tagName("body"),texto));
    
	}
    
    
	public void esperaExplicitaSeguridad(By locator, String Cedula) throws InterruptedException, NumberFormatException, SQLException {
		WebDriverWait wait = new WebDriverWait(driver,200);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));		

		String Concepto = "";
		OriginacionCreditoQuery query = new OriginacionCreditoQuery();
		ResultSet resultado;
		long start_time = System.currentTimeMillis();
		long wait_time = 30000;
		long end_time = start_time + wait_time;

		// si en 20 segundos no obtiene respuesta el test falla
		while (System.currentTimeMillis() < end_time && (Concepto=="" || Concepto==null)) {			
			resultado = query.ConsultaProspeccion(Cedula);
			while(resultado.next()) {
				Concepto = resultado.getString(1);
			}
		}
        log.info(" Consulta prospeccion Exitosa, Concepto igual a: " + Concepto);
	    if (Concepto!=null && (Concepto.equals("VIABLE") || Concepto.contains("CONDICIONADO"))) {
	    	assertTrue(" Consulta prospeccion Exitosa, Concepto igual a: " + Concepto, true);
	    } else {
	    	assertTrue(" Consulta prospeccion falló, Concepto igual a: " + Concepto, false);
	    }
	}
    
    public void esperaExplicitaNopresente() {		
		WebDriverWait wait = new WebDriverWait(driver, 60);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class='ui-growl-title']")));
	}
	
    public void esperaExplicitaNopresente(By locator) {
		WebDriverWait wait = new WebDriverWait(driver, 60);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
	}
	
  public void esperaExplicitaclickable(By locator) {
		WebDriverWait wait = new WebDriverWait(driver, 60);
		wait.until(ExpectedConditions.elementToBeClickable(locator));
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
		fWait.withTimeout(1250, TimeUnit.SECONDS);
		fWait.pollingEvery(1250, TimeUnit.MILLISECONDS);
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
	public void ElementVisiblenotificacion() {

		By Spinner =By.xpath("//*[@class='ui-growl-title']");
		FluentWait<WebDriver> fWait = new FluentWait<WebDriver>(driver);
		fWait.withTimeout(70, TimeUnit.SECONDS);
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
		boolean visible = fWait.until(func);
		while (visible = true) {
			break;
		}
	}

	public void hacerClicknotificacion() {
		//assertEstaPresenteElemento(By.xpath("//*[@class='ui-growl-title']"))
		if(driver.findElements(By.xpath("//*[@class='ui-growl-title']")).isEmpty()==false) {
		WebElement element = driver.findElement(By.xpath("//*[@class='ui-growl-icon-close ui-icon ui-icon-closethick']"));
		JavascriptExecutor js= (JavascriptExecutor)driver;		
		js.executeScript("arguments[0].click();", element);
		}

	}

	public void hacerClickVariasNotificaciones() throws InterruptedException {
		if(assertEstaPresenteElemento(By.xpath("//*[@class='ui-growl-title']"))==true) {
		List<WebElement> clickvarios = driver.findElements(By.xpath("//*[@class='ui-growl-icon-close ui-icon ui-icon-closethick']"));
		for(int i=0;i<clickvarios.size();i++) {			
			JavascriptExecutor js= (JavascriptExecutor)driver;		
			js.executeScript("arguments[0].click();", clickvarios.get(i));			
			}
		}
	}

/************ FIN DE ESPERAS ***********/

	/*************** FUNCIONES COMUNOS JAVA BASICAS ***********************/
	public String getFechaActualDia() {
		Calendar calen = Calendar.getInstance(TimeZone.getDefault());
		int todayInt = calen.get(Calendar.DAY_OF_MONTH);
		return Integer.toString(todayInt);
	}
	
	/*CONSULTAR EL AÑO ACTUAL*/
	public String getFechaActualAno() {
		Calendar calen = Calendar.getInstance(TimeZone.getDefault());
		int todayInt = calen.get(Calendar.DAY_OF_YEAR);
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
			assertTrue("########## BASETEST - extraerValorPDF() ########"+ e,false);
			
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
			 //assertTrue("########## ErrorAplicacionCierreAccion - validarMensajeCargueTerminado() ########"+ e,false);
			}
		 return result;
	}
		
	
public WebDriver chromeDriverConnection() {
		System.setProperty("webdriver.chrome.driver","./src/test/resources/chromedriver/chromedriver.exe");
		driver= new ChromeDriver();
	
        return driver;
	}
    
	public WebDriver FirefoxDriverConnection() {
		System.setProperty("webdriver.gecko.driver","./src/test/resources/chromedriver/geckodriver.exe");
		driver= new FirefoxDriver();
		return driver;
	}
   
    public WebDriver IEDriverConnection() {
		System.setProperty("webdriver.ie.driver","./src/test/resources/chromedriver/IEDriverServer.exe");
		driver= new InternetExplorerDriver();
		return driver;
	}
    
    /*ThainerPerez 22/Sep/2021 - Se actualiza el metodo para que seleccione el tipo (Cartera - saneamiento) en los radio button
     * */
	public void ClickBtnMultiples(By ListEntidad, By ListFiltro, By ListMonto, By ListValorCuota, By ListFecha,
			By ListNumObligacion, By ListRadioSaneamiento, By ListBtnAprobar, By ListTipo, By Listradiocompra, String[] EntidadSaneamiento,
			String[] VlrMonto, String VlrCuota[], String VlrFecha[], String VlrObligacion[])
			throws InterruptedException {
		List<WebElement> Entidad = driver.findElements(ListEntidad);
		List<WebElement> Filtro = driver.findElements(ListFiltro);
		List<WebElement> Monto = driver.findElements(ListMonto);
		List<WebElement> Cuota = driver.findElements(ListValorCuota);
		List<WebElement> Fecha = driver.findElements(ListFecha);
		List<WebElement> NumObligacion = driver.findElements(ListNumObligacion);
		List<WebElement> BtnAprobar = driver.findElements(ListBtnAprobar);
		List<WebElement> RadioSaneamiento = driver.findElements(ListRadioSaneamiento);
		List<WebElement> Tipo = driver.findElements(ListTipo);
		List<WebElement> RadioCompra = driver.findElements(Listradiocompra);
		String a[] = new String[VlrCuota.length];
		
		for (int i = 0; i < Entidad.size(); i++) {
			
			Hacer_scroll_Abajo(By.id(Entidad.get(i).getAttribute("id")));			
			esperaExplicita(By.id(Entidad.get(i).getAttribute("id")));
			
			if(Tipo.get(i).getText().trim().contains("SANEAMIENTO")){
				driver.findElement(By.id(RadioSaneamiento.get(i).getAttribute("id"))).click();
			}
			else {
				driver.findElement(By.id(RadioCompra.get(i).getAttribute("id"))).click();
			}
			
           //Llenar la entidad
			driver.findElement(By.id(Entidad.get(i).getAttribute("id"))).click();
			driver.findElement(By.id(Filtro.get(i).getAttribute("id"))).sendKeys(EntidadSaneamiento[i]);
			EnviarEnter(By.id(Filtro.get(i).getAttribute("id")));
            //llenar el monto
			Hacer_scroll(By.name(Monto.get(i).getAttribute("name")));
			Clear(By.name(Monto.get(i).getAttribute("name")));
			driver.findElement(By.name(Monto.get(i).getAttribute("name"))).sendKeys(VlrMonto[i]);
            //llenar la cuota
			Clear(By.name(Cuota.get(i).getAttribute("name")));
			driver.findElement(By.name(Cuota.get(i).getAttribute("name"))).sendKeys(VlrCuota[i]);
            //llenar la fecha
			Clear(By.name(Fecha.get(i).getAttribute("name")));
			driver.findElement(By.name(Fecha.get(i).getAttribute("name"))).sendKeys(VlrFecha[i]);
            //llenar numero de obligacion
			Clear(By.name(NumObligacion.get(i).getAttribute("name")));
			driver.findElement(By.name(NumObligacion.get(i).getAttribute("name"))).sendKeys(VlrObligacion[i]);
			a[i] = BtnAprobar.get(i).getAttribute("id");
		}

        //Aprobar las compras
		for (int i = 0; i < BtnAprobar.size(); i++) {
			assertEstaPresenteElemento(By.id(a[i]));
			Hacer_scroll_Abajo(By.id(a[i]));
			driver.findElement(By.id(a[i])).click();
			hacerClicknotificacion();
			i = i++;
			driver.findElement(By.id(a[i])).click();
			hacerClicknotificacion();
		}
	}
    
    public void  ToleranciaPeso(int a,int b){
    	int Tolerancia=a-b;
    	if (Tolerancia < 0) {
    		Tolerancia = Tolerancia * -1;
    	}
        if(Tolerancia<=1 && Tolerancia>=0){
        	System.out.println("Valor TRUE "+" Valor a "+a+" Valor b "+b);
    		assertTrue("Valor TRUE "+" Valor a "+a+" Valor b "+b,true);
    	}else {
    		System.out.println("Valor TRUE "+" Valor a "+a+" Valor b "+b);
    		assertTrue("Valor FALSE "+" Valor a "+a+" Valor b "+b,false);
    	}
    }
  
    
    public void  ToleranciaPesoMensaje(String mensaje,int a,int b){
    	int Tolerancia=a-b;
    	if (Tolerancia < 0) {
    		Tolerancia = Tolerancia * -1;
    	}
        if(Tolerancia<=1 && Tolerancia>=0){
        	log.info(mensaje+" - Valor a "+a+" Valor b "+b);
    	}else {
    		assertTrue("########### ERROR CALCULANDO " +mensaje+" ########"+" Valor a "+a+" Valor b "+b,false);
    	}
    }
    
    public String[] RetornarStringListWebElemen(By locator) {
    	
    	String[] Valores = new String[21];
    	int valor=0;
    	List<WebElement> ListaElement = driver.findElements(locator);
    	
        for(int i=0;i<ListaElement.size();i++) {
        	if(i<3) {
        		Valores[i]=ListaElement.get(i).getText().replace(".","").replace(",",".");	
        	} else {
	        	String ValorNumerico = ListaElement.get(i).getText().replace(".","");
	        	int coma = 	ValorNumerico.indexOf(",");
	        	
	        	if(coma==-1) {
	        		Valores[i]=ListaElement.get(i).getText().replace(".","").replace(",",".");	
	        	} else {
	        		Valores[i]=	ValorNumerico.substring(0,coma);
	            	System.out.println(i+" Resultado de valor llamado a bienvenida "+Valores[i]);
	        	}
        
        	}
        	System.out.println("valor " + i + " - " + Valores[i]);
        }
        
        return Valores;
    }
    
    
	/**************************************/
//backup metodo de carteras y saneamientos
    public void ClickBtnMultiplesBackup(By ListEntidad, By ListFiltro, By ListMonto, By ListValorCuota, By ListFecha,
			By ListNumObligacion, By ListRadioSaneamiento, By ListBtnAprobar, String[] EntidadSaneamiento,
			String[] VlrMonto, String VlrCuota[], String VlrFecha[], String VlrObligacion[])
			throws InterruptedException {
		List<WebElement> Entidad = driver.findElements(ListEntidad);
		List<WebElement> Filtro = driver.findElements(ListFiltro);
		List<WebElement> Monto = driver.findElements(ListMonto);
		List<WebElement> Cuota = driver.findElements(ListValorCuota);
		List<WebElement> Fecha = driver.findElements(ListFecha);
		List<WebElement> NumObligacion = driver.findElements(ListNumObligacion);
		List<WebElement> BtnAprobar = driver.findElements(ListBtnAprobar);
		List<WebElement> RadioSaneamiento = driver.findElements(ListRadioSaneamiento);
		String a[] = new String[2];

		for (int i = 0; i < Entidad.size(); i++) {
			Hacer_scroll_Abajo(By.id(Entidad.get(i).getAttribute("id")));
			esperaExplicita(By.id(Entidad.get(i).getAttribute("id")));
           //Llenar la entidad
			driver.findElement(By.id(Entidad.get(i).getAttribute("id"))).click();
			driver.findElement(By.id(Filtro.get(i).getAttribute("id"))).sendKeys(EntidadSaneamiento[i]);
			EnviarEnter(By.id(Filtro.get(i).getAttribute("id")));
            //llenar el monto
			Hacer_scroll(By.name(Monto.get(i).getAttribute("name")));
			Clear(By.name(Monto.get(i).getAttribute("name")));
			driver.findElement(By.name(Monto.get(i).getAttribute("name"))).sendKeys(VlrMonto[i]);
            //llenar la cuota
			Clear(By.name(Cuota.get(i).getAttribute("name")));
			driver.findElement(By.name(Cuota.get(i).getAttribute("name"))).sendKeys(VlrCuota[i]);
            //llenar la fecha
			Clear(By.name(Fecha.get(i).getAttribute("name")));
			driver.findElement(By.name(Fecha.get(i).getAttribute("name"))).sendKeys(VlrFecha[i]);
            //llenar numero de obligacion
			Clear(By.name(NumObligacion.get(i).getAttribute("name")));
			driver.findElement(By.name(NumObligacion.get(i).getAttribute("name"))).sendKeys(VlrObligacion[i]);
			a[i] = BtnAprobar.get(i).getAttribute("id");
		}
        
		Hacer_scroll_Abajo(By.id(RadioSaneamiento.get(RadioSaneamiento.size()-1).getAttribute("id")));
		driver.findElement(By.id(RadioSaneamiento.get(RadioSaneamiento.size()-1).getAttribute("id"))).click();

        //Aprobar las compras
		for (int i = 0; i < BtnAprobar.size(); i++) {
			assertEstaPresenteElemento(By.id(a[i]));
			Hacer_scroll_Abajo(By.id(a[i]));
			driver.findElement(By.id(a[i])).click();
			hacerClicknotificacion();
			i = i++;
			driver.findElement(By.id(a[i])).click();
			hacerClicknotificacion();
		}
	}

    public void Hacer_scroll_centrado(By locator) throws InterruptedException {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		WebElement Element = driver.findElement(locator);
		js.executeScript("arguments[0].scrollIntoView({inline: \"center\", block: \"center\", behavior: \"smooth\"});", Element);
    }

    public List<String> parseWebElementsToList(List<WebElement> list) {
    	int totalElementos = list.size();
		List<String> listString = new ArrayList<>();
		for(int i = 0; i < totalElementos; i++) {
			listString.add(list.get(i).getAttribute("id"));
		}
		return listString;
    }
    
    public void clickVariosReferenciasPositivas(By locator) throws InterruptedException {
		Thread.sleep(1000);
		List<WebElement> clickvarios = driver.findElements(locator);
		int totalElementos = clickvarios.size();
		List<String> botones = this.parseWebElementsToList(clickvarios);
		if (totalElementos != 0) {
			for(int i = 0; i < totalElementos; i++) {
				Thread.sleep(3000);
				String item = botones.get(i);
				this.esperaExplicita(By.id(item));
				this.Hacer_scroll_centrado(By.id(item));
				this.hacerClick(By.id(item));
				this.esperaExplicita(By.xpath("//*[@class='ui-growl-title']"));
				this.hacerClicknotificacion();
			}
		}
	}
    
    public boolean EncontrarElementoVisibleCss(By locator) {
    	
    	WebElement element = driver.findElement(locator);    	
		return element.getCssValue("display").equalsIgnoreCase("none");
    	
    }

    public void esperaExplicitaPestana(By locator) {
		WebDriverWait wait = new WebDriverWait(driver, 2);
		wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}
    
    public void RepetirConsultaCentrales(By locator, By locator_search) throws InterruptedException{				

		long start_time = System.currentTimeMillis();
		long wait_time = 10000;
		long end_time = start_time + wait_time;

		// si en 10 segundos no obtiene respuesta el test falla
		while (System.currentTimeMillis() < end_time || !ValidarElementoPresente(locator_search) ) {
			System.out.println("# Resultado de repetir consulta "+ValidarElementoPresente(locator_search));
			if(ValidarElementoPresente(locator_search)) {
				hacerClick(locator);		
			}else {
				 break;
			}
		}
       
	}
    
    
    
}
