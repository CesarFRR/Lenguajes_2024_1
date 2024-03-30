/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.scanner.Token;
import model.scanner.TokenType;
import view.UX;

/**
 *
 * @author Cesar_R
 */
public class utils {
    
    public static void runComand(String comand) throws IOException {
        String readyComand[] = comand.split(" ");
        File currentDir = new File(".");

        // Ejecuta el comando
        ProcessBuilder processBuilder = new ProcessBuilder(readyComand);
        processBuilder.directory(currentDir);  // Establece el directorio de trabajo

        Process process = null;
        int exitCode = 0;
        try {
            process = processBuilder.start();
            exitCode = process.waitFor();
        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }

        // Captura y muestra la salida del comando
        if (exitCode == 0 && process != null) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } else {
            System.out.println("Command execution failed with exit code: " + exitCode);
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        }
    }

    public static void initUXLookAndFeel(boolean isWindows, boolean isLinux) {

        javax.swing.UIManager.LookAndFeelInfo[] lista = javax.swing.UIManager.getInstalledLookAndFeels();

        // Convert to a list of names using stream and lambda
        List<String> osList = new ArrayList<>();
        for (javax.swing.UIManager.LookAndFeelInfo name : lista) {
            osList.add(name.getClassName());
        }
        //System.out.print("lista: " + osList);
        String selectedStyle = null;

        if (isWindows) {
            for (String name : osList) {
                if (name.toLowerCase().contains("windows")) {
                    selectedStyle = name;
                    break;
                }
            }
        } else if (isLinux) {
            for (String name : osList) {
                if (name.toLowerCase().contains("gtk") || name.toLowerCase().contains("motif")) {
                    selectedStyle = name;
                    break;
                }
            }
        }

        if (selectedStyle == null) {
            selectedStyle = "Nimbus";
        }
        try {

            System.out.print("seleccionado: " + selectedStyle);

            javax.swing.UIManager.setLookAndFeel(selectedStyle);

        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(UX.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(UX.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(UX.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UX.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

    }
    
    public static void adjustCurrentDir() throws IOException, IOException{
        File currentDir = new File(".");
        File targetDir = new File("Lenguajes_2024_1");

        // Si el directorio "Lenguajes_2024_1" existe en el directorio actual, cambia a ese directorio
        if (new File(currentDir, "Lenguajes_2024_1").exists()) {
            currentDir = new File(currentDir, "Lenguajes_2024_1");
        } // De lo contrario, sube en la jerarquía de directorios hasta que el directorio actual sea "Lenguajes_2024_1"
        else if (!currentDir.getCanonicalPath().endsWith("Lenguajes_2024_1")) {
            while (!currentDir.getCanonicalPath().endsWith("Lenguajes_2024_1")) {
                currentDir = currentDir.getParentFile();
            }
        }
    }
    
  
}
