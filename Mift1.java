import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.io.*;

public class Mift1
{
    static long CYCLE_TIME_SECONDS_1 = 54;
    static long CYCLE_TIME_SECONDS_2 = 60;
    static double RUN_SECONDS = 36000;
    static long DOWN_TIME = 6;
    static boolean appendReport = false;
    static boolean appendOutput = false;
    static boolean interjectDownTime = false;
    static boolean interjectDownTimes = false;
    static int infiniteOcean = 1;
    static int assemblyLine1[] = {0, 0, 0};
    static int buffer1 = 0;
    static int assemblyLine2[] = {0, 0, 0};
    static int output = 0;
    static double r1 = 0;
    static double r2 = 0;
    static double r3 = 0;
    static double r4 = 0;
    static double r5 = 0;
    static double r6 = 0;
    static String REPORT_FILE_NAME = "report1.txt";
    static String OUTPUT_FILE_NAME = "output1.txt";
    static String displayText = "";
    static long SECS_PER_HOUR = 3600;
    static int t = 0;
    static long t1 = 0;
    static long t2 = 0;
    static String outputs[] = new String[40000];
    static int runHour = 1;
    static int i = 0;

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
                fw = new FileWriter(file, appendReport);

                for(int i = 0; i < outputs.length; i++)
                {
                    if(outputs[i] != null)
                    {
                        fw.write(outputs[i] + '\n');
                    }
                }

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
                fw = new FileWriter(file, appendOutput);
                fw.write(String.valueOf(output) + '\n');
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
        TimerTask timerTask1 = new TimerTask()
        {
            @Override
            public void run()
            {
                if(interjectDownTime)
                {
                    displayText = "Deliberate 1/2 hour down time";
                    System.out.println(displayText);
                    outputs[i++] = displayText;
                    try {Thread.sleep(SECS_PER_HOUR / 2);}
                    catch (Exception exception) {}
                    interjectDownTime = false;
                    t1 += SECS_PER_HOUR / 2;
                }
                else
                {
                    if(interjectDownTimes)
                    {
                        if((runHour == 1) && t >= SECS_PER_HOUR)
                        {
                            displayText = "Deliberate 1/2 hour down time, hour " + runHour;
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
                        else if((runHour == 10) && (t >= (SECS_PER_HOUR * 10)))
                        {
                            displayText = "Deliberate 1/2 hour down time, hour " + runHour;
                            System.out.println(displayText);
                            outputs[i++] = displayText;
                            try {Thread.sleep(SECS_PER_HOUR / 2);} catch (Exception exception) {}
                            runHour++;
                            t1 += SECS_PER_HOUR / 2;
                        }
                    }
                }
                Random rand = new Random();
                r1 = new Random().nextDouble();
                r2 = new Random().nextDouble();
                r3 = new Random().nextDouble();
                if (!(((assemblyLine1[0] == 1) && (r1 >= .98)) || ((assemblyLine1[1] == 1) && (r2 >= .95)) || ((assemblyLine1[2] == 1) && (r3 >= .99)))) {
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
                    System.out.println("FAILED1:");
                    outputs[i++] = "FAILED1:";
                    try {Thread.sleep(DOWN_TIME);}
                    catch (Exception exception) {}
                    t1 += DOWN_TIME;
                    displayText = "t1: " + Math.round(t1) + " ";
                    displayText += "assembly line 1: " + assemblyLine1[0] + assemblyLine1[1] + assemblyLine1[2] + " ";
                    displayText += "buffer1: " + buffer1;
                    System.out.println(displayText);
                    outputs[i++] = displayText;
                }
            }
        };

        TimerTask timerTask2 = new TimerTask()
        {
            @Override
            public void run()
            {
                Random rand = new Random();
                r4 = new Random().nextDouble();
                r5 = new Random().nextDouble();
                r6 = new Random().nextDouble();
                if(!(((assemblyLine2[0] == 1) && (r4 >= .92)) || ((assemblyLine2[1] == 1) && (r5 >= .90)) || ((assemblyLine2[2] == 1) && (r6 >= .94))))
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
                    System.out.println("FAILED2:");
                    outputs[i++] = "FAILED2:";
                    try {Thread.sleep(DOWN_TIME);}
                    catch(Exception exception) {}
                    t2 += DOWN_TIME;
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
            public void run()
            {
                t += 1;
                if(t > RUN_SECONDS)
                {
                    writeToOutputFile(REPORT_FILE_NAME);
                    writeToReportFile(OUTPUT_FILE_NAME);
                    System.exit(0);
                }
            }
        };

        Timer timer3 = new Timer("MyTimer3");
        timer3.scheduleAtFixedRate(timerTask3, 0, 1);

        Timer timer1 = new Timer("MyTimer1");
        timer1.scheduleAtFixedRate(timerTask1, 0, CYCLE_TIME_SECONDS_1);

        Timer timer2 = new Timer("MyTimer2");
        timer2.scheduleAtFixedRate(timerTask2, 0, CYCLE_TIME_SECONDS_2);
    }
}