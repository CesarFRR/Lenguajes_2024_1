package T2.MainTests.AST;

public class NodeVar extends Node3{

    public NodeVar(Node type, Node name, Node value) {
        super(type, name, value);
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

    /**
     * Al llamar a execute se ejecuta el nodo y se devuelve el valor de la variable
     *
     * @return
     **/
    @Override
    public Object execute() {
        return child3.execute();
    }
    public String getType() {
        return (String) child1.execute();
    }
    public void setType(Object type) {
        child1 = (type instanceof Node)? (Node) type : new NodeLeaf(0, type);
    }

    public String getName() {
        return (String) child2.execute();
    }
    public void setName(Object name) {
        child2 = (name instanceof Node)? (Node) name : new NodeLeaf(0, name);
    }



    public void setValue(Object value) {
        child3 = (value instanceof Node)? (Node) value : new NodeLeaf(0, value);
       // Node forma2 = value;
    }
}
