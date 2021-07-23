package Runner;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.SnippetType;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(
		features = "src/test/resources/feature/AplicacionCierrePagaduria.feature", 					
		glue = "StepsDefinitions",
		tags = {"not @CarguePlanillaAlSistema and not @RecaudoPagaduria and not @PreaplicacionPagaduria and not @AplicacionFinalPagaduria and not @CierrePagaduria"},				
	    snippets = SnippetType.CAMELCASE
		)
public class AplicacionCierrePagaduriaRunner {

}