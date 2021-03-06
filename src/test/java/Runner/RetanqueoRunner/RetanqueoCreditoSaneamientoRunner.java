package Runner.RetanqueoRunner;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.SnippetType;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(
		features = "src/test/resources/feature/RetanqueoFeature/RetanqueoCreditoSaneamiento.feature", 					
		glue = "StepsDefinitions",
		tags = {"@AnalisisCreditoRetanqueoSaneamiento"},
	    snippets = SnippetType.CAMELCASE
		)
public class RetanqueoCreditoSaneamientoRunner {

}

//tags = {"@RetanqueoCreditoSaneamiento,@AnalisisCreditoRetanqueoSaneamiento,@ClientesBienvenidaRetanqueosRetanqueoSaneamiento,@VisacionRetanqueosSaneamiento,@DesembolsoSaneamiento,@DesembolsoRetanqueoRemanente"},