import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Scanner;

public class GreedyFlorist {

    /**
     * Strategy: Each person should buy most expensive flower available and continue doing so until none are left
     */
    static int getMinimumCost(int numberOfFriends, LinkedList<Integer> flowerPrices) {
        flowerPrices.sort(Collections.reverseOrder());
        int grandTotal = 0;
        int purchasesPerCustomer = 1;
        while(!flowerPrices.isEmpty() && flowerPrices.size() >= numberOfFriends) {
            for (int i = 0; i < numberOfFriends; i++) {
                grandTotal += flowerPrices.remove(0) * purchasesPerCustomer;
            }
            purchasesPerCustomer++;
        }
        int finalPurchasesPerCustomer = purchasesPerCustomer;
        grandTotal += flowerPrices.stream().reduce(0, (a, b) -> a + b * finalPurchasesPerCustomer);
        return grandTotal;
    }

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        String[] nk = scanner.nextLine().split(" ");

        int numberOfFlowers = Integer.parseInt(nk[0]);

        int numberOfFriends = Integer.parseInt(nk[1]);

        LinkedList<Integer> c = new LinkedList<>();

        String[] cItems = scanner.nextLine().split(" ");
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        for (int i = 0; i < numberOfFlowers; i++) {
            int cItem = Integer.parseInt(cItems[i]);
            c.add(cItem);
        }

        int minimumCost = getMinimumCost(numberOfFriends, c);

        System.out.println(minimumCost);

        scanner.close();
    }
}
