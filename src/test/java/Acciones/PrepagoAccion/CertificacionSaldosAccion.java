package Acciones.PrepagoAccion;


import static org.junit.Assert.assertTrue;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import Acciones.RecaudosAccion;
import Acciones.ComunesAccion.PanelPrincipalAccion;
import Acciones.ConfigGlobalAccion.ConfigPrepagoAccion;
import CommonFuntions.BaseTest;
import Consultas.CertificacionSaldoQuery;
import Pages.Prepago.CertificacionSaldoPage;
import dto.SaldoInsolutoDto;

public class CertificacionSaldosAccion extends BaseTest {

	WebDriver driver;
	CertificacionSaldoPage certificacionsaldopage = new CertificacionSaldoPage(driver);	
	PanelPrincipalAccion panelprincipal;
	RecaudosAccion recaudoAccion;
	ConfigPrepagoAccion configlobalprepagoAccion;
	GestionCertificadosAccion gestioncertificado;
	CertificacionSaldoQuery query;
	SaldoInsolutoDto conceptosCertificacion;
	
	private static Logger log = Logger.getLogger(CertificacionSaldosAccion.class);
	
	//Variables de uso grobal
	String nombreDoc = "";
	public static String rutaDocumentoG;
	
	public CertificacionSaldosAccion(WebDriver driver) {
		super(driver);
		// this.driver = driver;
		panelprincipal = new PanelPrincipalAccion(driver);
		recaudoAccion = new RecaudosAccion(driver);
		configlobalprepagoAccion = new ConfigPrepagoAccion(driver);
		gestioncertificado = new GestionCertificadosAccion(driver);
		query = new CertificacionSaldoQuery();
		conceptosCertificacion = new SaldoInsolutoDto();
	}
	
	/********** INICIA INSTANCIA CLASE NAVEGAR SISTEMA****************/
	/*
	 * PANEL PRINCIPAL - MODULOS (SOLICITUD CERTIFICADO-RECAUDO-CONFIGURACION GLOBAL PREPAGO - GESTION CERTIFICADO)
	 * pestana n/a
	 * Se crean las acciones para ingresar a cada uno de las pantallas del sistema
	 * */
	public void ingresarSolicitudCertificado() {
		panelprincipal.navegarCertificacionSaldo();
		adjuntarCaptura("IngresoSolicitudCertificado");
	}
	
	public void ingresarGestionCertificado() {
		panelprincipal.navegarGestionCertificado();
	}
	
	public void ingresarConfigGlobalPrepago() {
		panelprincipal.navegarConfigPrepago();
	}
	/********** FINALIZA INSTANCIA NAVEGAR SISTEMA****************/
    
	/*==========================================================================================*/
	
	/********** INICIA ACCIONES VENTANA CERTIFIACION SALDO****************/
	/*
	 * PREPAGO - MODULO CERTIFICACION DE SALDO
	 * pestana n/a
	 * Se realiza la accion de buscar el cliente con el numero de radicado y la cedula en la tabla principal 
	 * */
	public void buscarCliente(int numRadicado, int numCedula, String estado) throws InterruptedException {
		log.info("************ CertificacionSaldosAccion - buscarCliente() ************");
		try {
			hacerClick(certificacionsaldopage.inputCedula);
			EscribirElemento(certificacionsaldopage.inputCedula, String.valueOf(numCedula));
			ElementVisible();
			hacerClick(certificacionsaldopage.inputNumRadicado);
			EscribirElemento(certificacionsaldopage.inputNumRadicado, String.valueOf(numRadicado));
			ElementVisible();			
			contarFilasTablas(certificacionsaldopage.countFilas);
			hacerClicknotificacion();
		} catch (Exception e) {
			log.error("########## ERROR  buscarCliente() ##########" + e);
			assertTrue("########## ERROR  buscarCliente() ##########"+ e,false);
		}
		adjuntarCaptura("buscarCliente");
		
	}
	
