/** A class that holds data and links to other instances of itself. */
public class Node<T>
{
    public T data; // entry in bag
    public Node<T> next; // link to next node

    public Node(T dataPortion)
    {
        this(dataPortion, null);
    } // end constructor

    public Node(T dataPortion, Node<T> nextNode)
    {
        data = dataPortion;
        next = nextNode;
    }

    public T getData()
    {
        return data;
    } // end getData

    public void setData(T newData)
    {
        data = newData;
    } // end setData;

    public Node<T> getNextNode()
    {
        return next;
    } // end getNextNode

    public void setNextNode(Node<T> nextNode)
    {
        next = nextNode;
    } // end setNextNode
} // end Node