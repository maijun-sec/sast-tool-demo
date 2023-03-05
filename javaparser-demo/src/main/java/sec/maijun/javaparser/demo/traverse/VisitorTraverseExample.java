package sec.maijun.javaparser.demo.traverse;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.expr.VariableDeclarationExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 通过访问者设计模式，对 AST 进行遍历
 * 当前大部分的静态代码分析工具，如果是基于代码结构匹配检查的，一般都是基于 Visitor 设计模式实现的
 *
 * JavaParser 实现了 GenericVisitor 和 VoidVisitor 两种，然后通过适配器模式，适配了
 * GenericVisitorAdapter 和 VoidVisitorAdapter 两个基本的抽象类，方便进行实现
 *
 * @since 2023-03-05
 * @author maijun
 */
public class VisitorTraverseExample {
    public static void main(String[] args) {
        basicVisitor();
    }

    /**
     * 本例实现了一个基于 VoidVisitorAdapter 的访问器用来记录当前 cu 中所有的 变量声明
     */
    public static void basicVisitor() {
        var lst = new ArrayList<VariableDeclarationExpr>();
        var cu = genCU();

        new RecordVariableDeclarationExprVisitor().visit(cu, lst);
        lst.forEach(node -> System.out.println(node));
    }

    private static class RecordVariableDeclarationExprVisitor extends VoidVisitorAdapter<List<VariableDeclarationExpr>> {
        @Override
        public void visit(VariableDeclarationExpr node, List<VariableDeclarationExpr> lst) {
            lst.add(node);

            // 不管基于哪种 Adapter 实现，在 visit 最后，对需要调用 super.visit，否则就无法遍历子节点了
            super.visit(node, lst);
        }
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
