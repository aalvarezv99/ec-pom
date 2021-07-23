package Acciones;

import static org.junit.Assert.assertTrue;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

import Archivo.LeerArchivo;
import CommonFuntions.BaseTest;
import Consultas.CertificacionSaldoQuery;
import Consultas.OriginacionCreditoQuery;
import JSch.JSchSSHConnection;
import Pages.CreditoSolicitudPage;
import Pages.PagesClienteParaBienvenida;
import Pages.PagesClienteParaVisacion;
import Pages.PagesCreditosDesembolso;
import Pages.PagesTareas;
import Pages.PanelNavegacionPage;
import Pages.RetanqueoPages;
import Pages.SimuladorAsesorPages;
import Pages.SolicitudCreditoPage.PestanaDigitalizacionPage;
import Pages.SolicitudCreditoPage.PestanaFormularioPage;
import Pages.SolicitudCreditoPage.PestanaReferenciacionPage;
import Pages.SolicitudCreditoPage.PestanaSimuladorInternoPage;

public class RetanqueoCreditos extends BaseTest {

	WebDriver driver;

	PanelPrincipalAccion panelnavegacionaccion;
	PanelNavegacionPage PanelNavegacionPage;
	RetanqueoPages retanqueopages;
	PestanaSimuladorInternoPage pestanasimuladorinternopage;
	PestanaDigitalizacionPage pestanadigitalizacionPage;
	PestanaReferenciacionPage pestanareferenciacionpage;
	PagesClienteParaBienvenida pagesclienteparabienvenida;
	Pages.SolicitudCreditoPage.pestanaSeguridadPage pestanaSeguridadPage;

	LoginAccion loginaccion;
	LeerArchivo archivo;
	// BaseTest baseTest;
	private static Logger log = Logger.getLogger(OriginacionCreditosAccion.class);
	static int Monto;
	static int Remanente;
	static int SaldoAlDia;
	static int VlrRetanqueo;

	public RetanqueoCreditos(WebDriver driver) {
		// this.driver = driver;
		super(driver);
		// baseTest = new BaseTest(driver);
		panelnavegacionaccion = new PanelPrincipalAccion(driver);
		PanelNavegacionPage = new PanelNavegacionPage(driver);
		retanqueopages = new RetanqueoPages(driver);
		loginaccion = new LoginAccion(driver);
		pestanasimuladorinternopage = new PestanaSimuladorInternoPage(driver);
		pestanadigitalizacionPage = new PestanaDigitalizacionPage(driver);
		pestanareferenciacionpage = new PestanaReferenciacionPage(driver);
		pagesclienteparabienvenida = new PagesClienteParaBienvenida(driver);
		pestanaSeguridadPage = new Pages.SolicitudCreditoPage.pestanaSeguridadPage(driver);
		archivo = new LeerArchivo();
	}

	/************ INICIO ACCIONES RETANQUEO CREDITOS ***************/
	public void iniciarSesion() {
		// loginaccion.iniciarSesion();
	}

	public void ingresarRetanqueoAsesor() {
		panelnavegacionaccion.Retanqueo();
		
	}

	public void FiltrarCredito(String Cedula, String Credito) throws InterruptedException {
		BuscarenGrilla(retanqueopages.cedula, Cedula);
		Thread.sleep(5000);
		ElementVisible();
		BuscarenGrilla(retanqueopages.credito, Credito);
		Thread.sleep(5000);
		ElementVisible();
		esperaExplicitaTexto(Credito);

	}

	public void Retanquear() throws InterruptedException {
		ElementVisible();
		hacerClick(retanqueopages.BtnRentaqueo);
		Thread.sleep(5000);
		ElementVisible();
		assertTextonotificacion(retanqueopages.notificacion, "Se ha generado un retanqueo para el");
	}

	public void Credito(String Cedula) throws InterruptedException {
		panelnavegacionaccion.navegarCreditoSolicitud();
		BuscarenGrilla(retanqueopages.inputCedula, Cedula);
		ElementVisible();
		esperaExplicitaTexto(Cedula);
	}

	public void seleccionarRetanqueo() {
		ClicUltimoElemento(retanqueopages.continuar);
		ElementVisible();
	}

	public void borrarArchivos() throws InterruptedException {
		clickvariosespera(retanqueopages.borrararchivos);
	}

