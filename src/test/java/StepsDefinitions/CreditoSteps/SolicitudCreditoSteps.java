package StepsDefinitions.CreditoSteps;

import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import Acciones.CreditoAccion.OriginacionCreditosAccion;
import Acciones.CreditoAccion.SolicitudCompraCarteraSaneamientoAccion;
import CommonFuntions.BaseTest;
import StepsDefinitions.CommunSteps.Driver;
import cucumber.api.java.es.Cuando;
import cucumber.api.java.es.Entonces;
import cucumber.api.java.es.Y;
import io.cucumber.datatable.DataTable;

public class SolicitudCreditoSteps {
	
	WebDriver driver;
	Logger log = Logger.getLogger(SolicitudCreditoSteps.class);
	OriginacionCreditosAccion originacionaccion;
	SolicitudCompraCarteraSaneamientoAccion solicitudCompraCarteraSaneamientoAccion;
	BaseTest baseTest;
	
	public SolicitudCreditoSteps() throws InterruptedException {		
		driver = Driver.driver;		
		originacionaccion = new OriginacionCreditosAccion(driver);
		solicitudCompraCarteraSaneamientoAccion = new SolicitudCompraCarteraSaneamientoAccion(driver);
		baseTest = new BaseTest(driver);
	}
	
	@Cuando("el agente ingrese a la pestana solicitud credito con la cedula del cliente {string}{string}")
	public void el_agente_ingrese_a_la_pestana_solicitud_credito_con_la_cedula_del_cliente_Cedula(String Cedula,String NombreCredito) throws InterruptedException {
		originacionaccion.ingresarSolicitudCredito(Cedula,NombreCredito);
	}

	@Y("consulta la pestana seguridad dejando el cliente viable")
	public void consultaLaPestanaSeguridadDejandoElClienteViable() throws InterruptedException, NumberFormatException, SQLException {
		originacionaccion.Seguridad();
	}

	@Y("valida los calculos correctos de la simulacion interna {string}{string}{string}{string}{string}{string}{string}{string}{string}{string}{string}{string}")
	public void valida_los_calculos_correctos_de_la_simulacion_interna(String Fecha, String Tasa,String Plazo,String Monto,String DiasHabilesIntereses,String Ingresos,String descLey,String descNomina,String vlrCompasSaneamientos,String tipo,String pagaduria, String rutaPdf) throws NumberFormatException, SQLException, InterruptedException {
		originacionaccion.assertSimuladorinterno(Fecha, Tasa, Plazo, Monto, DiasHabilesIntereses, Ingresos, descLey, descNomina, vlrCompasSaneamientos, tipo, pagaduria, rutaPdf);
	}

	@Y("carga todos los archivos en la pestana de digitalizacion {string}")
	public void cargaTodosLosArchivosEnLaPestanaDeDigitalizacion(String Pdf) throws InterruptedException {
        originacionaccion.Digitalizacion(Pdf);
	}

	@Y("marcar los check en correcto guardando en la pestana de digitalizacion")
	public void marcarLosCheckEnCorrectoGuardandoEnLaPestanaDeDigitalizacion() throws InterruptedException {
		originacionaccion.DigitalizacionCheck();
	}

	@Y("se llenan los campos obligatorios en la pestana formulario guardando {string}{string}{string}{string}{string}{string}{string}{string}{string}")
	public void seLlenanLosCamposObligatoriosEnLaPestanaFormularioGuardando(String DestinoCredito,String Sexo,String EstadoCivil,String Direccion,String Dpto,String Ciudad,String TipoVivienda,String Correo, String Celular) throws InterruptedException {
		originacionaccion.formulario(DestinoCredito,Sexo,EstadoCivil,Direccion,Dpto,Ciudad,TipoVivienda,Correo,Celular);
	}

	@Y("se agregar las referencias en la segunta pestana del formulario guardando {string}{string}{string}{string}{string}{string}{string}{string}{string}")
	public void seAgregarLasReferenciasEnLaSeguntaPestanaDelFormularioGuardando(String IngresosMes,String TotalActivos,String Papellido,String Pnombre,String Direccion,String TelefonoResidencia,String TelefonoTrabajo,String Dpto,String Ciudad) throws InterruptedException {
		originacionaccion.formularioSegundaPestana(IngresosMes,TotalActivos,Papellido,Pnombre,Direccion,TelefonoResidencia,TelefonoTrabajo,Dpto,Ciudad);
	}

	@Y("se presiona en verificacion en la pestana digitalizacion")
	public void sePresionaEnVerificacionEnLaPestanaDigitalizacion() {
         originacionaccion.DigitalizacionVerificacion();
	}
	
