package model.parser.AST;
import model.parser.ParserSym;

public class NodeExprBoolean extends Node3 implements InterfaceExpr{

    public NodeExprBoolean(int id, Node expr1, Node boolOperator, Node expr2) {
        super(id, expr1, boolOperator, expr2);
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
        if (child1 != null  && child2 != null) {
            Object e1 = getRealValue(child1.execute());
            Object operator = getRealValue(child2.execute());
            Object e2 = getRealValue(child3.execute());
            if (!(operator instanceof String)) throw new IllegalStateException("El operador no es válido. (1)");

            if (e1 instanceof String || e2 instanceof String) {
                Object a, b;
                a = e1 instanceof Node n? n.execute() : e1;
                b = e2 instanceof Node n? n.execute() : e2;
                a = a.toString().replace("\"", "");
                b = b.toString().replace("\"", "");
                result = a.toString().equals(b.toString());
                if (operator.equals("!=")) result = !((boolean) result);

            }else if(e1 instanceof Boolean && e2 instanceof Boolean){
                result = switch ((String) operator) {
                    case "&&" -> (Boolean) e1 && (Boolean) e2;
                    case "||" -> (Boolean) e1 || (Boolean) e2;
                    default -> "Operador no válido";
                };
            } else if(e1 instanceof Boolean && e2 == null && operator.equals("!")){
                result = !(Boolean) e1;

            } else {
                double diff = 0;
                double epsilon = 1e-10; // o cualquier otro valor pequeño
//                System.out.println("e1: " + e1 + " e2: " + e2 + " operator: " + operator + "\n");
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
