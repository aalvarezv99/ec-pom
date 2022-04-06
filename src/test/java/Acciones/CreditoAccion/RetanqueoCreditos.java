package Acciones.CreditoAccion;

import Acciones.ComunesAccion.LoginAccion;
import Acciones.ComunesAccion.PanelPrincipalAccion;
import Archivo.LeerArchivo;
import CommonFuntions.BaseTest;
import Consultas.OriginacionCreditoQuery;
import Pages.ComunesPage.PanelNavegacionPage;
import Pages.CreditosPage.*;
import Pages.SolicitudCreditoPage.PestanaDigitalizacionPage;
import Pages.SolicitudCreditoPage.PestanaReferenciacionPage;
import Pages.SolicitudCreditoPage.PestanaSimuladorInternoPage;
import dto.SimuladorDto;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.junit.Assert.assertTrue;

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
    PagesCartaNotificaciones pagesCartaNotificaciones;

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
    List<String> ValoresCredito = new ArrayList<>();
    Map<String, String> ValoresCarta = new HashMap<>();
    int DesPrimaAntic = 0;
    public static Map<Integer, Map<String, String>> listaCreditosPadre = new HashMap<>();
    int Tasaxmillonseguro = 4625;
    //Variables verificacion Plan de Pagos
    private String vg_SegundaTasaInteres_Retanqueo;
    private String vg_MontoAprobado_Retanqueo;
    private String vg_PrimaSeguroAnticipada_Retanqueo;
    private String vg_PrimaNoDevengadaSeguro_Retanqueo;
    private String vg_PrimaNetaSeguro_Retanqueo;
    private String vg_CuotasPrimaSeguroAnticipada;
    String fechaActual = new SimpleDateFormat("dd/MM/yyyy").format(new Date());

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
        pagesCartaNotificaciones = new PagesCartaNotificaciones(driver);
    }

    /************ INICIO ACCIONES RETANQUEO CREDITOS ***************/
    public void iniciarSesion() {
        // loginaccion.iniciarSesion();
    }

    public void ingresarRetanqueoAsesor() {
    	log.info("****************** RetanqueoCreditos - ingresarRetanqueoAsesor()********** ");
        try {
        	panelnavegacionaccion.Retanqueo();
            adjuntarCaptura("Ingresa al modulo de retanqueo  ");
        } catch (Exception e) {
            log.error("########## Error - RetanqueoCreditos - ingresarRetanqueoAsesor() #######" + e);
            assertTrue("########## Error - RetanqueoCreditos - ingresarRetanqueoAsesor() ########" + e, false);
        }
        
    }

    public void FiltrarCredito(String Cedula, String Credito) throws InterruptedException {
        log.info("************ Buscar credito a retanquear, RetanqueoCreditos - FiltrarCredito()*****");
        try {
            BuscarenGrilla(retanqueopages.cedula, Cedula);
            ElementVisible();
            BuscarenGrilla(retanqueopages.credito, Credito);
            ElementVisible();
            esperaExplicitaTexto(Credito);
            adjuntarCaptura("Se filtra el retanqueo ");
        } catch (Exception e) {
            log.error("########## ERROR RetanqueoCreditos - FiltrarCredito() ########" + e);
            assertTrue("########## ERROR RetanqueoCreditos - FiltrarCredito() ########" + e, false);
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
            assertTrue("########## ERROR RetanqueoCreditos - Retanquear() ########" + e, false);
        }

    }

    public void Credito(String Cedula) throws InterruptedException {
        log.info("*********Buscar Credito en solicitud, RetanqueoCreditos - Credito()********");
        try {
            panelnavegacionaccion.navegarCreditoSolicitud();
            BuscarenGrilla(retanqueopages.inputCedula, Cedula);
            ElementVisible();
            CedulaCliente = Cedula;
            adjuntarCaptura("FiltroCedula");
            esperaExplicitaTexto(Cedula);
        } catch (Exception e) {
            log.error("########## ERROR RetanqueoCreditos - Credito() ########" + e);
            assertTrue("########## ERROR RetanqueoCreditos - Credito() ########" + e, false);
        }

    }

    public void seleccionarRetanqueo() {
        log.info("****** seleccionar continuar retanqueo,  RetanqueoCreditos - seleccionarRetanqueo()******");
        try {
            ClicUltimoElemento(retanqueopages.btnVerEditar);
            adjuntarCaptura("Se seleciona el retanqueo ");
            ElementVisible();
        } catch (Exception e) {
            log.error("########## ERROR RetanqueoCreditos - seleccionarRetanqueo() ########" + e);
            assertTrue("########## ERROR RetanqueoCreditos - seleccionarRetanqueo() ########" + e, false);
        }

    }

    public void borrarArchivos() throws InterruptedException {
        log.info("******** Borrar archivos, RetanqueoCreditos - borrarArchivos()********");
        try {
            clickvariosespera(retanqueopages.borrararchivos);
            adjuntarCaptura("Se borran los archivos del credito anterior ");
        } catch (Exception e) {
            log.error("########## ERROR RetanqueoCreditos - borrarArchivos() ########" + e);
            assertTrue("########## ERROR RetanqueoCreditos - borrarArchivos() ########" + e, false);
        }

    }

    public void CargarArchivos(String pdf) throws InterruptedException {
        log.info("*********Cargar los nuevod documento, RetanqueoCreditos - CargarArchivos()*******");
        try {
            Thread.sleep(1000);
            Rutapdf = pdf;
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
            assertTrue("########## ERROR RetanqueoCreditos - CargarArchivos() ########" + e, false);
        }

    }

    public void ConsultaCentrales() throws InterruptedException {
        log.info("******** consultar centrales,  RetanqueoCreditos - ConsultaCentrales()*********");
        try {
            ElementVisible();
            hacerClick(retanqueopages.BtnConsultaCentrales);
            ElementVisible();
            esperaExplicita(retanqueopages.notificacion);
            adjuntarCaptura("Consulta a Centrales");
            Thread.sleep(5000);
        } catch (Exception e) {
            log.error("########## ERROR RetanqueoCreditos - ConsultaCentrales() ########" + e);
            assertTrue("########## ERROR RetanqueoCreditos - ConsultaCentrales() ########" + e, false);
        }

    }

    public void Seguridad() throws InterruptedException {
        log.info("******** espera credito viable, RetanqueoCreditos - Seguridad()******");
        try {
            recorerpestanas("SEGURIDAD");
            hacerClick(retanqueopages.Viable);
            // hacerClick(retanqueopages.Guardar);
            RepetirConsultaCentrales(retanqueopages.Guardar, retanqueopages.Concepto);
            ElementVisible();
            // esperaExplicita(retanqueopages.Concepto);
            esperaExplicitaSeguridad(pestanaSeguridadPage.BtnCheck, CedulaCliente);
            Refrescar();
            esperaExplicita(pestanaSeguridadPage.Concepto);
            Hacer_scroll(pestanaSeguridadPage.Concepto);
            esperaExplicitaSeguridad(pestanaSeguridadPage.BtnCheck, CedulaCliente);
            adjuntarCaptura("Se marca el credito viable ");
        } catch (Exception e) {
            log.error("########## ERROR RetanqueoCreditos - Seguridad() ########" + e);
            assertTrue("########## ERROR RetanqueoCreditos - Seguridad() ########" + e, false);
        }

    }

    public void Simulador(String Retanqueo, String Tasa, String Plazo, String DiasHabilesIntereses, String Ingresos,
                          String descLey, String descNomina, String VlrCompraSaneamiento) throws NumberFormatException, SQLException {

        log.info("********* llenar simulador interno RTANQ, RetanqueoCreditos - Simulador()*********");
        try {
            recorerpestanas("SIMULADOR");
            VlrRetanqueo = Integer.parseInt(Retanqueo);

            // consulta base de datos
            OriginacionCreditoQuery query = new OriginacionCreditoQuery();
            ResultSet resultado = query.ConsultaDescuentoPrimaAntic();
            while (resultado.next()) {
                DesPrimaAntic = Integer.parseInt(resultado.getString(1));
            }

            if (Integer.valueOf(Plazo) < DesPrimaAntic) {
                int periodoGracia = (int) Math.ceil((double) Integer.parseInt(DiasHabilesIntereses) / 30);
                DesPrimaAntic = periodoGracia + Integer.valueOf(Plazo);
            }

            esperaExplicita(retanqueopages.labelTasa);
            hacerClicknotificacion();
            hacerClick(retanqueopages.labelTasa);
            selectValorLista(retanqueopages.listTasa, retanqueopages.optionsTasa, Tasa);
            assertTextoelemento(retanqueopages.labelTasa, Tasa);

            hacerClicknotificacion();
            ElementVisible();
            hacerClick(retanqueopages.inputPlazo);
            ElementVisible();
            LimpiarConTeclado(retanqueopages.inputPlazo);
            EscribirElemento(retanqueopages.inputPlazo, Plazo);
            ElementVisible();
            hacerClick(retanqueopages.inputMonto);
            ElementVisible();

            int countMultiple = 0;
            resultado = query.consultarCreditosMultiples(CedulaCliente);
            while (resultado.next()) {
                countMultiple = Integer.parseInt(resultado.getString(1));
            }


            if (countMultiple > 1) {
                log.info("Retanqueo multiple");

                if (ValidarElementoPresente(retanqueopages.listValoresRetanqMultiple)) {
                    assertBooleanImprimeMensaje("########## ERROR no se visualiza la lista de retanqueos en el simulador interno ###################", true);
                }

                SaldoAlDia = sumarListaValoresCreditosValue(retanqueopages.listValoresRetanqMultiple);
                Monto = Integer.parseInt(TextoElemento(retanqueopages.inputMontoValor));
                int saldoRetanqueoMultiple = Integer.parseInt(TextoElemento(retanqueopages.inputSumaSaldoDiaRetanqMultiple));
                // se comentó momentaneamente por error de la instancia
                // calculoCondicionesCreditoRecoger(Monto, SaldoAlDia, VlrRetanqueo, saldoRetanqueoMultiple);

            } else {
                log.info("RetanqueoNormal");
                SaldoAlDia = Integer.parseInt(TextoElemento(retanqueopages.inputMontoValor));
                Monto = Integer.parseInt(TextoElemento(retanqueopages.inputMontoValor)) + VlrRetanqueo;
                LimpiarConTeclado(retanqueopages.inputMonto);
                ElementVisible();
                EscribirElemento(retanqueopages.inputMonto, String.valueOf(Monto));
                ElementVisible();
            }

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
            Remanente = Integer.parseInt(TextoElemento(pestanasimuladorinternopage.RemanenteEstimado));
            adjuntarCaptura("Se llenan los campos del simulador ");
        } catch (Exception e) {
            log.error("########## ERROR RetanqueoCreditos - Simulador() ########" + e);
            assertTrue("########## ERROR RetanqueoCreditos - Simulador() ########" + e, false);
        }
    }


    /*
     * TP - 31/08/2021, Se actualiza el simulador con retanqueo para prima mensual
     * anticipada, se agrega el monto maximo sugerido
     *
     * ThainePerez 17/09/2021, Se actualiza el calculo de est y Fianza del credito
     * hijo, teniendo en cuenta los valores no cunsumidos del padre
     *
     * JV - 20/12/2021, Se Implementa el uso de la funcion en Base de Datos.
     * 
     * ThainerPerez 03/03/2022, Se implementa la fecha actual en la funcion de retanqueo 
     * 								para que sea utilizada en el estudio de credito
     * ThainerPerez 30/03/2022, Se encierra en el bloque de codigo Try Cath para controlar la exepxion en
     * 							caso que ocurra
     */
    public void ValidarSimulador(String Ingresos, String descLey, String descNomin, String Tasa, String Plazo,
                                 String Credito, String DiasHabilesIntereses, String VlrCompraSaneamiento)
            throws NumberFormatException, SQLException {

        log.info("********Validar Simulador interno RETANQ, RetanqueoCreditos - ValidarSimulador()***********");
        try {
        	// consulta base de datos descuento prima anticipada
            OriginacionCreditoQuery query = new OriginacionCreditoQuery();
            // consulta base de datos calculo de prima true o false
            String prima = "";
            ResultSet resultadoPrima = query.CalculoPrima(Credito);
            while (resultadoPrima.next()) {
                prima = resultadoPrima.getString(1);
            }
            System.out.println(" Variable prima: " + prima);
            //Obtencion valor "Monto" para la funcion DB-SQL
            String montoSolicitarPantalla = TextoElemento(pestanasimuladorinternopage.ResultMontoSoli);

            //DTO para almacenar consulta DB-SQL
            SimuladorDto calculosSimulador = new SimuladorDto();

            calculosSimulador = this.consultarCalculosSimuladorRetanqueo(Credito, Tasa, Plazo, DiasHabilesIntereses, montoSolicitarPantalla, VlrCompraSaneamiento,  fechaActual);

            log.info("Tipo Calculos" + calculosSimulador.getTipoCalculos());
            log.info("Prima Seguro Anticipada" + calculosSimulador.getPrimaSeguroAnticipada());
            log.info("Cuota Corriente" + calculosSimulador.getCuotaCorriente());
            log.info("Gmf4X100" + calculosSimulador.getGmf4X100());
            log.info("Prima No Devengada" + calculosSimulador.getPrimaNoDevengada());
            log.info("Prima Neta" + calculosSimulador.getPrimaNeta());
            log.info("Suma Fianzas" + calculosSimulador.getSumaFianzas());
            log.info("Fianza Padre" + calculosSimulador.getFianzaPadre());
            log.info("fianza neta" + calculosSimulador.getFianzaNeta());
            log.info("Estudio Credito" + calculosSimulador.getEstudioCredito());
            log.info("Saldo al Dia" + calculosSimulador.getSaldoAlDia());
            log.info("Remanente Estimado" + calculosSimulador.getRemanenteEstimado());

            //Logica segun Anticipado o Mensualizado

            if (prima == "") {
                log.info("----------------- MENSUALIZADO -----------------------");

                ToleranciaPesoMensaje("######  SIM ASESOR RETANQUEO - CALCULANDO Prima Anticipada de seguro ",
                        Integer.parseInt(TextoElemento(pestanasimuladorinternopage.PrimaAnticipadaSeguroSInterno)), calculosSimulador.getPrimaSeguroAnticipada());
                ToleranciaPesoMensaje("######  SIM ASESOR RETANQUEO - CALCULANDO Cuota corriente ",
                        Integer.parseInt(TextoElemento(pestanasimuladorinternopage.CuotaCorriente)), calculosSimulador.getCuotaCorriente());
                ToleranciaPesoMensaje("######  SIM ASESOR RETANQUEO - CALCULANDO Estudio Credito IVA ",
                        Integer.parseInt(TextoElemento(pestanasimuladorinternopage.EstudioCreditoIVA)), calculosSimulador.getEstudioCredito());
                ToleranciaPesoMensaje("######  SIM ASESOR RETANQUEO - CALCULANDO Comparación fianza total ",
                        Integer.parseInt(TextoElemento(pestanasimuladorinternopage.valorFianzaTotal)), calculosSimulador.getSumaFianzas());
                ToleranciaPesoMensaje("######  SIM ASESOR RETANQUEO - CALCULANDO Comparación fianza padre ",
                        Integer.parseInt(TextoElemento(pestanasimuladorinternopage.valorFianzaPadre)), calculosSimulador.getFianzaPadre());
                ToleranciaPesoMensaje("######  SIM ASESOR RETANQUEO - CALCULANDO Comparacion Fianza",
                        Integer.parseInt(TextoElemento(pestanasimuladorinternopage.ValorFianza)), calculosSimulador.getFianzaNeta());
                ToleranciaPesoMensaje("######  SIM ASESOR RETANQUEO - CALCULANDO Pantalla Gmf 4x1000 ",
                        Integer.parseInt(TextoElemento(pestanasimuladorinternopage.Gmf4100)), calculosSimulador.getGmf4X100());
                //ToleranciaPesoMensaje("######  SIM ASESOR RETANQUEO - CALCULANDO Valor Desembolsar ",
                //		Integer.parseInt(TextoElemento(pestanasimuladorinternopage.SimuladorInternorValoraDesembolsar)),calculosSimulador.getRemanenteEstimado());
            } else {
                log.info(" ---------------------- ANTICIPADO ----------------------------- ");

                ToleranciaPesoMensaje("######  SIM ASESOR RETANQUEO - CALCULANDO Prima Anticipada de seguro ",
                        Integer.parseInt(TextoElemento(pestanasimuladorinternopage.PrimaAnticipadaSeguroSInterno)), calculosSimulador.getPrimaSeguroAnticipada());
                ToleranciaPesoMensaje("######  SIM ASESOR RETANQUEO - CALCULANDO Prima neta ",
                        Integer.parseInt(TextoElemento(pestanasimuladorinternopage.PrimaNeta)), calculosSimulador.getPrimaNeta());
                ToleranciaPesoMensaje("######  SIM ASESOR RETANQUEO - CALCULANDO Prima No Devengada ",
                        Integer.parseInt(TextoElemento(pestanasimuladorinternopage.PrimaNoDevengadaSimuladorInterno)), calculosSimulador.getPrimaNoDevengada());
                ToleranciaPesoMensaje("######  SIM ASESOR RETANQUEO - CALCULANDO Cuota corriente ",
                        Integer.parseInt(TextoElemento(pestanasimuladorinternopage.CuotaCorriente)), calculosSimulador.getCuotaCorriente());
                ToleranciaPesoMensaje("######  SIM ASESOR RETANQUEO - CALCULANDO Estudio Credito IVA ",
                        Integer.parseInt(TextoElemento(pestanasimuladorinternopage.EstudioCreditoIVA)), calculosSimulador.getEstudioCredito());
                ToleranciaPesoMensaje("######  SIM ASESOR RETANQUEO - CALCULANDO Comparación fianza total ",
                        Integer.parseInt(TextoElemento(pestanasimuladorinternopage.valorFianzaTotal)), calculosSimulador.getSumaFianzas());
                ToleranciaPesoMensaje("######  SIM ASESOR RETANQUEO - CALCULANDO Comparación fianza padre ",
                        Integer.parseInt(TextoElemento(pestanasimuladorinternopage.valorFianzaPadre)), calculosSimulador.getFianzaPadre());
                ToleranciaPesoMensaje("######  SIM ASESOR RETANQUEO - CALCULANDO Comparacion Fianza",
                        Integer.parseInt(TextoElemento(pestanasimuladorinternopage.ValorFianza)), calculosSimulador.getFianzaNeta());
                ToleranciaPesoMensaje("######  SIM ASESOR RETANQUEO - CALCULANDO Pantalla Gmf 4x1000 ",
                        Integer.parseInt(TextoElemento(pestanasimuladorinternopage.Gmf4100)), calculosSimulador.getGmf4X100());
                //ToleranciaPesoMensaje("######  SIM ASESOR RETANQUEO - CALCULANDO Valor Desembolsar ",
                //		Integer.parseInt(TextoElemento(pestanasimuladorinternopage.SimuladorInternorValoraDesembolsar)),calculosSimulador.getRemanenteEstimado());

            }
            adjuntarCaptura("Validacion Simulador");
		} catch (Exception e) {
			log.error("########## Error - RetanqueoCreditos - ValidarSimulador() #######" + e);
            assertTrue("########## Error - RetanqueoCreditos - ValidarSimulador() ########" + e, false);
		}
        
    }

    public void SolicitarCredito() throws InterruptedException {
        log.info("*********** Presionar boton solicitar,  RetanqueoCreditos - SolicitarCredito()*********");
        try {
            Hacer_scroll_Abajo(pestanasimuladorinternopage.Solicitar);
            hacerClick(pestanasimuladorinternopage.Solicitar);
            ElementVisible();
            hacerClicknotificacion();

            if (!EncontrarElementoVisibleCss(pestanasimuladorinternopage.ModalExcepciones)) {
                AprobarExcepciones(Rutapdf, CedulaCliente);
                Credito(CedulaCliente);
                seleccionarRetanqueo();
                ElementVisible();
            }
            ElementVisible();
            adjuntarCaptura("Solicitar Credito");
        } catch (Exception e) {
            log.error("########## ERROR RetanqueoCreditos - SolicitarCredito() ########" + e);
            assertTrue("########## ERROR RetanqueoCreditos - SolicitarCredito() ########" + e, false);
        }

    }

    public void Confirmaidentidad(String codigo) {
    	log.info("******************RetanqueoCreditos - Confirmaidentidad()********** ");
        try {
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
             adjuntarCaptura("Se confirma Entidad");
             esperaExplicitaNopresente(pestanadigitalizacionPage.Notificacion);
        } catch (Exception e) {
            log.error("########## Error - RetanqueoCreditos - Confirmaidentidad() #######" + e);
            assertTrue("########## Error - RetanqueoCreditos - Confirmaidentidad() ########" + e, false);
        }
       
    }

    /*ThainerPerez V1.2 24/Marzo/2022:  1. Se encierre el bloque en un try catch
     * 									2. Se crea la acaptura de pantalla despues de terminar el proceso*/
    public void AprobarReferenciasPagaduria() {
    	log.info("******* Aprobando referencias de pagaduria, RetanqueoCreditos - AprobarReferenciasPagaduria() *********");
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
            adjuntarCaptura("AprobarReferenciaPagaduria");
		} catch (Exception e) {
			 log.error("########## ERROR RetanqueoCreditos - AprobarReferenciasPagaduria() ########" + e);
	         assertTrue("########## ERROR RetanqueoCreditos - AprobarReferenciasPagaduria() ########" + e, false);
		}
    }
    
    public void navegarSimuladoranalistaRetanqueo(String fecha, String Mes, String anno) {
    	log.info("** Llenando informacion simulador analista Retanqueo, RatanqueoCreditos - navegarSimuladoranalistaRetanqueo()***");
    	try {
    		esperaExplicita(pestanasimuladorinternopage.MesDeAfecatcion);
    		ElementVisible();
    		hacerClick(pestanasimuladorinternopage.MesDeAfecatcion);
            selectValorLista(pestanasimuladorinternopage.ListaMes, Mes);
            ElementVisible();
            hacerClick(pestanasimuladorinternopage.FechaDesembolso);
            Clear(pestanasimuladorinternopage.FechaDesembolso);
            EscribirElemento(pestanasimuladorinternopage.FechaDesembolso, fecha);
            hacerTab(pestanasimuladorinternopage.FechaDesembolso);
            esperaExplicita(pestanasimuladorinternopage.MesDeAfecatcion);
            Clear(pestanasimuladorinternopage.anoAfectacion);
            EscribirElemento(pestanasimuladorinternopage.anoAfectacion, anno);
            hacerClick(pestanasimuladorinternopage.FechasManuales);
            ElementVisible();
            adjuntarCaptura("Llenado sim Analista");
            hacerClick(pestanasimuladorinternopage.CalcularDesglose);
            ElementVisible();
            hacerClicknotificacion();
            esperaExplicitaNopresente(pestanadigitalizacionPage.Notificacion);
            adjuntarCaptura("simulador analista, captura de campos");
            Hacer_scroll(pestanasimuladorinternopage.ValorCuota);
            adjuntarCaptura("simulador analista, captura de campos");
            Hacer_scroll(pestanasimuladorinternopage.IngresosAsesor);
            adjuntarCaptura("simulador analista, captura de campos");
		} catch (Exception e) {
			log.error("########## Error - RetanqueoCreditos - ValidarSimuladorAnalistaRetanqueos() #######" + e);
            assertTrue("########## Error - RetanqueoCreditos- ValidarSimuladorAnalistaRetanqueos() ########" + e, false);
		}
    }

    public void ValidarSimuladorAnalistaRetanqueos(String anno,String Credito,String retanqueo,String fecha,
    		String Mes,String Plazo,String Ingresos,String descLey,String descNomina,String DiasHabilesIntereses,String Tasa,String VlrCompraSaneamiento) throws InterruptedException, SQLException {
      
    	navegarSimuladoranalistaRetanqueo(fecha,  Mes,  anno);
    	log.info("******* Validando simulador Analista retanqueo, RetanqueoCreditos - ValidarSimuladorAnalistaRetanqueos() *******");
    	try {
    		// consulta base de datos calculo de prima true o false
            String prima = "";
            OriginacionCreditoQuery query = new OriginacionCreditoQuery();
            ResultSet resultadoPrima = query.CalculoPrima(Credito);
            while (resultadoPrima.next()) {
                prima = resultadoPrima.getString(1);
            }

            // consulta base de datos descuento prima anticipada
            ResultSet resultado = query.ConsultaDescuentoPrimaAntic();
            while (resultado.next()) {
                DesPrimaAntic = Integer.parseInt(resultado.getString(1));
            }

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

            log.info("Tasa Estudio Credito " + EstudioCredito);
            log.info("Tasa Fianza " + TasaFianza);
            log.info("Valor mes Dos " + mesDos);
            log.info("Tasa Dos" + tasaDos);

            // consulta para validar prima menor a 24 meses
            if (Integer.parseInt(Plazo) < DesPrimaAntic) {
                int periodoGracia = (int) Math.ceil((double) Integer
                        .parseInt(TextoElemento(pestanasimuladorinternopage.InteresesInicialesSimuladorAnalista)) / 30);
                DesPrimaAntic = periodoGracia + Integer.parseInt(Plazo);
            }
            vg_CuotasPrimaSeguroAnticipada = String.valueOf(DesPrimaAntic);//***************************************


            System.out.println(" Variable prima: " + prima);
            int PrimaNoDevengada = 0;

            String calculoSoliPantalla = TextoElemento(pestanasimuladorinternopage.CapitalTotal);
            String diasInteresesPantalla = TextoElemento(pestanasimuladorinternopage.InteresesInicialesSimuladorAnalista);

            if (!ValidarElementoPresente(pestanasimuladorinternopage.listaCreditosRecoger)) {
                log.info("Entra a validar los calculos de las condiciones del credito a recoger");
                int creditoRecoger = sumarListaValoresCreditosValue(pestanasimuladorinternopage.listaCreditosRecoger);
                int MontoSolicitado = Integer.parseInt(TextoElemento(pestanasimuladorinternopage.MontoSolicitado));
                calculoCondicionesCreditoRecoger(MontoSolicitado, creditoRecoger, Integer.parseInt(retanqueo), creditoRecoger);
            }

            //DTO para almacenar consulta DB-SQL
            SimuladorDto calculosSimulador = new SimuladorDto();
            
            String fechaDesembolso = TextoElemento(pestanasimuladorinternopage.FechaDesembolso);
            calculosSimulador = this.consultarCalculosSimuladorRetanqueo(Credito, Tasa, Plazo, diasInteresesPantalla, calculoSoliPantalla, VlrCompraSaneamiento, fechaDesembolso);

            log.info("Tipo Calculos" + calculosSimulador.getTipoCalculos());
            log.info("Prima Seguro Anticipada" + calculosSimulador.getPrimaSeguroAnticipada());
            log.info("Cuota Corriente" + calculosSimulador.getCuotaCorriente());
            log.info("Gmf4X100" + calculosSimulador.getGmf4X100());
            log.info("Prima No Devengada" + calculosSimulador.getPrimaNoDevengada());
            log.info("Prima Neta" + calculosSimulador.getPrimaNeta());
            log.info("Suma Fianzas" + calculosSimulador.getSumaFianzas());
            log.info("Fianza Padre" + calculosSimulador.getFianzaPadre());
            log.info("fianza neta" + calculosSimulador.getFianzaNeta());
            log.info("Estudio Credito" + calculosSimulador.getEstudioCredito());
            log.info("Saldo al Dia" + calculosSimulador.getSaldoAlDia());
            log.info("Remanente Estimado" + calculosSimulador.getRemanenteEstimado());

            if (prima == "") {
                log.info("----------------- MENSUALIZADO -----------------------");

                ToleranciaPesoMensaje("######  SIM ANALISTA RETANQUEO - CALCULANDO Prima Anticipada de seguro ",
                        Integer.parseInt(TextoElemento(pestanasimuladorinternopage.PrimaAnticipadaSeguroAsesor)), calculosSimulador.getPrimaSeguroAnticipada());
                ToleranciaPesoMensaje("######  SIM ANALISTA RETANQUEO - CALCULANDO Estudio Credito IVA ",
                        Integer.parseInt(TextoElemento(pestanasimuladorinternopage.EstudioCreditoSAnalista)), calculosSimulador.getEstudioCredito());
                ToleranciaPesoMensaje("######  SIM ANALISTA RETANQUEO - CALCULANDO Comparación fianza total ",
                        Integer.parseInt(TextoElemento(pestanasimuladorinternopage.valorFianzaTotalAna)), calculosSimulador.getSumaFianzas());
                ToleranciaPesoMensaje("######  SIM ANALISTA RETANQUEO - CALCULANDO Comparación fianza padre ",
                        Integer.parseInt(TextoElemento(pestanasimuladorinternopage.valorFianzaPadreAna)), calculosSimulador.getFianzaPadre());
                ToleranciaPesoMensaje("######  SIM ANALISTA RETANQUEO - CALCULANDO Comparacion Fianza",
                        Integer.parseInt(TextoElemento(pestanasimuladorinternopage.ValorFianzaAnalista)), calculosSimulador.getFianzaNeta());
                ToleranciaPesoMensaje("######  SIM ASESOR RETANQUEO - CALCULANDO Valor Desembolsar ",
                		Integer.parseInt(TextoElemento(pestanasimuladorinternopage.ValoraDesembolsar)),calculosSimulador.getRemanenteEstimado());


                //Variables globales - Retanqueo - Validaciones Cabecera Plan De Pagos - IF
                vg_PrimaSeguroAnticipada_Retanqueo = String.valueOf(calculosSimulador.getPrimaSeguroAnticipada());
            } else {
                log.info(" ---------------------- ANTICIPADO ----------------------------- ");

                ToleranciaPesoMensaje("######  SIM ANALISTA RETANQUEO - CALCULANDO Prima Anticipada de seguro ",
                        Integer.parseInt(TextoElemento(pestanasimuladorinternopage.PrimaAnticipadaSeguroAsesor)), calculosSimulador.getPrimaSeguroAnticipada());
                ToleranciaPesoMensaje("######  SIM ASESOR RETANQUEO - CALCULANDO Prima neta ",
                        Integer.parseInt(TextoElemento(pestanasimuladorinternopage.PrimaNetaSimuladorAnalista)), calculosSimulador.getPrimaNeta());
                ToleranciaPesoMensaje("######  SIM ASESOR RETANQUEO - CALCULANDO Prima No Devengada ",
                        Integer.parseInt(TextoElemento(pestanasimuladorinternopage.PrimaNoDevengadaSimuladorAnalista)), calculosSimulador.getPrimaNoDevengada());
                ToleranciaPesoMensaje("######  SIM ANALISTA RETANQUEO - CALCULANDO Estudio Credito IVA ",
                        Integer.parseInt(TextoElemento(pestanasimuladorinternopage.EstudioCreditoSAnalista)), calculosSimulador.getEstudioCredito());
                ToleranciaPesoMensaje("######  SIM ANALISTA RETANQUEO - CALCULANDO Comparación fianza total ",
                        Integer.parseInt(TextoElemento(pestanasimuladorinternopage.valorFianzaTotalAna)), calculosSimulador.getSumaFianzas());
                ToleranciaPesoMensaje("######  SIM ANALISTA RETANQUEO - CALCULANDO Comparación fianza padre ",
                        Integer.parseInt(TextoElemento(pestanasimuladorinternopage.valorFianzaPadreAna)), calculosSimulador.getFianzaPadre());
                ToleranciaPesoMensaje("######  SIM ANALISTA RETANQUEO - CALCULANDO Comparacion Fianza",
                        Integer.parseInt(TextoElemento(pestanasimuladorinternopage.ValorFianzaAnalista)), calculosSimulador.getFianzaNeta());
                //Variables globales - Retanqueo - Validaciones Cabecera Plan De Pagos - ELSE
                vg_PrimaSeguroAnticipada_Retanqueo = String.valueOf(calculosSimulador.getPrimaSeguroAnticipada());
                vg_PrimaNetaSeguro_Retanqueo = String.valueOf(calculosSimulador.getFianzaNeta());
                vg_PrimaNoDevengadaSeguro_Retanqueo = String.valueOf(calculosSimulador.getPrimaNoDevengada());


            }

            //Variables globales - Retanqueo - Validaciones Cabecera Plan De Pagos
            vg_MontoAprobado_Retanqueo = String.valueOf(calculoSoliPantalla);
            vg_SegundaTasaInteres_Retanqueo = String.valueOf(tasaDos * 100);
		} catch (Exception e) {
			log.error("########## Error - RetanqueoCreditos - ValidarSimuladorAnalistaRetanqueos() #######" + e);
            assertTrue("########## Error - RetanqueoCreditos- ValidarSimuladorAnalistaRetanqueos() ########" + e, false);
		}
        
    }

    public void validelainformacioncabeceraconsusconceptosparaRetanqueo(String Tasa, String Plazo) {
    	log.info("******************RetanqueoCreditos - validelainformacioncabeceraconsusconceptosparaRetanqueo()**********");
    	try {
    		 validarCabeceraPlanDePagos("Retanqueo",
    	                Tasa,
    	                Plazo,
    	                vg_MontoAprobado_Retanqueo,
    	                vg_SegundaTasaInteres_Retanqueo,
    	                vg_PrimaSeguroAnticipada_Retanqueo,
    	                vg_CuotasPrimaSeguroAnticipada,
    	                vg_PrimaNoDevengadaSeguro_Retanqueo,
    	                vg_PrimaNetaSeguro_Retanqueo,
    	                pestanasimuladorinternopage.KeyCabeceraPlanDePagos,
    	                pestanasimuladorinternopage.ValueCabeceraPlanDePagos);
		} catch (Exception e) {
			log.error("########## Error - RetanqueoCreditos - validelainformacioncabeceraconsusconceptosparaRetanqueo() #######" + e);
            assertTrue("########## Error - RetanqueoCreditos- validelainformacioncabeceraconsusconceptosparaRetanqueo() ########" + e, false);
		}
       

    }

    public void ValidarSimuladorAnalistaRetanqueosCarteraSaneamiento(String anno, String Credito, String retanqueo,
                                                                     String fecha, String Mes, String Plazo, String Ingresos, String descLey, String descNomina, String Cartera,
                                                                     String Saneamiento, String DiasHabilesIntereses, String Tasa) throws InterruptedException, SQLException {
       
    	navegarSimuladoranalistaRetanqueo( fecha,  Mes,  anno);
    	log.info("******************RetanqueoCreditos - ValidarSimuladorAnalistaRetanqueosCarteraSaneamiento()********** ");
        try {
        	int TotalCarteras = (Integer.parseInt(Cartera) + Integer.parseInt(Saneamiento));
            int Gmf4100 = (int) Gmf4100(TotalCarteras, 0.004);
            int DescuentosPorCartera = ((Gmf4100 + TotalCarteras));

            // consulta base de datos calculo de prima true o false
            String prima = "";
            OriginacionCreditoQuery query = new OriginacionCreditoQuery();
            ResultSet resultadoPrima = query.CalculoPrima(Credito);
            while (resultadoPrima.next()) {
                prima = resultadoPrima.getString(1);
            }

            // consulta base de datos descuento prima anticipada
            ResultSet resultado = query.ConsultaDescuentoPrimaAntic();
            while (resultado.next()) {
                DesPrimaAntic = Integer.parseInt(resultado.getString(1));
            }

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

            log.info("Tasa Estudio Credito " + EstudioCredito);
            log.info("Tasa Fianza " + TasaFianza);
            log.info("Valor mes Dos " + mesDos);
            log.info("Tasa Dos" + tasaDos);

            // consulta para validar prima menor a 24 meses
            if (Integer.parseInt(Plazo) < DesPrimaAntic) {
                int periodoGracia = (int) Math.ceil((double) Integer
                        .parseInt(TextoElemento(pestanasimuladorinternopage.InteresesInicialesSimuladorAnalista)) / 30);
                DesPrimaAntic = periodoGracia + Integer.parseInt(Plazo);
            }
            vg_CuotasPrimaSeguroAnticipada = String.valueOf(DesPrimaAntic);//***************************************


            System.out.println(" Variable prima: " + prima);
            int PrimaNoDevengada = 0;

            String calculoSoliPantalla = TextoElemento(pestanasimuladorinternopage.CapitalTotal);

            if (!ValidarElementoPresente(pestanasimuladorinternopage.listaCreditosRecoger)) {
                log.info("Entra a validar los calculos de las condiciones del credito a recoger");
                int creditoRecoger = sumarListaValoresCreditosValue(pestanasimuladorinternopage.listaCreditosRecoger);
                int MontoSolicitado = Integer.parseInt(TextoElemento(pestanasimuladorinternopage.MontoSolicitado));
                calculoCondicionesCreditoRecoger(MontoSolicitado, creditoRecoger, Integer.parseInt(retanqueo), creditoRecoger);
            }

            //DTO para almacenar consulta DB-SQL
            SimuladorDto calculosSimulador = new SimuladorDto();
            
            String fechaDesembolso = TextoElemento(pestanasimuladorinternopage.FechaDesembolso);
            calculosSimulador = this.consultarCalculosSimuladorRetanqueo(Credito, Tasa, Plazo, DiasHabilesIntereses, calculoSoliPantalla, String.valueOf(TotalCarteras), fechaDesembolso);

            log.info("Tipo Calculos" + calculosSimulador.getTipoCalculos());
            log.info("Prima Seguro Anticipada" + calculosSimulador.getPrimaSeguroAnticipada());
            //log.info("Cuota Corriente" + calculosSimulador.getCuotaCorriente());
            //log.info("Gmf4X100" + calculosSimulador.getGmf4X100());
            log.info("Prima No Devengada" + calculosSimulador.getPrimaNoDevengada());
            log.info("Prima Neta" + calculosSimulador.getPrimaNeta());
            log.info("Suma Fianzas" + calculosSimulador.getSumaFianzas());
            log.info("Fianza Padre" + calculosSimulador.getFianzaPadre());
            log.info("fianza neta" + calculosSimulador.getFianzaNeta());
            log.info("Estudio Credito" + calculosSimulador.getEstudioCredito());
            //log.info("Saldo al Dia" + calculosSimulador.getSaldoAlDia());
            //log.info("Remanente Estimado" + calculosSimulador.getRemanenteEstimado());
            
            if (prima == "") {
                log.info("----------------- MENSUALIZADO -----------------------");

                ToleranciaPesoMensaje("######  SIM ANALISTA RETANQUEO - CALCULANDO Prima Anticipada de seguro ",
                        Integer.parseInt(TextoElemento(pestanasimuladorinternopage.PrimaAnticipadaSeguroAsesor)), calculosSimulador.getPrimaSeguroAnticipada());
                ToleranciaPesoMensaje("######  SIM ANALISTA RETANQUEO - CALCULANDO Estudio Credito IVA ",
                        Integer.parseInt(TextoElemento(pestanasimuladorinternopage.EstudioCreditoSAnalista)), calculosSimulador.getEstudioCredito());
                ToleranciaPesoMensaje("######  SIM ANALISTA RETANQUEO - CALCULANDO Comparación fianza total ",
                        Integer.parseInt(TextoElemento(pestanasimuladorinternopage.valorFianzaTotalAna)), calculosSimulador.getSumaFianzas());
                ToleranciaPesoMensaje("######  SIM ANALISTA RETANQUEO - CALCULANDO Comparación fianza padre ",
                        Integer.parseInt(TextoElemento(pestanasimuladorinternopage.valorFianzaPadreAna)), calculosSimulador.getFianzaPadre());
                ToleranciaPesoMensaje("######  SIM ANALISTA RETANQUEO - CALCULANDO Comparacion Fianza",
                        Integer.parseInt(TextoElemento(pestanasimuladorinternopage.ValorFianzaAnalista)), calculosSimulador.getFianzaNeta());

                //ToleranciaPesoMensaje("######  SIM ASESOR RETANQUEO - CALCULANDO Valor Desembolsar ",
                //		Integer.parseInt(TextoElemento(pestanasimuladorinternopage.SimuladorInternorValoraDesembolsar)),calculosSimulador.getRemanenteEstimado());


                //Variables globales - Retanqueo - Validaciones Cabecera Plan De Pagos - IF
                vg_PrimaSeguroAnticipada_Retanqueo = String.valueOf(calculosSimulador.getPrimaSeguroAnticipada());
            } else {
                log.info(" ---------------------- ANTICIPADO ----------------------------- ");

                ToleranciaPesoMensaje("######  SIM ANALISTA RETANQUEO - CALCULANDO Prima Anticipada de seguro ",
                        Integer.parseInt(TextoElemento(pestanasimuladorinternopage.PrimaAnticipadaSeguroAsesor)), calculosSimulador.getPrimaSeguroAnticipada());
                ToleranciaPesoMensaje("######  SIM ASESOR RETANQUEO - CALCULANDO Prima neta ",
                        Integer.parseInt(TextoElemento(pestanasimuladorinternopage.PrimaNetaSimuladorAnalista)), calculosSimulador.getPrimaNeta());
                ToleranciaPesoMensaje("######  SIM ASESOR RETANQUEO - CALCULANDO Prima No Devengada ",
                        Integer.parseInt(TextoElemento(pestanasimuladorinternopage.PrimaNoDevengadaSimuladorAnalista)), calculosSimulador.getPrimaNoDevengada());
                ToleranciaPesoMensaje("######  SIM ANALISTA RETANQUEO - CALCULANDO Estudio Credito IVA ",
                        Integer.parseInt(TextoElemento(pestanasimuladorinternopage.EstudioCreditoSAnalista)), calculosSimulador.getEstudioCredito());
                ToleranciaPesoMensaje("######  SIM ANALISTA RETANQUEO - CALCULANDO Comparación fianza total ",
                        Integer.parseInt(TextoElemento(pestanasimuladorinternopage.valorFianzaTotalAna)), calculosSimulador.getSumaFianzas());
                ToleranciaPesoMensaje("######  SIM ANALISTA RETANQUEO - CALCULANDO Comparación fianza padre ",
                        Integer.parseInt(TextoElemento(pestanasimuladorinternopage.valorFianzaPadreAna)), calculosSimulador.getFianzaPadre());
                ToleranciaPesoMensaje("######  SIM ANALISTA RETANQUEO - CALCULANDO Comparacion Fianza",
                        Integer.parseInt(TextoElemento(pestanasimuladorinternopage.ValorFianzaAnalista)), calculosSimulador.getFianzaNeta());
                //Variables globales - Retanqueo - Validaciones Cabecera Plan De Pagos - ELSE
                vg_PrimaSeguroAnticipada_Retanqueo = String.valueOf(calculosSimulador.getPrimaSeguroAnticipada());
                vg_PrimaNetaSeguro_Retanqueo = String.valueOf(calculosSimulador.getFianzaNeta());
                vg_PrimaNoDevengadaSeguro_Retanqueo = String.valueOf(calculosSimulador.getPrimaNoDevengada());


            }

            //Variables globales - Retanqueo - Validaciones Cabecera Plan De Pagos
            vg_MontoAprobado_Retanqueo = String.valueOf(calculoSoliPantalla);
            vg_SegundaTasaInteres_Retanqueo = String.valueOf(tasaDos * 100);
			
		} catch (Exception e) {
			log.error("########## Error - RetanqueoCreditos - ValidarSimuladorAnalistaRetanqueosCarteraSaneamiento() #######" + e);
            assertTrue("########## Error - RetanqueoCreditos- ValidarSimuladorAnalistaRetanqueosCarteraSaneamiento() ########" + e, false);
		}
        

    }

    public void DescargarMediosdedispercionRetanqueo(String Banco, String rutaPDF,String cedula,String tasa, String Credito, String Plazo, String DiasHabilesIntereses, String VlrCompraSaneamiento) throws InterruptedException, SQLException {
    	log.info("******************RetanqueoCreditos - DescargarMediosdedispercionRetanqueo()********** ");
    	try {
    		panelnavegacionaccion.CreditoParaDesembolsoDescargar();
            
            OriginacionCreditoQuery query = new OriginacionCreditoQuery();        
            String MontoSolicitar = "";
            ResultSet resultado = query.consultarMontoSolicitar(cedula);
            while (resultado.next()) {
            	MontoSolicitar = resultado.getString(1);
            }
            
            SimuladorDto calculosSimulador = new SimuladorDto();
            
            calculosSimulador = this.consultarCalculosSimuladorRetanqueo(Credito, tasa, Plazo, DiasHabilesIntereses, MontoSolicitar, VlrCompraSaneamiento,fechaActual);
        
            log.info("Remanente Estimado " + calculosSimulador.getRemanenteEstimado());  
                  
            esperaExplicita(PagesCreditosDesembolso.FiltroMonto);
            EscribirElemento(PagesCreditosDesembolso.FiltroMonto, String.valueOf(calculosSimulador.getRemanenteEstimado()-1));
            ElementVisible();
            Thread.sleep(2000);
            String pattern = "###,###,###.###";
            adjuntarCaptura("Filtrando monto");
            double value = Double.parseDouble(String.valueOf(calculosSimulador.getRemanenteEstimado()-1));     
            
            DecimalFormat myFormatter = new DecimalFormat(pattern);
            myFormatter = new DecimalFormat(pattern, DecimalFormatSymbols.getInstance(Locale.GERMANY));
            String output = myFormatter.format(value);
            esperaExplicita(By.xpath("//td[text()='" + output + "']"));
            hacerClick(PagesCreditosDesembolso.VerEditar);
            ElementVisible();
            hacerClick(PagesCreditosDesembolso.Banco);
            hacerClick(By.xpath("//li[starts-with(@id,'formLote:j_idt89') and contains(text(),'" + Banco + "' )]"));
            ElementVisible();
            cargarpdf(PagesCreditosDesembolso.CargarEvidencia, rutaPDF);
            esperaExplicita(PagesCreditosDesembolso.VerEvidencias);
            ElementVisible();
            hacerClick(PagesCreditosDesembolso.CrearArchivo);
            esperaExplicita(PagesCreditosDesembolso.ArchivoCreado);
            ElementVisible();
            adjuntarCaptura("Medios dispersion");
            hacerClick(PagesCreditosDesembolso.Guardar);
            ElementVisible();
		} catch (Exception e) {
			log.error("########## Error - RetanqueoCreditos - DescargarMediosdedispercionRetanqueo() #######" + e);
            assertTrue("########## Error - RetanqueoCreditos- DescargarMediosdedispercionRetanqueo() ########" + e, false);
		}
        
    }

	public void ValidarValoresLlamadoBienvenidaRetanqueo(String Credito, String Plazo, String DiasHabilesIntereses)
			throws NumberFormatException, SQLException, InterruptedException {

		log.info(
				"********Validar Valores Llamado Bienvenida Retanqueo, RetanqueoCreditos - ValidarValoresLlamadoBienvenidaRetanqueo()***********");
		try {
			recorerpestanas("CONDICIONES DEL CRÉDITO");

			ResultSet resultado;
			ValoresCredito = RetornarStringListWebElemen(pagesclienteparabienvenida.ValoresCondicionesCredito);

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

			// Consultar los conceptos para el cambio de tasa
			double EstudioCredito = 0;
			double TasaFianza = 0;
			int mesDos = 0;
			double tasaDos = 0;
			String Tasa = ValoresCredito.get(2);
			log.info("Tasa Creada " + Tasa);
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

			// consulta base de datos calculo de prima true o false
			String prima = "";
			ResultSet resultadoPrima = query.CalculoPrima(Credito);
			while (resultadoPrima.next()) {
				prima = resultadoPrima.getString(1);
			}
			System.out.println(" Variable prima: " + prima);
			// ################################
			String montoSolicitarPantalla = ValoresCredito.get(0);
			// DTO para almacenar consulta DB-SQL
			SimuladorDto calculosSimulador = new SimuladorDto();

			calculosSimulador = this.consultarCalculosSimuladorRetanqueo(Credito, Tasa, Plazo, DiasHabilesIntereses,
					montoSolicitarPantalla, ValoresCredito.get(7), fechaActual);

			// Valores para la funciones estaticos
			if (!ValidarElementoPresente(pagesclienteparabienvenida.ValorSaldoAlDia)) {
				int coma = GetText(pagesclienteparabienvenida.ValorSaldoAlDia).indexOf(",");
				if (coma == -1) {
					SaldoAlDia = Integer.parseInt(
							GetText(pagesclienteparabienvenida.ValorSaldoAlDia).replace(".", "").replace(",", "."));
					System.out.println(" Resultado de valor SALDO AL DIA IF " + SaldoAlDia);
				} else {
					SaldoAlDia = Integer.parseInt(
							GetText(pagesclienteparabienvenida.ValorSaldoAlDia).substring(0, coma).replace(".", ""));
					System.out.println(" Resultado de valor SALDO AL DIA ELSE " + SaldoAlDia);
				}
				ToleranciaPesoMensaje("######  SIM LLAMADO BIENVENIDA RETANQ - Saldo al dia, créditos a recoger  ",
						SaldoAlDia, calculosSimulador.getSaldoAlDia());// ##### Saldo al Dia
			} else if (!ValidarElementoPresente(pagesclienteparabienvenida.saldoAlDiaRetanqueo)) {
				SaldoAlDia = Integer.parseInt(
						GetText(pagesclienteparabienvenida.saldoAlDiaRetanqueo).replace(".", "").replace(",", "."));
				System.out.println(" Resultado de valor SALDO AL DIA RETANQUEO " + SaldoAlDia);
			}

			log.info("suma retanqueo y saldo al dia " + (SaldoAlDia + Integer.parseInt(ValoresCredito.get(12))));
			ToleranciaPesoMensaje("######  SIM LLAMADO BIENVENIDA RETANQ - CALCULANDO Prima Anticipada ",
					Integer.parseInt(ValoresCredito.get(9)), calculosSimulador.getPrimaSeguroAnticipada());// #####
																											// Prima
																											// Seguro
																											// Anticipada

			if (prima != "") {
				ToleranciaPesoMensaje("######  SIM LLAMADO BIENVENIDA RETANQ - CALCULANDO Prima neta",
						Integer.parseInt(ValoresCredito.get(11)), calculosSimulador.getPrimaNeta());// ##### Prima Neta
				ToleranciaPesoMensaje("######  SIM LLAMADO BIENVENIDA RETANQ - Prima neta no Devengada",
						Integer.parseInt(ValoresCredito.get(10)), calculosSimulador.getPrimaNoDevengada());// #### Prima
																											// No
																											// Devengada
			}

			ToleranciaPesoMensaje("######  SIM LLAMADO BIENVENIDA RETANQ -  CALCULANDO Pantalla GMF 4X1000 ",
					Integer.parseInt(ValoresCredito.get(8)), calculosSimulador.getGmf4X100());// ##### Gmf4X100
			ToleranciaPesoMensaje("######  SIM LLAMADO BIENVENIDA RETANQ -  CALCULANDO VALOR FIANZA TOTAL ########",
					Integer.parseInt(ValoresCredito.get(15)), calculosSimulador.getSumaFianzas());
			ToleranciaPesoMensaje("######  SIM LLAMADO BIENVENIDA RETANQ -  CALCULANDO VALOR FIANZA PADRE ########",
					Integer.parseInt(ValoresCredito.get(16)), calculosSimulador.getFianzaPadre());
			ToleranciaPesoMensaje("######  SIM LLAMADO BIENVENIDA RETANQ -  CALCULANDO VALOR FIANZA ########",
					Integer.parseInt(ValoresCredito.get(17)), calculosSimulador.getFianzaNeta());
			ToleranciaPesoMensaje("######  SIM LLAMADO BIENVENIDA RETANQ -# CALCULANDO ESTUDIO CREDITO ########",
					Integer.parseInt(ValoresCredito.get(19)), calculosSimulador.getEstudioCredito());
			adjuntarCaptura("simuladorAnalista");

//    ToleranciaPesoMensaje(" Valor Desembolsar ", Integer.parseInt(ValoresCredito.get(12)),
//            remantEstimado + Integer.parseInt(ValoresCredito.get(10)));
		} catch (Exception e) {
			log.error("########## Error - RetanqueoCreditos - ValidarValoresLlamadoBienvenidaRetanqueo() #######" + e);
            assertTrue("########## Error - RetanqueoCreditos- ValidarValoresLlamadoBienvenidaRetanqueo() ########" + e, false);
		}

	}

    //***************************************** 
    public void ValidarValoresLlamadoBienvenidaRetanqueoMultiple(String cedula, String pagaduria, String Tasa, String Plazo, String DiasHabilesIntereses, String VlrCompraSaneamiento)//Multiples Creditos
            throws NumberFormatException, SQLException, InterruptedException {
    	log.info("******** validando valores llamada de bienvenida, RetanqueoCreditos - ValidarValoresLlamadoBienvenidaRetanqueoMultiple()***********");
    	try {
    		recorerpestanas("CONDICIONES DEL CRÉDITO");

            ValoresCredito = RetornarStringListWebElemen(pagesclienteparabienvenida.ValoresCondicionesCredito);
            log.info("################# VALORES CAPTURADOS");
            log.info(ValoresCredito);

            // Valores para la funciones estaticos
            if (!ValidarElementoPresente(pagesclienteparabienvenida.ValorSaldoAlDia)) {
                int coma = GetText(pagesclienteparabienvenida.ValorSaldoAlDia).indexOf(",");
                if (coma == -1) {
                    SaldoAlDia = Integer.parseInt(
                            GetText(pagesclienteparabienvenida.ValorSaldoAlDia).replace(".", "").replace(",", "."));
                    System.out.println(" Resultado de valor SALDO AL DIA IF " + SaldoAlDia);
                } else {
                    SaldoAlDia = Integer.parseInt(
                            GetText(pagesclienteparabienvenida.ValorSaldoAlDia).substring(0, coma).replace(".", ""));
                    System.out.println(" Resultado de valor SALDO AL DIA ELSE " + SaldoAlDia);
                }
                int saldoRecoger = sumarListaValoresCreditos(pagesclienteparabienvenida.ListaCreditosRecoger);
                ToleranciaPesoMensaje(" Saldo al dia, créditos a recoger  ", SaldoAlDia, saldoRecoger);
            } else if (!ValidarElementoPresente(pagesclienteparabienvenida.saldoAlDiaRetanqueo)) {
                SaldoAlDia = Integer.parseInt(
                        GetText(pagesclienteparabienvenida.saldoAlDiaRetanqueo).replace(".", "").replace(",", "."));
                System.out.println(" Resultado de valor SALDO AL DIA RETANQUEO " + SaldoAlDia);
            }

            log.info("suma retanqueo y saldo al dia mas prima neta "
                    + (Integer.parseInt(ValoresCredito.get(12)) + SaldoAlDia + Integer.parseInt(ValoresCredito.get(11))));

            //**************Funcion SQL

            int montoSolicitarPantalla = Integer.parseInt(ValoresCredito.get(0));
            SimuladorDto calculosSimulador = new SimuladorDto();
            calculosSimulador = consultarCalculosSimuladorRetanqueoMultiple(cedula, pagaduria, Tasa, Plazo, DiasHabilesIntereses, montoSolicitarPantalla, VlrCompraSaneamiento, fechaActual);

            log.info("Tipo Calculos : " + calculosSimulador.getTipoCalculos());
            log.info("Prima Seguro Anticipada : " + calculosSimulador.getPrimaSeguroAnticipada());
            log.info("Cuota Corriente : " + calculosSimulador.getCuotaCorriente());
            log.info("Gmf4X1000 : " + calculosSimulador.getGmf4X100());
            log.info("Prima No Devengada : " + calculosSimulador.getPrimaNoDevengada());
            log.info("Prima Neta : " + calculosSimulador.getPrimaNeta());
            log.info("Suma Fianzas : " + calculosSimulador.getSumaFianzas());
            log.info("Fianza Padre : " + calculosSimulador.getFianzaPadre());
            log.info("fianza neta : " + calculosSimulador.getFianzaNeta());
            log.info("Estudio Credito : " + calculosSimulador.getEstudioCredito());
            log.info("Saldo al Dia : " + calculosSimulador.getSaldoAlDia());
            log.info("Remanente Estimado : " + calculosSimulador.getRemanenteEstimado());

    		ToleranciaPesoMensaje(" Prima Anticipada ", Integer.parseInt(ValoresCredito.get(9)),
    				calculosSimulador.getPrimaSeguroAnticipada());
    		// System.out.println("######## CALCULO DE PRIMA ######## " +
    		// PrimaAnticipadaSeguro + " "
    		// + ValoresCredito.get(13).isEmpty() + " " + DesPrimaAntic);

    		String tipoprima = calculosSimulador.getTipoCalculos();

    		if (tipoprima.equals("anticipado")) {
    			log.info("------------ ANTICIPADO ----------------");

    			ToleranciaPesoMensaje(" Prima neta", Integer.parseInt(ValoresCredito.get(11)),
    					calculosSimulador.getPrimaNeta());
    			ToleranciaPesoMensaje(" Prima neta no Devengada", Integer.parseInt(ValoresCredito.get(10)),
    					calculosSimulador.getPrimaNoDevengada());
    		} else {
    			log.info("----------- MENSUALIZADO ---------------");

    		}

    		ToleranciaPesoMensaje("Pantalla GMF 4X1000 ", Integer.parseInt(ValoresCredito.get(8)),
    				calculosSimulador.getGmf4X100());
    		ToleranciaPesoMensaje("######  CALCULANDO VALOR FIANZA ########", Integer.parseInt(ValoresCredito.get(15)),
    				calculosSimulador.getSumaFianzas());
    		ToleranciaPesoMensaje("###### CALCULANDO ESTUDIO CREDITO ########", Integer.parseInt(ValoresCredito.get(19)),
    				calculosSimulador.getEstudioCredito());

    		/*
    		 * ToleranciaPesoMensaje(" Valor Desembolsar ",
    		 * Integer.parseInt(ValoresCredito.get(13)),
    		 * calculosSimulador.getRemanenteEstimado() +
    		 * Integer.parseInt(ValoresCredito.get(11)));
    		 */	
		} catch (Exception e) {
			 log.error("########## Error - RetanqueoCreditos - ValidarValoresLlamadoBienvenidaRetanqueoMultiple() #######" + e);
	         assertTrue("########## Error - RetanqueoCreditos- ValidarValoresLlamadoBienvenidaRetanqueoMultiple() ########" + e, false);
		}
        
	}

    public void AprobarExcepciones(String Pdf, String Cedula) throws InterruptedException {
        ElementVisible();
        hacerClick(pestanasimuladorinternopage.DetalleExcepciones);
        ElementVisible();
        Thread.sleep(3000);
        esperaExplicita(pestanasimuladorinternopage.SolicitarAprobacion);
        cargarpdf(pestanasimuladorinternopage.SoportePdfExcepciones, Pdf);
        esperaExplicita(pestanasimuladorinternopage.Notificacion);
        hacerClicknotificacion();
        hacerClick(pestanasimuladorinternopage.SolicitarAprobacion);
        esperaExplicita(pestanasimuladorinternopage.Notificacion);
        String notificacion = GetText(pestanasimuladorinternopage.Notificacion);
        System.out.println(" ------------------- print después del if ---------------------- ");
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
            Hacer_scroll_centrado(pagestareas.Aprobar);
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

    /************ FIN RETANQUEO CREDITOS **********/

    public void validarLasCondicionesDeLaCartaDeNotificacionDeCreditos(String cedula) {
    	log.info("******************RetanqueoCreditos - validarLasCondicionesDeLaCartaDeNotificacionDeCreditos()********** ");
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
                if (entry.getKey().equals("Valor prima de seguro anticipada")) {
                    ToleranciaPesoMensaje("Valor prima de seguro anticipada ", Integer.parseInt(entry.getValue()),
                            Integer.parseInt(ValoresCredito.get(10)));
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
                            Integer.parseInt(entry.getValue()), Integer.parseInt(ValoresCredito.get(16)));
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
                            Integer.parseInt(ValoresCredito.get(9)));
                }

                if (entry.getKey().equals("Valor total a desembolsar")) {
                    ToleranciaPesoMensaje("Valor total a desembolsar (REMANENTE) ", Integer.parseInt(entry.getValue()),
                            Integer.parseInt(ValoresCredito.get(13)));
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

        for (String buscarTexto : buscarTextos) {
            BuscarTextoPage(buscarTexto);
        }
    }

    public void listarCreditosPadre(By locator) {
        capturarCreditosPadre(locator, listaCreditosPadre);
    }

    public void ValidarSimuladorRetanqueoMultiple(String cedula, String pagaduria, String Ingresos, String descLey, String descNomin, String Tasa, String Plazo,
                                                  String DiasHabilesIntereses, String VlrCompraSaneamiento)
            throws NumberFormatException, SQLException {
        log.info("********Validar Simulador interno RETANQUEO MULTIPLE, RetanqueoCreditos - ValidarSimuladorRetanqueoMultiple()***********");
        try {
        	String numCredito = listaCreditosPadre.size() > 0 ? listaCreditosPadre.get(1).get("numeroCredito") : "";

            // consulta base de datos descuento prima anticipada
            OriginacionCreditoQuery query = new OriginacionCreditoQuery();
            ResultSet resultado = query.ConsultaDescuentoPrimaAntic();
            while (resultado.next()) {
                DesPrimaAntic = Integer.parseInt(resultado.getString(1));
            }

            // consulta para validar prima menor a 24 meses
            if (Integer.parseInt(Plazo) < DesPrimaAntic) {
                int periodoGracia = (int) Math.ceil((double) Integer.parseInt(DiasHabilesIntereses) / 30);
                DesPrimaAntic = periodoGracia + Integer.parseInt(Plazo);
            }
            log.info("********* Valor de prima " + DesPrimaAntic);

            int montoSolicitarPantalla = Integer.parseInt(TextoElemento(pestanasimuladorinternopage.ResultMontoSoli));
            SimuladorDto calculosSimulador = new SimuladorDto();
            calculosSimulador = consultarCalculosSimuladorRetanqueoMultiple(cedula, pagaduria, Tasa, Plazo, DiasHabilesIntereses, montoSolicitarPantalla, VlrCompraSaneamiento, fechaActual);

            log.info("Tipo Calculos : " + calculosSimulador.getTipoCalculos());
            log.info("Prima Seguro Anticipada : " + calculosSimulador.getPrimaSeguroAnticipada());
            log.info("Cuota Corriente : " + calculosSimulador.getCuotaCorriente());
            log.info("Gmf4X1000 : " + calculosSimulador.getGmf4X100());
            log.info("Prima No Devengada : " + calculosSimulador.getPrimaNoDevengada());
            log.info("Prima Neta : " + calculosSimulador.getPrimaNeta());
            log.info("Suma Fianzas : " + calculosSimulador.getSumaFianzas());
            log.info("Fianza Padre : " + calculosSimulador.getFianzaPadre());
            log.info("fianza neta : " + calculosSimulador.getFianzaNeta());
            log.info("Estudio Credito : " + calculosSimulador.getEstudioCredito());
            log.info("Saldo al Dia : " + calculosSimulador.getSaldoAlDia());
            log.info("Remanente Estimado : " + calculosSimulador.getRemanenteEstimado());

            double tasaUno = Double.parseDouble(Tasa) / 100;
            /* Se cambia el valor por el de la pantalla */
            int calculoMontoSoli = montoSolicitarPantalla;
            if (listaCreditosPadre.size() > 1) {
                for (Map.Entry<Integer, Map<String, String>> entry : listaCreditosPadre.entrySet()) {
                    consultarDatosCreditosPadre(entry.getValue());
                }
            }

            System.out.println("------ lista creditos padre - ValidarSimuladorRetanqueoMultiple() -----" + listaCreditosPadre.toString());

            ToleranciaPesoMensaje("****** SIM INTERNO - Prima Anticipada de seguro ******",
                    Integer.parseInt(TextoElemento(pestanasimuladorinternopage.PrimaAnticipadaSeguroSInterno)),
                    calculosSimulador.getPrimaSeguroAnticipada());
            ToleranciaPesoMensaje("****** SIM INTERNO - Prima No Devengada ******",
                    Integer.parseInt(TextoElemento(pestanasimuladorinternopage.PrimaNoDevengadaSimuladorInterno)),
                    calculosSimulador.getPrimaNoDevengada());
            ToleranciaPesoMensaje("****** SIM INTERNO - Prima neta ******", Integer.parseInt(TextoElemento(pestanasimuladorinternopage.PrimaNeta)),
                    calculosSimulador.getPrimaNeta());
            ToleranciaPesoMensaje("****** SIM INTERNO - Cuota corriente ******",
                    Integer.parseInt(TextoElemento(pestanasimuladorinternopage.CuotaCorriente)),
                    calculosSimulador.getCuotaCorriente());
            ToleranciaPesoMensaje("****** SIM INTERNO - Estudio Credito IVA ******",
                    Integer.parseInt(TextoElemento(pestanasimuladorinternopage.EstudioCreditoIVA)),
                    calculosSimulador.getEstudioCredito());
            ToleranciaPesoMensaje("****** SIM INTERNO - CALCULANDO Comparación fianza total ******",
                    Integer.parseInt(TextoElemento(pestanasimuladorinternopage.valorFianzaTotal)), calculosSimulador.getSumaFianzas());
            ToleranciaPesoMensaje("****** SIM INTERNO - CALCULANDO Comparación fianza padre ******",
                    Integer.parseInt(TextoElemento(pestanasimuladorinternopage.valorFianzaPadre)), calculosSimulador.getFianzaPadre());
            ToleranciaPesoMensaje("****** SIM INTERNO - CALCULANDO Comparacion Fianza ******",
                    Integer.parseInt(TextoElemento(pestanasimuladorinternopage.ValorFianza)), calculosSimulador.getFianzaNeta());
            ToleranciaPesoMensaje("****** SIM INTERNO - Pantalla Gmf 4x1000 ******",
                    Integer.parseInt(TextoElemento(pestanasimuladorinternopage.Gmf4100)), calculosSimulador.getGmf4X100());
    		/*ToleranciaPesoMensaje("****** SIM INTERNO - Valor Desembolsar ******",
    				Integer.parseInt(TextoElemento(pestanasimuladorinternopage.SimuladorInternorValoraDesembolsar)),
    				calculosSimulador.getRemanenteEstimado());*/

            this.limpiarCreditosPadre();
		} catch (Exception e) {
			log.error("########## Error - RetanqueoCreditos - ValidarSimuladorRetanqueoMultiple() #######" + e);
            assertTrue("########## Error - RetanqueoCreditos- ValidarSimuladorRetanqueoMultiple() ########" + e, false);
		}
        
    }

    public void limpiarCreditosPadre() {
        if (listaCreditosPadre.size() > 1) {
            for (Map.Entry<Integer, Map<String, String>> entry : listaCreditosPadre.entrySet()) {
                for (Map.Entry<String, String> credito : entry.getValue().entrySet()) {
                    if (!credito.getKey().equals("numeroCredito")) {
                        credito.setValue("0");
                    }
                }
            }
        }
    }

    public void consultarDatosCreditosPadre(Map<String, String> credito) throws SQLException {
        int primaPadre = 0;
        OriginacionCreditoQuery query = new OriginacionCreditoQuery();
        ResultSet resultado = query.ValorPrimaCreditoPadre(credito.get("numeroCredito"));
        while (resultado.next()) {
            primaPadre = Integer.parseInt(resultado.getString(1));
            credito.put("primaPadre", String.valueOf(primaPadre));
        }

        int montoPadre = 0;
        resultado = query.ValorMontoCreditoPadre(credito.get("numeroCredito"));
        while (resultado.next()) {
            montoPadre = Integer.parseInt(resultado.getString(1));
            credito.put("montoPadre", String.valueOf(montoPadre));
        }

        int mesesActivoPadre = 0;
        resultado = query.MesesActivoPadre(credito.get("numeroCredito"));
        while (resultado.next()) {
            mesesActivoPadre = Integer.parseInt(resultado.getString(1));
            credito.put("mesesActivoPadre", String.valueOf(mesesActivoPadre));
        }

        int estudioCreditoPadre = 0;
        resultado = query.consultaEstudioCreditoPadre(credito.get("numeroCredito"));
        while (resultado.next()) {
            estudioCreditoPadre = Integer.parseInt(resultado.getString(1));
            credito.put("estudioCreditoPadre", String.valueOf(estudioCreditoPadre));
        }

        int fianzaPadre = 0;
        resultado = query.consultaFianzaCreditoPadre(credito.get("numeroCredito"));
        while (resultado.next()) {
            fianzaPadre = Integer.parseInt(resultado.getString(1));
            credito.put("fianzaPadre", String.valueOf(fianzaPadre));
        }

        int primaNoDevengada = (int) PrimaNoDevengadaCPadre(primaPadre, montoPadre, mesesActivoPadre, 0,
                1000000, Tasaxmillonseguro, DesPrimaAntic);
        credito.put("primaNoDevengada", String.valueOf(primaNoDevengada));
    }

    public Integer returnValuesCredits(String key) {
        int value = 0;
        for (Map.Entry<Integer, Map<String, String>> entry : listaCreditosPadre.entrySet()) {
            for (Map.Entry<String, String> credito : entry.getValue().entrySet()) {
                if (credito.getKey().equals(key)) {
                    value += Integer.parseInt(credito.getValue());
                }
            }
        }
        return value;
    }

    public void validarSimuladorAnalistaRetanqueosMultiple(String cedula,
                                                           String pagaduria,
                                                           String DiasHabilesIntereses,
                                                           String anno,
                                                           String retanqueo,
                                                           String fecha,
                                                           String Mes,
                                                           String Plazo,
                                                           String Tasa,
                                                           String VlrCompraSaneamiento) throws InterruptedException, SQLException {
    	
    	navegarSimuladoranalistaRetanqueo(fecha, Mes, anno);
    	log.info(" **** validando simulador analista Retanq Multiple, RetanqueoCreditos - validarSimuladorAnalistaRetanqueosMultiple() ****");
    	try {
    		OriginacionCreditoQuery query = new OriginacionCreditoQuery();

            // consulta base de datos descuento prima anticipada
            ResultSet resultado = query.ConsultaDescuentoPrimaAntic();
            while (resultado.next()) {
                DesPrimaAntic = Integer.parseInt(resultado.getString(1));
            }
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

            log.info("Tasa Estudio Credito " + EstudioCredito);
            log.info("Tasa Fianza " + TasaFianza);
            log.info("Valor mes Dos " + mesDos);
            log.info("Tasa Dos" + tasaDos);

            // consulta para validar prima menor a 24 meses
            if (Integer.parseInt(Plazo) < DesPrimaAntic) {
                int periodoGracia = (int) Math.ceil((double) Integer
                        .parseInt(TextoElemento(pestanasimuladorinternopage.InteresesInicialesSimuladorAnalista)) / 30);
                DesPrimaAntic = periodoGracia + Integer.parseInt(Plazo);
            }
            vg_CuotasPrimaSeguroAnticipada = String.valueOf(DesPrimaAntic);

            log.info("********* Valor de prima " + DesPrimaAntic);//*****************

            //int calculoSoliPantalla = Integer.parseInt(TextoElemento(pestanasimuladorinternopage.CapitalTotal));
            int montoSolicitarPantalla = Integer.parseInt(TextoElemento(pestanasimuladorinternopage.CapitalTotal));

            SimuladorDto calculosSimulador = new SimuladorDto();
            String fechaDesembolso = TextoElemento(pestanasimuladorinternopage.FechaDesembolso);
            calculosSimulador = consultarCalculosSimuladorRetanqueoMultiple(cedula, pagaduria, Tasa, Plazo, DiasHabilesIntereses, montoSolicitarPantalla, VlrCompraSaneamiento, fechaDesembolso);

            log.info("Tipo Calculos : " + calculosSimulador.getTipoCalculos());
            log.info("Prima Seguro Anticipada : " + calculosSimulador.getPrimaSeguroAnticipada());
            log.info("Cuota Corriente : " + calculosSimulador.getCuotaCorriente());
            log.info("Gmf4X1000 : " + calculosSimulador.getGmf4X100());
            log.info("Prima No Devengada : " + calculosSimulador.getPrimaNoDevengada());
            log.info("Prima Neta : " + calculosSimulador.getPrimaNeta());
            log.info("Suma Fianzas : " + calculosSimulador.getSumaFianzas());
            log.info("Fianza Padre : " + calculosSimulador.getFianzaPadre());
            log.info("fianza neta : " + calculosSimulador.getFianzaNeta());
            log.info("Estudio Credito : " + calculosSimulador.getEstudioCredito());
            log.info("Saldo al Dia : " + calculosSimulador.getSaldoAlDia());
            log.info("Remanente Estimado : " + calculosSimulador.getRemanenteEstimado());

            // consulta base de datos calculo de prima true o false
            String tipoprima = calculosSimulador.getTipoCalculos();
            System.out.println(" Variable prima: " + tipoprima);
            // Llenado del mapa con la lista de los creditos padre
            if (listaCreditosPadre.size() > 1) {
                for (Map.Entry<Integer, Map<String, String>> entry : listaCreditosPadre.entrySet()) {
                    consultarDatosCreditosPadre(entry.getValue());
                }
            }

            System.out.println("------ lista creditos padre - ValidarSimuladorRetanqueoMultiple() -----" + listaCreditosPadre.toString());

            if (tipoprima.equals("mensualizado")) {
                System.out.println("-------------------- MENSUALIZADO -------------------------");

                int calculoMontoSoli = montoSolicitarPantalla;

                int PrimaAnticipadaSeguro = calculosSimulador.getPrimaSeguroAnticipada();

                ToleranciaPesoMensaje(" Prima anticipada de seguro ",
                        Integer.parseInt(TextoElemento(pestanasimuladorinternopage.PrimaAnticipadaSeguroAsesor)),
                        calculosSimulador.getPrimaSeguroAnticipada());

                ToleranciaPesoMensaje(" Estudio Credito IVA",
                        Integer.parseInt(TextoElemento(pestanasimuladorinternopage.EstudioCreditoSAnalista)),
                        calculosSimulador.getEstudioCredito());

                ToleranciaPesoMensaje(" Valor fianza ",
                        Integer.parseInt(TextoElemento(pestanasimuladorinternopage.ValorFianzaAnalista)), calculosSimulador.getFianzaNeta());

                vg_MontoAprobado_Retanqueo = String.valueOf(calculoMontoSoli);
                vg_PrimaSeguroAnticipada_Retanqueo = String.valueOf(PrimaAnticipadaSeguro);
            } else {
                System.out.println("-------------------- ANTICIPADO -------------------------");

                int calculoMontoSoli = montoSolicitarPantalla;

                int PrimaAnticipadaSeguro = calculosSimulador.getPrimaSeguroAnticipada();

                ToleranciaPesoMensaje(" Prima anticipada de seguro ",
                        Integer.parseInt(TextoElemento(pestanasimuladorinternopage.PrimaAnticipadaSeguroAsesor)),
                        calculosSimulador.getPrimaSeguroAnticipada());

                int PrimaNeta = calculosSimulador.getPrimaNeta();
                ToleranciaPesoMensaje(" Prima neta",
                        Integer.parseInt(TextoElemento(pestanasimuladorinternopage.PrimaNetaSimuladorAnalista)), calculosSimulador.getPrimaNeta());

                int primaNoDevengada = calculosSimulador.getPrimaNoDevengada();
                ToleranciaPesoMensaje(" Prima neta No devengada",
                        Integer.parseInt(TextoElemento(pestanasimuladorinternopage.PrimaNoDevengadaSimuladorAnalista)),
                        calculosSimulador.getPrimaNoDevengada());


                ToleranciaPesoMensaje(" Estudio Credito IVA",
                        Integer.parseInt(TextoElemento(pestanasimuladorinternopage.EstudioCreditoSAnalista)),
                        calculosSimulador.getEstudioCredito());


                ToleranciaPesoMensaje(" Valor fianza ",
                        Integer.parseInt(TextoElemento(pestanasimuladorinternopage.ValorFianzaAnalista)), calculosSimulador.getFianzaNeta());

                //Variables globales - RetanqueoMultiple - Validaciones Cabecera Plan De Pagos - ELSE (prima == "")
                vg_MontoAprobado_Retanqueo = String.valueOf(calculoMontoSoli);
                vg_PrimaSeguroAnticipada_Retanqueo = String.valueOf(PrimaAnticipadaSeguro);
                vg_PrimaNetaSeguro_Retanqueo = String.valueOf(PrimaNeta);
                vg_PrimaNoDevengadaSeguro_Retanqueo = String.valueOf(primaNoDevengada);
            }
            //Variables globales - RetanqueoMultiple - Validaciones Cabecera Plan De Pagos - Generales
            vg_SegundaTasaInteres_Retanqueo = String.valueOf(tasaDos * 100);
            this.limpiarCreditosPadre();

		} catch (Exception e) {
			log.error("########## Error - RetanqueoCreditos - validarSimuladorAnalistaRetanqueosMultiple() #######" + e);
            assertTrue("########## Error - RetanqueoCreditos - validarSimuladorAnalistaRetanqueosMultiple() ########" + e, false);
		}
    }

    /*ThainerPerez V1.2  23/Marzo/2022 :  1. Modificar el metodo para que se ejecute con la funcion de retanqueo Multiple
     * 									  2. Se eliminan los metodos que apuntaban a base Test
     * 									  3. se agregan los valores (cedula, pagaduria y diashabilesIntereses)*/
    public void validarSimuladorAnalistaRetanqueosCCS(String cedula,String pagaduria,String DiasHabilesIntereses, 
    												  String anno, String retanqueo,
                                                      String fecha, String Mes, String Plazo, String Cartera,
                                                      String Saneamiento, String Tasa) throws InterruptedException, SQLException {
        
    	navegarSimuladoranalistaRetanqueo(fecha, Mes, anno);
    	log.info("******************RetanqueoCreditos - validarSimuladorAnalistaRetanqueosCCS()********** ");
    	try {
    		int TotalCarteras = (Integer.parseInt(Cartera) + Integer.parseInt(Saneamiento));

            OriginacionCreditoQuery query = new OriginacionCreditoQuery();

            // consulta base de datos descuento prima anticipada
            ResultSet resultado = query.ConsultaDescuentoPrimaAntic();
            while (resultado.next()) {
                DesPrimaAntic = Integer.parseInt(resultado.getString(1));
            }
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

            log.info("Tasa Estudio Credito " + EstudioCredito);
            log.info("Tasa Fianza " + TasaFianza);
            log.info("Valor mes Dos " + mesDos);
            log.info("Tasa Dos" + tasaDos);

            // consulta para validar prima menor a 24 meses
            if (Integer.parseInt(Plazo) < DesPrimaAntic) {
                int periodoGracia = (int) Math.ceil((double) Integer
                        .parseInt(TextoElemento(pestanasimuladorinternopage.InteresesInicialesSimuladorAnalista)) / 30);
                DesPrimaAntic = periodoGracia + Integer.parseInt(Plazo);
            }
            vg_CuotasPrimaSeguroAnticipada = String.valueOf(DesPrimaAntic);

            log.info("********* Valor de prima " + DesPrimaAntic);//*****************

            //int calculoSoliPantalla = Integer.parseInt(TextoElemento(pestanasimuladorinternopage.CapitalTotal));
            int montoSolicitarPantalla = Integer.parseInt(TextoElemento(pestanasimuladorinternopage.CapitalTotal));

            SimuladorDto calculosSimulador = new SimuladorDto();
            String fechaDesembolso = TextoElemento(pestanasimuladorinternopage.FechaDesembolso);
            calculosSimulador = consultarCalculosSimuladorRetanqueoMultiple(cedula, pagaduria, Tasa, Plazo, DiasHabilesIntereses, montoSolicitarPantalla, String.valueOf(TotalCarteras), fechaDesembolso);

            log.info("Tipo Calculos : " + calculosSimulador.getTipoCalculos());
            log.info("Prima Seguro Anticipada : " + calculosSimulador.getPrimaSeguroAnticipada());
            log.info("Cuota Corriente : " + calculosSimulador.getCuotaCorriente());
            log.info("Gmf4X1000 : " + calculosSimulador.getGmf4X100());
            log.info("Prima No Devengada : " + calculosSimulador.getPrimaNoDevengada());
            log.info("Prima Neta : " + calculosSimulador.getPrimaNeta());
            log.info("Suma Fianzas : " + calculosSimulador.getSumaFianzas());
            log.info("Fianza Padre : " + calculosSimulador.getFianzaPadre());
            log.info("fianza neta : " + calculosSimulador.getFianzaNeta());
            log.info("Estudio Credito : " + calculosSimulador.getEstudioCredito());
            log.info("Saldo al Dia : " + calculosSimulador.getSaldoAlDia());
            log.info("Remanente Estimado : " + calculosSimulador.getRemanenteEstimado());

            // consulta base de datos calculo de prima true o false
            String tipoprima = calculosSimulador.getTipoCalculos();
            System.out.println(" Variable prima: " + tipoprima);
            // Llenado del mapa con la lista de los creditos padre
            if (listaCreditosPadre.size() > 1) {
                for (Map.Entry<Integer, Map<String, String>> entry : listaCreditosPadre.entrySet()) {
                    consultarDatosCreditosPadre(entry.getValue());
                }
            }

            System.out.println("------ lista creditos padre - ValidarSimuladorRetanqueoMultiple() -----" + listaCreditosPadre.toString());

            if (tipoprima.equals("mensualizado")) {
                System.out.println("-------------------- MENSUALIZADO -------------------------");

                int calculoMontoSoli = montoSolicitarPantalla;

                int PrimaAnticipadaSeguro = calculosSimulador.getPrimaSeguroAnticipada();

                ToleranciaPesoMensaje(" Prima anticipada de seguro ",
                        Integer.parseInt(TextoElemento(pestanasimuladorinternopage.PrimaAnticipadaSeguroAsesor)),
                        calculosSimulador.getPrimaSeguroAnticipada());

                ToleranciaPesoMensaje(" Estudio Credito IVA",
                        Integer.parseInt(TextoElemento(pestanasimuladorinternopage.EstudioCreditoSAnalista)),
                        calculosSimulador.getEstudioCredito());

                ToleranciaPesoMensaje(" Valor fianza ",
                        Integer.parseInt(TextoElemento(pestanasimuladorinternopage.ValorFianzaAnalista)), calculosSimulador.getFianzaNeta());

                vg_MontoAprobado_Retanqueo = String.valueOf(calculoMontoSoli);
                vg_PrimaSeguroAnticipada_Retanqueo = String.valueOf(PrimaAnticipadaSeguro);
            } else {
                System.out.println("-------------------- ANTICIPADO -------------------------");

                int calculoMontoSoli = montoSolicitarPantalla;

                int PrimaAnticipadaSeguro = calculosSimulador.getPrimaSeguroAnticipada();

                ToleranciaPesoMensaje(" Prima anticipada de seguro ",
                        Integer.parseInt(TextoElemento(pestanasimuladorinternopage.PrimaAnticipadaSeguroAsesor)),
                        calculosSimulador.getPrimaSeguroAnticipada());

                int PrimaNeta = calculosSimulador.getPrimaNeta();
                ToleranciaPesoMensaje(" Prima neta",
                        Integer.parseInt(TextoElemento(pestanasimuladorinternopage.PrimaNetaSimuladorAnalista)), calculosSimulador.getPrimaNeta());

                int primaNoDevengada = calculosSimulador.getPrimaNoDevengada();
                ToleranciaPesoMensaje(" Prima neta No devengada",
                        Integer.parseInt(TextoElemento(pestanasimuladorinternopage.PrimaNoDevengadaSimuladorAnalista)),
                        calculosSimulador.getPrimaNoDevengada());


                ToleranciaPesoMensaje(" Estudio Credito IVA",
                        Integer.parseInt(TextoElemento(pestanasimuladorinternopage.EstudioCreditoSAnalista)),
                        calculosSimulador.getEstudioCredito());


                ToleranciaPesoMensaje(" Valor fianza ",
                        Integer.parseInt(TextoElemento(pestanasimuladorinternopage.ValorFianzaAnalista)), calculosSimulador.getFianzaNeta());

                //Variables globales - RetanqueoMultiple - Validaciones Cabecera Plan De Pagos - ELSE (prima == "")
                vg_MontoAprobado_Retanqueo = String.valueOf(calculoMontoSoli);
                vg_PrimaSeguroAnticipada_Retanqueo = String.valueOf(PrimaAnticipadaSeguro);
                vg_PrimaNetaSeguro_Retanqueo = String.valueOf(PrimaNeta);
                vg_PrimaNoDevengadaSeguro_Retanqueo = String.valueOf(primaNoDevengada);
            }
            //Variables globales - RetanqueoMultiple - Validaciones Cabecera Plan De Pagos - Generales
            vg_SegundaTasaInteres_Retanqueo = String.valueOf(tasaDos * 100);
            this.limpiarCreditosPadre();	
		} catch (Exception e) {
			log.error("########## Error - RetanqueoCreditos - validarSimuladorAnalistaRetanqueosCCS() #######" + e);
            assertTrue("########## Error - RetanqueoCreditos- validarSimuladorAnalistaRetanqueosCCS() ########" + e, false);
		}
        

    }

    /* * Jonathan Varon V1.0 - 20/Dic/2021, 1. Se implementa la funcion de calculos directamente desde la base de datos
	 * 									2. Parametros utilizados: (creditopadre numeric,
																	tasa numeric,
																	plazo numeric,
																	diashabilesintereses numeric,
																	monto numeric,
																	sumamontocarteras numeric)*/
    public SimuladorDto consultarCalculosSimuladorRetanqueo(String Credito, String Tasa, String Plazo, String DiasHabilesIntereses, String Monto, String VlrCompraSaneamiento, String fecha) {

        log.info("****** Calculando valores simulador Retanqueo por funcion SQL, RetanqueoCreditos -  consultarCalculosSimuladorRetanqueo()*******");

        SimuladorDto resultSimulador = new SimuladorDto();
        OriginacionCreditoQuery query = new OriginacionCreditoQuery();

        ResultSet r = null;
        try {
            r = query.consultarCalculosSimuladorRetanqueo(Credito, Tasa, Plazo, DiasHabilesIntereses, Monto, VlrCompraSaneamiento, fecha);
            while (r.next()) {

                resultSimulador.setTipoCalculos(r.getString(1));
                resultSimulador.setPrimaSeguroAnticipada(r.getInt(2));
                resultSimulador.setCuotaCorriente(r.getInt(3));
                resultSimulador.setGmf4X100(r.getInt(4));
                resultSimulador.setPrimaNoDevengada(r.getInt(5));
                resultSimulador.setPrimaNeta(r.getInt(6));
                resultSimulador.setSumaFianzas(r.getInt(7));
                resultSimulador.setFianzaPadre(r.getInt(8));
                resultSimulador.setFianzaNeta(r.getInt(9));
                resultSimulador.setEstudioCredito(r.getInt(10));
                resultSimulador.setSaldoAlDia(r.getInt(11));
                resultSimulador.setRemanenteEstimado(r.getInt(12));
            }
        } catch (Exception e) {
            log.error("########## Error - RetanqueoCreditos - consultarCalculosSimulador() #######" + e);
            assertTrue("########## Error - RetanqueoCreditos - consultarCalculosSimulador()########" + e,
                    false);
        }

        return resultSimulador;

    }

    public SimuladorDto consultarCalculosSimuladorRetanqueoMultiple(String cedula, String pagaduria, String tasa, String plazo, String diasIntIniciales, int monto, String compraCarteraSuma, String fecha) {

        log.info("****** Calculando valores simulador Retanqueo por funcion SQL, RetanqueoCreditos -  consultarCalculosSimuladorRetanqueo()*******");

        SimuladorDto resultSimulador = new SimuladorDto();
        OriginacionCreditoQuery query = new OriginacionCreditoQuery();

        ResultSet r = null;
        try {
            r = query.consultarCalculosSimuladorRetanqueoMultiple(cedula, pagaduria, tasa, plazo, diasIntIniciales, monto, compraCarteraSuma, fecha);
            while (r.next()) {

                resultSimulador.setTipoCalculos(r.getString(1));
                resultSimulador.setPrimaSeguroAnticipada(r.getInt(2));
                resultSimulador.setCuotaCorriente(r.getInt(3));
                resultSimulador.setGmf4X100(r.getInt(4));
                resultSimulador.setPrimaNoDevengada(r.getInt(5));
                resultSimulador.setPrimaNeta(r.getInt(6));
                resultSimulador.setSumaFianzas(r.getInt(7));
                resultSimulador.setFianzaPadre(r.getInt(8));
                resultSimulador.setFianzaNeta(r.getInt(9));
                resultSimulador.setEstudioCredito(r.getInt(10));
                resultSimulador.setSaldoAlDia(r.getInt(11));
                resultSimulador.setRemanenteEstimado(r.getInt(12));
            }
        } catch (Exception e) {
            log.error("########## Error - RetanqueoCreditos - consultarCalculosSimulador() #######" + e);
            assertTrue("########## Error - RetanqueoCreditos - consultarCalculosSimulador()########" + e,
                    false);
        }

        return resultSimulador;

    }

    public void validarEstadoCreditoPadre(String Credito, String FechaRegistro)
            throws InterruptedException, SQLException {
    	log.info("******************RetanqueoCreditos - validarEstadoCreditoPadre()********** ");
    	try {
    		 // consulta base de datos estado del credito padre true o false
            Boolean estado = null;
            OriginacionCreditoQuery query = new OriginacionCreditoQuery();
            ResultSet resultado = query.ConsultaEstadoCredito(Credito, FechaRegistro);
            while (resultado.next()) {
                estado = resultado.getBoolean(1);
            }
            assertTrue(" El capital amortizado no coincide con el saldo a capital para el credito con radicado #" + Credito,
                    estado);
		} catch (Exception e) {
			log.error("########## Error - RetanqueoCreditos - validarEstadoCreditoPadre() #######" + e);
            assertTrue("########## Error - RetanqueoCreditos- validarEstadoCreditoPadre() ########" + e, false);
		}
       
    }

	public void validarEstadoCreditoPadreMultiple(String FechaRegistro) throws InterruptedException, SQLException {
		// consulta base de datos estado del credito padre true o false
		log.info("******************RetanqueoCreditos - validarEstadoCreditoPadreMultiple()********** ");
		try {
			Boolean estado = null;
			OriginacionCreditoQuery query = new OriginacionCreditoQuery();

			int value = 0;
			for (Map.Entry<Integer, Map<String, String>> entry : listaCreditosPadre.entrySet()) {
				for (Map.Entry<String, String> credito : entry.getValue().entrySet()) {

					if (credito.getKey().equals("numeroCredito")) {
						ResultSet resultado = query.ConsultaEstadoCredito(credito.getValue(), FechaRegistro);
						while (resultado.next()) {
							estado = resultado.getBoolean(1);
						}
						assertTrue(
								" El capital amortizado no coincide con el saldo a capital para el credito con radicado #"
										+ credito.getValue(),
								estado);
					}

				}
			}
		} catch (Exception e) {
			log.error("########## Error - RetanqueoCreditos - validarEstadoCreditoPadreMultiple() #######" + e);
			assertTrue("########## Error - RetanqueoCreditos- validarEstadoCreditoPadreMultiple() ########" + e, false);
		}

	}
}
