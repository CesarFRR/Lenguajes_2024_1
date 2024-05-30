package T2.ParserTests;

import java.io.IOException;

import utils.utils;

public class GenerateParserJavaFile {


    public static void main(String[] args) throws IOException {

        boolean genLexer = true;
        String[] target = {"test/T2/ParserTests"};

        utils.adjustCurrentDir();
        if (genLexer) {
            if (!utils.fileExists("test/T2/ParserTests", "Lexer.java")) {
                System.out.println("\nNo existe Lexer.java, generando...\n");
                utils.generateFlexJavaFile(target[0], utils.isWindows());
            }
        }


        utils.generateCupJavaFile(target[0], utils.isWindows());
    }

}
