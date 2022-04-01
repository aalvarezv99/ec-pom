package Acciones.CreditoAccion;

import static org.junit.Assert.assertTrue;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import Acciones.ComunesAccion.LoginAccion;
import Acciones.ComunesAccion.PanelPrincipalAccion;
import Archivo.LeerArchivo;
import CommonFuntions.BaseTest;
import Pages.ComunesPage.PanelNavegacionPage;
import Pages.CreditosPage.PagesCartaNotificaciones;
import Pages.CreditosPage.PagesClienteParaBienvenida;
import Pages.CreditosPage.PagesTareas;
import Pages.CreditosPage.RetanqueoPages;
import Pages.SolicitudCreditoPage.PestanaDigitalizacionPage;
import Pages.SolicitudCreditoPage.PestanaReferenciacionPage;
import Pages.SolicitudCreditoPage.PestanaSimuladorInternoPage;

public class RetanqueoMultipleAccion extends BaseTest {
	WebDriver driver;
	PanelPrincipalAccion panelnavegacionaccion;
	PanelNavegacionPage PanelNavegacionPage;
	RetanqueoPages retanqueopages;
	PestanaSimuladorInternoPage pestanasimuladorinternopage;
	PestanaDigitalizacionPage pestanadigitalizacionPage;
	PestanaReferenciacionPage pestanareferenciacionpage;
	PagesClienteParaBienvenida pagesclienteparabienvenida;
	PagesTareas pagestareas;
	Pages.SolicitudCreditoPage.pestanaSeguridadPage pestanaSeguridadPage;
	PagesCartaNotificaciones pagesCartaNotificaciones;
	LoginAccion loginaccion;
	LeerArchivo archivo;
	private static Logger log = Logger.getLogger(RetanqueoCreditos.class);
	RetanqueoCreditos retanqueoCreditos;

	public RetanqueoMultipleAccion(WebDriver driver) throws InterruptedException {
		super(driver);
		panelnavegacionaccion = new PanelPrincipalAccion(driver);
		PanelNavegacionPage = new PanelNavegacionPage(driver);
		retanqueopages = new RetanqueoPages(driver);
		loginaccion = new LoginAccion(driver);
		pestanasimuladorinternopage = new PestanaSimuladorInternoPage(driver);
		pestanadigitalizacionPage = new PestanaDigitalizacionPage(driver);
		pestanareferenciacionpage = new PestanaReferenciacionPage(driver);
		pagesclienteparabienvenida = new PagesClienteParaBienvenida(driver);
		pestanaSeguridadPage = new Pages.SolicitudCreditoPage.pestanaSeguridadPage(driver);
		pagestareas = new PagesTareas(driver);
		pagesCartaNotificaciones = new PagesCartaNotificaciones(driver);
		retanqueoCreditos = new RetanqueoCreditos(driver);
	}


	/************ INICIO ACCIONES RETANQUEO MULTIPLE ***************/
	 public void FiltrarCreditoMultiple(String Cedula, String Pagaduria) {
	 		log.info("************ Buscar credito a retanquear multiple, RetanqueoCreditos - FiltrarCredito()*****");
	 		try {
	 			BuscarenGrilla(retanqueopages.cedula, Cedula); 			
	 			ElementVisible();
	 			BuscarenGrilla(retanqueopages.Pagaduria, Pagaduria); 	
	 			ElementVisible();
	 			esperaExplicitaTexto(Pagaduria);
				retanqueoCreditos.listarCreditosPadre(retanqueopages.listaCreditosPadre);
	 			adjuntarCaptura("Se filtra el retanqueo multiple ");
	 		} catch (Exception e) {
	 			log.error("########## ERROR RetanqueoCreditos - FiltrarCreditoMultiple() ########" + e);
	 			assertTrue("########## ERROR RetanqueoCreditos - FiltrarCreditoMultiple() ########"+ e,false);
	 		}
	 	}
	 
	 public void RetanquearMultiples()  {
			log.info("*********Seleccionar creditos multiples , RetanqueoCreditos - Retanquear()******");
			try {
				ElementVisible();
				clickvarios(retanqueopages.ListCreditos);
				ElementVisible();
				hacerClicknotificacion();
				adjuntarCaptura("Se genera el retanqueo para el credito multiple ");
			} catch (Exception e) {
				log.error("########## ERROR RetanqueoCreditos - RetanquearMultiples() ########" + e);
				assertTrue("########## ERROR RetanqueoCreditos - RetanquearMultiples() ########"+ e,false);
			}
			
		}
	 
	public void LlenarMontoSolicitar(String Retanqueo) {
			log.info("*********Llenar Monto solicitar, RetanqueoCreditos - Retanquear()******");
			try {
				ElementVisible();
				hacerClicknotificacion();
				int ValorCreditos = (int)Double.parseDouble(TextoElemento(retanqueopages.SaldoTotalRecoger));

				if(ValorCreditos+Integer.parseInt(Retanqueo)<=ValorCreditos) {
					LimpiarConTeclado(retanqueopages.montoTotalSolicitado);
					EscribirElemento(retanqueopages.montoTotalSolicitado,String.valueOf(ValorCreditos+Integer.parseInt(Retanqueo)));
					hacerClick(retanqueopages.cedula);
					esperaExplicita(retanqueopages.notificacion);
					assertTextonotificacion(retanqueopages.notificacion,"no puede ser menor o igual al Saldo total a recoger");
					ElementVisible();
					assertTrue("El valor ingresado nunca debe ser menor o igual al “Saldo Total a Recoger”.", false);

				}else if(ValorCreditos+Integer.parseInt(Retanqueo)>120000000) {
				   LimpiarConTeclado(retanqueopages.montoTotalSolicitado);
				   EscribirElemento(retanqueopages.montoTotalSolicitado,String.valueOf(ValorCreditos+Integer.parseInt(Retanqueo)));
				   hacerClick(retanqueopages.cedula);
				   esperaExplicita(retanqueopages.notificacion);
				   assertTextonotificacion(retanqueopages.notificacion,"no puede ser mayor al Monto Máximo Autorizado");
				   ElementVisible();
				   assertTrue("El valor ingresado debe ser máximo $120.000.000", false);
				}
				ToleranciaPesoMensaje(" Saldo al dia creditos multiples", ValorCreditos,sumarListaValoresCreditos(retanqueopages.SaldoAldiaCreditosMultiples));
				LimpiarConTeclado(retanqueopages.montoTotalSolicitado);
				EscribirElemento(retanqueopages.montoTotalSolicitado,String.valueOf(ValorCreditos+Integer.parseInt(Retanqueo)));
				hacerClick(retanqueopages.cedula);		
				ElementVisible();
				adjuntarCaptura("RetanqueoMultiple");
				hacerClick(retanqueopages.BtnRetanqueoMultiple);
				ElementVisible();
			} catch (Exception e) {
				log.error("########## ERROR RetanqueoCreditos - LlenarMontoSolitar ########" + e);
				assertTrue("########## ERROR RetanqueoCreditos - LlenarMontoSolitar ########"+ e,false);
			}
			
		}
	
	/************ FIN ACCIONES RETANQUEO MULTIPLE    ***************/
	
	
}
