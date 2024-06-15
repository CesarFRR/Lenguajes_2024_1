package model.parser.AST;

import model.parser.ParserSym;

public class NodeStructReturn extends NodeLeaf implements InterfaceStruct, InterfaceExpr{
    public NodeStructReturn(Object value) {
        super(ParserSym.RETURN, value);

    }

    @Override
    public String toString(int level) {
        return this.symPrint(id) + " " + this.value.toString();
    }

    @Override
    public String toString() {
        return this.symPrint(id);
    }

    @Override
    public Object execute() {

        if(this.value == null) {
            this.updateState("return", null);
        }

        if (this.value instanceof Node n) {
            this.updateState("return", n);
        }

        return this.value;
    }



}
