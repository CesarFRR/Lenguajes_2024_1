package com.unal.model.parser.AST;
import com.unal.model.parser.ParserSym;

public class NodeFnGetCharFromAscii extends NodeLeaf implements InterfaceExpr{

    public NodeFnGetCharFromAscii(Object asciiCode) {
        super(ParserSym.GETCHARFROMASCII, asciiCode);
    }

    @Override
    public Object execute() {
        Object realValue = getRealValue(this.value);
        if (realValue instanceof Integer i) {
            return String.valueOf((char) i.intValue());
        }
        return null;
    }

}