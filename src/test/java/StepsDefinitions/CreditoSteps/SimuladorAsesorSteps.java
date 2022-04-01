package StepsDefinitions.CreditoSteps;

import Acciones.ComunesAccion.LoginAccion;
import Acciones.CreditoAccion.OriginacionCreditosAccion;
import CommonFuntions.BaseTest;
import StepsDefinitions.CommunSteps.Driver;
import cucumber.api.java.es.Cuando;
import cucumber.api.java.es.Dado;
import cucumber.api.java.es.Entonces;
import cucumber.api.java.es.Y;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import java.sql.SQLException;

public class SimuladorAsesorSteps {

    WebDriver driver;
    Logger log = Logger.getLogger(SimuladorAsesorSteps.class);
    OriginacionCreditosAccion originacionaccion;
    RetanqueoAsesorSteps retanqueoasesorsteps;
    LoginAccion loginaccion;
    BaseTest baseTest;


    public SimuladorAsesorSteps() throws InterruptedException {
        this.driver = Driver.driver;
        loginaccion = new LoginAccion(driver);
        originacionaccion = new OriginacionCreditosAccion(driver);
    }

    @Dado("Un agente en el sistema core abacus con sesion iniciada")
    public void unAgenteEnElSistemaCoreAbacusConSesionIniciada() throws Exception {
        log.info("@STEP - Un agente en el sistema core abacus con sesion iniciada - @STEP");
        try {
            // login anterior
//			loginaccion.iniciarSesion();
            // nuevo login
            loginaccion.login();
        } catch (Exception e) {
            log.error("#ERROR###" + e);
        }
    }

    @Y("^con las funciones sql necesarias del proyecto creadas$")
    public void conLasFuncionesSqlNecesariasDelProyectoCreadas() throws Throwable {
        log.info("@STEP - con las funciones sql necesarias del proyecto creadas - @STEP");
        originacionaccion.crearFuncionesSql();
    }

    @Cuando("el agente ingresa a la pestana de simulador asesor")
    public void elAgenteIngreseALaPestanaDeSimuladorAsesor() {
        log.info("@STEP - el agente ingresa a la pestana de simulador asesor - @STEP");
        originacionaccion.ingresarSimuladorAsesor();
    }

    @Y("cambia la fecha del servidor {string}")
    public void cambialafechadelservidor(String FechaServidor) {
        log.info("@STEP - cambia la fecha del servidor - @STEP");
        originacionaccion.CambiarFechaServidor(FechaServidor);
    }

    @Y("cree la simulacion con la informacion del archivo contenida en la tabla {string} {string} {string} {string} {string} {string} {string} {string} {string} {string} {string} {string} {string} {string} {string}")
    public void creeLaSimulacionConLaInformacionDelArchivoContenidaEnLaTabla(String Pagaduria, String Cedula, String fecha, String Oficina, String Actividad, String Tasa, String Plazo, String Monto, String DiasHabilesIntereses, String Ingresos, String descLey, String descNomina, String vlrCompasSaneamientos, String tipo, String colchon) throws InterruptedException, NumberFormatException, SQLException {
        log.info("@STEP - cree la simulacion con la informacion del archivo contenida en la tabla - @STEP");
        originacionaccion.llenarFormularioAsesor(Pagaduria, Cedula, fecha, Oficina, Actividad, Tasa, Plazo, Monto, DiasHabilesIntereses, Ingresos, descLey, descNomina, vlrCompasSaneamientos, colchon);
    }

    @Y("valida los calculos correctos de la simulacion{string}{string}{string}{string}{string}{string}{string}{string}{string}{string}{string}")
    public void valida_los_calculos_correctos_de_la_simulacion(String Fecha, String Tasa, String Plazo, String Monto, String DiasHabilesIntereses, String Ingresos, String descLey, String descNomina, String vlrCompasSaneamientos, String tipo, String pagaduria) throws NumberFormatException, SQLException {
        log.info("@STEP - valida los calculos correctos de la simulacion - @STEP");
        originacionaccion.assertSimulador(Fecha, Tasa, Plazo, Monto, DiasHabilesIntereses, Ingresos, descLey, descNomina, vlrCompasSaneamientos, tipo, pagaduria);
    }

    @Y("guarda la simulacion presionando el boton guardar")
    public void guardeLaSimulacionPresionandoElBotonGuardar() throws InterruptedException {
        log.info("@STEP - guarda la simulacion presionando el boton guardar - @STEP");
        originacionaccion.GuardarSimulacion();
    }

    @Entonces("se permite crear el cliente {string}{string}{string}{string}{string}{string}{string}{string}{string}")
    public void se_permite_crear_el_cliente(String TipoContrato, String FechaIngreso, String Pnombre, String Papellido, String Sapellido, String Correo, String Celular, String Dpto, String Ciudad) throws InterruptedException {
        log.info("@STEP - se permite crear el cliente - @STEP");
        originacionaccion.CrearCliente(TipoContrato, FechaIngreso, Pnombre, Papellido, Sapellido, Correo, Celular, Dpto, Ciudad);
    }

    @Y("el sistema habilita el cargue de documentos para el cliente {string}")
    public void el_sistema_habilite_el_cargue_de_documentos_para_el_cliente(String ruta) throws InterruptedException {
        log.info("@STEP - el sistema habilita el cargue de documentos para el cliente - @STEP");
        originacionaccion.SubirDocumentos(ruta);
    }

    @Entonces("se finaliza con la consulta a centrales {string}")
    public void seFinalizaConLaConsultaACentrales(String Cedula) throws InterruptedException {
        log.info("@STEP - se finaliza con la consulta a centrales - @STEP");
        originacionaccion.ConsultaCentrales(Cedula);
    }

}
