package Runner.OriginacionRunner;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.SnippetType;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(
		features = "src/test/resources/feature/OriginacionFeature/OriginacionCompraCartera.feature", 					
		glue = "StepsDefinitions",
		tags = {"@AnalisisCredito"},
	    snippets = SnippetType.CAMELCASE
		)

public class OriginacionCompraCarteraRunner {
	

}

//"@SimuladorAsesor,@SolicitudCredito,@AnalisisCredito,@ClientesBienvenida,@CreditosVisacion,@DesembolsoCartera,@VisacionCartera,@Desembolso"