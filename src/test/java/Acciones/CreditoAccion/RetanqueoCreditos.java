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
import org.openqa.selenium.WebElement;

import Acciones.ComunesAccion.LoginAccion;
import Acciones.ComunesAccion.PanelPrincipalAccion;
import Archivo.LeerArchivo;
import CommonFuntions.BaseTest;
import Consultas.OriginacionCreditoQuery;
import Pages.ComunesPage.PanelNavegacionPage;
import Pages.CreditosPage.PagesClienteParaBienvenida;
import Pages.CreditosPage.PagesCreditosDesembolso;
import Pages.CreditosPage.PagesTareas;
import Pages.CreditosPage.RetanqueoPages;
import Pages.SolicitudCreditoPage.PestanaDigitalizacionPage;
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
	PagesTareas pagestareas;
	Pages.SolicitudCreditoPage.pestanaSeguridadPage pestanaSeguridadPage;

	LoginAccion loginaccion;
	LeerArchivo archivo;
	// BaseTest baseTest;
	private static Logger log = Logger.getLogger(OriginacionCreditosAccion.class);
	static int Monto;
	static int Remanente;
	static int SaldoAlDia;
	static int VlrRetanqueo;
	double vlrIva = 1.19;
	static String CedulaCliente;
	static String Rutapdf;

	public RetanqueoCreditos(WebDriver driver) throws InterruptedException {
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
		pagestareas = new PagesTareas(driver);
	}

	/************ INICIO ACCIONES RETANQUEO CREDITOS ***************/
	public void iniciarSesion() {
		// loginaccion.iniciarSesion();
	}

	public void ingresarRetanqueoAsesor() {
		panelnavegacionaccion.Retanqueo(); 
		adjuntarCaptura("Ingresa al modulo de retanqueo  ");	
	}

	public void FiltrarCredito(String Cedula, String Credito) throws InterruptedException {
		BuscarenGrilla(retanqueopages.cedula, Cedula);
		Thread.sleep(5000);
		ElementVisible();
		BuscarenGrilla(retanqueopages.credito, Credito);
		Thread.sleep(5000);
		ElementVisible();
		esperaExplicitaTexto(Credito);
		adjuntarCaptura("Se filtra el retanqueo ");	
	}

	public void Retanquear() throws InterruptedException {
		ElementVisible();
		hacerClick(retanqueopages.BtnRentaqueo);
		Thread.sleep(5000);
		ElementVisible();
		assertTextonotificacion(retanqueopages.notificacion, "Se ha generado un retanqueo para el");
		adjuntarCaptura("Se genera el retanqueo para el credito ");	
	}

	public void Credito(String Cedula) throws InterruptedException {
		panelnavegacionaccion.navegarCreditoSolicitud();
		BuscarenGrilla(retanqueopages.inputCedula, Cedula);
		ElementVisible();
		CedulaCliente=Cedula;
		esperaExplicitaTexto(Cedula);
	}

	public void seleccionarRetanqueo() {
		ClicUltimoElemento(retanqueopages.continuar);
		adjuntarCaptura("Se seleciona el retanqueo ");	
		ElementVisible();		
	}

	public void borrarArchivos() throws InterruptedException {
		clickvariosespera(retanqueopages.borrararchivos);
		adjuntarCaptura("Se borran los archivos del credito anterior ");	
	}

	public void CargarArchivos(String pdf) throws InterruptedException {
		Thread.sleep(1000);
		Rutapdf=pdf;
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
		adjuntarCaptura("Cargar archivos al nuevo credito ");	
	}

	public void ConsultaCentrales() throws InterruptedException {
		ElementVisible();
		hacerClick(retanqueopages.BtnConsultaCentrales);
		ElementVisible();
		esperaExplicita(retanqueopages.notificacion);
	}

	public void Seguridad() throws InterruptedException, NumberFormatException, SQLException {
		recorerpestanas("SEGURIDAD");
		hacerClick(retanqueopages.Viable);
		hacerClick(retanqueopages.Guardar);
		ElementVisible();
		esperaExplicita(retanqueopages.Concepto);
		esperaExplicitaSeguridad(pestanaSeguridadPage.BtnCheck,CedulaCliente);
		Refrescar();
		esperaExplicita(pestanaSeguridadPage.Concepto);
		Hacer_scroll(pestanaSeguridadPage.Concepto);
		esperaExplicitaSeguridad(pestanaSeguridadPage.BtnCheck,CedulaCliente);	
		adjuntarCaptura("Se marca el credito viable ");	
	}

	public void Simulador(String Retanqueo,String Tasa, String Plazo, String DiasHabilesIntereses, String Ingresos, String descLey,
			String descNomina,String VlrCompraSaneamiento) throws NumberFormatException, SQLException {
		recorerpestanas("SIMULADOR");
		VlrRetanqueo = Integer.parseInt(Retanqueo);
		
		 // consulta base de datos
		int DesPrimaAntic = 0;
		OriginacionCreditoQuery query = new OriginacionCreditoQuery();
		ResultSet resultado = query.ConsultaDescuentoPrimaAntic();
		while (resultado.next()) {
			DesPrimaAntic = Integer.parseInt(resultado.getString(1));
		}
		
		if(Integer.valueOf(Plazo)<DesPrimaAntic) {
			int periodoGracia = (int)Math.ceil((double)Integer.parseInt(DiasHabilesIntereses)/30);
			DesPrimaAntic = periodoGracia + Integer.valueOf(Plazo);
		}
		
		esperaExplicita(retanqueopages.inputTasa);
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
		SaldoAlDia = Integer.parseInt(TextoElemento(retanqueopages.inputMontoValor));
		Monto = Integer.parseInt(TextoElemento(retanqueopages.inputMontoValor)) + VlrRetanqueo;
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
		LimpiarConTeclado(retanqueopages.vlrCompra);
		EscribirElemento(retanqueopages.vlrCompra, VlrCompraSaneamiento);
		ElementVisible();
		hacerClick(retanqueopages.inputdDescNomina);
		ElementVisible();
		
		adjuntarCaptura("Se llenan los campos del simulador ");	

	}

	public void ValidarSimulador(String Ingresos, String descLey, String descNomin, String Tasa,
			String Plazo,String Credito,String DiasHabilesIntereses,String VlrCompraSaneamiento) throws NumberFormatException, SQLException {
        
		// Valores para la funciones estaticos
		int Tasaxmillonseguro = 4625;
		double variableFianza = 1.19;

		// consulta base de datos descuento prima anticipada
		int DesPrimaAntic = 0;
		OriginacionCreditoQuery query = new OriginacionCreditoQuery();
		ResultSet resultado = query.ConsultaDescuentoPrimaAntic();
		while (resultado.next()) {
			DesPrimaAntic = Integer.parseInt(resultado.getString(1));
		}
		
		//consulta para validar  prima menor a 24 meses
		
		if(Integer.valueOf(Plazo)<DesPrimaAntic) {
			int periodoGracia = (int)Math.ceil((double)Integer.parseInt(DiasHabilesIntereses)/30);
			DesPrimaAntic = periodoGracia + Integer.valueOf(Plazo);
		}
		
		// consulta base de datos calculo de prima true o false
		String prima ="" ;
		ResultSet resultadoPrima = query.CalculoPrima(Credito);
		while (resultadoPrima.next()) {
			prima = resultadoPrima.getString(1);
		}
		System.out.println(" Variable prima: "+prima);
		
		
		double EstudioCredito = 0;
		ResultSet resultado2 = query.EstudioCredito();
		while (resultado2.next()) {
			EstudioCredito = Double.parseDouble(resultado2.getString(1));
		}
		
		double TasaFianza =0;
		ResultSet resultado3 = query.porcentajefianza();
		while (resultado3.next()) {
			TasaFianza = Double.parseDouble(resultado3.getString(1));
		}
		
	    int PrimaPadre =0;
		ResultSet resultado4 = query.ValorPrimaCreditoPadre(Credito);
		while (resultado4.next()) {
			PrimaPadre = Integer.parseInt(resultado4.getString(1));
		}
		
		int MontoPadre =0;
			ResultSet resultado5 = query.ValorMontoCreditoPadre(Credito);
			while (resultado5.next()) {
				MontoPadre = Integer.parseInt(resultado5.getString(1));
	    }
		
		int MesesActivoPadre =0;
			ResultSet resultado6 = query.MesesActivoPadre(Credito);
			while (resultado6.next()) {
				MesesActivoPadre = Integer.parseInt(resultado6.getString(1));
	    }	
			
		
		if(prima=="") {			
		int calculoMontoSoli = (int) MontoaSolicitar(Monto, DesPrimaAntic, Tasaxmillonseguro);		

		ToleranciaPesoMensaje(" Monto Solicitado ",Integer.parseInt(TextoElemento(pestanasimuladorinternopage.ResultMontoSoli)),calculoMontoSoli);
					
		int PrimaAnticipadaSeguro = (int) PrimaAnticipadaSeguro(calculoMontoSoli, 1000000, Tasaxmillonseguro,DesPrimaAntic);	
	    ToleranciaPesoMensaje(" Prima Anticipada de seguro", Integer.parseInt(TextoElemento(pestanasimuladorinternopage.PrimaAnticipadaSeguro)), PrimaAnticipadaSeguro);
		int Gmf4100 = (int) Gmf4100(Integer.parseInt(VlrCompraSaneamiento), 0.004);			
	    ToleranciaPesoMensaje(" Valor Desembolsar if",Integer.parseInt(TextoElemento(pestanasimuladorinternopage.SimuladorInternorValoraDesembolsar)),(VlrRetanqueo-(Gmf4100+Integer.parseInt(VlrCompraSaneamiento))));

	    //////////////////////////////////////////////////////////////////////////////////////////////////////////

		int CuotaCorriente = (int) CuotaCorriente(calculoMontoSoli, Double.parseDouble(Tasa), Integer.parseInt(Plazo));
		ToleranciaPesoMensaje(" Cuota corriente ",Integer.parseInt(TextoElemento(pestanasimuladorinternopage.CuotaCorriente)),CuotaCorriente);   
		int EstudioCreditoIva = (int) EstudioCreditoIva(calculoMontoSoli, EstudioCredito);
		ToleranciaPesoMensaje(" Estudio Credito IVA",Integer.parseInt(TextoElemento(pestanasimuladorinternopage.EstudioCreditoIVA)),EstudioCreditoIva);  
		int ValorFianza = (int) ValorFianza(calculoMontoSoli, TasaFianza, variableFianza);
	    ToleranciaPeso(Integer.parseInt(TextoElemento(pestanasimuladorinternopage.ValorFianza)),ValorFianza);

        //////////////////////////////////////////////////////////////////////////////////////////////////////////		
		
		}else {
			int calculoMontoSoli = (int) MontoaSolicitar(Monto, DesPrimaAntic, Tasaxmillonseguro);		

			ToleranciaPesoMensaje(" Monto Solicitado ",Integer.parseInt(TextoElemento(pestanasimuladorinternopage.ResultMontoSoli)),calculoMontoSoli);
						
			int PrimaAnticipadaSeguro = (int) PrimaAnticipadaSeguro(calculoMontoSoli, 1000000, Tasaxmillonseguro,DesPrimaAntic);	
		    ToleranciaPesoMensaje(" Prima Anticipada de seguro ", Integer.parseInt(TextoElemento(pestanasimuladorinternopage.PrimaAnticipadaSeguro)), PrimaAnticipadaSeguro);
		    
		    int PrimaNeta = (int) PrimaNeta(PrimaPadre,MontoPadre,MesesActivoPadre,PrimaAnticipadaSeguro, 1000000, Tasaxmillonseguro,DesPrimaAntic);	
		    ToleranciaPesoMensaje(" Prima neta ", Integer.parseInt(TextoElemento(pestanasimuladorinternopage.PrimaNeta)), PrimaNeta);
		    
		    int PrimaNoDevengada = (int) PrimaNoDevengadaCPadre(PrimaPadre,MontoPadre,MesesActivoPadre,PrimaAnticipadaSeguro, 1000000, Tasaxmillonseguro,DesPrimaAntic);	
		    ToleranciaPesoMensaje(" Prima No Devengada ", Integer.parseInt(TextoElemento(pestanasimuladorinternopage.PrimaNoDevengadaSimuladorInterno)), PrimaNoDevengada);
		    
		    
			int Gmf4100 = (int) Gmf4100(Integer.parseInt(VlrCompraSaneamiento), 0.004);			
		    ToleranciaPesoMensaje(" Valor Desembolsar else ",Integer.parseInt(TextoElemento(pestanasimuladorinternopage.SimuladorInternorValoraDesembolsar)),(VlrRetanqueo-(Gmf4100+Integer.parseInt(VlrCompraSaneamiento))+PrimaNoDevengada));

		    //////////////////////////////////////////////////////////////////////////////////////////////////////////

			int CuotaCorriente = (int) CuotaCorriente(calculoMontoSoli, Double.parseDouble(Tasa), Integer.parseInt(Plazo));
			ToleranciaPesoMensaje(" Cuota corriente ",Integer.parseInt(TextoElemento(pestanasimuladorinternopage.CuotaCorriente)),CuotaCorriente);   
			int EstudioCreditoIva = (int) EstudioCreditoIva(calculoMontoSoli, EstudioCredito);
			ToleranciaPesoMensaje(" Estudio Credito IVA",Integer.parseInt(TextoElemento(pestanasimuladorinternopage.EstudioCreditoIVA)),EstudioCreditoIva);  
			int ValorFianza = (int) ValorFianza(calculoMontoSoli, TasaFianza, variableFianza);
		    ToleranciaPeso(Integer.parseInt(TextoElemento(pestanasimuladorinternopage.ValorFianza)),ValorFianza);

		}
	}

	public void SolicitarCredito() throws InterruptedException {
		Hacer_scroll_Abajo(pestanasimuladorinternopage.Solicitar);
		hacerClick(pestanasimuladorinternopage.Solicitar);
		ElementVisible();
		hacerClicknotificacion();
			
		if(!EncontrarElementoVisibleCss(pestanasimuladorinternopage.ModalExcepciones)) {
			AprobarExcepciones(Rutapdf,CedulaCliente);
			Credito(CedulaCliente);
			seleccionarRetanqueo();
		}
		ElementVisible();
		
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
	public void ValidarSimuladorAnalistaRetanqueos(String anno, String Credito,String retanqueo,String fecha,String Mes, String Plazo,String Ingresos, String descLey, String descNomina,String DiasHabilesIntereses) throws InterruptedException, SQLException{
    	
		
		esperaExplicita(pestanasimuladorinternopage.MesDeAfecatcion);
    	hacerClick(pestanasimuladorinternopage.FechaDesembolso);
    	Clear(pestanasimuladorinternopage.FechaDesembolso);
    	EscribirElemento(pestanasimuladorinternopage.FechaDesembolso, fecha);  
    	EnviarEnter(pestanasimuladorinternopage.FechaDesembolso);
    	hacerClick(pestanasimuladorinternopage.MesDeAfecatcion);    	
		ElementVisible();
		selectValorLista(pestanasimuladorinternopage.ListaMes, Mes);
		ElementVisible();
		Clear(pestanasimuladorinternopage.Anno);
		EscribirElemento(pestanasimuladorinternopage.Anno, anno);
		hacerClick(pestanasimuladorinternopage.FechasManuales);
		ElementVisible();
		hacerClick(pestanasimuladorinternopage.CalcularDesglose);
		ElementVisible();
		hacerClicknotificacion();
		esperaExplicitaNopresente(pestanadigitalizacionPage.Notificacion);
		Monto=Integer.parseInt(TextoElemento(pestanasimuladorinternopage.MontoSolicitado));
	
		// consulta base de datos calculo de prima true o false
		String prima = "";
		OriginacionCreditoQuery query = new OriginacionCreditoQuery();
		ResultSet resultadoPrima = query.CalculoPrima(Credito);
		while (resultadoPrima.next()) {
			prima = resultadoPrima.getString(1);
		}
		
		// consulta base de datos descuento prima anticipada
		int DesPrimaAntic = 0;
		ResultSet resultado = query.ConsultaDescuentoPrimaAntic();
		while (resultado.next()) {
			DesPrimaAntic = Integer.parseInt(resultado.getString(1));
		}

		//consulta para validar  prima menor a 24 meses
		
        if(Integer.valueOf(Plazo)<DesPrimaAntic) {
		int periodoGracia = (int)Math.ceil((double)Integer.parseInt(TextoElemento(pestanasimuladorinternopage.InteresesInicialesSimuladorAnalista))/30);
		DesPrimaAntic = periodoGracia + Integer.valueOf(Plazo);  //TextoElemento(pestanasimuladorinternopage.InteresesInicialesSimuladorAnalista)
		}
        
        int PrimaPadre =0;
		ResultSet resultado4 = query.ValorPrimaCreditoPadre(Credito);
		while (resultado4.next()) {
			PrimaPadre = Integer.parseInt(resultado4.getString(1));
		}
		
		int MontoPadre =0;
			ResultSet resultado5 = query.ValorMontoCreditoPadre(Credito);
			while (resultado5.next()) {
				MontoPadre = Integer.parseInt(resultado5.getString(1));
	    }
		
		int MesesActivoPadre =0;
			ResultSet resultado6 = query.MesesActivoPadre(Credito);
			while (resultado6.next()) {
				MesesActivoPadre = Integer.parseInt(resultado6.getString(1));
	    }
		
		int Tasaxmillonseguro = 4625;
		System.out.println(" Variable prima: "+prima);
		int PrimaNoDevengada=0;

		if (prima == "") {
			int calculoMontoSoli = (int) MontoaSolicitar(Monto, DesPrimaAntic, Tasaxmillonseguro);
			int PrimaAnticipadaSeguro = (int) PrimaAnticipadaSeguro(calculoMontoSoli, 1000000, Tasaxmillonseguro,DesPrimaAntic);
			ToleranciaPesoMensaje(" Prima anticipada de seguro ",Integer.parseInt(TextoElemento(pestanasimuladorinternopage.PrimaAnticipadaSeguroAsesor)),PrimaAnticipadaSeguro);
		}else {
			int calculoMontoSoli = (int) MontoaSolicitar(Monto, DesPrimaAntic, Tasaxmillonseguro);
			int PrimaAnticipadaSeguro = (int) PrimaAnticipadaSeguro(calculoMontoSoli, 1000000, Tasaxmillonseguro,DesPrimaAntic);
			ToleranciaPesoMensaje(" Prima anticipada de seguro ",Integer.parseInt(TextoElemento(pestanasimuladorinternopage.PrimaAnticipadaSeguroAsesor)),PrimaAnticipadaSeguro);
			int PrimaNeta = (int) PrimaNeta(PrimaPadre,MontoPadre,MesesActivoPadre,PrimaAnticipadaSeguro, 1000000, Tasaxmillonseguro,DesPrimaAntic);
		    ToleranciaPesoMensaje(" Prima neta", Integer.parseInt(TextoElemento(pestanasimuladorinternopage.PrimaNetaSimuladorAnalista)), PrimaNeta);
			
		    PrimaNoDevengada = (int) PrimaNoDevengadaCPadre(PrimaPadre,MontoPadre,MesesActivoPadre,PrimaAnticipadaSeguro, 1000000, Tasaxmillonseguro,DesPrimaAntic);
		    ToleranciaPesoMensaje(" Prima neta No devengada", Integer.parseInt(TextoElemento(pestanasimuladorinternopage.PrimaNoDevengadaSimuladorAnalista)), PrimaNoDevengada);
		 
		}
		
		if (TextoElemento(pestanasimuladorinternopage.ValoraDesembolsar).contains(".") == true) {
			ToleranciaPesoMensaje(" Valor Desembolsar IF ",Integer.parseInt(TextoElemento(pestanasimuladorinternopage.ValoraDesembolsar)
					.substring(0, TextoElemento(pestanasimuladorinternopage.ValoraDesembolsar).length() - 2).replaceAll("[^a-zA-Z0-9]", "")), (Integer.parseInt(retanqueo))+PrimaNoDevengada);
			System.out.println("dentro del if que contiene punto");
		} else {
			ToleranciaPesoMensaje("Valor Desembolsar ELSE ",Integer.parseInt(TextoElemento(pestanasimuladorinternopage.ValoraDesembolsar)),(Integer.parseInt(retanqueo))+PrimaNoDevengada);
			System.out.println("dentro del else que no contiene punto");
		}
		
		adjuntarCaptura("Validar valores del simulador analista ");	
            
    }
	
	public void ValidarSimuladorAnalistaRetanqueos(String anno,String Credito,String retanqueo,String fecha,String Mes, String Plazo,String Ingresos, String descLey, String descNomina, String cartera,String DiasHabilesIntereses) throws InterruptedException, SQLException{
    	esperaExplicita(pestanasimuladorinternopage.MesDeAfecatcion);
    	hacerClick(pestanasimuladorinternopage.FechaDesembolso);
    	Clear(pestanasimuladorinternopage.FechaDesembolso);
    	EscribirElemento(pestanasimuladorinternopage.FechaDesembolso, fecha);  
    	EnviarEnter(pestanasimuladorinternopage.FechaDesembolso);
    	hacerClick(pestanasimuladorinternopage.MesDeAfecatcion);    	
		ElementVisible();
		selectValorLista(pestanasimuladorinternopage.ListaMes, Mes);
		ElementVisible();
		Clear(pestanasimuladorinternopage.Anno);
		EscribirElemento(pestanasimuladorinternopage.Anno, anno);
		hacerClick(pestanasimuladorinternopage.FechasManuales);
		ElementVisible();
		hacerClick(pestanasimuladorinternopage.CalcularDesglose);
		ElementVisible();
		hacerClicknotificacion();
		esperaExplicitaNopresente(pestanadigitalizacionPage.Notificacion);
		Monto=Integer.parseInt(TextoElemento(pestanasimuladorinternopage.MontoSolicitado));
		
		int Gmf4100 = (int) Gmf4100(Integer.parseInt(cartera), 0.004);
		assertvalidarEquals(TextoElemento(pestanasimuladorinternopage.Gravamento4x1000), String.valueOf(Gmf4100));
		
		int DescuentosPorCartera = ((Gmf4100 + Integer.parseInt(cartera)));
		
		int VlrDesembolsar = (Integer.parseInt(retanqueo)) - DescuentosPorCartera;
		System.out.println("vlrdesembolso  "+ VlrDesembolsar + "  descuentos cartera   "+ DescuentosPorCartera + "  retanqueo   "+ retanqueo + "  Valor Sistema  " + TextoElemento(pestanasimuladorinternopage.ValoraDesembolsar).substring(0,TextoElemento(pestanasimuladorinternopage.ValoraDesembolsar).length()-2).replaceAll("[^a-zA-Z0-9]","") );

		String valordesembolsar;
		
		// consulta base de datos calculo de prima true o false
				String prima = "";
				OriginacionCreditoQuery query = new OriginacionCreditoQuery();
				ResultSet resultadoPrima = query.CalculoPrima(Credito);
				while (resultadoPrima.next()) {
					prima = resultadoPrima.getString(1);
				}
				
				// consulta base de datos descuento prima anticipada
				int DesPrimaAntic = 0;
				ResultSet resultado = query.ConsultaDescuentoPrimaAntic();
				while (resultado.next()) {
					DesPrimaAntic = Integer.parseInt(resultado.getString(1));
				}

				//consulta para validar  prima menor a 24 meses
				
		        if(Integer.valueOf(Plazo)<DesPrimaAntic) {
		    	int periodoGracia = (int)Math.ceil((double)Integer.parseInt(TextoElemento(pestanasimuladorinternopage.InteresesInicialesSimuladorAnalista))/30);
				DesPrimaAntic = periodoGracia + Integer.valueOf(Plazo);
				}
				
		        int PrimaPadre =0;
				ResultSet resultado4 = query.ValorPrimaCreditoPadre(Credito);
				while (resultado4.next()) {
					PrimaPadre = Integer.parseInt(resultado4.getString(1));
				}
				
				int MontoPadre =0;
					ResultSet resultado5 = query.ValorMontoCreditoPadre(Credito);
					while (resultado5.next()) {
						MontoPadre = Integer.parseInt(resultado5.getString(1));
			    }
				
				int MesesActivoPadre =0;
					ResultSet resultado6 = query.MesesActivoPadre(Credito);
					while (resultado6.next()) {
						MesesActivoPadre = Integer.parseInt(resultado6.getString(1));
			    }	
		        
		        
				int Tasaxmillonseguro = 4625;
				int PrimaNoDevengada =0;

				if (prima == "") {
					int calculoMontoSoli = (int) MontoaSolicitar(Monto, DesPrimaAntic, Tasaxmillonseguro);
					int PrimaAnticipadaSeguro = (int) PrimaAnticipadaSeguro(calculoMontoSoli, 1000000, Tasaxmillonseguro,DesPrimaAntic);
					ToleranciaPeso(Integer.parseInt(TextoElemento(pestanasimuladorinternopage.PrimaAnticipadaSeguroAsesor)),PrimaAnticipadaSeguro);
				}else {
					int calculoMontoSoli = (int) MontoaSolicitar(Monto, DesPrimaAntic, Tasaxmillonseguro);
					int PrimaAnticipadaSeguro = (int) PrimaAnticipadaSeguro(calculoMontoSoli, 1000000, Tasaxmillonseguro,DesPrimaAntic);
					int PrimaNeta = (int) PrimaNeta(PrimaPadre,MontoPadre,MesesActivoPadre,PrimaAnticipadaSeguro, 1000000, Tasaxmillonseguro,DesPrimaAntic);
				    ToleranciaPesoMensaje(" Prima neta", Integer.parseInt(TextoElemento(pestanasimuladorinternopage.PrimaNeta)), PrimaNeta);
					ToleranciaPesoMensaje(" Prima anticipada de seguro ",Integer.parseInt(TextoElemento(pestanasimuladorinternopage.PrimaNetaSimuladorAnalista)),PrimaAnticipadaSeguro);
					PrimaNoDevengada = (int) PrimaNoDevengadaCPadre(PrimaPadre,MontoPadre,MesesActivoPadre,PrimaAnticipadaSeguro, 1000000, Tasaxmillonseguro,DesPrimaAntic);
					ToleranciaPesoMensaje(" Prima neta No devengada", Integer.parseInt(TextoElemento(pestanasimuladorinternopage.PrimaNoDevengadaSimuladorAnalista)), PrimaNoDevengada);
					 
				}
		
		if (TextoElemento(pestanasimuladorinternopage.ValoraDesembolsar).contains(".")==true) {
			ToleranciaPesoMensaje(" Valor Desembolsar IF ",Integer.parseInt(TextoElemento(pestanasimuladorinternopage.ValoraDesembolsar).substring(0,TextoElemento(pestanasimuladorinternopage.ValoraDesembolsar).length()-2).replaceAll("[^a-zA-Z0-9]", "")),(VlrDesembolsar)+PrimaNoDevengada);
	    	   System.out.println("dentro del if que contiene punto");
		}else {
			ToleranciaPesoMensaje(" Valor Desembolsar ELSE ",Integer.parseInt(TextoElemento(pestanasimuladorinternopage.ValoraDesembolsar)),(VlrDesembolsar)+PrimaNoDevengada);
	    		   System.out.println("dentro del else que no contiene punto");	    
		}
    }
	
	public void ValidarSimuladorAnalistaRetanqueosCarteraSaneamiento(String anno,String Credito,String retanqueo,String fecha,String Mes, String Plazo,String Ingresos, String descLey, String descNomina, String Cartera, String Saneamiento,String DiasHabilesIntereses) throws InterruptedException, SQLException{
    	esperaExplicita(pestanasimuladorinternopage.MesDeAfecatcion);
    	hacerClick(pestanasimuladorinternopage.FechaDesembolso);
    	Clear(pestanasimuladorinternopage.FechaDesembolso);
    	EscribirElemento(pestanasimuladorinternopage.FechaDesembolso, fecha);  
    	EnviarEnter(pestanasimuladorinternopage.FechaDesembolso);
    	hacerClick(pestanasimuladorinternopage.MesDeAfecatcion);    	
		ElementVisible();
		selectValorLista(pestanasimuladorinternopage.ListaMes, Mes);
		ElementVisible();
		Clear(pestanasimuladorinternopage.Anno);
		EscribirElemento(pestanasimuladorinternopage.Anno, anno);
		hacerClick(pestanasimuladorinternopage.FechasManuales);
		ElementVisible();
		hacerClick(pestanasimuladorinternopage.CalcularDesglose);
		ElementVisible();
		hacerClicknotificacion();
		esperaExplicitaNopresente(pestanadigitalizacionPage.Notificacion);
		Monto=Integer.parseInt(TextoElemento(pestanasimuladorinternopage.MontoSolicitado));
		
		
		int TotalCarteras = (Integer.parseInt(Cartera)+Integer.parseInt(Saneamiento));		
		int Gmf4100 = (int) Gmf4100(TotalCarteras, 0.004);
		int DescuentosPorCartera = ((Gmf4100 + TotalCarteras));		
		
		// consulta base de datos calculo de prima true o false
		String prima ="" ;
		OriginacionCreditoQuery query = new OriginacionCreditoQuery();
		ResultSet resultadoPrima = query.CalculoPrima(Credito);
		while (resultadoPrima.next()) {
			prima = resultadoPrima.getString(1);
		}
		
		// consulta base de datos descuento prima anticipada
				int DesPrimaAntic = 0;
				ResultSet resultado = query.ConsultaDescuentoPrimaAntic();
				while (resultado.next()) {
					DesPrimaAntic = Integer.parseInt(resultado.getString(1));
				}
		
	   //consulta para validar  prima menor a 24 meses
				
		   if(Integer.valueOf(Plazo)<DesPrimaAntic) {
		    int periodoGracia = (int)Math.ceil((double)Integer.parseInt(TextoElemento(pestanasimuladorinternopage.InteresesInicialesSimuladorAnalista))/30);
			DesPrimaAntic = periodoGracia + Integer.valueOf(Plazo);
				}
		   
		   int PrimaPadre =0;
			ResultSet resultado4 = query.ValorPrimaCreditoPadre(Credito);
			while (resultado4.next()) {
				PrimaPadre = Integer.parseInt(resultado4.getString(1));
			}
			
			int MontoPadre =0;
				ResultSet resultado5 = query.ValorMontoCreditoPadre(Credito);
				while (resultado5.next()) {
					MontoPadre = Integer.parseInt(resultado5.getString(1));
		    }
			
			int MesesActivoPadre =0;
				ResultSet resultado6 = query.MesesActivoPadre(Credito);
				while (resultado6.next()) {
					MesesActivoPadre = Integer.parseInt(resultado6.getString(1));
		    }	
				
				
		int Tasaxmillonseguro = 4625;
		int PrimaNoDevengada =0;
		
		if(prima=="") {			
			int calculoMontoSoli = (int) MontoaSolicitar(Monto, DesPrimaAntic, Tasaxmillonseguro);
						
			int PrimaAnticipadaSeguro = (int) PrimaAnticipadaSeguro(calculoMontoSoli, 1000000, Tasaxmillonseguro,DesPrimaAntic);	
			ToleranciaPeso(Integer.parseInt(TextoElemento(pestanasimuladorinternopage.PrimaAnticipadaSeguroAsesor)),PrimaAnticipadaSeguro);
		}else {
			int calculoMontoSoli = (int) MontoaSolicitar(Monto, DesPrimaAntic, Tasaxmillonseguro);
			int PrimaAnticipadaSeguro = (int) PrimaAnticipadaSeguro(calculoMontoSoli, 1000000, Tasaxmillonseguro,DesPrimaAntic);
			
			int PrimaNeta = (int) PrimaNeta(PrimaPadre,MontoPadre,MesesActivoPadre,PrimaAnticipadaSeguro, 1000000, Tasaxmillonseguro,DesPrimaAntic);	
		    ToleranciaPesoMensaje(" Prima neta", Integer.parseInt(TextoElemento(pestanasimuladorinternopage.PrimaNeta)), PrimaNeta);
			ToleranciaPesoMensaje(" Prima anticipada de seguro ",Integer.parseInt(TextoElemento(pestanasimuladorinternopage.PrimaNetaSimuladorAnalista)),PrimaAnticipadaSeguro);
			PrimaNoDevengada = (int) PrimaNoDevengadaCPadre(PrimaPadre,MontoPadre,MesesActivoPadre,PrimaAnticipadaSeguro, 1000000, Tasaxmillonseguro,DesPrimaAntic);
			ToleranciaPesoMensaje(" Prima neta No devengada ", Integer.parseInt(TextoElemento(pestanasimuladorinternopage.PrimaNoDevengadaSimuladorAnalista)), PrimaNoDevengada);

		}		
		
			
		if (TextoElemento(pestanasimuladorinternopage.ValoraDesembolsar).contains(".")==true) {
			ToleranciaPesoMensaje(" Valor Desembolsar IF ",Integer.parseInt(TextoElemento(pestanasimuladorinternopage.ValoraDesembolsar).substring(0,TextoElemento(pestanasimuladorinternopage.ValoraDesembolsar).length()-2).replaceAll("[^a-zA-Z0-9]", "")),(Integer.parseInt(retanqueo)-DescuentosPorCartera)+PrimaNoDevengada);
		System.out.println("dentro del if que contiene punto");
		}else {
			ToleranciaPesoMensaje(" Valor Desembolsar ELSE ",Integer.parseInt(TextoElemento(pestanasimuladorinternopage.ValoraDesembolsar)),(Integer.parseInt(retanqueo)-DescuentosPorCartera)+PrimaNoDevengada);
		System.out.println("dentro del else que no contiene punto");
		}
		
    }
	
	

	public void DescargarMediosdedispercionRetanqueo(String Monto,String Banco, String Pdf) {

		
		panelnavegacionaccion.CreditoParaDesembolsoDescargar();
		esperaExplicita(PagesCreditosDesembolso.FiltroMonto);
		EscribirElemento(PagesCreditosDesembolso.FiltroMonto, String.valueOf(Monto));
		ElementVisible();	
		
		String pattern = "###,###,###.###";
		double value = Double.parseDouble(Monto);

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
	
	

	public void ValidarValoresLlamadoBienvenidaRetanqueo(String Credito) throws NumberFormatException, SQLException {
		recorerpestanas("CONDICIONES DEL CRÉDITO");	
	
		
		ResultSet resultado;
		
		String [] ValoresCredito=RetornarStringListWebElemen(pagesclienteparabienvenida.ValoresCondicionesCredito);
		
		// consulta base de datos
		int DesPrimaAntic = 0;
		OriginacionCreditoQuery query = new OriginacionCreditoQuery();
		resultado = query.ConsultaDescuentoPrimaAntic();
		while (resultado.next()) {
			DesPrimaAntic = Integer.parseInt(resultado.getString(1));
		}
		
        //consulta para validar  prima menor a 24 meses
		
        if(Integer.parseInt(ValoresCredito[1])<DesPrimaAntic) {
		int periodoGracia = (int)Math.ceil((double)Integer.parseInt(ValoresCredito[7])/30);
		DesPrimaAntic = periodoGracia + Integer.parseInt(ValoresCredito[1]);
		}
        		
		log.info("******** Valor de prima **** " + DesPrimaAntic);
		
		//Tasa fianza
		double TasaFianza =0;
		resultado = query.porcentajefianza();
		while (resultado.next()) {
			TasaFianza = Double.parseDouble(resultado.getString(1));
		}
		log.info("TasaFianza "+ TasaFianza);
		//Estudio Credito
		double EstudioCredito = 0;
		resultado = query.EstudioCredito();
		while (resultado.next()) {
			EstudioCredito = Double.parseDouble(resultado.getString(1));
		}
		log.info("EstudioCredito "+ EstudioCredito);
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
		double tasaUno = Double.parseDouble(ValoresCredito[2])/100;

		int PrimaPadre =0;
		ResultSet resultado4 = query.ValorPrimaCreditoPadre(Credito);
		while (resultado4.next()) {
		PrimaPadre = Integer.parseInt(resultado4.getString(1));
		}
			
		int MontoPadre =0;
		ResultSet resultado5 = query.ValorMontoCreditoPadre(Credito);
		while (resultado5.next()) {
		MontoPadre = Integer.parseInt(resultado5.getString(1));
		}
			
		int MesesActivoPadre =0;
		ResultSet resultado6 = query.MesesActivoPadre(Credito);
		while (resultado6.next()) {
		MesesActivoPadre = Integer.parseInt(resultado6.getString(1));
		}
		
		// consulta base de datos calculo de prima true o false
		String prima = "";
		ResultSet resultadoPrima = query.CalculoPrima(Credito);
		while (resultadoPrima.next()) {
		prima = resultadoPrima.getString(1);
		}
		
		// Valores para la funciones estaticos
		int Tasaxmillonseguro = 4625;				
		double variableFianza = 1.19;
		int SaldoAlDia=0;	
		
		if(ValidarElementoPresente(pagesclienteparabienvenida.SaldoAlDia)==false) {
			int coma = 	GetText(pagesclienteparabienvenida.ValorSaldoAlDia).indexOf(",");
			GetText(pagesclienteparabienvenida.ValorSaldoAlDia);
			if(coma==-1) {
				SaldoAlDia=Integer.parseInt(GetText(pagesclienteparabienvenida.ValorSaldoAlDia).replace(".","").replace(",","."));	
				System.out.println(" Resultado de valor SALDO AL DIA IF "+SaldoAlDia);
	        	}
	        	else {
	        		SaldoAlDia=Integer.parseInt(GetText(pagesclienteparabienvenida.ValorSaldoAlDia).substring(0,coma).replace(".",""));
	            	System.out.println(" Resultado de valor SALDO AL DIA ELSE "+SaldoAlDia);
	        	}
		}
		
		log.info("suma retanqueo y saldo al dia mas prima neta "+ (Integer.parseInt(ValoresCredito[13])+SaldoAlDia+Integer.parseInt(ValoresCredito[12])));
		int calculoMontoSoli = (int) MontoaSolicitar(Integer.parseInt(ValoresCredito[13])+SaldoAlDia+Integer.parseInt(ValoresCredito[12]), DesPrimaAntic, Tasaxmillonseguro);
		
		int PrimaAnticipadaSeguro = (int) PrimaAnticipadaSeguro(Integer.parseInt(ValoresCredito[13])+SaldoAlDia+Integer.parseInt(ValoresCredito[12]), 1000000, Tasaxmillonseguro,DesPrimaAntic);
		ToleranciaPesoMensaje(" Prima Anticipada ", Integer.parseInt(ValoresCredito[10]), PrimaAnticipadaSeguro);
		System.out.println("######## CALCULO DE PRIMA ######## "+PrimaAnticipadaSeguro+" "+ValoresCredito[13].isEmpty()+" "+DesPrimaAntic);
		int PrimaNoDevengada=0;
		
		if (prima != "") {
			int PrimaNeta = (int) PrimaNeta(PrimaPadre,MontoPadre,MesesActivoPadre,PrimaAnticipadaSeguro, 1000000, Tasaxmillonseguro,DesPrimaAntic);
		    ToleranciaPesoMensaje(" Prima neta", Integer.parseInt(ValoresCredito[12]), PrimaNeta);
		    PrimaNoDevengada = (int) PrimaNoDevengadaCPadre(PrimaPadre,MontoPadre,MesesActivoPadre,PrimaAnticipadaSeguro, 1000000, Tasaxmillonseguro,DesPrimaAntic);
		    ToleranciaPesoMensaje(" Prima neta no Devengada", Integer.parseInt(ValoresCredito[11]), PrimaNoDevengada);
		}	
		
		
		
		if(ValoresCredito[11].isEmpty()==true) {
		 calculoMontoSoli=calculoMontoSoli-PrimaAnticipadaSeguro;
		 //cambiar por CXC
		// ToleranciaPesoMensaje("###### ERROR CALCULANDO MONTO SOLICITUD IF ########",Integer.parseInt(ValoresCredito[0]),calculoMontoSoli);
		}else {
		 //cambiar por CXC
		// ToleranciaPesoMensaje("###### ERROR CALCULANDO MONTO SOLICITUD ELSE ########",Integer.parseInt(ValoresCredito[0]),calculoMontoSoli);
		}
		
		int ValorFianza = (int) ValorFianza(Integer.parseInt(ValoresCredito[13])+SaldoAlDia+Integer.parseInt(ValoresCredito[12]), TasaFianza, variableFianza);
		ToleranciaPesoMensaje("###### ERROR SIM ASESOR - CALCULANDO VALOR FIANZA ########",Integer.parseInt(ValoresCredito[16]),ValorFianza);				
		int EstudioCreditoIva = (int) EstudioCreditoIvacxc(Integer.parseInt(ValoresCredito[13])+SaldoAlDia+Integer.parseInt(ValoresCredito[12]), EstudioCredito);
		ToleranciaPesoMensaje("###### ERROR SIM ASESOR - CALCULANDO ESTUDIO CREDITO ########",Integer.parseInt(ValoresCredito[17]),EstudioCreditoIva);
	
		
		
	}

     public void AprobarExcepciones(String Pdf,String Cedula) throws InterruptedException {
    	 hacerClick(pestanasimuladorinternopage.DetalleExcepciones);
    	 ElementVisible();
    	 esperaExplicita(pestanasimuladorinternopage.SolicitarAprobacion);
    	 cargarpdf(pestanasimuladorinternopage.SoportePdfExcepciones,Pdf);
    	 esperaExplicita(pestanasimuladorinternopage.Notificacion);
    	 hacerClicknotificacion();
    	 hacerClick(pestanasimuladorinternopage.SolicitarAprobacion);
    	 esperaExplicita(pestanasimuladorinternopage.Notificacion);
    	 String notificacion = GetText(pestanasimuladorinternopage.Notificacion);
    	 if (notificacion.contains("Se han enviado solicitudes de aprobación para estas excepciones de tipo")) {
    		 hacerClicknotificacion();
    		 panelnavegacionaccion.navegarTareas();
    		 esperaExplicita(pagestareas.filtroDescipcion);
    	     EscribirElemento(pagestareas.filtroDescipcion, Cedula);
    	     ElementVisible();
    	     EscribirElemento(pagestareas.filtroTarea,"Revisar Aprobación excepción");
    	     ElementVisible();
    	     hacerClick(pagestareas.EditarVer);
    		 ElementVisible();
    		 esperaExplicita(pagestareas.Aprobar);
    		 Hacer_scroll_centrado(pagestareas.Aprobar);
    		 hacerClick(pagestareas.Aprobar);
    		 ElementVisible();
    		 hacerClickVariasNotificaciones();
    		 esperaExplicita(pagestareas.Guardar);
    		 Hacer_scroll(pagestareas.Guardar);
    		 hacerClick(pagestareas.Guardar);
    		 hacerClicknotificacion();
    	     
 		}else {
 			assertTrue("#### Error Aprobar excepcion por Perfil "+notificacion ,false);
 		}
    
     }

	/************ FIN RETANQUEO CREDITOS **********/
}
