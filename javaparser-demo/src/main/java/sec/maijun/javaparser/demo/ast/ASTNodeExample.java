package sec.maijun.javaparser.demo.ast;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.stmt.Statement;

/**
 * 这里很粗地写了几个表达式和语句的基类中的部分函数
 *
 * 每个具体的 AST 节点中还有自己的特有的函数，需要用到的时候，再具体查看即可
 *
 * @since 2023-03-01
 * @author maijun
 */
public class ASTNodeExample {
    public static void main(String[] args) {
        // 测试部分表达式的操作
        testExpression();

        // 测试部分语句的操作
        testStatement();
    }

    public static void testExpression() {
        var cu = genCU();

        // 这里举例的是 Expression，表达式
        // 语句 Statement 也有很多类似的函数，可以参考
        cu.findAll(Expression.class).forEach(expression -> {
            System.out.println("----> expression: " + expression.toString());
            System.out.println("begin line: " + expression.getBegin().get().line); // 开始行
            System.out.println("begin column: " + expression.getBegin().get().column); // 开始列

            System.out.println("end line: " + expression.getEnd().get().line); // 结束行
            System.out.println("end column: " + expression.getEnd().get().column); // 结束列

            // AST 节点中，包含了两大类函数 asXX 和 ifXX，asXX 是转换为 XX 类型，ifXX 是如果是 XX 类型，就做什么事情
            expression.ifLambdaExpr(lambdaExpr -> {  // ifLambdaExpr 判断是否为 LambdaExpr，如果是，做什么事情
                System.out.println("this is a lambda expression");
            });

            // findXX 类型的函数，可以查找很多的类型，包括找子节点、找祖先节点、或根据类型查找祖先节点等
            System.out.println(expression.findRootNode().getClass().getName()); // 获取根节点

            // 获取当前表达式的 Token 序列
            // 一般我们做事情的时候，有 AST 就足够了，但是挡不住需要在更基础的 Token 上面做事情
            // 这时候获取 Token 的动作对我们就很有用了
            System.out.println("tokens for current expression begin:");
            expression.getTokenRange().get().forEach(javaToken -> System.out.println(javaToken));
            System.out.println("tokens for current expression end!");
        });
    }

    public static void testStatement() {
        var cu = genCU();

        // 这里举例的是 Statement，语句
        cu.findAll(Statement.class).forEach(statement -> {
            System.out.println("----> statement: " + statement.toString());
            System.out.println("begin line: " + statement.getBegin().get().line); // 开始行
            System.out.println("begin column: " + statement.getBegin().get().column); // 开始列

            System.out.println("end line: " + statement.getEnd().get().line); // 结束行
            System.out.println("end column: " + statement.getEnd().get().column); // 结束列

            // AST 节点中，包含了两大类函数 asXX 和 ifXX，asXX 是转换为 XX 类型，ifXX 是如果是 XX 类型，就做什么事情
            statement.ifExpressionStmt(expressionStmt -> {  // ifExpressionStmt 判断是否为 ExpressionStmt，如果是，做什么事情
                System.out.println("this is a expr statement");
            });

            // findXX 类型的函数，可以查找很多的类型，包括找子节点、找祖先节点、或根据类型查找祖先节点等
            System.out.println(statement.findRootNode().getClass().getName()); // 获取根节点

            // 获取当前表达式的 Token 序列
            // 一般我们做事情的时候，有 AST 就足够了，但是挡不住需要在更基础的 Token 上面做事情
            // 这时候获取 Token 的动作对我们就很有用了
            System.out.println("tokens for current statement begin:");
            statement.getTokenRange().get().forEach(javaToken -> System.out.println(javaToken));
            System.out.println("tokens for current statement end!");
        });
    }

    private static CompilationUnit genCU() {
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
        return result.getResult().get();
    }
}
