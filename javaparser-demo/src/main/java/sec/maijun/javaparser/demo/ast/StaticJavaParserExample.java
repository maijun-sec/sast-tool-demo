package sec.maijun.javaparser.demo.ast;

import com.github.javaparser.StaticJavaParser;

/**
 * StaticJavaParser 就是把 JavaParser 封装了一下，看这个例子之前，请先看 JavaParserExample
 *
 * @since 2023-03-01
 * @author maijun
 */
public class StaticJavaParserExample {
    public static void main(String[] args) {
        System.out.println("parse complete java file:");
        parseCompilationUnit();

        System.out.println("parse expression:");
        parseExpression();

        System.out.println("parse statement:");
        parseStatement();

        System.out.println("parse VariableDeclarationExpr:");
        parseVariableDeclarationExpr();
    }

    /**
     * 用来针对单 Java 文件生成 AST 的测试用例。
     *
     * 输入可以是一个文件（路径格式、File、Path 等），也可以直接是 文件内容、IO流 等，基于需要，可以设置源码的编码方式
     *
     * 这里简化测试，直接输入文件的内容
     */
    public static void parseCompilationUnit() {
        var content = """
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

        var result = StaticJavaParser.parse(content); // 类型直接就是 CompilationUnit，没有封装
        System.out.println(result.getClass().getName()); // 获取到的AST类型，这里是 CompilationUnit
    }

    /**
     * 用来针对代码片段生成 AST，这里的代码片段为表达式
     */
    public static void parseExpression() {
        var content = """
                new ArrayList<>()
                """;
        var result = StaticJavaParser.parseExpression(content); // 直接就是个 Expression
        System.out.println(result.getClass().getName()); // 获取到的AST类型，这里是 ObjectCreationExpr
    }

    /**
     * 用来针对代码片段生成AST，这里是一个 语句
     */
    public static void parseStatement() {
        var content = """
                List<String> lst = new ArrayList<>();
                """;
    }

    /**
     * 用来针对代码片段生成AST，这里是 变量声明表达式(如果在后面加个分号，就是一条语句了)
     */
    public static void parseVariableDeclarationExpr() {
        var result = StaticJavaParser.parseVariableDeclarationExpr("int x = 3");
        System.out.println(result.getClass().getName()); // 获取到的AST类型，这里是 VariableDeclarationExpr
    }
}
