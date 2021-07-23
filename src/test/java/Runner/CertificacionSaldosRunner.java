package Runner;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.SnippetType;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(
		features = "src/test/resources/feature/CertificacionSaldos.feature", 					
		glue = "StepsDefinitions",
		tags = {"@CertificacionSaldoActivaCXCFianza"},				
	    snippets = SnippetType.CAMELCASE
		)
public class CertificacionSaldosRunner {

}
