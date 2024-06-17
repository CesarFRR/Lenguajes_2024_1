package App;/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */



import java.io.IOException;

import controller.MainController;
/**
 *
 * @autor TriviGod
 */
public class App {
    

    public static void main(String[] args) throws IOException, InterruptedException {
        String FlexJavaTarget = "src/model/scanner/";

        if (args.length > 0){ //for tests only
            FlexJavaTarget = args[0];
            System.out.println("FlexJavaTarget cambiado!!!: " + FlexJavaTarget);
        }
       
        
        
        MainController ctr = new MainController(FlexJavaTarget);
        ctr.start();

    }
    
}




