package mift;

import java.text.DecimalFormat;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class GLAP
{
    static Timer masterTimer, infOcnTimer, line1Timer, line2Timer, line3Timer, line4Timer, line5Timer;
    static int infOcn = 1;
    static int[][] assemblyLines =
    {
        {1, 1, 1, 1, 1, 1},
        {1, 1, 1, 1, 1, 1},
        {1, 1, 1, 1, 1, 1},
        {1, 1, 1, 1, 1, 1},
        {1, 1, 1, 1, 1, 1},
    };
    static int buffers[] = {10, 10, 0, 0};
    static int output = 0;
    static int numOfPartsInLines[] = {0, 0, 0, 0, 0};
    static double rs[] = 
    {
        0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0
    };
    static int t = 0;
    static int tPrevs[] = {0, 0, 0, 0, 0};
    static int runHour = 1;
    static int currRun = 1;
    static int i = 0;
    static boolean stasFailed[] = new boolean[30];
    static int staNumFailed = 0;
    static double totalOutputs = 0.0;
    static String displayText[] = new String[8];
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
            if(t > GLAPOptions.RUN_SECONDS)
            {
                totalOutputs += output;
                FileOps.writeToReportFile(GLAPOptions.REPORT_FILE_NAME, outputs, appendReport);
                FileOps.writeToOutputFile(GLAPOptions.OUTPUT_FILE_NAME, output, appendOutput);
                if(currRun >= GLAPOptions.NUM_RUNS)
                {
                    displayText[0] += GLAPOptions.NEW_LINES + "t: " + t;
                    System.out.println(displayText[0]);
                    double avgOutput = totalOutputs / GLAPOptions.NUM_RUNS;
                    DecimalFormat decimalFormat = new DecimalFormat("0.00");
                    System.out.println("avg output: " + decimalFormat.format(avgOutput));
                    FileOps.writeToOutputFile(GLAPOptions.OUTPUT_FILE_NAME, "avg output: " + avgOutput, true);
                    outputs[i++] = displayText[0];
                    System.exit(0);
                }
                t = 0;
                infOcn = 1;
                assemblyLines = new int[5][6];
                assemblyLines[0][0] = 1; assemblyLines[0][1] = 1; assemblyLines[0][2] = 1; assemblyLines[0][3] = 1; assemblyLines[0][4] = 1; assemblyLines[0][5] = 1;
                assemblyLines[1][0] = 1; assemblyLines[1][1] = 1; assemblyLines[1][2] = 1; assemblyLines[1][3] = 1; assemblyLines[1][4] = 1; assemblyLines[1][5] = 1;
                assemblyLines[2][0] = 1; assemblyLines[2][1] = 1; assemblyLines[2][2] = 1; assemblyLines[2][3] = 1; assemblyLines[2][4] = 1; assemblyLines[2][5] = 1;
                assemblyLines[3][0] = 1; assemblyLines[3][1] = 1; assemblyLines[3][2] = 1; assemblyLines[3][3] = 1; assemblyLines[3][4] = 1; assemblyLines[3][5] = 1;
                assemblyLines[4][0] = 1; assemblyLines[4][1] = 1; assemblyLines[4][2] = 1; assemblyLines[4][3] = 1; assemblyLines[4][4] = 1; assemblyLines[4][5] = 1;
                buffers = new int[4];
                buffers[0] = 10; buffers[1] = 10; buffers[2] = 0; buffers[3] = 0;
                tPrevs = new int[5];
                output = 0;
                outputs = new String[outputs.length];
                currRun++;
            }
            }
        };

        TimerTask infOcnTimerTask = new TimerTask()
        {
            @Override
            public void run()
            {
            if(GLAPOptions.run20Down20InfOcn)
            {
                if((t > (Constants.SECS_PER_MIN * 20)) && (t < ((Constants.SECS_PER_MIN * 20) * 2)))
                {
                    displayText[1] = "FAILED INFINITE OCEAN t: " + t;
                    System.out.println(displayText[1]);
                    outputs[i++] = displayText[1];
                    infOcn = 0;
                    try {Thread.sleep(Constants.SECS_PER_MIN * 20);} catch(Exception exception) {}
                    infOcn = 1;
                }
            }
            else if(GLAPOptions.half_1_6AndAHalfInfOcn)
            {
                if((t > (Constants.SECS_PER_HOUR / 2)) && (t < ((Constants.SECS_PER_HOUR / 2) + Constants.SECS_PER_HOUR)))
                {
                    displayText[1] = "FAILED INFINITE OCEAN t: " + t;
                    System.out.println(displayText[1]);
                    outputs[i++] = displayText[1];
                    infOcn = 0;
                    try {Thread.sleep(Constants.SECS_PER_HOUR);} catch(Exception exception) {}
                    infOcn = 1;
                }
            }
            }
        };

        final TimerTask line1TimerTask = new TimerTask()
        {
            @Override
            public void run()
            {
                if(GLAPOptions.interjectDownTime && (t >= Constants.SECS_PER_HOUR) && runHour == 1)
                {
                    displayText[2] = "Deliberate 1/2 hour down time line 1";
                    System.out.println(displayText[2]);
                    outputs[i++] = displayText[2];
                    try {Thread.sleep(Constants.SECS_PER_HOUR / 2);} catch(Exception exception) {}
                    runHour++;
                }
                else
                {
                    if(GLAPOptions.interjectDownTimes)
                    {
                        if(((runHour == 1) && (t >= Constants.SECS_PER_HOUR)) || ((runHour == 2) && (t >= (Constants.SECS_PER_HOUR * 2))) ||
                            ((runHour == 3) && (t >= (Constants.SECS_PER_HOUR * 3))) || ((runHour == 4) && (t >= (Constants.SECS_PER_HOUR * 4))) ||
                            ((runHour == 5) && (t >= (Constants.SECS_PER_HOUR * 5))) || ((runHour == 6) && (t >= (Constants.SECS_PER_HOUR * 6))) ||
                            ((runHour == 7) && (t >= (Constants.SECS_PER_HOUR * 7))) || ((runHour == 8) && (t >= (Constants.SECS_PER_HOUR * 8))) ||
                            ((runHour == 9) && (t >= (Constants.SECS_PER_HOUR * 9))))
                        {
                            displayText[2] = "Deliberate 1/2 hour down time line 1, hour " + runHour;
                            System.out.println(displayText[2]);
                            outputs[i++] = displayText[2];
                            try {Thread.sleep(Constants.SECS_PER_HOUR / 2);} catch(Exception exception) {}
                            runHour++;
                        }
                    }
                    else if(GLAPOptions.infOceanDownTimes)
                    {
                        if(((runHour == 1) && (t >= Constants.SECS_PER_HOUR)) || ((runHour == 2) && (t >= (Constants.SECS_PER_HOUR * 2))) ||
                            ((runHour == 3) && (t >= (Constants.SECS_PER_HOUR * 3))) || ((runHour == 4) && (t >= (Constants.SECS_PER_HOUR * 4))) ||
                            ((runHour == 5) && (t >= (Constants.SECS_PER_HOUR * 5))) || ((runHour == 6) && (t >= (Constants.SECS_PER_HOUR * 6))) ||
                            ((runHour == 7) && (t >= (Constants.SECS_PER_HOUR * 7))) || ((runHour == 8) && (t >= (Constants.SECS_PER_HOUR * 8))) ||
                            ((runHour == 9) && (t >= (Constants.SECS_PER_HOUR * 9))))
                        {
                            displayText[2] = "FAILED INFINITE OCEAN (infinite ocean down times) run hour:" + runHour;
                            System.out.println(displayText[2]);
                            outputs[i++] = displayText[2];
                            infOcn = 0;
                            try {Thread.sleep(Constants.SECS_PER_HOUR / 2);} catch(Exception exception) {}
                            infOcn = 1;
                            runHour++;
                        }
                    }
                    else if(GLAPOptions.run20Down20Line1)
                    {
                        if((t >= (Constants.SECS_PER_MIN * 20)) && (t < ((Constants.SECS_PER_MIN * 20) * 2)))
                        {
                            System.out.println("FAILED LINE 1 (run20Down20Line1), t: " + t);
                            outputs[i++] = "FAILED LINE 1 (run20Down20Line1), t: " + t;
                            displayText[2] = "line1 ";
                            displayText[2] += "infOcn:" + infOcn + " ";
                            displayText[2] += "t:" + t + " ";
                            displayText[2] += "line1:" + assemblyLines[0][0] + assemblyLines[0][1] + assemblyLines[0][2] + assemblyLines[0][3] + assemblyLines[0][4] + assemblyLines[0][5] + " ";
                            displayText[2] += "buf1:" + buffers[0] + " ";
                            displayText[2] += "line2:" + assemblyLines[1][0] + assemblyLines[1][1] + assemblyLines[1][2] + assemblyLines[1][3] + assemblyLines[1][4] + assemblyLines[1][5] + " ";
                            displayText[2] += "buf2:" + buffers[1] + " ";
                            displayText[2] += "line3:" + assemblyLines[2][0] + assemblyLines[2][1] + assemblyLines[2][2] + assemblyLines[2][3] + assemblyLines[2][4] + assemblyLines[2][5] + " ";
                            displayText[2] += "buf3:" + buffers[2] + " ";
                            displayText[2] += "line4:" + assemblyLines[3][0] + assemblyLines[3][1] + assemblyLines[3][2] + assemblyLines[3][3] + assemblyLines[3][4] + assemblyLines[3][5] + " ";
                            displayText[2] += "buf4:" + buffers[3] + " ";
                            displayText[2] += "line5:" + assemblyLines[4][0] + assemblyLines[4][1] + assemblyLines[4][2] + assemblyLines[4][3] + assemblyLines[4][4] + assemblyLines[4][5] + " ";
                            displayText[2] += "output:" + output + " ";
                            System.out.println(displayText[2]);
                            outputs[i++] = displayText[2];
                            try {Thread.sleep(Constants.SECS_PER_MIN * 20);} catch(Exception exception) {}
                        }
                    }
                    else if(GLAPOptions.half_1_6AndAHalf)
                    {
                        if((t >= (Constants.SECS_PER_MIN * 30)) && (t < (Constants.SECS_PER_HOUR)))
                        {
                            System.out.println("FAILED LINE 1 (.5-1-6.5), t: " + t);
                            outputs[i++] = "FAILED LINE 1 (.5-1-6.5), t: " + t;
                            displayText[2] = "line1 ";
                            displayText[2] += "infOcn:" + infOcn + " ";
                            displayText[2] += "t:" + t + " ";
                            displayText[2] += "line1:" + assemblyLines[0][0] + assemblyLines[0][1] + assemblyLines[0][2] + assemblyLines[0][3] + assemblyLines[0][4] + assemblyLines[0][5] + " ";
                            displayText[2] += "buf1:" + buffers[0] + " ";
                            displayText[2] += "line2:" + assemblyLines[1][0] + assemblyLines[1][1] + assemblyLines[1][2] + assemblyLines[1][3] + assemblyLines[1][4] + assemblyLines[1][5] + " ";
                            displayText[2] += "buf2:" + buffers[1] + " ";
                            displayText[2] += "line3:" + assemblyLines[2][0] + assemblyLines[2][1] + assemblyLines[2][2] + assemblyLines[2][3] + assemblyLines[2][4] + assemblyLines[2][5] + " ";
                            displayText[2] += "buf3:" + buffers[2] + " ";
                            displayText[2] += "line4:" + assemblyLines[3][0] + assemblyLines[3][1] + assemblyLines[3][2] + assemblyLines[3][3] + assemblyLines[3][4] + assemblyLines[3][5] + " ";
                            displayText[2] += "buf4:" + buffers[3] + " ";
                            displayText[2] += "line5:" + assemblyLines[4][0] + assemblyLines[4][1] + assemblyLines[4][2] + assemblyLines[4][3] + assemblyLines[4][4] + assemblyLines[4][5] + " ";
                            displayText[2] += "output:" + output + " ";
                            System.out.println(displayText[2]);
                            outputs[i++] = displayText[2];
                            try {Thread.sleep(Constants.SECS_PER_HOUR);} catch(Exception exception) {}
                        }
                    }
                    if(buffers[0] < GLAPOptions.BUFFER_MAX[0] && assemblyLines[0][5] == 1)
                    {
                        assemblyLines[0][5] = 0;
                        buffers[0]++;
                    }
                    else
                    {
                        numOfPartsInLines[0] = assemblyLines[0][0] + assemblyLines[0][1] + assemblyLines[0][2] + assemblyLines[0][3] + assemblyLines[0][4] + assemblyLines[0][5];
                        if(((numOfPartsInLines[0] == 0) && (infOcn > 0)) || ((t > (tPrevs[0] + GLAPOptions.CYCLE_TIME_SECONDS[0]))))
                        {
                            tPrevs[0] = t;
                            rs[0] = new Random().nextDouble();
                            rs[1] = new Random().nextDouble();
                            rs[2] = new Random().nextDouble();
                            rs[3] = new Random().nextDouble();
                            rs[4] = new Random().nextDouble();
                            rs[5] = new Random().nextDouble();
                            stasFailed[0] = ((assemblyLines[0][0] == 1) && (rs[0] > GLAPOptions.rLimits[0]));
                            stasFailed[1] = ((assemblyLines[0][1] == 1) && (rs[1] > GLAPOptions.rLimits[1]));
                            stasFailed[2] = ((assemblyLines[0][2] == 1) && (rs[2] > GLAPOptions.rLimits[2]));
                            stasFailed[3] = ((assemblyLines[0][3] == 1) && (rs[3] > GLAPOptions.rLimits[3]));
                            stasFailed[4] = ((assemblyLines[0][4] == 1) && (rs[4] > GLAPOptions.rLimits[4]));
                            stasFailed[5] = ((assemblyLines[0][5] == 1) && (rs[5] > GLAPOptions.rLimits[5]));
                            if(!GLAPOptions.lineNumDownTimes[0] || (GLAPOptions.lineNumDownTimes[0] && !(stasFailed[0] || stasFailed[1] || stasFailed[2] || stasFailed[3] || stasFailed[4] || stasFailed[5])))
                            {
                                if(buffers[0] < GLAPOptions.BUFFER_MAX[0])
                                {
                                    buffers[0] += assemblyLines[0][5];
                                    assemblyLines[0][5] = assemblyLines[0][4];
                                    assemblyLines[0][4] = assemblyLines[0][3];
                                    assemblyLines[0][3] = assemblyLines[0][2];
                                    assemblyLines[0][2] = assemblyLines[0][1];
                                    assemblyLines[0][1] = assemblyLines[0][0];
                                    assemblyLines[0][0] = infOcn;
                                }
                                displayText[2] = "line1 ";
                                displayText[2] += "infOcn:" + infOcn + " ";
                                displayText[2] += "t:" + tPrevs[0] + " ";
                                displayText[2] += "line1:" + assemblyLines[0][0] + assemblyLines[0][1] + assemblyLines[0][2] + assemblyLines[0][3] + assemblyLines[0][4] + assemblyLines[0][5] + " ";
                                displayText[2] += "buf1:" + buffers[0] + " ";
                                displayText[2] += "line2:" + assemblyLines[1][0] + assemblyLines[1][1] + assemblyLines[1][2] + assemblyLines[1][3] + assemblyLines[1][4] + assemblyLines[1][5] + " ";
                                displayText[2] += "buf2:" + buffers[1] + " ";
                                displayText[2] += "line3:" + assemblyLines[2][0] + assemblyLines[2][1] + assemblyLines[2][2] + assemblyLines[2][3] + assemblyLines[2][4] + assemblyLines[2][5] + " ";
                                displayText[2] += "buf3:" + buffers[2] + " ";
                                displayText[2] += "line4:" + assemblyLines[3][0] + assemblyLines[3][1] + assemblyLines[3][2] + assemblyLines[3][3] + assemblyLines[3][4] + assemblyLines[3][5] + " ";
                                displayText[2] += "buf4:" + buffers[3] + " ";
                                displayText[2] += "line5:" + assemblyLines[4][0] + assemblyLines[4][1] + assemblyLines[4][2] + assemblyLines[4][3] + assemblyLines[4][4] + assemblyLines[4][5] + " ";
                                displayText[2] += "output:" + output + " ";
                                System.out.println(displayText[2]);
                                outputs[i++] = displayText[2];
                            }
                            else
                            {
                                if(stasFailed[0]) {staNumFailed = 1;}
                                else if(stasFailed[1]) {staNumFailed = 2;}
                                else if(stasFailed[2]) {staNumFailed = 3;}
                                else if(stasFailed[3]) {staNumFailed = 4;}
                                else if(stasFailed[4]) {staNumFailed = 5;}
                                else if(stasFailed[5]) {staNumFailed = 6;}
                                System.out.println("FAILED LINE 1, STATION: " + staNumFailed + " t:" + tPrevs[0]);
                                outputs[i++] = "FAILED LINE 1, STATION: " + staNumFailed + " t:" + tPrevs[0];
                                displayText[2] = "line1 ";
                                displayText[2] += "infOcn:" + infOcn + " ";
                                displayText[2] += "t:" + tPrevs[0] + " ";
                                displayText[2] += "line1:" + assemblyLines[0][0] + assemblyLines[0][1] + assemblyLines[0][2] + assemblyLines[0][3] + assemblyLines[0][4] + assemblyLines[0][5] + " ";
                                displayText[2] += "buf1:" + buffers[0] + " ";
                                displayText[2] += "line2:" + assemblyLines[1][0] + assemblyLines[1][1] + assemblyLines[1][2] + assemblyLines[1][3] + assemblyLines[1][4] + assemblyLines[1][5] + " ";
                                displayText[2] += "buf2:" + buffers[1] + " ";
                                displayText[2] += "line3:" + assemblyLines[2][0] + assemblyLines[2][1] + assemblyLines[2][2] + assemblyLines[2][3] + assemblyLines[2][4] + assemblyLines[2][5] + " ";
                                displayText[2] += "buf3:" + buffers[2] + " ";
                                displayText[2] += "line4:" + assemblyLines[3][0] + assemblyLines[3][1] + assemblyLines[3][2] + assemblyLines[3][3] + assemblyLines[3][4] + assemblyLines[3][5] + " ";
                                displayText[2] += "buf4:" + buffers[3] + " ";
                                displayText[2] += "line5:" + assemblyLines[4][0] + assemblyLines[4][1] + assemblyLines[4][2] + assemblyLines[4][3] + assemblyLines[4][4] + assemblyLines[4][5] + " ";
                                displayText[2] += "output:" + output + " ";
                                System.out.println(displayText[2]);
                                outputs[i++] = displayText[2];
                                if(staNumFailed == 1)
                                {
                                    try {Thread.sleep(GLAPOptions.DOWN_TIME);} catch (Exception exception) {}
                                }
                                if(staNumFailed == 2)
                                {
                                    try {Thread.sleep(GLAPOptions.DOWN_TIME);} catch (Exception exception) {}
                                }
                                if(staNumFailed == 3)
                                {
                                    try {Thread.sleep(GLAPOptions.DOWN_TIME);} catch (Exception exception) {}
                                }
                                if(staNumFailed == 4)
                                {
                                    try {Thread.sleep(GLAPOptions.DOWN_TIME);} catch (Exception exception) {}
                                }
                                if(staNumFailed == 5)
                                {
                                    try {Thread.sleep(GLAPOptions.DOWN_TIME);} catch (Exception exception) {}
                                }
                                if(staNumFailed == 6)
                                {
                                    try {Thread.sleep(GLAPOptions.DOWN_TIME);} catch (Exception exception) {}
                                }
                                stasFailed[0] = false;
                                stasFailed[1] = false;
                                stasFailed[2] = false;
                                stasFailed[3] = false;
                                stasFailed[4] = false;
                                stasFailed[5] = false;
                                staNumFailed = 0;
                            }
                        }
                    }
                }
            }
        };

        final TimerTask line2TimerTask = new TimerTask()
        {
            @Override
            public void run()
            {
                numOfPartsInLines[1] = assemblyLines[1][0] + assemblyLines[1][1] + assemblyLines[1][2] + assemblyLines[1][3] + assemblyLines[1][4] + assemblyLines[1][5];
                if(GLAPOptions.half_1_6_AndAHalfLine2)
                {
                    if ((t >= (Constants.SECS_PER_MIN * 30)) && (t < (Constants.SECS_PER_HOUR)))
                    {
                        System.out.println("FAILED LINE 2 (.5-1-6.5), t: " + t);
                        outputs[i++] = "FAILED LINE 2 (.5-1-6.5), t: " + t;
                        displayText[3] = "line2 ";
                        displayText[3] += "infOcn:" + infOcn + " ";
                        displayText[3] += "t:" + t + " ";
                        displayText[3] += "line1:" + assemblyLines[0][0] + assemblyLines[0][1] + assemblyLines[0][2] + assemblyLines[0][3] + assemblyLines[0][4] + assemblyLines[0][5] + " ";
                        displayText[3] += "buf1:" + buffers[0] + " ";
                        displayText[3] += "line2:" + assemblyLines[1][0] + assemblyLines[1][1] + assemblyLines[1][2] + assemblyLines[1][3] + assemblyLines[1][4] + assemblyLines[1][5] + " ";
                        displayText[3] += "buf2:" + buffers[1] + " ";
                        displayText[3] += "line3:" + assemblyLines[2][0] + assemblyLines[2][1] + assemblyLines[2][2] + assemblyLines[2][3] + assemblyLines[2][4] + assemblyLines[2][5] + " ";
                        displayText[3] += "buf3:" + buffers[2] + " ";
                        displayText[3] += "line4:" + assemblyLines[3][0] + assemblyLines[3][1] + assemblyLines[3][2] + assemblyLines[3][3] + assemblyLines[3][4] + assemblyLines[3][5] + " ";
                        displayText[3] += "buf4:" + buffers[3] + " ";
                        displayText[3] += "line5:" + assemblyLines[4][0] + assemblyLines[4][1] + assemblyLines[4][2] + assemblyLines[4][3] + assemblyLines[4][4] + assemblyLines[4][5] + " ";
                        displayText[3] += "output:" + output + " ";
                        System.out.println(displayText[3]);
                        outputs[i++] = displayText[3];
                        try {Thread.sleep(Constants.SECS_PER_HOUR);} catch(Exception exception) {}
                    }
                }
                if(((numOfPartsInLines[1] == 0) && (buffers[0] > 0)) || ((t >= (tPrevs[1] + GLAPOptions.CYCLE_TIME_SECONDS[1]))))
                {
                    tPrevs[1] = t;
                    rs[6] = new Random().nextDouble();
                    rs[7] = new Random().nextDouble();
                    rs[8] = new Random().nextDouble();
                    rs[9] = new Random().nextDouble();
                    rs[10] = new Random().nextDouble();
                    rs[11] = new Random().nextDouble();
                    stasFailed[6] = ((assemblyLines[1][0] == 1) && (rs[6] > GLAPOptions.rLimits[6]));
                    stasFailed[7] = ((assemblyLines[1][1] == 1) && (rs[7] > GLAPOptions.rLimits[7]));
                    stasFailed[8] = ((assemblyLines[1][2] == 1) && (rs[8] > GLAPOptions.rLimits[8]));
                    stasFailed[9] = ((assemblyLines[1][3] == 1) && (rs[9] > GLAPOptions.rLimits[9]));
                    stasFailed[10] = ((assemblyLines[1][4] == 1) && (rs[10] > GLAPOptions.rLimits[10]));
                    stasFailed[11] = ((assemblyLines[1][5] == 1) && (rs[11] > GLAPOptions.rLimits[11]));
                    if(!GLAPOptions.lineNumDownTimes[1] || (GLAPOptions.lineNumDownTimes[1] && !(stasFailed[6] || stasFailed[7] || stasFailed[8] || stasFailed[9] || stasFailed[10] || stasFailed[11])))
                    {
                        if(buffers[1] < GLAPOptions.BUFFER_MAX[1])
                        {
                            buffers[1] += assemblyLines[1][5];
                            assemblyLines[1][5] = assemblyLines[1][4];
                            assemblyLines[1][4] = assemblyLines[1][3];
                            assemblyLines[1][3] = assemblyLines[1][2];
                            assemblyLines[1][2] = assemblyLines[1][1];
                            assemblyLines[1][1] = assemblyLines[1][0];
                            assemblyLines[1][0] = 0;
                            if(buffers[0] > 0)
                            {
                                buffers[0] -= 1;
                                assemblyLines[1][0] = 1;
                            }
                        }
                        displayText[3] = "line2 ";
                        displayText[3] += "infOcn:" + infOcn + " ";
                        displayText[3] += "t:" + tPrevs[1] + " ";
                        displayText[3] += "line1:" + assemblyLines[0][0] + assemblyLines[0][1] + assemblyLines[0][2] + assemblyLines[0][3] + assemblyLines[0][4] + assemblyLines[0][5] + " ";
                        displayText[3] += "buf1:" + buffers[0] + " ";
                        displayText[3] += "line2:" + assemblyLines[1][0] + assemblyLines[1][1] + assemblyLines[1][2] + assemblyLines[1][3] + assemblyLines[1][4] + assemblyLines[1][5] + " ";
                        displayText[3] += "buf2:" + buffers[1] + " ";
                        displayText[3] += "line3:" + assemblyLines[2][0] + assemblyLines[2][1] + assemblyLines[2][2] + assemblyLines[2][3] + assemblyLines[2][4] + assemblyLines[2][5] + " ";
                        displayText[3] += "buf3:" + buffers[2] + " ";
                        displayText[3] += "line4:" + assemblyLines[3][0] + assemblyLines[3][1] + assemblyLines[3][2] + assemblyLines[3][3] + assemblyLines[3][4] + assemblyLines[3][5] + " ";
                        displayText[3] += "buf4:" + buffers[3] + " ";
                        displayText[3] += "line5:" + assemblyLines[4][0] + assemblyLines[4][1] + assemblyLines[4][2] + assemblyLines[4][3] + assemblyLines[4][4] + assemblyLines[4][5] + " ";
                        displayText[3] += "output:" + output + " ";
                        System.out.println(displayText[3]);
                        outputs[i++] = displayText[3];
                    }
                    else
                    {
                        if(stasFailed[6]) {staNumFailed = 7;}
                        else if(stasFailed[7]) {staNumFailed = 8;}
                        else if(stasFailed[8]) {staNumFailed = 9;}
                        else if(stasFailed[9]) {staNumFailed = 10;}
                        else if(stasFailed[10]) {staNumFailed = 11;}
                        else if(stasFailed[11]) {staNumFailed = 12;}
                        System.out.println("FAILED LINE 2, STATION: " + staNumFailed + " t:" + tPrevs[1]);
                        outputs[i++] = "FAILED LINE 2, STATION: " + staNumFailed + " t:" + tPrevs[1];
                        displayText[3] = "line2 ";
                        displayText[3] += "infOcn:" + infOcn + " ";
                        displayText[3] += "t:" + tPrevs[1] + " ";
                        displayText[3] += "line1:" + assemblyLines[0][0] + assemblyLines[0][1] + assemblyLines[0][2] + assemblyLines[0][3] + assemblyLines[0][4] + assemblyLines[0][5] + " ";
                        displayText[3] += "buf1:" + buffers[0] + " ";
                        displayText[3] += "line2:" + assemblyLines[1][0] + assemblyLines[1][1] + assemblyLines[1][2] + assemblyLines[1][3] + assemblyLines[1][4] + assemblyLines[1][5] + " ";
                        displayText[3] += "buf2:" + buffers[1] + " ";
                        displayText[3] += "line3:" + assemblyLines[2][0] + assemblyLines[2][1] + assemblyLines[2][2] + assemblyLines[2][3] + assemblyLines[2][4] + assemblyLines[2][5] + " ";
                        displayText[3] += "buf3:" + buffers[2] + " ";
                        displayText[3] += "line4:" + assemblyLines[3][0] + assemblyLines[3][1] + assemblyLines[3][2] + assemblyLines[3][3] + assemblyLines[3][4] + assemblyLines[3][5] + " ";
                        displayText[3] += "buf4:" + buffers[3] + " ";
                        displayText[3] += "line5:" + assemblyLines[4][0] + assemblyLines[4][1] + assemblyLines[4][2] + assemblyLines[4][3] + assemblyLines[4][4] + assemblyLines[4][5] + " ";
                        displayText[3] += "output:" + output + " ";
                        System.out.println(displayText[3]);
                        outputs[i++] = displayText[3];
                        if(staNumFailed == 7)
                        {
                            try {Thread.sleep(GLAPOptions.DOWN_TIME);} catch(Exception exception) {}
                        }
                        if(staNumFailed == 8)
                        {
                            try {Thread.sleep(GLAPOptions.DOWN_TIME);} catch(Exception exception) {}
                        }
                        if(staNumFailed == 9)
                        {
                            try {Thread.sleep(GLAPOptions.DOWN_TIME);} catch(Exception exception) {}
                        }
                        if(staNumFailed == 10)
                        {
                            try {Thread.sleep(GLAPOptions.DOWN_TIME);} catch(Exception exception) {}
                        }
                        if(staNumFailed == 11)
                        {
                            try {Thread.sleep(GLAPOptions.DOWN_TIME);} catch(Exception exception) {}
                        }
                        if(staNumFailed == 12)
                        {
                            try {Thread.sleep(GLAPOptions.DOWN_TIME);} catch(Exception exception) {}
                        }
                        stasFailed[6] = false;
                        stasFailed[7] = false;
                        stasFailed[8] = false;
                        stasFailed[9] = false;
                        stasFailed[10] = false;
                        stasFailed[11] = false;
                        staNumFailed = 0;
                    }
                }
            }
        };

        final TimerTask line3TimerTask = new TimerTask()
        {
            @Override
            public void run()
            {
                numOfPartsInLines[2] = assemblyLines[2][0] + assemblyLines[2][1] + assemblyLines[2][2] + assemblyLines[2][3] + assemblyLines[2][4] + assemblyLines[2][5];
                if(((numOfPartsInLines[2] == 0) && (buffers[1] > 0)) || ((t >= (tPrevs[2] + GLAPOptions.CYCLE_TIME_SECONDS[2]))))
                {
                    tPrevs[2] = t;
                    rs[12] = new Random().nextDouble();
                    rs[13] = new Random().nextDouble();
                    rs[14] = new Random().nextDouble();
                    rs[15] = new Random().nextDouble();
                    rs[16] = new Random().nextDouble();
                    rs[17] = new Random().nextDouble();
                    stasFailed[12] = ((assemblyLines[2][0] == 1) && (rs[12] > GLAPOptions.rLimits[12]));
                    stasFailed[13] = ((assemblyLines[2][1] == 1) && (rs[13] > GLAPOptions.rLimits[13]));
                    stasFailed[14] = ((assemblyLines[2][2] == 1) && (rs[14] > GLAPOptions.rLimits[14]));
                    stasFailed[15] = ((assemblyLines[2][3] == 1) && (rs[15] > GLAPOptions.rLimits[15]));
                    stasFailed[16] = ((assemblyLines[2][4] == 1) && (rs[16] > GLAPOptions.rLimits[16]));
                    stasFailed[17] = ((assemblyLines[2][5] == 1) && (rs[17] > GLAPOptions.rLimits[17]));
                    if(!GLAPOptions.lineNumDownTimes[2] || (GLAPOptions.lineNumDownTimes[2] && !(stasFailed[12] || stasFailed[13] || stasFailed[14] || stasFailed[15] || stasFailed[16] || stasFailed[17])))
                    {
                        if(buffers[2] < GLAPOptions.BUFFER_MAX[2])
                        {
                            buffers[2] += assemblyLines[2][5];
                            assemblyLines[2][5] = assemblyLines[2][4];
                            assemblyLines[2][4] = assemblyLines[2][3];
                            assemblyLines[2][3] = assemblyLines[2][2];
                            assemblyLines[2][2] = assemblyLines[2][1];
                            assemblyLines[2][1] = assemblyLines[2][0];
                            assemblyLines[2][0] = 0;
                            if(buffers[1] > 0)
                            {
                                buffers[1] -= 1;
                                assemblyLines[2][0] = 1;
                            }
                        }
                        displayText[4] = "line3 ";
                        displayText[4] += "infOcn:" + infOcn + " ";
                        displayText[4] += "t:" + tPrevs[2] + " ";
                        displayText[4] += "line1:" + assemblyLines[0][0] + assemblyLines[0][1] + assemblyLines[0][2] + assemblyLines[0][3] + assemblyLines[0][4] + assemblyLines[0][5] + " ";
                        displayText[4] += "buf1:" + buffers[0] + " ";
                        displayText[4] += "line2:" + assemblyLines[1][0] + assemblyLines[1][1] + assemblyLines[1][2] + assemblyLines[1][3] + assemblyLines[1][4] + assemblyLines[1][5] + " ";
                        displayText[4] += "buf2:" + buffers[1] + " ";
                        displayText[4] += "line3:" + assemblyLines[2][0] + assemblyLines[2][1] + assemblyLines[2][2] + assemblyLines[2][3] + assemblyLines[2][4] + assemblyLines[2][5] + " ";
                        displayText[4] += "buf3:" + buffers[2] + " ";
                        displayText[4] += "line4:" + assemblyLines[3][0] + assemblyLines[3][1] + assemblyLines[3][2] + assemblyLines[3][3] + assemblyLines[3][4] + assemblyLines[3][5] + " ";
                        displayText[4] += "buf4:" + buffers[3] + " ";
                        displayText[4] += "line5:" + assemblyLines[4][0] + assemblyLines[4][1] + assemblyLines[4][2] + assemblyLines[4][3] + assemblyLines[4][4] + assemblyLines[4][5] + " ";
                        displayText[4] += "output:" + output + " ";
                        System.out.println(displayText[4]);
                        outputs[i++] = displayText[4];
                    }
                    else
                    {
                        if(stasFailed[12]) {staNumFailed = 13;}
                        else if(stasFailed[13]) {staNumFailed = 14;}
                        else if(stasFailed[14]) {staNumFailed = 15;}
                        else if(stasFailed[15]) {staNumFailed = 16;}
                        else if(stasFailed[16]) {staNumFailed = 17;}
                        else if(stasFailed[17]) {staNumFailed = 18;}
                        System.out.println("FAILED LINE 3, STATION: " + staNumFailed + " t:" + tPrevs[2]);
                        outputs[i++] = "FAILED LINE 3, STATION: " + staNumFailed + " t:" + tPrevs[2];
                        displayText[4] = "line3 ";
                        displayText[4] += "infOcn:" + infOcn + " ";
                        displayText[4] += "t:" + tPrevs[2] + " ";
                        displayText[4] += "line1:" + assemblyLines[0][0] + assemblyLines[0][1] + assemblyLines[0][2] + assemblyLines[0][3] + assemblyLines[0][4] + assemblyLines[0][5] + " ";
                        displayText[4] += "buf1:" + buffers[0] + " ";
                        displayText[4] += "line2:" + assemblyLines[1][0] + assemblyLines[1][1] + assemblyLines[1][2] + assemblyLines[1][3] + assemblyLines[1][4] + assemblyLines[1][5] + " ";
                        displayText[4] += "buf2:" + buffers[1] + " ";
                        displayText[4] += "line3:" + assemblyLines[2][0] + assemblyLines[2][1] + assemblyLines[2][2] + assemblyLines[2][3] + assemblyLines[2][4] + assemblyLines[2][5] + " ";
                        displayText[4] += "buf3:" + buffers[2] + " ";
                        displayText[4] += "line4:" + assemblyLines[3][0] + assemblyLines[3][1] + assemblyLines[3][2] + assemblyLines[3][3] + assemblyLines[3][4] + assemblyLines[3][5] + " ";
                        displayText[4] += "buf4:" + buffers[3] + " ";
                        displayText[4] += "line5:" + assemblyLines[4][0] + assemblyLines[4][1] + assemblyLines[4][2] + assemblyLines[4][3] + assemblyLines[4][4] + assemblyLines[4][5] + " ";
                        displayText[4] += "output:" + output + " ";
                        System.out.println(displayText[4]);
                        outputs[i++] = displayText[4];
                        if(staNumFailed == 13)
                        {
                            try {Thread.sleep(GLAPOptions.DOWN_TIME);} catch(Exception exception) {}
                        }
                        if(staNumFailed == 14)
                        {
                            try {Thread.sleep(GLAPOptions.DOWN_TIME);} catch(Exception exception) {}
                        }
                        if(staNumFailed == 15)
                        {
                            try {Thread.sleep(GLAPOptions.DOWN_TIME);} catch(Exception exception) {}
                        }
                        if(staNumFailed == 16)
                        {
                            try {Thread.sleep(GLAPOptions.DOWN_TIME);} catch(Exception exception) {}
                        }
                        if(staNumFailed == 17)
                        {
                            try {Thread.sleep(GLAPOptions.DOWN_TIME);} catch(Exception exception) {}
                        }
                        if(staNumFailed == 18)
                        {
                            try {Thread.sleep(GLAPOptions.DOWN_TIME);} catch(Exception exception) {}
                        }
                        stasFailed[12] = false;
                        stasFailed[13] = false;
                        stasFailed[14] = false;
                        stasFailed[15] = false;
                        stasFailed[16] = false;
                        stasFailed[17] = false;
                        staNumFailed = 0;
                    }
                }
            }
        };

        final TimerTask line4TimerTask = new TimerTask()
        {
            @Override
            public void run()
            {
                numOfPartsInLines[3] = assemblyLines[3][0] + assemblyLines[3][1] + assemblyLines[3][2] + assemblyLines[3][3] + assemblyLines[3][4] + assemblyLines[3][5];
                if(((numOfPartsInLines[3] == 0) && (buffers[2] > 0)) || ((t >= (tPrevs[3] + GLAPOptions.CYCLE_TIME_SECONDS[3]))))
                {
                    tPrevs[3] = t;
                    rs[18] = new Random().nextDouble();
                    rs[19] = new Random().nextDouble();
                    rs[20] = new Random().nextDouble();
                    rs[21] = new Random().nextDouble();
                    rs[22] = new Random().nextDouble();
                    rs[23] = new Random().nextDouble();
                    stasFailed[18] = ((assemblyLines[3][0] == 1) && (rs[18] > GLAPOptions.rLimits[18]));
                    stasFailed[19] = ((assemblyLines[3][1] == 1) && (rs[19] > GLAPOptions.rLimits[19]));
                    stasFailed[20] = ((assemblyLines[3][2] == 1) && (rs[20] > GLAPOptions.rLimits[20]));
                    stasFailed[21] = ((assemblyLines[3][3] == 1) && (rs[21] > GLAPOptions.rLimits[21]));
                    stasFailed[22] = ((assemblyLines[3][4] == 1) && (rs[22] > GLAPOptions.rLimits[22]));
                    stasFailed[23] = ((assemblyLines[3][5] == 1) && (rs[23] > GLAPOptions.rLimits[23]));
                    if(!GLAPOptions.lineNumDownTimes[3] || (GLAPOptions.lineNumDownTimes[3] && !(stasFailed[18] || stasFailed[19] || stasFailed[20] || stasFailed[21] || stasFailed[22] || stasFailed[23])))
                    {
                        if(buffers[3] < GLAPOptions.BUFFER_MAX[3])
                        {
                            buffers[3] += assemblyLines[3][5];
                            assemblyLines[3][5] = assemblyLines[3][4];
                            assemblyLines[3][4] = assemblyLines[3][3];
                            assemblyLines[3][3] = assemblyLines[3][2];
                            assemblyLines[3][2] = assemblyLines[3][1];
                            assemblyLines[3][1] = assemblyLines[3][0];
                            assemblyLines[3][0] = 0;
                            if(buffers[2] > 0)
                            {
                                buffers[2] -= 1;
                                assemblyLines[3][0] = 1;
                            }
                        }
                        displayText[5] = "line4 ";
                        displayText[5] += "infOcn:" + infOcn + " ";
                        displayText[5] += "t:" + tPrevs[3] + " ";
                        displayText[5] += "line1:" + assemblyLines[0][0] + assemblyLines[0][1] + assemblyLines[0][2] + assemblyLines[0][3] + assemblyLines[0][4] + assemblyLines[0][5] + " ";
                        displayText[5] += "buf1:" + buffers[0] + " ";
                        displayText[5] += "line2:" + assemblyLines[1][0] + assemblyLines[1][1] + assemblyLines[1][2] + assemblyLines[1][3] + assemblyLines[1][4] + assemblyLines[1][5] + " ";
                        displayText[5] += "buf2:" + buffers[1] + " ";
                        displayText[5] += "line3:" + assemblyLines[2][0] + assemblyLines[2][1] + assemblyLines[2][2] + assemblyLines[2][3] + assemblyLines[2][4] + assemblyLines[2][5] + " ";
                        displayText[5] += "buf3:" + buffers[2] + " ";
                        displayText[5] += "line4:" + assemblyLines[3][0] + assemblyLines[3][1] + assemblyLines[3][2] + assemblyLines[3][3] + assemblyLines[3][4] + assemblyLines[3][5] + " ";
                        displayText[5] += "buf4:" + buffers[3] + " ";
                        displayText[5] += "line5:" + assemblyLines[4][0] + assemblyLines[4][1] + assemblyLines[4][2] + assemblyLines[4][3] + assemblyLines[4][4] + assemblyLines[4][5] + " ";
                        displayText[5] += "output:" + output + " ";
                        System.out.println(displayText[5]);
                        outputs[i++] = displayText[5];
                    }
                    else
                    {
                        if(stasFailed[18]) {staNumFailed = 19;}
                        else if(stasFailed[19]) {staNumFailed = 20;}
                        else if(stasFailed[20]) {staNumFailed = 21;}
                        else if(stasFailed[21]) {staNumFailed = 22;}
                        else if(stasFailed[22]) {staNumFailed = 23;}
                        else if(stasFailed[23]) {staNumFailed = 24;}
                        System.out.println("FAILED LINE 4, STATION: " + staNumFailed + " t:" + tPrevs[3]);
                        outputs[i++] = "FAILED LINE 4, STATION: " + staNumFailed + " t:" + tPrevs[3];
                        displayText[5] = "line4 ";
                        displayText[5] += "infOcn:" + infOcn + " ";
                        displayText[5] += "t:" + tPrevs[3] + " ";
                        displayText[5] += "line1:" + assemblyLines[0][0] + assemblyLines[0][1] + assemblyLines[0][2] + assemblyLines[0][3] + assemblyLines[0][4] + assemblyLines[0][5] + " ";
                        displayText[5] += "buf1:" + buffers[0] + " ";
                        displayText[5] += "line2:" + assemblyLines[1][0] + assemblyLines[1][1] + assemblyLines[1][2] + assemblyLines[1][3] + assemblyLines[1][4] + assemblyLines[1][5] + " ";
                        displayText[5] += "buf2:" + buffers[1] + " ";
                        displayText[5] += "line3:" + assemblyLines[2][0] + assemblyLines[2][1] + assemblyLines[2][2] + assemblyLines[2][3] + assemblyLines[2][4] + assemblyLines[2][5] + " ";
                        displayText[5] += "buf3:" + buffers[2] + " ";
                        displayText[5] += "line4:" + assemblyLines[3][0] + assemblyLines[3][1] + assemblyLines[3][2] + assemblyLines[3][3] + assemblyLines[3][4] + assemblyLines[3][5] + " ";
                        displayText[5] += "buf4:" + buffers[3] + " ";
                        displayText[5] += "line5:" + assemblyLines[4][0] + assemblyLines[4][1] + assemblyLines[4][2] + assemblyLines[4][3] + assemblyLines[4][4] + assemblyLines[4][5] + " ";
                        displayText[5] += "output:" + output + " ";
                        System.out.println(displayText[5]);
                        outputs[i++] = displayText[5];
                        if(staNumFailed == 19)
                        {
                            try {Thread.sleep(GLAPOptions.DOWN_TIME);} catch(Exception exception) {}
                        }
                        if(staNumFailed == 20)
                        {
                            try {Thread.sleep(GLAPOptions.DOWN_TIME);} catch(Exception exception) {}
                        }
                        if(staNumFailed == 21)
                        {
                            try {Thread.sleep(GLAPOptions.DOWN_TIME);} catch(Exception exception) {}
                        }
                        if(staNumFailed == 22)
                        {
                            try {Thread.sleep(GLAPOptions.DOWN_TIME);} catch(Exception exception) {}
                        }
                        if(staNumFailed == 23)
                        {
                            try {Thread.sleep(GLAPOptions.DOWN_TIME);} catch(Exception exception) {}
                        }
                        if(staNumFailed == 24)
                        {
                            try {Thread.sleep(GLAPOptions.DOWN_TIME);} catch(Exception exception) {}
                        }
                        stasFailed[18] = false;
                        stasFailed[19] = false;
                        stasFailed[20] = false;
                        stasFailed[21] = false;
                        stasFailed[22] = false;
                        stasFailed[23] = false;
                        staNumFailed = 0;
                    }
                }
            }
        };

        final TimerTask line5TimerTask = new TimerTask()
        {
            @Override
            public void run()
            {
            numOfPartsInLines[4] = assemblyLines[4][0] + assemblyLines[4][1] + assemblyLines[4][2] + assemblyLines[4][3] + assemblyLines[4][4] + assemblyLines[4][5];
            if(GLAPOptions.half_1_6_AndAHalfLine5)
            {
                if((t >= (Constants.SECS_PER_MIN * 30)) && (t < (Constants.SECS_PER_HOUR)))
                {
                    System.out.println("FAILED LINE 5 (.5-1-6.5), t: " + t);
                    outputs[i++] = "FAILED LINE 5 (.5-1-6.5), t: " + t;
                    displayText[6] = "line5 ";
                    displayText[6] += "infOcn:" + infOcn + " ";
                    displayText[6] += "t:" + t + " ";
                    displayText[6] += "line1:" + assemblyLines[0][0] + assemblyLines[0][1] + assemblyLines[0][2] + assemblyLines[0][3] + assemblyLines[0][4] + assemblyLines[0][5] + " ";
                    displayText[6] += "buf1:" + buffers[0] + " ";
                    displayText[6] += "line2:" + assemblyLines[1][0] + assemblyLines[1][1] + assemblyLines[1][2] + assemblyLines[1][3] + assemblyLines[1][4] + assemblyLines[1][5] + " ";
                    displayText[6] += "buf2:" + buffers[1] + " ";
                    displayText[6] += "line3:" + assemblyLines[2][0] + assemblyLines[2][1] + assemblyLines[2][2] + assemblyLines[2][3] + assemblyLines[2][4] + assemblyLines[2][5] + " ";
                    displayText[6] += "buf3:" + buffers[2] + " ";
                    displayText[6] += "line4:" + assemblyLines[3][0] + assemblyLines[3][1] + assemblyLines[3][2] + assemblyLines[3][3] + assemblyLines[3][4] + assemblyLines[3][5] + " ";
                    displayText[6] += "buf4:" + buffers[3] + " ";
                    displayText[6] += "line5:" + assemblyLines[4][0] + assemblyLines[4][1] + assemblyLines[4][2] + assemblyLines[4][3] + assemblyLines[4][4] + assemblyLines[4][5] + " ";
                    displayText[6] += "output:" + output + " ";
                    System.out.println(displayText[6]);
                    outputs[i++] = displayText[6];
                    try {Thread.sleep(Constants.SECS_PER_HOUR);} catch(Exception exception) {}
                }
            }
            if(((numOfPartsInLines[4] == 0) && (buffers[3] > 0)) || ((t >= (tPrevs[4] + GLAPOptions.CYCLE_TIME_SECONDS[4]))))
            {
                tPrevs[4] = t;
                rs[24] = new Random().nextDouble();
                rs[25] = new Random().nextDouble();
                rs[26] = new Random().nextDouble();
                rs[27] = new Random().nextDouble();
                rs[28] = new Random().nextDouble();
                rs[29] = new Random().nextDouble();
                stasFailed[24] = ((assemblyLines[4][0] == 1) && (rs[24] > GLAPOptions.rLimits[24]));
                stasFailed[25] = ((assemblyLines[4][1] == 1) && (rs[25] > GLAPOptions.rLimits[25]));
                stasFailed[26] = ((assemblyLines[4][2] == 1) && (rs[26] > GLAPOptions.rLimits[26]));
                stasFailed[27] = ((assemblyLines[4][3] == 1) && (rs[27] > GLAPOptions.rLimits[27]));
                stasFailed[28] = ((assemblyLines[4][4] == 1) && (rs[28] > GLAPOptions.rLimits[28]));
                stasFailed[29] = ((assemblyLines[4][5] == 1) && (rs[29] > GLAPOptions.rLimits[29]));
                if(!GLAPOptions.lineNumDownTimes[4] || (GLAPOptions.lineNumDownTimes[4] && !(stasFailed[24] || stasFailed[25] || stasFailed[26] || stasFailed[27] || stasFailed[28] || stasFailed[29])))
                {
                    output += assemblyLines[4][5];
                    assemblyLines[4][5] = assemblyLines[4][4];
                    assemblyLines[4][4] = assemblyLines[4][3];
                    assemblyLines[4][3] = assemblyLines[4][2];
                    assemblyLines[4][2] = assemblyLines[4][1];
                    assemblyLines[4][1] = assemblyLines[4][0];
                    assemblyLines[4][0] = 0;
                    if(buffers[3] > 0)
                    {
                        buffers[3] -= 1;
                        assemblyLines[4][0] = 1;
                    }
                    displayText[6] = "line5 ";
                    displayText[6] += "infOcn:" + infOcn + " ";
                    displayText[6] += "t:" + tPrevs[4] + " ";
                    displayText[6] += "line1:" + assemblyLines[0][0] + assemblyLines[0][1] + assemblyLines[0][2] + assemblyLines[0][3] + assemblyLines[0][4] + assemblyLines[0][5] + " ";
                    displayText[6] += "buf1:" + buffers[0] + " ";
                    displayText[6] += "line2:" + assemblyLines[1][0] + assemblyLines[1][1] + assemblyLines[1][2] + assemblyLines[1][3] + assemblyLines[1][4] + assemblyLines[1][5] + " ";
                    displayText[6] += "buf2:" + buffers[1] + " ";
                    displayText[6] += "line3:" + assemblyLines[2][0] + assemblyLines[2][1] + assemblyLines[2][2] + assemblyLines[2][3] + assemblyLines[2][4] + assemblyLines[2][5] + " ";
                    displayText[6] += "buf3:" + buffers[2] + " ";
                    displayText[6] += "line4:" + assemblyLines[3][0] + assemblyLines[3][1] + assemblyLines[3][2] + assemblyLines[3][3] + assemblyLines[3][4] + assemblyLines[3][5] + " ";
                    displayText[6] += "buf4:" + buffers[3] + " ";
                    displayText[6] += "line5:" + assemblyLines[4][0] + assemblyLines[4][1] + assemblyLines[4][2] + assemblyLines[4][3] + assemblyLines[4][4] + assemblyLines[4][5] + " ";
                    displayText[6] += "output:" + output + " ";
                    System.out.println(displayText[6]);
                    outputs[i++] = displayText[6];
                }
                else
                {
                    if(stasFailed[24]) {staNumFailed = 25;}
                    else if(stasFailed[25]) {staNumFailed = 26;}
                    else if(stasFailed[26]) {staNumFailed = 27;}
                    else if(stasFailed[27]) {staNumFailed = 28;}
                    else if(stasFailed[28]) {staNumFailed = 29;}
                    else if(stasFailed[29]) {staNumFailed = 30;}
                    System.out.println("FAILED LINE 5, STATION: " + staNumFailed + " t:" + tPrevs[4]);
                    outputs[i++] = "FAILED LINE 5, STATION: " + staNumFailed + " t:" + tPrevs[4];
                    displayText[6] = "line5 ";
                    displayText[6] += "infOcn:" + infOcn + " ";
                    displayText[6] += "t:" + tPrevs[4] + " ";
                    displayText[6] += "line1:" + assemblyLines[0][0] + assemblyLines[0][1] + assemblyLines[0][2] + assemblyLines[0][3] + assemblyLines[0][4] + assemblyLines[0][5] + " ";
                    displayText[6] += "buf1:" + buffers[0] + " ";
                    displayText[6] += "line2:" + assemblyLines[1][0] + assemblyLines[1][1] + assemblyLines[1][2] + assemblyLines[1][3] + assemblyLines[1][4] + assemblyLines[1][5] + " ";
                    displayText[6] += "buf2:" + buffers[1] + " ";
                    displayText[6] += "line3:" + assemblyLines[2][0] + assemblyLines[2][1] + assemblyLines[2][2] + assemblyLines[2][3] + assemblyLines[2][4] + assemblyLines[2][5] + " ";
                    displayText[6] += "buf3:" + buffers[2] + " ";
                    displayText[6] += "line4:" + assemblyLines[3][0] + assemblyLines[3][1] + assemblyLines[3][2] + assemblyLines[3][3] + assemblyLines[3][4] + assemblyLines[3][5] + " ";
                    displayText[6] += "buf4:" + buffers[3] + " ";
                    displayText[6] += "line5:" + assemblyLines[4][0] + assemblyLines[4][1] + assemblyLines[4][2] + assemblyLines[4][3] + assemblyLines[4][4] + assemblyLines[4][5] + " ";
                    displayText[6] += "output:" + output + " ";
                    System.out.println(displayText[6]);
                    outputs[i++] = displayText[6];
                    if(staNumFailed == 25)
                    {
                        try {Thread.sleep(GLAPOptions.DOWN_TIME);} catch(Exception exception) {}
                    }
                    if(staNumFailed == 26)
                    {
                        try {Thread.sleep(GLAPOptions.DOWN_TIME);} catch(Exception exception) {}
                    }
                    if(staNumFailed == 27)
                    {
                        try {Thread.sleep(GLAPOptions.DOWN_TIME);} catch(Exception exception) {}
                    }
                    if(staNumFailed == 28)
                    {
                        try {Thread.sleep(GLAPOptions.DOWN_TIME);} catch(Exception exception) {}
                    }
                    if(staNumFailed == 29)
                    {
                        try {Thread.sleep(GLAPOptions.DOWN_TIME);} catch(Exception exception) {}
                    }
                    if(staNumFailed == 30)
                    {
                        try {Thread.sleep(GLAPOptions.DOWN_TIME);} catch(Exception exception) {}
                    }
                    stasFailed[24] = false;
                    stasFailed[25] = false;
                    stasFailed[26] = false;
                    stasFailed[27] = false;
                    stasFailed[28] = false;
                    stasFailed[29] = false;
                    staNumFailed = 0;
                }
            }
            }
        };

        masterTimer = new Timer("MasterTimer");
        masterTimer.scheduleAtFixedRate(masterTimerTask, 0, 1);

        infOcnTimer = new Timer("InfOcnTimer");
        infOcnTimer.scheduleAtFixedRate(infOcnTimerTask, 0, 1);

        line1Timer = new Timer("Line1Timer");
        line1Timer.scheduleAtFixedRate(line1TimerTask, 0, 1);

        line2Timer = new Timer("Line2Timer");
        line2Timer.scheduleAtFixedRate(line2TimerTask, 0, 1);

        line3Timer = new Timer("Line3Timer");
        line3Timer.scheduleAtFixedRate(line3TimerTask, 0, 1);

        line4Timer = new Timer("Line4Timer");
        line4Timer.scheduleAtFixedRate(line4TimerTask, 0, 1);

        line5Timer = new Timer("Line5Timer");
        line5Timer.scheduleAtFixedRate(line5TimerTask, 0, 1);
    }
}
