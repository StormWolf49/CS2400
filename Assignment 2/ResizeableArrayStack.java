import java.util.Arrays;
import java.util.EmptyStackException;
import javax.swing.JOptionPane;

/** A class of stacks whose entries are stored in an array. */
public class ResizeableArrayStack<T> implements StackInterface<T>
{
    private T[] stack; // Array of stack entries
    private int topIndex; // Index of top entry
    private boolean integrityOK = false;
    private static final int DEFAULT_CAPACITY = 50;
    private static final int MAX_CAPACITY = 10000;

    public ResizeableArrayStack()
    {
        this(DEFAULT_CAPACITY);
    } // end default constructor

    public ResizeableArrayStack(int initialCapacity)
    {
        integrityOK = false;
        checkCapacity(initialCapacity);

        // The cast is safe because the new array contains null entries
        @SuppressWarnings("unchecked")
        T[] tempStack = (T[])new Object[initialCapacity];
        stack = tempStack;
        topIndex = -1;
        integrityOK = true;
    } // end constructor

    public void push(T newEntry)
    {
        checkIntegrity();
        ensureCapacity();
        stack[topIndex + 1] = newEntry;
        topIndex++;
    } // end push

    private void ensureCapacity()
    {
        if(topIndex >= stack.length - 1) // If array is full, double its size
        {
            int newLength = 2 * stack.length;
            checkCapacity(newLength);
            stack = Arrays.copyOf(stack, newLength);
        } // end if
    } //end ensureCapacity

    public T pop()
    {
        checkIntegrity();
        if(isEmpty())
            throw new EmptyStackException();
        else
        {
            T top = stack[topIndex];
            stack[topIndex] = null;
            topIndex--;
            return top;
        } // end if
    } // end pop

    public T peek()
    {
        checkIntegrity();
        if(isEmpty())
            throw new EmptyStackException();
        else
            return stack[topIndex];
    } //end peek

    public boolean isEmpty()
    {
        return topIndex < 0;
    } // end isEmpty

    public void clear()
    {
        checkIntegrity();

        // Remove references to the objects in the stack,
        // but do not deallocate the array
        while(topIndex > -1)
        {
            stack[topIndex] = null;
            topIndex--;
        } // end while
        // Assertion: topIndex is -1
    } // end clear

    // Throws an exception if the client requests a capacity that is too large.
    private void checkCapacity(int capacity)
    {
        if(capacity > MAX_CAPACITY)
            throw new IllegalStateException("Attempt to create a stack whose "+"capacity exceeds allowed "+"maximum of "+ MAX_CAPACITY);
    } // end checkCapacity

    // Throws an exception if this object is not initialized.
    private void checkIntegrity()
    {
        if(!integrityOK)
          throw new SecurityException("ArrayStack object is corrupt.");
    } // end checkIntegrity
    
    /** Evaluates a postfix expression and  */
    public static int evaluatePostfix(String postfix, Integer variables)
    {
        ResizeableArrayStack<Integer> valueStack = new ResizeableArrayStack<>();
        Integer[] values = new Integer[variables];
        for(int x = 0; x < variables; x++)
        {
            values[x] = Integer.parseInt(JOptionPane.showInputDialog("Enter the value of variable number " + (x + 1)));
        }
        for(int x = 0; x < postfix.length(); x++)
        {
            char nextCharacter = postfix.charAt(x);
            if(Character.isLetterOrDigit(nextCharacter))
            {
                int nextDigit = values[Character.getNumericValue(nextCharacter) - Character.getNumericValue('a')];
                valueStack.push(nextDigit);
            }
            else
            {
                int operandTwo = valueStack.pop();
                int operandOne = valueStack.pop();

                switch(nextCharacter)
                {
                    case '+':
                        valueStack.push(operandOne + operandTwo);
                        break;
                    case '-':
                        valueStack.push(operandOne - operandTwo);
                        break;
                    case '/':
                        valueStack.push(operandOne / operandTwo);
                        break;
                    case '*':
                        valueStack.push(operandOne * operandTwo);
                        break;
                }
            }
        }
        return valueStack.pop();
    }
} // end RisezeableArrayStack
