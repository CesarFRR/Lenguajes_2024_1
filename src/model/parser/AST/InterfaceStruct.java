package model.parser.AST;

import java.util.*;

public interface InterfaceStruct {
    /**
     * Este es el estado por defecto de la Pila de estructura de control**/
    Object[] defaultElement = new Object[]{"main", null, null};
    /**Pila de estructura de control, se usa para el manejo de estructuras anidadas.
            * El primer elemento es el nombre de la estructura, el segundo es el valor de estado de retorno el cual puede ser:
            * <br>
     * "continue" para continuar con la siguiente iteración.
            * <br>
     * "break" para salir de la estructura de control.
     * <br>
     * null para continuar con la ejecución normal.
            * <br>
     * "return" para salir de la función con un valor opcional.
     **/
    Deque<Object[]> structStateStack = new ArrayDeque<>(Collections.singletonList(defaultElement));

    public default int createState(String type, Object status, Object value) {
        structStateStack.addLast(new Object[]{type, status, value});
        return structStateStack.size();
    }

    public default void updateState(String status, Object value) {
        Object[] last = getState();
        //TODO: MODIFICAR STATE

            switch (status) {
                case "continue":
                    last[1] = "continue";
                    break;
                case "break":
                    last[1] = "break";
                    break;
                case "return":
                    if(!existsState("fn")) throw new IllegalStateException("No se puede retornar un valor fuera de una función.");
                    while(!getState()[0].equals("fn"))
                        structStateStack.removeLast();
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + status);
            }

    }

    public default Object[] getState() {
        return structStateStack.getLast();
    }

    public default boolean existsState(String type) {
        for (Object[] state : structStateStack) {
            if (state[0].equals(type)) return true;
        }
        return false;
    }
    public default String getStatus() {
        return (String) getState()[1];
    }
    public default int getStackSize() {
        return structStateStack.size();
    }

    public default List<Node> getInstructionNodes(NodeTree root) {
        List<Node> instructionNodes = new ArrayList<>();
        NodeTree currentNode = root;

        while (currentNode != null) {
            if (currentNode.n != null) {
                instructionNodes.add(currentNode.n);
            }
            currentNode = currentNode.child;
        }

        return instructionNodes;
    }

    public default List<Integer> getIntValuesList(NodeTree root) {
        List<Integer> instructionNodes = new ArrayList<>();
        NodeTree currentNode = root;

        while (currentNode != null) {
            if (currentNode.n != null) {
                instructionNodes.add(  (int)getRealV(currentNode.n)  );
            }
            currentNode = currentNode.child;
        }

        return instructionNodes;
    }
    public default Object getRealV(Object value) {
        while (true){
            if (value instanceof Node) {
                value = ((Node) value).execute();
                if (value instanceof NodeVar) {
                    value = ((NodeVar) value).getValue();
                }
            } else {
                return value;
            }
        }
    }


        public default String executeInstructionNodes (Node root){
            if (root == null) throw new IllegalStateException("La lista de sentencias es null o vacía.");
            List<Node> instructionNodes;
            instructionNodes = (root instanceof NodeTree nt) ? this.getInstructionNodes(nt) : List.of(root);

            for (Node node : instructionNodes) {
                Object state = node.execute();
                if (state instanceof String s) {
                    if (s.equals("continue") || s.equals("break")) return s;
                }
            }
            return null;
        }

        public default int[] convertListToIntArray(List < Integer > list) {
            int[] array = new int[list.size()];
            for (int i = 0; i < list.size(); i++) {
                array[i] = list.get(i);
            }
            return array;
        }


    public default void removeState() {
        if(getStackSize()>1) structStateStack.removeLast();
    }
}
