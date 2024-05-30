/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package T1.ScannerTests;

import java.io.*;

import static utils.utils.runComand;

/**
 *
 * @author Cesar_R
 */

public class UseLexerFile {
    
    /* El package del Lexer.java generado tiene que cambiarse a...*/
    /*    package ScannerTests;   */
  public static void main(String[] args) throws IOException {

        
        
        try {
            runComand("cmd /c cd");
            String ruta = "src/data/ex1.txt";
            String ruta2 = "test/T1/ScannerTests/test1.txt";
            File codigo = new File(ruta);
            BufferedReader entrada = new BufferedReader(new InputStreamReader(new FileInputStream(codigo), "UTF8"));
//            Lexer lexer = new Lexer(entrada);
//            List<Token> tokens = new ArrayList<>();
//            while (true) {
//                Token token = lexer.yylex();
//                if (token == null || token.type == TokenType.EOF) {
//                    break;
//                }
//                tokens.add(token);
//                System.out.println(token);
//            }
        } catch (FileNotFoundException ex) {
            System.out.println("El archivo no pudo ser encontrado... " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("Error al leer el archivo... " + ex.getMessage());
        }
    }
}
