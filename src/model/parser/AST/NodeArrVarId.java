package model.parser.AST;
import model.parser.ParserSym;

public class NodeArrVarId extends Node implements InterfaceExpr, InterfaceStruct{
    private static TableAST table;
    private String identifier;

    public void setIndexes(Node indexes) {
        this.indexes = indexes;
    }

    private Node indexes;
    static {
        table = new TableAST();
    }
    public NodeArrVarId(int id, String identifier, Node indexes) {
        super(id);
        if(this.id ==-1){
            setId(ParserSym.IDENTIFICADOR);
        }
        this.identifier = identifier;
        this.indexes = indexes;
    }


    /**
     * Devuelve la representación en String del árbol.
     *
     * @param level Nivel del nodo
     * @return String con la representación del árbol
     **/
    @Override
    public String toString(int level) {
        return "";
    }

    @Override
    public String toString() {
        Object s = table.getId(identifier);
        return s == null ? "null" : s.toString();
    }

    /**
     * Ejecuta la acción de este nodo.
     *
     * @return
     **/
    @Override
    public Object execute() {

       // System.out.println("Ejecutando NodeArrVarId");
        if (table.getId(identifier) == null) {
            throw new RuntimeException("Variable arreglo " + identifier + " no definida.");
        }

        NodeArrVar var = (NodeArrVar)table.getId(identifier);
        int[] dimAccess = this.convertListToIntArray(this.getIntValuesList((NodeTree) this.indexes));
        int[] shape = var.getShape();
        if(dimAccess.length > shape.length) {
            throw new RuntimeException("Error: Acceso a una dimensión inexistente en la matriz");
        }
        //quiero comprobar que los indices esten dentro de los limites de la matriz y sus shapes
        for(int i = 0; i < dimAccess.length; i++) {
            if(dimAccess[i] >= shape[i]){
                throw new RuntimeException("Error: Acceso a una posición fuera de los límites de la matriz");
            }
            if (dimAccess[i] < 0) {
                // quiero hacer como en python, -1 es el último elemento, -2 el penúltimo, etc
                dimAccess[i] = shape[i] + dimAccess[i];
                // si sigue siendo negativo, entonces es un error
                if(dimAccess[i] < 0) {
                    throw new RuntimeException("Error: Acceso a una posición fuera de los límites de la matriz");
                }
            }
        }



        return var.get(dimAccess);
    }

    public Object getValue(){
        return this.execute();
    }
    public String getName() {
        return identifier;
    }
    public Node getIndexes() {
        return indexes;
    }
    public int[] getIndexesArray() {
        int[] dimAccess = this.convertListToIntArray(this.getIntValuesList((NodeTree) this.indexes));
        return dimAccess;
    }
}
