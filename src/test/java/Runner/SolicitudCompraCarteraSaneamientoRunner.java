package Runner;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.SnippetType;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(
		features = "src/test/resources/feature/SolicitudCompraCarteraSaneamiento.feature", 					
		glue = "StepsDefinitions",
		tags = {"@SimuladorAsesor,@SolicitudCredito,@AnalisisCredito,@ClientesBienvenida,@CreditosVisacion,@DesembolsoCartera,@VisacionCartera,@DesembolsoSaneamiento,@Desembolso"},
	    snippets = SnippetType.CAMELCASE
		)
public class SolicitudCompraCarteraSaneamientoRunner {

}


//tags = {"@SimuladorAsesor,@SolicitudCredito,@AnalisisCredito,@ClientesBienvenida,@CreditosVisacion,@DesembolsoCartera,@VisacionCartera,@DesembolsoSaneamiento,@Desembolso"},