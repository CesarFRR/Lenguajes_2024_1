
%%
%class Lexer
%type Token
%line
%column
%{
    /*Estos son los lexemas, se implementan en otro orden por eso las listas de aqui abajo
    solo sirven para tener una idea de los lexemas que se pueden encontrar en el código*/
    // List<String> palabrasReservadas_RW = Arrays.asList("if", "else", "while", "for", "return", "break", "continue", "fn", "bool", "int", "float", "string", "true", "false", "null")
    // List<String> palabrasReservadas_RW_Extra = Arrays.asList("mean", "max", "min", "median", "mode");
    // List<String> identificadores_ID = Arrays.asList("Nombres de variables", "funciones", "procedimientos", "tipos de datos");
    // List<String> operadoresAritmeticos_OP_AR = Arrays.asList("+", "-", "*", "/", "%");
    // List<String> operadoresRelacionales_OP_REL = Arrays.asList("<", "<=", ">", ">=", "==", "!=");
    // List<String> operadoresLogicos_OP_LOG = Arrays.asList("&&", "||", "!");
    // List<String> operadorAsignacion_OP_ASIGN = Collections.singletonList("=");
    // List<String> delimitadoresParentesis_DEL_LR = Arrays.asList("(", ")", "[", "]", "{", "}");
    // List<String> delimitadoresComillasComentarios_COM = Arrays.asList("\"", "'");
    // List<String> delimitadoresPuntuacion_DEL_PUNT = Arrays.asList(";", ",", ".", ":");
    // List<String> literalesNumericos = Arrays.asList("MAX_INT", "MAX_FLOAT");
    // List<String> literalesCadena = Arrays.asList("\"\"", "'C'");
    // List<String> literalesBooleanos_LIT_BOOL = Arrays.asList("true", "false");
    // List<String> comentarios = Arrays.asList("Se utilizan para documentar el código y no son interpretados por el compilador.");

    // String[] ALL_TOKENS = ['IF', 'ELSE', 'ELIF', 'WHILE', 'FOR', 'RETURN', 'BREAK', 'CONTINUE', 'FN', 'BOOL', 'INT', 'FLOAT', 'STRING', 'TRUE', 'FALSE', 'NULL', 'MEAN', 'MAX', 'MIN', 'MEDIAN', 'MODE', 'OP_SUMA', 'OP_RESTA', 'OP_MULT', 'OP_DIV', 'OP_MOD', 'OP_MENOR', 'OP_MENOR_IGUAL', 'OP_MAYOR', 'OP_MAYOR_IGUAL', 'OP_IGUAL', 'OP_DIFERENTE', 'OP_AND', 'OP_OR', 'OP_NOT', 'OP_ASIGN', 'L_PARENTESIS', 'R_PARENTESIS', 'L_LLAVE', 'R_LLAVE', 'L_CORCHETE', 'R_CORCHETE', 'COMA', 'PUNTO_COMA', 'PUNTO', 'DOS_PUNTOS', 'NUM_ENTERO', 'NUM_FLOTANTE', 'STRING', 'E_NUM_START_ZERO', 'E_ID_NO_', 'E_SIMB_NOT_FOUND']

    /* Explicación: se usa un enum de java que "enumera" cada nombre con un numero, esto se hace porque aun no se puede usar Symbol y Token de java_cup.runtime porque se necesita un archivo CUP y aun no se puede usar la libreria CUP porque hace parte del taller 2 y el profe prohibió eso, asi que en taller 1 --> enum */
    public enum TokenType {
        IF,
        ELSE,
        ELIF,
        WHILE,
        FOR,
        RETURN,
        BREAK,
        CONTINUE,
        FN,
        BOOL,
        INT,
        FLOAT,
        STRING,
        TRUE,
        FALSE,
        NULL,
        MEAN,
        MAX,
        MIN,
        MEDIAN,
        MODE,
        OP_SUMA,
        OP_RESTA,
        OP_MULT,
        OP_DIV,
        OP_MOD,
        OP_MENOR,
        OP_MENOR_IGUAL,
        OP_MAYOR,
        OP_MAYOR_IGUAL,
        OP_IGUAL,
        OP_DIFERENTE,
        OP_AND,
        OP_OR,
        OP_NOT,
        OP_ASIGN,
        L_PARENTESIS,
        R_PARENTESIS,
        L_LLAVE,
        R_LLAVE,
        L_CORCHETE,
        R_CORCHETE,
        COMA,
        PUNTO_COMA,
        PUNTO,
        DOS_PUNTOS,
        NUM_ENTERO,
        NUM_FLOTANTE,
        STRING,
        E_NUM_START_ZERO,
        E_ID_NO_,
        E_SIMB_NOT_FOUND,
    }

  
    public class Token {
      public TokenType type;
      public String text;
      public int line;
      public int column;
  
      public Token(TokenType type, String text, int line, int column) {
          this.type = type;
          this.text = text;
          this.line = line;
          this.column = column;
      }
  }
  
  private Token token(TokenType type, String lexeme, int line, int column){
      return new Token(type, lexeme, line, column);
  }
  %}
  
