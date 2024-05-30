import java_cup.runtime.*;
import model.scanner.Token.ColorType;
import model.scanner.Token.Token;
import model.scanner.Token.TokenType;
import java.util.List;
import java.util.ArrayList;
import java.util.Objects;

%%
%class Lexer
%cup
%line
%column
%{
                StringBuffer string = new StringBuffer();
                List<Token> tokens = new ArrayList<>();
                List<Token> errors = new ArrayList<>();

                public  List<Token> getTokens(){return tokens;}

                public  List<Token> getErrors(){return errors;}

                private Symbol symbol(int type){
                    return new Symbol(type, yyline, yycolumn);
                }
                private Symbol symbol(int type, Object value){
                    return new Symbol(type, yyline, yycolumn, value);
                }

                private Symbol symbol(int type, Object value, int line, int column){
                    return new Symbol(type, line, column, value);
                }

                private Symbol symbol(TokenType type,  Object value, int line, int column){
                  tokens.add(new Token(type, value.toString(), line, column));
                  Symbol s= null;
                  System.out.print("Token: "+type+" "+value+" +  id: " + type.ordinal());
                  if (Objects.equals(tokens.getLast().getLexemeType(), ColorType.ERR.name())){
                    errors.add(new Token(type, value.toString(), line, column));
                    s = new Symbol(TokenType.ERROR.ordinal(), line, column, value);

                  }else s = new Symbol(type.ordinal(), line, column, value);
                  System.out.print(" == " + s.toString());
                  System.out.println(" ");
                  return s;
                }
%}
/*Fin de archivo, importante a futuro para el analizador sintactico + semantica*/
%eofval{
return symbol(TokenType.EOF.ordinal());
%eofval}
%eofclose
/* Variables básicas de comentarios y espacios */
TerminadorDeLinea = \r|\n|\r\n
EntradaDeCaracter = [^\r\n]
EspacioEnBlanco = {TerminadorDeLinea} | [ \t\f]
ComentarioTradicional = "#" {EntradaDeCaracter}*
FinDeLineaComentario = "#" {EntradaDeCaracter}* {TerminadorDeLinea}?
ComentarioDeDocumentacion = "\\\"\\\"\\\"(\\\\.|[^\\\\])*\\\"\\\"\\\"" | "\\'\\'\\'(\\\\.|[^\\\\])*\\'\\'\\'"

Comentario = {ComentarioTradicional} | {FinDeLineaComentario} | {ComentarioDeDocumentacion}

/* Identificador */
Letra = [A-Za-zÑñ_ÁÉÍÓÚáéíóúÜü]
Digito = [0-9]
Identificador = {Letra}({Letra}|{Digito})*

/* Número */
Numero = 0 | [1-9][0-9]*

/* Número entero */
NumeroEntero = {Numero}

/* Número flotante */
NumeroFlotante = {NumeroEntero} "." [0-9]+

/* Booleano */
//Booleano = "true" | "false"

/* String */
String = \"(\\.|[^\"\\])*\" | \'(\\.|[^\'\\])*\'

%%
/* Palabras reservadas */
"if" { return symbol(TokenType.IF, yytext(), yyline, yycolumn); }
"else" { return symbol(TokenType.ELSE, yytext(), yyline, yycolumn); }
"elif" { return symbol(TokenType.ELIF, yytext(), yyline, yycolumn); }
"while" { return symbol(TokenType.WHILE, yytext(), yyline, yycolumn); }
"for" { return symbol(TokenType.FOR, yytext(), yyline, yycolumn); }
"return" { return symbol(TokenType.RETURN, yytext(), yyline, yycolumn); }
"break" { return symbol(TokenType.BREAK, yytext(), yyline, yycolumn); }
"continue" { return symbol(TokenType.CONTINUE, yytext(), yyline, yycolumn); }
"fn" { return symbol(TokenType.FN, yytext(), yyline, yycolumn); }
"bool" { return symbol(TokenType.BOOL, yytext(), yyline, yycolumn); }
"int" { return symbol(TokenType.INT, yytext(), yyline, yycolumn); }
"float" { return symbol(TokenType.FLOAT, yytext(), yyline, yycolumn); }
"string" { return symbol(TokenType.STRING, yytext(), yyline, yycolumn); }
"true" { return symbol(TokenType.TRUE, yytext(), yyline, yycolumn); }
"false" { return symbol(TokenType.FALSE, yytext(), yyline, yycolumn); }
"null" { return symbol(TokenType.NULL, yytext(), yyline, yycolumn); }
"print" { return symbol(TokenType.PRINT, yytext(), yyline, yycolumn); }
/* Extra: Palabras reservadas, para implementaciones estadisticas (Problema del proyecto)*/
"mean" { return symbol(TokenType.MEAN, yytext(), yyline, yycolumn); }
"max" { return symbol(TokenType.MAX, yytext(), yyline, yycolumn); }
"min" { return symbol(TokenType.MIN, yytext(), yyline, yycolumn); }
"median" { return symbol(TokenType.MEDIAN, yytext(), yyline, yycolumn); }
"mode" { return symbol(TokenType.MODE, yytext(), yyline, yycolumn); }


