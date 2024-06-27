package model.parser.AST;
import model.parser.ParserSym;

import java.util.ArrayList;
import java.util.List;

public class NodeFn extends Node implements InterfaceStruct, InterfaceExpr{
    private static TableAST table;
    static{
        table = new TableAST();
    }
    private List<NodeParam> params;
    private List<Node> args, sentList1, getSentListCore;
    private Node name, result;

    public NodeFn(Node name, List<NodeParam> params, List<Node> sentList1) {
        super(ParserSym.FN);
        this.name = name;
        this.params = params;
        this.sentList1 = sentList1;
    }

    /**
     * Devuelve la representación en String del árbol.
     *
     * @param level Nivel del nodo
     * @return String con la representación del árbol
     **/
    @Override
    public String toString(int level) {
        return "FN: " + name.toString(level) + " PARAMS: " + params.toString() + " SENTLIST: " + sentList1;
    }

    /**
     * Ejecuta la acción de este nodo.
     *
     * @return
     **/
    @Override
    public Object execute() {
//        System.out.println("Ejecutando función...");
        if( (this.args == null || this.params == null) ) throw new IllegalStateException("No se pudo ejecutar la función, los argumentos o parámetros son nulos.");
        if( (this.args.size() != this.params.size()) ) throw new IllegalStateException("No se pudo ejecutar la función, la cantidad de argumentos no coincide con la cantidad de parámetros.");
//        System.out.println("args: " + this.args + " params: " + this.params + " sentList1: " + this.sentList1 + " name: " + this.name + " result: " + this.result + "\n");
        List<Node> finalArgs = this.prepareArgs(this.args);
        Object result = null, sent;
        int statePosition = this.createState("fn", null, null);
        table.pushIdMap();
       // table.printAllMaps();
        for (Node n: finalArgs){
            n.execute();
            //System.out.println("declarando argumento: " + n);
        }
        //table.printAllMaps();

        //TODO: EJECUCION DE LA FUNCION AQUI:
        for (Node n: this.sentList1){
            sent = n.execute();
            int stackSize = getStackSize();
            if(stackSize < statePosition) break;
            if(stackSize == statePosition && getStatus()=="return"){
//                System.out.println("SE ENCONTRO UN RETURN " + getState()[2]);
                result = getState()[2];
                if(result instanceof String s && s.equals("void")) result = null;
                break;
            }
        }
//        System.out.println("result antes: " + result);
        table.popIdMap();
        this.removeState();
        this.args = null;
//        System.out.println("result despues: " + result);
//        System.out.println("el state al final es:: " + getState()[2]);

        return result;
    }

    public List<Node> prepareArgs(List<Node> args) {
        List<Node> newArgs = new ArrayList<>();
        for (int i = 0; i < args.size(); i++) {
            Node arg = args.get(i);
            NodeParam param = this.params.get(i);
            param.setValue(arg);
            newArgs.add(  (Node) (param).execute()  );
        }
        return newArgs;
    }

    public void setArgs(List<Node> args) {
        this.args = args;
    }

    public List<Node> getArgs() {
        return args;
    }

    public void setParams(List<NodeParam> p) {
        this.params = p;
    }

    public List<NodeParam> getParams() {
        return params;
    }
}
