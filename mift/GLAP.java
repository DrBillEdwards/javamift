package mift;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class GLAP
{
    static Timer masterTimer, infOcnTimer, line1Timer, line2Timer, line3Timer, line4Timer;
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

        TimerTask infOcnTimerTask = new TimerTask()
        {
            @Override
            public void run()
            {
                if(GLAPOptions.run20Down20InfOcn)
                {
                    if((t > (Constants.SECS_PER_MIN * 20)) && (t < ((Constants.SECS_PER_MIN * 20) * 2)))
                    {
                        displayText = "FAILED INFINITE OCEAN t: " + t;
                        System.out.println(displayText);
                        outputs[i++] = displayText;
                        infOcn = 0;
                        try {Thread.sleep(Constants.SECS_PER_MIN * 20);} catch(Exception exception) {}
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
                    displayText = "Deliberate 1/2 hour down time line 1";
                    System.out.println(displayText);
                    outputs[i++] = displayText;
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
                            displayText = "Deliberate 1/2 hour down time line 1, hour " + runHour;
                            System.out.println(displayText);
                            outputs[i++] = displayText;
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
                            displayText = "FAILED INFINITE OCEAN " + runHour;
                            System.out.println(displayText);
                            outputs[i++] = displayText;
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
                            displayText = "FAILED INFINITE OCEAN t: " + t + " DOWN LINE 1";
                            System.out.println(displayText);
                            outputs[i++] = displayText;
                            try {Thread.sleep(Constants.SECS_PER_MIN * 20);} catch (Exception exception) {}
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
                            if(!(stasFailed[0] || stasFailed[1] || stasFailed[2] || stasFailed[3] || stasFailed[4] || stasFailed[5]))
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
                                displayText = "t: " + tPrevs[0] + " ";
                                displayText += "assembly line 1: " + assemblyLines[0][0] + assemblyLines[0][1] + assemblyLines[0][2] + assemblyLines[0][3] + assemblyLines[0][4] + assemblyLines[0][5] + " ";;
                                displayText += "buffer1: " + buffers[0] + " ";
                                displayText += "output: " + output + " ";
                                System.out.println(displayText);
                                outputs[i++] = displayText;
                            }
                            else
                            {
                                if(stasFailed[0]) {staNumFailed = 1;}
                                else if(stasFailed[1]) {staNumFailed = 2;}
                                else if(stasFailed[2]) {staNumFailed = 3;}
                                else if(stasFailed[3]) {staNumFailed = 4;}
                                else if(stasFailed[4]) {staNumFailed = 5;}
                                else if(stasFailed[5]) {staNumFailed = 6;}
                                System.out.println("FAILED LINE 1, STATION: " + staNumFailed);
                                outputs[i++] = "FAILED LINE 1, STATION: " + staNumFailed;
                                if (stasFailed[0])
                                {
                                    try {Thread.sleep(GLAPOptions.DOWN_TIME);} catch (Exception exception) {}
                                }
                                if (stasFailed[1])
                                {
                                    try {Thread.sleep(GLAPOptions.DOWN_TIME);} catch (Exception exception) {}
                                }
                                if (stasFailed[2])
                                {
                                    try {Thread.sleep(GLAPOptions.DOWN_TIME);} catch (Exception exception) {}
                                }
                                if (stasFailed[3])
                                {
                                    try {Thread.sleep(GLAPOptions.DOWN_TIME);} catch (Exception exception) {}
                                }
                                if (stasFailed[4])
                                {
                                    try {Thread.sleep(GLAPOptions.DOWN_TIME);} catch (Exception exception) {}
                                }
                                if (stasFailed[5])
                                {
                                    try {Thread.sleep(GLAPOptions.DOWN_TIME);} catch (Exception exception) {}
                                }
                                displayText = "t: " + tPrevs[0] + " ";
                                displayText += "assembly line 1: " + assemblyLines[0][0] + assemblyLines[0][1] + assemblyLines[0][2] + assemblyLines[0][3] + assemblyLines[0][4] + assemblyLines[0][5] + " ";
                                displayText += "buffer1: " + buffers[0] + " ";
                                displayText += "output: " + output + " ";
                                System.out.println(displayText);
                                outputs[i++] = displayText;
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

//        final TimerTask line2TimerTask = new TimerTask()
//        {
//            @Override
//            public void run()
//            {
//                numOfPartsInLine2 = assemblyLine2[0] + assemblyLine2[1] + assemblyLine2[2];
//                if(((numOfPartsInLine2 == 0) && (buffer1 > 0)) || ((t >= (t2Prev + GLAPOptions.CYCLE_TIME_SECONDS_2))))
//                {
//                    t2Prev = t;
//                    r4 = new Random().nextDouble();
//                    r5 = new Random().nextDouble();
//                    r6 = new Random().nextDouble();
//                    sta4failed = ((assemblyLine2[0] == 1) && (r4 > .92));
//                    sta5failed = ((assemblyLine2[1] == 1) && (r5 > .90));
//                    sta6failed = ((assemblyLine2[2] == 1) && (r6 > .94));
//                    if(!(sta4failed || sta5failed || sta6failed))
//                    {
//                        output += assemblyLine2[2];
//                        assemblyLine2[2] = assemblyLine2[1];
//                        assemblyLine2[1] = assemblyLine2[0];
//                        assemblyLine2[0] = 0;
//                        if (buffer1 > 0)
//                        {
//                            buffer1 -= 1;
//                            assemblyLine2[0] = 1;
//                        }
//                        displayText = "t: " + t + " ";
//                        displayText += "assembly line 2: " + assemblyLine2[0] + assemblyLine2[1] + assemblyLine2[2] + " ";
//                        displayText += "buffer1: " + buffer1 + " ";
//                        displayText += "output: " + output + " ";
//                        System.out.println(displayText);
//                        outputs[i++] = displayText;
//                    }
//                    else
//                    {
//                        if(sta4failed) {staNumFailed = 4;}
//                        else if (sta5failed) {staNumFailed = 5;}
//                        else if(sta6failed) {staNumFailed = 6;}
//                        System.out.println("FAILED LINE 2, STATION: " + staNumFailed);
//                        outputs[i++] = "FAILED LINE 2, STATION: " + staNumFailed;
//                        if(sta4failed)
//                        {
//                            try {Thread.sleep(GLAPOptions.DOWN_TIME);} catch (Exception exception) {}
//                        }
//                        if(sta5failed)
//                        {
//                            try {Thread.sleep(GLAPOptions.DOWN_TIME);} catch (Exception exception) {}
//                        }
//                        if(sta6failed)
//                        {
//                            try {Thread.sleep(GLAPOptions.DOWN_TIME);} catch (Exception exception) {}
//                        }
//                        displayText = "t: " + t2Prev + " ";
//                        displayText += "assembly line 2: " + assemblyLine2[0] + assemblyLine2[1] + assemblyLine2[2] + " ";
//                        displayText += "buffer1: " + buffer1 + " ";
//                        displayText += "output: " + output + " ";
//                        System.out.println(displayText);
//                        outputs[i++] = displayText;
//                        sta4failed = false;
//                        sta5failed = false;
//                        sta6failed = false;
//                        staNumFailed = 0;
//                    }
//                }
//            }
//        };

        masterTimer = new Timer("MasterTimer");
        masterTimer.scheduleAtFixedRate(masterTimerTask, 0, 1);

        infOcnTimer = new Timer("InfOcnTimer");
        infOcnTimer.scheduleAtFixedRate(infOcnTimerTask, 0, 1);

        line1Timer = new Timer("Line1Timer");
        line1Timer.scheduleAtFixedRate(line1TimerTask, 0, 1);

//        line2Timer = new Timer("Line2Timer");
//        line2Timer.scheduleAtFixedRate(line2TimerTask, 0, 1);
//
//        line3Timer = new Timer("Line3Timer");
//        line3Timer.scheduleAtFixedRate(line3TimerTask, 0, 1);
//
//        line4Timer = new Timer("Line4Timer");
//        line4Timer.scheduleAtFixedRate(line4TimerTask, 0, 1);
    }
}
