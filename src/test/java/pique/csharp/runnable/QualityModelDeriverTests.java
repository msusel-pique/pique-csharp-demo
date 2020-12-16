package pique.csharp.runnable;

import org.junit.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

public class QualityModelDeriverTests {

    private final Path CONFIGURATION = Paths.get("src/test/resources/config/quality_model_full_deriver.properties");

    /**
     * Not a real test (for now).  Just verify the QualityModelDeriver can run, and manually verify the output file.
     */
    @Test
    public void testDeriveModel() {
        QualityModelDeriver.main(new String[] { CONFIGURATION.toString() });
    }

}