	/*
	 * PREPAGO - MODULO CERTIFICACION DE SALDO
	 * pestana n/a
	 * Se realiza el evento de presionar el boton cuando se encuentra el filtro aplicado (PreSolicitar Y/O Solicitar)
	 * */
	public void presionarBotonSolicitar() {
		
		log.info("******** CertificacionSaldosAccion - presionarBotonSolicitar()********** ");		
		try {
			esperaExplicitaNopresente();
			if(assertEstaPresenteElemento(certificacionsaldopage.botonPresolicitar)==true) {				
				hacerClick(certificacionsaldopage.botonPresolicitar);
				ElementVisible();
				hacerClick(certificacionsaldopage.botonSolicitar);
				ElementVisible();
				esperaExplicitaNopresente();
			}		
			else {							
				esperaExplicita(certificacionsaldopage.botonSolicitar);
				Hacer_scroll(certificacionsaldopage.botonSolicitar);				
				assertVisibleElemento(certificacionsaldopage.botonSolicitar);				
				hacerClick(certificacionsaldopage.botonSolicitar);
				ElementVisible();
				esperaExplicitaNopresente();
			}
		} catch (Exception e) {
			log.error("######### ERROR SALDOS ACCION - PRESIONARBOTONSOLICITAR() ######## " + e );	
			assertTrue("######### ERROR SALDOS ACCION - PRESIONARBOTONSOLICITAR() ########"+ e,false);
		}
		adjuntarCaptura("presionarBotonSolicitar");
		
	}
	
	/*
	 * PREPAGO - MODULO CERTIFICACION DE SALDO
	 * pestana n/a
	 * Se realiza el evento de presionar el boton guardar a la ventana desglegada en el modulo certificacion de saldo
	 * */
	public void presionarBotonGuardar() {
		log.info("********CertificacionSaldosAccion - presionarBotonGuardar()********** ");
		try {
			if(assertEstaPresenteElemento(certificacionsaldopage.botonGuardarCertificado)==true) {
				hacerClick(certificacionsaldopage.botonGuardarCertificado);
				ElementVisible();
			}
		} catch (Exception e) {
			log.error("######### ERROR SALDOS ACCION ######## presionarBotonGuardar()" +e);
			assertTrue("######### ERROR SALDOS ACCION ######## presionarBotonGuardar()"+ e,false);
		}
		adjuntarCaptura("presionarBotonGuardar");
	}
	/********** FINALIZA ACCIONES VENTANA CERTIFIACION SALDO****************/
	
	/*===========================================================================================================*/

	/********************** INICIA ACCION CONFIGURACION GLOBAL PREPAGO************************************/
	/*
	 * CONFIGURACION GLOBAL - MODULO PREPAGO
	 * pestana prepago
	 * se instancia un objeto de la clase CONFIGURACIONPGLOBAL y se ejecuta el metodo
	 * */
	public void configurarPregagoCertificacion(String diaCert, String venCert, String vlrCert) {
		configlobalprepagoAccion.configurarPrepagoGlobal(diaCert,venCert,vlrCert);
	}
	
	/************************FINALIZA ACCION CONGIGURACION GLOBAL PREPAGO**********************************/

	/*===========================================================================================================*/
	
	/************************INICIA ACCION LLAMADO CLASE GESTION CERTIFICADO ACCION**********************************/
	public void descargarPdfGestionCertificado(int numRadicado, int numCedula) {
		log.info("************* CertificacionSaldosAccion - descargarPdfGestionCertificado()***********");
		try {
			ingresarGestionCertificado();
			gestioncertificado.descargarCertificadoPdf(String.valueOf(numRadicado), String.valueOf(numCedula));
		} catch (Exception e) {
			log.error("############# ERROR CertificacionSaldosAccion - descargarPdfGestionCertificado() ###############" + e);
			assertTrue("############# ERROR CertificacionSaldosAccion - descargarPdfGestionCertificado() ###############"+ e,false);
		}
		
	}
	/************************FINALIZA ACCION LLAMADO GESTION CERTIFICADO ACCION**********************************/
	

