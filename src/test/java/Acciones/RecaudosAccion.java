package Acciones;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.sql.ResultSet;
import java.text.NumberFormat;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import CommonFuntions.BaseTest;
import Consultas.CertificacionSaldoQuery;
import Consultas.PrepagoQuery;
import Pages.RecaudoPage;
import Pages.AplicacionCierrePage.PagoPreaplicacionPagoPage;

public class RecaudosAccion extends BaseTest{
	WebDriver driver;
	RecaudoPage recaudopage = new RecaudoPage(driver); 
	PanelPrincipalAccion panelprincipal;
	PagoPreaplicacionPagoPage pagopreaplicacionpagopage;
	CertificacionSaldoQuery query;
	PrepagoQuery queryRecaudo;
	private static Logger log = Logger.getLogger(RecaudosAccion.class);
	
	//variables Locales
	String valorRecaudo;
	static String ValorRecaudo;
	static String SumValoresRecibidos;
	

	public RecaudosAccion(WebDriver driver) {
		super(driver);
		panelprincipal = new PanelPrincipalAccion(driver);
		pagopreaplicacionpagopage = new PagoPreaplicacionPagoPage(driver);
		query = new CertificacionSaldoQuery();
		queryRecaudo = new PrepagoQuery();
	}
	
	/*NAVEGACION PRINCIPAL
	 * ingreso al modulo de recaudos
	 * */
	public void ingresarVentanaRecaudo() {
		panelprincipal.navegarRecaudo();
		adjuntarCaptura("ingresarVentanaRecaudo");
	}
	
	public void IngresaVentanaPagos() {
		panelprincipal.navegarPagos();
		adjuntarCaptura("ingresarVentanaRecaudo");
	}
	
	public void abrirCertificacionNavegador(String rutaDocumento, String numRadicado) {	
		log.info("********************* RecaudosAccion - abrirCertificacionNavegador()*********");
		ResultSet result = query.ConsultarRegistroCertificacion(String.valueOf(numRadicado)); 
		String  nombreDoc = "";
		try {
			while(result.next()) {
				nombreDoc = result.getString(1);	
			}		
			result.close();			
		} catch (Exception e) {
			log.error("####### ERROR CertificacionSaldosAccion - abrirCertificacionNavegador() ##########"+ e);
			assertTrue("####### ERROR CertificacionSaldosAccion - abrirCertificacionNavegador() ##########"+ e,false);
		}	
		abriPdfNavegador(rutaDocumento+nombreDoc);	
		adjuntarCaptura("nombreDoc");
	}
	
	public String consultarVlrTotal(String numRadicado, String rutadocumento) {
		String vlrTotal = "";
		ResultSet result = query.ConsultarRegistroCertificacion(String.valueOf(numRadicado)); 
		String  nombreDoc = "";
		try { 
			while(result.next()) {
				nombreDoc = result.getString(1);	
			}		
			result.close();			
		} catch (Exception e) {
			log.error("####### ERROR CertificacionSaldosAccion - CertificacionSaldoActivaCxcFianza() ##########"+ e);
			assertTrue("####### ERROR CertificacionSaldosAccion - CertificacionSaldoActivaCxcFianza() ##########"+ e,false);
		}	
		return vlrTotal = extraerValorPDF(rutadocumento, nombreDoc,"Total a pagar $ ");
	}
	
	public void seleccionarPagaduria(String vlrpago, String origen) {
		hacerClick(recaudopage.botonAddPagoRecaudo);
		ElementVisible();
		hacerClick(recaudopage.inputFecha);
		selectFechActualCalendario(recaudopage.contDiasCalendario,recaudopage.selectDia);
		hacerClick(recaudopage.inputValor);
		EscribirElemento(recaudopage.inputValor, vlrpago);
		ElementVisible();						
		hacerClick(recaudopage.radioPagaduria);
	}

