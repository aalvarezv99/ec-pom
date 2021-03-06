package Runner.RetanqueoRunner;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.SnippetType;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(
		features = "src/test/resources/feature/RetanqueoFeature/RetanqueoCompraCartera.feature", 					
		glue = "StepsDefinitions",
		tags = {"@AnalisisCreditoRetanqueo"},
	    snippets = SnippetType.CAMELCASE
		)

public class RetanqueoCompraCarteraRunner {
	

}

//"@SimuladorAsesor,@SolicitudCredito,@AnalisisCredito,@ClientesBienvenida,@CreditosVisacion,@DesembolsoCartera,@VisacionCartera,@Desembolso"