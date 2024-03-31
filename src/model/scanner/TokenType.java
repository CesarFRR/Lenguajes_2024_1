/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package model.scanner;

/**
 *
 * @author Cesar_R
 */



 
public enum TokenType {
    IF(ColorType.RW),
    ELSE(ColorType.RW),
    ELIF(ColorType.RW),
    WHILE(ColorType.RW),
    FOR(ColorType.RW),
    RETURN(ColorType.RW),
    BREAK(ColorType.RW),
    CONTINUE(ColorType.RW),
    FN(ColorType.RW),
    BOOL(ColorType.DT),
    INT(ColorType.DT),
    FLOAT(ColorType.DT),
    STRING(ColorType.DT),
    TRUE(ColorType.LIT),
    FALSE(ColorType.LIT),
    NULL(ColorType.LIT),
    PRINT(ColorType.FN),
    MEAN(ColorType.FN),
    MAX(ColorType.FN),
    MIN(ColorType.FN),
    MEDIAN(ColorType.FN),
    MODE(ColorType.FN),
    IDENTIFICADOR(ColorType.ID),
    OP_SUMA(ColorType.OP_ARIT),
    OP_RESTA(ColorType.OP_ARIT),
    OP_MULT(ColorType.OP_ARIT),
    OP_DIV(ColorType.OP_ARIT),
    OP_MOD(ColorType.OP_ARIT),
    OP_POT(ColorType.OP_ARIT),
    OP_MENOR(ColorType.OP_REL),
    OP_MENOR_IGUAL(ColorType.OP_REL),
    OP_MAYOR(ColorType.OP_REL),
    OP_MAYOR_IGUAL(ColorType.OP_REL),
    OP_IGUAL(ColorType.OP_REL),
    OP_DIFERENTE(ColorType.OP_REL),
    OP_AND(ColorType.OP_LOG),
    OP_OR(ColorType.OP_LOG),
    OP_NOT(ColorType.OP_LOG),
    OP_ASIGN(ColorType.OP_ASIGN),
    L_PARENTESIS(ColorType.DEL),
    R_PARENTESIS(ColorType.DEL),
    L_LLAVE(ColorType.DEL),
    R_LLAVE(ColorType.DEL),
    L_CORCHETE(ColorType.DEL),
    R_CORCHETE(ColorType.DEL),
    COMA(ColorType.DEL),
    PUNTO_COMA(ColorType.DEL),
    PUNTO(ColorType.DEL),
    DOS_PUNTOS(ColorType.DEL),
    LIT_INT(ColorType.LIT),
    LIT_FLOAT(ColorType.LIT),
    LIT_STRING(ColorType.LIT),
    E_NUM_START_ZERO(ColorType.ERR),
    E_SIMB_NOT_FOUND(ColorType.ERR),
    E_ID_START_DIGIT(ColorType.ERR),
    EOF(ColorType.EOF);

    private final ColorType color;
    
    TokenType(ColorType color) {
        this.color = color;
    }

    public ColorType getColor() {
        return color;
    }
    
    
    
}
