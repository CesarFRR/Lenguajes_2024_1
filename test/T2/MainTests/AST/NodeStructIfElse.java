package T2.MainTests.AST;

import java.util.List;

public class NodeStructIfElse extends Node3 implements InterfaceStruct{
    /**
     * Constructor de la clase.
     *
     * @param exprBool  Expresión booleana
     * @param sentList1 Lista de sentencias 1 si la expresión es verdadera
     * @param sentList2 Lista de sentencias 2 si la expresión es falsa (else)
     **/
    public NodeStructIfElse(Node exprBool, Node sentList1, Node sentList2) {
        super(exprBool, sentList1, sentList2);
    }

    /**
     * Devuelve la representación en String del árbol.
     *
     * @param level Nivel del nodo
     * @return String con la representación del árbol
     **/
    @Override
    public String toString(int level) {
        String indent = "";
        // impresion para estructura if-else
        for (int i = 0; i < level; i++) {
            indent += " ";
        }
        return symPrint(id) + "\n" +
                indent + " " + child1.toString(level + 1) + "\n" +
                indent + " " + child2.toString(level + 1) + "\n" +
                indent + " " + (new NodeLeaf(id, "else")).toString(level + 1) + "\n" +
                indent + " " + child3.toString(level + 1);
    }

    /**
     * Ejecuta la acción de este nodo.
     *
     * @return
     **/
    @Override
    public Object execute() {
        if (child1 == null) throw new IllegalStateException("La expresión booleana es null o vacía.");
        if (child2 == null) throw new IllegalStateException("La lista de sentencias1 es null o vacía.");
        Object exprBool = child1.execute();
        if (!(exprBool instanceof Boolean)) throw new IllegalStateException("La expresión booleana no es booleana.");
        String state;
        if ((boolean) exprBool) {
             state = this.executeInstructionNodes(child2);
             if (state instanceof String s ){
                    if (s.equals("break") || s.equals("continue") || s.equals("return")) return s;
             }
        } else {
            if (child3 != null) {
                state = this.executeInstructionNodes(child3);
                if (state instanceof String s ){
                    if (s.equals("break") || s.equals("continue") || s.equals("return")) return s;
                }
            }
        }
        return null;
    }

}
