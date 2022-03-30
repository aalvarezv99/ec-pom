package Acciones.CreditoAccion;

import static org.junit.Assert.assertTrue;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import Acciones.ComunesAccion.LoginAccion;
import Acciones.ComunesAccion.PanelPrincipalAccion;
import Archivo.LeerArchivo;
import CommonFuntions.BaseTest;
import Consultas.OriginacionCreditoQuery;
import JSch.JSchSSHConnection;
import Pages.ComunesPage.PanelNavegacionPage;
import Pages.CreditosPage.PagesClienteParaBienvenida;
import Pages.CreditosPage.PagesClienteParaVisacion;
import Pages.CreditosPage.PagesCreditosDesembolso;
import Pages.CreditosPage.PagesTareas;
import Pages.CreditosPage.SimuladorAsesorPages;
import Pages.SolicitudCreditoPage.CreditoSolicitudPage;
import Pages.SolicitudCreditoPage.PestanaDigitalizacionPage;
import Pages.SolicitudCreditoPage.PestanaFormularioPage;
import Pages.SolicitudCreditoPage.PestanaReferenciacionPage;
import Pages.SolicitudCreditoPage.PestanaSimuladorInternoPage;

public class OriginacionCompraCarteraAccion  extends BaseTest {

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
	double vlrIva = 1.19;
	
	private static Logger log = Logger.getLogger(OriginacionCompraCarteraAccion.class);
	
