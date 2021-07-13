package Acciones;

import static org.junit.Assert.assertTrue;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import Archivo.LeerArchivo;
import CommonFuntions.BaseTest;
import Consultas.CertificacionSaldoQuery;
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

	public OriginacionCreditosAccion(WebDriver driver) {
		///this.driver = driver;
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
		pagesclienteparabienvenida= new PagesClienteParaBienvenida(driver);
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
	
	public void assertSimulador( String Fecha, String Tasa,String Plazo,String Monto,String DiasHabilesIntereses,String Ingresos,String descLey,String descNomina,String vlrCompasSaneamientos,String tipo,String colchon) throws NumberFormatException, SQLException{
		      
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

				int Capacidad = (int) CapacidadPagaduria(Integer.parseInt(Ingresos), Integer.parseInt(descLey),Integer.parseInt(descNomina));
				assertvalidarEquals(TextoElemento(simuladorasesorpage.CapacidadAproximada), String.valueOf(Capacidad));
				
				int edad = (int) edad(Fecha);
				assertvalidarEquals(TextoElemento(simuladorasesorpage.edad), String.valueOf(edad));

				int calculoMontoSoli = (int) MontoaSolicitar(Integer.parseInt(Monto), DesPrimaAntic, Tasaxmillonseguro);
				assertvalidarEquals(TextoElemento(simuladorasesorpage.ResultMontoSoli), String.valueOf(calculoMontoSoli));

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
				//assertvalidarEquals(TextoElemento(simuladorasesorpage.PrimaAnticipadaSeguro),String.valueOf(PrimaAnticipadaSeguro));

				int RemanenteEstimado = (int) RemanenteEstimado(calculoMontoSoli, Integer.parseInt(vlrCompasSaneamientos),
						Gmf4100, PrimaAnticipadaSeguro);
				assertvalidarEquals(TextoElemento(simuladorasesorpage.RemanenteEstimado), String.valueOf(RemanenteEstimado));

				int MontoMaxDesembolsar = (int) MontoMaxDesembolsar(Integer.parseInt(Ingresos), Integer.parseInt(descLey),
						Integer.parseInt(descNomina), Integer.parseInt(colchon), Double.parseDouble(Tasa),
						Integer.parseInt(Plazo), Tasaxmillonseguro, DesPrimaAntic);
				assertvalidarEquals(TextoElemento(simuladorasesorpage.MontoMaximoSugerido),
						String.valueOf(MontoMaxDesembolsar));
		
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
		assertTextonotificacion(simuladorasesorpage.notificacion,"Se ha solicitado la consulta en listas y centrales de riesgo para el crédito:");
		hacerClicknotificacion();
	}
	
	/************FIN ACCIONES PARA SIMULADOR ASESOR***************/

	//==============================================================
	/************INICIA ACCIONES SOLICITUD CREDITO***************/
	
	public void ingresarSolicitudCredito(String Cedula,String NombreCredito) throws InterruptedException {
		
        panelnavegacionaccion.navegarCreditoSolicitud();
        BuscarenGrilla(creditocolicitudpage.inputCedula,Cedula);
        esperaExplicitaTexto(NombreCredito);
        ElementVisible();  
        esperaExplicita(creditocolicitudpage.selectVerEditar);
        hacerClick(creditocolicitudpage.selectVerEditar);
        ElementVisible(); 
	}
	
	public void Seguridad() throws InterruptedException  {
		Refrescar();
		hacerClick(pestanaSeguridadPage.PestanaSeguridad);	
		esperaExplicita(pestanaSeguridadPage.BotonGuardar);
		hacerClick(pestanaSeguridadPage.BotonGuardar);
		assertTextonotificacion(simuladorasesorpage.notificacion,"Proceso Realizado Correctamente");
		esperaExplicitaNopresente(simuladorasesorpage.notificacion);
		esperaExplicita(pestanaSeguridadPage.Concepto);
		esperaExplicitaSeguridad(pestanaSeguridadPage.BtnCheck);
		Refrescar();
		esperaExplicita(pestanaSeguridadPage.Concepto);
		Hacer_scroll(pestanaSeguridadPage.Concepto);
		esperaExplicitaSeguridad(pestanaSeguridadPage.BtnCheck);		
		recorerpestanas("SIMULADOR");
		
	}
	
	public void assertSimuladorinterno( String Fecha, String Tasa,String Plazo,String Monto,String DiasHabilesIntereses,String Ingresos,String descLey,String descNomina,String vlrCompasSaneamientos,String tipo,String colchon) throws NumberFormatException, SQLException, InterruptedException{
	      
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
					Integer.parseInt(descNomina), Integer.parseInt(colchon), Double.parseDouble(Tasa),
					Integer.parseInt(Plazo), Tasaxmillonseguro, DesPrimaAntic);
			assertvalidarEquals(TextoElemento(pestanasimuladorinternopage.MontoMaximoSugerido),
					String.valueOf(MontoMaxDesembolsar));
			
			Hacer_scroll(pestanasimuladorinternopage.Solicitar);
			hacerClick(pestanasimuladorinternopage.Solicitar);
			esperaExplicita(simuladorasesorpage.notificacion);
			assertTextonotificacion(simuladorasesorpage.notificacion,"Se ha solicitado la radicación para el crédito");
			ElementVisible();  
	
}
	
	public void Digitalizacion(String Pdf) throws InterruptedException  {
		recorerpestanas("DIGITALIZACIÓN");
		esperaExplicita(pestanadigitalizacionPage.Titulo);
		cargarPdfDigitalizacion(Pdf);
		esperaExplicitaNopresente(pestanadigitalizacionPage.Notificacion);
		
	}
	
	public void DigitalizacionCheck() throws InterruptedException  {
		MarcarCheckCorrecto();
		Hacer_scroll(pestanadigitalizacionPage.Guardar);
		hacerClick(pestanadigitalizacionPage.Guardar);
		ElementVisible();
		esperaExplicita(pestanadigitalizacionPage.Notificacion); 
		hacerClicknotificacion();
		esperaExplicitaNopresente(pestanadigitalizacionPage.Notificacion);
	}
	
	public void formulario(String DestinoCredito,String Sexo,String EstadoCivil,String Direccion,String Dpto,String Ciudad,String TipoVivienda,String Correo,String Celular) throws InterruptedException  {
		recorerpestanas("FORMULARIO");
		esperaExplicita(pestanaformulariopage.DestinoCredito);
		hacerClick(pestanaformulariopage.DestinoCredito);
		selectValorLista(pestanaformulariopage.ListaDestinoCredito,DestinoCredito);
		ElementVisible(); 
		Hacer_scroll(pestanaformulariopage.AgregarCuenta);
		
		if(Sexo=="M") {                                          
		   hacerClick(pestanaformulariopage.SexoM);
		}else{
		   hacerClick(pestanaformulariopage.SexoF);
		}
		
		ElementVisible(); 
		hacerClick(pestanaformulariopage.EstadoCivil);
		selectValorLista(pestanaformulariopage.EstadoCivillist,EstadoCivil);
		ElementVisible(); 
		Clear(pestanaformulariopage.Correo);
		EscribirElemento(pestanaformulariopage.Correo,Correo);
		ElementVisible(); 
		Clear(pestanaformulariopage.Celular);
		EscribirElemento(pestanaformulariopage.Celular,Celular);
		ElementVisible(); 		
		Clear(pestanaformulariopage.Direccion);
		EscribirElemento(pestanaformulariopage.Direccion,Direccion);
		ElementVisible(); 
		hacerClick(pestanaformulariopage.Departamento);		
		selectValorLista(pestanaformulariopage.Departamentolist,Dpto);
		ElementVisible(); 
		hacerClick(pestanaformulariopage.Ciudad);		
		selectValorLista(pestanaformulariopage.Ciudadlist,Ciudad);
		ElementVisible(); 
		hacerClick(pestanaformulariopage.Correspondencia);
		ElementVisible(); 
		hacerClick(pestanaformulariopage.Tipovivienda);		
		selectValorLista(pestanaformulariopage.Tipoviviendalist,TipoVivienda);
		ElementVisible();
		Hacer_scroll(pestanaformulariopage.Guardar);
		hacerClick(pestanaformulariopage.Guardar);
		ElementVisible();
		esperaExplicita(pestanadigitalizacionPage.Notificacion);
		hacerClickVariasNotificaciones();
		//hacerClicknotificacion();
		//hacerClicknotificacion();
		esperaExplicitaNopresente(pestanadigitalizacionPage.Notificacion);	
	}
	
	public void formularioSegundaPestana(String IngresosMes,String TotalActivos,String Papellido,String Pnombre,String Direccion,String TelefonoResidencia,String TelefonoTrabajo,String Dpto,String Ciudad) throws InterruptedException {
		hacerClick(pestanaformulariopage.PestanaFormulario);
		ElementVisible();
		esperaExplicita(pestanaformulariopage.TituloReferencias);		
		hacerClick(pestanaformulariopage.IngresosMes);
		LimpiarConTeclado(pestanaformulariopage.IngresosMes);
		EscribirElemento(pestanaformulariopage.IngresosMes,IngresosMes);
		hacerClick(pestanaformulariopage.TotalActivos);
		LimpiarConTeclado(pestanaformulariopage.TotalActivos);
		EscribirElemento(pestanaformulariopage.TotalActivos,TotalActivos);
		hacerClick(pestanaformulariopage.MasReferencia);
		ElementVisible();
		Hacer_scroll(pestanaformulariopage.TituloReferencias);
		hacerClick(pestanaformulariopage.MasReferencia);
		ElementVisible();
		Hacer_scroll(pestanaformulariopage.TituloReferencias);
		ElementVisible();
		hacerClick(pestanaformulariopage.CheckFamiliar);
		ElementVisible();
		hacerClick(pestanaformulariopage.CheckPersonal);
		ElementVisible();		
		llenarDepartamentoCiudadReferenciacion(pestanaformulariopage.DepartamentoList, pestanaformulariopage.CiudadList,Dpto,Ciudad,2);
		
		llenarInputMultiples(pestanaformulariopage.PapellidoReferencia,Papellido);
		ElementVisible();
		llenarInputMultiples(pestanaformulariopage.PnombreReferencia,Pnombre);
		ElementVisible();
		llenarInputMultiples(pestanaformulariopage.DireccionReferencia,Direccion);
		ElementVisible();
	    llenarInputMultiples(pestanaformulariopage.TelefonoResidencia,TelefonoResidencia);
		llenarInputMultiples(pestanaformulariopage.TelefonoTrabajo,TelefonoTrabajo);
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
	    EscribirElemento(pestanadigitalizacionPage.CodigoProforenses,codigo);
		ElementVisible();
		hacerClick(pestanadigitalizacionPage.IdentidadConfirmada);
		ElementVisible();
		hacerClick(pestanadigitalizacionPage.Guardar);
		//hacerClick(pestanadigitalizacionPage.Guardar);
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
	/************FIN ACCIONES SOLICITUD CREDITO***************/
	
	/************INICIA ACCIONES ANALISTA***************/
    public void ingresarAnalisisCredito(String Cedula,String Nombre) throws InterruptedException {
		
        panelnavegacionaccion.navegarCreditoAnalisis();
        BuscarenGrilla(pestanasimuladorinternopage.FiltroCedula,Cedula);
        esperaExplicitaTexto(Nombre);
        ElementVisible();  
        esperaExplicita(pestanasimuladorinternopage.EditarVer);
        hacerClick(pestanasimuladorinternopage.EditarVer);
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
    public void SegundaPestanaSimuladorAnalista () {
    	hacerClick(pestanasimuladorinternopage.SgdPestana);
		ElementVisible(); 
		esperaExplicita(pestanadigitalizacionPage.Notificacion);
		hacerClicknotificacion();
		
    }
    
    public void ValidarSimuladorAnalista(String Mes,String Monto,String Tasa,String Plazo,String Ingresos,String descLey, String descNomina, String colchon,String vlrCompasSaneamientos) throws InterruptedException, NumberFormatException, SQLException {
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
		
		int Capacidad = (int) CapacidadPagaduria(Integer.parseInt(Ingresos), Integer.parseInt(descLey),Integer.parseInt(descNomina));
		assertvalidarEquals(TextoElemento(pestanasimuladorinternopage.CapacidadAsesor), String.valueOf(Capacidad));

		int calculoMontoSoli = (int) MontoaSolicitar(Integer.parseInt(Monto), DesPrimaAntic, Tasaxmillonseguro);
		assertvalidarEquals(TextoElemento(pestanasimuladorinternopage.CapitalTotal), String.valueOf(calculoMontoSoli));

		int CuotaCorriente = (int) CuotaCorriente(calculoMontoSoli, Double.parseDouble(Tasa), Integer.parseInt(Plazo));
		assertvalidarEquals(TextoElemento(pestanasimuladorinternopage.ValorCuota).replaceAll("[^a-zA-Z0-9]", ""), String.valueOf(CuotaCorriente));

		int PrimaAnticipadaSeguro = (int) PrimaAnticipadaSeguro(calculoMontoSoli, 1000000, Tasaxmillonseguro,
				DesPrimaAntic);
		// revisar calculo
		//assertvalidarEquals(TextoElemento(pestanasimuladorinternopage.PrimaAnticipadaSeguroAsesor),String.valueOf(PrimaAnticipadaSeguro));

		int MontoMaxDesembolsar = (int) MontoMaxDesembolsar(Integer.parseInt(Ingresos), Integer.parseInt(descLey),Integer.parseInt(descNomina), Integer.parseInt(colchon), Double.parseDouble(Tasa),Integer.parseInt(Plazo), Tasaxmillonseguro, DesPrimaAntic);
		assertvalidarEquals(TextoElemento(pestanasimuladorinternopage.MontoMaximoAsesor),String.valueOf(MontoMaxDesembolsar));
    		
	    int EstudioCreditoIva = (int) EstudioCreditoIva(calculoMontoSoli, EstudioCredito);
		assertvalidarEquals(TextoElemento(pestanasimuladorinternopage.ValorEstudioCreditoCXC), String.valueOf(EstudioCreditoIva));
		
		int ValorFianza = (int) ValorFianza(calculoMontoSoli,TasaFianza, variableFianza);
		assertvalidarEquals(TextoElemento(pestanasimuladorinternopage.ValorFianzaCXC), String.valueOf(ValorFianza));

		int Gmf4100 = (int) Gmf4100(Integer.parseInt(vlrCompasSaneamientos), 0.004);
		assertvalidarEquals(TextoElemento(pestanasimuladorinternopage.Gravamento4x1000), String.valueOf(Gmf4100));

		assertvalidarEquals(TextoElemento(pestanasimuladorinternopage.ValoraDesembolsar),Monto);
		assertvalidarEquals(TextoElemento(pestanasimuladorinternopage.MontoAsesor),Monto);
		assertvalidarEquals(TextoElemento(pestanasimuladorinternopage.PlazoAsesor),Plazo);
		assertvalidarEquals(TextoElemento(pestanasimuladorinternopage.TasaAsesor),Tasa);
		assertvalidarEquals(TextoElemento(pestanasimuladorinternopage.IngresosAsesor).substring(0,TextoElemento(pestanasimuladorinternopage.IngresosAsesor).length()-2).replaceAll("[^a-zA-Z0-9]", ""),Ingresos);
		assertvalidarEquals(TextoElemento(pestanasimuladorinternopage.DescuentosLey).substring(0,TextoElemento(pestanasimuladorinternopage.DescuentosLey).length()-2).replaceAll("[^a-zA-Z0-9]", ""),descLey);
		assertvalidarEquals(TextoElemento(pestanasimuladorinternopage.DescuentosNomina).substring(0,TextoElemento(pestanasimuladorinternopage.DescuentosNomina).length()-2).replaceAll("[^a-zA-Z0-9]", ""),descNomina);

    }
    
    
    public void GuardarSimulacionAnalista() throws InterruptedException {
    	ElementVisible(); 
    	hacerClick(pestanasimuladorinternopage.GuardarSimulacion);
    	ElementVisible(); 

    }
    
    public void EndeudamientoGlobal() throws InterruptedException{
    	recorerpestanas("ENDEUDAMIENTO GLOBAL");
    	hacerClick(pestanasimuladorinternopage.Aprobar);
    	hacerClick(pestanasimuladorinternopage.Aprobar);
    	assertTextonotificacion(simuladorasesorpage.notificacion,"Este crédito se ha enviado a flujo de aprobación de analisis.");
    	ElementVisible(); 
    	
    }
	
    public void AprobarTareaCredito(String Cedula) throws InterruptedException{
    	panelnavegacionaccion.navegarTareas();
    	esperaExplicita(pagestareas.filtroDescipcion);
    	EscribirElemento(pagestareas.filtroDescipcion,Cedula);
    	ElementVisible(); 
    	hacerClick(pagestareas.EditarVer);
    	ElementVisible(); 
    	Hacer_scroll(pagestareas.Aprobar);
    	esperaExplicita(pagestareas.Aprobar);
    	hacerClick(pagestareas.Aprobar);
    	ElementVisible();     	
    	
    }
	/************INICIA ACCIONES ANALISTA***************/
    
    /************Clientes Para Bienvenidad 
     * @throws InterruptedException ******************/
    
    public void ClientesParaBienvenida(String Cedula) throws InterruptedException {
    	panelnavegacionaccion.CreditoClientesBienvenida();
    	ElementVisible();     	
    	esperaExplicita(pagesclienteparabienvenida.filtrocedula);
    	EscribirElemento(pagesclienteparabienvenida.filtrocedula,Cedula);
    	ElementVisible(); 
    	esperaExplicita(By.xpath("//td[text()='"+Cedula+"']"));
    	hacerClick(pagesclienteparabienvenida.Continuar);
    	ElementVisible(); 
        
        
    }
    
    public void Correctocondiciones(String Telefono,String Correo) throws InterruptedException {
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
    
    
    /************FIN Clientes Para Bienvenidad *************/
    
    /************Clientes Para Visacion **********/
    
    public void ClientesParaVisacion(String Cedula) throws InterruptedException {
    	panelnavegacionaccion.CreditoClientesVisacion();
    	ElementVisible();     	
    	esperaExplicita(PagesClienteParaVisacion.filtrocedula);
    	EscribirElemento(PagesClienteParaVisacion.filtrocedula,Cedula);
    	ElementVisible(); 
    	esperaExplicita(By.xpath("//td[text()='"+Cedula+"']"));
    	hacerClick(PagesClienteParaVisacion.Continuar);
    	ElementVisible(); 
        
        
    }
    
    public void AprobarCredito(String fecha,String pdf) {
    	esperaExplicita(PagesClienteParaVisacion.AprobadoCheck);
    	hacerClick(PagesClienteParaVisacion.AprobadoCheck);
    	ElementVisible();
    	Clear(PagesClienteParaVisacion.FechaResultado);
    	EscribirElemento(PagesClienteParaVisacion.FechaResultado,fecha);
    	EnviarEnter(PagesClienteParaVisacion.FechaResultado);
    	ElementVisible();
    	cargarpdf(PagesClienteParaVisacion.DocumentoLibranza,pdf);
    	esperaExplicita(PagesClienteParaVisacion.cargapdf);
    	hacerClick(PagesClienteParaVisacion.Aprobar);
    	ElementVisible();
    	
    }
    /************FIN Clientes Para Visacion **********/
    
    /************ Creditos Para Desembolso ************/
    
    public void creditosparadesembolso(String Cedula) throws InterruptedException {
    	panelnavegacionaccion.CreditoParaDesembolso();
    	ElementVisible();     	
    	esperaExplicita(PagesCreditosDesembolso.filtrocedula);
    	EscribirElemento(PagesCreditosDesembolso.filtrocedula,Cedula);
    	ElementVisible(); 
    	esperaExplicita(By.xpath("//td[text()='"+Cedula+"']"));
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
    	EscribirElemento(PagesCreditosDesembolso.FiltroMonto,Monto);
    	ElementVisible(); 
    	
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
    
    
    
    /************ FIN Creditos Para Desembolso **********/
}
