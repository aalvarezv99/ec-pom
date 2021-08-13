package Acciones.CreditoAccion;

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
import Pages.ComunesPage.PanelNavegacionPage;
import Pages.CreditosPage.PagesClienteParaBienvenida;
import Pages.CreditosPage.PagesCreditosDesembolso;
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
		Thread.sleep(1000);
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
		Thread.sleep(10000);
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
		LimpiarConTeclado(retanqueopages.vlrCompra);
		EscribirElemento(retanqueopages.vlrCompra, VlrCompraSaneamiento);
		ElementVisible();
		hacerClick(retanqueopages.inputdDescNomina);
		ElementVisible();

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
			
		
		if(prima=="") {			
		int calculoMontoSoli = (int) MontoaSolicitar(Monto, DesPrimaAntic, Tasaxmillonseguro);
		ToleranciaPeso(Integer.parseInt(TextoElemento(pestanasimuladorinternopage.ResultMontoSoli)),calculoMontoSoli);
					
		int PrimaAnticipadaSeguro = (int) PrimaAnticipadaSeguro(calculoMontoSoli, 1000000, Tasaxmillonseguro,DesPrimaAntic);	
		ToleranciaPeso(Integer.parseInt(TextoElemento(pestanasimuladorinternopage.PrimaAnticipadaSeguro)),PrimaAnticipadaSeguro);
		int Gmf4100 = (int) Gmf4100(Integer.parseInt(VlrCompraSaneamiento), 0.004);			
	    ToleranciaPeso(Integer.parseInt(TextoElemento(pestanasimuladorinternopage.SimuladorInternorValoraDesembolsar)),VlrRetanqueo-(Gmf4100+Integer.parseInt(VlrCompraSaneamiento)));
//////////////////////////////////////////////////////////////////////////////////////////////////////////

		int CuotaCorriente = (int) CuotaCorriente(calculoMontoSoli, Double.parseDouble(Tasa), Integer.parseInt(Plazo));
		assertvalidarEquals(TextoElemento(pestanasimuladorinternopage.CuotaCorriente), String.valueOf(CuotaCorriente));

		int EstudioCreditoIva = (int) EstudioCreditoIva(calculoMontoSoli, EstudioCredito);
		assertvalidarEquals(TextoElemento(pestanasimuladorinternopage.EstudioCreditoIVA),
				String.valueOf(EstudioCreditoIva));

		int ValorFianza = (int) ValorFianza(calculoMontoSoli, TasaFianza, variableFianza);
	    ToleranciaPeso(Integer.parseInt(TextoElemento(pestanasimuladorinternopage.ValorFianza)),ValorFianza);


//////////////////////////////////////////////////////////////////////////////////////////////////////////		
		
		}else {
			
		int Gmf4100 = (int) Gmf4100(Integer.parseInt(VlrCompraSaneamiento), 0.004);		
		ToleranciaPeso(Integer.parseInt(TextoElemento(pestanasimuladorinternopage.ResultMontoSoli)),Monto);
	    ToleranciaPeso(Integer.parseInt(TextoElemento(pestanasimuladorinternopage.SimuladorInternorValoraDesembolsar)),VlrRetanqueo-(Gmf4100+Integer.parseInt(VlrCompraSaneamiento)));

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
	public void ValidarSimuladorAnalistaRetanqueos(String retanqueo,String fecha,String Mes, String Plazo,String Ingresos, String descLey, String descNomina,String Credito,String DiasHabilesIntereses) throws InterruptedException, SQLException{
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
		
		int Tasaxmillonseguro = 4625;

		if (prima == "") {
			int calculoMontoSoli = (int) MontoaSolicitar(Monto, DesPrimaAntic, Tasaxmillonseguro);
			int PrimaAnticipadaSeguro = (int) PrimaAnticipadaSeguro(calculoMontoSoli, 1000000, Tasaxmillonseguro,DesPrimaAntic);
			ToleranciaPeso(Integer.parseInt(TextoElemento(pestanasimuladorinternopage.PrimaAnticipadaSeguroAsesor)),PrimaAnticipadaSeguro);
		}

		if (TextoElemento(pestanasimuladorinternopage.ValoraDesembolsar).contains(".") == true) {
			ToleranciaPeso(Integer.parseInt(TextoElemento(pestanasimuladorinternopage.ValoraDesembolsar)
					.substring(0, TextoElemento(pestanasimuladorinternopage.ValoraDesembolsar).length() - 2).replaceAll("[^a-zA-Z0-9]", "")), (Integer.parseInt(retanqueo)));
			System.out.println("dentro del if que contiene punto");
		} else {
			ToleranciaPeso(Integer.parseInt(TextoElemento(pestanasimuladorinternopage.ValoraDesembolsar)),(Integer.parseInt(retanqueo)));
			System.out.println("dentro del else que no contiene punto");
		}
    
    }
	
	public void ValidarSimuladorAnalistaRetanqueos(String retanqueo,String fecha,String Mes, String Plazo,String Ingresos, String descLey, String descNomina, String cartera, String Credito,String DiasHabilesIntereses) throws InterruptedException, SQLException{
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
				
				int Tasaxmillonseguro = 4625;

				if (prima == "") {
					int calculoMontoSoli = (int) MontoaSolicitar(Monto, DesPrimaAntic, Tasaxmillonseguro);
					int PrimaAnticipadaSeguro = (int) PrimaAnticipadaSeguro(calculoMontoSoli, 1000000, Tasaxmillonseguro,DesPrimaAntic);
					ToleranciaPeso(Integer.parseInt(TextoElemento(pestanasimuladorinternopage.PrimaAnticipadaSeguroAsesor)),PrimaAnticipadaSeguro);
				}
		
		if (TextoElemento(pestanasimuladorinternopage.ValoraDesembolsar).contains(".")==true) {
			ToleranciaPeso(Integer.parseInt(TextoElemento(pestanasimuladorinternopage.ValoraDesembolsar).substring(0,TextoElemento(pestanasimuladorinternopage.ValoraDesembolsar).length()-2).replaceAll("[^a-zA-Z0-9]", "")),(VlrDesembolsar));
	    	   System.out.println("dentro del if que contiene punto");
		}else {
	    		   ToleranciaPeso(Integer.parseInt(TextoElemento(pestanasimuladorinternopage.ValoraDesembolsar)),(VlrDesembolsar));
	    		   System.out.println("dentro del else que no contiene punto");	    
		}
    }
	
	public void ValidarSimuladorAnalistaRetanqueosCarteraSaneamiento(String Credito,String retanqueo,String fecha,String Mes, String Plazo,String Ingresos, String descLey, String descNomina, String Cartera, String Saneamiento,String DiasHabilesIntereses) throws InterruptedException, SQLException{
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
				
				
		int Tasaxmillonseguro = 4625;
		
		
		if(prima=="") {			
			int calculoMontoSoli = (int) MontoaSolicitar(Monto, DesPrimaAntic, Tasaxmillonseguro);
						
			int PrimaAnticipadaSeguro = (int) PrimaAnticipadaSeguro(calculoMontoSoli, 1000000, Tasaxmillonseguro,DesPrimaAntic);	
			ToleranciaPeso(Integer.parseInt(TextoElemento(pestanasimuladorinternopage.PrimaAnticipadaSeguroAsesor)),PrimaAnticipadaSeguro);
		}
		
		
			
		if (TextoElemento(pestanasimuladorinternopage.ValoraDesembolsar).contains(".")==true) {
		ToleranciaPeso(Integer.parseInt(TextoElemento(pestanasimuladorinternopage.ValoraDesembolsar).substring(0,TextoElemento(pestanasimuladorinternopage.ValoraDesembolsar).length()-2).replaceAll("[^a-zA-Z0-9]", "")),(Integer.parseInt(retanqueo)-DescuentosPorCartera));
		System.out.println("dentro del if que contiene punto");
		}else {
		ToleranciaPeso(Integer.parseInt(TextoElemento(pestanasimuladorinternopage.ValoraDesembolsar)),(Integer.parseInt(retanqueo)-DescuentosPorCartera));
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

	/************ FIN RETANQUEO CREDITOS **********/
}
