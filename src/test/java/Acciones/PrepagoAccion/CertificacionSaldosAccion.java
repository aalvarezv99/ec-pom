package Acciones.PrepagoAccion;


import static org.junit.Assert.assertTrue;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import Acciones.RecaudosAccion;
import Acciones.ComunesAccion.PanelPrincipalAccion;
import Acciones.ConfigGlobalAccion.ConfigPrepagoAccion;
import CommonFuntions.BaseTest;
import Consultas.CertificacionSaldoQuery;
import Pages.Prepago.CertificacionSaldoPage;

public class CertificacionSaldosAccion extends BaseTest {

	WebDriver driver;
	CertificacionSaldoPage certificacionsaldopage = new CertificacionSaldoPage(driver);	
	PanelPrincipalAccion panelprincipal;
	RecaudosAccion recaudoAccion;
	ConfigPrepagoAccion configlobalprepagoAccion;
	GestionCertificadosAccion gestioncertificado;
	CertificacionSaldoQuery query;
	
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
			query.obtenerSaldoInsoluto(numRadicado, fechaFormato);
			while(result.next()) {
				vlrFianza = result.getString(1);
				estado = result.getString(2);
				cuentasCXC = result.getString(3);
				
			}
			if(vlrFianza!= null && estado.contains("ACTIVO") && cuentasCXC.toUpperCase().equals("CONCXC") ) {
				CertificacionSaldoActivaCxcFianza(String.valueOf(numRadicado));
			}
			else if (vlrFianza == null && estado.equals("ACTIVO") && cuentasCXC.toUpperCase().equals("CONCXC")) {
				CertificacionSaldoActivaCxcSinFianza(String.valueOf(numRadicado));
			}
			else if (vlrFianza == null && estado.equals("ACTIVO") && cuentasCXC.toUpperCase().equals("SINCXC")) {
				CertificacionSaldoActivaSinCXC(String.valueOf(numRadicado));
			}
		} catch (Exception e) {
			log.error("######### ERROR - validarValoresPDF()#######"+ e);
			assertTrue("######### ERROR - validarValoresPDF()#######"+ e,false);
		}
		
		abrirNuevaVentana();
		
	}
	
	public void CertificacionSaldoActivaCxcFianza(String numRadicado) throws SQLException {
		log.info("CERTIFICACION CON FIANZA Y ACTIVO Y CUENTAS POR COBRAR");
		log.info("*************************CertificacionSaldosAccion - CertificacionSaldoActivaCxcFianza()*********");		
		NumberFormat formatoNumero = NumberFormat.getNumberInstance();
		String rutaPdf = leerPropiedades("RutaArchivosDescargados");
		
		//SaldoInsolutoDto saldoInsolutoDto = query.obtenerSaldoInsoluto(null, rutaPdf, numRadicado, rutaPdf);
		
		//ResultSet result = query.ConsultarRegistroCertificacion(String.valueOf(numRadicado)); 
		
		/*try {
			while(result.next()) {
				nombreDoc = "certificacion-saldo-"+numRadicado+".pdf";
				//log.info("************** Buscando valores en la certificacion " + nombreDoc +" *******");
				//abriPdfNavegador(rutaPdf+nombreDoc);
				adjuntarCaptura(nombreDoc);
				buscarVlrArchivoPDF(nombreDoc,"Capital $ "+ formatoNumero.format(Double.parseDouble(result.getString(2))).replace('.', '.'),rutaPdf);
				buscarVlrArchivoPDF(nombreDoc, "Intereses Corrientes $ "+ formatoNumero.format(Double.parseDouble(result.getString(3))).replace('.', '.'), rutaPdf);				
				buscarVlrArchivoPDF(nombreDoc,"Seguro $ "+ formatoNumero.format(Double.parseDouble(result.getString(4))).replace('.', '.') ,rutaPdf);
				buscarVlrArchivoPDF(nombreDoc,"Seguro inicial pendiente de pago $ "+ formatoNumero.format(Double.parseDouble(result.getString(5))).replace('.', '.') ,rutaPdf);
				buscarVlrArchivoPDF(nombreDoc,"Intereses iniciales pendientes de pago $ "+ formatoNumero.format(Double.parseDouble(result.getString(6))).replace('.', '.') ,rutaPdf);
				buscarVlrArchivoPDF(nombreDoc,"Estudio de credito pendiente de pago $ "+ formatoNumero.format(Double.parseDouble(result.getString(7))).replace('.', '.') ,rutaPdf);
				buscarVlrArchivoPDF(nombreDoc,"Fianza $ "+ formatoNumero.format(Double.parseDouble(result.getString(8))).replace('.', '.') ,rutaPdf);
				buscarVlrArchivoPDF(nombreDoc,"Intereses de Fianza $ "+ formatoNumero.format(Double.parseDouble(result.getString(9))).replace('.', '.') ,rutaPdf);
				buscarVlrArchivoPDF(nombreDoc,"Prima de seguro anticipada pendiente de pago $ "+ formatoNumero.format(Double.parseDouble(result.getString(10))).replace('.', '.') ,rutaPdf);
				buscarVlrArchivoPDF(nombreDoc,"Intereses prima de seguro anticipada $ "+ formatoNumero.format(Double.parseDouble(result.getString(11))).replace('.', '.') ,rutaPdf);
				buscarVlrArchivoPDF(nombreDoc,"Total a pagar $ "+ formatoNumero.format(Double.parseDouble(result.getString(12))).replace('.', '.') ,rutaPdf);			
				log.info("Fecha Aprobacion Credito " +result.getString(13));
				log.info("Fecha vencimiento Certificado " +result.getString(14));
				buscarVlrArchivoPDF(nombreDoc,"Fecha limite de pago "+ result.getString(15),rutaPdf);
			}		
			result.close();
			
		} catch (Exception e) {
			log.error("####### ERROR CertificacionSaldosAccion - CertificacionSaldoActivaCxcFianza() ##########"+ e);
			assertTrue("####### ERROR CertificacionSaldosAccion - CertificacionSaldoActivaCxcFianza() ##########"+ e,false);
		}*/
	}
	
	public void CertificacionSaldoActivaCxcSinFianza(String numRadicado) {
		log.info("CERTIFICACION SIN FIANZA Y ACTIVO, CON CUENTAS POR COBRAR ");
		log.info("*************************CertificacionSaldosAccion - CertificacionSaldoActivaCxcSinFianza()*********");
				
		NumberFormat formatoNumero = NumberFormat.getNumberInstance();
		String rutaPdf = leerPropiedades("RutaArchivosDescargados");
		ResultSet result = query.ConsultarRegistroCertificacion(String.valueOf(numRadicado)); 
		
		try {
			while(result.next()) {
				nombreDoc = result.getString(1);
				//abriPdfNavegador(rutaPdf+nombreDoc);
				adjuntarCaptura(nombreDoc);
				log.info("************** Buscando valores en la certificacion " + nombreDoc +" *******");
				buscarVlrArchivoPDF(nombreDoc,"Capital $ "+ formatoNumero.format(Double.parseDouble(result.getString(2))).replace('.', '.'),rutaPdf);
				buscarVlrArchivoPDF(nombreDoc, "Intereses Corrientes $ "+ formatoNumero.format(Double.parseDouble(result.getString(3))).replace('.', '.'), rutaPdf);				
				buscarVlrArchivoPDF(nombreDoc,"Seguro $ "+ formatoNumero.format(Double.parseDouble(result.getString(4))).replace('.', '.') ,rutaPdf);
				buscarVlrArchivoPDF(nombreDoc,"Seguro inicial pendiente de pago $ "+ formatoNumero.format(Double.parseDouble(result.getString(5))).replace('.', '.') ,rutaPdf);
				buscarVlrArchivoPDF(nombreDoc,"Intereses iniciales pendientes de pago $ "+ formatoNumero.format(Double.parseDouble(result.getString(6))).replace('.', '.') ,rutaPdf);
				buscarVlrArchivoPDF(nombreDoc,"Estudio de credito pendiente de pago $ "+ formatoNumero.format(Double.parseDouble(result.getString(7))).replace('.', '.') ,rutaPdf);
				//buscarVlrArchivoPDF(nombreDoc,"Fianza $ "+ formatoNumero.format(Double.parseDouble(result.getString(8))).replace('.', '.') ,rutaPdf);
				buscarVlrArchivoPDF(nombreDoc,"Prima de seguro anticipada pendiente de pago $ "+ formatoNumero.format(Double.parseDouble(result.getString(10))).replace('.', '.') ,rutaPdf);
				buscarVlrArchivoPDF(nombreDoc,"Intereses prima de seguro anticipada $ "+ formatoNumero.format(Double.parseDouble(result.getString(11))).replace('.', '.') ,rutaPdf);
				buscarVlrArchivoPDF(nombreDoc,"Total a pagar $ "+ formatoNumero.format(Double.parseDouble(result.getString(12))).replace('.', '.') ,rutaPdf);			
				log.info("Fecha Aprobacion Credito " +result.getString(13));
				log.info("Fecha vencimiento Certificado " +result.getString(14));
				buscarVlrArchivoPDF(nombreDoc,"Fecha limite de pago "+ result.getString(15),rutaPdf);	
				buscarVlrArchivoPDF(nombreDoc,"Clausula de indemnizacion por renuncia al plazo $ "+ formatoNumero.format(Double.parseDouble(result.getString(16))).replace('.', '.') ,rutaPdf);
			}					
			result.close();
		} catch (Exception e) {
			log.error("####### ERROR CertificacionSaldosAccion - validarValoresPDF() ##########"+ e);
			assertTrue("####### ERROR CertificacionSaldosAccion - validarValoresPDF() ##########"+ e,false);
		}
	}
	
	public void CertificacionSaldoActivaSinCXC(String numRadicado) {
		log.info("CERTIFICACION SIN FIANZA, SIN CUENTAS POR COBRAR Y ACTIVO");
		log.info("*************************CertificacionSaldosAccion - CertificacionSaldoActivaSinCXC()*********");
		
		NumberFormat formatoNumero = NumberFormat.getNumberInstance();
		String rutaPdf = leerPropiedades("RutaArchivosDescargados");
		ResultSet result = query.ConsultarRegistroCertificacion(String.valueOf(numRadicado)); 
		
		try {
			while(result.next()) {
				nombreDoc = result.getString(1);
				//abriPdfNavegador(rutaPdf+nombreDoc);
				adjuntarCaptura(nombreDoc);
				log.info("************** Buscando valores en la certificacion " + nombreDoc +" *******");
				buscarVlrArchivoPDF(nombreDoc,"Capital $ "+ formatoNumero.format(Double.parseDouble(result.getString(2))).replace('.', '.'),rutaPdf);
				buscarVlrArchivoPDF(nombreDoc, "Intereses Corrientes $ "+ formatoNumero.format(Double.parseDouble(result.getString(3))).replace('.', '.'), rutaPdf);				
				buscarVlrArchivoPDF(nombreDoc,"Seguro $ "+ formatoNumero.format(Double.parseDouble(result.getString(4))).replace('.', '.') ,rutaPdf);
				//buscarVlrArchivoPDF(nombreDoc,"Seguro inicial pendiente de pago $ "+ formatoNumero.format(Double.parseDouble(result.getString(5))).replace('.', '.') ,rutaPdf);
				//buscarVlrArchivoPDF(nombreDoc,"Intereses iniciales pendientes de pago $ "+ formatoNumero.format(Double.parseDouble(result.getString(6))).replace('.', '.') ,rutaPdf);
				//buscarVlrArchivoPDF(nombreDoc,"Estudio de credito pendiente de pago $ "+ formatoNumero.format(Double.parseDouble(result.getString(7))).replace('.', '.') ,rutaPdf);
				//buscarVlrArchivoPDF(nombreDoc,"Fianza $ "+ formatoNumero.format(Double.parseDouble(result.getString(8))).replace('.', '.') ,rutaPdf);
				buscarVlrArchivoPDF(nombreDoc,"Prima de seguro anticipada pendiente de pago $ "+ formatoNumero.format(Double.parseDouble(result.getString(10))).replace('.', '.') ,rutaPdf);
				buscarVlrArchivoPDF(nombreDoc,"Intereses prima de seguro anticipada $ "+ formatoNumero.format(Double.parseDouble(result.getString(11))).replace('.', '.') ,rutaPdf);
				buscarVlrArchivoPDF(nombreDoc,"Total a pagar $ "+ formatoNumero.format(Double.parseDouble(result.getString(12))).replace('.', '.') ,rutaPdf);			
				log.info("Fecha Aprobacion Credito " +result.getString(13));
				log.info("Fecha vencimiento Certificado " +result.getString(14));
				buscarVlrArchivoPDF(nombreDoc,"Fecha limite de pago "+ result.getString(15),rutaPdf);	
				buscarVlrArchivoPDF(nombreDoc,"Clausula de indemnizacion por renuncia al plazo $ "+ formatoNumero.format(Double.parseDouble(result.getString(16))).replace('.', '.') ,rutaPdf);
			}					
			result.close();
		} catch (Exception e) {
			log.error("####### ERROR CertificacionSaldosAccion - validarValoresPDF() ##########"+ e);
			assertTrue("####### ERROR CertificacionSaldosAccion - validarValoresPDF() ##########"+ e,false);
		}
	}
	
	/************************FINALIZA ACCION VALIDAR VALORES PDF**********************************/
	/*===========================================================================================================*/
	
}
