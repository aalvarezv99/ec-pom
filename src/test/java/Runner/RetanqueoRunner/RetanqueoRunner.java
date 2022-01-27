package Runner.RetanqueoRunner;

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
@CucumberOptions(features = "src/test/resources/feature/RetanqueoFeature/Retanqueo.feature",
                 glue = "StepsDefinitions",
                 tags = {"@DesembolsoRetanqueos"},
                 snippets = SnippetType.CAMELCASE)

@RunWith(RunnerPersonalizado.class)

public class RetanqueoRunner {
    @BeforeSuite
    public static void test()
            throws InvalidFormatException, IOException, org.apache.poi.openxml4j.exceptions.InvalidFormatException {
        DataToFeature.overrideFeatureFiles("src/test/resources/feature/RetanqueoFeature/Retanqueo.feature");
    }
}
//@Retanqueo,@AnalisisCreditoRetanqueo,@ClientesBienvenidaRetanqueos,@CreditosVisacionRetanqueos,@DesembolsoRetanqueos,@ValidarDinamicasContablesRetanqueoHijo,@ValidarDinamicasContablesRetanqueoPadre
//tags = {"@SimuladorAsesor,@SolicitudCredito,@AnalisisCredito,@ClientesBienvenida"},