package utils;

import javax.swing.*;
import java.io.IOException;
import java.io.OutputStream;

public class Console extends OutputStream {
    private JTextArea textArea;

    public Console(JTextArea textArea) {
        this.textArea = textArea;
    }

    @Override
    public void write(int b) throws IOException {
        // redirige los datos a la zona de texto
        textArea.append(String.valueOf((char)b));
        // asegura que el último dato se muestra
        textArea.setCaretPosition(textArea.getDocument().getLength());
    }
}