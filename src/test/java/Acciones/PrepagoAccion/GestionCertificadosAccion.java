package Acciones.PrepagoAccion;

import static org.junit.Assert.assertTrue;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import CommonFuntions.BaseTest;
import Pages.Prepago.GestionCertificacionPage;

public class GestionCertificadosAccion extends BaseTest{
	private static Logger log = Logger.getLogger(GestionCertificadosAccion.class);
	GestionCertificacionPage gestioncertificacionpage = new GestionCertificacionPage(driver);
	public GestionCertificadosAccion(WebDriver driver) {
		super(driver);
	}
	
	/*
	 * PREPAGO - MODULO GESTION CERTIFICACION DE SALDOS
	 * pestana N/A
	 * metodo que aplica el filtro a la tabla mostrada en la pantalla con el numero de cedula y radicado
	 * para asi posteriormente descargar el documento el cual sera almacenado en la siguiente ruta 
	 * C:\Users\User\Downloads\CertificacionSaldos
	 * en caso de no contar con ella por favor crearla
	 * */
	public void descargarCertificadoPdf(String numRadicado, String numCedula) {
		log.info("************** GestionCertificadosAccion - descargarCertificadoPdf() ************");
		try {
			hacerClick(gestioncertificacionpage.inputFechaSolicitud);
			ElementVisible();
			selectFechActualCalendario(gestioncertificacionpage.contFecha, gestioncertificacionpage.listDias);
			ElementVisible();
			hacerClick(gestioncertificacionpage.inputNumCredito);
			EscribirElemento(gestioncertificacionpage.inputNumCredito, numRadicado);
			ElementVisible();				
			hacerClick(gestioncertificacionpage.inputCedula);
			EscribirElemento(gestioncertificacionpage.inputCedula, numCedula);			
			ElementVisible();
			contarFilasTablas(gestioncertificacionpage.countFilas);
			hacerClick(gestioncertificacionpage.botonDescargar);
		} catch (Exception e) {			
			log.error("########### ERROR - descargarCertificadoPdf() ###########" + e);
			assertTrue("########### ERROR - descargarCertificadoPdf() ###########"+ e,false);
		}
		adjuntarCaptura("descargarCertificadoPdf");
		
	}
}
