package Prueba.Automation;

import java.text.Normalizer;

import org.apache.log4j.Logger;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;


public class PruebaCodigo {
	static int i =0;
	
	public static void main(String[] args) {
		//String valor = extraerValorPDF("C:\\Users\\User\\Downloads\\CertificacionSaldos\\","certificacion-saldo-47152.pdf","Total a pagar $");
		//System.out.println(valor);
		pruebaFormula();
	}
	
	public static void periodo(int vlr) {
		System.out.println((double)vlr/30);
		
		System.out.println((int)Math.ceil((double)vlr/30));
	}
	
	public static void pruebaFormula() {
		/*
		//plazo menos a mes dos
		System.out.println(Math.round(2020000*((Math.pow((1+0.018), (12)) )-1)/(0.018*Math.pow((1+0.018),(12)))));
		
		//plazo mayor a mes dos
		System.out.println(Math.round(
		2020000*((Math.pow((1+0.018), (49-1)))-1)/(0.018*Math.pow((1+0.018), (49-1)) )+
		(2020000*(( Math.pow((1+0.0075), (120-(49-1))) )-1)/(0.0075* Math.pow((1+0.0075), (120-(49-1))) ))/Math.pow((1+0.018), (49-1)) ));
		*/
		
		//Cuota > segundo mes
		System.out.println(Math.round(31077250/((Math.pow((1+0.018),(49-1)) -1)/(0.018*Math.pow((1+0.018), (49-1)))
				+((Math.pow((1+0.0075), (120-(49-1)))-1)/(0.0075*Math.pow((1+0.0075), (120-(49-1) )) ))/(Math.pow((1+0.018), (49-1))))));
		
		//cuota < segundo mes
		System.out.println(Math.round(31077250/((Math.pow((1+0.018), (12)) -1)/(0.018* Math.pow((1+0.018), (12))))));
		
		
	} 
	
	public static void prueba() {
		String[] valor = {"1","2","3","4","5","6","7","8","9","10","11","12"};
		
		System.out.println(valor.length);
		do {
			System.out.println(valor[i]);
			System.out.println(valor[i+1]);
			System.out.println(valor[i+2]);
			System.out.println(valor[i+3]);
			i = i+1;
			
		} while (i<=valor.length/4);
	}
	
	public static String extraerValorPDF(String ruta, String nombreDoc, String vlrBuscar) {
		String result = "";
		
		 try {
	        	//abrimos el PDF
	        	PdfReader reader = new PdfReader(ruta+ nombreDoc);	        		        	       
	        	//empezamos la coversion a pdf
	        	String page = limpiarCadena(PdfTextExtractor.getTextFromPage(reader, 1));	
	        	//Assert.assertTrue(page.toUpperCase().contains(vlrBuscar.toUpperCase()));
	        	if(page.toUpperCase().contains(vlrBuscar.toUpperCase())) {
	        		System.out.println(page);
	        	}
	        	 
	        }
	    		 
			 catch (Exception e) {			 
			}
		return result;
	}
	
	public static String limpiarCadena(String cadena) {
		cadena = Normalizer.normalize(cadena, Normalizer.Form.NFD);
		cadena = cadena.replaceAll("[^\\p{ASCII}]", "");
		return cadena;
	}

}
