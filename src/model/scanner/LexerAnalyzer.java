/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.scanner;

import java_cup.runtime.Symbol;
import model.scanner.Token.Token;
import model.scanner.Token.TokenType;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import static utils.utils.*;

public class LexerAnalyzer {

    private List<Token> tokens;
    private List<Token> etokens;
    private Lexer lexer;
    public LexerAnalyzer(String filePath) {
        this.runLexer(filePath);
        this.tokens = this.lexer.getTokens();
        this.etokens = this.lexer.getErrors();
    }
    // Nuevo constructor que acepta un BufferedReader
    public LexerAnalyzer(BufferedReader reader) {
        this.runLexer(reader);
        this.tokens = this.lexer.getTokens();
        this.etokens = this.lexer.getErrors();
    }

    private Lexer runLexer(Object filename) {
        if (filename instanceof BufferedReader) {
            this.lexer = new Lexer((BufferedReader) filename);
        }else{
            File codigo = new File((String) filename);
            BufferedReader entrada = null;
            try {
                entrada = new BufferedReader(new InputStreamReader(new FileInputStream(codigo), "UTF8"));
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            this.lexer = new Lexer(entrada);
        }

        while (true) {
            Symbol token = null;
            try {
                token = lexer.next_token();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if (token == null || token.sym == TokenType.EOF.ordinal()) {
                break;
            }
        }
        return this.lexer;
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
        return this.runLexer(filename).getTokens();
    }
    
    public List<Token> getTokensFromFile(BufferedReader entrada) {
        return this.runLexer(entrada).getTokens();
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
