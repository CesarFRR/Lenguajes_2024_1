/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import model.Lexer;
public class LexerAnalyzer {

    private Lexer lexer;
    public LexerAnalyzer(Reader filePath) {
        Lexer lexer = new Lexer(new FileReader(filePath));
        Token token;
    }

    public static void main(String[] args) throws IOException {
        // Usar el archivo ex1.txt en la carpeta data
        LexerAnalyzer lexerAnalyzer = new LexerAnalyzer("../data/ex1.txt");
        Token token;
        do {
            token = lexerAnalyzer.lexer.next_token();
            if (token != null) {
                System.out.println("Token: " + token.text + ", Tipo: " + token.type);
            }
        } while (token.type != TokenType.EOF); // Continuar hasta que next_token() devuelva null, lo que indica el fin de archivo
    }
}