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
        if (var == null) {
            return name + ": null";
        }
        return name + ": " + ((NodeVar)var).getValue();
    }
    public String toString() {
        Object var = table.getId(name);
        if( var instanceof NodeArrVar nav){
            return nav.toString();
        }
        if (var == null) {
            return name + ": null";
        }
        Object v = ((NodeVar)var).getValue();
        return  v.toString();
    }

    @Override
    public Object execute() {

        Object var = table.getId(name);
        if(var ==null){
            //System.out.println("Variable "+name+" no definida");
            return null;
        }
        if( var instanceof NodeArrVar nav){
            //System.out.println("Variable "+name+" es un arreglo");

            return nav;
        }
        return ((NodeVar)var).getValue();
    }
public String getName() {
        return name;
    }
}
