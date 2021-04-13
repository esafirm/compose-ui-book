package nolambda.uibook.processors

import com.sun.source.tree.MethodTree
import com.sun.source.util.TreePath
import com.sun.source.util.TreePathScanner
import com.sun.source.util.Trees
import javax.lang.model.element.ElementKind
import javax.lang.model.element.ExecutableElement

internal class MethodScanner(
    private val logger: Logger
) : TreePathScanner<List<MethodTree>, Trees?>() {

    private val methodTrees = mutableListOf<MethodTree>()

    fun scan(methodElement: ExecutableElement, trees: Trees): MethodTree {
        assert(methodElement.kind == ElementKind.METHOD)
        val methodTrees = this.scan(trees.getPath(methodElement), trees)
        assert(methodTrees.size == 1)
        return methodTrees[0]
    }

    override fun scan(treePath: TreePath, trees: Trees?): List<MethodTree> {
        super.scan(treePath, trees)
        return methodTrees
    }

    override fun visitMethod(methodTree: MethodTree, trees: Trees?): List<MethodTree> {
        logger.note("Visit Method: $methodTree \n")
        logger.note("\nTrees: $trees")

        methodTrees.add(methodTree)
        return super.visitMethod(methodTree, trees)
    }
}