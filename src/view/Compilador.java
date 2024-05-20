package view;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.Timer;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.AbstractTableModel;
import model.scanner.LexerAnalyzer;
import model.scanner.Token;
import utils.TokenTableModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;
import model.scanner.ColorType;
import utils.utils;
import model.scanner.Token;

/**
 *
 * @author yisus
 */
public class Compilador extends javax.swing.JFrame {

    public TokenTableModel tblTokensModel;
    public LexerAnalyzer lex;
    public String currentFilePath = null;
    public String currentFileName = null;
    private HashMap<String, Style> colorStyles;
    private List Ctokens;
    private boolean dark=false;

    /**
     * Creates new form Compilador
     */
    public Compilador() {
        initComponents();
        init();
    }

    private void init() {
        setTitle("Analizador Léxico");
        utils.initUXLookAndFeel(utils.isWindows(), utils.islinux());
        utils.enumerateJTextComponent(this.jtpCode, this.jspCode);
        utils.autocompleteJTextComponent(this.jtpCode, this.jspCode);
        this.tblTokensModel = new TokenTableModel();
        this.tblTokens.setModel(this.tblTokensModel);
        var tokenstable = this.tblTokens;
        tokenstable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    return;
                }

                // Obtener las filas seleccionadas
                int[] selectedRows = tokenstable.getSelectedRows();

