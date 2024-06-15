package T2.MainTests.AST;

public class NodeVarAssignment extends Node2 {
    private String type;

    public NodeVarAssignment(Node identifier, Node value) {
        super(identifier, value);
    }

    /**
     * Devuelve la representación en String del árbol.
     *
     * @param level Nivel del nodo
     * @return String con la representación del árbol
     **/
    @Override
    public String toString(int level) {
        String indent = "";
        for (int i = 0; i < level; i++) {
            indent += " ";
        }
        //agregar el simbolo de asignacion =
        return symPrint(id) + "\n" +
                indent + " " + child1.toString(level + 1) + "\n" +
                indent + " " + (new NodeLeaf(id, "=")).toString(level + 1) + "\n" +
                indent + " " + child2.toString(level + 1);
    }

    /**
     * Ejecuta la acción de este nodo.
     *
     * @return
     **/
    @Override
    public Object execute() {
        TableAST table = new TableAST();
        String identifier = (String) child1.execute();
        Object finalValue = child2.execute();
        if (finalValue == null) throw new IllegalStateException("Value assigment for "+identifier+" is null.");
        if (table.existsId(identifier)) throw new IllegalStateException("Variable "+identifier+" not declared.");

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
