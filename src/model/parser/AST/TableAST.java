package model.parser.AST;
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
        idTable.addLast(new HashMap<>());
    }

    public void popIdMap() {
        if (idTable.size() > 1) { // Evitar eliminar el entorno global
            idTable.removeLast();
        }
    }

    public Object getId(String name) {
        // Buscar en el entorno actual
        if(idTable.isEmpty()) return null;
        Map<String, Object> currentIdMap = idTable.getLast();
        if (currentIdMap.containsKey(name)) {
            return currentIdMap.get(name);
        }
//        Object temp = idTable.getFirst().get(name);
//        System.out.println("está en el entorno global?: "+ this.existsId(name) + " " + temp!=null);

//        System.out.println("No se encontro la variable: " + name + " se buscará en el entorno global.");
//        System.out.println("Entorno actual: " + idTable.size());
        // Si no se encuentra y hay más de un entorno, buscar en el entorno global
        if (idTable.size() > 1) {
            return idTable.getFirst().get(name);
        }
        // Si no se encuentra y solo hay un entorno, devolver null
        return null;
    }

    public void setId(String name, Object value) {
        // Asignar en el entorno actual
        assert idTable.isEmpty();
        idTable.getLast().put(name, value);
    }

    public void updateId(String name, Object value) {
        // Obtener el entorno actual
        Map<String, Object> currentIdMap = idTable.getLast();

        NodeVar val;

        if (value instanceof NodeVar) {
            val = (NodeVar) value;
        } else {
            // Si el identificador existe en el entorno actual, actualizar su valor
            if (currentIdMap != null && currentIdMap.containsKey(name)) {
                NodeVar temp1 =(NodeVar) currentIdMap.get(name);
                temp1.setValue(value);
            }
            // Si el identificador no se encuentra y hay más de un entorno, buscar en el entorno global
            else if (idTable.size() > 1 && idTable.getFirst().containsKey(name)) {
                NodeVar temp1 = (NodeVar) idTable.getFirst().get(name);
                temp1.setValue(value);
            }
        }
    }

    public boolean existsId(String name) {
        // Obtener el entorno actual
        Map<String, Object> currentIdMap = idTable.getLast();
        if (currentIdMap == null) return false;

        // Comprobar si la clave ya existe en el entorno actual
        if (currentIdMap.containsKey(name)) {
            return true;
        }

        // Si no se encuentra y hay más de un entorno, buscar en el entorno global
        //System.out.println("existe en global?" + idTable.getFirst().containsKey(name));
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

    public void deleteId(String name) {
        // Obtener el entorno actual
        Map<String, Object> currentIdMap = idTable.getLast();
        if (currentIdMap == null) return;

        // Eliminar la clave del entorno actual
        currentIdMap.remove(name);
    }

    public void printMap() {
        System.out.println("\nEntorno actual:" + idTable.size() + "");
        this.printMap(idTable.size() - 1);
    }

    public void printMap(int entorno) {
        System.out.println("\nEntorno actual:" + entorno + "");
        for (Map.Entry<String, Object> entry : ((Map<String, Object>)idTable.toArray()[entorno]).entrySet()) {
            System.out.println(entry.getKey() + " = " + entry.getValue());
        }
        System.out.println("\n");
    }

    public void printAllMaps() {
        int entorno = 0;
        for (Map<String, Object> map : idTable) {
            System.out.println("Entorno: " + entorno);
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                System.out.println(entry.getKey() + " = " + entry.getValue());
            }
            System.out.println("\n");
            entorno++;
        }
    }

}

