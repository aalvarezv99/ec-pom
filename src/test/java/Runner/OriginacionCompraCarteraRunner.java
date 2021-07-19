package Runner;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.SnippetType;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(
		features = "src/test/resources/feature/OriginacionCompraCartera.feature", 					
		glue = "StepsDefinitions",
		tags = {"@SimuladorAsesor,@SolicitudCredito,@AnalisisCredito,@ClientesBienvenida,@CreditosVisacion,@DesembolsoCartera,@VisacionCartera,@Desembolso"},
	    snippets = SnippetType.CAMELCASE
		)

public class OriginacionCompraCarteraRunner {
	

}

//"@SimuladorAsesor,@SolicitudCredito,@AnalisisCredito,@ClientesBienvenida,@CreditosVisacion,@DesembolsoCartera,@VisacionCartera,@Desembolso"