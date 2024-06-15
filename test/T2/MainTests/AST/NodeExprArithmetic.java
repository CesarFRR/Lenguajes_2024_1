package T2.MainTests.AST;

public class NodeExprArithmetic extends Node3{

    public NodeExprArithmetic(Node expr1, Node operator, Node expr2) {
        super(expr1, operator, expr2);
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
     * Ejecuta la acción de este nodo.
     *
     * @return
     **/
    @Override
    public Object execute() {
        Object left = child1.execute();
        Object operator = child2.execute();
        Object right = child3.execute();
        switch (operator.toString()) {
            case "+" -> {
                if (isType(left, "String") || isType(right, "String")) {
                    return left.toString() + right.toString();
                } else if (isType(left, "Float") || isType(right, "Float")) {
                    return Float.parseFloat(left.toString()) + Float.parseFloat(right.toString());
                } else {
                    return Integer.parseInt(left.toString()) + Integer.parseInt(right.toString());
                }
            }
            case "-" -> {
                if (isType(left, "Float") || isType(right, "Float")) {
                    //this.result_type = "Float";
                    return Float.parseFloat(left.toString()) - Float.parseFloat(right.toString());
                } else {
                    //this.result_type = "Integer";
                    return Integer.parseInt(left.toString()) - Integer.parseInt(right.toString());
                }
            }
            case "*" -> {
                if (isType(left, "Float") || isType(right, "Float")) {
                    //this.result_type = "Float";
                    return Float.parseFloat(left.toString()) * Float.parseFloat(right.toString());
                } else {
                    //this.result_type = "Integer";
                    return Integer.parseInt(left.toString()) * Integer.parseInt(right.toString());
                }
            }
            case "/" -> {
                if (isType(left, "Float") || isType(right, "Float")) {
                    //this.result_type = "Float";
                    return Float.parseFloat(left.toString()) / Float.parseFloat(right.toString());
                } else {
                    //this.result_type = "Integer";
                    return Integer.parseInt(left.toString()) / Integer.parseInt(right.toString());
                }
            }
            case "%" -> {
                if (isType(left, "Float") || isType(right, "Float")) {
                    //this.result_type = "Float";
                    return Float.parseFloat(left.toString()) % Float.parseFloat(right.toString());
                } else {
                    //this.result_type = "Integer";
                    return Integer.parseInt(left.toString()) % Integer.parseInt(right.toString());
                }
            }
            case "^" -> {
                if (isType(left, "Float") || isType(right, "Float")) {
                    //this.result_type = "Float";
                    return Math.pow(Float.parseFloat(left.toString()), Float.parseFloat(right.toString()));
                } else {
                    //this.result_type = "Integer";
                    return Math.pow(Integer.parseInt(left.toString()), Integer.parseInt(right.toString()));
                }
            }
            default -> {
                throw new IllegalStateException("Operador no válido");
            }
        }
    }


    public static boolean isType(Object obj, String type) {
        String actualType = getDataType(obj);
        return actualType.equals(type);
    }

    public static String getDataType(Object obj) {
        switch (obj) {
            case Integer i -> {
                return "Integer";
            }
            case Float v -> {
                return "Float";
            }
            case String s -> {
                String str = obj.toString();
                if (str.matches("\\d+\\.\\d+")) {
                    return "Float";
                } else {
                    return "String";
                }
            }
            case null, default -> {
                return "Unknown";
            }
        }
    }
}