	@Y("se crean los tipos de cartera o saneamiento a recoger")
	public void seCreanLosTiposDeCarteraOSaneamientoARecoger(DataTable dataTable) {
	    originacionaccion.agregarSaneamientosCarteras(dataTable);
	}

	@Y("se pasa a la segunda pestana de digitalizacion se agrega el codigo proforences aprueba referencias{string}")
	public void se_pasa_a_la_segunta_pestana_de_digitalizacion_se_agrega_el_codigo_proforences_aprueba_referencias(String codigo) throws InterruptedException {
	     originacionaccion.Referenciaspositivas(codigo);
	}

	@Y("se marca identidida confirmada radicando la solicitud")
	public void seMarcaIdentididaConfirmadaRadicandoLaSolicitud() throws InterruptedException {
	     originacionaccion.Radicar();
	}

	@Entonces("se aprueba la referenciacion de la pagaduria en la pestana referenciacion permite realizar la solicitud del analisis")
	public void seApruebaLaReferenciacionDeLaPagaduriaEnLaPestanaReferenciacionpermiterealizarlasolicituddelanalisis() throws InterruptedException {
		 originacionaccion.ReferenciacionSolicitarAnalisis();
	}
	
    //####################### AnalisisCredito #####################################################################
	
	@Cuando("el agente ingresa a pestana analisis de credito busca con la cedula del cliente {string}{string}")
	public void el_agente_ingresa_a_pestana_analisis_de_credito_busca_con_la_cedula_del_cliente(String Cedula, String Nombre) throws InterruptedException {
		 originacionaccion.ingresarAnalisisCredito(Cedula,Nombre);
	}

	@Y("ingresa los valores guardando {string}{string}{string}")
	public void ingresa_los_valores_guardando(String Ingresos, String descLey,String descNomina) throws InterruptedException {
		originacionaccion.LlenarIngresos(Ingresos,descLey,descNomina);
	}

	@Y("pasa a la siguiente pestana del simulador analista")
	public void pasa_a_la_siguiente_pestana_del_simulador_analista() throws InterruptedException {
		originacionaccion.SegundaPestanaSimuladorAnalista();
	}

	@Entonces("Valida los valores del simulador{string}{string}{string}{string}{string}{string}{string}{string}{string}{string}{string}")
	public void validaLosValoresDelSimulador(String Mes,String Monto,String Tasa,String Plazo,String Ingresos,String descLey, String descNomina, String pagaduria,String vlrCompasSaneamientos,String anoAnalisis, String fechaDesembolso) throws InterruptedException, NumberFormatException, SQLException {
	  originacionaccion.ValidarSimuladorAnalista(Mes,Monto,Tasa,Plazo,Ingresos,descLey,descNomina,pagaduria,vlrCompasSaneamientos,anoAnalisis, fechaDesembolso);
	}
	
	
	@Y ("Guarda los datos del simulador")
	public void guarda_los_datos_del_simulador() throws InterruptedException {
	 originacionaccion.GuardarSimulacionAnalista();
	}
	//ADP-98-Jonathan Varon
	@Y("ingrese a la pestana del plan de pagos")
	public void ingreseALaPestanaDelPlanDePagos() throws Throwable {
		originacionaccion.PestanaPlanDePagos(); // ADP - 98 - Jonathan Varon
	}
	
	@Y("valide la informacion cabecera con sus conceptos para Originacion{string}{string}")
	public void validelainformacioncabeceraconsusconceptosparaOriginacion (String Tasa, String Plazo) {
		originacionaccion.validelainformacioncabeceraconsusconceptosparaOriginacion(Tasa,Plazo);
	}

	/*@Y("valide la informacion cabecera con sus conceptos{string}{string}{string}")
	public void valideLaInformacionCabeceraConSusConceptos(String Monto,String Tasa,String Plazo) throws Throwable {
		originacionaccion.VerificacionCabeceraAnalisisCredito(Monto,Tasa,Plazo);
	}*/

	@Y("Validacion de saldo a capital en el desgloce del plan de pagos con el ultimo{string}")
	public void Validaciondesaldoacapitaleneldesglocedelplandepagosconelultimo(String plazo) throws Throwable {
		originacionaccion.VerificacionUltimoSaldoCapitalPlanDePagos(plazo);
	}

	@Y ("Pasa a la pestana endeudamiento global aprobando")
	public void pasa_a_la_pestana_endeudamiento_global_aprobando() throws InterruptedException {
		originacionaccion.EndeudamientoGlobal();
	}
	
