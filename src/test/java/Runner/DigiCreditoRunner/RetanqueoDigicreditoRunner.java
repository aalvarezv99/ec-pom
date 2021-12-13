package Runner.DigiCreditoRunner;

import java.io.IOException;

import org.junit.runner.RunWith;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;

import CommonFuntions.BeforeSuite;
import CommonFuntions.DataToFeature;
import Runner.RunnerPersonalizado;
import cucumber.api.CucumberOptions;
import cucumber.api.SnippetType;
// import cucumber.api.junit.Cucumber;

// @RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/feature/DigiCredito/RetanqueoDigiCredito.feature",
                 glue = "StepsDefinitions",
                 tags = {"@ClientesBienvenidaRetanqueos" },
                 snippets = SnippetType.CAMELCASE)

@RunWith(RunnerPersonalizado.class)

public class RetanqueoDigicreditoRunner {
    @BeforeSuite
    public static void test()
            throws InvalidFormatException, IOException, org.apache.poi.openxml4j.exceptions.InvalidFormatException {
        DataToFeature.overrideFeatureFiles("src/test/resources/feature/DigiCredito/RetanqueoDigiCredito.feature");
    }
}
//@Retanqueo,@AnalisisCreditoRetanqueo,@ClientesBienvenidaRetanqueos,@CreditosVisacionRetanqueos,@DesembolsoRetanqueos
//tags = {"@SimuladorAsesor,@SolicitudCredito,@AnalisisCredito,@ClientesBienvenida"},