# javamift

MIFT - Move It Forward Theory

OPTIONS:

    CYCLE_TIME_SECONDS_1 = 54
    CYCLE_TIME_SECONDS_2 = 60
    RUN_SECONDS = 36000
    DOWN_TIME = 6
    appendReport = false
    appendOutput = false
    injectDownTime = false
    interjectDownTimes = true
    BUFFER_MAX = 5 : for Mift 2 implementation
    r1Limit = .98
    r2Limit = .95
    r3Limit = .99
    r4Limit = .98
    r5Limit = .90
    r6Limit = .92
    r7Limit = .75
    r8Limit = .50
    r9Limit = .90
    REPORT_FILE_NAME = "report2.txt"
    OUTPUT_FILE_NAME = "output2.txt"

To compile changes and run:

from DOS command line,

    change directory to your project directory
    git pull
    javac Mift1.java
    java Mift1
OR
    You can always make a new project directory/folder, change directory into that folder and run the following command from the DOS window:
    git clone https://github.com/DrBillEdwards/javamift.git
    This command will download the whole project into the new folder, brand new javamift folder with all my latest changes.
    Then from the current directory you changed to, change directory again into the new javamift, compile, and run.
    javac Mift1.java
    java Mift1

You should see something like this,

t1: 54 assembly line 1: 100 buffer: 0
t2: 60 assembly line 2: 000 output: 0 buffer: 0
t1: 108 assembly line 1: 110 buffer: 0
t2: 120 assembly line 2: 000 output: 0 buffer: 0
t1: 162 assembly line 1: 111 buffer: 0
t2: 180 assembly line 2: 000 output: 0 buffer: 0
t1: 216 assembly line 1: 111 buffer: 1
t2: 240 assembly line 2: 100 output: 0 buffer: 0
t1: 270 assembly line 1: 111 buffer: 1
t2: 300 assembly line 2: 110 output: 0 buffer: 0
t1: 324 assembly line 1: 111 buffer: 1
t2: 360 assembly line 2: 111 output: 0 buffer: 0
t1: 378 assembly line 1: 111 buffer: 1
t2: 420 assembly line 2: 111 output: 1 buffer: 0
t1: 432 assembly line 1: 111 buffer: 1
FAILED2:
t2: 426 assembly line 2: 111 buffer: 1
t1: 486 assembly line 1: 111 buffer: 2
t2: 486 assembly line 2: 111 output: 2 buffer: 1
t1: 540 assembly line 1: 111 buffer: 2
t1: 594 assembly line 1: 111 buffer: 2
t2: 546 assembly line 2: 111 output: 3 buffer: 1
t1: 648 assembly line 1: 111 buffer: 2
t2: 606 assembly line 2: 111 output: 4 buffer: 1
t1: 702 assembly line 1: 111 buffer: 2
t2: 666 assembly line 2: 111 output: 5 buffer: 1
t1: 756 assembly line 1: 111 buffer: 2
t2: 726 assembly line 2: 111 output: 6 buffer: 1
t1: 810 assembly line 1: 111 buffer: 2
FAILED2:
t2: 732 assembly line 2: 111 buffer: 2
t1: 864 assembly line 1: 111 buffer: 2
t2: 792 assembly line 2: 111 output: 7 buffer: 1
t1: 918 assembly line 1: 111 buffer: 2
t2: 852 assembly line 2: 111 output: 8 buffer: 1
t1: 972 assembly line 1: 111 buffer: 2
FAILED2:
t2: 858 assembly line 2: 111 buffer: 2
t1: 1026 assembly line 1: 111 buffer: 2
FAILED2:
t1: 1080 assembly line 1: 111 buffer: 2
t2: 864 assembly line 2: 111 buffer: 2
t1: 1134 assembly line 1: 111 buffer: 2
FAILED2:
t2: 870 assembly line 2: 111 buffer: 2
t1: 1188 assembly line 1: 111 buffer: 2
t2: 930 assembly line 2: 111 output: 9 buffer: 1
...
