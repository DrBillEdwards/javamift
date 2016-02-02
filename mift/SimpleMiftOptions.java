package mift;

/**
 * Created by james on 2/1/16.
 */
public class SimpleMiftOptions
{
    // OPTIONS
    public static long CYCLE_TIME_SECONDS_1 = 54;
    public static long CYCLE_TIME_SECONDS_2 = 60;
    public static double RUN_SECONDS = 3600;
    public static long DOWN_TIME = 6;
    public static int NUM_RUNS = 5;
    public static boolean interjectDownTime = false; // EITHER
    public static boolean interjectDownTimes = false; // OR
    public static boolean infOceanDownTimes = false; // EITHER
    public static boolean run20Down20InfOcn = true; // OR
    public static boolean run20Down20Line1 = false; // EITHER
    public static String REPORT_FILE_NAME = "report1.txt";
    public static String OUTPUT_FILE_NAME = "output1.txt";
    public static String NEW_LINES = "\n"; // toggle newline types
    // static String NEW_LINES = "\r\n";

    // GLAP
    static int BUFFER_MAX[] = {20, 20, 20, 20, 20};
    public static double rLimits[] =
    {
        .90, .90, .90, .90, .90,
        .90, .90, .90, .90, .90,
        .90, .90, .90, .90, .90,
        .90, .90, .90, .90, .90,
        .90, .90, .90, .90, .90,
        .90, .90, .90, .90, .90,
    };
}
