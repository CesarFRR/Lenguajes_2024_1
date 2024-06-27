package model.parser.AST;
import model.parser.ParserSym;
import model.scanner.Token.TokenType;

public abstract class Node {

    public int id;
    public  static  int count = 0;

    public Node() {
        this.id = 0;
    }

    /**
     * Constructor de la clase Node, si se pasa -1 como id, se le buscará el id correspondiente al valor.
     * @param id
     */
    public Node(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return toString(0);
    }

    /** Devuelve la representación en String del árbol.
     * @param level Nivel del nodo
     * @return String con la representación del árbol
     **/
    public abstract String toString(int level);
    /** Ejecuta la acción de este nodo.
     * @return
     **/

    public abstract Object execute();

    public String symPrint(int id) {
        // Aquí puedes agregar los símbolos que necesites
        if(id < 0) return "-1";
        return TokenType.getSymbol(id);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Object getValue() {
        return null;
    }

    public Object copy(){
        return "**Object**";
    }
}

