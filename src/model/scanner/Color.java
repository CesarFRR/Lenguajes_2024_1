/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package model.scanner;

/**
 *
 * @author Cesar_R
 */
 public enum Color {
    RW("#E003"),
    FN("#F004"),
    DT("#D005"),
    LIT("#C006"),
    ID("#B007"),
    OP_ARIT("#A008"),
    OP_REL("#9009"),
    OP_LOG("#8010"),
    OP_ASIGN("#7011"),
    DEL("#6012"),
    ERR("#5013"),
    EOF("#4014");

    private final String colorCode;

    Color(String colorCode) {
        this.colorCode = colorCode;
    }

    public String getColorCode() {
        return colorCode;
    }
}