/* Variables básicas de comentarios y espacios */
TerminadorDeLinea = \r|\n|\r\n
EntradaDeCaracter = [^\r\n]
EspacioEnBlanco = {TerminadorDeLinea} | [ \t\f]
ComentarioTradicional = "#" {EntradaDeCaracter}*
FinDeLineaComentario = "#" {EntradaDeCaracter}* {TerminadorDeLinea}?
// ComentarioDeDocumentacion = "\"\"\""[^\"]*"\"\"\"" | "'''"[^\']*"'''"

Comentario = {ComentarioTradicional} | {FinDeLineaComentario} 

/* Identificador */
Letra = [A-Za-zÑñ_ÁÉÍÓÚáéíóúÜü]
Digito = [0-9]
Identificador = {Letra}({Letra}|{Digito})*

/* Número */
Numero = 0 | [1-9][0-9]*
%%

/* Número entero */
NumeroEntero = {Numero}

/* Número flotante */
NumeroFlotante = {NumeroEntero} "." [0-9]+

/* Booleano */
Booleano = "true" | "false"

/* String */
String = '"' {EntradaDeCaracter}* '"' | "'" {EntradaDeCaracter}* "'"

%%
/* Palabras reservadas */
"if" { return token(TokenType.IF, yytext(), yyline, yycolumn); }
"else" { return token(TokenType.ELSE, yytext(), yyline, yycolumn); }
"elif" { return token(TokenType.ELIF, yytext(), yyline, yycolumn); }
"while" { return token(TokenType.WHILE, yytext(), yyline, yycolumn); }
"for" { return token(TokenType.FOR, yytext(), yyline, yycolumn); }
"return" { return token(TokenType.RETURN, yytext(), yyline, yycolumn); }
"break" { return token(TokenType.BREAK, yytext(), yyline, yycolumn); }
"continue" { return token(TokenType.CONTINUE, yytext(), yyline, yycolumn); }
"fn" { return token(TokenType.FN, yytext(), yyline, yycolumn); }
"bool" { return token(TokenType.BOOL, yytext(), yyline, yycolumn); }
"int" { return token(TokenType.INT, yytext(), yyline, yycolumn); }
"float" { return token(TokenType.FLOAT, yytext(), yyline, yycolumn); }
"string" { return token(TokenType.STRING, yytext(), yyline, yycolumn); }
"true" { return token(TokenType.TRUE, yytext(), yyline, yycolumn); }
"false" { return token(TokenType.FALSE, yytext(), yyline, yycolumn); }
"null" { return token(TokenType.NULL, yytext(), yyline, yycolumn); }

