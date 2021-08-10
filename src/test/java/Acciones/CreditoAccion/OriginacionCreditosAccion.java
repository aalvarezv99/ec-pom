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

public class OriginacionCreditosAccion extends BaseTest {

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
	// BaseTest baseTest;
	private static Logger log = Logger.getLogger(OriginacionCreditosAccion.class);
	double vlrIva = 1.19;

	public OriginacionCreditosAccion(WebDriver driver) throws InterruptedException {
		/// this.driver = driver;
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

	/************ INICIO ACCIONES PARA SIMULADOR ASESOR ***************/

	public void ingresarSimuladorAsesor() {
		panelnavegacionaccion.navegarSimulador();
	}

	public void CambiarFechaServidor(String FechaServidor) {
		jSchSSHConnection.CambioHoraServidor(FechaServidor);
	}

	/*
	 * Metodo que se utiliza para llenar el formulario asesor del la opcion
	 * somulador ir
	 */
	public void llenarFormularioAsesor(String Pagaduria, String Cedula, String Fecha, String Oficina, String Actividad,
			String Tasa, String Plazo, String Monto, String DiasHabilesIntereses, String Ingresos, String descLey,
			String descNomina, String vlrCompasSaneamientos, String colchon)
			throws InterruptedException, NumberFormatException, SQLException {
		log.info("*********************OriginacionCreditosAccion - llenarFormularioAsesor() *************");
		try {
			
			assertEstaPresenteElemento(simuladorasesorpage.desPagaduria);
			hacerClick(simuladorasesorpage.desPagaduria);
			// driver.findElement(simuladorasesorpage.desPagaduria).click();
			selectValorLista(simuladorasesorpage.contPagaduria, simuladorasesorpage.listPagaduria, Pagaduria);
		} catch (Exception e) {
			log.error("##### -ERROR- #####" + e);
		}
		ElementVisible();
		assertTextoelemento(simuladorasesorpage.desPagaduria, Pagaduria);
		hacerClick(simuladorasesorpage.inputIdentificacion);
		assertEstaPresenteElemento(simuladorasesorpage.inputIdentificacion);
		EscribirElemento(simuladorasesorpage.inputIdentificacion, Cedula);
		ElementVisible();
		MetodoFecha(Fecha, simuladorasesorpage.selectFecha, simuladorasesorpage.contAno, simuladorasesorpage.contMes,
				simuladorasesorpage.contDia, simuladorasesorpage.listDia);
		ElementVisible();

		// Componente Oficina seleccionando la oficina del documento

		assertEstaPresenteElemento(simuladorasesorpage.desOficina);
		hacerClick(simuladorasesorpage.desOficina);
		selectValorLista(simuladorasesorpage.contOficina, simuladorasesorpage.listOficina, Oficina);
		ElementVisible();
		assertTextoelemento(simuladorasesorpage.desOficina, Oficina);

		// Componente Actividad selecciona la actividad del cliente
		assertEstaPresenteElemento(simuladorasesorpage.desOcupacion);
		hacerClick(simuladorasesorpage.desOcupacion);
		selectValorLista(simuladorasesorpage.contOcupacion, simuladorasesorpage.listOcupacion, Actividad);
		ElementVisible();
		assertTextoelemento(simuladorasesorpage.desOcupacion, Actividad);

		// Llenar formulario campos del credito
		LimpiarConTeclado(simuladorasesorpage.inputTasa);
		EscribirElemento(simuladorasesorpage.inputTasa, Tasa);
		ElementVisible();
		hacerClick(simuladorasesorpage.inputPlazo);
		ElementVisible();
		LimpiarConTeclado(simuladorasesorpage.inputPlazo);
		EscribirElemento(simuladorasesorpage.inputPlazo, Plazo);
		ElementVisible();
		hacerClick(simuladorasesorpage.inputMonto);
		ElementVisible();
		LimpiarConTeclado(simuladorasesorpage.inputMonto);
		EscribirElemento(simuladorasesorpage.inputMonto, Monto);
		ElementVisible();
		hacerClick(simuladorasesorpage.diasIntInicial);
		ElementVisible();
		LimpiarConTeclado(simuladorasesorpage.diasIntInicial);
		EscribirElemento(simuladorasesorpage.diasIntInicial, DiasHabilesIntereses);
		ElementVisible();
		hacerClick(simuladorasesorpage.inputIngresos);
		ElementVisible();
		LimpiarConTeclado(simuladorasesorpage.inputIngresos);
		EscribirElemento(simuladorasesorpage.inputIngresos, Ingresos);
		ElementVisible();
		hacerClick(simuladorasesorpage.inputDescLey);
		ElementVisible();
		LimpiarConTeclado(simuladorasesorpage.inputDescLey);
		EscribirElemento(simuladorasesorpage.inputDescLey, descLey);
		ElementVisible();
		hacerClick(simuladorasesorpage.inputdDescNomina);
		ElementVisible();
		LimpiarConTeclado(simuladorasesorpage.inputdDescNomina);
		EscribirElemento(simuladorasesorpage.inputdDescNomina, descNomina);
		ElementVisible();
		hacerClick(simuladorasesorpage.vlrCompra);
		ElementVisible();
		LimpiarConTeclado(simuladorasesorpage.vlrCompra);
		EscribirElemento(simuladorasesorpage.vlrCompra, vlrCompasSaneamientos);
		hacerClick(simuladorasesorpage.inputIngresos);
		ElementVisible();

	}	
	
	/* 
	 * TP - 02/08/2021  Se actualiza el assert simulador modificando los valores para que 
	 * funcione con la capitalizacion de intereses tasaDos y mesDos, se ajustan los calculos en todas las formulas*/
	public void assertSimulador( String Fecha, String Tasa,String Plazo,String Monto,String DiasHabilesIntereses,String Ingresos,String descLey,String descNomina,String vlrCompasSaneamientos,String tipo, String pagaduria) throws NumberFormatException, SQLException{
		      	
		log.info("************ OriginacionCreditosAccion - assertSimulador() *********************");
				ResultSet resultado;
				
		       // consulta base de datos
				int DesPrimaAntic = 0;
				OriginacionCreditoQuery query = new OriginacionCreditoQuery();
				resultado = query.ConsultaDescuentoPrimaAntic();
				while (resultado.next()) {
					DesPrimaAntic = Integer.parseInt(resultado.getString(1));
				}
				log.info("******** Valor de prima **** " + DesPrimaAntic);
				
				if(Integer.valueOf(Plazo)<DesPrimaAntic) {
					int periodoGracia = (int)Math.ceil((double)Integer.parseInt(DiasHabilesIntereses)/30);
					DesPrimaAntic = periodoGracia + Integer.valueOf(Plazo);
					log.info("******** Nuevo valor de prima plazo menor a 24  **** " + DesPrimaAntic);
				} 
				
				int colchon = 0;
				resultado = query.colchonpagaduria(pagaduria);
				while (resultado.next()) {
					colchon = Integer.parseInt(resultado.getString(1));
				}
		 
				double EstudioCredito = 0;
				resultado = query.EstudioCredito();
				while (resultado.next()) {
					EstudioCredito = Double.parseDouble(resultado.getString(1));
				}
				//EstudioCredito = 2.35; //EliminarLinea
				log.info("TasaEstudioCredito " +EstudioCredito);
				
				double TasaFianza =0;
				resultado = query.porcentajefianza();
				while (resultado.next()) {
					TasaFianza = Double.parseDouble(resultado.getString(1));
				}
				log.info("TasaFianza "+ TasaFianza);
				
				//Valores CXC capitalizadas				
				int mesDos = 0;
				double tasaDos = 0;
				
				
				resultado = query.consultarValoresMesCapitalizadas();
				while (resultado.next()) {
					mesDos = resultado.getInt(1);			
				}
				
				resultado = query.consultarValoresTasaDosCapitalizadas();
				while (resultado.next()) {
					tasaDos = Double.parseDouble(resultado.getString(1))/100;			
				}
				
				
				//mesDos =48;
				//tasaDos = 0.0075;
				log.info("TasaFianza "+ mesDos);
				log.info("TasaFianza "+ tasaDos);
				double tasaUno = Double.parseDouble(Tasa)/100;
				
				//porcentajefianza
		
		       // Valores para la funciones estaticos
				int Tasaxmillonseguro = 4625;				
				double variableFianza = 1.19;
				
				
				// Validar resultados de simulacion

				int Capacidad = (int) CapacidadPagaduria(Integer.parseInt(Ingresos), Integer.parseInt(descLey),Integer.parseInt(descNomina),colchon);
				assertValidarEqualsImprimeMensaje("###### ERROR CALCULANDO CAPACIDAD ########",TextoElemento(simuladorasesorpage.CapacidadAproximada), String.valueOf(Capacidad));
				
				int edad = (int) edad(Fecha);
				//assertvalidarEquals(TextoElemento(simuladorasesorpage.edad), String.valueOf(edad));

				int calculoMontoSoli = (int) MontoaSolicitar(Integer.parseInt(Monto), DesPrimaAntic, Tasaxmillonseguro, EstudioCredito, TasaFianza, vlrIva);
				assertValidarEqualsImprimeMensaje("###### ERROR CALCULANDO MONTO SOLICITUD ########",TextoElemento(simuladorasesorpage.ResultMontoSoli), String.valueOf(calculoMontoSoli));
				ToleranciaPeso(Integer.parseInt(TextoElemento(simuladorasesorpage.ResultMontoSoli)),calculoMontoSoli);

				int CuotaCorriente = (int) CuotaCorriente(calculoMontoSoli, tasaUno, Integer.parseInt(Plazo), tasaDos, mesDos);
				assertValidarEqualsImprimeMensaje("###### ERROR CALCULANDO CUOTA CORRIENTE ########",TextoElemento(simuladorasesorpage.CuotaCorriente), String.valueOf(CuotaCorriente));

				int EstudioCreditoIva = (int) EstudioCreditoIva(Integer.parseInt(Monto), EstudioCredito);
				assertValidarEqualsImprimeMensaje("###### ERROR CALCULANDO ESTUDIO CREDITO ########",TextoElemento(simuladorasesorpage.EstudioCreditoIVA), String.valueOf(EstudioCreditoIva));

				int ValorFianza = (int) ValorFianza(Integer.parseInt(Monto), TasaFianza, variableFianza);
				assertValidarEqualsImprimeMensaje("###### ERROR CALCULANDO VALOR FIANZA ########",TextoElemento(simuladorasesorpage.ValorFianza), String.valueOf(ValorFianza));

				int Gmf4100 = (int) Gmf4100(Integer.parseInt(vlrCompasSaneamientos), 0.004);
				assertValidarEqualsImprimeMensaje("######## ERROR CALCULO GMF4100 #######", TextoElemento(simuladorasesorpage.Gmf4100), String.valueOf(Gmf4100));

				int ValorInteresesIniciales = (int) ValorInteresesIniciales(calculoMontoSoli, Double.parseDouble(Tasa),
						Integer.parseInt(DiasHabilesIntereses), 30);
				assertValidarEqualsImprimeMensaje("######## ERROR CALCULO VLR INT INICIALES #######", TextoElemento(simuladorasesorpage.Valorinteresesini),
						String.valueOf(ValorInteresesIniciales));

				int PrimaAnticipadaSeguro = (int) PrimaAnticipadaSeguro(Integer.parseInt(Monto), 1000000, Tasaxmillonseguro,
						DesPrimaAntic);
				assertValidarEqualsImprimeMensaje("######## ERROR CALCULO PRIMA SEGURO ANTICIPADO #######", TextoElemento(simuladorasesorpage.PrimaAnticipadaSeguro),String.valueOf(PrimaAnticipadaSeguro));

				int RemanenteEstimado = (int) RemanenteEstimado(calculoMontoSoli, Integer.parseInt(vlrCompasSaneamientos),
						Gmf4100, PrimaAnticipadaSeguro,EstudioCreditoIva,ValorFianza);
				assertValidarEqualsImprimeMensaje("######## ERROR CALCULO REMANENTE ESTIMADO #######", TextoElemento(simuladorasesorpage.RemanenteEstimado), String.valueOf(RemanenteEstimado));

				int MontoMaxDesembolsar = (int) MontoMaxDesembolsar(Integer.parseInt(Ingresos), Integer.parseInt(descLey),
						Integer.parseInt(descNomina), colchon, tasaUno,
						Integer.parseInt(Plazo), tasaDos, mesDos);
				assertValidarEqualsImprimeMensaje("######## ERROR CALCULO MONTO MAXIMO DESEMBOLSAR #######", TextoElemento(simuladorasesorpage.MontoMaximoSugerido),String.valueOf(MontoMaxDesembolsar));
				ToleranciaPeso(Integer.parseInt(TextoElemento(simuladorasesorpage.MontoMaximoSugerido)),MontoMaxDesembolsar);
		
	}
	
	public void GuardarSimulacion() throws InterruptedException {
		log.info("******************OriginacionCreditosAccion - GuardarSimulacion()********** ");
		try {
			Hacer_scroll(simuladorasesorpage.btnGuardar);
			hacerClick(simuladorasesorpage.btnGuardar);
			assertEstaPresenteElemento(simuladorasesorpage.notificacionGuardado);
			hacerClicknotificacion();/////////////////////////////////////////////////////////////////
			esperaExplicita(simuladorasesorpage.notificacionGuardado);
		} catch (Exception e) {
			log.error("########## Error - OriginacionCreditosAccion - GuardarSimulacion() #######" + e);
			assertTrue("########## Error - OriginacionCreditosAccion - GuardarSimulacion() ########"+ e,false);
		}
		

	}

	public void CrearCliente(String TipoContrato, String FechaIngreso, String Pnombre, String Papellido,
			String Sapellido, String Correo, String Celular, String Dpto, String Ciudad) throws InterruptedException {
		log.info("******************OriginacionCreditosAccion - CrearCliente()********** ");
		try {
			Hacer_scroll(simuladorasesorpage.btnCrearCliente);
			assertEstaPresenteElemento(simuladorasesorpage.btnCrearCliente);
			hacerClick(simuladorasesorpage.btnCrearCliente);
			ElementVisible();
			esperaExplicita(simuladorasesorpage.selectContrato);
			hacerClick(simuladorasesorpage.selectContrato);
			selectValorLista(simuladorasesorpage.contContrato, simuladorasesorpage.listContrato, TipoContrato);
			ElementVisible();
			assertEstaPresenteElemento(simuladorasesorpage.selectFechaIngre);
			hacerClick(simuladorasesorpage.selectFechaIngre);
			EscribirElemento(simuladorasesorpage.selectFechaIngre, FechaIngreso);
			ElementVisible();
			hacerClick(simuladorasesorpage.inputCelular);
			ElementVisible();
			hacerClick(simuladorasesorpage.inputPrNombre);
			assertEstaPresenteElemento(simuladorasesorpage.inputPrNombre);
			EscribirElemento(simuladorasesorpage.inputPrNombre, Pnombre);
			ElementVisible();
			assertEstaPresenteElemento(simuladorasesorpage.inputPrApellido);
			EscribirElemento(simuladorasesorpage.inputPrApellido, Papellido);
			ElementVisible();
			assertEstaPresenteElemento(simuladorasesorpage.inputSeApellido);
			EscribirElemento(simuladorasesorpage.inputSeApellido, Sapellido);
			ElementVisible();
			assertEstaPresenteElemento(simuladorasesorpage.inputCorreo);
			EscribirElemento(simuladorasesorpage.inputCorreo, Correo);
			ElementVisible();
			assertEstaPresenteElemento(simuladorasesorpage.inputCelular);
			EscribirElemento(simuladorasesorpage.inputCelular, Celular);
			ElementVisible();
			Hacer_scroll(simuladorasesorpage.btnCrear);
			hacerClick(simuladorasesorpage.selectDpto);
			selectValorLista(simuladorasesorpage.contDpto, simuladorasesorpage.listDpto, Dpto);
			ElementVisible();
			hacerClick(simuladorasesorpage.selectCiudad);
			selectValorLista(simuladorasesorpage.contCiudad, simuladorasesorpage.listCiudad, Ciudad);
			ElementVisible();
			assertEstaPresenteElemento(simuladorasesorpage.btnCrear);
			hacerClick(simuladorasesorpage.btnCrear);
			assertEstaPresenteElemento(simuladorasesorpage.notificacionCreacionCliente);
			hacerClicknotificacion();////////////////////////////////////////////////////////
			esperaExplicitaNopresente(simuladorasesorpage.notificacionCreacionCliente);
			ElementVisible();
		} catch (Exception e) {
			log.error("########## Error - OriginacionCreditosAccion - CrearCliente() #######" + e);
			assertTrue("########## Error - OriginacionCreditosAccion - CrearCliente() ########"+ e,false);
		}
		
	}

	public void SubirDocumentos(String ruta) throws InterruptedException {
		log.info("********** OriginacionCreditosAccion - SubirDocumentos()************");
		try {
			cargarPdf(simuladorasesorpage.fileAutorizacion, simuladorasesorpage.fileCedula, simuladorasesorpage.fileNomina,
					ruta);
			ElementVisible();
		} catch (Exception e) {
			log.error("########## Error - OriginacionCreditosAccion - SubirDocumentos() #######" + e);
			assertTrue("########## Error - OriginacionCreditosAccion - SubirDocumentos() ########"+ e,false);
		}
		
	}

	public void ConsultaCentrales() throws InterruptedException {
		log.info("******************** OriginacionCreditosAccion - ConsultaCentrales()  ***************");
		try {
			hacerClick(simuladorasesorpage.btnSoliConsulta);
			ElementVisible();
			esperaExplicita(simuladorasesorpage.notificacion);
			String TextoNotificacion=GetText(simuladorasesorpage.notificacion);
			
			if(TextoNotificacion.contains("OCURRIÓ UN ERROR")==true){
		    hacerClicknotificacion();
			hacerClick(simuladorasesorpage.btnSoliConsulta);
			ElementVisible();		
			assertTextonotificacion(simuladorasesorpage.notificacion,"Se ha solicitado la consulta en listas y centrales de riesgo para el crédito:");
			hacerClicknotificacion();
			}else 
			{
			assertTextonotificacion(simuladorasesorpage.notificacion,"Se ha solicitado la consulta en listas y centrales de riesgo para el crédito:");
			hacerClicknotificacion();
			}
		} catch (Exception e) {
			log.error("########## Error - OriginacionCreditosAccion - ConsultaCentrales() #######" + e);
			assertTrue("########## Error - OriginacionCreditosAccion - ConsultaCentrales() ########"+ e,false);
		}
		
	}

	/************ FIN ACCIONES PARA SIMULADOR ASESOR ***************/

	// ==============================================================
	/************ INICIA ACCIONES SOLICITUD CREDITO ***************/

	public void ingresarSolicitudCredito(String Cedula, String NombreCredito) throws InterruptedException {
		log.info("******************** OriginacionCreditosAccion - ingresarSolicitudCredito()  ***************");
		try {
			panelnavegacionaccion.navegarCreditoSolicitud();
			BuscarenGrilla(creditocolicitudpage.inputCedula, Cedula);
			esperaExplicitaTexto(NombreCredito);
			ElementVisible();
			esperaExplicita(creditocolicitudpage.selectVerEditar);
			hacerClick(creditocolicitudpage.selectVerEditar);
			ElementVisible();
		} catch (Exception e) {
			log.error("########## Error - OriginacionCreditosAccion - ingresarSolicitudCredito() #######" + e);
			assertTrue("########## Error - OriginacionCreditosAccion - ingresarSolicitudCredito() ########"+ e,false);
		}
		
	}

	public void Seguridad() throws InterruptedException {

		log.info("******************** OriginacionCreditosAccion - Seguridad()  ***************");	
		try {
			Refrescar();
			hacerClick(pestanaSeguridadPage.PestanaSeguridad);
			esperaExplicita(pestanaSeguridadPage.BotonGuardar);
			hacerClick(pestanaSeguridadPage.Viable);
			hacerClick(pestanaSeguridadPage.BotonGuardar);
			assertTextonotificacion(simuladorasesorpage.notificacion, "Proceso Realizado Correctamente");
			esperaExplicitaNopresente(simuladorasesorpage.notificacion);
			esperaExplicita(pestanaSeguridadPage.Concepto);
			esperaExplicitaSeguridad(pestanaSeguridadPage.BtnCheck);
			Refrescar();
			esperaExplicita(pestanaSeguridadPage.Concepto);
			Hacer_scroll(pestanaSeguridadPage.Concepto);
			esperaExplicitaSeguridad(pestanaSeguridadPage.BtnCheck);
			recorerpestanas("SIMULADOR");
		} catch (Exception e) {
			log.error("########## Error - OriginacionCreditosAccion - Seguridad() #######" + e);
			assertTrue("########## Error - OriginacionCreditosAccion - Seguridad() ########"+ e,false);
		}
		
		
	}
	
	public void assertSimuladorinterno( String Fecha, String Tasa,String Plazo,String Monto,String DiasHabilesIntereses,String Ingresos,String descLey,String descNomina,String vlrCompasSaneamientos,String tipo,String pagaduria) throws NumberFormatException, SQLException, InterruptedException{
		log.info("******************** OriginacionCreditosAccion - assertSimuladorinterno()  ***************");
	       // consulta base de datos
			int DesPrimaAntic = 0;
			OriginacionCreditoQuery query = new OriginacionCreditoQuery();
			ResultSet resultado = query.ConsultaDescuentoPrimaAntic();
			while (resultado.next()) {
				DesPrimaAntic = Integer.parseInt(resultado.getString(1));
			}
			
			int colchon = 0;
			ResultSet resultadocolchon = query.colchonpagaduria(pagaduria);
			while (resultadocolchon.next()) {
				colchon = Integer.parseInt(resultadocolchon.getString(1));
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
			
			
			
	
	       // Valores para la funciones estaticos
			int Tasaxmillonseguro = 4625;
			double variableFianza = 1.19;
			
			//Valores CXC capitalizadas
			int mesDos =48;
			double tasaDos = 0.0075;
			double tasaUno = Double.parseDouble(Tasa)/100;

			// Validar resultados de simulacion

			int edad = (int) edad(Fecha);
			assertvalidarEquals(TextoElemento(pestanasimuladorinternopage.edad), String.valueOf(edad));

			int calculoMontoSoli = (int) MontoaSolicitar(Integer.parseInt(Monto), DesPrimaAntic, Tasaxmillonseguro, EstudioCredito, TasaFianza, vlrIva);
			assertValidarEqualsImprimeMensaje("###### ERROR CALCULANDO MONTO SOLICITUD ########",TextoElemento(pestanasimuladorinternopage.ResultMontoSoli), String.valueOf(calculoMontoSoli));


			int CuotaCorriente = (int) CuotaCorriente(calculoMontoSoli, tasaUno, Integer.parseInt(Plazo), tasaDos, mesDos);
			//(int valorCredito,double tasaUno,int plazo, double tasaDos, int mesDos
			assertValidarEqualsImprimeMensaje("###### ERROR CALCULANDO CUOTA CORRIENTE ########",TextoElemento(pestanasimuladorinternopage.CuotaCorriente), String.valueOf(CuotaCorriente));

			int EstudioCreditoIva = (int) EstudioCreditoIva(calculoMontoSoli, EstudioCredito);
			assertValidarEqualsImprimeMensaje("###### ERROR CALCULANDO ESTUDIO CREDITO ########",TextoElemento(pestanasimuladorinternopage.EstudioCreditoIVA), String.valueOf(EstudioCreditoIva));

			int ValorFianza = (int) ValorFianza(calculoMontoSoli,TasaFianza, variableFianza);
			assertValidarEqualsImprimeMensaje("###### ERROR CALCULANDO VALOR FIANZA ########",TextoElemento(pestanasimuladorinternopage.ValorFianza), String.valueOf(ValorFianza));

			int Gmf4100 = (int) Gmf4100(Integer.parseInt(vlrCompasSaneamientos), 0.004);
			assertValidarEqualsImprimeMensaje("###### ERROR CALCULANDO VALOR GMF 4X1000 ########",TextoElemento(pestanasimuladorinternopage.Gmf4100), String.valueOf(Gmf4100));

			int ValorInteresesIniciales = (int) ValorInteresesIniciales(calculoMontoSoli, Double.parseDouble(Tasa),
					Integer.parseInt(DiasHabilesIntereses), 30);
			assertValidarEqualsImprimeMensaje("###### ERROR CALCULANDO VALOR INT INICIALES ########",TextoElemento(pestanasimuladorinternopage.Valorinteresesini),
					String.valueOf(ValorInteresesIniciales));

			int PrimaAnticipadaSeguro = (int) PrimaAnticipadaSeguro(calculoMontoSoli, 1000000, Tasaxmillonseguro,
					DesPrimaAntic);
			assertValidarEqualsImprimeMensaje("###### ERROR CALCULANDO VALOR PRIMA SEGURO ANTICIPADA ########",TextoElemento(simuladorasesorpage.PrimaAnticipadaSeguro),String.valueOf(PrimaAnticipadaSeguro));

			int RemanenteEstimado = (int) RemanenteEstimado(calculoMontoSoli, Integer.parseInt(vlrCompasSaneamientos),
					Gmf4100, PrimaAnticipadaSeguro, EstudioCreditoIva, ValorFianza);
			assertValidarEqualsImprimeMensaje("###### ERROR CALCULANDO VALOR REMANENTE ESTIMADO ########",TextoElemento(pestanasimuladorinternopage.RemanenteEstimado), String.valueOf(RemanenteEstimado));

			int MontoMaxDesembolsar = (int) MontoMaxDesembolsar(Integer.parseInt(Ingresos), Integer.parseInt(descLey),
					Integer.parseInt(descNomina), colchon, tasaUno,
					Integer.parseInt(Plazo), tasaDos, mesDos);
			Hacer_scroll(pestanasimuladorinternopage.Solicitar);
			hacerClick(pestanasimuladorinternopage.Solicitar);
			esperaExplicita(simuladorasesorpage.notificacion);
			assertTextonotificacion(simuladorasesorpage.notificacion,"Se ha solicitado la radicación para el crédito");
			ElementVisible();  
	
}
	
	public void Digitalizacion(String Pdf) throws InterruptedException  {
		log.info("******************** OriginacionCreditosAccion Cargue PDF - Digitalizacion() ***************");
		try {
			recorerpestanas("DIGITALIZACIÓN");
			esperaExplicita(pestanadigitalizacionPage.Titulo);
			cargarPdfDigitalizacion(Pdf);
			esperaExplicitaNopresente(pestanadigitalizacionPage.Notificacion);
		} catch (Exception e) {
			log.error("########## Error - OriginacionCreditosAccion Cargue PDF - Digitalizacion()  #######" + e);
			assertTrue("########## Error - OriginacionCreditosAccion Cargue PDF - Digitalizacion()  ########"+ e,false);
		}
		

	}

	public void DigitalizacionCheck() throws InterruptedException {
		log.info("******************** OriginacionCreditosAccion seleccion Check - DigitalizacionCheck() ***************");
		try {
			MarcarCheckCorrecto();
			Hacer_scroll(pestanadigitalizacionPage.Guardar);
			hacerClick(pestanadigitalizacionPage.Guardar);
			ElementVisible();
			esperaExplicita(pestanadigitalizacionPage.Notificacion);
			hacerClicknotificacion();
			esperaExplicitaNopresente(pestanadigitalizacionPage.Notificacion);
		} catch (Exception e) {
			log.error("########## Error - OriginacionCreditosAccion Cargue PDF - DigitalizacionCheck()   #######" + e);
			assertTrue("########## Error - OriginacionCreditosAccion Cargue PDF - DigitalizacionCheck()   ########"+ e,false);
		}
		
	}

	public void formulario(String DestinoCredito, String Sexo, String EstadoCivil, String Direccion, String Dpto,
			String Ciudad, String TipoVivienda, String Correo, String Celular) throws InterruptedException {
		recorerpestanas("FORMULARIO");
		esperaExplicita(pestanaformulariopage.DestinoCredito);
		hacerClick(pestanaformulariopage.DestinoCredito);
		selectValorLista(pestanaformulariopage.ListaDestinoCredito, DestinoCredito);
		ElementVisible();
		Hacer_scroll(pestanaformulariopage.AgregarCuenta);

		if (Sexo == "M") {
			hacerClick(pestanaformulariopage.SexoM);
		} else {
			hacerClick(pestanaformulariopage.SexoF);
		}

		ElementVisible();
		hacerClick(pestanaformulariopage.EstadoCivil);
		selectValorLista(pestanaformulariopage.EstadoCivillist, EstadoCivil);
		ElementVisible();
		Clear(pestanaformulariopage.Correo);
		EscribirElemento(pestanaformulariopage.Correo, Correo);
		ElementVisible();
		Clear(pestanaformulariopage.Celular);
		EscribirElemento(pestanaformulariopage.Celular, Celular);
		ElementVisible();
		Clear(pestanaformulariopage.Direccion);
		EscribirElemento(pestanaformulariopage.Direccion, Direccion);
		ElementVisible();
		hacerClick(pestanaformulariopage.Departamento);
		selectValorLista(pestanaformulariopage.Departamentolist, Dpto);
		ElementVisible();
		hacerClick(pestanaformulariopage.Ciudad);
		selectValorLista(pestanaformulariopage.Ciudadlist, Ciudad);
		ElementVisible();
		hacerClick(pestanaformulariopage.Correspondencia);
		ElementVisible();
		hacerClick(pestanaformulariopage.Tipovivienda);
		selectValorLista(pestanaformulariopage.Tipoviviendalist, TipoVivienda);
		ElementVisible();
		Hacer_scroll(pestanaformulariopage.Guardar);
		hacerClick(pestanaformulariopage.Guardar);
		ElementVisible();
		//esperaExplicita(pestanadigitalizacionPage.Notificacion);
		//hacerClickVariasNotificaciones();
		// hacerClicknotificacion();
		// hacerClicknotificacion();
		esperaExplicitaNopresente(pestanadigitalizacionPage.Notificacion);
	}

	public void formularioSegundaPestana(String IngresosMes, String TotalActivos, String Papellido, String Pnombre,
			String Direccion, String TelefonoResidencia, String TelefonoTrabajo, String Dpto, String Ciudad)
			throws InterruptedException {
		recorerpestanas("FORMULARIO");//borrar despues de la prueba
		hacerClick(pestanaformulariopage.PestanaFormulario);
		ElementVisible();
		esperaExplicita(pestanaformulariopage.TituloReferencias);
		esperaExplicita(pestanaformulariopage.IngresosMes);
		hacerClick(pestanaformulariopage.IngresosMes);
		LimpiarConTeclado(pestanaformulariopage.IngresosMes);
		EscribirElemento(pestanaformulariopage.IngresosMes, IngresosMes);
		hacerClick(pestanaformulariopage.TotalActivos);
		LimpiarConTeclado(pestanaformulariopage.TotalActivos);
		EscribirElemento(pestanaformulariopage.TotalActivos, TotalActivos);
		esperaExplicita(pestanaformulariopage.MasReferencia);
		hacerClick(pestanaformulariopage.MasReferencia);
		ElementVisible();
		esperaExplicita(pestanaformulariopage.MasReferencia);
		Hacer_scroll(pestanaformulariopage.TituloReferencias);
		hacerClick(pestanaformulariopage.MasReferencia);
		ElementVisible();
		Hacer_scroll(pestanaformulariopage.TituloReferencias);
		ElementVisible();
		hacerClick(pestanaformulariopage.CheckFamiliar);
		ElementVisible();
		hacerClick(pestanaformulariopage.CheckPersonal);
		ElementVisible();
		llenarDepartamentoCiudadReferenciacion(pestanaformulariopage.DepartamentoList, pestanaformulariopage.CiudadList,
				Dpto, Ciudad, 2);

		llenarInputMultiples(pestanaformulariopage.PapellidoReferencia, Papellido);
		ElementVisible();
		llenarInputMultiples(pestanaformulariopage.PnombreReferencia, Pnombre);
		ElementVisible();
		llenarInputMultiples(pestanaformulariopage.DireccionReferencia, Direccion);
		ElementVisible();
		llenarInputMultiples(pestanaformulariopage.TelefonoResidencia, TelefonoResidencia);
		llenarInputMultiples(pestanaformulariopage.TelefonoTrabajo, TelefonoTrabajo);
		ElementVisible();
		Hacer_scroll(pestanaformulariopage.GuardarRefer);
		hacerClick(pestanaformulariopage.GuardarRefer);
		hacerClick(pestanaformulariopage.GuardarRefer);
		esperaExplicita(pestanadigitalizacionPage.Notificacion);
		ElementVisible();
		esperaExplicita(pestanadigitalizacionPage.Notificacion);
		hacerClicknotificacion();
		esperaExplicitaNopresente(pestanadigitalizacionPage.Notificacion);
	}

	public void DigitalizacionVerificacion() {
		recorerpestanas("DIGITALIZACIÓN");
		esperaExplicita(pestanadigitalizacionPage.Titulo);		
		  hacerClick(pestanadigitalizacionPage.EnVerificacion); ElementVisible();
		  esperaExplicita(pestanadigitalizacionPage.Notificacion);
		  hacerClicknotificacion();
		  esperaExplicitaNopresente(pestanadigitalizacionPage.Notificacion);
		 
	}

	public void Referenciaspositivas(String codigo) throws InterruptedException {

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
		EscribirElemento(pestanadigitalizacionPage.CodigoProforenses, codigo);
		ElementVisible();
		hacerClick(pestanadigitalizacionPage.IdentidadConfirmada);
		ElementVisible();
		hacerClick(pestanadigitalizacionPage.Guardar);
		// hacerClick(pestanadigitalizacionPage.Guardar);
		ElementVisible();
		esperaExplicitaNopresente(pestanadigitalizacionPage.Notificacion);

	}

	public void Radicar() throws InterruptedException {
		hacerClick(pestanadigitalizacionPage.Radicar);
		ElementVisible();
		esperaExplicita(pestanadigitalizacionPage.Notificacion);
		hacerClicknotificacion();
		hacerClicknotificacion();
		esperaExplicitaNopresente(pestanadigitalizacionPage.Notificacion);
	}

	public void ReferenciacionSolicitarAnalisis() throws InterruptedException {
		recorerpestanas("REFERENCIACIÓN");
		Hacer_scroll(pestanareferenciacionpage.SolicitarAnalisis);
		hacerClick(pestanareferenciacionpage.SolicitarAnalisis);
		ElementVisible();
		esperaExplicita(pestanadigitalizacionPage.Notificacion);
		hacerClicknotificacion();
		esperaExplicitaNopresente(pestanadigitalizacionPage.Notificacion);

	}

	/************ FIN ACCIONES SOLICITUD CREDITO ***************/

	/************ INICIA ACCIONES ANALISTA ***************/
	public void ingresarAnalisisCredito(String Cedula, String Nombre) throws InterruptedException {

		panelnavegacionaccion.navegarCreditoAnalisis();
		BuscarenGrilla(pestanasimuladorinternopage.FiltroCedula, Cedula);
		ElementVisible();
		esperaExplicitaTexto(Nombre);
		Thread.sleep(1000);		
		esperaExplicita(pestanasimuladorinternopage.EditarVer);
		ClicUltimoElemento(pestanasimuladorinternopage.EditarVer);
		ElementVisible();
		esperaExplicita(pestanasimuladorinternopage.inputMesada);
	}

	public void LlenarIngresos(String Ingresos, String descLey, String descNomina) throws InterruptedException {

		Clear(pestanasimuladorinternopage.inputMesada);
		EscribirElemento(pestanasimuladorinternopage.inputMesada, Ingresos);
		ElementVisible();
		Clear(pestanasimuladorinternopage.inputSalud);
		EscribirElemento(pestanasimuladorinternopage.inputSalud, descLey);
		Hacer_scroll(pestanasimuladorinternopage.Guardar);
		Clear(pestanasimuladorinternopage.DescuentoAfiliaciones);
		EscribirElemento(pestanasimuladorinternopage.DescuentoAfiliaciones, descNomina);
		ElementVisible();
		Hacer_scroll(pestanasimuladorinternopage.Guardar);
		hacerClick(pestanasimuladorinternopage.Guardar);
		ElementVisible();
		esperaExplicita(pestanadigitalizacionPage.Notificacion);
		hacerClicknotificacion();
		esperaExplicitaNopresente(pestanadigitalizacionPage.Notificacion);
			
    	
    }
    public void SegundaPestanaSimuladorAnalista () throws InterruptedException {
    	Thread.sleep(4000);
    	hacerClick(pestanasimuladorinternopage.SgdPestana);
		ElementVisible(); 
		esperaExplicita(pestanadigitalizacionPage.Notificacion);
		hacerClicknotificacion();
		
    }
    
    public void ValidarSimuladorAnalista(String Mes,String Monto,String Tasa,String Plazo,String Ingresos,String descLey, String descNomina, String pagaduria,String vlrCompasSaneamientos) throws InterruptedException, NumberFormatException, SQLException {
    	esperaExplicita(pestanasimuladorinternopage.MesDeAfecatcion); 
    	hacerClick(pestanasimuladorinternopage.MesDeAfecatcion);
    	ElementVisible (); 
    	selectValorLista(pestanasimuladorinternopage.ListaMes,Mes);
    	ElementVisible(); 
    	hacerClick(pestanasimuladorinternopage.CalcularDesglose);
    	ElementVisible(); 
    	hacerClicknotificacion();
    	esperaExplicitaNopresente(pestanadigitalizacionPage.Notificacion);
    	
    	/* // consulta base de datos
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
		ResultSet resultadocolchon = query.colchonpagaduria(pagaduria);
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
		
		//Valores CXC capitalizadas
		int mesDos =48;
		double tasaDos = 0.0075;
		double tasaUno = Double.parseDouble(Tasa)/100;

		// Validar resultados de simulacion

		assertvalidarEquals(TextoElemento(pestanasimuladorinternopage.MontoSolicitado),Monto);
		
		int Capacidad = (int) CapacidadPagaduria(Integer.parseInt(Ingresos), Integer.parseInt(descLey),Integer.parseInt(descNomina), colchon);
		assertvalidarEquals(TextoElemento(pestanasimuladorinternopage.CapacidadAsesor), String.valueOf(Capacidad));

		int calculoMontoSoli = (int) MontoaSolicitar(Integer.parseInt(Monto), DesPrimaAntic, Tasaxmillonseguro, EstudioCredito, TasaFianza, vlrIva);
		assertvalidarEquals(TextoElemento(pestanasimuladorinternopage.CapitalTotal), String.valueOf(calculoMontoSoli));


		int CuotaCorriente = (int) CuotaCorriente(calculoMontoSoli, tasaUno, Integer.parseInt(Plazo), tasaDos, mesDos);

		int PrimaAnticipadaSeguro = (int) PrimaAnticipadaSeguro(calculoMontoSoli, 1000000, Tasaxmillonseguro,
				DesPrimaAntic);
		// revisar calculo
		//assertvalidarEquals(TextoElemento(pestanasimuladorinternopage.PrimaAnticipadaSeguroAsesor),String.valueOf(PrimaAnticipadaSeguro));

		int MontoMaxDesembolsar = (int) MontoMaxDesembolsar(Integer.parseInt(Ingresos), Integer.parseInt(descLey),
				Integer.parseInt(descNomina), colchon, tasaUno,
				Integer.parseInt(Plazo), Tasaxmillonseguro, DesPrimaAntic,tasaDos, mesDos);
		assertvalidarEquals(TextoElemento(pestanasimuladorinternopage.MontoMaximoAsesor),String.valueOf(MontoMaxDesembolsar));
    		
	    int EstudioCreditoIva = (int) EstudioCreditoIva(calculoMontoSoli, EstudioCredito);
		assertvalidarEquals(TextoElemento(pestanasimuladorinternopage.ValorEstudioCreditoCXC), String.valueOf(EstudioCreditoIva));
		
		int ValorFianza = (int) ValorFianza(calculoMontoSoli,TasaFianza, variableFianza);
		assertvalidarEquals(TextoElemento(pestanasimuladorinternopage.ValorFianzaCXC), String.valueOf(ValorFianza));

		int Gmf4100 = (int) Gmf4100(Integer.parseInt(vlrCompasSaneamientos), 0.004);
		assertvalidarEquals(TextoElemento(pestanasimuladorinternopage.Gravamento4x1000), String.valueOf(Gmf4100));

		assertvalidarEquals(TextoElemento(pestanasimuladorinternopage.ValoraDesembolsar), Monto);
		assertvalidarEquals(TextoElemento(pestanasimuladorinternopage.MontoAsesor), Monto);
		assertvalidarEquals(TextoElemento(pestanasimuladorinternopage.PlazoAsesor), Plazo);
		assertvalidarEquals(TextoElemento(pestanasimuladorinternopage.TasaAsesor), Tasa);
		assertvalidarEquals(TextoElemento(pestanasimuladorinternopage.IngresosAsesor)
				.substring(0, TextoElemento(pestanasimuladorinternopage.IngresosAsesor).length() - 2)
				.replaceAll("[^a-zA-Z0-9]", ""), Ingresos);
		assertvalidarEquals(TextoElemento(pestanasimuladorinternopage.DescuentosLey)
				.substring(0, TextoElemento(pestanasimuladorinternopage.DescuentosLey).length() - 2)
				.replaceAll("[^a-zA-Z0-9]", ""), descLey);
		assertvalidarEquals(TextoElemento(pestanasimuladorinternopage.DescuentosNomina)
				.substring(0, TextoElemento(pestanasimuladorinternopage.DescuentosNomina).length() - 2)
				.replaceAll("[^a-zA-Z0-9]", ""), descNomina);*/

	}

	public void GuardarSimulacionAnalista() throws InterruptedException {
		ElementVisible();
		hacerClick(pestanasimuladorinternopage.GuardarSimulacion);
		ElementVisible();

	}

	public void EndeudamientoGlobal() throws InterruptedException {
		recorerpestanas("ENDEUDAMIENTO GLOBAL");
		hacerClick(pestanasimuladorinternopage.Aprobar);
		hacerClick(pestanasimuladorinternopage.Aprobar);
		assertTextonotificacion(simuladorasesorpage.notificacion,
				"Este crédito se ha enviado a flujo de aprobación de analisis.");
		ElementVisible();

	}

	public void AprobarTareaCredito(String Cedula) throws InterruptedException {
		panelnavegacionaccion.navegarTareas();
		esperaExplicita(pagestareas.filtroDescipcion);
		EscribirElemento(pagestareas.filtroDescipcion, Cedula);
		ElementVisible();
		hacerClick(pagestareas.EditarVer);
		ElementVisible();
		Hacer_scroll(pagestareas.Aprobar);
		esperaExplicita(pagestareas.Aprobar);
		hacerClick(pagestareas.Aprobar);
		ElementVisible();

	}

	/************ INICIA ACCIONES ANALISTA ***************/

	/************
	 * Clientes Para Bienvenidad
	 * 
	 * @throws InterruptedException
	 ******************/

	public void ClientesParaBienvenida(String Cedula) throws InterruptedException {
		panelnavegacionaccion.CreditoClientesBienvenida();
		ElementVisible();
		esperaExplicita(pagesclienteparabienvenida.filtrocedula);
		EscribirElemento(pagesclienteparabienvenida.filtrocedula, Cedula);
		ElementVisible();
		esperaExplicita(By.xpath("//td[text()='" + Cedula + "']"));
		hacerClick(pagesclienteparabienvenida.Continuar);
		ElementVisible();

	}

	public void Correctocondiciones(String Telefono, String Correo) throws InterruptedException {
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

	public void Aceptacondiconesdelcredito(String TipoDesen) throws InterruptedException {
		recorerpestanas("CONDICIONES DEL CRÉDITO");
		Refrescar();
		MarcarCheck(pagesclienteparabienvenida.CheckCondicionesCredito);
		// assertvalidarEquals(TextoElemento(pagesclienteparabienvenida.ValorDesembolsar),
		// String.valueOf(Integer.parseInt(TextoElemento(pestanasimuladorinternopage.MontoTotalAprobado))-(pestanasimuladorinternopage.SaldoAlDia)));
		Hacer_scroll(pagesclienteparabienvenida.detalledelascarteras);
		Thread.sleep(1000);
		hacerClick(pagesclienteparabienvenida.Desembolso);
		selectValorLista(pagesclienteparabienvenida.ListDesembolso, TipoDesen);
		hacerClick(pagesclienteparabienvenida.CalificacionProceso);
		hacerClick(pagesclienteparabienvenida.CalificacionCobro);
		hacerScrollAbajo();
		hacerClick(pagesclienteparabienvenida.Acepta);
		ElementVisible();
	}

	/************ FIN Clientes Para Bienvenidad *************/

	/************ Clientes Para Visacion **********/

	public void ClientesParaVisacion(String Cedula) throws InterruptedException {
		panelnavegacionaccion.CreditoClientesVisacion();
		ElementVisible();
		esperaExplicita(PagesClienteParaVisacion.filtrocedula);
		EscribirElemento(PagesClienteParaVisacion.filtrocedula, Cedula);
		ElementVisible();
		esperaExplicita(By.xpath("//td[text()='" + Cedula + "']"));
		hacerClick(PagesClienteParaVisacion.Continuar);
		ElementVisible();

	}

	public void AprobarCredito(String fecha, String pdf) {
		esperaExplicita(PagesClienteParaVisacion.AprobadoCheck);
		hacerClick(PagesClienteParaVisacion.AprobadoCheck);
		ElementVisible();
		Clear(PagesClienteParaVisacion.FechaResultado);
		EscribirElemento(PagesClienteParaVisacion.FechaResultado, fecha);
		EnviarEnter(PagesClienteParaVisacion.FechaResultado);
		ElementVisible();
		cargarpdf(PagesClienteParaVisacion.DocumentoLibranza, pdf);
		esperaExplicita(PagesClienteParaVisacion.cargapdf);
		hacerClick(PagesClienteParaVisacion.Aprobar);
		ElementVisible();

	}

	/************ FIN Clientes Para Visacion **********/

	/************ Creditos Para Desembolso ************/

	public void creditosparadesembolso(String Cedula) throws InterruptedException {
		panelnavegacionaccion.CreditoParaDesembolso();
		ElementVisible();
		esperaExplicita(PagesCreditosDesembolso.filtrocedula);
		EscribirElemento(PagesCreditosDesembolso.filtrocedula, Cedula);
		ElementVisible();
		esperaExplicita(By.xpath("//td[text()='" + Cedula + "']"));
		ElementVisible();

	}

	public void ProcesarPagos() {
		hacerClick(PagesCreditosDesembolso.CheckProcesarPagos);
		ElementVisible();
		hacerClick(PagesCreditosDesembolso.ProcesarPagos);
		ElementVisible();
	}

	public void DescargarMediosdedispercion(String Monto, String Banco, String Pdf) {
		panelnavegacionaccion.CreditoParaDesembolsoDescargar();
		esperaExplicita(PagesCreditosDesembolso.FiltroMonto);
		EscribirElemento(PagesCreditosDesembolso.FiltroMonto, Monto);
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

	/************ FIN Creditos Para Desembolso **********/
}
