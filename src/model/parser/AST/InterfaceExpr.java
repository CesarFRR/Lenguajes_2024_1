package model.parser.AST;

public interface InterfaceExpr {

    public default Object getRealValue(Object value) {
        while (true){
            if (value instanceof Node n) {
                value = n.execute();
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
}
