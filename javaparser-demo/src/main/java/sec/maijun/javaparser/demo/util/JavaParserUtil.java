package sec.maijun.javaparser.demo.util;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParserConfiguration;
import com.github.javaparser.symbolsolver.JavaSymbolSolver;

import java.io.IOException;
import java.util.List;

public class JavaParserUtil {
    public static JavaParser genBasicJavaParser() {
        return new JavaParser();
    }

    public static JavaParser genJavaParserWithTypeSolver(String sourcePath, List<String> dependencies) throws IOException {
        var typeSolver = SolverUtil.generateTypeSolver(sourcePath, dependencies);
        var symbolSolver = new JavaSymbolSolver(typeSolver);
        var configuration = new ParserConfiguration();
        configuration.setSymbolResolver(symbolSolver);
        var parser = new JavaParser(configuration);
        return parser;
    }
}
