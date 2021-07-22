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
import Pages.AplicacionCierrePage.PagoAplicacionFinalPage;
import Pages.AplicacionCierrePage.PagoListaPagoPages;
import Pages.AplicacionCierrePage.PagoPreaplicacionPagoPage;


public class AplicacionCierreAccion extends BaseTest {
	
	
	WebDriver driver;
	//Inicializacion pages
	PagoListaPagoPages listapagopage = new PagoListaPagoPages(driver);
	PagoPreaplicacionPagoPage pagopreaplicacionpagopage = new PagoPreaplicacionPagoPage(driver);
	PagoAplicacionFinalPage pagoaplicacionfinalpage = new PagoAplicacionFinalPage(driver);
		
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
			if(Math.round(Long.valueOf(vlr))!=0) {
				assertValidarEqualsImprimeMensaje(" 'ERROR' - YA SE ENCUENTRA CARGADA LA AGADURIA","0" , vlr);
			}
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
	
	/*
	 *TP - 21/07/2021
	 *Valida contra base de datos si ya el valor de preaplicacion se encuentra en TRUE */
	public void validarCheckPreaplicacion(String idPagaduria) {
		log.info("*¨*************************** AplicacionCierreAccion - validarCheckPreaplicacion()");
		ResultSet result = query.validarPreaplicacionRealizada(idPagaduria);
		boolean checkPagaduria= false;
		try {
			while(result.next()) {
				checkPagaduria = result.getBoolean(1);
			}
			assertBooleanImprimeMensaje("ERROR - EXISTE UNA PREAPLICACION EN EL SISTEMA",checkPagaduria);
		} catch (Exception e) {
			log.error("############## AplicacionCierreAccion - validarCheckPreaplicacion()  ################"+e);
			assertTrue("########## AplicacionCierreAccion - validarCheckPreaplicacion()########"+ e,false);
		}
	}
	
	/*
	 * Tp - 21/07/2021
	 * Se crea el metodo que compara los valores de la planilla con el del recaudo
	 */
	public void capturarValidarValoresPreaplicacion() {
		log.info("********************* AplicacionCierreAccion - capturarValidarValoresPreaplicacion()");
		try {
			String ValorRecaudo=GetText(pagopreaplicacionpagopage.ValorRecaudo).substring(0,GetText(pagopreaplicacionpagopage.ValorRecaudo).length()-2).replaceAll("[^a-zA-Z0-9]", "");
			String SumValoresRecibidos=GetText(pagopreaplicacionpagopage.ValoresRecibidos).substring(0,GetText(pagopreaplicacionpagopage.ValoresRecibidos).length()-2).replaceAll("[^a-zA-Z0-9]", "");
			assertValidarEqualsImprimeMensaje(" ################ ERROR - EL VALOR DEL RECAUDO NO COINCIDE CON EL DEL LISTADO ##########", 
					SumValoresRecibidos,  ValorRecaudo);
		} catch (Exception e) {
			log.error("############## AplicacionCierreAccion - capturarValidarValoresPreaplicacion()  ################"+e);
			assertTrue("########## AplicacionCierreAccion - capturarValidarValoresPreaplicacion()########"+ e,false);
		}
		
	}
	
