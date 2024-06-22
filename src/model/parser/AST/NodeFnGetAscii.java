package model.parser.AST;

import model.parser.ParserSym;
import model.parser.ParserSym;

public class NodeFnGetAscii extends NodeLeaf implements InterfaceExpr{

    public NodeFnGetAscii(Object string) {
        super(ParserSym.GETASCII, string);
    }

    @Override
    public Object execute() {
        Object realValue = getRealValue(this.value);
        if (realValue instanceof String s) {
            return (int) (cleanString(s)).charAt(0);
        }
        return null;
    }

}