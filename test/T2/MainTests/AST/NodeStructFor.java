package T2.MainTests.AST;

public class NodeStructFor extends Node4{
    /**
     * Constructor de la clase.
     *
     * @param varDeclaration Debe ser de tipo NodeVarDeclaration
     * @param exprBoolean Debe ser de tipo NodeExprBoolean
     * @param VarUpdate Debe ser de tipo NodeVarAssignment con ésta estructura: i = i + (cantidad) ó i= i - (cantidad)
     * @param sentList Debe ser de tipo Node o NodeTree, de preferencia NodeTree
     **/
    public NodeStructFor(Node varDeclaration, Node exprBoolean, Node VarUpdate, Node sentList) {
        super(varDeclaration, exprBoolean, VarUpdate, sentList);
        if (!(varDeclaration instanceof NodeVarDeclaration)) throw new IllegalStateException("La declaración de variable no es válida.");
        if (!(exprBoolean instanceof NodeExprBoolean)) throw new IllegalStateException("La expresión booleana no es válida.");
        if (!(VarUpdate instanceof NodeVarAssignment)) throw new IllegalStateException("La actualización de variable no es válida.");
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
                indent + " " + child2.toString(level + 1) + "\n" +
                indent + " " + child3.toString(level + 1) + "\n" +
                indent + " " + child4.toString(level + 1);
    }

    /**
     * Ejecuta la acción de este nodo.
     *
     * @return
     **/
    @Override
    public Object execute() {
        if (child1 == null || child2 == null || child3 == null || child4 == null) {
            throw new IllegalStateException("No se pudo ejecutar el ciclo for, falta algún operando u operando.");
        }

        Object varDeclaration = child1.execute();
        Object exprBoolean = child2.execute();
        Object VarUpdate = child3.execute();
        return null;
    }
}
