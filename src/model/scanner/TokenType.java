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
    IF(Color.RW),
    ELSE(Color.RW),
    ELIF(Color.RW),
    WHILE(Color.RW),
    FOR(Color.RW),
    RETURN(Color.RW),
    BREAK(Color.RW),
    CONTINUE(Color.RW),
    FN(Color.RW),
    BOOL(Color.DT),
    INT(Color.DT),
    FLOAT(Color.DT),
    STRING(Color.DT),
    TRUE(Color.LIT),
    FALSE(Color.LIT),
    NULL(Color.LIT),
    PRINT(Color.FN),
    MEAN(Color.FN),
    MAX(Color.FN),
    MIN(Color.FN),
    MEDIAN(Color.FN),
    MODE(Color.FN),
    IDENTIFICADOR(Color.ID),
    OP_SUMA(Color.OP_ARIT),
    OP_RESTA(Color.OP_ARIT),
    OP_MULT(Color.OP_ARIT),
    OP_DIV(Color.OP_ARIT),
    OP_MOD(Color.OP_ARIT),
    OP_POT(Color.OP_ARIT),
    OP_MENOR(Color.OP_REL),
    OP_MENOR_IGUAL(Color.OP_REL),
    OP_MAYOR(Color.OP_REL),
    OP_MAYOR_IGUAL(Color.OP_REL),
    OP_IGUAL(Color.OP_REL),
    OP_DIFERENTE(Color.OP_REL),
    OP_AND(Color.OP_LOG),
    OP_OR(Color.OP_LOG),
    OP_NOT(Color.OP_LOG),
    OP_ASIGN(Color.OP_ASIGN),
    L_PARENTESIS(Color.DEL),
    R_PARENTESIS(Color.DEL),
    L_LLAVE(Color.DEL),
    R_LLAVE(Color.DEL),
    L_CORCHETE(Color.DEL),
    R_CORCHETE(Color.DEL),
    COMA(Color.DEL),
    PUNTO_COMA(Color.DEL),
    PUNTO(Color.DEL),
    DOS_PUNTOS(Color.DEL),
    NUM_ENTERO(Color.LIT),
    NUM_FLOTANTE(Color.LIT),
    E_NUM_START_ZERO(Color.ERR),
    E_SIMB_NOT_FOUND(Color.ERR),
    E_ID_START_DIGIT(Color.ERR),
    //COMENTARIO_DOC,
    EOF(Color.EOF);

    private final Color color;
    
    TokenType(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }
    
    
    
}