	public void CargarArchivos(String pdf) throws InterruptedException {
		cargarpdf(retanqueopages.ImputAutorizacion, pdf);
		esperaExplicitaNopresente(retanqueopages.BarraCarga);
		hacerClickVariasNotificaciones();
		cargarpdf(retanqueopages.ImputCedula, pdf);
		esperaExplicitaNopresente(retanqueopages.BarraCarga);
		hacerClickVariasNotificaciones();
		cargarpdf(retanqueopages.ImputDesprendibleNomina, pdf);
		esperaExplicitaNopresente(retanqueopages.BarraCarga);
		hacerClickVariasNotificaciones();
		cargarpdf(retanqueopages.ImputOtros, pdf);
		esperaExplicitaNopresente(retanqueopages.BarraCarga);
		hacerClickVariasNotificaciones();

	}

	public void ConsultaCentrales() throws InterruptedException {
		ElementVisible();
		hacerClick(retanqueopages.BtnConsultaCentrales);
		ElementVisible();
		// assertTextonotificacion(retanqueopages.notificacion,"Se ha solicitado la
		// consulta en listas");
		Thread.sleep(5000);
	}

	public void Seguridad() throws InterruptedException {
		recorerpestanas("SEGURIDAD");
		hacerClick(retanqueopages.Viable);
		hacerClick(retanqueopages.Guardar);
		ElementVisible();
		esperaExplicita(retanqueopages.Concepto);
		esperaExplicitaSeguridad(pestanaSeguridadPage.BtnCheck);
		Refrescar();
		esperaExplicita(pestanaSeguridadPage.Concepto);
		Hacer_scroll(pestanaSeguridadPage.Concepto);
		esperaExplicitaSeguridad(pestanaSeguridadPage.BtnCheck);
		Thread.sleep(5000);
	}

	public void Simulador(String Retanqueo,String Tasa, String Plazo, String DiasHabilesIntereses, String Ingresos, String descLey,
			String descNomina) {
		recorerpestanas("SIMULADOR");
		VlrRetanqueo = Integer.parseInt(Retanqueo);
		LimpiarConTeclado(retanqueopages.inputTasa);
		EscribirElemento(retanqueopages.inputTasa, Tasa);

		hacerClicknotificacion();
		ElementVisible();
		hacerClick(retanqueopages.inputPlazo);
		ElementVisible();
		LimpiarConTeclado(retanqueopages.inputPlazo);
		EscribirElemento(retanqueopages.inputPlazo, Plazo);
		ElementVisible();
		hacerClick(retanqueopages.inputMonto);
		ElementVisible();
		SaldoAlDia = Integer.parseInt(TextoElemento(retanqueopages.MontoSolicitar));
		Monto = Integer.parseInt(TextoElemento(retanqueopages.MontoSolicitar)) + VlrRetanqueo;
		Remanente = Integer.parseInt(TextoElemento(pestanasimuladorinternopage.RemanenteEstimado));
		LimpiarConTeclado(retanqueopages.inputMonto);
		EscribirElemento(retanqueopages.inputMonto, String.valueOf(Monto));
		ElementVisible();
		hacerClick(retanqueopages.diasIntInicial);
		ElementVisible();
		LimpiarConTeclado(retanqueopages.diasIntInicial);
		EscribirElemento(retanqueopages.diasIntInicial, DiasHabilesIntereses);
		ElementVisible();
		hacerClick(retanqueopages.inputIngresos);
		ElementVisible();
		LimpiarConTeclado(retanqueopages.inputIngresos);
		EscribirElemento(retanqueopages.inputIngresos, Ingresos);
		ElementVisible();
		hacerClick(retanqueopages.inputDescLey);
		ElementVisible();
		LimpiarConTeclado(retanqueopages.inputDescLey);
		EscribirElemento(retanqueopages.inputDescLey, descLey);
		ElementVisible();
		hacerClick(retanqueopages.inputdDescNomina);
		ElementVisible();
		LimpiarConTeclado(retanqueopages.inputdDescNomina);
		EscribirElemento(retanqueopages.inputdDescNomina, descNomina);
		ElementVisible();
		hacerClick(retanqueopages.vlrCompra);
		ElementVisible();

	}

	public void ValidarSimulador(String Ingresos, String descLey, String descNomina, String colchon, String Tasa,
			String Plazo) throws NumberFormatException, SQLException {

		// Valores para la funciones estaticos
		int Tasaxmillonseguro = 4625;
		double variableFianza = 1.19;

		// consulta base de datos
		int DesPrimaAntic = 0;
		OriginacionCreditoQuery query = new OriginacionCreditoQuery();
		ResultSet resultado = query.ConsultaDescuentoPrimaAntic();
		while (resultado.next()) {
			DesPrimaAntic = Integer.parseInt(resultado.getString(1));
		}

	}

	public void SolicitarCredito() throws InterruptedException {
		Hacer_scroll_Abajo(pestanasimuladorinternopage.Solicitar);
		hacerClick(pestanasimuladorinternopage.Solicitar);
		ElementVisible();
		hacerClickVariasNotificaciones();
	}

