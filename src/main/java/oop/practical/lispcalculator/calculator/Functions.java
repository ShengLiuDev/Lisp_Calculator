package oop.practical.lispcalculator.calculator;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.net.Inet4Address;
import java.util.List;

final class Functions {

    static BigDecimal add(List<BigDecimal> arguments)
    {
        var result = BigDecimal.ZERO;
        for (var number : arguments) {
            result = result.add(number);
        }
        return result;
    }

    //TODO: Implement sub, mul, and div
    static BigDecimal sub(List<BigDecimal> arguments) throws CalculateException
    {
        if (arguments.isEmpty())
        {
            throw new CalculateException("no values to sub");
        }

        var result = arguments.get(0);
        // if there is only one argument, return negation (add negative sign)
        if(arguments.size() == 1)
        {
            return result.negate();
        }

        // else not needed here but I prefer it
        else
        {
            for (int i = 1; i < arguments.size(); i++)
            {
                result = result.subtract(arguments.get(i));
            }
        }
        return result;
    }

    static BigDecimal mul(List<BigDecimal> arguments)
    {
        if (arguments.isEmpty())
        {
            return BigDecimal.ONE;
        }
        if (arguments.size() == 1)
        {
            return arguments.get(0);
        }

        var result = BigDecimal.ONE;

        for (var number : arguments)
        {
            result = result.multiply(number);
        }
        return result;
    }

    // throw new UnsupportedOperationException(); //TODO: Implement
    static BigDecimal div(List<BigDecimal> arguments) throws CalculateException
    {
        if (arguments.isEmpty())
        {
            throw new CalculateException("no arguments to divide");
        }

        try
        {
            if (arguments.size() == 1) {
                var result = arguments.get(0);
                return BigDecimal.ONE.divide(result, result.scale(), RoundingMode.HALF_EVEN);
            }
            var result = arguments.get(0);


            result = result.setScale(arguments.get(0).scale(), RoundingMode.HALF_EVEN);
            for (int i = 1; i < arguments.size(); i++)
            {
                var divisor = arguments.get(i);
                if (divisor.compareTo(BigDecimal.ZERO) == 0)
                {
                    throw new CalculateException("cannot divide by zero");
                }
                divisor = divisor.setScale(arguments.get(0).scale(), RoundingMode.HALF_EVEN);
                result = result.divide(divisor, RoundingMode.HALF_EVEN);
            }

            return result;
        }
        catch (ArithmeticException e)
        {
            throw new CalculateException("invalid arguments");
        }

    }

    // go watch lecture video on how to implement pow
    static BigDecimal pow(List<BigDecimal> arguments) throws CalculateException
    {
        if (arguments.size() != 2)
        {
            throw new CalculateException("incorrect number of arguments");
        }

        try
        {
            BigDecimal base = arguments.get(0);
            BigDecimal exponent = arguments.get(1);
            if(base.abs().compareTo(BigDecimal.valueOf(Integer.MAX_VALUE)) > 0)
            {
                throw new CalculateException("base is too large");
            }
            return base.pow(exponent.intValueExact());
        }
        catch (ArithmeticException e)
        {
            throw new CalculateException("invalid arguments");
        }

    }

    static BigDecimal sqrt(List<BigDecimal> arguments) throws CalculateException
    {
        if (arguments.size() != 1)
        {
            throw new CalculateException("incorrect number of arguments");
        }
        var result = arguments.get(0);

        if (result.compareTo(BigDecimal.ZERO) == 0)
        {
            return BigDecimal.ZERO;
        }
        if (result.compareTo(BigDecimal.ZERO) < 0)
        {
            throw new CalculateException("the argument is negative");
        }
        if (result.stripTrailingZeros().scale() <= 0)
        {
            return result.sqrt(MathContext.DECIMAL32).setScale(result.scale(), RoundingMode.HALF_EVEN);
        }
        // reference: https://docs.oracle.com/javase/8/docs/api/java/math/MathContext.html
        // options: DECIMAL32, DECIMAL64, UNLIMITED
        BigDecimal ans = result.sqrt(MathContext.DECIMAL32);
        BigDecimal roundedAns = ans.setScale(result.scale(), RoundingMode.HALF_EVEN);

        return roundedAns.stripTrailingZeros();
    }
    static BigDecimal rem(List<BigDecimal> arguments) throws CalculateException
    {
        if (arguments.size() != 2)
        {
            throw new CalculateException("incorrect number of arguments");
        }
        var num = arguments.get(0);
        var divisor = arguments.get(1);
        if (divisor.compareTo(BigDecimal.ZERO) == 0)
        {
            throw new CalculateException("divisor is 0");
        }
        var result = num.remainder(divisor);
        return result;
    }

    static BigDecimal mod(List<BigDecimal> arguments) throws CalculateException
    {
        if (arguments.size() != 2)
        {
            throw new CalculateException("incorrect number of arguments");
        }
        var num = arguments.get(0);
        var divisor = arguments.get(1);
        if (divisor.compareTo(BigDecimal.ZERO) == 0)
        {
            throw new CalculateException("divisor is 0");
        }

        // reference: https://stackoverflow.com/questions/5385024/mod-in-java-produces-negative-numbers
        var result = num.remainder(divisor);
        //System.out.println(result);
        //System.out.println(result.signum());
        if (result.signum() < 0)
        {
            result = result.add(divisor);
        }

        return result.abs();
    }

    static BigDecimal sin(List<BigDecimal> arguments) throws CalculateException
    {
        if (arguments.size() != 1)
        {
            throw new CalculateException("incorrect number of arguments");
        }


        try
        {
            if (arguments.get(0).compareTo(BigDecimal.valueOf(2147483647)) == 1)
            {
                arguments.get(0).intValueExact();
            }
            double num = arguments.get(0).doubleValue();
            double result = Math.sin(num);
            return BigDecimal.valueOf(result);
        }
        catch(ArithmeticException e)
        {
            throw new CalculateException("invalid arguments");
        }

    }

    static BigDecimal cos(List<BigDecimal> arguments) throws CalculateException
    {
        if (arguments.size() != 1)
        {
            throw new CalculateException("incorrect number of arguments");
        }


        try
        {
            if (arguments.get(0).compareTo(BigDecimal.valueOf(2147483647)) == 1)
            {
                arguments.get(0).intValueExact();
            }
            double num = arguments.get(0).doubleValue();
            double result = Math.cos(num);
            return BigDecimal.valueOf(result);
        }
        catch(ArithmeticException e)
        {
            throw new CalculateException("invalid arguments");
        }
    }

}
