package model.parser.AST;
import model.parser.ParserSym;

import java.util.List;

public class NodeStructFor extends Node4 implements InterfaceStruct, InterfaceExpr{


    private static TableAST table;
    static {
        table = new TableAST();
    }
    /**
     * Constructor de la clase.
     *
     * @param varDeclaration Debe ser de tipo NodeVarDeclaration
     * @param exprBoolean Debe ser de tipo NodeExprBoolean
     * @param VarUpdate Debe ser de tipo NodeVarAssignment con ésta estructura: i = i + (cantidad) ó i= i - (cantidad)
     * @param sentList Debe ser de tipo Node o NodeTree, de preferencia NodeTree
     **/
    public NodeStructFor(int id, Node varDeclaration, Node exprBoolean, Node VarUpdate, Node sentList) {
        super(id, varDeclaration, exprBoolean, VarUpdate, sentList);
        if (!(varDeclaration instanceof NodeVar)) throw new IllegalStateException("La declaración de variable no es válida.");
        if (!(exprBoolean instanceof NodeExprBoolean)) throw new IllegalStateException("La expresión booleana no es válida.");
        if (!(VarUpdate instanceof NodeVar)) throw new IllegalStateException("La actualización de variable no es válida.");
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
       // table.pushIdMap();
        int statePosition = this.createState("for", null, null);

        Object varDeclaration = getRealValue(child1);
        Object exprBoolean = getRealValue(child2);

        boolean si =table.existsId(((NodeVar) child1).getName());
        //System.out.println("existe la variable: "+ si);

        List<Node> instructionNodes = (child4 instanceof NodeTree nt) ? this.getInstructionNodes(nt) : List.of(child4);
        String status;
        int len = instructionNodes.size();
        FOR:
        while ((boolean) exprBoolean) {
            instructionNodes = (child4 instanceof NodeTree nt) ? this.getInstructionNodes(nt) : List.of(child4);
            SENTENCIAS:
            for (Node node : instructionNodes) {
                Object sentencia = node.execute();

                if(getStackSize() < statePosition) {
                    /**NOTA: Lo que aqui sucede es que en algun lugar se ejecutó un return y se eliminarion n estados hasta que la pila encontró un estado de tipo "fn" funcion
                     *  de tal manera que se van a detener cualquier ciclo que se esté ejecutando hasta que se encuentre un estado de tipo "fn" funcion y esta maneje el return
                     *  Esto es una simulacion de como se manejan las funciones en la pila de estados
                     */
                    break FOR;
                }
                status = getStatus();
                if(status==null) continue;
                if(status.equals("break")) {
                    break FOR;
                }
                if(status.equals("continue")) {
                    //System.out.println("Se encontró un continue en el ciclo for. Se saltará a la siguiente iteración.");
                    break SENTENCIAS;
                }
            }
            getRealValue(child3); // Actualizar la variable
            exprBoolean = getRealValue(child2); // actualizar la expresión booleana para la siguiente iteración
        }

//
//        System.out.println("\nANTES:");
//        table.printAllMaps();
//
//        //table.printMap(0);
//        System.out.println("==========");
        //table.printMap(1);
        String indexName = ((NodeVar) child1).getName();
        
        table.deleteId(indexName);
        this.removeState();
        //table.popIdMap();


//       System.out.println("\nDESPUES:");
//       table.printAllMaps();
        //System.out.println("\n\n");
        return null;
    }
}
