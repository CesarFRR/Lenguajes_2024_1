

package model.parser.AST;
import model.parser.ParserSym;

public class NodeFnToFloat extends NodeLeaf implements InterfaceExpr, InterfaceStruct {
    private static TableAST table;

    static {
        table = new TableAST();
    }

    public NodeFnToFloat(Object value) {
        super(ParserSym.LEN, value);
    }

    @Override
    public Object execute() {
        Object result = null;
        if (this.value instanceof NodeVarId) {
            result = ((NodeVarId) this.value).execute();
            if(!(result instanceof Node)){
                if(result instanceof Float || result instanceof Double){
                    result = (result instanceof Integer) ? (int)(float)result : (int)(double)result;
                    return result;
                }
            }
        }
        // Si no es un string, ES UN ARREGLO!!
        //TODO: Implementar el largo de un arreglo

        if (result instanceof NodeArrVar || result instanceof NodeArrVarId) {
            if (result instanceof NodeArrVar arrVar) {
                NodeArrVar nva = (NodeArrVar)arrVar.copy();
                nva.toFloat();
                return nva;
            }


        }
        return result;
    }
}