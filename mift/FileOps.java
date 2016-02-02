package mift;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileOps
{
    public static void createFile(String fileName)
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
                fw.close();
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
        }
        catch(Exception exception){}
    }

    public static void writeToReportFile(String fileName, String[] outputs, boolean appendReport)
    {
        try
        {
            FileWriter fw = null;
            File file = null;
            try
            {
                file = new File(fileName);
                fw = new FileWriter(file, appendReport);

                for(int i = 0; i < outputs.length; i++)
                {
                    if(outputs[i] != null)
                    {
                        fw.write(outputs[i] + SimpleMiftOptions.NEW_LINES);
                    }
                }

                fw.flush();
                fw.close();
                System.out.println(fileName + " written successfully");
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
        }
        catch(Exception exception){}
    }

    public static void writeToOutputFile(String fileName, int output, boolean appendOutput)
    {
        try
        {
            FileWriter fw = null;
            File file = null;
            try
            {
                file = new File(fileName);
                fw = new FileWriter(file, appendOutput);
                fw.write(String.valueOf(output) + SimpleMiftOptions.NEW_LINES);
                fw.flush();
                fw.close();
                System.out.println(fileName + " written succesfully");
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
        }
        catch(Exception exception){}
    }

    public static void writeToOutputFile(String fileName, String output, boolean appendOutput)
    {
        try
        {
            FileWriter fw = null;
            File file = null;
            try
            {
                file = new File(fileName);
                fw = new FileWriter(file, appendOutput);
                fw.write(String.valueOf(output) + SimpleMiftOptions.NEW_LINES);
                fw.flush();
                fw.close();
                System.out.println(fileName + " written succesfully");
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
        }
        catch(Exception exception){}
    }
}
