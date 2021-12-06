package Runner.OriginacionRunner;

import java.io.IOException;

import org.junit.runner.RunWith;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;

import CommonFuntions.BeforeSuite;
import CommonFuntions.DataToFeature;
import Runner.RunnerPersonalizado;
import cucumber.api.CucumberOptions;
import cucumber.api.SnippetType;

//@RunWith(Cucumber.class)
@CucumberOptions(
		features = "src/test/resources/feature/OriginacionFeature/OriginacionCompraCarteraSaneamiento.feature", 					
		glue = "StepsDefinitions",
		tags = {"@OriginacionCCS"},
	    snippets = SnippetType.CAMELCASE
		)

@RunWith(RunnerPersonalizado.class)
public class OriginacionCompraCarteraSaneamientoRunner {
	 @BeforeSuite
	    public static void test()
	            throws InvalidFormatException, IOException, org.apache.poi.openxml4j.exceptions.InvalidFormatException {
	        DataToFeature.overrideFeatureFiles("src/test/resources/feature/OriginacionFeature/OriginacionCompraCarteraSaneamiento.feature");
	    }
}

//tags = {"@OriginacionCCS,@SolicitudCreditoCCS,@AnalisisCreditoCCS,@ClientesBienvenidaCCS,@CreditosVisacionCCS,@DesembolsoCarteraCCS,@VisacionCarteraCCS,@DesembolsoSaneamientoCCS,@DesembolsoCCS,@ValidarDinamicasContablesOriginacion"},