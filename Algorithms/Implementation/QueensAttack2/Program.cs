using System.CodeDom.Compiler;
using System.Collections.Generic;
using System.Collections;
using System.ComponentModel;
using System.Diagnostics.CodeAnalysis;
using System.Globalization;
using System.IO;
using System.Linq;
using System.Reflection;
using System.Runtime.Serialization;
using System.Text.RegularExpressions;
using System.Text;
using System;

/// You will be given a square chess board (n x n) with one queen and a number of obstacles placed on it.
/// Determine how many squares the queen can attack.
/// Ref: https://www.hackerrank.com/challenges/queens-attack-2/problem?isFullScreen=true

enum Directions
{
    N,
    NE,
    E,
    SE,
    S,
    SW,
    W,
    NW
}

class Solution
{
    // Complete the queensAttack function below.
    static int queensAttack(int n, int k, int r_q, int c_q, int[][] obstacles)
    {
        int sum = 0;

        sum = queensAttack(n, k, r_q + 1, c_q, obstacles, sum, Directions.N);
        sum = queensAttack(n, k, r_q + 1, c_q + 1, obstacles, sum, Directions.NE);
        sum = queensAttack(n, k, r_q, c_q + 1, obstacles, sum, Directions.E);
        sum = queensAttack(n, k, r_q - 1, c_q + 1, obstacles, sum, Directions.SE);
        sum = queensAttack(n, k, r_q - 1, c_q, obstacles, sum, Directions.S);
        sum = queensAttack(n, k, r_q - 1, c_q - 1, obstacles, sum, Directions.SW);
        sum = queensAttack(n, k, r_q, c_q - 1, obstacles, sum, Directions.W);
        sum = queensAttack(n, k, r_q + 1, c_q - 1, obstacles, sum, Directions.NW);

        return sum;
    }

    static int queensAttack(int n, int k, int r_q, int c_q, int[][] obstacles, int sum, Directions direction)
    {
        if (r_q > n || r_q <= 0 || c_q > n || c_q <= 0)
        {
            return sum;
        }
        for(int i = 0; i < obstacles.GetLength(0); i++)
        {
            if(obstacles[i][0] == r_q && obstacles[i][1] == c_q)
            {
                return sum;
            }
        }

        switch (direction)
        {
            case Directions.N:
                r_q += 1;
                break;
            case Directions.NE:
                r_q += 1;
                c_q += 1;
                break;
            case Directions.E:
                c_q += 1;
                break;
            case Directions.SE:
                r_q -= 1;
                c_q += 1;
                break;
            case Directions.S:
                r_q -= 1;
                break;
            case Directions.SW:
                r_q -= 1;
                c_q -= 1;
                break;
            case Directions.W:
                c_q -= 1;
                break;
            case Directions.NW:
                r_q += 1;
                c_q -= 1;
                break;
        }

        sum = queensAttack(n, k, r_q, c_q, obstacles, sum, direction);
        return sum + 1;
    }

    static void Main(string[] args)
    {
        //TextWriter textWriter = new StreamWriter(@System.Environment.GetEnvironmentVariable("OUTPUT_PATH"), true);
        TextWriter textWriter = Console.Out;

        string[] nk = Console.ReadLine().Split(' ');

        int n = Convert.ToInt32(nk[0]);

        int k = Convert.ToInt32(nk[1]);

        string[] r_qC_q = Console.ReadLine().Split(' ');

        int r_q = Convert.ToInt32(r_qC_q[0]);

        int c_q = Convert.ToInt32(r_qC_q[1]);

        int[][] obstacles = new int[k][];

        for (int i = 0; i < k; i++)
        {
            obstacles[i] = Array.ConvertAll(Console.ReadLine().Split(' '), obstaclesTemp => Convert.ToInt32(obstaclesTemp));
        }

        int result = queensAttack(n, k, r_q, c_q, obstacles);

        textWriter.WriteLine(result);

        textWriter.Flush();
        textWriter.Close();
    }
}
