package model.parser.AST;
import model.parser.ParserSym;

import java.util.ArrayList;
import java.util.List;

public class NodeFnDeclar extends Node3 implements InterfaceStruct, InterfaceExpr{
    private static TableAST table;

    static {
        table = new TableAST();
    }

    public NodeFnDeclar(Node name, Node params, Node sentList1) {
        super(ParserSym.FN, name, params, sentList1);
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
//        if(this.child1==null || this.child2==null || this.child3==null){
//            throw new IllegalStateException("No se pudo ejecutar la función, falta algún elemento.");
//        }


//        System.out.println("Executing function declaration...");

        this.child2 = this.child2 == null ? new NodeLeaf(-1, null) : this.child2;

        List<Node> plist = (this.child2 instanceof NodeTree nt ) ? this.getInstructionNodes(nt) : new ArrayList<Node>();

        List<NodeParam> paramList = plist.stream().map(node -> (NodeParam) node).toList();

        List<Node> sentList = (this.child3 instanceof NodeTree nt ) ? this.getInstructionNodes(nt) : new ArrayList<Node>();
        String name = (String) this.child1.execute();

        NodeFn fn = new NodeFn(new NodeLeaf(ParserSym.IDENTIFICADOR, name), paramList, sentList);
        table.setFn(name, fn);

//        System.out.println("Function " + name + " declared: " + table.getFn(name));



        return null;
    }
}
