/** A test class of the 3 new methods added to the LinkedBag class. */
public class LinkedBagTest
{
    public static void main(String[] args) {
        BagInterface<String> bag1 = new LinkedBag<String>();
        BagInterface<String> bag2 = new LinkedBag<String>();

        // adding strings
        String[] contentsOfBag1 = {"c", "b", "a"};
        testAdd(bag1, contentsOfBag1);
        String[] contentsOFBag2 = {"e", "d", "b", "b"};
        testAdd(bag2, contentsOFBag2);

        // tests
        System.out.print("Union: ");
        displayBag(bag1.union(bag2));
        System.out.print("Intersection: ");
        displayBag(bag1.intersection(bag2));
        System.out.print("Difference (1-2): ");
        displayBag(bag1.difference(bag2));
        System.out.print("Difference (2-1): ");
        displayBag(bag2.difference(bag1));
    } // end main

    // Tests the method add.
    private static void testAdd(BagInterface<String> aBag, String[] content)
    {
        System.out.println("Adding to the bag: ");
        for(int index = 0; index < content.length; index++)
        {
            aBag.add(content[index]);
            System.out.print(content[index] + " ");
        } // end for
        System.out.println();

        displayBag(aBag);
    } // end testAdd
    
    // Tests the method toArray while displaying the bag.
    private static void displayBag(BagInterface<String> aBag)
    {
        System.out.println("The bag contains the following string(s):");
        Object[] bagArray = aBag.toArray();
        for(int index = 0; index < bagArray.length; index++)
        {
            System.out.print(bagArray[index] + " ");
        } // end for

        System.out.println();
    } // end displayBag
} // end LinkedBagTest
