package Data;
import java.util.List;
import java.util.ArrayList;

import java_cup.runtime.*;

import Token.*;

%%
%public
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

    private boolean isToken(Object type){
        return type instanceof TokenType;
    }
    private Symbol symbol(Object type){
        return symbol(type, "");
    }
    private Symbol symbol(Object type, Object value){
        int n = (isToken(type)) ? ((TokenType)type).ordinal() : (int) type;
        Token token = new Token(TokenType.getType(n), value.toString(), yyline, yycolumn);
        tokens.add(token);
        if (n == 1) errors.add(token);
        return new Symbol(n, value);
    }
%}
/*Fin de archivo, importante a futuro para el analizador sintactico + semantica*/
%eofval{
return symbol(TokenType.EOF);
%eofval}
/* Variables básicas de comentarios y espacios */
TerminadorDeLinea = \r|\n|\r\n
EntradaDeCaracter = [^\r\n]
EspacioEnBlanco = {TerminadorDeLinea} | [ \t\f]
ComentarioTradicional = "#" {EntradaDeCaracter}*
FinDeLineaComentario = "#" {EntradaDeCaracter}* {TerminadorDeLinea}?
ComentarioDeDocumentacion = "\\\"\\\"\\\"(\\\\.|[^\\\\])*\\\"\\\"\\\"" | "\\'\\'\\'(\\\\.|[^\\\\])*\\'\\'\\'" | "//" [^\n]*

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
//Char = \'(\\.|[^\'\\])\'
%%
/* Palabras reservadas */
"if" { return symbol(TokenType.IF, yytext()); }
"else" { return symbol(TokenType.ELSE, yytext()); }
"elif" { return symbol(TokenType.ELIF, yytext()); }
"while" { return symbol(TokenType.WHILE, yytext()); }
"for" { return symbol(TokenType.FOR, yytext()); }
"return" { return symbol(TokenType.RETURN, yytext()); }
"break" { return symbol(TokenType.BREAK, yytext()); }
"continue" { return symbol(TokenType.CONTINUE, yytext()); }
"fn" { return symbol(TokenType.FN, yytext()); }
"boolean" { return symbol(TokenType.BOOLEAN, yytext()); }
"int" { return symbol(TokenType.INT, yytext()); }
"float" { return symbol(TokenType.FLOAT, yytext()); }
"string" { return symbol(TokenType.STRING, yytext()); }
"true" { return symbol(TokenType.TRUE, yytext()); }
"false" { return symbol(TokenType.FALSE, yytext()); }
"null" { return symbol(TokenType.NULL, yytext()); }
"print" { return symbol(TokenType.PRINT, yytext()); }
/* Extra: Palabras reservadas, para implementaciones estadisticas (Problema del proyecto)*/
"mean" { return symbol(TokenType.MEAN, yytext()); }
"max" { return symbol(TokenType.MAX, yytext()); }
"min" { return symbol(TokenType.MIN, yytext()); }
"median" { return symbol(TokenType.MEDIAN, yytext()); }
"mode" { return symbol(TokenType.MODE, yytext()); }
"getChar" { return symbol(TokenType.GETCHAR, yytext()); } //getChar("hola", 2) -> un string de el elemento en la posición 2
"getCharFromAscii" { return symbol(TokenType.GETCHARFROMASCII, yytext()); } //getCharFromAscii(97) -> un string de el elemento dado su codigo ascii
"getAscii" { return symbol(TokenType.GETASCII, yytext()); } // getAscii("a") -> un int de el codigo ascii de el caracter
"len" { return symbol(TokenType.LEN, yytext()); } // len("hola") -> un int de la longitud de un string o un arreglo

/* Identificador */
{Identificador} { return symbol(TokenType.IDENTIFICADOR, yytext()); }

/* Operadores aritméticos */
"+" { return symbol(TokenType.OP_SUMA, yytext()); }
"-" { return symbol(TokenType.OP_RESTA, yytext()); }
"*" { return symbol(TokenType.OP_MULT, yytext()); }
"/" { return symbol(TokenType.OP_DIV, yytext()); }
"%" { return symbol(TokenType.OP_MOD, yytext()); }
"^" { return symbol(TokenType.OP_POT, yytext()); }
/* Operadores relacionales */
"<" { return symbol(TokenType.OP_MENOR, yytext()); }
"<=" { return symbol(TokenType.OP_MENOR_IGUAL, yytext()); }
">" { return symbol(TokenType.OP_MAYOR, yytext()); }
">=" { return symbol(TokenType.OP_MAYOR_IGUAL, yytext()); }
"==" { return symbol(TokenType.OP_IGUAL, yytext()); }
"!=" { return symbol(TokenType.OP_DIFERENTE, yytext()); }

/* Operadores lógicos */
"&&" { return symbol(TokenType.OP_AND, yytext()); }
"||" { return symbol(TokenType.OP_OR, yytext()); }
"!" { return symbol(TokenType.OP_NOT, yytext()); }

/* Operador de asignación */
"=" { return symbol(TokenType.OP_ASIGN, yytext()); }

/* Operadores de agrupación o delimitadores parentesis */
"(" { return symbol(TokenType.L_PARENTESIS, yytext()); }
")" { return symbol(TokenType.R_PARENTESIS, yytext()); }
"{" { return symbol(TokenType.L_LLAVE, yytext()); }
"}" { return symbol(TokenType.R_LLAVE, yytext()); }
"[" { return symbol(TokenType.L_CORCHETE, yytext()); }
"]" { return symbol(TokenType.R_CORCHETE, yytext()); }

/* Comentarios o espacios en blanco */
{Comentario}|{EspacioEnBlanco} { /*Ignorar*/ }
/*Nota: todo elemento dentro de comillas dobles o simples se toma como comentario y se ignora */
/* Signos de puntuación */
"," { return symbol(TokenType.COMA, yytext()); }
";" { return symbol(TokenType.PUNTO_COMA, yytext()); }
"." { return symbol(TokenType.PUNTO, yytext()); }
":" { return symbol(TokenType.DOS_PUNTOS, yytext()); }

/* Literales */
{NumeroEntero} { return symbol(TokenType.LIT_INT, Integer.valueOf(yytext())); }
{NumeroFlotante} { return symbol(TokenType.LIT_FLOAT, Float.valueOf( yytext()) ); }
{String} { return symbol(TokenType.LIT_STRING, yytext()); }

/* null, true, false definidos en palabras reservadas también cuentan como literales */

/* Errores */
0 {Numero}+ {
          System.err.println("Error léxico en la línea " + yyline + ", columna " + yycolumn + ": los números no deben empezar por 0 (cero): " + yytext());
          return symbol(TokenType.ERROR, yytext());
      }

. {
          System.err.println("Error léxico en la línea " + yyline + ", columna " + yycolumn + ": Carácter inesperado: " + yytext());
          return symbol(TokenType.ERROR, yytext());
      }

{Numero}+{Identificador} {
          System.err.println("Error léxico en la línea " + yyline + ", columna " + yycolumn + ": Los identificadores (nombres de variables y funciones) no deben empezar por números: " + yytext());
          return symbol(TokenType.ERROR, yytext());
      }
