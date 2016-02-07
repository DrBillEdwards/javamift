import mift.GLAPOptions;
import java.text.DecimalFormat;

public class HelloWorld
{
    public static void main(String[] args)
    {
        System.out.println("Hello World");
	    System.out.println("This is where we prove MIFT to the world!!");
        System.out.println((7200 % 3600));
        double avgOutput = 33.0 / 5;
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        System.out.println(decimalFormat.format(avgOutput));
    }
}