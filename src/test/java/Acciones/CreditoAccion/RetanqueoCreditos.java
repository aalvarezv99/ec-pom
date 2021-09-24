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
	private static Logger log = Logger.getLogger(RetanqueoCreditos.class);
	static int Monto;
	static int Remanente;
	static int SaldoAlDia;
	static int VlrRetanqueo;
	double vlrIva = 1.19;

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
		log.info("************ Buscar credito a retanquear, RetanqueoCreditos - FiltrarCredito()*****");
		try {
			BuscarenGrilla(retanqueopages.cedula, Cedula);
			Thread.sleep(5000);
			ElementVisible();
			BuscarenGrilla(retanqueopages.credito, Credito);
			Thread.sleep(5000);
			ElementVisible();
			esperaExplicitaTexto(Credito);
		} catch (Exception e) {
			log.error("########## ERROR RetanqueoCreditos - FiltrarCredito() ########" + e);
			assertTrue("########## ERROR RetanqueoCreditos - FiltrarCredito() ########"+ e,false);
		}
	}

	public void Retanquear() throws InterruptedException {
		log.info("*********Seleccionar el boton retanqueo, RetanqueoCreditos - Retanquear()******");
		try {
			ElementVisible();
			hacerClick(retanqueopages.BtnRentaqueo);
			Thread.sleep(5000);
			ElementVisible();
			assertTextonotificacion(retanqueopages.notificacion, "Se ha generado un retanqueo para el");
		} catch (Exception e) {
			log.error("########## ERROR RetanqueoCreditos - Retanquear() ########" + e);
			assertTrue("########## ERROR RetanqueoCreditos - Retanquear() ########"+ e,false);
		}
		
	}

	public void Credito(String Cedula) throws InterruptedException {
		log.info("*********Buscar Credito en solicitud, RetanqueoCreditos - Credito()********");
		try {
			panelnavegacionaccion.navegarCreditoSolicitud();
			BuscarenGrilla(retanqueopages.inputCedula, Cedula);
			ElementVisible();
			esperaExplicitaTexto(Cedula);
		} catch (Exception e) {
			log.error("########## ERROR RetanqueoCreditos - Credito() ########" + e);
			assertTrue("########## ERROR RetanqueoCreditos - Credito() ########"+ e,false);
		}
		
	}

	public void seleccionarRetanqueo() {
		log.info("****** seleccionar continuar retanqueo,  RetanqueoCreditos - seleccionarRetanqueo()******");
		try {
			ClicUltimoElemento(retanqueopages.continuar);
			ElementVisible();
		} catch (Exception e) {
			log.error("########## ERROR RetanqueoCreditos - seleccionarRetanqueo() ########" + e);
			assertTrue("########## ERROR RetanqueoCreditos - seleccionarRetanqueo() ########"+ e,false);
		}
		
	}

	public void borrarArchivos() throws InterruptedException {
		log.info("******** Borrar archivos, RetanqueoCreditos - borrarArchivos()********");
		try {
			clickvariosespera(retanqueopages.borrararchivos);	
		} catch (Exception e) {
			log.error("########## ERROR RetanqueoCreditos - borrarArchivos() ########" + e);
			assertTrue("########## ERROR RetanqueoCreditos - borrarArchivos() ########"+ e,false);
		}
		
	}

	public void CargarArchivos(String pdf) throws InterruptedException {
		log.info("*********Cargar los nuevod documento, RetanqueoCreditos - CargarArchivos()*******");
		try {
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
		} catch (Exception e) {
			log.error("########## ERROR RetanqueoCreditos - CargarArchivos() ########" + e);
			assertTrue("########## ERROR RetanqueoCreditos - CargarArchivos() ########"+ e,false);
		}
		

	}

	public void ConsultaCentrales() throws InterruptedException {
		log.info("******** consultar centrales,  RetanqueoCreditos - ConsultaCentrales()*********");
		try {
			ElementVisible();
			hacerClick(retanqueopages.BtnConsultaCentrales);
			ElementVisible();
			// assertTextonotificacion(retanqueopages.notificacion,"Se ha solicitado la
			// consulta en listas");
			//Thread.sleep(5000);
		} catch (Exception e) {
			log.error("########## ERROR RetanqueoCreditos - ConsultaCentrales() ########" + e);
			assertTrue("########## ERROR RetanqueoCreditos - ConsultaCentrales() ########"+ e,false);
		}
		
	}

	public void Seguridad() throws InterruptedException {
		log.info("******** espera credito viable, RetanqueoCreditos - Seguridad()******");
		try {
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
		} catch (Exception e) {
			log.error("########## ERROR RetanqueoCreditos - Seguridad() ########" + e);
			assertTrue("########## ERROR RetanqueoCreditos - Seguridad() ########"+ e,false);
		}
		
	}

	public void Simulador(String Retanqueo,String Tasa, String Plazo, String DiasHabilesIntereses, String Ingresos, String descLey,
			String descNomina,String VlrCompraSaneamiento) throws NumberFormatException, SQLException {
		
		log.info("********* llenar simulador interno RTANQ, RetanqueoCreditos - Simulador()*********");
		try {
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
		} catch (Exception e) {
			log.error("########## ERROR RetanqueoCreditos - Simulador() ########" + e);
			assertTrue("########## ERROR RetanqueoCreditos - Simulador() ########"+ e,false);
		}
	}

	/*
	 * TP - 31/08/2021, Se actualiza el simulador con retanqueo para prima mensual anticipada, se agrega el monto maximo sugerido
	 * 
	 * ThainePerez 17/09/2021, Se actualiza el calculo de est y Fianza del credito hijo, teniendo en cuenta los valores no cunsumidos del padre*/
	public void ValidarSimulador(String Ingresos, String descLey, String descNomin, String Tasa,
			String Plazo,String Credito,String DiasHabilesIntereses,String VlrCompraSaneamiento) throws NumberFormatException, SQLException {

		log.info("********Validar Simulador interno RETANQ, RetanqueoCreditos - ValidarSimulador()***********");
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
		
		String pagaduria = "";
		resultado = query.consultarPagaduriaRetanq(Credito);
		while (resultado.next()) {
			pagaduria = resultado.getString(1);
		}
		
		int colchon = 0;
		ResultSet resultadocolchon = query.colchonpagaduria(pagaduria);
		while (resultadocolchon.next()) {
			colchon = Integer.parseInt(resultadocolchon.getString(1));
		}
		log.info("Colchon Pagaduria " + colchon );

		
		//consulta para validar  prima menor a 24 meses
		
		if(Integer.valueOf(Plazo)<DesPrimaAntic) {
			int periodoGracia = (int)Math.ceil((double)Integer.parseInt(DiasHabilesIntereses)/30);
			DesPrimaAntic = periodoGracia + Integer.valueOf(Plazo);
		}
		log.info("********* Valor de prima " + DesPrimaAntic);
		
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
		log.info("****** Estudio de Credito" + EstudioCredito);
		
		double TasaFianza =0;
		ResultSet resultado3 = query.porcentajefianza();
		while (resultado3.next()) {
			TasaFianza = Double.parseDouble(resultado3.getString(1));
		}
		log.info("****** Tasa Fianza" + TasaFianza);
		
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
		
		/*Consultar Estudio Credito Padre*/
		int estudioCreditoPadre = 0;
		resultado = query.consultaEstudioCreditoPadre(Credito);			
		while (resultado.next()) {
			estudioCreditoPadre = Integer.parseInt(resultado.getString(1));
			log.info("******** Estudio Credito padre - "+estudioCreditoPadre+" ***********");
		}
		
		/*Consultar fianza Hijo*/		
		int fianzaPadre = 0;
		resultado = query.consultaFianzaCreditoPadre(Credito);			
		while (resultado.next()) {
			fianzaPadre = Integer.parseInt(resultado.getString(1));
			log.info("******** vlrFianzaPadre - "+fianzaPadre+" ***********");
		}
		
		if (prima == "") {

			int calculoMontoSoli = (int) MontoaSolicitar(Monto, DesPrimaAntic, Tasaxmillonseguro, EstudioCredito,
					TasaFianza, vlrIva);

			ToleranciaPesoMensaje("Monto Solicitado ",
					Integer.parseInt(TextoElemento(pestanasimuladorinternopage.ResultMontoSoli)), calculoMontoSoli);

			int PrimaAnticipadaSeguro = (int) PrimaAnticipadaSeguro(Monto, 1000000, Tasaxmillonseguro, DesPrimaAntic);
			ToleranciaPesoMensaje("Prima Anticipada de seguro ",
					Integer.parseInt(TextoElemento(pestanasimuladorinternopage.PrimaAnticipadaSeguro)),
					PrimaAnticipadaSeguro);

			//////////////////////////////////////////////////////////////////////////////////////////////////////////

			int CuotaCorriente = (int) CuotaCorriente(calculoMontoSoli, tasaUno, Integer.parseInt(Plazo), tasaDos,
					mesDos);
			ToleranciaPesoMensaje(" Cuota corriente ",
					Integer.parseInt(TextoElemento(pestanasimuladorinternopage.CuotaCorriente)), CuotaCorriente);

			int EstudioCreditoIva = (int) EstudioCreditoIva(Monto, EstudioCredito);

			int resultEstudioCredito = EstudioCreditoIva-estudioCreditoPadre;
			resultEstudioCredito=(resultEstudioCredito<0)?resultEstudioCredito*0:resultEstudioCredito;
			
			ToleranciaPesoMensaje("Estudio Credito IVA ",
							Integer.parseInt(TextoElemento(pestanasimuladorinternopage.EstudioCreditoIVA)), resultEstudioCredito);
			
			int ValorFianza = (int) ValorFianza(Monto, TasaFianza, variableFianza);
			int resultFianza = ValorFianza-fianzaPadre;
			resultFianza=(resultFianza<0)?resultFianza*0:resultFianza;
			ToleranciaPesoMensaje("Comparacion Fianza",Integer.parseInt(TextoElemento(pestanasimuladorinternopage.ValorFianza)), resultFianza);
			
			int MontoMaxDesembolsar = (int) MontoMaxDesembolsar(Integer.parseInt(Ingresos), Integer.parseInt(descLey),
					Integer.parseInt(descNomin), colchon, tasaUno, Integer.parseInt(Plazo), tasaDos, mesDos);

			int Gmf4100 = (int) Gmf4100(Integer.parseInt(VlrCompraSaneamiento), 0.004);
			
			int remantEstimado = (int) remanenteEstimadoRetanqueo(calculoMontoSoli,SaldoAlDia,resultFianza,resultEstudioCredito,Integer.parseInt(VlrCompraSaneamiento),Gmf4100,PrimaAnticipadaSeguro);
			ToleranciaPesoMensaje(" Valor Desembolsar ",
					Integer.parseInt(TextoElemento(pestanasimuladorinternopage.SimuladorInternorValoraDesembolsar)),remantEstimado);


		} else {

			int calculoMontoSoli = (int) MontoaSolicitar(Monto, DesPrimaAntic, Tasaxmillonseguro, EstudioCredito,
					TasaFianza, vlrIva);
			ToleranciaPesoMensaje("Monto Solicitado ",
					Integer.parseInt(TextoElemento(pestanasimuladorinternopage.ResultMontoSoli)), calculoMontoSoli);

			int PrimaAnticipadaSeguro = (int) PrimaAnticipadaSeguro(Monto, 1000000, Tasaxmillonseguro, DesPrimaAntic);
			ToleranciaPesoMensaje("Prima Anticipada de seguro ",
					Integer.parseInt(TextoElemento(pestanasimuladorinternopage.PrimaAnticipadaSeguro)),
					PrimaAnticipadaSeguro);

			int CuotaCorriente = (int) CuotaCorriente(calculoMontoSoli, tasaUno, Integer.parseInt(Plazo), tasaDos,
					mesDos);
			ToleranciaPesoMensaje(" Cuota corriente ",
					Integer.parseInt(TextoElemento(pestanasimuladorinternopage.CuotaCorriente)), CuotaCorriente);

			int EstudioCreditoIva = (int) EstudioCreditoIva(Monto, EstudioCredito);
			int resultEstudioCredito = EstudioCreditoIva-estudioCreditoPadre;
			resultEstudioCredito=(resultEstudioCredito<0)?resultEstudioCredito*0:resultEstudioCredito;
			ToleranciaPesoMensaje(" Estudio Credito IVA",
					Integer.parseInt(TextoElemento(pestanasimuladorinternopage.EstudioCreditoIVA)), resultEstudioCredito);

			int ValorFianza = (int) ValorFianza(Monto, TasaFianza, variableFianza);
			int resultFianza = ValorFianza-fianzaPadre;
			resultFianza=(resultFianza<0)?resultFianza*0:resultFianza;
			ToleranciaPeso(Integer.parseInt(TextoElemento(pestanasimuladorinternopage.ValorFianza)), resultFianza);

			int MontoMaxDesembolsar = (int) MontoMaxDesembolsar(Integer.parseInt(Ingresos), Integer.parseInt(descLey),
					Integer.parseInt(descNomin), colchon, tasaUno, Integer.parseInt(Plazo), tasaDos, mesDos);

			int Gmf4100 = (int) Gmf4100(Integer.parseInt(VlrCompraSaneamiento), 0.004);
			
			int remantEstimado = (int) remanenteEstimadoRetanqueo(calculoMontoSoli,SaldoAlDia,resultFianza,resultEstudioCredito,Integer.parseInt(VlrCompraSaneamiento),Gmf4100,PrimaAnticipadaSeguro);
			ToleranciaPesoMensaje(" Valor Desembolsar ",
					Integer.parseInt(TextoElemento(pestanasimuladorinternopage.SimuladorInternorValoraDesembolsar)),remantEstimado);

		}
	}

	public void SolicitarCredito() throws InterruptedException {
		log.info("*********** Presionar boton solicitar,  RetanqueoCreditos - SolicitarCredito()*********");
		try {
			Hacer_scroll_Abajo(pestanasimuladorinternopage.Solicitar);
			hacerClick(pestanasimuladorinternopage.Solicitar);
			ElementVisible();
			hacerClickVariasNotificaciones();
		} catch (Exception e) {
			log.error("########## ERROR RetanqueoCreditos - SolicitarCredito() ########" + e);
			assertTrue("########## ERROR RetanqueoCreditos - SolicitarCredito() ########"+ e,false);
		}
		
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
	
	public void ValidarSimuladorAnalistaRetanqueos(String retanqueo,String fecha,String Mes, String Plazo,String Ingresos, String descLey, String descNomina,String Credito,String DiasHabilesIntereses, String tasa) throws InterruptedException, SQLException{
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
		//ToleranciaPeso(Integer.parseInt(TextoElemento(pestanasimuladorinternopage.ValoraDesembolsar)),(Integer.parseInt(retanqueo)));
    	
		/*
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

		//consulta para validar  prima menor a 24 meses
		
        if(Integer.valueOf(Plazo)<DesPrimaAntic) {
		int periodoGracia = (int)Math.ceil((double)Integer.parseInt(TextoElemento(pestanasimuladorinternopage.InteresesInicialesSimuladorAnalista))/30);
		DesPrimaAntic = periodoGracia + Integer.valueOf(Plazo);
		}
		
		int Tasaxmillonseguro = 4625;

		if (prima == "") {
			int calculoMontoSoli = (int) MontoaSolicitar(Monto, DesPrimaAntic, Tasaxmillonseguro, EstudioCredito,
					TasaFianza, vlrIva);
			int PrimaAnticipadaSeguro = (int) PrimaAnticipadaSeguro(calculoMontoSoli, 1000000, Tasaxmillonseguro,DesPrimaAntic);
			ToleranciaPesoMensaje(" Prima anticipada de seguro ",Integer.parseInt(TextoElemento(pestanasimuladorinternopage.PrimaAnticipadaSeguroAsesor)),PrimaAnticipadaSeguro);
		}

		if (TextoElemento(pestanasimuladorinternopage.ValoraDesembolsar).contains(".") == true) {
			ToleranciaPesoMensaje(" Prima Valor Desembolsar IF ",Integer.parseInt(TextoElemento(pestanasimuladorinternopage.ValoraDesembolsar)
					.substring(0, TextoElemento(pestanasimuladorinternopage.ValoraDesembolsar).length() - 2).replaceAll("[^a-zA-Z0-9]", "")), (Integer.parseInt(retanqueo)));
			System.out.println("dentro del if que contiene punto");
		} else {
			ToleranciaPesoMensaje(" Prima Valor Desembolsar ELSE ",Integer.parseInt(TextoElemento(pestanasimuladorinternopage.ValoraDesembolsar)),(Integer.parseInt(retanqueo)));
			System.out.println("dentro del else que no contiene punto");
		}
        */    
    }
	
	public void ValidarSimuladorAnalistaRetanqueos(String retanqueo,String fecha,String Mes, String Plazo,String Ingresos, String descLey, String descNomina, String cartera, String Credito,String DiasHabilesIntereses, String tasa) throws InterruptedException, SQLException{
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

		// consulta para validar prima menor a 24 meses

		if (Integer.valueOf(Plazo) < DesPrimaAntic) {
			int periodoGracia = (int) Math.ceil((double) Integer
					.parseInt(TextoElemento(pestanasimuladorinternopage.InteresesInicialesSimuladorAnalista)) / 30);
			DesPrimaAntic = periodoGracia + Integer.valueOf(Plazo);
		}
		
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
				
		int Tasaxmillonseguro = 4625;

		if (prima == "") {
			int calculoMontoSoli = (int) MontoaSolicitar(Monto, DesPrimaAntic, Tasaxmillonseguro, EstudioCredito,
					TasaFianza, vlrIva);
			int PrimaAnticipadaSeguro = (int) PrimaAnticipadaSeguro(calculoMontoSoli, 1000000, Tasaxmillonseguro,
					DesPrimaAntic);
			ToleranciaPeso(Integer.parseInt(TextoElemento(pestanasimuladorinternopage.PrimaAnticipadaSeguroAsesor)),
					PrimaAnticipadaSeguro);
		}
		
		if (TextoElemento(pestanasimuladorinternopage.ValoraDesembolsar).contains(".")==true) {
			ToleranciaPeso(Integer.parseInt(TextoElemento(pestanasimuladorinternopage.ValoraDesembolsar).substring(0,TextoElemento(pestanasimuladorinternopage.ValoraDesembolsar).length()-2).replaceAll("[^a-zA-Z0-9]", "")),(VlrDesembolsar));
	    	   System.out.println("dentro del if que contiene punto");
		}else {
	    		   ToleranciaPeso(Integer.parseInt(TextoElemento(pestanasimuladorinternopage.ValoraDesembolsar)),(VlrDesembolsar));
	    		   System.out.println("dentro del else que no contiene punto");	    
		}
    }
	
	public void ValidarSimuladorAnalistaRetanqueosCarteraSaneamiento(String Credito,String retanqueo,String fecha,String Mes, String Plazo,String Ingresos, String descLey, String descNomina, String Cartera, String Saneamiento,String DiasHabilesIntereses, String tasa) throws InterruptedException, SQLException{
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

			
		int Tasaxmillonseguro = 4625;
		
		
		if(prima=="") {			
			int calculoMontoSoli = (int) MontoaSolicitar(Monto, DesPrimaAntic, Tasaxmillonseguro, EstudioCredito,
					TasaFianza, vlrIva);
						
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
