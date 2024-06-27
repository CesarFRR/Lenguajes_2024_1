package model.parser.AST;
import model.parser.ParserSym;

import java.util.ArrayList;
import java.util.List;

public class NodeFnCall extends Node3 implements InterfaceExpr, InterfaceStruct{
    private static TableAST table;

    static {
        table = new TableAST();
    }
    public NodeFnCall(Node name, Node args, Node result) {
        super(ParserSym.IDENTIFICADOR, name, args, result);
    }

    /**
     * Devuelve la representación en String del árbol.
     *
     * @param level Nivel del nodo
     * @return String con la representación del árbol
     **/
    @Override
    public String toString(int level) {
        return "";
    }

    /**
     * Ejecuta la acción de este nodo.
     *
     * @return
     **/
    @Override
    public Object execute() {
        Object result = null;
        String fnName = (String) this.child1.execute();
        NodeFn fn = (NodeFn) table.getFn(fnName);
        if(fn == null) throw new IllegalStateException("No se pudo ejecutar la función " +fnName +", no existe.");
        List<Node> args = (this.child2 instanceof NodeTree nt)? this.getInstructionNodes(nt) : new ArrayList<Node>();


//        System.out.println("\n"+"FNCALL: Ejecutando llamado a la función: " + fn);
//        System.out.println("FNCALL: Con argumentos: " + args +"\n");

        if (args.isEmpty() ){
            // NO HAY ARGUMENTOS, TENER ESTO EN CUENTA, Y LLAMAR A LA FUNCION CON ARGS VACIO
            fn.setArgs(args);
            result = fn.execute();

        }else{
            // HAY ARGUMENTOS, COMPROBAR QUE LOS ARGUMENTOS SEAN DEL TIPO CORRECTO
//            System.out.println("FNCALL: Ejecutando llamado a la función: " + fn);
            fn.setArgs(args);
            result = fn.execute();
        }

        if(result instanceof NodeLeaf nl){
            Object v = nl.value;
            if(v instanceof NodeVar nv)    result = nv;
            if(v instanceof NodeArrVar nf)     result = nf;
        }

        result = result instanceof Node ? result : new NodeLeaf(0, result);

        return result;
    }
}
