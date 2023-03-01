package sec.maijun.javaparser.demo.ast;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseResult;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.stmt.Statement;

/**
 * JavaParser 最基本的功能，这里只是列了一小部分，在基本的AST生成层面，除了下面的例子，还有：
 * 1. parse 文件时，有各种不同的输入；
 * 2. parse AST节点信息，还支持直接 parse Block，Import、SimpleName 等，只是用得不常见。
 */
public class JavaParserExample {
    public static void main(String[] args) {
        System.out.println("parse complete java file:");
        parseCompilationUnit();

        System.out.println("parse expression:");
        parseExpression();

        System.out.println("parse statement:");
        parseStatement();
    }

    /**
     * 用来针对单 Java 文件生成 AST 的测试用例。
     *
     * 输入可以是一个文件（路径格式、File、Path 等），也可以直接是 文件内容、IO流 等，基于需要，可以设置源码的编码方式
     *
     * 这里简化测试，直接输入文件的内容
     */
    public static void parseCompilationUnit() {
        String content = """
                import java.util.List;
                import java.util.ArrayList;
                
                public class Hello {
                    public static void main(String[] args) {
                        List<String> lst = new ArrayList<>();
                        lst.forEach(s -> {
                            System.out.println(s);
                        });
                    }
                }
                """;

        ParseResult<CompilationUnit> result = new JavaParser().parse(content);
        System.out.println(result.isSuccessful());  // 直接校验是否生成AST成功
        System.out.println(result.getResult().get() != null); // 获取到的AST内容，判断是否为空
        System.out.println(result.getResult().get().getClass().getName()); // 获取到的AST类型，这里是 CompilationUnit
    }

    public static void parseExpression() {
        String content = """
                new ArrayList<>()
                """;
        ParseResult<Expression> result = new JavaParser().parseExpression(content);
        System.out.println(result.isSuccessful()); // 直接校验是否生成AST成功
        System.out.println(result.getResult().get() != null); // 获取到的AST内容，判断是否为空
        System.out.println(result.getResult().get().getClass().getName()); // 获取到的AST类型，这里是 ObjectCreationExpr
    }

    public static void parseStatement() {
        String content = """
                List<String> lst = new ArrayList<>();
                """;
        ParseResult<Statement> result = new JavaParser().parseStatement(content);
        System.out.println(result.isSuccessful()); // 直接校验是否生成AST成功
        System.out.println(result.getResult().get() != null); // 获取到的AST内容，判断是否为空
        System.out.println(result.getResult().get().getClass().getName()); // 获取到的AST类型，这里是 ExpressionStmt
    }
}
