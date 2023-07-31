public class LinkedStackTest
{
    public static void main(String[] args) {
        String infix = "a*b/(c-a)+d*e";
        System.out.println("Infix expression: " + infix);
        System.out.println("Postfix expression: " + LinkedStack.convertToPostfix(infix));
    }
}
