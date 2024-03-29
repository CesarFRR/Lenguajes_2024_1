/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.scanner;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import static utils.utils.*;

public class LexerAnalyzer {
    public LexerAnalyzer(Reader filePath) {
        Token token;
    }

    public static void main(String[] args) throws IOException {

        // Extraer tokens
        
        try {
            runComand("cmd /c cd");
            File codigo = new File("src/data/ex1.txt");
            BufferedReader entrada = new BufferedReader(new InputStreamReader(new FileInputStream(codigo), "UTF8"));
            Lexer lexer = new Lexer(entrada);
            List<Token> tokens = new ArrayList<>();
            while (true) {
                Token token = lexer.yylex();
                if (token == null || token.type == TokenType.EOF) {
                    break;
                }
                tokens.add(token);
                System.out.println(token);
            }
        } catch (FileNotFoundException ex) {
            System.out.println("El archivo no pudo ser encontrado... " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("Error al leer el archivo... " + ex.getMessage());
        }
    }
}