	public OriginacionCompraCarteraAccion (WebDriver driver) throws InterruptedException {
		///this.driver = driver;
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
	
	public void DatosCartera(String entidad, String cartera, String vlr_cuota, String fecha_vencimiento, String num_obligacion) throws InterruptedException {
		log.info("********** OriginacionCompraCarteraAccion - DatosCartera()**********");
		try {
			recorerpestanas("DIGITALIZACIÓN");
			Hacer_scroll_Abajo(pestanadigitalizacionPage.SegundaPestanaDigitalizacion);
			hacerClick(pestanadigitalizacionPage.SegundaPestanaDigitalizacion);
			esperaExplicita(pestanadigitalizacionPage.AgregarCartera);
			hacerClick(pestanadigitalizacionPage.AgregarCartera);
			ElementVisible();
			esperaExplicita(pestanadigitalizacionPage.Entidad);
			hacerClick(pestanadigitalizacionPage.Entidad);
			EscribirElemento(pestanadigitalizacionPage.FiltroEntidad, entidad);
			EnviarEnter(pestanadigitalizacionPage.FiltroEntidad);
			EscribirElemento(pestanadigitalizacionPage.MontoCartera, cartera);
			EscribirElemento(pestanadigitalizacionPage.ValorCuota, vlr_cuota);
			EscribirElemento(pestanadigitalizacionPage.FechaVencimiento, fecha_vencimiento);
			EscribirElemento(pestanadigitalizacionPage.NumObligacion, num_obligacion);
			ElementVisible();
		} catch (Exception e) {
			log.error("########## Error - OriginacionCompraCarteraAccion - DatosCartera() #######" + e);
			assertTrue("########## Error - OriginacionCompraCarteraAccion - DatosCartera() ########"+ e,false);
		}
				
	}
	
	public void GuardarCartera () throws InterruptedException {
		log.info("******** OriginacionCompraCarteraAccion - GuardarCartera()**********");
		try {
			Hacer_scroll_Abajo(pestanadigitalizacionPage.Guardar);
			hacerClick(pestanadigitalizacionPage.Guardar);
			ElementVisible();
		} catch (Exception e) {
			log.error("########## Error - OriginacionCompraCarteraAccion - GuardarCartera() #######" + e);
			assertTrue("########## Error - OriginacionCompraCarteraAccion - GuardarCartera()########"+ e,false);
		}
				
	}
	
	public void ConfirmarEntidad(String entidad) throws InterruptedException {
		log.info("**************OriginacionCompraCarteraAccion - ConfirmarEntidad() ****************");
		try {
			recorerpestanas("REFERENCIACIÓN");
			esperaExplicita(pestanareferenciacionpage.Aprobar);
			Hacer_scroll_Abajo(pestanareferenciacionpage.Aprobar);
			hacerClick(pestanareferenciacionpage.Entidad);
			EscribirElemento(pestanareferenciacionpage.FiltroEntidad, entidad);
			EnviarEnter(pestanareferenciacionpage.FiltroEntidad);
			ElementVisible();
		} catch (Exception e) {
			log.error("########## Error - OriginacionCompraCarteraAccion - ConfirmarEntidad() #######" + e);
			assertTrue("########## Error - OriginacionCompraCarteraAccion - ConfirmarEntidad()########"+ e,false);
		}
				
	}
	
	public void ConfirmarObligacion(String num_obligacion) {
		log.info("*************** OriginacionCompraCarteraAccion - ConfirmarObligacion()*********");
		try {
			esperaExplicita(pestanareferenciacionpage.Entidad);
			EscribirElemento(pestanareferenciacionpage.NumObligacion, num_obligacion);
			ElementVisible();
		} catch (Exception e) {
			log.error("########## Error - OriginacionCompraCarteraAccion - ConfirmarObligacion() #######" + e);
			assertTrue("########## Error - OriginacionCompraCarteraAccion - ConfirmarObligacion()########"+ e,false);
		}
				
	}
	
	public void AprobarCartera () {
		log.info("*********** OriginacionCompraCarteraAccion - AprobarCartera()********** ");
		try {
			esperaExplicita(pestanareferenciacionpage.Aprobar);
			hacerClick(pestanareferenciacionpage.Aprobar);
			ElementVisible();
		} catch (Exception e) {
			log.error("########## Error - OriginacionCompraCarteraAccion - AprobarCartera() #######" + e);
			assertTrue("########## Error - OriginacionCompraCarteraAccion - AprobarCartera() ########"+ e,false);
		}
			
	}
	
	public void Guardar () {
		log.info("*********** Guardar Referencia, OriginacionCompraCarteraAccion -  Guardar()**********");
		try {
			esperaExplicita(pestanareferenciacionpage.Guardar);
			hacerClick(pestanareferenciacionpage.Guardar);
			ElementVisible();
		} catch (Exception e) {
			log.error("########## Error - OriginacionCompraCarteraAccion -  Guardar()#######" + e);
			assertTrue("########## Error -  OriginacionCompraCarteraAccion -  Guardar()########"+ e,false);
		}
				
	}
	
	public void MarcarCartera() throws InterruptedException {
		log.info("*********** Marcar Cartera Si, OriginacionCompraCarteraAccion -  MarcarCartera()**********");
		try {
			hacerClick(pestanadigitalizacionPage.MarcarCartera);
			hacerClick(pestanadigitalizacionPage.Guardar);
			ElementVisible();
		} catch (Exception e) {
			log.error("########## Error - OriginacionCompraCarteraAccion -  MarcarCartera()#######" + e);
			assertTrue("########## Error -  OriginacionCompraCarteraAccion -  MarcarCartera()########"+ e,false);
		}
		
	}
	
    public void Referenciaspositivas(String codigo) throws InterruptedException {
    	log.info("*********** Marcar Referencias positivas Compra cartera, OriginacionCompraCarteraAccion -  Referenciaspositivas()**********");
    	try {
    		recorerpestanas("DIGITALIZACIÓN");
        	ElementVisible();
    		recorerpestanas("REFERENCIACIÓN");
    		hacerClick(pestanareferenciacionpage.SalarioCheck);
    		ElementVisible();
    		hacerClick(pestanareferenciacionpage.FechaIngreso);
    		ElementVisible();
    		hacerClick(pestanareferenciacionpage.TipoContrato);
    		ElementVisible();
    		hacerClick(pestanareferenciacionpage.CargoCheck);
    		ElementVisible();
    		Hacer_scroll(pestanareferenciacionpage.Guardar);
    		hacerClick(pestanareferenciacionpage.Guardar);
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
    		esperaExplicita(pestanadigitalizacionPage.Titulo);
    		hacerClick(pestanadigitalizacionPage.SegundaPestanaDigitalizacion);	
    		esperaExplicita(pestanadigitalizacionPage.CodigoProforenses);
    	    EscribirElemento(pestanadigitalizacionPage.CodigoProforenses,codigo);
    		ElementVisible();
    		hacerClick(pestanadigitalizacionPage.IdentidadConfirmada);
    		ElementVisible();
    		hacerClick(pestanadigitalizacionPage.Guardar);
    		//hacerClick(pestanadigitalizacionPage.Guardar);
    		ElementVisible();
    		esperaExplicitaNopresente(pestanadigitalizacionPage.Notificacion);
		} catch (Exception e) {
			log.error("########## Error - OriginacionCompraCarteraAccion -  Referenciaspositivas()#######" + e);
			assertTrue("########## Error -  OriginacionCompraCarteraAccion -  Referenciaspositivas()########"+ e,false);
		}
    			
	}

    public void Radicar() throws InterruptedException {	
    	log.info("**********Radicar Compra Cartera, OriginacionCompraCarteraAccion - Radicar() ************");
    	try {
    		hacerClick(pestanadigitalizacionPage.Radicar);
        	ElementVisible();
        	esperaExplicita(pestanadigitalizacionPage.Notificacion);
        	hacerClicknotificacion();
        	hacerClicknotificacion();
        	esperaExplicitaNopresente(pestanadigitalizacionPage.Notificacion);
		} catch (Exception e) {
			log.error("########## Error - OriginacionCompraCarteraAccion - Radicar()#######" + e);
			assertTrue("########## Error -  OriginacionCompraCarteraAccion - Radicar()########"+ e,false);
		}
    	
    	}
    
    public void ReferenciacionSolicitarAnalisis() throws InterruptedException {
    	log.info("***********Presionar boton Analisis, OriginacionCompraCarteraAccion - ReferenciacionSolicitarAnalisis() ****************");
    	try {
    		recorerpestanas("REFERENCIACIÓN");
    		Hacer_scroll(pestanareferenciacionpage.SolicitarAnalisis);
    		hacerClick(pestanareferenciacionpage.SolicitarAnalisis);
    		ElementVisible();
    		esperaExplicita(pestanadigitalizacionPage.Notificacion);
    		hacerClicknotificacion();
    		esperaExplicitaNopresente(pestanadigitalizacionPage.Notificacion);
		} catch (Exception e) {
			log.error("########## Error - OriginacionCompraCarteraAccion - ReferenciacionSolicitarAnalisis()#######" + e);
			assertTrue("########## Error -  OriginacionCompraCarteraAccion - ReferenciacionSolicitarAnalisis()########"+ e,false);
		}
				
	}


/************FIN ACCIONES SOLICITUD CREDITO COMPRA DE CARTERA***************/

/************INICIA ACCIONES ANALISTA DE CREDITO COMPRA CARTERA*************
 * @throws SQLException 
 * @throws NumberFormatException ***************/

   
/************FINALIZA ACCIONES ANALISTA DE CREDITO COMPRA DE CARTERA*************/
   
/************INICIA ACCIONES LLAMADA DE BIENVENIDA DE CREDITO COMPRA DE CARTERA*************/
   
   public void AceptaCondiconesCreditoCompraCartera(String TipoDesen) throws InterruptedException {
  	 recorerpestanas("CONDICIONES DEL CRÉDITO");
  	 Refrescar();
       hacerClick(pagesclienteparabienvenida.AceptaCartera);
       MarcarCheck(pagesclienteparabienvenida.CheckCondicionesCredito);
       Hacer_scroll(pagesclienteparabienvenida.detalledelascarteras);
       Thread.sleep(1000);
       hacerClick(pagesclienteparabienvenida.Desembolso);
       selectValorLista(pagesclienteparabienvenida.ListDesembolso,TipoDesen);  
       hacerClick(pagesclienteparabienvenida.CalificacionProceso);
       hacerClick(pagesclienteparabienvenida.CalificacionCobro);
       hacerScrollAbajo();
       hacerClick(pagesclienteparabienvenida.Acepta);
       ElementVisible(); 
  }
   
/************FINALIZA ACCIONES LLAMADA DE BIENVENIDA DE CREDITO COMPRA DE CARTERA*************/
   
/************INICIA ACCIONES DESEMBOLSO CARTERA /
 * @throws InterruptedException*************/
   
  public void ProcesarCartera (String Cedula) throws InterruptedException {
	panelnavegacionaccion.CreditoParaDesembolso();
  	ElementVisible();     	
  	esperaExplicita(PagesCreditosDesembolso.filtrocedula);
  	EscribirElemento(PagesCreditosDesembolso.filtrocedula,Cedula);
  	ElementVisible();
  	Hacer_scroll(pagescreditosdesembolso.FiltroEstadoPago);
  	hacerClick(PagesCreditosDesembolso.FiltroTipoOperacion);
  	hacerClick(pagescreditosdesembolso.TipoOperacionCompraCartera);
  	Hacer_scroll(pagescreditosdesembolso.CheckProcesarPagos);
  	ElementVisible();
  	hacerClick(pagescreditosdesembolso.CheckProcesarPagos);
  	ElementVisible();
  	hacerClick(pagescreditosdesembolso.ProcesarPagos);
  	ElementVisible(); 
  }
   
  //ThainerPerez 17/nov/2021 - Se comentarea este metodo aparentemente no se usa se puede eliminar si han pasado dos meses y no explota nada
 /* public void DescargarMediosDispercionCartera(String Monto, String Banco, String Pdf) throws InterruptedException {
  	panelnavegacionaccion.CreditoParaDesembolsoDescargar();
  	esperaExplicita(PagesCreditosDesembolso.FiltroMonto);
  	EscribirElemento(PagesCreditosDesembolso.FiltroMonto,Monto);
  	Thread.sleep(4000);
  	
  	
  	String pattern = "###,###,###.###";
      double value = Double.parseDouble(Monto);

      DecimalFormat myFormatter = new DecimalFormat(pattern);
      myFormatter = new DecimalFormat(pattern,DecimalFormatSymbols.getInstance(Locale.GERMANY));
      String output = myFormatter.format(value);    	
  	esperaExplicita(By.xpath("//td[text()='"+output+"']"));
  	hacerClick(PagesCreditosDesembolso.VerEditar);
  	ElementVisible(); 
  	hacerClick(PagesCreditosDesembolso.Banco);
  	hacerClick(By.xpath("//li[starts-with(@id,'formLote:j_idt89') and text()='"+Banco+"' ]"));    	
  	ElementVisible(); 
  	cargarpdf(PagesCreditosDesembolso.CargarEvidencia,Pdf);
  	esperaExplicita(PagesCreditosDesembolso.VerEvidencias);
  	ElementVisible(); 
  	hacerClick(PagesCreditosDesembolso.CrearArchivo);
  	esperaExplicita(PagesCreditosDesembolso.ArchivoCreado);
  	ElementVisible(); 
  	hacerClick(PagesCreditosDesembolso.Guardar);
  	ElementVisible();    	
  }
  
  
  public void VisacionCartera (String Pdf) throws InterruptedException {
	  recorerpestanas("CARTERAS Y SANEAMIENTOS");
	  Hacer_scroll(pagesclienteparavisacion.PazYSalvoCartera);
	  cargarpdf(pagesclienteparavisacion.PazYSalvoCartera,Pdf);
	  ElementVisible();
  }*/
  
  /************FINALIZA ACCIONES VISACION CARTERA *************/ 
  
  /************INICIA ACCIONES DESEMBOLSO REMANENTE 
 * @throws InterruptedException *************/
  
  public void DescargarMediosDispercionRemanente(String Retanqueo, String cartera, String Banco, String Pdf) throws InterruptedException {
	  	panelnavegacionaccion.CreditoParaDesembolsoDescargar();
	  	esperaExplicita(PagesCreditosDesembolso.FiltroMonto);
	  	
	  	int Gmf4100 = (Integer.parseInt(cartera))*4/1000;
	  	int Remanente =  (Integer.parseInt(Retanqueo)) - (Integer.parseInt(cartera))- (int) (Gmf4100) ;
	  	EscribirElemento(PagesCreditosDesembolso.FiltroMonto,String.valueOf(Remanente));
	  	Thread.sleep(4000);
	  	ElementVisible(); 	  	
	  	String pattern = "###,###,###.###";
	      int value = Remanente;

	      DecimalFormat myFormatter = new DecimalFormat(pattern);
	      myFormatter = new DecimalFormat(pattern,DecimalFormatSymbols.getInstance(Locale.GERMANY));
	      String output = myFormatter.format(value);    	
	  	esperaExplicita(By.xpath("//td[text()='"+output+"']"));
	  	hacerClick(PagesCreditosDesembolso.VerEditar);
	  	ElementVisible(); 
	  	hacerClick(PagesCreditosDesembolso.Banco);
	  	hacerClick(By.xpath("//li[starts-with(@id,'formLote:j_idt89') and text()='"+Banco+"' ]"));    	
	  	ElementVisible(); 
	  	cargarpdf(PagesCreditosDesembolso.CargarEvidencia,Pdf);
	  	esperaExplicita(PagesCreditosDesembolso.VerEvidencias);
	  	ElementVisible(); 
	  	hacerClick(PagesCreditosDesembolso.CrearArchivo);
	  	esperaExplicita(PagesCreditosDesembolso.ArchivoCreado);
	  	ElementVisible(); 
	  	hacerClick(PagesCreditosDesembolso.Guardar);
	  	ElementVisible();    	
	  }


}