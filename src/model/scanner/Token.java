package model.scanner;

import model.scanner.TokenType;


public class Token {
    public TokenType type;
    public String text;
    public int line;
    public int column;

    public Token(TokenType type, String text, int line, int column) {
        this.type = type;
        this.text = text;
        this.line = line;
        this.column = column;
    }

    @Override
    public String toString() {
        return "Token{" + "type=" + type + ", text=" + text + ", line=" + line + ", column=" + column + '}';
    }
    
    
}
