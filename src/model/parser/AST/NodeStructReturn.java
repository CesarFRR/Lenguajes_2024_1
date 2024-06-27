package model.parser.AST;
import model.parser.ParserSym;

import java.util.Map;

public class NodeStructReturn extends NodeLeaf implements InterfaceStruct, InterfaceExpr{
    private static TableAST table;
    static {
        table = new TableAST();
    }
    public NodeStructReturn(Object value) {
        super(ParserSym.RETURN, value);

    }

    @Override
    public String toString(int level) {
        return this.symPrint(id) + " " + this.value.toString();
    }

    @Override
    public String toString() {
        return this.symPrint(id);
    }

    @Override
    public Object execute() {
        Object result = null;

        if(this.value == null) {
            this.updateState("return", "void");
            return this.value;
        }

        if (this.value instanceof Node n) {

            if(n instanceof NodeVarId nvid){
                String name = nvid.getName();
                result= table.getId(name);
            }
            if(n instanceof NodeArrVarId navid){
                Object arrval = navid.execute();
                NodeArrVar arrv = (NodeArrVar)((NodeArrVar) table.getId(navid.getName()));
                int[] indexs = navid.getIndexesArray();
                Object arrvalue = arrv.get(indexs);
                String key = arrv.generateKey(indexs);
                Map<String, Object> arrayM = arrv.getArray();
                //System.out.println("/VALOR DEL ARREGLO: " + arrayM.get(key) + " --->" + arrv);
                if (!(arrval instanceof Node nav) ){
                    result = new NodeLeaf(0, arrval);
                }
            }

            if(this.value instanceof NodeVar nv) {
                result = new NodeLeaf(0, nv.getValue());
            } else if (this.value instanceof NodeArrVar nav) {
                result = nav;
            }else{
                result = getRealValue(this.value);
            }

        }
        //System.out.println("RETURNING NODE: " + this.value + " " + this.value.getClass().getSimpleName());
        this.updateState("return", result);

        //table.printAllMaps();
        result = result== null ? this.value : result;
        //clean();
        return result;
    }

public void clean(){
        this.value = null;
}

}
