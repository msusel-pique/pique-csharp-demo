package pique.csharp.runnable;

import org.junit.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

public class CSharpSingleProjectEvaluatorTests {

    private final Path CONFIGURATION_DERIVE = Paths.get("src/test/resources/config/quality_model_full_deriver.properties");
    private final Path CONFIGURATION_ASSESS = Paths.get("src/test/resources/config/quality_model_full.properties");

    /**
     * Not a real test (for now).  Just verify the QualityModelDeriver can run, and manually verify the output file.
     */
    @Test
    public void testAssessProject() {

        // First, derive a model to use for assessment (copy and paste from QualityModelDeriverTests)...
        QualityModelDeriver.main(new String[] { CONFIGURATION_DERIVE.toString() });

        // Run assessment using the freshly derived model. (This temporary test makes assumptions of where the quality
        // model files are placed as defined in the .properties files)
        CSharpSingleProjectEvaluator.main(new String[] { CONFIGURATION_ASSESS.toString() });
    }
}
