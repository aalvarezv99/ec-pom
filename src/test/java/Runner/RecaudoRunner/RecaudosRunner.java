package Runner.RecaudoRunner;

import java.io.IOException;

import org.junit.runner.RunWith;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;

import CommonFuntions.BeforeSuite;
import CommonFuntions.DataToFeature;
import Runner.RunnerPersonalizado;
import cucumber.api.CucumberOptions;
import cucumber.api.SnippetType;

@CucumberOptions(
		features = "src/test/resources/feature/RecaudoFeature/Recaudos.feature", 					
		glue = "StepsDefinitions",
		tags = {"@RecaudoDebitoAutomatico"},				
	    snippets = SnippetType.CAMELCASE
		)
@RunWith(RunnerPersonalizado.class)

public class RecaudosRunner {
@BeforeSuite
public static void test()
	         throws InvalidFormatException, IOException, org.apache.poi.openxml4j.exceptions.InvalidFormatException {
	     DataToFeature.overrideFeatureFiles("src/test/resources/feature/RecaudoFeature/Recaudos.feature");
 }
}