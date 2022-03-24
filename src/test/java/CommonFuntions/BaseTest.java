package CommonFuntions;

import Consultas.OriginacionCreditoQuery;
import Pages.SolicitudCreditoPage.PestanaDigitalizacionPage;
import com.google.common.base.Function;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;
import io.qameta.allure.Allure;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import net.bytebuddy.asm.Advice.Return;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.*;

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
        ((JavascriptExecutor) driver).executeScript("window.open()");
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

    public Boolean assertEstaPresenteElemento(By locator) {
        try {
            esperaExplicitaPestana(locator);
            return driver.findElement(locator).isDisplayed();
        } catch (Exception e) {
            return false;
        }

    }

    public void assertvalidarEquals(String a, String b) {
        assertEquals(a, b);

    }

    public void assertValidarEqualsImprimeMensaje(String mensaje, String a, String b) {
        assertEquals(mensaje, a, b);
    }

    public void assertBooleanImprimeMensaje(String mensaje, boolean variable) {
        assertFalse("####################" + mensaje + "#############", variable);
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

    public void selectByVisibleText(By locator, String valor) {

        Select se = new Select(driver.findElement(locator));
        se.selectByVisibleText(valor);

    }

    public void BuscarenGrilla(By locator, String Dato) {
        driver.findElement(locator).click();
        driver.findElement(locator).sendKeys(Keys.HOME, Keys.chord(Keys.SHIFT, Keys.END), Dato);
    }

    /************* FIN FUNC Assert Selenium ****************/

    public void Refrescar() {
        driver.navigate().refresh();
    }

    public void recorerpestanas(String Dato) {
    	log.info("****Ingreso a la pestama****** " + Dato);
        By locator = By.xpath("//a[text()='" + Dato + "']");
        while (assertEstaPresenteElemento(locator) == false) {
            hacerClick(pestanaSeguridadPage.Siguiente);
        }
        hacerClick(locator);
    }

    public void EnviarEnter(By locator) {
        driver.findElement(locator).sendKeys(Keys.ENTER);
    }

    public void assertTextonotificacion(By locator, String Texto) {
        String pageText = driver.findElement(locator).getText();
        if (assertEstaPresenteElemento(By.xpath("//*[@class='ui-growl-title']")) == true) {
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
        resultado = (resultado - parteEntera) * Math.pow(10, numeroDecimales);
        resultado = Math.round(resultado);
        resultado = (resultado / Math.pow(10, numeroDecimales)) + parteEntera;
        return resultado;
    }

    public static String reemplazar(String cadena, String busqueda, String reemplazo) {
        return cadena.replaceAll(busqueda, reemplazo);
    }

    public int edad(String Fecha) {

        String modificada = "";
        String[] mes = Fecha.split("/");

        switch (mes[1]) {
            case "Ene":
                modificada = Fecha.replaceAll("Ene", "01");
                break;
            case "Feb":
                modificada = Fecha.replaceAll("Feb", "02");
                break;
            case "Mar":
                modificada = Fecha.replaceAll("Mar", "03");
                break;
            case "Abr":
                modificada = Fecha.replaceAll("Abr", "04");
                break;
            case "May":
                modificada = Fecha.replaceAll("May", "05");
                break;
            case "Jun":
                modificada = Fecha.replaceAll("Jun", "06");
                break;
            case "Jul":
                modificada = Fecha.replaceAll("Jul", "07");
                break;
            case "Ago":
                modificada = Fecha.replaceAll("Ago", "08");
                break;
            case "Sep":
                modificada = Fecha.replaceAll("Sep", "09");
                break;
            case "Oct":
                modificada = Fecha.replaceAll("Oct", "10");
                break;
            case "Nov":
                modificada = Fecha.replaceAll("Nov", "11");
                break;
            case "Dic":
                modificada = Fecha.replaceAll("Dic", "12");
                ;
                break;
        }

        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate fechaNacimiento = LocalDate.parse(modificada, formato);
        Period edad = Period.between(fechaNacimiento, LocalDate.now());
        int a = edad.getYears();
        return a;
    }

    public double MontoaSolicitar(int valorCredito, int primaAnticipada, double tasaPorMillon, double estudioCredito,
                                  double tasaFianza, double vlrIva) {

        double Valor = valorCredito + (valorCredito * tasaPorMillon / 1000000 * primaAnticipada)
                + (valorCredito * (estudioCredito / 100) * vlrIva) + (valorCredito * (tasaFianza / 100) * vlrIva);
        log.info("MontoaSolicitar " + redondearDecimales(Valor, 0));
        return redondearDecimales(Valor, 0);
    }

    /*
     * TP 10/08/2021 Se actualiza al nuevo calculo con la tasa dos y el mes dos
     */
    public double CuotaCorriente(int valorCredito, double tasaUno, int plazo, double tasaDos, int mesDos) {
        double Valor = 0;
        // Se valida que el plazo sea manor al mes Dos y toma una u otra formula
        if (plazo < mesDos) {
            Valor = Math.round(valorCredito
                    / ((Math.pow((1 + tasaUno), (plazo)) - 1) / (tasaUno * Math.pow((1 + tasaUno), (plazo)))));
        } else {
            Valor = Math.round(valorCredito
                    / ((Math.pow((1 + tasaUno), (mesDos - 1)) - 1) / (tasaUno * Math.pow((1 + tasaUno), (mesDos - 1)))
                    + ((Math.pow((1 + tasaDos), (plazo - (mesDos - 1))) - 1)
                    / (tasaDos * Math.pow((1 + tasaDos), (plazo - (mesDos - 1)))))
                    / (Math.pow((1 + tasaUno), (mesDos - 1)))));

        }
        log.info("Cuotacorriente " + redondearDecimales(Valor, 0));
        return redondearDecimales(Valor, 0);
    }

    /*
     * TP 10/08/2021 Se actualiza el calculo del valor de la fianza por el monto
     * neto, ya no es por monto total de la solicitud
     */
    public double EstudioCreditoIva(int MontoSoli, double porcentajeEstudioCredito) {

        double Valor = ((MontoSoli * porcentajeEstudioCredito) / 100)
                + (((MontoSoli * porcentajeEstudioCredito) / 100) * 0.19);
        log.info("Estudio Credito Hijo " + redondearDecimales(Valor, 0));
        return (int) redondearDecimales(Valor, 0);

    }

    public double CapacidadPagaduria(int IngresosCliente, int DescuentosLey, int DescuentosNomina, int colchon) {
        double Valor = ((IngresosCliente - DescuentosLey) / 2) - DescuentosNomina - colchon;
        log.info("Capacidad Pagaduria" + redondearDecimales(Valor, 0));
        return (int) redondearDecimales(Valor, 0);
    }

    /*
     * TP 10/08/2021 Se actualiza el calculo del valor de la fianza por el monto
     * neto, ya no es por monto total de la solicitud
     */
    public double ValorFianza(int MontoSoli, double TasaFianza, double Variable) {

        double Valor = ((MontoSoli * TasaFianza) / 100) * Variable;
        log.info("VlrFianza Hijo " + redondearDecimales(Valor, 0));
        return (int) redondearDecimales(Valor, 0);
    }

    /**
     * ThainerPerez 28/Sept/2021, Se crea el metodo para calcular la fianza de
     * retanqueo
     */
    public double vlrFianzaRetanqueoHijo(int montoSoli, double tasaFianza, double iva, double porEstudioCre,
                                         int tasaXmillon, int periodoPrima) {
        double valor = montoSoli / (1 + ((porEstudioCre / 100) * iva) + ((tasaFianza / 100) * iva)
                + ((double) tasaXmillon / 1000000) * periodoPrima) * (tasaFianza / 100) * iva;
        log.info("Fianza Hijo " + valor);
        return (int) redondearDecimales(valor, 0);
    }

    public double PrimaSeguroRetanqueoHijo(int montoSoli, double tasaFianza, double iva, double porEstudioCre,
                                           int tasaXmillon, int periodoPrima) {
        double valor = montoSoli
                / (1 + (porEstudioCre / 100) * iva + (tasaFianza / 100) * iva
                + (double) tasaXmillon / 1000000 * periodoPrima)
                * tasaXmillon * (double) periodoPrima / 1000000;
        log.info("Prima Seguro Hijo " + valor);
        return (int) redondearDecimales(valor, 0);
    }

    public double EstudioCreditoRetanqueoHijo(int montoSoli, double tasaFianza, double iva, double porEstudioCre,
                                              int tasaXmillon, int periodoPrima) {
        double valor = montoSoli / (1 + (porEstudioCre / 100) * iva + (tasaFianza / 100) * iva
                + ((double) tasaXmillon / 1000000 * periodoPrima)) * (porEstudioCre / 100) * iva;
        log.info("Estudio Credito Hijo" + valor);
        return (int) redondearDecimales(valor, 0);
    }

    public double Gmf4100(int comprascartera, double variable) {

        double Valor = comprascartera * variable;
        log.info("Vlr Gmf4100 " + redondearDecimales(Valor, 0));
        return redondearDecimales(Valor, 0);

    }

    public double ValorInteresesIniciales(int TotalMontoSoli, double Tasa, int DiasIniciales, int diasMes) {

        double Valor = ((TotalMontoSoli * Tasa) / 100) * ((double) DiasIniciales / diasMes);
        log.info("Vlr Intereses Iniciales" + redondearDecimales(Valor, 0));
        return redondearDecimales(Valor, 0);

    }

    /*
     * TP 10/08/2021 Se actualiza para que calcule con el monto Neto, no con el
     * monto total de la solicitud
     */
    public double PrimaAnticipadaSeguro(int MontoSoli, int variable, double TasaxMillon, int ParametroPrimaSeguro) {
        double Valor = ((double) MontoSoli / variable) * (TasaxMillon * ParametroPrimaSeguro);
        log.info("Prima Seguro Anticipado " + redondearDecimales(Valor, 0));
        return redondearDecimales(Valor, 0);
    }

    public double PrimaNeta(int PrimaPadre, int MontoPadre, int MesesActivos, int PrimaHijo, int variableMillon,
                            double TasaxMillon, int ParametroPrimaSeguro) {

        double Valor = PrimaPadre - ((MontoPadre * TasaxMillon) / variableMillon) * MesesActivos;
        log.info(" ###### Valor no consumido ##### " + Valor);
        if (Valor <= 0)
            Valor = 0;
        Valor = PrimaHijo - redondearDecimales(Valor, 0);
        log.info(" ###### Valor prima ##### " + Valor);
        if (Valor <= 0)
            Valor = 0;
        return redondearDecimales(Valor, 0);
    }

    public double PrimaNoDevengadaCPadre(int PrimaPadre, int MontoPadre, int MesesActivos, int PrimaHijo,
                                         int variableMillon, double TasaxMillon, int ParametroPrimaSeguro) {

        double Valor = PrimaPadre - ((MontoPadre * TasaxMillon) / variableMillon) * MesesActivos;
        log.info(" ###### Valor no consumido ##### " + Valor);
        if (Valor <= 0)
            Valor = 0;

        return redondearDecimales(Valor, 0);
    }

    public double RemanenteEstimado(int TotalMontoSoli, int CompraCartera, int Gravamento4100,
                                    int DescuentoPrimaAnticipada, int estudioCredito, int ValorFianza) {
        double Valor = TotalMontoSoli
                - (CompraCartera + Gravamento4100 + DescuentoPrimaAnticipada + estudioCredito + ValorFianza);
        log.info("Remanente estimado " + redondearDecimales(Valor, 0));
        return redondearDecimales(Valor, 0);
    }

    /*
     * TP 06/08/2021 Se actualiza el metodo para que trabaje con la capacidad del
     * cliente - tasa uno -plazo -mesdos y tasa dos
     */
    public double MontoMaxDesembolsar(int IngresosCliente, int DescuentosLey, int DescuentosNomina, int Colchon,
                                      double tasaUno, int plazo, double tasaDos, int mesDos) {
        double Capacidad = ((double) ((IngresosCliente - DescuentosLey) / 2)) - DescuentosNomina - Colchon;
        double valor = 0;
        if (plazo < mesDos) {
            // plazo menos a mes dos
            valor = Math.round(Capacidad * ((Math.pow((1 + tasaUno), (plazo))) - 1)
                    / (tasaUno * Math.pow((1 + tasaUno), (plazo))));
        } else {
            // plazo mayor a mes dos
            valor = Math.round(Capacidad * ((Math.pow((1 + tasaUno), (mesDos - 1))) - 1)
                    / (tasaUno * Math.pow((1 + tasaUno), (mesDos - 1)))
                    + (Capacidad * ((Math.pow((1 + tasaDos), (plazo - (mesDos - 1)))) - 1)
                    / (tasaDos * Math.pow((1 + tasaDos), (plazo - (mesDos - 1)))))
                    / Math.pow((1 + tasaUno), (mesDos - 1)));
        }
        log.info("Monto Maximo Desembolsar " + redondearDecimales(valor = (valor < 0) ? 0 : valor, 0));
        return redondearDecimales(valor, 0);
    }

    /*
     * ThainerPerez 20-sep-2021, Se crea el metodo que devuelve el remanente
     * estimado para retanqueos
     */
    public double remanenteEstimadoRetanqueo(int TotalMontoSoli, int saldoDia, int fianza, int estudioCredito,
                                             int comprasCartera, int gmf4x100, int primaSeguro) {
        System.out.println("TotalMontoSoli = " + TotalMontoSoli);
        System.out.println("saldoDia = " + saldoDia);
        System.out.println("fianza = " + fianza);
        System.out.println("estudioCredito = " + estudioCredito);
        System.out.println("comprasCartera = " + comprasCartera);
        System.out.println("gmf4x100 = " + gmf4x100);
        System.out.println("primaSeguro = " + primaSeguro);
        double valor = 0;
        valor = TotalMontoSoli - (saldoDia + fianza + estudioCredito + comprasCartera + gmf4x100 + primaSeguro);
        log.info("Remanente estimado Retanqueo " + redondearDecimales(valor, 0));
        return redondearDecimales(valor, 0);

    }

    /************************* Fin Formula de CXC *****************************/

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
        for (WebElement webElement : ListaElement) {
            String str = limpiarCadena(webElement.getText());
            if (str.toUpperCase().contains(limpiarCadena(Texto.toUpperCase()))) {
                driver.findElement(By.id(webElement.getAttribute("id"))).click();
                ElementVisible();
                break;
            }
        }

    }

    public void assertVisibleElemento(By locator) {
        assertTrue(driver.findElement(locator).isDisplayed());
    }

    // Metodo que retorna true o false si esta o no presente un elemento
    public boolean ValidarElementoPresente(By locator) {
        return driver.findElements(locator).isEmpty();
    }

    public void ClicUltimoElemento(By lista) {
        try {
            List<WebElement> ListaElement = driver.findElements(lista);
            int i = ListaElement.size() - 1;
            if (i > 0) {
                driver.findElement(By.id(ListaElement.get(i).getAttribute("id"))).click();
            } else {
                driver.findElement(By.id(ListaElement.get(0).getAttribute("id"))).click();
            }
        } catch (Exception e) {
            log.error("########## ERROR BASETEST - ClicUltimoElemento() ##########" + e);
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

    // metodo que usa JavaScrip para hacer Scroll
    public void Hacer_scroll(By locator) throws InterruptedException {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebElement Element = driver.findElement(locator);
        js.executeScript("arguments[0].scrollIntoView();", Element);
    }

    // metodo que usa JavaScrip para hacer Scroll Abajo
    public void Hacer_scroll_Abajo(By locator) throws InterruptedException {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebElement Element = driver.findElement(locator);
        js.executeScript("arguments[0].scrollIntoView(false);", Element);
    }

    // metodo que usa JavaScrip para hacer Scroll Arriba
    public void Hacer_scroll_Arriba(By locator) throws InterruptedException {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebElement Element = driver.findElement(locator);
        js.executeScript("arguments[0].scrollIntoView(true);", Element);
    }

    public void cargarPdf(By AutorizacionConsulta, By CopiaCedula, By DesprendibleNomina, String Pdf)
            throws InterruptedException {
        File fichero = new File(Pdf);
        driver.findElement(AutorizacionConsulta).sendKeys(fichero.getAbsolutePath());
        Thread.sleep(1000);
        esperaExplicitaNopresente(AutorizacionConsulta);
        ElementVisible();
        hacerClicknotificacion();
        ElementVisible();
        driver.findElement(CopiaCedula).sendKeys(fichero.getAbsolutePath());
        Thread.sleep(1000);
        esperaExplicitaNopresente(CopiaCedula);
        ElementVisible();
        hacerClicknotificacion();
        ElementVisible();
        driver.findElement(DesprendibleNomina).sendKeys(fichero.getAbsolutePath());
        Thread.sleep(1000);
        esperaExplicitaNopresente(DesprendibleNomina);
        ElementVisible();
        hacerClicknotificacion();
        ElementVisible();
        hacerClicknotificacion();
    }

    public void cargarPdfDigitalizacion(String Pdf) throws InterruptedException {
        try {
            File fichero = new File(Pdf);
            List<WebElement> BtnCarga = driver
                    .findElements(By.xpath("//input[starts-with(@id,'form:cargarDocumentos')]"));
            String[] id = new String[BtnCarga.size()];
            for (int i = 0; i < BtnCarga.size(); i++) {
                id[i] = BtnCarga.get(i).getAttribute("id");
            }
            for (int i = 0; i < id.length; i++) {
                log.info("ID DEL INPUT FILE -----------> " + id[i]);
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
        } catch (Exception e) {
            log.info("ERROR AL CARGAR LOS ARCHIVOS EN LA PESTAÑA DIGITALIZACIÓN", e);
        }

    }

    public void cargarPdfVisacionCarteras(By locator, String ruta) {
        File fichero = new File(ruta);
        try {
            List<WebElement> listaElement = driver.findElements(locator);
            List<String> lisString = this.parseWebElementsToList(listaElement);
            for (int i = 0; i < lisString.size(); i++) {
                driver.findElement(By.id(lisString.get(i))).sendKeys(fichero.getAbsolutePath());
                ;
            }

        } catch (Exception e) {
            log.error("########## ERROR BASETEST - cargarPdfVisacionCarteras() ##########" + e);
        }
    }

    public void llenarInputMultiples(By locator, String Text) throws InterruptedException {
        List<WebElement> Input = driver.findElements(locator);
        for (int i = 0; i < Input.size(); i++) {
            driver.findElement(By.id(Input.get(i).getAttribute("id"))).click();
            ElementVisible();
            driver.findElement(By.id(Input.get(i).getAttribute("id"))).sendKeys(Text);
            ElementVisible();

        }
        ElementVisible();
        Thread.sleep(1000);
    }

    // Metodo utilizado para abir los pdf en el navegador el que recibe la ruta del
    // pdf + el nombre
    public void abriPdfNavegador(String rutaPdf) {
        try {
        	Thread.sleep(2500);
            driver.get(rutaPdf);
            Thread.sleep(2000);
        } catch (Exception e) {
            log.error("########## ERROR BASETEST - ABRIPDFNAVEGADOR() ##########" + e);
        }

    }

    public void llenarDepartamentoCiudadReferenciacion(By DepartamentoList, By CiudadList, String Departamento,
                                                       String Ciudad, int cantidaRef) throws InterruptedException {

        List<WebElement> DptList = driver.findElements(DepartamentoList);
        List<WebElement> CdaList = driver.findElements(CiudadList);
        List<WebElement> CdaLabel = driver
                .findElements(By.xpath("//label[starts-with(@id,'form:j_idt156:') and contains(@id,'ciudad_label')]"));
        List<WebElement> DptLabel = driver.findElements(
                By.xpath("//label[starts-with(@id,'form:j_idt156:') and contains(@id,'departamento_label')]"));

        ElementVisible();
        for (int i = 0; i < cantidaRef; i++) {
            Thread.sleep(1000);

            hacerClick(By.id(DptLabel.get(i).getAttribute("id")));
            ElementVisible();
            selectValorLista(DepartamentoList, Departamento);
            ElementVisible();
            Thread.sleep(1000);
            hacerClick(By.id("form:j_idt156:" + i + ":ciudad_label"));

            ElementVisible();
            selectValorLista(CiudadList, Ciudad);
            ElementVisible();

        }

        // form:j_idt156:0:relacion_label
        // form:j_idt156:1:relacion_label
        ElementVisible();

    }

    public void MarcarCheckCorrecto() throws InterruptedException {
        Thread.sleep(1000);
        WebElement Valor = driver.findElement(By.id("form:dUiRepeat"));
        List<WebElement> BtnCheck = Valor
                .findElements(By.xpath("//*[starts-with(@id,'form:estado') and contains(@id,'0_clone')]"));

        WebElement Valor2 = driver.findElement(By.id("form:dUiRepeat"));
        List<WebElement> BtnCheck2 = Valor2.findElements(By.xpath("//*[contains(@id,'form:j_idt1')]"));

        int count = 0;
        for (WebElement contenido : BtnCheck) {
            contenido.click();
            count = count + 1;
            String a = "//div[@id='" + BtnCheck2.get(count).getAttribute("id") + "']";
            hacerScrollAbajo();
        }
        adjuntarCaptura("MarcacionCheck");
    }

    /************* INICIO FUNC REPORTES ***********************/

    /*
     * Metodo que selecciona el tipo de captura que se va a realizar (Reporte o
     * Local) Modificar el archivo test.properties el valor "TipoCaptura"
     */
    public void adjuntarCaptura(String descripcion) {
        String tipoCaptura = leerPropiedades("TipoCaptura");
        if (tipoCaptura.equals("Local")) {
            adjuntarCapturaLocal(descripcion);
        } else {
            adjuntarCapturaReporte(descripcion);
        }
    }

    /*
     * Accion que se ejecuta y guarda agrega las imagenes en los reportes de Allure
     */
    public byte[] adjuntarCapturaReporte(String descripcion) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMMMM-yyyy hh.mm.ss");
        byte[] captura = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
        // log.info("**************** Evidencia Tomada Reporte:" + descripcion +
        // dateFormat.format(GregorianCalendar.getInstance().getTime())
        // +"**************");
        Allure.addAttachment(descripcion + dateFormat.format(GregorianCalendar.getInstance().getTime()),
                new ByteArrayInputStream(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES)));
        return captura;
    }

    /*
     * Accion que guarda las capturas de imagenes en la siguiente ruta
     * src/test/resources/Data/Capturas
     */
    public void adjuntarCapturaLocal(String descripcion) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMMMM-yyyy hh.mm.ss");
            String imageNombre = leerPropiedades("CapturasPath") + "\\" + descripcion
                    + dateFormat.format(GregorianCalendar.getInstance().getTime());
            File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            // log.info("**************** Evidencia Tomada Local:" + descripcion +
            // dateFormat.format(GregorianCalendar.getInstance().getTime())
            // +"**************");
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
     * @param locator El elemento de tipo radio button o checkbox.
     * @throws InterruptedException
     */
    public void MarcarCheck(By locator) throws InterruptedException {
        Thread.sleep(1000);

        List<WebElement> BtnCheck = driver.findElements(locator);

        int count = 0;

        for (WebElement contenido : BtnCheck) {

            contenido.click();
            ElementVisible();
            count = count + 1;
            hacerScrollAbajo();
        }
        adjuntarCaptura("Se marcan todos los check correctos");
    }

    public void marcarCheckMultiple(By locator) throws InterruptedException {
        List<WebElement> list = driver.findElements(locator);
        List<String> listString = this.parseWebElementsToList(list);
        for (int i = 0; i < listString.size(); i++) {
            String item = listString.get(i);
            hacerClick(By.id(item));
            ElementVisible();
        }
        adjuntarCaptura("Checks marcados de procesar pagos");

    }

    /********* INICIO FUNC AVANZADAS SELENIUM 
     * @throws InterruptedException **************/

    public void clickvarios(By locator) throws InterruptedException {
        List<WebElement> clickvariosElement = driver.findElements(locator);
        List<String> clickvarios = parseWebElementsToList(clickvariosElement);
        
        for (int i = 0; i < clickvarios.size(); i++) {
        	Hacer_scroll_centrado(By.id(clickvarios.get(i)));  
        	//esperaExplicita(By.id(clickvarios.get(i)));                       
            hacerClick(By.id(clickvarios.get(i)));
            ElementVisible();
            hacerClicknotificacion();
            // driver.findElement(By.id(Input.get(i).getAttribute("id"))).click();
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
     */
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
     * TP - 21/07/2021 Se crea el metodo que recibe un dia y lo selecciona en
     * cualquier calendario
     */
    public void selecDiaCalendario(By lisDias, String dia) {
        List<WebElement> list = driver.findElements(lisDias);
        for (WebElement contenido : list) {
            if (contenido.getText().contains(dia)) {
                contenido.click();
                break;
            }
        }
    }

    public void escribirListaInput(By listElementos, int Posicion, String vlrCampo) {
        List<WebElement> list = driver.findElements(listElementos);

        int count = 1;
        for (WebElement element : list) {
            if (Posicion == count) {
                element.clear();
                element.sendKeys(vlrCampo);
            }
            count = count + 1;
        }
    }

    public void clickvariosespera(By locator) throws InterruptedException {
        Thread.sleep(1000);
        List<WebElement> clickvarios = driver.findElements(locator);
        int Totaldoc = clickvarios.size();
        if (Totaldoc != 0) {
            String Borrar = clickvarios.get(0).getAttribute("id");
            for (int i = 0; i < Totaldoc; i++) {
                hacerClicknotificacion();
                Thread.sleep(2000);
                Hacer_scroll_centrado(By.id(Borrar));
                hacerClick(By.id(Borrar));
                ElementVisible();
                hacerClickVariasNotificaciones();
            }
            hacerClickVariasNotificaciones();
        }
    }

    public void cargarpdf(By locator, String Pdf) {
        File fichero = new File(Pdf);
        driver.findElement(locator).sendKeys(fichero.getAbsolutePath());
    }

    /************ FIN FUNC JAVASCRIPT ************/

    /************ INICIO DE ESPERAS ************/

    public void esperaExplicitaTexto(String texto) {

        WebDriverWait wait = new WebDriverWait(driver, 60);
        wait.until(ExpectedConditions.textToBePresentInElementLocated(By.tagName("body"), texto));

    }

    public void esperaExplicitaSeguridad(By locator, String Cedula)
            throws InterruptedException, NumberFormatException, SQLException {
        WebDriverWait wait = new WebDriverWait(driver, 200);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));

        String Concepto = "";
        OriginacionCreditoQuery query = new OriginacionCreditoQuery();
        ResultSet resultado;
        long start_time = System.currentTimeMillis();
        long wait_time = 120000;
        long end_time = start_time + wait_time;

        // si en 2 minutos no obtiene respuesta el test falla
        while (System.currentTimeMillis() < end_time && (Concepto == "" || Concepto == null)) {
            resultado = query.ConsultaProspeccion(Cedula);
            while (resultado.next()) {
                Concepto = resultado.getString(1);
            }
        }
        log.info(" Consulta prospeccion Exitosa, Concepto igual a: " + Concepto);
        if (Concepto != null && (Concepto.equals("VIABLE") || Concepto.contains("CONDICIONADO"))) {
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
        WebDriverWait wait = new WebDriverWait(driver, 60);
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

        By Spinner = By.xpath("//*[@class='ui-growl-title']");
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
        // assertEstaPresenteElemento(By.xpath("//*[@class='ui-growl-title']"))
        if (driver.findElements(By.xpath("//*[@class='ui-growl-title']")).isEmpty() == false) {
            WebElement element = driver
                    .findElement(By.xpath("//*[@class='ui-growl-icon-close ui-icon ui-icon-closethick']"));
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].click();", element);
        }

    }

    public void hacerClickVariasNotificaciones() throws InterruptedException {
        if (assertEstaPresenteElemento(By.xpath("//*[@class='ui-growl-title']")) == true) {
            List<WebElement> clickvarios = driver
                    .findElements(By.xpath("//*[@class='ui-growl-icon-close ui-icon ui-icon-closethick']"));
            for (int i = 0; i < clickvarios.size(); i++) {
                JavascriptExecutor js = (JavascriptExecutor) driver;
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

    /* CONSULTAR EL AÑO ACTUAL */
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

    /***************** FUNCIONES AVANZADAS JAVA *********************/
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
            log.info(
                    "********************************* extraerValorPDF() Valor Extraido " + vlrBuscar + " - " + result);

        } catch (Exception e) {
            log.error("########## ERROR VALIDACION PDF ########" + vlrBuscar + "|" + e);
            assertTrue("########## BASETEST - extraerValorPDF() ########" + e, false);

        }
        return result;
    }

    /*ThainerPerez 12/Dic/2021, V1.2 - 1.	Se ajusta el metodo de buscar archivo donde al parametro vlrBuscar se le quita el ultimo caracter
     * 								   2.	La funcion tolerancia no funciona debido a que se estan comparando Dos String*/
    public boolean buscarVlrArchivoPDF(String nombreArchivo, String vlrBuscar, String ruta) {
        boolean result = false;
        log.info("************Buscando valor " + nombreArchivo + " : " + vlrBuscar + " ************");
        try {
            // abrimos el PDF
            PdfReader reader = new PdfReader(ruta + nombreArchivo);
            // empezamos la coversion a pdf
            String page = limpiarCadena(PdfTextExtractor.getTextFromPage(reader, 1).replace(",", "").replace(".", ""));
            assertThat(page.toUpperCase(), containsString(vlrBuscar.substring(0, vlrBuscar.length() - 1).toUpperCase()));
        } catch (Exception e) {
            log.error("########## ERROR VALIDACION PDF ########" + vlrBuscar + e);
            // assertTrue("########## ErrorAplicacionCierreAccion -
            // validarMensajeCargueTerminado() ########"+ e,false);
        }
        return result;
    }

    public WebDriver chromeDriverConnection() {
        System.setProperty("webdriver.chrome.driver", "./src/test/resources/chromedriver/chromedriver.exe");
        driver = new ChromeDriver();

        return driver;
    }

    public WebDriver FirefoxDriverConnection() {
        System.setProperty("webdriver.gecko.driver", "./src/test/resources/chromedriver/geckodriver.exe");
        driver = new FirefoxDriver();
        return driver;
    }

    public WebDriver IEDriverConnection() {
        System.setProperty("webdriver.ie.driver", "./src/test/resources/chromedriver/IEDriverServer.exe");
        driver = new InternetExplorerDriver();
        return driver;
    }

    /*
     * ThainerPerez 22/Sep/2021 - Se actualiza el metodo para que seleccione el tipo
     * (Cartera - saneamiento) en los radio button
     * ThainerPerez 14/Dic/2021 V1.2 - 1.	Se crea el metodo confirmarCarterasReferenciacion(), para llenar las referencias
     * 										cuando los nombres son diferentes.
     */
    public void ClickBtnMultiples(By ListEntidad, By ListFiltro, By ListMonto, By ListValorCuota, By ListFecha,
                                  By ListNumObligacion, By ListRadioSaneamiento, By ListBtnAprobar, By ListTipo, By Listradiocompra,
                                  By ListDescEntidad, String[] EntidadSaneamiento, String[] VlrMonto, String VlrCuota[], String VlrFecha[],
                                  String VlrObligacion[]) throws InterruptedException {
    	
    	try {
    		
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

            List<WebElement> descEntidad = driver.findElements(ListDescEntidad);
            String a[] = new String[VlrCuota.length];
            
            String descEntidadGet[] = new String[VlrCuota.length];
            for(int i=0;i<descEntidad.size();i++) {
            	descEntidadGet[i]=descEntidad.get(i).getText();
            	a[i] = BtnAprobar.get(i).getAttribute("id");
            }           

            
    		log.info("************ se confirman las carteras y saneamientos  basetest() **********");

            for (int i = 0; i < BtnAprobar.size(); i++) {

                Hacer_scroll_centrado(By.id(Entidad.get(i).getAttribute("id")));
                esperaExplicita(By.id(Entidad.get(i).getAttribute("id")));
                String radio = Tipo.get(i).getText().trim();
                if (radio.contains("SANEAMIENTO")) {
                    driver.findElement(By.id(RadioSaneamiento.get(i).getAttribute("id"))).click();
                } else {
                    driver.findElement(By.id(RadioCompra.get(i).getAttribute("id"))).click();
                }
                
                String[] parts = EntidadSaneamiento[i].split("- ");          
                if (!descEntidadGet[i].equals(parts[1])) {
                    for (int j = 0; j < EntidadSaneamiento.length; j++) {
                        parts = EntidadSaneamiento[j].split("- ");
                        if (descEntidadGet[i].equals(parts[1])) {
                            confirmarCarterasReferenciacion(radio, Entidad, Filtro, Monto, Cuota, Fecha, NumObligacion,
                                    EntidadSaneamiento, VlrMonto, VlrCuota, VlrFecha, VlrObligacion, i, j);
                            break;
                        }
                    }
                } else {
                    confirmarCarterasReferenciacion(radio, Entidad, Filtro, Monto, Cuota, Fecha, NumObligacion,
                            EntidadSaneamiento, VlrMonto, VlrCuota, VlrFecha, VlrObligacion, i, i);         	                 
                }
                
            }

            // Aprobar las compras
           
            for (int i = 0; i < BtnAprobar.size(); i++) {        	
                assertEstaPresenteElemento(By.id(a[i]));
                Hacer_scroll_Abajo(By.id(a[i]));
                driver.findElement(By.id(a[i])).click();
                hacerClicknotificacion();
                i = i++;
                driver.findElement(By.id(a[i])).click();
                hacerClicknotificacion();
            }
			
		} catch (Exception e) {
			log.error("########## Error - BAseTest - ClickBtnMultiples() ####### : " + e);
		}
    	
        
    }

    public void confirmarCarterasReferenciacion(String desRadio, List<WebElement> Entidad, List<WebElement> Filtro,
                                                List<WebElement> Monto, List<WebElement> Cuota, List<WebElement> Fecha, List<WebElement> NumObligacion,
                                                String[] EntidadSaneamiento, String[] VlrMonto, String VlrCuota[], String VlrFecha[],
                                                String VlrObligacion[],
                                                int indexUno, int indexDos) {
        try {
            if (desRadio.contains("SANEAMIENTO")
                    && EntidadSaneamiento[indexDos].contains("PAN AMERICAN")) {
                log.info("*** Se Agrego un saneamiento de PAN AMERICAN ***");
            } else {
            	log.info("*** Se Agrega un saneamiento");
                // Llenar la entidad
                driver.findElement(By.id(Entidad.get(indexUno).getAttribute("id"))).click();
                driver.findElement(By.id(Filtro.get(indexUno).getAttribute("id"))).sendKeys(EntidadSaneamiento[indexDos]);
                EnviarEnter(By.id(Filtro.get(indexUno).getAttribute("id")));
                // llenar el monto
                Hacer_scroll_centrado(By.name(Monto.get(indexUno).getAttribute("name")));
                Clear(By.name(Monto.get(indexUno).getAttribute("name")));
                driver.findElement(By.name(Monto.get(indexUno).getAttribute("name"))).sendKeys(VlrMonto[indexDos]);
                // llenar la cuota
                Clear(By.name(Cuota.get(indexUno).getAttribute("name")));
                driver.findElement(By.name(Cuota.get(indexUno).getAttribute("name"))).sendKeys(VlrCuota[indexDos]);
                // llenar la fecha
                Clear(By.name(Fecha.get(indexUno).getAttribute("name")));
                driver.findElement(By.name(Fecha.get(indexUno).getAttribute("name"))).sendKeys(VlrFecha[indexDos]);
                // llenar numero de obligacion
                Clear(By.name(NumObligacion.get(indexUno).getAttribute("name")));
                driver.findElement(By.name(NumObligacion.get(indexUno).getAttribute("name"))).sendKeys(VlrObligacion[indexDos]);
            }
        } catch (Exception e) {
            log.error("########## Error - BAseTest - confirmarCarterasReferenciacion() ####### : " + e);
            assertTrue("########## Error - BaseTest - confirmarCarterasReferenciacion() ######## : " + e, false);
        }

    }

    public void ToleranciaPeso(int a, int b) {
        int Tolerancia = a - b;
        if (Tolerancia < 0) {
            Tolerancia = Tolerancia * -1;
        }
        if (Tolerancia <= 1 && Tolerancia >= 0) {
            System.out.println("Valor TRUE " + " Valor a " + a + " Valor b " + b);
            assertTrue("Valor TRUE " + " Valor a " + a + " Valor b " + b, true);
        } else {
            System.out.println("Valor TRUE " + " Valor a " + a + " Valor b " + b);
            assertTrue("Valor FALSE " + " Valor a " + a + " Valor b " + b, false);
        }
    }

    public void ToleranciaPesoMensaje(String mensaje, int a, int b) {
        int Tolerancia = a - b;
        if (Tolerancia < 0) {
            Tolerancia = Tolerancia * -1;
        }
        if (Tolerancia <= 1 && Tolerancia >= 0) {
            log.info(mensaje + " - Valor a " + a + " Valor b " + b);
        } else {
            assertTrue("########### ERROR CALCULANDO " + mensaje + " ########" + " Valor a " + a + " Valor b " + b,
                    false);
        }
    }

    public List<String> RetornarStringListWebElemen(By locator) {

        List<String> Valores = new ArrayList<>();
        int valor = 0;
        List<WebElement> ListaElement = driver.findElements(locator);

        for (int i = 0; i < ListaElement.size(); i++) {
            if (i < 3) {
                Valores.add(ListaElement.get(i).getText().replace(".", "").replace(",", "."));
            } else if (i == 3) {
                Valores.add(ListaElement.get(i).getText().replace(",", "."));
            } else {
                String ValorNumerico = ListaElement.get(i).getText().replace(".", "");
                int coma = ValorNumerico.indexOf(",");

                if (coma == -1) {
                    Valores.add(ListaElement.get(i).getText().replace(".", "").replace(",", "."));
                } else {
                    Valores.add(ValorNumerico.substring(0, coma));
                    System.out.println(i + " Resultado de valor llamado a bienvenida " + Valores.get(i));
                }
            }
            System.out.println("valor " + i + " - " + Valores.get(i));
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
            // Llenar la entidad
            driver.findElement(By.id(Entidad.get(i).getAttribute("id"))).click();
            driver.findElement(By.id(Filtro.get(i).getAttribute("id"))).sendKeys(EntidadSaneamiento[i]);
            EnviarEnter(By.id(Filtro.get(i).getAttribute("id")));
            // llenar el monto
            Hacer_scroll(By.name(Monto.get(i).getAttribute("name")));
            Clear(By.name(Monto.get(i).getAttribute("name")));
            driver.findElement(By.name(Monto.get(i).getAttribute("name"))).sendKeys(VlrMonto[i]);
            // llenar la cuota
            Clear(By.name(Cuota.get(i).getAttribute("name")));
            driver.findElement(By.name(Cuota.get(i).getAttribute("name"))).sendKeys(VlrCuota[i]);
            // llenar la fecha
            Clear(By.name(Fecha.get(i).getAttribute("name")));
            driver.findElement(By.name(Fecha.get(i).getAttribute("name"))).sendKeys(VlrFecha[i]);
            // llenar numero de obligacion
            Clear(By.name(NumObligacion.get(i).getAttribute("name")));
            driver.findElement(By.name(NumObligacion.get(i).getAttribute("name"))).sendKeys(VlrObligacion[i]);
            a[i] = BtnAprobar.get(i).getAttribute("id");
        }

        Hacer_scroll_Abajo(By.id(RadioSaneamiento.get(RadioSaneamiento.size() - 1).getAttribute("id")));
        driver.findElement(By.id(RadioSaneamiento.get(RadioSaneamiento.size() - 1).getAttribute("id"))).click();

        // Aprobar las compras
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
        js.executeScript("arguments[0].scrollIntoView({inline: \"center\", block: \"center\", behavior: \"smooth\"});",
                Element);
    }

    public List<String> parseWebElementsToList(List<WebElement> list) {
        List<String> listString = new ArrayList<>();
        for (WebElement webElement : list) {
            listString.add(webElement.getAttribute("id"));
        }
        return listString;
    }

    public void clickVariosReferenciasPositivas(By locator) throws InterruptedException {
        Thread.sleep(1000);
        List<WebElement> clickvarios = driver.findElements(locator);
        int totalElementos = clickvarios.size();
        List<String> botones = this.parseWebElementsToList(clickvarios);
        if (totalElementos != 0) {
            for (int i = 0; i < totalElementos; i++) {
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

    public void RepetirConsultaCentrales(By locator, By locator_search) throws InterruptedException {

        long start_time = System.currentTimeMillis();
        long wait_time = 10000;
        long end_time = start_time + wait_time;

        // si en 10 segundos no obtiene respuesta el test falla
        while (System.currentTimeMillis() < end_time || !ValidarElementoPresente(locator_search)) {
            System.out.println("# Resultado de repetir consulta " + ValidarElementoPresente(locator_search));
            if (ValidarElementoPresente(locator_search)) {
                hacerClick(locator);
            } else {
                break;
            }
        }
    }

    public void EnviarEscape(By locator) {
        driver.findElement(locator).sendKeys(Keys.ESCAPE);
    }

    public void cambiarFocoDriver(int index) {
        ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(index));
    }

    public void cargarDatosCarta(By locator) {
        while (driver.findElement(locator).getText().equals("$")) {
            // va a consultar el contenido hasta que cambie
        }
        System.out.println(" El valor del contenido ha cambiado " + driver.findElement(locator).getText());
    }

    public Map<String, String> cleanValues(By locatorKeys, By locatorValues) {
        Map<String, String> Valores = new HashMap<String, String>();
        List<WebElement> keys = driver.findElements(locatorKeys);
        List<WebElement> values = driver.findElements(locatorValues);
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i).getText();
            String valor = values.get(i).getText();
            if (valor.contains("$")) {
                valor = valor.replace("$", "").replace(".", "");
            } else if (valor.contains("%")) {
                valor = valor.replace("%", "");
            }
            Valores.put(key, valor.trim());
        }
        this.sumarValores(Valores);
        return Valores;
    }

    public Map<String, String> obtainValuesMap(By locatorKeys, By locatorValues) { //Jonathan Varon
        Map<String, String> Valores = new HashMap<String, String>();
        List<WebElement> keys = driver.findElements(locatorKeys);
        List<WebElement> values = driver.findElements(locatorValues);
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i).getText();
            String valor = values.get(i).getText();
            if (valor.contains("$")) {
                valor = valor.replace("$", "").replace(".", "");
            } else if (valor.contains("(% NMV)")) {
                valor = valor.replace("(% NMV)", "");
            } else if (valor.contains("(Meses)")) {
                valor = valor.replace("(Meses)", "");
            } else if (valor.contains("%")) {
                valor = valor.replace("%", "");
            }
            Valores.put(key, valor.trim());
        }
        return Valores;
    }

    public void sumarValores(Map<String, String> valores) {
        int total = 0;
        int gmf = 0;
        for (Map.Entry<String, String> entry : valores.entrySet()) {
            if (entry.getKey().contains("Valor compra de cartera") || entry.getKey().contains("Valor saneamiento")) {
                total += Integer.parseInt(entry.getValue());
            }
            if (entry.getKey().contains("GMF compra de cartera") || entry.getKey().contains("GMF saneamiento")) {
                gmf += Integer.parseInt(entry.getValue());
            }
        }
        valores.put("totalCompraCartera", String.valueOf(total));
        valores.put("totalGmf", String.valueOf(gmf));
    }

    public void ToleranciaDoubleMensaje(String mensaje, double a, double b) {
        double Tolerancia = a - b;
        if (Tolerancia < 0) {
            Tolerancia = Tolerancia * -1;
        }
        if (Tolerancia <= 1 && Tolerancia >= 0) {
            log.info(mensaje + " - Valor a " + a + " Valor b " + b);
        } else {
            assertTrue("########### ERROR CALCULANDO " + mensaje + " ########" + " Valor a " + a + " Valor b " + b,
                    false);
        }
    }

    // Metodo para verificar todos los link de una pagina
    public void checkingPageLink() {
        List<WebElement> Links = driver.findElements(By.tagName("a"));
        String url = "";
        List<String> brokenLinks = new ArrayList<>();
        List<String> OkLinks = new ArrayList<>();

        HttpURLConnection httpConection = null;
        int responseCode = 200;
        Iterator<WebElement> it = Links.iterator();

        while (it.hasNext()) {
            url = it.next().getAttribute("href");
            if (url == null || url.isEmpty()) {
                System.out.println(url + " url no configurada o vacia");
                continue;
            }
            try {
                httpConection = (HttpURLConnection) (new URL(url).openConnection());
                httpConection.setRequestMethod("HEAD");
                httpConection.connect();
                responseCode = httpConection.getResponseCode();

                if (responseCode > 400) {
                    System.out.println("Error Link: -- " + url);
                    brokenLinks.add(url);
                } else {
                    System.out.println("Valido Link: -- " + url);
                    OkLinks.add(url);
                }

            } catch (Exception e) {
                System.out.println("Error al consultar la url: ----> " + e);
            }
        }

        System.out.println("Links Validos: -- " + OkLinks.size());
        System.out.println("Links Invalidos: -- " + brokenLinks.size());

        if (brokenLinks.size() > 0) {
            System.out.println("**** ERROR ------------------- lINK ROTOS");
            for (int i = 0; i < brokenLinks.size(); i++) {
                System.out.println(brokenLinks.get(i));
            }
            assertTrue("**** ERROR ------------------- lINK ROTOS", false);
        }
    }

    public void cambiarPestana(int index) {
        // driver.findElement(By.xpath("//body")).sendKeys(Keys.CONTROL + "" +
        // Keys.SHIFT + "" + Keys.TAB);
        List<String> tabs = new ArrayList<>(driver.getWindowHandles());
        System.out.println(tabs.size());
        driver.switchTo().window(tabs.get(index));
        this.cambiarFocoDriver(index);
    }

    public void esperaporestadoBD(By locator, String Cedula, String Estado) throws NumberFormatException, SQLException {
        String ConsulEstado = "";
        String notificacion = "";
        OriginacionCreditoQuery query = new OriginacionCreditoQuery();
        ResultSet resultado;
        int Contador = 0;
        // si en la cantida de intentos de contador no pasa el test falla
        while (Contador < 3 && (notificacion.isEmpty() || (notificacion.contains("pendiente")
                || notificacion.contains("error") || notificacion.contains("no se pudo crear la tarea")))) {
            resultado = query.ConsultaEstado(Cedula);
            while (resultado.next()) {
                ConsulEstado = resultado.getString(1);
            }
            esperaExplicita(locator);
            hacerClick(locator);
            ElementVisible();
            esperaExplicita(By.xpath("//*[@class='ui-growl-title']"));
            notificacion = GetText(By.xpath("//*[@class='ui-growl-title']")).toLowerCase();
            hacerClicknotificacion();
            Contador++;
        }

        if (Contador >= 3 && (notificacion.contains("pendiente") || notificacion.contains("error") || notificacion.contains("no se pudo crear la tarea"))) {
            assertTrue("Falló al realizar la consulta a centrales, # Intentos: " + Contador, false);
        }
    }

    public int sumarListaValoresCreditos(By locator) throws InterruptedException {
        List<WebElement> list = driver.findElements(locator);
        int value = 0;
        for (WebElement webElement : list) {
            value += (int) Double.parseDouble(limpiarCadenaRegex(webElement.getText().replace(".", "").replace(",", "."), "\\d+(?:[.,]\\d+)?"));
        }
        return value;
    }

    public int sumarListaValoresCreditosValue(By locator) throws InterruptedException {
        List<WebElement> list = driver.findElements(locator);
        int value = 0;
        for (WebElement webElement : list) {
            if (webElement.getAttribute("value") != null) {
                value += Integer.parseInt(webElement.getAttribute("value").replace(".", "").replace(",", "."));
            }
        }
        return value;
    }

    public void calculoCondicionesCreditoRecoger(int monto, int saldoAlDia, int retanqueoVlr, int sumaSaldoDiaRetanqueoMul) {

        if (monto != (saldoAlDia + retanqueoVlr)) {
            assertBooleanImprimeMensaje("##### ERROR el monto " + monto + " es diferente al monto total solicitado de la lista de retanqueo " + (saldoAlDia + retanqueoVlr), true);
        } else if (sumaSaldoDiaRetanqueoMul != saldoAlDia) {
            assertBooleanImprimeMensaje("##### ERROR el saldo al dia sumado y el total no coinciden #######", true);
        } else if (monto < saldoAlDia) {
            assertBooleanImprimeMensaje("##### ERROR el monto no puede ser menor a la suma "
                    + "de saldo al dia ######", true);
        } else if (monto > 120000000) {
            assertBooleanImprimeMensaje("##### ERROR El monto sobrepasa los 120.000.000 ######", true);
        }
    }

    public void capturarCreditosPadre(By locator, Map<Integer, Map<String, String>> creditosPadre) {
        List<WebElement> listCreditosPadre = driver.findElements(locator);
        List<Integer> creditosOrdenados = parseWebElementsToListGetText(listCreditosPadre);
        Comparator<Integer> comparador = Collections.reverseOrder();
        creditosOrdenados.sort(comparador);
        for (int i = 0; i < creditosOrdenados.size(); i++) {
            Map<String, String> credito = new HashMap<>();
            credito.put("numeroCredito", String.valueOf(creditosOrdenados.get(i)));
            creditosPadre.put(i + 1, credito);
        }
    }

    public static String limpiarCadenaRegex(String elem, String regex) {
        String all = "";
        Pattern pat = Pattern.compile(regex);
        Matcher m = pat.matcher(elem);

        while (m.find()) {
            all += m.group(0);
        }
        return all;
    }

    public List<Integer> parseWebElementsToListGetText(List<WebElement> list) {
        List<Integer> listString = new ArrayList<>();
        for (WebElement webElement : list) {
            listString.add(Integer.parseInt(webElement.getText()));
        }
        return listString;
    }

    public void validarCabeceraPlanDePagos(
            String validacion,
            String Tasa,
            String Plazo,
            String vg_MontoAprobado,
            String vg_SegundaTasaInteres,
            String vg_PrimaSeguroAnticipada,
            String vg_CuotasPrimaSeguroAnticipada,
            String vg_PrimaNoDevengadaSeguro,
            String vg_PrimaNetaSeguro,
            By keyPage, By valuePage) {

        //MAP Variables Locales
        Map<String, String> ValoresCabeceraPlanDePagos = new HashMap<>();

        // Obtencion de datos - Generacion MAP con datos de la cabecera

        ValoresCabeceraPlanDePagos = obtainValuesMap(keyPage, valuePage);

        for (String key : ValoresCabeceraPlanDePagos.keySet()) {
            log.info(key + " = " + ValoresCabeceraPlanDePagos.get(key));
        }
        log.info("*********** Iniciando la VALIDACION Cabecera Plan De Pagos ***********");

        try {

            String tasaFront = String.format("%.2f", Double.parseDouble(ValoresCabeceraPlanDePagos.get("Tasa inicial del crédito")));
            tasaFront = tasaFront.replace(",", ".");
            log.info("############ tasa  front ############## " + tasaFront);
            assertValidarEqualsImprimeMensaje("Fallo: Validando TASA INICIAL",
                    tasaFront, Tasa);
            assertValidarEqualsImprimeMensaje("Fallo: Validando PLAZO Uno",
                    ValoresCabeceraPlanDePagos.get("Plazo:"), Plazo);
            log.info("### Validando Monto aprobado (capital total cŕedito): ## " + Integer.parseInt(ValoresCabeceraPlanDePagos.get("Monto aprobado (capital total cŕedito):")) + "## vg_MontoAprobado ##" + vg_MontoAprobado);
            ToleranciaPesoMensaje("Validando Monto aprobado (capital total cŕedito):",
                    Integer.parseInt(ValoresCabeceraPlanDePagos.get("Monto aprobado (capital total cŕedito):")),
                    Integer.parseInt(vg_MontoAprobado));
        	/*
        	log.info("Segunda tasa de interés (desde el mes "+(Integer.parseInt(Plazo)+1)+"):");
        	
        	assertValidarEqualsImprimeMensaje("Fallo: Validando Segunda TASA",
        			ValoresCabeceraPlanDePagos.get("Segunda tasa de interés (desde el mes 37):").replace("0", ""), vg_SegundaTasaInteres);
        	*/
        	/*
        	assertValidarEqualsImprimeMensaje("Fallo: Validando Segunda TASA",
        			ValoresCabeceraPlanDePagos.get("Segunda tasa de interés (desde el mes "+(Integer.parseInt(Plazo)+1)+"):").replace("0", ""), vg_SegundaTasaInteres);
        	*/

            ToleranciaPesoMensaje("Validando Prima de seguro anticipada ",
                    Integer.parseInt(ValoresCabeceraPlanDePagos
                            .get("Prima de seguro anticipada a favor de Asegurador (" + vg_CuotasPrimaSeguroAnticipada + " Cuotas anticipadas):")
                            .split(",")[0]), Integer.parseInt(vg_PrimaSeguroAnticipada));


            if (validacion.equals("Retanqueos")) {

                ToleranciaPesoMensaje("Validando Prima No Devengada",
                        Integer.parseInt(ValoresCabeceraPlanDePagos.get("Prima No Devengada de Seguro Crédito Padre:")
                                .split(",")[0]), Integer.parseInt(vg_PrimaNoDevengadaSeguro));
                ToleranciaPesoMensaje("Validando Prima Neta",
                        Integer.parseInt(ValoresCabeceraPlanDePagos.get("Prima Neta de seguro:").split(",")[0]),
                        Integer.parseInt(vg_PrimaNetaSeguro));
            }

            log.info("*********** Datos cabecera Validados -> OK ***********");
            log.info("*********** Tomando Capturas Pantall Datos cabecera Validados ***********");
            //Captura De Pantalla
            adjuntarCaptura("AnalisisCredito - Cabecera Plan de Pagos - Inicio");
            //Hacer Scroll hasta valor cabecera : Plazo
            Hacer_scroll(By.xpath("//div[contains(@id,'form:j_id')]/child::div[@class=\"row\"]/child::div[@class=\"col-xs-6\"][1]/child::label[contains(text(), 'Plazo')]"));
            //Captura De Pantalla
            adjuntarCaptura("AnalisisCredito - Cabecera Plan de Pagos - Final");

        } catch (Exception e) {
            log.error("########## Error - VerificacionCabeceraAnalisisCredito() - validarCabeceraPlanDePagos() ####### : " + e);
            assertTrue("########## Error - BaseTest - validarCabeceraPlanDePagos() ######## : " + e, false);
        }
    }

    public void eliminarReferencias(By locator) throws InterruptedException {
        List<WebElement> listMenosReferencia = driver.findElements(locator);
        List<String> listString = parseWebElementsToList(listMenosReferencia);

        for (int i = 0; i < 2; i++) {
            Hacer_scroll_centrado(By.id(listString.get(i)));
            esperaExplicita(By.id(listString.get(i)));
            hacerClick(By.id(listString.get(i)));
            ElementVisible();
        }
    }

    public void validarTelefonosRefs(String xpathSelects, By labelTelefonos) {
        List<WebElement> listLabelTelefonos = driver.findElements(labelTelefonos);
        List<WebElement> listSelectTelefonos = driver.findElements(By.xpath(xpathSelects));
        List<String> idStringTelefonos = parseWebElementsToList(listSelectTelefonos);
        for (int i = 0; i < listLabelTelefonos.size(); i++) {
            if (listLabelTelefonos.get(i).getText().trim().isEmpty()) {
                String hijos = "//ul[@id = '" + idStringTelefonos.get(i) + "']/child::li";
                seleccionarTelefono(listLabelTelefonos.get(i), hijos);
            }
        }
    }

    public void seleccionarTelefono(WebElement xpathPadre, String xpathHijos) {
        List<WebElement> listHijosSelect = driver.findElements(By.xpath(xpathHijos));

        for (WebElement element : listHijosSelect) {
            if (element.getAttribute("data-label") == null
                    || !element.getAttribute("data-label").equals("&nbsp;")
                    || element.getAttribute("data-label").isEmpty()) {
                xpathPadre.click();
                esperaExplicita(By.xpath(xpathHijos));
                driver.findElement(By.id(element.getAttribute("id"))).click();
            }
        }
    }
    
    public String obtenerTokenDevelopmentExcelCredit() {
    	log.info("########## Obteniendo TOKEN Develop ExcelCredit ##########");
    	
    	String access_token = null;
    	
    	try {
    		String developToken = leerPropiedades("UrlDevelopmentExcelCredit");
    		String grant_type = leerPropiedades("grant_type");
    		String client_id = leerPropiedades("client_id");
    		String client_secret = leerPropiedades("client_secret");
    		
    		
    		RestAssured.baseURI = developToken;
    		RequestSpecification request = RestAssured.given()
    				.contentType("application/x-www-form-urlencoded; charset=utf-8")
    				.formParam("grant_type", grant_type)
    				.formParam("client_id", client_id)
    				.formParam("client_secret", client_secret);
    		
    		Response response = request.post("/token");
    		
    		assertvalidarEquals(String.valueOf(200),String.valueOf(response.getStatusCode()));
    		
    		access_token = response.then().extract().path("access_token");		   		  		
			
		} catch (Exception e) {
			log.error("########## Error - BaseTest - Obtener Token Development ExcelCredit #######" + e);
            assertTrue("########## Error - BaseTest - Obtener Token Development ExcelCredit - consumo de API########" + e,
                    false);
		}
		return access_token;
    }
    
    public String obtenerTokenAPI_notificacion_OPT(String tokenDevEC, String creditoHijo, String idCliente, String idUsuario) {
    	
    	log.info("########## Obteniendo TOKEN API Notificacion OPT ##########");
    	
    	String token_notificacion_OTP = null;
    	
    	try {
			String ulrOnboarding = leerPropiedades("UrlOnboardingOTP");
			RestAssured.baseURI = ulrOnboarding + "creditos/" + creditoHijo + "/prospeccion";
			RequestSpecification request = RestAssured.given();
			
			request.header("Authorization","Bearer "+tokenDevEC);
			request.header("content-type","application/json;charset=UTF-8");
			
			
			JSONObject requestParams = new JSONObject();
			requestParams.put("idCliente", idCliente);
			requestParams.put("idUsuario", idUsuario);
			request.body(requestParams.toJSONString());
			
			Response response = request.post("/notificacion-otp");

			assertvalidarEquals(String.valueOf(200),String.valueOf(response.getStatusCode()));
					
			token_notificacion_OTP = response.then().extract().path("token");
			
		} catch (Exception e) {
			log.error("########## Error - BaseTest - obtener TokenAPI notificacion OPT #######" + e);
            assertTrue("########## Error - BaseTest - obtenerTokenAPI_notificacion_OPT - consumo de API########" + e,
                    false);
		}
    	return token_notificacion_OTP;
    }
    
    public void clickvarioslist(By locator) throws InterruptedException {
        List<WebElement> clickvariosElement = driver.findElements(locator);
        
        for (int i = 0; i < clickvariosElement.size(); i++) {        	
        	clickvariosElement.get(i).click();
        	ElementVisible();        	
         
        }
    }

    public void capturesValoresCondicionesCredito(By locator) throws InterruptedException {
        List<String> posiciones = Arrays.asList("formCondicionCredito:tasaEfectivaAnual:0_clone", "formCondicionCredito:primaNoDevengada:0_clone", "formCondicionCredito:valorFianzaPadres:0_clone");
        List<WebElement> webElements = driver.findElements(locator);
        List<String> valores = parseWebElementsToList(webElements);
        for (String valor : valores) {
            if (posiciones.contains(valor)) {
                Hacer_scroll(By.id(valor));
                adjuntarCaptura("Campos condiciones del crédito");
            }
        }
    }
}
