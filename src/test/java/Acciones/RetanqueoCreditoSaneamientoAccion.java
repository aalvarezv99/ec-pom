package Acciones;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import Archivo.LeerArchivo;
import CommonFuntions.BaseTest;
import JSch.JSchSSHConnection;
import Pages.CreditoSolicitudPage;
import Pages.PagesClienteParaBienvenida;
import Pages.PagesClienteParaVisacion;
import Pages.PagesCreditosDesembolso;
import Pages.PagesTareas;
import Pages.PanelNavegacionPage;
import Pages.SimuladorAsesorPages;
import Pages.SolicitudCreditoPage.PestanaDigitalizacionPage;
import Pages.SolicitudCreditoPage.PestanaFormularioPage;
import Pages.SolicitudCreditoPage.PestanaReferenciacionPage;
import Pages.SolicitudCreditoPage.PestanaSimuladorInternoPage;

public class RetanqueoCreditoSaneamientoAccion extends BaseTest {

	WebDriver driver;
	SimuladorAsesorPages simuladorasesorpage;
	PanelPrincipalAccion panelnavegacionaccion;
	PanelNavegacionPage PanelNavegacionPage;
	CreditoSolicitudPage creditocolicitudpage;
	PestanaSimuladorInternoPage pestanasimuladorinternopage;
	Pages.SolicitudCreditoPage.pestanaSeguridadPage pestanaSeguridadPage;
	PestanaDigitalizacionPage pestanadigitalizacionPage;
	PestanaFormularioPage pestanaformulariopage;
	PestanaReferenciacionPage pestanareferenciacionpage;
	PagesTareas pagestareas;
	PagesClienteParaBienvenida pagesclienteparabienvenida;
	PagesClienteParaVisacion pagesclienteparavisacion;
	JSchSSHConnection jSchSSHConnection;
	PagesCreditosDesembolso pagescreditosdesembolso;
	
	LoginAccion loginaccion;
	LeerArchivo archivo;
	private static Logger log = Logger.getLogger(OriginacionCreditosAccion.class);

	public RetanqueoCreditoSaneamientoAccion(WebDriver driver) throws InterruptedException {
	//this.driver = driver;
	super(driver);
	// baseTest = new BaseTest(driver);
	simuladorasesorpage = new SimuladorAsesorPages(driver);
	panelnavegacionaccion = new PanelPrincipalAccion(driver);
	PanelNavegacionPage = new PanelNavegacionPage(driver);
	creditocolicitudpage = new CreditoSolicitudPage(driver);
	pestanaSeguridadPage = new Pages.SolicitudCreditoPage.pestanaSeguridadPage(driver);
	pestanasimuladorinternopage = new PestanaSimuladorInternoPage(driver);
	pestanadigitalizacionPage = new PestanaDigitalizacionPage(driver);
	pestanaformulariopage = new PestanaFormularioPage(driver);
	pestanareferenciacionpage = new PestanaReferenciacionPage(driver);
	pagesclienteparavisacion = new PagesClienteParaVisacion(driver);
	pagestareas = new PagesTareas(driver);
	loginaccion = new LoginAccion(driver);
	pagesclienteparabienvenida = new PagesClienteParaBienvenida(driver);
	pagescreditosdesembolso = new PagesCreditosDesembolso(driver);
	jSchSSHConnection = new JSchSSHConnection();
	archivo = new LeerArchivo();
	}
	
	/********* INICIO ACCIONES RETANQUEO CREDITO SANEAMIENTO *****
	********************** @throws InterruptedException ***************/
		
