package T2.ParserTests;

import model.scanner.LexerAnalyzer;

public class UseParserAnalyzer {


        public static void main(String[] args) {
            String ruta = "src/data/ex1.txt";
            LexerAnalyzer lex = new LexerAnalyzer(ruta);
            System.out.println("\nTokens: ");
            lex.printTokens();
            System.out.println("\nerror tokens: ");
            lex.printErrorTokens();
        }


}
