import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.io.*;

public class SimpleMift
{
    // OPTIONS
    static long CYCLE_TIME_SECONDS_1 = 54;
    static long CYCLE_TIME_SECONDS_2 = 60;
    static double RUN_SECONDS = 36000;
    static long DOWN_TIME = 6;
    static boolean appendReport = true;
    static boolean appendOutput = true;
    static boolean interjectDownTime = false; // EITHER
    static boolean interjectDownTimes = false; // OR
    static String REPORT_FILE_NAME = "report1.txt";
    static String OUTPUT_FILE_NAME = "output1.txt";
    static int NUM_RUNS = 101;
    static String NEW_LINES = "\n"; // Toggle newline types
    // static String NEW_LINES = "\r\n";
    static boolean INF_OCN_DOWNTIMES = true;

    // Static Variables
    static int infiniteOcean = 1;
    static int assemblyLine1[] = {1, 1, 1};
    static int buffer1 = 2;
    static int assemblyLine2[] = {1, 1, 1};
    static int output = 0;
    static double r1 = 0;
    static double r2 = 0;
    static double r3 = 0;
    static double r4 = 0;
    static double r5 = 0;
    static double r6 = 0;
    static String displayText = "";
    static long SECS_PER_HOUR = 3600;
    static int t = 0;
    static long t1 = 0;
    static long t2 = 0;
    static String outputs[] = new String[2000000]; // RUN_SECONDS * NUM_RUNS
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
    static Timer timer1, timer2, timer3;

