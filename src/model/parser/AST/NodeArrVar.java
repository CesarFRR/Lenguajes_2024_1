package model.parser.AST;
import model.parser.ParserSym;

import java.util.*;

public class NodeArrVar extends Node implements InterfaceStruct, InterfaceExpr{

    private static TableAST table;
    private Node child1;
    public Node child2;
    private Node child3;

    public Node getChild4() {
        return child4;
    }
    public void setChild4(Node child4) {
        this.child4 = child4;
    }
    public void isDeclarationWithAssignment(){
        this.declarationWithAssignment = true;
    }
    public void isAssignment(){
        this.assignment = true;
    }
    private Node child4;
    private boolean declarationWithAssignment = false;
    private boolean assignment = false;
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
        if (this.array== null) {
            return "[]";
        }
        List<String> sortedKeys = new ArrayList<>(array.keySet());
        Collections.sort(sortedKeys);

        //System.out.println("todas las claves: "+sortedKeys);

        // Obtener los valores del mapa en el orden de las claves
        List<Object> values = new ArrayList<>();
        for (String key : sortedKeys) {
            values.add(array.get(key));
        }

       // System.out.println("NodeArrVar: toString() -> " +this.identifier+"  "+ values + " " + Arrays.toString(shape));

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
        Object ch4 = child4;






        ch4 = (ch4 instanceof NodeFnCall nfc) ? (Node)nfc.execute() : ch4;
        ch4 = (ch4 instanceof NodeArrVarId navi)? navi.execute() : ch4;
        ch4 = (ch4 instanceof Node)? ch4 : new NodeLeaf(0, ch4);
        if(this.assignment){
//            System.out.println("POR AQUI ES ASIGNACION A ARRAY:::  "+this.getIdentifier() + "  "+table.getId(this.getIdentifier()));
//            System.out.println(child1 + "  "+child2 + "  "+child3 + "  "+child4);
            String tipo = (String) getRealValue(child1);
            String identificador = (String) getRealValue(this.child2.execute());
            int[] dim = this.convertListToIntArray(this.getIntValuesList((NodeTree) child3));
            NodeArrVar nav = (NodeArrVar) table.getId(identificador);
            nav.set(dim, ((Node)ch4).execute());

            return nav; //Caso 1: Declaracion de arreglo
        }


        if (this.declarationWithAssignment) {
            this.declarationWithAssignment = false;


            String tipo = (String) getRealValue(child1);
            String identificador = (String) getRealValue(this.child2.execute());
            int[] dim = this.convertListToIntArray(this.getIntValuesList((NodeTree) child3));
//            System.out.println("\n\n\nLOS INDICES SON: "+Arrays.toString(dim));
            this.setIdentifier(identificador);
            this.setType(tipo);
            this.setShape(dim);

            Object v = ch4 instanceof NodeVarId nvid ? table.getId(nvid.getName()) : ch4;

                if(v instanceof NodeArrVar nav){
//                    System.out.println("POR AQUI:::" + this.getIdentifier() + "  "+table.getId(this.getIdentifier()));
                    v = table.getId(this.getIdentifier() );
                    NodeArrVar nv =(NodeArrVar) nav.copy(identificador);
                    nv.child4=null;
                    this.child4 = null;
                    table.setId(identificador, nv);
                    return nv;
                }







            List<Node[]> values = this.buildNestedList((Node)ch4);
//            child4 = null;


            if(this.array == null){
                this.initialize(dim);
            }
            NodeTree p = (NodeTree) ch4;
            //System.out.println("#1-->p: "+p);
            while (p instanceof NodeTree) {
                p = p.child;
            }
//            System.out.println("p: "+p);

            //TODO: aqui se deben asignar los valores:

//            for(Node[] n : values){
//                System.out.print(  Arrays.toString(n) +  " , ");
//            }
//            System.out.println("");

            this.assignValuesToMap(values);
            //
            NodeArrVar nodeArrVar = this;
            table.setId(identificador, nodeArrVar);
//            ch4=null;
//            child4 = null;
            return nodeArrVar; //Caso 1: Declaracion de arreglo
        }

