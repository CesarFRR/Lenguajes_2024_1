package T2.MainTests.AST;

public class Node2 extends Node {

    public Node child1;
    public Node child2;

    public Node2(Node child1, Node child2) {
        this.child1 = child1;
        this.child2 = child2;
    }

    /**
     * @param level Nivel del nodo
     * @return String con la representación del árbol
     */
    @Override
    public String toString(int level) {
        String indent = "";
        for (int i = 0; i < level; i++) {
            indent += " ";
        }
        return symPrint(id) + "\n" +
                indent + " " + child1.toString(level + 1) + "\n" +
                indent + " " + child2.toString(level + 1);
    }

    /**
     * Ejecuta la acción de este nodo.
     *
     * @return
     **/
    @Override
    public Object execute() {
        if (child1 != null  && child2 != null) {
            Object result1 = child1.execute();
            Object result2 = child2.execute();
            System.out.println("-> " + result1 + " " + result2);
        }
        return null;
    }

}
