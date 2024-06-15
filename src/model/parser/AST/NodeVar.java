package model.parser.AST;

import model.scanner.Token.TokenType;

public class NodeVar extends Node3{

    private static TableAST table;
    static {
        table = new TableAST();
    }
    public NodeVar(int id, Node type, Node name, Node value) {
        super(id, type, name, value);
    }
    /**
     * Devuelve la representación en String del árbol.
     *
     * @param level Nivel del nodo
     * @return String con la representación del árbol
     **/
    @Override
    public String toString(int level) {
        String simb = "";
        String indent = "";
        for (int i = 0; i < level; i++) {
            indent += " ";
        }
        if (this.id == -1) {
            this.id = TokenType.IDENTIFICADOR.ordinal();
        }
        //agregar el simbolo de asignacion =
        if (this.child1 == null && this.child3 == null) {
            simb = symPrint(id);
            return indent + simb + " " + child2.toString(level + 1);
        } else if (this.child1 == null) {
            simb = symPrint(id);
            return indent + simb + " " + child2.toString(level + 1) + "\n" +
                    indent + " " + (new NodeLeaf(id, "=")).toString(level + 1) + "\n" +
                    indent + " " + child3.toString(level + 1);
        } else {
            if (this.child3 != null) {
                NodeVar nv = (NodeVar) table.getId((String) this.child2.execute());
                //System.out.println("NODO VAR valor: " + nv.getValue());
                simb = symPrint(id);
                return indent + simb + " " + child1.toString(level + 1) + " " +
                        indent + " " + child2.toString(level + 1) + " " +
                        indent + " " + (new NodeLeaf(id, "=")).toString(level + 1) + " " +
                        indent + " " + child3.toString(level + 1);
            }
        }
        return "NODE VAR " + id;
    }



    /**
     * Al llamar a execute se ejecuta el nodo y se devuelve el valor de la variable
     *
     * @return
     **/
    @Override
    public Object execute() {
        //System.out.println("EJECUTANDO NODEVAR  " + this.id + " " + this.child1 + " " + this.child2 + " " + this.child3);
        String nameid = (String) this.child2.execute();


        if (this.child1==null && child3 == null) {
            //System.out.println("child1 y child3 son null  buscar en la tabla: " + table.getId(nameid) );

            //System.out.println("child1 y child3 son null  " + this.id + " " + this.child1 + " " + this.child2 + " " + this.child3);
            if (!NodeVar.table.existsId(nameid)) throw new IllegalStateException("Variable " + nameid + " not declared.");
            return NodeVar.table.getId(nameid); // CASO 1: solo se tiene el nombre de la variable, se devuelve su valor
        }

        if (this.child1==null && this.child3 != null) {
            //System.out.println("child1 es null y child3 no es null  " + this.id + " " + this.child1 + " " + this.child2 + " " + this.child3);
            NodeVar.table.updateId(nameid, this.child3.execute()); //CASO 2: se actualiza el valor de la variable
            return NodeVar.table.getId(nameid); // se devuelve el valor de la variable
        }

        if (this.child1!=null && this.child3 == null) {
            //System.out.println("child1 no es null y child3 es null  " + this.id + " " + this.child1 + " " + this.child2 + " " + this.child3);
            if(table.existsId(nameid)) throw new IllegalStateException("Variable " + nameid + " already declared.");
            String type = (String) this.child1.execute();
            Object value = getDefaultValue(type);
            NodeVar result = new NodeVar(this.id, new NodeLeaf(0, type), new NodeLeaf(0, nameid), new NodeLeaf(0, value));
            NodeVar.table.setId(nameid, result); // CASO 3: se declara la variable con un valor por defecto
            return (NodeVar)NodeVar.table.getId(nameid);
        }

        if (this.child1!=null && this.child3 != null) {
            //System.out.println("child1 y child3 no son null  " + this.id + " " + this.child1 + " " + this.child2 + " " + this.child3);
            if(table.existsId(nameid)) throw new IllegalStateException("Variable " + nameid + " already declared.");
            String type = (String) this.child1.execute();
            Object value = getRealValue(this.child3.execute());
            if (this.getTypeFromValue(value).equals("int") && type.equals("float")) value = ((Integer) value).floatValue();
            if(!this.getTypeFromValue(value).equals(type)) throw new IllegalStateException("Type mismatch: "+type+" expected, but "+this.getTypeFromValue(value)+" found.");
            NodeVar result = new NodeVar(this.id, new NodeLeaf(0, type), new NodeLeaf(0, nameid), new NodeLeaf(0, value));
            NodeVar.table.setId(nameid, result); // CASO 4: se declara la variable con un valor asignado
            return (NodeVar)NodeVar.table.getId(nameid);
        }
        return null;
    }
    public String getType() {
        return (String) child1.execute();
    }
    public void setType(Object type) {
        child1 = (type instanceof Node)? (Node) type : new NodeLeaf(0, type);
    }

    public String getName() {
        return (String) child2.execute();
    }
    public void setName(Object name) {
        child2 = (name instanceof Node)? (Node) name : new NodeLeaf(0, name);
    }



    public void setValue(Object value) {
        child3 = (value instanceof Node)? (Node) value : new NodeLeaf(0, value);
       // Node forma2 = value;
    }

    public Object getValue() {
        return child3.execute();
    }

    private Object getDefaultValue(String type) {
        return switch (type) {
            case "int" -> 0;
            case "float" -> 0.0;
            case "string" -> "";
            case "boolean" -> false;
            case "char" -> '\u0000';
            default -> throw new IllegalStateException("Unexpected value: " + type);
        };
    }

    private String getTypeFromValue(Object value) {
        if (value instanceof Node) value = getRealValue(value);
       // System.out.println("VALOR DE VALUE: " + value + " " + value.getClass().getSimpleName() );
        return switch (value.getClass().getSimpleName()) {
            case "Integer" -> "int";
            case "Float" -> "float";
            case "String" -> "string";
            case "Boolean" -> "boolean";
            case "Character" -> "char";
            default -> throw new IllegalStateException("Unexpected value: " + value);
        };
    }

    private Object getRealValue(Object value) {
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

}
