package model.scanner.Token;
import java_cup.runtime.Symbol;

public class Token extends Symbol {
    public TokenType type;
    public ColorType color;
    public String lexemeType;

    public Token(TokenType type, String text, int line, int column) {
        super(type.ordinal(), line, column, text);
        this.type = type;
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
        return this.value.toString();
    }

    public int getLine() {
        return this.left;
    }

    public int getColumn() {
        return this.right;
    }

    @Override
    public String toString() {
        return "Token{"
                + "type=" + this.type
                + ", text=" + this.getText()
                + ", line=" + this.getLine()
                + ", column=" + this.getColumn()
                + ", lexemeType=" + this.lexemeType
                + ", color=" + this.lexemeType+":"+this.getColorCode()+ "}";
    }

}
