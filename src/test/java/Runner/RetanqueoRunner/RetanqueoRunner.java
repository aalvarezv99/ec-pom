package Runner.RetanqueoRunner;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.SnippetType;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(
		features = "src/test/resources/feature/RetanqueoFeature/Retanqueo.feature", 					
		glue = "StepsDefinitions",
		tags = {"@ClientesBienvenidaRetanqueos,@CreditosVisacionRetanqueos,@DesembolsoRetanqueos"},
	    snippets = SnippetType.CAMELCASE
		)
public class RetanqueoRunner {
   
}
//@Retanqueo,@AnalisisCreditoRetanqueo,@ClientesBienvenidaRetanqueos,@CreditosVisacionRetanqueos,@DesembolsoRetanqueos
//tags = {"@SimuladorAsesor,@SolicitudCredito,@AnalisisCredito,@ClientesBienvenida"},