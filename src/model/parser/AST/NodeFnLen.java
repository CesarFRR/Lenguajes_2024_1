package model.parser.AST;
import model.parser.ParserSym;

public class NodeFnLen extends NodeLeaf implements InterfaceExpr, InterfaceStruct{
    private static TableAST table;

    static {
        table = new TableAST();
    }
    public NodeFnLen(Object value) {
        super(ParserSym.LEN, value);
    }

    @Override
    public Object execute() {
        Object result =0;
        Object value = this.value;


        if(value  instanceof NodeVarId){
            value = ((NodeVarId)value).execute();
            //System.out.println("value: "+this.value + " type: "+value.getClass().getSimpleName());
        }

        // Si no es un string, ES UN ARREGLO!!

        //TODO: Implementar el largo de un arreglo

        if(value instanceof NodeArrVar || value instanceof NodeArrVarId){
            if(value instanceof NodeArrVar arrVar) {
                result = arrVar.getShape()[0];
            }
            if(value instanceof NodeArrVarId arrVarId) {
                String name = arrVarId.getName();
                Node indexes = arrVarId.getIndexes();
                NodeArrVar arrVar = (NodeArrVar) table.getId(name);
                int[] dimAccess = this.convertListToIntArray(this.getIntValuesList((NodeTree) indexes));
                int[] shape = arrVar.getShape();
                if(dimAccess.length > shape.length) {
                    throw new RuntimeException("Error: Acceso a una dimensión inexistente en la matriz");
                }
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

                if (dimAccess.length >= shape.length) { // se quiere hallar el length del elemento en la posición dimAccess, posiblemente un string
                    result = arrVar.get(dimAccess);
//                    System.out.println("se quiere hallar el length de: "+result+" de tipo: "+result.getClass().getSimpleName());
                    if (result instanceof String) {
                        result = cleanString((String) result).length();
                    }
                    //result = arrVar.get(dimAccess).length();

                } else { // se quiere retornar el length de una dimensión
                    int index = dimAccess.length-1;
                    result = shape[dimAccess.length];
                }

            }
        }else{

//                System.out.println("#0 -> se quiere hallar el length de: "+value+" de tipo: "+value.getClass().getSimpleName());
                Object realValue = getRealValue(value);
//                System.out.println("#1 -> se quiere hallar el length de: "+realValue+" de tipo: "+realValue.getClass().getSimpleName());

                if(realValue instanceof String s){
                    result= cleanString(s).length();
                }



        }








        return new NodeLeaf(ParserSym.LIT_INT, result);
    }

}