	public void Confirmaidentidad(String codigo) {
		recorerpestanas("DIGITALIZACIÓN");
		esperaExplicita(pestanadigitalizacionPage.SegundaPestanaDigitalizacion);
		hacerClick(pestanadigitalizacionPage.SegundaPestanaDigitalizacion);
		esperaExplicita(pestanadigitalizacionPage.CodigoProforenses);
		EscribirElemento(pestanadigitalizacionPage.CodigoProforenses, codigo);
		ElementVisible();
		hacerClick(pestanadigitalizacionPage.IdentidadConfirmada);
		ElementVisible();
		hacerClick(pestanadigitalizacionPage.Guardar);
		ElementVisible();
		esperaExplicitaNopresente(pestanadigitalizacionPage.Notificacion);
	}

	public void AprobarReferenciasPagaduria() {
		recorerpestanas("REFERENCIACIÓN");
		hacerClick(pestanareferenciacionpage.SalarioCheck);
		ElementVisible();
		hacerClick(pestanareferenciacionpage.FechaIngreso);
		ElementVisible();
		hacerClick(pestanareferenciacionpage.TipoContrato);
		ElementVisible();
		hacerClick(pestanareferenciacionpage.CargoCheck);
		ElementVisible();
	}

	public void ValidarSimuladorAnalistaRetanqueos(String retanqueo,String fecha,String Mes, String Plazo,String Ingresos, String descLey, String descNomina) throws InterruptedException{
    	esperaExplicita(pestanasimuladorinternopage.MesDeAfecatcion);
    	hacerClick(pestanasimuladorinternopage.FechaDesembolso);
    	Clear(pestanasimuladorinternopage.FechaDesembolso);
    	EscribirElemento(pestanasimuladorinternopage.FechaDesembolso, fecha);  
    	EnviarEnter(pestanasimuladorinternopage.FechaDesembolso);
    	hacerClick(pestanasimuladorinternopage.MesDeAfecatcion);    	
		ElementVisible();
		selectValorLista(pestanasimuladorinternopage.ListaMes, Mes);
		ElementVisible();
		hacerClick(pestanasimuladorinternopage.FechasManuales);
		ElementVisible();
		hacerClick(pestanasimuladorinternopage.CalcularDesglose);
		ElementVisible();
		hacerClicknotificacion();
		esperaExplicitaNopresente(pestanadigitalizacionPage.Notificacion);
		ToleranciaPeso(Integer.parseInt(TextoElemento(pestanasimuladorinternopage.ValoraDesembolsar)),(Integer.parseInt(retanqueo)));
    	/*int Tolerancia=(Integer.parseInt(TextoElemento(pestanasimuladorinternopage.ValoraDesembolsar))-(Integer.parseInt(retanqueo)));
        if(Tolerancia<=1 && Tolerancia>=0){
    		assertTrue(true);
    	}else {
    		assertTrue(false);
    	}*/
    }

	public void DescargarMediosdedispercionRetanqueo(String Retanqueo,String Banco, String Pdf) {

		Monto = (Integer.parseInt(Retanqueo));
		panelnavegacionaccion.CreditoParaDesembolsoDescargar();
		esperaExplicita(PagesCreditosDesembolso.FiltroMonto);
		EscribirElemento(PagesCreditosDesembolso.FiltroMonto, String.valueOf(Monto));
		ElementVisible();

		String pattern = "###,###,###.###";
		double value = Double.parseDouble(String.valueOf(Monto));

		DecimalFormat myFormatter = new DecimalFormat(pattern);
		myFormatter = new DecimalFormat(pattern, DecimalFormatSymbols.getInstance(Locale.GERMANY));
		String output = myFormatter.format(value);
		esperaExplicita(By.xpath("//td[text()='" + output + "']"));
		hacerClick(PagesCreditosDesembolso.VerEditar);
		ElementVisible();
		hacerClick(PagesCreditosDesembolso.Banco);
		hacerClick(By.xpath("//li[starts-with(@id,'formLote:j_idt89') and text()='" + Banco + "' ]"));
		ElementVisible();
		cargarpdf(PagesCreditosDesembolso.CargarEvidencia, Pdf);
		esperaExplicita(PagesCreditosDesembolso.VerEvidencias);
		ElementVisible();
		hacerClick(PagesCreditosDesembolso.CrearArchivo);
		esperaExplicita(PagesCreditosDesembolso.ArchivoCreado);
		ElementVisible();
		hacerClick(PagesCreditosDesembolso.Guardar);
		ElementVisible();
	}

	/************ FIN RETANQUEO CREDITOS **********/
}