	/*
	 * TP - 21/07/2021
	 * Se crea el metodo para seleccionar el boton de preaplicar
	 */
	public void realizarPreaplicacion(String mensaje) {
		try {
			esperaExplicita(pagopreaplicacionpagopage.btnPreaplicar);
			hacerClick(pagopreaplicacionpagopage.btnPreaplicar);
			assertTextonotificacion(pagopreaplicacionpagopage.notificacion, mensaje);
			hacerClicknotificacion();
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
	
	/*
	 * TP - 21/07/2021
	 * Valida la finalizacion de la preaplicacion - preaplicacion*/
	public void mensajeFinalizacion(String mensaje) {
		try {
			esperaExplicita(By.xpath("//*[starts-with(@id,'formMenu:j_idt') and starts-with(@class,'ui-dialog')]"));
			ElementVisible();			
			assertTextonotificacion(listapagopage.notificacion, mensaje);
		} catch (Exception e) {
			// TODO: handle exception
		}

	}
	
	/*
	 * TP - 21/07/2021 
	 * Acciones para aplicacion final*/
	public void filtrarTablaAplicacion(String pagaduria, String Periodo, String refresh) {
		log.info("****************** AplicacionCierreAccion- filtrarTablaAplicacion() **************" );
		try {
			if(refresh.equals("SI")) {
				log.info("************** AplicacionCierreAccion - Refrescar(); ***********");
				Refrescar();
				esperaExplicita(pagoaplicacionfinalpage.inputPeriodo);
				EscribirElemento(pagoaplicacionfinalpage.inputPeriodo, Periodo);
				ElementVisible();
				String dia = separarFecha(Periodo, "dia");
				selecDiaCalendario(pagoaplicacionfinalpage.listDiasCalendario, dia);
				ElementVisible();
				esperaExplicita(pagoaplicacionfinalpage.inputPagaduria);
				EscribirElemento(pagoaplicacionfinalpage.inputPagaduria, pagaduria);
				ElementVisible();
				esperaExplicitaTexto(pagaduria);
			}else {
				esperaExplicita(pagoaplicacionfinalpage.inputPeriodo);
				EscribirElemento(pagoaplicacionfinalpage.inputPeriodo, Periodo);
				ElementVisible();
				String dia = separarFecha(Periodo, "dia");
				selecDiaCalendario(pagoaplicacionfinalpage.listDiasCalendario, dia);
				ElementVisible();
				esperaExplicita(pagoaplicacionfinalpage.inputPagaduria);
				EscribirElemento(pagoaplicacionfinalpage.inputPagaduria, pagaduria);
				ElementVisible();
				esperaExplicitaTexto(pagaduria);
			}
		} catch (Exception e) {
			log.error("############## AplicacionCierreAccion - filtrarTablaAplicacion() ################"+e);
			assertTrue("########## AplicacionCierreAccion - filtrarTablaAplicacion()########"+ e,false);
		}
	}
	
	/*TP - 21/07/2021
	 * Este metodo inicia la aplicacion final presionando el check
	 * */
	public void iniciarAplicacionFinal() {
		log.info("*************** AplicacionCierreAccion - iniciarAplicacionFinal()*************");
		try {
			esperaExplicita(pagoaplicacionfinalpage.btnConfirmarPago);
			hacerClick(pagoaplicacionfinalpage.btnConfirmarPago);
		} catch (Exception e) {
			log.error("############## AplicacionCierreAccion - iniciarAplicacionFinal()() ################"+e);
			assertTrue("########## AplicacionCierreAccion - iniciarAplicacionFinal()()########"+ e,false);
		}
	}
	
	/*
	 * TP - 22/07/2021
	 * Este metodo Inicia el cierre precionando el boton */
	public void iniciarCierrePagaduria() {
		log.info("*************** AplicacionCierreAccion - iniciarCierrePagaduria()*************");
		try {
			esperaExplicita(pagoaplicacionfinalpage.btnCerrarPagaduria);
			hacerClick(pagoaplicacionfinalpage.btnCerrarPagaduria);
		} catch (Exception e) {
			log.error("############## AplicacionCierreAccion - iniciarCierrePagaduria() ################"+e);
			assertTrue("########## AplicacionCierreAccion - iniciarCierrePagaduria()########"+ e,false);
		}
	}
	
	/*
	 * TP - 21/07/2021
	 * Metodo para validar */
	public void validarCambioestado(String vlrfila, String vlrColumna, String pagaduria, String periodo) {
		int vlrContador = 0;
		String vlrFilaNavegador = "";
		while (vlrContador<300) {									
			switch (vlrColumna) {
			case "Recaudo confirmado":
				vlrFilaNavegador = buscarElementoFilaTabla(pagoaplicacionfinalpage.contTablaColumnas, 13);
				break;

			case "Estado Pagaduria":
				vlrFilaNavegador = buscarElementoFilaTabla(pagoaplicacionfinalpage.contTablaColumnas, 14);
				break;
			}
			if(vlrFilaNavegador.contains(vlrfila) && vlrContador < 300) {
				break;
			}
			else {
				vlrContador = vlrContador + 10;
				filtrarTablaAplicacion( pagaduria,  periodo, "SI");
			}
		}
		if(vlrContador>=300) {
			assertBooleanImprimeMensaje("######### ERROR - SUPERO LA MAXIMA ESPERA PARA EL CAMBIO DE ESTADO"
					+ " DE LA PAGADURIA - #######" + pagaduria, true);
		}
		log.info("Salio del Wile");
	}
	
	
	/*Acciones para aplicacion final*/
	
	
}
