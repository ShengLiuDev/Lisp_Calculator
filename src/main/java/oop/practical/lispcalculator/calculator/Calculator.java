package oop.practical.lispcalculator.calculator;

import oop.practical.lispcalculator.lisp.Ast;

import java.math.BigDecimal;
import java.nio.charset.UnsupportedCharsetException;
import java.util.ArrayList;
import java.util.Arrays;

public final class Calculator {

    public BigDecimal visit(Ast ast) throws CalculateException {
        return switch (ast) {
            case Ast.Number number -> visit(number);
            case Ast.Variable variable -> visit(variable);
            case Ast.Function function -> visit(function);
        };
    }

    private BigDecimal visit(Ast.Number ast) {
        //System.out.println("this is number ast..............." + ast.toString());
        return ast.value();
    }

    // TODO pi and e values
    private BigDecimal visit(Ast.Variable ast) throws CalculateException{
        var e = BigDecimal.valueOf(Math.E);
        var pi = BigDecimal.valueOf(Math.PI);
        //System.out.println("this is the ast.................." + ast);
        if (ast.name().equals("e") || ast.name().equals("E"))
        {
            return e;
        }

        if (ast.name().contains("pi") || ast.name().contains("PI"))
        {

            if (ast.name().contains("/"))
            {
                var splitted = ast.name().split("/");
                var divisor = splitted[1];
                return pi.divide(BigDecimal.valueOf(Integer.parseInt(divisor)));
            }
            else if (ast.name().matches("[0-9]+.*P[0-9]*"))
            {
                var splitted = ast.name().split("P");
                var coefficient = new BigDecimal(splitted[0]);
                return pi.multiply(coefficient);
            }
            else {
                return pi;

            }
        }
        else {
            //System.out.println("entering the else statement ..................");
            throw new CalculateException(null);
        }
    }



    private BigDecimal visit(Ast.Function ast) throws CalculateException {
        var arguments = new ArrayList<BigDecimal>();
        for (Ast argument : ast.arguments())
        {
            arguments.add(visit(argument));
        }
        return switch (ast.name()) {
            case "+", "add" -> Functions.add(arguments);
            case "-", "sub" -> Functions.sub(arguments);
            case "*", "mul" -> Functions.mul(arguments);
            case "/", "div" -> Functions.div(arguments);
            case "pow" -> Functions.pow(arguments);
            case "sqrt" -> Functions.sqrt(arguments);
            case "rem" -> Functions.rem(arguments);
            case "mod" -> Functions.mod(arguments);
            case "sin" -> Functions.sin(arguments);
            case "cos" -> Functions.cos(arguments);
            //TODO: Define remaining functions
            default -> throw new CalculateException("Unknown function " + ast.name() + ".");
        };
    }

}