                // Activar algo
                for (int i = 0; i < selectedRows.length; i++) {
                    System.out.println("Fila seleccionada: " + selectedRows[i]);
                }
                System.out.println("\n");
            }
        });

        setLocationRelativeTo(null);
        // Crear una acción
        Action action = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnGuardarActionPerformed(e);
            }
        };

        // Obtén el InputMap y el ActionMap del panel que contiene el botón
        InputMap inputMap = this.buttonsFilePanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = this.buttonsFilePanel.getActionMap();

        // Vincula la tecla Ctrl + S a la acción
        inputMap.put(KeyStroke.getKeyStroke("ctrl S"), "save");
        actionMap.put("save", action);
        createStyles();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        rootPanel = new javax.swing.JPanel();
        buttonsFilePanel = new javax.swing.JPanel();
        btnAbrir = new javax.swing.JButton();
        btnNuevo = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        btnGuardarComo = new javax.swing.JButton();
        jspCode = new javax.swing.JScrollPane();
        jtpCode = new javax.swing.JTextPane();
        panelButtonCompilerExecute = new javax.swing.JPanel();
        btnCompilar = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jtaConsole = new javax.swing.JTextArea();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblTokens = new javax.swing.JTable();
        bt_bg = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new javax.swing.BoxLayout(getContentPane(), javax.swing.BoxLayout.LINE_AXIS));

        btnAbrir.setText("Abrir");
        btnAbrir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAbrirActionPerformed(evt);
            }
        });

        btnNuevo.setText("Nuevo");
        btnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoActionPerformed(evt);
            }
        });

        btnGuardar.setText("Guardar");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        btnGuardarComo.setText("Guardar como");
        btnGuardarComo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarComoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout buttonsFilePanelLayout = new javax.swing.GroupLayout(buttonsFilePanel);
        buttonsFilePanel.setLayout(buttonsFilePanelLayout);
        buttonsFilePanelLayout.setHorizontalGroup(
            buttonsFilePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(buttonsFilePanelLayout.createSequentialGroup()
                .addComponent(btnNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnAbrir)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnGuardar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnGuardarComo)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        buttonsFilePanelLayout.setVerticalGroup(
            buttonsFilePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(buttonsFilePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(buttonsFilePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAbrir)
                    .addComponent(btnNuevo)
                    .addComponent(btnGuardar)
                    .addComponent(btnGuardarComo))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        jtpCode.setFont(new java.awt.Font("Monospaced", 0, 14)); // NOI18N
        jtpCode.setForeground(new java.awt.Color(0, 0, 0));
        jtpCode.setToolTipText("");
        jtpCode.setCaretColor(new java.awt.Color(0, 0, 0));
        jtpCode.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        jtpCode.setDisabledTextColor(new java.awt.Color(0, 0, 153));
        jtpCode.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jtpCodeKeyReleased(evt);
            }
        });
        jspCode.setViewportView(jtpCode);

        btnCompilar.setText("Ejecutar análisis léxico");
        btnCompilar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCompilarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelButtonCompilerExecuteLayout = new javax.swing.GroupLayout(panelButtonCompilerExecute);
        panelButtonCompilerExecute.setLayout(panelButtonCompilerExecuteLayout);
        panelButtonCompilerExecuteLayout.setHorizontalGroup(
            panelButtonCompilerExecuteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelButtonCompilerExecuteLayout.createSequentialGroup()
                .addGap(0, 6, Short.MAX_VALUE)
                .addComponent(btnCompilar, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        panelButtonCompilerExecuteLayout.setVerticalGroup(
            panelButtonCompilerExecuteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelButtonCompilerExecuteLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnCompilar)
                .addContainerGap(7, Short.MAX_VALUE))
        );

        jtaConsole.setEditable(false);
        jtaConsole.setBackground(new java.awt.Color(255, 255, 255));
        jtaConsole.setColumns(20);
        jtaConsole.setRows(5);
        jScrollPane2.setViewportView(jtaConsole);

        tblTokens.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Componente léxico", "Lexema", "[Línea, Columna]"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(tblTokens);

        bt_bg.setText("*");
        bt_bg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_bgActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout rootPanelLayout = new javax.swing.GroupLayout(rootPanel);
        rootPanel.setLayout(rootPanelLayout);
        rootPanelLayout.setHorizontalGroup(
            rootPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(rootPanelLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(rootPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, rootPanelLayout.createSequentialGroup()
                        .addComponent(buttonsFilePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(panelButtonCompilerExecute, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 693, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jspCode, javax.swing.GroupLayout.PREFERRED_SIZE, 693, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(rootPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(rootPanelLayout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 372, Short.MAX_VALUE))
                    .addGroup(rootPanelLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(bt_bg)))
                .addGap(24, 24, 24))
        );
        rootPanelLayout.setVerticalGroup(
            rootPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(rootPanelLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(rootPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(buttonsFilePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(panelButtonCompilerExecute, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bt_bg))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(rootPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(rootPanelLayout.createSequentialGroup()
                        .addComponent(jspCode)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 415, Short.MAX_VALUE))
                .addGap(24, 24, 24))
        );

        getContentPane().add(rootPanel);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
        System.out.println("nuevo");
        fillTable(this.tblTokens, new ArrayList());
        this.jtaConsole.setText("");
        this.jtpCode.setText("");
        this.Ctokens.clear();


    }//GEN-LAST:event_btnNuevoActionPerformed
    private void btnAbrirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAbrirActionPerformed
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Text Files", "txt", "cz"));
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            this.currentFilePath = selectedFile.getAbsolutePath();
            this.currentFileName = selectedFile.getName();
            System.out.println("Selected file: " + selectedFile.getAbsolutePath());
            try {
                BufferedReader reader = new BufferedReader(new FileReader(selectedFile));
                StringBuilder content = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    content.append(line).append("\n");
                }
                reader.close();
                this.jtpCode.setText(content.toString());
                this.jtaConsole.setText("");
                fillTable(this.tblTokens, new ArrayList<>());
            } catch (IOException e) {
                System.out.println("Error reading file: " + e.getMessage());
            }
        }
        this.updateColors();
    }//GEN-LAST:event_btnAbrirActionPerformed
    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        if (this.currentFilePath == null || this.currentFileName == null) {
            btnAbrirActionPerformed(evt);
        } else {
            System.out.println("guardar");
            // Add code here to save changes to the current file
            try {
                FileWriter fileWriter = new FileWriter(this.currentFilePath);
                fileWriter.write(this.jtpCode.getText());
                fileWriter.close();
            } catch (IOException ex) {
                System.out.println("Error al guardar el archivo: " + ex.getMessage());
            }
        }
    }//GEN-LAST:event_btnGuardarActionPerformed
    private void btnGuardarComoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarComoActionPerformed
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Text Files", "txt", "cz"));
        int result = fileChooser.showSaveDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try {
                FileWriter writer = new FileWriter(selectedFile);
                writer.write(jtpCode.getText());
                writer.close();
                System.out.println("\nFile saved: " + selectedFile.getAbsolutePath());
                this.currentFileName = selectedFile.getName();
                this.currentFilePath = selectedFile.getAbsolutePath();
            } catch (IOException e) {
                System.out.println("\nError saving file: " + e.getMessage());
            }
        }
    }//GEN-LAST:event_btnGuardarComoActionPerformed

    private void btnCompilarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCompilarActionPerformed
        System.out.println("compilar");
        String txt = this.jtpCode.getText().trim();
        Reader reader = new BufferedReader(new StringReader(txt));

        this.lex = new LexerAnalyzer((BufferedReader) reader);

        System.out.println("resultado: ");
        lex.printTokens();
        //tblTokens
        fillTable(this.tblTokens, lex.getTokens());

        fillErrorPanel(this.jtaConsole, lex.getErrorTokens());
    }//GEN-LAST:event_btnCompilarActionPerformed

    private void jtpCodeKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtpCodeKeyReleased
        // Obtén el StyledDocument del JTextPane
        this.updateColors();

    }//GEN-LAST:event_jtpCodeKeyReleased
    public void updateColors(){
        
        StyledDocument doc = (StyledDocument) this.jtpCode.getDocument();
        // Restablece el estilo a su estado original
        Style defaultStyle;
        
        defaultStyle =StyleContext.getDefaultStyleContext().getStyle(StyleContext.DEFAULT_STYLE);
        
        

        // Modifica el nuevo estilo
        if(this.dark) StyleConstants.setForeground(defaultStyle, Color.WHITE); // Cambia el color de la fuente a #808000
        else StyleConstants.setForeground(defaultStyle, Color.BLACK); 
        doc.setCharacterAttributes(0, doc.getLength(), defaultStyle, true);
        String txt = this.jtpCode.getText().trim();
        Reader reader = new BufferedReader(new StringReader(txt));
        LexerAnalyzer minilex = new LexerAnalyzer((BufferedReader) reader);
        this.Ctokens = (List<Token>) minilex.getTokens();
        // Recorre los tokens
        for (Iterator it = this.Ctokens.iterator(); it.hasNext();) {
            Token token = (Token) it.next();
            // Encuentra el estilo correspondiente al tipo de lexema del token
            Style style = colorStyles.get(token.getLexemeType());
            
            // Si encontramos un estilo, lo aplicamos al texto del token
            // Si encontramos un estilo, lo aplicamos a todas las ocurrencias del texto del token
            if (style != null) {
                String text = token.getText();
                if (token.getLexemeType().equals("ERR")) {
                    StyleConstants.setItalic(style, true);
                    StyleConstants.setUnderline(style, true);
                    StyleConstants.setForeground(style, Color.RED);

                }
                try {
                    String docText = doc.getText(0, doc.getLength());
                    int start = 0;
                    while ((start = docText.indexOf(text, start)) != -1) {
                        doc.setCharacterAttributes(start, text.length(), style, false);
                        start += text.length();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
    private void bt_bgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_bgActionPerformed
        // TODO add your handling code here:
        this.dark = !this.dark;
        if(this.dark ==true){
            Color myColor = new Color(40, 44, 52);
            this.jtpCode.setBackground(myColor);
            this.jtpCode.setCaretColor(Color.WHITE);
            this.jspCode.setForeground(Color.WHITE);
        }else{
            this.jtpCode.setBackground(Color.WHITE);
            this.jtpCode.setCaretColor(Color.BLACK);
            this.jspCode.setForeground(Color.WHITE);
        }
        this.createStyles();
        this.updateColors();
        
        
    }//GEN-LAST:event_bt_bgActionPerformed

    public void fillTable(JTable table, List<Token> tokens) {
        TokenTableModel model = new TokenTableModel(tokens);
        table.setModel(model);
    }

    public void fillErrorPanel(JTextArea txt, List<Token> tokens) {
        String result = "";
        if (tokens.isEmpty()) {
            result = "No se encontraron errores en el análisis léxico.\n";
        } else {
            result = "Se encontraron errores en el análisis léxico.\n";

            for (Token token : tokens) {
                result += token.toString() + "\n";
            }
        }
        txt.setText(result);
    }

    private void executeCode(ArrayList<String> blocksOfCode, int repeats) {

    }

    private void compile() {

    }

    private void lexicalAnalysis() {
        // Extraer tokens
//        Lexer lexer;
//        try {
//            File codigo = new File("code.encrypter");
//            FileOutputStream output = new FileOutputStream(codigo);
//            byte[] bytesText = jtpCode.getText().getBytes();
//            output.write(bytesText);
//            BufferedReader entrada = new BufferedReader(new InputStreamReader(new FileInputStream(codigo), "UTF8"));
//            lexer = new Lexer(entrada);
//            while (true) {
//                Token token = lexer.yylex();
//                if (token == null) {
//                    break;
//                }
//                tokens.add(token);
//            }
//        } catch (FileNotFoundException ex) {
//            System.out.printlnln("El archivo no pudo ser encontrado... " + ex.getMessage());
//        } catch (IOException ex) {
//            System.out.printlnln("Error al escribir en el archivo... " + ex.getMessage());
//        }
    }

    private void syntacticAnalysis() {
//        Grammar gramatica = new Grammar(tokens, errors);
//
//        /* Deshabilitar mensajes y validaciones */
//        gramatica.disableMessages();
//        gramatica.disableValidations();
//
//        /* Eliminación de errores */
//        gramatica.delete(new String[]{"ERROR", "ERROR_1", "ERROR_2"}, 14);
//
//        /* Agrupación de valores */
//        gramatica.group("VALOR", "(NUMERO | COLOR)", true);
//
//        /* Declaración de variables */
//        gramatica.group("VARIABLE", "TIPO_DATO IDENTIFICADOR OP_ASIG VALOR", true, identProd);
//        gramatica.group("VARIABLE", "TIPO_DATO OP_ASIG VALOR", true,
//                1, " × Error sintáctico {}: falta el identificador en la declaración de variable [#, %]");
//
//        gramatica.finalLineColumn();
//
//        gramatica.group("VARIABLE", "TIPO_DATO IDENTIFICADOR OP_ASIG", true,
//                2, " × Error sintáctico {}: falta el valor en la declaración de variable [#, %]");
//
//        gramatica.initialLineColumn();
//
//        gramatica.group("VARIABLE", "TIPO_DATO IDENTIFICADOR VALOR", true,
//                3, " × Error sintáctico {}: falta el operador de asignación en la declaración de variable [#, %]", 2);
//        gramatica.group("VARIABLE", "IDENTIFICADOR OP_ASIG VALOR", true,
//                4, " × Error sintáctico {}: falta el tipo de dato en la declaración de variable [#, %]");
//
//        /* Eliminación de tipos de datos y operadores de asignación */
//        gramatica.delete("TIPO_DATO",
//                5, " × Error sintáctico {}: el tipo de dato no está en la declaración de una variable [#, %]");
//        gramatica.delete("OP_ASIG",
//                6, " × Error sintáctico {}: el operador de asignación no está en la declaración de una variable [#, %]");
//
//        /* Agrupación de identificadores como valor y definición de parámetros */
//        gramatica.group("VALOR", "IDENTIFICADOR", true);
//        gramatica.group("PARAMETROS", "VALOR (COMA VALOR)+");
//
//        /* Agrupación de funciones */
//        gramatica.group("FUNCION", "(MOVIMIENTO | PINTAR | DETENER_PINTAR |"
//                + " TOMAR | LANZAR_MONEDA | VER | DETENER_REPETIR )", true);
//        gramatica.group("FUNCION_COMP", "FUNCION PARENTESIS_A (VALOR | PARAMETROS)? PARENTESIS_C", true);
//        gramatica.group("FUNCION_COMP", "FUNCION (VALOR | PARAMETROS)? PARENTESIS_C", true,
//                7, " × Error sintáctico {}: falta el paréntesis que abre en la función [#, %]");
//        gramatica.finalLineColumn();
//        gramatica.group("FUNCION_COMP", "FUNCION PARENTESIS_A (VALOR | PARAMETROS)", true,
//                8, " × Error sintáctico {}: falta el paréntesis que cierra en la función [#, %]");
//
//        gramatica.initialLineColumn();
//
//        /* Eliminación de funciones mal declaradas */
//        gramatica.delete("FUNCION",
//                9, " × Error sintáctico {}: La función no está declarada correctamente [#, %]");
//
//        /* Expresiones lógicas */
//        gramatica.loopForFunExecUntilChangeNotDetected(() -> {
//            gramatica.group("EXP_LOGICA", "(EXP_LOGICA | FUNCION_COMP) (OP_LOGICO (EXP_LOGICA | FUNCION_COMP))+");
//            gramatica.group("EXP_LOGICA", "PARENTESIS_A (EXP_LOGICA | FUNCION_COMP) PARENTESIS_C", true);
//        });
//
//        /* Eliminación de operadores lógicos */
//        gramatica.delete("OP_LOGICO",
//                10, " × Error sintáctico {}: El operador lógico no está contenido en una expresión [#, %]");
//
//        /* Agrupación de expresiones lógicas como valores y parámetros */
//        gramatica.group("VALOR", "EXP_LOGICA", true);
//        gramatica.group("PARAMETROS", "VALOR (COMA VALOR)+");
//
//        /* Agrupación de estructuras de control */
//        gramatica.group("EST_CONTROL", "(REPETIR | ESTRUCTURA_SI)", true);
//        gramatica.group("EST_CONTROL_COMP", "EST_CONTROL (VALOR | PARAMETROS)", true);
//        gramatica.group("EST_CONTROL_COMP", "EST_CONTROL PARENTESIS_A PARENTESIS_C", true);
//        gramatica.group("EST_CONTROL_COMP", "EST_CONTROL PARENTESIS_A (VALOR | PARAMETROS) PARENTESIS_C", true);
//        gramatica.group("EST_CONTROL_COMP", "EST_CONTROL (VALOR | PARAMETROS) PARENTESIS_C", true,
//                11, " × Error sintáctico {}: falta el paréntesis que abre en la estructura [#, %]");
//        gramatica.group("EST_CONTROL_COMP", "EST_CONTROL PARENTESIS_C", true,
//                12, " × Error sintáctico {}: falta el paréntesis que abre en la estructura [#, %]");
//        gramatica.finalLineColumn();
//        gramatica.group("EST_CONTROL_COMP", "EST_CONTROL PARENTESIS_A (VALOR | PARAMETROS)", true,
//                13, " × Error sintáctico {}: falta el paréntesis que cierra en la estructura [#, %]");
//        gramatica.group("EST_CONTROL_COMP", "EST_CONTROL PARENTESIS_A", true,
//                14, " × Error sintáctico {}: falta el paréntesis que cierra en la estructura [#, %]");
//
//        gramatica.initialLineColumn();
//
//        /* Eliminación de estructuras de control mal declaradas */
//        gramatica.delete("EST_CONTROL",
//                15, " × Error sintáctico {}: La estructura de control no está declarada correctamente [#, %]");
//
//        /* Eliminación de paréntesis */
//        gramatica.delete(new String[]{"PARENTESIS_A", "PARENTESIS_C"},
//                16, " × Error sintáctico {}: El paréntesis [] no está contenido en una agrupación [#, %]");
//
//        /* Eliminación de valores */
//        gramatica.delete("VALOR",
//                17, " × Error sintáctico {}: El valor no está contenido en una función o estructura de control [#, %]");
//
//        gramatica.finalLineColumn();
//
//        /* Verificación de punto y coma al final de la sentencia */
//        // Identificadores
//        gramatica.group("VARIABLE_PC", "VARIABLE PUNTO_COMA", true);
//        gramatica.group("VARIABLE_PC", "VARIABLE", true,
//                18, " × Error sintáctico {}: falta el punto y coma al final de la declaración de variable [#, %]");
//        // Funciones
//        gramatica.group("FUNCION_COMP_PC", "FUNCION_COMP PUNTO_COMA", true);
//        gramatica.group("FUNCION_COMP_PC", "FUNCION_COMP", true,
//                19, " × Error sintáctico {}: falta el punto y coma al final de la declaración de función [#, %]");
//
//        gramatica.initialLineColumn();
//
//        /* Eliminación de punto y coma */
//        gramatica.delete("PUNTO_COMA",
//                20, " × Error sintáctico {}: el punto y coma no está al final de una sentencia [#, %]");
//
//        /* Agrupación de sentencias */
//        gramatica.group("SENTENCIAS", "(VARIABLE_PC | FUNCION_COMP_PC)+");
//        /* Estructuras de control completas */
//        gramatica.loopForFunExecUntilChangeNotDetected(() -> {
//            gramatica.group("EST_CONTROL_COMP_LASLC", "EST_CONTROL_COMP LLAVE_A (SENTENCIAS)? LLAVE_C", true);
//            gramatica.group("SENTENCIAS", "(SENTENCIAS | EST_CONTROL_COMP_LASLC)+");
//        });
//
//        /* Estructuras de control incompletas */
//        gramatica.loopForFunExecUntilChangeNotDetected(() -> {
//            gramatica.initialLineColumn();
//
//            gramatica.group("EST_CONTROL_COMP_LASLC", "EST_CONTROL_COMP (SENTENCIAS)? LLAVE_C", true,
//                    21, " × Error sintáctico {}: falta la llave que abre en la estructura de control [#, %]");
//
//            gramatica.finalLineColumn();
//
//            gramatica.group("EST_CONTROL_COMP_LASLC", "EST_CONTROL_COMP LLAVE_A SENTENCIAS",
//                    22, " × Error sintáctico {}: falta la llave que cierra en la estructura de control [#, %]");
//            gramatica.group("SENTENCIAS", "(SENTENCIAS | EST_CONTROL_COMP_LASLC)+");
//        });
//
//        /* Eliminación de llaves */
//        gramatica.delete(new String[]{"LLAVE_A", "LLAVE_C"},
//                23, " × Error sintáctico {}: la llave no está contenida en una agrupación [#, %]");

        /* Mostrar gramáticas */
        // gramatica.show();
    }

    private void semanticAnalysis() {
//        HashMap<String, String> identDataType = new HashMap<>();
//        identDataType.put("color", "COLOR");
//        identDataType.put("número", "NUMERO");
//        for (Production id : identProd) {
//            if (!identDataType.get(id.lexemeRank(0)).equals(id.lexicalCompRank(-1))) {
//                errors.add(new ErrorLSSL(1, " × Error semántico {}: valor no compatible con el tipo de dato [#, %]", id, true));
//            }
//            if (id.lexicalCompRank(-1).equals("COLOR") && !id.lexemeRank(-1).matches("#[0-9a-fA-F]+")) {
//                errors.add(new ErrorLSSL(2, " × Error lógico {}: el color no es un número hexadecimal [#, %]", id, false));
//            }
//            identificadores.put(id.lexemeRank(1), id.lexemeRank(-1));
//        }
    }

    private void fillTableTokens() {
//        tokens.forEach(token -> {
//            Object[] data = new Object[]{token.getLexicalComp(), token.getLexeme(), "[" + token.getLine() + ", " + token.getColumn() + "]"};
//            Functions.addRowDataInTable(tblTokens, data);
//        });
    }

    private void printlnConsole() {
//        int sizeErrors = errors.size();
//        if (sizeErrors > 0) {
//            Functions.sortErrorsByLineAndColumn(errors);
//            String strErrors = "\n";
//            for (ErrorLSSL error : errors) {
//                String strError = String.valueOf(error);
//                strErrors += strError + "\n";
//            }
//            jtaOutputConsole.setText("Compilación terminada...\n" + strErrors + "\nLa compilación terminó con errores...");
//        } else {
//            jtaOutputConsole.setText("Compilación terminada...");
//        }
//        jtaOutputConsole.setCaretPosition(0);
    }

    private void clearFields() {
//        Functions.clearDataInTable(tblTokens);
//        jtaOutputConsole.setText("");
//        tokens.clear();
//        errors.clear();
//        identProd.clear();
//        identificadores.clear();
//        codeHasBeenCompiled = false;
    }

    public void createStyles() {
        // Obtén el StyledDocument del JTextPane
        StyledDocument doc = (StyledDocument) this.jtpCode.getDocument();
        this.colorStyles = new HashMap<>();
        // Recorre los valores del enum
        for (ColorType colorType : ColorType.values()) {
            // Crea un estilo para cada valor
            Style style = doc.addStyle(colorType.name(), null);
            if(this.dark ==true){
            StyleConstants.setForeground(style, colorType.getdColor());
            }else{
            StyleConstants.setForeground(style, colorType.getColor());
            }
            this.colorStyles.put(colorType.name(), style);
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Compilador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Compilador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Compilador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Compilador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {

            new Compilador().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bt_bg;
    private javax.swing.JButton btnAbrir;
    private javax.swing.JButton btnCompilar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnGuardarComo;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JPanel buttonsFilePanel;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jspCode;
    private javax.swing.JTextArea jtaConsole;
    private javax.swing.JTextPane jtpCode;
    private javax.swing.JPanel panelButtonCompilerExecute;
    private javax.swing.JPanel rootPanel;
    private javax.swing.JTable tblTokens;
    // End of variables declaration//GEN-END:variables
}