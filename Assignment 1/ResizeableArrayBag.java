import java.util.Arrays;

/** A bag of objects made with a resizeable array. */
public class ResizeableArrayBag<T> implements BagInterface<T>
{
    private T[] bag;
    private static final int DEFAULT_CAPACITY = 25;
    private int numberOfEntries;
    private boolean integrityOK = false;
    private static final int MAX_CAPACITY = 10000;

    /** Creates an empty bag whose initial capacity is 25. */
    public ResizeableArrayBag()
    {
        // The cast is safe because the new array contains null entries
        @SuppressWarnings("unchecked")
        T[] tempBag= (T[])new Object[DEFAULT_CAPACITY]; // Unchecked cast
        bag = tempBag;
        numberOfEntries= 0;
        integrityOK= true;
    } // end default constructor

    /** Creates an empty bag having a given capacity.
     * @param desiredCapacity The integer capacity desired. */
    public ResizeableArrayBag(int desiredCapacity)
    {
        if(desiredCapacity<= MAX_CAPACITY)
        {
            // The cast is safe because the new array contains null entries
            @SuppressWarnings("unchecked")
            T[] tempBag= (T[])new Object[desiredCapacity]; // Unchecked cast
            bag = tempBag;
            numberOfEntries= 0;
            integrityOK= true;
        }
        else
            throw new IllegalStateException("Attempt to create a bag whose "+"capacity exceeds allowed maximum.");
    } // end constructor

    // Throws an exception if this object is not initialized.
    private void checkIntegrity()
    {
        if(!integrityOK)
          throw new SecurityException("ArrayBag object is corrupt.");
    } // end checkIntegrity

    /** Adds a new entry to this bag.
     * @param newEntry The object to be added as a new entry.
     * @return True.  */
    public boolean add(T newEntry)
    {
        checkIntegrity();
        if(isArrayFull())
        {
            doubleCapacity();
        } // end if
        
        bag[numberOfEntries] = newEntry;
        numberOfEntries++;
        
        return true;
    } // end add

    /** Retrieves all entries that are in this bad
     * @return a newly allocated array of all the entries in the bag */
    public T[] toArray()
    {
        // the cast is safe because the new erray contains null entries
        @SuppressWarnings("unchecked")
        T[] result = (T[])new Object[numberOfEntries]; // unchecked cast
        for (int index = 0; index < numberOfEntries; index++)
        {
            result[index] = bag[index];
        } // end toArray

        return result;
    } // end toArray

    /** Sees whether this bag is empty.
     * @return True if this bag is empty, or false if not. */
    public boolean isEmpty()
    {
        return numberOfEntries == 0;
    } // end isEmpty

    /** Sees whether this bag is full.
     * @return True if this bag is full, or false if not. */
    public boolean isArrayFull()
    {
        return numberOfEntries == MAX_CAPACITY;
    } // end isArrayFull

    /** Gets the current number of entries in this bag.
     * @return The integer number of entries currently in this bag. */
    public int getCurrentSize()
    {
        return numberOfEntries;
    } // end getCurrentSize

    /** Counts the number of times a given entry appears in this bag.
     * @param anEntry The entry to be counted.@returnThe number of times anEntryappears in this bag. */
    public int getFrequencyOf(T anEntry)
    {
        checkIntegrity();
        int counter = 0;
        
        for(int index = 0; index < numberOfEntries; index++)
        {
            if(anEntry.equals(bag[index]))
            {
                counter++;
            } // end if
        } // end for
        return counter;
    } // end getFrequencyOf

    /** Removes one occurrence of a given entry from this bag.
     * @param anEntry The entry to be removed.
     * @return True if the removal was successful, or false if not. */
    public boolean remove(T anEntry)
    {
        checkIntegrity();
        int index = getIndexOf(anEntry);
        T result = removeEntry(index);
        return anEntry.equals(result);
    } // end remove

    /** Removes one unspecified entry from this bag, if possible.
     * @return Either the removed entry, if the removal was successful, or null otherwise. */
    public T remove()
    {
        checkIntegrity();
        T result = removeEntry(numberOfEntries-1);
        return result;
    } // end remove

    // Locates a given entry within the array bag.
    // Returns the index of the entry, if located, or -1 otherwise.
    // Precondition: checkIntegrity has been called.
    private int getIndexOf(T anEntry)
    {
        int where = -1;
        boolean found = false;
        int index = 0;
        while(!found && (index < numberOfEntries))
        {
            if(anEntry.equals(bag[index]))
            {
                found = true;
                where = index;
            } // end if
            index++;
        } // end while
        
        // Assertion: If where > -1, anEntry is in the array bag, and it
        // equals bag[where]; otherwise, anEntry is not in the array
        
        return where;
    } // end getIndexOf

    // Removes and returns the entry at a given index within the array bag.
    // If no such entry exists, returns null.
    // Preconditions: 0 <= givenIndex< numberOfEntries;
    //                checkIntegrityhas been called.
    private T removeEntry(int givenIndex)
    {
        T result = null;
        
        if(!isEmpty() && (givenIndex>= 0))
        {
            result = bag[givenIndex];       // Entry to remove
            bag[givenIndex] = bag[numberOfEntries-1]; // Replace entry with last entry
            bag[numberOfEntries-1] = null;            // Remove last entry
            numberOfEntries--;
        } // end if
        return result;
    } // end removeEntry

    /** Removes all entries from this bag. */
    public void clear() 
    {
        while(!isEmpty())
            remove();
    } // end clear

    /** Tests whether this bag contains a given entry.
     * @paraman Entry The entry to locate. 
     * @return True if this bag contains anEntry, or false otherwise. */
    public boolean contains(T anEntry)
    {
        checkIntegrity();
        return getIndexOf(anEntry) > -1; // or >= 0
    } // end contains

    // Throws an exception if the client requests a capacity that is too large.
    private void checkCapacity(int capacity)
    {
        if(capacity > MAX_CAPACITY)
            throw new IllegalStateException("Attempt to create a bag whose "+"capacity exeeds allowed "+"maximum of "+ MAX_CAPACITY);
    } // end checkCapacity

    // Doubles the size of the array bag.
    // Precondition: checkIntegrity has been called.
    private void doubleCapacity()
    {
        int newLength= 2* bag.length;
        checkCapacity(newLength);
        bag = Arrays.copyOf(bag, newLength);
    } // end doubleCapacity

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
        ResizeableArrayBag<T> result = new ResizeableArrayBag<>();
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
} // end ResizeableArrayBag