package at.htl.baumschule.boundary;

import com.intuit.karate.junit5.Karate;
import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class FeatureRunner {

    @Karate.Test
    Karate startTests() {
        return Karate.run("src/test/java/at/htl/baumschule/boundary/features/test-orchestrator.feature");
    }
}
