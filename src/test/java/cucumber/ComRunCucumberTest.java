package cucumber;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"src/test/resources/"}
        , glue = "cucumber",
        plugin = {"html:target/cucumber-report.html"}
)
public class ComRunCucumberTest {
}
