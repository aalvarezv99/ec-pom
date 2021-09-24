package StepsDefinitions.CommunSteps;


import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;


import cucumber.api.java.es.Cuando;
import cucumber.api.java.es.Dado;
import cucumber.api.java.es.Entonces;
import io.cucumber.datatable.DataTable;

public class OutLinesteps {
	Logger log = Logger.getLogger(OutLinesteps.class);
	
	 
	  
	@Dado("Un usuario en abacus")
	public void unUsuarioEnAbacus(DataTable tabla)
	{
		/*List<List<String>> data = tabla.asLists(String.class);
		data.forEach((datos)->{
			log.info("Imprimiento - " + limpiarCadena(datos.toString()));
		});*/
		
		try {
			List<Map<String, String>> data = tabla.asMaps(String.class, String.class);
			int contador = 0;
			String tipo = "";
			String valor = "";
			for (Map<String, String> e : data) {
				
				//if(contador>=1) {
					 tipo = e.get("tipo");
					 valor =e.get("valor") ;
				//}
				log.info(tipo);
				log.info(valor);
				contador = contador+1;
			}
			log.info(contador);
			
		} catch (Exception e) {
			log.error("################### ERROR " + e);
		}
		
	}

	@Cuando("^Ingrese el correo (.*)$")
	public void ingreseElCorreo(String data) {
		log.info(data);
	}

	@Entonces("^e ingrese la contrasena (.*)$")
	public void eIngreseLaContrasenaSuaita(String Data) {
		// Write code here that turns the phrase above into concrete actions
		log.info(Data);
	}
	
	public String limpiarCadena(String cadena) {
		cadena = Normalizer.normalize(cadena, Normalizer.Form.NFD);
		cadena = cadena.replaceAll("[^\\p{ASCII}]", "");
		return cadena;
	}
	
	public class DataCartera{
		public String tipo;
		public String valor;
		
		public DataCartera(String tipo) {
			this.tipo = tipo;
		}
		
	
	}
}