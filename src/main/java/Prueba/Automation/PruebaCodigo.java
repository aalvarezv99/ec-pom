package Prueba.Automation;

import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;

import jdk.internal.org.jline.utils.Log;


public class PruebaCodigo {
	static int i =0;
	
	public static void main(String[] args) {
		//String valor = extraerValorPDF("C:\\Users\\User\\Downloads\\CertificacionSaldos\\","certificacion-saldo-47152.pdf","Total a pagar $");
		//System.out.println(valor);
	//	pruebaFormula();
		fecha();
	}
	
	public static void fecha() {
		/*Calendar cal = Calendar.getInstance();
		cal.set(2021, 10, 12);
		Date date = cal.getTime();
		System.out.println(date);*/
		

		
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	        Date date = sdf.parse("2021-12-15");

	        Calendar calendar = Calendar.getInstance();  
	        calendar.setTime(date);         
	        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));       

	        System.out.println("First Day Of Month : " + calendar.getActualMinimum(Calendar.DAY_OF_MONTH));  
	        System.out.println("Last Day of Month  : " + calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
	        System.out.println("Ultimo dia del mes: "+sdf.format(calendar.getTime()));
	        
	        
		} catch (Exception e) {
			System.out.println(e);
		}
		
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
		//montoSoli/(1+porEstudioCre*iva+tasaFianza*iva+tasaXmillon/1000000*periodoPrima)*tasaFianza*iva;
		// 38096808/(1+(0.0*1.19)+(8.35*1.19)+(4625/1000000)*17);//*8.35*1.19;
		double valor = 38096808/(1+((0.0/100)*1.19)+((8.35/100)*1.19)+((double)4625/1000000)*17)*(8.35/100)*1.19;
		System.out.println( Math.round(valor) );
		
		System.out.println( (double)4625);
		
		//Cuota > segundo mes
		/*System.out.println(Math.round(31077250/((Math.pow((1+0.018),(49-1)) -1)/(0.018*Math.pow((1+0.018), (49-1)))
				+((Math.pow((1+0.0075), (120-(49-1)))-1)/(0.0075*Math.pow((1+0.0075), (120-(49-1) )) ))/(Math.pow((1+0.018), (49-1))))));
		
		//cuota < segundo mes
		System.out.println(Math.round(31077250/((Math.pow((1+0.018), (12)) -1)/(0.018* Math.pow((1+0.018), (12))))));*/
		
		
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
