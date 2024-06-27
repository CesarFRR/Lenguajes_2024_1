package model.parser.AST;
import model.parser.ParserSym;

import java.util.List;

public class NodeParam extends Node3 implements  InterfaceStruct, InterfaceExpr{
    private static TableAST table;
    static {
        table = new TableAST();
    }
    private Object value;
    public NodeParam(Node type, Node name, Node dims) {
        super(ParserSym.IDENTIFICADOR, type, name, dims);
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


    public String toString() {
        String dims = this.child3 == null ? "" : this.child3.toString();
        return "NodeParam: " + this.child1 + " " + this.child2 + " " + dims + " " + this.value;
    }

    /**
     * Ejecuta la acción de este nodo.
     *
     * @return
     **/
    @Override
    public Object execute() {
        Node result = null;
        Object value0= null;
        if (this.value == null) return null;
//        System.out.println("PARAM EL VALOR ES: " + this.getRealValue(this.value));
        value0 = (value instanceof NodeVarId nvid)? table.getId(nvid.getName()):value;
        if (this.child3 == null && value0 instanceof NodeVar nv) { // Es una variable normal
            NodeVar invar = (NodeVar) nv.copy();
            String intype, partype;
            intype = invar.getType();
            partype = (String)(this.child1).execute();
            if(intype.equals(partype)){
                nv.child2 = this.child2; // renombrar la variable antes de declararla
                result = nv;
            } else {
                throw new IllegalStateException("No se pudo ejecutar la declaración de variable, los tipos no coinciden: " + invar.getName()+ " -> "+ intype + " " +" | " + (String)child2.execute() +" -> "+ partype);
            }
        } else if (value0 instanceof NodeArrVar nav){ // Es un arreglo
            Object ch3 = this.child3;
            List<Node> dims = (ch3 instanceof NodeTree nt)? this.getInstructionNodes(nt) : null;
            if (dims == null) throw new IllegalStateException("No se pudo ejecutar la declaración de arreglo, falta algún operando u operando.");
            int parDims = dims.size(); // Se divide en dos porque si se tiene [][] se tienen 4 caracteres: 2*[ + 2*] = 4, por lo que 4/2 = 2 que coincice con el numero de dimensiones
            int varDims = nav.getShape().length;
            if(parDims != varDims) throw new IllegalStateException("Parametro invalido, las dimensiones del arreglo no coinciden, se piden "+ parDims + " pero se obtuvo "+ varDims);
            String name = (String) this.child2.execute();
            NodeArrVar invar = (NodeArrVar) nav.copy(name);
            String intype, partype;
            intype = invar.getType();
            partype = (String)(this.child1).execute();
            if(intype.equals(partype)){
                return new NodeLeaf(ParserSym.IDENTIFICADOR, invar);
            } else {
                System.out.println("los tipos no coinciden: " + intype + " "+invar.getIdentifier() +",  "+ partype + " "+ this.child2.execute());
                throw new IllegalStateException("No se pudo ejecutar la declaración de arreglo, los tipos no coinciden: " + value+ " -> "+ intype + " " +" | " + (String)child2.execute() +" -> "+ partype);
            }
        }else{
            if(value instanceof NodeLeaf nl){
                Object v = nl.execute();
                String intype, partype;
                intype = getTypeFromValue(v);
                partype = (String)(this.child1).execute();
                if(intype.equals(partype)){
                    NodeVar nv = new NodeVar(ParserSym.IDENTIFICADOR, this.child1, this.child2, null);
                    nv.setValue(v);
                    result = nv;
                } else {
                    throw new IllegalStateException("No se pudo ejecutar la declaración de variable, los tipos no coinciden.");
                }
            }else if(value instanceof NodeExprBoolean expb){
                Object v = expb.execute();
                String intype, partype;
                intype = getTypeFromValue(v);
                partype = (String)(this.child1).execute();
                if(intype.equals(partype)){
                    NodeVar nv = new NodeVar(ParserSym.IDENTIFICADOR, this.child1, this.child2, null);
                    nv.setValue(v);
                    result = nv;
                } else {
                    throw new IllegalStateException("No se pudo ejecutar la declaración de variable, los tipos no coinciden.");
                }
            } else{
                Object v = getRealValue(value);
                String intype, partype;
                intype = getTypeFromValue(v);
                partype = (String)(this.child1).execute();
                if(intype.equals(partype)) {
                    NodeVar nv = new NodeVar(ParserSym.IDENTIFICADOR, this.child1, this.child2, null);
                    nv.setValue(v);
                    result = nv;
                }
            }
        }
        return result;
    }

    public void setValue(Object value) {
        this.value = value;
    }
    public Object getValue() {
        return value;
    }

    public void clean(){
        this.value = null;
    }
}
