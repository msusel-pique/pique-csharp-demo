package pique.csharp.runnable;

import pique.analysis.ITool;
import pique.csharp.analysis.RoslynatorAnalyzer;
import pique.csharp.analysis.RoslynatorLoc;
import pique.model.QualityModel;
import pique.model.QualityModelExport;
import pique.model.QualityModelImport;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Main method: perform configuration set up, then call `deriveModel()` from Pique.
 */
public class ModelDeriver {

    public static void main(String[] args) {

        // C# specific stuff
        Path ROSLYN_RESOURCE_ROOT = Paths.get("src/main/resources/Roslynator");

        // Setup
        Properties properties = new Properties();
        try { properties.load((new FileInputStream(args[0]))); }
        catch (IOException e) { e.printStackTrace(); }

        // Initialize inputs
        QualityModelImport qmImport = new QualityModelImport(Paths.get(properties.getProperty("qm.filepath")));
        QualityModel qmDescription = qmImport.importQualityModel();

        ITool roslynatorLoc = new RoslynatorLoc(ROSLYN_RESOURCE_ROOT, Paths.get(properties.getProperty("msbuild.bin")));
        ITool roslynator = new RoslynatorAnalyzer(ROSLYN_RESOURCE_ROOT, Paths.get(properties.getProperty("msbuild.bin")));
        Set<ITool> tools = Stream.of(roslynatorLoc, roslynator).collect(Collectors.toSet());

        Path benchmarkRepository = Paths.get(properties.getProperty("benchmark.repo"));
        Path qmOutputLocation = Paths.get(properties.getProperty("qm.output"));
        String projectRootFlag = properties.getProperty("target.flag");

        // Run derivation process
        QualityModel derivedQM = pique.runnable.QualityModelDeriver.deriveModel(
                qmDescription, tools, benchmarkRepository, projectRootFlag);

        // Output to file
        Path jsonOutput = new QualityModelExport(derivedQM).exportToJson("qm_derived", qmOutputLocation);

        System.out.println("Quality Model derivation finished. You can find the file at " + jsonOutput.toAbsolutePath().toString());

    }
}
