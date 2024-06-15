package model.parser.AST;

import model.parser.ParserSym;

public class NodeFnGetChar extends Node2 implements InterfaceExpr{

    public NodeFnGetChar(Node string, Node index) {
        super(ParserSym.LIT_STRING, string, index);
    }

    @Override
    public Object execute() {

        Object realValue = getRealValue(this.child1);
        Object realIndex = getRealValue(this.child2);

        if (realValue instanceof String s && realIndex instanceof Integer i) {
            s = cleanString(s);
            int size = s.length();
            if (i >= 0 && i < size) {
                return  String.valueOf(s.charAt(i));
            } else {
                throw new IllegalStateException("Index out of bounds");
            }
        }else return new IllegalStateException("Error en los parametros de la funcion getChar");

    }

}

