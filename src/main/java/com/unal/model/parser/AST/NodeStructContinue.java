package com.unal.model.parser.AST;
import com.unal.model.parser.ParserSym;

public class NodeStructContinue extends NodeLeaf implements InterfaceStruct, InterfaceExpr{
    public NodeStructContinue() {
        super(ParserSym.CONTINUE, null);
    }

    @Override
    public String toString(int level) {
        return this.symPrint(id);
    }

    @Override
    public String toString() {
        return this.symPrint(id);
    }

    @Override
    public Object execute() {
        this.updateState("continue", null);
        return null;
    }
}
