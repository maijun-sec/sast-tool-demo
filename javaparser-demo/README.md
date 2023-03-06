## JavaParser 使用基本例子
本模块介绍了一些和 JavaParser 使用相关的例子，主要的内容如下：

### JavaParser AST 生成相关的例子

可以按照如下顺序查看

- `JavaParserExample` 介绍了基本的 JavaParser AST 生成的例子
- `StaticJavaParserExample` 基本的使用 StaticJavaParser 生成 AST 的例子
- `ASTPrintExample` 介绍了三种输出 JavaParser AST 的方法
- `ASTNodeExample` 介绍了 Statement 和 Expression 这两个基类里面的部分方法，及如何获取节点位置信息

### JavaParser AST 遍历相关的例子

可以按照如下顺序查看

- `BasicTraverseExample` 基于 CompilationUnit 中的 find 方法，查找特定的节点遍历
- `WalkTraverseExample` 基于 CompilationUnit 中的 walk 方法，对全部 AST 节点进行遍历
- `VisitorTraverseExample` 基于 Visitor 模式遍历 AST 节点，应用最广泛的一种方法

### JavaParser 类型推断和声明解析的例子

- `BasicTypeSolverExample` 提供了基本的类型推断和声明解析的例子


### TODO

针对部分特殊的场景的类型推断进行测试：
- lambda 表达式中的未指定类型的临时变量
- java新版本的 `var` 关键字
- lombok 注解的支持
- ...