	public void DatosCarteraSaneamiento( String Competidor, String Saneamiento, String VlrCuota, String FechaVencimiento, String NumObligacion) throws InterruptedException {
		recorerpestanas("DIGITALIZACIÓN");
		Hacer_scroll_Abajo(pestanadigitalizacionPage.SegundaPestanaDigitalizacion);
		hacerClick(pestanadigitalizacionPage.SegundaPestanaDigitalizacion);
		esperaExplicita(pestanadigitalizacionPage.AgregarCartera);
		hacerClick(pestanadigitalizacionPage.AgregarCartera);
		ElementVisible();
		Thread.sleep(1000);
		//Diligenciar datos Saneamiento
		esperaExplicita(pestanadigitalizacionPage.RadioSaneamiento);
		hacerClick(pestanadigitalizacionPage.RadioSaneamiento);
		hacerClick(pestanadigitalizacionPage.EntidadCompetidor);
		EscribirElemento(pestanadigitalizacionPage.FiltroLista, Competidor);
		EnviarEnter(pestanadigitalizacionPage.FiltroLista);
		EscribirElemento(pestanadigitalizacionPage.FechaVencimientoSaneamiento, FechaVencimiento);
		EnviarEnter(pestanadigitalizacionPage.FechaVencimientoSaneamiento);
		EscribirElemento(pestanadigitalizacionPage.MontoSaneamiento, Saneamiento);
		EscribirElemento(pestanadigitalizacionPage.ValorCuotaSaneamiento, VlrCuota);
		EscribirElemento(pestanadigitalizacionPage.NumeroObligacionSaneamiento, NumObligacion);
		ElementVisible();
	}
	
