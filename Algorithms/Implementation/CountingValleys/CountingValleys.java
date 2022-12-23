import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.*;
import java.util.regex.*;
import java.util.stream.*;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

public class CountingValleys {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        int steps = Integer.parseInt(bufferedReader.readLine().trim());

        String path = bufferedReader.readLine();

        int result = Result.countingValleys(steps, path);

        System.out.println(result);

        bufferedReader.close();
    }

    private static class Result {
        private static char UP = 'U';
        private static char DOWN = 'D';

        /*
         * Complete the 'countingValleys' function below.
         *
         * The function is expected to return an INTEGER.
         * The function accepts following parameters:
         *  1. INTEGER steps
         *  2. STRING path
         */

        public static int countingValleys(int steps, String path) {
            // Write your code here
            int elevation = 0;
            int numberOfValleys = 0;

            for (int i = 0; i < steps; i++) {
                char c = path.charAt(i);
                if (c == UP) {
                    elevation++;
                    if (elevation == 0) numberOfValleys++;
                    continue;
                }

                if (c == DOWN) {
                    elevation--;
                }

            }

            return numberOfValleys;
        }

    }
}
