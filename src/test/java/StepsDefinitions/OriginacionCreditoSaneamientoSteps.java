package StepsDefinitions;

import Acciones.OriginacionCreditoSaneamientoAccion;
import Acciones.OriginacionCreditosAccion;
import CommonFuntions.BaseTest;
import cucumber.api.java.es.Cuando;
import cucumber.api.java.es.Y;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import java.sql.SQLException;

/**
 * @author Adri&aacute;n Francisco Becerra Arias - abecerra@excelcredit.co
 * @version 1.0
 */
public class OriginacionCreditoSaneamientoSteps {

    WebDriver driver;
    Logger log = Logger.getLogger(SolicitudCreditoSteps.class);
    OriginacionCreditoSaneamientoAccion originacionCreditoSaneamientoAccion;
    BaseTest baseTest;

    public OriginacionCreditoSaneamientoSteps() {
        driver = Driver.driver;
        originacionCreditoSaneamientoAccion = new OriginacionCreditoSaneamientoAccion(driver);
        baseTest = new BaseTest(driver);
    }

    @Cuando("el agente ingrese a la pestana solicitud credito con la cedula del cliente {string}{string}")
    public void el_agente_ingrese_a_la_pestana_solicitud_credito_con_la_cedula_del_cliente_Cedula(String Cedula,String NombreCredito) throws InterruptedException {
        originacionCreditoSaneamientoAccion.ingresarSolicitudCredito(Cedula,NombreCredito);
    }

    @Y("consulta la pestana seguridad dejando el cliente viable")
    public void consultaLaPestanaSeguridadDejandoElClienteViable() throws InterruptedException {
        originacionCreditoSaneamientoAccion.Seguridad();
    }

    @Y("valida los calculos correctos de la simulacion interna {string}{string}{string}{string}{string}{string}{string}{string}{string}{string}{string}")
    public void valida_los_calculos_correctos_de_la_simulacion_interna(String Fecha, String Tasa,String Plazo,String Monto,String DiasHabilesIntereses,String Ingresos,String descLey,String descNomina,String vlrCompasSaneamientos,String tipo,String colchon) throws NumberFormatException, SQLException, InterruptedException {
        originacionCreditoSaneamientoAccion.assertSimuladorinterno(Fecha, Tasa, Plazo, Monto, DiasHabilesIntereses, Ingresos, descLey, descNomina, vlrCompasSaneamientos, tipo, colchon);
    }

    @Y("carga todos los archivos en la pestana de digitalizacion {string}")
    public void cargaTodosLosArchivosEnLaPestanaDeDigitalizacion(String Pdf) throws InterruptedException {
        originacionCreditoSaneamientoAccion.Digitalizacion(Pdf);

    }

    @Y("marcar los check en correcto guardando en la pestana de digitalizacion")
    public void marcarLosCheckEnCorrectoGuardandoEnLaPestanaDeDigitalizacion() throws InterruptedException {
        originacionCreditoSaneamientoAccion.DigitalizacionCheck();
    }

    @Y("pasar a la siguiente pestaña")
    public void pasarASiguienteTab() {

    }
}
