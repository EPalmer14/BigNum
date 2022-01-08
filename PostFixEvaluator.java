import java.util.Stack;
import java.util.StringTokenizer;

public class PostFixEvaluator {

    BigNum op2;
    BigNum op1;
    String symbol;
    Stack<BigNum> stack;
    StringTokenizer st;

    // Construct a PostFixEvaluator from String s.
    // Assume s is a legitimate postfix expression.
    public PostFixEvaluator(String s) {
        stack = new Stack();
        st = new StringTokenizer(s, " ");
        while (st.hasMoreTokens()) {
            symbol = st.nextToken();
            if (symbol.equals("+") || symbol.equals("-") || symbol.equals("*")) {
                op2 = stack.pop();
                op1 = stack.pop();
                if (symbol.equals("+")) {
                    BigNum eval = op1.add(op2);
                    stack.push(eval);
                }
                if (symbol.equals("-")) {
                    BigNum eval = op1.subtract(op2);
                    stack.push(eval);
                }
                if (symbol.equals("*")) {
                    BigNum eval = op1.multiply(op2);
                    stack.push(eval);
                }
            } else {
                BigNum number = new BigNum(symbol);
                stack.push(number);
            }
        }
    }

    // Return the evaluation of this postfix expression
    public BigNum evaluate() {
        BigNum num = this.stack.pop();
        return num;
    }

    // Use this to test your PostFixEvaluator methods
    public static void main(String[] args) {
        PostFixEvaluator pfe = new PostFixEvaluator("1234 4321 + 1000000 40000 - *");
        System.out.println(pfe.evaluate());
    }
}