        if(child1!=null && child2!=null && child3!=null && ch4 != null){
            String tipo = (String) getRealValue(child1);
            String identificador = (String) getRealValue(this.child2.execute());
            int[] dim = this.convertListToIntArray(this.getIntValuesList((NodeTree) child3));

            Object v = ch4;
            if(v instanceof NodeVarId nvid){
                v = table.getId(nvid.getName());
            }
            if(v instanceof NodeArrVar nav){
                NodeArrVar nv =(NodeArrVar) nav.copy();
//                nv.child4=null;
                nv.child1 = this.child1;
                nv.child2 = this.child2;
                nv.child3 = this.child3;
                nv.setIdentifier(identificador);
                nv.setType(tipo);
                nv.setShape(dim);
                nv.setArray(nv.getArray());
//                this.child4 = null;
                NodeArrVar av = this;
//                System.out.println("NodeArrVar: execute() -> "+av + "identenificador: " + identificador + "  "+nv);
                table.setId(identificador, av);
//                ch4=null;
//                child4 = null;
                return nv;
            }


            this.setIdentifier(identificador);
            this.setType(tipo);
            this.setShape(dim);
            if(this.array == null){
                this.initialize(dim);
            }
            NodeArrVar nodeArrVar = this;
            table.setId(identificador, nodeArrVar);
//            ch4=null;
//            child4 = null;
//            System.out.println("daclaracion de arreglo vacio: "+nodeArrVar + " "+ nodeArrVar.getArray() + " "+nodeArrVar.getShape() + " "+nodeArrVar.getType() + " "+nodeArrVar.getIdentifier());
            return nodeArrVar; //Caso 1: Declaracion de arreglo
        }




        //TODO: implementar declaracion donde se guarda una instancia NodeArrVar en la tabla de simbolos
        // TODO: implementar la asignacion de un valor a un arreglo dado un indice
        if(child1 != null && child2 != null && child3 != null && ch4 == null){
            String tipo = (String) getRealValue(child1);
            String identificador = (String) getRealValue(this.child2.execute());
            int[] dim = this.convertListToIntArray(this.getIntValuesList((NodeTree) child3));
           // System.out.println("DIMENSIONES DEL ARRAY EN DECLARACION: "+ Arrays.toString(dim));
            this.setIdentifier(identificador);
            this.setType(tipo);
            this.setShape(dim);
            if(this.array == null){
                this.initialize(dim);
            }
//            if(this.array.entrySet().isEmpty()){
//                this.initialize(dim);
//            }

            NodeArrVar nodeArrVar = this;
            //System.out.println("NodearrVar==================> "+nodeArrVar);
            table.setId(identificador, nodeArrVar);
//            ch4=null;
//            child4 = null;
            return nodeArrVar; //Caso 1: Declaracion de arreglo
        }



        if(child1 == null && child2!=null && child3!=null && child4!=null){
            String name = (String) child2.execute();
            NodeArrVar nodeArrVar = (NodeArrVar) table.getId(name);
            int[] indices = this.convertListToIntArray(this.getIntValuesList((NodeTree) child3));

            if(ch4 instanceof  NodeVarId nvid){
                ch4 =(Node) table.getId(nvid.getName());
            }

            if (ch4 instanceof NodeArrVar nav) {
                nodeArrVar.assignArray(nav);
                ch4=null;
                child4 = null;
                return nodeArrVar;
            }



            Object value = this.getRealValue(ch4);
            ch4=null;
            child4 = null;
            nodeArrVar.set(indices, value);
            return nodeArrVar; //Caso 2: Asignacion de valor a un arreglo
        }


