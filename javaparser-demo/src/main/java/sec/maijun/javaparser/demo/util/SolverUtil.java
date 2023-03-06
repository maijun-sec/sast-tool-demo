package sec.maijun.javaparser.demo.util;

import com.github.javaparser.symbolsolver.resolution.typesolvers.CombinedTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.JarTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.JavaParserTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.ReflectionTypeSolver;

import java.io.IOException;
import java.util.List;

/**
 * 关于 TypeSolver 的解释
 *
 * 1) AarTypeSolver: 在Android aar文件中查找特定的类型;
 * 2) ClassLoaderTypeSolver: 针对自定义的ClassLoader使用，可以处理由该ClassLoader加载的类，这种类型在静态代码分析中很少用到;
 * 3) CombinedTypeSolver: 可以封装多个Type Solver一起使用
 * 4) JarTypeSolver: 在Jar文件中，查找特定的类型
 * 5) JavaParserTypeSolver: 在源码中查找特定的类型，只需要传递源码根路径即可
 * 6) MemoryTypeSolver: 一般不需要使用，可以在测试中使用
 * 7) ReflectionTypeSolver: 用来处理应用classpath里面的类，一般用于处理JRE中的类
 *
 * 通常，作为一个解析 Java 代码的工具，一般解析来源有三个：
 * 1) 当前模块中的其他源码；
 * 2) 其他模块中的 class 文件；
 * 3) 依赖的 jar 中的信息
 */
public class SolverUtil {

    /**
     * 需要处理的类型有三个来源：① 当前自己工程中定义；② 当前工程的第三方依赖；③ JRE中的基本类型。
     * 对应上面的JavaParserTypeSolver，JarTypeSolver和ReflectionTypeSolver，组合成一个CombinedTypeSolver
     *
     * @param sourcePath
     * @param dependencies
     * @return
     * @throws IOException
     */
    public static CombinedTypeSolver generateTypeSolver(String sourcePath, List<String> dependencies) throws IOException {
        CombinedTypeSolver solver = new CombinedTypeSolver();
        // 1. JavaParserTypeSolver
        if (sourcePath != null) {
            solver.add(new JavaParserTypeSolver(sourcePath));
        }

        // 2. JarTypeSolver
        if (dependencies != null) {
            for (String dependency : dependencies) {
                solver.add(new JarTypeSolver(dependency));
            }
        }

        // 3. ReflectionTypeSolver
        solver.add(new ReflectionTypeSolver());

        return solver;
    }
}
