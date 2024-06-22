package model.parser.AST;

public interface InterfaceExpr {

    public default Object getRealValue(Object value) {
        while (true){
            if (value instanceof Node n) {
                value = n.execute();
                if(value== null){
                    return null;
                }
                if (value instanceof NodeArrVarId) {
                    value = ((NodeArrVarId) value).execute();
                }
                else if (value instanceof NodeVar) {
                    value = ((NodeVar) value).getValue();
                } else {
                    return value;

                }
            } else {
                return value;
            }
        }
    }


    public default String cleanString(String str) {
        return str.replace("\"", "");
    }

    public default Object copyValue(Object original) {
        String type = getTypeFromValue(original);
        return switch (type) {
            case "int" -> Integer.valueOf((Integer) original);
            case "float" -> Float.valueOf((Float) original);
            case "string" -> new String((String) original);
            case "boolean" -> Boolean.valueOf((Boolean) original);
            case "char" -> Character.valueOf((Character) original);
            default -> throw new IllegalStateException("Unexpected value: " + original);
        };
    }
    public default String getTypeFromValue(Object value) {
        if (value instanceof Node) value = getRealValue(value);
        // System.out.println("VALOR DE VALUE: " + value + " " + value.getClass().getSimpleName() );
        return switch (value.getClass().getSimpleName()) {
            case "Integer" -> "int";
            case "Float" -> "float";
            case "String" -> "string";
            case "Boolean" -> "boolean";
            case "Character" -> "char";
            default -> throw new IllegalStateException("Unexpected value: " + value);
        };
    }
}
