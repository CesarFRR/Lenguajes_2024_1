/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ScannerTests;

/**
 *
 * @author Cesar_R
 */
import model.scanner.LexerAnalyzer;
public class UseLexerAnalyzer {
    public static void main(String[] args) {
        String ruta = "src/data/ex1.txt";
        LexerAnalyzer lex = new LexerAnalyzer(ruta);
        System.out.println("\nTokens: ");
        lex.printTokens();
        System.out.println("\nerror tokens: ");
        lex.printErrorTokens();
    }
}
