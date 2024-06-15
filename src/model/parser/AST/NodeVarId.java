package model.parser.AST;

import model.parser.ParserSym;

public class NodeVarId extends Node{
    private static TableAST table;
    private String name;

    static {
        table = new TableAST();
    }

    public NodeVarId(String identifier) {
        super(ParserSym.IDENTIFICADOR);
        this.name = identifier;
    }

    @Override
    public String toString(int level) {
        Object var = table.getId(name);
        if( var instanceof NodeArrVar nav){
            return nav.toString();
        }
        return name + ": " + ((NodeVar)var).getValue();
    }

    @Override
    public Object execute() {

        Object var = table.getId(name);
        if( var instanceof NodeArrVar nav){
            return nav;
        }
        return ((NodeVar)var).getValue();
    }
public String getName() {
        return name;
    }
}
