package model;


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
}

public enum TokenType {
    IF,
    ELSE,
    ELIF,
    WHILE,
    FOR,
    RETURN,
    BREAK,
    CONTINUE,
    FN,
    BOOL,
    INT,
    FLOAT,
    STRING,
    TRUE,
    FALSE,
    NULL,
    MEAN,
    MAX,
    MIN,
    MEDIAN,
    MODE,
    OP_SUMA,
    OP_RESTA,
    OP_MULT,
    OP_DIV,
    OP_MOD,
    OP_MENOR,
    OP_MENOR_IGUAL,
    OP_MAYOR,
    OP_MAYOR_IGUAL,
    OP_IGUAL,
    OP_DIFERENTE,
    OP_AND,
    OP_OR,
    OP_NOT,
    OP_ASIGN,
    L_PARENTESIS,
    R_PARENTESIS,
    L_LLAVE,
    R_LLAVE,
    L_CORCHETE,
    R_CORCHETE,
    COMA,
    PUNTO_COMA,
    PUNTO,
    DOS_PUNTOS,
    NUM_ENTERO,
    NUM_FLOTANTE,
    E_NUM_START_ZERO,
    E_ID_NO_,
    E_SIMB_NOT_FOUND,
    COMENTARIO_DOC,
    EOF,
}
