package Prueba.Automation;

import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.TemporalAdjusters;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;

public class PruebaCodigo {
	static int i =0;
	
	public static void main(String[] args) throws ParseException {
		//String valor = extraerValorPDF("C:\\Users\\User\\Downloads\\CertificacionSaldos\\","certificacion-saldo-47152.pdf","Total a pagar $");
		//System.out.println(valor);
	//	pruebaFormula();
		System.out.println(countDistinctIntegers(7));
	}
	
	 public static int countDistinctIntegers(int n) {
		    // Write your code here.
		   List<Integer> listaNumeros = new ArrayList<Integer>();		       
		       for(int i=1;i<=n;i++){
		           if(n%i==1){
		             listaNumeros.add(n-i);
		           }
		       }
		       
		    for(int variable:listaNumeros) {
		    	for(int j=1;j<=variable;j++) {		    		
			    	if(variable%j==1) {			    		
			    	    System.out.println(n-j);
			    	}			    
		    				    		
			    }
		    }   
		    
		       		       
		       return listaNumeros.size();
      }
	
	public static int getUniqueCharacter(String s) {
	    // Write your code here
	      Map<Character,Integer> count = new HashMap<>();
	      int n = s.length();
	      
	      for(int i=0;i<n;i++){
	          char c = s.charAt(i);
	          int freq = count.getOrDefault(c, 0);
	          count.put(c,(freq+1));
	      }
	      
	      for(int j = 0;j<n;j++){
	          if (count.get(s.charAt(j))==1) {
	          return j+1;
	          }
	      }
	      return -1;
	      
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
	
	public static void pruebaFormula() throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        Date date = sdf.parse("2021/02/15");

        Calendar calendar = Calendar.getInstance();  
        calendar.setTime(date);         
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));       

        System.out.println("First Day Of Month : " + calendar.getActualMinimum(Calendar.DAY_OF_MONTH));  
        System.out.println("Last Day of Month  : " + calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        System.out.println("Ultimo dia del mes: "+sdf.format(calendar.getTime()));        
		
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
