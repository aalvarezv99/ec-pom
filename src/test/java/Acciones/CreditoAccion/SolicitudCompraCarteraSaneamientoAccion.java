package Acciones.CreditoAccion;

import static org.junit.Assert.assertTrue;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.IntStream;

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
import io.cucumber.datatable.DataTable;
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
	
	
	public void GuardarDatosSaneamiento() throws InterruptedException {
		Hacer_scroll_Abajo(pestanadigitalizacionPage.Guardar);
		hacerClick(pestanadigitalizacionPage.Guardar);
		ElementVisible();
	}
	
	/*ThainerPerez 22/sep/2021 - Se actualiza el metodo donde recibe la tabla de (Cartera - Saneamiento) para manipular la data
	 * */
	public void ConfirmarEntidad(DataTable tabla) throws InterruptedException {
		log.info("************ se confirman las carteras y saneamientos, SolicitudCompraCarteraSaneamientoAccion - ConfirmarEntidad() **********");
		try {
			recorerpestanas("REFERENCIACIÓN");
			hacerClick(pestanareferenciacionpage.SalarioCheck);
			ElementVisible();
			hacerClick(pestanareferenciacionpage.FechaIngreso);
			ElementVisible();
			hacerClick(pestanareferenciacionpage.TipoContrato);
			ElementVisible();
			hacerClick(pestanareferenciacionpage.CargoCheck);
			ElementVisible();
			
			List<Map<String, String>> data = tabla.asMaps(String.class, String.class); 

			String ListCompetidores[]= new String[data.size()];
			String ListMonto[]  =  new String[data.size()];
			String ListCuota[] =  new String[data.size()];
			String ListFecha[] =  new String[data.size()];
			String ListVlrObligacion[] =  new String[data.size()];
			
			
			int contador = 0;
			for (Map<String, String> dataFeature : data) {
				ListCompetidores[contador] = dataFeature.get("Entidad");
				ListMonto[contador] = dataFeature.get("Monto");	
				ListCuota[contador] = dataFeature.get("VlrCuota");;
				ListFecha[contador] = dataFeature.get("FechaVencimiento");
				ListVlrObligacion[contador] = dataFeature.get("NumObligacion");
				contador++;
			}
			
			ClickBtnMultiples(
					pestanareferenciacionpage.ListLabelEntidad,pestanareferenciacionpage.ListFiltroEntidad,
					pestanareferenciacionpage.ListMonto,pestanareferenciacionpage.ListValorCuota,
					pestanareferenciacionpage.ListFecha,pestanareferenciacionpage.ListNumObligacion,pestanareferenciacionpage.ListRadioSaneamiento,
					pestanareferenciacionpage.ListBtnAprobar,pestanareferenciacionpage.ListTipo, pestanareferenciacionpage.ListRadioCompra,
					ListCompetidores,ListMonto,ListCuota,
					ListFecha,ListVlrObligacion);
			
			hacerClicknotificacion();
			hacerClick(pestanareferenciacionpage.GuardarReferencias);
			ElementVisible();
		} catch (Exception e) {
			// TODO: handle exception
		}
		
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
    	clickvarios(pestanadigitalizacionPage.listCheckSiCarteras);
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
    
       public void ValidarSimuladorAnalistaCompraCartera(String Mes, String Monto,String Tasa,String Plazo, String Ingresos, String descLey, String descNomina, String Pagaduria, String Cartera1, String Saneamiento2, String anoAnalisis, String fechaDesembolso) throws NumberFormatException, SQLException {
    	   
    	log.info("***************** AplicacionCierreAccion - ValidarSimuladorAnalistaCompraCartera()");
    	try {
    	Clear(pestanasimuladorinternopage.FechaDesembolso);
    	EscribirElemento(pestanasimuladorinternopage.FechaDesembolso, fechaDesembolso);
    	EnviarEscape(pestanasimuladorinternopage.FechaDesembolso);
    	esperaExplicita(pestanasimuladorinternopage.MesDeAfecatcion);
       	hacerClick(pestanasimuladorinternopage.MesDeAfecatcion);
       	ElementVisible(); 
       	selectValorLista(pestanasimuladorinternopage.ListaMes,Mes);
       	ElementVisible(); 
       	Clear(pestanasimuladorinternopage.anoAfectacion);
       	EscribirElemento(pestanasimuladorinternopage.anoAfectacion, anoAnalisis);
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
    		log.info("******** Valor de prima **** " + DesPrimaAntic);

    		String DiasHabilesIntereses = TextoElemento(pestanasimuladorinternopage.DiasInteresesIniciales);
			if(Integer.valueOf(Plazo)<DesPrimaAntic) {
				int periodoGracia = (int)Math.ceil((double)Integer.parseInt(DiasHabilesIntereses)/30);
				DesPrimaAntic = periodoGracia + Integer.valueOf(Plazo);
				log.info("******** Nuevo valor de prima plazo menor a 24  **** " + DesPrimaAntic);
			}
    		
    		int colchon = 0;
    		ResultSet resultadocolchon = query.colchonpagaduria(Pagaduria);
    		while (resultadocolchon.next()) {
    			colchon = Integer.parseInt(resultadocolchon.getString(1));
    		}

    		// Consultar los conceptos para el cambio de tasa
    		double EstudioCredito = 0;
			double TasaFianza =0;
			int mesDos = 0;
			double tasaDos = 0;
			resultado = query.consultarValoresCapitalizador(Tasa);
			while (resultado.next()) {
				tasaDos  = Double.parseDouble(resultado.getString(2))/100;	
				EstudioCredito = Double.parseDouble(resultado.getString(3));
				TasaFianza = Double.parseDouble(resultado.getString(4));
				mesDos = resultado.getInt(5);							
			}
			//EstudioCredito = 2.35; //EliminarLinea
			log.info("Tasa Estudio Credito " +EstudioCredito);
			log.info("Tasa Fianza " +TasaFianza);
			log.info("Valor mes Dos " + mesDos);
			log.info("Tasa Dos" + tasaDos);

          // Valores para la funciones estaticos
    		int Tasaxmillonseguro = 4625;
    		double variableFianza = 1.19;
    		double tasaUno = Double.parseDouble(Tasa)/100;
    		
    		assertvalidarEquals(TextoElemento(pestanasimuladorinternopage.MontoSolicitado),Monto);
    		
    		int Capacidad = (int) CapacidadPagaduria(Integer.parseInt(Ingresos), Integer.parseInt(descLey),Integer.parseInt(descNomina), colchon);
    		ToleranciaPesoMensaje("###### ERROR SIM ANALISTA - CALCULANDO MONTO CAPACIDAD ########",Integer.parseInt(TextoElemento(pestanasimuladorinternopage.CapacidadAsesor)), Capacidad);

    		int calculoMontoSoli = (int)  MontoaSolicitar(Integer.parseInt(Monto), DesPrimaAntic, Tasaxmillonseguro, EstudioCredito, TasaFianza, vlrIva);
    		ToleranciaPesoMensaje("###### SIM ANALISTA - CALCULANDO MONTO SOLICITUD ########",Integer.parseInt(TextoElemento(pestanasimuladorinternopage.CapitalTotal)) , calculoMontoSoli);

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
    		assertvalidarEquals(GetText(pestanasimuladorinternopage.TasaAsesor).replace("0", ""),Tasa);
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

       public void AceptarcondiconesdelcreditoComSan(String TipoDesen, String cedula) throws InterruptedException {
    	   this.cambiarPestana(0);
		   this.ClientesParaBienvenida(cedula);
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

     public void ClientesParaBienvenida(String Cedula) throws InterruptedException {
 		log.info("********** OriginacionCreditosAccion - ClientesParaBienvenida() **********");
 		try {
 			panelnavegacionaccion.CreditoClientesBienvenida();
 			ElementVisible();
 			esperaExplicita(pagesclienteparabienvenida.filtrocedula);
 			EscribirElemento(pagesclienteparabienvenida.filtrocedula, Cedula);
 			ElementVisible();
 			esperaExplicita(By.xpath("//td[text()='" + Cedula + "']"));
 			hacerClick(pagesclienteparabienvenida.Continuar);
 			ElementVisible();
 		} catch (Exception e) {
 			log.error("########## Error - OriginacionCreditosAccion  - ClientesParaBienvenida() #######" + e);
 			assertTrue("########## Error - OriginacionCreditosAccion - ClientesParaBienvenida()########"+ e,false);
 		}
 	}
}