/* Identificador */
{Identificador} { return symbol(TokenType.IDENTIFICADOR, yytext(), yyline, yycolumn); }

/* Operadores aritméticos */
"+" { return symbol(TokenType.OP_SUMA, yytext(), yyline, yycolumn); }
"-" { return symbol(TokenType.OP_RESTA, yytext(), yyline, yycolumn); }
"*" { return symbol(TokenType.OP_MULT, yytext(), yyline, yycolumn); }
"/" { return symbol(TokenType.OP_DIV, yytext(), yyline, yycolumn); }
"%" { return symbol(TokenType.OP_MOD, yytext(), yyline, yycolumn); }
"^" { return symbol(TokenType.OP_POT, yytext(), yyline, yycolumn); }
/* Operadores relacionales */
"<" { return symbol(TokenType.OP_MENOR, yytext(), yyline, yycolumn); }
"<=" { return symbol(TokenType.OP_MENOR_IGUAL, yytext(), yyline, yycolumn); }
">" { return symbol(TokenType.OP_MAYOR, yytext(), yyline, yycolumn); }
">=" { return symbol(TokenType.OP_MAYOR_IGUAL, yytext(), yyline, yycolumn); }
"==" { return symbol(TokenType.OP_IGUAL, yytext(), yyline, yycolumn); }
"!=" { return symbol(TokenType.OP_DIFERENTE, yytext(), yyline, yycolumn); }

/* Operadores lógicos */
"&" { return symbol(TokenType.OP_AND, yytext(), yyline, yycolumn); }
"|" { return symbol(TokenType.OP_OR, yytext(), yyline, yycolumn); }
"!" { return symbol(TokenType.OP_NOT, yytext(), yyline, yycolumn); }

/* Operador de asignación */
"=" { return symbol(TokenType.OP_ASIGN, yytext(), yyline, yycolumn); }

/* Operadores de agrupación o delimitadores parentesis */
"(" { return symbol(TokenType.L_PARENTESIS, yytext(), yyline, yycolumn); }
")" { return symbol(TokenType.R_PARENTESIS, yytext(), yyline, yycolumn); }
"{" { return symbol(TokenType.L_LLAVE, yytext(), yyline, yycolumn); }
"}" { return symbol(TokenType.R_LLAVE, yytext(), yyline, yycolumn); }
"[" { return symbol(TokenType.L_CORCHETE, yytext(), yyline, yycolumn); }
"]" { return symbol(TokenType.R_CORCHETE, yytext(), yyline, yycolumn); }

/* Comentarios o espacios en blanco */
{Comentario}|{EspacioEnBlanco} { /*Ignorar*/ }
/*Nota: todo elemento dentro de comillas dobles o simples se toma como comentario y se ignora */
/* Signos de puntuación */
"," { return symbol(TokenType.COMA, yytext(), yyline, yycolumn); }
";" { return symbol(TokenType.PUNTO_COMA, yytext(), yyline, yycolumn); }
"." { return symbol(TokenType.PUNTO, yytext(), yyline, yycolumn); }
":" { return symbol(TokenType.DOS_PUNTOS, yytext(), yyline, yycolumn); }

/* Literales */
{NumeroEntero} { return symbol(TokenType.LIT_INT, Integer.valueOf(yytext()), yyline, yycolumn); }
{NumeroFlotante} { return symbol(TokenType.LIT_FLOAT, yytext(), yyline, yycolumn); }
{String} { return symbol(TokenType.LIT_STRING, yytext(), yyline, yycolumn); }

/* null, true, false definidos en palabras reservadas también cuentan como literales */

/* Errores */
0 {Numero}+ {
          System.err.println("Error léxico en la línea " + yyline + ", columna " + yycolumn + ": los números no deben empezar por 0 (cero): " + yytext());
          return symbol(TokenType.ERROR, yytext(), yyline, yycolumn);
      }

. {
          System.err.println("Error léxico en la línea " + yyline + ", columna " + yycolumn + ": Carácter inesperado: " + yytext());
          return symbol(TokenType.ERROR, yytext(), yyline, yycolumn);
      }

{Numero}+{Identificador} {
          System.err.println("Error léxico en la línea " + yyline + ", columna " + yycolumn + ": Los identificadores (nombres de variables y funciones) no deben empezar por números: " + yytext());
          return symbol(TokenType.ERROR, yytext(), yyline, yycolumn);
      }


