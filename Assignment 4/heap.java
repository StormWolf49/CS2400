import java.util.Arrays;
public class heap
{
    public Integer[] heap;
    private int lastIndex;
    private boolean initialized = false;
    private static final int DEFAULT_CAPACITY = 25;
    private static final int MAX_CAPACITY = 10000;
    public Integer swaps = 0;

    public heap()
    {
        this(DEFAULT_CAPACITY);
    }
    public heap(int initialCapacity)
    {
        if (initialCapacity < DEFAULT_CAPACITY)
            initialCapacity = DEFAULT_CAPACITY;
        else
            checkCapacity(initialCapacity);

        Integer[] tempHeap = new Integer[initialCapacity + 1];
        heap = tempHeap;
        lastIndex = 0;
        initialized = true;
    }

    public Integer getMax()
    {
        checkInitialization();
        Integer root = null;
        if(!isEmpty())
            root = heap[1];
        return root;
    }

    public boolean isEmpty()
    {
        return lastIndex < 1;
    }

    public int getSize()
    {
        return lastIndex;
    }

    public void clear()
    {
        checkInitialization();
        while(lastIndex > -1)
        {
            heap[lastIndex] = null;
            lastIndex--;
        }
        lastIndex = 0;
    }

    public void add(Integer newEntry)
    {
        checkInitialization();
        int newIndex = lastIndex + 1;
        int parentIndex = newIndex / 2;
        while((parentIndex > 0) && newEntry.compareTo(heap[parentIndex]) > 0)
        {
            heap[newIndex] = heap[parentIndex];
            newIndex = parentIndex;
            parentIndex = newIndex / 2;
            swaps++;
        }

        heap[newIndex] = newEntry;
        lastIndex++;
        ensureCapacity();
    }

    public void sequential(Integer[] entries)
    {
        for (int index = 0; index < entries.length; index++)
            add(entries[index]);
    }
    
    public void optimal(Integer[] entries)
    {
        for (int index = 0; index < entries.length; index++)
        {
            heap[index + 1] = entries[index];
            lastIndex++;
        }

        for(int rootIndex = lastIndex / 2; rootIndex > 0; rootIndex--)
        {
            reheap(rootIndex);
        }
    }

    public Integer removeMax()
    {
        checkInitialization();
        Integer root = null;

        if(!isEmpty())
        {
            root = heap[1];
            heap[1] = heap[lastIndex];
            lastIndex--;
            reheap(1);
        }

        return root;
    }

    private void checkInitialization()
    {
        if(!initialized)
          throw new SecurityException("heap object is corrupt.");
    }
    
    private void checkCapacity(int capacity)
    {
        if(capacity > MAX_CAPACITY)
            throw new IllegalStateException("Attempt to create a heap whose "+"capacity exeeds allowed "+"maximum of "+ MAX_CAPACITY);
    }

    private void ensureCapacity()
    {
        if(lastIndex + 1 == heap.length)
        {
            int newLength = 2 * heap.length;
            checkCapacity(newLength);
            heap = Arrays.copyOf(heap, newLength);
        }
    }

    private void reheap(int rootIndex)
    {
        boolean done = false;
        Integer orphan = heap[rootIndex];
        int leftChildIndex = 2 * rootIndex;
        
        while(!done && (leftChildIndex <= lastIndex))
        {
            int largerChildIndex = leftChildIndex;
            int rightChildIndex = leftChildIndex + 1;
            if((rightChildIndex <= lastIndex) && heap[rightChildIndex] > heap[largerChildIndex])
            {
                largerChildIndex = rightChildIndex;
            }

            if(orphan <= heap[largerChildIndex])
            {
                heap[rootIndex] = heap[largerChildIndex];
                rootIndex = largerChildIndex;
                leftChildIndex = 2 * rootIndex;
                swaps++;
            }
            else
                done = true;
        }

        heap[rootIndex] = orphan;
    }
}
