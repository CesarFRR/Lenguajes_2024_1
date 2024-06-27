package model.parser.AST;
import model.parser.ParserSym;

import java.util.*;

public interface InterfaceStruct {
    TableAST table = new TableAST();


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
                    while(!getState()[0].equals("fn")) structStateStack.removeLast();
                    if(value == null || value == "void") last[2] = "void";
                    else last[2] = value;

                    last[1] = "return";
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

    public default List<Object> getObjectValuesList(NodeTree root) {
        List<Object> objectValues = new ArrayList<>();
        NodeTree currentNode = root;

        while (currentNode != null) {
            if (currentNode.n != null) {
                objectValues.add(currentNode.n);
            }
            currentNode = currentNode.child;
        }

        return objectValues;
    }

    public default List<Integer> getIntValuesList(NodeTree root) {
        List<Integer> instructionNodes = new ArrayList<>();
        NodeTree currentNode = root;
        Object cn = null;
        String s = "";
        while (currentNode != null) {
            cn = currentNode.n;
            if (cn != null) {
                cn = (cn instanceof NodeLeaf) ? ((NodeLeaf)cn).value : ((NodeTree)cn).n;
                if(cn instanceof NodeVarId nvi) {
                    Node v = (Node)table.getId(nvi.getName());
//                    System.out.println("CN es un NodeVarId " + v);
                    if (v instanceof NodeVar) {
                        cn = ((NodeVar) v).getValue();
                    } else if (v instanceof NodeArrVar) {
                        cn = ((NodeArrVar) v).getValue();
                    }
                }
                if(cn instanceof NodeArrVarId navi){
//                    System.out.println("CN es un NodeArrVarId "+ navi);
                    cn = navi.execute();
                }
//                if(cn instanceof NodeVarId nvi) {
//                    System.out.println("CN es un NodeVarId " + nvi  + " " + nvi.execute());
//                }
                cn = (cn instanceof Node) ? ((Node)cn).execute() : cn;

//                System.out.println("CN es este:  " + cn + " " + cn.getClass() );
//                System.out.println("   \n\nCN es este:  " + cn + " " + cn.getClass() );
                instructionNodes.add( (int)(getRealV(cn)) );
            }
            currentNode = currentNode.child;
        }

        return instructionNodes;
    }
    public default Object getRealV(Object value) {
        Object v = value;
        while (true){
            if (v instanceof Node n) {
                v = n.execute();
                if (v instanceof NodeVar nv) {
                    v = nv.getValue();
                }
            } else {
                return v;
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

    public default Object[] convertListToObjectArray(List<Object> list) {
        Object[] array = new Object[list.size()];
        for (int i = 0; i < list.size(); i++) {
            array[i] = list.get(i);
        }
        return array;
    }
    public default List<Integer> convertArrayToList(int[] array) {
        List<Integer> list = new ArrayList<>();
        for (int i : array) {
            list.add(i);
        }
        return list;
    }

    public default void removeState() {
//        System.out.println("REMOVING STATE: " + getState()[0] + " " + getState()[1] + " " + getState()[2] + "\n");

        if(getStackSize()>1) structStateStack.removeLast();

//        System.out.println("\n\nREMOVING STATE DESPUES: " + getState()[0] + " " + getState()[1] + " " + getState()[2] + "\n");
    }

    public default NodeTree createNodeTreeList(int[] array) {
        if (array.length == 0) {
            return null;
        }

        NodeTree head = new NodeTree(new NodeLeaf(0, array[0]), null);
        NodeTree current = head;

        for (int i = 1; i < array.length; i++) {
            NodeLeaf leaf = new NodeLeaf(0, array[i]);
            NodeTree newNode = new NodeTree(leaf, null);
            current.child = newNode;
            current = newNode;
        }

        return head;
    }
}
