package Acciones.AplicacionCierreAccion;

import static org.junit.Assert.assertTrue;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import Acciones.ComunesAccion.PanelPrincipalAccion;
import CommonFuntions.BaseTest;
import Consultas.AplicacionCierreQuery;
import Consultas.OriginacionCreditoQuery;
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
		adjuntarCaptura("IngresarCarguePlanilla");
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
		log.info("****** Se cargo la pagaduria - "+ nombrePagaduria+" ****** " );
		try {
			esperaExplicita(listapagopage.inputPagaduria);
			EscribirElemento(listapagopage.inputPagaduria, nombrePagaduria);
			ElementVisible();
			//se valida el campo que no se encuentre en cero (VlrListado)
			esperaExplicitaTexto(nombrePagaduria);
			String vlr = buscarElementoFilaTabla(listapagopage.contTablafiltro, 12).replaceAll("[^a-zA-Z0-9]", "");	
			if(Math.round(Long.valueOf(vlr))!=0) {
				Hacer_scroll_Abajo(listapagopage.btnCargaArchivo);
				adjuntarCaptura("PagaduriaCargada");
				assertValidarEqualsImprimeMensaje(" 'ERROR' - YA SE ENCUENTRA CARGADA LA PAGADURIA","0" , vlr);
			}
			adjuntarCaptura("FiltroPagaduria");
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
			adjuntarCaptura("InicioCarguePlanilla");
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
			esperaExplicita(listapagopage.notificacion);
			esperaExplicita(By.xpath("//*[starts-with(@id,'formMenu:j_idt') and starts-with(@class,'ui-dialog')]"));
			ElementVisible();
			String texto= (GetText(listapagopage.notificacion).contains("Procesando"))?"Pagaduria Grande":" Pagaduria Pequeña"; 
			
			log.info("**** Cargando un archivo de ***" + texto);
			
			while(GetText(listapagopage.notificacion).contains("Procesando")) {
				esperaExplicita(listapagopage.notificacion);
				hacerClicknotificacion();
				esperaExplicita(By.xpath("//*[starts-with(@id,'formMenu:j_idt') and starts-with(@class,'ui-dialog')]"));
				ElementVisible();
			}
					
			assertTextonotificacion(listapagopage.notificacion, notificacion);
			adjuntarCaptura("CargueTerminado");
			hacerClicknotificacion();
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
			Hacer_scroll_Abajo(listapagopage.btnCargaArchivo);
			adjuntarCaptura("ValorPlanillaCargada");
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
	public void filtrarTablaAplicacion(String pagaduria, String Periodo) {
		log.info("****************** AplicacionCierreAccion- filtrarTablaAplicacion() **************" );
		try {
			
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
	 * Metodo para validar cambio de estados*/
	public void validarCambioestado(String vlrfila, String vlrColumna, String pagaduria, String periodo) {
		int vlrContador = 0;
		String vlrFilaNavegador = "";

		switch (vlrColumna) {
		case "Recaudo confirmado":
			log.info("****** Realizando validacion de Aplicacion Pagos Pagaduria*****");
			validarfinalizacionAplicacioncierre(pagaduria,"Aplicacion",periodo);
			vlrFilaNavegador = buscarElementoFilaTabla(pagoaplicacionfinalpage.contTablaColumnas, 13);
			log.info("********Se realizo el cargue de la pagaduria  ******" + vlrFilaNavegador);
			filtrarTablaAplicacion( pagaduria,  periodo);
			break;

		case "Estado Pagaduria":
			log.info("****** Realizando validacion de Cierre Pagaduria *****");
			validarfinalizacionAplicacioncierre(pagaduria,"Cierre",periodo);
			vlrFilaNavegador = buscarElementoFilaTabla(pagoaplicacionfinalpage.contTablaColumnas, 14);
			filtrarTablaAplicacion( pagaduria,  periodo);
			log.info("********El estado del cierre es  ******" + vlrFilaNavegador);
			break;
		}

	}
	
	
	/*ThainerPerez, 03/Nov/2021 - Esta funcion se encarga de validar que en bases de datos cambio el estado tiene un tiempo maximo de 5 min*/
	 public void validarfinalizacionAplicacioncierre(String Pagaduria, String proceso, String fechaPeriodoFeature) {
		    
		 	try {
		 		String fechaPeridoDB = "";
		        Boolean aplicacionFinalizada = false;
		        String cierreFinalizado = "";
		        ResultSet resultado;
		        long start_time = System.currentTimeMillis();
		        long wait_time = 360000;
		        long end_time = start_time + wait_time;
		        int contador = 1;
				while (System.currentTimeMillis() < end_time && (aplicacionFinalizada == false || cierreFinalizado == null)) {
					contador = contador +1;
					resultado = query.validarAplicacionCierre(Pagaduria);
					while (resultado.next()) {
						fechaPeridoDB = resultado.getString(1);
						aplicacionFinalizada = resultado.getBoolean(2);
						cierreFinalizado = resultado.getString(3);

					}
				}
				log.info("**** Fecha Periodo DB**** " + fechaPeridoDB);
				log.info("**** blr Aplicacion Pago DB****" + aplicacionFinalizada);
				log.info("***** Vlr Cierre DB ***" + cierreFinalizado);
				
				assertValidarEqualsImprimeMensaje(
						"### ERROR - la fecha de la consulta y del feature son diferentes ###", fechaPeridoDB,
						fechaPeriodoFeature);

				if (proceso.equals("Aplicacion") && aplicacionFinalizada == false) {
					assertBooleanImprimeMensaje(
							" ERROR - la validacion de APLICACION supero el limite de tiempo de 6 minutos  y no cambio de estado",
							true);
				} else {
					Refrescar();
				}

				if (cierreFinalizado != "CERRADA" && proceso.equals("Cierre")) {
					assertBooleanImprimeMensaje(
							" ERROR - la validacion de CIERRE supero el limite de tiempo de 6 minutos y no cambio a CERRADA", true);
				} else {
					Refrescar();
				}
			} catch (Exception e) {
				log.error("############## AplicacionCierreAccion - validarfinalizacionAplicacioncierre() ################"+e);
				assertTrue("########## AplicacionCierreAccion - validarfinalizacionAplicacioncierre()########"+ e,false);
			}
	        
	    }
	
	
}
