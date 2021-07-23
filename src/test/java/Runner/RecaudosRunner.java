package Runner;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.SnippetType;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(
		features = "src/test/resources/feature/Recaudos.feature", 					
		glue = "StepsDefinitions",
		tags = {"not @RecaudoCertificacionSaldos and not @RecaudoPrepagoCredito and not @RecaudoPagaduria"},				
	    snippets = SnippetType.CAMELCASE
		)
public class RecaudosRunner {

}
