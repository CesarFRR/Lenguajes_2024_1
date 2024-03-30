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

    private List<Token> tokens;
    private List<Token> etokens;

    public LexerAnalyzer(String filePath) {
        this.tokens = this.getTokensFromFile(filePath);
        this.etokens = this.getErrorTokens(tokens);
    }
    // Nuevo constructor que acepta un BufferedReader
    public LexerAnalyzer(BufferedReader reader) {
        this.tokens = this.getTokensFromFile(reader);
        this.etokens = this.getErrorTokens(tokens);
    }

    public static void main(String[] args) throws IOException {

        // Extraer tokens
        runComand("cmd /c cd");
        String ruta = "src/data/ex1.txt";

        LexerAnalyzer lex = new LexerAnalyzer(ruta);
        System.out.println("\nTokens: ");
        lex.printTokens();
        System.out.println("\nerror tokens: ");
        lex.printErrorTokens();

        //List<Token> etokens = lex.getErrorTokens();
    }

    public List<Token> getTokensFromFile(String filename) {
        List<Token> tokens = new ArrayList<>();
        try {
            File codigo = new File(filename);
            BufferedReader entrada = new BufferedReader(new InputStreamReader(new FileInputStream(codigo), "UTF8"));
            Lexer lexer = new Lexer(entrada);
            while (true) {
                Token token = lexer.yylex();
                if (token == null || token.type == TokenType.EOF) {
                    break;
                }
                tokens.add(token);
                //System.out.println(token);
            }
        } catch (FileNotFoundException ex) {
            System.out.println("El archivo no pudo ser encontrado... " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("Error al leer el archivo... " + ex.getMessage());
        }
        return tokens;
    }
    
    public List<Token> getTokensFromFile(BufferedReader entrada) {
        List<Token> tokens = new ArrayList<>();
        try {
            Lexer lexer = new Lexer(entrada);
            while (true) {
                Token token = lexer.yylex();
                if (token == null || token.type == TokenType.EOF) {
                    break;
                }
                tokens.add(token);
                //System.out.println(token);
            }
        } catch (FileNotFoundException ex) {
            System.out.println("El archivo no pudo ser encontrado... " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("Error al leer el archivo... " + ex.getMessage());
        }
        return tokens;
    }

    public List<Token> getTokens() {
        return this.tokens;
    }

    public List<Token> getErrorTokens(List<Token> list) {
        List<Token> etokens = new ArrayList<>();
        for (Token t : list) {
            //System.out.print(t.lexemeType);
            if ("ERR".equals(t.lexemeType)) {
                etokens.add(t);
                //System.out.println(t);
            }
        }
        return etokens;
    }

    public List<Token> getErrorTokens() {
        return this.etokens;
    }

    public void printTokens() {
        for (Token t : this.tokens) {
            System.out.println(t);
        }
    }
    
    public void printErrorTokens(){
        for (Token t : this.etokens) {
            System.out.println(t);
        }
    }
    
    
}
