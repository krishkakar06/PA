import java.util.Stack;

/**
 * File: EvaluateReversePolishNotation.java
 * LeetCode 150: Evaluate Reverse Polish Notation
 * 
 * Problem Description:
 * You are given an array of strings tokens that represents an arithmetic expression in a Reverse Polish Notation.
 * Evaluate the expression. Return an integer that represents the value of the expression.
 * 
 * Note that:
 * - The valid operators are '+', '-', '*', and '/'.
 * - Each operand may be an integer or another expression.
 * - The division between two integers always truncates toward zero.
 * - There will not be any division by zero.
 * - The input represents a valid arithmetic expression in a reverse polish notation.
 * - The answer and all intermediate calculations can be represented in a 32-bit integer.
 * 
 * Complexity:
 * - Time Complexity: O(N), where N is the length of tokens array. We traverse tokens once.
 * - Space Complexity: O(N), for the stack storing intermediate operands.
 */
public class EvaluateReversePolishNotation {

    /**
     * Evaluates the value of an arithmetic expression in Reverse Polish Notation.
     *
     * @param tokens array of strings containing operands and operators in RPN
     * @return the evaluated integer result of the expression
     */
    public int evalRPN(String[] tokens) {
        // Stack to store operands and intermediate results
        Stack<Integer> stack = new Stack<>();

        // Process each token sequentially
        for (String token : tokens) {
            switch (token) {
                case "+":
                    // Addition: order does not affect the sum (a + b == b + a)
                    stack.push(stack.pop() + stack.pop());
                    break;

                case "-": {
                    // Subtraction: order matters!
                    // The first popped value is the subtrahend (right operand).
                    // The second popped value is the minuend (left operand).
                    int right = stack.pop();
                    int left = stack.pop();
                    stack.push(left - right);
                    break;
                }

                case "*":
                    // Multiplication: order does not affect the product (a * b == b * a)
                    stack.push(stack.pop() * stack.pop());
                    break;

                case "/": {
                    // Division: order matters!
                    // The first popped value is the divisor (denominator / right operand).
                    // The second popped value is the dividend (numerator / left operand).
                    // Note: Integer division in Java naturally truncates toward zero as required.
                    int divisor = stack.pop();
                    int dividend = stack.pop();
                    stack.push(dividend / divisor);
                    break;
                }

                default:
                    // If the token is not an operator, it is an integer operand.
                    // Parse the string representation into an integer and push onto the stack.
                    stack.push(Integer.parseInt(token));
                    break;
            }
        }

        // The final evaluated result remains at the top of the stack
        return stack.pop();
    }
}