	/*===========================================================================================================*/
	
	/************************INICIA ACCION VALIDAR VALORES DE PDF**********************************/
	/*
	 * BASES DE DATOS - QUERY CERT SALDOS
	 * pestana n/a
	 * Se realiza la busqueda del pdf generado con el numero de radicacion
	 * el cual se encuentra en la siguiente ruta C:\Users\User\Downloads\CertificacionSaldos
	 * si no la tiene por favor crearla, posteriormente realiza la consulta o llamado a la funcion
	 * para realizar la validacion de los valores mostrados en el PDF 
	 * */
	public void validarValoresPDF(String numRadicado, String rutadocumento) {
		
		log.info("************** CertificacionSaldosAccion - validarValoresPDF() ************");	
		
		ResultSet result = query.consultarTipoCertificacion(String.valueOf(numRadicado)); 		
		String vlrFianza = null;		
		String estado = null;
		String cuentasCXC = null;
		String fecchaVencimiento = extraerValorPDF(limpiarCadena(rutadocumento),"certificacion-saldo-"+numRadicado+".pdf", "Fecha limite de pago ").replace("pago ", "");
		SimpleDateFormat formato = new SimpleDateFormat("dd/mm/yyyy");
		SimpleDateFormat formatoSalida = new SimpleDateFormat("yyyy-mm-dd");
		try {
			Date fechaFormato = formato.parse(fecchaVencimiento);
			log.info(formatoSalida.format(fechaFormato));
			conceptosCertificacion = query.obtenerSaldoInsoluto(numRadicado, formatoSalida.format(fechaFormato)); 
			
			while(result.next()) {
				vlrFianza = result.getString(1);
				estado = result.getString(2);
				cuentasCXC = result.getString(3);
			}
			
			String rutaPdf = leerPropiedades("RutaArchivosDescargados");
			
			Map<String,String> desConceptos = conceptosCertificacion(conceptosCertificacion,estado,vlrFianza,cuentasCXC);
			
			for (Map.Entry<String, String> entry : desConceptos.entrySet()) {
				buscarVlrArchivoPDF("certificacion-saldo-"+numRadicado+".pdf",entry.getValue(),rutaPdf);
			}
			
		} catch (Exception e) {
			log.error("######### ERROR - validarValoresPDF()#######"+ e);
			assertTrue("######### ERROR - validarValoresPDF()#######"+ e,false);
		}
		
		abrirNuevaVentana();
		
	}
	
	public Map<String, String> conceptosCertificacion(SaldoInsolutoDto valores,String estado, String vlrFianza, String cuentasCXC){
		Map<String,String> conceptos = new HashMap<String,String>();
		
		conceptos.put("Capital", "Capital $ " +  valores.getCapital());
		conceptos.put("IntCorriente", "Intereses Corrientes $ " + valores.getInteresesCorrientes());
		conceptos.put("Seguro","Seguro $ "+ valores.getSeguro());
		
		if(vlrFianza != null) {
			conceptos.put("Fianza","Fianza $ " + valores.getCxcFianza());
			conceptos.put("IntFianza","Intereses de Fianza $ " + valores.getCxcInteresesFianza());
		}
		else if(cuentasCXC.toUpperCase()!=("SINCXC")) {
			conceptos.put("SegIniPendiente","Seguro inicial pendiente de pago $ " + valores.getCxcSeguroIncial());
			conceptos.put("IntIniPendiente","Intereses iniciales pendientes de pago $ " + valores.getCxcIntesesInciales());
		}
		
		conceptos.put("PrimaSegPendien","Prima de seguro anticipada pendiente de pago $ ");
		conceptos.put("IntPrimaAnti","Intereses prima de seguro anticipada $ ");
		conceptos.put("Total","Total a pagar $ " + valores.getSaldoInsoluto());
		
		
		
		return conceptos;
	}
	
	
	
	/************************FINALIZA ACCION VALIDAR VALORES PDF**********************************/
	/*===========================================================================================================*/
	
}
