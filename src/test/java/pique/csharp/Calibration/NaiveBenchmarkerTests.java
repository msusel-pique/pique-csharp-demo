package pique.csharp.Calibration;

import org.junit.Assert;
import org.junit.Test;
import pique.analysis.ITool;
import pique.calibration.IBenchmarker;
import pique.calibration.NaiveBenchmarker;
import pique.csharp.analysis.RoslynatorAnalyzer;
import pique.csharp.analysis.RoslynatorLoc;
import pique.model.QualityModel;
import pique.model.QualityModelImport;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class NaiveBenchmarkerTests {

    private final Path ROSLYN_ROOT = Paths.get("src/main/resources/Roslynator");
    private final Path MS_BUILD = Paths.get("C:/Program Files (x86)/Microsoft Visual Studio/2019/Community/MSBuild/Current/Bin");
    private final Path BENCHMARK_REPO = Paths.get("C:/Users/David/Repository/benchmark_projects");
    private final Path QM_FILE = Paths.get("src/test/resources/quality_models/qualityModel_full_description.json");

    @Test
    public void testPiqueDeriveThresholds() {

        // Set up
        String flag = ".sln";

        QualityModel qmDescription = new QualityModelImport(QM_FILE).importQualityModel();

        ITool roslynatorLoc = new RoslynatorLoc(ROSLYN_ROOT, MS_BUILD);
        ITool roslynator = new RoslynatorAnalyzer(ROSLYN_ROOT, MS_BUILD);
        Set<ITool> tools = Stream.of(roslynator, roslynatorLoc).collect(Collectors.toSet());

        IBenchmarker benchmarker = new NaiveBenchmarker();
        Map<String, Double[]> thresholds = benchmarker.deriveThresholds(BENCHMARK_REPO, qmDescription, tools,
                flag);

        // Assert
        Assert.assertTrue(thresholds.size() > 0);

    }
}
