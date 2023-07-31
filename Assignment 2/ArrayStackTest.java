public class ArrayStackTest
{
    public static void main(String[] args) {
        String postfix = "ab*ca-/de*+";
        Integer variables = 5;
        System.out.println("Postfix expression: " + postfix);
        System.out.println("Variable values: a = 2, b = 3, c = 4, d = 5, e = 6");
        System.out.println("Postfix evaluation: " + ResizeableArrayStack.evaluatePostfix(postfix, variables));
    }
}
