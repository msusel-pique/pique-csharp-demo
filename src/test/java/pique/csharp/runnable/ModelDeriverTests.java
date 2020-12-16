package pique.csharp.runnable;

import org.junit.Test;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.nio.file.Path;
import java.nio.file.Paths;

public class ModelDeriverTests {

    private final Path CONFIGURATION = Paths.get("src/test/resources/config/quality_model_deriver.properties");

    /**
     * Not a real test (for now).  Just verify the ModelDeriver can run, and manually verify the output file.
     */
    @Test
    public void testDeriveModel() {
        ModelDeriver.main(new String[] { CONFIGURATION.toString() });
    }

}
