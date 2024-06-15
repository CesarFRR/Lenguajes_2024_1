/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package model.scanner.Token;

import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;

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
    BOOLEAN(ColorType.DT),
    INT(ColorType.DT),
    FLOAT(ColorType.DT),
    STRING(ColorType.DT),
    CHAR(ColorType.DT),

    // Literales
    TRUE(ColorType.LIT),
    FALSE(ColorType.LIT),
    NULL(ColorType.LIT),
    LIT_INT(ColorType.LIT),
    LIT_FLOAT(ColorType.LIT),
    LIT_STRING(ColorType.LIT),
    LIT_CHAR(ColorType.LIT),

    // Funciones
    PRINT(ColorType.FN),
    MEAN(ColorType.FN),
    MAX(ColorType.FN),
    MIN(ColorType.FN),
    MEDIAN(ColorType.FN),
    MODE(ColorType.FN),
    GETCHAR(ColorType.FN),
    GETCHARFROMASCII(ColorType.FN),
    GETASCII(ColorType.FN),
    LEN(ColorType.FN),
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

    //Adicional

    private final ColorType color;
    private static final Map<String, TokenType> STRING_TO_ENUM = new HashMap<>();
    private static final Map<Integer, TokenType> INTEGER_TO_ENUM = new HashMap<>();

    static {
        for (TokenType tokenType : values()) {
            STRING_TO_ENUM.put(tokenType.name(), tokenType);
            INTEGER_TO_ENUM.put(tokenType.ordinal(), tokenType);
        }
    }


    TokenType(ColorType color) {
        this.color = color;
    }

    public ColorType getColor() {
        return color;
    }


    public static TokenType getType(String key) { return STRING_TO_ENUM.get(key); }
    public static TokenType getType(int key) { return INTEGER_TO_ENUM.get(key); }
    /**
     * Devuelve el simbolo (valor como tal, como if, else, palabras clave, while, etc) correspondiente al key (id)
     * @param key (id) del simbolo
     * @return String con el simbolo correspondiente al key
     */
    public static String getSymbol(int key) { return TokenSymbol.getSymbol(key); }

}



enum TokenSymbol {
    // Predefinidos
    EOF("EOF"),
    ERROR("ERROR"),

    // Palabras reservadas
    IF("if"),
    ELSE("else"),
    ELIF("elif"),
    WHILE("while"),
    FOR("for"),
    RETURN("return"),
    BREAK("break"),
    CONTINUE("continue"),
    FN("fn"),

    // Tipos de datos
    BOOLEAN("boolean"),
    INT("int"),
    FLOAT("float"),
    STRING("string"),
    CHAR("char"),

    // Literales
    TRUE("true"),
    FALSE("false"),
    NULL("null"),
    LIT_INT("LIT_INT"),
    LIT_FLOAT("LIT_FLOAT"),
    LIT_STRING("LIT_STRING"),
    LIT_CHAR("LIT_CHAR"),

    // Funciones
    PRINT("print"),
    MEAN("mean"),
    MAX("max"),
    MIN("min"),
    MEDIAN("median"),
    MODE("mode"),
    GETCHAR("getChar"),
    GETCHARFROMASCII("getCharFromAscii"),
    GETASCII("getAscii"),
    LEN("len"),

    // Identificador
    IDENTIFICADOR("IDENTIFICADOR"),

    // Operadores aritméticos
    OP_SUMA("+"),
    OP_RESTA("-"),
    OP_MULT("*"),
    OP_DIV("/"),
    OP_MOD("%"),
    OP_POT("^"),

    // Operadores relacionales
    OP_MENOR("<"),
    OP_MENOR_IGUAL("<="),
    OP_MAYOR(">"),
    OP_MAYOR_IGUAL(">="),
    OP_IGUAL("=="),
    OP_DIFERENTE("!="),

    // Operadores lógicos
    OP_AND("&&"),
    OP_OR("||"),
    OP_NOT("!"),

    // Operador de asignación
    OP_ASIGN("="),

    // Delimitadores
    L_PARENTESIS("("),
    R_PARENTESIS(")"),
    L_LLAVE("{"),
    R_LLAVE("}"),
    L_CORCHETE("["),
    R_CORCHETE("]"),
    COMA(","),
    PUNTO_COMA(";"),
    PUNTO("."),
    DOS_PUNTOS(":");

    private final String symbol;
    private static final Map<Integer, TokenSymbol> INTEGER_TO_ENUM = new HashMap<>();


    TokenSymbol(String symbol) {
        this.symbol = symbol;
    }

    static {
        for (TokenSymbol tokenSymbol : values()) {
            INTEGER_TO_ENUM.put(tokenSymbol.ordinal(), TokenSymbol.valueOf(tokenSymbol.name()));
        }
    }

    public static String getSymbol(int key) { return INTEGER_TO_ENUM.get(key).symbol; }

}
