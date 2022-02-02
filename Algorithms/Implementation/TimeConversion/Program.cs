using System.Text.RegularExpressions;
using System;

class Result
{

    /*
     * Complete the 'timeConversion' function below.
     *
     * The function is expected to return a STRING.
     * The function accepts STRING s as parameter.
     */

    public static string timeConversion(string date)
    {
        Regex regex = new Regex(@"(\d{1,2}):(\d{1,2}):(\d{1,2})(AM|PM)");
        Match parsedDate = regex.Match(date);

        string hours = parsedDate.Groups[1].Value;
        string minutes = parsedDate.Groups[2].Value;
        string seconds = parsedDate.Groups[3].Value;
        string dayTime = parsedDate.Groups[4].Value;

        if (hours.Equals("12"))
        {
            hours = dayTime.Equals("AM") ? "00" : hours;
        }
        else if (dayTime.Equals("PM"))
        {
            hours = (Int32.Parse(hours) + 12).ToString("D2");
        }

        return $"{hours}:{minutes}:{seconds}";

    }

}

class Solution
{
    public static void Main(string[] args)
    {
        string s = Console.ReadLine();

        string result = Result.timeConversion(s);

        Console.WriteLine(result);
    }
}
