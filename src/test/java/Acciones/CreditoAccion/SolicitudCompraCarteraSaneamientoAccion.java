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
//import StepsDefinitions.string;

public class SolicitudCompraCarteraSaneamientoAccion extends BaseTest {

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
	double vlrIva = 1.19;
	
	LoginAccion loginaccion;
	LeerArchivo archivo;
	private static Logger log = Logger.getLogger(OriginacionCreditosAccion.class);

	public SolicitudCompraCarteraSaneamientoAccion(WebDriver driver) throws InterruptedException {
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
	
	/********* INICIO ACCIONES PARA COMPRA CARTERA SANEAMIENTO *****
	********************** @throws InterruptedException ***************/
	
	public void DatosCarteraSaneamiento( String Competidor1, String Cartera1, String VlrCuota1, String FechaVencimiento1, String NumObligacion1, String Competidor2, String Saneamiento2, String VlrCuota2, String FechaVencimiento2, String NumObligacion2) throws InterruptedException {
		recorerpestanas("DIGITALIZACIÓN");
		Hacer_scroll_Abajo(pestanadigitalizacionPage.SegundaPestanaDigitalizacion);
		hacerClick(pestanadigitalizacionPage.SegundaPestanaDigitalizacion);
		esperaExplicita(pestanadigitalizacionPage.AgregarCartera);
		hacerClick(pestanadigitalizacionPage.AgregarCartera);
		ElementVisible();
		hacerClick(pestanadigitalizacionPage.AgregarCartera);
		ElementVisible();
		Thread.sleep(5000);
		//Diligenciar datos Saneamiento
		esperaExplicita(pestanadigitalizacionPage.RadioSaneamiento);
		Hacer_scroll_Abajo(pestanadigitalizacionPage.RadioSaneamiento);
		hacerClick(pestanadigitalizacionPage.RadioSaneamiento);
		//esperaExplicita(pestanadigitalizacionPage.EntidadCompetidor);
		hacerClick(pestanadigitalizacionPage.EntidadCompetidor);
		EscribirElemento(pestanadigitalizacionPage.FiltroLista, Competidor2);
		EnviarEnter(pestanadigitalizacionPage.FiltroLista);
		EscribirElemento(pestanadigitalizacionPage.FechaVencimientoSaneamiento, FechaVencimiento2);
		EnviarEnter(pestanadigitalizacionPage.FechaVencimientoSaneamiento);
		EscribirElemento(pestanadigitalizacionPage.MontoSaneamiento, Saneamiento2);
		EscribirElemento(pestanadigitalizacionPage.ValorCuotaSaneamiento, VlrCuota2);
		EscribirElemento(pestanadigitalizacionPage.NumeroObligacionSaneamiento, NumObligacion2);
		//Diligenciar datos Cartera
		Hacer_scroll_Abajo(pestanadigitalizacionPage.RadioCompra);
		hacerClick(pestanadigitalizacionPage.RadioCompra);
		hacerClick(pestanadigitalizacionPage.EntidadCompetidorCartera);
		EscribirElemento(pestanadigitalizacionPage.FiltroListaCartera, Competidor1);
		EnviarEnter(pestanadigitalizacionPage.FiltroListaCartera);
		EscribirElemento(pestanadigitalizacionPage.FechaVencimientoCartera, FechaVencimiento1);
		EnviarEnter(pestanadigitalizacionPage.FechaVencimientoCartera);
		EscribirElemento(pestanadigitalizacionPage.MontoCartera, Cartera1);
		EscribirElemento(pestanadigitalizacionPage.ValorCuotaCartera, VlrCuota1);
		EscribirElemento(pestanadigitalizacionPage.NumeroObligacionCartera, NumObligacion1);
		ElementVisible();
	}
	public void GuardarDatosSaneamiento() throws InterruptedException {
		Hacer_scroll_Abajo(pestanadigitalizacionPage.Guardar);
		hacerClick(pestanadigitalizacionPage.Guardar);
		ElementVisible();
	}
	
	public void ConfirmarEntidad(String Competidor1, String Cartera1, String VlrCuota1, String FechaVencimiento1, String NumObligacion1, String Competidor2, String Saneamineto1, String VlrCuota2, String FechaVencimiento2, String NumObligacion2) throws InterruptedException {
		recorerpestanas("REFERENCIACIÓN");
		hacerClick(pestanareferenciacionpage.SalarioCheck);
		ElementVisible();
		hacerClick(pestanareferenciacionpage.FechaIngreso);
		ElementVisible();
		hacerClick(pestanareferenciacionpage.TipoContrato);
		ElementVisible();
		hacerClick(pestanareferenciacionpage.CargoCheck);
		ElementVisible();
		
		String ListCompetidores[]= {Competidor1,Competidor2};
		String ListMonto[]= {Cartera1,Saneamineto1};
		String ListCuota[]= {VlrCuota1,VlrCuota2};
		String ListFecha[]= {FechaVencimiento1,FechaVencimiento2};
		String ListVlrObligacion[]= {NumObligacion1,NumObligacion2};
		ClickBtnMultiples(
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
	
    public void Referenciaspositivas(String Codigo) throws InterruptedException {
    	
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
    	hacerClick(pestanadigitalizacionPage.MarcarCartera2);
    	hacerClick(pestanadigitalizacionPage.IdentidadConfirmada);
    	ElementVisible();
    	hacerClick(pestanadigitalizacionPage.BotonGuardarCartera);
    	ElementVisible();
    	esperaExplicitaNopresente(pestanadigitalizacionPage.Notificacion);
    }
    /************FIN ACCIONES SOLICITUD CREDITO COMPRA DE CARTERA CON SANEAMIENTO ***************/

    /************INICIA ACCIONES ANALISTA DE CREDITO COMPRA CARTERA*************
     * @throws SQLException 
     * @throws NumberFormatException ***************/
    
       public void ValidarSimuladorAnalistaCompraCartera(String Mes, String Monto,String Tasa,String Plazo, String Ingresos, String descLey, String descNomina, String Pagaduria, String Cartera1, String Saneamiento2) throws NumberFormatException, SQLException {
    	   
    	log.info("***************** AplicacionCierreAccion - ValidarSimuladorAnalistaCompraCartera()");
    	try {   
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
    		//Valores CXC capitalizadas				
    		int mesDos = 0;
    		double tasaDos = 0;
    		
    		
    		resultado = query.consultarValoresMesCapitalizadas();
    		while (resultado.next()) {
    			mesDos = resultado.getInt(1);			
    		}
    		log.info("Mes Dos "+ mesDos);
    		
    		resultado = query.consultarValoresTasaDosCapitalizadas();
    		while (resultado.next()) {
    			tasaDos = Double.parseDouble(resultado.getString(1))/100;			
    		}				
    		log.info("TasaFianza "+ tasaDos);
    		double tasaUno = Double.parseDouble(Tasa)/100;

    		assertvalidarEquals(TextoElemento(pestanasimuladorinternopage.MontoSolicitado),Monto);
    		
    		int Capacidad = (int) CapacidadPagaduria(Integer.parseInt(Ingresos), Integer.parseInt(descLey),Integer.parseInt(descNomina), colchon);
    		ToleranciaPesoMensaje("###### ERROR SIM ANALISTA - CALCULANDO MONTO CAPACIDAD ########",Integer.parseInt(TextoElemento(pestanasimuladorinternopage.CapacidadAsesor)), Capacidad);

    		int calculoMontoSoli = (int)  MontoaSolicitar(Integer.parseInt(Monto), DesPrimaAntic, Tasaxmillonseguro, EstudioCredito, TasaFianza, vlrIva);
    		assertvalidarEquals(TextoElemento(pestanasimuladorinternopage.CapitalTotal), String.valueOf(calculoMontoSoli));

    		int CuotaCorriente = (int) CuotaCorriente(calculoMontoSoli, tasaUno, Integer.parseInt(Plazo), tasaDos, mesDos);
    		ToleranciaPesoMensaje("######### ERROR SIM ANALISTA - CALCULANDO CUOTA CORRIENTE ##############", Integer.parseInt(TextoElemento(pestanasimuladorinternopage.ValorCuota).replaceAll("[^a-zA-Z0-9]", "")) , CuotaCorriente);

    		int PrimaAnticipadaSeguro = (int) PrimaAnticipadaSeguro(Integer.parseInt(Monto), 1000000, Tasaxmillonseguro,
    				DesPrimaAntic);
    		ToleranciaPesoMensaje("###### ERROR SIM ANALISTA - CALCULANDO PRIMA SEGURO ########",Integer.parseInt(TextoElemento(pestanasimuladorinternopage.PrimaAnticipadaSeguroAsesor)) ,PrimaAnticipadaSeguro);

    		int MontoMaxDesembolsar = (int) MontoMaxDesembolsar(Integer.parseInt(Ingresos), Integer.parseInt(descLey),
    				Integer.parseInt(descNomina), colchon, tasaUno,
    				Integer.parseInt(Plazo), tasaDos, mesDos);
    		ToleranciaPesoMensaje("###### ERROR SIM ANALISTA - CALCULANDO MONTO MAXIMO DESEMBOLSAR ########",Integer.parseInt(TextoElemento(pestanasimuladorinternopage.MontoMaximoAsesor)) ,MontoMaxDesembolsar);
       		
    	    int EstudioCreditoIva = (int) EstudioCreditoIva(Integer.parseInt(Monto), EstudioCredito);
    	    ToleranciaPesoMensaje("###### ERROR SIM ANALISTA - CALCULANDO ESTUDIO CREDITO ########",Integer.parseInt(TextoElemento(pestanasimuladorinternopage.ValorEstudioCreditoCXC)) , EstudioCreditoIva);
    		
    		int ValorFianza = (int) ValorFianza(Integer.parseInt(Monto), TasaFianza, variableFianza);
    		ToleranciaPesoMensaje("###### ERROR SIM ANALISTA - CALCULANDO FIANZA ########",Integer.parseInt(TextoElemento(pestanasimuladorinternopage.ValorFianzaCXC)) , ValorFianza);
    		
    		int TotalCarteras = (Integer.parseInt(Cartera1)+Integer.parseInt(Saneamiento2));
    		
    		int Gmf4100 = (int) Gmf4100(TotalCarteras, 0.004);
    		ToleranciaPesoMensaje("###### ERROR SIM ANALISTA - CALCULANDO 4X1000 ########",Integer.parseInt(TextoElemento(pestanasimuladorinternopage.Gravamento4x1000)) , Gmf4100);
    		
    		
    		int RemanenteEstimado = (int) RemanenteEstimado(calculoMontoSoli, TotalCarteras,
    				Gmf4100, PrimaAnticipadaSeguro, EstudioCreditoIva, ValorFianza);
    		assertvalidarEquals(TextoElemento(pestanasimuladorinternopage.ValoraDesembolsar), String.valueOf(RemanenteEstimado));
    		assertvalidarEquals(TextoElemento(pestanasimuladorinternopage.ValorCompraCartera), String.valueOf(TotalCarteras));
    		assertvalidarEquals(TextoElemento(pestanasimuladorinternopage.MontoAsesor),Monto);
    		assertvalidarEquals(TextoElemento(pestanasimuladorinternopage.PlazoAsesor),Plazo);
    		assertvalidarEquals(TextoElemento(pestanasimuladorinternopage.TasaAsesor),Tasa);
    		assertvalidarEquals(TextoElemento(pestanasimuladorinternopage.IngresosAsesor).substring(0,TextoElemento(pestanasimuladorinternopage.IngresosAsesor).length()-2).replaceAll("[^a-zA-Z0-9]", ""),Ingresos);
    		assertvalidarEquals(TextoElemento(pestanasimuladorinternopage.DescuentosLey).substring(0,TextoElemento(pestanasimuladorinternopage.DescuentosLey).length()-2).replaceAll("[^a-zA-Z0-9]", ""),descLey);
    		assertvalidarEquals(TextoElemento(pestanasimuladorinternopage.DescuentosNomina).substring(0,TextoElemento(pestanasimuladorinternopage.DescuentosNomina).length()-2).replaceAll("[^a-zA-Z0-9]", ""),descNomina);
    		}
      catch (Exception e) {
    	  	log.error("########## Error - AplicacionCierreAccion - SeleccionarPeriodoAno()  #######" + e);
			assertTrue("########## Error - AplicacionCierreAccion - SeleccionarPeriodoAno() ########"+ e,false);
		}
     }
    /************FINALIZA ACCIONES ANALISTA DE CREDITO COMPRA DE CARTERA*************/
       
    /************INICIA ACCIONES LLAMADA DE BIENVENIDA DE CREDITO COMPRA DE CARTERA*************/
       
       public void Aceptarcondiconesdelcredito(String TipoDesen) throws InterruptedException {
      	 recorerpestanas("CONDICIONES DEL CRÉDITO");
      	 Refrescar();
           hacerClick(pagesclienteparabienvenida.AceptarCartera);
           hacerClick(pagesclienteparabienvenida.AceptarSaneamiento);
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
       
      public void DescargarMediosDispercionCartera(String Monto, String Banco, String Pdf) throws InterruptedException {
      	panelnavegacionaccion.CreditoParaDesembolsoDescargar();
      	esperaExplicita(PagesCreditosDesembolso.FiltroMonto);
      	EscribirElemento(PagesCreditosDesembolso.FiltroMonto,Monto);
      	ElementVisible();
      	Thread.sleep(5000);
      	
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
      /************FINALIZA ACCIONES DESEMBOLSO CARTERA *************/  
      
      /************INICIA ACCIONES VISACION CARTERA 
     * @throws InterruptedException *************/  
      
      public void VisacionCartera (String Pdf) throws InterruptedException {
    	  recorerpestanas("CARTERAS Y SANEAMIENTOS");
    	  Hacer_scroll(pagesclienteparavisacion.PazYSalvoCartera);
    	  cargarpdf(pagesclienteparavisacion.PazYSalvoCartera,Pdf);
    	  cargarpdf(pagesclienteparavisacion.PazYSalvoSaneamiento,Pdf);
    	  ElementVisible();
      }
      
      /************FINALIZA ACCIONES VISACION CARTERA *************/ 
      
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
         
        public void DescargarMediosDispercionSaneamiento(String Saneamiento2, String Banco, String Pdf) throws InterruptedException {
        	panelnavegacionaccion.CreditoParaDesembolsoDescargar();
        	esperaExplicita(PagesCreditosDesembolso.FiltroMonto);
        	EscribirElemento(PagesCreditosDesembolso.FiltroMonto,Saneamiento2);
        	Thread.sleep(2000);
        	ElementVisible(); 
        	
        	String pattern = "###,###,###.###";
            double value = Double.parseDouble(Saneamiento2);

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
          	hacerClick(pagescreditosdesembolso.FiltroEstadoPago);
          	hacerClick(pagescreditosdesembolso.EstadoPagoHabilitado);
          	ElementVisible();
          	hacerClick(pagescreditosdesembolso.FiltroTipoOperacion);
          	hacerClick(pagescreditosdesembolso.TipoOperacionRemanente);
        	Thread.sleep(2000);
           Hacer_scroll(pagescreditosdesembolso.CheckProcesarPagos);
          	ElementVisible();
          	hacerClick(pagescreditosdesembolso.CheckProcesarPagos);
          	ElementVisible();
          	hacerClick(pagescreditosdesembolso.ProcesarPagos);
          	ElementVisible(); 
     }
          
     public void DescargarMediosDispercionRemanente(String Monto, String Cartera1,String Saneamiento2, String Banco, String Pdf) throws InterruptedException {
      	  	panelnavegacionaccion.CreditoParaDesembolsoDescargar();
      	  	esperaExplicita(PagesCreditosDesembolso.FiltroMonto);
      	           	
      	
        int TotalCarteras = (Integer.parseInt(Cartera1)+Integer.parseInt(Saneamiento2));
        int Gmf4100 = (int) Gmf4100(TotalCarteras, 0.004);
      	int DescuentosPorCartera = ((Gmf4100 + TotalCarteras));
      	int Remanente =  (Integer.parseInt(Monto) - (DescuentosPorCartera));
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
