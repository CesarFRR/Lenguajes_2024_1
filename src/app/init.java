/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package app;

import java.io.IOException;
import utils.utils;
import view.Compilador;
import controller.MainController;
/**
 *
 * @autor TriviGod
 */
public class init {
    

    public static void main(String[] args) throws IOException, InterruptedException {
        String FlexJavaTarget = "src/model/scanner/";

        if (args.length > 0){ //for tests only
            FlexJavaTarget = args[0];
        }
       
        
        
        MainController ctr = new MainController(FlexJavaTarget);
        ctr.start();

    }
    
}




