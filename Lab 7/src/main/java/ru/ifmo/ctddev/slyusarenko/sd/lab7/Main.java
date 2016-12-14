package ru.ifmo.ctddev.slyusarenko.sd.lab7;

import ru.ifmo.ctddev.slyusarenko.sd.lab7.parser.ParseException;
import ru.ifmo.ctddev.slyusarenko.sd.lab7.parser.Tokenizer;
import ru.ifmo.ctddev.slyusarenko.sd.lab7.token.Token;
import ru.ifmo.ctddev.slyusarenko.sd.lab7.visitor.CalcVisitor;
import ru.ifmo.ctddev.slyusarenko.sd.lab7.visitor.ParserVisitor;
import ru.ifmo.ctddev.slyusarenko.sd.lab7.visitor.PrintVisitor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Maxim Slyusarenko
 * @since 14.12.16
 */
public class Main {

    public static void main(String[] args) {
        Tokenizer tokenizer = new Tokenizer();
        List<Token> tokens;
        try {
            tokens = tokenizer.parse(System.in);
            ParserVisitor parserVisitor = new ParserVisitor(tokens);
            List<Token> polishTokens = parserVisitor.parse();
            PrintVisitor printVisitor = new PrintVisitor(polishTokens);
            System.out.print("Polish notation: ");
            printVisitor.solve().flush();
            System.out.println();
            CalcVisitor calcVisitor = new CalcVisitor(polishTokens);
            System.out.println("Result of evaluation: " + calcVisitor.evaluate());
        } catch (IOException e) {
            e.printStackTrace();
            return;
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            return;
        }
    }
}
