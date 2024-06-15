package model.parser.AST;

import model.parser.ParserSym;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.HashMap;
import java.util.Arrays;

public class NodeArrVar extends Node implements InterfaceStruct, InterfaceExpr{

    private static TableAST table;
    private Node child1, child2, child3, child4;
    static {
        table = new TableAST();
    }
    private Map<String, Object> array;
    private String type, identifier;
    private int[] shape;
    public NodeArrVar(int id, Node type, Node ident, Node index, Node indValue) {
        super(id);
        this.child1 = type;
        this.child2 = ident;
        this.child3 = index;
        this.child4 = indValue;
        if(id ==-1){
            this.id = ParserSym.IDENTIFICADOR;
        }
    }

    /**
     * Devuelve la representación en String del árbol.
     *
     * @param level Nivel del nodo
     * @return String con la representación del árbol
     **/
    @Override
    public String toString(int level) {
        return symPrint(id) + "\n" + this.toString();
    }

    @Override
    public String toString() {
        // Ordenar las claves del mapa
        List<String> sortedKeys = new ArrayList<>(array.keySet());
        Collections.sort(sortedKeys);

        // Obtener los valores del mapa en el orden de las claves
        List<Object> values = new ArrayList<>();
        for (String key : sortedKeys) {
            values.add(array.get(key));
        }

        // Convertir la lista de valores en una representación de cadena del arreglo multidimensional
        return arrayToString(values, shape, 0);
    }

    private String arrayToString(List<Object> values, int[] dimensions, int dimension) {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        int size = dimensions[dimension];
        for (int i = 0; i < size; i++) {
            if (dimension == dimensions.length - 1) {
                // Si estamos en la última dimensión, simplemente añadimos el valor
                sb.append(values.get(i));
            } else {
                // Si no estamos en la última dimensión, llamamos recursivamente a arrayToString
                int subSize = 1;
                for (int j = dimension + 1; j < dimensions.length; j++) {
                    subSize *= dimensions[j];
                }
                List<Object> subValues = values.subList(i * subSize, (i + 1) * subSize);
                sb.append(arrayToString(subValues, dimensions, dimension + 1));
            }
            if (i < size - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }


    /**
     * Ejecuta la acción de este nodo.
     *
     * @return
     **/
    @Override
    public Object execute() {
        if (child1 == null && child2 == null && child3 == null && child4 == null) {
            throw new IllegalStateException("No se pudo ejecutar la declaración de arreglo, falta por lo menos algun argumento.");
        }

        //TODO: implementar declaracion donde se guarda una instancia NodeArrVar en la tabla de simbolos
        // TODO: implementar la asignacion de un valor a un arreglo dado un indice
        if(child1 != null && child2 != null && child3 != null){
            String tipo = (String) getRealValue(child1);
            String identificador = (String) getRealValue(this.child2.execute());
            int[] dim = this.convertListToIntArray(this.getIntValuesList((NodeTree) child3));
           // System.out.println("DIMENSIONES DEL ARRAY EN DECLARACION: "+ Arrays.toString(dim));
            this.setIdentifier(identificador);
            this.setType(tipo);
            this.setShape(dim);
            this.initialize(dim);
            NodeArrVar nodeArrVar = new NodeArrVar(this.id, child1, child2, child3, child4);
            nodeArrVar.setArray(this.array);
            nodeArrVar.setShape(dim);
            nodeArrVar.setType(tipo);
            nodeArrVar.setIdentifier(identificador);
            table.setId(identificador, nodeArrVar);
            return nodeArrVar; //Caso 1: Declaracion de arreglo
        }

        if(child1 == null && child2!=null && child3!=null && child4!=null){
            String name = (String) child2.execute();
            NodeArrVar nodeArrVar = (NodeArrVar) table.getId(name);
            int[] indices = this.convertListToIntArray(this.getIntValuesList((NodeTree) child3));
            Object value = this.getRealValue(child4);
            nodeArrVar.set(indices, value);
            return nodeArrVar; //Caso 2: Asignacion de valor a un arreglo
        }


        return null;
    }

    public void set(int[] indices, Object value) {
        String key = generateKey(indices);
        array.put(key, value);
    }

    public Object get(int[] indices) {
        String key = generateKey(indices);
        return array.get(key);
    }

    private String generateKey(int[] indices) {
        StringBuilder key = new StringBuilder(identifier);
        for (int index : indices) {
            key.append("[").append(index).append("]");
        }
        return key.toString();
    }

    public void initialize(int[] dimensions) {
        if(array == null){
            array = new HashMap<>();
        }
        initialize(dimensions, 0, new int[dimensions.length]);
    }

    private void initialize(int[] dimensions, int depth, int[] indices) {
        if (depth == dimensions.length) {
            String key = generateKey(indices);
            array.put(key, getDefaultValue());
        } else {
            for (int i = 0; i < dimensions[depth]; i++) {
                indices[depth] = i;
                initialize(dimensions, depth + 1, indices);
            }
        }
    }

    private Object getDefaultValue() {
        return switch (type) {
            case "int" -> 0;
            case "float" -> 0.0f;
            case "string" -> "";
            case "boolean" -> false;
            default -> throw new IllegalStateException("Unexpected value: " + type);
        };
    }

    public Map<String, Object> getArray() {
        return array;
    }

    public void setArray(Map<String, Object> array) {
        this.array = array;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public int[] getShape() {
        return shape;
    }

    public void setShape(int[] shape) {
        this.shape = shape;
    }
}
