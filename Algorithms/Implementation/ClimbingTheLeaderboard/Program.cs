using System.Collections.Generic;
using System.Linq;
using System;

class Result
{

    /*
     * See: https://www.hackerrank.com/challenges/climbing-the-leaderboard/
     * Complete the 'climbingLeaderboard' function below.
     *
     * The function is expected to return an INTEGER_ARRAY.
     * The function accepts following parameters:
     *  1. INTEGER_ARRAY ranked
     *  2. INTEGER_ARRAY player
     */

    public static List<int> climbingLeaderboard(List<int> ranked, List<int> player)
    {
        var Result = new List<int>();
        var rankedArray = ranked.Distinct().ToArray();
        var playerArray = player.ToArray();

        int PlayerScoreIndex = 0;
        int RankedScoreIndex = rankedArray.Length - 1;

        if(rankedArray[rankedArray.Length - 1] >= playerArray[playerArray.Length - 1])
        {
            return playerArray.Select(x => rankedArray.Length).ToList();
        }

        while(RankedScoreIndex >= 0 && PlayerScoreIndex < playerArray.Length)
        {
            if (playerArray[PlayerScoreIndex] < rankedArray[RankedScoreIndex])
            {
                Result.Add(RankedScoreIndex + 2);
                PlayerScoreIndex++;
            } else
            {
                RankedScoreIndex--;
            }
        }

        if (PlayerScoreIndex < playerArray.Length)
        {
            for(int i = PlayerScoreIndex; i < playerArray.Length; i++)
            {
                Result.Add(RankedScoreIndex + 2);
            }
        }

        return Result;
    }
}

class Solution
{
    public static void Main(string[] args)
    {
        int rankedCount = Convert.ToInt32(Console.ReadLine().Trim());

        List<int> ranked = Console.ReadLine().TrimEnd().Split(' ').ToList().Select(rankedTemp => Convert.ToInt32(rankedTemp)).ToList();

        int playerCount = Convert.ToInt32(Console.ReadLine().Trim());

        List<int> player = Console.ReadLine().TrimEnd().Split(' ').ToList().Select(playerTemp => Convert.ToInt32(playerTemp)).ToList();

        IEnumerable<int> result = Result.climbingLeaderboard(ranked, player);

        foreach(int rank in result)
        {
            Console.WriteLine(rank);
        }
    }
}
