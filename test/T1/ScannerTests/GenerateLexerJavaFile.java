/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package T1.ScannerTests;

/**
 *
 * @author Cesar_R
 */

import utils.utils;

public class GenerateLexerJavaFile {
    
    public static void main(String[] args) {
        String[] target = {"test/T1/ScannerTests"};
        utils.generateFlexJavaFile(target[0], utils.isWindows());
    }
    
}
