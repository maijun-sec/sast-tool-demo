package sec.maijun.javaparser.demo.ast;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseResult;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.stmt.Statement;
import org.junit.Test;



public class TestCase {
    @Test
    public void test_lambda_ast() {
        String content = """
            lst.stream().forEach(s -> {
                System.out.println(s);
            });
        """;
        ParseResult<Statement> result = new JavaParser().parseStatement(content);
        System.out.println(result.getResult().get());
    }
}
