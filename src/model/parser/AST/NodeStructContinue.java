package model.parser.AST;
import model.parser.ParserSym;

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
