package T2.MainTests.AST;

public class NodeVarDeclaration extends Node3 {

    public NodeVarDeclaration(Node type, Node identifier, Node value) {
        super(type, identifier, value);
    }

    /**
     * Devuelve la representación en String del árbol.
     *
     * @param level Nivel del nodo
     * @return String con la representación del árbol
     **/
    // toString(0) para representar el árbol de una declaracion con tipo, identificador y valor
    @Override
    public String toString(int level) {
        String indent = "";
        for (int i = 0; i < level; i++) {
            indent += " ";
        }
        return symPrint(id) + "\n" +
                indent + " " + child1.toString(level + 1) + "\n" +
                indent + " " + child2.toString(level + 1) + "\n" +
                indent + " " + (new NodeLeaf(id, "=")).toString(level + 1) + "\n" +
                indent + " " + child3.toString(level + 1);
    }

    /**
     * Ejecuta la acción de este nodo.
     *
     * @return
     **/
    @Override
    public Object execute() {
        TableAST table = new TableAST();
        String type = (String) child1.execute();
        String identifier = (String) child2.execute();
        Object finalValue = child3.execute();
        finalValue = finalValue == null ? getDefaultValue(type) : finalValue;
        if (table.existsId(identifier)) throw new IllegalStateException("Variable "+identifier+" already declared.");
        switch (finalValue) {
            case Integer i -> table.setId(identifier, i);
            case String s -> table.setId(identifier, s);
            case Float f -> table.setId(identifier, f);
            default -> throw new IllegalStateException("Unexpected value: " + finalValue);
        }
        return null;
    }

    private Object getDefaultValue(String type) {
        return switch (type) {
            case "int" -> 0;
            case "float" -> 0.0;
            case "string" -> "";
            default -> throw new IllegalStateException("Unexpected value: " + type);
        };
    }
}
