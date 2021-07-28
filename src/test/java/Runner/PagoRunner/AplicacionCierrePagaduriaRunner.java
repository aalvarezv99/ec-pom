package Runner.PagoRunner;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.SnippetType;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(
		features = "src/test/resources/feature/PagosFeature/AplicacionCierrePagaduria.feature", 					
		glue = "StepsDefinitions",
		tags = {"@CarguePlanillaAlSistema, @RecaudoPagaduria,@PreaplicacionPagaduria, @AplicacionFinalPagaduria,@CierrePagaduria"},				
	    snippets = SnippetType.CAMELCASE
		)
public class AplicacionCierrePagaduriaRunner {

}

//and @RecaudoPagaduria and @PreaplicacionPagaduria and @AplicacionFinalPagaduria and @CierrePagaduria