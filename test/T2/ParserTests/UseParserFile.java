package T2.ParserTests;


import T2.ScannerTests.Parser;

import java.io.File;
import java.io.*;


import static utils.utils.runComand;
public class UseParserFile {

    /* El package del Lexer.java y Parser.java generados tiene que cambiarse a...*/
    /*    package ScannerTests;   */


//    public static void main(String[] args) throws Exception {
//        Scanner scanner = new Scanner(System.in);
//        Lexer lexer = new Lexer(scanner);
//        Symbol symbol = lexer.yylex();
//
//        Parser parser = new Parser((java_cup.runtime.Scanner) lexer);
//        Object result = parser.parse();
//        System.out.println("Resultado: " + result);
//    }
    public static void main(String[] args) throws IOException {
        boolean ConsoleInput = true;
//        boolean StringInput = false;
//        if (StringInput) {
//            try {
//                String input = "=";
//                Lexer lexer = new Lexer(new StringReader(input));
//                Parser parser = new Parser(lexer);
//                Object result = parser.parse();
//                System.out.println("Parsing completed. Result: " + result);
//            } catch (Exception ex) {
//                System.out.println("Error during parsing... " + ex.getMessage());
//            }
//            return;
//        }
//        if (ConsoleInput) {
//            try {
//                System.out.print("Digite su operacion: ");
//                BufferedReader entrada = new BufferedReader(new InputStreamReader(System.in, "UTF8"));
//                Lexer lexer = new Lexer(entrada);
//                //System.out.println("new lexer() completo!!!");
//                Parser parser = new Parser(lexer);
//                Object result = parser.parse();
//                System.out.println("Parsing completed. Result: " + result);
//            } catch (IOException ex) {
//                System.out.println("Error al leer la entrada... " + ex.getMessage());
//            } catch (Exception ex) {
//                System.out.println("Error during parsing... " + ex.getMessage());
//            }
//            return;
//        }
//
//        try {
//            String osName = System.getProperty("os.name").toLowerCase();
//            boolean isWindows = osName.contains("windows");
//            String comand = isWindows ? "cmd /c cd" : "pwd";
//            runComand(comand);
//            String ruta = "test/T2/ParserTests/test1.txt";
//            String ruta2 = "test/T2/ScannerTests/test1.txt";
//            String ruta3 = "test/T2/ParserTests/test2.txt";
//            File codigo = new File(ruta3);
//            BufferedReader entrada = new BufferedReader(new InputStreamReader(new FileInputStream(codigo), "UTF8"));
//            Lexer lexer = new Lexer(entrada);
//            System.out.println("new lexer() completo!!!");
//            Parser parser = new Parser(lexer);
//            Object result = parser.parse();
//            System.out.println("Parsing completed. Result: " + result);
//        } catch (FileNotFoundException ex) {
//            System.out.println("El archivo no pudo ser encontrado... " + ex.getMessage());
//        } catch (IOException ex) {
//            System.out.println("Error al leer el archivo... " + ex.getMessage());
//        } catch (Exception ex) {
//            System.out.println("Error during parsing... " + ex.getMessage());
//        }
    }
}
