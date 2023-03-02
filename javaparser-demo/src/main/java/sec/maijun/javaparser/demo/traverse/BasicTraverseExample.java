package sec.maijun.javaparser.demo.traverse;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.LambdaExpr;

/**
 * 简单的获取 AST 子节点的示例
 *
 * @since 2023-03-02
 * @author maijun
 */
public class BasicTraverseExample {
    public static void main(String[] args) {
        basicOp();
    }

    public static void basicOp() {
        var cu = genCU();

        // 查找到所有的 Lambda 表达式，然后循环执行某种操作
        cu.findAll(LambdaExpr.class).forEach(lambdaExpr -> System.out.println(lambdaExpr));

        // 以特定的顺序查找相关的表达式或者语句
        // TreeTraversal 是一个枚举类型，定义了多种遍历的方式
        // 如下：postorder 是一种后序遍历方式
        cu.findAll(VariableDeclarator.class, Node.TreeTraversal.POSTORDER)
                .forEach(variableDeclarator -> System.out.println(variableDeclarator));

        // 查询特定类型，并且第二个参数表示符合特定的特征
        // 如下：第二个参数表示变量名为 lst 的 变量声明
        cu.findAll(VariableDeclarator.class, variableDeclarator -> variableDeclarator.getName().toString() == "lst")
                .forEach(System.out::println);
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
