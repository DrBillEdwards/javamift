import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.io.*;

public class SimpleMift
{
    // OPTIONS
    static long CYCLE_TIME_SECONDS_1 = 54;
    static long CYCLE_TIME_SECONDS_2 = 60;
    static double RUN_SECONDS = 3600;
    static long DOWN_TIME = 6;
    static int NUM_RUNS = 1;
    static boolean interjectDownTime = false; // EITHER
    static boolean interjectDownTimes = false; // OR
    static boolean infOceanDownTimes = false; // EITHER
    static boolean run20Down20InfOcn = true; // OR
    static boolean run20Down20Line1 = false; // EITHER
    static String REPORT_FILE_NAME = "report1.txt";
    static String OUTPUT_FILE_NAME = "output1.txt";
    static boolean appendReport = true;
    static boolean appendOutput = true;
    static String NEW_LINES = "\n"; // toggle newline types
    // static String NEW_LINES = "\r\n";

    // Static Variables
    static int infOcn = 1;
    //static int assemblyLine1[] = {0, 0, 0}; // toggle me
    static int assemblyLine1[] = {1, 1, 1}; // toggle me
    static int buffer1 = 2;
    // static int assemblyLine2[] = {0, 0, 0}; // toggle me
    static int assemblyLine2[] = {1, 1, 1}; // toggle me
    static int output = 0;
    static int numOfPartsInLine1 = 0, numOfPartsInLine2 = 0;
    static double r1 = 0;
    static double r2 = 0;
    static double r3 = 0;
    static double r4 = 0;
    static double r5 = 0;
    static double r6 = 0;
    static String displayText = "";
    static String outputs[] = new String[2000000]; // RUN_SECONDS * NUM_RUNS
    static long SECS_PER_HOUR = 3600;
    static long SECS_PER_MIN = 60;
    static int t = 0;
    static int t1Prev = 0;
    static int t2Prev = 0;
    static int runHour = 1;
    static int currRun = 1;
    static int i = 0;
    static boolean sta1failed = false;
    static boolean sta2failed = false;
    static boolean sta3failed = false;
    static boolean sta4failed = false;
    static boolean sta5failed = false;
    static boolean sta6failed = false;
    static int staNumFailed = 0;
    static Timer masterTimer, infOcnTimer, line1Timer, line2Timer;

