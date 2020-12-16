package pique.csharp.analysis;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Test;
import pique.model.Diagnostic;
import pique.model.Finding;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

public class RoslynatorLocTests {

    private final Path ROSLYN_ROOT = Paths.get("src/main/resources/Roslynator");
    private final Path MS_BUILD = Paths.get("C:/Program Files (x86)/Microsoft Visual " +
            "Studio/2019/Community/MSBuild/Current/Bin");
    private final Path TARGET = Paths.get("src/test/resources/projects/TestNetFramework");
    private final Path ROSLYN_OUTPUT = Paths.get("out/roslynator_loc_output.xml");

    @Test
    public void testRoslynatorLocAnalyze() {

        if (ROSLYN_OUTPUT.toFile().exists()) {
            try { FileUtils.forceDelete(ROSLYN_OUTPUT.toFile()); }
            catch (IOException e) { e.printStackTrace(); }
        }

        RoslynatorLoc analyzer = new RoslynatorLoc(ROSLYN_ROOT, MS_BUILD);
        Path analyzeOutPath = analyzer.analyze(TARGET);

        Assert.assertTrue(analyzeOutPath.toFile().exists());
    }

    @Test
    public void testRoslynatorLocParseAnalysis() {

        if (ROSLYN_OUTPUT.toFile().exists()) {
            try { FileUtils.forceDelete(ROSLYN_OUTPUT.toFile()); }
            catch (IOException e) { e.printStackTrace(); }
        }

        RoslynatorLoc analyzer = new RoslynatorLoc(ROSLYN_ROOT, MS_BUILD);
        Path analyzeOutPath = analyzer.analyze(TARGET);

        Assert.assertTrue(analyzeOutPath.toFile().exists());

        Map<String, Diagnostic> parsedAnalysis = analyzer.parseAnalysis(analyzeOutPath);

        Assert.assertEquals(1, parsedAnalysis.size());

        Assert.assertTrue(parsedAnalysis.containsKey("loc"));
        Assert.assertEquals(1, parsedAnalysis.get("loc").getNumChildren());
        Assert.assertTrue(parsedAnalysis.get("loc").getChildren().values().stream()
                .allMatch(findingNode -> findingNode.getValue() == 39));
    }

}
