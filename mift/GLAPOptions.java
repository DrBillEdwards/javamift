package mift;

public class GLAPOptions
{
    // OPTIONS
    public static long CYCLE_TIME_SECONDS[] = {47, 50, 53, 56, 60};
    public static double RUN_SECONDS = 3600;
    public static long DOWN_TIME = 6;
    public static int NUM_RUNS = 1;
    public static boolean lineNumDownTimes[] = {true, true, true, true, true};
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
        .99, .99, .99, .99, .99,
        .99, .99, .99, .99, .99,
        .99, .99, .99, .99, .99,
        .99, .99, .99, .99, .99,
        .99, .99, .99, .99, .99,
        .99, .99, .99, .99, .99,
    };
}
