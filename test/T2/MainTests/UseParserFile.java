package T2.MainTests;
import T2.MainTests.Data.*;
import java_cup.runtime.Symbol;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.Scanner;

public class UseParserFile {

    public static void main(String[] args) {
        boolean ConsoleInput = true;
        boolean StringInput = false;
        System.out.println("probando el Parser:");
        if (StringInput) {
            try {
                try {
                    String expresion = """

                            3*4;
                           """;
//                    Lexer lexer = new Lexer(new StringReader(expresion));
//                    Parser p = new Parser(lexer);
//
//                    Object result =  p.parse();
//                    System.out.println("Parsing completed. Result: " + ((Symbol) result).value+ "  --> "+result);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (Exception ex) {
                System.out.println("Error during parsing... " + ex.getMessage());
            }
            return;
        }
      if (ConsoleInput) {
            try {
                Scanner scanner = new Scanner(System.in);
                System.out.print("Digite su operacion: ");
                String input = scanner.nextLine();
//                Lexer lexer = new Lexer(new StringReader(input));
//                Parser p = new Parser(lexer);
//                Object result =  p.parse();
//                System.out.println("Parsing completed. Result: " + ((Symbol) result).value+ "  --> "+result);
            } catch (Exception ex) {
                System.out.println("Error during parsing... " + ex.getMessage());
            }
            return;
        }
    }
}
