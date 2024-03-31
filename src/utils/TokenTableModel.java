/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import model.scanner.Token;

/**
 *
 * @author Cesar_R
 */
 public class TokenTableModel extends AbstractTableModel {
    private final List<Token> tokens;
    private final String[] columnNames = {"txt", "Token", "[Linea, columna]", "Lexema"};

    public TokenTableModel(List<Token> tokens) {
        this.tokens = tokens;
    }
    public TokenTableModel() {
        this.tokens =new ArrayList<>();
    }
    @Override
    public int getRowCount() {
        return tokens.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Token token = tokens.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return token.getText();
            case 1:
                return token.getType();
            case 2:
                return "["+token.getLine()+", "+token.getColumn()+"]";
            case 3:
                return token.getLexemeType();
            default:
                return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }
}