    public static void writeToReportFile(String fileName)
    {
        try
        {
            FileWriter fw = null;
            File file = null;
            try
            {
                file = new File(fileName);
                if(!file.exists())
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
                if(!file.exists())
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

    public static void main(String[] args)
    {
        final TimerTask timerTask1 = new TimerTask()
        {
            @Override
            public void run() // Line 1
            {
                if(interjectDownTime && (t >= SECS_PER_HOUR) && runHour == 1)
                {
                    displayText = "Deliberate 1/2 hour down time line 1";
                    System.out.println(displayText);
                    outputs[i++] = displayText;
                    try {Thread.sleep(SECS_PER_HOUR / 2);}
                    catch (Exception exception) {}
                    runHour++;
                    t1 += SECS_PER_HOUR / 2;
                }
                else
                {
                    if(interjectDownTimes)
                    {
                        if((runHour == 1) && t >= SECS_PER_HOUR)
                        {
                            displayText = "Deliberate 1/2 hour down time line 1, hour " + runHour;
                            System.out.println(displayText);
                            outputs[i++] = displayText;
                            try {Thread.sleep(SECS_PER_HOUR / 2);}
                            catch (Exception exception) {}
                            runHour++;
                            t1 += SECS_PER_HOUR / 2;
                        }
                        else if((runHour == 2) && (t >= (SECS_PER_HOUR * 2)))
                        {
                            displayText = "Deliberate 1/2 hour down time, hour " + runHour;
                            System.out.println(displayText);
                            outputs[i++] = displayText;
                            try {Thread.sleep(SECS_PER_HOUR / 2);}
                            catch (Exception exception) {}
                            runHour++;
                            t1 += SECS_PER_HOUR / 2;
                        }
                        else if((runHour == 3) && (t >= (SECS_PER_HOUR * 3)))
                        {
                            displayText = "Deliberate 1/2 hour down time, hour " + runHour;
                            System.out.println(displayText);
                            outputs[i++] = displayText;
                            try {Thread.sleep(SECS_PER_HOUR / 2);}
                            catch (Exception exception) {}
                            runHour++;
                            t1 += SECS_PER_HOUR / 2;
                        }
                        else if((runHour == 4) && (t >= (SECS_PER_HOUR * 4)))
                        {
                            displayText = "Deliberate 1/2 hour down time, hour " + runHour;
                            System.out.println(displayText);
                            outputs[i++] = displayText;
                            try {Thread.sleep(SECS_PER_HOUR / 2);}
                            catch (Exception exception) {}
                            runHour++;
                            t1 += SECS_PER_HOUR / 2;
                        }
                        else if((runHour == 5) && (t >= (SECS_PER_HOUR * 5)))
                        {
                            displayText = "Deliberate 1/2 hour down time, hour " + runHour;
                            System.out.println(displayText);
                            outputs[i++] = displayText;
                            try {Thread.sleep(SECS_PER_HOUR / 2);}
                            catch (Exception exception) {}
                            runHour++;
                            t1 += SECS_PER_HOUR / 2;
                        }
                        else if((runHour == 6) && (t >= (SECS_PER_HOUR * 6)))
                        {
                            displayText = "Deliberate 1/2 hour down time, hour " + runHour;
                            System.out.println(displayText);
                            outputs[i++] = displayText;
                            try {Thread.sleep(SECS_PER_HOUR / 2);}
                            catch (Exception exception) {}
                            runHour++;
                            t1 += SECS_PER_HOUR / 2;
                        }
                        else if((runHour == 7) && (t >= (SECS_PER_HOUR * 7)))
                        {
                            displayText = "Deliberate 1/2 hour down time, hour " + runHour;
                            System.out.println(displayText);
                            outputs[i++] = displayText;
                            try {Thread.sleep(SECS_PER_HOUR / 2);}
                            catch (Exception exception) {}
                            runHour++;
                            t1 += SECS_PER_HOUR / 2;
                        }
                        else if((runHour == 8) && (t >= (SECS_PER_HOUR * 8)))
                        {
                            displayText = "Deliberate 1/2 hour down time, hour " + runHour;
                            System.out.println(displayText);
                            outputs[i++] = displayText;
                            try {Thread.sleep(SECS_PER_HOUR / 2);}
                            catch (Exception exception) {}
                            runHour++;
                            t1 += SECS_PER_HOUR / 2;
                        }
                        else if((runHour == 9) && (t >= (SECS_PER_HOUR * 9)))
                        {
                            displayText = "Deliberate 1/2 hour down time, hour " + runHour;
                            System.out.println(displayText);
                            outputs[i++] = displayText;
                            try {Thread.sleep(SECS_PER_HOUR / 2);}
                            catch (Exception exception) {}
                            runHour++;
                            t1 += SECS_PER_HOUR / 2;
                        }
                    }
                }
                if(INF_OCN_DOWNTIMES) {
                    if ((runHour == 1) && t >= SECS_PER_HOUR) {
                        displayText = "FAILED INFINITE OCEAN " + runHour;
                        System.out.println(displayText);
                        outputs[i++] = displayText;
                        infiniteOcean = 0;
                        try {Thread.sleep(SECS_PER_HOUR / 2);} catch (Exception exception) {}
                        infiniteOcean = 1;
                        runHour++;
                        t1 += SECS_PER_HOUR / 2;
                    } else if ((runHour == 2) && (t >= (SECS_PER_HOUR * 2))) {
                        displayText = "FAILED INFINITE OCEAN " + runHour;
                        System.out.println(displayText);
                        outputs[i++] = displayText;
                        infiniteOcean = 0;
                        try {Thread.sleep(SECS_PER_HOUR / 2);} catch (Exception exception) {}
                        infiniteOcean = 1;
                        runHour++;
                        t1 += SECS_PER_HOUR / 2;
                    } else if ((runHour == 3) && (t >= (SECS_PER_HOUR * 3))) {
                        displayText = "FAILED INFINITE OCEAN " + runHour;
                        System.out.println(displayText);
                        outputs[i++] = displayText;
                        infiniteOcean = 0;
                        try {Thread.sleep(SECS_PER_HOUR / 2);} catch (Exception exception) {}
                        infiniteOcean = 1;
                        runHour++;
                        t1 += SECS_PER_HOUR / 2;
                    } else if ((runHour == 4) && (t >= (SECS_PER_HOUR * 4))) {
                        displayText = "FAILED INFINITE OCEAN " + runHour;
                        System.out.println(displayText);
                        outputs[i++] = displayText;
                        infiniteOcean = 0;
                        try {Thread.sleep(SECS_PER_HOUR / 2);} catch (Exception exception) {}
                        infiniteOcean = 1;
                        runHour++;
                        t1 += SECS_PER_HOUR / 2;
                    } else if ((runHour == 5) && (t >= (SECS_PER_HOUR * 5))) {
                        displayText = "FAILED INFINITE OCEAN " + runHour;
                        System.out.println(displayText);
                        outputs[i++] = displayText;
                        infiniteOcean = 0;
                        try {Thread.sleep(SECS_PER_HOUR / 2);} catch (Exception exception) {}
                        infiniteOcean = 1;
                        runHour++;
                        t1 += SECS_PER_HOUR / 2;
                    } else if ((runHour == 6) && (t >= (SECS_PER_HOUR * 6))) {
                        displayText = "FAILED INFINITE OCEAN " + runHour;
                        System.out.println(displayText);
                        outputs[i++] = displayText;
                        infiniteOcean = 0;
                        try {Thread.sleep(SECS_PER_HOUR / 2);} catch (Exception exception) {}
                        infiniteOcean = 1;
                        runHour++;
                        t1 += SECS_PER_HOUR / 2;
                    } else if ((runHour == 7) && (t >= (SECS_PER_HOUR * 7))) {
                        displayText = "FAILED INFINITE OCEAN " + runHour;
                        System.out.println(displayText);
                        outputs[i++] = displayText;
                        infiniteOcean = 0;
                        try {Thread.sleep(SECS_PER_HOUR / 2);} catch (Exception exception) {}
                        infiniteOcean = 1;
                        runHour++;
                        t1 += SECS_PER_HOUR / 2;
                    } else if ((runHour == 8) && (t >= (SECS_PER_HOUR * 8))) {
                        displayText = "FAILED INFINITE OCEAN " + runHour;
                        System.out.println(displayText);
                        outputs[i++] = displayText;
                        infiniteOcean = 0;
                        try {Thread.sleep(SECS_PER_HOUR / 2);} catch (Exception exception) {}
                        infiniteOcean = 1;
                        runHour++;
                        t1 += SECS_PER_HOUR / 2;
                    } else if ((runHour == 9) && (t >= (SECS_PER_HOUR * 9))) {
                        displayText = "FAILED INFINITE OCEAN " + runHour;
                        System.out.println(displayText);
                        outputs[i++] = displayText;
                        infiniteOcean = 0;
                        try {Thread.sleep(SECS_PER_HOUR / 2);} catch (Exception exception) {}
                        infiniteOcean = 1;
                        runHour++;
                        t1 += SECS_PER_HOUR / 2;
                    }
                }
                r1 = new Random().nextDouble();
                r2 = new Random().nextDouble();
                r3 = new Random().nextDouble();
                sta1failed = ((assemblyLine1[0] == 1) && (r1 > .98));
                sta2failed = ((assemblyLine1[1] == 1) && (r2 > .95));
                sta3failed = ((assemblyLine1[2] == 1) && (r3 > .99));
                if (!(sta1failed || sta2failed || sta3failed))
                {
                    if (buffer1 < 2)
                    {
                        buffer1 += assemblyLine1[2];
                        assemblyLine1[2] = assemblyLine1[1];
                        assemblyLine1[1] = assemblyLine1[0];
                        assemblyLine1[0] = infiniteOcean;
                    }
                    t1 += CYCLE_TIME_SECONDS_1;
                    displayText = "t1: " + t1 + " ";
                    displayText += "assembly line 1: " + assemblyLine1[0] + assemblyLine1[1] + assemblyLine1[2] + " ";
                    displayText += "buffer1: " + buffer1;
                    System.out.println(displayText);
                    outputs[i++] = displayText;
                }
                else
                {
                    if(sta1failed){staNumFailed = 1;}
                    else if(sta2failed){staNumFailed = 2;}
                    else if(sta3failed){staNumFailed = 3;}
                    System.out.println("FAILED LINE 1, STATION: " + staNumFailed);
                    outputs[i++] = "FAILED LINE 1, STATION: " + staNumFailed;
                    if(sta1failed)
                    {
                        try {Thread.sleep(DOWN_TIME);}
                        catch (Exception exception) {}
                        t1 += DOWN_TIME;
                        if(sta2failed)
                        {
                            try {Thread.sleep(DOWN_TIME);}
                            catch (Exception exception) {}
                            t1 += DOWN_TIME;
                            if(sta3failed)
                            {
                                try {Thread.sleep(DOWN_TIME);}
                                catch (Exception exception) {}
                                t1 += DOWN_TIME;
                            }
                        }
                    }
                    displayText = "t1: " + Math.round(t1) + " ";
                    displayText += "assembly line 1: " + assemblyLine1[0] + assemblyLine1[1] + assemblyLine1[2] + " ";
                    displayText += "buffer1: " + buffer1;
                    System.out.println(displayText);
                    outputs[i++] = displayText;
                }
            }
        };

        final TimerTask timerTask2 = new TimerTask()
        {
            @Override
            public void run() // Line 2
            {
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
                    if(buffer1 > 0)
                    {
                        buffer1 -= 1;
                        assemblyLine2[0] = 1;
                    }
                    t2 += CYCLE_TIME_SECONDS_2;
                    displayText = "t2: " + t2 + " ";
                    displayText += "assembly line 2: " + assemblyLine2[0] + assemblyLine2[1] + assemblyLine2[2] + " ";
                    displayText += "output: " + output + " ";
                    displayText += "buffer1: " + buffer1;
                    System.out.println(displayText);
                    outputs[i++] = displayText;
                }
                else
                {
                    if(sta4failed){staNumFailed = 4;}
                    else if(sta5failed){staNumFailed = 5;}
                    else if(sta6failed){staNumFailed = 6;}
                    System.out.println("FAILED LINE 2, STATION: " + staNumFailed);
                    outputs[i++] = "FAILED LINE 2, STATION: " + staNumFailed;
                    if(sta4failed)
                    {
                        try {Thread.sleep(DOWN_TIME);}
                        catch (Exception exception) {}
                        t2 += DOWN_TIME;
                        if(sta5failed)
                        {
                            try {Thread.sleep(DOWN_TIME);}
                            catch (Exception exception) {}
                            t2 += DOWN_TIME;
                            if(sta6failed)
                            {
                                try {Thread.sleep(DOWN_TIME);}
                                catch (Exception exception) {}
                                t2 += DOWN_TIME;
                            }
                        }
                    }
                    displayText = "t2: " + t2 + " ";
                    displayText += "assembly line 2: " + assemblyLine2[0] + assemblyLine2[1] + assemblyLine2[2] + " ";
                    displayText += "buffer1: " + buffer1;
                    System.out.println(displayText);
                    outputs[i++] = displayText;
                }
            }
        };

        TimerTask timerTask3 = new TimerTask()
        {
            @Override
            public void run() // Master time
            {
                t += 1;
                if(t > RUN_SECONDS)
                {
                    t = 0;
                    writeToOutputFile(OUTPUT_FILE_NAME);
                    writeToReportFile(REPORT_FILE_NAME);
                    if(currRun >= NUM_RUNS)
                    {
                        System.exit(0);
                    }
                    assemblyLine1 = new int[assemblyLine1.length];
                    buffer1 = 0;
                    assemblyLine2 = new int[assemblyLine2.length];
                    output = 0;
                    t1 = 0;
                    t2 = 0;
                    outputs = new String[outputs.length];
                    runHour = 1;
                    currRun++;
                }
            }
        };

        timer3 = new Timer("MyTimer3");
        timer3.scheduleAtFixedRate(timerTask3, 0, 1);

        timer1 = new Timer("MyTimer1");
        timer1.scheduleAtFixedRate(timerTask1, 0, CYCLE_TIME_SECONDS_1);

        timer2 = new Timer("MyTimer2");
        timer2.scheduleAtFixedRate(timerTask2, 0, CYCLE_TIME_SECONDS_2);
    }
}