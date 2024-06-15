package model.parser.AST;

import java.util.ArrayList;
import java.util.List;

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
        List<Object> results = new ArrayList<>();

        if (n != null) {
            Object result = n.execute();
            result = result == null ? "null" : result;
            //System.out.println("n-> " + result + " " + result.getClass());
            results.add(result);
        }

        if (child != null) {
            Object childResult = child.execute();
            if (childResult != null) {
                results.add(childResult);
            }
        }

        count++;
        Object finalResult = results.isEmpty() ? null : results.getLast();
        //System.out.println("Count: " + count + " result-> " + finalResult);
        return finalResult;
    }

    public NodeTree(Node n, NodeTree child) {
        this.n = n;
        this.child = child;
    }
}
