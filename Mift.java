import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.io.*;

public class Mift
{
    static String file = "thritySixRuns.txt";
    static long t1 = 0;
    static long t2 = 0;
    static long CYCLE_TIME_SECONDS_1 = 54; //* 1000
    static long CYCLE_TIME_SECONDS_2 = 60; //* 1000
    static double RUN_SECONDS = 36000; //* 1000
    static long DOWN_TIME = 6; //* 1000
    static int infiniteOcean = 1;
    static int assemblyLine1[] = {0, 0, 0};
    static int buffer = 0;
    static int assemblyLine2[] = {0, 0, 0};
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
    static boolean tWasSecsPerHour = false;
    static String outputs[] = new String[40000];
    static int i = 0;

    public static void main(String[] args)
    {
        TimerTask timerTask1 = new TimerTask()
        {
            @Override
            public void run()
            {
                if (!tWasSecsPerHour && (t >= SECS_PER_HOUR))
                {
                    tWasSecsPerHour = true;
                    displayText = "Deliberate 1/2 hour down time";
                    System.out.println(displayText);
                    outputs[i++] = displayText;
                    try {Thread.sleep(SECS_PER_HOUR);}
                    catch (Exception exception) {}
                    t1 += SECS_PER_HOUR;
                }
                Random rand = new Random();
                r1 = new Random().nextDouble();
                r2 = new Random().nextDouble();
                r3 = new Random().nextDouble();
                if (!(((assemblyLine1[0] == 1) && (r1 >= .98)) || ((assemblyLine1[1] == 1) && (r2 >= .95)) || ((assemblyLine1[2] == 1) && (r3 >= .99)))) {
                    if (buffer < 2) {
                        buffer += assemblyLine1[2];
                        assemblyLine1[2] = assemblyLine1[1];
                        assemblyLine1[1] = assemblyLine1[0];
                        assemblyLine1[0] = infiniteOcean;
                    }
                    t1 += CYCLE_TIME_SECONDS_1;
                    displayText = "t1: " + t1 + " ";
                    displayText += "assembly line 1: " + assemblyLine1[0] + assemblyLine1[1] + assemblyLine1[2] + " ";
                    displayText += "buffer: " + buffer;
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
                    displayText += "buffer: " + buffer;
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
                    if(buffer > 0)
                    {
                        buffer -= 1;
                        assemblyLine2[0] = 1;
                    }
                    t2 += CYCLE_TIME_SECONDS_2;
                    displayText = "t2: " + t2 + " ";
                    displayText += "assembly line 2: " + assemblyLine2[0] + assemblyLine2[1] + assemblyLine2[2] + " ";
                    displayText += "output: " + output + " ";
                    displayText += "buffer: " + buffer;
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
                    displayText += "buffer: " + buffer;
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
                    try
                    {
                        FileWriter fw= null;
                        File file =null;
                        try
                        {
                            file=new File("report.txt");
                            if(!file.exists())
                            {
                                file.createNewFile();
                            }
                            fw = new FileWriter(file);

                            for(int i = 0; i < outputs.length; i++)
                            {
                                if(outputs[i] != null)
                                {
                                    fw.write(outputs[i] + '\n');
                                }
                            }

                            fw.flush();
                            fw.close();
                            System.out.println("File written succesfully");
                        }
                        catch (IOException e)
                        {
                            e.printStackTrace();
                        }
                    }
                    catch(Exception exception){}
                    System.exit(1);
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