import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class testHeap
{
    public static void main(String[] args) throws FileNotFoundException
    {
        Scanner data = new Scanner(new File("data.txt"));
        PrintWriter out = new PrintWriter("output.txt");
        Integer[] input = new Integer[100];

        for(int index = 0; index < input.length; index++)
        {
            input[index] = data.nextInt();
        }

        heap sequential = new heap(input.length);
        heap optimal = new heap(input.length);
        sequential.sequential(input);
        optimal.optimal(input);
        
        out.println("=====================================================================");
        out.print("Heap built using sequential insertions: ");
        for(int index = 0; index < 10; index++)
        {
            out.print(sequential.heap[index + 1] + ",");
        }
        out.println("...");
        out.println("Number of swaps in the heap creation: " + sequential.swaps);
        for(int index = 0; index < 10; index++)
        {
            sequential.removeMax();
        }
        out.print("Heap after 10 removals: ");
        for(int index = 0; index < 10; index++)
        {
            out.print(sequential.heap[index + 1] + ",");
        }
        out.println("...");

        out.println();

        out.print("Heap built using optimal method: ");
        for(int index = 0; index < 10; index++)
        {
            out.print(optimal.heap[index + 1] + ",");
        }
        out.println("...");
        out.println("Number of swaps in the heap creation: " + optimal.swaps);
        for(int index = 0; index < 10; index++)
        {
            optimal.removeMax();
        }
        out.print("Heap after 10 removals: ");
        for(int index = 0; index < 10; index++)
        {
            out.print(optimal.heap[index + 1] + ",");
        }
        out.println("...");
        out.println("=====================================================================");

        data.close();
        out.close();
    }
}
