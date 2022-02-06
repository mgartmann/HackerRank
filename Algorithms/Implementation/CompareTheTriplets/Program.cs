using System.Collections.Generic;
using System.Linq;
using System;

class Result
{

    /*
     * Complete the 'compareTriplets' function below.
     *
     * The function is expected to return an INTEGER_ARRAY.
     * The function accepts following parameters:
     *  1. INTEGER_ARRAY a
     *  2. INTEGER_ARRAY b
     */

    public static List<int> compareTriplets(List<int> a, List<int> b)
    {
        int aPoints = 0, bPoints = 0;
        for (int i = 0; i < a.Count; i++)
        {
            if (a[i] > b[i])
            {
                aPoints++;
            }
            else if (a[i] < b[i])
            {
                bPoints++;
            }
        }

        return new List<int>()
        {
            aPoints,
            bPoints
        };
    }

}

class Solution
{
    public static void Main(string[] args)
    {
        List<int> a = Console.ReadLine().TrimEnd().Split(' ').ToList().Select(aTemp => Convert.ToInt32(aTemp)).ToList();

        List<int> b = Console.ReadLine().TrimEnd().Split(' ').ToList().Select(bTemp => Convert.ToInt32(bTemp)).ToList();

        List<int> result = Result.compareTriplets(a, b);

        Console.WriteLine(String.Join(" ", result));
    }
}
