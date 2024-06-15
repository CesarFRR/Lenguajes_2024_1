package T2.MainTests.AST;

public class NodeExprBoolean extends Node3{

    public NodeExprBoolean(Node expr1, Node boolOperator, Node expr2) {
        super(expr1, boolOperator, expr2);
    }

    /**
     * @param level Nivel del nodo
     * @return String con la representación del árbol
     */
    @Override
    public String toString(int level) {
        String indent = "";
        for (int i = 0; i < level; i++) {
            indent += " ";
        }
        return symPrint(id) + "\n" +
                indent + " " + child1.toString(level + 1) + "\n" +
                indent + " " + child2.toString(level + 1) + "\n" +
                indent + " " + child3.toString(level + 1);
    }

    /**
     * Ejecuta la acción de este nodo.
     *
     * @return
     **/
    @Override
    public Object execute() {
        Object result;
        if (child1 != null  && child2 != null && child3 != null) {
            Object e1 = child1.execute();
            Object operator = child2.execute();
            Object e2 = child3.execute();
            if (!(operator instanceof String)) throw new IllegalStateException("El operador no es válido. (1)");

            if (e1 instanceof String || e2 instanceof String) {
                result = (e1.toString()).compareTo(e2.toString()) == 0;
            } else {
                double diff = 0;
                double epsilon = 1e-10; // o cualquier otro valor pequeño
                diff = ((Number) e1).doubleValue() - ((Number) e2).doubleValue();
                result = switch ((String) operator) {
                    case "<" -> Math.abs(diff) < epsilon ? false : diff < 0;
                    case "<=" -> Math.abs(diff) < epsilon ? true : diff < 0;
                    case ">" -> Math.abs(diff) < epsilon ? false : diff > 0;
                    case ">=" -> Math.abs(diff) < epsilon ? true : diff > 0;
                    case "==" -> Math.abs(diff) < epsilon;
                    case "!=" -> Math.abs(diff) > epsilon;
                    default -> "Operador no válido";
                };

                if (result instanceof String s) throw new IllegalStateException("El operador no es válido. (2)");
            }
        }else throw new IllegalStateException("No se pudo ejecutar la expresión booleana, falta algún operando u operando.");

        return result;
    }
}
