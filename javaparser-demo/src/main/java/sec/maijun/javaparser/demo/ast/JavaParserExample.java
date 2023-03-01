package sec.maijun.javaparser.demo.ast;

import com.github.javaparser.JavaParser;

/**
 * JavaParser 最基本的功能，这里只是列了一小部分，在基本的AST生成层面，除了下面的例子，还有：
 * 1. parse 文件时(输入为文件)，有各种不同的输入；
 * 2. parse AST节点信息(输入为代码片段，代表不同的语句或者表达式)，还支持直接 parse Block，Import、SimpleName 等，只是用得不常见。
 */
public class JavaParserExample {
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

        var result = new JavaParser().parse(content);
        System.out.println(result.isSuccessful());  // 直接校验是否生成AST成功
        System.out.println(result.getResult().get() != null); // 获取到的AST内容，判断是否为空
        System.out.println(result.getResult().get().getClass().getName()); // 获取到的AST类型，这里是 CompilationUnit
    }

    /**
     * 用来针对代码片段生成 AST，这里的代码片段为表达式
     */
    public static void parseExpression() {
        var content = """
                new ArrayList<>()
                """;
        var result = new JavaParser().parseExpression(content);
        System.out.println(result.isSuccessful()); // 直接校验是否生成AST成功
        System.out.println(result.getResult().get() != null); // 获取到的AST内容，判断是否为空
        System.out.println(result.getResult().get().getClass().getName()); // 获取到的AST类型，这里是 ObjectCreationExpr
    }

    /**
     * 用来针对代码片段生成AST，这里是一个 语句
     */
    public static void parseStatement() {
        var content = """
                List<String> lst = new ArrayList<>();
                """;
        var result = new JavaParser().parseStatement(content);
        System.out.println(result.isSuccessful()); // 直接校验是否生成AST成功
        System.out.println(result.getResult().get() != null); // 获取到的AST内容，判断是否为空
        System.out.println(result.getResult().get().getClass().getName()); // 获取到的AST类型，这里是 ExpressionStmt
    }

    /**
     * 用来针对代码片段生成AST，这里是 变量声明表达式(如果在后面加个分号，就是一条语句了)
     */
    public static void parseVariableDeclarationExpr() {
        var result = new JavaParser().parseVariableDeclarationExpr("int x = 3");
        System.out.println(result.isSuccessful()); // 直接校验是否生成AST成功
        System.out.println(result.getResult().get() != null); // 获取到的AST内容，判断是否为空
        System.out.println(result.getResult().get().getClass().getName()); // 获取到的AST类型，这里是 VariableDeclarationExpr
    }
}
