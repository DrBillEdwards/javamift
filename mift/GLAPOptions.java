package mift;

public class GLAPOptions
{
    // OPTIONS
    public static long CYCLE_TIME_SECONDS[] = {47, 50, 53, 56, 60};
    public static double RUN_SECONDS = Constants.SECS_IN_8_HOURS;
    public static long DOWN_TIME = 6;
    public static int NUM_RUNS = 6;
    public static boolean lineNumDownTimes[] = {false, false, false, false, false};
    public static boolean half_1_6AndAHalf = false; // EITHER
    public static boolean half_1_6AndAHalfInfOcn = true; // OR
    public static boolean interjectDownTime = false; // EITHER
    public static boolean interjectDownTimes = false; // OR
    public static boolean infOceanDownTimes = false; // EITHER
    public static boolean run20Down20InfOcn = false; // OR
    public static boolean run20Down20Line1 = false; // EITHER
    public static String REPORT_FILE_NAME = "GLAP_Report1.txt";
    public static String OUTPUT_FILE_NAME = "GLAP_Output1.txt";
    public static String NEW_LINES = "\n"; // toggle newline types
    // static String NEW_LINES = "\r\n";

    // GLAP
    static int BUFFER_MAX[] = {10, 10, 10, 10, 10};
    public static double rLimits[] =
    {
        0.990000, 0.998333, 0.996667, 0.999167, 0.999167, 0.996667,
        1.000000, 0.999444, 1.000000, 0.951389, 0.999167, 0.953889,
        0.957222, 0.956667, 0.956111, 0.959444, 0.956667, 0.953889,
        1.000000, 0.990000, 0.993330, 0.981667, 0.980000, 0.945833,
        0.999444, 0.998333, 0.998333, 0.999444, 0.999167, 0.952778
    };
}
