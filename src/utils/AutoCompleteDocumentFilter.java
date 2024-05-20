/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import java.util.Arrays;
import java.util.List;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.DocumentFilter;
import javax.swing.text.JTextComponent;

/**
 *
 * @author Cesar_R
 */
public class AutoCompleteDocumentFilter extends DocumentFilter {

    private JTextComponent textComponent;

    public AutoCompleteDocumentFilter(JTextComponent textComponent) {
        this.textComponent = textComponent;
    }

    @Override
    public void insertString(FilterBypass fb, int offset, String text, AttributeSet attr) throws BadLocationException {
        if (text.equals("(")) {
            text = "()";
        } else if (text.equals("{")) {
            text = "{}";
        } else if (text.equals("[")) {
            text = "[]";
        }
        super.insertString(fb, offset, text, attr);
        if (text.length() == 2) {
            textComponent.setCaretPosition(offset + 1);
        }
    }

    @Override
    public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
        if (text.equals("(")) {
            text = "()";
        } else if (text.equals("{")) {
            text = "{}";
        } else if (text.equals("[")) {
            text = "[]";
        } else if (text.equals("'")) {
            text = "''";
        } else if (text.equals("\"")) {
            text = "\"\"";
        } else if (text.equals("\n") || text.equals("\r\n")) {
            List<String> Lpar = Arrays.asList("{", "[", "(");
            List<String> Rpar = Arrays.asList("}", "]", ")");
            String prevChar = offset > 0 ? textComponent.getDocument().getText(offset - 1, 1) : "";
            String nextChar = getNextNonWhitespaceChar(textComponent.getDocument(), offset);

            if (Lpar.contains(prevChar) && Rpar.contains(nextChar)) {
                textComponent.setCaretPosition(offset + 1);
                int tabs = addTabs(textComponent.getDocument(), offset);
                if (tabs == 0) {
                    text = "\n    \n";
                    super.replace(fb, offset, length, text, attrs);
                    textComponent.setCaretPosition(offset + 4 + 1);
                    return;

                } else {
                    text = "\n    " + "    ".repeat(tabs) + "\n" + "    ".repeat(tabs);
                    super.replace(fb, offset, length, text, attrs);
                    textComponent.setCaretPosition(offset + 4 * tabs + 4 + 1);
                    return;
                }

            } else {
                //textComponent.setCaretPosition(offset + 1);
                int tabs = addTabs(textComponent.getDocument(), offset);
                if (tabs>0){
                
                text = "\n" + "    ".repeat(tabs) ;
                super.replace(fb, offset, length, text, attrs);
                textComponent.setCaretPosition(offset + 4 * tabs + 1);
                return;
                }
            }
        }
        super.replace(fb, offset, length, text, attrs);
        if (text.length() == 2) {
            textComponent.setCaretPosition(offset + 1);
        }
    }

    private String getNextNonWhitespaceChar(Document doc, int offset) throws BadLocationException {
        for (int i = offset; i < doc.getLength(); i++) {
            String charAtI = doc.getText(i, 1);
            if (!Character.isWhitespace(charAtI.charAt(0))) {
                return charAtI;
            }
        }
        return "";
    }

    private int addTabs(Document doc, int offset) throws BadLocationException {
        int line = doc.getDefaultRootElement().getElementIndex(offset);
        int lineStart = doc.getDefaultRootElement().getElement(line).getStartOffset();
        String lineText = doc.getText(lineStart, offset - lineStart);
        String tabSum = "";
        int tabsCount = 0;
        if (!lineText.startsWith("\t") && !lineText.startsWith(" ")) {
            return 0;
        }
        for (int i = 0; i < lineText.length(); i += 4) {
            if (i + 4 <= lineText.length() && lineText.substring(i, i + 4).equals("    ")) {
                tabSum += "    ";
                tabsCount++;
            } else {
                break;
            }
        }
        return tabsCount;
    }

}
