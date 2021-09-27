package Acciones.CreditoAccion;

import static org.junit.Assert.assertTrue;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
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
	String Cedula;
	String NombreCredito;
	
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
		hacerClick(simuladorasesorpage.inputTasa);
		EscribirElemento(simuladorasesorpage.inputTasaFiltro,Tasa);
		EnviarEnter(simuladorasesorpage.inputTasaFiltro);
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
		ElementVisible();
		hacerClick(simuladorasesorpage.inputdDescNomina);
		ElementVisible();

	}	
	
	public void assertSimulador( String Fecha, String Tasa,String Plazo,String Monto,String DiasHabilesIntereses,String Ingresos,String descLey,String descNomina,String vlrCompasSaneamientos,String tipo, String pagaduria) throws NumberFormatException, SQLException{
		      
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
				
				
				//porcentajefianza
		
		       // Valores para la funciones estaticos
				int Tasaxmillonseguro = 4625;				
				double variableFianza = 1.19;
				
				// Validar resultados de simulacion

				int Capacidad = (int) CapacidadPagaduria(Integer.parseInt(Ingresos), Integer.parseInt(descLey),Integer.parseInt(descNomina),colchon);
				assertvalidarEquals(TextoElemento(simuladorasesorpage.CapacidadAproximada), String.valueOf(Capacidad));
				
				int edad = (int) edad(Fecha);
				assertvalidarEquals(TextoElemento(simuladorasesorpage.edad), String.valueOf(edad));

				int calculoMontoSoli = (int) MontoaSolicitar(Integer.parseInt(Monto), DesPrimaAntic, Tasaxmillonseguro);
				//assertvalidarEquals(TextoElemento(simuladorasesorpage.ResultMontoSoli), String.valueOf(calculoMontoSoli));
				ToleranciaPeso(Integer.parseInt(TextoElemento(simuladorasesorpage.ResultMontoSoli)),calculoMontoSoli);

				int CuotaCorriente = (int) CuotaCorriente(calculoMontoSoli, Double.parseDouble(Tasa), Integer.parseInt(Plazo));
				assertvalidarEquals(TextoElemento(simuladorasesorpage.CuotaCorriente), String.valueOf(CuotaCorriente));

				int EstudioCreditoIva = (int) EstudioCreditoIva(calculoMontoSoli, EstudioCredito);
				assertvalidarEquals(TextoElemento(simuladorasesorpage.EstudioCreditoIVA), String.valueOf(EstudioCreditoIva));

				int ValorFianza = (int) ValorFianza(calculoMontoSoli, TasaFianza, variableFianza);
				assertvalidarEquals(TextoElemento(simuladorasesorpage.ValorFianza), String.valueOf(ValorFianza));

				int Gmf4100 = (int) Gmf4100(Integer.parseInt(vlrCompasSaneamientos), 0.004);
				assertvalidarEquals(TextoElemento(simuladorasesorpage.Gmf4100), String.valueOf(Gmf4100));

				int ValorInteresesIniciales = (int) ValorInteresesIniciales(calculoMontoSoli, Double.parseDouble(Tasa),
						Integer.parseInt(DiasHabilesIntereses), 30);
				assertvalidarEquals(TextoElemento(simuladorasesorpage.Valorinteresesini),
						String.valueOf(ValorInteresesIniciales));

				int PrimaAnticipadaSeguro = (int) PrimaAnticipadaSeguro(calculoMontoSoli, 1000000, Tasaxmillonseguro,
						DesPrimaAntic);
				// revisar calculo
				assertvalidarEquals(TextoElemento(simuladorasesorpage.PrimaAnticipadaSeguro),String.valueOf(PrimaAnticipadaSeguro));

				int RemanenteEstimado = (int) RemanenteEstimado(calculoMontoSoli, Integer.parseInt(vlrCompasSaneamientos),
						Gmf4100, PrimaAnticipadaSeguro);
				assertvalidarEquals(TextoElemento(simuladorasesorpage.RemanenteEstimado), String.valueOf(RemanenteEstimado));

				int MontoMaxDesembolsar = (int) MontoMaxDesembolsar(Integer.parseInt(Ingresos), Integer.parseInt(descLey),
						Integer.parseInt(descNomina), colchon, Double.parseDouble(Tasa),
						Integer.parseInt(Plazo), Tasaxmillonseguro, DesPrimaAntic);
				//assertvalidarEquals(TextoElemento(simuladorasesorpage.MontoMaximoSugerido),String.valueOf(MontoMaxDesembolsar));
				//ToleranciaPeso(Integer.parseInt(TextoElemento(simuladorasesorpage.MontoMaximoSugerido)),MontoMaxDesembolsar);
		
	}
	
	public void GuardarSimulacion() throws InterruptedException {

		Hacer_scroll(simuladorasesorpage.btnGuardar);
		hacerClick(simuladorasesorpage.btnGuardar);
		assertEstaPresenteElemento(simuladorasesorpage.notificacionGuardado);
		hacerClicknotificacion();/////////////////////////////////////////////////////////////////
		esperaExplicita(simuladorasesorpage.notificacionGuardado);

	}

	public void CrearCliente(String TipoContrato, String FechaIngreso, String Pnombre, String Papellido,
			String Sapellido, String Correo, String Celular, String Dpto, String Ciudad) throws InterruptedException {
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
	}

	public void SubirDocumentos(String ruta) throws InterruptedException {

		cargarPdf(simuladorasesorpage.fileAutorizacion, simuladorasesorpage.fileCedula, simuladorasesorpage.fileNomina,
				ruta);
		ElementVisible();
	}

	public void ConsultaCentrales() throws InterruptedException {
		hacerClick(simuladorasesorpage.btnSoliConsulta);
		ElementVisible();
		esperaExplicita(simuladorasesorpage.notificacion);
		String TextoNotificacion=GetText(simuladorasesorpage.notificacion);
		
		if(TextoNotificacion.contains("OCURRIÓ UN ERROR")==true){
	    hacerClicknotificacion();
		hacerClick(simuladorasesorpage.btnSoliConsulta);
		ElementVisible();		
		hacerClicknotificacion();
		}
	}

	/************ FIN ACCIONES PARA SIMULADOR ASESOR ***************/

	// ==============================================================
	/************ INICIA ACCIONES SOLICITUD CREDITO ***************/

	public void ingresarSolicitudCredito(String Cedula, String NombreCredito) throws InterruptedException {
		this.Cedula = Cedula;
		this.NombreCredito = NombreCredito;
		panelnavegacionaccion.navegarCreditoSolicitud();
		BuscarenGrilla(creditocolicitudpage.inputCedula, Cedula);
		esperaExplicitaTexto(NombreCredito);
		ElementVisible();
		esperaExplicita(creditocolicitudpage.selectVerEditar);
		hacerClick(creditocolicitudpage.selectVerEditar);
		ElementVisible();
	}

	public void Seguridad() throws InterruptedException, NumberFormatException, SQLException {
		Refrescar();
		hacerClick(pestanaSeguridadPage.PestanaSeguridad);
		esperaExplicita(pestanaSeguridadPage.BotonGuardar);
		hacerClick(pestanaSeguridadPage.Viable);
		hacerClick(pestanaSeguridadPage.BotonGuardar);
		ElementVisible();
		String notificacion = GetText(simuladorasesorpage.notificacion);		
		if (!notificacion.equals("Proceso Realizado Correctamente")) {
			hacerClick(pestanaSeguridadPage.BotonGuardar);
			ElementVisible();	
		}
		assertTextonotificacion(simuladorasesorpage.notificacion, "Proceso Realizado Correctamente");
		esperaExplicitaNopresente(simuladorasesorpage.notificacion);
		Hacer_scroll(pestanaSeguridadPage.Concepto);
		esperaExplicitaSeguridad(pestanaSeguridadPage.BtnCheck,Cedula);
		recorerpestanas("SIMULADOR");
	}
	
	public void assertSimuladorinterno( String Fecha, String Tasa,String Plazo,String Monto,String DiasHabilesIntereses,String Ingresos,String descLey,String descNomina,String vlrCompasSaneamientos,String tipo,String pagaduria, String rutaPdf) throws NumberFormatException, SQLException, InterruptedException{
	      /*
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

			// Validar resultados de simulacion
            
			int edad = (int) edad(Fecha);
			assertvalidarEquals(TextoElemento(pestanasimuladorinternopage.edad), String.valueOf(edad));

			int calculoMontoSoli = (int) MontoaSolicitar(Integer.parseInt(Monto), DesPrimaAntic, Tasaxmillonseguro);
			assertvalidarEquals(TextoElemento(pestanasimuladorinternopage.ResultMontoSoli), String.valueOf(calculoMontoSoli));

			int CuotaCorriente = (int) CuotaCorriente(calculoMontoSoli, Double.parseDouble(Tasa), Integer.parseInt(Plazo));
			assertvalidarEquals(TextoElemento(pestanasimuladorinternopage.CuotaCorriente), String.valueOf(CuotaCorriente));

			int EstudioCreditoIva = (int) EstudioCreditoIva(calculoMontoSoli, EstudioCredito);
			assertvalidarEquals(TextoElemento(pestanasimuladorinternopage.EstudioCreditoIVA), String.valueOf(EstudioCreditoIva));

			int ValorFianza = (int) ValorFianza(calculoMontoSoli,TasaFianza, variableFianza);
			assertvalidarEquals(TextoElemento(pestanasimuladorinternopage.ValorFianza), String.valueOf(ValorFianza));

			int Gmf4100 = (int) Gmf4100(Integer.parseInt(vlrCompasSaneamientos), 0.004);
			assertvalidarEquals(TextoElemento(pestanasimuladorinternopage.Gmf4100), String.valueOf(Gmf4100));

			int ValorInteresesIniciales = (int) ValorInteresesIniciales(calculoMontoSoli, Double.parseDouble(Tasa),
					Integer.parseInt(DiasHabilesIntereses), 30);
			assertvalidarEquals(TextoElemento(pestanasimuladorinternopage.Valorinteresesini),
					String.valueOf(ValorInteresesIniciales));

			int PrimaAnticipadaSeguro = (int) PrimaAnticipadaSeguro(calculoMontoSoli, 1000000, Tasaxmillonseguro,
					DesPrimaAntic);
			// revisar calculo
			//assertvalidarEquals(TextoElemento(simuladorasesorpage.PrimaAnticipadaSeguro),String.valueOf(PrimaAnticipadaSeguro));

			int RemanenteEstimado = (int) RemanenteEstimado(calculoMontoSoli, Integer.parseInt(vlrCompasSaneamientos),
					Gmf4100, PrimaAnticipadaSeguro);
			assertvalidarEquals(TextoElemento(pestanasimuladorinternopage.RemanenteEstimado), String.valueOf(RemanenteEstimado));

			int MontoMaxDesembolsar = (int) MontoMaxDesembolsar(Integer.parseInt(Ingresos), Integer.parseInt(descLey),
					Integer.parseInt(descNomina), colchon, Double.parseDouble(Tasa),
					Integer.parseInt(Plazo), Tasaxmillonseguro, DesPrimaAntic);
			assertvalidarEquals(TextoElemento(pestanasimuladorinternopage.MontoMaximoSugerido),
					String.valueOf(MontoMaxDesembolsar));
			*/
			Hacer_scroll(pestanasimuladorinternopage.Solicitar);
			hacerClick(pestanasimuladorinternopage.Solicitar);
			esperaExplicita(simuladorasesorpage.notificacion);
			ElementVisible();
			hacerClicknotificacion();
			if(!EncontrarElementoVisibleCss(pestanasimuladorinternopage.ModalExcepciones)) {
				// esperaExplicita(pestanasimuladorinternopage.DetalleExcepciones);
				this.AprobarExcepciones(rutaPdf, this.Cedula);
				this.ingresarSolicitudCredito(this.Cedula, this.NombreCredito);
			}
			ElementVisible();
}
	
	public void Digitalizacion(String Pdf) throws InterruptedException  {
		recorerpestanas("DIGITALIZACIÓN");
		esperaExplicita(pestanadigitalizacionPage.Titulo);
		cargarPdfDigitalizacion(Pdf);
		esperaExplicitaNopresente(pestanadigitalizacionPage.Notificacion);

	}

	public void DigitalizacionCheck() throws InterruptedException {
		MarcarCheckCorrecto();
		Hacer_scroll(pestanadigitalizacionPage.Guardar);
		hacerClick(pestanadigitalizacionPage.Guardar);
		ElementVisible();
		esperaExplicita(pestanadigitalizacionPage.Notificacion);
		hacerClicknotificacion();
		esperaExplicitaNopresente(pestanadigitalizacionPage.Notificacion);
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
		  hacerClick(pestanadigitalizacionPage.EnVerificacion); 
		  ElementVisible();
		  esperaExplicita(pestanadigitalizacionPage.Notificacion);
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
		this.Cedula = Cedula;
		this.NombreCredito = Nombre;
		panelnavegacionaccion.navegarCreditoAnalisis();	
		BuscarenGrilla(pestanasimuladorinternopage.FiltroCedula, Cedula);		
		ElementVisible();
		Thread.sleep(1000);
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

		// Validar resultados de simulacion

		assertvalidarEquals(TextoElemento(pestanasimuladorinternopage.MontoSolicitado),Monto);
		
		int Capacidad = (int) CapacidadPagaduria(Integer.parseInt(Ingresos), Integer.parseInt(descLey),Integer.parseInt(descNomina), colchon);
		assertvalidarEquals(TextoElemento(pestanasimuladorinternopage.CapacidadAsesor), String.valueOf(Capacidad));

		int calculoMontoSoli = (int) MontoaSolicitar(Integer.parseInt(Monto), DesPrimaAntic, Tasaxmillonseguro);
		assertvalidarEquals(TextoElemento(pestanasimuladorinternopage.CapitalTotal), String.valueOf(calculoMontoSoli));

		int CuotaCorriente = (int) CuotaCorriente(calculoMontoSoli, Double.parseDouble(Tasa), Integer.parseInt(Plazo));
		assertvalidarEquals(TextoElemento(pestanasimuladorinternopage.ValorCuota).replaceAll("[^a-zA-Z0-9]", ""),
				String.valueOf(CuotaCorriente));

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
				.replaceAll("[^a-zA-Z0-9]", ""), descNomina);

	}

	public void GuardarSimulacionAnalista() throws InterruptedException {
		ElementVisible();
		hacerClick(pestanasimuladorinternopage.GuardarSimulacion);
		ElementVisible();

	}

	public void EndeudamientoGlobal() throws InterruptedException {
		recorerpestanas("ENDEUDAMIENTO GLOBAL");
		hacerClick(pestanasimuladorinternopage.Aprobar);
		// hacerClick(pestanasimuladorinternopage.Aprobar);
		// assertTextonotificacion(simuladorasesorpage.notificacion, "Este crédito se ha enviado a flujo de aprobación de analisis.");
		esperaExplicita(simuladorasesorpage.notificacion);
		String notificacion = GetText(simuladorasesorpage.notificacion);
		ElementVisible();			
		if (!notificacion.equals("Este crédito se ha enviado a flujo de aprobación de analisis.")) {
			this.MostrarReferencias();
		}
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
	
	public void ValidarValoresLlamadoBienvenida() throws NumberFormatException, SQLException {
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
		
		log.info("suma retanqueo y saldo al dia  "+ (Integer.parseInt(ValoresCredito[11])+SaldoAlDia));
		
		int calculoMontoSoli = (int) MontoaSolicitar(Integer.parseInt(ValoresCredito[11])+SaldoAlDia, DesPrimaAntic, Tasaxmillonseguro, EstudioCredito, TasaFianza, vlrIva);
				
		int PrimaAnticipadaSeguro = (int) PrimaAnticipadaSeguro(Integer.parseInt(ValoresCredito[11])+SaldoAlDia, 1000000, Tasaxmillonseguro,DesPrimaAntic);
		System.out.println("######## CALCULO DE PRIMA ######## "+PrimaAnticipadaSeguro+" "+ValoresCredito[10].isEmpty()+" "+DesPrimaAntic);
	  
			
		if(ValoresCredito[10].isEmpty()==true) {
		 calculoMontoSoli=calculoMontoSoli-PrimaAnticipadaSeguro;
		 ToleranciaPesoMensaje("###### ERROR CALCULANDO MONTO SOLICITUD IF ########",Integer.parseInt(ValoresCredito[0]),calculoMontoSoli);
		}else {
		 ToleranciaPesoMensaje("###### ERROR CALCULANDO MONTO SOLICITUD ELSE ########",Integer.parseInt(ValoresCredito[0]),calculoMontoSoli);
		}
		
		int ValorFianza = (int) ValorFianza(Integer.parseInt(ValoresCredito[11])+SaldoAlDia, TasaFianza, variableFianza);
		ToleranciaPesoMensaje("###### ERROR SIM ASESOR - CALCULANDO VALOR FIANZA ########",Integer.parseInt(ValoresCredito[14]),ValorFianza);				
		int EstudioCreditoIva = (int) EstudioCreditoIvacxc(Integer.parseInt(ValoresCredito[11])+SaldoAlDia, EstudioCredito);
		ToleranciaPesoMensaje("###### ERROR SIM ASESOR - CALCULANDO ESTUDIO CREDITO ########",Integer.parseInt(ValoresCredito[16]),EstudioCreditoIva);
	
		
		
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
		Thread.sleep(1000);
		hacerClicknotificacion();
		MarcarCheck(pagesclienteparabienvenida.CheckCondicionesCredito);
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

	public void MostrarReferencias() throws InterruptedException {
		panelnavegacionaccion.navegarCreditoSolicitud();
		esperaExplicita(pestanasimuladorinternopage.FiltroCedulaCredito);
		BuscarenGrilla(pestanasimuladorinternopage.FiltroCedulaCredito, this.Cedula);
		Thread.sleep(2000);
		ElementVisible();
		esperaExplicita(creditocolicitudpage.selectVerEditar);
		ClicUltimoElemento(creditocolicitudpage.selectVerEditar);
		ElementVisible();
		recorerpestanas("REFERENCIACIÓN");
		Hacer_scroll(creditocolicitudpage.SegundaPestana);
		esperaExplicita(creditocolicitudpage.SegundaPestana);
		hacerClick(creditocolicitudpage.SegundaPestana);
		ElementVisible();
		this.AgregarReferencias();
	}

	public void AgregarReferencias() throws InterruptedException {
		clickVariosReferenciasPositivas(creditocolicitudpage.ListBtnAddReference);
		Hacer_scroll(pestanareferenciacionpage.Titulo);
		clickvarios(pestanareferenciacionpage.ReferenciaPositiva);
		ElementVisible();
		Hacer_scroll(pestanareferenciacionpage.Titulo);
		clickvarios(pestanareferenciacionpage.CheckSI);
		Hacer_scroll(pestanareferenciacionpage.GuardarReferencias);
		hacerClick(pestanareferenciacionpage.GuardarReferencias);
		ElementVisible();
		this.ingresarAnalisisCredito(this.Cedula, this.NombreCredito);
		this.EndeudamientoGlobal();
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
   		 Hacer_scroll(pagestareas.Aprobar);
   		 hacerClick(pagestareas.Aprobar);
   		 ElementVisible();
   		 hacerClickVariasNotificaciones();
   		 esperaExplicita(pagestareas.Guardar);
   		 Hacer_scroll(pagestareas.Guardar);
   		 hacerClick(pagestareas.Guardar);
   		 hacerClicknotificacion();
		} else {
			assertTrue("#### Error Aprobar excepcion por Perfil " + notificacion, false);
		}
   
    }
}