	public void ConfirmarEntidad(String Competidor, String Saneamineto, String VlrCuota, String FechaVencimiento, String NumObligacion) throws InterruptedException {
		recorerpestanas("REFERENCIACIÓN");
		hacerClick(pestanareferenciacionpage.SalarioCheck);
		ElementVisible();
		hacerClick(pestanareferenciacionpage.FechaIngreso);
		ElementVisible();
		hacerClick(pestanareferenciacionpage.TipoContrato);
		ElementVisible();
		hacerClick(pestanareferenciacionpage.CargoCheck);
		ElementVisible();
		
		String ListCompetidores[]= {Competidor};
		String ListMonto[]= {Saneamineto};
		String ListCuota[]= {VlrCuota};
		String ListFecha[]= {FechaVencimiento};
		String ListVlrObligacion[]= {NumObligacion};
		ClickBtnMultiplesBackup(
				pestanareferenciacionpage.ListLabelEntidad,pestanareferenciacionpage.ListFiltroEntidad,
				pestanareferenciacionpage.ListMonto,pestanareferenciacionpage.ListValorCuota,
				pestanareferenciacionpage.ListFecha,pestanareferenciacionpage.ListNumObligacion,pestanareferenciacionpage.ListRadioSaneamiento,
				pestanareferenciacionpage.ListBtnAprobar,
				ListCompetidores,ListMonto,ListCuota,
				ListFecha,ListVlrObligacion);
		
		hacerClicknotificacion();
		hacerClick(pestanareferenciacionpage.GuardarReferencias);
		ElementVisible();
	}
public void ReferenciasPositivasRetanqueoSaneamiento(String Codigo) throws InterruptedException {
    	
    	ElementVisible();
    	esperaExplicitaNopresente(pestanadigitalizacionPage.Notificacion);
		clickvarios(pestanareferenciacionpage.ReferenciaPositiva);
		ElementVisible();
		Hacer_scroll(pestanareferenciacionpage.Titulo);
		clickvarios(pestanareferenciacionpage.CheckSI);
		Hacer_scroll(pestanareferenciacionpage.GuardarReferencias);
		hacerClick(pestanareferenciacionpage.GuardarReferencias);
		ElementVisible();
		esperaExplicita(pestanadigitalizacionPage.Notificacion);
		hacerClicknotificacion();		
		esperaExplicitaNopresente(pestanadigitalizacionPage.Notificacion);   	
		recorerpestanas("DIGITALIZACIÓN");
		Hacer_scroll_Abajo(pestanadigitalizacionPage.SegundaPestanaDigitalizacion);
		hacerClick(pestanadigitalizacionPage.SegundaPestanaDigitalizacion);
		esperaExplicita(pestanadigitalizacionPage.CodigoProforenses);
		Hacer_scroll_Abajo(pestanadigitalizacionPage.CodigoProforenses);
    	EscribirElemento(pestanadigitalizacionPage.CodigoProforenses, Codigo);
    	hacerClick(pestanadigitalizacionPage.MarcarCartera1);
    	hacerClick(pestanadigitalizacionPage.IdentidadConfirmada);
    	ElementVisible();
    	hacerClick(pestanadigitalizacionPage.BotonGuardarCartera);
    	ElementVisible();
    	esperaExplicitaNopresente(pestanadigitalizacionPage.Notificacion);
    }
/************FIN ACCIONES RETANQUEO CREDITO CON SANEAMIENTO ***************/
/************ INICIA ACCIONES ANALISTA ***************/
public void ValidarSimuladorAnalistaRetanqueosCarteraSaneamiento(String retanqueo,String fecha,String Mes, String Plazo,String Ingresos, String descLey, String descNomina, String Saneamiento) throws InterruptedException{
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
	int Gmf4100 = (int) Gmf4100(Integer.parseInt(Saneamiento), 0.004);
	int DescuentosPorSaneamiento = ((Gmf4100 + Integer.parseInt(Saneamiento)));		
		
	//ToleranciaPeso(Integer.parseInt(TextoElemento(pestanasimuladorinternopage.ValoraDesembolsar)),(Integer.parseInt(retanqueo)-DescuentosPorCartera));
	
		if (TextoElemento(pestanasimuladorinternopage.ValoraDesembolsar).contains(".")==true) {
		ToleranciaPeso(Integer.parseInt(TextoElemento(pestanasimuladorinternopage.ValoraDesembolsar).substring(0,TextoElemento(pestanasimuladorinternopage.ValoraDesembolsar).length()-2).replaceAll("[^a-zA-Z0-9]", "")),(Integer.parseInt(retanqueo)-DescuentosPorSaneamiento));
		System.out.println("dentro del if que contiene punto");
		}
		else {
		ToleranciaPeso(Integer.parseInt(TextoElemento(pestanasimuladorinternopage.ValoraDesembolsar)),(Integer.parseInt(retanqueo)-DescuentosPorSaneamiento));
		System.out.println("dentro del else que no contiene punto");
		}
	}
/************ FINALIZA ACCIONES ANALISTA ***************/
/************INICIA ACCIONES LLAMADA DE BIENVENIDA DE CREDITO COMPRA DE CARTERA*************/

public void AceptarcondiconesdelcreditoRetanqueoSaneamiento(String TipoDesen) throws InterruptedException {
	 recorerpestanas("CONDICIONES DEL CRÉDITO");
	 Refrescar();
    hacerClick(pagesclienteparabienvenida.AceptarCartera);
   // hacerClick(pagesclienteparabienvenida.AceptarSaneamiento);
    MarcarCheck(pagesclienteparabienvenida.CheckCondicionesCredito);
    Hacer_scroll(pagesclienteparabienvenida.detalledelascarteras);
    Thread.sleep(3000);
    hacerClick(pagesclienteparabienvenida.Desembolso);
    selectValorLista(pagesclienteparabienvenida.ListDesembolso,TipoDesen);  
    hacerClick(pagesclienteparabienvenida.CalificacionProceso);
    hacerClick(pagesclienteparabienvenida.CalificacionCobro);
    hacerScrollAbajo();
    hacerClick(pagesclienteparabienvenida.Acepta);
    ElementVisible(); 
}
/************FINALIZA ACCIONES LLAMADA DE BIENVENIDA DE CREDITO COMPRA DE CARTERA*************/

/************INICIA ACCIONES DESEMBOLSO CARTERA */

}
