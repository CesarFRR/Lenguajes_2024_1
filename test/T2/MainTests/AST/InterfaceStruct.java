package T2.MainTests.AST;

import java.util.ArrayList;
import java.util.List;

public interface InterfaceStruct {
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

    public default String executeInstructionNodes(Node root) {
        if (root == null) throw new IllegalStateException("La lista de sentencias es null o vacía.");
        List<Node> instructionNodes;
        instructionNodes = (root instanceof NodeTree nt)? this.getInstructionNodes(nt): List.of(root);
        for (Node node : instructionNodes) {
            Object state = node.execute();
            if (state instanceof String s) {
                if (s.equals("continue") || s.equals("break")) return s;
            }
        }
        return null;
    }
}
