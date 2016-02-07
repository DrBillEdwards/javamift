# javamift

MIFT - Move It Forward Theory
DOA - Dynamic Operational Availability
GLAP - Great Lakes Assembly Plant
EP = Engine Plant

OPTIONS:

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
    static String NEW_LINES = "\n"; // toggle newline types
    // static String NEW_LINES = "\r\n";

To compile changes and run:

from DOS command line,

    change directory to your project directory
    git pull
    javac mift.SimpleMift.java
    java mift.SimpleMift
OR
    You can always make a new project directory/folder, change directory into that folder and run the following command from the DOS window:
    git clone https://github.com/DrBillEdwards/javamift.git
    This command will download the whole project into the new folder, brand new javamift folder with all my latest changes.
    Then from the current directory you changed to, change directory again into the new javamift, compile, and run.
    javac mift.SimpleMift.java
    java mift.SimpleMift
OR
    Download the zip file of the project with your browser from github, with Download ZIP button located on the right hand side of https://github.com/DrBillEdwards/javamift

You should see something like this,

t1: 54 assembly line 1: 100 buffer: 0
t2: 60 assembly line 2: 000 output: 0 buffer: 0
t1: 108 assembly line 1: 110 buffer: 0
t2: 120 assembly line 2: 000 output: 0 buffer: 0
...
