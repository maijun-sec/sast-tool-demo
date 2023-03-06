package sec.maijun.javaparser.demo.typesolver;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParserConfiguration;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.symbolsolver.JavaSymbolSolver;
import sec.maijun.javaparser.demo.typesolver.util.SolverUtil;

import java.io.IOException;

/**
 * 本例简单给出了 JavaParser 中，如何对 变量、表达式、函数调用 等进行类型推断
 *
 * @author maijun
 * @since 2023-03-05
 */
public class BasicTypeSolverExample {
    public static void main(String[] args) throws IOException {
        testTypeInference();
    }

    public static void testTypeInference() throws IOException {
        var cu = genCUWithTypeSolverConfig();

        // calculateResolvedType() 方法用来获取表达式的类型
        System.out.println("==========> variable or expression type inference");
        cu.findAll(Expression.class).forEach(expression -> {
            var resolvedType = expression.calculateResolvedType();
            if (resolvedType.isPrimitive()) {
                var typeName = resolvedType.asPrimitive().describe();
                System.out.println("type for \"" + expression + "\" is " + typeName);
            } else {
                System.out.println("type for \"" + expression + "\" is " + expression.calculateResolvedType());
            }
        });

        // resolve() 方法用来解析变量或者函数的声明
        System.out.println("==========> function declaration resolve");
        cu.findAll(MethodCallExpr.class).forEach(methodCallExpr -> {
            var reflectionMethodDeclaration = methodCallExpr.resolve();
            if (reflectionMethodDeclaration != null) {
                // 这里还可以获得很多内容，比如 package，class 等，可以直接基于这些内容和其他的AST节点联合查询
                var resolvedQualifiedSignature = reflectionMethodDeclaration.getQualifiedSignature();
                System.out.println("type for \"" + methodCallExpr + "\" is " + resolvedQualifiedSignature);
            }
        });

        System.out.println("==========> variable declaration resolve");
        cu.findAll(NameExpr.class).forEach(nameExpr -> {
            try {
                var varDecl = nameExpr.resolve();
                if (varDecl != null) {
                    var node = varDecl.toAst();
                    node.ifPresent(n -> {
                        var begin = n.getBegin().get();
                        System.out.println("declaration for \"" + nameExpr + "\" is " + node + "(" + begin.line + "," + begin.column + ")");
                    });
                }
            } catch (Exception e) {
                // ...
            }
        });
    }


    private static CompilationUnit genCUWithTypeSolverConfig() throws IOException {
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

        // 当前我们简单解析单文件，还不涉及跨文件或者第三方依赖
        var typeSolver = SolverUtil.generateTypeSolver(null, null);
        var symbolSolver = new JavaSymbolSolver(typeSolver);
        var configuration = new ParserConfiguration();
        configuration.setSymbolResolver(symbolSolver);
        var parser = new JavaParser(configuration);
        var result = parser.parse(content);
        return result.getResult().get();
    }
}
