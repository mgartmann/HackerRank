using System.IO;
using System;

class Solution
{

    // Complete the dayOfProgrammer function below.
    static string DayOfProgrammer(int year)
    {
        int[] daysInMonth = new int[] { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };

        if (year <= 1917)
        {
            if (IsJulianLeapYear(year)) daysInMonth[1] = 29;
        } 
        else if (year == 1918)
        {
            daysInMonth[1] = 15;
        } 
        else
        {
            if (IsGregorianLeapYear(year)) daysInMonth[1] = 29;
        }

        int day = 256;
        int month = 1;

        for(int i = 0; i < daysInMonth.Length && day - daysInMonth[i] > 0; i++)
        {
            month++;
            day -= daysInMonth[i];
        }

        string monthString = month.ToString("D2"); // Add leading zero to months smaller than 10.
        return $"{day}.{monthString}.{year}";
    }

    static bool IsJulianLeapYear(int year)
    {
        return year % 4 == 0;
    }

    static bool IsGregorianLeapYear(int year)
    {
        return (year % 4 == 0 && year % 100 != 0) || year % 400 == 0;
    }

    static void Main(string[] args)
    {
        TextWriter textWriter = new StreamWriter(@System.Environment.GetEnvironmentVariable("OUTPUT_PATH"), true);

        int year = Convert.ToInt32(Console.ReadLine().Trim());

        string result = DayOfProgrammer(year);

        textWriter.WriteLine(result);

        textWriter.Flush();
        textWriter.Close();
    }
}
