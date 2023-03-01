package sec.maijun.javaparser.demo.ast;

import com.github.javaparser.JavaParser;
import com.github.javaparser.printer.DotPrinter;
import com.github.javaparser.printer.XmlPrinter;
import com.github.javaparser.printer.YamlPrinter;

/**
 * 介绍 JavaParser 中 AST 的输出方式。
 *
 * 我们如果要在 AST 上面做什么事情，必须要先知道 AST 长什么样子，我们一般有两种方式来了解：
 * 1. debug 方式，打断点，然后看某个 AST 对象的具体内容，但是并不直观；
 * 2. 将 AST 输出出来。
 *
 * 这里，我们将介绍如何将 AST 输出出来，并且输出的 XML，YAML 和 Graphiz dot 三种输出方式
 *
 * @since 2023-03-01
 * @author maijun
 */
public class ASTPrintExample {
    public static void main(String[] args) {
        // 输出 XML 格式
        dumpXml();

        // 输出 YAML 格式
        dumpYaml();

        // 输出 Graphiz dot 格式
        dumpGraphizDot();
    }

    /**
     * 以 XML 格式输出
     */
    public static void dumpXml() {
        var result = new JavaParser().parseExpression("y = x + 4");
        var expr = result.getResult();
        expr.ifPresent(XmlPrinter::print);  // 如果 expr 存在，以 XML 格式输出到控制台，这里 XML 只有一行，没有格式化

        // 下面代码，将 AST 以 XML 形式放到字符串中，随后可以自行决策如何处理（比如是否打印到文件）
        // var xmlFormatAST = new XmlPrinter(true).output(expr.get());
        // ... do something with xml format ast string.
    }

    /**
     * 以 Yaml 格式输出
     */
    public static void dumpYaml() {
        var result = new JavaParser().parseExpression("y = x + 4");
        var expr = result.getResult();
        expr.ifPresent(YamlPrinter::print); // 如果 expr 存在，以 YAML 格式输出到控制台

        // 下面代码，将 AST 以 YAML 形式放到字符串中，随后可以自行决策如何处理（比如是否打印到文件）
        // var yamlFormatAST = new YamlPrinter(true).output(expr.get());
        // ... do something with yaml format ast string.
    }

    /**
     * dot打印(可以通过Graphiz dot命令，将输出生成为图片格式，例如 dot -Tpng ast.dot > ast.png)
     */
    public static void dumpGraphizDot() {
        var result = new JavaParser().parseExpression("y = x + 4");
        var expr = result.getResult();
        expr.ifPresent(e -> System.out.println(new DotPrinter(true).output(e))); // DOT 格式输出

        // 下面代码，将 AST 以 DOT 形式放到字符串中，随后可以自行决策如何处理（比如是否打印到文件，正常这里都应该写到 .dot 文件里面）
        // var dotFormatAST = new DotPrinter(true).output(expr.get());
        // ... do something with dot format ast string.
    }
}
