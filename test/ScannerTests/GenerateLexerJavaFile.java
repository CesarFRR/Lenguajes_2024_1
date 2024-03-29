/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ScannerTests;

/**
 *
 * @author Cesar_R
 */
import model.scanner.Token;
import model.scanner.TokenType;
import app.init;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
public class GenerateLexerJavaFile {
    
    public static void main(String[] args) {
        try {
            String[] target = {"test/ScannerTests"};
            init.main(args = target);
        } catch (IOException ex) {
            Logger.getLogger(GenerateLexerJavaFile.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(GenerateLexerJavaFile.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
