package pique.csharp.analysis;

import org.junit.Assert;
import org.junit.Test;
import pique.model.Diagnostic;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

public class RoslynatorTests {

    private final Path ROSLYN_ROOT = Paths.get("src/main/resources/Roslynator");
    private final Path MS_BUILD = Paths.get("C:/Program Files (x86)/Microsoft Visual " +
            "Studio/2019/Community/MSBuild/Current/Bin");
    private final Path TARGET = Paths.get("src/test/resources/projects/TestNetFramework");

    @Test
    public void testRoslynatorAnalyze() {

        RoslynatorAnalyzer analyzer = new RoslynatorAnalyzer(ROSLYN_ROOT, MS_BUILD);
        Path analyzeOutPath = analyzer.analyze(TARGET);

        Assert.assertTrue(analyzeOutPath.toFile().exists());
    }

    @Test
    public void testRoslynatorParseAnalysis() {
        RoslynatorAnalyzer analyzer = new RoslynatorAnalyzer(ROSLYN_ROOT, MS_BUILD);
        Path analyzeOutPath = analyzer.analyze(TARGET);

        Assert.assertTrue(analyzeOutPath.toFile().exists());

        Map<String, Diagnostic> parsedAnalysis = analyzer.parseAnalysis(analyzeOutPath);

        Assert.assertEquals(3, parsedAnalysis.size());

        Assert.assertTrue(parsedAnalysis.containsKey("RCS1163"));
        Assert.assertTrue(parsedAnalysis.containsKey("RCS1018"));
        Assert.assertTrue(parsedAnalysis.containsKey("SCS0005"));

        Assert.assertEquals(1, parsedAnalysis.get("RCS1163").getNumChildren());
        Assert.assertEquals(1, parsedAnalysis.get("RCS1018").getNumChildren());
        Assert.assertEquals(1, parsedAnalysis.get("SCS0005").getNumChildren());
    }

}
