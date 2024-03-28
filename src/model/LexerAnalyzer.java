/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;
/**
 *
 * @author Cesar_R
 */
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import java_cup.runtime.Symbol;

public class LexerAnalyzer {

    private Lexer lexer;
    public LexerAnalyzer(Reader reader) {
        this.lexer = new Lexer(reader);
    }

    public Token nextToken() throws IOException {
        Token token = null;
        Symbol symbol = lexer.yylex();
        switch (symbol.sym) {
            case Lexer.IDENTIFICADOR:
                token = new Token(TokenType.IDENTIFICADOR, symbol.value.toString());
                break;
            case Lexer.NUMERO:
                token = new Token(TokenType.NUMERO, symbol.value.toString());
                break;
            case Lexer.OPERADOR:
                token = new Token(TokenType.OPERADOR, symbol.value.toString());
                break;
            case Lexer.DELIMITADOR:
                token = new Token(TokenType.DELIMITADOR, symbol.value.toString());
                break;
            case Lexer.PALABRA_RESERVADA:
                token = new Token(TokenType.PALABRA_RESERVADA, symbol.value.toString());
                break;
            case Lexer.EOF:
                token = new Token(TokenType.EOF, null);
                break;
            default:
                // Handle lexical errors
                throw new IOException("Lexical error: " + symbol.value.toString());
        }
        return token;
    }

    public static void main(String[] args) throws IOException {
        // Reemplazar "ruta/archivo.txt" con la ruta real de tu archivo de prueba
        Reader reader = new FileReader("ruta/archivo.txt");
        LexerAnalyzer lexerAnalyzer = new LexerAnalyzer(reader);
        Token token;
        do {
            token = lexerAnalyzer.nextToken();
            System.out.println(token);
        } while (token.getType() != TokenType.EOF);
    }
}

enum TokenType {
    IDENTIFICADOR,
    NUMERO,
    OPERADOR,
    DELIMITADOR,
    PALABRA_RESERVADA,
    EOF
}

class Token {

    private TokenType type;
    private String lexeme;

    public Token(TokenType type, String lexeme) {
        this.type = type;
        this.lexeme = lexeme;
    }

    public TokenType getType() {
        return type;
    }

    public String getLexeme() {
        return lexeme;
    }

    @Override
    public String toString() {
        return "[" + type + ", " + lexeme + "]";
    }
}
