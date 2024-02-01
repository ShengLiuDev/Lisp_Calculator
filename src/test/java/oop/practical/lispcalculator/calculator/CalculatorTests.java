package oop.practical.lispcalculator.calculator;

import oop.practical.lispcalculator.lisp.Lisp;
import oop.practical.lispcalculator.lisp.ParseException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.function.ThrowingSupplier;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.time.chrono.ThaiBuddhistChronology;
import java.util.stream.Stream;

public class CalculatorTests {

    @ParameterizedTest
    @MethodSource
    void testNumber(String test, String input, BigDecimal expected) {
        test(input, expected);
    }

    public static Stream<Arguments> testNumber() {
        return Stream.of(
                Arguments.of("Integer", "1", new BigDecimal("1")),
                Arguments.of("Decimal", "1.0", new BigDecimal("1.0"))
        );
    }

    @ParameterizedTest
    @MethodSource
    void testIdentifier(String test, String input, BigDecimal expected) {
        test(input, expected);
    }

    public static Stream<Arguments> testIdentifier() {
        return Stream.of(
                Arguments.of("E", "e", BigDecimal.valueOf(Math.E)),
                Arguments.of("PI", "pi", BigDecimal.valueOf(Math.PI)),
                // An expected value of null means a CalculateException is thrown
                Arguments.of("Undefined", "undefined", null)
        );
    }

    @ParameterizedTest
    @MethodSource
    void testAdd(String test, String input, BigDecimal expected) {
        test(input, expected);
    }

    public static Stream<Arguments> testAdd() {
        return Stream.of(
                Arguments.of("Empty", "(add)", new BigDecimal("0")),
                Arguments.of("Single", "(add 1)", new BigDecimal("1")),
                Arguments.of("Multiple", "(add 1 2 3)", new BigDecimal("6")),
                Arguments.of("Symbol", "(+)", new BigDecimal("0"))
        );
    }

    @ParameterizedTest
    @MethodSource
    void testSub(String test, String input, BigDecimal expected) {
        test(input, expected);
    }

    public static Stream<Arguments> testSub() {

        return Stream.of(
                Arguments.of("Empty", "(sub)", null),
                Arguments.of("Single", "(sub 1)", new BigDecimal("-1")),
                Arguments.of("Multiple", "(sub 2 6 8)", new BigDecimal("-12")),
                Arguments.of("Symbol num", "(- 13)", new BigDecimal("-13"))
        ); //TODO: Implement
    }

    @ParameterizedTest
    @MethodSource
    void testMul(String test, String input, BigDecimal expected) {
        test(input, expected);
    }

    public static Stream<Arguments> testMul() {
        return Stream.of(
                Arguments.of("Empty", "(mul)", new BigDecimal("1")),
                Arguments.of("Single", "(mul 3)", new BigDecimal("3")),
                Arguments.of("Multiple", "(mul 5 5 5)", new BigDecimal("125")),
                Arguments.of("Multiple", "(mul 2.5 2.5 2.5)", new BigDecimal("15.625")),
                Arguments.of("Zero", "(mul 2 2 0)", new BigDecimal("0")),
                Arguments.of("Symbol", "(*)", new BigDecimal("1"))
        ); //TODO: Implement
    }

    @ParameterizedTest
    @MethodSource
    void testDiv(String test, String input, BigDecimal expected) {
        test(input, expected);
    }

    public static Stream<Arguments> testDiv() {
        return Stream.of(
                Arguments.of("Empty", "(div)", null),
                Arguments.of("Single", "(div 2)", new BigDecimal("0")),
                Arguments.of("Single", "(div 4)", new BigDecimal("0")),
                Arguments.of("Multiple", "(div 1.0 2.0 3.0)", new BigDecimal("0.2")),
                Arguments.of("Symbol num", "(/ 2.0)", new BigDecimal("0.5")),
                Arguments.of("Symbol num", "(/ 4.0)", new BigDecimal("0.2")),
                Arguments.of("Zero", "(div 2.0 0)", null),
                Arguments.of("Multiple Zero", "(div 3 2 0)", null),
                Arguments.of("Multiple", "(div 100.00 2 3)", new BigDecimal("16.67"))
        ); //TODO: Implement
    }

    // TODO: Define and implement tests for the other functions.
    @ParameterizedTest
    @MethodSource
    void testPow(String test, String input, BigDecimal expected) {
        test(input, expected);
    }

    public static Stream<Arguments> testPow() {
        return Stream.of(
                Arguments.of("Empty", "(pow)", null),
                Arguments.of("Valid", "(pow 3 2)", new BigDecimal("9")),
                Arguments.of("Large-num", "(pow 3 10000000000000000)", null),
                Arguments.of("Large-num", "(pow 10000000000000000 2)", null),
                Arguments.of("Multiple", "(pow 1 2 3)", null),
                Arguments.of("Not an int32", "(pow 16 3.5)", null),
                Arguments.of("Not an int32", "(pow 3.5 3.5)", null),
                Arguments.of("Negative", "(pow 4 -1)", null),
                Arguments.of("Negative", "(pow -4 1)", "-4")
        );
    }

