package model.parser.AST;

import model.parser.ParserSym;

public class NodeFnPrint extends NodeLeaf implements InterfaceExpr{


    public NodeFnPrint(Object value) {
        super(ParserSym.PRINT, value);
    }

    @Override
    public Object execute() {
        Object result;
        if (this.value == null) {
            throw new IllegalStateException("No se puede imprimir un valor nulo");
        }

        if(this.value instanceof Node) {
            result = getRealValue(this.value);
            result = result == null ? "null" : result;
        } else {
            result = this.value;
        }

        System.out.println(cleanString(result.toString()));
        return null;
    }

}
