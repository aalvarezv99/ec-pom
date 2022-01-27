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
import org.openqa.selenium.WebElement;

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
import dto.SimuladorDto;
import io.cucumber.datatable.DataTable;
//import StepsDefinitions.string;

public class SolicitudCompraCarteraSaneamientoAccion extends BaseTest {

	WebDriver driver;
	SimuladorAsesorPages simuladorasesorpage;
	OriginacionCreditosAccion origicacionCreditoAccion;
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
	//Variables verificacion Plan de Pagos - OriginacionCCS
	private String vg_MontoAprobado_Originacion;
  	private String vg_SegundaTasaInteres_Originacion;
  	private String vg_PrimaSeguroAnticipada_Originacion;
  	private String vg_CuotasPrimaSeguroAnticipada;

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
	origicacionCreditoAccion = new OriginacionCreditosAccion(driver);
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
			
			log.info("************ se confirman las carteras y saneamientos  ClickBtnMultiples()  **********");
						
             ClickBtnMultiples(
					pestanareferenciacionpage.ListLabelEntidad,pestanareferenciacionpage.ListFiltroEntidad,
					pestanareferenciacionpage.ListMonto,pestanareferenciacionpage.ListValorCuota,
					pestanareferenciacionpage.ListFecha,pestanareferenciacionpage.ListNumObligacion,pestanareferenciacionpage.ListRadioSaneamiento,
					pestanareferenciacionpage.ListBtnAprobar,pestanareferenciacionpage.ListTipo, pestanareferenciacionpage.ListRadioCompra,pestanareferenciacionpage.listDescEntidad,
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
			vg_CuotasPrimaSeguroAnticipada = String.valueOf(DesPrimaAntic);
			int TotalCarteras = (Integer.parseInt(Cartera1)+Integer.parseInt(Saneamiento2));
    		
			SimuladorDto calculosSimulador = new SimuladorDto();
	      	
	      	calculosSimulador = origicacionCreditoAccion.consultarCalculosSimulador(Monto,DesPrimaAntic,Tasa,Plazo,DiasHabilesIntereses,String.valueOf(TotalCarteras), 
	        		Ingresos, descLey, descNomina, Pagaduria);

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

    		
    		assertvalidarEquals(TextoElemento(pestanasimuladorinternopage.MontoSolicitado),Monto);    	
    		ToleranciaPesoMensaje("###### SIM ANALISTA - CALCULANDO MONTO CAPACIDAD ########",Integer.parseInt(TextoElemento(pestanasimuladorinternopage.CapacidadAsesor)), calculosSimulador.getCapacidadCliente());
    		ToleranciaPesoMensaje("###### SIM ANALISTA - CALCULANDO MONTO SOLICITUD ########",Integer.parseInt(TextoElemento(pestanasimuladorinternopage.CapitalTotal)) , calculosSimulador.getMontoSolicitar());
    		ToleranciaPesoMensaje("###### SIM ANALISTA - CALCULANDO CUOTA CORRIENTE ##############", Integer.parseInt(TextoElemento(pestanasimuladorinternopage.ValorCuota).replaceAll("[^a-zA-Z0-9]", "")) , calculosSimulador.getCuotaCorriente());
    		ToleranciaPesoMensaje("###### SIM ANALISTA - CALCULANDO PRIMA SEGURO ########",Integer.parseInt(TextoElemento(pestanasimuladorinternopage.PrimaAnticipadaSeguroAsesor)) ,calculosSimulador.getPrimaSeguroAnticipada());
    		ToleranciaPesoMensaje("###### SIM ANALISTA - CALCULANDO MONTO MAXIMO DESEMBOLSAR ########",Integer.parseInt(TextoElemento(pestanasimuladorinternopage.MontoMaximoAsesor)) ,calculosSimulador.getMontoMaxDesembolsar());
    	    ToleranciaPesoMensaje("###### SIM ANALISTA - CALCULANDO ESTUDIO CREDITO ########",Integer.parseInt(TextoElemento(pestanasimuladorinternopage.ValorEstudioCreditoCXC)) , calculosSimulador.getEstudioCredito());
    		ToleranciaPesoMensaje("###### SIM ANALISTA - CALCULANDO FIANZA ########",Integer.parseInt(TextoElemento(pestanasimuladorinternopage.ValorFianzaCXC)) , calculosSimulador.getFianza());    		
    		ToleranciaPesoMensaje("###### SIM ANALISTA - CALCULANDO 4X1000 ########",Integer.parseInt(TextoElemento(pestanasimuladorinternopage.Gravamento4x1000)) , calculosSimulador.getGmf4X100());    		
    		assertvalidarEquals(TextoElemento(pestanasimuladorinternopage.ValoraDesembolsar), String.valueOf(calculosSimulador.getRemanenteEstimado()));
    		assertvalidarEquals(TextoElemento(pestanasimuladorinternopage.ValorCompraCartera), String.valueOf(TotalCarteras));
    		assertvalidarEquals(TextoElemento(pestanasimuladorinternopage.MontoAsesor),Monto);
    		assertvalidarEquals(TextoElemento(pestanasimuladorinternopage.PlazoAsesor),Plazo);
    		assertvalidarEquals(GetText(pestanasimuladorinternopage.TasaAsesor),Tasa);
    		assertvalidarEquals(TextoElemento(pestanasimuladorinternopage.IngresosAsesor).substring(0,TextoElemento(pestanasimuladorinternopage.IngresosAsesor).length()-2).replaceAll("[^a-zA-Z0-9]", ""),Ingresos);
    		assertvalidarEquals(TextoElemento(pestanasimuladorinternopage.DescuentosLey).substring(0,TextoElemento(pestanasimuladorinternopage.DescuentosLey).length()-2).replaceAll("[^a-zA-Z0-9]", ""),descLey);
    		assertvalidarEquals(TextoElemento(pestanasimuladorinternopage.DescuentosNomina).substring(0,TextoElemento(pestanasimuladorinternopage.DescuentosNomina).length()-2).replaceAll("[^a-zA-Z0-9]", ""),descNomina);

    		//Variables globales para posterior analisis Plan de Pagos - OriginacionCCS
    		vg_MontoAprobado_Originacion= String.valueOf(calculosSimulador.getMontoSolicitar());
    		vg_SegundaTasaInteres_Originacion = String.valueOf(tasaDos*100);
    		vg_PrimaSeguroAnticipada_Originacion = String.valueOf(calculosSimulador.getPrimaSeguroAnticipada());
    		}
      catch (Exception e) {
    	  	log.error("########## Error - AplicacionCierreAccion - SeleccionarPeriodoAno()  #######" + e);
			assertTrue("########## Error - AplicacionCierreAccion - SeleccionarPeriodoAno() ########"+ e,false);
		}
     }
       
       public void validelainformacioncabeceraconsusconceptosparaOriginacionCCS(String Tasa, String Plazo) throws InterruptedException {
   		
   		try {
   		
   		validarCabeceraPlanDePagos("Originacion",
   				Tasa,
       			Plazo,
       			vg_MontoAprobado_Originacion,
       			vg_SegundaTasaInteres_Originacion,
       			vg_PrimaSeguroAnticipada_Originacion,
       			vg_CuotasPrimaSeguroAnticipada,
       			null, 
       			null, 
       			pestanasimuladorinternopage.KeyCabeceraPlanDePagos, 
       			pestanasimuladorinternopage.ValueCabeceraPlanDePagos);
   			
   		} catch (Exception e) {
   			log.error("########## Error - OriginacionCreditosAccion  - PestanaPlanDePagos () #######" + e);
   			assertTrue("########## Error - OriginacionCreditosAccion - PestanaPlanDePagos () ########" + e, false);
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
       
	public void ProcesarCartera(String tipo, String Cedula) throws InterruptedException {
		log.info("*** Procesar Desembolsos de cartera - saneamientos, OriginacionCreditosAccion - ProcesarCartera()***");
		try {
			panelnavegacionaccion.CreditoParaDesembolso();
			ElementVisible();
			esperaExplicita(PagesCreditosDesembolso.filtrocedula);
			EscribirElemento(PagesCreditosDesembolso.filtrocedula, Cedula);
			ElementVisible();
			Hacer_scroll(pagescreditosdesembolso.FiltroEstadoPago);
			hacerClick(PagesCreditosDesembolso.FiltroTipoOperacion);
			if (tipo.equals("Cartera")) {
				hacerClick(pagescreditosdesembolso.TipoOperacionCompraCartera);
				ElementVisible();
			} else {
				hacerClick(pagescreditosdesembolso.TipoOperacionSaneamiento);
				ElementVisible();
			}
			Hacer_scroll(pagescreditosdesembolso.CheckProcesarPagos);
			ElementVisible();
			marcarCheckMultiple(pagescreditosdesembolso.CheckProcesarPagos);
			hacerClick(pagescreditosdesembolso.ProcesarPagos);
		} catch (Exception e) {
			log.error("########## Error - OriginacionCreditosAccion  - ProcesarCartera() #######" + e);
 			assertTrue("########## Error - OriginacionCreditosAccion - ProcesarCartera()########"+ e,false);
		}

	}
     
     //ThainePerez 21/Nov/2021 - Se comentarea este codigo al parecer no se utiliza borrar despues de dos meses se no se explota 
     // y se llega a este metodo 
    /*  public void DescargarMediosDispercionCartera(String Monto, String Banco, String Pdf) throws InterruptedException {
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
      }*/
      /************FINALIZA ACCIONES DESEMBOLSO CARTERA *************/  
      
      /************INICIA ACCIONES VISACION CARTERA 
     * @throws InterruptedException *************/  
      
      /*ThainePerez 17/Nov/2021 V1.2 Se actualiza el metodo para que utilice el metodo cargarPdfVisacionCarteras*/ 
      public void VisacionCartera(String Pdf) throws InterruptedException {
    	log.info("***Se visan las carteras o saneamientos agregando el PDF,OriginacionCreditosAccion  - VisacionCartera()  ***");
    	try {
    		  recorerpestanas("CARTERAS Y SANEAMIENTOS");
        	  Hacer_scroll(pagesclienteparavisacion.listPazYSalvoPdf);
        	  cargarPdfVisacionCarteras(pagesclienteparavisacion.listPazYSalvoPdf, Pdf);
        	  ElementVisible();
		} catch (Exception e) {
			log.error("########## Error - OriginacionCreditosAccion  - VisacionCartera() #######" + e);
 			assertTrue("########## Error - OriginacionCreditosAccion - VisacionCartera()########"+ e,false);
		}
    	  
      }
      
      /************FINALIZA ACCIONES VISACION CARTERA *************/ 
      
      /************INICIA ACCIONES DESEMBOLSO SANEAMIENTO /
       * @throws InterruptedException*************/
                
         
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
        	ClicUltimoElemento(PagesCreditosDesembolso.VerEditar);
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
          
     public void DescargarMediosDispercionRemanente(DataTable tablaFeature) throws InterruptedException {
    	 log.info("***Se descargan los medios de dispercion del remanente restando carteras y saneamientos, OriginacionCreditosAccion -  DescargarMediosDispercionRemanente() ***");
    	try {
    		 panelnavegacionaccion.CreditoParaDesembolsoDescargar();
    	      	esperaExplicita(PagesCreditosDesembolso.FiltroMonto);
    	      	List<Map<String, String>> data = tablaFeature.asMaps(String.class, String.class); 
    			
    			int contador = 0;
    			for (Map<String, String> dataFeature : data) {
    	      	
    	        int TotalCarteras = (Integer.parseInt(dataFeature.get("Cartera"))+Integer.parseInt(dataFeature.get("Saneamiento")));
    	        int Gmf4100 = (int) Gmf4100(TotalCarteras, 0.004);
    	      	int DescuentosPorCartera = ((Gmf4100 + TotalCarteras));
    	      	int Remanente =  (Integer.parseInt(dataFeature.get("Monto")) - (DescuentosPorCartera));
    	      	  	EscribirElemento(PagesCreditosDesembolso.FiltroMonto,String.valueOf(Remanente));
    	      	    Thread.sleep(2000);
    	      	  	ElementVisible(); 	  	
    	      	  	String pattern = "###,###,###.###";
    	      	      int value = Remanente;

    	      	      DecimalFormat myFormatter = new DecimalFormat(pattern);
    	      	      myFormatter = new DecimalFormat(pattern,DecimalFormatSymbols.getInstance(Locale.GERMANY));
    	      	      String output = myFormatter.format(value);    	
    	      	  	esperaExplicita(By.xpath("//td[text()='"+output+"']"));
    	      	  	ClicUltimoElemento(PagesCreditosDesembolso.VerEditar);
    	      	  	ElementVisible(); 
    	      	  	hacerClick(PagesCreditosDesembolso.Banco);
    	      	  	hacerClick(By.xpath("//li[starts-with(@id,'formLote:j_idt89') and text()='"+dataFeature.get("Banco")+"' ]"));    	
    	      	  	ElementVisible(); 
    	      	  	cargarpdf(PagesCreditosDesembolso.CargarEvidencia,dataFeature.get("RutaPdf"));
    	      	  	esperaExplicita(PagesCreditosDesembolso.VerEvidencias);
    	      	  	ElementVisible(); 
    	      	  	hacerClick(PagesCreditosDesembolso.CrearArchivo);
    	      	  	esperaExplicita(PagesCreditosDesembolso.ArchivoCreado);
    	      	  	ElementVisible(); 
    	      	  	hacerClick(PagesCreditosDesembolso.Guardar);
    	      	  	ElementVisible(); 
    			}	
		} catch (Exception e) {
			log.error("########## Error - OriginacionCreditosAccion  - DescargarMediosDispercionRemanente() #######" + e);
 			assertTrue("########## Error - OriginacionCreditosAccion - DescargarMediosDispercionRemanente()########"+ e,false);
		}
    	
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
