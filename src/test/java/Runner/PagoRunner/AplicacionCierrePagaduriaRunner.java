package Runner.PagoRunner;

import java.io.IOException;
import org.junit.runner.RunWith;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import CommonFuntions.BeforeSuite;
import CommonFuntions.DataToFeature;
import Runner.RunnerPersonalizado;
import cucumber.api.CucumberOptions;
import cucumber.api.SnippetType;

//@CarguePlanillaAlSistema, @RecaudoPagaduria, @PreaplicacionPagaduria, @AplicacionFinalPagaduria, @ValidarDinamicasContablesAPLPAG, @CierrePagaduria

//@RunWith(Cucumber.class)
@CucumberOptions(
		features = "src/test/resources/feature/PagosFeature/AplicacionCierrePagaduria.feature", 					
		glue = "StepsDefinitions",
		tags = {"@CarguePlanillaAlSistema, @RecaudoPagaduria, @PreaplicacionPagaduria, @AplicacionFinalPagaduria, @ValidarDinamicasContablesAPLPAG, @CierrePagaduria"},
	    snippets = SnippetType.CAMELCASE
		)
@RunWith(RunnerPersonalizado.class)

public class AplicacionCierrePagaduriaRunner {
 @BeforeSuite
 public static void test()
         throws InvalidFormatException, IOException, org.apache.poi.openxml4j.exceptions.InvalidFormatException {
     DataToFeature.overrideFeatureFiles("src/test/resources/feature/PagosFeature/AplicacionCierrePagaduria.feature");
 }
}