        return null;
    }
    public List<Node[]> buildNestedList(Node root) {
        List<Node[]> currentList = new ArrayList<>();
        Node currentNode = root;
        Object curr= null;

        for (int i = 0; i < shape.length; i++) {
            currentNode = (currentNode instanceof NodeExprArithmetic expr) ?  new NodeLeaf(0, expr.execute() ) : currentNode;
//            System.out.println("currentNode: "+currentNode.execute());
            if (currentNode instanceof NodeTree nt) {
//                System.out.println("0 - nt.n : "+nt.n);
                nt.n = (nt.n instanceof NodeExprArithmetic expr) ?  new NodeLeaf(0, expr.execute() ) : nt.n;
//                System.out.println("1 - nt.n : "+nt.n);
                if (nt.n instanceof NodeLeaf) {
                    Node[] nodes = new Node[shape[shape.length - 1]];
                    for (int j = 0; j < nodes.length; j++) {

                        nodes[j] = nt.n;
//                        System.out.print("NodeLeaf "+j + ": "+nt.n+" ");
                        nt = nt.child;
                        if (nt == null) {
                            break;
                        }
                    }
//                    System.out.println("");

                    currentList.add(nodes);
                    break;
                } else {
                    currentList.addAll(buildNestedList(nt.n));
                    currentNode = nt.child;
                }
            } else {
                currentNode = (currentNode instanceof NodeExprArithmetic expr) ?  new NodeLeaf(0, expr.execute() ) : currentNode;

                currentList.add(new Node[]{currentNode});
                break;
            }
        }

        return currentList;
    }
    public void printList(List<Object> list){
        for (int i = 0; i < list.size(); i++) {
            Object current = list.get(i);
            if (current instanceof List){
                printList((List<Object>) current);
            } else {
                System.out.print("i: "+i+"  value: "+current+" "+current.getClass()+",  ");
            }
        }
        System.out.println("");
    }
    private void assignValuesToMap(List<Node[]> values) {
        // Obtén todas las claves del mapa
        List<String> keys = new ArrayList<>(array.keySet());

        // Ordena las claves lexicográficamente
        Collections.sort(keys);

        // Junta todos los arrays Node[] en un solo array
        List<Node> allNodes = new ArrayList<>();
        for (Node[] nodes : values) {
            allNodes.addAll(Arrays.asList(nodes));
        }

        // Asigna un Node a cada clave en orden
//        System.out.println("keys: "+keys + "  allNodes: "+allNodes);
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            Node node = allNodes.get(i);
            array.put(key, node.execute());
        }
    }


    public void assignArray(NodeArrVar nav){
        //System.out.println("NodeArrVar: assignArray(" + nav + ")");
        String name = (String) child2.execute();
        NodeArrVar nodeArrVar2 = (NodeArrVar) ((NodeArrVar) table.getId(name)).copy(nav.identifier);
        //System.out.println("nodevarrvar: assignArray: " +nodeArrVar2.identifier+":  "+ nodeArrVar2);
        int[] currentShape = this.getShape();
        int[] newShape = nav.getShape();
        if(!Arrays.equals(currentShape, newShape)){
            throw new IllegalStateException("Las dimensiones de los arreglos no coinciden.");
        }
        if(!type.equals(nav.getType())  ) {
            throw new IllegalStateException("Los tipos de los arreglos no coinciden.");
        }
        nodeArrVar2.setArray(nav.getArray());
        nodeArrVar2.setShape(nav.getShape());
        nodeArrVar2.setType(nav.getType());
        this.child4 = null;
    }

    public void set(int[] indices, Object value) {
        String key = generateKey(indices);
        array.put(key, value);
    }

    public Object get(int[] indices) {
        String key = generateKey(indices);
        return array.get(key);
    }

    public String generateKey(int[] indices) {
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
            case "float" -> -999999f;
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
        return new String(type);
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

    public void sortAll() {
        int totalSubarrays = 1;
        for (int i = 0; i < shape.length - 1; i++) {
            totalSubarrays *= shape[i];
        }

        for (int i = 0; i < totalSubarrays; i++) {
            int[] indices = new int[shape.length - 1];
            int temp = i;
            for (int j = shape.length - 2; j >= 0; j--) {
                indices[j] = temp % shape[j];
                temp /= shape[j];
            }
            sort(indices);
        }
    }

    public void sort(int[] indices) {
        // Comprobar si los índices conducen a un valor en lugar de a un array
        if (indices.length == shape.length) {
            return;
        }

        // Comprobar si los índices son válidos
        if (indices.length > shape.length) {
            throw new IllegalArgumentException("Los índices proporcionados son inválidos.");
        }

        // Generar todas las claves posibles para los elementos en la dimensión especificada
        List<String> keys = new ArrayList<>();
        generateKeys(indices, 0, new int[shape.length], keys);

        // Recoger todos los valores correspondientes a estas claves en una lista
        List<Object> values = new ArrayList<>();
        for (String key : keys) {
            values.add(array.get(key));
        }

        // Ordenar la lista de valores
        Collections.sort(values, new Comparator<Object>() {
            @Override
            public int compare(Object o1, Object o2) {
                if (o1 instanceof Comparable && o2 instanceof Comparable) {
                    Comparable c1 = (Comparable) o1;
                    Comparable c2 = (Comparable) o2;
                    return c1.compareTo(c2);
                }
                return 0;
            }
        });

        // Actualizar el mapa con los valores ordenados
        for (int i = 0; i < keys.size(); i++) {
            array.put(keys.get(i), values.get(i));
        }
    }

    private void generateKeys(int[] indices, int depth, int[] currentIndices, List<String> keys) {
        if (depth == indices.length) {
            for (int i = 0; i < shape[depth]; i++) {
                currentIndices[depth] = i;
                keys.add(generateKey(currentIndices));
            }
        } else {
            currentIndices[depth] = indices[depth];
            generateKeys(indices, depth + 1, currentIndices, keys);
        }
    }

    public Map<String, Object> deepCopyMap(Map<String, Object> originalMap) {
        Map<String, Object> copiedMap = new HashMap<>();
        for (Map.Entry<String, Object> entry : originalMap.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            Object copiedValue = copyValue(value);
            copiedMap.put(key, copiedValue);
        }
        return copiedMap;
    }
    public Map<String, Object> deepCopyMapWithNewName(Map<String, Object> originalMap, String newName) {
        Map<String, Object> copiedMap = new HashMap<>();
        for (Map.Entry<String, Object> entry : originalMap.entrySet()) {
            String key = entry.getKey().replaceFirst("^[^\\[]+", newName);
            //System.out.println("new key: " + key);
            Object value = entry.getValue();
            Object copiedValue = copyValue(value);
            copiedMap.put(key, copiedValue);
        }
        //printArray(copiedMap);
        return copiedMap;
    }
    public Object copyValue(Object original) {
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


    public Map<String, Object> copyArray(){
        Map<String, Object> arr =  deepCopyMap(this.array);
        //printArray(arr);
        return arr;
    }
    public void printArray(Map<String, Object> map){
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            System.out.print(entry.getKey() + " = " + entry.getValue() + ",  ");
        }
    }

    @Override
    public Object copy() {
        String type, name;
        name = (String) this.copyValue(this.getIdentifier());
        type = (String) this.copyValue(this.getType());

        int[] currentShape = this.getShape();
        Node index = this.createNodeTreeList(currentShape);
        NodeArrVar av = new NodeArrVar(this.id, new NodeLeaf(0, type), new NodeLeaf(0, name), index, null);
        av.setArray(this.copyArray());
        av.setShape(currentShape);
        av.setType(type);
        av.setIdentifier(name);
        av.setId(this.id);

        this.child4 = null;
        return av;
    }
    public Object copy(String newName) {
        String type, name;
        name = newName;
        type = (String) this.copyValue(this.getType());

        int[] currentShape = this.getShape();
        Node index = this.createNodeTreeList(currentShape);
        NodeArrVar av = new NodeArrVar(this.id, new NodeLeaf(0, type), new NodeLeaf(0, name), index, null);
        Map<String, Object> nmap = this.deepCopyMapWithNewName(this.array, newName);
       // System.out.println("nmap: "+nmap + "---> "+nmap.keySet());
        av.setArray(nmap);
        av.setShape(currentShape);
        av.setType(type);
        av.setIdentifier(newName);
        av.setId(this.id);
        List<String> sortedKeys = new ArrayList<>(av.array.keySet());
       //System.out.println("copy con new Name:  todas las claves : "+sortedKeys+ "  av: "+av);
        this.child4 = null;
        return av;
    }

    public void setChild2(Node child2) {
        this.child2 = child2;
    }

    public void floorAllValues() {
        if ("float".equals(type)) {
            for (String key : array.keySet()) {
                Object value = array.get(key);
                if (value instanceof Float) {
                    array.put(key, Math.floor((Float) value));
                }
            }
        }
    }

    public void ceilAllValues() {
        if ("float".equals(type)) {
            for (String key : array.keySet()) {
                Object value = array.get(key);
                if (value instanceof Float) {
                    array.put(key, Math.ceil((Float) value));
                }
            }
        }
    }

    public void roundAllValues() {
        if ("float".equals(type)) {
            for (String key : array.keySet()) {
                Object value = array.get(key);
                if (value instanceof Float) {
                    array.put(key, Math.round((Float) value));
                }
            }
        }
    }

    public void toInt() {
        if ("float".equals(type)) {
            for (String key : array.keySet()) {
                Object v = array.get(key);
                if(v instanceof Double){
                    v =(int) ((Double) v).floatValue();
                }else if (v instanceof Integer){
                    v = (int)((Integer) v).floatValue();
                }
                array.put(key, v);
            }
            type = "int";
            if (child1 instanceof NodeLeaf) {
                ((NodeLeaf) child1).value= type;
            }
        }
    }

    public void toFloat() {
        if ("int".equals(type)) {
            for (String key : array.keySet()) {
                Object value = array.get(key);
                if (value instanceof Integer) {
                    array.put(key, Float.valueOf((Integer) value));
                } else if (value instanceof Double) {
                    array.put(key, ((Double) value).floatValue());
                }
            }
            type = "float";
            if (child1 instanceof NodeLeaf) {
                ((NodeLeaf) child1).value = type;
            }
        }
    }


}
