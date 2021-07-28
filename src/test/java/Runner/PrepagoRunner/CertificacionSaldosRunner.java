package Runner.PrepagoRunner;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.SnippetType;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(
		features = "src/test/resources/feature/PrepagoFeature/CertificacionSaldos.feature", 					
		glue = "StepsDefinitions",
		tags = {"@CertidicacionSaldoActivaCXCFianza"},				
	    snippets = SnippetType.CAMELCASE
		)
public class CertificacionSaldosRunner {

}
