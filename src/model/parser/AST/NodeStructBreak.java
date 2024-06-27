package model.parser.AST;
import model.parser.ParserSym;

public class NodeStructBreak extends NodeLeaf implements InterfaceStruct, InterfaceExpr{
    public NodeStructBreak() {
        super(ParserSym.BREAK, null);
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
        this.updateState("break", null);
        return null;
    }
}
