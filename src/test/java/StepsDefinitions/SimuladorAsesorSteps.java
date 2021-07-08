package StepsDefinitions;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.exec.ExecuteException;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import Acciones.LoginAccion;
import Acciones.OriginacionCreditosAccion;
import CommonFuntions.BaseTest;
import CommonFuntions.Navegador;
import cucumber.api.java.es.Cuando;
import cucumber.api.java.es.Dado;
import cucumber.api.java.es.Entonces;
import cucumber.api.java.es.Y;

public class SimuladorAsesorSteps {
	
	WebDriver driver;
	Logger log = Logger.getLogger(SimuladorAsesorSteps.class);	
	OriginacionCreditosAccion originacionaccion;
	RetanqueoAsesorSteps retanqueoasesorsteps;
	LoginAccion loginaccion;
	BaseTest baseTest;
	private List<String> Variables;
	static int a=0;
	
	public SimuladorAsesorSteps() {					
		//super(driver);
		//this.driver=Driver.driver;	
		this.driver=Navegador.driver;
		originacionaccion = new OriginacionCreditosAccion(driver);	
		loginaccion=new LoginAccion(driver);
	}
	
	@Dado("^Un agente en el sistema core abacus con sesion iniciada$")
	public void unAgenteEnElSistemaCoreAbacusConSesionIniciada(List<String> Valores) throws Exception {

		this.Variables=Valores;
		
		String Url=null;
		String Navegador=null;
		String Usuario=null;
		String Password=null;
		
	   	for(int i=1;i!=a;i++) {					
			Url=Variables.get(a);
			Navegador=Variables.get(a+1);
			Usuario=Variables.get(a+2);
			Password=Variables.get(a+3);
			a=(a+3)+1;	
			i=a-1;
		}
		
		try {			
			driver = loginaccion.iniciarSesion(Url,Navegador,Usuario,Password);  //*********************************************************
			originacionaccion = new OriginacionCreditosAccion(driver);//*******************************
		} catch (Exception e) {
			log.error("#ERROR###"+e);
			throw new Exception();
		}
	}

	@Cuando("el agente ingrese a la pestana de simulador asesor")
	public void elAgenteIngreseALaPestanaDeSimuladorAsesor() {
		originacionaccion.ingresarSimuladorAsesor();
	}
	
	@Y("cambia la fecha del servidor {string}")
	public void cambialafechadelservidor(String FechaServidor) {
		originacionaccion.CambiarFechaServidor(FechaServidor);
	}
	@Y("cree la simulacion con la informacion del archivo contenida en la tabla {string}{string}{string}{string}{string}{string}{string}{string}{string}{string}{string}{string}{string}{string}{string}")
	public void creeLaSimulacionConLaInformacionDelArchivoContenidaEnLaTabla(String Pagaduria,String Cedula,  String fecha,  String Oficina,  String Actividad,  String Tasa,  String Plazo, 	String Monto,  String DiasHabilesIntereses,  String Ingresos,  String descLey,  String descNomina,  String vlrCompasSaneamientos,  String tipo, String colchon) throws InterruptedException, NumberFormatException, SQLException {
	originacionaccion.llenarFormularioAsesor(Pagaduria,Cedula,fecha,Oficina,Actividad,Tasa,Plazo,Monto,DiasHabilesIntereses,Ingresos,descLey,descNomina,vlrCompasSaneamientos,colchon);
	}

	@Y("valida los calculos correctos de la simulacion{string}{string}{string}{string}{string}{string}{string}{string}{string}{string}{string}")
	public void valida_los_calculos_correctos_de_la_simulacion(String Fecha, String Tasa,String Plazo,String Monto,String DiasHabilesIntereses,String Ingresos,String descLey,String descNomina,String vlrCompasSaneamientos,String tipo,String colchon) throws NumberFormatException, SQLException {
    originacionaccion.assertSimulador(Fecha,Tasa,Plazo,Monto,DiasHabilesIntereses,Ingresos,descLey,descNomina,vlrCompasSaneamientos,tipo,colchon);  
	}

	@Y("guarde la simulacion presionando el boton guardar")
	public void guardeLaSimulacionPresionandoElBotonGuardar() throws InterruptedException {
	originacionaccion.GuardarSimulacion();
	}
	
	@Entonces("se permite crear el cliente {string}{string}{string}{string}{string}{string}{string}{string}{string}")
	public void se_permite_crear_el_cliente(String TipoContrato, String FechaIngreso, String Pnombre, String Papellido, String Sapellido, String Correo, String Celular, String Dpto, String Ciudad) throws InterruptedException {
	originacionaccion.CrearCliente(TipoContrato,FechaIngreso,Pnombre,Papellido,Sapellido,Correo,Celular,Dpto,Ciudad);
	}

	@Y("el sistema habilite el cargue de documentos para el cliente {string}")
	public void el_sistema_habilite_el_cargue_de_documentos_para_el_cliente(String ruta) throws InterruptedException {
	originacionaccion.SubirDocumentos(ruta);
	}

	@Entonces("se finaliza con la consulta a centrales")
	public void seFinalizaConLaConsultaACentrales() throws InterruptedException {
	originacionaccion.ConsultaCentrales();
	}

}
