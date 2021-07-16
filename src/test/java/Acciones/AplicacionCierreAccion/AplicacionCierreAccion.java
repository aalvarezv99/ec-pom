package Acciones.AplicacionCierreAccion;

import static org.junit.Assert.assertTrue;

import java.sql.ResultSet;

import org.apache.log4j.Logger;
import org.apache.poi.ss.formula.functions.Replace;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import Acciones.PanelPrincipalAccion;
import CommonFuntions.BaseTest;
import Consultas.AplicacionCierreQuery;
import Pages.AplicacionCierrePage.PagoListaPagoPages;


public class AplicacionCierreAccion extends BaseTest {
	
	
	WebDriver driver;
	//Inicializacion pages
		PagoListaPagoPages listapagopage = new PagoListaPagoPages(driver);
		
	private static Logger log = Logger.getLogger(AplicacionCierreAccion.class);
	PanelPrincipalAccion panelprincipalaccion;
	AplicacionCierreQuery query;
	
	
	public AplicacionCierreAccion(WebDriver driver) {
		super(driver);
		panelprincipalaccion = new PanelPrincipalAccion(driver);
		query = new AplicacionCierreQuery();
	}
	
	public void NavegarPagoConOpcion(String opcion) {
		panelprincipalaccion.navegarPagoconOpcion(opcion);
	}
	
	/*
	 *Seleccionar el periodo y el año en el la pantalla de cargue */
	public void SeleccionarPeriodoAno(String Periodo) {
		log.info("***************** AplicacionCierreAccion - SeleccionarPeriodoAno()");
		try {
			esperaExplicita(listapagopage.labelPeriodo);
			assertEstaPresenteElemento(listapagopage.labelPeriodo);
			hacerClick(listapagopage.labelPeriodo);
			selectValorLista(listapagopage.listPeriodo, Periodo);
			ElementVisible();
		} catch (Exception e) {
			log.error("########## Error - AplicacionCierreAccion - SeleccionarPeriodoAno()  #######" + e);
			assertTrue("########## Error - AplicacionCierreAccion - SeleccionarPeriodoAno() ########"+ e,false);
		}
		
		
	}
	
	/*
	 * Se filtra por la pagaduria y se valida que no este cargada*/
	public void escribirPagaduriaValidarCargue(String nombrePagaduria) {
		log.info("***************** AplicacionCierreAccion - escribirPagaduriaValidarCargue()");
		try {
			esperaExplicita(listapagopage.inputPagaduria);
			EscribirElemento(listapagopage.inputPagaduria, nombrePagaduria);
			ElementVisible();
			//se valida el campo que no se encuentre en cero (VlrListado)
			esperaExplicitaTexto(nombrePagaduria);
			String vlr = buscarElementoFilaTabla(listapagopage.contTablafiltro, 12).replaceAll("[^a-zA-Z0-9]", "");	
			/*if(Math.round(Integer.valueOf(vlr))!=0) {
				assertValidarEqualsImprimeMensaje(" 'ERROR' - YA SE ENCUENTRA CARGADA LA AGADURIA", vlr, "0");
			}*/
		} catch (Exception e) {
			log.error("############## AplicacionCierreAccion - escribirPagaduriaValidarCargue()  ################"+ e);
			assertTrue("########## Error - AplicacionCierreAccion - escribirPagaduriaValidarCargue() ########"+ e,false);
		}
		
	}
	
	
	/*
	 *presiona el boton de cargar el archivo */
	public void cargarArchivoPagaduria(String nombrePagaduria, String rutaDocumento) {
		log.info("*********** AplicacionCierreAccion - cargarArchivoPagaduria() **********");
		try {			
			Hacer_scroll_Abajo(listapagopage.btnCargaArchivo);
			cargarpdf(listapagopage.btnCargaArchivo, rutaDocumento +nombrePagaduria+".xlsx");
			esperaExplicita(listapagopage.notificacion);
			assertTextonotificacion(listapagopage.notificacion, "Se ha iniciado la carga del archivo.");			
			hacerClicknotificacion();
		} catch (Exception e) {
			log.error("############## AplicacionCierreAccion - cargarArchivoPagaduria()  ################"+e);
			assertTrue("########## ErrorAplicacionCierreAccion - cargarArchivoPagaduria() ########"+ e,false);
		}
		
	}
	
	public void validarMensajeCargueTerminado(String notificacion) {
		log.info("*********** AplicacionCierreAccion - validarMensajeCargueTerminado()  **********");
		try {
			Hacer_scroll_Abajo(listapagopage.btnCargaArchivo);
			//esperaExplicita(listapagopage.notificacion);
			esperaExplicita(By.xpath("//*[starts-with(@id,'formMenu:j_idt') and starts-with(@class,'ui-dialog')]"));
			ElementVisible();
			
			assertTextonotificacion(listapagopage.notificacion, notificacion);
		} catch (Exception e) {
			log.error("############## AplicacionCierreAccion - validarMensajeCargueTerminado()  ################"+e);
			assertTrue("########## ErrorAplicacionCierreAccion - validarMensajeCargueTerminado() ########"+ e,false);
		}
	}
	
	public void validarVlrPlanillaContraSistema(String nombrePagaduria, String Periodo) {
		log.info("*********** AplicacionCierreAccion - validarVlrPlanillaContrasistema()  **********");
		ResultSet result = query.consultarVlrCargadoPlanilla(nombrePagaduria, Periodo);
		String vlrListadoDB = null;
		try {
			while(result.next()) {
				vlrListadoDB = result.getString(3);
			}		
			result.close();
			
			String Vlrpantalla = buscarElementoFilaTabla(listapagopage.contTablafiltro, 12).replaceAll("[^a-zA-Z0-9]", "");			
			assertValidarEqualsImprimeMensaje("LOS VALORES CARGADOS EN LISTADO NO COINCIDEN CON LOS CARGADOS EN BASE DE DATOS", 
					vlrListadoDB,  Vlrpantalla.substring(0, Vlrpantalla.length()-2));
		} catch (Exception e) {
			log.error("############## AplicacionCierreAccion - validarVlrPlanillaContrasistema()  ################"+e);
			assertTrue("########## ErrorAplicacionCierreAccion - validarVlrPlanillaContrasistema() ########"+ e,false);
		}
		
	}
	
	
}
