package Runner.OriginacionRunner;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.SnippetType;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(
		features = "src/test/resources/feature/OriginacionFeature/OriginacionCreditos.feature", 					
		glue = "StepsDefinitions",
		tags = {"@SimuladorAsesor,@SolicitudCredito"},
	    snippets = SnippetType.CAMELCASE
		)
public class OriginacionCreditosRunner {

}

//tags = {"@SimuladorAsesor,@SolicitudCredito,@AnalisisCredito,@ClientesBienvenida,@CreditosVisacion,@Desembolso"},
