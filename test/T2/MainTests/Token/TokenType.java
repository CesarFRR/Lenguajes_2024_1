/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package T2.MainTests.Token;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Cesar_R
 */



 
public enum TokenType {

    // Predefinidos
    EOF(ColorType.EOF),
    ERROR(ColorType.ERR),

    // Palabras reservadas
    IF(ColorType.RW),
    ELSE(ColorType.RW),
    ELIF(ColorType.RW),
    WHILE(ColorType.RW),
    FOR(ColorType.RW),
    RETURN(ColorType.RW),
    BREAK(ColorType.RW),
    CONTINUE(ColorType.RW),
    FN(ColorType.RW),

    // Tipos de datos
    BOOL(ColorType.DT),
    INT(ColorType.DT),
    FLOAT(ColorType.DT),
    STRING(ColorType.DT),

    // Literales
    TRUE(ColorType.LIT),
    FALSE(ColorType.LIT),
    NULL(ColorType.LIT),
    LIT_INT(ColorType.LIT),
    LIT_FLOAT(ColorType.LIT),
    LIT_STRING(ColorType.LIT),

    // Funciones
    PRINT(ColorType.FN),
    MEAN(ColorType.FN),
    MAX(ColorType.FN),
    MIN(ColorType.FN),
    MEDIAN(ColorType.FN),
    MODE(ColorType.FN),

    // Identificador
    IDENTIFICADOR(ColorType.ID),

    // Operadores aritméticos
    OP_SUMA(ColorType.OP_ARIT),
    OP_RESTA(ColorType.OP_ARIT),
    OP_MULT(ColorType.OP_ARIT),
    OP_DIV(ColorType.OP_ARIT),
    OP_MOD(ColorType.OP_ARIT),
    OP_POT(ColorType.OP_ARIT),

    // Operadores relacionales
    OP_MENOR(ColorType.OP_REL),
    OP_MENOR_IGUAL(ColorType.OP_REL),
    OP_MAYOR(ColorType.OP_REL),
    OP_MAYOR_IGUAL(ColorType.OP_REL),
    OP_IGUAL(ColorType.OP_REL),
    OP_DIFERENTE(ColorType.OP_REL),

    // Operadores lógicos
    OP_AND(ColorType.OP_LOG),
    OP_OR(ColorType.OP_LOG),
    OP_NOT(ColorType.OP_LOG),

    // Operador de asignación
    OP_ASIGN(ColorType.OP_ASIGN),

    // Delimitadores
    L_PARENTESIS(ColorType.DEL),
    R_PARENTESIS(ColorType.DEL),
    L_LLAVE(ColorType.DEL),
    R_LLAVE(ColorType.DEL),
    L_CORCHETE(ColorType.DEL),
    R_CORCHETE(ColorType.DEL),
    COMA(ColorType.DEL),
    PUNTO_COMA(ColorType.DEL),
    PUNTO(ColorType.DEL),
    DOS_PUNTOS(ColorType.DEL);

    private final ColorType color;
    private static final Map<String, TokenType> STRING_TO_ENUM = new HashMap<>();

    static {
        for (TokenType tokenType : values()) {
            STRING_TO_ENUM.put(tokenType.name(), tokenType);
        }
    }


    TokenType(ColorType color) {
        this.color = color;
    }

    public ColorType getColor() {
        return color;
    }


    public static TokenType fromString(String text) {
        return STRING_TO_ENUM.get(text);
    }

}
