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
	private static Logger log = Logger.getLogger(RetanqueoCreditos.class);
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
		log.info("************ Buscar credito a retanquear, RetanqueoCreditos - FiltrarCredito()*****");
		try {
			BuscarenGrilla(retanqueopages.cedula, Cedula);
			Thread.sleep(5000);
			ElementVisible();
			BuscarenGrilla(retanqueopages.credito, Credito);
			Thread.sleep(5000);
			ElementVisible();
			esperaExplicitaTexto(Credito);
			adjuntarCaptura("Se filtra el retanqueo ");
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
			adjuntarCaptura("Se genera el retanqueo para el credito ");
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
			CedulaCliente=Cedula;
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
			adjuntarCaptura("Se seleciona el retanqueo ");
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
			adjuntarCaptura("Se borran los archivos del credito anterior ");
		} catch (Exception e) {
			log.error("########## ERROR RetanqueoCreditos - borrarArchivos() ########" + e);
			assertTrue("########## ERROR RetanqueoCreditos - borrarArchivos() ########"+ e,false);
		}
		
	}

	public void CargarArchivos(String pdf) throws InterruptedException {
		log.info("*********Cargar los nuevod documento, RetanqueoCreditos - CargarArchivos()*******");
		try {
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
			esperaExplicita(retanqueopages.notificacion);
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
			esperaExplicitaSeguridad(pestanaSeguridadPage.BtnCheck,CedulaCliente);
			Refrescar();
			esperaExplicita(pestanaSeguridadPage.Concepto);
			Hacer_scroll(pestanaSeguridadPage.Concepto);
			esperaExplicitaSeguridad(pestanaSeguridadPage.BtnCheck,CedulaCliente);
			adjuntarCaptura("Se marca el credito viable ");
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
			
			/*esperaExplicita(retanqueopages.inputTasa);
			LimpiarConTeclado(retanqueopages.inputTasa);
			EscribirElemento(retanqueopages.inputTasa, Tasa);*/
			hacerClick(retanqueopages.labelTasa);
			EscribirElemento(retanqueopages.inputTasaFiltro,Tasa);
					EnviarEnter(retanqueopages.inputTasaFiltro);

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
			
		
		// Valores CXC capitalizadas
		/* Consultar los conceptos para el cambio de tasa */
		double EstudioCredito = 0;
		double TasaFianza = 0;
		int mesDos = 0;
		double tasaDos = 0;
		resultado = query.consultarValoresCapitalizador(Tasa);
		while (resultado.next()) {
			tasaDos = Double.parseDouble(resultado.getString(2)) / 100;
			EstudioCredito = Double.parseDouble(resultado.getString(3));
			TasaFianza = Double.parseDouble(resultado.getString(4));
			mesDos = resultado.getInt(5);
		}
		// EstudioCredito = 2.35; //EliminarLinea
		log.info("Tasa Estudio Credito " + EstudioCredito);
		log.info("Tasa Fianza " + TasaFianza);
		log.info("Valor mes Dos " + mesDos);
		log.info("Tasa Dos" + tasaDos);
		double tasaUno = Double.parseDouble(Tasa) / 100;
		
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
		
		int montoSolicitarPantalla = Integer.parseInt(TextoElemento(pestanasimuladorinternopage.ResultMontoSoli)) ;
		
		if (prima == "") {

			int calculoMontoSoli = (int) MontoaSolicitar(Monto, DesPrimaAntic, Tasaxmillonseguro, EstudioCredito,
					TasaFianza, vlrIva);
			
			/*Se cambia el valor por el de la pantalla*/
			calculoMontoSoli = montoSolicitarPantalla;
		
			/*ToleranciaPesoMensaje("Monto Solicitado ",
					Integer.parseInt(TextoElemento(pestanasimuladorinternopage.ResultMontoSoli)), calculoMontoSoli);*/

			int PrimaAnticipadaSeguro = (int) PrimaSeguroRetanqueoHijo(calculoMontoSoli, TasaFianza, vlrIva,EstudioCredito, Tasaxmillonseguro, DesPrimaAntic);
			ToleranciaPesoMensaje("Prima Anticipada de seguro ",
					Integer.parseInt(TextoElemento(pestanasimuladorinternopage.PrimaAnticipadaSeguro)),
					PrimaAnticipadaSeguro);

			//////////////////////////////////////////////////////////////////////////////////////////////////////////

			int CuotaCorriente = (int) CuotaCorriente(calculoMontoSoli, tasaUno, Integer.parseInt(Plazo), tasaDos,
					mesDos);
			ToleranciaPesoMensaje(" Cuota corriente ",
					Integer.parseInt(TextoElemento(pestanasimuladorinternopage.CuotaCorriente)), CuotaCorriente);

			int EstudioCreditoIva = (int) EstudioCreditoRetanqueoHijo(calculoMontoSoli, TasaFianza, vlrIva,EstudioCredito, Tasaxmillonseguro, DesPrimaAntic);

			int resultEstudioCredito = EstudioCreditoIva-estudioCreditoPadre;
			resultEstudioCredito=(resultEstudioCredito<0)?resultEstudioCredito*0:resultEstudioCredito;
			log.info("Result Pantalla Estudio Credito" + resultEstudioCredito);
			ToleranciaPesoMensaje("Estudio Credito IVA ",
							Integer.parseInt(TextoElemento(pestanasimuladorinternopage.EstudioCreditoIVA)), resultEstudioCredito);
			
			//(int montoSoli, double tasaFianza, double iva, double porEstudioCre, int tasaXmillon, int periodoPrima ) {
			int ValorFianza = (int) vlrFianzaRetanqueoHijo(calculoMontoSoli, TasaFianza, vlrIva,EstudioCredito, Tasaxmillonseguro, DesPrimaAntic);
			int resultFianza = ValorFianza-fianzaPadre;
			resultFianza=(resultFianza<0)?resultFianza*0:resultFianza;
			log.info("Result Pantalla Fianza " + resultFianza);
			ToleranciaPesoMensaje("Comparacion Fianza",Integer.parseInt(TextoElemento(pestanasimuladorinternopage.ValorFianza)), resultFianza);
			
			int MontoMaxDesembolsar = (int) MontoMaxDesembolsar(Integer.parseInt(Ingresos), Integer.parseInt(descLey),
					Integer.parseInt(descNomin), colchon, tasaUno, Integer.parseInt(Plazo), tasaDos, mesDos);
			ToleranciaPesoMensaje("Pantalla MontoMaxDesembolsar ",
					Integer.parseInt(TextoElemento(pestanasimuladorinternopage.MontoMaximoSugerido)), MontoMaxDesembolsar);

			int Gmf4100 = (int) Gmf4100(Integer.parseInt(VlrCompraSaneamiento), 0.004);			
			ToleranciaPesoMensaje("Pantalla MontoMaxDesembolsar ",
					Integer.parseInt(TextoElemento(pestanasimuladorinternopage.Gmf4100)), Gmf4100);
			
			int remantEstimado = (int) remanenteEstimadoRetanqueo(calculoMontoSoli,SaldoAlDia,resultFianza,resultEstudioCredito,Integer.parseInt(VlrCompraSaneamiento),Gmf4100,PrimaAnticipadaSeguro);
			/*ToleranciaPesoMensaje(" Valor Desembolsar ",
					Integer.parseInt(TextoElemento(pestanasimuladorinternopage.SimuladorInternorValoraDesembolsar)),remantEstimado);*/


		} else {

			int calculoMontoSoli = (int) MontoaSolicitar(Monto, DesPrimaAntic, Tasaxmillonseguro, EstudioCredito,
					TasaFianza, vlrIva);
			
			/*Se cambia el valor por el de la pantalla*/
			calculoMontoSoli = montoSolicitarPantalla;
			/*ToleranciaPesoMensaje("Monto Solicitado ",
					Integer.parseInt(TextoElemento(pestanasimuladorinternopage.ResultMontoSoli)), calculoMontoSoli);*/

			int PrimaAnticipadaSeguro = (int) PrimaSeguroRetanqueoHijo(calculoMontoSoli, TasaFianza, vlrIva,EstudioCredito, Tasaxmillonseguro, DesPrimaAntic);
			ToleranciaPesoMensaje("Prima Anticipada de seguro ",
					Integer.parseInt(TextoElemento(pestanasimuladorinternopage.PrimaAnticipadaSeguro)),
					PrimaAnticipadaSeguro);
			
			int PrimaNeta = (int) PrimaNeta(PrimaPadre,MontoPadre,MesesActivoPadre,PrimaAnticipadaSeguro, 1000000, Tasaxmillonseguro,DesPrimaAntic);	
			/*ToleranciaPesoMensaje(" Prima neta ", Integer.parseInt(TextoElemento(pestanasimuladorinternopage.PrimaNeta)), PrimaNeta);*/		

			int PrimaNoDevengada = (int) PrimaNoDevengadaCPadre(PrimaPadre,MontoPadre,MesesActivoPadre,PrimaAnticipadaSeguro, 1000000, Tasaxmillonseguro,DesPrimaAntic);	
			/*ToleranciaPesoMensaje(" Prima No Devengada ", Integer.parseInt(TextoElemento(pestanasimuladorinternopage.PrimaNoDevengadaSimuladorInterno)), PrimaNoDevengada);*/

			int CuotaCorriente = (int) CuotaCorriente(calculoMontoSoli, tasaUno, Integer.parseInt(Plazo), tasaDos,
					mesDos);
			ToleranciaPesoMensaje(" Cuota corriente ",
					Integer.parseInt(TextoElemento(pestanasimuladorinternopage.CuotaCorriente)), CuotaCorriente);

			int EstudioCreditoIva = (int) EstudioCreditoRetanqueoHijo(calculoMontoSoli, TasaFianza, vlrIva,EstudioCredito, Tasaxmillonseguro, DesPrimaAntic);
			int resultEstudioCredito = EstudioCreditoIva-estudioCreditoPadre;
			resultEstudioCredito=(resultEstudioCredito<0)?resultEstudioCredito*0:resultEstudioCredito;
			ToleranciaPesoMensaje(" Estudio Credito IVA",
					Integer.parseInt(TextoElemento(pestanasimuladorinternopage.EstudioCreditoIVA)), resultEstudioCredito);

			int ValorFianza = (int) vlrFianzaRetanqueoHijo(calculoMontoSoli, TasaFianza, vlrIva,EstudioCredito, Tasaxmillonseguro, DesPrimaAntic);
			int resultFianza = ValorFianza-fianzaPadre;
			resultFianza=(resultFianza<0)?resultFianza*0:resultFianza;
			ToleranciaPeso(Integer.parseInt(TextoElemento(pestanasimuladorinternopage.ValorFianza)), resultFianza);

			int MontoMaxDesembolsar = (int) MontoMaxDesembolsar(Integer.parseInt(Ingresos), Integer.parseInt(descLey),
					Integer.parseInt(descNomin), colchon, tasaUno, Integer.parseInt(Plazo), tasaDos, mesDos);
			ToleranciaPesoMensaje("Pantalla MontoMaxDesembolsar ",
					Integer.parseInt(TextoElemento(pestanasimuladorinternopage.MontoMaximoSugerido)), MontoMaxDesembolsar);

			int Gmf4100 = (int) Gmf4100(Integer.parseInt(VlrCompraSaneamiento), 0.004);
			ToleranciaPesoMensaje("Pantalla MontoMaxDesembolsar ",
					Integer.parseInt(TextoElemento(pestanasimuladorinternopage.Gmf4100)), Gmf4100);
			
			int remantEstimado = (int) remanenteEstimadoRetanqueo(calculoMontoSoli,SaldoAlDia,resultFianza,resultEstudioCredito,Integer.parseInt(VlrCompraSaneamiento),Gmf4100,PrimaAnticipadaSeguro);
			/*ToleranciaPesoMensaje(" Valor Desembolsar ",
					Integer.parseInt(TextoElemento(pestanasimuladorinternopage.SimuladorInternorValoraDesembolsar)),remantEstimado);*/

		}
	}

	public void SolicitarCredito() throws InterruptedException {
		log.info("*********** Presionar boton solicitar,  RetanqueoCreditos - SolicitarCredito()*********");
		try {
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
		hacerClick(pestanasimuladorinternopage.FechasManuales);
		ElementVisible();
		hacerClick(pestanasimuladorinternopage.CalcularDesglose);
		ElementVisible();
		hacerClicknotificacion();
		esperaExplicitaNopresente(pestanadigitalizacionPage.Notificacion);
		//ToleranciaPeso(Integer.parseInt(TextoElemento(pestanasimuladorinternopage.ValoraDesembolsar)),(Integer.parseInt(retanqueo)));
    	
		
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
		System.out.println(" Variable prima: "+ prima);
		int PrimaNoDevengada=0;
		
		int calculoSoliPantalla = Integer.parseInt(TextoElemento(pestanasimuladorinternopage.CapitalTotal));
		
		if (prima == "") {
			int calculoMontoSoli = (int) MontoaSolicitar(Monto, DesPrimaAntic, Tasaxmillonseguro, EstudioCredito,
					TasaFianza, vlrIva);
			calculoMontoSoli =calculoSoliPantalla;
			//int montoSoli, double tasaFianza, double iva, double porEstudioCre, int tasaXmillon, int periodoPrima 
			int PrimaAnticipadaSeguro = (int) PrimaSeguroRetanqueoHijo(calculoMontoSoli, TasaFianza,vlrIva,EstudioCredito, Tasaxmillonseguro, DesPrimaAntic);
			ToleranciaPesoMensaje(" Prima anticipada de seguro ",Integer.parseInt(TextoElemento(pestanasimuladorinternopage.PrimaAnticipadaSeguroAsesor)),PrimaAnticipadaSeguro);
		}else {
			int calculoMontoSoli = (int) MontoaSolicitar(Monto, DesPrimaAntic, Tasaxmillonseguro, EstudioCredito,
					TasaFianza, vlrIva);
			calculoMontoSoli =calculoSoliPantalla;
			int PrimaAnticipadaSeguro = (int) PrimaSeguroRetanqueoHijo(calculoMontoSoli, TasaFianza,vlrIva,EstudioCredito, Tasaxmillonseguro, DesPrimaAntic);
			ToleranciaPesoMensaje(" Prima anticipada de seguro ",Integer.parseInt(TextoElemento(pestanasimuladorinternopage.PrimaAnticipadaSeguroAsesor)),PrimaAnticipadaSeguro);
			int PrimaNeta = (int) PrimaNeta(PrimaPadre,MontoPadre,MesesActivoPadre,PrimaAnticipadaSeguro, 1000000, Tasaxmillonseguro,DesPrimaAntic);
		    ToleranciaPesoMensaje(" Prima neta", Integer.parseInt(TextoElemento(pestanasimuladorinternopage.PrimaNetaSimuladorAnalista)), PrimaNeta);
			
		    PrimaNoDevengada = (int) PrimaNoDevengadaCPadre(PrimaPadre,MontoPadre,MesesActivoPadre,PrimaAnticipadaSeguro, 1000000, Tasaxmillonseguro,DesPrimaAntic);
		    ToleranciaPesoMensaje(" Prima neta No devengada", Integer.parseInt(TextoElemento(pestanasimuladorinternopage.PrimaNoDevengadaSimuladorAnalista)), PrimaNoDevengada);
		 
		}

		if (TextoElemento(pestanasimuladorinternopage.ValoraDesembolsar).contains(".") == true) {
			ToleranciaPesoMensaje(" Prima Valor Desembolsar IF ",Integer.parseInt(TextoElemento(pestanasimuladorinternopage.ValoraDesembolsar)
					.substring(0, TextoElemento(pestanasimuladorinternopage.ValoraDesembolsar).length() - 2).replaceAll("[^a-zA-Z0-9]", "")), (Integer.parseInt(retanqueo)));
			System.out.println("dentro del if que contiene punto");
		} else {
			ToleranciaPesoMensaje(" Prima Valor Desembolsar ELSE ",Integer.parseInt(TextoElemento(pestanasimuladorinternopage.ValoraDesembolsar)),(Integer.parseInt(retanqueo)));
			System.out.println("dentro del else que no contiene punto");
		}
        
    }
	
	
	public void ValidarSimuladorAnalistaRetanqueos(String anno,String Credito,String retanqueo,String fecha,String Mes, String Plazo,String Ingresos, String descLey, String descNomina,String cartera,String DiasHabilesIntereses) throws InterruptedException, SQLException{
    	esperaExplicita(pestanasimuladorinternopage.MesDeAfecatcion);
    	hacerClick(pestanasimuladorinternopage.FechaDesembolso);
    	Clear(pestanasimuladorinternopage.FechaDesembolso);
    	EscribirElemento(pestanasimuladorinternopage.FechaDesembolso, fecha);  
    	EnviarEnter(pestanasimuladorinternopage.FechaDesembolso);
    	hacerClick(pestanasimuladorinternopage.MesDeAfecatcion);    	
		ElementVisible();
		selectValorLista(pestanasimuladorinternopage.ListaMes, Mes);
		ElementVisible();
		Clear(pestanasimuladorinternopage.anoAfectacion);
		EscribirElemento(pestanasimuladorinternopage.anoAfectacion, anno);
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
			int calculoMontoSoli = (int) MontoaSolicitar(Monto, DesPrimaAntic, Tasaxmillonseguro, EstudioCredito,
					TasaFianza, vlrIva);
			int PrimaAnticipadaSeguro = (int) PrimaAnticipadaSeguro(calculoMontoSoli, 1000000, Tasaxmillonseguro,
					DesPrimaAntic);
			ToleranciaPeso(Integer.parseInt(TextoElemento(pestanasimuladorinternopage.PrimaAnticipadaSeguroAsesor)),
					PrimaAnticipadaSeguro);
		}else {
			int calculoMontoSoli = (int) MontoaSolicitar(Monto, DesPrimaAntic, Tasaxmillonseguro, EstudioCredito,
			TasaFianza, vlrIva);
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
	
	
	public void ValidarSimuladorAnalistaRetanqueosCarteraSaneamiento(String anno,String Credito,String retanqueo,String fecha,String Mes, String Plazo,String Ingresos, String descLey, String descNomina, String Cartera, String Saneamiento,String DiasHabilesIntereses, String tasa) throws InterruptedException, SQLException{
    	esperaExplicita(pestanasimuladorinternopage.MesDeAfecatcion);
    	hacerClick(pestanasimuladorinternopage.FechaDesembolso);
    	Clear(pestanasimuladorinternopage.FechaDesembolso);
    	EscribirElemento(pestanasimuladorinternopage.FechaDesembolso, fecha);  
    	EnviarEnter(pestanasimuladorinternopage.FechaDesembolso);
    	hacerClick(pestanasimuladorinternopage.MesDeAfecatcion);    	
		ElementVisible();
		selectValorLista(pestanasimuladorinternopage.ListaMes, Mes);
		ElementVisible();
		Clear(pestanasimuladorinternopage.anoAfectacion);
		EscribirElemento(pestanasimuladorinternopage.anoAfectacion, anno);
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
		int PrimaNoDevengada =0;
		
		if(prima=="") {			
			int calculoMontoSoli = (int) MontoaSolicitar(Monto, DesPrimaAntic, Tasaxmillonseguro, EstudioCredito,
					TasaFianza, vlrIva);
						
			int PrimaAnticipadaSeguro = (int) PrimaAnticipadaSeguro(calculoMontoSoli, 1000000, Tasaxmillonseguro,DesPrimaAntic);	
			ToleranciaPeso(Integer.parseInt(TextoElemento(pestanasimuladorinternopage.PrimaAnticipadaSeguroAsesor)),PrimaAnticipadaSeguro);
		}else {
			int calculoMontoSoli = (int) MontoaSolicitar(Monto, DesPrimaAntic, Tasaxmillonseguro, EstudioCredito,
					TasaFianza, vlrIva);
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
		//int calculoMontoSoli = (int) MontoaSolicitar(Integer.parseInt(ValoresCredito[13])+SaldoAlDia+Integer.parseInt(ValoresCredito[12]), DesPrimaAntic, Tasaxmillonseguro);
		
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
		
		/**/
		
		if(ValoresCredito[11].isEmpty()==true) {
		// calculoMontoSoli=calculoMontoSoli-PrimaAnticipadaSeguro;
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
