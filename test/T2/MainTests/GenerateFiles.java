package T2.MainTests;
import utils.utils;

import java.io.IOException;

public class GenerateFiles {
    public static void main(String[] args) throws IOException {
        System.out.println("Hello, World!");
        utils.runComand("java -jar lib/jflex-full-1.9.1.jar test/T2/MainTests/Data/Lexer2.flex");
        utils.runComand("java -jar lib/java-cup-11b.jar -destdir test/T2/MainTests/Data test/T2/MainTests/Data/Parser.cup");
        int[][] matrix = new int[3][2];

    }
}
