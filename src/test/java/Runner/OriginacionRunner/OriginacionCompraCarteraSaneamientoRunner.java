package Runner.OriginacionRunner;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.SnippetType;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(
		features = "src/test/resources/feature/OriginacionFeature/OriginacionCompraCarteraSaneamiento.feature", 					
		glue = "StepsDefinitions",
		tags = {"@SimuladorAsesor"},
	    snippets = SnippetType.CAMELCASE
		)
public class OriginacionCompraCarteraSaneamientoRunner {

}


//tags = {"@SimuladorAsesor,@SolicitudCredito,@AnalisisCredito,@ClientesBienvenida,@CreditosVisacion,@DesembolsoCartera,@VisacionCartera,@DesembolsoSaneamiento,@Desembolso"},