package Acciones.CreditoAccion;

import Acciones.ComunesAccion.LoginAccion;
import Acciones.ComunesAccion.PanelPrincipalAccion;
import Archivo.LeerArchivo;
import CommonFuntions.BaseTest;
import Consultas.FuncionesSqlQuery;
import Consultas.OriginacionCreditoQuery;
import JSch.JSchSSHConnection;
import Pages.ComunesPage.PanelNavegacionPage;
import Pages.CreditosPage.*;
import Pages.SolicitudCreditoPage.*;
import dto.SimuladorDto;
import io.cucumber.datatable.DataTable;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.*;

import static org.junit.Assert.assertTrue;

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
    PagesCartaNotificaciones pagesCartaNotificaciones;

    LoginAccion loginaccion;
    LeerArchivo archivo;
    // BaseTest baseTest;
    private static Logger log = Logger.getLogger(OriginacionCreditosAccion.class);
    double vlrIva = 1.19;
    String Cedula;
    String NombreCredito;
    List<String> ValoresCredito = new ArrayList<>();
    Map<String, String> ValoresCarta = new HashMap<>();
    Map<String, String> ValoresCabeceraPlanDePagos = new HashMap<>();
    int DesPrimaAntic = 0;
    //Variables verificacion Plan de Pagos - Originacion
    private String vg_MontoAprobado_Originacion;
    private String vg_SegundaTasaInteres_Originacion;
    private String vg_PrimaSeguroAnticipada_Originacion;
    private String vg_CuotasPrimaSeguroAnticipada;
    String xpathSelectTelefonosRef = "//ul[starts-with(@id, 'formReferenciacion:j_idt91')]";

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
        pagesCartaNotificaciones = new PagesCartaNotificaciones(driver);
    }

    /************ INICIO ACCIONES PARA SIMULADOR ASESOR ***************/

    public void ingresarSimuladorAsesor() {
        panelnavegacionaccion.navegarSimulador();
    }

    public void crearFuncionesSql() {

        log.info("***********************************************************************************************************");
        log.info("[ Configuracion ] - Ejecutando Funciones SQL");
        log.info("***********************************************************************************************************");
        FuncionesSqlQuery.funcionCxcFianzaCertSaldo();
        FuncionesSqlQuery.ejecutarFuncionOriginacion();
        FuncionesSqlQuery.ejecutarFuncionRetanqueo();
        FuncionesSqlQuery.ejecutarFuncionRetanqueoMultiple();
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
        assertEstaPresenteElemento(simuladorasesorpage.desPagaduria);
        hacerClick(simuladorasesorpage.desPagaduria);
        // driver.findElement(simuladorasesorpage.desPagaduria).click();
        selectValorLista(simuladorasesorpage.contPagaduria, simuladorasesorpage.listPagaduria, Pagaduria);
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
        assertEstaPresenteElemento(simuladorasesorpage.labelTasa);
        hacerClick(simuladorasesorpage.labelTasa);
        selectValorLista(simuladorasesorpage.listTasa, simuladorasesorpage.optionsTasa, Tasa);
        assertTextoelemento(simuladorasesorpage.labelTasa, Tasa);
        // EnviarEnter(simuladorasesorpage.inputTasaFiltro);
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
        adjuntarCaptura("SimuladorDeligenciado");
    }

    /*
     * TP - 02/08/2021 Se actualiza el assert simulador modificando los valores para
     * que funcione con la capitalizacion de intereses tasaDos y mesDos, se ajustan
     * los calculos en todas las formulas
     */
    public void assertSimulador(String Fecha, String Tasa, String Plazo, String Monto, String DiasHabilesIntereses,
                                String Ingresos, String descLey, String descNomina, String vlrCompasSaneamientos, String tipo,
                                String pagaduria) throws NumberFormatException, SQLException {

        log.info("************ OriginacionCreditosAccion - assertSimulador() *********************");

        ResultSet resultado;
        // consulta base de datos
        OriginacionCreditoQuery query = new OriginacionCreditoQuery();
        resultado = query.ConsultaDescuentoPrimaAntic();
        while (resultado.next()) {
            DesPrimaAntic = Integer.parseInt(resultado.getString(1));
        }
        log.info("******** Valor de prima **** " + DesPrimaAntic);

        if (Integer.valueOf(Plazo) < DesPrimaAntic) {
            int periodoGracia = (int) Math.ceil((double) Integer.parseInt(DiasHabilesIntereses) / 30);
            DesPrimaAntic = periodoGracia + Integer.valueOf(Plazo);
            log.info("******** Nuevo valor de prima plazo menor a 24  **** " + DesPrimaAntic);
        }

        SimuladorDto calculosSimulador = new SimuladorDto();

        calculosSimulador = this.consultarCalculosSimulador(TextoElemento(simuladorasesorpage.ResultMontoSoli), DesPrimaAntic, Tasa, Plazo, DiasHabilesIntereses, vlrCompasSaneamientos,
                Ingresos, descLey, descNomina, pagaduria);

        int edad = (int) edad(Fecha);
        // assertvalidarEquals(TextoElemento(simuladorasesorpage.edad),
        // String.valueOf(edad));

        ToleranciaPesoMensaje("SIM ASESOR - CALCULANDO CAPACIDAD",
                Integer.parseInt(TextoElemento(simuladorasesorpage.CapacidadAproximada)), calculosSimulador.getCapacidadCliente());
        ToleranciaPesoMensaje("SIM ASESOR - CALCULANDO MONTO SOLICITUD ",
                Integer.parseInt(TextoElemento(simuladorasesorpage.ResultMontoSoli)), calculosSimulador.getMontoSolicitar());
        ToleranciaPesoMensaje("SIM ASESOR -CALCULANDO CUOTA CORRIENTE ",
                Integer.parseInt(TextoElemento(simuladorasesorpage.CuotaCorriente)), calculosSimulador.getCuotaCorriente());
        ToleranciaPesoMensaje("SIM ASESOR - CALCULANDO ESTUDIO CREDITO ",
                Integer.parseInt(TextoElemento(simuladorasesorpage.EstudioCreditoIVA)), calculosSimulador.getEstudioCredito());
        ToleranciaPesoMensaje("SIM ASESOR - CALCULANDO VALOR FIANZA ",
                Integer.parseInt(TextoElemento(simuladorasesorpage.ValorFianza)), calculosSimulador.getFianza());
        ToleranciaPesoMensaje("SIM ASESOR - CALCULO GMF4100 ",
                Integer.parseInt(TextoElemento(simuladorasesorpage.Gmf4100)), calculosSimulador.getGmf4X100());
        ToleranciaPesoMensaje("SIM ASESOR - CALCULO VLR INT INICIALES ",
                Integer.parseInt(TextoElemento(simuladorasesorpage.Valorinteresesini)), calculosSimulador.getInteresesIniciales());
        ToleranciaPesoMensaje("SIM ASESOR - CALCULO PRIMA SEGURO ANTICIPADO ",
                Integer.parseInt(TextoElemento(simuladorasesorpage.PrimaAnticipadaSeguro)), calculosSimulador.getPrimaSeguroAnticipada());
        ToleranciaPesoMensaje("SIM ASESOR - CALCULO REMANENTE ESTIMADO ",
                Integer.parseInt(TextoElemento(simuladorasesorpage.RemanenteEstimado)), calculosSimulador.getRemanenteEstimado());
        if (calculosSimulador.getRemanenteEstimado() < 0) {
            assertBooleanImprimeMensaje(
                    "El valor del remanente estimado es negativo, remanente estimado: " + calculosSimulador.getRemanenteEstimado(), true);
        }
        ToleranciaPesoMensaje("SIM ASESOR -  CALCULO MONTO MAXIMO DESEMBOLSAR ",
                Integer.parseInt(TextoElemento(simuladorasesorpage.MontoMaximoSugerido)), calculosSimulador.getMontoMaxDesembolsar());
    }

    public void GuardarSimulacion() throws InterruptedException {
        log.info("******************OriginacionCreditosAccion - GuardarSimulacion()********** ");
        try {
            Hacer_scroll(simuladorasesorpage.btnGuardar);
            hacerClick(simuladorasesorpage.btnGuardar);
            assertEstaPresenteElemento(simuladorasesorpage.notificacionGuardado);
            hacerClicknotificacion();/////////////////////////////////////////////////////////////////
            esperaExplicita(simuladorasesorpage.notificacionGuardado);
            adjuntarCaptura("SimulacionGuardada");
        } catch (Exception e) {
            log.error("########## Error - OriginacionCreditosAccion - GuardarSimulacion() #######" + e);
            assertTrue("########## Error - OriginacionCreditosAccion - GuardarSimulacion() ########" + e, false);
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
            adjuntarCaptura("CreacionCliente");
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
            adjuntarCaptura("CreacionCliente");
            hacerClick(simuladorasesorpage.btnCrear);
            adjuntarCaptura("NotificacionCreacionCliente");
            assertEstaPresenteElemento(simuladorasesorpage.notificacionCreacionCliente);
            hacerClicknotificacion();////////////////////////////////////////////////////////
            esperaExplicitaNopresente(simuladorasesorpage.notificacionCreacionCliente);
            ElementVisible();
        } catch (Exception e) {
            log.error("########## Error - OriginacionCreditosAccion - CrearCliente() #######" + e);
            assertTrue("########## Error - OriginacionCreditosAccion - CrearCliente() ########" + e, false);
        }

    }

    public void SubirDocumentos(String ruta) throws InterruptedException {
        log.info("********** OriginacionCreditosAccion - SubirDocumentos()************");
        try {
            cargarPdf(simuladorasesorpage.fileAutorizacion, simuladorasesorpage.fileCedula,
                    simuladorasesorpage.fileNomina, ruta);
            ElementVisible();
            adjuntarCaptura("CargueDeDocumentos");
        } catch (Exception e) {
            log.error("########## Error - OriginacionCreditosAccion - SubirDocumentos() #######" + e);
            assertTrue("########## Error - OriginacionCreditosAccion - SubirDocumentos() ########" + e, false);
        }

    }

    public void ConsultaCentrales(String Cedula) throws InterruptedException {
        log.info("******************** OriginacionCreditosAccion - ConsultaCentrales()  ***************");
        try {
            ElementVisible();
            esperaporestadoBD(simuladorasesorpage.btnSoliConsulta, Cedula, "PENDIENTE_RESPUESTA_CONSULTA_CENTRALES");
            adjuntarCaptura("ConsultaCentrales");

        } catch (Exception e) {
            log.error("########## Error - OriginacionCreditosAccion - ConsultaCentrales() #######" + e);
            assertTrue("########## Error - OriginacionCreditosAccion - ConsultaCentrales() ########" + e, false);
        }

    }

    /************ FIN ACCIONES PARA SIMULADOR ASESOR ***************/

    // ==============================================================

    /************ INICIA ACCIONES SOLICITUD CREDITO ***************/

    public void ingresarSolicitudCredito(String Cedula, String NombreCredito) throws InterruptedException {
        log.info("******************** OriginacionCreditosAccion - ingresarSolicitudCredito()  ***************");
        try {
            this.Cedula = Cedula;
            this.NombreCredito = NombreCredito;
            panelnavegacionaccion.navegarCreditoSolicitud();
            BuscarenGrilla(creditocolicitudpage.inputCedula, Cedula);
            ElementVisible();
            esperaExplicitaTexto(NombreCredito);
            ElementVisible();
            adjuntarCaptura("Filtra el registro en solicitud de credito");
            esperaExplicita(creditocolicitudpage.selectVerEditar);
            hacerClick(creditocolicitudpage.selectVerEditar);
            ElementVisible();
        } catch (Exception e) {
            log.error("########## Error - OriginacionCreditosAccion - ingresarSolicitudCredito() #######" + e);
            assertTrue("########## Error - OriginacionCreditosAccion - ingresarSolicitudCredito() ########" + e, false);
        }

    }

    public void ingresarSolicitudCreditoDigicredito(String radicacion, String NombreCredito) throws InterruptedException {
        log.info("******************** OriginacionCreditosAccion - ingresarSolicitudCredito()  ***************");
        try {
            this.NombreCredito = NombreCredito;
            panelnavegacionaccion.navegarCreditoSolicitud();
            BuscarenGrilla(creditocolicitudpage.inputRadicado, radicacion);
            esperaExplicitaTexto(NombreCredito);
            ElementVisible();
            esperaExplicita(creditocolicitudpage.selectVerEditar);
            hacerClick(creditocolicitudpage.selectVerEditar);
            ElementVisible();

        } catch (Exception e) {
            log.error("########## Error - OriginacionCreditosAccion - ingresarSolicitudCredito() #######" + e);
            assertTrue("########## Error - OriginacionCreditosAccion - ingresarSolicitudCredito() ########" + e, false);
        }

    }

    public void Seguridad() throws InterruptedException {
        log.info("******************** OriginacionCreditosAccion - Seguridad()  ***************");
        try {
            Refrescar();
            hacerClick(pestanaSeguridadPage.PestanaSeguridad);
            esperaExplicita(pestanaSeguridadPage.BotonGuardar);
            hacerClick(pestanaSeguridadPage.Viable);
            RepetirConsultaCentrales(pestanaSeguridadPage.BotonGuardar, pestanaSeguridadPage.Concepto);
            Hacer_scroll(pestanaSeguridadPage.Concepto);
            adjuntarCaptura("Capture después de marcar el credito como viable");
            esperaExplicitaSeguridad(pestanaSeguridadPage.BtnCheck, Cedula);
            recorerpestanas("SIMULADOR");
        } catch (Exception e) {
            log.error("########## Error - OriginacionCreditosAccion - Seguridad() #######" + e);
            assertTrue("########## Error - OriginacionCreditosAccion - Seguridad() ########" + e, false);
        }

    }

    public void assertSimuladorinterno(String Fecha, String Tasa, String Plazo, String Monto,
                                       String DiasHabilesIntereses, String Ingresos, String descLey, String descNomina,
                                       String vlrCompasSaneamientos, String tipo, String pagaduria, String rutaPdf)
            throws NumberFormatException, SQLException, InterruptedException {
        log.info("******************** OriginacionCreditosAccion - assertSimuladorinterno()  ***************");

        // consulta base de datos
        OriginacionCreditoQuery query = new OriginacionCreditoQuery();
        ResultSet resultado = query.ConsultaDescuentoPrimaAntic();
        while (resultado.next()) {
            DesPrimaAntic = Integer.parseInt(resultado.getString(1));
        }
        log.info("******** Valor de prima **** " + DesPrimaAntic);

        if (Integer.valueOf(Plazo) < DesPrimaAntic) {
            int periodoGracia = (int) Math.ceil((double) Integer.parseInt(DiasHabilesIntereses) / 30);
            DesPrimaAntic = periodoGracia + Integer.valueOf(Plazo);
            log.info("******** Nuevo valor de prima plazo menor a 24  **** " + DesPrimaAntic);
        }
        SimuladorDto calculosSimulador = new SimuladorDto();

        calculosSimulador = this.consultarCalculosSimulador(TextoElemento(pestanasimuladorinternopage.ResultMontoSoli), DesPrimaAntic, Tasa, Plazo, DiasHabilesIntereses, vlrCompasSaneamientos,
                Ingresos, descLey, descNomina, pagaduria);

        int edad = (int) edad(Fecha);
        ToleranciaPesoMensaje("SIM INTERNO - CALCULANDO LA EDAD DEL CLIENTE ",
                Integer.parseInt(TextoElemento(pestanasimuladorinternopage.edad)), edad);
        ToleranciaPesoMensaje("SIM INTERNO - CALCULANDO MONTO SOLICITUD ",
                Integer.parseInt(TextoElemento(pestanasimuladorinternopage.ResultMontoSoli)), calculosSimulador.getMontoSolicitar());
        ToleranciaPesoMensaje("SIM INTERNO - CALCULANDO CUOTA CORRIENTE ",
                Integer.parseInt(TextoElemento(pestanasimuladorinternopage.CuotaCorriente)), calculosSimulador.getCuotaCorriente());
        ToleranciaPesoMensaje("SIM INTERNO - CALCULANDO ESTUDIO CREDITO ",
                Integer.parseInt(TextoElemento(pestanasimuladorinternopage.EstudioCreditoIVA)), calculosSimulador.getEstudioCredito());
        ToleranciaPesoMensaje("SIM INTERNO - CALCULANDO VALOR FIANZA ",
                Integer.parseInt(TextoElemento(pestanasimuladorinternopage.ValorFianza)), calculosSimulador.getFianza());
        ToleranciaPesoMensaje("SIM INTERNO - CALCULANDO VALOR GMF 4X1000",
                Integer.parseInt(TextoElemento(pestanasimuladorinternopage.Gmf4100)), calculosSimulador.getGmf4X100());
        ToleranciaPesoMensaje("SIM INTERNO - CALCULANDO VALOR INT INICIALES ",
                Integer.parseInt(TextoElemento(pestanasimuladorinternopage.Valorinteresesini)),
                calculosSimulador.getInteresesIniciales());
        ToleranciaPesoMensaje("SIM INTERNO - CALCULANDO VALOR PRIMA SEGURO ANTICIPADA ",
                Integer.parseInt(TextoElemento(simuladorasesorpage.PrimaAnticipadaSeguro)), calculosSimulador.getPrimaSeguroAnticipada());
        ToleranciaPesoMensaje("SIM INTERNO - CALCULANDO VALOR REMANENTE ESTIMADO ",
                Integer.parseInt(TextoElemento(pestanasimuladorinternopage.RemanenteEstimado)), calculosSimulador.getRemanenteEstimado());

        Hacer_scroll(pestanasimuladorinternopage.btnGuardar);
        adjuntarCaptura("SimuladorInterno");
        Hacer_scroll(pestanasimuladorinternopage.Solicitar);
        hacerClick(pestanasimuladorinternopage.Solicitar);
        esperaExplicita(simuladorasesorpage.notificacion);
        adjuntarCaptura("SolicitarRadicacion");
        ElementVisible();
        hacerClicknotificacion();
        if (!EncontrarElementoVisibleCss(pestanasimuladorinternopage.ModalExcepciones)) {
        	log.info("**** Se ingresa Aprobar la Exepcion *******");
            this.AprobarExcepciones(rutaPdf, this.Cedula);
            this.ingresarSolicitudCredito(this.Cedula, this.NombreCredito);
        }
        ElementVisible();
    }

    public void Digitalizacion(String Pdf) throws InterruptedException {
        log.info("******************** OriginacionCreditosAccion Cargue PDF - Digitalizacion() ***************");
        try {
            recorerpestanas("DIGITALIZACIÓN");
            esperaExplicita(pestanadigitalizacionPage.Titulo);
            cargarPdfDigitalizacion(Pdf);
            // hacerClicknotificacion();
            esperaExplicitaNopresente(pestanadigitalizacionPage.Notificacion);
        } catch (Exception e) {
            log.error("########## Error - OriginacionCreditosAccion Cargue PDF - Digitalizacion()  #######" + e);
            assertTrue("########## Error - OriginacionCreditosAccion Cargue PDF - Digitalizacion()  ########" + e,
                    false);
        }

    }

    public void DigitalizacionCheck() throws InterruptedException {
        log.info(
                "******************** OriginacionCreditosAccion seleccion Check - DigitalizacionCheck() ***************");
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
            assertTrue("########## Error - OriginacionCreditosAccion Cargue PDF - DigitalizacionCheck()   ########" + e,
                    false);
        }

    }

    public void formulario(String DestinoCredito, String Sexo, String EstadoCivil, String Direccion, String Dpto,
                           String Ciudad, String TipoVivienda, String Correo, String Celular) throws InterruptedException {
        log.info(
                "******************** OriginacionCreditosAccion Llenar formulario cliente - formulario()  ***************");
        try {
            recorerpestanas("FORMULARIO");
            esperaExplicita(pestanaformulariopage.DestinoCredito);
            hacerClick(pestanaformulariopage.DestinoCredito);
            selectValorLista(pestanaformulariopage.ListaDestinoCredito, DestinoCredito);
            ElementVisible();
            Hacer_scroll(pestanaformulariopage.AgregarCuenta);
            adjuntarCaptura("LlenarFormulario");
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
            adjuntarCaptura("LlenarFormulario");
            ElementVisible();
            hacerClick(pestanaformulariopage.Correspondencia);
            ElementVisible();
            hacerClick(pestanaformulariopage.Tipovivienda);
            selectValorLista(pestanaformulariopage.Tipoviviendalist, TipoVivienda);
            ElementVisible();
            adjuntarCaptura("LlenarFormulario");
            Hacer_scroll(pestanaformulariopage.Guardar);
            hacerClick(pestanaformulariopage.Guardar);
            ElementVisible();
            // esperaExplicita(pestanadigitalizacionPage.Notificacion);
            // hacerClickVariasNotificaciones();
            // hacerClicknotificacion();
            // hacerClicknotificacion();
            esperaExplicitaNopresente(pestanadigitalizacionPage.Notificacion);
        } catch (Exception e) {
            log.error("########## Error - OriginacionCreditosAccion - formulario() #######" + e);
            assertTrue("########## Error - OriginacionCreditosAccion - formulario() ########" + e, false);
        }

    }

    public void formularioDigicredito(String DestinoCredito, String Sexo, String EstadoCivil, String Direccion, String Dpto,
                                      String Ciudad, String TipoVivienda, String Correo, String Celular, String TipoContrato) throws InterruptedException {
        log.info(
                "******************** OriginacionCreditosAccion Llenar formulario cliente - formulario()  ***************");
        try {
            recorerpestanas("FORMULARIO");
            esperaExplicita(pestanaformulariopage.DestinoCredito);
            hacerClick(pestanaformulariopage.tipoContrato);
            selectValorLista(pestanaformulariopage.listTipoContrato, TipoContrato);
            hacerClick(pestanaformulariopage.DestinoCredito);
            selectValorLista(pestanaformulariopage.ListaDestinoCredito, DestinoCredito);
            ElementVisible();
            Hacer_scroll(pestanaformulariopage.AgregarCuenta);
            adjuntarCaptura("LlenarFormulario");
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
            adjuntarCaptura("LlenarFormulario");
            ElementVisible();
            hacerClick(pestanaformulariopage.Correspondencia);
            ElementVisible();
            hacerClick(pestanaformulariopage.Tipovivienda);
            selectValorLista(pestanaformulariopage.Tipoviviendalist, TipoVivienda);
            ElementVisible();
            adjuntarCaptura("LlenarFormulario");
            Hacer_scroll(pestanaformulariopage.Guardar);
            hacerClick(pestanaformulariopage.Guardar);
            ElementVisible();
            // esperaExplicita(pestanadigitalizacionPage.Notificacion);
            // hacerClickVariasNotificaciones();
            // hacerClicknotificacion();
            // hacerClicknotificacion();
            esperaExplicitaNopresente(pestanadigitalizacionPage.Notificacion);
        } catch (Exception e) {
            log.error("########## Error - OriginacionCreditosAccion - formulario() #######" + e);
            assertTrue("########## Error - OriginacionCreditosAccion - formulario() ########" + e, false);
        }

    }

    public void formularioSegundaPestana(String IngresosMes, String TotalActivos, String Papellido, String Pnombre,
                                         String Direccion, String TelefonoResidencia, String TelefonoTrabajo, String Dpto, String Ciudad)
            throws InterruptedException {
        log.info(
                "******************** OriginacionCreditosAccion Llenar referencias - formularioSegundaPestana()  ***************");
        try {
            recorerpestanas("FORMULARIO");// borrar despues de la prueba
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
            llenarDepartamentoCiudadReferenciacion(pestanaformulariopage.DepartamentoList,
                    pestanaformulariopage.CiudadList, Dpto, Ciudad, 2);

            llenarInputMultiples(pestanaformulariopage.PapellidoReferencia, Papellido);
            ElementVisible();
            llenarInputMultiples(pestanaformulariopage.PnombreReferencia, Pnombre);
            ElementVisible();
            llenarInputMultiples(pestanaformulariopage.DireccionReferencia, Direccion);
            ElementVisible();
            llenarInputMultiples(pestanaformulariopage.TelefonoResidencia, TelefonoResidencia);
            llenarInputMultiples(pestanaformulariopage.TelefonoTrabajo, TelefonoTrabajo);
            ElementVisible();
            adjuntarCaptura("Referencias");
            Hacer_scroll(pestanaformulariopage.GuardarRefer);
            hacerClick(pestanaformulariopage.GuardarRefer);
            hacerClick(pestanaformulariopage.GuardarRefer);
            esperaExplicita(pestanadigitalizacionPage.Notificacion);
            ElementVisible();
            esperaExplicita(pestanadigitalizacionPage.Notificacion);
            hacerClicknotificacion();
            esperaExplicitaNopresente(pestanadigitalizacionPage.Notificacion);
        } catch (Exception e) {
            log.error(
                    "########## Error - OriginacionCreditosAccion - Llenar referencias - formularioSegundaPestana() #######"
                            + e);
            assertTrue(
                    "########## Error - OriginacionCreditosAccion - Llenar referencias - formularioSegundaPestana()########"
                            + e,
                    false);
        }

    }

    public void formularioSegundaPestanaDigicredito(String IngresosMes, String TotalActivos, String Papellido, String Pnombre,
                                                    String Direccion, String TelefonoResidencia, String TelefonoTrabajo, String Dpto, String Ciudad)
            throws InterruptedException {
        log.info(
                "******************** OriginacionCreditosAccion Llenar referencias - formularioSegundaPestana()  ***************");
        try {
            recorerpestanas("FORMULARIO");// borrar despues de la prueba
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
            ElementVisible();
            Hacer_scroll(pestanaformulariopage.TituloReferencias);
            ElementVisible();
            //eliminarReferencias(pestanaformulariopage.listMenosReferencia);
            adjuntarCaptura("Referencias");
            Hacer_scroll(pestanaformulariopage.GuardarRefer);
            hacerClick(pestanaformulariopage.GuardarRefer);
            hacerClick(pestanaformulariopage.GuardarRefer);
            esperaExplicita(pestanadigitalizacionPage.Notificacion);
            ElementVisible();
            esperaExplicita(pestanadigitalizacionPage.Notificacion);
            hacerClicknotificacion();
            esperaExplicitaNopresente(pestanadigitalizacionPage.Notificacion);


        } catch (Exception e) {
            log.error(
                    "########## Error - OriginacionCreditosAccion - Llenar referencias - formularioSegundaPestana() #######"
                            + e);
            assertTrue(
                    "########## Error - OriginacionCreditosAccion - Llenar referencias - formularioSegundaPestana()########"
                            + e,
                    false);
        }

    }

    public void DigitalizacionVerificacion() {
        log.info("******************** OriginacionCreditosAccion - DigitalizacionVerificacion()  ***************");
        try {
            recorerpestanas("DIGITALIZACIÓN");
            esperaExplicita(pestanadigitalizacionPage.Titulo);
            hacerClick(pestanadigitalizacionPage.EnVerificacion);
            ElementVisible();
            esperaExplicita(pestanadigitalizacionPage.Notificacion);
            adjuntarCaptura("EnVerificacion");
            hacerClicknotificacion();
            esperaExplicitaNopresente(pestanadigitalizacionPage.Notificacion);
        } catch (Exception e) {
            log.error(
                    "########## Error - OriginacionCreditosAccion - Llenar referencias - DigitalizacionVerificacion() #######"
                            + e);
            assertTrue(
                    "########## Error - OriginacionCreditosAccion - Llenar referencias - DigitalizacionVerificacion()########"
                            + e,
                    false);
        }

    }

    public void Referenciaspositivas(String codigo) throws InterruptedException {
        log.info(
                "******************** OriginacionCreditosAccion referencias Positivas - Referenciaspositivas()  ***************");
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
            adjuntarCaptura("Referencias");
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
            adjuntarCaptura("CodigoPreforences");
            hacerClick(pestanadigitalizacionPage.Guardar);
            ElementVisible();
            hacerClick(pestanadigitalizacionPage.Guardar);
            ElementVisible();
            esperaExplicitaNopresente(pestanadigitalizacionPage.Notificacion);
        } catch (Exception e) {
            log.error("########## Error - OriginacionCreditosAccion  - Referenciaspositivas() #######" + e);
            assertTrue("########## Error - OriginacionCreditosAccion - Referenciaspositivas()########" + e, false);
        }

    }

    public void ReferenciaspositivasDigiCredito(String codigo) throws InterruptedException {
        log.info(
                "******************** OriginacionCreditosAccion referencias Positivas - ReferenciaspositivasDigicredito()  ***************");
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
            adjuntarCaptura("Referencias");
            Hacer_scroll(pestanareferenciacionpage.Guardar);
            hacerClick(pestanareferenciacionpage.Guardar);
            ElementVisible();
            esperaExplicitaNopresente(pestanadigitalizacionPage.Notificacion);
            clickVariosReferenciasPositivas(creditocolicitudpage.ListBtnAddReference);
            Hacer_scroll(pestanareferenciacionpage.Titulo);
            clickvarios(pestanareferenciacionpage.ReferenciaPositiva);
            ElementVisible();
            Hacer_scroll(pestanareferenciacionpage.Titulo);
            clickvarios(pestanareferenciacionpage.CheckSI);
            Hacer_scroll(pestanareferenciacionpage.GuardarReferencias);
            hacerClick(pestanareferenciacionpage.GuardarReferencias);
            ElementVisible();
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
            Clear(pestanadigitalizacionPage.CodigoProforenses);
            EscribirElemento(pestanadigitalizacionPage.CodigoProforenses, codigo);
            ElementVisible();
            hacerClick(pestanadigitalizacionPage.IdentidadConfirmada);
            ElementVisible();
            adjuntarCaptura("CodigoPreforences");
            //hacerClick(pestanadigitalizacionPage.Guardar);
            hacerClick(pestanadigitalizacionPage.Guardar);
            ElementVisible();
            esperaExplicitaNopresente(pestanadigitalizacionPage.Notificacion);
        } catch (Exception e) {
            log.error("########## Error - OriginacionCreditosAccion  - ReferenciaspositivasDigicredito() #######" + e);
            assertTrue("########## Error - OriginacionCreditosAccion - ReferenciaspositivasDigicredito()########" + e, false);
        }

    }

    public void Radicar() throws InterruptedException {
        log.info("******************** OriginacionCreditosAccion - Radicar()  ***************");
        try {
            ElementVisible();
            hacerClick(pestanadigitalizacionPage.Radicar);
            ElementVisible();
            esperaExplicita(pestanadigitalizacionPage.Notificacion);
            adjuntarCaptura("RadicarCredito");
            hacerClicknotificacion();
            hacerClicknotificacion();
            esperaExplicitaNopresente(pestanadigitalizacionPage.Notificacion);
        } catch (Exception e) {
            log.error("########## Error - OriginacionCreditosAccion  - Radicar() #######" + e);
            assertTrue("########## Error - OriginacionCreditosAccion - Radicar()########" + e, false);
        }

    }

    public void ReferenciacionSolicitarAnalisis() throws InterruptedException {
        log.info("******************** OriginacionCreditosAccion - ReferenciacionSolicitarAnalisis()  ***************");
        try {
            recorerpestanas("REFERENCIACIÓN");
            ElementVisible();
            clickvarios(pestanareferenciacionpage.Aprobarsaneamiento);
            ElementVisible();
            Hacer_scroll(pestanareferenciacionpage.SolicitarAnalisis);
            hacerClick(pestanareferenciacionpage.SolicitarAnalisis);
            ElementVisible();
            esperaExplicita(pestanadigitalizacionPage.Notificacion);
            adjuntarCaptura("AnalisisCredito");
            hacerClicknotificacion();
            esperaExplicitaNopresente(pestanadigitalizacionPage.Notificacion);
        } catch (Exception e) {
            log.error("########## Error - OriginacionCreditosAccion  - ReferenciacionSolicitarAnalisis() #######" + e);
            assertTrue("########## Error - OriginacionCreditosAccion - ReferenciacionSolicitarAnalisis()########" + e,
                    false);
        }

    }

    /************ FIN ACCIONES SOLICITUD CREDITO ***************/

    /************ INICIA ACCIONES ANALISTA ***************/
    public void ingresarAnalisisCredito(String Cedula, String Nombre) throws InterruptedException {
        log.info("******************** OriginacionCreditosAccion - ingresarAnalisisCredito()  ***************");
        try {
            this.Cedula = Cedula;
            this.NombreCredito = Nombre;
            panelnavegacionaccion.navegarCreditoAnalisis();
            BuscarenGrilla(pestanasimuladorinternopage.FiltroCedula, Cedula);
            ElementVisible();
            Thread.sleep(1000);
            esperaExplicitaTexto(Nombre);
            esperaExplicita(pestanasimuladorinternopage.EditarVer);
            Thread.sleep(1000);
            ClicUltimoElemento(pestanasimuladorinternopage.listVerEditar);
            ElementVisible();
            esperaExplicita(pestanasimuladorinternopage.inputMesada);
            adjuntarCaptura("filtar usuario en grilla para analisis de credito");
        } catch (Exception e) {
            log.error("########## Error - OriginacionCreditosAccion  - ingresarAnalisisCredito() #######" + e);
            assertTrue("########## Error - OriginacionCreditosAccion - ingresarAnalisisCredito()########" + e, false);
        }

    }

    public void LlenarIngresos(String Ingresos, String descLey, String descNomina) throws InterruptedException {
        log.info(
                "******************** OriginacionCreditosAccion Llenar ingresos en analisis - ingresarAnalisisCredito()  ***************");
        try {
            esperaExplicita(pestanasimuladorinternopage.inputMesada);
            Clear(pestanasimuladorinternopage.inputMesada);
            EscribirElemento(pestanasimuladorinternopage.inputMesada, Ingresos);
            ElementVisible();
            Clear(pestanasimuladorinternopage.inputSalud);
            EscribirElemento(pestanasimuladorinternopage.inputSalud, descLey);
            Hacer_scroll(pestanasimuladorinternopage.Guardar);
            Clear(pestanasimuladorinternopage.DescuentoAfiliaciones);
            EscribirElemento(pestanasimuladorinternopage.DescuentoAfiliaciones, descNomina);
            adjuntarCaptura("campos ingresos, descuentos de ley y de nómina diligenciados");
            ElementVisible();
            Hacer_scroll(pestanasimuladorinternopage.Guardar);
            hacerClick(pestanasimuladorinternopage.Guardar);
            ElementVisible();
            esperaExplicita(pestanadigitalizacionPage.Notificacion);
            hacerClicknotificacion();
            esperaExplicitaNopresente(pestanadigitalizacionPage.Notificacion);
        } catch (Exception e) {
            log.error("########## Error - OriginacionCreditosAccion  - ingresarAnalisisCredito() #######" + e);
            assertTrue("########## Error - OriginacionCreditosAccion - ingresarAnalisisCredito()########" + e, false);
        }

    }

    public void SegundaPestanaSimuladorAnalista() throws InterruptedException {
        log.info(
                "******************** OriginacionCreditosAccion cambiar pestana - SegundaPestanaSimuladorAnalista ()  ***************");
        try {
            Thread.sleep(4000);
            hacerClick(pestanasimuladorinternopage.SgdPestana);
            ElementVisible();
            esperaExplicita(pestanadigitalizacionPage.Notificacion);
            hacerClicknotificacion();
        } catch (Exception e) {
            log.error("########## Error - OriginacionCreditosAccion  - SegundaPestanaSimuladorAnalista () #######" + e);
            assertTrue("########## Error - OriginacionCreditosAccion - SegundaPestanaSimuladorAnalista ()########" + e,
                    false);
        }

    }

    public void ValidarSimuladorAnalista(String Mes, String Monto, String Tasa, String Plazo, String Ingresos,
                                         String descLey, String descNomina, String pagaduria, String vlrCompasSaneamientos, String anoAnalisis,
                                         String fechaDesembolso) throws InterruptedException, NumberFormatException, SQLException {
        log.info(
                "******************** OriginacionCreditosAccion validar Calculos - ValidarSimuladorAnalista()  ***************");
        Clear(pestanasimuladorinternopage.FechaDesembolso);
        EscribirElemento(pestanasimuladorinternopage.FechaDesembolso, fechaDesembolso);
        EnviarEscape(pestanasimuladorinternopage.FechaDesembolso);
        esperaExplicita(pestanasimuladorinternopage.MesDeAfecatcion);
        hacerClick(pestanasimuladorinternopage.MesDeAfecatcion);
        ElementVisible();
        selectValorLista(pestanasimuladorinternopage.ListaMes, Mes);
        ElementVisible();
        Clear(pestanasimuladorinternopage.anoAfectacion);
        EscribirElemento(pestanasimuladorinternopage.anoAfectacion, anoAnalisis);
        ElementVisible();
        hacerClick(pestanasimuladorinternopage.CalcularDesglose);
        // hacerClicknotificacion();
        hacerClickVariasNotificaciones();
        esperaExplicitaNopresente(pestanadigitalizacionPage.Notificacion);
        adjuntarCaptura("simulador analista, captura de campos");
        Hacer_scroll(pestanasimuladorinternopage.ValorCuota);
        adjuntarCaptura("simulador analista, captura de campos");
        Hacer_scroll(pestanasimuladorinternopage.IngresosAsesor);
        adjuntarCaptura("simulador analista, captura de campos");


        //consulta base de datos
        OriginacionCreditoQuery query = new OriginacionCreditoQuery();
        ResultSet resultado = query.ConsultaDescuentoPrimaAntic();
        while (resultado.next()) {
            DesPrimaAntic = Integer.parseInt(resultado.getString(1));
        }
        
        log.info("******** Valor de prima **** " + DesPrimaAntic);
        String DiasHabilesIntereses = TextoElemento(pestanasimuladorinternopage.DiasInteresesIniciales);
        if (Integer.parseInt(Plazo) < DesPrimaAntic) {
            int periodoGracia = (int) Math.ceil((double) Integer.parseInt(DiasHabilesIntereses) / 30);
            DesPrimaAntic = periodoGracia + Integer.parseInt(Plazo);
            log.info("******** Nuevo valor de prima plazo menor a 24  **** " + DesPrimaAntic);
        }
        vg_CuotasPrimaSeguroAnticipada = String.valueOf(DesPrimaAntic);

        // Valores CXC capitalizadas
        // Consultar los conceptos para el cambio de tasa 
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

        String calculoSoliPantalla = TextoElemento(pestanasimuladorinternopage.CapitalTotal);
        SimuladorDto calculosSimulador = this.consultarCalculosSimulador(calculoSoliPantalla, DesPrimaAntic, Tasa, Plazo, DiasHabilesIntereses, vlrCompasSaneamientos,
                Ingresos, descLey, descNomina, pagaduria);


        // Validar resultados de simulacion
        assertvalidarEquals(TextoElemento(pestanasimuladorinternopage.MontoSolicitado), Monto);

        ToleranciaPesoMensaje("###### SIM ANALISTA - CALCULANDO MONTO CAPACIDAD ########",
                Integer.parseInt(TextoElemento(pestanasimuladorinternopage.CapacidadAsesor)), calculosSimulador.getCapacidadCliente());
        ToleranciaPesoMensaje("###### SIM ANALISTA - CALCULANDO MONTO SOLICITUD ########",
                Integer.parseInt(TextoElemento(pestanasimuladorinternopage.CapitalTotal)), calculosSimulador.getMontoSolicitar());
        ToleranciaPesoMensaje("######### SIM ANALISTA - CALCULANDO CUOTA CORRIENTE ##############",
                Integer.parseInt(TextoElemento(pestanasimuladorinternopage.ValorCuota).replaceAll("[^a-zA-Z0-9]", "")),
                calculosSimulador.getCuotaCorriente());
        ToleranciaPesoMensaje("###### SIM ANALISTA - CALCULANDO PRIMA SEGURO ########",
                Integer.parseInt(TextoElemento(pestanasimuladorinternopage.PrimaAnticipadaSeguroAsesor)),
                calculosSimulador.getPrimaSeguroAnticipada());
        ToleranciaPesoMensaje("###### SIM ANALISTA - CALCULANDO MONTO MAXIMO DESEMBOLSAR ########",
                Integer.parseInt(TextoElemento(pestanasimuladorinternopage.MontoMaximoAsesor)), calculosSimulador.getMontoMaxDesembolsar());
        ToleranciaPesoMensaje("###### SIM ANALISTA - CALCULANDO ESTUDIO CREDITO ########",
                Integer.parseInt(TextoElemento(pestanasimuladorinternopage.ValorEstudioCreditoCXC)), calculosSimulador.getEstudioCredito());
        ToleranciaPesoMensaje("###### SIM ANALISTA - CALCULANDO FIANZA ########",
                Integer.parseInt(TextoElemento(pestanasimuladorinternopage.ValorFianzaCXC)), calculosSimulador.getFianza());
        ToleranciaPesoMensaje("###### SIM ANALISTA - CALCULANDO 4X1000 ########",
                Integer.parseInt(TextoElemento(pestanasimuladorinternopage.Gravamento4x1000)), calculosSimulador.getGmf4X100());
        ToleranciaPesoMensaje("###### SIM ANALISTA - CALCULANDO REMANENTE ESTIMADO ########",
                Integer.parseInt(TextoElemento(pestanasimuladorinternopage.ValoraDesembolsar)),
                calculosSimulador.getRemanenteEstimado());
        assertvalidarEquals(TextoElemento(pestanasimuladorinternopage.ValorCompraCartera), vlrCompasSaneamientos);
        assertvalidarEquals(TextoElemento(pestanasimuladorinternopage.PlazoAsesor), Plazo);
        assertvalidarEquals(GetText(pestanasimuladorinternopage.TasaAsesor), Tasa);
        assertvalidarEquals(TextoElemento(pestanasimuladorinternopage.IngresosAsesor)
                .substring(0, TextoElemento(pestanasimuladorinternopage.IngresosAsesor).length() - 2)
                .replaceAll("[^a-zA-Z0-9]", ""), Ingresos);
        assertvalidarEquals(TextoElemento(pestanasimuladorinternopage.DescuentosLey)
                .substring(0, TextoElemento(pestanasimuladorinternopage.DescuentosLey).length() - 2)
                .replaceAll("[^a-zA-Z0-9]", ""), descLey);
        assertvalidarEquals(TextoElemento(pestanasimuladorinternopage.DescuentosNomina)
                .substring(0, TextoElemento(pestanasimuladorinternopage.DescuentosNomina).length() - 2)
                .replaceAll("[^a-zA-Z0-9]", ""), descNomina);
        //Variables globales para posterior analisis Plan de Pagos - Originacion
        vg_MontoAprobado_Originacion = String.valueOf(calculosSimulador.getMontoSolicitar());
        vg_SegundaTasaInteres_Originacion = String.valueOf(tasaDos * 100);
        vg_PrimaSeguroAnticipada_Originacion = String.valueOf(calculosSimulador.getPrimaSeguroAnticipada());
    }

    public void GuardarSimulacionAnalista() throws InterruptedException {
        log.info(
                "***********Guardar simulador analista, OriginacionCreditosAccion - GuardarSimulacionAnalista()**********");
        try {
            ElementVisible();
            hacerClick(pestanasimuladorinternopage.GuardarSimulacion);
            ElementVisible();
        } catch (Exception e) {
            log.error("########## Error - OriginacionCreditosAccion  - GuardarSimulacionAnalista() #######" + e);
            assertTrue("########## Error - OriginacionCreditosAccion - GuardarSimulacionAnalista()########" + e, false);
        }

    }

    // ADP - 98 Jonathan Varon
    public void PestanaPlanDePagos() throws InterruptedException {
        log.info(
                "******************** OriginacionCreditosAccion cambiar pestana - PestanaPlanDePagos ()  ***************");
        try {
            Thread.sleep(4000);
            hacerClick(pestanasimuladorinternopage.PestanaPlanDePagos);
        } catch (Exception e) {
            log.error("########## Error - OriginacionCreditosAccion  - PestanaPlanDePagos () #######" + e);
            assertTrue("########## Error - OriginacionCreditosAccion - PestanaPlanDePagos () ########" + e, false);
        }
    }

    public void validelainformacioncabeceraconsusconceptosparaOriginacion(String Tasa, String Plazo) throws InterruptedException {

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

    public void VerificacionCabeceraAnalisisCredito(String Monto, String Tasa, String Plazo)
            throws InterruptedException, NumberFormatException, SQLException {
        log.info("******************** OriginacionCreditosAccion - VerificacionCabeceraAnalisisCredito () ***************");
        // Valores para comparacion con consulta por XPATH
        String tasaInicial_Pagina;
        String montoSolicitado_Pagina;
        String plazo_Pagina;

        // Obtencion de datos - Generacion MAP con datos de la cabecera

        ValoresCabeceraPlanDePagos = obtainValuesMap(pestanasimuladorinternopage.KeyCabeceraPlanDePagos, pestanasimuladorinternopage.ValueCabeceraPlanDePagos);

        // Guardado de datos del MAP en valiable local
        tasaInicial_Pagina = ValoresCabeceraPlanDePagos.get("Tasa inicial del crédito");
        montoSolicitado_Pagina = ValoresCabeceraPlanDePagos.get("Monto Solicitado:");
        plazo_Pagina = ValoresCabeceraPlanDePagos.get("Plazo:");

        //Validacion
        try {
            log.info("*********** Validando TASA INICIAL ***********");
            assertvalidarEquals(tasaInicial_Pagina.replace("0", ""), Tasa);
            log.info("*********** Validando MONTO ***********");
            assertvalidarEquals(montoSolicitado_Pagina, Monto);
            log.info("*********** Validando PLAZO ***********");
            assertvalidarEquals(plazo_Pagina, Plazo);
            log.info("*********** Datos cabecera Validados -> OK ***********");

        } catch (Exception e) {
            log.error("########## Error - VerificacionCabeceraAnalisisCredito() - Validando TASA INICIAL #######" + e);
            assertTrue("########## Error - OriginacionCreditosAccion - PestanaPlanDePagos () ########" + e, false);
        }

    }

    public void VerificacionUltimoSaldoCapitalPlanDePagos(String plazo) {
        log.info("******************** OriginacionCreditosAccion - VerificacionUltimoSaldoCapitalPlanDePagos () ***************");
        int campoDeVerificacion = Integer.parseInt(plazo);
        campoDeVerificacion -= 1;
        String saldoCapital_Plazo_N;

        try {
            //Verificar la existencia del ultimo mes del "plazo" en el "Desglose plan de pagos"
            esperaExplicita(By.xpath("//tbody[@id='form:j_idt212_data' and @class='ui-datatable-data ui-widget-content']/child::tr[@data-ri=\"" + campoDeVerificacion + "\"]\r\n"
                    + ""));

            //Obtener el valor de la columna "Saldo Capital" de el Ultimo mes del plazo
            saldoCapital_Plazo_N = GetText(By.xpath("//tbody[@id='form:j_idt212_data' and @class='ui-datatable-data ui-widget-content']/child::tr[@data-ri=\"" + campoDeVerificacion + "\"]/child::td[@role=\"gridcell\"][7]"));
            //log.info("************ SALDO CAPITAL******** ->>>>>>> "+ saldoCapital_Plazo_N);
            //Comparar el ultimo Saldo a Capital con el Valor esperado
            log.info("*********** Validando ultimo SALDO CAPITAL = '0' ***********");
            assertvalidarEquals(saldoCapital_Plazo_N, "0");
            log.info("*********** Datos Plan de PAGOS Validados -> OK ***********");

            //Hacer Scroll hasta valor cabecera : Plazo
            Hacer_scroll(By.xpath("//tbody[@id='form:j_idt212_data' and @class='ui-datatable-data ui-widget-content']/child::tr[@data-ri=\"" + campoDeVerificacion + "\"]/child::td[@role=\"gridcell\"][7]"));
            //Captura De Pantalla
            adjuntarCaptura("AnalisisCredito - Ultimo plazo desglose plan de Pagos");

        } catch (Exception e) {
            log.error("########## Error - VerificacionUltimoSaldoCapitalPlanDePagos() - Validacion Plazo #######" + e);
            assertTrue("########## Error - OriginacionCreditosAccion - VerificacionUltimoSaldoCapitalPlanDePagos() ########" + e, false);
        }


    }

    public void EndeudamientoGlobal() throws InterruptedException {
        log.info("***********Cambiar pestana, OriginacionCreditosAccion - EndeudamientoGlobal()**********");
        try {
            recorerpestanas("ENDEUDAMIENTO GLOBAL");
            hacerClick(pestanasimuladorinternopage.Aprobar);
            esperaExplicita(simuladorasesorpage.notificacion);
            String notificacion = GetText(simuladorasesorpage.notificacion);
            ElementVisible();
            if (!notificacion.equals("Este crédito se ha enviado a flujo de aprobación de analisis.")) {
                this.MostrarReferencias();
            }
        } catch (Exception e) {
            log.error("########## Error - OriginacionCreditosAccion  - EndeudamientoGlobal() #######" + e);
            assertTrue("########## Error - OriginacionCreditosAccion - EndeudamientoGlobal()########" + e, false);
        }

    }

    public void AprobarTareaCredito(String Cedula) throws InterruptedException {
        log.info("********** OriginacionCreditosAccion - AprobarTareaCredito() **********");
        try {
            panelnavegacionaccion.navegarTareas();
            esperaExplicita(pagestareas.filtroDescipcion);
            EscribirElemento(pagestareas.filtroDescipcion, Cedula);
            ElementVisible();
            adjuntarCaptura("Filtro de tarea antes de aprobar");
            hacerClick(pagestareas.EditarVer);
            ElementVisible();
            Hacer_scroll(pagestareas.Aprobar);
            esperaExplicita(pagestareas.Aprobar);
            hacerClick(pagestareas.Aprobar);
            ElementVisible();
        } catch (Exception e) {
            log.error("########## Error - OriginacionCreditosAccion  - AprobarTareaCredito() #######" + e);
            assertTrue("########## Error - OriginacionCreditosAccion - AprobarTareaCredito()########" + e, false);
        }

    }

    /************ INICIA ACCIONES ANALISTA ***************/

    /************
     * Clientes Para Bienvenidad
     *
     * @throws InterruptedException
     ******************/

    public void ClientesParaBienvenida(String Cedula) throws InterruptedException {
        log.info("********** OriginacionCreditosAccion - ClientesParaBienvenida() **********");
        try {
            panelnavegacionaccion.CreditoClientesBienvenida();
            ElementVisible();
            esperaExplicita(pagesclienteparabienvenida.filtrocedula);
            EscribirElemento(pagesclienteparabienvenida.filtrocedula, Cedula);
            ElementVisible();
            esperaExplicita(By.xpath("//td[text()='" + Cedula + "']"));
            adjuntarCaptura("Filtra la cédula en clientes para bienvenida");
            hacerClick(pagesclienteparabienvenida.Continuar);
            ElementVisible();
        } catch (Exception e) {
            log.error("########## Error - OriginacionCreditosAccion  - ClientesParaBienvenida() #######" + e);
            assertTrue("########## Error - OriginacionCreditosAccion - ClientesParaBienvenida()########" + e, false);
        }
    }

    public void ValidarValoresLlamadoBienvenida(String ingresos, String descLey, String descNomina, String pagaduria) throws NumberFormatException, SQLException, InterruptedException {
        recorerpestanas("CONDICIONES DEL CRÉDITO");

        ResultSet resultado;

        ValoresCredito = RetornarStringListWebElemen(pagesclienteparabienvenida.ValoresCondicionesCredito);

        adjuntarCaptura("Campos condiciones del crédito");
        this.capturesValoresCondicionesCredito(pagesclienteparabienvenida.CheckCondicionesCredito);

        // consulta base de datos
        OriginacionCreditoQuery query = new OriginacionCreditoQuery();
        resultado = query.ConsultaDescuentoPrimaAntic();
        while (resultado.next()) {
            DesPrimaAntic = Integer.parseInt(resultado.getString(1));
        }

        // consulta para validar prima menor a 24 meses

        if (Integer.parseInt(ValoresCredito.get(1)) < DesPrimaAntic) {
            int periodoGracia = (int) Math.ceil((double) Integer.parseInt(ValoresCredito.get(7)) / 30);
            DesPrimaAntic = periodoGracia + Integer.parseInt(ValoresCredito.get(1));
        }

        log.info("******** Valor de prima **** " + DesPrimaAntic);

        /*
         * Parametros del metodo
         * (String Monto, int DesPrimaAntic, String Tasa, String Plazo,
              String DiasHabilesIntereses, String vlrCompasSaneamientos, String Ingresos, String descLey,
              String descNomina, String pagaduriat)*/
        SimuladorDto calculosSimulador = this.consultarCalculosSimulador(ValoresCredito.get(0),DesPrimaAntic,ValoresCredito.get(2),String.valueOf(ValoresCredito.get(1)),
                ValoresCredito.get(6),ValoresCredito.get(7),
                ingresos, descLey, descNomina, pagaduria);


        ToleranciaPesoMensaje("***** LLAMADA BIENVENIDA - COMPARA PRIMA *****", Integer.parseInt(ValoresCredito.get(9)),
                calculosSimulador.getPrimaSeguroAnticipada());
        ToleranciaPesoMensaje("***** LLAMADA BIENVENIDA - COMPARA MONTO SOLICITUD ******",
                (int) Double.parseDouble(ValoresCredito.get(0)), calculosSimulador.getMontoSolicitar());
        ToleranciaPesoMensaje("***** LLAMADA BIENVENIDA - COMPARA VALOR FIANZA *****", Integer.parseInt(ValoresCredito.get(17)),
                calculosSimulador.getFianza());
        ToleranciaPesoMensaje("***** LLAMADA BIENVENID- COMPARA ESTUDIO CREDITO ****",
                Integer.parseInt(ValoresCredito.get(19)), calculosSimulador.getEstudioCredito());

    }

    public void Correctocondiciones(String Telefono, String Correo) throws InterruptedException {
        log.info("********** OriginacionCreditosAccion - Correctocondiciones() **********");
        try {
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
        } catch (Exception e) {
            log.error("########## Error - OriginacionCreditosAccion  - Correctocondiciones() #######" + e);
            assertTrue("########## Error - OriginacionCreditosAccion - Correctocondiciones()########" + e, false);
        }

    }

    public void Aceptacondiconesdelcredito(String TipoDesen, String cedula) throws InterruptedException {
        log.info("********** OriginacionCreditosAccion - Aceptacondiconesdelcredito() **********");
        try {
            this.cambiarPestana(0);
            this.ClientesParaBienvenida(cedula);
            recorerpestanas("CONDICIONES DEL CRÉDITO");
            Refrescar();
            esperaExplicita(pagesclienteparabienvenida.SaldoAlDia);
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
        } catch (Exception e) {
            log.error("########## Error - OriginacionCreditosAccion  - Aceptacondiconesdelcredito() #######" + e);
            assertTrue("########## Error - OriginacionCreditosAccion - Aceptacondiconesdelcredito()########" + e,
                    false);
        }

    }

    /************ FIN Clientes Para Bienvenidad *************/

    /************ Clientes Para Visacion **********/

    public void ClientesParaVisacion(String Cedula) throws InterruptedException {
        log.info("********** OriginacionCreditosAccion - ClientesParaVisacion() **********");
        try {
            panelnavegacionaccion.CreditoClientesVisacion();
            ElementVisible();
            esperaExplicita(PagesClienteParaVisacion.filtrocedula);
            EscribirElemento(PagesClienteParaVisacion.filtrocedula, Cedula);
            ElementVisible();
            esperaExplicita(By.xpath("//td[text()='" + Cedula + "']"));
            adjuntarCaptura("Filtra la cédula en clientes para visación");
            hacerClick(PagesClienteParaVisacion.Continuar);
            ElementVisible();
        } catch (Exception e) {
            log.error("########## Error - OriginacionCreditosAccion  - ClientesParaVisacion() #######" + e);
            assertTrue("########## Error - OriginacionCreditosAccion - ClientesParaVisacion()########" + e, false);
        }

    }

    public void AprobarCredito(String fecha, String pdf) {
        log.info("********** Visacion, OriginacionCreditosAccion - AprobarCredito() **********");
        try {
            esperaExplicita(PagesClienteParaVisacion.AprobadoCheck);
            hacerClick(PagesClienteParaVisacion.AprobadoCheck);
            ElementVisible();
            Clear(PagesClienteParaVisacion.FechaResultado);
            EscribirElemento(PagesClienteParaVisacion.FechaResultado, fecha);
            EnviarEnter(PagesClienteParaVisacion.FechaResultado);
            ElementVisible();
            adjuntarCaptura("Fecha diligenciada clientes para visación");
            cargarpdf(PagesClienteParaVisacion.DocumentoLibranza, pdf);
            esperaExplicita(PagesClienteParaVisacion.cargapdf);
            adjuntarCaptura("Capture antes de aprobar");
            hacerClick(PagesClienteParaVisacion.Aprobar);
            ElementVisible();
        } catch (Exception e) {
            log.error("########## Error - OriginacionCreditosAccion - AprobarCredito() #######" + e);
            assertTrue("########## Error - OriginacionCreditosAccion - AprobarCredito()########" + e, false);
        }

    }

    /************ FIN Clientes Para Visacion **********/

    /************ Creditos Para Desembolso ************/

    public void creditosparadesembolso(String Cedula) throws InterruptedException {
        log.info("********** Desembolso, OriginacionCreditosAccion - creditosparadesembolso() **********");
        try {
            panelnavegacionaccion.CreditoParaDesembolso();
            ElementVisible();
            esperaExplicita(PagesCreditosDesembolso.filtrocedula);
            EscribirElemento(PagesCreditosDesembolso.filtrocedula, Cedula);
            ElementVisible();
            esperaExplicita(By.xpath("//td[text()='" + Cedula + "']"));
            ElementVisible();
            adjuntarCaptura("Filtra la cédula en credito para desembolso");
        } catch (Exception e) {
            log.error("########## Error - OriginacionCreditosAccion - creditosparadesembolso() #######" + e);
            assertTrue("########## Error - OriginacionCreditosAccion - creditosparadesembolso()########" + e, false);
        }

    }

    public void ProcesarPagos() {
        log.info("********** Desembolso, OriginacionCreditosAccion - ProcesarPagos() **********");
        try {
            hacerClick(PagesCreditosDesembolso.CheckProcesarPagos);
            ElementVisible();
            adjuntarCaptura("Capture check aprobado");
            hacerClick(PagesCreditosDesembolso.ProcesarPagos);
            ElementVisible();
        } catch (Exception e) {
            log.error("########## Error - OriginacionCreditosAccion - ProcesarPagos() #######" + e);
            assertTrue("########## Error - OriginacionCreditosAccion - ProcesarPagos()########" + e, false);
        }

    }

    public void DescargarMediosdedispercion(String Monto, String Banco, String Pdf) {
        log.info("********** Desembolso, OriginacionCreditosAccion - DescargarMediosdedispercion() **********");
        try {
            panelnavegacionaccion.CreditoParaDesembolsoDescargar();
            esperaExplicita(PagesCreditosDesembolso.FiltroMonto);
            EscribirElemento(PagesCreditosDesembolso.FiltroMonto, Monto);
            ElementVisible();
            contarFilasTablas(By.xpath("//*[@id = 'form:listaLotes_data']/child::tr"));

            String pattern = "###,###,###.###";
            double value = Double.parseDouble(Monto);

            DecimalFormat myFormatter = new DecimalFormat(pattern);
            myFormatter = new DecimalFormat(pattern, DecimalFormatSymbols.getInstance(Locale.GERMANY));
            String output = myFormatter.format(value);
            esperaExplicita(By.xpath("//td[text()='" + output + "']"));
            adjuntarCaptura("Capture después de filtrar el monto");
            ClicUltimoElemento(PagesCreditosDesembolso.VerEditar);
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
            adjuntarCaptura("Capture edición antes de guardar");
            hacerClick(PagesCreditosDesembolso.Guardar);
            ElementVisible();
        } catch (Exception e) {
            log.error("########## Error - OriginacionCreditosAccion - DescargarMediosdedispercion() #######" + e);
            assertTrue("########## Error - OriginacionCreditosAccion - DescargarMediosdedispercion()########" + e,
                    false);
        }

    }

    /*
     * ThainerPerez 21/09/2021 - Se agrega el metodo dinamico por medio de la tabla
     * que agrega X cantidad de registros saneamiento o carteras
     */
    public void agregarSaneamientosCarteras(DataTable tabla) {
        log.info(
                "********** Agregar saneamientos o carteras, OriginacionCreditosAccion - agregarSaneamientosCarteras() *********** ");
        try {
            recorerpestanas("DIGITALIZACIÓN");
            Hacer_scroll_Abajo(pestanadigitalizacionPage.SegundaPestanaDigitalizacion);
            esperaExplicita(pestanadigitalizacionPage.SegundaPestanaDigitalizacion);
            hacerClick(pestanadigitalizacionPage.SegundaPestanaDigitalizacion);
            List<Map<String, String>> data = tabla.asMaps(String.class, String.class);
            int contador = 0;
            for (Map<String, String> e : data) {
                Hacer_scroll_Abajo(pestanadigitalizacionPage.AgregarCartera);
                ElementVisible();
                esperaExplicita(pestanadigitalizacionPage.AgregarCartera);
                hacerClick(pestanadigitalizacionPage.AgregarCartera);
                ElementVisible();

                String RadioSaneamiento = String.valueOf(pestanadigitalizacionPage.RadioSaneamiento)
                        .replaceAll("By.id: ", "");
                String radioCompra = String.valueOf(pestanadigitalizacionPage.RadioCompra).replaceAll("By.id: ", "");
                String competidor = String.valueOf(pestanadigitalizacionPage.EntidadCompetidor).replaceAll("By.id: ",
                        "");
                String filtroCompetidor = String.valueOf(pestanadigitalizacionPage.FiltroEntidad).replaceAll("By.id: ",
                        "");
                String monto = String.valueOf(pestanadigitalizacionPage.MontoSaneamiento).replaceAll("By.id: ", "");
                String valorCuota = String.valueOf(pestanadigitalizacionPage.ValorCuotaSaneamiento)
                        .replaceAll("By.id: ", "");
                String fechaVencimiento = String.valueOf(pestanadigitalizacionPage.FechaVencimientoSaneamiento)
                        .replaceAll("By.id: ", "");
                String numOblogacion = String.valueOf(pestanadigitalizacionPage.NumeroObligacionSaneamiento)
                        .replaceAll("By.id: ", "");


                switch (e.get("Tipo")) {
                    case "Saneamiento":
                        log.info("********* Se ha seleccionado senamiento ***");
                        Hacer_scroll_Abajo(By.id(RadioSaneamiento.replaceAll(":" + String.valueOf(contador) + ":",
                                ":" + String.valueOf(contador) + ":")));
                        esperaExplicita(By.id(RadioSaneamiento.replaceAll(":" + String.valueOf(contador) + ":",
                                ":" + String.valueOf(contador) + ":")));
                        hacerClick(By.id(RadioSaneamiento.replaceAll(":" + String.valueOf(contador) + ":",
                                ":" + String.valueOf(contador) + ":")));
                        ElementVisible();
                        break;
                    case "Cartera":
                        log.info("********* Se ha seleccionado Cartera ***");
                        Hacer_scroll_Abajo(By.id(radioCompra.replaceAll(":" + String.valueOf(contador) + ":",
                                ":" + String.valueOf(contador) + ":")));
                        esperaExplicita(By.id(radioCompra.replaceAll(":" + String.valueOf(contador) + ":",
                                ":" + String.valueOf(contador) + ":")));
                        hacerClick(By.id(radioCompra.replaceAll(":" + String.valueOf(contador) + ":",
                                ":" + String.valueOf(contador) + ":")));
                        ElementVisible();
                        break;
                }

                ElementVisible();
                esperaExplicita(By.id(competidor.replaceAll(":" + String.valueOf(contador) + ":",
                        ":" + String.valueOf(contador) + ":")));
                hacerClick(By.id(competidor.replaceAll(":" + String.valueOf(contador) + ":",
                        ":" + String.valueOf(contador) + ":")));
                EscribirElemento(By.id(filtroCompetidor.replaceAll(":" + String.valueOf(contador) + ":",
                        ":" + String.valueOf(contador) + ":")), e.get("Entidad"));
                ElementVisible();
                EnviarEnter(By.id(filtroCompetidor.replaceAll(":" + String.valueOf(contador) + ":",
                        ":" + String.valueOf(contador) + ":")));
                ElementVisible();
                if (e.get("Tipo").equals("Saneamiento") && e.get("Entidad").contains("PAN AMERICA")) {
                    String selectMonto = String.valueOf(pestanadigitalizacionPage.montoDesplegable)
                            .replaceAll("By.id: ", "");
                    String listMonto = String.valueOf(pestanadigitalizacionPage.listMontoDesplegable)
                            .replaceAll("By.xpath: ", "");
                    hacerClick(By.id(selectMonto.replaceAll(":" + String.valueOf(contador) + ":",
                            ":" + String.valueOf(contador) + ":")));
                    ////////////////////Jonathan Varon
                    DecimalFormat formatMonto = new DecimalFormat("###,###", DecimalFormatSymbols.getInstance(Locale.ENGLISH));                    
                    String pMonto = "$" + formatMonto.format(Double.parseDouble(e.get("Monto")));  
                    selectValorLista(By.xpath(listMonto.replaceAll(":" + String.valueOf(contador) + ":",
                            ":" + String.valueOf(contador) + ":")), pMonto);
                    ////////////////////
                } else {
                    EscribirElemento(By.id(monto.replaceAll(":" + String.valueOf(contador) + ":",
                            ":" + String.valueOf(contador) + ":")), e.get("Monto"));
                    ElementVisible();
                    EscribirElemento(By.id(valorCuota.replaceAll(":" + String.valueOf(contador) + ":",
                            ":" + String.valueOf(contador) + ":")), e.get("VlrCuota"));
                    ElementVisible();
                    EscribirElemento(By.id(fechaVencimiento.replaceAll(":" + String.valueOf(contador) + ":",
                            ":" + String.valueOf(contador) + ":")), e.get("FechaVencimiento"));
                    ElementVisible();
                    EscribirElemento(By.id(numOblogacion.replaceAll(":" + String.valueOf(contador) + ":",
                            ":" + String.valueOf(contador) + ":")), e.get("NumObligacion"));
                    ElementVisible();
                }
                contador = contador + 1;
            }
            adjuntarCaptura("Finaliza de diligenciar las carteras y saneamientos");
        } catch (Exception e) {
            log.error("########## Error - OriginacionCreditosAccion - agregarSaneamientosCarteras() #######" + e);
            assertTrue("########## Error - OriginacionCreditosAccion - agregarSaneamientosCarteras()########" + e,
                    false);
        }

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
        validarTelefonosRefs(xpathSelectTelefonosRef, pestanareferenciacionpage.labelTelefonos);
        Hacer_scroll(pestanareferenciacionpage.Titulo);
        clickvarios(pestanareferenciacionpage.CheckSI);
        Hacer_scroll(pestanareferenciacionpage.GuardarReferencias);
        hacerClick(pestanareferenciacionpage.GuardarReferencias);
        ElementVisible();
        this.ingresarAnalisisCredito(this.Cedula, this.NombreCredito);
        this.EndeudamientoGlobal();
    }


    public void AprobarExcepciones(String Pdf, String Cedula) throws InterruptedException {
        hacerClick(pestanasimuladorinternopage.DetalleExcepciones);
        ElementVisible();
        esperaExplicita(pestanasimuladorinternopage.SolicitarAprobacion);
        cargarpdf(pestanasimuladorinternopage.SoportePdfExcepciones, Pdf);
        esperaExplicita(pestanasimuladorinternopage.Notificacion);
        hacerClicknotificacion();
        hacerClick(pestanasimuladorinternopage.SolicitarAprobacion);
        esperaExplicita(pestanasimuladorinternopage.Notificacion);
        String notificacion = GetText(pestanasimuladorinternopage.Notificacion);
        adjuntarCaptura("AprobarExepcion");
        if (notificacion.contains("Se han enviado solicitudes de aprobación para estas excepciones de tipo")) {
            hacerClicknotificacion();
            panelnavegacionaccion.navegarTareas();
            esperaExplicita(pagestareas.filtroDescipcion);
            EscribirElemento(pagestareas.filtroDescipcion, Cedula);
            ElementVisible();
            EscribirElemento(pagestareas.filtroTarea, "Revisar Aprobación excepción");
            ElementVisible();
            adjuntarCaptura("AprobarExepcionTareas");
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
            adjuntarCaptura("GuardarExepcion");
            hacerClicknotificacion();
        } else {
            assertTrue("#### Error Aprobar excepcion por Perfil " + notificacion, false);
        }
    }

    public void validarLasCondicionesDeLaCartaDeNotificacionDeCreditos(String cedula) {
        try {
            panelnavegacionaccion.cartaNotificacionCondicionesCredito();
            adjuntarCaptura("Ingreso al modulo carta de notificacion");
            BuscarenGrilla(pagesCartaNotificaciones.filtroCedula, cedula);
            Thread.sleep(2000);
            ElementVisible();
            esperaExplicita(pagesCartaNotificaciones.generarCarta);
            ClicUltimoElemento(pagesCartaNotificaciones.generarCarta);
            cambiarFocoDriver(1);
            esperaExplicitaNopresente(pagesCartaNotificaciones.spinnerCarta);
            esperaExplicita(pagesCartaNotificaciones.tablaValores);
            cargarDatosCarta(pagesCartaNotificaciones.vlrCredito);
            ValoresCarta = cleanValues(pagesCartaNotificaciones.listaValoresKey,
                    pagesCartaNotificaciones.listaValoresValue);
            System.out.println("VALORES DE LA CARTA -------------" + ValoresCarta.toString());
            for (Map.Entry<String, String> entry : ValoresCarta.entrySet()) {
                if (entry.getKey().equals("Valor de Crédito")) {
                    ToleranciaDoubleMensaje("Valor del crédito carta condiciones ",
                            Double.parseDouble(entry.getValue()), Double.parseDouble(ValoresCredito.get(0)));
                }
                if (entry.getKey().contains("Valor prima de seguro anticipada")) {
                    ToleranciaPesoMensaje("Valor prima de seguro anticipada ", Integer.parseInt(entry.getValue()),
                            Integer.parseInt(ValoresCredito.get(9)));
                }
                if (entry.getKey().equals("Prima de seguro no devengada crédito retanqueado")) {
                    ToleranciaPesoMensaje("Prima de seguro no devengada crédito retanqueado ",
                            Integer.parseInt(entry.getValue()), Integer.parseInt(ValoresCredito.get(11)));
                }

                if (entry.getKey().equals("Valor prima de seguro anticipada a pagar")) {
                    ToleranciaPesoMensaje("Valor prima de seguro anticipada a pagar ",
                            Integer.parseInt(entry.getValue()), Integer.parseInt(ValoresCredito.get(12)));
                }
                if (entry.getKey().contains("Valor de la fianza (IVA incluido)")) {
                    ToleranciaPesoMensaje("Valor de la fianza (IVA incluido) a pagar ",
                            Integer.parseInt(entry.getValue()), Integer.parseInt(ValoresCredito.get(15)));
                }

                if (entry.getKey().equals("Valor credito a recoger")) {
                    ToleranciaPesoMensaje("Valor credito a recoger ", Integer.parseInt(entry.getValue()),
                            RetanqueoCreditos.SaldoAlDia);
                }

                if (entry.getKey().equals("totalCompraCartera")) {
                    ToleranciaPesoMensaje("total Compra Cartera ", Integer.parseInt(entry.getValue()),
                            Integer.parseInt(ValoresCredito.get(8)));
                }

                if (entry.getKey().equals("totalGmf")) {
                    ToleranciaPesoMensaje("total 4*1000 ", Integer.parseInt(entry.getValue()),
                            Integer.parseInt(ValoresCredito.get(8)));
                }

                if (entry.getKey().equals("Valor total a desembolsar")) {
                    ToleranciaPesoMensaje("Valor total a desembolsar (REMANENTE) ", Integer.parseInt(entry.getValue()),
                            Integer.parseInt(ValoresCredito.get(12)));
                }

                if (entry.getKey().equals("Tasa EA inicial") || entry.getKey().equals("Tasa de interés EA inicial")) {
                    ToleranciaDoubleMensaje("Tasa EA inicial ", Double.parseDouble(entry.getValue()),
                            Double.parseDouble(ValoresCredito.get(3)));
                }

                if (entry.getKey().equals("Tasa NMV inicial") || entry.getKey().equals("Tasa de interés NMV inicial")) {
                    ToleranciaDoubleMensaje("Tasa NMV inicial ", Double.parseDouble(entry.getValue()),
                            Double.parseDouble(ValoresCredito.get(2)));
                }

                if (entry.getKey().equals("Plazo")) {
                    ToleranciaPesoMensaje("Plazo ", Integer.parseInt(entry.getValue()),
                            Integer.parseInt(ValoresCredito.get(1)));
                }

                if (entry.getKey().equals("Total valor cuota mensual")) {
                    ToleranciaPesoMensaje("Total valor cuota mensual ", Integer.parseInt(entry.getValue()),
                            Integer.parseInt(ValoresCredito.get(4)));
                }
            }

            checkingPageLink();
            this.validarTextos();
        } catch (Exception e) {
            log.error(
                    "########## ERROR RetanqueoCreditos - validarLasCondicionesDeLaCartaDeNotificacionDeCreditos() ########"
                            + e);
            assertTrue(
                    "########## ERROR RetanqueoCreditos - validarLasCondicionesDeLaCartaDeNotificacionDeCreditos() ########"
                            + e,
                    false);
        }
    }

    public void validarTextos() {
        if (this.DesPrimaAntic > 24) {
            this.DesPrimaAntic = 24;
        }

        List<String> buscarTextos = new ArrayList<>();
        buscarTextos.add("existe un cobro anticipado por " + this.DesPrimaAntic);
        buscarTextos.add("y que cubrirá los primeros " + this.DesPrimaAntic);
        buscarTextos.add("antes de que se hayan cumplido " + this.DesPrimaAntic);
        buscarTextos.add("único pago para la siguiente vigencia por " + this.DesPrimaAntic);

        for (int i = 0; i < buscarTextos.size(); i++) {
            BuscarTextoPage(buscarTextos.get(i));
        }
    }

    public void aceptaCondicionesDelCreditoLibreInversion(String TipoDesen, String cedula) throws InterruptedException {
        log.info("********** OriginacionCreditosAccion - aceptaCondicionesDelCreditoLibreInversion() **********");
        try {
            this.cambiarPestana(0);
            this.ClientesParaBienvenida(cedula);
            recorerpestanas("CONDICIONES DEL CRÉDITO");
            Refrescar();
            esperaExplicita(pagesclienteparabienvenida.Desembolso);
            MarcarCheck(pagesclienteparabienvenida.CheckCondicionesCredito);
            Hacer_scroll(pagesclienteparabienvenida.detalledelascarteras);
            Thread.sleep(1000);
            hacerClick(pagesclienteparabienvenida.Desembolso);
            selectValorLista(pagesclienteparabienvenida.ListDesembolso, TipoDesen);
            hacerClick(pagesclienteparabienvenida.CalificacionProceso);
            hacerClick(pagesclienteparabienvenida.CalificacionCobro);
            hacerScrollAbajo();
            Thread.sleep(1000);
            hacerClick(pagesclienteparabienvenida.Acepta);
            ElementVisible();

        } catch (Exception e) {
            log.error(
                    "########## Error - OriginacionCreditosAccion  - aceptaCondicionesDelCreditoLibreInversion() #######"
                            + e);
            assertTrue(
                    "########## Error - OriginacionCreditosAccion - aceptaCondicionesDelCreditoLibreInversion()########"
                            + e,
                    false);
        }

    }

    public void ingresaCodigoOTP(String cedula) throws InterruptedException {
        log.info("********** OriginacionCreditosAccion - ingresaCodigoOTP() **********");
        try {
            ElementVisible();

            OriginacionCreditoQuery query = new OriginacionCreditoQuery();
            esperaExplicita(pagesclienteparabienvenida.ImputOTP);
            String idcliente = null;
            ResultSet resultadoIdCliente = query.idClienteCedula(cedula);
            while (resultadoIdCliente.next()) {
                idcliente = resultadoIdCliente.getString(1);
            }
            System.out.println("### idcliente ### " + idcliente);
            String token = null;
            ResultSet resultadoToken = query.ConsultaToken(idcliente);
            while (resultadoToken.next()) {
                token = resultadoToken.getString(1);
            }
            System.out.println("### TOKEN ### " + token);
            for (int i = 0; i < token.length(); i++) {
                char currentCharacter = token.charAt(i);
                EscribirElemento(pagesclienteparabienvenida.ImputOTP, String.valueOf(currentCharacter));
                ElementVisible();
            }

            ElementVisible();


        } catch (Exception e) {
            log.error(
                    "########## Error - OriginacionCreditosAccion  - ingresaCodigoOTP() #######"
                            + e);
            assertTrue(
                    "########## Error - OriginacionCreditosAccion - ingresaCodigoOTP()########"
                            + e,
                    false);
        }

    }

    /*
     * ThainerPerez 17/Nov/2021 V1.2 - Se agrega el Datatable y se recorre con un ForMap para repetir el paso X cantidad de veces*/
    public void DescargarMediosDispercionCartera(DataTable tabla) throws InterruptedException {
        log.info("***Descargar medios de dispercion para las carteras,OriginacionCreditosAccion - DescargarMediosDispercionCartera() **");
        try {
            List<Map<String, String>> data = tabla.asMaps(String.class, String.class);
            int contador = 0;
            for (Map<String, String> objectTablaFeature : data) {
                panelnavegacionaccion.CreditoParaDesembolsoDescargar();
                esperaExplicita(PagesCreditosDesembolso.FiltroMonto);
                EscribirElemento(PagesCreditosDesembolso.FiltroMonto, objectTablaFeature.get("Monto"));
                ElementVisible();
                Thread.sleep(4000);

                String pattern = "###,###,###.###";
                double value = Double.parseDouble(objectTablaFeature.get("Monto"));

                DecimalFormat myFormatter = new DecimalFormat(pattern);
                myFormatter = new DecimalFormat(pattern, DecimalFormatSymbols.getInstance(Locale.GERMANY));
                String output = myFormatter.format(value);
                esperaExplicita(By.xpath("//td[text()='" + output + "']"));
                ClicUltimoElemento(PagesCreditosDesembolso.VerEditar);
                ElementVisible();
                hacerClick(PagesCreditosDesembolso.Banco);
                hacerClick(By.xpath("//li[starts-with(@id,'formLote:j_idt89') and text()='" + objectTablaFeature.get("Banco") + "' ]"));
                ElementVisible();
                cargarpdf(PagesCreditosDesembolso.CargarEvidencia, objectTablaFeature.get("RutaPdf"));
                esperaExplicita(PagesCreditosDesembolso.VerEvidencias);
                ElementVisible();
                hacerClick(PagesCreditosDesembolso.CrearArchivo);
                esperaExplicita(PagesCreditosDesembolso.ArchivoCreado);
                ElementVisible();
                adjuntarCaptura("Capture antes de guardar");
                hacerClick(PagesCreditosDesembolso.Guardar);
                ElementVisible();
            }
        } catch (Exception e) {
            log.error("########## Error - OriginacionCreditosAccion  - aceptaCondicionesDelCreditoLibreInversion() #######" + e);
            assertTrue("########## Error - OriginacionCreditosAccion - aceptaCondicionesDelCreditoLibreInversion()########" + e, false);
        }
    }

    //ThainerPerez 17/Nov/2021 Se comentarea este metodo ya que al parecer no se usa, eliminar si no se revienta en 1 mes
	/*
	public void VisacionCartera(String Pdf) throws InterruptedException {
		recorerpestanas("CARTERAS Y SANEAMIENTOS");
		Hacer_scroll(pagesclienteparavisacion.PazYSalvoCartera);
		cargarpdf(pagesclienteparavisacion.PazYSalvoCartera, Pdf);
		ElementVisible();
	}*/

    public void ProcesarSaneamiento(String Cedula) throws InterruptedException {
        panelnavegacionaccion.CreditoParaDesembolso();
        ElementVisible();
        esperaExplicita(PagesCreditosDesembolso.filtrocedula);
        EscribirElemento(PagesCreditosDesembolso.filtrocedula, Cedula);
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

    public void ProcesarRemanente(String Cedula) throws InterruptedException {
        log.info("***Se procesa el remanente de credito,OriginacionCreditosAccion -  ProcesarRemanente() ***");
        try {
            panelnavegacionaccion.CreditoParaDesembolso();
            ElementVisible();
            esperaExplicita(PagesCreditosDesembolso.filtrocedula);
            EscribirElemento(PagesCreditosDesembolso.filtrocedula, Cedula);
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
            adjuntarCaptura("Captura antes de procesar pagos desembolso css");
            hacerClick(pagescreditosdesembolso.ProcesarPagos);
            ElementVisible();
        } catch (Exception e) {
            log.error("########## Error - OriginacionCreditosAccion  - aceptaCondicionesDelCreditoLibreInversion() #######" + e);
            assertTrue("########## Error - OriginacionCreditosAccion - aceptaCondicionesDelCreditoLibreInversion()########" + e, false);
        }

    }

    public void agregarSaneamientosCarterasDigicredito() {
        try {
            recorerpestanas("DIGITALIZACIÓN");
            Hacer_scroll_Abajo(pestanadigitalizacionPage.SegundaPestanaDigitalizacion);
            esperaExplicita(pestanadigitalizacionPage.SegundaPestanaDigitalizacion);
            hacerClick(pestanadigitalizacionPage.SegundaPestanaDigitalizacion);
        } catch (Exception e) {
            log.error("########## Error - OriginacionCreditosAccion - agregarSaneamientosCarteras() #######" + e);
            assertTrue("########## Error - OriginacionCreditosAccion - agregarSaneamientosCarteras()########" + e,
                    false);
        }
    }

    /* * ThainerPerez V1.0 - 03/Dic/2021, 1. Se implementa la funcion de calculos directamente desde la base de datos
     * 									2. Parametros utilizados: (p_monto integer, p_xperiodoprima integer, p_tasainicial numeric,
     *  															p_plazo numeric, p_diasiniciales numeric, p_vlrcompassaneamientos numeric
                                                                    p_ingresos numeric, p_descley numeric, p_descnomina numeric, p_pagaduria text)*/
    public SimuladorDto consultarCalculosSimulador(String Monto, int DesPrimaAntic, String Tasa, String Plazo,
                                                   String DiasHabilesIntereses, String vlrCompasSaneamientos, String Ingresos, String descLey,
                                                   String descNomina, String pagaduria) {

        log.info("****** Calculando valores simulador Originacion por funcion SQL, OriginacionCreditosAccion -  consultarCalculosSimulador()*******");

        SimuladorDto resultSimulador = new SimuladorDto();
        OriginacionCreditoQuery query = new OriginacionCreditoQuery();

        ResultSet r = null;
        try {
            r = query.consultarCalculosSimuladorOriginacion(Monto, DesPrimaAntic, Tasa, Plazo,
                    DiasHabilesIntereses, vlrCompasSaneamientos, Ingresos, descLey,
                    descNomina, pagaduria);
            while (r.next()) {

                resultSimulador.setMontoSolicitar(r.getInt(1));
                resultSimulador.setCuotaCorriente(r.getInt(2));
                resultSimulador.setEstudioCredito(r.getInt(3));
                resultSimulador.setFianza(r.getInt(4));
                resultSimulador.setGmf4X100(r.getInt(5));
                resultSimulador.setInteresesIniciales(r.getInt(6));
                resultSimulador.setPrimaSeguroAnticipada(r.getInt(7));
                resultSimulador.setRemanenteEstimado(r.getInt(8));
                resultSimulador.setMontoMaxDesembolsar(r.getInt(9));
                resultSimulador.setCapacidadCliente(r.getInt(10));
            }
        } catch (Exception e) {
            log.error("########## Error - OriginacionCreditosAccion - consultarCalculosSimulador() #######" + e);
            assertTrue("########## Error - OriginacionCreditosAccion - consultarCalculosSimulador()########" + e,
                    false);
        }

        return resultSimulador;

    }
}
