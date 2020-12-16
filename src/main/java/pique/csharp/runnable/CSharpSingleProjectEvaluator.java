package pique.csharp.runnable;

import pique.analysis.ITool;
import pique.csharp.analysis.RoslynatorAnalyzer;
import pique.csharp.analysis.RoslynatorLoc;
import pique.runnable.SingleProjectEvaluator;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Main method: audit the quality of a C#, .NET project or solution.
 */
public class CSharpSingleProjectEvaluator {

    public static void main(String[] args) {

        // C# stuff
        Path ROSLYN_RESOURCE_ROOT = Paths.get("src/main/resources/Roslynator");

        // Setup
        Properties properties = new Properties();
        try { properties.load((new FileInputStream(args[0]))); }
        catch (IOException e) { e.printStackTrace(); }

        // Initialize inputs
        Path qualityModelInput = Paths.get(properties.getProperty("qm.filepath"));
        Path target = Paths.get(properties.getProperty("project.root"));
        Path resultsDirectory = Paths.get(properties.getProperty("results.directory"));

        ITool roslynatorLoc = new RoslynatorLoc(ROSLYN_RESOURCE_ROOT, Paths.get(properties.getProperty("msbuild.bin")));
        ITool roslynator = new RoslynatorAnalyzer(ROSLYN_RESOURCE_ROOT, Paths.get(properties.getProperty("msbuild.bin")));
        Set<ITool> tools = Stream.of(roslynatorLoc, roslynator).collect(Collectors.toSet());

        Path jsonOutput = new SingleProjectEvaluator().runEvaluator(target, resultsDirectory, qualityModelInput, tools);
        System.out.println("Project or Solution quality assessment finished. You can find the file at "
                + jsonOutput.toAbsolutePath().toString());

    }

}
