package mift;

import java.util.Timer;

public class GLAP
{
    static Timer masterTimer;
    static int infOcn = 1;
    static int[][] assemblyLines =
    {
        {0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0},
    };
    static int buffers[] = {0, 0, 0, 0};
    static boolean appendReport = false;
    static boolean appendOutput = false;

    public static void main(String[] args)
    {
        FileOps.createFile(Options.REPORT_FILE_NAME);
        FileOps.createFile(Options.OUTPUT_FILE_NAME);
        if(Options.NUM_RUNS > 1)
        {
            appendReport = true;
            appendOutput = true;
        }

        System.out.println("hello Glap world");
    }
}
