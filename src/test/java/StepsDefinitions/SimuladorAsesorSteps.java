package StepsDefinitions;

import java.util.List;

import org.apache.commons.exec.ExecuteException;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import Acciones.LoginAccion;
import Acciones.OriginacionCreditosAccion;
import CommonFuntions.BaseTest;
import cucumber.api.java.es.Cuando;
import cucumber.api.java.es.Dado;
import cucumber.api.java.es.Entonces;
import cucumber.api.DataTable;


public class SimuladorAsesorSteps {
	
	WebDriver driver;
	Logger log = Logger.getLogger(SimuladorAsesorSteps.class);	
	OriginacionCreditosAccion originacionaccion;	
	BaseTest baseTest;
	LoginAccion loginAccion;
	
	
	public SimuladorAsesorSteps() {		
		//super(driver);
		this.driver = Driver.driver;
		loginAccion = new LoginAccion(driver);
		originacionaccion = new OriginacionCreditosAccion(driver);	
	}
	
	@Dado("Un agente en el sistema core abacus con sesion iniciada") 
    public void unAgenteEnElSistemaCoreAbacusConSesionIniciada()  throws Exception {
		try {
			loginAccion.iniciarSesion();
		} catch (Exception e) {
			log.error("#ERROR###"+e);
			throw new Exception();
			
		}
		
	}

	@Cuando("el agente ingrese a la pestana de simulador asesor")
	public void elAgenteIngreseALaPestanaDeSimuladorAsesor() {
		originacionaccion.ingresarSimuladorAsesor();
	}

	
	@Cuando("cree la simulacion con la informacion del archivo contenida en la tabla {string}{string}{string}{string}{string}{string}{string}{string}{string}{string}{string}{string}{string}{string}")
	public void creeLaSimulacionConLaInformacionDelArchivoContenidaEnLaTabla(String Pagaduria,String Cedula,  String fecha,  String Oficina,  String Actividad,  String Tasa,  String Plazo, 	String Monto,  String DiasHabilesIntereses,  String Ingresos,  String descLey,  String descNomina,  String vlrCompasSaneamientos,  String tipo) throws InterruptedException {	    
		originacionaccion.llenarFormularioAsesor(Pagaduria,Cedula,fecha,Oficina,Actividad,Tasa,Plazo,Monto,DiasHabilesIntereses,Ingresos,descLey,descNomina,vlrCompasSaneamientos);
	}



	@Cuando("guarde la simulacion presionando el boton guardar")
	public void guardeLaSimulacionPresionandoElBotonGuardar() {
	}

	@Entonces("se permite crear el cliente con la informacion del archivo contenida en la hoja formCliente")
	public void sePermiteCrearElClienteConLaInformacionDelArchivoContenidaEnLaHojaFormCliente() {
	}

	@Entonces("el sistema habilite el cargue de documentos para el cliente")
	public void elSistemaHabiliteElCargueDeDocumentosParaElCliente() {
	}

	@Entonces("se finaliza con la consulta a centrales")
	public void seFinalizaConLaConsultaACentrales() {
	}

}
