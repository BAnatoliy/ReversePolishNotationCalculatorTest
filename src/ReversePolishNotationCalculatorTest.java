import java.util.ArrayDeque;
import java.util.Deque;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReversePolishNotationCalculatorTest {
    ReversePolishNotationCalculator calc;
    String expression = "";
    @BeforeEach
    public void setCalculator() {
        calc = new ReversePolishNotationCalculator();
    }

    @Test
    public void shouldCalculateAddition() {
        expression = "4 5 +";

        int expect = 9;
        int result = calc.calculatePolishNotation(expression);

        Assertions.assertEquals(expect, result);
    }

    @Test
    public void shouldCalculateSubtraction() {
        expression = "5 10 -";

        int expect = -5;
        int result = calc.calculatePolishNotation(expression);

        Assertions.assertEquals(expect, result);
    }

    @Test
    public void shouldCalculateMultiplication() {
        expression = "7 3 *";

        int expect = 21;
        int result = calc.calculatePolishNotation(expression);

        Assertions.assertEquals(expect, result);
    }

    @Test
    public void shouldCalculateMultiplicationSubtractionAndAddition() {
        expression = "17 3 8 2 * + -";

        int expect = -2;
        int result = calc.calculatePolishNotation(expression);

        Assertions.assertEquals(expect, result);
    }

    @Test
    public void shouldCalculateWhenExpressionHasRedundantSpace() {
        expression = "7 3 8 2 *    +               -";

        int expect = -12;
        int result = calc.calculatePolishNotation(expression);

        Assertions.assertEquals(expect, result);
    }

    @Test
    public void shouldCalculateWhenExpressionHasNotSpaceBetweenOperation() {
        expression = "7 3 8 2 *+-";

        NumberFormatException exception = Assertions.assertThrows(NumberFormatException.class, () -> {
                    calc.calculatePolishNotation(expression);
                }
        );
        Assertions.assertEquals("For input string: \"*+-\"", exception.getMessage());
    }

}

class ReversePolishNotationCalculator {

    public int calculatePolishNotation(String str) {
        String[] parts = str.split(" ");
        Deque<Integer> numbers = new ArrayDeque<>();
        int index = 0;

        while (index != parts.length) {

            if (parts[index].isBlank()) {
                index++;
                continue;
            }

            if (isOperation(parts[index])) {
                int operandOne = numbers.pop();
                int operandTwo = numbers.pop();

                if (parts[index].equals("+")) {
                    numbers.push(operandOne + operandTwo);
                } else if (parts[index].equals("-")) {
                    numbers.push(operandTwo - operandOne);
                } else if (parts[index].equals("*")) {
                    numbers.push(operandOne * operandTwo);
                }

            } else {
                numbers.push(Integer.parseInt(parts[index]));
            }

            index++;
        }

        return numbers.pop();
    }

    private boolean isOperation(String part) {
        if (part.equals("+")
                || part.equals("-")
                || part.equals("*")) {
            return true;
        }

        return false;
    }
} 