    public static void main(String[] args)
    {
        TimerTask masterTimerTask = new TimerTask()
        {
            @Override
            public void run()
            {
                t += 1;
                if(t > RUN_SECONDS)
                {
                    writeToOutputFile(OUTPUT_FILE_NAME);
                    writeToReportFile(REPORT_FILE_NAME);
                    if(currRun >= NUM_RUNS)
                    {
                        displayText += NEW_LINES + "t: " + t;
                        System.out.println(displayText);
                        outputs[i++] = displayText;
                        System.exit(0);
                    }
                    t = 0;
                    assemblyLine1 = new int[assemblyLine1.length];
                    buffer1 = 0;
                    assemblyLine2 = new int[assemblyLine2.length];
                    output = 0;
                    outputs = new String[outputs.length];
                    runHour = 1;
                    currRun++;
                }
            }
        };

        TimerTask infOcnTimerTask = new TimerTask()
        {
            @Override
            public void run()
            {
                if(run20Down20InfOcn)
                {
                    if((t > (SECS_PER_MIN * 20)) && (t < ((SECS_PER_MIN * 20) * 2)))
                    {
                        displayText = "FAILED INFINITE OCEAN t: " + t;
                        System.out.println(displayText);
                        outputs[i++] = displayText;
                        infOcn = 0;
                        try {Thread.sleep(SECS_PER_MIN * 20);} catch(Exception exception) {}
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
                if(interjectDownTime && (t >= SECS_PER_HOUR) && runHour == 1)
                {
                    displayText = "Deliberate 1/2 hour down time line 1";
                    System.out.println(displayText);
                    outputs[i++] = displayText;
                    try {Thread.sleep(SECS_PER_HOUR / 2);} catch (Exception exception) {}
                    runHour++;
                }
                else
                {
                    if(interjectDownTimes)
                    {
                        if(((runHour == 1) && (t >= SECS_PER_HOUR)) || ((runHour == 2) && (t >= (SECS_PER_HOUR * 2))) ||
                            ((runHour == 3) && (t >= (SECS_PER_HOUR * 3))) || ((runHour == 4) && (t >= (SECS_PER_HOUR * 4))) ||
                            ((runHour == 5) && (t >= (SECS_PER_HOUR * 5))) || ((runHour == 6) && (t >= (SECS_PER_HOUR * 6))) ||
                            ((runHour == 7) && (t >= (SECS_PER_HOUR * 7))) || ((runHour == 8) && (t >= (SECS_PER_HOUR * 8))) ||
                            ((runHour == 9) && (t >= (SECS_PER_HOUR * 9))))
                        {
                            displayText = "Deliberate 1/2 hour down time line 1, hour " + runHour;
                            System.out.println(displayText);
                            outputs[i++] = displayText;
                            try {Thread.sleep(SECS_PER_HOUR / 2);} catch (Exception exception) {}
                            runHour++;
                        }
                    }
                    else if(infOceanDownTimes)
                    {
                        if(((runHour == 1) && (t >= SECS_PER_HOUR)) || ((runHour == 2) && (t >= (SECS_PER_HOUR * 2))) ||
                            ((runHour == 3) && (t >= (SECS_PER_HOUR * 3))) || ((runHour == 4) && (t >= (SECS_PER_HOUR * 4))) ||
                            ((runHour == 5) && (t >= (SECS_PER_HOUR * 5))) || ((runHour == 6) && (t >= (SECS_PER_HOUR * 6))) ||
                            ((runHour == 7) && (t >= (SECS_PER_HOUR * 7))) || ((runHour == 8) && (t >= (SECS_PER_HOUR * 8))) ||
                            ((runHour == 9) && (t >= (SECS_PER_HOUR * 9))))
                        {
                            displayText = "FAILED INFINITE OCEAN " + runHour;
                            System.out.println(displayText);
                            outputs[i++] = displayText;
                            infOcn = 0;
                            try {Thread.sleep(SECS_PER_HOUR / 2);} catch (Exception exception) {}
                            infOcn = 1;
                            runHour++;
                        }
                    }
                    else if(run20Down20Line1)
                    {
                        if((t >= (SECS_PER_MIN * 20)) && (t < ((SECS_PER_MIN * 20) * 2)))
                        {
                            displayText = "FAILED INFINITE OCEAN t: " + t + " DOWN LINE 1";
                            System.out.println(displayText);
                            outputs[i++] = displayText;
                            try {Thread.sleep(SECS_PER_MIN * 20);} catch (Exception exception) {}
                        }
                    }
                    numOfPartsInLine1 = assemblyLine1[0] + assemblyLine1[1] + assemblyLine1[2];
                    if(((numOfPartsInLine1 == 0) && (infOcn > 0)) || ((t > (t1Prev + CYCLE_TIME_SECONDS_1))))
                    {
                        t1Prev = t;
                        r1 = new Random().nextDouble();
                        r2 = new Random().nextDouble();
                        r3 = new Random().nextDouble();
                        sta1failed = ((assemblyLine1[0] == 1) && (r1 > .98));
                        sta2failed = ((assemblyLine1[1] == 1) && (r2 > .95));
                        sta3failed = ((assemblyLine1[2] == 1) && (r3 > .99));
                        if(!(sta1failed || sta2failed || sta3failed))
                        {
                            if(buffer1 < 2)
                            {
                                buffer1 += assemblyLine1[2];
                                assemblyLine1[2] = assemblyLine1[1];
                                assemblyLine1[1] = assemblyLine1[0];
                                assemblyLine1[0] = infOcn;
                            }
                            displayText = "t: " + t1Prev + " ";
                            displayText += "assembly line 1: " + assemblyLine1[0] + assemblyLine1[1] + assemblyLine1[2] + " ";
                            displayText += "buffer1: " + buffer1 + " ";
                            displayText += "output: " + output + " ";
                            System.out.println(displayText);
                            outputs[i++] = displayText;
                        }
                        else
                        {
                            if(sta1failed) {staNumFailed = 1;}
                            else if(sta2failed) {staNumFailed = 2;}
                            else if(sta3failed) {staNumFailed = 3;}
                            System.out.println("FAILED LINE 1, STATION: " + staNumFailed);
                            outputs[i++] = "FAILED LINE 1, STATION: " + staNumFailed;
                            if(sta1failed)
                            {
                                try {Thread.sleep(DOWN_TIME);} catch (Exception exception) {}
                            }
                            if(sta2failed)
                            {
                                try {Thread.sleep(DOWN_TIME);} catch (Exception exception) {}
                            }
                            if(sta3failed)
                            {
                                try {Thread.sleep(DOWN_TIME);} catch (Exception exception) {}
                            }
                            displayText = "t: " + t1Prev + " ";
                            displayText += "assembly line 1: " + assemblyLine1[0] + assemblyLine1[1] + assemblyLine1[2] + " ";
                            displayText += "buffer1: " + buffer1 + " ";
                            displayText += "output: " + output + " ";
                            System.out.println(displayText);
                            outputs[i++] = displayText;
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
                numOfPartsInLine2 = assemblyLine2[0] + assemblyLine2[1] + assemblyLine2[2];
                if(((numOfPartsInLine2 == 0) && (buffer1 > 0)) || ((t > (t2Prev + CYCLE_TIME_SECONDS_2))))
                {
                    t2Prev = t;
                    r4 = new Random().nextDouble();
                    r5 = new Random().nextDouble();
                    r6 = new Random().nextDouble();
                    sta4failed = ((assemblyLine2[0] == 1) && (r4 > .92));
                    sta5failed = ((assemblyLine2[1] == 1) && (r5 > .90));
                    sta6failed = ((assemblyLine2[2] == 1) && (r6 > .94));
                    if(!(sta4failed || sta5failed || sta6failed))
                    {
                        output += assemblyLine2[2];
                        assemblyLine2[2] = assemblyLine2[1];
                        assemblyLine2[1] = assemblyLine2[0];
                        assemblyLine2[0] = 0;
                        if (buffer1 > 0)
                        {
                            buffer1 -= 1;
                            assemblyLine2[0] = 1;
                        }
                        displayText = "t: " + t + " ";
                        displayText += "assembly line 2: " + assemblyLine2[0] + assemblyLine2[1] + assemblyLine2[2] + " ";
                        displayText += "buffer1: " + buffer1 + " ";
                        displayText += "output: " + output + " ";
                        System.out.println(displayText);
                        outputs[i++] = displayText;
                    }
                    else
                    {
                        if(sta4failed) {staNumFailed = 4;}
                        else if (sta5failed) {staNumFailed = 5;}
                        else if(sta6failed) {staNumFailed = 6;}
                        System.out.println("FAILED LINE 2, STATION: " + staNumFailed);
                        outputs[i++] = "FAILED LINE 2, STATION: " + staNumFailed;
                        if(sta4failed)
                        {
                            try {Thread.sleep(DOWN_TIME);} catch (Exception exception) {}
                        }
                        if(sta5failed)
                        {
                            try {Thread.sleep(DOWN_TIME);} catch (Exception exception) {}
                        }
                        if(sta6failed)
                        {
                            try {Thread.sleep(DOWN_TIME);} catch (Exception exception) {}
                        }
                        displayText = "t: " + t2Prev + " ";
                        displayText += "assembly line 2: " + assemblyLine2[0] + assemblyLine2[1] + assemblyLine2[2] + " ";
                        displayText += "buffer1: " + buffer1 + " ";
                        displayText += "output: " + output + " ";
                        System.out.println(displayText);
                        outputs[i++] = displayText;
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

        line2Timer = new Timer("MyTimer2");
        line2Timer.scheduleAtFixedRate(line2TimerTask, 0, 1);
    }

    public static void writeToReportFile(String fileName)
    {
        try
        {
            FileWriter fw = null;
            File file = null;
            try
            {
                file = new File(fileName);
                if(file.exists())
                {
                    file.delete();
                    file.createNewFile();
                }
                else
                {
                    file.createNewFile();
                }
                fw = new FileWriter(file, appendReport);

                for(int i = 0; i < outputs.length; i++)
                {
                    if(outputs[i] != null)
                    {
                        fw.write(outputs[i] + NEW_LINES);
                    }
                }

                fw.flush();
                fw.close();
                System.out.println(fileName + " written successfully");
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        catch(Exception exception){}
    }

    public static void writeToOutputFile(String fileName)
    {
        try
        {
            FileWriter fw = null;
            File file = null;
            try
            {
                file = new File(fileName);
                if(file.exists())
                {
                    file.delete();
                    file.createNewFile();
                }
                else
                {
                    file.createNewFile();
                }
                fw = new FileWriter(file, appendOutput);
                fw.write(String.valueOf(output) + NEW_LINES);
                fw.flush();
                fw.close();
                System.out.println(fileName + " written succesfully");
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        catch(Exception exception){}
    }
}