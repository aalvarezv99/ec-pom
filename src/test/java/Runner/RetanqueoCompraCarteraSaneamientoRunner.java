package Runner;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.SnippetType;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(
		features = "src/test/resources/feature/RetanqueoCompraCarteraSaneamiento.feature", 					
		glue = "StepsDefinitions",
		tags = {"@RetanqueoCompraCarteraSaneamiento,@AnalisisCreditoRetanqueoCarteraSaneamiento,@ClientesBienvenidaRetanqueosRetanqueoCarteraSaneamiento,@CreditosVisacionRetanqueosCarteraSaneamiento,@DesembolsoCarteraCarteraSaneamiento,@VisacionCarteraCarteraSaneamiento,@DesembolsoSaneamientoCarteraSaneamiento,@DesembolsoRetanqueosCarteraSaneamiento"},
	    snippets = SnippetType.CAMELCASE
		)
public class RetanqueoCompraCarteraSaneamientoRunner {
}
