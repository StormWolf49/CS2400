/** A bag of objects made with linked nodes. */
public class LinkedBag<T> implements BagInterface<T>
{
    private Node firstNode;     // reference the first node
    private int numberOfEntries;

    public LinkedBag()
    {
        firstNode = null;
        numberOfEntries = 0;
    } // end default constructor

    private class Node // private inner class
    {
        private T data; // entry in bag
        private Node next; // link to next node

        private Node(T dataPortion)
        {
            this(dataPortion, null);
        } // end constructor

        private Node(T dataPortion, Node nextNode)
        {
            data = dataPortion;
            next = nextNode;
        }

        private T getData()
        {
            return data;
        } // end getData

        private void setData(T newData)
        {
            data = newData;
        } // end setData;

        private Node getNextNode()
        {
            return next;
        } // end getNextNode

        private void setNextNode(Node nextNode)
        {
            next = nextNode;
        } // end setNextNode
    } // end Node

    /** Adds a new entry to this bag.
     * @param newEntry The object to be added as a new entry
     * @return True if the addition is successful, or false if not. */
    public boolean add(T newEntry)      // OutOfMemoryErrorpossible
    {
        // Add to beginning of chain:
        Node newNode = new Node(newEntry);
        newNode.setNextNode(firstNode); // Make new node reference rest of chain
                                 // (firstNodeis null if chain is empty)

        firstNode= newNode;      // New node is at beginning of chain
        numberOfEntries++;
        return true;
    } // end add

    /** Removes one unspecified entry from this bag, if possible.
     * @return Either the removed entry, if the removal was successful, or null. */
    public T remove()
    {
        T result= null;
        if(firstNode!= null)
        {
            result= firstNode.getData();
            firstNode= firstNode.getNextNode(); // Remove first node from chain
            numberOfEntries--;
        } // end if
        return result;
    } // end remove

    // Locates a given entry within this bag.
    // Returns a reference to the node containing the 
    // entry, if located, or null otherwise.
    private Node getReferenceTo(T anEntry)
    {
        boolean found= false;
        Node currentNode= firstNode;
        
        while(!found && (currentNode!= null))
        {
            if(anEntry.equals(currentNode.getData()))
                found= true;
            else
            currentNode= currentNode.getNextNode();
        } // end while
        
        return currentNode;
    } // end getReferenceTo

    /** Removes one occurrence of a given entry from this bag, if possible.
     * @param anEntry The entry to be removed.
     * @return True if the removal was successful, or false otherwise. */
    public boolean remove(T anEntry)
    {
        boolean result= false;
        Node nodeN= getReferenceTo(anEntry);
        
        if(nodeN!= null)
        {
            // Replace located entry with entry in first node
            nodeN.setData(firstNode.getData());
            // Remove first node
            firstNode= firstNode.getNextNode();
            
            numberOfEntries--;
            result = true;
        } // end if
        return result;
    } // end remove

    /** Sees whether this bag is empty.
    @return True if this bag is empty, or false if not. */
    public boolean isEmpty()
    {
        return numberOfEntries == 0;
    } // end isEmpty

    /** Gets the number of entries currently in this bag.
     * @return The integer number of entries currently in this bag. */
    public int getCurrentSize()
    {
        return numberOfEntries;
    } // end getCurrentSize

    /** Removes all entries from this bag. */
    public void clear()
    {
        while (!isEmpty())
            remove();
    } //end clear

    /** Counts the number of times a given entry appears in this bag.
     * @param anEntry The entry to be counted.
     * @return The number of times anEntry appears in this bag. */
    public int getFrequencyOf(T anEntry)
    {
        int frequency = 0;

        int counter = 0;
        Node currentNode = firstNode;
        while ((counter < numberOfEntries) && (currentNode != null))
        {
            if(anEntry.equals(currentNode.getData()))
            {
                frequency++;
            } // end if

            counter++;
            currentNode = currentNode.getNextNode();
        } // end while

        return frequency;
    } // end getFrequencyOf

    /** Tests wether this bag contains a given entry.
     * @param anEntry The entry to locate.
     * @return True if the bag contains anEntry, or false otherwise. */
    public boolean contains(T anEntry)
    {
        boolean found = false;
        Node currentNode = firstNode;

        while (!found && (currentNode != null))
        {
            if(anEntry.equals(currentNode.getData()))
                found = true;
            else
                currentNode = currentNode.getNextNode();
        } // end while

        return found;
    } // end contains

    /** Retrieves all entries that are in this bag.
     * @return A newly allocated array of all the entries in this bag. */
    public T[] toArray()
    {
        // The cast is safe because the new array contains null entries
        @SuppressWarnings("unchecked")
        T[] result = (T[])new Object[numberOfEntries]; // Unchecked cast

        int index = 0;
        Node curreNode = firstNode;
        while ((index < numberOfEntries) && (curreNode != null))
        {
            result[index] = curreNode.getData();
            index++;
            curreNode = curreNode.getNextNode();
        } // end while

        return result;
    } // end toArray

    /** Combines all entries in this bag with another bag's entries into one bag.
     * @param aBag The bag that will be combined with this one.
     * @return The new bag containing both the entries from this bag and the other one. */
    public BagInterface<T> union(BagInterface<T> aBag)
    {
        T[] bag2 = aBag.toArray();
        T[] bag1 = toArray();
        ResizeableArrayBag<T> result = new ResizeableArrayBag<>();
        
        for(int x = 0; x < bag1.length; x++)
        {
            result.add(bag1[x]);
        } // end for
        
        for(int x = 0; x < bag2.length; x++)
        {
            result.add(bag2[x]);
        } // end for

        return result;
    } // end union

    /** Finds all entries in this bag that also exist in another bag and places them in a new bag.
     * @param aBag The bag that will be intersected with this one.
     * @return The new bag containing the intersection of both bags. */
    public BagInterface<T> intersection(BagInterface<T> aBag)
    {
        T[] bag2 = aBag.toArray();
        LinkedBag<T> result = new LinkedBag<>();
        int count = 0;

        for(int x = 0; x < bag2.length; x++)
        {
            int frequency = aBag.getFrequencyOf(bag2[x]);
            
            if((contains(bag2[x]) && (getFrequencyOf(bag2[x]) > 1)) && (frequency > 1))
            {
                result.add(bag2[x]);
                count++;
            }
            else if(contains(bag2[x]) && (count < getFrequencyOf(bag2[x])))
            {
                result.add(bag2[x]);
                count++;
            }
            else
            {
                count = 0;
            }

        } // end for

        return result;
    } // end intersection

    /** Places all entries in the first bag that do not occur in another bag into a new bag.
     * @param aBag The bag that will be compared with this one.
     * @return The new bag containing the entries from the first bag that don't occur in the second. */
    public BagInterface<T> difference(BagInterface<T> aBag)
    {
        T[] bag2 = aBag.toArray();
        T[] bag1 = toArray();
        ResizeableArrayBag<T> result = new ResizeableArrayBag<>();
        
        for(int x = 0; x < bag1.length; x++)
        {
            result.add(bag1[x]);
        } // end for
        
        for(int x = 0; x < bag2.length; x++)
        {
            if(result.contains(bag2[x]))
            {
                result.remove(bag2[x]);
            }
        } // end for

        return result;
    } // end difference
} // end LinkedBag