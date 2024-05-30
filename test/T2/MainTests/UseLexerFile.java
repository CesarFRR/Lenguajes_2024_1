package T2.MainTests;

import java_cup.runtime.Symbol;
import java.io.*;
import java.util.Objects;

import T2.MainTests.Data.*;

public class UseLexerFile {
    public static void main(String[] args) throws IOException {
        System.out.println("probando el lexer:");
        Lexer lexer = new Lexer(new StringReader("1+2"));

        while (true) {
            Symbol token = lexer.next_token();
            assert token != null;
            if (token == null | Objects.equals(token.toString(), "#0")) {
                break;
            }

            System.out.println(token.sym + " " + token.value + " " + token.toString());
        }
    }
}
