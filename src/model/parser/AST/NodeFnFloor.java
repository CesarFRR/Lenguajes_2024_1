package model.parser.AST;
import model.parser.ParserSym;

public class NodeFnFloor extends NodeLeaf implements InterfaceExpr, InterfaceStruct {
    private static TableAST table;

    static {
        table = new TableAST();
    }

    public NodeFnFloor(Object value) {
        super(ParserSym.LEN, value);
    }

    @Override
    public Object execute() {
        Object result = null;
        if (this.value instanceof NodeVarId) {
            result = ((NodeVarId) this.value).execute();
            //System.out.println("value: "+this.value + " type: "+this.value.getClass().getSimpleName());
        }
        // Si no es un string, ES UN ARREGLO!!
        //TODO: Implementar el largo de un arreglo

        if (result instanceof NodeArrVar || result instanceof NodeArrVarId) {
            if (result instanceof NodeArrVar arrVar) {
                arrVar.floorAllValues();
                return arrVar;
            }


        }
        return result;
    }
}