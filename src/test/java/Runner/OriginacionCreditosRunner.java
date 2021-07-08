package Runner;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.SnippetType;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(
		features = "src/test/resources/feature/OriginacionCreditos.feature", 					
		glue = "StepsDefinitions",
		tags = {"@SimuladorAsesor"},
	    snippets = SnippetType.CAMELCASE
		)
public class OriginacionCreditosRunner {

}

//tags = {"@SimuladorAsesor,@SolicitudCredito,@AnalisisCredito,@ClientesBienvenida"},