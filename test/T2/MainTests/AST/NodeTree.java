package T2.MainTests.AST;

public class NodeTree extends Node {
    Node n;
    NodeTree child;

    /**
     * @param level Nivel del nodo
     * @return String con la representación del árbol
     */
    @Override
    public String toString(int level) {
        if (child == null) {
            return n == null ? " " : n.toString(level);
        } else {
            return (n == null ? "" : n.toString(level)) + "\n;\n\n" +
                    child.toString(level);
        }
    }

    /** Ejecuta la acción de este nodo.
     * @return 
     **/
    @Override
    public Object execute() {
        if (n != null) {
            Object result = n.execute();
            System.out.println("-> " + result);
        }
        return child == null ? null : child.execute();
    }

    public NodeTree(Node n, NodeTree child) {
        this.n = n;
        this.child = child;
    }
}
