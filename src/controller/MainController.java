/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.io.IOException;
import javax.swing.ImageIcon;
import model.scanner.LexerAnalyzer;
import utils.utils;
import view.Compilador;

/**
 *
 * @author Cesar_R
 */
public  class MainController {
    
    public Compilador UX;
    
    public  MainController(String FlexJavaTarget) throws IOException {
        

        String osName = System.getProperty("os.name").toLowerCase();
        boolean isWindows = osName.contains("windows");
        utils.adjustCurrentDir();
        
        if (!utils.fileExists("src/model/scanner", "Lexer.java")) {
            System.out.println("\nNo existe Lexer.java, generando...\n");
            utils.generateFlexJavaFile(FlexJavaTarget, isWindows);
        }
        utils.initUXLookAndFeel(isWindows, isWindows); 
    }
    
    public void start(){
        Compilador UX = new Compilador();
        ImageIcon img = new ImageIcon("src/data/logo.png");
        UX.setIconImage(img.getImage());
        UX.setVisible(true);
    }
}
