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
public enum ColorType {
    RW(Color.RED),
    FN(Color.ORANGE),
    DT(Color.YELLOW),
    LIT(Color.GREEN),
    ID(Color.BLUE),
    OP_ARIT(Color.CYAN),
    OP_REL(Color.MAGENTA),
    OP_LOG(Color.PINK),
    OP_ASIGN(Color.GRAY),
    DEL(Color.DARK_GRAY),
    ERR(Color.BLACK),
    EOF(Color.WHITE);

    private final Color color;

    ColorType(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public String getColorCode() {
        int rgb = color.getRGB();
        String hex = String.format("#%06x", rgb & 0xFFFFFF);
        return hex;
    }
}