	/*
	 * RECAUDO - MODULO RECAUDO
	 * pestana N/A
	 * se ejecutan las acciones para realizar el recaudo de un cliente, donde se ingresa la descripcion "TipoRecaudo", la cedula
	 * del cliente y el valor del recaudo que esta ingresando
	 * */
	public void recaudoCliente(String vlrRecaudo, String tipoRecaudo, String identitificacion, String numRadicado) {
		log.info("************* RecaudosAccion - RecaudoCliente()**********" + "VALOR " + vlrRecaudo + " TIPO " + tipoRecaudo);
		valorRecaudo = vlrRecaudo;
		try {
			hacerClick(recaudopage.botonAddPagoRecaudo);
			ElementVisible();
			hacerClick(recaudopage.inputFecha);
			selectFechActualCalendario(recaudopage.contDiasCalendario,recaudopage.selectDia);
			hacerClick(recaudopage.inputValor);
			EscribirElemento(recaudopage.inputValor, vlrRecaudo);
			ElementVisible();						
			hacerClick(recaudopage.checkCliente);
			hacerClick(recaudopage.checkCliente);
			ElementVisible();
			hacerClick(recaudopage.tipoPago);
			selectValorLista(recaudopage.contTipoPago,recaudopage.listTipoPago, tipoRecaudo);
			ElementVisible();			
			hacerClick(recaudopage.inputCedula);
			EscribirElemento(recaudopage.inputCedula, identitificacion);
			ElementVisible();			
			esperaExplicita(By.xpath("//span[contains(text(),'"+identitificacion+"')]"));
			hacerClick(By.xpath("//span[contains(text(),'"+identitificacion+"')]"));
			ElementVisible();
			hacerClick(recaudopage.selectCredito);
			//Esta condicion se diseña en el prepago por los tipos de pago
			//Si el numero de radicado es null significa que el credito es una certificacion
			if(numRadicado == null) {
				esperaExplicita(recaudopage.listCredito);
				hacerClick(recaudopage.listCredito);
			}
			//si contiene numero de radicado se esta realizando un prepago
			else {
				esperaExplicita(By.xpath("//*[starts-with(@id,'formulario-pagos-recaudo:j_idt') and contains(text(),'"+numRadicado+"')]"));
				hacerClick(By.xpath("//*[starts-with(@id,'formulario-pagos-recaudo:j_idt') and contains(text(),'"+numRadicado+"')]"));
			}			
			ElementVisible();
			adjuntarCaptura("recaudoCliente" + tipoRecaudo);
			hacerClick(recaudopage.botonGuardar);
			ElementVisible();
			assertTextonotificacion(recaudopage.notificacion, "Se registro correctamente el recaudo del pago");
			adjuntarCaptura("RecaudoRealizado" + tipoRecaudo);
		} catch (Exception e) {
			log.error("############## ERROR recaudoClienteCertificacion() ###########" + e);
			assertTrue("############## ERROR recaudoClienteCertificacion() ###########"+ e,false);
		}		
		
	}
	
	
	/*
	 * VALIDACION - BASE DE DATOS CAMBIO ESTADO
	 * pestana N/A
	 * se ejecutan las acciones para realizar la validacion del cambio de estado del Credito a PREPAGADO
	 * */
	public void validarEstadoCredito(String numRadicado, String estadoCredito) {
		ResultSet result = queryRecaudo.validarRecaudo(numRadicado, "PREPAGO"); 
		NumberFormat formatoNumero = NumberFormat.getNumberInstance();		
		String vlrDbRecaudo = "";
		String estadoDbCredito = "";
		try {
			while(result.next()) {
				vlrDbRecaudo = result.getString(1);	
				estadoDbCredito = result.getString(2);	
			}		
			result.close();		
			
			assertvalidarEquals(valorRecaudo.replace(",","."),vlrDbRecaudo);
			assertvalidarEquals(estadoCredito.toUpperCase(),estadoDbCredito);
			
		} catch (Throwable e) {
			log.error("####### ERROR RecaudosAccion - validarEstadoCredito() ##########"+ e);
			assertTrue("####### ERROR RecaudosAccion - validarEstadoCredito() ##########"+ e,false);
		}	
		
	}
	
	public void filtrosPreAplicacionPagos(String Pagaduria,String Ano,String Periodo) throws InterruptedException {
		esperaExplicita(pagopreaplicacionpagopage.ListPagaduria);
		hacerClick(pagopreaplicacionpagopage.ListPagaduria);
		EscribirElemento(pagopreaplicacionpagopage.FiltroPagaduria, Pagaduria);
		EnviarEnter(pagopreaplicacionpagopage.FiltroPagaduria);
		ElementVisible();
		hacerClick(pagopreaplicacionpagopage.Ano);
		selectValorLista(pagopreaplicacionpagopage.ListAno,Ano);
		ElementVisible();
		hacerClick(pagopreaplicacionpagopage.Periodo);		
		hacerClick(By.xpath("//li[text()='"+Periodo+"']"));
	}
	
	public void capturarValoresPreaplicacionPagos() {
		
		ValorRecaudo=GetText(pagopreaplicacionpagopage.ValorRecaudo).substring(0,GetText(pagopreaplicacionpagopage.ValorRecaudo).length()-2).replaceAll("[^a-zA-Z0-9]", "");
		SumValoresRecibidos=GetText(pagopreaplicacionpagopage.ValoresRecibidos).substring(0,GetText(pagopreaplicacionpagopage.ValoresRecibidos).length()-2).replaceAll("[^a-zA-Z0-9]", "");
		if(ValorRecaudo=="0") {
			assertTrue(true);
		}else {
			assertFalse(false);
		}
		
	}
	
	public void pestanarecaudo() {
		panelprincipal.navegarRecaudo();
	}
	
	public void Agregarpago(String Pagaduria,String Ano,String Periodo) {
		hacerClick(recaudopage.botonAddPagoRecaudo);
		ElementVisible();
		hacerClick(recaudopage.inputFecha);
		selectFechActualCalendario(recaudopage.contDiasCalendario,recaudopage.selectDia);
		ElementVisible();
		hacerClick(recaudopage.inputValor);
		EscribirElemento(recaudopage.inputValor, ValorRecaudo);
		ElementVisible();
		hacerClick(recaudopage.checkPagaduria);
		hacerClick(recaudopage.checkPagaduria);
		ElementVisible();
		hacerClick(recaudopage.listaPagaduria);
		ElementVisible();
		EscribirElemento(recaudopage.FiltroPagaduria, Pagaduria);
		EnviarEnter(recaudopage.FiltroPagaduria);
		ElementVisible();
		Clear(recaudopage.Ano);
		EscribirElemento(recaudopage.Ano,Ano);
		ElementVisible();
		hacerClick(recaudopage.RecaudoPeriodo);
		hacerClick(By.xpath("//li[text()='"+Periodo+"']"));
		ElementVisible();
		esperaExplicita(recaudopage.botonGuardarInfPago);
		hacerClick(recaudopage.botonGuardarInfPago);
		ElementVisible();
		assertTextonotificacion(recaudopage.notificacion,"Se registro correctamente el recaudo del pago");
		//
	}
	
}
