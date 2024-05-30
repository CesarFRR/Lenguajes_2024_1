package T2.MainTests.Data;
import java_cup.runtime.*;
%%
%public
%class Lexer
%cup
%line
%column
%{
                StringBuffer string = new StringBuffer();



                private Symbol symbol(int type){
                    return new Symbol(type, yyline, yycolumn);
                }
                private Symbol symbol(int type, Object value){
                    return new Symbol(type, yyline, yycolumn, value);
                }

                private Symbol symbol(int type, Object value, int line, int column){
                    return new Symbol(type, yyline, yycolumn, value);
                }
%}
/*Fin de archivo, importante a futuro para el analizador sintactico + semantica*/
%eofval{
return symbol(ParserSym.EOF);
%eofval}

/* Variables básicas de comentarios y espacios */
TerminadorDeLinea = \r|\n|\r\n
EntradaDeCaracter = [^\r\n]
EspacioEnBlanco = {TerminadorDeLinea} | [ \t\f]

/* Identificador */
Digito = [0-9]
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
/* Operadores aritméticos */
"+" { return symbol(ParserSym.OP_SUMA, yytext(), yyline, yycolumn); }
"*" { return symbol(ParserSym.OP_MULT, yytext(), yyline, yycolumn); }

/* Operadores relacionales */
/* Comentarios o espacios en blanco */
{EspacioEnBlanco} { /*Ignorar*/ }


/* Literales */
{NumeroEntero} { return symbol(ParserSym.LIT_INT, Integer.valueOf(yytext()), yyline, yycolumn); }


. {
          System.err.println("Error léxico en la línea " + yyline + ", columna " + yycolumn + ": Carácter inesperado: " + yytext());
          return symbol(ParserSym.error, yytext(), yyline, yycolumn);
      }




