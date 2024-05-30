/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package T2.ScannerTests;

import java_cup.runtime.Symbol;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
            String osName = System.getProperty("os.name").toLowerCase();
            boolean isWindows = osName.contains("windows");
            String comand = isWindows ? "cmd /c cd" : "pwd";
            runComand(comand);
            String ruta = "src/data/ex1.txt";
            String ruta2 = "test/T2/ScannerTests/test1.txt";
            File codigo = new File(ruta);
            BufferedReader entrada = new BufferedReader(new InputStreamReader(new FileInputStream(codigo), "UTF8"));
            Lexer lexer = new Lexer(entrada);
            List<Symbol> tokens = new ArrayList<>();
            while (true) {
                Symbol token = lexer.next_token();
                assert token != null;
                if (token == null | Objects.equals(token.toString(), "#0")) {
                    break;
                }
                tokens.add(token);
                System.out.println(token.toString());
            }
            if (tokens.get(0) instanceof Symbol) {
                System.out.println("SI ES HEREDERA DE SYMBOL!!!!!!!");
            }
            System.out.println("Cantidad de tokens: " + tokens.size());
            System.out.println("tokens: " + lexer.getTokens());
            System.out.println("Errores: " + lexer.getErrors());
        } catch (FileNotFoundException ex) {
            System.out.println("El archivo no pudo ser encontrado... " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("Error al leer el archivo... " + ex.getMessage());
        }
    }
}
