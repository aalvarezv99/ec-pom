package Runner;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.SnippetType;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(
		features = "src/test/resources/feature/RetanqueoCompraCartera.feature", 					
		glue = "StepsDefinitions",
		tags = {"@AnalisisCreditoRetanqueo,@ClientesBienvenida,@CreditosVisacion,@DesembolsoCartera,@VisacionCartera,@Desembolso"},
	    snippets = SnippetType.CAMELCASE
		)

public class RetanqueoCompraCarteraRunner {
	

}

//"@SimuladorAsesor,@SolicitudCredito,@AnalisisCredito,@ClientesBienvenida,@CreditosVisacion,@DesembolsoCartera,@VisacionCartera,@Desembolso"