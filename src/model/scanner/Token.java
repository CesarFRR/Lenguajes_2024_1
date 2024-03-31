package model.scanner;
import java.awt.Color;

public class Token {

    public TokenType type;
    public String text;
    public int line;
    public int column;
    public ColorType color;
    public String lexemeType;

    public Token(TokenType type, String text, int line, int column) {
        this.type = type;
        this.text = text;
        this.line = line;
        this.column = column;
        this.color = this.type.getColor();
        this.lexemeType = this.color.name();
    }

    /**
     * Devuelve en string el nombre del token, ej: OP_SUMA, NUM_ENTERO, etc
     */
    public String getType() {
        return this.type.name();
    }

    /**
     * Devuelve en string el nombre del lexema: si es Funcion, palabra
     * reservada, delimitador, etc
     */
    public String getLexemeType() {
        return lexemeType;
    }

    /**
     * Devuelve en string el codigo de color del tipo de lexema, ej: "#E003F
     */
    public ColorType getColor() {
        return this.color;
    }
    public String getColorCode() {
        return this.color.getColorCode();
    }

    public TokenType getTokenType() {
        return type;
    }

    public String getText() {
        return text;
    }

    public int getLine() {
        return line;
    }

    public int getColumn() {
        return column;
    }

    @Override
    public String toString() {
        return "Token{"
                + "type=" + this.type
                + ", text=" + this.text
                + ", line=" + this.line
                + ", column=" + this.column
                + ", lexemeType=" + this.lexemeType
                + ", color=" + this.lexemeType+":"+this.getColorCode()+ "}";
    }

}
