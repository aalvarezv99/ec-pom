package Runner;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.SnippetType;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(
		features = "src/test/resources/feature/AplicacionCierrePagaduria.feature", 					
		glue = "StepsDefinitions",
		tags = {"@CarguePlanillaAlSistema, @RecaudoPagaduria, @PreaplicacionPagaduria, @AplicacionFinalPagaduria"},				
	    snippets = SnippetType.CAMELCASE
		)
public class AplicacionCierrePagaduriaRunner {

}
//@CarguePlanillaAlSistema, @RecaudoPagaduria, @PreaplicacionPagaduria, @AplicacionFinalPagaduria