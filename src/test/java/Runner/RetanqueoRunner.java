package Runner;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.SnippetType;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(
		features = "src/test/resources/feature/Retanqueo.feature", 					
		glue = "StepsDefinitions",
		tags = {"not @Retanqueo"},
	    snippets = SnippetType.CAMELCASE
		)
public class RetanqueoRunner {

}

//tags = {"@SimuladorAsesor,@SolicitudCredito,@AnalisisCredito,@ClientesBienvenida"},