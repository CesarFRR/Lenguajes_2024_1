package model.parser.AST;
import model.parser.ParserSym;

public class NodeFnToInt extends NodeLeaf implements InterfaceExpr, InterfaceStruct {
    private static TableAST table;

    static {
        table = new TableAST();
    }

    public NodeFnToInt(Object value) {
        super(ParserSym.LEN, value);
    }

    @Override
    public Object execute() {
        Object result = null;
        if (this.value instanceof NodeVarId) {
            result = ((NodeVarId) this.value).execute();


            if(!(result instanceof Node)){
                if(result instanceof Float || result instanceof Double){
                    if(result instanceof Double d){
                        result =(int) d.floatValue();
                    }else if (result instanceof Float f ){
                        result = (int) f.floatValue();
                    }
                    int r = (int) result;
                    return r;
                }
            }


        }
        // Si no es un string, ES UN ARREGLO!!
        //TODO: Implementar el largo de un arreglo

        if (result instanceof NodeArrVar || result instanceof NodeArrVarId) {
            if (result instanceof NodeArrVar arrVar) {
                arrVar.toInt();
                return arrVar;
            }


        }
        return result;
    }
}