import re

data = """import java_cup.runtime.Symbol;

%%
%class Lexer
%type Symbol
%line
%column
%{
    // Estos son los lexemas, se implementan en otro orden por eso las listas de aqui abajo
    // solo sirven para tener una idea de los lexemas que se pueden encontrar en el código
    List<String> palabrasReservadas_RW = Arrays.asList("if", "else", "while", "for", "return", "break", "continue", "fn", "bool", "int", "float", "string", "true", "false", "null")
    List<String> palabrasReservadas_RW_Extra = Arrays.asList("mean", "max", "min", "median", "mode");
    List<String> identificadores_ID = Arrays.asList("Nombres de variables", "funciones", "procedimientos", "tipos de datos");
    List<String> operadoresAritmeticos_OP_AR = Arrays.asList("+", "-", "*", "/", "%");
    List<String> operadoresRelacionales_OP_REL = Arrays.asList("<", "<=", ">", ">=", "==", "!=");
    List<String> operadoresLogicos_OP_LOG = Arrays.asList("&&", "||", "!");
    List<String> operadorAsignacion_OP_ASIGN = Collections.singletonList("=");
    List<String> delimitadoresParentesis_DEL_LR = Arrays.asList("(", ")", "[", "]", "{", "}");
    List<String> delimitadoresComillasComentarios_COM = Arrays.asList("\"", "'");
    List<String> delimitadoresPuntuacion_DEL_PUNT = Arrays.asList(";", ",", ".", ":");
    List<String> literalesNumericos = Arrays.asList("MAX_INT", "MAX_FLOAT");
    List<String> literalesCadena = Arrays.asList("\"\"", "'C'");
    List<String> literalesBooleanos_LIT_BOOL = Arrays.asList("true", "false");
    List<String> comentarios = Arrays.asList("Se utilizan para documentar el código y no son interpretados por el compilador.");


    public enum TokenType {
        IDENTIFICADOR,
        L_PARENTESIS,
        R_PARENTESIS,
        L_LLAVE,
        R_LLAVE,
        L_CORCHETE,
        R_CORCHETE,
        COMA,
        PUNTO_COMA,
        OP_ASIG,
        COND_IF,
        COND_ELIF,
        COND_ELSE,
        OP_AND,
        OP_OR,
        OP_NOT,
        E_NUM_START_ZERO,
        E_ID_NO_$,
        E_SIMB_NOT_FOUND
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
ComentarioDeDocumentacion = "\"\"\""{EntradaDeCaracter}*"\"\"\""

Comentario = {ComentarioTradicional} | {FinDeLineaComentario} | {ComentarioDeDocumentacion}

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

<YYINITIAL> {
  
  /* Palabras reservadas */
  "if" { return token(sym.IF, yytext(), yyline, yycolumn); }
  "else" { return token(sym.ELSE, yytext(), yyline, yycolumn); }
  "elif" { return token(sym.ELIF, yytext(), yyline, yycolumn); }
  "while" { return token(sym.WHILE, yytext(), yyline, yycolumn); }
  "for" { return token(sym.FOR, yytext(), yyline, yycolumn); }
  "return" { return token(sym.RETURN, yytext(), yyline, yycolumn); }
  "break" { return token(sym.BREAK, yytext(), yyline, yycolumn); }
  "continue" { return token(sym.CONTINUE, yytext(), yyline, yycolumn); }
  "fn" { return token(sym.FN, yytext(), yyline, yycolumn); }
  "bool" { return token(sym.BOOL, yytext(), yyline, yycolumn); }
  "int" { return token(sym.INT, yytext(), yyline, yycolumn); }
  "float" { return token(sym.FLOAT, yytext(), yyline, yycolumn); }
  "string" { return token(sym.STRING, yytext(), yyline, yycolumn); }
  "true" { return token(sym.TRUE, yytext(), yyline, yycolumn); }
  "false" { return token(sym.FALSE, yytext(), yyline, yycolumn); }
  "null" { return token(sym.NULL, yytext(), yyline, yycolumn); }

  /* Extra: Palabras reservadas, para implementaciones estadisticas (Problema del proyecto)*/
  "mean" { return token(sym.MEAN, yytext(), yyline, yycolumn); }
  "max" { return token(sym.MAX, yytext(), yyline, yycolumn); }
  "min" { return token(sym.MIN, yytext(), yyline, yycolumn); }
  "median" { return token(sym.MEDIAN, yytext(), yyline, yycolumn); }
  "mode" { return token(sym.MODE, yytext(), yyline, yycolumn); }


  /* Identificador */
  \${Identificador} { return token(TokenType.IDENTIFICADOR, yytext(), yyline, yycolumn); }
  
  /* Operadores aritméticos */
  "+" { return token(sym.OP_SUMA, yytext(), yyline, yycolumn); }
  "-" { return token(sym.OP_RESTA, yytext(), yyline, yycolumn); }
  "*" { return token(sym.OP_MULT, yytext(), yyline, yycolumn); }
  "/" { return token(sym.OP_DIV, yytext(), yyline, yycolumn); }
  "%" { return token(sym.OP_MOD, yytext(), yyline, yycolumn); }
  
  /* Operadores relacionales */
  "<" { return token(sym.OP_MENOR, yytext(), yyline, yycolumn); }
  "<=" { return token(sym.OP_MENOR_IGUAL, yytext(), yyline, yycolumn); }
  ">" { return token(sym.OP_MAYOR, yytext(), yyline, yycolumn); }
  ">=" { return token(sym.OP_MAYOR_IGUAL, yytext(), yyline, yycolumn); }
  "==" { return token(sym.OP_IGUAL, yytext(), yyline, yycolumn); }
  "!=" { return token(sym.OP_DIFERENTE, yytext(), yyline, yycolumn); }
  
  /* Operadores lógicos */
  "&&" { return token(sym.OP_AND, yytext(), yyline, yycolumn); }
  "||" { return token(sym.OP_OR, yytext(), yyline, yycolumn); }
  "!" { return token(sym.OP_NOT, yytext(), yyline, yycolumn); }
  
  /* Operador de asignación */
  "=" { return token(sym.OP_ASIGN, yytext(), yyline, yycolumn); }

  /* Operadores de agrupación o delimitadores parentesis */
  "(" { return token(sym.L_PARENTESIS, yytext(), yyline, yycolumn); }
  ")" { return token(sym.R_PARENTESIS, yytext(), yyline, yycolumn); }
  "{" { return token(sym.L_LLAVE, yytext(), yyline, yycolumn); }
  "}" { return token(sym.R_LLAVE, yytext(), yyline, yycolumn); }
  "[" { return token(sym.L_CORCHETE, yytext(), yyline, yycolumn); }
  "]" { return token(sym.R_CORCHETE, yytext(), yyline, yycolumn); }
  
  /* Comentarios o espacios en blanco */
  {Comentario}|{EspacioEnBlanco} { /*Ignorar*/ }
  /*Nota: todo elemento dentro de comillas dobles o simples se toma como comentario y se ignora */
  /* Signos de puntuación */
  "," { return token(sym.COMA, yytext(), yyline, yycolumn); }
  ";" { return token(sym.PUNTO_COMA, yytext(), yyline, yycolumn); }
  "." { return token(sym.PUNTO, yytext(), yyline, yycolumn); }
  ":" { return token(sym.DOS_PUNTOS, yytext(), yyline, yycolumn); }

  /* Literales */
  {NumeroEntero} { return token(sym.NUM_ENTERO, yytext(), yyline, yycolumn); }
  {NumeroFlotante} { return token(sym.NUM_FLOTANTE, yytext(), yyline, yycolumn); }
  {String} { return token(sym.STRING, yytext(), yyline, yycolumn); }
  /* null, true, false definidos en palabras reservadas también cuentan como literales */

    /* Errores */
    // Número erróneo
    0 {Numero}+ { return token(sym.E_NUM_START_ZERO, yytext(), yyline, yycolumn); }
    // Identificador sin $
    //{Identificador} { return token(sym.E_ID_NO_$, yytext(), yyline, yycolumn); }
    /* Este error se quita porque se decidió no empezar las variables con $ porque hoy en dia y con la facilidad de herramientas que se tiene ya no se necesita esa nomenclatura*/
    . { return token(sym.E_SIMB_NOT_FOUND, yytext(), yyline, yycolumn); }
    
}

"""
def collectTokensNames(data):
    """Returns a list of tokens names"""
    return [match.split('.')[1] for match in re.findall(r'sym\.\w+', data)]

def generate_enum(words):
    """Generates a Java enum with the tokens names"""
    print("public enum TokenType {")
    for word in words:
        print("    " + word + ",")
    print("}")

words = collectTokensNames(data)
print(words)
generate_enum(words)
