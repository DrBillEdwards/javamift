package mift;

import java.util.Timer;
import java.util.TimerTask;

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
    static int buffers[] = {0, 0, 0, 0};;
    static int output = 0;
    static int numOfPartsInLines[] = {0, 0, 0, 0, 0};
    static double rs[] =
    {
        0, 0, 0, 0, 0,
        0, 0, 0, 0, 0,
        0, 0, 0, 0, 0,
        0, 0, 0, 0, 0,
        0, 0, 0, 0, 0,
        0, 0, 0, 0, 0
    };
    static int t = 0;
    static int tPrevs[] = {0, 0, 0, 0, 0};
    static int runHour = 1;
    static int currRun = 1;
    static int i = 0;
    static boolean stasfailed[] = new boolean[30];
    static int staNumFailed = 0;
    static int totalOutputs = 0;
    static String displayText = "";
    static String outputs[] = new String[2000000]; // GLAPOptions.RUN_SECONDS * GLAPOptions.NUM_RUNS
    static boolean appendReport = false;
    static boolean appendOutput = false;

    public static void main(String[] args)
    {
        FileOps.createFile(GLAPOptions.REPORT_FILE_NAME);
        FileOps.createFile(GLAPOptions.OUTPUT_FILE_NAME);
        if(GLAPOptions.NUM_RUNS > 1)
        {
            appendReport = true;
            appendOutput = true;
        }
        TimerTask masterTimerTask = new TimerTask()
        {
            @Override
            public void run()
            {
                t += 1;
                System.out.println(t);
                if(t > GLAPOptions.RUN_SECONDS)
                {
                    totalOutputs += output;
                    FileOps.writeToReportFile(GLAPOptions.REPORT_FILE_NAME, outputs, appendReport);
                    FileOps.writeToOutputFile(GLAPOptions.OUTPUT_FILE_NAME, output, appendOutput);
                    if(currRun >= GLAPOptions.NUM_RUNS)
                    {
                        displayText += GLAPOptions.NEW_LINES + "t: " + t;
                        System.out.println(displayText);
                        int avgOutput = Math.round(totalOutputs / GLAPOptions.NUM_RUNS);
                        System.out.println("avg output: " + totalOutputs / GLAPOptions.NUM_RUNS);
                        FileOps.writeToOutputFile(GLAPOptions.OUTPUT_FILE_NAME, "avg output: " + avgOutput, true);
                        outputs[i++] = displayText;
                        System.exit(0);
                    }
                    t = 0;
                    infOcn = 1;
                    assemblyLines = new int[5][6];
                    buffers = new int[4];
                    tPrevs = new int[5];
                    output = 0;
                    outputs = new String[outputs.length];
                    currRun++;
                }
            }
        };

        masterTimer = new Timer("MasterTimer");
        masterTimer.scheduleAtFixedRate(masterTimerTask, 0, 1);
    }
}
