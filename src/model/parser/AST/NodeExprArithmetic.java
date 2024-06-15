package model.parser.AST;

public class NodeExprArithmetic extends Node3 implements InterfaceExpr{

    public NodeExprArithmetic(int id, Node expr1, Node operator, Node expr2) {
        super(id, expr1, operator, expr2);
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
        Object left = getRealValue(child1.execute()); // getRealValue(
        Object operator = getRealValue(child2.execute());
        Object right = getRealValue(child3.execute());

        switch (operator.toString()) {
            case "+" -> {
                if (isType(left, "String") || isType(right, "String")) {
                    return (left.toString() + right.toString()).replace("\"", "");
                } else if (isType(left, "Float") || isType(right, "Float")) {
                    return Float.parseFloat(left.toString()) + Float.parseFloat(right.toString());
                } else {

                    //System.out.println("left: " + left.toString() + " right: " + right.toString());
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
                if (Float.parseFloat(right.toString()) == 0) {
                    throw new IllegalStateException("No se puede dividir por 0");
                }
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
                Double result;
                if (isType(left, "Float") || isType(right, "Float")) {
                    result = Math.pow(Float.parseFloat(left.toString()), Float.parseFloat(right.toString()));
                } else {
                    result = Math.pow(Integer.parseInt(left.toString()), Integer.parseInt(right.toString()));
                }

                if (result.equals(Math.floor(result))) {
                    // result es un número entero, devolver como int
                    return result.intValue();
                } else {
                    // result no es un número entero, devolver como float
                    return result.floatValue();
                }
            }
            default -> {
                throw new IllegalStateException("Operador no válido");
            }
        }
    }


    public static boolean isType(Object obj, String type) {
        String actualType = getDataType(obj);

        if (actualType.equals("Double") && type.equals("Float")) {
            return true;
        }
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
