package T2.MainTests.AST;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;
import java.util.Deque;

public class TableAST {
    private static Deque<Map<String, Object>> idTable= new ArrayDeque<>();
    private static Map<String, Object> fnTable= new HashMap<>();

    static {
        idTable.push(new HashMap<>());
    }
    public TableAST() {
    }

    public void pushIdMap() {
        idTable.push(new HashMap<>());
    }

    public void popIdMap() {
        if (idTable.size() > 1) { // Evitar eliminar el entorno global
            idTable.pop();
        }
    }

    public Object getId(String name) {
        // Buscar en el entorno actual
        if(idTable.peek() == null) return null;
        Map<String, Object> currentIdMap = idTable.peek();
        if (currentIdMap.containsKey(name)) {
            return currentIdMap.get(name);
        }
        // Si no se encuentra y hay más de un entorno, buscar en el entorno global
        if (idTable.size() > 1) {
            return idTable.getFirst().get(name);
        }
        // Si no se encuentra y solo hay un entorno, devolver null
        return null;
    }

    public void setId(String name, Object value) {
        // Asignar en el entorno actual
        assert idTable.peek() != null;
        idTable.peek().put(name, value);
    }

    public void updateId(String name, Object value) {
        // Obtener el entorno actual
        Map<String, Object> currentIdMap = idTable.peek();

        // Si el identificador existe en el entorno actual, actualizar su valor
        if (currentIdMap != null && currentIdMap.containsKey(name)) {
            currentIdMap.put(name, value);
        }
        // Si el identificador no se encuentra y hay más de un entorno, buscar en el entorno global
        else if (idTable.size() > 1 && idTable.getFirst().containsKey(name)) {
            idTable.getFirst().put(name, value);
        }
    }

    public boolean existsId(String name) {
        // Obtener el entorno actual
        Map<String, Object> currentIdMap = idTable.peek();
        if (currentIdMap == null) return false;

        // Comprobar si la clave ya existe en el entorno actual
        if (currentIdMap.containsKey(name)) {
            return true;
        }

        // Si no se encuentra y hay más de un entorno, buscar en el entorno global
        if (idTable.size() > 1) {
            return idTable.getFirst().containsKey(name);
        }

        // Si no se encuentra y solo hay un entorno, devolver false
        return false;
    }

    public Object getFn(String name) {
        return fnTable.get(name);
    }

    public void setFn(String name, Object value) {
        fnTable.put(name, value);
    }
}