    @ParameterizedTest
    @MethodSource
    void testSqrt(String test, String input, BigDecimal expected){
        test(input, expected);
    }

    public static Stream<Arguments> testSqrt(){
        return Stream.of(
                Arguments.of("Empty", "(sqrt)", null),
                Arguments.of("Valid", "(sqrt 1)", new BigDecimal("1")),
                Arguments.of("Valid", "(sqrt 9)", new BigDecimal("3")),
                Arguments.of("Floating zero", "(sqrt 16.0)", new BigDecimal("4")),
                Arguments.of("Negative", "(sqrt -9)", null),
                Arguments.of("Multiple", "(sqrt 1 4)", null),
                Arguments.of("Zero", "(sqrt 0)", new BigDecimal("0")),
                Arguments.of("Fraction", "(sqrt 0.25)", new BigDecimal("0.5")),
                Arguments.of("Decimal", "(sqrt 2.000)", new BigDecimal("1.414"))
        );
    }

    @ParameterizedTest
    @MethodSource
    void testRem(String test, String input, BigDecimal expected){
        test(input, expected);
    }

    public static Stream<Arguments> testRem()
    {
        return Stream.of(
                Arguments.of("Empty", "(rem)", null),
                Arguments.of("Single", "(rem 1)", null),
                Arguments.of("Valid", "(rem 10 3)", new BigDecimal("1")),
                Arguments.of("Valid", "(rem 7 2)", new BigDecimal("1")),
                Arguments.of("Negative num", "(rem -7 2)", new BigDecimal("-1")),
                Arguments.of("Negative divisor", "(rem 7 -2)", new BigDecimal("1")),
                Arguments.of("Single", "(rem 2.0)", null)
        );
    }

    @ParameterizedTest
    @MethodSource
    void testMod(String test, String input, BigDecimal expected){
        test(input, expected);
    }

    public static Stream<Arguments> testMod(){
        return Stream.of(
                Arguments.of("Empty", "(mod)", null),
                Arguments.of("Single", "(mod 1)", null),
                Arguments.of("Valid", "(mod -7 5)", new BigDecimal("3")),
                Arguments.of("Valid", "(mod 7 5)", new BigDecimal("2")),
                Arguments.of("Negative num", "(mod -7 2)", new BigDecimal("1")),
                Arguments.of("Single", "(mod 4.0)", null)
        );
    }

    @ParameterizedTest
    @MethodSource
    void testSin(String test, String input, BigDecimal expected){
        test(input, expected);
    }

    public static Stream<Arguments> testSin(){
        return Stream.of(
                Arguments.of("Empty", "(sin)", null),
                Arguments.of("Valid", "(sin 0)", new BigDecimal("0.0")),
                Arguments.of("Valid", "(sin 1)", new BigDecimal("0.8414709848078965")),
                Arguments.of("Invalid", "(sin 1 1)", null),
                Arguments.of("PI Divide", "(sin (div pi 2))", new BigDecimal("1.0")),
                Arguments.of("Negative PI Divide", "(sin (div -pi 2))", new BigDecimal("1.0")),
                Arguments.of("PI Multiply", "(sin (mul 2 pi))", new BigDecimal("-2.4492935982947064E-16")),
                Arguments.of("PI Multiply Large", "(sin (mul 10 pi))", new BigDecimal("-1.2246467991473533E-15")),
                Arguments.of("Large-num", "(sin 10000000000000000)", null)
        );
    }

    @ParameterizedTest
    @MethodSource
    void testCos(String test, String input, BigDecimal expected){
        test(input, expected);
    }

    public static Stream<Arguments> testCos(){
        return Stream.of(
                Arguments.of("Empty", "(cos)", null),
                Arguments.of("Valid", "(cos 0)", new BigDecimal("1.0")),
                Arguments.of("Valid", "(cos 1)", new BigDecimal("0.5403023058681398")),
                Arguments.of("Invalid", "(cos 1 1)", null),
                Arguments.of("Large-num", "(cos 10000000000000000)", null),
                Arguments.of("PI", "(cos pi)", new BigDecimal("-1.0")),
                Arguments.of("PI Multiple 2", "(cos (mul 2 pi))", new BigDecimal("1.0")),
                Arguments.of("PI Multiple 3", "(cos (mul 3 pi))", new BigDecimal("-1.0"))
        );

    }

    private static void test(String input, BigDecimal expected) {
        if (expected != null) {
            var result = Assertions.assertDoesNotThrow(() -> new Calculator().visit(Lisp.parse(input)));
            Assertions.assertEquals(expected, result);
        } else {
            Assertions.assertThrows(CalculateException.class, () -> new Calculator().visit(Lisp.parse(input)));
        }
    }

}
