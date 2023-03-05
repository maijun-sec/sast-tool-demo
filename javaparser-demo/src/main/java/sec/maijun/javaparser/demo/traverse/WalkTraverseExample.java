package sec.maijun.javaparser.demo.traverse;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.expr.VariableDeclarationExpr;

/**
 * 直接调用 CompilationUnit 的 walk 的方法，对所有的节点进行遍历
 *
 * 可以定义遍历的顺序，还有对节点进行筛选，是一种相对比较简单的节点遍历的方法
 *
 * @since 2023-03-05
 * @author maijun
 */
public class WalkTraverseExample {
    public static void main(String[] args) {
        basicWalk();
    }

    public static void basicWalk() {
        var cu = genCU();

        // 直接调用 walk，对节点进行遍历，遇到 VariableDeclarationExpr 节点打印
        System.out.println("=======> default order:");
        cu.walk(node -> {
            if (node instanceof VariableDeclarationExpr) {
                System.out.println("variable decl expr: " + node);
            }
        });

        // postorder 调用 walk，对节点进行遍历，遇到 VariableDeclarationExpr 节点打印
        System.out.println("=======> post order:");
        cu.walk(Node.TreeTraversal.POSTORDER, node -> {
            if (node instanceof VariableDeclarationExpr) {
                System.out.println("variable decl expr: " + node);
            }
        });

        // 在执行遍历时，就对节点就行筛选，这样就不用在 Consumer 中进行筛选了
        System.out.println("=======> select in walk:");
        cu.walk(VariableDeclarationExpr.class, node -> {
            System.out.println("variable decl expr: " + node);
        });
    }

    private static CompilationUnit genCU() {
        var content = """
                import java.util.List;
                import java.util.ArrayList;
                
                public class Hello {
                    public static void main(String[] args) {
                        int x = 3;
                        int y = 4;
                        int z = x + y;
                        int z1 = z * x;
                        z1 *= y;
                        System.out.println(z1);
                    }
                }
                """;

        var result = new JavaParser().parse(content);
        return result.getResult().get();
    }
}
