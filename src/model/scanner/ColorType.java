/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package model.scanner;

import java.awt.Color;

/**
 *
 * @author Cesar_R
 */
// para fondo blanco / fondo oscuro, respectivamente:

public enum ColorType {
    RW(Color.decode("#7F0055"), Color.decode("#E06C75")), // Keywords (Purple)
    FN(Color.decode("#7F0055"), Color.decode("#E5C07B")), // Function (Purple)
    DT(Color.decode("#2A00FF"), Color.decode("#98C379")), // Data types (Blue)
    LIT(Color.decode("#008000"), Color.decode("#808000")), // Literals, e.g. Strings, numbers (Green)
    ID(Color.decode("#0000FF"), Color.decode("#61AFEF")), // Identifiers, e.g. variable names (Blue)
    OP_ARIT(Color.decode("#4B0082"), Color.WHITE), // Arithmetic operators (Indigo)
    OP_REL(Color.decode("#800080"), Color.decode("#C678DD")), // Relational operators (Purple)
    OP_LOG(Color.decode("#800080"), Color.decode("#C678DD")), // Logical operators (Purple)
    OP_ASIGN(Color.decode("#4B0082"), Color.WHITE), // Assignment operators (Indigo)
    DEL(Color.decode("#4B0082"), Color.WHITE), // Delimiters, e.g. braces, parentheses (Indigo)
    ERR(Color.decode("#FF0000"), Color.decode("#BE5046")), // Errors (Red)
    EOF(Color.decode("#000000"), Color.decode("#282C34")); // End of file (Black)

    private final Color color;
    private final Color dcolor;
    ColorType(Color color, Color dcolor) {
        this.color = color;
        this.dcolor = dcolor;
        
    }

    public Color getColor() {
        return color;
    }
    public Color getdColor(){
        return dcolor;
    }

    public String getColorCode() {
        int rgb = color.getRGB();
        String hex = String.format("#%06x", rgb & 0xFFFFFF);
        return hex;
    }
}

