package model.parser.AST;

import model.parser.ParserSym;

import java.util.Arrays;

public class NodeFnSort extends NodeLeaf implements InterfaceExpr, InterfaceStruct{
    private static TableAST table;

    static {
        table = new TableAST();
    }
    public NodeFnSort(Object value) {
        super(ParserSym.SORT, value);
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


        if(this.value instanceof NodeVarId){
            this.value = ((NodeVarId) this.value).execute();
            //System.out.println("value: "+this.value + " type: "+this.value.getClass().getSimpleName());
        }




        if(this.value instanceof NodeArrVar || this.value instanceof NodeArrVarId){
            if(this.value instanceof NodeArrVar arrVar) {
                arrVar.sortAll();
            }
            if(this.value instanceof NodeArrVarId arrVarId) {
                String name = arrVarId.getName();
                Node indexes = arrVarId.getIndexes();
                NodeArrVar arrVar = (NodeArrVar) table.getId(name);
                int[] dimAccess = this.convertListToIntArray(this.getIntValuesList((NodeTree) indexes));
                int[] shape = arrVar.getShape();
                if(dimAccess.length > shape.length) {
                    throw new RuntimeException("Error: Acceso a una dimensión inexistente en la matriz");
                }
                if(dimAccess.length == shape.length) return null;

                //quiero comprobar que los indices esten dentro de los limites de la matriz y sus shapes
                for(int i = 0; i < dimAccess.length; i++) {
                    if(dimAccess[i] >= shape[i]){
                        throw new RuntimeException("Error: Acceso a una posición fuera de los límites de la matriz");
                    }
                    if (dimAccess[i] < 0) {
                        // quiero hacer como en python, -1 es el último elemento, -2 el penúltimo, etc
                        dimAccess[i] = shape[i] + dimAccess[i];
                        // si sigue siendo negativo, entonces es un error
                        if(dimAccess[i] < 0) {
                            throw new RuntimeException("Error: Acceso a una posición fuera de los límites de la matriz");
                        }
                    }
                }

               arrVar.sort(dimAccess);
//                int index = dimAccess.length-1;
//                result = shape[dimAccess.length];


            }
        }else{
            Object realValue = getRealValue(this.value);
            if(realValue instanceof String s){
                char[] charArray = s.toCharArray();
                Arrays.sort(charArray);
                result = new NodeLeaf(ParserSym.LIT_STRING, new String(charArray));
            }
        }
        return result;
    }
}
