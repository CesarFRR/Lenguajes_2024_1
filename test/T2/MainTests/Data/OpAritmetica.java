package T2.MainTests.Data;

public class OpAritmetica {

    private Object _left, _right, result;
    private String _operator, _result_type;
    public OpAritmetica(Object left, Object operator, Object right) {
        this._left = left;
        this._right = right;
        this._operator = operator.toString();
    }

    public static Object eval(Object left, Object operator, Object right)  {
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
                return null;
            }
        }
    }

    public Object getLeft() {
        return _left;
    }
    public Object getRight() {
        return _right;
    }
    public String getOperator() {
        return _operator;
    }
    public void setLeft(Object left) {
        this._left = left;
    }
    public void setRight(Object right) {
        this._right = right;
    }
    public void setOperator(String operator) {
        this._operator = operator;
    }

    public String getResult_type() {
        return _result_type;
    }

    public void setResult_type(String result_type) {
        //this.result_type = result_type;
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
