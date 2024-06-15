package T2.MainTests.AST;

public class NodeLeaf extends Node{
    /** The generic value of this node, used for identifier names and literal values. **/
    public Object value;

    public NodeLeaf(int id, Object value) {
        super(id);
        this.value = value;
    }

    @Override
    public String toString(int level) {
        return value.toString();
    }

    /**
     * Ejecuta la acción de este nodo.
     *
     * @return
     **/
    @Override
    public Object execute() {
        if (value != null) {
            if (value instanceof Node) {
                System.out.println("-> " + ((Node) value).execute());
            } else {
                System.out.println("-> " + value);
            }
        }
        return value;
    }

}
