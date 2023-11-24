import java.util.Stack;

public class Math {
    private final Stack<Double> operands;
    private final Stack<String> operators;

    Math() {
        this.operands = new Stack<>();
        this.operators = new Stack<>();
    }

    public double evaluate(String expression) {
        char[] tokens = expression.replaceAll("\\s", "").toCharArray();

        for (int i = 0; i < tokens.length; i++) {
            if (Character.isDigit(tokens[i])) {
                i = processNumber(tokens, i);
            } else if (tokens[i] == '(') {
                operators.push(Character.toString(tokens[i]));
            } else if (tokens[i] == ')') {
                processClosingParenthesis();
            } else if (isOperator(tokens[i])) {
                processOperator(Character.toString(tokens[i]));
            }
        }

        processRemainingOperators();

        if (operands.size() == 1) {
            Double result = operands.pop();
            return result;
        } else {
            throw new IllegalArgumentException("Invalid expression: " + expression);
        }
    }

    private int processNumber(char[] tokens, int startIndex) {
        StringBuilder sb = new StringBuilder();
        while (startIndex < tokens.length && (Character.isDigit(tokens[startIndex]) || tokens[startIndex] == '.')) {
            sb.append(tokens[startIndex++]);
        }
        operands.push(Double.parseDouble(sb.toString()));
        return startIndex - 1;
    }

    private void processClosingParenthesis() {
        while (!operators.isEmpty() && !operators.peek().equals("(")) {
            applyOperator();
        }
        operators.pop(); // Pop the opening parenthesis
    }

    private void processOperator(String currentOperator) {
        while (!operators.isEmpty() && hasPrecedence(currentOperator, operators.peek())) {
            applyOperator();
        }
        operators.push(currentOperator);
    }

    private void processRemainingOperators() {
        while (!operators.isEmpty()) {
            applyOperator();
        }
    }

    private boolean isOperator(char c) {
        return c == '+' || c == '-' || c == 'X' || c == '/';
    }

    private boolean hasPrecedence(String op1, String op2) {
        return (op2.equals("X") || op2.equals("/")) && (op1.equals("+") || op1.equals("-"));
    }

    private void applyOperator() {
        String operator = operators.pop();
        double operand2 = operands.pop();
        double operand1 = operands.pop();

        switch (operator) {
            case "+":
                operands.push(operand1 + operand2);
                break;
            case "-":
                operands.push(operand1 - operand2);
                break;
            case "X":
                operands.push(operand1 * operand2);
                break;
            case "/":
                if (operand2 == 0) {
                    throw new ArithmeticException("Cannot divide by zero");
                }
                operands.push(operand1 / operand2);
                break;
            default:
                throw new IllegalArgumentException("Invalid operator: " + operator);
        }
    }
}
