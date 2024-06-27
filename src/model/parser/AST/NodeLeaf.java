package model.parser.AST;
import model.parser.ParserSym;

import model.scanner.Token.TokenType;

public class NodeLeaf extends Node{
    /** The generic value of this node, used for identifier names and literal values. **/
    public Object value;

    public NodeLeaf(int id, Object value) {
        super(id);
        this.value = value;
    }

    @Override
    public String toString(int level) {
        String s = value == null ? "*null*" : value.toString();
        return s;
    }

    @Override
    public String symPrint(int id) {
        // Aquí puedes agregar los símbolos que necesites
        String simb = TokenType.getSymbol(id);
        if (simb.startsWith("LIT")) {
            return value.toString();
        }
        return simb;
    }


    /**
     * Ejecuta la acción de este nodo.
     *
     * @return
     **/
    @Override
    public Object execute() {
        if (value instanceof Node n) {
            return n.execute();
        }
        return value;
    }

}
