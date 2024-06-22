package model.parser;

import java_cup.runtime.Symbol;
import model.parser.AST.Node;
import model.parser.AST.TableAST;
import model.scanner.Lexer;
import model.scanner.Token.Token;
import model.scanner.Token.TokenType;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ParserAnalyzer {
    private String filePath;
    private Lexer lexer;
    private Parser parser;
    private Object result;
    private List<Token> tokens;
    private List<Token> etokens;
     private TableAST table = new TableAST();

    public ParserAnalyzer(String filePath) throws Exception {

        this.filePath = filePath;
        this.runParser(filePath);
        this.tokens = this.lexer.getTokens();
        this.etokens = this.lexer.getErrors();
    }

    public ParserAnalyzer(BufferedReader reader) throws Exception {

        this.runParser(reader);
        this.tokens = this.lexer.getTokens();
        this.etokens = this.lexer.getErrors();
    }

    private Parser runParser(Object filename) throws Exception {
        table.cleanidTable();
        table.cleanfnTable();
        if (filename instanceof BufferedReader bff) {

            this.lexer = new Lexer(bff);
        }else{
            File codigo = new File((String) filename);
            BufferedReader entrada = null;
            try {
                entrada = new BufferedReader(new InputStreamReader(new FileInputStream(codigo), "UTF8"));
            } catch (UnsupportedEncodingException | FileNotFoundException e) {
                throw new RuntimeException(e);            }
            this.lexer = new Lexer(entrada);
        }
        this.parser = new Parser(this.lexer);

            this.result =  this.parser.parse().value;


        return this.parser;
    }
    public Object execute(){

        if(this.parser == null || this.result == null){
            return null;
        }

        return ((Node)this.result).execute();

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

    public Parser getParser() {
        return this.parser;
    }
    public Lexer getLexer() {
        return this.lexer;
    }
}
