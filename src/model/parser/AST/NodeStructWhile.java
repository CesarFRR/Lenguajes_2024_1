package model.parser.AST;

public class NodeStructWhile extends Node2 implements InterfaceStruct, InterfaceExpr{


    private static TableAST table;
    static {
        table = new TableAST();
    }
    /**
     * Constructor de la clase.
     *
     * @param exprBoolean Debe ser de tipo NodeExprBoolean
     * @param sentList Debe ser de tipo Node o NodeTree, de preferencia NodeTree
     **/
    public NodeStructWhile(int id, Node exprBoolean, Node sentList) {
        super(id, exprBoolean, sentList);
        if (!(exprBoolean instanceof NodeExprBoolean)) throw new IllegalStateException("La expresión booleana no es válida.");
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
        for (int i = 0; i < level; i++) {
            indent += " ";
        }
        return symPrint(id) + "\n" +
                indent + " " + child1.toString(level + 1) + "\n" +
                indent + " " + child2.toString(level + 1) + "\n" ;
    }

    /**
     * Ejecuta la acción de este nodo.
     *
     * @return
     **/
    @Override
    public Object execute() {
        if (child1 == null || child2 == null) {
            throw new IllegalStateException("No se pudo ejecutar el ciclo while, falta algún operando u operando.");
        }
        //table.pushIdMap();

        Object exprBoolean = getRealValue(child1);
        label:
        while ((boolean) exprBoolean) {
            Object state = this.executeInstructionNodes(child2);
            if (state instanceof String s) {
                switch (s) {
                    case "break":
                        break label;
                    case "continue":
                        continue;
                    case "return":
                        return s;
                }
            }
            exprBoolean = getRealValue(child1);
        }

        return null;
    }
}
