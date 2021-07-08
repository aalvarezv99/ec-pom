package Runner;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.SnippetType;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(
features = "src/test/resources/feature/OutLine.feature",
glue = "StepsDefinitions",
tags = "@Prueba",
   snippets = SnippetType.CAMELCASE
)
public class OutlineRunner {

}