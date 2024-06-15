package T2.MainTests.AST;
import T2.MainTests.Token.TokenType;

public abstract class Node {

    public int id;

    public Node() {
        this.id = 0;
    }

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
        return TokenType.getSymbol(id);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


}

