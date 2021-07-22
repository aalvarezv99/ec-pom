package Acciones;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

import Archivo.LeerArchivo;
import CommonFuntions.BaseTest;
import Consultas.OriginacionCreditoQuery;
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

public class OriginacionCreditoSaneamientoAccion extends BaseTest {

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
	private static Logger log = Logger.getLogger(OriginacionCreditoSaneamientoAccion.class);

	public OriginacionCreditoSaneamientoAccion(WebDriver driver) {
		super(driver);
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
	

	/************ INICIO ACCIONES PARA SOLICITUD DE CREDITO SANEAMIENTO ***************/
	
	public void DatosSaneamiento(String entidad_san, String saneamiento, String vlr_cuota_san, String fecha_vencimiento, String num_obligacion_San) throws InterruptedException {
		recorerpestanas("DIGITALIZACIÓN");
		Hacer_scroll(pestanadigitalizacionPage.SegundaPestanaDigitalizacion);
		hacerClick(pestanadigitalizacionPage.SegundaPestanaDigitalizacion);
		esperaExplicita(pestanadigitalizacionPage.AgregarCartera);
		hacerClick(pestanadigitalizacionPage.AgregarCartera);
		ElementVisible();
		esperaExplicita(pestanadigitalizacionPage.Entidad);
		hacerClick(pestanadigitalizacionPage.Entidad);
		EscribirElemento(pestanadigitalizacionPage.FiltroEntidad, entidad_san);
		EnviarEnter(pestanadigitalizacionPage.FiltroEntidad);
		EscribirElemento(pestanadigitalizacionPage.MontoCartera, saneamiento);
		EscribirElemento(pestanadigitalizacionPage.ValorCuota, vlr_cuota_san);
		EscribirElemento(pestanadigitalizacionPage.FechaVencimiento, fecha_vencimiento);
		EscribirElemento(pestanadigitalizacionPage.NumObligacion, num_obligacion_San);
		ElementVisible();		
	}
	
	public void SeleccionSaneamiento() {
		hacerClick(pestanadigitalizacionPage.SeleccionSaneamiento);
	}
	
	public void GuardarSaneamiento()  {
		//hacerScrollAbajo(pestanadigitalizacionPage.Guardar);
		hacerClick(pestanadigitalizacionPage.Guardar);
		ElementVisible();	
	}
		
	/*public void ConfirmarObligacionSaneamiento(String num_obligacion) {
		esperaExplicita(pestanareferenciacionpage.Entidad);
		EscribirElemento(pestanareferenciacionpage.NumObligacion, num_obligacion);
		ElementVisible();		
	}*/
	
	public void AprobarSaneamiento () {
		hacerClick(pestanareferenciacionpage.Aprobar);
		ElementVisible();	
	}
	

	
	/*public void Referenciaspositivas(String codigo) throws InterruptedException {
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
	}*/
	
	public void MarcarSaneamiento () throws InterruptedException {
		hacerClick(pestanadigitalizacionPage.MarcarCartera);
		hacerClick(pestanadigitalizacionPage.Guardar);
		ElementVisible();
	}

/************FIN ACCIONES SOLICITUD CREDITO SANEAMIENTO***************/
    
/************INICIA ACCIONES ANALISTA DE CREDITO SANEAMIENTO*************
     * @throws SQLException 
     * @throws NumberFormatException ***************/

       public void ValidarSimuladorAnalistaSaneamiento(String Mes, String Monto,String Tasa,String Plazo, String Ingresos, String descLey, String descNomina, String Pagaduria, String saneamiento) throws NumberFormatException, SQLException {
    	   esperaExplicita(pestanasimuladorinternopage.MesDeAfecatcion);
       	hacerClick(pestanasimuladorinternopage.MesDeAfecatcion);
       	ElementVisible(); 
       	selectValorLista(pestanasimuladorinternopage.ListaMes,Mes);
       	ElementVisible(); 
       	hacerClick(pestanasimuladorinternopage.CalcularDesglose);
       	ElementVisible(); 
       	hacerClicknotificacion();
       	esperaExplicitaNopresente(pestanadigitalizacionPage.Notificacion);
       	
       	 // consulta base de datos
    		int DesPrimaAntic = 0;
    		OriginacionCreditoQuery query = new OriginacionCreditoQuery();
    		ResultSet resultado = query.ConsultaDescuentoPrimaAntic();
    		while (resultado.next()) {
    			DesPrimaAntic = Integer.parseInt(resultado.getString(1));
    		}

    		double EstudioCredito = 0;
    		ResultSet resultado2 = query.EstudioCredito();
    		while (resultado2.next()) {
    			EstudioCredito = Double.parseDouble(resultado2.getString(1));
    		}
    		
    		int colchon = 0;
    		ResultSet resultadocolchon = query.colchonpagaduria(Pagaduria);
    		while (resultadocolchon.next()) {
    			colchon = Integer.parseInt(resultadocolchon.getString(1));
    		}
    		
    		double TasaFianza =0;
    		ResultSet resultado3 = query.porcentajefianza();
    		while (resultado3.next()) {
    			TasaFianza = Double.parseDouble(resultado3.getString(1));
    		}
    		
          // Valores para la funciones estaticos
    		int Tasaxmillonseguro = 4625;
    		double variableFianza = 1.19;

    		// Validar resultados de simulacion

    		assertvalidarEquals(TextoElemento(pestanasimuladorinternopage.MontoSolicitado),Monto);
    		
    		int Capacidad = (int) CapacidadPagaduria(Integer.parseInt(Ingresos), Integer.parseInt(descLey),Integer.parseInt(descNomina), colchon);
    		assertvalidarEquals(TextoElemento(pestanasimuladorinternopage.CapacidadAsesor), String.valueOf(Capacidad));

    		int calculoMontoSoli = (int) MontoaSolicitar(Integer.parseInt(Monto), DesPrimaAntic, Tasaxmillonseguro);
    		assertvalidarEquals(TextoElemento(pestanasimuladorinternopage.CapitalTotal), String.valueOf(calculoMontoSoli));

    		int CuotaCorriente = (int) CuotaCorriente(calculoMontoSoli, Double.parseDouble(Tasa), Integer.parseInt(Plazo));
    		assertvalidarEquals(TextoElemento(pestanasimuladorinternopage.ValorCuota).replaceAll("[^a-zA-Z0-9]", ""), String.valueOf(CuotaCorriente));

    		int PrimaAnticipadaSeguro = (int) PrimaAnticipadaSeguro(calculoMontoSoli, 1000000, Tasaxmillonseguro,
    				DesPrimaAntic);
    		// revisar calculo
    		//assertvalidarEquals(TextoElemento(pestanasimuladorinternopage.PrimaAnticipadaSeguroAsesor),String.valueOf(PrimaAnticipadaSeguro));

    		int MontoMaxDesembolsar = (int) MontoMaxDesembolsar(Integer.parseInt(Ingresos), Integer.parseInt(descLey),Integer.parseInt(descNomina), colchon, Double.parseDouble(Tasa),Integer.parseInt(Plazo), Tasaxmillonseguro, DesPrimaAntic);
    		assertvalidarEquals(TextoElemento(pestanasimuladorinternopage.MontoMaximoAsesor),String.valueOf(MontoMaxDesembolsar));
       		
    	    int EstudioCreditoIva = (int) EstudioCreditoIva(calculoMontoSoli, EstudioCredito);
    		assertvalidarEquals(TextoElemento(pestanasimuladorinternopage.ValorEstudioCreditoCXC), String.valueOf(EstudioCreditoIva));
    		
    		int ValorFianza = (int) ValorFianza(calculoMontoSoli,TasaFianza, variableFianza);
    		assertvalidarEquals(TextoElemento(pestanasimuladorinternopage.ValorFianzaCXC), String.valueOf(ValorFianza));

    		int Gmf4100 = (int) Gmf4100(Integer.parseInt(saneamiento), 0.004);
    		assertvalidarEquals(TextoElemento(pestanasimuladorinternopage.Gravamento4x1000), String.valueOf(Gmf4100));
    		
    		int DescuentosPorCartera = ((Gmf4100 + Integer.parseInt(saneamiento)));
    		int Remanente = (Integer.parseInt(Monto));
    		Remanente = Remanente - DescuentosPorCartera;
    		assertvalidarEquals(TextoElemento(pestanasimuladorinternopage.ValoraDesembolsar), String.valueOf( Remanente ));
    		assertvalidarEquals(TextoElemento(pestanasimuladorinternopage.CompraSaneamiento),saneamiento);
    		assertvalidarEquals(TextoElemento(pestanasimuladorinternopage.MontoAsesor),Monto);
    		assertvalidarEquals(TextoElemento(pestanasimuladorinternopage.PlazoAsesor),Plazo);
    		assertvalidarEquals(TextoElemento(pestanasimuladorinternopage.TasaAsesor),Tasa);
    		assertvalidarEquals(TextoElemento(pestanasimuladorinternopage.IngresosAsesor).substring(0,TextoElemento(pestanasimuladorinternopage.IngresosAsesor).length()-2).replaceAll("[^a-zA-Z0-9]", ""),Ingresos);
    		assertvalidarEquals(TextoElemento(pestanasimuladorinternopage.DescuentosLey).substring(0,TextoElemento(pestanasimuladorinternopage.DescuentosLey).length()-2).replaceAll("[^a-zA-Z0-9]", ""),descLey);
    		assertvalidarEquals(TextoElemento(pestanasimuladorinternopage.DescuentosNomina).substring(0,TextoElemento(pestanasimuladorinternopage.DescuentosNomina).length()-2).replaceAll("[^a-zA-Z0-9]", ""),descNomina);
       }
       
    /************FINALIZA ACCIONES ANALISTA DE CREDITO SANEAMIENTO*************/
       

/************INICIA ACCIONES LLAMADA DE BIENVENIDA DE CREDITO SANEAMIENTO*************/
	   
       public void CorrectocondicionesSaneamiento(String Celular, String Correo) throws InterruptedException {
       	MarcarCheck(pagesclienteparabienvenida.Check);
           Hacer_scroll(pagesclienteparabienvenida.label_Nombres_Completos);
           hacerClick(pagesclienteparabienvenida.Contactado);
           ElementVisible(); 
           hacerClick(pagesclienteparabienvenida.Direccion_Residencia_Si); 
           ElementVisible(); 
           Hacer_scroll_Abajo(pagesclienteparabienvenida.Guardar);
           hacerClick(pagesclienteparabienvenida.Guardar);
           ElementVisible(); 
           hacerClick(pagesclienteparabienvenida.Correcta);
           ElementVisible();
          
       }
	  
       public void AceptaCondiconesCreditoSaneamiento(String TipoDesen) throws InterruptedException {
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
	   
/************FINALIZA ACCIONES LLAMADA DE BIENVENIDA DE CREDITO SANEAMIENTO*************/

/************INICIA ACCIONES DESEMBOLSO SANEAMIENTO /
        * @throws InterruptedException*************/
          
         public void ProcesarSaneamiento (String Cedula) throws InterruptedException {
       	panelnavegacionaccion.CreditoParaDesembolso();
         	ElementVisible();     	
         	esperaExplicita(PagesCreditosDesembolso.filtrocedula);
         	EscribirElemento(PagesCreditosDesembolso.filtrocedula,Cedula);
         	ElementVisible();
         	Hacer_scroll(pagescreditosdesembolso.FiltroEstadoPago);
         	hacerClick(PagesCreditosDesembolso.FiltroTipoOperacion);
         	hacerClick(pagescreditosdesembolso.TipoOperacionSaneamiento);
         	Hacer_scroll(pagescreditosdesembolso.CheckProcesarPagos);
         	ElementVisible();
         	hacerClick(pagescreditosdesembolso.CheckProcesarPagos);
         	ElementVisible();
         	hacerClick(pagescreditosdesembolso.ProcesarPagos);
         	ElementVisible(); 
         }
          
         public void DescargarMediosDispercionSaneamiento(String saneamiento, String Banco, String Pdf) throws InterruptedException {
         	panelnavegacionaccion.CreditoParaDesembolsoDescargar();
         	esperaExplicita(PagesCreditosDesembolso.FiltroMonto);
         	EscribirElemento(PagesCreditosDesembolso.FiltroMonto,saneamiento);
         	Thread.sleep(2000);
         	ElementVisible(); 
         	
         	String pattern = "###,###,###.###";
             double value = Double.parseDouble(saneamiento);

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
         
/************FINALIZA ACCIONES DESEMBOLSO SANEAMIENTO *************/  
         
         
/************INICIA ACCIONES DESEMBOLSO SANEAMIENTO /
          * @throws InterruptedException*************/
            
       public void ProcesarRemanente (String Cedula) throws InterruptedException {
         	panelnavegacionaccion.CreditoParaDesembolso();
           	ElementVisible();     	
           	esperaExplicita(PagesCreditosDesembolso.filtrocedula);
           	EscribirElemento(PagesCreditosDesembolso.filtrocedula,Cedula);
           	ElementVisible();
           	Hacer_scroll(pagescreditosdesembolso.FiltroEstadoPago);
           	hacerClick(PagesCreditosDesembolso.FiltroTipoOperacion);
           	hacerClick(pagescreditosdesembolso.TipoOperacionRemanente);
           	Hacer_scroll(pagescreditosdesembolso.CheckProcesarPagos);
           	ElementVisible();
           	hacerClick(pagescreditosdesembolso.CheckProcesarPagos);
           	ElementVisible();
           	hacerClick(pagescreditosdesembolso.ProcesarPagos);
           	ElementVisible(); 
      }
           
      public void DescargarMediosDispercionRemanente(String Monto, String saneamiento, String Banco, String Pdf) throws InterruptedException {
       	  	panelnavegacionaccion.CreditoParaDesembolsoDescargar();
       	  	esperaExplicita(PagesCreditosDesembolso.FiltroMonto);
       	           	  	
       	  	int Gmf4100 = (Integer.parseInt(saneamiento))*4/1000;
       	  	int Remanente =  (Integer.parseInt(Monto)) - (Integer.parseInt(saneamiento))- (int) (Gmf4100) ;
       	  	EscribirElemento(PagesCreditosDesembolso.FiltroMonto,String.valueOf(Remanente));
       	    Thread.sleep(2000);
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
/************FINALIZA ACCIONES DESEMBOLSO SANEAMIENTO*************/

}