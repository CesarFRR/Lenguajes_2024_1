package model.parser.AST;

import model.parser.ParserSym;

public class NodeFnPrintln extends NodeLeaf implements InterfaceExpr{


    public NodeFnPrintln(Object value) {
        super(ParserSym.PRINT, value);
    }
    @Override
    public String toString(int level) {
        return "PRINT: " + this.value;
    }
    public String toString() {
        return "PRINT: " + this.value;
    }

    @Override
    public Object execute() {
        Object result=null;
        if (this.value == null) {
            throw new IllegalStateException("No se puede imprimir un valor nulo");
        }

        if(this.value instanceof Node nd) {
            if( nd instanceof NodeFnCall nodeFnCall){
                result = nodeFnCall.execute();
//                System.out.println("SE EJECUTA UNA FUNCION:--> "  + result);
            }
//            System.out.println("SE IMPRIME UN NODE:--> "  + result);
//            System.out.println("ANTES DE IMPRIMIR REAL VALUE: ----_>_->_>_>_****: " + nd + nd.getClass());
            result = getRealValue(nd);
            result = result == null ? "null" : result;
        } else {
//            System.out.println("SE IMPRIME UN object:--> "  + this.value.getClass().getSimpleName());
            result = this.value;
        }

        System.out.println(cleanString(result.toString()));
        return null;
    }

}
