import java.util.EmptyStackException;

/** A class of stacks whose entries are stored in a chain of nodes. */
public class LinkedStack<T> implements StackInterface<T>
{
    private Node<T> topNode; // refrences the first node in the chain

    public LinkedStack()
    {
        topNode = null;
    } // end defaukt constructor

    public void push(T newEntry)
    {
        topNode = new Node<T>(newEntry, topNode);
    } // end push

    public T pop()
    {
        T top = peek(); // Might throw EmptyStackException
        // Assertion: top Node != null
        topNode = topNode.getNextNode();

        return top;
    } // end pop

    public T peek()
    {
        if(isEmpty())
            throw new EmptyStackException();
        else
            return topNode.getData();
    } // end peek

    public boolean isEmpty()
    {
        return topNode == null;
    } // end isEmpty

    public void clear()
    {
        topNode = null;
    } // end clear

    static int precedence(char ch)
    {
        switch (ch)
        {
            case '+': case '-':
                    return 1;
            case '*': case '/':
                return 2;
            case '^':
                return 3;
        }
        return -1;
    }

    /** Converts an infix expression to an equivalent postfix expression. */
    public static String convertToPostfix(String infix)
    {
        LinkedStack<Character> operatorStack = new LinkedStack<>();
        String postfix = "";
        for(int x = 0; x < infix.length(); x++)
        {
            char nextCharacter = infix.charAt(x);
            if(Character.isLetterOrDigit(nextCharacter))
                postfix += nextCharacter;
            else if(nextCharacter == '(')
            {
                operatorStack.push(nextCharacter);
            }
            else if(nextCharacter == ')')
            {
                char topOperator = operatorStack.pop();
                while (topOperator != '(')
                {
                    postfix += topOperator;
                    topOperator = operatorStack.pop();
                }
            }
            else
            {
                while (!operatorStack.isEmpty() && precedence(nextCharacter) <= precedence(operatorStack.peek()))
                {
                    postfix += operatorStack.pop();
                }
                operatorStack.push(nextCharacter);
            }
        }
        while (!(operatorStack.isEmpty()))
        {
            postfix += operatorStack.pop();
        }
        return postfix;
    } // end convertToPostfix
} // end LinkedStack

