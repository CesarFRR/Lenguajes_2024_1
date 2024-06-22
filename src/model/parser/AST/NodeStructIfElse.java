package model.parser.AST;

import java.util.List;

public class NodeStructIfElse extends Node3 implements InterfaceStruct{
    /**
     * Constructor de la clase.
     *
     * @param exprBool  Expresión booleana
     * @param sentList1 Lista de sentencias 1 si la expresión es verdadera
     * @param sentList2 Lista de sentencias 2 si la expresión es falsa (else)
     **/
    public NodeStructIfElse(int id, Node exprBool, Node sentList1, Node sentList2) {
        super(id, exprBool, sentList1, sentList2);
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
        String elseStr = (child3 == null) ? "" : child3.toString(level + 1);
        return symPrint(id) + "\n" +
                indent + " " + child1.toString(level + 1) + "\n" +
                indent + " " + child2.toString(level + 1) + "\n" +
                indent + " " + (new NodeLeaf(id, "else")).toString(level + 1) + "\n" +
                indent + " " + elseStr;
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
        Object sent;
        String status;
        List<Node> instructionNodes;
        if ((boolean) exprBool) {
            instructionNodes = (child2 instanceof NodeTree nt) ? this.getInstructionNodes(nt) : List.of(child2);
            for (Node node : instructionNodes) {
                 sent = node.execute();
                 status = getStatus();
                 if (status==null) continue;
                 if (status.equals("break") || status.equals("continue") || status.equals("return")) return null;
             }
        } else {
            if (child3 != null) {
                instructionNodes = (child3 instanceof NodeTree nt) ? this.getInstructionNodes(nt) : List.of(child3);
                for (Node node : instructionNodes) {
                    sent = node.execute();
                    status = getStatus();
                    if (status.equals("break") || status.equals("continue") || status.equals("return")) return null;
                }

            }
        }
        return null;
    }

}
