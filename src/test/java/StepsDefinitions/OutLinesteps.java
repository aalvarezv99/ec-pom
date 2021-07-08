package StepsDefinitions;

import org.apache.log4j.Logger;

import cucumber.api.java.es.Cuando;
import cucumber.api.java.es.Dado;
import cucumber.api.java.es.Entonces;

public class OutLinesteps {
	Logger log = Logger.getLogger(OutLinesteps.class);

	@Dado("Un usuario en abacus")
	public void unUsuarioEnAbacus() {
		// Write code here that turns the phrase above into concrete actions
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
}