	@Y ("Aprueba la tarea del credito{string}")
	public void apruebaLaTareaDelCredito(String Cedula) throws InterruptedException {
		originacionaccion.AprobarTareaCredito(Cedula);
	}
	
	//########################### Clientes para bienvenida ##############################
	@Cuando("el agente ingresa a la pestana clientes para bienvenida{string}")
	public void elAgenteIngresaALaPestanaClientesParaBienvenida(String Cedula ) throws InterruptedException {
		originacionaccion.ClientesParaBienvenida(Cedula);
	}
	
	@Y("se marcar los check correctos junto con el celular y correo{string} {string}")
	public void semarcarloscheckcorretosjuntoconelcelularycorreo (String Celular,String Correo) throws InterruptedException {
		originacionaccion.Correctocondiciones(Celular,Correo);
	}
	
	@Y("se validan los valores de las condiciones del credito")
	public void sevalidanlosvaloresdelascondicionesdelcredito() throws NumberFormatException, SQLException {
		originacionaccion.ValidarValoresLlamadoBienvenida();
	}
	
	
	@Y("se marcan los chech y se acepta {string} {string}")
	public void semarcanloschechyseacepta (String TipoDesen, String cedula) throws InterruptedException {
		originacionaccion.Aceptacondiconesdelcredito(TipoDesen, cedula);
	}
	//########################### Clientes para visacion ##############################
	@Cuando("el agente ingresa a la pestana clientes para Visacion {string}")
	public void elagenteingresaalapestanaclientesparaVisacion (String Cedula) throws InterruptedException {
		originacionaccion.ClientesParaVisacion(Cedula);
	}
	
	@Y("se marca aprobado se selecciona la fecha aprobando{string}{string}")
	public void semarcaaprobadoyseseleccionalafecha (String FechaActual,String pdf) throws InterruptedException {
		originacionaccion.AprobarCredito(FechaActual,pdf);
	}
	
	//########################### Clientes para Desembolso ##############################
	@Cuando("el agente ingresa a la pestana Desembolso lista de pagos {string}")
	public void elagenteingresaalapestanaDesembolsolistadepagos (String Cedula) throws InterruptedException {
		originacionaccion.creditosparadesembolso(Cedula);
	}
	
	@Y("se marca el check aprobando el proceso de pagos")
	public void semarcaelcheckaprobandoelprocesodepagos() {
		originacionaccion.ProcesarPagos();
	}
	
	@Y("se filtra por monto y se edita {string}{string}{string}")
	public void sefiltrapormontoyseedita(String Monto, String Banco,String Pdf) {
		originacionaccion.DescargarMediosdedispercion(Monto, Banco,Pdf);
	}

	@Y("validar las condiciones de la carta de notificacion de creditos originacion {string}")
	public void validarLasCondicionesDeLaCartaDeNotificacionDeCreditos(String cedula) throws NumberFormatException, SQLException {
		originacionaccion.validarLasCondicionesDeLaCartaDeNotificacionDeCreditos(cedula);
	}

	@Y("se marcan los chech y se acepta carteras y saneamientos {string}{string}")
	public void semarcanloschechyseaceptacarterasysaneamientos (String TipoDesen, String cedula) throws InterruptedException {
		solicitudCompraCarteraSaneamientoAccion.AceptarcondiconesdelcreditoComSan(TipoDesen, cedula);
	}

	@Y("se marcan los chech y se acepta el detalle originacion{string}{string}")
	public void seMarcanLosChechySeaceptaElDetalleOriginacion (String TipoDesen, String cedula) throws InterruptedException {
		originacionaccion.aceptaCondicionesDelCreditoLibreInversion(TipoDesen, cedula);
	}
	
	@Y("se descargan medios de dispersion para la cartera")
	public void sedescargadescarganmediosdedispersionparalacartera(DataTable datatable) throws InterruptedException {
		originacionaccion.DescargarMediosDispercionCartera(datatable);
	}
	
	
	 @Y("se navega hasta carteras {string}")
		public void senavegahastacarteras(String Pdf) throws InterruptedException {
		 solicitudCompraCarteraSaneamientoAccion.VisacionCartera(Pdf);
	 }
	
	 @Cuando ("el agente ingresa a la lista de pagos para procesar el remanente {string}")
	   public void elagenteingresaalalistadepagosparaprocesarelremanente (String Cedula) throws InterruptedException {
		 originacionaccion.ProcesarRemanente(Cedula);
	   }
}
