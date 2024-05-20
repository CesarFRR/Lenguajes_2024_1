/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import java.awt.Color;
import java.awt.Font;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import view.Compilador;
import java.nio.file.Files;
import java.nio.file.Paths;
import javax.swing.JFrame;
import javax.swing.JTextPane;
import javax.swing.JScrollPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.AbstractDocument;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import utils.AutoCompleteDocumentFilter;

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

    public static boolean isWindows() {
        String osName = System.getProperty("os.name").toLowerCase();
        return osName.contains("windows");

    }

    public static boolean islinux() {
        String osName = System.getProperty("os.name").toLowerCase();
        return osName.contains("linux");

    }

    public static void initUXLookAndFeel(boolean isWindows, boolean isLinux) {

        javax.swing.UIManager.LookAndFeelInfo[] lista = javax.swing.UIManager.getInstalledLookAndFeels();

        // Convert to a list of names using stream and lambda
        List<String> osList = new ArrayList<>();
        for (javax.swing.UIManager.LookAndFeelInfo name : lista) {
            osList.add(name.getClassName());
        }
        System.out.print("lista: " + osList);
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
                    System.out.print("\nseleccionado LINUX: " + selectedStyle);
                    break;
                }
            }
            
        }

        if (selectedStyle == null) {
            selectedStyle = osList.getFirst();
        }
        try {

            System.out.print("seleccionado: " + selectedStyle);

            javax.swing.UIManager.setLookAndFeel(selectedStyle);

        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Compilador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Compilador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Compilador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Compilador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

    }

    public static void adjustCurrentDir() throws IOException, IOException {
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

    public static void generateFlexJavaFile(String FlexJavaTarget, boolean isWindows) {
        try {
            System.out.println("Generando analizador léxico java, directorio actual: ");
            String printDirComand = isWindows ? "cmd /c cd" : "pwd";
            runComand(printDirComand);
            System.out.println("");
            String jflexComand = "java -jar lib/jflex-full-1.9.1.jar -d " + FlexJavaTarget + " src/data/Lexer.flex";
            runComand(jflexComand);
        } catch (IOException ex) {
            Logger.getLogger(utils.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static boolean fileExists(String path, String fileName) {
        return Files.exists(Paths.get(path, fileName));
    }

    public static void enumerateJTextComponent(JTextPane textPane, JScrollPane scrollPane) {
        textPane.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 14));
        TextLineNumber tln = new TextLineNumber(textPane);
        scrollPane.setRowHeaderView(tln);
    }

    public static void autocompleteJTextComponent(JTextPane textPane, JScrollPane scrollPane) {
        ((AbstractDocument) textPane.getDocument()).setDocumentFilter(new AutoCompleteDocumentFilter(textPane));
        // Obtén el StyledDocument del JTextPane

    }

    public static void highlightMain(JTextPane textPane, Style style) {
        // Get the document and text
        StyledDocument doc = textPane.getStyledDocument();
        String text = textPane.getText();

        // Remove any existing highlighting (optional, for efficiency)
        int index = 0;
        while ((index = text.indexOf("main", index)) != -1) {
            // Apply the style to each occurrence of "main"
            doc.setCharacterAttributes(index, 4, style, true);
            index += 4; // Move the search index after "main"
        }

        // Re-add the DocumentListener for continuous highlighting
        doc.addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                highlightMain(textPane, style);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                highlightMain(textPane, style);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                // Not needed for this specific case
            }
        });
    }
}