/* Extra: Palabras reservadas, para implementaciones estadisticas (Problema del proyecto)*/
"mean" { return token(TokenType.MEAN, yytext(), yyline, yycolumn); }
"max" { return token(TokenType.MAX, yytext(), yyline, yycolumn); }
"min" { return token(TokenType.MIN, yytext(), yyline, yycolumn); }
"median" { return token(TokenType.MEDIAN, yytext(), yyline, yycolumn); }
"mode" { return token(TokenType.MODE, yytext(), yyline, yycolumn); }


/* Identificador */
{Identificador} { return token(TokenType.IDENTIFICADOR, yytext(), yyline, yycolumn); }

/* Operadores aritméticos */
"+" { return token(TokenType.OP_SUMA, yytext(), yyline, yycolumn); }
"-" { return token(TokenType.OP_RESTA, yytext(), yyline, yycolumn); }
"*" { return token(TokenType.OP_MULT, yytext(), yyline, yycolumn); }
"/" { return token(TokenType.OP_DIV, yytext(), yyline, yycolumn); }
"%" { return token(TokenType.OP_MOD, yytext(), yyline, yycolumn); }

/* Operadores relacionales */
"<" { return token(TokenType.OP_MENOR, yytext(), yyline, yycolumn); }
"<=" { return token(TokenType.OP_MENOR_IGUAL, yytext(), yyline, yycolumn); }
">" { return token(TokenType.OP_MAYOR, yytext(), yyline, yycolumn); }
">=" { return token(TokenType.OP_MAYOR_IGUAL, yytext(), yyline, yycolumn); }
"==" { return token(TokenType.OP_IGUAL, yytext(), yyline, yycolumn); }
"!=" { return token(TokenType.OP_DIFERENTE, yytext(), yyline, yycolumn); }

/* Operadores lógicos */
"&&" { return token(TokenType.OP_AND, yytext(), yyline, yycolumn); }
"||" { return token(TokenType.OP_OR, yytext(), yyline, yycolumn); }
"!" { return token(TokenType.OP_NOT, yytext(), yyline, yycolumn); }

/* Operador de asignación */
"=" { return token(TokenType.OP_ASIGN, yytext(), yyline, yycolumn); }

/* Operadores de agrupación o delimitadores parentesis */
"(" { return token(TokenType.L_PARENTESIS, yytext(), yyline, yycolumn); }
")" { return token(TokenType.R_PARENTESIS, yytext(), yyline, yycolumn); }
"{" { return token(TokenType.L_LLAVE, yytext(), yyline, yycolumn); }
"}" { return token(TokenType.R_LLAVE, yytext(), yyline, yycolumn); }
"[" { return token(TokenType.L_CORCHETE, yytext(), yyline, yycolumn); }
"]" { return token(TokenType.R_CORCHETE, yytext(), yyline, yycolumn); }

/* Comentarios o espacios en blanco */
{Comentario}|{EspacioEnBlanco} { /*Ignorar*/ }
/*Nota: todo elemento dentro de comillas dobles o simples se toma como comentario y se ignora */
/* Signos de puntuación */
"," { return token(TokenType.COMA, yytext(), yyline, yycolumn); }
";" { return token(TokenType.PUNTO_COMA, yytext(), yyline, yycolumn); }
"." { return token(TokenType.PUNTO, yytext(), yyline, yycolumn); }
":" { return token(TokenType.DOS_PUNTOS, yytext(), yyline, yycolumn); }

/* Literales */
{NumeroEntero} { return token(TokenType.NUM_ENTERO, yytext(), yyline, yycolumn); }
{NumeroFlotante} { return token(TokenType.NUM_FLOTANTE, yytext(), yyline, yycolumn); }
{String} { return token(TokenType.STRING, yytext(), yyline, yycolumn); }
/* null, true, false definidos en palabras reservadas también cuentan como literales */

/* Errores */
0 {Numero}+ { return token(TokenType.E_NUM_START_ZERO, yytext(), yyline, yycolumn); }
. { return token(TokenType.E_SIMB_NOT_FOUND, yytext(), yyline, yycolumn); }



