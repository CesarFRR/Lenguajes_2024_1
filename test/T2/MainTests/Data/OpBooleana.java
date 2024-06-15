package T2.MainTests.Data;

public class OpBooleana {
    private Object e1, e2;
    private String operator;
    private boolean result;



    public OpBooleana(Object left, Object operator, Object right) {
        this.e1 = left;
        this.e2 = right;
        this.operator = operator.toString();
    }

    public static boolean eval(Object e1 , String operator, Object e2) {
        if (e1 instanceof String || e2 instanceof String) {
            return (e1.toString()).compareTo(e2.toString()) == 0;
        } else {
            double result = 0;
            double epsilon = 1e-10; // o cualquier otro valor pequeño
            result = ((Number)e1).doubleValue() - ((Number)e2).doubleValue() ;
            boolean answer = switch (operator) {
                case "<" -> Math.abs(result) < epsilon ? false : result < 0;
                case "<=" -> Math.abs(result) < epsilon ? true : result < 0;
                case ">" -> Math.abs(result) < epsilon ? false : result > 0;
                case ">=" -> Math.abs(result) < epsilon ? true : result > 0;
                case "==" -> Math.abs(result) < epsilon;
                case "!=" -> Math.abs(result) > epsilon;
                default -> false;
            };
            return answer;
        }
    }
}
