/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package app;

import static utils.utils.*;
import java.io.IOException;

/**
 *
 * @autor TriviGod
 */
public class init {

    public static void main(String[] args) throws IOException, InterruptedException {
        String FlexJavaTarget = "src/model/scanner/";
        if (args.length > 0){
            FlexJavaTarget = args[0];
        }
        String osName = System.getProperty("os.name").toLowerCase();
        boolean isWindows = osName.contains("windows");
        boolean isLinux = osName.contains("linux");
         
        adjustCurrentDir();
        initUXLookAndFeel(isWindows, isLinux);
        
        

        generateFlexJavaFile(FlexJavaTarget, isWindows);

    }

}




